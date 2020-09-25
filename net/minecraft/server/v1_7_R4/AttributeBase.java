/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public abstract class AttributeBase implements IAttribute {
/*    */   private final String a;
/*    */   private final double b;
/*    */   private boolean c;
/*    */   
/*    */   protected AttributeBase(String paramString, double paramDouble) {
/*  9 */     this.a = paramString;
/* 10 */     this.b = paramDouble;
/*    */     
/* 12 */     if (paramString == null) throw new IllegalArgumentException("Name cannot be null!");
/*    */   
/*    */   }
/*    */   
/*    */   public String getName() {
/* 17 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public double b() {
/* 22 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 27 */     return this.c;
/*    */   }
/*    */   
/*    */   public AttributeBase a(boolean paramBoolean) {
/* 31 */     this.c = paramBoolean;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 37 */     return this.a.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AttributeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */