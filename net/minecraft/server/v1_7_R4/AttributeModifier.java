/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeModifier
/*    */ {
/*    */   private final double a;
/*    */   private final int b;
/*    */   private final String c;
/*    */   private final UUID d;
/*    */   private boolean e = true;
/*    */   
/*    */   public AttributeModifier(String paramString, double paramDouble, int paramInt) {
/* 20 */     this(UUID.randomUUID(), paramString, paramDouble, paramInt);
/*    */   }
/*    */   
/*    */   public AttributeModifier(UUID paramUUID, String paramString, double paramDouble, int paramInt) {
/* 24 */     this.d = paramUUID;
/* 25 */     this.c = paramString;
/* 26 */     this.a = paramDouble;
/* 27 */     this.b = paramInt;
/*    */     
/* 29 */     Validate.notEmpty(paramString, "Modifier name cannot be empty", new Object[0]);
/* 30 */     Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(paramInt), "Invalid operation", new Object[0]);
/*    */   }
/*    */   
/*    */   public UUID a() {
/* 34 */     return this.d;
/*    */   }
/*    */   
/*    */   public String b() {
/* 38 */     return this.c;
/*    */   }
/*    */   
/*    */   public int c() {
/* 42 */     return this.b;
/*    */   }
/*    */   
/*    */   public double d() {
/* 46 */     return this.a;
/*    */   }
/*    */   
/*    */   public boolean e() {
/* 50 */     return this.e;
/*    */   }
/*    */   
/*    */   public AttributeModifier a(boolean paramBoolean) {
/* 54 */     this.e = paramBoolean;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 60 */     if (this == paramObject) return true; 
/* 61 */     if (paramObject == null || getClass() != paramObject.getClass()) return false;
/*    */     
/* 63 */     AttributeModifier attributeModifier = (AttributeModifier)paramObject;
/*    */     
/* 65 */     if ((this.d != null) ? !this.d.equals(attributeModifier.d) : (attributeModifier.d != null)) return false;
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 72 */     return (this.d != null) ? this.d.hashCode() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name='" + this.c + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AttributeModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */