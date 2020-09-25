/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMineshaftStart
/*    */   extends StructureStart
/*    */ {
/*    */   public WorldGenMineshaftStart() {}
/*    */   
/*    */   public WorldGenMineshaftStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 16 */     super(paramInt1, paramInt2);
/*    */     
/* 18 */     WorldGenMineshaftRoom worldGenMineshaftRoom = new WorldGenMineshaftRoom(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
/* 19 */     this.a.add(worldGenMineshaftRoom);
/* 20 */     worldGenMineshaftRoom.a(worldGenMineshaftRoom, this.a, paramRandom);
/*    */     
/* 22 */     c();
/* 23 */     a(paramWorld, paramRandom, 10);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMineshaftStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */