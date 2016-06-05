package net.re_renderreality.permissions.config;

import java.util.List;

import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.config.backend.ConfigManager;
import net.re_renderreality.permissions.config.backend.Configs;
import net.re_renderreality.permissions.config.backend.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class Reader {
	
	private static Configurable mainConfig = MainConfig.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	public static void init() {
		List<? extends CommentedConfigurationNode> list = Configs.getConfig(mainConfig).getChildrenList();
		
		Permissions.INSTANCE.logger.info("Reader" + list.size());
		for(CommentedConfigurationNode n : list) {
			Permissions.INSTANCE.logger.info("loop");
			Permissions.INSTANCE.logger.info(n.getValue());
		}
		
		
	}
}
