/*    */ package net.minecraft.util.io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import org.jboss.marshalling.ByteOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ChannelBufferByteOutput
/*    */   implements ByteOutput
/*    */ {
/*    */   private final ByteBuf buffer;
/*    */   
/*    */   public ChannelBufferByteOutput(ByteBuf buffer) {
/* 36 */     this.buffer = buffer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 51 */     this.buffer.writeByte(b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] bytes) throws IOException {
/* 56 */     this.buffer.writeBytes(bytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] bytes, int srcIndex, int length) throws IOException {
/* 61 */     this.buffer.writeBytes(bytes, srcIndex, length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ByteBuf getBuffer() {
/* 69 */     return this.buffer;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\marshalling\ChannelBufferByteOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */