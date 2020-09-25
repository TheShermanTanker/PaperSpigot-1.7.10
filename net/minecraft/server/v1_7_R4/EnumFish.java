/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EnumFish
/*     */ {
/* 104 */   COD(0, "cod", 2, 0.1F, 5, 0.6F),
/* 105 */   SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
/* 106 */   CLOWNFISH(2, "clownfish", 1, 0.1F),
/* 107 */   PUFFERFISH(3, "pufferfish", 1, 0.1F);
/*     */   static {
/* 109 */     e = Maps.newHashMap();
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
/* 121 */     for (EnumFish enumFish : values())
/* 122 */       e.put(Integer.valueOf(enumFish.a()), enumFish); 
/*     */   }
/*     */   private boolean n = false; private static final Map e; private final int f; private final String g;
/*     */   
/*     */   EnumFish(int paramInt1, String paramString1, int paramInt2, float paramFloat1, int paramInt3, float paramFloat2) {
/* 127 */     this.f = paramInt1;
/* 128 */     this.g = paramString1;
/* 129 */     this.j = paramInt2;
/* 130 */     this.k = paramFloat1;
/* 131 */     this.l = paramInt3;
/* 132 */     this.m = paramFloat2;
/* 133 */     this.n = true;
/*     */   }
/*     */   private final int j; private final float k; private final int l; private final float m;
/*     */   EnumFish(int paramInt1, String paramString1, int paramInt2, float paramFloat) {
/* 137 */     this.f = paramInt1;
/* 138 */     this.g = paramString1;
/* 139 */     this.j = paramInt2;
/* 140 */     this.k = paramFloat;
/* 141 */     this.l = 0;
/* 142 */     this.m = 0.0F;
/* 143 */     this.n = false;
/*     */   }
/*     */   
/*     */   public int a() {
/* 147 */     return this.f;
/*     */   }
/*     */   
/*     */   public String b() {
/* 151 */     return this.g;
/*     */   }
/*     */   
/*     */   public int c() {
/* 155 */     return this.j;
/*     */   }
/*     */   
/*     */   public float d() {
/* 159 */     return this.k;
/*     */   }
/*     */   
/*     */   public int e() {
/* 163 */     return this.l;
/*     */   }
/*     */   
/*     */   public float f() {
/* 167 */     return this.m;
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
/*     */   public boolean i() {
/* 187 */     return this.n;
/*     */   }
/*     */   
/*     */   public static EnumFish a(int paramInt) {
/* 191 */     EnumFish enumFish = (EnumFish)e.get(Integer.valueOf(paramInt));
/*     */     
/* 193 */     if (enumFish == null) {
/* 194 */       return COD;
/*     */     }
/* 196 */     return enumFish;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFish a(ItemStack paramItemStack) {
/* 201 */     if (paramItemStack.getItem() instanceof ItemFish) {
/* 202 */       return a(paramItemStack.getData());
/*     */     }
/* 204 */     return COD;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumFish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */