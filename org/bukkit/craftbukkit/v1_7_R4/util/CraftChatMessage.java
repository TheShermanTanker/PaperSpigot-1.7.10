/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.server.v1_7_R4.ChatClickable;
/*     */ import net.minecraft.server.v1_7_R4.ChatComponentText;
/*     */ import net.minecraft.server.v1_7_R4.ChatModifier;
/*     */ import net.minecraft.server.v1_7_R4.EnumChatFormat;
/*     */ import net.minecraft.server.v1_7_R4.EnumClickAction;
/*     */ import net.minecraft.server.v1_7_R4.IChatBaseComponent;
/*     */ 
/*     */ 
/*     */ public final class CraftChatMessage
/*     */ {
/*     */   private static class StringMessage
/*     */   {
/*     */     private static final Map<Character, EnumChatFormat> formatMap;
/*  22 */     private static final Pattern INCREMENTAL_PATTERN = Pattern.compile("(" + String.valueOf('§') + "[0-9a-fk-or])|(\\n)|((?:(?:https?)://)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))", 2);
/*     */     
/*     */     static {
/*  25 */       ImmutableMap.Builder<Character, EnumChatFormat> builder = ImmutableMap.builder();
/*  26 */       for (EnumChatFormat format : EnumChatFormat.values()) {
/*  27 */         builder.put(Character.valueOf(Character.toLowerCase(format.getChar())), format);
/*     */       }
/*  29 */       formatMap = (Map<Character, EnumChatFormat>)builder.build();
/*     */     }
/*     */     
/*  32 */     private final List<IChatBaseComponent> list = new ArrayList<IChatBaseComponent>();
/*  33 */     private IChatBaseComponent currentChatComponent = (IChatBaseComponent)new ChatComponentText("");
/*  34 */     private ChatModifier modifier = new ChatModifier();
/*     */     private final IChatBaseComponent[] output;
/*     */     private int currentIndex;
/*     */     private final String message;
/*     */     
/*     */     private StringMessage(String message) {
/*  40 */       this.message = message;
/*  41 */       if (message == null) {
/*  42 */         this.output = new IChatBaseComponent[] { this.currentChatComponent };
/*     */         return;
/*     */       } 
/*  45 */       this.list.add(this.currentChatComponent);
/*     */       
/*  47 */       Matcher matcher = INCREMENTAL_PATTERN.matcher(message);
/*  48 */       String match = null;
/*  49 */       while (matcher.find()) {
/*  50 */         EnumChatFormat format; int groupId = 0;
/*  51 */         while ((match = matcher.group(++groupId)) == null);
/*     */ 
/*     */         
/*  54 */         appendNewComponent(matcher.start(groupId));
/*  55 */         switch (groupId) {
/*     */           case 1:
/*  57 */             format = formatMap.get(Character.valueOf(match.toLowerCase().charAt(1)));
/*  58 */             if (format == EnumChatFormat.RESET) {
/*  59 */               this.modifier = new ChatModifier(); break;
/*  60 */             }  if (format.isFormat()) {
/*  61 */               switch (format) {
/*     */                 case BOLD:
/*  63 */                   this.modifier.setBold(Boolean.TRUE);
/*     */                   break;
/*     */                 case ITALIC:
/*  66 */                   this.modifier.setItalic(Boolean.TRUE);
/*     */                   break;
/*     */                 case STRIKETHROUGH:
/*  69 */                   this.modifier.setStrikethrough(Boolean.TRUE);
/*     */                   break;
/*     */                 case UNDERLINE:
/*  72 */                   this.modifier.setUnderline(Boolean.TRUE);
/*     */                   break;
/*     */                 case RANDOM:
/*  75 */                   this.modifier.setRandom(Boolean.TRUE);
/*     */                   break;
/*     */               } 
/*  78 */               throw new AssertionError("Unexpected message format");
/*     */             } 
/*     */             
/*  81 */             this.modifier = (new ChatModifier()).setColor(format);
/*     */             break;
/*     */           
/*     */           case 2:
/*  85 */             this.currentChatComponent = null;
/*     */             break;
/*     */           case 3:
/*  88 */             if (!match.startsWith("http://") && !match.startsWith("https://")) {
/*  89 */               match = "http://" + match;
/*     */             }
/*  91 */             this.modifier.setChatClickable(new ChatClickable(EnumClickAction.OPEN_URL, match));
/*  92 */             appendNewComponent(matcher.end(groupId));
/*  93 */             this.modifier.setChatClickable((ChatClickable)null); break;
/*     */         } 
/*  95 */         this.currentIndex = matcher.end(groupId);
/*     */       } 
/*     */       
/*  98 */       if (this.currentIndex < message.length()) {
/*  99 */         appendNewComponent(message.length());
/*     */       }
/*     */       
/* 102 */       this.output = this.list.<IChatBaseComponent>toArray(new IChatBaseComponent[this.list.size()]);
/*     */     }
/*     */     
/*     */     private void appendNewComponent(int index) {
/* 106 */       if (index <= this.currentIndex) {
/*     */         return;
/*     */       }
/* 109 */       IChatBaseComponent addition = (new ChatComponentText(this.message.substring(this.currentIndex, index))).setChatModifier(this.modifier);
/* 110 */       this.currentIndex = index;
/* 111 */       this.modifier = this.modifier.clone();
/* 112 */       if (this.currentChatComponent == null) {
/* 113 */         this.currentChatComponent = (IChatBaseComponent)new ChatComponentText("");
/* 114 */         this.list.add(this.currentChatComponent);
/*     */       } 
/* 116 */       this.currentChatComponent.addSibling(addition);
/*     */     }
/*     */     
/*     */     private IChatBaseComponent[] getOutput() {
/* 120 */       return this.output;
/*     */     }
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent[] fromString(String message) {
/* 125 */     return (new StringMessage(message)).getOutput();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\CraftChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */