/*     */ package net.minecraft.util.io.netty.handler.codec.serialization;
/*     */ 
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufOutputStream;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*     */ import net.minecraft.util.io.netty.util.Attribute;
/*     */ import net.minecraft.util.io.netty.util.AttributeKey;
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
/*     */ public class CompatibleObjectEncoder
/*     */   extends MessageToByteEncoder<Serializable>
/*     */ {
/*  39 */   private static final AttributeKey<ObjectOutputStream> OOS = new AttributeKey(CompatibleObjectEncoder.class.getName() + ".oos");
/*     */ 
/*     */   
/*     */   private final int resetInterval;
/*     */ 
/*     */   
/*     */   private int writtenObjects;
/*     */ 
/*     */   
/*     */   public CompatibleObjectEncoder() {
/*  49 */     this(16);
/*     */   }
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
/*     */   public CompatibleObjectEncoder(int resetInterval) {
/*  62 */     if (resetInterval < 0) {
/*  63 */       throw new IllegalArgumentException("resetInterval: " + resetInterval);
/*     */     }
/*     */     
/*  66 */     this.resetInterval = resetInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectOutputStream newObjectOutputStream(OutputStream out) throws Exception {
/*  75 */     return new ObjectOutputStream(out);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
/*  80 */     Attribute<ObjectOutputStream> oosAttr = ctx.attr(OOS);
/*  81 */     ObjectOutputStream oos = (ObjectOutputStream)oosAttr.get();
/*  82 */     if (oos == null) {
/*  83 */       oos = newObjectOutputStream((OutputStream)new ByteBufOutputStream(out));
/*  84 */       ObjectOutputStream newOos = (ObjectOutputStream)oosAttr.setIfAbsent(oos);
/*  85 */       if (newOos != null) {
/*  86 */         oos = newOos;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     synchronized (oos) {
/*  91 */       if (this.resetInterval != 0) {
/*     */         
/*  93 */         this.writtenObjects++;
/*  94 */         if (this.writtenObjects % this.resetInterval == 0) {
/*  95 */           oos.reset();
/*     */         }
/*     */       } 
/*     */       
/*  99 */       oos.writeObject(msg);
/* 100 */       oos.flush();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\serialization\CompatibleObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */