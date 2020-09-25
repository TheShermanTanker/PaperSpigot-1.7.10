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
/*     */ public class DemoPlayerInteractManager
/*     */   extends PlayerInteractManager
/*     */ {
/*     */   private boolean c;
/*     */   private boolean d;
/*     */   private int e;
/*     */   private int f;
/*     */   
/*     */   public DemoPlayerInteractManager(World paramWorld) {
/*  20 */     super(paramWorld);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  25 */     super.a();
/*  26 */     this.f++;
/*     */     
/*  28 */     long l1 = this.world.getTime();
/*  29 */     long l2 = l1 / 24000L + 1L;
/*     */     
/*  31 */     if (!this.c && this.f > 20) {
/*  32 */       this.c = true;
/*  33 */       this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 0.0F));
/*     */     } 
/*     */     
/*  36 */     this.d = (l1 > 120500L);
/*  37 */     if (this.d) {
/*  38 */       this.e++;
/*     */     }
/*     */     
/*  41 */     if (l1 % 24000L == 500L) {
/*  42 */       if (l2 <= 6L) {
/*  43 */         this.player.sendMessage(new ChatMessage("demo.day." + l2, new Object[0]));
/*     */       }
/*  45 */     } else if (l2 == 1L) {
/*  46 */       if (l1 == 100L) {
/*  47 */         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 101.0F));
/*  48 */       } else if (l1 == 175L) {
/*  49 */         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 102.0F));
/*  50 */       } else if (l1 == 250L) {
/*  51 */         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 103.0F));
/*     */       } 
/*  53 */     } else if (l2 == 5L && 
/*  54 */       l1 % 24000L == 22000L) {
/*  55 */       this.player.sendMessage(new ChatMessage("demo.day.warning", new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void e() {
/*  61 */     if (this.e > 100) {
/*  62 */       this.player.sendMessage(new ChatMessage("demo.reminder", new Object[0]));
/*  63 */       this.e = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dig(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  69 */     if (this.d) {
/*  70 */       e();
/*     */       return;
/*     */     } 
/*  73 */     super.dig(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3) {
/*  78 */     if (this.d) {
/*     */       return;
/*     */     }
/*  81 */     super.a(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean breakBlock(int paramInt1, int paramInt2, int paramInt3) {
/*  86 */     if (this.d) {
/*  87 */       return false;
/*     */     }
/*  89 */     return super.breakBlock(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useItem(EntityHuman paramEntityHuman, World paramWorld, ItemStack paramItemStack) {
/*  94 */     if (this.d) {
/*  95 */       e();
/*  96 */       return false;
/*     */     } 
/*  98 */     return super.useItem(paramEntityHuman, paramWorld, paramItemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(EntityHuman paramEntityHuman, World paramWorld, ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 103 */     if (this.d) {
/* 104 */       e();
/* 105 */       return false;
/*     */     } 
/* 107 */     return super.interact(paramEntityHuman, paramWorld, paramItemStack, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DemoPlayerInteractManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */