package ar.edu.ub.seginfo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RepositoryBlockChainAccess extends RepositoryBlockChainDB {
	private String pathDatabase;

	///////////////////////////////////////////////////////////////////////////
	//

	public RepositoryBlockChainAccess(String pathDatabase) {
		this.setPathDatabase(pathDatabase);
	}

	private String getPathDatabase() {
		return pathDatabase;
	}

	private void setPathDatabase(String pathDatabase) {
		if (pathDatabase.startsWith("/"))
			pathDatabase = "." + pathDatabase;

		this.pathDatabase = pathDatabase;
	}

	@Override
	protected Connection abrirConexionDB() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:ucanaccess://" + this.getPathDatabase() + ";memory=true;immediatelyReleaseResources=true");
	}
}
