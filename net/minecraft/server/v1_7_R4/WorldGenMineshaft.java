/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMineshaft
/*    */   extends StructureGenerator
/*    */ {
/* 10 */   private double e = 0.004D;
/*    */ 
/*    */   
/*    */   public WorldGenMineshaft() {}
/*    */ 
/*    */   
/*    */   public String a() {
/* 17 */     return "Mineshaft";
/*    */   }
/*    */   
/*    */   public WorldGenMineshaft(Map paramMap) {
/* 21 */     for (Map.Entry entry : paramMap.entrySet()) {
/* 22 */       if (((String)entry.getKey()).equals("chance")) {
/* 23 */         this.e = MathHelper.a((String)entry.getValue(), this.e);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean a(int paramInt1, int paramInt2) {
/* 30 */     return (this.b.nextDouble() < this.e && this.b.nextInt(80) < Math.max(Math.abs(paramInt1), Math.abs(paramInt2)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureStart b(int paramInt1, int paramInt2) {
/* 35 */     return new WorldGenMineshaftStart(this.c, this.b, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMineshaft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */