/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
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
/*     */ public class CommandEffect
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  20 */     return "effect";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  25 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  30 */     return "commands.effect.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  35 */     if (paramArrayOfString.length >= 2) {
/*  36 */       EntityPlayer entityPlayer = d(paramICommandListener, paramArrayOfString[0]);
/*     */       
/*  38 */       if (paramArrayOfString[1].equals("clear")) {
/*  39 */         if (entityPlayer.getEffects().isEmpty()) {
/*  40 */           throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entityPlayer.getName() });
/*     */         }
/*  42 */         entityPlayer.removeAllEffects();
/*  43 */         a(paramICommandListener, this, "commands.effect.success.removed.all", new Object[] { entityPlayer.getName() });
/*     */       } else {
/*     */         
/*  46 */         int i = a(paramICommandListener, paramArrayOfString[1], 1);
/*  47 */         int j = 600;
/*  48 */         int k = 30;
/*  49 */         int m = 0;
/*     */         
/*  51 */         if (i < 0 || i >= MobEffectList.byId.length || MobEffectList.byId[i] == null) {
/*  52 */           throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[] { Integer.valueOf(i) });
/*     */         }
/*     */         
/*  55 */         if (paramArrayOfString.length >= 3) {
/*  56 */           k = a(paramICommandListener, paramArrayOfString[2], 0, 1000000);
/*  57 */           if (MobEffectList.byId[i].isInstant()) {
/*  58 */             j = k;
/*     */           } else {
/*  60 */             j = k * 20;
/*     */           } 
/*  62 */         } else if (MobEffectList.byId[i].isInstant()) {
/*  63 */           j = 1;
/*     */         } 
/*     */         
/*  66 */         if (paramArrayOfString.length >= 4) {
/*  67 */           m = a(paramICommandListener, paramArrayOfString[3], 0, 255);
/*     */         }
/*     */         
/*  70 */         if (k == 0) {
/*  71 */           if (entityPlayer.hasEffect(i)) {
/*  72 */             entityPlayer.removeEffect(i);
/*  73 */             a(paramICommandListener, this, "commands.effect.success.removed", new Object[] { new ChatMessage(MobEffectList.byId[i].a(), new Object[0]), entityPlayer.getName() });
/*     */           } else {
/*  75 */             throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatMessage(MobEffectList.byId[i].a(), new Object[0]), entityPlayer.getName() });
/*     */           } 
/*     */         } else {
/*  78 */           MobEffect mobEffect = new MobEffect(i, j, m);
/*  79 */           entityPlayer.addEffect(mobEffect);
/*  80 */           a(paramICommandListener, this, "commands.effect.success", new Object[] { new ChatMessage(mobEffect.f(), new Object[0]), Integer.valueOf(i), Integer.valueOf(m), entityPlayer.getName(), Integer.valueOf(k) });
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  87 */     throw new ExceptionUsage("commands.effect.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  92 */     if (paramArrayOfString.length == 1) {
/*  93 */       return a(paramArrayOfString, d());
/*     */     }
/*     */     
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/* 100 */     return MinecraftServer.getServer().getPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 105 */     return (paramInt == 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */