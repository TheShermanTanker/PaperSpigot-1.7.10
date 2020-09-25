package com.avaje.ebean.config;

public interface EncryptKeyManager {
  void initialise();
  
  EncryptKey getEncryptKey(String paramString1, String paramString2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\EncryptKeyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */