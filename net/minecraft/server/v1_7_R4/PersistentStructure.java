/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PersistentStructure
/*    */   extends PersistentBase
/*    */ {
/*    */   private NBTTagCompound a;
/*    */   
/*    */   public PersistentStructure(String paramString) {
/* 12 */     super(paramString);
/* 13 */     this.a = new NBTTagCompound();
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 18 */     this.a = paramNBTTagCompound.getCompound("Features");
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 23 */     paramNBTTagCompound.set("Features", this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound, int paramInt1, int paramInt2) {
/* 31 */     this.a.set(b(paramInt1, paramInt2), paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   public static String b(int paramInt1, int paramInt2) {
/* 35 */     return "[" + paramInt1 + "," + paramInt2 + "]";
/*    */   }
/*    */   
/*    */   public NBTTagCompound a() {
/* 39 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PersistentStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */