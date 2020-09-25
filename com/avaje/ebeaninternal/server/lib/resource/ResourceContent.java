package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceContent {
  String getName();
  
  long size();
  
  long lastModified();
  
  InputStream getInputStream() throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\ResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */