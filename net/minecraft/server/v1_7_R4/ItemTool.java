/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.Multimap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemTool
/*    */   extends Item
/*    */ {
/*    */   private Set c;
/* 15 */   protected float a = 4.0F;
/*    */   
/*    */   private float d;
/*    */   protected EnumToolMaterial b;
/*    */   
/*    */   protected ItemTool(float paramFloat, EnumToolMaterial paramEnumToolMaterial, Set paramSet) {
/* 21 */     this.b = paramEnumToolMaterial;
/* 22 */     this.c = paramSet;
/* 23 */     this.maxStackSize = 1;
/* 24 */     setMaxDurability(paramEnumToolMaterial.a());
/* 25 */     this.a = paramEnumToolMaterial.b();
/* 26 */     this.d = paramFloat + paramEnumToolMaterial.c();
/* 27 */     a(CreativeModeTab.i);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock) {
/* 32 */     return this.c.contains(paramBlock) ? this.a : 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 37 */     paramItemStack.damage(2, paramEntityLiving2);
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
/* 44 */     if (paramBlock.f(paramWorld, paramInt1, paramInt2, paramInt3) != 0.0D) paramItemStack.damage(1, paramEntityLiving); 
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumToolMaterial i() {
/* 54 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public int c() {
/* 59 */     return this.b.e();
/*    */   }
/*    */   
/*    */   public String j() {
/* 63 */     return this.b.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2) {
/* 68 */     if (this.b.f() == paramItemStack2.getItem()) {
/* 69 */       return true;
/*    */     }
/* 71 */     return super.a(paramItemStack1, paramItemStack2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Multimap k() {
/* 76 */     Multimap multimap = super.k();
/*    */     
/* 78 */     multimap.put(GenericAttributes.e.getName(), new AttributeModifier(f, "Tool modifier", this.d, 0));
/*    */     
/* 80 */     return multimap;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */