/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public abstract class PersistentBase
/*    */ {
/*    */   public final String id;
/*    */   private boolean a;
/*    */   
/*    */   public PersistentBase(String paramString) {
/* 10 */     this.id = paramString;
/*    */   }
/*    */   
/*    */   public abstract void a(NBTTagCompound paramNBTTagCompound);
/*    */   
/*    */   public abstract void b(NBTTagCompound paramNBTTagCompound);
/*    */   
/*    */   public void c() {
/* 18 */     a(true);
/*    */   }
/*    */   
/*    */   public void a(boolean paramBoolean) {
/* 22 */     this.a = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 26 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PersistentBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */