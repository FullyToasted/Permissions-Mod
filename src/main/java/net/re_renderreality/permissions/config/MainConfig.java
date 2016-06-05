package net.re_renderreality.permissions.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.re_renderreality.permissions.Permissions;
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

	private Path configFile = Permissions.INSTANCE.getConfigPath().resolve("Permissions.conf");
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
		//Populates with General COnfig information. Anything specialized will be given dedicated .conf file
		get().getNode("Groups").setComment("List of Groups");
		get().getNode("Groups", "Wood").setValue(1);
		get().getNode("Groups", "Stone").setValue(2);
		get().getNode("Groups", "Iron").setValue(3);
		get().getNode("Groups", "Gold").setValue(4);
		get().getNode("Groups", "Diamond").setValue(5);
	
		
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}