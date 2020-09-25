/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class MaterialGas extends Material {
/*    */   public MaterialGas(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*  6 */     i();
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MaterialGas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */