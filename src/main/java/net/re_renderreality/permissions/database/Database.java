package net.re_renderreality.permissions.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.sqlite.SQLite;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.Permissions.DebugLevel;
import net.re_renderreality.permissions.Reference;
import net.re_renderreality.permissions.config.readers.MainConfigReader;
import net.re_renderreality.permissions.utils.Log;
import net.re_renderreality.permissions.utils.SQLService;

public class Database {
	public static Connection connections;
	public static List<String> queue = new ArrayList<String>();
	
	public static void setup(FMLPreInitializationEvent event) {

		try {
			//creates a .db file if MySQL file is not provieded
			if(!MainConfigReader.useMySQL()) {
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				Path configFile = Paths.get(event.getModConfigurationDirectory().getAbsolutePath() + "\\Permissions\\Data\\");
				if (!Files.exists(configFile)) {
					Log.info("Attempting to generate Data Directory");
					try {
						Files.createDirectories(configFile);
						Log.info("Success!");
					} catch (IOException e) {
						e.printStackTrace();
						Log.info("Failure");
					}
		    	}
				configFile = configFile.resolve("Database.db");
		    	if(!Files.exists(configFile)) {
		    		try {
		    			Files.createFile(configFile);
		    		} catch (IOException e)	{
		    			e.printStackTrace();
		    		}
		    	}
		    	connections = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alexh\\Desktop\\Permissions\\run\\config\\Permissions\\Data\\Database.db");		    	
			} else {
				//Gets MySQL data from the congig file
				String host = MainConfigReader.getMySQLHost();
				String port = String.valueOf(MainConfigReader.getMySQLPort());
				String username = MainConfigReader.getMySQLUsername();
				String password = MainConfigReader.getMySQLPassword();
				String database = MainConfigReader.getMySQLDatabase();
					
				//datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);	
			}
			DatabaseMetaData metadata = connections.getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			//Creates all the tables in the selected database
			List<String> tables = new ArrayList<String>();		
			while(resultset.next()) {
				tables.add(resultset.getString(3));
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (ID INT, uuid VARCHAR(60), name TEXT, IP VARCHAR(45), nick TEXT, channel TEXT, money DOUBLE, banned BOOL, god BOOL, fly BOOL, tptoggle BOOL, invisible BOOL, onlinetime DOUBLE, lastlocation TEXT, lastdeath TEXT, firstseen TEXT, lastseen TEXT)");
				execute("CREATE TABLE mana (ID INT, uuid VARCHAR(60), name TEXT, IP VARCHAR(45), nick TEXT, channel TEXT, money DOUBLE, banned BOOL, god BOOL, fly BOOL, tptoggle BOOL, invisible BOOL, onlinetime DOUBLE, lastlocation TEXT, lastdeath TEXT, firstseen TEXT, lastseen TEXT)");
				execute("INSERT INTO players VALUES (0, '" + "uuid" + "', '" + "name" + "', '" + "192.168.1.1" + "', '" + "nick" + "', '" + "channel" + "', 123.0, 0,  1, 0, 1, 0, 123.0, '" + "LastLocation" + "', '" + "LastDeath" + "', '" + "FirstSeen" + "', '" + "LastSeen" + "');");
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * @param execute string MySQL command to execute
	 */
	public static void execute(String execute) {	
		try {
			Log.info(execute);
			Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alexh\\Desktop\\Permissions\\run\\config\\Permissions\\Data\\Database.db");
			Statement statement = connection.createStatement();
			statement.execute(execute);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
