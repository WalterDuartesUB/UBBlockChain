package ar.edu.ub.seginfo.timestamping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TimeStampingProviderSystem implements ITimeStampingProvider {

	//TODO crear una clase nueva que implemente este codigo
	@Override
	public long getTimeStamp( String hashToStamp ) {
		String user = null;
		String password = null;
		String url = "https://freetsa.org/tsr";
		try {
			TSAClient client = new TSAClient( new URL(url), user, password, MessageDigest.getInstance("MD5") );
			
			client.getTimeStampToken( hashToStamp.getBytes() );
			
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return System.currentTimeMillis();
	}

}
