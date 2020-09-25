/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTime
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "time";
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
/* 24 */     return "commands.time.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 29 */     if (paramArrayOfString.length > 1) {
/* 30 */       if (paramArrayOfString[0].equals("set")) {
/*    */         int i;
/*    */         
/* 33 */         if (paramArrayOfString[1].equals("day")) {
/* 34 */           i = 1000;
/* 35 */         } else if (paramArrayOfString[1].equals("night")) {
/* 36 */           i = 13000;
/*    */         } else {
/* 38 */           i = a(paramICommandListener, paramArrayOfString[1], 0);
/*    */         } 
/*    */         
/* 41 */         a(paramICommandListener, i);
/* 42 */         a(paramICommandListener, this, "commands.time.set", new Object[] { Integer.valueOf(i) }); return;
/*    */       } 
/* 44 */       if (paramArrayOfString[0].equals("add")) {
/* 45 */         int i = a(paramICommandListener, paramArrayOfString[1], 0);
/* 46 */         b(paramICommandListener, i);
/*    */         
/* 48 */         a(paramICommandListener, this, "commands.time.added", new Object[] { Integer.valueOf(i) });
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/* 53 */     throw new ExceptionUsage("commands.time.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 58 */     if (paramArrayOfString.length == 1)
/* 59 */       return a(paramArrayOfString, new String[] { "set", "add" }); 
/* 60 */     if (paramArrayOfString.length == 2 && paramArrayOfString[0].equals("set")) {
/* 61 */       return a(paramArrayOfString, new String[] { "day", "night" });
/*    */     }
/*    */     
/* 64 */     return null;
/*    */   }
/*    */   
/*    */   protected void a(ICommandListener paramICommandListener, int paramInt) {
/* 68 */     for (byte b = 0; b < (MinecraftServer.getServer()).worldServer.length; b++) {
/* 69 */       (MinecraftServer.getServer()).worldServer[b].setDayTime(paramInt);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void b(ICommandListener paramICommandListener, int paramInt) {
/* 74 */     for (byte b = 0; b < (MinecraftServer.getServer()).worldServer.length; b++) {
/* 75 */       WorldServer worldServer = (MinecraftServer.getServer()).worldServer[b];
/* 76 */       worldServer.setDayTime(worldServer.getDayTime() + paramInt);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */