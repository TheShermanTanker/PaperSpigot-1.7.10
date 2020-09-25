/*     */ package org.bukkit;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.util.NumberConversions;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Location
/*     */   implements Cloneable
/*     */ {
/*     */   private World world;
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float pitch;
/*     */   private float yaw;
/*     */   
/*     */   public Location(World world, double x, double y, double z) {
/*  27 */     this(world, x, y, z, 0.0F, 0.0F);
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
/*     */   public Location(World world, double x, double y, double z, float yaw, float pitch) {
/*  41 */     this.world = world;
/*  42 */     this.x = x;
/*  43 */     this.y = y;
/*  44 */     this.z = z;
/*  45 */     this.pitch = pitch;
/*  46 */     this.yaw = yaw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorld(World world) {
/*  55 */     this.world = world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  64 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk getChunk() {
/*  73 */     return this.world.getChunkAt(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/*  82 */     return this.world.getBlockAt(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(double x) {
/*  91 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 100 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockX() {
/* 110 */     return locToBlock(this.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(double y) {
/* 119 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 128 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockY() {
/* 138 */     return locToBlock(this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(double z) {
/* 147 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 156 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockZ() {
/* 166 */     return locToBlock(this.z);
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
/*     */   public void setYaw(float yaw) {
/* 184 */     this.yaw = yaw;
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
/*     */   public float getYaw() {
/* 202 */     return this.yaw;
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
/*     */   public void setPitch(float pitch) {
/* 218 */     this.pitch = pitch;
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
/*     */   public float getPitch() {
/* 234 */     return this.pitch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getDirection() {
/* 245 */     Vector vector = new Vector();
/*     */     
/* 247 */     double rotX = getYaw();
/* 248 */     double rotY = getPitch();
/*     */     
/* 250 */     vector.setY(-Math.sin(Math.toRadians(rotY)));
/*     */     
/* 252 */     double xz = Math.cos(Math.toRadians(rotY));
/*     */     
/* 254 */     vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
/* 255 */     vector.setZ(xz * Math.cos(Math.toRadians(rotX)));
/*     */     
/* 257 */     return vector;
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
/*     */   public Location setDirection(Vector vector) {
/* 273 */     double _2PI = 6.283185307179586D;
/* 274 */     double x = vector.getX();
/* 275 */     double z = vector.getZ();
/*     */     
/* 277 */     if (x == 0.0D && z == 0.0D) {
/* 278 */       this.pitch = (vector.getY() > 0.0D) ? -90.0F : 90.0F;
/* 279 */       return this;
/*     */     } 
/*     */     
/* 282 */     double theta = Math.atan2(-x, z);
/* 283 */     this.yaw = (float)Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D);
/*     */     
/* 285 */     double x2 = NumberConversions.square(x);
/* 286 */     double z2 = NumberConversions.square(z);
/* 287 */     double xz = Math.sqrt(x2 + z2);
/* 288 */     this.pitch = (float)Math.toDegrees(Math.atan(-vector.getY() / xz));
/*     */     
/* 290 */     return this;
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
/*     */   public Location add(Location vec) {
/* 302 */     if (vec == null || vec.getWorld() != getWorld()) {
/* 303 */       throw new IllegalArgumentException("Cannot add Locations of differing worlds");
/*     */     }
/*     */     
/* 306 */     this.x += vec.x;
/* 307 */     this.y += vec.y;
/* 308 */     this.z += vec.z;
/* 309 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location add(Vector vec) {
/* 320 */     this.x += vec.getX();
/* 321 */     this.y += vec.getY();
/* 322 */     this.z += vec.getZ();
/* 323 */     return this;
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
/*     */   public Location add(double x, double y, double z) {
/* 336 */     this.x += x;
/* 337 */     this.y += y;
/* 338 */     this.z += z;
/* 339 */     return this;
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
/*     */   public Location subtract(Location vec) {
/* 351 */     if (vec == null || vec.getWorld() != getWorld()) {
/* 352 */       throw new IllegalArgumentException("Cannot add Locations of differing worlds");
/*     */     }
/*     */     
/* 355 */     this.x -= vec.x;
/* 356 */     this.y -= vec.y;
/* 357 */     this.z -= vec.z;
/* 358 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location subtract(Vector vec) {
/* 369 */     this.x -= vec.getX();
/* 370 */     this.y -= vec.getY();
/* 371 */     this.z -= vec.getZ();
/* 372 */     return this;
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
/*     */   public Location subtract(double x, double y, double z) {
/* 386 */     this.x -= x;
/* 387 */     this.y -= y;
/* 388 */     this.z -= z;
/* 389 */     return this;
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
/*     */   public double length() {
/* 404 */     return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double lengthSquared() {
/* 415 */     return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
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
/*     */   public double distance(Location o) {
/* 431 */     return Math.sqrt(distanceSquared(o));
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
/*     */   public double distanceSquared(Location o) {
/* 443 */     if (o == null)
/* 444 */       throw new IllegalArgumentException("Cannot measure distance to a null location"); 
/* 445 */     if (o.getWorld() == null || getWorld() == null)
/* 446 */       throw new IllegalArgumentException("Cannot measure distance to a null world"); 
/* 447 */     if (o.getWorld() != getWorld()) {
/* 448 */       throw new IllegalArgumentException("Cannot measure distance between " + getWorld().getName() + " and " + o.getWorld().getName());
/*     */     }
/*     */     
/* 451 */     return NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z);
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
/*     */   public Location multiply(double m) {
/* 463 */     this.x *= m;
/* 464 */     this.y *= m;
/* 465 */     this.z *= m;
/* 466 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location zero() {
/* 476 */     this.x = 0.0D;
/* 477 */     this.y = 0.0D;
/* 478 */     this.z = 0.0D;
/* 479 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 484 */     if (obj == null) {
/* 485 */       return false;
/*     */     }
/* 487 */     if (getClass() != obj.getClass()) {
/* 488 */       return false;
/*     */     }
/* 490 */     Location other = (Location)obj;
/*     */     
/* 492 */     if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
/* 493 */       return false;
/*     */     }
/* 495 */     if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
/* 496 */       return false;
/*     */     }
/* 498 */     if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
/* 499 */       return false;
/*     */     }
/* 501 */     if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
/* 502 */       return false;
/*     */     }
/* 504 */     if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
/* 505 */       return false;
/*     */     }
/* 507 */     if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
/* 508 */       return false;
/*     */     }
/* 510 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 515 */     int hash = 3;
/*     */     
/* 517 */     hash = 19 * hash + ((this.world != null) ? this.world.hashCode() : 0);
/* 518 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32L);
/* 519 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32L);
/* 520 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32L);
/* 521 */     hash = 19 * hash + Float.floatToIntBits(this.pitch);
/* 522 */     hash = 19 * hash + Float.floatToIntBits(this.yaw);
/* 523 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 528 */     return "Location{world=" + this.world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",pitch=" + this.pitch + ",yaw=" + this.yaw + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector toVector() {
/* 538 */     return new Vector(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Location clone() {
/*     */     try {
/* 544 */       return (Location)super.clone();
/* 545 */     } catch (CloneNotSupportedException e) {
/* 546 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int locToBlock(double loc) {
/* 558 */     return NumberConversions.floor(loc);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Location.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */