package com.avaje.ebean.validation.factory;

public interface Validator {
  String getKey();
  
  Object[] getAttributes();
  
  boolean isValid(Object paramObject);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\Validator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */