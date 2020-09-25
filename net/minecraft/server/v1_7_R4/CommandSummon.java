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
/*     */ public class CommandSummon
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  15 */     return "summon";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  20 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  25 */     return "commands.summon.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  30 */     if (paramArrayOfString.length >= 1) {
/*     */       
/*  32 */       String str = paramArrayOfString[0];
/*  33 */       double d1 = (paramICommandListener.getChunkCoordinates()).x + 0.5D;
/*  34 */       double d2 = (paramICommandListener.getChunkCoordinates()).y;
/*  35 */       double d3 = (paramICommandListener.getChunkCoordinates()).z + 0.5D;
/*     */       
/*  37 */       if (paramArrayOfString.length >= 4) {
/*  38 */         d1 = a(paramICommandListener, d1, paramArrayOfString[1]);
/*  39 */         d2 = a(paramICommandListener, d2, paramArrayOfString[2]);
/*  40 */         d3 = a(paramICommandListener, d3, paramArrayOfString[3]);
/*     */       } 
/*     */       
/*  43 */       World world = paramICommandListener.getWorld();
/*  44 */       if (!world.isLoaded((int)d1, (int)d2, (int)d3)) {
/*  45 */         a(paramICommandListener, this, "commands.summon.outOfWorld", new Object[0]);
/*     */         
/*     */         return;
/*     */       } 
/*  49 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*  50 */       boolean bool = false;
/*  51 */       if (paramArrayOfString.length >= 5) {
/*  52 */         IChatBaseComponent iChatBaseComponent = a(paramICommandListener, paramArrayOfString, 4);
/*     */         try {
/*  54 */           NBTBase nBTBase = MojangsonParser.parse(iChatBaseComponent.c());
/*  55 */           if (nBTBase instanceof NBTTagCompound) {
/*  56 */             nBTTagCompound = (NBTTagCompound)nBTBase;
/*  57 */             bool = true;
/*     */           } else {
/*  59 */             a(paramICommandListener, this, "commands.summon.tagError", new Object[] { "Not a valid tag" });
/*     */             return;
/*     */           } 
/*  62 */         } catch (MojangsonParseException mojangsonParseException) {
/*  63 */           a(paramICommandListener, this, "commands.summon.tagError", new Object[] { mojangsonParseException.getMessage() });
/*     */           return;
/*     */         } 
/*     */       } 
/*  67 */       nBTTagCompound.setString("id", str);
/*     */       
/*  69 */       Entity entity = EntityTypes.a(nBTTagCompound, world);
/*  70 */       if (entity != null) {
/*  71 */         entity.setPositionRotation(d1, d2, d3, entity.yaw, entity.pitch);
/*  72 */         if (!bool)
/*     */         {
/*  74 */           if (entity instanceof EntityInsentient) {
/*  75 */             ((EntityInsentient)entity).prepare(null);
/*     */           }
/*     */         }
/*  78 */         world.addEntity(entity);
/*     */ 
/*     */         
/*  81 */         Entity entity1 = entity;
/*  82 */         NBTTagCompound nBTTagCompound1 = nBTTagCompound;
/*  83 */         while (entity1 != null && nBTTagCompound1.hasKeyOfType("Riding", 10)) {
/*  84 */           Entity entity2 = EntityTypes.a(nBTTagCompound1.getCompound("Riding"), world);
/*  85 */           if (entity2 != null) {
/*  86 */             entity2.setPositionRotation(d1, d2, d3, entity2.yaw, entity2.pitch);
/*  87 */             world.addEntity(entity2);
/*  88 */             entity1.mount(entity2);
/*     */           } 
/*  90 */           entity1 = entity2;
/*  91 */           nBTTagCompound1 = nBTTagCompound1.getCompound("Riding");
/*     */         } 
/*  93 */         a(paramICommandListener, this, "commands.summon.success", new Object[0]);
/*     */         return;
/*     */       } 
/*  96 */       a(paramICommandListener, this, "commands.summon.failed", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 101 */     throw new ExceptionUsage("commands.summon.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 106 */     if (paramArrayOfString.length == 1) {
/* 107 */       return a(paramArrayOfString, d());
/*     */     }
/*     */     
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/* 114 */     return (String[])EntityTypes.b().toArray((Object[])new String[0]);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSummon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */