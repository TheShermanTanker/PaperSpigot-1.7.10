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
/*    */ public class CommandSpawnpoint
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 16 */     return "spawnpoint";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.spawnpoint.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     EntityPlayer entityPlayer = (paramArrayOfString.length == 0) ? b(paramICommandListener) : d(paramICommandListener, paramArrayOfString[0]);
/*    */     
/* 33 */     if (paramArrayOfString.length == 4) {
/* 34 */       if (entityPlayer.world != null) {
/* 35 */         byte b = 1;
/* 36 */         int i = 30000000;
/* 37 */         int j = a(paramICommandListener, paramArrayOfString[b++], -i, i);
/* 38 */         int k = a(paramICommandListener, paramArrayOfString[b++], 0, 256);
/* 39 */         int m = a(paramICommandListener, paramArrayOfString[b++], -i, i);
/*    */         
/* 41 */         entityPlayer.setRespawnPosition(new ChunkCoordinates(j, k, m), true);
/* 42 */         a(paramICommandListener, this, "commands.spawnpoint.success", new Object[] { entityPlayer.getName(), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(m) });
/*    */       } 
/* 44 */     } else if (paramArrayOfString.length <= 1) {
/* 45 */       ChunkCoordinates chunkCoordinates = entityPlayer.getChunkCoordinates();
/* 46 */       entityPlayer.setRespawnPosition(chunkCoordinates, true);
/* 47 */       a(paramICommandListener, this, "commands.spawnpoint.success", new Object[] { entityPlayer.getName(), Integer.valueOf(chunkCoordinates.x), Integer.valueOf(chunkCoordinates.y), Integer.valueOf(chunkCoordinates.z) });
/*    */     } else {
/* 49 */       throw new ExceptionUsage("commands.spawnpoint.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 55 */     if (paramArrayOfString.length == 1 || paramArrayOfString.length == 2) {
/* 56 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 64 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSpawnpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */