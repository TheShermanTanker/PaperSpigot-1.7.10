/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mushroom
/*     */   extends MaterialData
/*     */ {
/*     */   private static final byte SHROOM_NONE = 0;
/*     */   private static final byte SHROOM_STEM = 10;
/*     */   private static final byte NORTH_LIMIT = 4;
/*     */   private static final byte SOUTH_LIMIT = 6;
/*     */   private static final byte EAST_WEST_LIMIT = 3;
/*     */   private static final byte EAST_REMAINDER = 0;
/*     */   private static final byte WEST_REMAINDER = 1;
/*     */   private static final byte NORTH_SOUTH_MOD = 3;
/*     */   private static final byte EAST_WEST_MOD = 1;
/*     */   
/*     */   public Mushroom(Material shroom) {
/*  25 */     super(shroom);
/*  26 */     Validate.isTrue((shroom == Material.HUGE_MUSHROOM_1 || shroom == Material.HUGE_MUSHROOM_2), "Not a mushroom!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Mushroom(Material shroom, byte data) {
/*  35 */     super(shroom, data);
/*  36 */     Validate.isTrue((shroom == Material.HUGE_MUSHROOM_1 || shroom == Material.HUGE_MUSHROOM_2), "Not a mushroom!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Mushroom(int type, byte data) {
/*  45 */     super(type, data);
/*  46 */     Validate.isTrue((type == Material.HUGE_MUSHROOM_1.getId() || type == Material.HUGE_MUSHROOM_2.getId()), "Not a mushroom!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStem() {
/*  53 */     return (getData() == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStem() {
/*  60 */     setData((byte)10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFacePainted(BlockFace face) {
/*  70 */     byte data = getData();
/*     */     
/*  72 */     if (data == 0 || data == 10) {
/*  73 */       return false;
/*     */     }
/*     */     
/*  76 */     switch (face) {
/*     */       case WEST:
/*  78 */         return (data < 4);
/*     */       case EAST:
/*  80 */         return (data > 6);
/*     */       case NORTH:
/*  82 */         return (data % 3 == 0);
/*     */       case SOUTH:
/*  84 */         return (data % 3 == 1);
/*     */       case UP:
/*  86 */         return true;
/*     */     } 
/*  88 */     return false;
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
/*     */   public void setFacePainted(BlockFace face, boolean painted) {
/* 102 */     if (painted == isFacePainted(face)) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     byte data = getData();
/*     */     
/* 108 */     if (data == 10) {
/* 109 */       data = 5;
/*     */     }
/*     */     
/* 112 */     switch (face) {
/*     */       case WEST:
/* 114 */         if (painted) {
/* 115 */           data = (byte)(data - 3); break;
/*     */         } 
/* 117 */         data = (byte)(data + 3);
/*     */         break;
/*     */ 
/*     */       
/*     */       case EAST:
/* 122 */         if (painted) {
/* 123 */           data = (byte)(data + 3); break;
/*     */         } 
/* 125 */         data = (byte)(data - 3);
/*     */         break;
/*     */ 
/*     */       
/*     */       case NORTH:
/* 130 */         if (painted) {
/* 131 */           data = (byte)(data + 1); break;
/*     */         } 
/* 133 */         data = (byte)(data - 1);
/*     */         break;
/*     */ 
/*     */       
/*     */       case SOUTH:
/* 138 */         if (painted) {
/* 139 */           data = (byte)(data - 1); break;
/*     */         } 
/* 141 */         data = (byte)(data + 1);
/*     */         break;
/*     */ 
/*     */       
/*     */       case UP:
/* 146 */         if (!painted) {
/* 147 */           data = 0;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/* 152 */         throw new IllegalArgumentException("Can't paint that face of a mushroom!");
/*     */     } 
/*     */     
/* 155 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<BlockFace> getPaintedFaces() {
/* 163 */     EnumSet<BlockFace> faces = EnumSet.noneOf(BlockFace.class);
/*     */     
/* 165 */     if (isFacePainted(BlockFace.WEST)) {
/* 166 */       faces.add(BlockFace.WEST);
/*     */     }
/*     */     
/* 169 */     if (isFacePainted(BlockFace.NORTH)) {
/* 170 */       faces.add(BlockFace.NORTH);
/*     */     }
/*     */     
/* 173 */     if (isFacePainted(BlockFace.SOUTH)) {
/* 174 */       faces.add(BlockFace.SOUTH);
/*     */     }
/*     */     
/* 177 */     if (isFacePainted(BlockFace.EAST)) {
/* 178 */       faces.add(BlockFace.EAST);
/*     */     }
/*     */     
/* 181 */     if (isFacePainted(BlockFace.UP)) {
/* 182 */       faces.add(BlockFace.UP);
/*     */     }
/*     */     
/* 185 */     return faces;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return Material.getMaterial(getItemTypeId()).toString() + (isStem() ? "{STEM}" : (String)getPaintedFaces());
/*     */   }
/*     */ 
/*     */   
/*     */   public Mushroom clone() {
/* 195 */     return (Mushroom)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Mushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */