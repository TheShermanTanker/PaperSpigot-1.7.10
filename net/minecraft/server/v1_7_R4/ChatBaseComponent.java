/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.com.google.common.collect.Iterators;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ 
/*     */ 
/*     */ public abstract class ChatBaseComponent
/*     */   implements IChatBaseComponent
/*     */ {
/*  12 */   protected List a = Lists.newArrayList();
/*     */   
/*     */   private ChatModifier b;
/*     */   
/*     */   public IChatBaseComponent addSibling(IChatBaseComponent paramIChatBaseComponent) {
/*  17 */     paramIChatBaseComponent.getChatModifier().a(getChatModifier());
/*  18 */     this.a.add(paramIChatBaseComponent);
/*  19 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List a() {
/*  24 */     return this.a;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatBaseComponent a(String paramString) {
/*  29 */     return addSibling(new ChatComponentText(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatBaseComponent setChatModifier(ChatModifier paramChatModifier) {
/*  34 */     this.b = paramChatModifier;
/*     */     
/*  36 */     for (IChatBaseComponent iChatBaseComponent : this.a) {
/*  37 */       iChatBaseComponent.getChatModifier().a(getChatModifier());
/*     */     }
/*     */     
/*  40 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatModifier getChatModifier() {
/*  45 */     if (this.b == null) {
/*  46 */       this.b = new ChatModifier();
/*  47 */       for (IChatBaseComponent iChatBaseComponent : this.a) {
/*  48 */         iChatBaseComponent.getChatModifier().a(this.b);
/*     */       }
/*     */     } 
/*  51 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/*  56 */     return Iterators.concat((Iterator)Iterators.forArray((Object[])new ChatBaseComponent[] { this }, ), a(this.a));
/*     */   }
/*     */ 
/*     */   
/*     */   public final String c() {
/*  61 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*  63 */     for (IChatBaseComponent iChatBaseComponent : this) {
/*  64 */       stringBuilder.append(iChatBaseComponent.e());
/*     */     }
/*     */     
/*  67 */     return stringBuilder.toString();
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
/*     */   public static Iterator a(Iterable paramIterable) {
/*  84 */     Iterator iterator = Iterators.concat(Iterators.transform(paramIterable.iterator(), new ChatFunction1()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     iterator = Iterators.transform(iterator, new ChatFunction2());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return iterator;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 103 */     if (this == paramObject) return true;
/*     */     
/* 105 */     if (paramObject instanceof ChatBaseComponent) {
/* 106 */       ChatBaseComponent chatBaseComponent = (ChatBaseComponent)paramObject;
/* 107 */       return (this.a.equals(chatBaseComponent.a) && getChatModifier().equals(chatBaseComponent.getChatModifier()));
/*     */     } 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     return 31 * this.b.hashCode() + this.a.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 120 */     return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatBaseComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */