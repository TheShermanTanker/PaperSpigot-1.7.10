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
/*    */ public class CommandEnchant
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 16 */     return "enchant";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.enchant.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length >= 2) {
/* 32 */       EntityPlayer entityPlayer = d(paramICommandListener, paramArrayOfString[0]);
/*    */       
/* 34 */       int i = a(paramICommandListener, paramArrayOfString[1], 0, Enchantment.byId.length - 1);
/* 35 */       int j = 1;
/*    */       
/* 37 */       ItemStack itemStack = entityPlayer.bF();
/* 38 */       if (itemStack == null) {
/* 39 */         throw new CommandException("commands.enchant.noItem", new Object[0]);
/*    */       }
/*    */       
/* 42 */       Enchantment enchantment = Enchantment.byId[i];
/* 43 */       if (enchantment == null) {
/* 44 */         throw new ExceptionInvalidNumber("commands.enchant.notFound", new Object[] { Integer.valueOf(i) });
/*    */       }
/*    */       
/* 47 */       if (!enchantment.canEnchant(itemStack)) {
/* 48 */         throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
/*    */       }
/*    */       
/* 51 */       if (paramArrayOfString.length >= 3) {
/* 52 */         j = a(paramICommandListener, paramArrayOfString[2], enchantment.getStartLevel(), enchantment.getMaxLevel());
/*    */       }
/*    */       
/* 55 */       if (itemStack.hasTag()) {
/* 56 */         NBTTagList nBTTagList = itemStack.getEnchantments();
/* 57 */         if (nBTTagList != null) {
/* 58 */           for (byte b = 0; b < nBTTagList.size(); b++) {
/* 59 */             short s = nBTTagList.get(b).getShort("id");
/*    */             
/* 61 */             if (Enchantment.byId[s] != null) {
/* 62 */               Enchantment enchantment1 = Enchantment.byId[s];
/* 63 */               if (!enchantment1.a(enchantment)) {
/* 64 */                 throw new CommandException("commands.enchant.cantCombine", new Object[] { enchantment.c(j), enchantment1.c(nBTTagList.get(b).getShort("lvl")) });
/*    */               }
/*    */             } 
/*    */           } 
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 72 */       itemStack.addEnchantment(enchantment, j);
/*    */       
/* 74 */       a(paramICommandListener, this, "commands.enchant.success", new Object[0]);
/*    */       
/*    */       return;
/*    */     } 
/* 78 */     throw new ExceptionUsage("commands.enchant.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 83 */     if (paramArrayOfString.length == 1) {
/* 84 */       return a(paramArrayOfString, d());
/*    */     }
/*    */     
/* 87 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 91 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 96 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandEnchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */