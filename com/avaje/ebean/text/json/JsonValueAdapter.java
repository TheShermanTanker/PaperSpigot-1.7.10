package com.avaje.ebean.text.json;

import java.sql.Date;
import java.sql.Timestamp;

public interface JsonValueAdapter {
  String jsonFromDate(Date paramDate);
  
  String jsonFromTimestamp(Timestamp paramTimestamp);
  
  Date jsonToDate(String paramString);
  
  Timestamp jsonToTimestamp(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonValueAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */