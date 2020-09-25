package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.OutputStream;

@Beta
public final class NullOutputStream extends OutputStream {
  public void write(int b) {}
  
  public void write(byte[] b, int off, int len) {}
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\NullOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */