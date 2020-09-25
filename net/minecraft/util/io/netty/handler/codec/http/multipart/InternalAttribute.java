/*     */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.util.AbstractReferenceCounted;
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
/*     */ final class InternalAttribute
/*     */   extends AbstractReferenceCounted
/*     */   implements InterfaceHttpData
/*     */ {
/*  31 */   private final List<ByteBuf> value = new ArrayList<ByteBuf>();
/*     */   private final Charset charset;
/*     */   
/*     */   public InternalAttribute(Charset charset) {
/*  35 */     this.charset = charset;
/*     */   }
/*     */   private int size;
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  40 */     return InterfaceHttpData.HttpDataType.InternalAttribute;
/*     */   }
/*     */   
/*     */   public void addValue(String value) {
/*  44 */     if (value == null) {
/*  45 */       throw new NullPointerException("value");
/*     */     }
/*  47 */     ByteBuf buf = Unpooled.copiedBuffer(value, this.charset);
/*  48 */     this.value.add(buf);
/*  49 */     this.size += buf.readableBytes();
/*     */   }
/*     */   
/*     */   public void addValue(String value, int rank) {
/*  53 */     if (value == null) {
/*  54 */       throw new NullPointerException("value");
/*     */     }
/*  56 */     ByteBuf buf = Unpooled.copiedBuffer(value, this.charset);
/*  57 */     this.value.add(rank, buf);
/*  58 */     this.size += buf.readableBytes();
/*     */   }
/*     */   
/*     */   public void setValue(String value, int rank) {
/*  62 */     if (value == null) {
/*  63 */       throw new NullPointerException("value");
/*     */     }
/*  65 */     ByteBuf buf = Unpooled.copiedBuffer(value, this.charset);
/*  66 */     ByteBuf old = this.value.set(rank, buf);
/*  67 */     if (old != null) {
/*  68 */       this.size -= old.readableBytes();
/*     */     }
/*  70 */     this.size += buf.readableBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  75 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  80 */     if (!(o instanceof Attribute)) {
/*  81 */       return false;
/*     */     }
/*  83 */     Attribute attribute = (Attribute)o;
/*  84 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/*  89 */     if (!(o instanceof InternalAttribute)) {
/*  90 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o.getHttpDataType());
/*     */     }
/*     */     
/*  93 */     return compareTo((InternalAttribute)o);
/*     */   }
/*     */   
/*     */   public int compareTo(InternalAttribute o) {
/*  97 */     return getName().compareToIgnoreCase(o.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     StringBuilder result = new StringBuilder();
/* 103 */     for (ByteBuf elt : this.value) {
/* 104 */       result.append(elt.toString(this.charset));
/*     */     }
/* 106 */     return result.toString();
/*     */   }
/*     */   
/*     */   public int size() {
/* 110 */     return this.size;
/*     */   }
/*     */   
/*     */   public ByteBuf toByteBuf() {
/* 114 */     return (ByteBuf)Unpooled.compositeBuffer().addComponents(this.value).writerIndex(size()).readerIndex(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 119 */     return "InternalAttribute";
/*     */   }
/*     */   
/*     */   protected void deallocate() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\InternalAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */