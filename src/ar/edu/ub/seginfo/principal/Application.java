package ar.edu.ub.seginfo.principal;

import java.io.IOException;

import ar.edu.ub.seginfo.action.MainWindowAction;
import ar.edu.ub.seginfo.util.Configuracion;

public class Application {

	public static void main(String[] args) {
		try (Configuracion configuracion = new Configuracion(args.length == 0 ? "configuracion.properties" : args[0])) {
			MainWindowAction.actionPerformed(configuracion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
