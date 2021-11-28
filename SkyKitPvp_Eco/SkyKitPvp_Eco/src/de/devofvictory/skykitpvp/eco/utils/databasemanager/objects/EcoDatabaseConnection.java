package de.devofvictory.skykitpvp.eco.utils.databasemanager.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.devofvictory.skykitpvp.eco.main.EcoMain;
import de.devofvictory.skykitpvp.eco.utils.databasemanager.enums.EcoConnectionState;
import de.devofvictory.skykitpvp.eco.utils.databasemanager.enums.EcoOutputMessage;


public class EcoDatabaseConnection {
	
	private static String host;
	private static String database;
	private static String username;
	private static String password;
	
	private static EcoConnectionState connected;
	private static Connection con;
	
	private static EcoOutputMessage output;
	
	@SuppressWarnings("static-access")
	public EcoDatabaseConnection(String host, String database, String username, String password, EcoOutputMessage messages) {
		
		//Get User Inputs 
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.output = messages;
		
		//Set Default Values
		this.connected = EcoConnectionState.DISCONNECTED;
	}
	
	@SuppressWarnings("static-access")
	public EcoDatabaseConnection(String host, String database, String username, String password) {
		
		//Get User Inputs 
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.output = EcoOutputMessage.EVERYTHING;
		
		//Set Default Values
		this.connected = EcoConnectionState.DISCONNECTED;
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS || output == EcoOutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Deleted from " + table + " where " + request + " = " + result);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS || output == EcoOutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Updated " + line + " to " + value + " where " + request + " = " + result + " in " + table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		
		
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS || output == EcoOutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Updated " + line + " to " + value + " in " + table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS || output == EcoOutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Inserted " + newInfosString + " into " + table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Selected " + column + " from " + table + " where " + request + " == " + result);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Selected * from " + table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Selected " + column + " from " + table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS || output == EcoOutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Created Table " + name + table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		
	}
	
	public void createTableIfNotExist(String name, ArrayList<String> values) {
		
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
		
		qry = "CREATE TABLE IF NOT EXISTS " + name + " " + table;
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS || output == EcoOutputMessage.ONLY_UPDATE_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Created Table " + name+ table);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
				e.printStackTrace();
			}
		}
		
	}
	
	public void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host +  "/" + database, username, password);
				System.out.println(EcoMain.getConsolePrefix() + "Connected to Database(" + host + ")!");
			} catch (SQLException e) {
				e.printStackTrace();
				if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS || output == EcoOutputMessage.ONLY_CONNECTION_INFORMATION) {
					System.out.println(EcoMain.getConsolePrefix() + "Error whilest connecting with Database(" + host + ")!");
				}
			}
		}
		updateConnectionState();
	}
	
	public void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_CONNECTION_INFORMATION) {
					System.out.println(EcoMain.getConsolePrefix() + "Closed Connection with Database(" + host + ")");
				}
			} catch (SQLException e) {
				if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS || output == EcoOutputMessage.ONLY_CONNECTION_INFORMATION) {
					System.out.println(EcoMain.getConsolePrefix() + "Error whilest disconnecting with Database(" + host + ")!");
				}
			}
		}else {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_CONNECTION_INFORMATION) {
				System.out.println(EcoMain.getConsolePrefix() + "Tryed to disconnect with Database(" + host + "), but Database is not connected!");
			}
		}
		connected = EcoConnectionState.DISCONNECTED;

	}
	
	public void executeQuery(String qry) {
		if(!isConnected()) {
			connect();
		}
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(qry);
			ps.executeUpdate();
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Executed: " + qry);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
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
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_EXECUTIONS) {
				System.out.println(EcoMain.getConsolePrefix() + "Executed: " + qry);
			}
		} catch (Exception e) {
			if(output == EcoOutputMessage.EVERYTHING || output == EcoOutputMessage.ONLY_ERRORS) {
				System.out.println(EcoMain.getConsolePrefix() + "Error whilest executing query: " + qry);
			}
		}
		return result;
	}
	
	public boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public void updateConnectionState() {
		if(isConnected()) {
			connected = EcoConnectionState.CONNECTED;
		}else {
			connected = EcoConnectionState.ERROR;
		}
	}
	
	@SuppressWarnings("static-access")
	public void setOutputMessage(EcoOutputMessage output) {
		this.output = output;
	}
	
	public EcoOutputMessage getOutputMessage() {
		return output;
	}
	
	public EcoConnectionState getConnectionState() {
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
