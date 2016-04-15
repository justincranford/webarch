package org.justin.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogManager wraps SLF4J API 1.7.21 (http://www.slf4j.org/manual.html). Deployment-time binding options to third-party log frameworks are:
 * 
 * slf4j-log4j12-1.7.21.jar
 *  Binding for log4j version 1.2, a widely used logging framework. You also need to place log4j.jar on your class path.
 *  
 * slf4j-jdk14-1.7.21.jar
 *  Binding for java.util.logging, also referred to as JDK 1.4 logging
 *  
 * slf4j-nop-1.7.21.jar
 *  Binding for NOP, silently discarding all logging.
 *  
 * slf4j-simple-1.7.21.jar
 *  Binding for Simple implementation, which outputs all events to System.err. Only messages of level INFO and higher are printed. This binding may be useful in the context of small applications.
 *  
 * slf4j-jcl-1.7.21.jar
 *  Binding for Jakarta Commons Logging. This binding will delegate all SLF4J logging to JCL.
 *  
 * logback-classic-1.0.13.jar (requires logback-core-1.0.13.jar)
 *  NATIVE IMPLEMENTATION There are also SLF4J bindings external to the SLF4J project, e.g. logback which implements SLF4J natively. Logback's ch.qos.logback.classic.Logger class is a direct implementation of SLF4J's org.slf4j.Logger interface. Thus, using SLF4J in conjunction with logback involves strictly zero memory and computational overhead.
 * 
 * @author justin.cranford
 *
 */
public class LogManager {
	// SLF4J STATIC vs INSTANCE: http://wiki.apache.org/commons/Logging/StaticLog
	// Logger object instances can be static in classes loaded in a non-shared class loader (ex: Tomcat WebClassLoader).
	// Logger object instances should only avoid static if in a shared parent class loader (ex: Tomcat CommonClassLoader or SystemClassLoader) 
	private static final Logger LOGGER = LoggerFactory.getLogger(LogManager.class);
	static {
		LOGGER.info("Initialized at {}.", new Date());	// ASSUMPTION: String formatting only if INFO is loggable.
	}

	private LogManager() {
		// do nothing, hide the default constructor
	}
}