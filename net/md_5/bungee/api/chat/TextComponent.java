/*     */ package net.md_5.bungee.api.chat;
/*     */ 
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ 
/*     */ 
/*     */ public class TextComponent
/*     */   extends BaseComponent
/*     */ {
/*     */   public void setText(String text) {
/*  15 */     this.text = text; } @ConstructorProperties({"text"})
/*  16 */   public TextComponent(String text) { this.text = text; }
/*     */ 
/*     */   
/*     */   public TextComponent() {}
/*     */   
/*  21 */   private static final Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BaseComponent[] fromLegacyText(String message) {
/*  33 */     ArrayList<BaseComponent> components = new ArrayList<BaseComponent>();
/*  34 */     StringBuilder builder = new StringBuilder();
/*  35 */     TextComponent component = new TextComponent();
/*  36 */     Matcher matcher = url.matcher(message);
/*     */     
/*  38 */     for (int i = 0; i < message.length(); i++) {
/*     */       
/*  40 */       char c = message.charAt(i);
/*  41 */       if (c == '§') {
/*     */         
/*  43 */         i++;
/*  44 */         c = message.charAt(i);
/*  45 */         if (c >= 'A' && c <= 'Z')
/*     */         {
/*  47 */           c = (char)(c + 32);
/*     */         }
/*  49 */         ChatColor format = ChatColor.getByChar(c);
/*  50 */         if (format != null) {
/*     */ 
/*     */ 
/*     */           
/*  54 */           if (builder.length() > 0) {
/*     */             
/*  56 */             TextComponent old = component;
/*  57 */             component = new TextComponent(old);
/*  58 */             old.setText(builder.toString());
/*  59 */             builder = new StringBuilder();
/*  60 */             components.add(old);
/*     */           } 
/*  62 */           switch (format) {
/*     */             
/*     */             case BOLD:
/*  65 */               component.setBold(Boolean.valueOf(true));
/*     */               break;
/*     */             case ITALIC:
/*  68 */               component.setItalic(Boolean.valueOf(true));
/*     */               break;
/*     */             case UNDERLINE:
/*  71 */               component.setUnderlined(Boolean.valueOf(true));
/*     */               break;
/*     */             case STRIKETHROUGH:
/*  74 */               component.setStrikethrough(Boolean.valueOf(true));
/*     */               break;
/*     */             case MAGIC:
/*  77 */               component.setObfuscated(Boolean.valueOf(true));
/*     */               break;
/*     */             case RESET:
/*  80 */               format = ChatColor.WHITE;
/*     */             default:
/*  82 */               component = new TextComponent();
/*  83 */               component.setColor(format);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } else {
/*  88 */         int pos = message.indexOf(' ', i);
/*  89 */         if (pos == -1)
/*     */         {
/*  91 */           pos = message.length();
/*     */         }
/*  93 */         if (matcher.region(i, pos).find())
/*     */         
/*     */         { 
/*  96 */           if (builder.length() > 0) {
/*     */             
/*  98 */             TextComponent textComponent = component;
/*  99 */             component = new TextComponent(textComponent);
/* 100 */             textComponent.setText(builder.toString());
/* 101 */             builder = new StringBuilder();
/* 102 */             components.add(textComponent);
/*     */           } 
/*     */           
/* 105 */           TextComponent old = component;
/* 106 */           component = new TextComponent(old);
/* 107 */           String urlString = message.substring(i, pos);
/* 108 */           component.setText(urlString);
/* 109 */           component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlString.startsWith("http") ? urlString : ("http://" + urlString)));
/*     */           
/* 111 */           components.add(component);
/* 112 */           i += pos - i - 1;
/* 113 */           component = old; }
/*     */         else
/*     */         
/* 116 */         { builder.append(c); } 
/*     */       } 
/* 118 */     }  if (builder.length() > 0) {
/*     */       
/* 120 */       component.setText(builder.toString());
/* 121 */       components.add(component);
/*     */     } 
/*     */ 
/*     */     
/* 125 */     if (components.isEmpty())
/*     */     {
/* 127 */       components.add(new TextComponent(""));
/*     */     }
/*     */     
/* 130 */     return components.<BaseComponent>toArray(new BaseComponent[components.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 136 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextComponent(TextComponent textComponent) {
/* 146 */     super(textComponent);
/* 147 */     setText(textComponent.getText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextComponent(BaseComponent... extras) {
/* 158 */     setText("");
/* 159 */     setExtra(new ArrayList<BaseComponent>(Arrays.asList(extras)));
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
/* 170 */     return new TextComponent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void toPlainText(StringBuilder builder) {
/* 176 */     builder.append(this.text);
/* 177 */     super.toPlainText(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void toLegacyText(StringBuilder builder) {
/* 183 */     builder.append(getColor());
/* 184 */     if (isBold())
/*     */     {
/* 186 */       builder.append(ChatColor.BOLD);
/*     */     }
/* 188 */     if (isItalic())
/*     */     {
/* 190 */       builder.append(ChatColor.ITALIC);
/*     */     }
/* 192 */     if (isUnderlined())
/*     */     {
/* 194 */       builder.append(ChatColor.UNDERLINE);
/*     */     }
/* 196 */     if (isStrikethrough())
/*     */     {
/* 198 */       builder.append(ChatColor.STRIKETHROUGH);
/*     */     }
/* 200 */     if (isObfuscated())
/*     */     {
/* 202 */       builder.append(ChatColor.MAGIC);
/*     */     }
/* 204 */     builder.append(this.text);
/* 205 */     super.toLegacyText(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 211 */     return String.format("TextComponent{text=%s, %s}", new Object[] { this.text, super.toString() });
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\TextComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */