/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import java.text.ParsePosition;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import java.util.TimeZone;
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
/*    */ 
/*    */ final class HttpHeaderDateFormat
/*    */   extends SimpleDateFormat
/*    */ {
/*    */   private static final long serialVersionUID = -925286159755905325L;
/* 37 */   private final SimpleDateFormat format1 = new HttpHeaderDateFormatObsolete1();
/* 38 */   private final SimpleDateFormat format2 = new HttpHeaderDateFormatObsolete2();
/*    */   
/* 40 */   private static final ThreadLocal<HttpHeaderDateFormat> dateFormatThreadLocal = new ThreadLocal<HttpHeaderDateFormat>()
/*    */     {
/*    */       protected HttpHeaderDateFormat initialValue()
/*    */       {
/* 44 */         return new HttpHeaderDateFormat();
/*    */       }
/*    */     };
/*    */   
/*    */   static HttpHeaderDateFormat get() {
/* 49 */     return dateFormatThreadLocal.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private HttpHeaderDateFormat() {
/* 57 */     super("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
/* 58 */     setTimeZone(TimeZone.getTimeZone("GMT"));
/*    */   }
/*    */ 
/*    */   
/*    */   public Date parse(String text, ParsePosition pos) {
/* 63 */     Date date = super.parse(text, pos);
/* 64 */     if (date == null) {
/* 65 */       date = this.format1.parse(text, pos);
/*    */     }
/* 67 */     if (date == null) {
/* 68 */       date = this.format2.parse(text, pos);
/*    */     }
/* 70 */     return date;
/*    */   }
/*    */ 
/*    */   
/*    */   private static final class HttpHeaderDateFormatObsolete1
/*    */     extends SimpleDateFormat
/*    */   {
/*    */     private static final long serialVersionUID = -3178072504225114298L;
/*    */ 
/*    */     
/*    */     HttpHeaderDateFormatObsolete1() {
/* 81 */       super("E, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH);
/* 82 */       setTimeZone(TimeZone.getTimeZone("GMT"));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static final class HttpHeaderDateFormatObsolete2
/*    */     extends SimpleDateFormat
/*    */   {
/*    */     private static final long serialVersionUID = 3010674519968303714L;
/*    */ 
/*    */     
/*    */     HttpHeaderDateFormatObsolete2() {
/* 95 */       super("E MMM d HH:mm:ss yyyy", Locale.ENGLISH);
/* 96 */       setTimeZone(TimeZone.getTimeZone("GMT"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpHeaderDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */