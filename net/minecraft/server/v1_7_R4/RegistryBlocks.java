/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class RegistryBlocks
/*    */   extends RegistryMaterials
/*    */ {
/*    */   private final String d;
/*    */   private Object e;
/*    */   
/*    */   public RegistryBlocks(String paramString) {
/* 10 */     this.d = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(int paramInt, String paramString, Object paramObject) {
/* 15 */     if (this.d.equals(paramString)) {
/* 16 */       this.e = paramObject;
/*    */     }
/*    */     
/* 19 */     super.a(paramInt, paramString, paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(String paramString) {
/* 24 */     Object object = super.get(paramString);
/* 25 */     return (object == null) ? this.e : object;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object a(int paramInt) {
/* 30 */     Object object = super.a(paramInt);
/* 31 */     return (object == null) ? this.e : object;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegistryBlocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */