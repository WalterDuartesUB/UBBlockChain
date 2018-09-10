/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Basado en: https://svn.apache.org/repos/asf/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/signature/TSAClient.java
 * Basado en: https://gist.github.com/Glamdring/c452531e97073a9ab259b987b62bbd77
 */

package ar.edu.ub.seginfo.timestamping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.pdfbox.io.IOUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.Time;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;

/**
 * Time Stamping Authority (TSA) Client [RFC 3161].
 * @author Vakhtang Koroghlishvili
 * @author John Hewson
 */
public class TSAClient
{
	class TimestampResponseDto{

		public void setTime(Object signingTime) {
			// TODO Auto-generated method stub
			
		}

		public void setEncodedToken(String encodeToString) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
//    private static final Log LOG = LogFactory.getLog(TSAClient.class);

    private final URL url;
    private final String username;
    private final String password;
    private final MessageDigest digest;

    /**
     *
     * @param url the URL of the TSA service
     * @param username user name of TSA
     * @param password password of TSA
     * @param digest the message digest to use
     */
    public TSAClient(URL url, String username, String password, MessageDigest digest)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.digest = digest;
    }

    /**
     *
     * @param messageImprint imprint of message contents
     * @return the encoded time stamp token
     * @throws IOException if there was an error with the connection or data from the TSA server,
     *                     or if the time stamp response could not be validated
     */
    public byte[] getTimeStampToken(byte[] messageImprint) throws IOException
    {
        digest.reset();
        byte[] hash = digest.digest(messageImprint);

        // 32-bit cryptographic nonce
        SecureRandom random = new SecureRandom();
        int nonce = random.nextInt();

        // generate TSA request
        TimeStampRequestGenerator tsaGenerator = new TimeStampRequestGenerator();
        tsaGenerator.setCertReq(true);
        ASN1ObjectIdentifier oid = getHashObjectIdentifier(digest.getAlgorithm());
        TimeStampRequest request = tsaGenerator.generate(oid, hash, BigInteger.valueOf(nonce));

        // get TSA response
        byte[] tsaResponse = getTSAResponse(request.getEncoded());

        TimeStampResponse response;
        try
        {
            response = new TimeStampResponse(tsaResponse);
            response.validate(request);
        }
        catch (TSPException e)
        {
            throw new IOException(e);
        }
        
        TimeStampToken token = response.getTimeStampToken();
        if (token == null)
        {
            throw new IOException("Response does not have a time stamp token");
        }

        ///////////////////////////////////////////////////////////////////////
        // Desarmo la respuesta para poder obtener la fecha y la data que fue 
        // TSA-ed
        
        TimestampResponseDto responseDto = new TimestampResponseDto();
        
        try {
			responseDto.setTime(getSigningTime(token.getSignedAttributes()));
			responseDto.setEncodedToken(Base64.getEncoder().encodeToString(token.getEncoded()));
		} catch (CMSException e) {
			e.printStackTrace();
		}

        return token.getEncoded();
    }

    private Object getSigningTime(AttributeTable signedAttrTable) throws CMSException {
        ASN1EncodableVector v = signedAttrTable.getAll(CMSAttributes.signingTime);
        switch (v.size()) {
        case 0:
            return null;
        case 1: {
            Attribute t = (Attribute) v.get(0);
            ASN1Set attrValues = t.getAttrValues();
            if (attrValues.size() != 1) {
                throw new CMSException("A signingTime attribute MUST have a single attribute value");
            }

            Date date = Time.getInstance(attrValues.getObjectAt(0).toASN1Primitive()).getDate();
            LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
            return ldt;
        }
        default:
            throw new CMSException(
                    "The SignedAttributes in a signerInfo MUST NOT include multiple instances of the signingTime attribute");
        }
	}

	// gets response data for the given encoded TimeStampRequest data
    // throws IOException if a connection to the TSA cannot be established
    private byte[] getTSAResponse(byte[] request) throws IOException
    {
//        LOG.debug("Opening connection to TSA server");

        // todo: support proxy servers
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/timestamp-query");

//        LOG.debug("Established connection to TSA server");

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty())
        {
            connection.setRequestProperty(username, password);
        }

        // read response
        OutputStream output = null;
        try
        {
            output = connection.getOutputStream();
            output.write(request);
        }
        finally
        {
        	//TODO resolver esto dentro de otro metodo con los try-catch correspondientes        	
            IOUtils.closeQuietly(output);
        }

//        LOG.debug("Waiting for response from TSA server");

        InputStream input = null;
        byte[] response;
        try
        {
            input = connection.getInputStream();
            response = IOUtils.toByteArray(input);
        }
        finally
        {
        	//TODO resolver esto dentro de otro metodo con los try-catch correspondientes
            IOUtils.closeQuietly(input);
        }

//        LOG.debug("Received response from TSA server");

        return response;
    }

    // returns the ASN.1 OID of the given hash algorithm
    private ASN1ObjectIdentifier getHashObjectIdentifier(String algorithm)
    {
        switch (algorithm)
        {
            case "MD2":
                return new ASN1ObjectIdentifier(PKCSObjectIdentifiers.md2.getId());
            case "MD5":
                return new ASN1ObjectIdentifier(PKCSObjectIdentifiers.md5.getId());
            case "SHA-1":
                return new ASN1ObjectIdentifier(OIWObjectIdentifiers.idSHA1.getId());
            case "SHA-224":
                return new ASN1ObjectIdentifier(NISTObjectIdentifiers.id_sha224.getId());
            case "SHA-256":
                return new ASN1ObjectIdentifier(NISTObjectIdentifiers.id_sha256.getId());
            case "SHA-384":
                return new ASN1ObjectIdentifier(NISTObjectIdentifiers.id_sha384.getId());
            case "SHA-512":
                return new ASN1ObjectIdentifier(NISTObjectIdentifiers.id_sha512.getId());
            default:
                return new ASN1ObjectIdentifier(algorithm);
        }
    }
}