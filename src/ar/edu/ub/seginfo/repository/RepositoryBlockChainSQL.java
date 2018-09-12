package ar.edu.ub.seginfo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import ar.edu.ub.seginfo.model.IBlock;

public class RepositoryBlockChainSQL implements IRepositoryBlockChain {
	
    // If you want to use you ODBC DSN
    //private static final String URL = "jdbc:odbc:TestDB";

//    private static final String USERNAME = "";
//    private static final String PASSWORD = "";
//    private static final String URL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\UB\\UBBlockChain.accdb;}";
	
	public RepositoryBlockChainSQL(){
        Connection connection = null;
        try 
        {
        	//Creo el conector de prueba con ucanaccess para probar la libreria
            connection=DriverManager.getConnection("jdbc:ucanaccess://c:/ub/ubblockchain/database.accdb;memory=true");

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
                
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public IBlock getLastBlock() {
		return null;
	}

	@Override
	public void add(IBlock block) {

	}

	@Override
	public void getAll(Collection<IBlock> collection) {

	}

}
