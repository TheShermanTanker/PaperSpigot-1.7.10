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
/*     */ public class TileEntityMobSpawnerData
/*     */   extends WeightedRandomChoice
/*     */ {
/*     */   public final NBTTagCompound b;
/*     */   public final String c;
/*     */   
/*     */   public TileEntityMobSpawnerData(MobSpawnerAbstract paramMobSpawnerAbstract, NBTTagCompound paramNBTTagCompound) {
/* 274 */     super(paramNBTTagCompound.getInt("Weight"));
/*     */     
/* 276 */     NBTTagCompound nBTTagCompound = paramNBTTagCompound.getCompound("Properties");
/* 277 */     String str = paramNBTTagCompound.getString("Type");
/*     */     
/* 279 */     if (str.equals("Minecart")) {
/* 280 */       if (nBTTagCompound != null) {
/* 281 */         switch (nBTTagCompound.getInt("Type")) {
/*     */           case 1:
/* 283 */             str = "MinecartChest";
/*     */             break;
/*     */           case 2:
/* 286 */             str = "MinecartFurnace";
/*     */             break;
/*     */           case 0:
/* 289 */             str = "MinecartRideable";
/*     */             break;
/*     */         } 
/*     */       } else {
/* 293 */         str = "MinecartRideable";
/*     */       } 
/*     */     }
/*     */     
/* 297 */     this.b = nBTTagCompound;
/* 298 */     this.c = str;
/*     */   }
/*     */   
/*     */   public TileEntityMobSpawnerData(MobSpawnerAbstract paramMobSpawnerAbstract, NBTTagCompound paramNBTTagCompound, String paramString) {
/* 302 */     super(1);
/*     */     
/* 304 */     if (paramString.equals("Minecart")) {
/* 305 */       if (paramNBTTagCompound != null) {
/* 306 */         switch (paramNBTTagCompound.getInt("Type")) {
/*     */           case 1:
/* 308 */             paramString = "MinecartChest";
/*     */             break;
/*     */           case 2:
/* 311 */             paramString = "MinecartFurnace";
/*     */             break;
/*     */           case 0:
/* 314 */             paramString = "MinecartRideable";
/*     */             break;
/*     */         } 
/*     */       } else {
/* 318 */         paramString = "MinecartRideable";
/*     */       } 
/*     */     }
/*     */     
/* 322 */     this.b = paramNBTTagCompound;
/* 323 */     this.c = paramString;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a() {
/* 327 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */     
/* 329 */     nBTTagCompound.set("Properties", this.b);
/* 330 */     nBTTagCompound.setString("Type", this.c);
/* 331 */     nBTTagCompound.setInt("Weight", this.a);
/*     */     
/* 333 */     return nBTTagCompound;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityMobSpawnerData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */