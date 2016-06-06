package net.re_renderreality.permissions.config.backend;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ConfigUtils {
	public static void createNode(CommentedConfigurationNode node) {
		if (node.isVirtual()) {
			node.setValue(null);
		}
	}
	
	public static void createNode(CommentedConfigurationNode node, Object value) {
		if (node.isVirtual()) {
			node.setValue(value);
		}
	}

	public static void createNode(CommentedConfigurationNode node, Object value, String note) {
		if (node.isVirtual()) {
			node.setValue(value).setComment(note);
		} else if (!node.getComment().isPresent() || !node.getComment().get().equals(note)) {
			node.setComment(note);
		}
	}
	
	public static void createCommentNode(CommentedConfigurationNode node, String note) {
		if (node.isVirtual()) {
			node.setComment(note);
		} else if (!node.getComment().isPresent() || !node.getComment().get().equals(note)) {
			node.setComment(note);
		}
	}
}
