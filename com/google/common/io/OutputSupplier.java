package com.google.common.io;

import java.io.IOException;

public interface OutputSupplier<T> {
  T getOutput() throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\OutputSupplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */