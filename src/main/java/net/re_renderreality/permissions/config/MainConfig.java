package net.re_renderreality.permissions.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.Reference;
import net.re_renderreality.permissions.config.backend.ConfigUtils;
import net.re_renderreality.permissions.config.backend.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 * Handles the config.conf file
 */
public class MainConfig implements Configurable
{
	private static MainConfig config = new MainConfig();

	private MainConfig()
	{
		;
	}

	public static MainConfig getConfig()
	{
		return config;
	}

	private Path configFile = Permissions.INSTANCE.getConfigPath().resolve("Config.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	@Override
	public void setup()
	{
		if (!Files.exists(configFile))
		{
			try
			{
				Files.createFile(configFile);
				load();
				populate();
				save();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			load();
		}
	}

	@Override
	public void refresh()
	{
		load();
		populate();
		save();
	}
	
	@Override
	public void load()
	{
		try
		{
			configNode = configLoader.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void save()
	{
		try
		{
			configLoader.save(configNode);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void populate()
	{
		String debuggingComment = "Level of detail GroupManager will use when logging. \nAcceptable entries are - ALL,CONFIG,FINE,FINER,FINEST,INFO,OFF,SEVERE,WARNING";

		//Populates with General COnfig information. Anything specialized will be given dedicated .conf file
		ConfigUtils.createCommentNode(get().getNode("Settings", "MYSQL"),"MySQL Options for RRRP2.");
		ConfigUtils.createNode(get().getNode("Settings", "MYSQL", "use"), false, "Enables/Disables MySQL usage for Permissions.");
		ConfigUtils.createNode(get().getNode("Settings", "MYSQL", "port"), "8080", "Port of MySQL Database.");
		ConfigUtils.createNode(get().getNode("Settings", "MYSQL", "host"), "localhost", "Address of MySQL Database.");
		ConfigUtils.createNode(get().getNode("Settings", "MYSQL", "database"), "Minecraft", "Name of MySQL Database.");
		ConfigUtils.createNode(get().getNode("Settings", "MYSQL", "username"), "root", "Username for MySQL Database.");
		ConfigUtils.createNode(get().getNode("Settings", "MYSQL", "password"), "pass", "Password for MySQL Database.");
		
		ConfigUtils.createNode(get().getNode("Settings", "Debug", "Logging Level"), "INFO", "Level of info to spit out to the console");
		
		ConfigUtils.createNode(get().getNode("Settings", "General", "opOverrides"), true, "With this enabled anyone set as op has full permissions when managing GroupManager \n The user will be able to promote players to the same group or even above.");
		ConfigUtils.createNode(get().getNode("Settings", "General", "Config Refresh"), false, "Will refresh config files on server bootup \nWill not affect your set values and is done automatically upon Mod update");
		ConfigUtils.createNode(get().getNode("Version#"), Reference.VERSION, "DO NOT CHANGE THIS!!!");
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}
