package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;

public interface ResourceSource {
  String getRealPath();
  
  ResourceContent getContent(String paramString);
  
  String readString(ResourceContent paramResourceContent, int paramInt) throws IOException;
  
  byte[] readBytes(ResourceContent paramResourceContent, int paramInt) throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\ResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */