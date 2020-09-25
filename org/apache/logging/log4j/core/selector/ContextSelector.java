package org.apache.logging.log4j.core.selector;

import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.core.LoggerContext;

public interface ContextSelector {
  LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean);
  
  LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI);
  
  List<LoggerContext> getLoggerContexts();
  
  void removeContext(LoggerContext paramLoggerContext);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\ContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */