/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*    */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*    */ public class DefaultLastHttpContent
/*    */   extends DefaultHttpContent
/*    */   implements LastHttpContent
/*    */ {
/* 29 */   private final HttpHeaders trailingHeaders = new DefaultHttpHeaders()
/*    */     {
/*    */       void validateHeaderName0(String name) {
/* 32 */         super.validateHeaderName0(name);
/* 33 */         if (name.equalsIgnoreCase("Content-Length") || name.equalsIgnoreCase("Transfer-Encoding") || name.equalsIgnoreCase("Trailer"))
/*    */         {
/*    */           
/* 36 */           throw new IllegalArgumentException("prohibited trailing header: " + name);
/*    */         }
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public DefaultLastHttpContent() {
/* 43 */     this(Unpooled.buffer(0));
/*    */   }
/*    */   
/*    */   public DefaultLastHttpContent(ByteBuf content) {
/* 47 */     super(content);
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent copy() {
/* 52 */     DefaultLastHttpContent copy = new DefaultLastHttpContent(content().copy());
/* 53 */     copy.trailingHeaders().set(trailingHeaders());
/* 54 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent duplicate() {
/* 59 */     DefaultLastHttpContent copy = new DefaultLastHttpContent(content().duplicate());
/* 60 */     copy.trailingHeaders().set(trailingHeaders());
/* 61 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent retain(int increment) {
/* 66 */     super.retain(increment);
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent retain() {
/* 72 */     super.retain();
/* 73 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpHeaders trailingHeaders() {
/* 78 */     return this.trailingHeaders;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 83 */     StringBuilder buf = new StringBuilder(super.toString());
/* 84 */     buf.append(StringUtil.NEWLINE);
/* 85 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 88 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 89 */     return buf.toString();
/*    */   }
/*    */   
/*    */   private void appendHeaders(StringBuilder buf) {
/* 93 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)trailingHeaders()) {
/* 94 */       buf.append(e.getKey());
/* 95 */       buf.append(": ");
/* 96 */       buf.append(e.getValue());
/* 97 */       buf.append(StringUtil.NEWLINE);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultLastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */