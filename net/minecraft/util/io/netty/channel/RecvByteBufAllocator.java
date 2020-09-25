package net.minecraft.util.io.netty.channel;

import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.ByteBufAllocator;

public interface RecvByteBufAllocator {
  Handle newHandle();
  
  public static interface Handle {
    ByteBuf allocate(ByteBufAllocator param1ByteBufAllocator);
    
    int guess();
    
    void record(int param1Int);
  }
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\RecvByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */