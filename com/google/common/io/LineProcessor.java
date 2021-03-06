package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.IOException;

@Beta
public interface LineProcessor<T> {
  boolean processLine(String paramString) throws IOException;
  
  T getResult();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\LineProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */