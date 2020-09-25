/*     */ package net.md_5.bungee.api.chat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentBuilder
/*     */ {
/*     */   private TextComponent current;
/*  29 */   private final List<BaseComponent> parts = new ArrayList<BaseComponent>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder(ComponentBuilder original) {
/*  39 */     this.current = new TextComponent(original.current);
/*  40 */     for (BaseComponent baseComponent : original.parts)
/*     */     {
/*  42 */       this.parts.add(baseComponent.duplicate());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder(String text) {
/*  53 */     this.current = new TextComponent(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder append(String text) {
/*  65 */     return append(text, FormatRetention.ALL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder append(String text, FormatRetention retention) {
/*  78 */     this.parts.add(this.current);
/*     */     
/*  80 */     this.current = new TextComponent(this.current);
/*  81 */     this.current.setText(text);
/*  82 */     retain(retention);
/*     */     
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder color(ChatColor color) {
/*  95 */     this.current.setColor(color);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder bold(boolean bold) {
/* 107 */     this.current.setBold(Boolean.valueOf(bold));
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder italic(boolean italic) {
/* 119 */     this.current.setItalic(Boolean.valueOf(italic));
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder underlined(boolean underlined) {
/* 131 */     this.current.setUnderlined(Boolean.valueOf(underlined));
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder strikethrough(boolean strikethrough) {
/* 143 */     this.current.setStrikethrough(Boolean.valueOf(strikethrough));
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder obfuscated(boolean obfuscated) {
/* 155 */     this.current.setObfuscated(Boolean.valueOf(obfuscated));
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder event(ClickEvent clickEvent) {
/* 167 */     this.current.setClickEvent(clickEvent);
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder event(HoverEvent hoverEvent) {
/* 179 */     this.current.setHoverEvent(hoverEvent);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder reset() {
/* 190 */     return retain(FormatRetention.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentBuilder retain(FormatRetention retention) {
/* 201 */     BaseComponent previous = this.current;
/*     */     
/* 203 */     switch (retention) {
/*     */       
/*     */       case NONE:
/* 206 */         this.current = new TextComponent(this.current.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EVENTS:
/* 212 */         this.current = new TextComponent(this.current.getText());
/* 213 */         this.current.setClickEvent(previous.getClickEvent());
/* 214 */         this.current.setHoverEvent(previous.getHoverEvent());
/*     */         break;
/*     */       case FORMATTING:
/* 217 */         this.current.setClickEvent(null);
/* 218 */         this.current.setHoverEvent(null);
/*     */         break;
/*     */     } 
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseComponent[] create() {
/* 232 */     this.parts.add(this.current);
/* 233 */     return this.parts.<BaseComponent>toArray(new BaseComponent[this.parts.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum FormatRetention
/*     */   {
/* 242 */     NONE,
/*     */ 
/*     */ 
/*     */     
/* 246 */     FORMATTING,
/*     */ 
/*     */ 
/*     */     
/* 250 */     EVENTS,
/*     */ 
/*     */ 
/*     */     
/* 254 */     ALL;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\ComponentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */