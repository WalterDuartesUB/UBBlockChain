package ar.edu.ub.seginfo.principal;

import ar.edu.ub.seginfo.action.MainWindowAction;

public class Application {

	public static void main(String[] args) {
		/*
		 * Prueba del hashed con archivo local
		TimestampingProviderLocalFile l = new TimestampingProviderLocalFile();		
		l.stamp( new HashedData("asd", null) );
		*/
		MainWindowAction.actionPerformed();
	}

}
