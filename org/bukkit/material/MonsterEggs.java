/*    */ package org.bukkit.material;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MonsterEggs
/*    */   extends TexturedMaterial
/*    */ {
/* 13 */   private static final List<Material> textures = new ArrayList<Material>();
/*    */   static {
/* 15 */     textures.add(Material.STONE);
/* 16 */     textures.add(Material.COBBLESTONE);
/* 17 */     textures.add(Material.SMOOTH_BRICK);
/*    */   }
/*    */   
/*    */   public MonsterEggs() {
/* 21 */     super(Material.MONSTER_EGGS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public MonsterEggs(int type) {
/* 30 */     super(type);
/*    */   }
/*    */   
/*    */   public MonsterEggs(Material type) {
/* 34 */     super(textures.contains(type) ? Material.MONSTER_EGGS : type);
/* 35 */     if (textures.contains(type)) {
/* 36 */       setMaterial(type);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public MonsterEggs(int type, byte data) {
/* 46 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public MonsterEggs(Material type, byte data) {
/* 55 */     super(type, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Material> getTextures() {
/* 60 */     return textures;
/*    */   }
/*    */ 
/*    */   
/*    */   public MonsterEggs clone() {
/* 65 */     return (MonsterEggs)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\MonsterEggs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */