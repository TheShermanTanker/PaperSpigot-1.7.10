/*    */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WebSocketFrame
/*    */   extends DefaultByteBufHolder
/*    */ {
/*    */   private final boolean finalFragment;
/*    */   private final int rsv;
/*    */   
/*    */   protected WebSocketFrame(ByteBuf binaryData) {
/* 38 */     this(true, 0, binaryData);
/*    */   }
/*    */   
/*    */   protected WebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
/* 42 */     super(binaryData);
/* 43 */     this.finalFragment = finalFragment;
/* 44 */     this.rsv = rsv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFinalFragment() {
/* 52 */     return this.finalFragment;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int rsv() {
/* 59 */     return this.rsv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getClass().getSimpleName() + "(data: " + content().toString() + ')';
/*    */   }
/*    */ 
/*    */   
/*    */   public WebSocketFrame retain() {
/* 75 */     super.retain();
/* 76 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public WebSocketFrame retain(int increment) {
/* 81 */     super.retain(increment);
/* 82 */     return this;
/*    */   }
/*    */   
/*    */   public abstract WebSocketFrame copy();
/*    */   
/*    */   public abstract WebSocketFrame duplicate();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */