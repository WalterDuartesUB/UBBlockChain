package ar.edu.ub.seginfo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import ar.edu.ub.seginfo.exception.RepositoryException;
import ar.edu.ub.seginfo.model.IBlock;

public abstract class RepositoryBlockChainDB implements IRepositoryBlockChain {

	///////////////////////////////////////////////////////////////////////////
	// BlockFieldDB es la clase que se encarga de persistir los datos de la base de
	/////////////////////////////////////////////////////////////////////////// datos

	private class BlockFieldDB implements IBlock {
		private String previousHash;
		private String hash;

		public BlockFieldDB(String previousHash, String hash) {
			this.setPreviousHash(previousHash);
			this.setHash(hash);
		}

		@Override
		public String getPreviousHash() {
			return this.previousHash;
		}

		@Override
		public String getHash() {
			return this.hash;
		}

		@Override
		public boolean hasTheSameDataThan(IBlock block) {
			return this.getHash().equals(block.getHash());
		}

		private void setPreviousHash(String previousHash) {
			this.previousHash = previousHash;
		}

		private void setHash(String hash) {
			this.hash = hash;
		}

	}

	protected abstract Connection abrirConexionDB() throws SQLException;

	private int getLastIdBlockDB() throws RepositoryException {
		int lastId = 0;

		// Pido el max id de la tabla blocks
		try {
			Connection connection = this.abrirConexionDB();
			Statement stmt = connection.createStatement();

			String sql = "SELECT max( id ) FROM Blocks";
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				lastId = rs.getInt(1);

			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			throw new RepositoryException("Ocurrio un error al tratar de obtener el último bloque en el repositorio",
					e);
		}

		return lastId;
	}

	@Override
	public boolean isEmpty() throws RepositoryException {
		return this.getLastIdBlockDB() == 0;
	}

	@Override
	public IBlock getLastBlock() throws RepositoryException {
		return this.getBlock(this.getLastIdBlockDB());
	}

	private IBlock getBlock(int idBlock) throws RepositoryException {
		IBlock block = null;

		try {
			Connection connection = this.abrirConexionDB();
			String sql = "SELECT PreviousHash, Hash FROM Blocks WHERE id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, idBlock);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				block = new BlockFieldDB(rs.getString("PreviousHash"), rs.getString("Hash"));
			}

			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			throw new RepositoryException("Ocurrio un error al tratar de obtener un bloque en el repositorio", e);
		}

		return block;
	}

	@Override
	public void add(IBlock block) throws RepositoryException {

		try {
			Connection connection = this.abrirConexionDB();
			String sql = "INSERT INTO Blocks(PreviousHash, Hash) VALUES(?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, block.getPreviousHash());
			stmt.setString(2, block.getHash());

			stmt.executeUpdate();

			stmt.close();
			connection.close();
		} catch (SQLException e) {
			throw new RepositoryException("Ocurrio un error al tratar de agregar un bloque en el repositorio", e);
		}
	}

	@Override
	public void getAll(Collection<IBlock> collection) throws RepositoryException {
		try {
			Connection connection = this.abrirConexionDB();
			String sql = "SELECT PreviousHash, Hash FROM Blocks ORDER BY Id ASC";
			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				collection.add(new BlockFieldDB(rs.getString("PreviousHash"), rs.getString("Hash")));
			}

			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			throw new RepositoryException("Ocurrio un error al tratar de obtener todos los bloques en el repositorio",
					e);
		}
	}
}
