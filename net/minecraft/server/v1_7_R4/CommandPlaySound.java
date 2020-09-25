/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPlaySound
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "playsound";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 18 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 23 */     return "commands.playsound.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 28 */     if (paramArrayOfString.length < 2) {
/* 29 */       throw new ExceptionUsage(c(paramICommandListener), new Object[0]);
/*    */     }
/*    */     
/* 32 */     byte b = 0;
/* 33 */     String str = paramArrayOfString[b++];
/* 34 */     EntityPlayer entityPlayer = d(paramICommandListener, paramArrayOfString[b++]);
/* 35 */     double d1 = (entityPlayer.getChunkCoordinates()).x;
/* 36 */     double d2 = (entityPlayer.getChunkCoordinates()).y;
/* 37 */     double d3 = (entityPlayer.getChunkCoordinates()).z;
/* 38 */     double d4 = 1.0D;
/* 39 */     double d5 = 1.0D;
/* 40 */     double d6 = 0.0D;
/*    */     
/* 42 */     if (paramArrayOfString.length > b) d1 = a(paramICommandListener, d1, paramArrayOfString[b++]); 
/* 43 */     if (paramArrayOfString.length > b) d2 = a(paramICommandListener, d2, paramArrayOfString[b++], 0, 0); 
/* 44 */     if (paramArrayOfString.length > b) d3 = a(paramICommandListener, d3, paramArrayOfString[b++]);
/*    */     
/* 46 */     if (paramArrayOfString.length > b) d4 = a(paramICommandListener, paramArrayOfString[b++], 0.0D, 3.4028234663852886E38D); 
/* 47 */     if (paramArrayOfString.length > b) d5 = a(paramICommandListener, paramArrayOfString[b++], 0.0D, 2.0D); 
/* 48 */     if (paramArrayOfString.length > b) d6 = a(paramICommandListener, paramArrayOfString[b++], 0.0D, 1.0D);
/*    */     
/* 50 */     double d7 = (d4 > 1.0D) ? (d4 * 16.0D) : 16.0D;
/* 51 */     double d8 = entityPlayer.f(d1, d2, d3);
/*    */     
/* 53 */     if (d8 > d7) {
/* 54 */       if (d6 > 0.0D) {
/* 55 */         double d9 = d1 - entityPlayer.locX;
/* 56 */         double d10 = d2 - entityPlayer.locY;
/* 57 */         double d11 = d3 - entityPlayer.locZ;
/* 58 */         double d12 = Math.sqrt(d9 * d9 + d10 * d10 + d11 * d11);
/* 59 */         double d13 = entityPlayer.locX;
/* 60 */         double d14 = entityPlayer.locY;
/* 61 */         double d15 = entityPlayer.locZ;
/*    */         
/* 63 */         if (d12 > 0.0D) {
/* 64 */           d13 += d9 / d12 * 2.0D;
/* 65 */           d14 += d10 / d12 * 2.0D;
/* 66 */           d15 += d11 / d12 * 2.0D;
/*    */         } 
/*    */         
/* 69 */         entityPlayer.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(str, d13, d14, d15, (float)d6, (float)d5));
/*    */       } else {
/* 71 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { entityPlayer.getName() });
/*    */       } 
/*    */     } else {
/* 74 */       entityPlayer.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(str, d1, d2, d3, (float)d4, (float)d5));
/*    */     } 
/*    */     
/* 77 */     a(paramICommandListener, this, "commands.playsound.success", new Object[] { str, entityPlayer.getName() });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 82 */     return (paramInt == 1);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandPlaySound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */