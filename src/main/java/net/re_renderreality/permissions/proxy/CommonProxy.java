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
import net.re_renderreality.permissions.database.Database;
import net.re_renderreality.permissions.debugger.Reader;
import net.re_renderreality.permissions.utils.Log;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		Log.setup(event);
		registerConfig(event);
		Database.setup(event);
	}
    public void registerRenderers() {
    	
    }

    public void registerEventHandlers() {
    	 
    }
    
    
    public void registerConfig(FMLPreInitializationEvent event) {
    	String sPath = event.getModConfigurationDirectory().getAbsolutePath() + "\\Permissions\\";
    	Path path = Paths.get(sPath);
    	Permissions.INSTANCE.setConfigPath(path);
    	
    	Log.debug(sPath);
   		if (!Files.exists(path)) {
   			Log.info("Attempting to generate Directory");
   			try {
				Files.createDirectories(path);
				Log.info("Success!");
			} catch (IOException e) {
				e.printStackTrace();
				Log.info("Failure");
			}
    	}
   		MainConfig.getConfig().setup();
   		Reference.setDebugLevel(MainConfigReader.getDebugLevel());
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
