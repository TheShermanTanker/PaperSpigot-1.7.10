/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CombatTracker
/*     */ {
/*  19 */   private final List a = new ArrayList();
/*     */   private final EntityLiving b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private boolean f;
/*     */   private boolean g;
/*     */   private String h;
/*     */   
/*     */   public CombatTracker(EntityLiving paramEntityLiving) {
/*  29 */     this.b = paramEntityLiving;
/*     */   }
/*     */   
/*     */   public void a() {
/*  33 */     j();
/*     */     
/*  35 */     if (this.b.h_()) {
/*  36 */       Block block = this.b.world.getType(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.boundingBox.b), MathHelper.floor(this.b.locZ));
/*     */       
/*  38 */       if (block == Blocks.LADDER) {
/*  39 */         this.h = "ladder";
/*  40 */       } else if (block == Blocks.VINE) {
/*  41 */         this.h = "vines";
/*     */       } 
/*  43 */     } else if (this.b.M()) {
/*  44 */       this.h = "water";
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(DamageSource paramDamageSource, float paramFloat1, float paramFloat2) {
/*  49 */     g();
/*  50 */     a();
/*     */     
/*  52 */     CombatEntry combatEntry = new CombatEntry(paramDamageSource, this.b.ticksLived, paramFloat1, paramFloat2, this.h, this.b.fallDistance);
/*     */     
/*  54 */     this.a.add(combatEntry);
/*  55 */     this.c = this.b.ticksLived;
/*  56 */     this.g = true;
/*     */     
/*  58 */     if (combatEntry.f() && !this.f && this.b.isAlive()) {
/*  59 */       this.f = true;
/*  60 */       this.d = this.b.ticksLived;
/*  61 */       this.e = this.d;
/*  62 */       this.b.bu();
/*     */     } 
/*     */   }
/*     */   public IChatBaseComponent b() {
/*     */     IChatBaseComponent iChatBaseComponent2;
/*  67 */     if (this.a.size() == 0) return new ChatMessage("death.attack.generic", new Object[] { this.b.getScoreboardDisplayName() });
/*     */     
/*  69 */     CombatEntry combatEntry1 = i();
/*  70 */     CombatEntry combatEntry2 = this.a.get(this.a.size() - 1);
/*     */     
/*  72 */     IChatBaseComponent iChatBaseComponent1 = combatEntry2.h();
/*  73 */     Entity entity = combatEntry2.a().getEntity();
/*     */     
/*  75 */     if (combatEntry1 != null && combatEntry2.a() == DamageSource.FALL) {
/*  76 */       IChatBaseComponent iChatBaseComponent = combatEntry1.h();
/*     */       
/*  78 */       if (combatEntry1.a() == DamageSource.FALL || combatEntry1.a() == DamageSource.OUT_OF_WORLD) {
/*  79 */         iChatBaseComponent2 = new ChatMessage("death.fell.accident." + a(combatEntry1), new Object[] { this.b.getScoreboardDisplayName() });
/*  80 */       } else if (iChatBaseComponent != null && (iChatBaseComponent1 == null || !iChatBaseComponent.equals(iChatBaseComponent1))) {
/*  81 */         Entity entity1 = combatEntry1.a().getEntity();
/*  82 */         ItemStack itemStack = (entity1 instanceof EntityLiving) ? ((EntityLiving)entity1).be() : null;
/*     */         
/*  84 */         if (itemStack != null && itemStack.hasName()) {
/*  85 */           iChatBaseComponent2 = new ChatMessage("death.fell.assist.item", new Object[] { this.b.getScoreboardDisplayName(), iChatBaseComponent, itemStack.E() });
/*     */         } else {
/*  87 */           iChatBaseComponent2 = new ChatMessage("death.fell.assist", new Object[] { this.b.getScoreboardDisplayName(), iChatBaseComponent });
/*     */         } 
/*  89 */       } else if (iChatBaseComponent1 != null) {
/*  90 */         ItemStack itemStack = (entity instanceof EntityLiving) ? ((EntityLiving)entity).be() : null;
/*  91 */         if (itemStack != null && itemStack.hasName()) {
/*  92 */           iChatBaseComponent2 = new ChatMessage("death.fell.finish.item", new Object[] { this.b.getScoreboardDisplayName(), iChatBaseComponent1, itemStack.E() });
/*     */         } else {
/*  94 */           iChatBaseComponent2 = new ChatMessage("death.fell.finish", new Object[] { this.b.getScoreboardDisplayName(), iChatBaseComponent1 });
/*     */         } 
/*     */       } else {
/*  97 */         iChatBaseComponent2 = new ChatMessage("death.fell.killer", new Object[] { this.b.getScoreboardDisplayName() });
/*     */       } 
/*     */     } else {
/* 100 */       iChatBaseComponent2 = combatEntry2.a().getLocalizedDeathMessage(this.b);
/*     */     } 
/*     */     
/* 103 */     return iChatBaseComponent2;
/*     */   }
/*     */   
/*     */   public EntityLiving c() {
/* 107 */     EntityLiving entityLiving = null;
/* 108 */     EntityHuman entityHuman = null;
/* 109 */     float f1 = 0.0F;
/* 110 */     float f2 = 0.0F;
/*     */     
/* 112 */     for (CombatEntry combatEntry : this.a) {
/* 113 */       if (combatEntry.a().getEntity() instanceof EntityHuman && (entityHuman == null || combatEntry.c() > f2)) {
/* 114 */         f2 = combatEntry.c();
/* 115 */         entityHuman = (EntityHuman)combatEntry.a().getEntity();
/*     */       } 
/*     */       
/* 118 */       if (combatEntry.a().getEntity() instanceof EntityLiving && (entityLiving == null || combatEntry.c() > f1)) {
/* 119 */         f1 = combatEntry.c();
/* 120 */         entityLiving = (EntityLiving)combatEntry.a().getEntity();
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     if (entityHuman != null && f2 >= f1 / 3.0F) {
/* 125 */       return entityHuman;
/*     */     }
/* 127 */     return entityLiving;
/*     */   }
/*     */ 
/*     */   
/*     */   private CombatEntry i() {
/* 132 */     CombatEntry combatEntry1 = null;
/* 133 */     CombatEntry combatEntry2 = null;
/* 134 */     byte b1 = 0;
/* 135 */     float f = 0.0F;
/*     */     
/* 137 */     for (byte b2 = 0; b2 < this.a.size(); b2++) {
/* 138 */       CombatEntry combatEntry3 = this.a.get(b2);
/* 139 */       CombatEntry combatEntry4 = (b2 > 0) ? this.a.get(b2 - 1) : null;
/*     */       
/* 141 */       if ((combatEntry3.a() == DamageSource.FALL || combatEntry3.a() == DamageSource.OUT_OF_WORLD) && combatEntry3.i() > 0.0F && (combatEntry1 == null || combatEntry3.i() > f)) {
/* 142 */         if (b2 > 0) {
/* 143 */           combatEntry1 = combatEntry4;
/*     */         } else {
/* 145 */           combatEntry1 = combatEntry3;
/*     */         } 
/* 147 */         f = combatEntry3.i();
/*     */       } 
/*     */       
/* 150 */       if (combatEntry3.g() != null && (combatEntry2 == null || combatEntry3.c() > b1)) {
/* 151 */         combatEntry2 = combatEntry3;
/*     */       }
/*     */     } 
/*     */     
/* 155 */     if (f > 5.0F && combatEntry1 != null)
/* 156 */       return combatEntry1; 
/* 157 */     if (b1 > 5 && combatEntry2 != null) {
/* 158 */       return combatEntry2;
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String a(CombatEntry paramCombatEntry) {
/* 165 */     return (paramCombatEntry.g() == null) ? "generic" : paramCombatEntry.g();
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
/*     */   private void j() {
/* 188 */     this.h = null;
/*     */   }
/*     */   
/*     */   public void g() {
/* 192 */     byte b = this.f ? 300 : 100;
/*     */     
/* 194 */     if (this.g && (!this.b.isAlive() || this.b.ticksLived - this.c > b)) {
/* 195 */       boolean bool = this.f;
/* 196 */       this.g = false;
/* 197 */       this.f = false;
/* 198 */       this.e = this.b.ticksLived;
/*     */       
/* 200 */       if (bool) {
/* 201 */         this.b.bv();
/*     */       }
/* 203 */       this.a.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CombatTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */