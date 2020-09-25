/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class RemoteStatusReply {
/*    */   private ByteArrayOutputStream buffer;
/*    */   
/*    */   public RemoteStatusReply(int paramInt) {
/* 10 */     this.buffer = new ByteArrayOutputStream(paramInt);
/* 11 */     this.stream = new DataOutputStream(this.buffer);
/*    */   }
/*    */   private DataOutputStream stream;
/*    */   public void write(byte[] paramArrayOfbyte) {
/* 15 */     this.stream.write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*    */   }
/*    */   
/*    */   public void write(String paramString) {
/* 19 */     this.stream.writeBytes(paramString);
/* 20 */     this.stream.write(0);
/*    */   }
/*    */   
/*    */   public void write(int paramInt) {
/* 24 */     this.stream.write(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(short paramShort) {
/* 29 */     this.stream.writeShort(Short.reverseBytes(paramShort));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getBytes() {
/* 41 */     return this.buffer.toByteArray();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 45 */     this.buffer.reset();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RemoteStatusReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */