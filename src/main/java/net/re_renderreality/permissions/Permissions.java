package net.re_renderreality.permissions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.re_renderreality.permissions.config.MainConfig;
import net.re_renderreality.permissions.config.Reader;
import net.re_renderreality.permissions.proxy.CommonProxy;

// dependencies = "required-after:Forge@[" + Reference.MIN_FORGE_VER + ",)",
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Permissions
{    
	public static Logger logger;
	
	private Path configPath; 
	
    @Mod.Instance(Reference.MODID)
    public static Permissions INSTANCE;
    
    @SidedProxy(modId = Reference.MODID, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	this.logger = event.getModLog();
    	
    	String path = event.getModConfigurationDirectory().getAbsolutePath() + "\\Permissions\\";
    	configPath = Paths.get(path); 
    	logger.info(path);
   		if (!Files.exists(configPath))
   		{
   			try
			{
    			logger.info("Attempting to generate Directory");
				Files.createDirectories(configPath);
				logger.info("Success!");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				logger.info("Failure");
			}
    	}
   		
   		MainConfig.getConfig().setup();
   		Reader.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.registerRenderers();
        proxy.registerEventHandlers();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	
    }
    
    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
    	
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	
    }
    
    public Path getConfigPath() {
    	return this.configPath;
    }
}
