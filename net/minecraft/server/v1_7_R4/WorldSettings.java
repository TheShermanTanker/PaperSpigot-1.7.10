/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ public final class WorldSettings
/*     */ {
/*     */   private final long a;
/*     */   private final EnumGamemode b;
/*     */   private final boolean c;
/*     */   private final boolean d;
/*     */   private final WorldType e;
/*     */   private boolean f;
/*     */   private boolean g;
/*  79 */   private String h = "";
/*     */   
/*     */   public WorldSettings(long paramLong, EnumGamemode paramEnumGamemode, boolean paramBoolean1, boolean paramBoolean2, WorldType paramWorldType) {
/*  82 */     this.a = paramLong;
/*  83 */     this.b = paramEnumGamemode;
/*  84 */     this.c = paramBoolean1;
/*  85 */     this.d = paramBoolean2;
/*  86 */     this.e = paramWorldType;
/*     */   }
/*     */   
/*     */   public WorldSettings(WorldData paramWorldData) {
/*  90 */     this(paramWorldData.getSeed(), paramWorldData.getGameType(), paramWorldData.shouldGenerateMapFeatures(), paramWorldData.isHardcore(), paramWorldData.getType());
/*     */   }
/*     */   
/*     */   public WorldSettings a() {
/*  94 */     this.g = true;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings a(String paramString) {
/* 104 */     this.h = paramString;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 109 */     return this.g;
/*     */   }
/*     */   
/*     */   public long d() {
/* 113 */     return this.a;
/*     */   }
/*     */   
/*     */   public EnumGamemode e() {
/* 117 */     return this.b;
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 121 */     return this.d;
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 125 */     return this.c;
/*     */   }
/*     */   
/*     */   public WorldType h() {
/* 129 */     return this.e;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 133 */     return this.f;
/*     */   }
/*     */   
/*     */   public static EnumGamemode a(int paramInt) {
/* 137 */     return EnumGamemode.getById(paramInt);
/*     */   }
/*     */   
/*     */   public String j() {
/* 141 */     return this.h;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */