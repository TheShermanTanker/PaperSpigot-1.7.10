/*     */ package net.md_5.bungee.api.chat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ 
/*     */ 
/*     */ public class TranslatableComponent
/*     */   extends BaseComponent
/*     */ {
/*     */   public void setTranslate(String translate) {
/*  16 */     this.translate = translate; } public String toString() {
/*  17 */     return "TranslatableComponent(locales=" + getLocales() + ", format=" + getFormat() + ", translate=" + getTranslate() + ", with=" + getWith() + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  22 */   private final ResourceBundle locales = ResourceBundle.getBundle("mojang-translations/en_US"); public ResourceBundle getLocales() { return this.locales; }
/*  23 */    private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)"); public Pattern getFormat() { return this.format; }
/*     */   
/*     */   private String translate;
/*     */   private List<BaseComponent> with;
/*     */   
/*     */   public String getTranslate() {
/*  29 */     return this.translate;
/*     */   }
/*     */   
/*     */   public List<BaseComponent> getWith() {
/*  33 */     return this.with;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TranslatableComponent(TranslatableComponent original) {
/*  42 */     super(original);
/*  43 */     setTranslate(original.getTranslate());
/*  44 */     for (BaseComponent baseComponent : original.getWith())
/*     */     {
/*  46 */       this.with.add(baseComponent.duplicate());
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
/*     */   public TranslatableComponent(String translate, Object... with) {
/*  62 */     setTranslate(translate);
/*  63 */     List<BaseComponent> temp = new ArrayList<BaseComponent>();
/*  64 */     for (Object w : with) {
/*     */       
/*  66 */       if (w instanceof String) {
/*     */         
/*  68 */         temp.add(new TextComponent((String)w));
/*     */       } else {
/*     */         
/*  71 */         temp.add((BaseComponent)w);
/*     */       } 
/*     */     } 
/*  74 */     setWith(temp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseComponent duplicate() {
/*  85 */     return new TranslatableComponent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWith(List<BaseComponent> components) {
/*  96 */     for (BaseComponent component : components)
/*     */     {
/*  98 */       component.parent = this;
/*     */     }
/* 100 */     this.with = components;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWith(String text) {
/* 111 */     addWith(new TextComponent(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWith(BaseComponent component) {
/* 122 */     if (this.with == null)
/*     */     {
/* 124 */       this.with = new ArrayList<BaseComponent>();
/*     */     }
/* 126 */     component.parent = this;
/* 127 */     this.with.add(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void toPlainText(StringBuilder builder) {
/*     */     String str;
/*     */     try {
/* 136 */       str = this.locales.getString(this.translate);
/* 137 */     } catch (MissingResourceException e) {
/* 138 */       str = this.translate;
/*     */     } 
/*     */     
/* 141 */     Matcher matcher = this.format.matcher(str);
/* 142 */     int position = 0;
/* 143 */     int i = 0;
/* 144 */     while (matcher.find(position)) {
/*     */       String withIndex;
/* 146 */       int pos = matcher.start();
/* 147 */       if (pos != position)
/*     */       {
/* 149 */         builder.append(str.substring(position, pos));
/*     */       }
/* 151 */       position = matcher.end();
/*     */       
/* 153 */       String formatCode = matcher.group(2);
/* 154 */       switch (formatCode.charAt(0)) {
/*     */         
/*     */         case 'd':
/*     */         case 's':
/* 158 */           withIndex = matcher.group(1);
/* 159 */           ((BaseComponent)this.with.get((withIndex != null) ? (Integer.parseInt(withIndex) - 1) : i++)).toPlainText(builder);
/*     */         
/*     */         case '%':
/* 162 */           builder.append('%');
/*     */       } 
/*     */     
/*     */     } 
/* 166 */     if (str.length() != position)
/*     */     {
/* 168 */       builder.append(str.substring(position, str.length()));
/*     */     }
/*     */     
/* 171 */     super.toPlainText(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void toLegacyText(StringBuilder builder) {
/*     */     String str;
/*     */     try {
/* 180 */       str = this.locales.getString(this.translate);
/* 181 */     } catch (MissingResourceException e) {
/* 182 */       str = this.translate;
/*     */     } 
/*     */     
/* 185 */     Matcher matcher = this.format.matcher(str);
/* 186 */     int position = 0;
/* 187 */     int i = 0;
/* 188 */     while (matcher.find(position)) {
/*     */       String withIndex;
/* 190 */       int pos = matcher.start();
/* 191 */       if (pos != position) {
/*     */         
/* 193 */         addFormat(builder);
/* 194 */         builder.append(str.substring(position, pos));
/*     */       } 
/* 196 */       position = matcher.end();
/*     */       
/* 198 */       String formatCode = matcher.group(2);
/* 199 */       switch (formatCode.charAt(0)) {
/*     */         
/*     */         case 'd':
/*     */         case 's':
/* 203 */           withIndex = matcher.group(1);
/* 204 */           ((BaseComponent)this.with.get((withIndex != null) ? (Integer.parseInt(withIndex) - 1) : i++)).toLegacyText(builder);
/*     */         
/*     */         case '%':
/* 207 */           addFormat(builder);
/* 208 */           builder.append('%');
/*     */       } 
/*     */     
/*     */     } 
/* 212 */     if (str.length() != position) {
/*     */       
/* 214 */       addFormat(builder);
/* 215 */       builder.append(str.substring(position, str.length()));
/*     */     } 
/* 217 */     super.toLegacyText(builder);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addFormat(StringBuilder builder) {
/* 222 */     builder.append(getColor());
/* 223 */     if (isBold())
/*     */     {
/* 225 */       builder.append(ChatColor.BOLD);
/*     */     }
/* 227 */     if (isItalic())
/*     */     {
/* 229 */       builder.append(ChatColor.ITALIC);
/*     */     }
/* 231 */     if (isUnderlined())
/*     */     {
/* 233 */       builder.append(ChatColor.UNDERLINE);
/*     */     }
/* 235 */     if (isStrikethrough())
/*     */     {
/* 237 */       builder.append(ChatColor.STRIKETHROUGH);
/*     */     }
/* 239 */     if (isObfuscated())
/*     */     {
/* 241 */       builder.append(ChatColor.MAGIC);
/*     */     }
/*     */   }
/*     */   
/*     */   public TranslatableComponent() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\TranslatableComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */