package com.avaje.ebean.text.json;

public interface JsonElement {
  boolean isPrimitive();
  
  String toPrimitiveString();
  
  Object eval(String paramString);
  
  int evalInt(String paramString);
  
  String evalString(String paramString);
  
  boolean evalBoolean(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */