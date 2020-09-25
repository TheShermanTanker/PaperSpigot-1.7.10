package net.minecraft.util.io.netty.handler.codec.spdy;

public interface SpdyHeadersFrame extends SpdyStreamFrame {
  boolean isInvalid();
  
  SpdyHeadersFrame setInvalid();
  
  boolean isTruncated();
  
  SpdyHeadersFrame setTruncated();
  
  SpdyHeaders headers();
  
  SpdyHeadersFrame setStreamId(int paramInt);
  
  SpdyHeadersFrame setLast(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeadersFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */