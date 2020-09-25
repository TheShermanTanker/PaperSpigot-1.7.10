/*     */ package net.md_5.bungee.chat;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.ClickEvent;
/*     */ import net.md_5.bungee.api.chat.HoverEvent;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseComponentSerializer
/*     */ {
/*     */   protected void deserialize(JsonObject object, BaseComponent component, JsonDeserializationContext context) {
/*  20 */     if (object.has("color"))
/*     */     {
/*  22 */       component.setColor(ChatColor.valueOf(object.get("color").getAsString().toUpperCase()));
/*     */     }
/*  24 */     if (object.has("bold"))
/*     */     {
/*  26 */       component.setBold(Boolean.valueOf(object.get("bold").getAsBoolean()));
/*     */     }
/*  28 */     if (object.has("italic"))
/*     */     {
/*  30 */       component.setItalic(Boolean.valueOf(object.get("italic").getAsBoolean()));
/*     */     }
/*  32 */     if (object.has("underlined"))
/*     */     {
/*  34 */       component.setUnderlined(Boolean.valueOf(object.get("underlined").getAsBoolean()));
/*     */     }
/*  36 */     if (object.has("strikethrough"))
/*     */     {
/*  38 */       component.setStrikethrough(Boolean.valueOf(object.get("strikethrough").getAsBoolean()));
/*     */     }
/*  40 */     if (object.has("obfuscated"))
/*     */     {
/*  42 */       component.setObfuscated(Boolean.valueOf(object.get("obfuscated").getAsBoolean()));
/*     */     }
/*  44 */     if (object.has("extra"))
/*     */     {
/*  46 */       component.setExtra(Arrays.asList((Object[])context.deserialize(object.get("extra"), BaseComponent[].class)));
/*     */     }
/*     */ 
/*     */     
/*  50 */     if (object.has("clickEvent")) {
/*     */       
/*  52 */       JsonObject event = object.getAsJsonObject("clickEvent");
/*  53 */       component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(event.get("action").getAsString().toUpperCase()), event.get("value").getAsString()));
/*     */     } 
/*     */ 
/*     */     
/*  57 */     if (object.has("hoverEvent")) {
/*     */       BaseComponent[] res;
/*  59 */       JsonObject event = object.getAsJsonObject("hoverEvent");
/*     */       
/*  61 */       if (event.get("value").isJsonArray()) {
/*     */         
/*  63 */         res = (BaseComponent[])context.deserialize(event.get("value"), BaseComponent[].class);
/*     */       } else {
/*     */         
/*  66 */         res = new BaseComponent[] { (BaseComponent)context.deserialize(event.get("value"), BaseComponent.class) };
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  71 */       component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(event.get("action").getAsString().toUpperCase()), res));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void serialize(JsonObject object, BaseComponent component, JsonSerializationContext context) {
/*  77 */     boolean first = false;
/*  78 */     if (ComponentSerializer.serializedComponents.get() == null) {
/*     */       
/*  80 */       first = true;
/*  81 */       ComponentSerializer.serializedComponents.set(new HashSet<BaseComponent>());
/*     */     } 
/*     */     
/*     */     try {
/*  85 */       Preconditions.checkArgument(!((HashSet)ComponentSerializer.serializedComponents.get()).contains(component), "Component loop");
/*  86 */       ((HashSet<BaseComponent>)ComponentSerializer.serializedComponents.get()).add(component);
/*  87 */       if (component.getColorRaw() != null)
/*     */       {
/*  89 */         object.addProperty("color", component.getColorRaw().getName());
/*     */       }
/*  91 */       if (component.isBoldRaw() != null)
/*     */       {
/*  93 */         object.addProperty("bold", component.isBoldRaw());
/*     */       }
/*  95 */       if (component.isItalicRaw() != null)
/*     */       {
/*  97 */         object.addProperty("italic", component.isItalicRaw());
/*     */       }
/*  99 */       if (component.isUnderlinedRaw() != null)
/*     */       {
/* 101 */         object.addProperty("underlined", component.isUnderlinedRaw());
/*     */       }
/* 103 */       if (component.isStrikethroughRaw() != null)
/*     */       {
/* 105 */         object.addProperty("strikethrough", component.isStrikethroughRaw());
/*     */       }
/* 107 */       if (component.isObfuscatedRaw() != null)
/*     */       {
/* 109 */         object.addProperty("obfuscated", component.isObfuscatedRaw());
/*     */       }
/*     */       
/* 112 */       if (component.getExtra() != null)
/*     */       {
/* 114 */         object.add("extra", context.serialize(component.getExtra()));
/*     */       }
/*     */ 
/*     */       
/* 118 */       if (component.getClickEvent() != null) {
/*     */         
/* 120 */         JsonObject clickEvent = new JsonObject();
/* 121 */         clickEvent.addProperty("action", component.getClickEvent().getAction().toString().toLowerCase());
/* 122 */         clickEvent.addProperty("value", component.getClickEvent().getValue());
/* 123 */         object.add("clickEvent", (JsonElement)clickEvent);
/*     */       } 
/* 125 */       if (component.getHoverEvent() != null) {
/*     */         
/* 127 */         JsonObject hoverEvent = new JsonObject();
/* 128 */         hoverEvent.addProperty("action", component.getHoverEvent().getAction().toString().toLowerCase());
/* 129 */         hoverEvent.add("value", context.serialize(component.getHoverEvent().getValue()));
/* 130 */         object.add("hoverEvent", (JsonElement)hoverEvent);
/*     */       } 
/*     */     } finally {
/*     */       
/* 134 */       ((HashSet)ComponentSerializer.serializedComponents.get()).remove(component);
/* 135 */       if (first)
/*     */       {
/* 137 */         ComponentSerializer.serializedComponents.set(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\chat\BaseComponentSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */