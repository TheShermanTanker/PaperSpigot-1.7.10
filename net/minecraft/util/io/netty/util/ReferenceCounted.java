package net.minecraft.util.io.netty.util;

public interface ReferenceCounted {
  int refCnt();
  
  ReferenceCounted retain();
  
  ReferenceCounted retain(int paramInt);
  
  boolean release();
  
  boolean release(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\ReferenceCounted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */