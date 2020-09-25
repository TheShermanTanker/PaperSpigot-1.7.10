package com.google.common.io;

import java.io.IOException;

public interface InputSupplier<T> {
  T getInput() throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\InputSupplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */