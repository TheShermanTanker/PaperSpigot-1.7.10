package net.minecraft.util.io.netty.channel;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import net.minecraft.util.io.netty.util.ReferenceCounted;

public interface FileRegion extends ReferenceCounted {
  long position();
  
  long transfered();
  
  long count();
  
  long transferTo(WritableByteChannel paramWritableByteChannel, long paramLong) throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\FileRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */