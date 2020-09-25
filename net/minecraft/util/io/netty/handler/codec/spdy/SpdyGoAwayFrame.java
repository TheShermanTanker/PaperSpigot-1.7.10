package net.minecraft.util.io.netty.handler.codec.spdy;

public interface SpdyGoAwayFrame extends SpdyFrame {
  int getLastGoodStreamId();
  
  SpdyGoAwayFrame setLastGoodStreamId(int paramInt);
  
  SpdySessionStatus getStatus();
  
  SpdyGoAwayFrame setStatus(SpdySessionStatus paramSpdySessionStatus);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyGoAwayFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */