package com.avaje.ebean.text.csv;

import com.avaje.ebean.EbeanServer;

public interface CsvCallback<T> {
  void begin(EbeanServer paramEbeanServer);
  
  void readHeader(String[] paramArrayOfString);
  
  boolean processLine(int paramInt, String[] paramArrayOfString);
  
  void processBean(int paramInt, String[] paramArrayOfString, T paramT);
  
  void end(int paramInt);
  
  void endWithError(int paramInt, Exception paramException);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\csv\CsvCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */