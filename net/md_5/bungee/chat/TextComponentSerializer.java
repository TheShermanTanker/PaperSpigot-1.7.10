/*    */ package net.md_5.bungee.chat;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.TextComponent;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializer;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonPrimitive;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextComponentSerializer
/*    */   extends BaseComponentSerializer
/*    */   implements JsonSerializer<TextComponent>, JsonDeserializer<TextComponent>
/*    */ {
/*    */   public TextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 23 */     TextComponent component = new TextComponent();
/* 24 */     JsonObject object = json.getAsJsonObject();
/* 25 */     deserialize(object, (BaseComponent)component, context);
/* 26 */     component.setText(object.get("text").getAsString());
/* 27 */     return component;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement serialize(TextComponent src, Type typeOfSrc, JsonSerializationContext context) {
/* 33 */     List<BaseComponent> extra = src.getExtra();
/* 34 */     if (!src.hasFormatting() && (extra == null || extra.isEmpty()))
/*    */     {
/* 36 */       return (JsonElement)new JsonPrimitive(src.getText());
/*    */     }
/* 38 */     JsonObject object = new JsonObject();
/* 39 */     serialize(object, (BaseComponent)src, context);
/* 40 */     object.addProperty("text", src.getText());
/* 41 */     return (JsonElement)object;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\chat\TextComponentSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */