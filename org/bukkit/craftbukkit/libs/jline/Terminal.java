package org.bukkit.craftbukkit.libs.jline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Terminal {
  void init() throws Exception;
  
  void restore() throws Exception;
  
  void reset() throws Exception;
  
  boolean isSupported();
  
  int getWidth();
  
  int getHeight();
  
  boolean isAnsiSupported();
  
  OutputStream wrapOutIfNeeded(OutputStream paramOutputStream);
  
  InputStream wrapInIfNeeded(InputStream paramInputStream) throws IOException;
  
  boolean hasWeirdWrap();
  
  boolean isEchoEnabled();
  
  void setEchoEnabled(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\Terminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */