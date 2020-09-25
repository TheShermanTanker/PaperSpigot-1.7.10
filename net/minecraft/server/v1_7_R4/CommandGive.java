/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandGive
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 16 */     return "give";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.give.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length < 2) {
/* 32 */       throw new ExceptionUsage("commands.give.usage", new Object[0]);
/*    */     }
/*    */     
/* 35 */     EntityPlayer entityPlayer = d(paramICommandListener, paramArrayOfString[0]);
/*    */     
/* 37 */     Item item = f(paramICommandListener, paramArrayOfString[1]);
/* 38 */     int i = 1;
/* 39 */     int j = 0;
/*    */     
/* 41 */     if (paramArrayOfString.length >= 3) {
/* 42 */       i = a(paramICommandListener, paramArrayOfString[2], 1, 64);
/*    */     }
/*    */     
/* 45 */     if (paramArrayOfString.length >= 4) {
/* 46 */       j = a(paramICommandListener, paramArrayOfString[3]);
/*    */     }
/*    */     
/* 49 */     ItemStack itemStack = new ItemStack(item, i, j);
/*    */     
/* 51 */     if (paramArrayOfString.length >= 5) {
/* 52 */       String str = a(paramICommandListener, paramArrayOfString, 4).c();
/*    */       try {
/* 54 */         NBTBase nBTBase = MojangsonParser.parse(str);
/* 55 */         if (nBTBase instanceof NBTTagCompound) {
/* 56 */           itemStack.setTag((NBTTagCompound)nBTBase);
/*    */         } else {
/* 58 */           a(paramICommandListener, this, "commands.give.tagError", new Object[] { "Not a valid tag" });
/*    */           return;
/*    */         } 
/* 61 */       } catch (MojangsonParseException mojangsonParseException) {
/* 62 */         a(paramICommandListener, this, "commands.give.tagError", new Object[] { mojangsonParseException.getMessage() });
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/* 67 */     EntityItem entityItem = entityPlayer.drop(itemStack, false);
/* 68 */     entityItem.pickupDelay = 0;
/* 69 */     entityItem.a(entityPlayer.getName());
/*    */     
/* 71 */     a(paramICommandListener, this, "commands.give.success", new Object[] { itemStack.E(), Integer.valueOf(i), entityPlayer.getName() });
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 76 */     if (paramArrayOfString.length == 1) {
/* 77 */       return a(paramArrayOfString, d());
/*    */     }
/* 79 */     if (paramArrayOfString.length == 2) {
/* 80 */       return a(paramArrayOfString, Item.REGISTRY.keySet());
/*    */     }
/*    */     
/* 83 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 87 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 92 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandGive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */