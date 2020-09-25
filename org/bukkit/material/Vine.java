/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vine
/*     */   extends MaterialData
/*     */ {
/*     */   private static final int VINE_NORTH = 4;
/*     */   private static final int VINE_EAST = 8;
/*     */   private static final int VINE_WEST = 2;
/*     */   private static final int VINE_SOUTH = 1;
/*  17 */   EnumSet<BlockFace> possibleFaces = EnumSet.of(BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST);
/*     */   
/*     */   public Vine() {
/*  20 */     super(Material.VINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Vine(int type, byte data) {
/*  29 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Vine(byte data) {
/*  38 */     super(Material.VINE, data);
/*     */   }
/*     */   
/*     */   public Vine(BlockFace... faces) {
/*  42 */     this(EnumSet.copyOf(Arrays.asList(faces)));
/*     */   }
/*     */   
/*     */   public Vine(EnumSet<BlockFace> faces) {
/*  46 */     this((byte)0);
/*  47 */     faces.retainAll(this.possibleFaces);
/*     */     
/*  49 */     byte data = 0;
/*     */     
/*  51 */     if (faces.contains(BlockFace.WEST)) {
/*  52 */       data = (byte)(data | 0x2);
/*     */     }
/*     */     
/*  55 */     if (faces.contains(BlockFace.NORTH)) {
/*  56 */       data = (byte)(data | 0x4);
/*     */     }
/*     */     
/*  59 */     if (faces.contains(BlockFace.SOUTH)) {
/*  60 */       data = (byte)(data | 0x1);
/*     */     }
/*     */     
/*  63 */     if (faces.contains(BlockFace.EAST)) {
/*  64 */       data = (byte)(data | 0x8);
/*     */     }
/*     */     
/*  67 */     setData(data);
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
/*     */   public boolean isOnFace(BlockFace face) {
/*  79 */     switch (face) {
/*     */       case WEST:
/*  81 */         return ((getData() & 0x2) == 2);
/*     */       case NORTH:
/*  83 */         return ((getData() & 0x4) == 4);
/*     */       case SOUTH:
/*  85 */         return ((getData() & 0x1) == 1);
/*     */       case EAST:
/*  87 */         return ((getData() & 0x8) == 8);
/*     */       case NORTH_EAST:
/*  89 */         return (isOnFace(BlockFace.EAST) && isOnFace(BlockFace.NORTH));
/*     */       case NORTH_WEST:
/*  91 */         return (isOnFace(BlockFace.WEST) && isOnFace(BlockFace.NORTH));
/*     */       case SOUTH_EAST:
/*  93 */         return (isOnFace(BlockFace.EAST) && isOnFace(BlockFace.SOUTH));
/*     */       case SOUTH_WEST:
/*  95 */         return (isOnFace(BlockFace.WEST) && isOnFace(BlockFace.SOUTH));
/*     */       case UP:
/*  97 */         return true;
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putOnFace(BlockFace face) {
/* 109 */     switch (face) {
/*     */       case WEST:
/* 111 */         setData((byte)(getData() | 0x2));
/*     */       
/*     */       case NORTH:
/* 114 */         setData((byte)(getData() | 0x4));
/*     */       
/*     */       case SOUTH:
/* 117 */         setData((byte)(getData() | 0x1));
/*     */       
/*     */       case EAST:
/* 120 */         setData((byte)(getData() | 0x8));
/*     */       
/*     */       case NORTH_WEST:
/* 123 */         putOnFace(BlockFace.WEST);
/* 124 */         putOnFace(BlockFace.NORTH);
/*     */       
/*     */       case SOUTH_WEST:
/* 127 */         putOnFace(BlockFace.WEST);
/* 128 */         putOnFace(BlockFace.SOUTH);
/*     */       
/*     */       case NORTH_EAST:
/* 131 */         putOnFace(BlockFace.EAST);
/* 132 */         putOnFace(BlockFace.NORTH);
/*     */       
/*     */       case SOUTH_EAST:
/* 135 */         putOnFace(BlockFace.EAST);
/* 136 */         putOnFace(BlockFace.SOUTH);
/*     */       
/*     */       case UP:
/*     */         return;
/*     */     } 
/* 141 */     throw new IllegalArgumentException("Vines can't go on face " + face.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFromFace(BlockFace face) {
/* 151 */     switch (face) {
/*     */       case WEST:
/* 153 */         setData((byte)(getData() & 0xFFFFFFFD));
/*     */       
/*     */       case NORTH:
/* 156 */         setData((byte)(getData() & 0xFFFFFFFB));
/*     */       
/*     */       case SOUTH:
/* 159 */         setData((byte)(getData() & 0xFFFFFFFE));
/*     */       
/*     */       case EAST:
/* 162 */         setData((byte)(getData() & 0xFFFFFFF7));
/*     */       
/*     */       case NORTH_WEST:
/* 165 */         removeFromFace(BlockFace.WEST);
/* 166 */         removeFromFace(BlockFace.NORTH);
/*     */       
/*     */       case SOUTH_WEST:
/* 169 */         removeFromFace(BlockFace.WEST);
/* 170 */         removeFromFace(BlockFace.SOUTH);
/*     */       
/*     */       case NORTH_EAST:
/* 173 */         removeFromFace(BlockFace.EAST);
/* 174 */         removeFromFace(BlockFace.NORTH);
/*     */       
/*     */       case SOUTH_EAST:
/* 177 */         removeFromFace(BlockFace.EAST);
/* 178 */         removeFromFace(BlockFace.SOUTH);
/*     */       
/*     */       case UP:
/*     */         return;
/*     */     } 
/* 183 */     throw new IllegalArgumentException("Vines can't go on face " + face.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 189 */     return "VINE";
/*     */   }
/*     */ 
/*     */   
/*     */   public Vine clone() {
/* 194 */     return (Vine)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Vine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */