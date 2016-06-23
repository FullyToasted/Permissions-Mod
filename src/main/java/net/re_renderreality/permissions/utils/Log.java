package net.re_renderreality.permissions.utils;

import org.apache.logging.log4j.Logger;

import net.re_renderreality.permissions.Reference;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.re_renderreality.permissions.Permissions.DebugLevel;

public class Log {

	private static Logger logger;
	
	public static void setup(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		logger.info("Logger Activated");
	}
	
	public static void info(String message) {
		if(Reference.getDebugLevel() == DebugLevel.INFO || Reference.getDebugLevel() == DebugLevel.ALL || Reference.getDebugLevel() == DebugLevel.DEBUG) {
			logger.info(message);
		}
	}
	
	public static void warn(String message) {
		logger.warn(message);
		if(Reference.getDebugLevel() == DebugLevel.INFO || Reference.getDebugLevel() == DebugLevel.ALL || Reference.getDebugLevel() == DebugLevel.WARNING || Reference.getDebugLevel() == DebugLevel.DEBUG) {
			logger.warn(message);
		}
	}
	
	public static void severe(String message) {
		if(Reference.getDebugLevel() == DebugLevel.INFO || Reference.getDebugLevel() == DebugLevel.ALL || Reference.getDebugLevel() == DebugLevel.SEVERE || Reference.getDebugLevel() == DebugLevel.DEBUG) {
			logger.error(message);
		}
	}
	
	public static void config(String message) {
		if(Reference.getDebugLevel() == DebugLevel.INFO || Reference.getDebugLevel() == DebugLevel.ALL || Reference.getDebugLevel() == DebugLevel.CONFIG || Reference.getDebugLevel() == DebugLevel.DEBUG) {
			logger.info(message);
		}
	}
	
	public static void debug(String message) {
		if(Reference.getDebugLevel() == DebugLevel.DEBUG || Reference.getDebugLevel() == DebugLevel.ALL) {
			logger.debug(message);
		}
	}
}
