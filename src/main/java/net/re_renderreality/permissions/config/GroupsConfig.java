package net.re_renderreality.permissions.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.config.backend.ConfigUtils;
import net.re_renderreality.permissions.config.backend.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 * Handles the config.conf file
 */
public class GroupsConfig implements Configurable
{
	private static GroupsConfig groups = new GroupsConfig();

	private GroupsConfig()
	{
		;
	}

	public static GroupsConfig getConfig()
	{
		return groups;
	}

	private Path configFile = Permissions.INSTANCE.getConfigPath().resolve("Groups.conf");
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
		//Populates with General COnfig information. Anything specialized will be given dedicated .conf file
		ConfigUtils.createCommentNode(get().getNode("Groups"), "List of Groups");
		
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Info", "Suffix"), "");
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Info", "build"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Info", "Prefix"), "&0[&4Owner&0]");
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Inheritance"), "Wood");
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Permissions", "node.example.1"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Permissions", "node.example.2"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Permissions", "node.example.3"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Owner", "Default"), true);
		
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Info", "Suffix"), "");
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Info", "build"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Info", "Prefix"), "&6[&8wood&6]");
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Inheritance"), "Wood");
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Permissions", "node.example.1"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Permissions", "node.example.2"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Permissions", "node.example.3"), true);	
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Default"), true);
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}