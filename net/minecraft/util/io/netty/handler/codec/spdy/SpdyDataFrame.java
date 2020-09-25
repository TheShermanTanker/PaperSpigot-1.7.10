package net.minecraft.util.io.netty.handler.codec.spdy;

import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.ByteBufHolder;

public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame {
  SpdyDataFrame setStreamId(int paramInt);
  
  SpdyDataFrame setLast(boolean paramBoolean);
  
  ByteBuf content();
  
  SpdyDataFrame copy();
  
  SpdyDataFrame duplicate();
  
  SpdyDataFrame retain();
  
  SpdyDataFrame retain(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyDataFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */