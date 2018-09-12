package ar.edu.ub.seginfo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import ar.edu.ub.seginfo.model.IBlock;

public class RepositoryBlockChainAccess implements IRepositoryBlockChain {
	
	///////////////////////////////////////////////////////////////////////////
	//
	
	private class BlockFieldDB implements IBlock{
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
		public boolean isValidBlock() {
			return false;
		}

		@Override
		public boolean hasTheSameDataThan(IBlock block) {			
			return false;
		}

		private void setPreviousHash(String previousHash) {
			this.previousHash = previousHash;
		}

		private void setHash(String hash) {
			this.hash = hash;
		}
		
	}
		
	///////////////////////////////////////////////////////////////////////////
	//
	
	public RepositoryBlockChainAccess(){		
/*		
        Connection connection = null;
        try 
        {
        	//Creo el conector de prueba con ucanaccess para probar la libreria
            connection = abrirConexionDB();

            // Do something with the connection here!            
            Statement stmt = connection.createStatement();
            
            String sql = "SELECT id, PreviousHash, Hash FROM Blocks";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
               //Retrieve by column name
               int id  = rs.getInt("id");
               String phash = rs.getString("PreviousHash");
               String hash = rs.getString("Hash");               

               //Display values
               System.out.print("ID: " + id);
               System.out.print(", Age: " + phash);
               System.out.println(", Last: " + hash);
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
*/
	}

	private Connection abrirConexionDB() throws SQLException {
		Connection connection;
		connection=DriverManager.getConnection("jdbc:ucanaccess://c:/ub/ubblockchain/database/database.accdb;memory=true");
		return connection;
	}

	private int getLastIdBlockDB() {
		int lastId = 0;
		
		//Pido el max id de la tabla blocks		
		try {
			Connection connection = this.abrirConexionDB();		
            Statement stmt = connection.createStatement();
            
            String sql = "SELECT max( id ) FROM Blocks";
            ResultSet rs = stmt.executeQuery(sql);
            
            if( rs.next() )
            	lastId = rs.getInt(1);
            
            rs.close();
            connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lastId;
	}
	

	@Override
	public boolean isEmpty() {
		return this.getLastIdBlockDB() == 0;
	}

	@Override
	public IBlock getLastBlock() {		
		return this.getBlock( this.getLastIdBlockDB() );
	}

	private IBlock getBlock(int idBlock) {
		IBlock block = null;
				
		try {
			Connection connection = this.abrirConexionDB();
			String sql = "SELECT PreviousHash, Hash FROM Blocks WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement( sql );
            
            stmt.setInt(1, idBlock);
           
            ResultSet rs = stmt.executeQuery();
            
            if( rs.next() )
            {
            	block = new BlockFieldDB( rs.getString("PreviousHash"), rs.getString("Hash") );
            }
            
            rs.close();
            connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return block;
	}

	@Override
	public void add(IBlock block) {
		
		try {
			Connection connection = this.abrirConexionDB();
			String sql = "INSERT INTO Blocks(PreviousHash, Hash) VALUES(?,?)";
            PreparedStatement stmt = connection.prepareStatement( sql );
            
            stmt.setString(1, block.getPreviousHash());
            stmt.setString(2, block.getHash());
           
            int rs = stmt.executeUpdate();
             
            System.out.println( String.format( "RS: %d", rs ) );
            connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void getAll(Collection<IBlock> collection) {
		try {
			Connection connection = this.abrirConexionDB();
			String sql = "SELECT PreviousHash, Hash FROM Blocks ORDER BY Id ASC";
            PreparedStatement stmt = connection.prepareStatement( sql );
            
            ResultSet rs = stmt.executeQuery();
            
            while( rs.next() )
            {
            	collection.add( new BlockFieldDB( rs.getString("PreviousHash"), rs.getString("Hash") ) );
            }
            
            rs.close();
            connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}