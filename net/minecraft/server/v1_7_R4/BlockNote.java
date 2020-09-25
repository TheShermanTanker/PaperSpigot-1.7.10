/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockNote
/*    */   extends BlockContainer
/*    */ {
/*    */   public BlockNote() {
/* 11 */     super(Material.WOOD);
/* 12 */     a(CreativeModeTab.d);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 18 */     boolean bool = paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3);
/* 19 */     TileEntityNote tileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 20 */     if (tileEntityNote != null && tileEntityNote.i != bool) {
/* 21 */       if (bool) {
/* 22 */         tileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */       }
/* 24 */       tileEntityNote.i = bool;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 31 */     if (paramWorld.isStatic) return true; 
/* 32 */     TileEntityNote tileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 33 */     if (tileEntityNote != null) {
/* 34 */       tileEntityNote.a();
/* 35 */       tileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */     } 
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 42 */     if (paramWorld.isStatic)
/* 43 */       return;  TileEntityNote tileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 44 */     if (tileEntityNote != null) tileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   
/*    */   }
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt) {
/* 49 */     return new TileEntityNote();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 54 */     float f = (float)Math.pow(2.0D, (paramInt5 - 12) / 12.0D);
/*    */     
/* 56 */     String str = "harp";
/* 57 */     if (paramInt4 == 1) str = "bd"; 
/* 58 */     if (paramInt4 == 2) str = "snare"; 
/* 59 */     if (paramInt4 == 3) str = "hat"; 
/* 60 */     if (paramInt4 == 4) str = "bassattack";
/*    */     
/* 62 */     paramWorld.makeSound(paramInt1 + 0.5D, paramInt2 + 0.5D, paramInt3 + 0.5D, "note." + str, 3.0F, f);
/* 63 */     paramWorld.addParticle("note", paramInt1 + 0.5D, paramInt2 + 1.2D, paramInt3 + 0.5D, paramInt5 / 24.0D, 0.0D, 0.0D);
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockNote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */