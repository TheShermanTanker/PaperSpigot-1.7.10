/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SerializableAs("BlockVector")
/*     */ public class BlockVector
/*     */   extends Vector
/*     */ {
/*     */   public BlockVector() {
/*  19 */     this.x = 0.0D;
/*  20 */     this.y = 0.0D;
/*  21 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(Vector vec) {
/*  30 */     this.x = vec.getX();
/*  31 */     this.y = vec.getY();
/*  32 */     this.z = vec.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(int x, int y, int z) {
/*  43 */     this.x = x;
/*  44 */     this.y = y;
/*  45 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(double x, double y, double z) {
/*  56 */     this.x = x;
/*  57 */     this.y = y;
/*  58 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector(float x, float y, float z) {
/*  69 */     this.x = x;
/*  70 */     this.y = y;
/*  71 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  82 */     if (!(obj instanceof BlockVector)) {
/*  83 */       return false;
/*     */     }
/*  85 */     BlockVector other = (BlockVector)obj;
/*     */     
/*  87 */     return ((int)other.getX() == (int)this.x && (int)other.getY() == (int)this.y && (int)other.getZ() == (int)this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  98 */     return Integer.valueOf((int)this.x).hashCode() >> 13 ^ Integer.valueOf((int)this.y).hashCode() >> 7 ^ Integer.valueOf((int)this.z).hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockVector clone() {
/* 108 */     return (BlockVector)super.clone();
/*     */   }
/*     */   
/*     */   public static BlockVector deserialize(Map<String, Object> args) {
/* 112 */     double x = 0.0D;
/* 113 */     double y = 0.0D;
/* 114 */     double z = 0.0D;
/*     */     
/* 116 */     if (args.containsKey("x")) {
/* 117 */       x = ((Double)args.get("x")).doubleValue();
/*     */     }
/* 119 */     if (args.containsKey("y")) {
/* 120 */       y = ((Double)args.get("y")).doubleValue();
/*     */     }
/* 122 */     if (args.containsKey("z")) {
/* 123 */       z = ((Double)args.get("z")).doubleValue();
/*     */     }
/*     */     
/* 126 */     return new BlockVector(x, y, z);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\BlockVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */