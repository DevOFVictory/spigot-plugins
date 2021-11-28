package de.devofvictory.skykitpvp.utils.databasemanager.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.devofvictory.skykitpvp.main.Main;
import de.devofvictory.skykitpvp.utils.databasemanager.enums.ConnectionState;
import de.devofvictory.skykitpvp.utils.databasemanager.enums.OutputMessage;



public class DatabaseConnection {
	
	private static String host;
	private static String database;
	private static String username;
	private static String password;
	
	private static ConnectionState connected;
	private static Connection con;
	
	private static OutputMessage output;
	
	@SuppressWarnings("static-access")
	public DatabaseConnection(String host, String database, String username, String password, OutputMessage messages) {
		
		//Get User Inputs 
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.output = messages;
		
		//Set Default Values
		this.connected = ConnectionState.DISCONNECTED;
	}
	
	@SuppressWarnings("static-access")
	public DatabaseConnection(String host, String database, String username, String password) {
		
		//Get User Inputs 
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.output = OutputMessage.EVERYTHING;
		
		//Set Default Values
		this.connected = ConnectionState.DISCONNECTED;
	}
	
	public void delete(String table, String request, String result) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		
		qry = "DELETE FROM " + table + " WHERE " + request + "='" + result + "'";
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS || output == OutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Deleted from " + table + " where " + request + " = " + result);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
	}
	
	public void update(String table, String line, String value, String request, String result) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		
		qry = "UPDATE " + table + " SET " + line + " = '" + value + "' WHERE " + request + "='" + result + "'";
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS || output == OutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Updated " + line + " to " + value + " where " + request + " = " + result + " in " + table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		
		
	}
	
	public ResultSet executeQueryWithResultSet(String qry) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery(qry);
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Executed: " + qry);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		return rs;
	}
	
	public void update(String table, String line, String value) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		
		qry = "UPDATE " + table + " SET " + line + " = '" + value + "'";
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS || output == OutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Updated " + line + " to " + value + " in " + table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		
		
	}
	
	public void insert(String table, ArrayList<String> tableInfos, ArrayList<String> newInfos) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		
		String tableInfosString;
		String newInfosString;
		
		tableInfosString = "(";
		for(int i = 0; i < tableInfos.size(); i++) {
			tableInfosString = tableInfosString + tableInfos.get(i);
			if(i != tableInfos.size() - 1) {
				tableInfosString = tableInfosString + ", ";
			}
		}
		tableInfosString = tableInfosString + ")";
		
		newInfosString = "(";
		for(int i = 0; i < newInfos.size(); i++) {
			newInfosString = newInfosString + "'" + newInfos.get(i) + "'";
			if(i != newInfos.size() - 1) {
				newInfosString = newInfosString + ", ";
			}
		}
		newInfosString = newInfosString + ")";
		
		qry = "INSERT INTO " + table + " " + tableInfosString + " VALUES " + newInfosString;
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS || output == OutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Inserted " + newInfosString + " into " + table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
	}
	
	public String select(String column, String table, String request, String result) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		ResultSet rs;
		String qryResult = null;
		
		qry = "SELECT " + column + " FROM " + table + " WHERE " + request + "='" + result + "'";
		
		try {
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery(qry);
			while(rs.next()){
				qryResult = rs.getString(1);
			}
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Selected " + column + " from " + table + " where " + request + " == " + result);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		return qryResult;
	}
	
	public String select(String table) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		ResultSet rs;
		String result = null;
		
		qry = "SELECT * FROM '" + table + "'";
		
		try {
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery(qry);
			while(rs.next()){
				result = rs.getString(1);
			}
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Selected * from " + table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		return result;
	}
	
	public String select(String column, String table) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		String qry;
		ResultSet rs;
		String result = null;
		
		qry = "SELECT " + column + " FROM '" + table + "'";
		
		try {
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery(qry);
			while(rs.next()){
				result = rs.getString(1);
			}
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Selected " + column + " from " + table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		return result;
	}
	
	public void createTable(String name, ArrayList<String> values) {
		
		if(!isConnected()) {
			connect();
		}
		
		String table;
		PreparedStatement ps;
		String qry;
		
		table = "(";
		for(int i = 0; i < values.size(); i++) {
			table = table + values.get(i);
			if(i != values.size() - 1) {
				table = table + ", ";
			}
		}
		table = table + ")";
		
		qry = "CREATE TABLE " + name + " " + table;
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS || output == OutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Created Table " + name + table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		
	}
	
	public void createTableIfNotExist(String name, ArrayList<String> values, String primaryKey) {
		
		if(!isConnected()) {
			connect();
		}
		
		String table;
		PreparedStatement ps;
		String qry;
		
		table = "(";
		for(int i = 0; i < values.size(); i++) {
			table = table + values.get(i);
			if(i != values.size() - 1) {
				table = table + ", ";
			}
		}
		table = table + ", PRIMARY KEY ("+primaryKey+"))";
		
		qry = "CREATE TABLE IF NOT EXISTS " + name + " " + table;
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS || output == OutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Created Table " + name+ table);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		
	}
	
	public void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host +  "/" + database, username, password);
				System.out.println(Main.getConsolePrefix() + "Connected to Database(" + host + ")!");
			} catch (SQLException e) {
				e.printStackTrace();
				if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS || output == OutputMessage.ONLY_CONNECTION_INFORMATION) {
					System.out.println(Main.getConsolePrefix() + "Error whilest connecting with Database(" + host + ")!");
				}
			}
		}
		updateConnectionState();
	}
	
	public void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_CONNECTION_INFORMATION) {
					System.out.println(Main.getConsolePrefix() + "Closed Connection with Database(" + host + ")");
				}
			} catch (SQLException e) {
				if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS || output == OutputMessage.ONLY_CONNECTION_INFORMATION) {
					System.out.println(Main.getConsolePrefix() + "Error whilest disconnecting with Database(" + host + ")!");
				}
			}
		}else {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_CONNECTION_INFORMATION) {
				System.out.println(Main.getConsolePrefix() + "Tryed to disconnect with Database(" + host + "), but Database is not connected!");
			}
		}
		connected = ConnectionState.DISCONNECTED;

	}
	
	public void executeQuery(String qry) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Executed: " + qry);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
	}
	
	public String executeQueryWithResult(String qry) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		ResultSet rs;
		String result = null;
		
		try {
			ps = con.prepareStatement(qry);
			rs = ps.executeQuery(qry);
			while(rs.next()){
				result = rs.getString(1);
			}
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_EXECUTIONS) {
				System.out.println(Main.getConsolePrefix() + "Executed: " + qry);
			}
		} catch (Exception e) {
			if(output == OutputMessage.EVERYTHING || output == OutputMessage.ONLY_ERRORS) {
				System.out.println(Main.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		return result;
	}
	
	public boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public void updateConnectionState() {
		if(isConnected()) {
			connected = ConnectionState.CONNECTED;
		}else {
			connected = ConnectionState.ERROR;
		}
	}
	
	@SuppressWarnings("static-access")
	public void setOutputMessage(OutputMessage output) {
		this.output = output;
	}
	
	public OutputMessage getOutputMessage() {
		return output;
	}
	
	public ConnectionState getConnectionState() {
		updateConnectionState();
		return connected;
	}
	
	@SuppressWarnings("static-access")
	public void setHost(String host) {
		this.host = host;
		connect();
	}
	
	@SuppressWarnings("static-access")
	public void setDatabaseName(String database) {
		this.database = database;
		connect();
	}
	
	@SuppressWarnings("static-access")
	public void setUsername(String username) {
		this.username = username;
		connect();
	}
	
	@SuppressWarnings("static-access")
	public void setPassword(String password) {
		this.password = password;
		connect();
	}

}
