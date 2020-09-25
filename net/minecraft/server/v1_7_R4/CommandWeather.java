/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandWeather
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "weather";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 20 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 25 */     return "commands.weather.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 30 */     if (paramArrayOfString.length < 1 || paramArrayOfString.length > 2) {
/* 31 */       throw new ExceptionUsage("commands.weather.usage", new Object[0]);
/*    */     }
/*    */     
/* 34 */     int i = (300 + (new Random()).nextInt(600)) * 20;
/* 35 */     if (paramArrayOfString.length >= 2) {
/* 36 */       i = a(paramICommandListener, paramArrayOfString[1], 1, 1000000) * 20;
/*    */     }
/*    */     
/* 39 */     WorldServer worldServer = (MinecraftServer.getServer()).worldServer[0];
/* 40 */     WorldData worldData = worldServer.getWorldData();
/*    */     
/* 42 */     if ("clear".equalsIgnoreCase(paramArrayOfString[0])) {
/* 43 */       worldData.setWeatherDuration(0);
/* 44 */       worldData.setThunderDuration(0);
/* 45 */       worldData.setStorm(false);
/* 46 */       worldData.setThundering(false);
/* 47 */       a(paramICommandListener, this, "commands.weather.clear", new Object[0]);
/* 48 */     } else if ("rain".equalsIgnoreCase(paramArrayOfString[0])) {
/* 49 */       worldData.setWeatherDuration(i);
/* 50 */       worldData.setStorm(true);
/* 51 */       worldData.setThundering(false);
/* 52 */       a(paramICommandListener, this, "commands.weather.rain", new Object[0]);
/* 53 */     } else if ("thunder".equalsIgnoreCase(paramArrayOfString[0])) {
/* 54 */       worldData.setWeatherDuration(i);
/* 55 */       worldData.setThunderDuration(i);
/* 56 */       worldData.setStorm(true);
/* 57 */       worldData.setThundering(true);
/* 58 */       a(paramICommandListener, this, "commands.weather.thunder", new Object[0]);
/*    */     } else {
/* 60 */       throw new ExceptionUsage("commands.weather.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 66 */     if (paramArrayOfString.length == 1) {
/* 67 */       return a(paramArrayOfString, new String[] { "clear", "rain", "thunder" });
/*    */     }
/*    */     
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandWeather.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */