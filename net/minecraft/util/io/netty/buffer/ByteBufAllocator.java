package net.minecraft.util.io.netty.buffer;

public interface ByteBufAllocator {
  ByteBuf buffer();
  
  ByteBuf buffer(int paramInt);
  
  ByteBuf buffer(int paramInt1, int paramInt2);
  
  ByteBuf ioBuffer();
  
  ByteBuf ioBuffer(int paramInt);
  
  ByteBuf ioBuffer(int paramInt1, int paramInt2);
  
  ByteBuf heapBuffer();
  
  ByteBuf heapBuffer(int paramInt);
  
  ByteBuf heapBuffer(int paramInt1, int paramInt2);
  
  ByteBuf directBuffer();
  
  ByteBuf directBuffer(int paramInt);
  
  ByteBuf directBuffer(int paramInt1, int paramInt2);
  
  CompositeByteBuf compositeBuffer();
  
  CompositeByteBuf compositeBuffer(int paramInt);
  
  CompositeByteBuf compositeHeapBuffer();
  
  CompositeByteBuf compositeHeapBuffer(int paramInt);
  
  CompositeByteBuf compositeDirectBuffer();
  
  CompositeByteBuf compositeDirectBuffer(int paramInt);
  
  boolean isDirectBufferPooled();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\ByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */