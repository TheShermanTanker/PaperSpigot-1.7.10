package com.avaje.ebeaninternal.server.text.json;

public interface ReadJsonInterface {
  void ignoreWhiteSpace();
  
  char nextChar();
  
  String getTokenKey();
  
  boolean readKeyNext();
  
  boolean readValueNext();
  
  boolean readArrayNext();
  
  String readQuotedValue();
  
  String readUnquotedValue(char paramChar);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonInterface.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */