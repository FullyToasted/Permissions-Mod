package net.re_renderreality.permissions.config.backend;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

/**
 * Template used for all config files.
 */
public interface Configurable {

    void setup();

    void load();

    void save();

    void populate();
    
    void refresh();

    CommentedConfigurationNode get();
}
