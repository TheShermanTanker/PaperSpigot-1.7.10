/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ 
/*    */ public class BiomeTheEndDecorator
/*    */   extends BiomeDecorator
/*    */ {
/*  8 */   protected WorldGenerator J = new WorldGenEnder(Blocks.WHITESTONE);
/*    */ 
/*    */   
/*    */   protected void a(BiomeBase biomebase) {
/* 12 */     a();
/* 13 */     if (this.b.nextInt(5) == 0) {
/* 14 */       int i = this.c + this.b.nextInt(16) + 8;
/* 15 */       int j = this.d + this.b.nextInt(16) + 8;
/* 16 */       int k = this.a.i(i, j);
/*    */       
/* 18 */       this.J.generate(this.a, this.b, i, k, j);
/*    */     } 
/*    */     
/* 21 */     if (this.c == 0 && this.d == 0) {
/* 22 */       EntityEnderDragon entityenderdragon = new EntityEnderDragon(this.a);
/*    */       
/* 24 */       entityenderdragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.b.nextFloat() * 360.0F, 0.0F);
/* 25 */       this.a.addEntity(entityenderdragon, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeTheEndDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */