/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultAddressedEnvelope<M, A extends SocketAddress>
/*     */   implements AddressedEnvelope<M, A>
/*     */ {
/*     */   private final M message;
/*     */   private final A sender;
/*     */   private final A recipient;
/*     */   
/*     */   public DefaultAddressedEnvelope(M message, A recipient, A sender) {
/*  42 */     if (message == null) {
/*  43 */       throw new NullPointerException("message");
/*     */     }
/*     */     
/*  46 */     this.message = message;
/*  47 */     this.sender = sender;
/*  48 */     this.recipient = recipient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultAddressedEnvelope(M message, A recipient) {
/*  56 */     this(message, recipient, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public M content() {
/*  61 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public A sender() {
/*  66 */     return this.sender;
/*     */   }
/*     */ 
/*     */   
/*     */   public A recipient() {
/*  71 */     return this.recipient;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  76 */     if (this.message instanceof ReferenceCounted) {
/*  77 */       return ((ReferenceCounted)this.message).refCnt();
/*     */     }
/*  79 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AddressedEnvelope<M, A> retain() {
/*  85 */     ReferenceCountUtil.retain(this.message);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public AddressedEnvelope<M, A> retain(int increment) {
/*  91 */     ReferenceCountUtil.retain(this.message, increment);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  97 */     return ReferenceCountUtil.release(this.message);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 102 */     return ReferenceCountUtil.release(this.message, decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     if (this.sender != null) {
/* 108 */       return StringUtil.simpleClassName(this) + '(' + this.sender + " => " + this.recipient + ", " + this.message + ')';
/*     */     }
/*     */     
/* 111 */     return StringUtil.simpleClassName(this) + "(=> " + this.recipient + ", " + this.message + ')';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\DefaultAddressedEnvelope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */