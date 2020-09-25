package com.avaje.ebeaninternal.server.deploy;

public interface CollectionTypeConverter {
  Object toUnderlying(Object paramObject);
  
  Object toWrapped(Object paramObject);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\CollectionTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */