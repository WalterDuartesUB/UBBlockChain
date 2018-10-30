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
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

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

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;

/**
 * Time Stamping Authority (TSA) Client [RFC 3161].
 * @author Vakhtang Koroghlishvili
 * @author John Hewson
 */
public class TSAClient
{
    private URL url;
    private String username;
    private String password;    

    /**
     *
     * @param url the URL of the TSA service
     * @param username user name of TSA
     * @param password password of TSA
     */
    public TSAClient(URL url, String username, String password) {
        this.setUrl(url);
        this.setUsername(username);
        this.setPassword(password);		
	}

	/**
     *
     * @param data the hashed data to get stamped
     * @return the encoded time stamp token
     * @throws IOException if there was an error with the connection or data from the TSA server,
     *                     or if the time stamp response could not be validated
     */

    public ITimestampResponse getTimeStampToken(IHashedData data)  throws IOException {
    	
    	// 32-bit cryptographic nonce
    	SecureRandom random = new SecureRandom();
    	int nonce = random.nextInt();
    	
    	// generate TSA request
    	TimeStampRequestGenerator tsaGenerator = new TimeStampRequestGenerator();
    	tsaGenerator.setCertReq(true);
    	ASN1ObjectIdentifier oid = getHashObjectIdentifier( data.getDigestAlgorithm() );
    	TimeStampRequest request = tsaGenerator.generate(oid, data.getHash(), BigInteger.valueOf(nonce));
    	
    	// get TSA response
    	byte[] tsaResponse = getTSAResponse( request.getEncoded() );
    	
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
    	
    	return this.createTimestampResponse( token );    	
    }
    
	private ITimestampResponse createTimestampResponse(TimeStampToken token) throws IOException {
		TimestampResponse responseDto = null;
        
        try {
        	responseDto = new TimestampResponse( this.getSigningTime( token ), this.getEncodedToken( token ) );
		} catch (CMSException e) {
			e.printStackTrace();
		}
        
        return responseDto;
	}

	private LocalDateTime getSigningTime(TimeStampToken token) throws CMSException {
		return getSigningTime( token.getSignedAttributes() );
	}

	private String getEncodedToken(TimeStampToken token) throws IOException {
		return Base64.getEncoder().encodeToString( token.getEncoded() );
	}

    private LocalDateTime getSigningTime(AttributeTable signedAttrTable) throws CMSException {
        
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
        // todo: support proxy servers
        URLConnection connection = getUrl().openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/timestamp-query");

        if (getUsername() != null && getPassword() != null && !getUsername().isEmpty() && !getPassword().isEmpty())
        {
            connection.setRequestProperty(getUsername(), getPassword());
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
            this.closeQuietly(output);
        }

        InputStream input = null;
        byte[] response;
        try
        {
            input = connection.getInputStream();
            response = IOUtils.toByteArray(input);
        }
        finally
        {
            this.closeQuietlly(input);
        }

        return response;
    }

	private void closeQuietlly(InputStream input) {
		IOUtils.closeQuietly(input);
	}

	private void closeQuietly(OutputStream output) {
		IOUtils.closeQuietly(output);
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

	private URL getUrl() {
		return url;
	}

	private String getUsername() {
		return username;
	}

	private String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	private void setUrl(URL url) {
		this.url = url;
	}

}