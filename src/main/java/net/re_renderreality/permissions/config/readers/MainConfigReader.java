package net.re_renderreality.permissions.config.readers;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.Reference;
import net.re_renderreality.permissions.Permissions.DebugLevel;
import net.re_renderreality.permissions.config.MainConfig;
import net.re_renderreality.permissions.config.backend.ConfigManager;
import net.re_renderreality.permissions.config.backend.Configs;
import net.re_renderreality.permissions.config.backend.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class MainConfigReader {
	private static Configurable mainConfig = MainConfig.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	
	//START DEBUG
	/**
	 * @return the enum off the chosen debug level
	 */
	public static DebugLevel getDebugLevel() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "Debug", "Logging Level");
		if (configManager.getString(node).isPresent()) {
			if(configManager.getString(node).equals("ALL")) {
				return DebugLevel.ALL;
			} else if(configManager.getString(node).equals("CONFIG")) {
				return DebugLevel.CONFIG;
			} else if(configManager.getString(node).equals("INFO")) {
				return DebugLevel.INFO;
			} else if(configManager.getString(node).equals("OFF")) {
				return DebugLevel.OFF;
			} else if(configManager.getString(node).equals("SEVERE")) {
				return DebugLevel.SEVERE;
			} else if(configManager.getString(node).equals("WARNING")) {
				return DebugLevel.WARNING;
			} 
		}
		Permissions.INSTANCE.logger.error("Error getting DEBUG level from Config. Resetting to default");
		setSQLPort("INFO");
		return DebugLevel.INFO;
	}
	
	/**
	 * @param value value to set Debug Level to. Must be one of the 6 possible options(ALL, CONFIG, INFO, OFF, SEVERE, WARNING)
	 */
	public static void setDebugLevel(String value)	{
		if(value.equals("ALL") || value.equals("CONFIG") || value.equals("INFO") || value.equals("OFF") || value.equals("SEVERE") || value.equals("WARNING")) {
			Configs.setValue(mainConfig, new Object[] { "Settings", "Debug", "Logging Level" }, value);
		} else {
			Permissions.INSTANCE.logger.error("Error getting DEBUG level to Config. Resetting to default");
			Configs.setValue(mainConfig, new Object[] { "Settings", "Debug", "Logging Level" }, "INFO");
		}
	}
	//END DEBUG	
	
	//START GENERAL
	/**
	 * @return If OP overrides is enabled
	 */
	public static boolean getOPOverride() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "General", "opOverrides");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setOPOverride(true);
		return true;
	}
	
	/**
	 * @param value choice of using OPOverride
	 */
	public static void setOPOverride(boolean value) {
		Configs.setValue(mainConfig, new Object[] { "Settings", "General", "opOverrides" }, value);
	}
	
	/**
	 * @return If Config Refresh is enabled
	 */
	public static boolean getConfigRefresh() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "General", "Config Refresh");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		getConfigRefresh(false);
		return false;
	}
	
	/**
	 * @param value choice of using Config Refresh
	 */
	public static void getConfigRefresh(boolean value) {
		Configs.setValue(mainConfig, new Object[] { "Settings", "General", "Config Refresh" }, value);
	}
	
	//END GENERAL
	
	//START VERSION
	/**
	 * @return The Config Version Number
	 */
	public static String getVersion() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Version#");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setVersion(Reference.VERSION);
		return Reference.VERSION;
	}
	
	/**
	 * @param value Version number to set config to
	 */
	public static void setVersion(String value)	{
		Configs.setValue(mainConfig, new Object[] { "Version#" }, value);
		Configs.saveConfig(mainConfig);
	}
	//END VERSION
	
	//START MYSQL
	/**
	 * @return either the default port or the port set in the config
	 */
	public static String getMySQLPort() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "MYSQL", "port");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLPort("8080");
		return "8080";
	}
	
	/**
	 * @param value value to set the SQL port to. Must be in "number" format
	 */
	public static void setSQLPort(String value)	{
		Configs.setValue(mainConfig, new Object[] { "Settings", "MYSQL", "port" }, value);
	}
	
	/**
	 * @return either the default database name or the database set in the config
	 */
	public static String getMySQLDatabase()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "MYSQL", "database");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLDatabase("Minecraft");
		return "Minecraft";
	}
	
	/**
	 * @param value value to set the Database name too.
	 */
	public static void setSQLDatabase(String value)	{
		Configs.setValue(mainConfig, new Object[] { "Settings", "MYSQL", "database" }, value);
	}
	
	/**
	 * @return either the default password or the password set in the config
	 */
	public static String getMySQLPassword()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "MYSQL", "password");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLPass("password");
		return "password";
	}
	
	/**
	 * @param value value to set the MySQL password to.
	 */
	public static void setSQLPass(String value)	{
		Configs.setValue(mainConfig, new Object[] { "Settings", "MYSQL", "password" }, value);
	}
	
	/**
	 * @return either the default username or the username set in the config
	 */
	public static String getMySQLUsername()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "MYSQL", "username");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLUsername("username");
		return "username";
	}
	
	/**
	 * @param value value to set the MySQL username to.
	 */
	public static void setSQLUsername(String value)	{
		Configs.setValue(mainConfig, new Object[] { "Settings", "MYSQL", "username" }, value);
	}
	
	/**
	 * @return either the default Host or the Host set in the config
	 */
	public static String getMySQLHost()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "MYSQL", "host");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLHost("host");
		return "host";
	}
	
	/**
	 * @param value value to set the MySQL username to.
	 */
	public static void setSQLHost(String value)	{
		Configs.setValue(mainConfig, new Object[] { "Settings", "MYSQL", "host" }, value);
	}
	
	/**
	 * @return the choice of whether or not to use MySQL. default is False
	 */
	public static boolean useMySQL() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Settings", "MYSQL", "use");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setUseMySQL(false);
		return false;
	}
	
	/**
	 * @param value choice of using MySQL True or False
	 */
	public static void setUseMySQL(boolean value) {
		Configs.setValue(mainConfig, new Object[] { "Settings", "MYSQL", "use" }, value);
	}
	//END MYSQL
}
