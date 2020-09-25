package com.avaje.ebeaninternal.server.text.json;

public interface ReadJsonSource {
  char nextChar(String paramString);
  
  void ignoreWhiteSpace();
  
  void back();
  
  int pos();
  
  String getErrorHelp();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */