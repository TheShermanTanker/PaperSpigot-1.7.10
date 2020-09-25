/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSetWorldSpawn
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 16 */     return "setworldspawn";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.setworldspawn.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length == 3) {
/* 32 */       if (paramICommandListener.getWorld() != null) {
/* 33 */         byte b = 0;
/* 34 */         int i = a(paramICommandListener, paramArrayOfString[b++], -30000000, 30000000);
/* 35 */         int j = a(paramICommandListener, paramArrayOfString[b++], 0, 256);
/* 36 */         int k = a(paramICommandListener, paramArrayOfString[b++], -30000000, 30000000);
/*    */         
/* 38 */         paramICommandListener.getWorld().x(i, j, k);
/* 39 */         a(paramICommandListener, this, "commands.setworldspawn.success", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
/*    */       } else {
/* 41 */         throw new ExceptionUsage("commands.setworldspawn.usage", new Object[0]);
/*    */       } 
/* 43 */     } else if (paramArrayOfString.length == 0) {
/* 44 */       ChunkCoordinates chunkCoordinates = b(paramICommandListener).getChunkCoordinates();
/* 45 */       paramICommandListener.getWorld().x(chunkCoordinates.x, chunkCoordinates.y, chunkCoordinates.z);
/* 46 */       a(paramICommandListener, this, "commands.setworldspawn.success", new Object[] { Integer.valueOf(chunkCoordinates.x), Integer.valueOf(chunkCoordinates.y), Integer.valueOf(chunkCoordinates.z) });
/*    */     } else {
/* 48 */       throw new ExceptionUsage("commands.setworldspawn.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSetWorldSpawn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */