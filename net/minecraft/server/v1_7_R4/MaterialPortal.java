/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class MaterialPortal extends Material {
/*    */   public MaterialPortal(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuildable() {
/* 10 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 15 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MaterialPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */