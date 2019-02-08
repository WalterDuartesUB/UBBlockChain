package ar.edu.ub.seginfo.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuracion implements Closeable {

	private Properties properties;
	private FileInputStream fis;

	public Configuracion(String fileName) {

		this.setProperties(new Properties());

		try {

			this.setFis(new FileInputStream(fileName));

			getProperties().load(this.getFis());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getConfiguracion(String propiedad) {

		return this.getProperties().getProperty(propiedad);

	}

	public int getConfiguracionAsInt(String propiedad) {
		return Integer.parseInt(this.getProperties().getProperty(propiedad));
	}

	private Properties getProperties() {
		return properties;
	}

	private void setProperties(Properties properties) {
		this.properties = properties;
	}

	private FileInputStream getFis() {
		return fis;
	}

	private void setFis(FileInputStream fis) {
		this.fis = fis;
	}

	@Override
	public void close() throws IOException {
		this.getFis().close();
	}

	public String getConfiguracion(String configuracion, String valorDefault) {
		return this.getProperties().getProperty(configuracion, valorDefault);
	}

	public int getConfiguracionAsInt(String configuracion, int valorDefault) {
		return Integer.parseInt(this.getConfiguracion(configuracion, String.valueOf(valorDefault)));
	}

	public void setConfiguracion(String key, String value) {
		this.getProperties().setProperty(key, value);
	}

}