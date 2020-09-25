/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.spigotmc.SpigotConfig;
/*    */ 
/*    */ public class CommandDispatcher extends CommandHandler implements ICommandDispatcher {
/*    */   public CommandDispatcher() {
/*  8 */     a(new CommandTime());
/*  9 */     a(new CommandGamemode());
/* 10 */     a(new CommandDifficulty());
/* 11 */     a(new CommandGamemodeDefault());
/* 12 */     a(new CommandKill());
/* 13 */     a(new CommandToggleDownfall());
/* 14 */     a(new CommandWeather());
/* 15 */     a(new CommandXp());
/* 16 */     a(new CommandTp());
/* 17 */     a(new CommandGive());
/* 18 */     a(new CommandEffect());
/* 19 */     a(new CommandEnchant());
/* 20 */     a(new CommandMe());
/* 21 */     a(new CommandSeed());
/* 22 */     a(new CommandHelp());
/* 23 */     a(new CommandDebug());
/* 24 */     a(new CommandTell());
/* 25 */     a(new CommandSay());
/* 26 */     a(new CommandSpawnpoint());
/* 27 */     a(new CommandSetWorldSpawn());
/* 28 */     a(new CommandGamerule());
/* 29 */     a(new CommandClear());
/* 30 */     a(new CommandTestFor());
/* 31 */     a(new CommandSpreadPlayers());
/* 32 */     a(new CommandPlaySound());
/* 33 */     a(new CommandScoreboard());
/* 34 */     a(new CommandAchievement());
/* 35 */     a(new CommandSummon());
/* 36 */     a(new CommandSetBlock());
/* 37 */     a(new CommandTestForBlock());
/* 38 */     a(new CommandTellRaw());
/* 39 */     if (MinecraftServer.getServer().X()) {
/* 40 */       a(new CommandOp());
/* 41 */       a(new CommandDeop());
/* 42 */       a(new CommandStop());
/* 43 */       a(new CommandSaveAll());
/* 44 */       a(new CommandSaveOff());
/* 45 */       a(new CommandSaveOn());
/* 46 */       a(new CommandBanIp());
/* 47 */       a(new CommandPardonIP());
/* 48 */       a(new CommandBan());
/* 49 */       a(new CommandBanList());
/* 50 */       a(new CommandPardon());
/* 51 */       a(new CommandKick());
/* 52 */       a(new CommandList());
/* 53 */       a(new CommandWhitelist());
/* 54 */       a(new CommandIdleTimeout());
/* 55 */       a(new CommandNetstat());
/*    */     } else {
/* 57 */       a(new CommandPublish());
/*    */     } 
/*    */     
/* 60 */     CommandAbstract.a(this);
/*    */   }
/*    */   
/*    */   public void a(ICommandListener icommandlistener, ICommand icommand, int i, String s, Object... aobject) {
/* 64 */     boolean flag = true;
/*    */     
/* 66 */     if (icommandlistener instanceof CommandBlockListenerAbstract && !(MinecraftServer.getServer()).worldServer[0].getGameRules().getBoolean("commandBlockOutput")) {
/* 67 */       flag = false;
/*    */     }
/*    */     
/* 70 */     ChatMessage chatmessage = new ChatMessage("chat.type.admin", new Object[] { icommandlistener.getName(), new ChatMessage(s, aobject) });
/*    */     
/* 72 */     chatmessage.getChatModifier().setColor(EnumChatFormat.GRAY);
/* 73 */     chatmessage.getChatModifier().setItalic(Boolean.valueOf(true));
/* 74 */     if (flag) {
/* 75 */       Iterator<EntityHuman> iterator = (MinecraftServer.getServer().getPlayerList()).players.iterator();
/*    */       
/* 77 */       while (iterator.hasNext()) {
/* 78 */         EntityHuman entityhuman = iterator.next();
/*    */         
/* 80 */         if (entityhuman != icommandlistener && MinecraftServer.getServer().getPlayerList().isOp(entityhuman.getProfile()) && icommand.canUse(entityhuman) && (!(icommandlistener instanceof RemoteControlCommandListener) || MinecraftServer.getServer().m())) {
/* 81 */           entityhuman.sendMessage(chatmessage);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     if (icommandlistener != MinecraftServer.getServer() && !SpigotConfig.silentCommandBlocks) {
/* 87 */       MinecraftServer.getServer().sendMessage(chatmessage);
/*    */     }
/*    */     
/* 90 */     if ((i & 0x1) != 1)
/* 91 */       icommandlistener.sendMessage(new ChatMessage(s, aobject)); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */