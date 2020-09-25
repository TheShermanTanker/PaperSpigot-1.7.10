/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ public class Village {
/*     */   private World world;
/*  11 */   private final List doors = new ArrayList();
/*  12 */   private final ChunkCoordinates c = new ChunkCoordinates(0, 0, 0);
/*  13 */   private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
/*     */   private int size;
/*     */   private int f;
/*     */   private int time;
/*     */   private int population;
/*     */   private int noBreedTicks;
/*  19 */   private TreeMap playerStandings = new TreeMap<Object, Object>();
/*  20 */   private List aggressors = new ArrayList();
/*     */   private int ironGolemCount;
/*     */   
/*     */   public Village() {}
/*     */   
/*     */   public Village(World world) {
/*  26 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void a(World world) {
/*  30 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void tick(int i) {
/*  34 */     this.time = i;
/*  35 */     m();
/*  36 */     l();
/*  37 */     if (i % 20 == 0) {
/*  38 */       k();
/*     */     }
/*     */     
/*  41 */     if (i % 30 == 0) {
/*  42 */       countPopulation();
/*     */     }
/*     */     
/*  45 */     int j = this.population / 10;
/*     */     
/*  47 */     if (this.ironGolemCount < j && this.doors.size() > 20 && this.world.random.nextInt(7000) == 0) {
/*  48 */       Vec3D vec3d = a(MathHelper.d(this.center.x), MathHelper.d(this.center.y), MathHelper.d(this.center.z), 2, 4, 2);
/*     */       
/*  50 */       if (vec3d != null) {
/*  51 */         EntityIronGolem entityirongolem = new EntityIronGolem(this.world);
/*     */         
/*  53 */         entityirongolem.setPosition(vec3d.a, vec3d.b, vec3d.c);
/*  54 */         this.world.addEntity(entityirongolem, CreatureSpawnEvent.SpawnReason.VILLAGE_DEFENSE);
/*  55 */         this.ironGolemCount++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Vec3D a(int i, int j, int k, int l, int i1, int j1) {
/*  61 */     for (int k1 = 0; k1 < 10; k1++) {
/*  62 */       int l1 = i + this.world.random.nextInt(16) - 8;
/*  63 */       int i2 = j + this.world.random.nextInt(6) - 3;
/*  64 */       int j2 = k + this.world.random.nextInt(16) - 8;
/*     */       
/*  66 */       if (a(l1, i2, j2) && b(l1, i2, j2, l, i1, j1)) {
/*  67 */         return Vec3D.a(l1, i2, j2);
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   private boolean b(int i, int j, int k, int l, int i1, int j1) {
/*  75 */     if (!World.a(this.world, i, j - 1, k)) {
/*  76 */       return false;
/*     */     }
/*  78 */     int k1 = i - l / 2;
/*  79 */     int l1 = k - j1 / 2;
/*     */     
/*  81 */     for (int i2 = k1; i2 < k1 + l; i2++) {
/*  82 */       for (int j2 = j; j2 < j + i1; j2++) {
/*  83 */         for (int k2 = l1; k2 < l1 + j1; k2++) {
/*  84 */           if (this.world.getType(i2, j2, k2).r()) {
/*  85 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void countPopulation() {
/*  96 */     List list = this.world.a(EntityIronGolem.class, AxisAlignedBB.a((this.center.x - this.size), (this.center.y - 4), (this.center.z - this.size), (this.center.x + this.size), (this.center.y + 4), (this.center.z + this.size)));
/*     */     
/*  98 */     this.ironGolemCount = list.size();
/*     */   }
/*     */   
/*     */   private void k() {
/* 102 */     List list = this.world.a(EntityVillager.class, AxisAlignedBB.a((this.center.x - this.size), (this.center.y - 4), (this.center.z - this.size), (this.center.x + this.size), (this.center.y + 4), (this.center.z + this.size)));
/*     */     
/* 104 */     this.population = list.size();
/* 105 */     if (this.population == 0) {
/* 106 */       this.playerStandings.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public ChunkCoordinates getCenter() {
/* 111 */     return this.center;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 115 */     return this.size;
/*     */   }
/*     */   
/*     */   public int getDoorCount() {
/* 119 */     return this.doors.size();
/*     */   }
/*     */   
/*     */   public int d() {
/* 123 */     return this.time - this.f;
/*     */   }
/*     */   
/*     */   public int getPopulationCount() {
/* 127 */     return this.population;
/*     */   }
/*     */   
/*     */   public boolean a(int i, int j, int k) {
/* 131 */     return (this.center.e(i, j, k) < (this.size * this.size));
/*     */   }
/*     */   
/*     */   public List getDoors() {
/* 135 */     return this.doors;
/*     */   }
/*     */   
/*     */   public VillageDoor b(int i, int j, int k) {
/* 139 */     VillageDoor villagedoor = null;
/* 140 */     int l = Integer.MAX_VALUE;
/* 141 */     Iterator<VillageDoor> iterator = this.doors.iterator();
/*     */     
/* 143 */     while (iterator.hasNext()) {
/* 144 */       VillageDoor villagedoor1 = iterator.next();
/* 145 */       int i1 = villagedoor1.b(i, j, k);
/*     */       
/* 147 */       if (i1 < l) {
/* 148 */         villagedoor = villagedoor1;
/* 149 */         l = i1;
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return villagedoor;
/*     */   }
/*     */   
/*     */   public VillageDoor c(int i, int j, int k) {
/* 157 */     VillageDoor villagedoor = null;
/* 158 */     int l = Integer.MAX_VALUE;
/* 159 */     Iterator<VillageDoor> iterator = this.doors.iterator();
/*     */     
/* 161 */     while (iterator.hasNext()) {
/* 162 */       VillageDoor villagedoor1 = iterator.next();
/* 163 */       int i1 = villagedoor1.b(i, j, k);
/*     */       
/* 165 */       if (i1 > 256) {
/* 166 */         i1 *= 1000;
/*     */       } else {
/* 168 */         i1 = villagedoor1.f();
/*     */       } 
/*     */       
/* 171 */       if (i1 < l) {
/* 172 */         villagedoor = villagedoor1;
/* 173 */         l = i1;
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return villagedoor;
/*     */   }
/*     */   public VillageDoor e(int i, int j, int k) {
/*     */     VillageDoor villagedoor;
/* 181 */     if (this.center.e(i, j, k) > (this.size * this.size)) {
/* 182 */       return null;
/*     */     }
/* 184 */     Iterator<VillageDoor> iterator = this.doors.iterator();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 189 */       if (!iterator.hasNext()) {
/* 190 */         return null;
/*     */       }
/*     */       
/* 193 */       villagedoor = iterator.next();
/* 194 */     } while (villagedoor.locX != i || villagedoor.locZ != k || Math.abs(villagedoor.locY - j) > 1);
/*     */     
/* 196 */     return villagedoor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDoor(VillageDoor villagedoor) {
/* 201 */     this.doors.add(villagedoor);
/* 202 */     this.c.x += villagedoor.locX;
/* 203 */     this.c.y += villagedoor.locY;
/* 204 */     this.c.z += villagedoor.locZ;
/* 205 */     n();
/* 206 */     this.f = villagedoor.addedTime;
/*     */   }
/*     */   
/*     */   public boolean isAbandoned() {
/* 210 */     return this.doors.isEmpty();
/*     */   }
/*     */   public void a(EntityLiving entityliving) {
/*     */     VillageAggressor villageaggressor;
/* 214 */     Iterator<VillageAggressor> iterator = this.aggressors.iterator();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 219 */       if (!iterator.hasNext()) {
/* 220 */         this.aggressors.add(new VillageAggressor(this, entityliving, this.time));
/*     */         
/*     */         return;
/*     */       } 
/* 224 */       villageaggressor = iterator.next();
/* 225 */     } while (villageaggressor.a != entityliving);
/*     */     
/* 227 */     villageaggressor.b = this.time;
/*     */   }
/*     */   
/*     */   public EntityLiving b(EntityLiving entityliving) {
/* 231 */     double d0 = Double.MAX_VALUE;
/* 232 */     VillageAggressor villageaggressor = null;
/*     */     
/* 234 */     for (int i = 0; i < this.aggressors.size(); i++) {
/* 235 */       VillageAggressor villageaggressor1 = this.aggressors.get(i);
/* 236 */       double d1 = villageaggressor1.a.f(entityliving);
/*     */       
/* 238 */       if (d1 <= d0) {
/* 239 */         villageaggressor = villageaggressor1;
/* 240 */         d0 = d1;
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     return (villageaggressor != null) ? villageaggressor.a : null;
/*     */   }
/*     */   
/*     */   public EntityHuman c(EntityLiving entityliving) {
/* 248 */     double d0 = Double.MAX_VALUE;
/* 249 */     EntityHuman entityhuman = null;
/* 250 */     Iterator<String> iterator = this.playerStandings.keySet().iterator();
/*     */     
/* 252 */     while (iterator.hasNext()) {
/* 253 */       String s = iterator.next();
/*     */       
/* 255 */       if (d(s)) {
/* 256 */         EntityHuman entityhuman1 = this.world.a(s);
/*     */         
/* 258 */         if (entityhuman1 != null) {
/* 259 */           double d1 = entityhuman1.f(entityliving);
/*     */           
/* 261 */           if (d1 <= d0) {
/* 262 */             entityhuman = entityhuman1;
/* 263 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     return entityhuman;
/*     */   }
/*     */   
/*     */   private void l() {
/* 273 */     Iterator<VillageAggressor> iterator = this.aggressors.iterator();
/*     */     
/* 275 */     while (iterator.hasNext()) {
/* 276 */       VillageAggressor villageaggressor = iterator.next();
/*     */       
/* 278 */       if (!villageaggressor.a.isAlive() || Math.abs(this.time - villageaggressor.b) > 300) {
/* 279 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void m() {
/* 285 */     boolean flag = false;
/* 286 */     boolean flag1 = (this.world.random.nextInt(50) == 0);
/* 287 */     Iterator<VillageDoor> iterator = this.doors.iterator();
/*     */     
/* 289 */     while (iterator.hasNext()) {
/* 290 */       VillageDoor villagedoor = iterator.next();
/*     */       
/* 292 */       if (flag1) {
/* 293 */         villagedoor.d();
/*     */       }
/*     */       
/* 296 */       if (!isDoor(villagedoor.locX, villagedoor.locY, villagedoor.locZ) || Math.abs(this.time - villagedoor.addedTime) > 1200) {
/* 297 */         this.c.x -= villagedoor.locX;
/* 298 */         this.c.y -= villagedoor.locY;
/* 299 */         this.c.z -= villagedoor.locZ;
/* 300 */         flag = true;
/* 301 */         villagedoor.removed = true;
/* 302 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     if (flag) {
/* 307 */       n();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isDoor(int i, int j, int k) {
/* 312 */     return (this.world.getType(i, j, k) == Blocks.WOODEN_DOOR);
/*     */   }
/*     */   
/*     */   private void n() {
/* 316 */     int i = this.doors.size();
/*     */     
/* 318 */     if (i == 0) {
/* 319 */       this.center.b(0, 0, 0);
/* 320 */       this.size = 0;
/*     */     } else {
/* 322 */       this.center.b(this.c.x / i, this.c.y / i, this.c.z / i);
/* 323 */       int j = 0;
/*     */ 
/*     */ 
/*     */       
/* 327 */       for (Iterator<VillageDoor> iterator = this.doors.iterator(); iterator.hasNext(); j = Math.max(villagedoor.b(this.center.x, this.center.y, this.center.z), j)) {
/* 328 */         VillageDoor villagedoor = iterator.next();
/*     */       }
/*     */       
/* 331 */       this.size = Math.max(32, (int)Math.sqrt(j) + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(String s) {
/* 336 */     Integer integer = (Integer)this.playerStandings.get(s);
/*     */     
/* 338 */     return (integer != null) ? integer.intValue() : 0;
/*     */   }
/*     */   
/*     */   public int a(String s, int i) {
/* 342 */     int j = a(s);
/* 343 */     int k = MathHelper.a(j + i, -30, 10);
/*     */     
/* 345 */     this.playerStandings.put(s, Integer.valueOf(k));
/* 346 */     return k;
/*     */   }
/*     */   
/*     */   public boolean d(String s) {
/* 350 */     return (a(s) <= -15);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 354 */     this.population = nbttagcompound.getInt("PopSize");
/* 355 */     this.size = nbttagcompound.getInt("Radius");
/* 356 */     this.ironGolemCount = nbttagcompound.getInt("Golems");
/* 357 */     this.f = nbttagcompound.getInt("Stable");
/* 358 */     this.time = nbttagcompound.getInt("Tick");
/* 359 */     this.noBreedTicks = nbttagcompound.getInt("MTick");
/* 360 */     this.center.x = nbttagcompound.getInt("CX");
/* 361 */     this.center.y = nbttagcompound.getInt("CY");
/* 362 */     this.center.z = nbttagcompound.getInt("CZ");
/* 363 */     this.c.x = nbttagcompound.getInt("ACX");
/* 364 */     this.c.y = nbttagcompound.getInt("ACY");
/* 365 */     this.c.z = nbttagcompound.getInt("ACZ");
/* 366 */     NBTTagList nbttaglist = nbttagcompound.getList("Doors", 10);
/*     */     
/* 368 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 369 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 370 */       VillageDoor villagedoor = new VillageDoor(nbttagcompound1.getInt("X"), nbttagcompound1.getInt("Y"), nbttagcompound1.getInt("Z"), nbttagcompound1.getInt("IDX"), nbttagcompound1.getInt("IDZ"), nbttagcompound1.getInt("TS"));
/*     */       
/* 372 */       this.doors.add(villagedoor);
/*     */     } 
/*     */     
/* 375 */     NBTTagList nbttaglist1 = nbttagcompound.getList("Players", 10);
/*     */     
/* 377 */     for (int j = 0; j < nbttaglist1.size(); j++) {
/* 378 */       NBTTagCompound nbttagcompound2 = nbttaglist1.get(j);
/*     */       
/* 380 */       this.playerStandings.put(nbttagcompound2.getString("Name"), Integer.valueOf(nbttagcompound2.getInt("S")));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 385 */     nbttagcompound.setInt("PopSize", this.population);
/* 386 */     nbttagcompound.setInt("Radius", this.size);
/* 387 */     nbttagcompound.setInt("Golems", this.ironGolemCount);
/* 388 */     nbttagcompound.setInt("Stable", this.f);
/* 389 */     nbttagcompound.setInt("Tick", this.time);
/* 390 */     nbttagcompound.setInt("MTick", this.noBreedTicks);
/* 391 */     nbttagcompound.setInt("CX", this.center.x);
/* 392 */     nbttagcompound.setInt("CY", this.center.y);
/* 393 */     nbttagcompound.setInt("CZ", this.center.z);
/* 394 */     nbttagcompound.setInt("ACX", this.c.x);
/* 395 */     nbttagcompound.setInt("ACY", this.c.y);
/* 396 */     nbttagcompound.setInt("ACZ", this.c.z);
/* 397 */     NBTTagList nbttaglist = new NBTTagList();
/* 398 */     Iterator<VillageDoor> iterator = this.doors.iterator();
/*     */     
/* 400 */     while (iterator.hasNext()) {
/* 401 */       VillageDoor villagedoor = iterator.next();
/* 402 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 404 */       nbttagcompound1.setInt("X", villagedoor.locX);
/* 405 */       nbttagcompound1.setInt("Y", villagedoor.locY);
/* 406 */       nbttagcompound1.setInt("Z", villagedoor.locZ);
/* 407 */       nbttagcompound1.setInt("IDX", villagedoor.d);
/* 408 */       nbttagcompound1.setInt("IDZ", villagedoor.e);
/* 409 */       nbttagcompound1.setInt("TS", villagedoor.addedTime);
/* 410 */       nbttaglist.add(nbttagcompound1);
/*     */     } 
/*     */     
/* 413 */     nbttagcompound.set("Doors", nbttaglist);
/* 414 */     NBTTagList nbttaglist1 = new NBTTagList();
/* 415 */     Iterator<String> iterator1 = this.playerStandings.keySet().iterator();
/*     */     
/* 417 */     while (iterator1.hasNext()) {
/* 418 */       String s = iterator1.next();
/* 419 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*     */       
/* 421 */       nbttagcompound2.setString("Name", s);
/* 422 */       nbttagcompound2.setInt("S", ((Integer)this.playerStandings.get(s)).intValue());
/* 423 */       nbttaglist1.add(nbttagcompound2);
/*     */     } 
/*     */     
/* 426 */     nbttagcompound.set("Players", nbttaglist1);
/*     */   }
/*     */   
/*     */   public void h() {
/* 430 */     this.noBreedTicks = this.time;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 434 */     return (this.noBreedTicks == 0 || this.time - this.noBreedTicks >= 3600);
/*     */   }
/*     */   
/*     */   public void b(int i) {
/* 438 */     Iterator<String> iterator = this.playerStandings.keySet().iterator();
/*     */     
/* 440 */     while (iterator.hasNext()) {
/* 441 */       String s = iterator.next();
/*     */       
/* 443 */       a(s, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Village.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */