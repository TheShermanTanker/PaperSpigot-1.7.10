/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeMushrooms
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeMushrooms(int paramInt) {
/*  9 */     super(paramInt);
/*    */     
/* 11 */     this.ar.x = -100;
/* 12 */     this.ar.y = -100;
/* 13 */     this.ar.z = -100;
/*    */     
/* 15 */     this.ar.B = 1;
/* 16 */     this.ar.H = 1;
/*    */     
/* 18 */     this.ai = Blocks.MYCEL;
/*    */     
/* 20 */     this.as.clear();
/* 21 */     this.at.clear();
/* 22 */     this.au.clear();
/*    */     
/* 24 */     this.at.add(new BiomeMeta(EntityMushroomCow.class, 8, 4, 8));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeMushrooms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */