package com.avaje.ebean.text.csv;

import com.avaje.ebean.text.StringParser;
import java.io.Reader;
import java.util.Locale;

public interface CsvReader<T> {
  void setDefaultLocale(Locale paramLocale);
  
  void setDefaultTimeFormat(String paramString);
  
  void setDefaultDateFormat(String paramString);
  
  void setDefaultTimestampFormat(String paramString);
  
  void setPersistBatchSize(int paramInt);
  
  void setHasHeader(boolean paramBoolean1, boolean paramBoolean2);
  
  void setAddPropertiesFromHeader();
  
  void setIgnoreHeader();
  
  void setLogInfoFrequency(int paramInt);
  
  void addIgnore();
  
  void addProperty(String paramString);
  
  void addReference(String paramString);
  
  void addProperty(String paramString, StringParser paramStringParser);
  
  void addDateTime(String paramString1, String paramString2);
  
  void addDateTime(String paramString1, String paramString2, Locale paramLocale);
  
  void process(Reader paramReader) throws Exception;
  
  void process(Reader paramReader, CsvCallback<T> paramCsvCallback) throws Exception;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\csv\CsvReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */