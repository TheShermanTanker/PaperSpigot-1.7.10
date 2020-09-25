/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ 
/*    */ public abstract class ExpirableListEntry
/*    */   extends JsonListEntry
/*    */ {
/* 11 */   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*    */   protected final Date b;
/*    */   protected final String c;
/*    */   protected final Date d;
/*    */   protected final String e;
/*    */   
/*    */   public ExpirableListEntry(Object object, Date date, String s, Date date1, String s1) {
/* 18 */     super(object);
/* 19 */     this.b = (date == null) ? new Date() : date;
/* 20 */     this.c = (s == null) ? "(Unknown)" : s;
/* 21 */     this.d = date1;
/* 22 */     this.e = (s1 == null) ? "Banned by an operator." : s1;
/*    */   }
/*    */   
/*    */   protected ExpirableListEntry(Object object, JsonObject jsonobject) {
/* 26 */     super(checkExpiry(object, jsonobject), jsonobject);
/*    */     
/*    */     Date date1, date2;
/*    */     
/*    */     try {
/* 31 */       date1 = jsonobject.has("created") ? a.parse(jsonobject.get("created").getAsString()) : new Date();
/* 32 */     } catch (ParseException parseexception) {
/* 33 */       date1 = new Date();
/*    */     } 
/*    */     
/* 36 */     this.b = date1;
/* 37 */     this.c = jsonobject.has("source") ? jsonobject.get("source").getAsString() : "(Unknown)";
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 42 */       date2 = jsonobject.has("expires") ? a.parse(jsonobject.get("expires").getAsString()) : null;
/* 43 */     } catch (ParseException parseexception1) {
/* 44 */       date2 = null;
/*    */     } 
/*    */     
/* 47 */     this.d = date2;
/* 48 */     this.e = jsonobject.has("reason") ? jsonobject.get("reason").getAsString() : "Banned by an operator.";
/*    */   }
/*    */   
/*    */   public Date getExpires() {
/* 52 */     return this.d;
/*    */   }
/*    */   
/*    */   public String getReason() {
/* 56 */     return this.e;
/*    */   }
/*    */   
/*    */   boolean hasExpired() {
/* 60 */     return (this.d == null) ? false : this.d.before(new Date());
/*    */   }
/*    */   
/*    */   protected void a(JsonObject jsonobject) {
/* 64 */     jsonobject.addProperty("created", a.format(this.b));
/* 65 */     jsonobject.addProperty("source", this.c);
/* 66 */     jsonobject.addProperty("expires", (this.d == null) ? "forever" : a.format(this.d));
/* 67 */     jsonobject.addProperty("reason", this.e);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSource() {
/* 72 */     return this.c;
/*    */   }
/*    */   
/*    */   public Date getCreated() {
/* 76 */     return this.b;
/*    */   }
/*    */   
/*    */   private static Object checkExpiry(Object object, JsonObject jsonobject) {
/* 80 */     Date expires = null;
/*    */     
/*    */     try {
/* 83 */       expires = jsonobject.has("expires") ? a.parse(jsonobject.get("expires").getAsString()) : null;
/* 84 */     } catch (ParseException ex) {}
/*    */ 
/*    */ 
/*    */     
/* 88 */     if (expires == null || expires.after(new Date())) {
/* 89 */       return object;
/*    */     }
/* 91 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ExpirableListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */