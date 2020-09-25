package com.avaje.ebean.config.dbplatform;

public interface DbEncryptFunction {
  String getDecryptSql(String paramString);
  
  String getEncryptBindSql();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DbEncryptFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */