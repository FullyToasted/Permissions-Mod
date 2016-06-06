package net.re_renderreality.permissions.proxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.re_renderreality.permissions.Permissions;
import net.re_renderreality.permissions.Reference;
import net.re_renderreality.permissions.config.*;
import net.re_renderreality.permissions.config.readers.MainConfigReader;
import net.re_renderreality.permissions.debugger.Reader;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		registerConfig(event);
	}
    public void registerRenderers() {
    	
    }

    public void registerEventHandlers() {
    	 
    }
    
    
    public void registerConfig(FMLPreInitializationEvent event) {
    	Logger logger = Permissions.INSTANCE.logger;
    	String sPath = event.getModConfigurationDirectory().getAbsolutePath() + "\\Permissions\\";
    	Path path = Paths.get(sPath);
    	Permissions.INSTANCE.setConfigPath(path);
    	
    	logger.info(path);
   		if (!Files.exists(path)) {
   			try {
    			logger.info("Attempting to generate Directory");
				Files.createDirectories(path);
				logger.info("Success!");
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("Failure");
			}
    	}
   		MainConfig.getConfig().setup();
   		if(MainConfigReader.getConfigRefresh() || !MainConfigReader.getVersion().equals(Reference.VERSION)) {
   			MainConfig.getConfig().refresh();
   			GlobalGroupsConfig.getConfig().refresh();
	   		GroupsConfig.getConfig().refresh();
	   		Mirrors.getConfig().refresh();
   		} else {
	   		GlobalGroupsConfig.getConfig().setup();
	   		GroupsConfig.getConfig().setup();
	   		Mirrors.getConfig().setup();
   		}
   		Reader.init();
    }
}
