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
/*     */ public class SecondaryWorldData
/*     */   extends WorldData
/*     */ {
/*     */   private final WorldData a;
/*     */   
/*     */   public SecondaryWorldData(WorldData paramWorldData) {
/*  21 */     this.a = paramWorldData;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound a() {
/*  26 */     return this.a.a();
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound paramNBTTagCompound) {
/*  31 */     return this.a.a(paramNBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed() {
/*  36 */     return this.a.getSeed();
/*     */   }
/*     */ 
/*     */   
/*     */   public int c() {
/*  41 */     return this.a.c();
/*     */   }
/*     */ 
/*     */   
/*     */   public int d() {
/*  46 */     return this.a.d();
/*     */   }
/*     */ 
/*     */   
/*     */   public int e() {
/*  51 */     return this.a.e();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTime() {
/*  56 */     return this.a.getTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDayTime() {
/*  61 */     return this.a.getDayTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound i() {
/*  71 */     return this.a.i();
/*     */   }
/*     */ 
/*     */   
/*     */   public int j() {
/*  76 */     return this.a.j();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  81 */     return this.a.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int l() {
/*  86 */     return this.a.l();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/*  96 */     return this.a.isThundering();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getThunderDuration() {
/* 101 */     return this.a.getThunderDuration();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasStorm() {
/* 106 */     return this.a.hasStorm();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeatherDuration() {
/* 111 */     return this.a.getWeatherDuration();
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumGamemode getGameType() {
/* 116 */     return this.a.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(long paramLong) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayTime(long paramLong) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawn(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String paramString) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThundering(boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThunderDuration(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStorm(boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeatherDuration(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldGenerateMapFeatures() {
/* 185 */     return this.a.shouldGenerateMapFeatures();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 193 */     return this.a.isHardcore();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getType() {
/* 198 */     return this.a.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(WorldType paramWorldType) {}
/*     */ 
/*     */   
/*     */   public boolean allowCommands() {
/* 207 */     return this.a.allowCommands();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 216 */     return this.a.isInitialized();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void d(boolean paramBoolean) {}
/*     */ 
/*     */   
/*     */   public GameRules getGameRules() {
/* 225 */     return this.a.getGameRules();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SecondaryWorldData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */