package net.re_renderreality.permissions;

import java.io.File;
import java.util.Date;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.re_renderreality.permissions.Permissions.DebugLevel;

/**
 * Reference Class to be used by other classes that need access to basic mod information
 * 
 * @author AlexHoff97
 */
public final class Reference {
	/** the mod id */
	public static final String MODID = "permissions";
	/** the mod version */
	public static final String VERSION = "0.0.1";
	/** the mod name */
	public static final String NAME = "Permissions";
	/** the mod network channel name */
	public static final String CHANNEL = "perm";
	/** The website URL */
	public static final String WEBSITE = "http://http://re-renderreality.net/";
	/** The build date */
    public static final Date BUILD = new Date(System.currentTimeMillis()); //gets replaced during build process
    
    private static DebugLevel debug;
    public static final String SERVER_PROXY_CLASS = "net.re_renderreality.permissions.proxy.CommonProxy";
	
	public static final boolean ForceDebug = false;
	
	
	private Reference(FMLPreInitializationEvent event) {
		;
	}
	/**
	 * Initializes the the configuration directories from
	 * {@link FMLPreInitializationEvent#getModConfigurationDirectory()}
	 */
	public static final void init(FMLPreInitializationEvent event) {

	}
	
	public static void setDebugLevel(DebugLevel newDebug) {
		debug = newDebug;
	}
	
	public static DebugLevel getDebugLevel() {
		return debug;
	}
}