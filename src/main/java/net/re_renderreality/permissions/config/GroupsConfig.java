package net.re_renderreality.permissions.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jline.internal.Log;
import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.config.backend.ConfigUtils;
import net.re_renderreality.permissions.config.backend.Configs;
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
		
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Info", "Suffix"), "");
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Info", "build"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Info", "Prefix"), "&6[&8wood&6]");
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Inheritance"), "Wood");
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Permissions", "node.example.1"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Permissions", "node.example.2"), true);
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Permissions", "node.example.3"), true);	
		ConfigUtils.createNode(get().getNode("Groups", "Default", "Default"), true);
	}
	
	public void addGroup(String groupname, boolean canBuild, boolean isDefault) 
	{
		Log.info("Attempting Group Genereation");
		if(isDefault) {
			Log.info("Default Group Detected");
			Map<Object, ? extends CommentedConfigurationNode> temp = new HashMap<Object, CommentedConfigurationNode>();
			temp = get().getNode("Groups").getChildrenMap();
			Set<Object> groups = temp.keySet();
			for(Object k : groups) {
				Log.debug(k.toString());
				get().getNode("Groups", k.toString(), "Default").setValue(false);
			}
		}
		Log.info("Group " + groupname + " generating");
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Info", "Suffix"), "");
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Info", "build"), canBuild);
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Info", "Prefix"), "");
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Inheritance"), "Wood");
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Permissions", "node.example.1"), true);
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Permissions", "node.example.2"), true);
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Permissions", "node.example.3"), true);	
		ConfigUtils.createNode(get().getNode("Groups", groupname, "Default"), isDefault);
		save();
		Log.info("Success!");
		
		//Create array of groupnames set default to false for all groups
	}
	
	public void removeGroup(String groupName) {
		get().getNode("Groups", groupName).setValue(null);
		save();
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}