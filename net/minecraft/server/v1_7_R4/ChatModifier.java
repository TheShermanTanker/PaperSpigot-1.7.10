/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatModifier
/*     */ {
/*     */   private ChatModifier a;
/*     */   private EnumChatFormat b;
/*     */   private Boolean c;
/*     */   private Boolean d;
/*     */   private Boolean e;
/*     */   private Boolean f;
/*     */   private Boolean g;
/*     */   private ChatClickable h;
/*     */   private ChatHoverable i;
/*     */   
/*     */   public EnumChatFormat a() {
/*  23 */     return (this.b == null) ? n().a() : this.b;
/*     */   }
/*     */   
/*     */   public boolean b() {
/*  27 */     return (this.c == null) ? n().b() : this.c.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  31 */     return (this.d == null) ? n().c() : this.d.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  35 */     return (this.f == null) ? n().d() : this.f.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean e() {
/*  39 */     return (this.e == null) ? n().e() : this.e.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean f() {
/*  43 */     return (this.g == null) ? n().f() : this.g.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean g() {
/*  47 */     return (this.c == null && this.d == null && this.f == null && this.e == null && this.g == null && this.b == null && this.h == null && this.i == null);
/*     */   }
/*     */   
/*     */   public ChatClickable h() {
/*  51 */     return (this.h == null) ? n().h() : this.h;
/*     */   }
/*     */   
/*     */   public ChatHoverable i() {
/*  55 */     return (this.i == null) ? n().i() : this.i;
/*     */   }
/*     */   
/*     */   public ChatModifier setColor(EnumChatFormat paramEnumChatFormat) {
/*  59 */     this.b = paramEnumChatFormat;
/*  60 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setBold(Boolean paramBoolean) {
/*  64 */     this.c = paramBoolean;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setItalic(Boolean paramBoolean) {
/*  69 */     this.d = paramBoolean;
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setStrikethrough(Boolean paramBoolean) {
/*  74 */     this.f = paramBoolean;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setUnderline(Boolean paramBoolean) {
/*  79 */     this.e = paramBoolean;
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setRandom(Boolean paramBoolean) {
/*  84 */     this.g = paramBoolean;
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier setChatClickable(ChatClickable paramChatClickable) {
/*  89 */     this.h = paramChatClickable;
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier a(ChatHoverable paramChatHoverable) {
/*  94 */     this.i = paramChatHoverable;
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier a(ChatModifier paramChatModifier) {
/*  99 */     this.a = paramChatModifier;
/* 100 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChatModifier n() {
/* 128 */     return (this.a == null) ? j : this.a;
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
/*     */   public String toString() {
/* 147 */     return "Style{hasParent=" + ((this.a != null) ? 1 : 0) + ", color=" + this.b + ", bold=" + this.c + ", italic=" + this.d + ", underlined=" + this.e + ", obfuscated=" + this.g + ", clickEvent=" + h() + ", hoverEvent=" + i() + '}';
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
/*     */   public boolean equals(Object paramObject) {
/* 161 */     if (this == paramObject) return true; 
/* 162 */     if (paramObject instanceof ChatModifier) {
/*     */       
/* 164 */       ChatModifier chatModifier = (ChatModifier)paramObject;
/*     */       
/* 166 */       return (b() == chatModifier.b() && a() == chatModifier.a() && c() == chatModifier.c() && f() == chatModifier.f() && d() == chatModifier.d() && e() == chatModifier.e() && ((h() != null) ? h().equals(chatModifier.h()) : (chatModifier.h() == null)) && ((i() != null) ? i().equals(chatModifier.i()) : (chatModifier.i() == null)));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 181 */     int i = this.b.hashCode();
/* 182 */     i = 31 * i + this.c.hashCode();
/* 183 */     i = 31 * i + this.d.hashCode();
/* 184 */     i = 31 * i + this.e.hashCode();
/* 185 */     i = 31 * i + this.f.hashCode();
/* 186 */     i = 31 * i + this.g.hashCode();
/* 187 */     i = 31 * i + this.h.hashCode();
/* 188 */     i = 31 * i + this.i.hashCode();
/* 189 */     return i;
/*     */   }
/*     */   
/* 192 */   private static final ChatModifier j = new ChatStyleRoot();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatModifier clone() {
/* 218 */     ChatModifier chatModifier = new ChatModifier();
/* 219 */     chatModifier.c = this.c;
/* 220 */     chatModifier.d = this.d;
/* 221 */     chatModifier.f = this.f;
/* 222 */     chatModifier.e = this.e;
/* 223 */     chatModifier.g = this.g;
/* 224 */     chatModifier.b = this.b;
/* 225 */     chatModifier.h = this.h;
/* 226 */     chatModifier.i = this.i;
/* 227 */     chatModifier.a = this.a;
/* 228 */     return chatModifier;
/*     */   }
/*     */   
/*     */   public ChatModifier m() {
/* 232 */     ChatModifier chatModifier = new ChatModifier();
/*     */     
/* 234 */     chatModifier.setBold(Boolean.valueOf(b()));
/* 235 */     chatModifier.setItalic(Boolean.valueOf(c()));
/* 236 */     chatModifier.setStrikethrough(Boolean.valueOf(d()));
/* 237 */     chatModifier.setUnderline(Boolean.valueOf(e()));
/* 238 */     chatModifier.setRandom(Boolean.valueOf(f()));
/* 239 */     chatModifier.setColor(a());
/* 240 */     chatModifier.setChatClickable(h());
/* 241 */     chatModifier.a(i());
/*     */     
/* 243 */     return chatModifier;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */