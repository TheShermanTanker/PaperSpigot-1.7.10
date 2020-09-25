package org.apache.logging.log4j.core.jmx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface LoggerContextAdminMBean {
  public static final String PATTERN = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s";
  
  public static final String NOTIF_TYPE_RECONFIGURED = "com.apache.logging.log4j.core.jmx.config.reconfigured";
  
  String getStatus();
  
  String getName();
  
  String getConfigLocationURI();
  
  void setConfigLocationURI(String paramString) throws URISyntaxException, IOException;
  
  String getConfigText() throws IOException;
  
  String getConfigText(String paramString) throws IOException;
  
  void setConfigText(String paramString1, String paramString2);
  
  String getConfigName();
  
  String getConfigClassName();
  
  String getConfigFilter();
  
  String getConfigMonitorClassName();
  
  Map<String, String> getConfigProperties();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\LoggerContextAdminMBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */