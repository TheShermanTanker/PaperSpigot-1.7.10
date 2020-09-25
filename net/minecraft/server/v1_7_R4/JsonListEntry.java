/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ 
/*    */ public class JsonListEntry
/*    */ {
/*    */   private final Object a;
/*    */   
/*    */   public JsonListEntry(Object object) {
/* 10 */     this.a = object;
/*    */   }
/*    */   
/*    */   protected JsonListEntry(Object object, JsonObject jsonobject) {
/* 14 */     this.a = object;
/*    */   }
/*    */   
/*    */   public Object getKey() {
/* 18 */     return this.a;
/*    */   }
/*    */   
/*    */   boolean hasExpired() {
/* 22 */     return false;
/*    */   }
/*    */   
/*    */   protected void a(JsonObject jsonobject) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\JsonListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */