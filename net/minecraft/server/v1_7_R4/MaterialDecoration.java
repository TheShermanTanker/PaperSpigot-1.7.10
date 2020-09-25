/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class MaterialDecoration extends Material {
/*    */   public MaterialDecoration(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*  6 */     p();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuildable() {
/* 11 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MaterialDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */