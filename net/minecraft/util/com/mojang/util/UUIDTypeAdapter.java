/*    */ package net.minecraft.util.com.mojang.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapter;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonReader;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonWriter;
/*    */ 
/*    */ public class UUIDTypeAdapter
/*    */   extends TypeAdapter<UUID>
/*    */ {
/*    */   public void write(JsonWriter out, UUID value) throws IOException {
/* 13 */     out.value(fromUUID(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID read(JsonReader in) throws IOException {
/* 18 */     return fromString(in.nextString());
/*    */   }
/*    */   
/*    */   public static String fromUUID(UUID value) {
/* 22 */     return value.toString().replace("-", "");
/*    */   }
/*    */   
/*    */   public static UUID fromString(String input) {
/* 26 */     return UUID.fromString(input.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojan\\util\UUIDTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */