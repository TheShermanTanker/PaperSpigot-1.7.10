package net.minecraft.util.com.google.common.io;

import java.io.IOException;

@Deprecated
public interface OutputSupplier<T> {
  T getOutput() throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\OutputSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */