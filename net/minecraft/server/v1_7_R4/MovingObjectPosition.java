/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingObjectPosition
/*    */ {
/*    */   public EnumMovingObjectType type;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int face;
/*    */   public Vec3D pos;
/*    */   public Entity entity;
/*    */   
/*    */   public MovingObjectPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Vec3D paramVec3D) {
/* 19 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramVec3D, true);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Vec3D paramVec3D, boolean paramBoolean) {
/* 23 */     this.type = paramBoolean ? EnumMovingObjectType.BLOCK : EnumMovingObjectType.MISS;
/* 24 */     this.b = paramInt1;
/* 25 */     this.c = paramInt2;
/* 26 */     this.d = paramInt3;
/* 27 */     this.face = paramInt4;
/* 28 */     this.pos = Vec3D.a(paramVec3D.a, paramVec3D.b, paramVec3D.c);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity paramEntity) {
/* 32 */     this(paramEntity, Vec3D.a(paramEntity.locX, paramEntity.locY, paramEntity.locZ));
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity paramEntity, Vec3D paramVec3D) {
/* 36 */     this.type = EnumMovingObjectType.ENTITY;
/* 37 */     this.entity = paramEntity;
/* 38 */     this.pos = paramVec3D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return "HitResult{type=" + this.type + ", x=" + this.b + ", y=" + this.c + ", z=" + this.d + ", f=" + this.face + ", pos=" + this.pos + ", entity=" + this.entity + '}';
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MovingObjectPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */