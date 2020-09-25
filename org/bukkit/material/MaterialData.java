/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ public class MaterialData
/*     */   implements Cloneable
/*     */ {
/*     */   private final int type;
/*  11 */   private byte data = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MaterialData(int type) {
/*  19 */     this(type, (byte)0);
/*     */   }
/*     */   
/*     */   public MaterialData(Material type) {
/*  23 */     this(type, (byte)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MaterialData(int type, byte data) {
/*  32 */     this.type = type;
/*  33 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MaterialData(Material type, byte data) {
/*  42 */     this(type.getId(), data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte getData() {
/*  53 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setData(byte data) {
/*  64 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getItemType() {
/*  73 */     return Material.getMaterial(this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getItemTypeId() {
/*  84 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack toItemStack() {
/*  93 */     return new ItemStack(this.type, 0, (short)this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack toItemStack(int amount) {
/* 103 */     return new ItemStack(this.type, amount, (short)this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return getItemType() + "(" + getData() + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return getItemTypeId() << 8 ^ getData();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 118 */     if (obj != null && obj instanceof MaterialData) {
/* 119 */       MaterialData md = (MaterialData)obj;
/*     */       
/* 121 */       return (md.getItemTypeId() == getItemTypeId() && md.getData() == getData());
/*     */     } 
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialData clone() {
/*     */     try {
/* 130 */       return (MaterialData)super.clone();
/* 131 */     } catch (CloneNotSupportedException e) {
/* 132 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\MaterialData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */