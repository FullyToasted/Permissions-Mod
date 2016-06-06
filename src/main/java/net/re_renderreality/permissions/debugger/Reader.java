package net.re_renderreality.permissions.debugger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.Reference;
import net.re_renderreality.permissions.Permissions.DebugLevel;
import net.re_renderreality.permissions.config.Mirrors;
import net.re_renderreality.permissions.config.backend.ConfigManager;
import net.re_renderreality.permissions.config.backend.Configs;
import net.re_renderreality.permissions.config.backend.Configurable;
import net.re_renderreality.permissions.utils.Log;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class Reader {
	
	private static List<String> langueadaar = new ArrayList<String>();
	private static Configurable mainConfig = Mirrors.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	public static void init() {
		
		CommentedConfigurationNode nodes = Configs.getConfig(mainConfig).getNode("Mirrors", "world");
		if(Reference.getDebugLevel() == DebugLevel.ALL) {
			Map<Object, ? extends CommentedConfigurationNode> nodeList = nodes.getChildrenMap();
			Log.debug("Reader " + nodeList.size());
			Set<Object> keys = nodeList.keySet();
			for(Object s : keys) {
				Log.debug(s.toString());
			}
			Collection<? extends CommentedConfigurationNode> values = nodeList.values();
			for(CommentedConfigurationNode s : values) {
				Log.debug(s.getValue().toString());
			}
		}	
	}
}
