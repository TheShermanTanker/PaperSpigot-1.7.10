/*    */ package net.minecraft.util.io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.jcraft.jzlib.Deflater;
/*    */ import com.jcraft.jzlib.Inflater;
/*    */ import com.jcraft.jzlib.JZlib;
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
/*    */ final class ZlibUtil
/*    */ {
/*    */   static void fail(Inflater z, String message, int resultCode) {
/* 28 */     throw inflaterException(z, message, resultCode);
/*    */   }
/*    */   
/*    */   static void fail(Deflater z, String message, int resultCode) {
/* 32 */     throw deflaterException(z, message, resultCode);
/*    */   }
/*    */   
/*    */   static CompressionException inflaterException(Inflater z, String message, int resultCode) {
/* 36 */     return new CompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
/*    */   }
/*    */   
/*    */   static CompressionException deflaterException(Deflater z, String message, int resultCode) {
/* 40 */     return new CompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
/*    */   }
/*    */   
/*    */   static JZlib.WrapperType convertWrapperType(ZlibWrapper wrapper) {
/*    */     JZlib.WrapperType convertedWrapperType;
/* 45 */     switch (wrapper) {
/*    */       case NONE:
/* 47 */         convertedWrapperType = JZlib.W_NONE;
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
/* 61 */         return convertedWrapperType;case ZLIB: convertedWrapperType = JZlib.W_ZLIB; return convertedWrapperType;case GZIP: convertedWrapperType = JZlib.W_GZIP; return convertedWrapperType;case ZLIB_OR_NONE: convertedWrapperType = JZlib.W_ANY; return convertedWrapperType;
/*    */     } 
/*    */     throw new Error();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\ZlibUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */