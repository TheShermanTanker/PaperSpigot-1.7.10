/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class RegistryDefault extends RegistrySimple {
/*    */   private final Object a;
/*    */   
/*    */   public RegistryDefault(Object paramObject) {
/*  7 */     this.a = paramObject;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object paramObject) {
/* 12 */     Object object = super.get(paramObject);
/* 13 */     return (object == null) ? this.a : object;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegistryDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */