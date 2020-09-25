/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class MaterialLiquid extends Material {
/*    */   public MaterialLiquid(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*  6 */     i();
/*  7 */     n();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLiquid() {
/* 12 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 17 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuildable() {
/* 22 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MaterialLiquid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */