package ar.edu.ub.seginfo.timestamping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;

public class TimestampingProviderURL implements ITimestampingProvider {
	@Override
	public long getTimeStamp( String hashToStamp ) 
	{
		
		String user = null;
		String password = null;
		String url = "https://freetsa.org/tsr";
		
		ITimestampResponse tsaResponse = null;
		
		try 
		{
			System.out.println( hashToStamp );
			//TODO refactorizar para poder enviar un hash sin tener que pasar un digest
			TSAClient client = new TSAClient( new URL(url), user, password, MessageDigest.getInstance("MD5") );
			
			tsaResponse = client.getTimeStampToken( hashToStamp.getBytes() );
			
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Devuelvo el tiempo del TSAServer en milisegundos para mantener la 
		// compatibilidad con la interfaz
		// Deberia haber un TSAException para el caso en el que el server no responda
		
		return tsaResponse.getSigningTime().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
	}
}
