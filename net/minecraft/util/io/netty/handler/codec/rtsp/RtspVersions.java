/*    */ package net.minecraft.util.io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
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
/*    */ public final class RtspVersions
/*    */ {
/* 28 */   public static final HttpVersion RTSP_1_0 = new HttpVersion("RTSP", 1, 0, true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static HttpVersion valueOf(String text) {
/* 37 */     if (text == null) {
/* 38 */       throw new NullPointerException("text");
/*    */     }
/*    */     
/* 41 */     text = text.trim().toUpperCase();
/* 42 */     if ("RTSP/1.0".equals(text)) {
/* 43 */       return RTSP_1_0;
/*    */     }
/*    */     
/* 46 */     return new HttpVersion(text, true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\rtsp\RtspVersions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */