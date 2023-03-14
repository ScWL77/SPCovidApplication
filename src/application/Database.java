package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {
	
	
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/project?useSSL=false";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "";
    public static final String SELECT_QUERY = "SELECT * FROM users WHERE username = ? and password = ?";
    public static final String INSERT_QUERY = " INSERT INTO information (date, temperature, code,image)" + " values(?,?,?,?)";
    public static final String CHECK_QUERY = " SELECT * FROM code WHERE Code = ?";
    public static final String LOAD_QUERY = "SELECT * from information";
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    
    
}

class Validate extends Database{
	public boolean validate(String txtUser, String password) throws SQLException {

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (
            Connection connection = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, txtUser);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }
}

class CheckCode extends Database{
	public boolean checkcode(String code) throws SQLException{
		try (
	            Connection connection = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_QUERY)) {
	            preparedStatement.setString(1, code);

	            System.out.println(preparedStatement);

	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                return true;
	            }


	        } catch (SQLException e) {
	            // print SQL exception information
	            printSQLException(e);
	        }
	        return false;
	}
}

class InsertInfo extends Database{
	public void insert (String temperature, String code, String path) throws SQLException
    {
    	try {
    		Connection connection = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
    		
    		File file = new File(path);
    		FileInputStream fis = new FileInputStream(file);
    		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
    		
    		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
    		preparedStatement.setTimestamp(1, date);
    		preparedStatement.setString(2, temperature);
    		preparedStatement.setString(3, code);
    		preparedStatement.setBinaryStream(4,fis,(int)file.length());
    		
    		preparedStatement.execute();
    		
    	} catch (SQLException | FileNotFoundException e) {
    		printSQLException((SQLException) e);
    	}
    }
}
