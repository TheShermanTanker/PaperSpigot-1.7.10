/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTp
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "tp";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 18 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 24 */     return "commands.tp.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 29 */     if (paramArrayOfString.length >= 1) {
/*    */       EntityPlayer entityPlayer;
/*    */       
/* 32 */       if (paramArrayOfString.length == 2 || paramArrayOfString.length == 4) {
/* 33 */         entityPlayer = d(paramICommandListener, paramArrayOfString[0]);
/* 34 */         if (entityPlayer == null) throw new ExceptionPlayerNotFound(); 
/*    */       } else {
/* 36 */         entityPlayer = b(paramICommandListener);
/*    */       } 
/*    */       
/* 39 */       if (paramArrayOfString.length == 3 || paramArrayOfString.length == 4) {
/* 40 */         if (entityPlayer.world != null) {
/* 41 */           int i = paramArrayOfString.length - 3;
/*    */           
/* 43 */           double d1 = a(paramICommandListener, entityPlayer.locX, paramArrayOfString[i++]);
/* 44 */           double d2 = a(paramICommandListener, entityPlayer.locY, paramArrayOfString[i++], 0, 0);
/* 45 */           double d3 = a(paramICommandListener, entityPlayer.locZ, paramArrayOfString[i++]);
/*    */           
/* 47 */           entityPlayer.mount((Entity)null);
/* 48 */           entityPlayer.enderTeleportTo(d1, d2, d3);
/* 49 */           a(paramICommandListener, this, "commands.tp.success.coordinates", new Object[] { entityPlayer.getName(), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3) });
/*    */         } 
/* 51 */       } else if (paramArrayOfString.length == 1 || paramArrayOfString.length == 2) {
/* 52 */         EntityPlayer entityPlayer1 = d(paramICommandListener, paramArrayOfString[paramArrayOfString.length - 1]);
/* 53 */         if (entityPlayer1 == null) throw new ExceptionPlayerNotFound(); 
/* 54 */         if (entityPlayer1.world != entityPlayer.world) {
/* 55 */           a(paramICommandListener, this, "commands.tp.notSameDimension", new Object[0]);
/*    */           
/*    */           return;
/*    */         } 
/* 59 */         entityPlayer.mount((Entity)null);
/* 60 */         entityPlayer.playerConnection.a(entityPlayer1.locX, entityPlayer1.locY, entityPlayer1.locZ, entityPlayer1.yaw, entityPlayer1.pitch);
/* 61 */         a(paramICommandListener, this, "commands.tp.success", new Object[] { entityPlayer.getName(), entityPlayer1.getName() });
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 67 */     throw new ExceptionUsage("commands.tp.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 72 */     if (paramArrayOfString.length == 1 || paramArrayOfString.length == 2) {
/* 73 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 76 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 81 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandTp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */