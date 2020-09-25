/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class RandomPositionGenerator
/*    */ {
/*  7 */   private static Vec3D a = Vec3D.a(0.0D, 0.0D, 0.0D);
/*    */   
/*    */   public static Vec3D a(EntityCreature entitycreature, int i, int j) {
/* 10 */     return c(entitycreature, i, j, (Vec3D)null);
/*    */   }
/*    */   
/*    */   public static Vec3D a(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
/* 14 */     vec3d.a -= entitycreature.locX;
/* 15 */     vec3d.b -= entitycreature.locY;
/* 16 */     vec3d.c -= entitycreature.locZ;
/* 17 */     return c(entitycreature, i, j, a);
/*    */   }
/*    */   
/*    */   public static Vec3D b(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
/* 21 */     a.a = entitycreature.locX - vec3d.a;
/* 22 */     a.b = entitycreature.locY - vec3d.b;
/* 23 */     a.c = entitycreature.locZ - vec3d.c;
/* 24 */     return c(entitycreature, i, j, a);
/*    */   }
/*    */   private static Vec3D c(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
/*    */     boolean flag1;
/* 28 */     Random random = entitycreature.aI();
/* 29 */     boolean flag = false;
/*    */     
/* 31 */     double k = 0.0D;
/* 32 */     double l = 0.0D;
/* 33 */     double i1 = 0.0D;
/*    */     
/* 35 */     float f = -99999.0F;
/*    */ 
/*    */     
/* 38 */     if (entitycreature.bY()) {
/* 39 */       double d0 = (entitycreature.bV().e(MathHelper.floor(entitycreature.locX), MathHelper.floor(entitycreature.locY), MathHelper.floor(entitycreature.locZ)) + 4.0F);
/* 40 */       double d1 = (entitycreature.bW() + i);
/*    */       
/* 42 */       flag1 = (d0 < d1 * d1);
/*    */     } else {
/* 44 */       flag1 = false;
/*    */     } 
/*    */     
/* 47 */     for (int j1 = 0; j1 < 10; j1++) {
/*    */       
/* 49 */       int k1 = random.nextInt(2 * i + 1) - i;
/* 50 */       int l1 = random.nextInt(2 * j + 1) - j;
/* 51 */       int i2 = random.nextInt(2 * i + 1) - i;
/*    */ 
/*    */       
/* 54 */       if (vec3d == null || k1 * vec3d.a + i2 * vec3d.c >= 0.0D) {
/*    */         
/* 56 */         int k1Mod = k1 + MathHelper.floor(entitycreature.locX);
/* 57 */         int l1Mod = l1 + MathHelper.floor(entitycreature.locY);
/* 58 */         int i2Mod = i2 + MathHelper.floor(entitycreature.locZ);
/* 59 */         if (!flag1 || entitycreature.b(k1Mod, l1Mod, i2Mod)) {
/* 60 */           float f1 = entitycreature.a(k1Mod, l1Mod, i2Mod);
/*    */           
/* 62 */           if (f1 > f) {
/* 63 */             f = f1;
/*    */             
/* 65 */             k = entitycreature.locX + k1;
/* 66 */             l = entitycreature.locY + l1;
/* 67 */             i1 = entitycreature.locZ + i2;
/*    */             
/* 69 */             flag = true;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     if (flag) {
/* 76 */       return Vec3D.a(k, l, i1);
/*    */     }
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RandomPositionGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */