/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ 
/*     */ public class AttributeModifiable implements AttributeInstance {
/*     */   private final AttributeMapBase a;
/*  10 */   private final Map c = Maps.newHashMap(); private final IAttribute b;
/*  11 */   private final Map d = Maps.newHashMap();
/*  12 */   private final Map e = Maps.newHashMap();
/*     */   private double f;
/*     */   private boolean g = true;
/*     */   private double h;
/*     */   
/*     */   public AttributeModifiable(AttributeMapBase paramAttributeMapBase, IAttribute paramIAttribute) {
/*  18 */     this.a = paramAttributeMapBase;
/*  19 */     this.b = paramIAttribute;
/*  20 */     this.f = paramIAttribute.b();
/*     */     
/*  22 */     for (byte b = 0; b < 3; b++) {
/*  23 */       this.c.put(Integer.valueOf(b), new HashSet());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IAttribute getAttribute() {
/*  29 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public double b() {
/*  34 */     return this.f;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(double paramDouble) {
/*  39 */     if (paramDouble == b())
/*  40 */       return;  this.f = paramDouble;
/*  41 */     f();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection a(int paramInt) {
/*  46 */     return (Collection)this.c.get(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection c() {
/*  51 */     HashSet hashSet = new HashSet();
/*     */     
/*  53 */     for (byte b = 0; b < 3; b++) {
/*  54 */       hashSet.addAll(a(b));
/*     */     }
/*     */     
/*  57 */     return hashSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeModifier a(UUID paramUUID) {
/*  62 */     return (AttributeModifier)this.e.get(paramUUID);
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
/*     */   public void a(AttributeModifier paramAttributeModifier) {
/*  74 */     if (a(paramAttributeModifier.a()) != null) throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*     */     
/*  76 */     Set<AttributeModifier> set = (Set)this.d.get(paramAttributeModifier.b());
/*     */     
/*  78 */     if (set == null) {
/*  79 */       set = new HashSet();
/*  80 */       this.d.put(paramAttributeModifier.b(), set);
/*     */     } 
/*     */     
/*  83 */     ((Set<AttributeModifier>)this.c.get(Integer.valueOf(paramAttributeModifier.c()))).add(paramAttributeModifier);
/*  84 */     set.add(paramAttributeModifier);
/*  85 */     this.e.put(paramAttributeModifier.a(), paramAttributeModifier);
/*     */     
/*  87 */     f();
/*     */   }
/*     */   
/*     */   private void f() {
/*  91 */     this.g = true;
/*  92 */     this.a.a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(AttributeModifier paramAttributeModifier) {
/*  97 */     for (byte b = 0; b < 3; b++) {
/*  98 */       Set set1 = (Set)this.c.get(Integer.valueOf(b));
/*  99 */       set1.remove(paramAttributeModifier);
/*     */     } 
/*     */     
/* 102 */     Set set = (Set)this.d.get(paramAttributeModifier.b());
/*     */     
/* 104 */     if (set != null) {
/* 105 */       set.remove(paramAttributeModifier);
/*     */       
/* 107 */       if (set.isEmpty()) {
/* 108 */         this.d.remove(paramAttributeModifier.b());
/*     */       }
/*     */     } 
/*     */     
/* 112 */     this.e.remove(paramAttributeModifier.a());
/* 113 */     f();
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
/*     */   public double getValue() {
/* 153 */     if (this.g) {
/* 154 */       this.h = g();
/* 155 */       this.g = false;
/*     */     } 
/*     */     
/* 158 */     return this.h;
/*     */   }
/*     */   
/*     */   private double g() {
/* 162 */     double d1 = b();
/*     */     
/* 164 */     for (AttributeModifier attributeModifier : a(0)) {
/* 165 */       d1 += attributeModifier.d();
/*     */     }
/*     */     
/* 168 */     double d2 = d1;
/*     */     
/* 170 */     for (AttributeModifier attributeModifier : a(1)) {
/* 171 */       d2 += d1 * attributeModifier.d();
/*     */     }
/*     */     
/* 174 */     for (AttributeModifier attributeModifier : a(2)) {
/* 175 */       d2 *= 1.0D + attributeModifier.d();
/*     */     }
/*     */     
/* 178 */     return this.b.a(d2);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AttributeModifiable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */