package org.apache.logging.log4j.core.config;

import java.util.Map;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.Filterable;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.net.Advertiser;

public interface Configuration extends Filterable {
  public static final String CONTEXT_PROPERTIES = "ContextProperties";
  
  String getName();
  
  LoggerConfig getLoggerConfig(String paramString);
  
  Map<String, Appender> getAppenders();
  
  Map<String, LoggerConfig> getLoggers();
  
  void addLoggerAppender(Logger paramLogger, Appender paramAppender);
  
  void addLoggerFilter(Logger paramLogger, Filter paramFilter);
  
  void setLoggerAdditive(Logger paramLogger, boolean paramBoolean);
  
  Map<String, String> getProperties();
  
  void start();
  
  void stop();
  
  void addListener(ConfigurationListener paramConfigurationListener);
  
  void removeListener(ConfigurationListener paramConfigurationListener);
  
  StrSubstitutor getStrSubstitutor();
  
  void createConfiguration(Node paramNode, LogEvent paramLogEvent);
  
  <T> T getComponent(String paramString);
  
  void addComponent(String paramString, Object paramObject);
  
  void setConfigurationMonitor(ConfigurationMonitor paramConfigurationMonitor);
  
  ConfigurationMonitor getConfigurationMonitor();
  
  void setAdvertiser(Advertiser paramAdvertiser);
  
  Advertiser getAdvertiser();
  
  boolean isShutdownHookEnabled();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */