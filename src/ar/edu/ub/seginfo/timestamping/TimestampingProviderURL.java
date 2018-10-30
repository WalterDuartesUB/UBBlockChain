package ar.edu.ub.seginfo.timestamping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;
import ar.edu.ub.seginfo.exception.TimestampingException;

public class TimestampingProviderURL implements ITimestampingProvider {
	private URL url;
	private String user;
	private String password;

	public TimestampingProviderURL(String url, String user, String password) {
		this.setUrl(url);
		this.setUser(user);
		this.setPassword(password);
	}

	public TimestampingProviderURL(String url) {
		this(url, null, null);
	}

	private URL getUrl() {
		return url;
	}

	private void setUrl(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private String getUser() {
		return user;
	}

	private void setUser(String user) {
		this.user = user;
	}

	private String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	@Override
	public IStampedHashedData stamp(IHashedData data) throws TimestampingException {
		ITimestampResponse tsaResponse = null;

		try {
			TSAClient client = new TSAClient(this.getUrl(), this.getUser(), this.getPassword());

			// Pido al TSA un token
			tsaResponse = client.getTimeStampToken(data);

		} catch (MalformedURLException e) {
			throw new TimestampingException("Ocurrio un error al tratar de obtener el Timestamp para el token. ", e);
		} catch (IOException e) {
			throw new TimestampingException("Ocurrio un error al tratar de obtener el Timestamp para el token. ", e);
		}

		// Devuelvo el tiempo del TSAServer en milisegundos para mantener la
		// compatibilidad con la interfaz

		return new StampedHashedData(data,
				tsaResponse.getSigningTime().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
	}
}
