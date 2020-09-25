/*     */ package net.md_5.bungee.api.chat;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ 
/*     */ public abstract class BaseComponent {
/*     */   BaseComponent parent;
/*     */   private ChatColor color;
/*     */   private Boolean bold;
/*     */   private Boolean italic;
/*     */   private Boolean underlined;
/*     */   
/*  13 */   public void setColor(ChatColor color) { this.color = color; } private Boolean strikethrough; private Boolean obfuscated; private List<BaseComponent> extra; private ClickEvent clickEvent; private HoverEvent hoverEvent; public void setBold(Boolean bold) { this.bold = bold; } public void setItalic(Boolean italic) { this.italic = italic; } public void setUnderlined(Boolean underlined) { this.underlined = underlined; } public void setStrikethrough(Boolean strikethrough) { this.strikethrough = strikethrough; } public void setObfuscated(Boolean obfuscated) { this.obfuscated = obfuscated; } public void setClickEvent(ClickEvent clickEvent) { this.clickEvent = clickEvent; } public void setHoverEvent(HoverEvent hoverEvent) { this.hoverEvent = hoverEvent; } public String toString() {
/*  14 */     return "BaseComponent(color=" + getColor() + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", extra=" + getExtra() + ", clickEvent=" + getClickEvent() + ", hoverEvent=" + getHoverEvent() + ")";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseComponent() {}
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
/*     */   public List<BaseComponent> getExtra() {
/*  55 */     return this.extra;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClickEvent getClickEvent() {
/*  62 */     return this.clickEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HoverEvent getHoverEvent() {
/*  68 */     return this.hoverEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   BaseComponent(BaseComponent old) {
/*  73 */     setColor(old.getColorRaw());
/*  74 */     setBold(old.isBoldRaw());
/*  75 */     setItalic(old.isItalicRaw());
/*  76 */     setUnderlined(old.isUnderlinedRaw());
/*  77 */     setStrikethrough(old.isStrikethroughRaw());
/*  78 */     setObfuscated(old.isObfuscatedRaw());
/*  79 */     setClickEvent(old.getClickEvent());
/*  80 */     setHoverEvent(old.getHoverEvent());
/*  81 */     if (old.getExtra() != null)
/*     */     {
/*  83 */       for (BaseComponent component : old.getExtra())
/*     */       {
/*  85 */         addExtra(component.duplicate());
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toLegacyText(BaseComponent... components) {
/* 106 */     StringBuilder builder = new StringBuilder();
/* 107 */     for (BaseComponent msg : components)
/*     */     {
/* 109 */       builder.append(msg.toLegacyText());
/*     */     }
/* 111 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toPlainText(BaseComponent... components) {
/* 122 */     StringBuilder builder = new StringBuilder();
/* 123 */     for (BaseComponent msg : components)
/*     */     {
/* 125 */       builder.append(msg.toPlainText());
/*     */     }
/* 127 */     return builder.toString();
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
/*     */   public ChatColor getColor() {
/* 139 */     if (this.color == null) {
/*     */       
/* 141 */       if (this.parent == null)
/*     */       {
/* 143 */         return ChatColor.WHITE;
/*     */       }
/* 145 */       return this.parent.getColor();
/*     */     } 
/* 147 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatColor getColorRaw() {
/* 158 */     return this.color;
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
/*     */   public boolean isBold() {
/* 170 */     if (this.bold == null)
/*     */     {
/* 172 */       return (this.parent != null && this.parent.isBold());
/*     */     }
/* 174 */     return this.bold.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isBoldRaw() {
/* 185 */     return this.bold;
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
/*     */   public boolean isItalic() {
/* 197 */     if (this.italic == null)
/*     */     {
/* 199 */       return (this.parent != null && this.parent.isItalic());
/*     */     }
/* 201 */     return this.italic.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isItalicRaw() {
/* 212 */     return this.italic;
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
/*     */   public boolean isUnderlined() {
/* 224 */     if (this.underlined == null)
/*     */     {
/* 226 */       return (this.parent != null && this.parent.isUnderlined());
/*     */     }
/* 228 */     return this.underlined.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isUnderlinedRaw() {
/* 239 */     return this.underlined;
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
/*     */   public boolean isStrikethrough() {
/* 251 */     if (this.strikethrough == null)
/*     */     {
/* 253 */       return (this.parent != null && this.parent.isStrikethrough());
/*     */     }
/* 255 */     return this.strikethrough.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isStrikethroughRaw() {
/* 266 */     return this.strikethrough;
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
/*     */   public boolean isObfuscated() {
/* 278 */     if (this.obfuscated == null)
/*     */     {
/* 280 */       return (this.parent != null && this.parent.isObfuscated());
/*     */     }
/* 282 */     return this.obfuscated.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isObfuscatedRaw() {
/* 293 */     return this.obfuscated;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtra(List<BaseComponent> components) {
/* 298 */     for (BaseComponent component : components)
/*     */     {
/* 300 */       component.parent = this;
/*     */     }
/* 302 */     this.extra = components;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtra(String text) {
/* 313 */     addExtra(new TextComponent(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtra(BaseComponent component) {
/* 324 */     if (this.extra == null)
/*     */     {
/* 326 */       this.extra = new ArrayList<BaseComponent>();
/*     */     }
/* 328 */     component.parent = this;
/* 329 */     this.extra.add(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFormatting() {
/* 339 */     return (this.color != null || this.bold != null || this.italic != null || this.underlined != null || this.strikethrough != null || this.obfuscated != null || this.hoverEvent != null || this.clickEvent != null);
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
/*     */   public String toPlainText() {
/* 352 */     StringBuilder builder = new StringBuilder();
/* 353 */     toPlainText(builder);
/* 354 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   void toPlainText(StringBuilder builder) {
/* 359 */     if (this.extra != null)
/*     */     {
/* 361 */       for (BaseComponent e : this.extra)
/*     */       {
/* 363 */         e.toPlainText(builder);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toLegacyText() {
/* 376 */     StringBuilder builder = new StringBuilder();
/* 377 */     toLegacyText(builder);
/* 378 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   void toLegacyText(StringBuilder builder) {
/* 383 */     if (this.extra != null)
/*     */     {
/* 385 */       for (BaseComponent e : this.extra)
/*     */       {
/* 387 */         e.toLegacyText(builder);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract BaseComponent duplicate();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\BaseComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */