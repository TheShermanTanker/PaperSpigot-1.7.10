package net.minecraft.util.io.netty.handler.codec.spdy;

public interface SpdySynReplyFrame extends SpdyHeadersFrame {
  SpdySynReplyFrame setStreamId(int paramInt);
  
  SpdySynReplyFrame setLast(boolean paramBoolean);
  
  SpdySynReplyFrame setInvalid();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdySynReplyFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */