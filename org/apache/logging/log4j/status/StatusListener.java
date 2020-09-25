package org.apache.logging.log4j.status;

import org.apache.logging.log4j.Level;

public interface StatusListener {
  void log(StatusData paramStatusData);
  
  Level getStatusLevel();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */