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
/*     */ public class CommandSetBlock
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  17 */     return "setblock";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  22 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  27 */     return "commands.setblock.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  32 */     if (paramArrayOfString.length >= 4) {
/*     */       
/*  34 */       int i = (paramICommandListener.getChunkCoordinates()).x;
/*  35 */       int j = (paramICommandListener.getChunkCoordinates()).y;
/*  36 */       int k = (paramICommandListener.getChunkCoordinates()).z;
/*  37 */       i = MathHelper.floor(a(paramICommandListener, i, paramArrayOfString[0]));
/*  38 */       j = MathHelper.floor(a(paramICommandListener, j, paramArrayOfString[1]));
/*  39 */       k = MathHelper.floor(a(paramICommandListener, k, paramArrayOfString[2]));
/*     */       
/*  41 */       Block block = CommandAbstract.g(paramICommandListener, paramArrayOfString[3]);
/*     */       
/*  43 */       int m = 0;
/*  44 */       if (paramArrayOfString.length >= 5) {
/*  45 */         m = a(paramICommandListener, paramArrayOfString[4], 0, 15);
/*     */       }
/*     */       
/*  48 */       World world = paramICommandListener.getWorld();
/*  49 */       if (!world.isLoaded(i, j, k)) {
/*  50 */         throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */       }
/*     */       
/*  53 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*  54 */       boolean bool = false;
/*  55 */       if (paramArrayOfString.length >= 7 && block.isTileEntity()) {
/*  56 */         String str = a(paramICommandListener, paramArrayOfString, 6).c();
/*     */         try {
/*  58 */           NBTBase nBTBase = MojangsonParser.parse(str);
/*  59 */           if (nBTBase instanceof NBTTagCompound) {
/*  60 */             nBTTagCompound = (NBTTagCompound)nBTBase;
/*  61 */             bool = true;
/*     */           } else {
/*  63 */             throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
/*     */           } 
/*  65 */         } catch (MojangsonParseException mojangsonParseException) {
/*  66 */           throw new CommandException("commands.setblock.tagError", new Object[] { mojangsonParseException.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/*  70 */       if (paramArrayOfString.length >= 6) {
/*  71 */         if (paramArrayOfString[5].equals("destroy")) {
/*  72 */           world.setAir(i, j, k, true);
/*  73 */         } else if (paramArrayOfString[5].equals("keep") && 
/*  74 */           !world.isEmpty(i, j, k)) {
/*  75 */           throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  80 */       if (!world.setTypeAndData(i, j, k, block, m, 3)) {
/*  81 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       }
/*     */       
/*  84 */       if (bool) {
/*  85 */         TileEntity tileEntity = world.getTileEntity(i, j, k);
/*  86 */         if (tileEntity != null) {
/*     */           
/*  88 */           nBTTagCompound.setInt("x", i);
/*  89 */           nBTTagCompound.setInt("y", j);
/*  90 */           nBTTagCompound.setInt("z", k);
/*     */           
/*  92 */           tileEntity.a(nBTTagCompound);
/*     */         } 
/*     */       } 
/*  95 */       a(paramICommandListener, this, "commands.setblock.success", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     throw new ExceptionUsage("commands.setblock.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 104 */     if (paramArrayOfString.length == 4) {
/* 105 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/*     */     }
/* 107 */     if (paramArrayOfString.length == 6) {
/* 108 */       return a(paramArrayOfString, new String[] { "replace", "destroy", "keep" });
/*     */     }
/*     */     
/* 111 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSetBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */