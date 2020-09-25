/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SerializableAs("Vector")
/*     */ public class Vector
/*     */   implements Cloneable, ConfigurationSerializable
/*     */ {
/*     */   private static final long serialVersionUID = -2657651106777219169L;
/*  21 */   private static Random random = new Random();
/*     */ 
/*     */   
/*     */   private static final double epsilon = 1.0E-6D;
/*     */ 
/*     */   
/*     */   protected double x;
/*     */ 
/*     */   
/*     */   protected double y;
/*     */   
/*     */   protected double z;
/*     */ 
/*     */   
/*     */   public Vector() {
/*  36 */     this.x = 0.0D;
/*  37 */     this.y = 0.0D;
/*  38 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(int x, int y, int z) {
/*  49 */     this.x = x;
/*  50 */     this.y = y;
/*  51 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(double x, double y, double z) {
/*  62 */     this.x = x;
/*  63 */     this.y = y;
/*  64 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(float x, float y, float z) {
/*  75 */     this.x = x;
/*  76 */     this.y = y;
/*  77 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector add(Vector vec) {
/*  87 */     this.x += vec.x;
/*  88 */     this.y += vec.y;
/*  89 */     this.z += vec.z;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector subtract(Vector vec) {
/* 100 */     this.x -= vec.x;
/* 101 */     this.y -= vec.y;
/* 102 */     this.z -= vec.z;
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(Vector vec) {
/* 113 */     this.x *= vec.x;
/* 114 */     this.y *= vec.y;
/* 115 */     this.z *= vec.z;
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector divide(Vector vec) {
/* 126 */     this.x /= vec.x;
/* 127 */     this.y /= vec.y;
/* 128 */     this.z /= vec.z;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector copy(Vector vec) {
/* 139 */     this.x = vec.x;
/* 140 */     this.y = vec.y;
/* 141 */     this.z = vec.z;
/* 142 */     return this;
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
/*     */   public double length() {
/* 155 */     return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double lengthSquared() {
/* 164 */     return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
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
/*     */   public double distance(Vector o) {
/* 178 */     return Math.sqrt(NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSquared(Vector o) {
/* 188 */     return NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float angle(Vector other) {
/* 198 */     double dot = dot(other) / length() * other.length();
/*     */     
/* 200 */     return (float)Math.acos(dot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector midpoint(Vector other) {
/* 210 */     this.x = (this.x + other.x) / 2.0D;
/* 211 */     this.y = (this.y + other.y) / 2.0D;
/* 212 */     this.z = (this.z + other.z) / 2.0D;
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getMidpoint(Vector other) {
/* 223 */     double x = (this.x + other.x) / 2.0D;
/* 224 */     double y = (this.y + other.y) / 2.0D;
/* 225 */     double z = (this.z + other.z) / 2.0D;
/* 226 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(int m) {
/* 237 */     this.x *= m;
/* 238 */     this.y *= m;
/* 239 */     this.z *= m;
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(double m) {
/* 251 */     this.x *= m;
/* 252 */     this.y *= m;
/* 253 */     this.z *= m;
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(float m) {
/* 265 */     this.x *= m;
/* 266 */     this.y *= m;
/* 267 */     this.z *= m;
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(Vector other) {
/* 279 */     return this.x * other.x + this.y * other.y + this.z * other.z;
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
/*     */   public Vector crossProduct(Vector o) {
/* 295 */     double newX = this.y * o.z - o.y * this.z;
/* 296 */     double newY = this.z * o.x - o.z * this.x;
/* 297 */     double newZ = this.x * o.y - o.x * this.y;
/*     */     
/* 299 */     this.x = newX;
/* 300 */     this.y = newY;
/* 301 */     this.z = newZ;
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector normalize() {
/* 311 */     double length = length();
/*     */     
/* 313 */     this.x /= length;
/* 314 */     this.y /= length;
/* 315 */     this.z /= length;
/*     */     
/* 317 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector zero() {
/* 326 */     this.x = 0.0D;
/* 327 */     this.y = 0.0D;
/* 328 */     this.z = 0.0D;
/* 329 */     return this;
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
/*     */   public boolean isInAABB(Vector min, Vector max) {
/* 343 */     return (this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInSphere(Vector origin, double radius) {
/* 354 */     return (NumberConversions.square(origin.x - this.x) + NumberConversions.square(origin.y - this.y) + NumberConversions.square(origin.z - this.z) <= NumberConversions.square(radius));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 363 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockX() {
/* 373 */     return NumberConversions.floor(this.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 382 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockY() {
/* 392 */     return NumberConversions.floor(this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 401 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockZ() {
/* 411 */     return NumberConversions.floor(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setX(int x) {
/* 421 */     this.x = x;
/* 422 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setX(double x) {
/* 432 */     this.x = x;
/* 433 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setX(float x) {
/* 443 */     this.x = x;
/* 444 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setY(int y) {
/* 454 */     this.y = y;
/* 455 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setY(double y) {
/* 465 */     this.y = y;
/* 466 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setY(float y) {
/* 476 */     this.y = y;
/* 477 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setZ(int z) {
/* 487 */     this.z = z;
/* 488 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setZ(double z) {
/* 498 */     this.z = z;
/* 499 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector setZ(float z) {
/* 509 */     this.z = z;
/* 510 */     return this;
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
/*     */   public boolean equals(Object obj) {
/* 522 */     if (!(obj instanceof Vector)) {
/* 523 */       return false;
/*     */     }
/*     */     
/* 526 */     Vector other = (Vector)obj;
/*     */     
/* 528 */     return (Math.abs(this.x - other.x) < 1.0E-6D && Math.abs(this.y - other.y) < 1.0E-6D && Math.abs(this.z - other.z) < 1.0E-6D && getClass().equals(obj.getClass()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 538 */     int hash = 7;
/*     */     
/* 540 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32L);
/* 541 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32L);
/* 542 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32L);
/* 543 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector clone() {
/*     */     try {
/* 554 */       return (Vector)super.clone();
/* 555 */     } catch (CloneNotSupportedException e) {
/* 556 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 565 */     return this.x + "," + this.y + "," + this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location toLocation(World world) {
/* 575 */     return new Location(world, this.x, this.y, this.z);
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
/*     */   public Location toLocation(World world, float yaw, float pitch) {
/* 587 */     return new Location(world, this.x, this.y, this.z, yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector toBlockVector() {
/* 596 */     return new BlockVector(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getEpsilon() {
/* 605 */     return 1.0E-6D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getMinimum(Vector v1, Vector v2) {
/* 616 */     return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getMaximum(Vector v1, Vector v2) {
/* 627 */     return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getRandom() {
/* 637 */     return new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble());
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 641 */     Map<String, Object> result = new LinkedHashMap<String, Object>();
/*     */     
/* 643 */     result.put("x", Double.valueOf(getX()));
/* 644 */     result.put("y", Double.valueOf(getY()));
/* 645 */     result.put("z", Double.valueOf(getZ()));
/*     */     
/* 647 */     return result;
/*     */   }
/*     */   
/*     */   public static Vector deserialize(Map<String, Object> args) {
/* 651 */     double x = 0.0D;
/* 652 */     double y = 0.0D;
/* 653 */     double z = 0.0D;
/*     */     
/* 655 */     if (args.containsKey("x")) {
/* 656 */       x = ((Double)args.get("x")).doubleValue();
/*     */     }
/* 658 */     if (args.containsKey("y")) {
/* 659 */       y = ((Double)args.get("y")).doubleValue();
/*     */     }
/* 661 */     if (args.containsKey("z")) {
/* 662 */       z = ((Double)args.get("z")).doubleValue();
/*     */     }
/*     */     
/* 665 */     return new Vector(x, y, z);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\Vector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */