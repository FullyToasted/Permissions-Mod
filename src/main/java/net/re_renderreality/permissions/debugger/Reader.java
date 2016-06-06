package net.re_renderreality.permissions.debugger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.config.Mirrors;
import net.re_renderreality.permissions.config.backend.ConfigManager;
import net.re_renderreality.permissions.config.backend.Configs;
import net.re_renderreality.permissions.config.backend.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class Reader {
	
	private static List<String> langueadaar = new ArrayList<String>();
	private static Configurable mainConfig = Mirrors.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	public static void init() {
		
		CommentedConfigurationNode nodes = Configs.getConfig(mainConfig).getNode("Mirrors", "world");
		/*
		List<? extends CommentedConfigurationNode> list = node.getChildrenList();
		
		Permissions.INSTANCE.logger.info("Reader " + list.size());
		for(CommentedConfigurationNode n : list) {
			Permissions.INSTANCE.logger.info("loop");
			Permissions.INSTANCE.logger.info(n.getValue());
		}
		*/
		Map<Object, ? extends CommentedConfigurationNode> nodeList = nodes.getChildrenMap();
		Permissions.INSTANCE.logger.info("Reader " + nodeList.size());
		Set<Object> keys = nodeList.keySet();
		for(Object s : keys) {
			Permissions.INSTANCE.logger.info(s.toString());
		}
		Collection<? extends CommentedConfigurationNode> values = nodeList.values();
		for(CommentedConfigurationNode s : values) {
			Permissions.INSTANCE.logger.info(s.getValue().toString());
		}
		
	}
}
