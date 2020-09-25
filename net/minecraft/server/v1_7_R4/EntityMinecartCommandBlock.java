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
/*     */ public class EntityMinecartCommandBlock
/*     */   extends EntityMinecartAbstract
/*     */ {
/*  22 */   private final CommandBlockListenerAbstract a = new EntityMinecartCommandBlockListener(this);
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
/*  51 */   private int b = 0;
/*     */   
/*     */   public EntityMinecartCommandBlock(World paramWorld) {
/*  54 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartCommandBlock(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  58 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  63 */     super.c();
/*  64 */     getDataWatcher().a(23, "");
/*  65 */     getDataWatcher().a(24, "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/*  70 */     super.a(paramNBTTagCompound);
/*  71 */     this.a.b(paramNBTTagCompound);
/*  72 */     getDataWatcher().watch(23, getCommandBlock().getCommand());
/*  73 */     getDataWatcher().watch(24, ChatSerializer.a(getCommandBlock().h()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/*  78 */     super.b(paramNBTTagCompound);
/*  79 */     this.a.a(paramNBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public int m() {
/*  84 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block o() {
/*  89 */     return Blocks.COMMAND;
/*     */   }
/*     */   
/*     */   public CommandBlockListenerAbstract getCommandBlock() {
/*  93 */     return this.a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/*  98 */     if (paramBoolean && 
/*  99 */       this.ticksLived - this.b >= 4) {
/* 100 */       getCommandBlock().a(this.world);
/* 101 */       this.b = this.ticksLived;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(EntityHuman paramEntityHuman) {
/* 108 */     if (this.world.isStatic) {
/* 109 */       paramEntityHuman.a(getCommandBlock());
/*     */     }
/*     */     
/* 112 */     return super.c(paramEntityHuman);
/*     */   }
/*     */ 
/*     */   
/*     */   public void i(int paramInt) {
/* 117 */     super.i(paramInt);
/*     */     
/* 119 */     if (paramInt == 24) {
/*     */       try {
/* 121 */         this.a.b(ChatSerializer.a(getDataWatcher().getString(24)));
/* 122 */       } catch (Throwable throwable) {}
/* 123 */     } else if (paramInt == 23) {
/* 124 */       this.a.setCommand(getDataWatcher().getString(23));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartCommandBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */