package net.minecraft.util.com.google.common.io;

import java.io.IOException;
import net.minecraft.util.com.google.common.annotations.Beta;

@Beta
public interface LineProcessor<T> {
  boolean processLine(String paramString) throws IOException;
  
  T getResult();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\LineProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */