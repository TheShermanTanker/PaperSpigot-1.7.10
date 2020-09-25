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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTestForBlock
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  24 */     return "testforblock";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  34 */     return "commands.testforblock.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  39 */     if (paramArrayOfString.length >= 4) {
/*  40 */       int i = (paramICommandListener.getChunkCoordinates()).x;
/*  41 */       int j = (paramICommandListener.getChunkCoordinates()).y;
/*  42 */       int k = (paramICommandListener.getChunkCoordinates()).z;
/*  43 */       i = MathHelper.floor(a(paramICommandListener, i, paramArrayOfString[0]));
/*  44 */       j = MathHelper.floor(a(paramICommandListener, j, paramArrayOfString[1]));
/*  45 */       k = MathHelper.floor(a(paramICommandListener, k, paramArrayOfString[2]));
/*     */       
/*  47 */       Block block1 = Block.b(paramArrayOfString[3]);
/*  48 */       if (block1 == null) {
/*  49 */         throw new ExceptionInvalidNumber("commands.setblock.notFound", new Object[] { paramArrayOfString[3] });
/*     */       }
/*     */       
/*  52 */       int m = -1;
/*  53 */       if (paramArrayOfString.length >= 5) {
/*  54 */         m = a(paramICommandListener, paramArrayOfString[4], -1, 15);
/*     */       }
/*     */       
/*  57 */       World world = paramICommandListener.getWorld();
/*  58 */       if (!world.isLoaded(i, j, k)) {
/*  59 */         throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*     */       }
/*     */       
/*  62 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*  63 */       boolean bool = false;
/*  64 */       if (paramArrayOfString.length >= 6 && block1.isTileEntity()) {
/*  65 */         String str = a(paramICommandListener, paramArrayOfString, 5).c();
/*     */         try {
/*  67 */           NBTBase nBTBase = MojangsonParser.parse(str);
/*  68 */           if (nBTBase instanceof NBTTagCompound) {
/*  69 */             nBTTagCompound = (NBTTagCompound)nBTBase;
/*  70 */             bool = true;
/*     */           } else {
/*  72 */             throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
/*     */           } 
/*  74 */         } catch (MojangsonParseException mojangsonParseException) {
/*  75 */           throw new CommandException("commands.setblock.tagError", new Object[] { mojangsonParseException.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/*  79 */       Block block2 = world.getType(i, j, k);
/*  80 */       if (block2 != block1) {
/*  81 */         throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), block2.getName(), block1.getName() });
/*     */       }
/*     */       
/*  84 */       if (m > -1) {
/*  85 */         int n = world.getData(i, j, k);
/*  86 */         if (n != m) {
/*  87 */           throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(n), Integer.valueOf(m) });
/*     */         }
/*     */       } 
/*     */       
/*  91 */       if (bool) {
/*  92 */         TileEntity tileEntity = world.getTileEntity(i, j, k);
/*  93 */         if (tileEntity == null) {
/*  94 */           throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
/*     */         }
/*  96 */         NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
/*  97 */         tileEntity.b(nBTTagCompound1);
/*     */         
/*  99 */         if (!a(nBTTagCompound, nBTTagCompound1)) {
/* 100 */           throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
/*     */         }
/*     */       } 
/*     */       
/* 104 */       paramICommandListener.sendMessage(new ChatMessage("commands.testforblock.success", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 109 */     throw new ExceptionUsage("commands.testforblock.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(NBTBase paramNBTBase1, NBTBase paramNBTBase2) {
/* 114 */     if (paramNBTBase1 == paramNBTBase2) return true; 
/* 115 */     if (paramNBTBase1 == null) return true; 
/* 116 */     if (paramNBTBase2 == null) return false; 
/* 117 */     if (!paramNBTBase1.getClass().equals(paramNBTBase2.getClass())) return false;
/*     */     
/* 119 */     if (paramNBTBase1 instanceof NBTTagCompound) {
/* 120 */       NBTTagCompound nBTTagCompound1 = (NBTTagCompound)paramNBTBase1;
/* 121 */       NBTTagCompound nBTTagCompound2 = (NBTTagCompound)paramNBTBase2;
/*     */       
/* 123 */       for (String str : nBTTagCompound1.c()) {
/* 124 */         NBTBase nBTBase = nBTTagCompound1.get(str);
/* 125 */         if (!a(nBTBase, nBTTagCompound2.get(str))) return false;
/*     */       
/*     */       } 
/* 128 */       return true;
/*     */     } 
/* 130 */     return paramNBTBase1.equals(paramNBTBase2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 136 */     if (paramArrayOfString.length == 4) {
/* 137 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/*     */     }
/*     */     
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandTestForBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */