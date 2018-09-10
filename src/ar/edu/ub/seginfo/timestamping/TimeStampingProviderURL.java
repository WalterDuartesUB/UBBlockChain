package ar.edu.ub.seginfo.timestamping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TimeStampingProviderURL implements ITimeStampingProvider {
	@Override
	public long getTimeStamp( String hashToStamp ) 
	{
		
		String user = null;
		String password = null;
		String url = "https://freetsa.org/tsr";
		
		try 
		{
			//TODO refactorizar para poder enviar un hash sin tener que pasar un digest
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
				
		//TODO consultar que se debe devolver
		return -1;
	}
}
