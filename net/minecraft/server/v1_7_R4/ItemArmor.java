/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemArmor
/*     */   extends Item
/*     */ {
/*  22 */   private static final int[] m = new int[] { 11, 16, 15, 13 };
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final String[] n = new String[] { "leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay" };
/*     */ 
/*     */ 
/*     */   
/*  30 */   public static final String[] a = new String[] { "empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots" };
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static final IDispenseBehavior o = new DispenseBehaviorArmor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int b;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EnumArmorMaterial p;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemArmor(EnumArmorMaterial paramEnumArmorMaterial, int paramInt1, int paramInt2) {
/* 119 */     this.p = paramEnumArmorMaterial;
/* 120 */     this.b = paramInt2;
/* 121 */     this.d = paramInt1;
/* 122 */     this.c = paramEnumArmorMaterial.b(paramInt2);
/* 123 */     setMaxDurability(paramEnumArmorMaterial.a(paramInt2));
/* 124 */     this.maxStackSize = 1;
/* 125 */     a(CreativeModeTab.j);
/* 126 */     BlockDispenser.a.a(this, o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int c() {
/* 147 */     return this.p.a();
/*     */   }
/*     */   
/*     */   public EnumArmorMaterial m_() {
/* 151 */     return this.p;
/*     */   }
/*     */   
/*     */   public boolean c_(ItemStack paramItemStack) {
/* 155 */     if (this.p != EnumArmorMaterial.CLOTH) return false; 
/* 156 */     if (!paramItemStack.hasTag()) return false; 
/* 157 */     if (!paramItemStack.getTag().hasKeyOfType("display", 10)) return false; 
/* 158 */     if (!paramItemStack.getTag().getCompound("display").hasKeyOfType("color", 3)) return false;
/*     */     
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   public int b(ItemStack paramItemStack) {
/* 164 */     if (this.p != EnumArmorMaterial.CLOTH) return -1;
/*     */     
/* 166 */     NBTTagCompound nBTTagCompound1 = paramItemStack.getTag();
/* 167 */     if (nBTTagCompound1 == null) return 10511680; 
/* 168 */     NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompound("display");
/* 169 */     if (nBTTagCompound2 == null) return 10511680;
/*     */     
/* 171 */     if (nBTTagCompound2.hasKeyOfType("color", 3)) {
/* 172 */       return nBTTagCompound2.getInt("color");
/*     */     }
/* 174 */     return 10511680;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(ItemStack paramItemStack) {
/* 187 */     if (this.p != EnumArmorMaterial.CLOTH)
/* 188 */       return;  NBTTagCompound nBTTagCompound1 = paramItemStack.getTag();
/* 189 */     if (nBTTagCompound1 == null)
/* 190 */       return;  NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompound("display");
/* 191 */     if (nBTTagCompound2.hasKey("color")) nBTTagCompound2.remove("color"); 
/*     */   }
/*     */   
/*     */   public void b(ItemStack paramItemStack, int paramInt) {
/* 195 */     if (this.p != EnumArmorMaterial.CLOTH) throw new UnsupportedOperationException("Can't dye non-leather!");
/*     */     
/* 197 */     NBTTagCompound nBTTagCompound1 = paramItemStack.getTag();
/*     */     
/* 199 */     if (nBTTagCompound1 == null) {
/* 200 */       nBTTagCompound1 = new NBTTagCompound();
/* 201 */       paramItemStack.setTag(nBTTagCompound1);
/*     */     } 
/*     */     
/* 204 */     NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompound("display");
/* 205 */     if (!nBTTagCompound1.hasKeyOfType("display", 10)) nBTTagCompound1.set("display", nBTTagCompound2); 
/* 206 */     nBTTagCompound2.setInt("color", paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2) {
/* 211 */     if (this.p.b() == paramItemStack2.getItem()) {
/* 212 */       return true;
/*     */     }
/* 214 */     return super.a(paramItemStack1, paramItemStack2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 230 */     int i = EntityInsentient.b(paramItemStack) - 1;
/* 231 */     ItemStack itemStack = paramEntityHuman.r(i);
/*     */     
/* 233 */     if (itemStack == null) {
/* 234 */       paramEntityHuman.setEquipment(i, paramItemStack.cloneItemStack());
/* 235 */       paramItemStack.count = 0;
/*     */     } 
/*     */     
/* 238 */     return paramItemStack;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */