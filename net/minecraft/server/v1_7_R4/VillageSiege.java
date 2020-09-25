/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ public class VillageSiege {
/*     */   private World world;
/*     */   private boolean b;
/*  10 */   private int c = -1;
/*     */   private int d;
/*     */   private int e;
/*     */   private Village f;
/*     */   private int g;
/*     */   private int h;
/*     */   private int i;
/*     */   
/*     */   public VillageSiege(World world) {
/*  19 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void a() {
/*  23 */     boolean flag = false;
/*     */     
/*  25 */     if (flag) {
/*  26 */       if (this.c == 2) {
/*  27 */         this.d = 100;
/*     */         return;
/*     */       } 
/*     */     } else {
/*  31 */       if (this.world.w()) {
/*  32 */         this.c = 0;
/*     */         
/*     */         return;
/*     */       } 
/*  36 */       if (this.c == 2) {
/*     */         return;
/*     */       }
/*     */       
/*  40 */       if (this.c == 0) {
/*  41 */         float f = this.world.c(0.0F);
/*     */         
/*  43 */         if (f < 0.5D || f > 0.501D) {
/*     */           return;
/*     */         }
/*     */         
/*  47 */         this.c = (this.world.random.nextInt(10) == 0) ? 1 : 2;
/*  48 */         this.b = false;
/*  49 */         if (this.c == 2) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  55 */       if (this.c == -1) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  61 */     if (!this.b) {
/*  62 */       if (!b()) {
/*     */         return;
/*     */       }
/*     */       
/*  66 */       this.b = true;
/*     */     } 
/*     */     
/*  69 */     if (this.e > 0) {
/*  70 */       this.e--;
/*     */     } else {
/*  72 */       this.e = 2;
/*  73 */       if (this.d > 0) {
/*  74 */         c();
/*  75 */         this.d--;
/*     */       } else {
/*  77 */         this.c = 2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean b() {
/*  83 */     List list = this.world.players;
/*  84 */     Iterator<EntityHuman> iterator = list.iterator();
/*     */     
/*  86 */     while (iterator.hasNext()) {
/*  87 */       EntityHuman entityhuman = iterator.next();
/*     */       
/*  89 */       this.f = this.world.villages.getClosestVillage((int)entityhuman.locX, (int)entityhuman.locY, (int)entityhuman.locZ, 1);
/*  90 */       if (this.f != null && this.f.getDoorCount() >= 10 && this.f.d() >= 20 && this.f.getPopulationCount() >= 20) {
/*  91 */         ChunkCoordinates chunkcoordinates = this.f.getCenter();
/*  92 */         float f = this.f.getSize();
/*  93 */         boolean flag = false;
/*  94 */         int i = 0;
/*     */ 
/*     */         
/*  97 */         while (i < 10) {
/*     */           
/*  99 */           float angle = this.world.random.nextFloat() * 3.1415927F * 2.0F;
/* 100 */           this.g = chunkcoordinates.x + (int)((MathHelper.cos(angle) * f) * 0.9D);
/* 101 */           this.h = chunkcoordinates.y;
/* 102 */           this.i = chunkcoordinates.z + (int)((MathHelper.sin(angle) * f) * 0.9D);
/*     */           
/* 104 */           flag = false;
/* 105 */           Iterator<Village> iterator1 = this.world.villages.getVillages().iterator();
/*     */           
/* 107 */           while (iterator1.hasNext()) {
/* 108 */             Village village = iterator1.next();
/*     */             
/* 110 */             if (village != this.f && village.a(this.g, this.h, this.i)) {
/* 111 */               flag = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 116 */           if (flag) {
/* 117 */             i++;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 122 */         if (flag) {
/* 123 */           return false;
/*     */         }
/*     */         
/* 126 */         Vec3D vec3d = a(this.g, this.h, this.i);
/*     */         
/* 128 */         if (vec3d != null) {
/* 129 */           this.e = 0;
/* 130 */           this.d = 20;
/* 131 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */   private boolean c() {
/*     */     EntityZombie entityzombie;
/* 142 */     Vec3D vec3d = a(this.g, this.h, this.i);
/*     */     
/* 144 */     if (vec3d == null) {
/* 145 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 150 */       entityzombie = new EntityZombie(this.world);
/* 151 */       entityzombie.prepare((GroupDataEntity)null);
/* 152 */       entityzombie.setVillager(false);
/* 153 */     } catch (Exception exception) {
/* 154 */       exception.printStackTrace();
/* 155 */       return false;
/*     */     } 
/*     */     
/* 158 */     entityzombie.setPositionRotation(vec3d.a, vec3d.b, vec3d.c, this.world.random.nextFloat() * 360.0F, 0.0F);
/* 159 */     this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION);
/* 160 */     ChunkCoordinates chunkcoordinates = this.f.getCenter();
/*     */     
/* 162 */     entityzombie.a(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, this.f.getSize());
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vec3D a(int i, int j, int k) {
/* 168 */     for (int l = 0; l < 10; l++) {
/* 169 */       int i1 = i + this.world.random.nextInt(16) - 8;
/* 170 */       int j1 = j + this.world.random.nextInt(6) - 3;
/* 171 */       int k1 = k + this.world.random.nextInt(16) - 8;
/*     */       
/* 173 */       if (this.f.a(i1, j1, k1) && SpawnerCreature.a(EnumCreatureType.MONSTER, this.world, i1, j1, k1))
/*     */       {
/* 175 */         return Vec3D.a(i1, j1, k1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 180 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\VillageSiege.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */