/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class WatchableObject
/*    */ {
/*    */   private final int a;
/*    */   private final int b;
/*    */   private Object c;
/*    */   private boolean d;
/*    */   
/*    */   public WatchableObject(int i, int j, Object object) {
/* 11 */     this.b = j;
/* 12 */     this.c = object;
/* 13 */     this.a = i;
/* 14 */     this.d = true;
/*    */   }
/*    */   
/*    */   public int a() {
/* 18 */     return this.b;
/*    */   }
/*    */   
/*    */   public void a(Object object) {
/* 22 */     this.c = object;
/*    */   }
/*    */   
/*    */   public Object b() {
/* 26 */     return this.c;
/*    */   }
/*    */   
/*    */   public int c() {
/* 30 */     return this.a;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 34 */     return this.d;
/*    */   }
/*    */   
/*    */   public void a(boolean flag) {
/* 38 */     this.d = flag;
/*    */   }
/*    */   
/*    */   static boolean a(WatchableObject watchableobject, boolean flag) {
/* 42 */     return watchableobject.d = flag;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WatchableObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */