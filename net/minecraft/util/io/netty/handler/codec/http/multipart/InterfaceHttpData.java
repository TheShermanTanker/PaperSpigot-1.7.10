/*    */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*    */ 
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
/*    */ public interface InterfaceHttpData
/*    */   extends Comparable<InterfaceHttpData>, ReferenceCounted
/*    */ {
/*    */   String getName();
/*    */   
/*    */   HttpDataType getHttpDataType();
/*    */   
/*    */   public enum HttpDataType
/*    */   {
/* 25 */     Attribute, FileUpload, InternalAttribute;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\InterfaceHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */