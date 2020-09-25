/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandNetstat
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "netstat";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 18 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 23 */     return "commands.players.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 28 */     if (paramICommandListener instanceof EntityHuman) {
/* 29 */       paramICommandListener.sendMessage(new ChatComponentText("Command is not available for players"));
/*    */       
/*    */       return;
/*    */     } 
/* 33 */     if (paramArrayOfString.length > 0 && paramArrayOfString[0].length() > 1) {
/* 34 */       if ("hottest-read".equals(paramArrayOfString[0])) {
/* 35 */         paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.e().toString()));
/* 36 */       } else if ("hottest-write".equals(paramArrayOfString[0])) {
/* 37 */         paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.g().toString()));
/* 38 */       } else if ("most-read".equals(paramArrayOfString[0])) {
/* 39 */         paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.f().toString()));
/* 40 */       } else if ("most-write".equals(paramArrayOfString[0])) {
/* 41 */         paramICommandListener.sendMessage(new ChatComponentText(NetworkManager.h.h().toString()));
/* 42 */       } else if ("packet-read".equals(paramArrayOfString[0])) {
/* 43 */         if (paramArrayOfString.length > 1 && paramArrayOfString[1].length() > 0) {
/*    */           try {
/* 45 */             int i = Integer.parseInt(paramArrayOfString[1].trim());
/* 46 */             PacketStatistics packetStatistics = NetworkManager.h.a(i);
/* 47 */             a(paramICommandListener, i, packetStatistics);
/* 48 */           } catch (Exception exception) {
/* 49 */             paramICommandListener.sendMessage(new ChatComponentText("Packet " + paramArrayOfString[1] + " not found!"));
/*    */           } 
/*    */         } else {
/* 52 */           paramICommandListener.sendMessage(new ChatComponentText("Packet id is missing"));
/*    */         } 
/* 54 */       } else if ("packet-write".equals(paramArrayOfString[0])) {
/* 55 */         if (paramArrayOfString.length > 1 && paramArrayOfString[1].length() > 0) {
/*    */           try {
/* 57 */             int i = Integer.parseInt(paramArrayOfString[1].trim());
/* 58 */             PacketStatistics packetStatistics = NetworkManager.h.b(i);
/* 59 */             a(paramICommandListener, i, packetStatistics);
/* 60 */           } catch (Exception exception) {
/* 61 */             paramICommandListener.sendMessage(new ChatComponentText("Packet " + paramArrayOfString[1] + " not found!"));
/*    */           } 
/*    */         } else {
/* 64 */           paramICommandListener.sendMessage(new ChatComponentText("Packet id is missing"));
/*    */         } 
/* 66 */       } else if ("read-count".equals(paramArrayOfString[0])) {
/* 67 */         paramICommandListener.sendMessage(new ChatComponentText("total-read-count" + String.valueOf(NetworkManager.h.c())));
/* 68 */       } else if ("write-count".equals(paramArrayOfString[0])) {
/* 69 */         paramICommandListener.sendMessage(new ChatComponentText("total-write-count" + String.valueOf(NetworkManager.h.d())));
/*    */       } else {
/* 71 */         paramICommandListener.sendMessage(new ChatComponentText("Unrecognized: " + paramArrayOfString[0]));
/*    */       } 
/*    */     } else {
/* 74 */       String str = "reads: " + NetworkManager.h.a();
/* 75 */       str = str + ", writes: " + NetworkManager.h.b();
/* 76 */       paramICommandListener.sendMessage(new ChatComponentText(str));
/*    */     } 
/*    */   }
/*    */   
/*    */   private void a(ICommandListener paramICommandListener, int paramInt, PacketStatistics paramPacketStatistics) {
/* 81 */     if (paramPacketStatistics != null) {
/* 82 */       paramICommandListener.sendMessage(new ChatComponentText(paramPacketStatistics.toString()));
/*    */     } else {
/* 84 */       paramICommandListener.sendMessage(new ChatComponentText("Packet " + paramInt + " not found!"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandNetstat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */