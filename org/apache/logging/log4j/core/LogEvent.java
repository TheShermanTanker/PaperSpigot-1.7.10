package org.apache.logging.log4j.core;

import java.io.Serializable;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.Message;

public interface LogEvent extends Serializable {
  Level getLevel();
  
  String getLoggerName();
  
  StackTraceElement getSource();
  
  Message getMessage();
  
  Marker getMarker();
  
  String getThreadName();
  
  long getMillis();
  
  Throwable getThrown();
  
  Map<String, String> getContextMap();
  
  ThreadContext.ContextStack getContextStack();
  
  String getFQCN();
  
  boolean isIncludeLocation();
  
  void setIncludeLocation(boolean paramBoolean);
  
  boolean isEndOfBatch();
  
  void setEndOfBatch(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\LogEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */