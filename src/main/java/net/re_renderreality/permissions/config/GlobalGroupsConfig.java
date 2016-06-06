package net.re_renderreality.permissions.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.config.backend.ConfigUtils;
import net.re_renderreality.permissions.config.backend.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 * Handles the config.conf file
 */
public class GlobalGroupsConfig implements Configurable
{
	private static GlobalGroupsConfig config = new GlobalGroupsConfig();

	private GlobalGroupsConfig()
	{
		;
	}

	public static GlobalGroupsConfig getConfig()
	{
		return config;
	}

	private Path configFile = Permissions.INSTANCE.getConfigPath().resolve("GlobalGroups.conf");
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
		String groupsComment = "These groups only contain permission nodes. \n" +
				"\n" +
				"**** You can NOT add anything other than permission nodes **** \n" +
				"**** This is NOT where you set up the groups which you give to users! **** \n" +
				"**** goto groupmanager/worlds/worldname/groups.yml if you want to set the actual groups! **** \n" +
				"\n" +
				"These collections are to be inherited in your different worlds groups.yml's \n" +
				"They can also be added as one of a users subgroups, but NOT as a primary group. \n" +
				"These collections are available to ALL group and user yml's. \n" +
				"\n" +
				"Add to and customize these groups to fit your needs.";


		//Populates with General COnfig information. Anything specialized will be given dedicated .conf file
		ConfigUtils.createCommentNode(get().getNode("Groups"),groupsComment);
		
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}
