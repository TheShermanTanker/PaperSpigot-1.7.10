/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.com.google.common.collect.Iterators;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ 
/*     */ public class ChatMessage
/*     */   extends ChatBaseComponent
/*     */ {
/*     */   private final String d;
/*     */   private final Object[] e;
/*  17 */   private final Object f = new Object();
/*  18 */   private long g = -1L;
/*     */   
/*  20 */   List b = Lists.newArrayList();
/*     */ 
/*     */   
/*  23 */   public static final Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */   
/*     */   public ChatMessage(String paramString, Object... paramVarArgs) {
/*  26 */     this.d = paramString;
/*  27 */     this.e = paramVarArgs;
/*     */     
/*  29 */     for (Object object : paramVarArgs) {
/*  30 */       if (object instanceof IChatBaseComponent) {
/*  31 */         ((IChatBaseComponent)object).getChatModifier().a(getChatModifier());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void g() {
/*  38 */     synchronized (this.f) {
/*  39 */       long l = LocaleI18n.a();
/*  40 */       if (l == this.g) {
/*     */         return;
/*     */       }
/*  43 */       this.g = l;
/*  44 */       this.b.clear();
/*     */     } 
/*     */     
/*     */     try {
/*  48 */       b(LocaleI18n.get(this.d));
/*  49 */     } catch (ChatMessageException chatMessageException) {
/*  50 */       this.b.clear();
/*     */       try {
/*  52 */         b(LocaleI18n.b(this.d));
/*  53 */       } catch (ChatMessageException chatMessageException1) {
/*  54 */         throw chatMessageException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(String paramString) {
/*  60 */     boolean bool = false;
/*  61 */     Matcher matcher = c.matcher(paramString);
/*     */     
/*  63 */     byte b = 0;
/*  64 */     int i = 0;
/*     */     
/*     */     try {
/*  67 */       while (matcher.find(i)) {
/*  68 */         int j = matcher.start();
/*  69 */         int k = matcher.end();
/*     */ 
/*     */         
/*  72 */         if (j > i) {
/*  73 */           ChatComponentText chatComponentText = new ChatComponentText(String.format(paramString.substring(i, j), new Object[0]));
/*  74 */           chatComponentText.getChatModifier().a(getChatModifier());
/*  75 */           this.b.add(chatComponentText);
/*     */         } 
/*     */         
/*  78 */         String str1 = matcher.group(2);
/*  79 */         String str2 = paramString.substring(j, k);
/*     */ 
/*     */         
/*  82 */         if ("%".equals(str1) && "%%".equals(str2)) {
/*  83 */           ChatComponentText chatComponentText = new ChatComponentText("%");
/*  84 */           chatComponentText.getChatModifier().a(getChatModifier());
/*  85 */           this.b.add(chatComponentText);
/*  86 */         } else if ("s".equals(str1)) {
/*  87 */           String str = matcher.group(1);
/*  88 */           boolean bool1 = (str != null) ? (Integer.parseInt(str) - 1) : b++;
/*  89 */           this.b.add(a(bool1));
/*     */         } else {
/*  91 */           throw new ChatMessageException(this, "Unsupported format: '" + str2 + "'");
/*     */         } 
/*     */         
/*  94 */         i = k;
/*     */       } 
/*     */ 
/*     */       
/*  98 */       if (i < paramString.length()) {
/*  99 */         ChatComponentText chatComponentText = new ChatComponentText(String.format(paramString.substring(i), new Object[0]));
/* 100 */         chatComponentText.getChatModifier().a(getChatModifier());
/* 101 */         this.b.add(chatComponentText);
/*     */       } 
/* 103 */     } catch (IllegalFormatException illegalFormatException) {
/* 104 */       throw new ChatMessageException(this, illegalFormatException);
/*     */     } 
/*     */   }
/*     */   private IChatBaseComponent a(int paramInt) {
/*     */     IChatBaseComponent iChatBaseComponent;
/* 109 */     if (paramInt >= this.e.length) {
/* 110 */       throw new ChatMessageException(this, paramInt);
/*     */     }
/*     */     
/* 113 */     Object object = this.e[paramInt];
/*     */ 
/*     */     
/* 116 */     if (object instanceof IChatBaseComponent) {
/* 117 */       iChatBaseComponent = (IChatBaseComponent)object;
/*     */     } else {
/* 119 */       iChatBaseComponent = new ChatComponentText((object == null) ? "null" : object.toString());
/* 120 */       iChatBaseComponent.getChatModifier().a(getChatModifier());
/*     */     } 
/*     */     
/* 123 */     return iChatBaseComponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatBaseComponent setChatModifier(ChatModifier paramChatModifier) {
/* 128 */     super.setChatModifier(paramChatModifier);
/*     */     
/* 130 */     for (Object object : this.e) {
/* 131 */       if (object instanceof IChatBaseComponent) {
/* 132 */         ((IChatBaseComponent)object).getChatModifier().a(getChatModifier());
/*     */       }
/*     */     } 
/*     */     
/* 136 */     if (this.g > -1L) {
/* 137 */       for (IChatBaseComponent iChatBaseComponent : this.b) {
/* 138 */         iChatBaseComponent.getChatModifier().a(paramChatModifier);
/*     */       }
/*     */     }
/*     */     
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 147 */     g();
/*     */     
/* 149 */     return Iterators.concat(a(this.b), a(this.a));
/*     */   }
/*     */ 
/*     */   
/*     */   public String e() {
/* 154 */     g();
/*     */     
/* 156 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 158 */     for (IChatBaseComponent iChatBaseComponent : this.b) {
/* 159 */       stringBuilder.append(iChatBaseComponent.e());
/*     */     }
/*     */     
/* 162 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatMessage h() {
/* 167 */     Object[] arrayOfObject = new Object[this.e.length];
/*     */     
/* 169 */     for (byte b = 0; b < this.e.length; b++) {
/* 170 */       if (this.e[b] instanceof IChatBaseComponent) {
/* 171 */         arrayOfObject[b] = ((IChatBaseComponent)this.e[b]).f();
/*     */       } else {
/* 173 */         arrayOfObject[b] = this.e[b];
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     ChatMessage chatMessage = new ChatMessage(this.d, arrayOfObject);
/* 178 */     chatMessage.setChatModifier(getChatModifier().clone());
/* 179 */     for (IChatBaseComponent iChatBaseComponent : a()) {
/* 180 */       chatMessage.addSibling(iChatBaseComponent.f());
/*     */     }
/* 182 */     return chatMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 187 */     if (this == paramObject) return true;
/*     */     
/* 189 */     if (paramObject instanceof ChatMessage) {
/* 190 */       ChatMessage chatMessage = (ChatMessage)paramObject;
/* 191 */       return (Arrays.equals(this.e, chatMessage.e) && this.d.equals(chatMessage.d) && super.equals(paramObject));
/*     */     } 
/*     */     
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 199 */     int i = super.hashCode();
/* 200 */     i = 31 * i + this.d.hashCode();
/* 201 */     i = 31 * i + Arrays.hashCode(this.e);
/* 202 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return "TranslatableComponent{key='" + this.d + '\'' + ", args=" + Arrays.toString(this.e) + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String i() {
/* 216 */     return this.d;
/*     */   }
/*     */   
/*     */   public Object[] j() {
/* 220 */     return this.e;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */