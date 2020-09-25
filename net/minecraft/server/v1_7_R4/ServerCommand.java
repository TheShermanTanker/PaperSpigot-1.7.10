/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ServerCommand
/*    */ {
/*    */   public final String command;
/*    */   public final ICommandListener source;
/*    */   
/*    */   public ServerCommand(String paramString, ICommandListener paramICommandListener) {
/* 10 */     this.command = paramString;
/* 11 */     this.source = paramICommandListener;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */