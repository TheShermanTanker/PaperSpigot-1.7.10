package org.apache.logging.log4j.core.jmx;

import java.util.List;
import org.apache.logging.log4j.status.StatusData;

public interface StatusLoggerAdminMBean {
  public static final String NAME = "org.apache.logging.log4j2:type=StatusLogger";
  
  public static final String NOTIF_TYPE_DATA = "com.apache.logging.log4j.core.jmx.statuslogger.data";
  
  public static final String NOTIF_TYPE_MESSAGE = "com.apache.logging.log4j.core.jmx.statuslogger.message";
  
  List<StatusData> getStatusData();
  
  String[] getStatusDataHistory();
  
  String getLevel();
  
  void setLevel(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\StatusLoggerAdminMBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */