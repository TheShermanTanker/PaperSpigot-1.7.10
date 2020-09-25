/*    */ package net.minecraft.util.io.netty.channel.udt;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*    */ import net.minecraft.util.io.netty.buffer.DefaultByteBufHolder;
/*    */ import net.minecraft.util.io.netty.util.ReferenceCounted;
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
/*    */ public final class UdtMessage
/*    */   extends DefaultByteBufHolder
/*    */ {
/*    */   public UdtMessage(ByteBuf data) {
/* 31 */     super(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public UdtMessage copy() {
/* 36 */     return new UdtMessage(content().copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public UdtMessage duplicate() {
/* 41 */     return new UdtMessage(content().duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public UdtMessage retain() {
/* 46 */     super.retain();
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public UdtMessage retain(int increment) {
/* 52 */     super.retain(increment);
/* 53 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channe\\udt\UdtMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */