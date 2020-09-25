/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathEntity
/*    */ {
/*    */   private final PathPoint[] a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PathEntity(PathPoint[] paramArrayOfPathPoint) {
/* 12 */     this.a = paramArrayOfPathPoint;
/* 13 */     this.c = paramArrayOfPathPoint.length;
/*    */   }
/*    */   
/*    */   public void a() {
/* 17 */     this.b++;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 21 */     return (this.b >= this.c);
/*    */   }
/*    */   
/*    */   public PathPoint c() {
/* 25 */     if (this.c > 0) {
/* 26 */       return this.a[this.c - 1];
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   public PathPoint a(int paramInt) {
/* 32 */     return this.a[paramInt];
/*    */   }
/*    */   
/*    */   public int d() {
/* 36 */     return this.c;
/*    */   }
/*    */   
/*    */   public void b(int paramInt) {
/* 40 */     this.c = paramInt;
/*    */   }
/*    */   
/*    */   public int e() {
/* 44 */     return this.b;
/*    */   }
/*    */   
/*    */   public void c(int paramInt) {
/* 48 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public Vec3D a(Entity paramEntity, int paramInt) {
/* 52 */     double d1 = (this.a[paramInt]).a + (int)(paramEntity.width + 1.0F) * 0.5D;
/* 53 */     double d2 = (this.a[paramInt]).b;
/* 54 */     double d3 = (this.a[paramInt]).c + (int)(paramEntity.width + 1.0F) * 0.5D;
/* 55 */     return Vec3D.a(d1, d2, d3);
/*    */   }
/*    */   
/*    */   public Vec3D a(Entity paramEntity) {
/* 59 */     return a(paramEntity, this.b);
/*    */   }
/*    */   
/*    */   public boolean a(PathEntity paramPathEntity) {
/* 63 */     if (paramPathEntity == null) return false; 
/* 64 */     if (paramPathEntity.a.length != this.a.length) return false; 
/* 65 */     for (byte b = 0; b < this.a.length; b++) {
/* 66 */       if ((this.a[b]).a != (paramPathEntity.a[b]).a || (this.a[b]).b != (paramPathEntity.a[b]).b || (this.a[b]).c != (paramPathEntity.a[b]).c)
/* 67 */         return false; 
/* 68 */     }  return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean b(Vec3D paramVec3D) {
/* 78 */     PathPoint pathPoint = c();
/* 79 */     if (pathPoint == null) return false; 
/* 80 */     return (pathPoint.a == (int)paramVec3D.a && pathPoint.c == (int)paramVec3D.c);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */