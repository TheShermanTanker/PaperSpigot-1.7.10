/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class VillageDoor {
/*    */   public VillageDoor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  5 */     this.locX = paramInt1;
/*  6 */     this.locY = paramInt2;
/*  7 */     this.locZ = paramInt3;
/*  8 */     this.d = paramInt4;
/*  9 */     this.e = paramInt5;
/* 10 */     this.addedTime = paramInt6;
/*    */   }
/*    */ 
/*    */   
/*    */   public final int locX;
/*    */   
/*    */   public final int locY;
/*    */   
/*    */   public final int locZ;
/*    */   
/*    */   public final int d;
/*    */   public final int e;
/*    */   public int addedTime;
/*    */   public boolean removed;
/*    */   private int bookings;
/*    */   
/*    */   public int b(int paramInt1, int paramInt2, int paramInt3) {
/* 27 */     int i = paramInt1 - this.locX;
/* 28 */     int j = paramInt2 - this.locY;
/* 29 */     int k = paramInt3 - this.locZ;
/* 30 */     return i * i + j * j + k * k;
/*    */   }
/*    */   
/*    */   public int c(int paramInt1, int paramInt2, int paramInt3) {
/* 34 */     int i = paramInt1 - this.locX - this.d;
/* 35 */     int j = paramInt2 - this.locY;
/* 36 */     int k = paramInt3 - this.locZ - this.e;
/* 37 */     return i * i + j * j + k * k;
/*    */   }
/*    */   
/*    */   public int getIndoorsX() {
/* 41 */     return this.locX + this.d;
/*    */   }
/*    */   
/*    */   public int getIndoorsY() {
/* 45 */     return this.locY;
/*    */   }
/*    */   
/*    */   public int getIndoorsZ() {
/* 49 */     return this.locZ + this.e;
/*    */   }
/*    */   
/*    */   public boolean a(int paramInt1, int paramInt2) {
/* 53 */     int i = paramInt1 - this.locX;
/* 54 */     int j = paramInt2 - this.locZ;
/* 55 */     return (i * this.d + j * this.e >= 0);
/*    */   }
/*    */   
/*    */   public void d() {
/* 59 */     this.bookings = 0;
/*    */   }
/*    */   
/*    */   public void e() {
/* 63 */     this.bookings++;
/*    */   }
/*    */   
/*    */   public int f() {
/* 67 */     return this.bookings;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\VillageDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */