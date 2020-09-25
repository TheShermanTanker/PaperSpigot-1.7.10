/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnEgg
/*    */   extends MaterialData
/*    */ {
/*    */   public SpawnEgg() {
/* 12 */     super(Material.MONSTER_EGG);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public SpawnEgg(int type, byte data) {
/* 21 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public SpawnEgg(byte data) {
/* 30 */     super(Material.MONSTER_EGG, data);
/*    */   }
/*    */   
/*    */   public SpawnEgg(EntityType type) {
/* 34 */     this();
/* 35 */     setSpawnedType(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityType getSpawnedType() {
/* 44 */     return EntityType.fromId(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpawnedType(EntityType type) {
/* 53 */     setData((byte)type.getTypeId());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return "SPAWN EGG{" + getSpawnedType() + "}";
/*    */   }
/*    */ 
/*    */   
/*    */   public SpawnEgg clone() {
/* 63 */     return (SpawnEgg)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\SpawnEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */