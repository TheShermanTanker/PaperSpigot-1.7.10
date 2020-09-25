/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersistentVillage
/*     */   extends PersistentBase
/*     */ {
/*     */   private World world;
/*  17 */   private final List b = new ArrayList();
/*  18 */   private final List c = new ArrayList();
/*  19 */   private final List villages = new ArrayList();
/*     */   private int time;
/*     */   
/*     */   public PersistentVillage(String paramString) {
/*  23 */     super(paramString);
/*     */   }
/*     */   
/*     */   public PersistentVillage(World paramWorld) {
/*  27 */     super("villages");
/*  28 */     this.world = paramWorld;
/*  29 */     c();
/*     */   }
/*     */   
/*     */   public void a(World paramWorld) {
/*  33 */     this.world = paramWorld;
/*     */     
/*  35 */     for (Village village : this.villages) {
/*  36 */       village.a(paramWorld);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3) {
/*  41 */     if (this.b.size() > 64)
/*  42 */       return;  if (!d(paramInt1, paramInt2, paramInt3)) this.b.add(new ChunkCoordinates(paramInt1, paramInt2, paramInt3)); 
/*     */   }
/*     */   
/*     */   public void tick() {
/*  46 */     this.time++;
/*  47 */     for (Village village : this.villages)
/*  48 */       village.tick(this.time); 
/*  49 */     e();
/*  50 */     f();
/*  51 */     g();
/*     */     
/*  53 */     if (this.time % 400 == 0) {
/*  54 */       c();
/*     */     }
/*     */   }
/*     */   
/*     */   private void e() {
/*  59 */     for (Iterator<Village> iterator = this.villages.iterator(); iterator.hasNext(); ) {
/*  60 */       Village village = iterator.next();
/*  61 */       if (village.isAbandoned()) {
/*  62 */         iterator.remove();
/*  63 */         c();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getVillages() {
/*  69 */     return this.villages;
/*     */   }
/*     */   
/*     */   public Village getClosestVillage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  73 */     Village village = null;
/*  74 */     float f = Float.MAX_VALUE;
/*  75 */     for (Village village1 : this.villages) {
/*     */       
/*  77 */       float f1 = village1.getCenter().e(paramInt1, paramInt2, paramInt3);
/*  78 */       if (f1 >= f)
/*     */         continue; 
/*  80 */       float f2 = (paramInt4 + village1.getSize());
/*  81 */       if (f1 > f2 * f2)
/*     */         continue; 
/*  83 */       village = village1;
/*  84 */       f = f1;
/*     */     } 
/*  86 */     return village;
/*     */   }
/*     */   
/*     */   private void f() {
/*  90 */     if (this.b.isEmpty())
/*  91 */       return;  a(this.b.remove(0));
/*     */   }
/*     */ 
/*     */   
/*     */   private void g() {
/*  96 */     for (byte b = 0; b < this.c.size(); b++) {
/*  97 */       VillageDoor villageDoor = this.c.get(b);
/*  98 */       boolean bool = false;
/*  99 */       for (Village village : this.villages) {
/* 100 */         int i = (int)village.getCenter().e(villageDoor.locX, villageDoor.locY, villageDoor.locZ);
/* 101 */         int j = 32 + village.getSize();
/* 102 */         if (i > j * j)
/* 103 */           continue;  village.addDoor(villageDoor);
/* 104 */         bool = true;
/*     */       } 
/*     */       
/* 107 */       if (!bool) {
/*     */ 
/*     */         
/* 110 */         Village village = new Village(this.world);
/* 111 */         village.addDoor(villageDoor);
/* 112 */         this.villages.add(village);
/* 113 */         c();
/*     */       } 
/* 115 */     }  this.c.clear();
/*     */   }
/*     */   
/*     */   private void a(ChunkCoordinates paramChunkCoordinates) {
/* 119 */     byte b1 = 16, b2 = 4, b3 = 16;
/* 120 */     for (int i = paramChunkCoordinates.x - b1; i < paramChunkCoordinates.x + b1; i++) {
/* 121 */       for (int j = paramChunkCoordinates.y - b2; j < paramChunkCoordinates.y + b2; j++) {
/* 122 */         for (int k = paramChunkCoordinates.z - b3; k < paramChunkCoordinates.z + b3; k++) {
/* 123 */           if (e(i, j, k)) {
/*     */             
/* 125 */             VillageDoor villageDoor = b(i, j, k);
/* 126 */             if (villageDoor == null) { c(i, j, k); }
/* 127 */             else { villageDoor.addedTime = this.time; }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private VillageDoor b(int paramInt1, int paramInt2, int paramInt3) {
/* 135 */     for (VillageDoor villageDoor : this.c) {
/* 136 */       if (villageDoor.locX == paramInt1 && villageDoor.locZ == paramInt3 && Math.abs(villageDoor.locY - paramInt2) <= 1) return villageDoor; 
/* 137 */     }  for (Village village : this.villages) {
/* 138 */       VillageDoor villageDoor = village.e(paramInt1, paramInt2, paramInt3);
/* 139 */       if (villageDoor != null) return villageDoor; 
/*     */     } 
/* 141 */     return null;
/*     */   }
/*     */   
/*     */   private void c(int paramInt1, int paramInt2, int paramInt3) {
/* 145 */     int i = ((BlockDoor)Blocks.WOODEN_DOOR).e(this.world, paramInt1, paramInt2, paramInt3);
/* 146 */     if (i == 0 || i == 2) {
/* 147 */       byte b = 0; byte b1;
/* 148 */       for (b1 = -5; b1 < 0; b1++) {
/* 149 */         if (this.world.i(paramInt1 + b1, paramInt2, paramInt3)) b--; 
/* 150 */       }  for (b1 = 1; b1 <= 5; b1++) {
/* 151 */         if (this.world.i(paramInt1 + b1, paramInt2, paramInt3)) b++; 
/* 152 */       }  if (b != 0) this.c.add(new VillageDoor(paramInt1, paramInt2, paramInt3, (b > 0) ? -2 : 2, 0, this.time)); 
/*     */     } else {
/* 154 */       byte b = 0; byte b1;
/* 155 */       for (b1 = -5; b1 < 0; b1++) {
/* 156 */         if (this.world.i(paramInt1, paramInt2, paramInt3 + b1)) b--; 
/* 157 */       }  for (b1 = 1; b1 <= 5; b1++) {
/* 158 */         if (this.world.i(paramInt1, paramInt2, paramInt3 + b1)) b++; 
/* 159 */       }  if (b != 0) this.c.add(new VillageDoor(paramInt1, paramInt2, paramInt3, 0, (b > 0) ? -2 : 2, this.time)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean d(int paramInt1, int paramInt2, int paramInt3) {
/* 164 */     for (ChunkCoordinates chunkCoordinates : this.b) {
/* 165 */       if (chunkCoordinates.x == paramInt1 && chunkCoordinates.y == paramInt2 && chunkCoordinates.z == paramInt3) return true; 
/* 166 */     }  return false;
/*     */   }
/*     */   
/*     */   private boolean e(int paramInt1, int paramInt2, int paramInt3) {
/* 170 */     return (this.world.getType(paramInt1, paramInt2, paramInt3) == Blocks.WOODEN_DOOR);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 176 */     this.time = paramNBTTagCompound.getInt("Tick");
/*     */     
/* 178 */     NBTTagList nBTTagList = paramNBTTagCompound.getList("Villages", 10);
/* 179 */     for (byte b = 0; b < nBTTagList.size(); b++) {
/* 180 */       NBTTagCompound nBTTagCompound = nBTTagList.get(b);
/* 181 */       Village village = new Village();
/* 182 */       village.a(nBTTagCompound);
/* 183 */       this.villages.add(village);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 190 */     paramNBTTagCompound.setInt("Tick", this.time);
/* 191 */     NBTTagList nBTTagList = new NBTTagList();
/* 192 */     for (Village village : this.villages) {
/* 193 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 194 */       village.b(nBTTagCompound);
/* 195 */       nBTTagList.add(nBTTagCompound);
/*     */     } 
/* 197 */     paramNBTTagCompound.set("Villages", nBTTagList);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PersistentVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */