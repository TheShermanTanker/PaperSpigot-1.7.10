/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.ForwardingSet;
/*    */ import net.minecraft.util.com.google.common.collect.Sets;
/*    */ import net.minecraft.util.com.google.gson.JsonArray;
/*    */ import net.minecraft.util.com.google.gson.JsonElement;
/*    */ import net.minecraft.util.com.google.gson.JsonPrimitive;
/*    */ 
/*    */ public class AchievementSet extends ForwardingSet implements IJsonStatistic {
/* 12 */   private final Set a = Sets.newHashSet();
/*    */ 
/*    */   
/*    */   public void a(JsonElement paramJsonElement) {
/* 16 */     if (paramJsonElement.isJsonArray()) {
/* 17 */       for (JsonElement jsonElement : paramJsonElement.getAsJsonArray()) {
/* 18 */         add(jsonElement.getAsString());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement a() {
/* 25 */     JsonArray jsonArray = new JsonArray();
/*    */     
/* 27 */     for (String str : this) {
/* 28 */       jsonArray.add((JsonElement)new JsonPrimitive(str));
/*    */     }
/*    */     
/* 31 */     return (JsonElement)jsonArray;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set delegate() {
/* 36 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AchievementSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */