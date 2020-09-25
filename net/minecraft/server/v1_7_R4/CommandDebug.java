/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandDebug
/*     */   extends CommandAbstract
/*     */ {
/*  20 */   private static final Logger a = LogManager.getLogger();
/*     */   
/*     */   private long b;
/*     */   private int c;
/*     */   
/*     */   public String getCommand() {
/*  26 */     return "debug";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  31 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  36 */     return "commands.debug.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  41 */     if (paramArrayOfString.length == 1) {
/*  42 */       if (paramArrayOfString[0].equals("start")) {
/*  43 */         a(paramICommandListener, this, "commands.debug.start", new Object[0]);
/*     */         
/*  45 */         MinecraftServer.getServer().am();
/*  46 */         this.b = MinecraftServer.ar();
/*  47 */         this.c = MinecraftServer.getServer().al(); return;
/*     */       } 
/*  49 */       if (paramArrayOfString[0].equals("stop")) {
/*  50 */         if (!(MinecraftServer.getServer()).methodProfiler.a) {
/*  51 */           throw new CommandException("commands.debug.notStarted", new Object[0]);
/*     */         }
/*     */         
/*  54 */         long l1 = MinecraftServer.ar();
/*  55 */         int i = MinecraftServer.getServer().al();
/*     */         
/*  57 */         long l2 = l1 - this.b;
/*  58 */         int j = i - this.c;
/*     */         
/*  60 */         a(l2, j);
/*     */         
/*  62 */         (MinecraftServer.getServer()).methodProfiler.a = false;
/*  63 */         a(paramICommandListener, this, "commands.debug.stop", new Object[] { Float.valueOf((float)l2 / 1000.0F), Integer.valueOf(j) });
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  68 */     throw new ExceptionUsage("commands.debug.usage", new Object[0]);
/*     */   }
/*     */   
/*     */   private void a(long paramLong, int paramInt) {
/*  72 */     File file = new File(MinecraftServer.getServer().d("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
/*     */     
/*  74 */     file.getParentFile().mkdirs();
/*     */     
/*     */     try {
/*  77 */       FileWriter fileWriter = new FileWriter(file);
/*  78 */       fileWriter.write(b(paramLong, paramInt));
/*  79 */       fileWriter.close();
/*  80 */     } catch (Throwable throwable) {
/*  81 */       a.error("Could not save profiler results to " + file, throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String b(long paramLong, int paramInt) {
/*  86 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*  88 */     stringBuilder.append("---- Minecraft Profiler Results ----\n");
/*  89 */     stringBuilder.append("// ");
/*  90 */     stringBuilder.append(d());
/*  91 */     stringBuilder.append("\n\n");
/*     */     
/*  93 */     stringBuilder.append("Time span: ").append(paramLong).append(" ms\n");
/*  94 */     stringBuilder.append("Tick span: ").append(paramInt).append(" ticks\n");
/*  95 */     stringBuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(paramInt / (float)paramLong / 1000.0F) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/*     */ 
/*     */     
/*  98 */     stringBuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
/*     */     
/* 100 */     a(0, "root", stringBuilder);
/*     */     
/* 102 */     stringBuilder.append("--- END PROFILE DUMP ---\n\n");
/*     */     
/* 104 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void a(int paramInt, String paramString, StringBuilder paramStringBuilder) {
/* 108 */     List<ProfilerInfo> list = (MinecraftServer.getServer()).methodProfiler.b(paramString);
/* 109 */     if (list == null || list.size() < 3)
/*     */       return; 
/* 111 */     for (byte b = 1; b < list.size(); b++) {
/* 112 */       ProfilerInfo profilerInfo = list.get(b);
/*     */       
/* 114 */       paramStringBuilder.append(String.format("[%02d] ", new Object[] { Integer.valueOf(paramInt) }));
/* 115 */       for (byte b1 = 0; b1 < paramInt; b1++)
/* 116 */         paramStringBuilder.append(" "); 
/* 117 */       paramStringBuilder.append(profilerInfo.c);
/* 118 */       paramStringBuilder.append(" - ");
/* 119 */       paramStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(profilerInfo.a) }));
/* 120 */       paramStringBuilder.append("%/");
/* 121 */       paramStringBuilder.append(String.format("%.2f", new Object[] { Double.valueOf(profilerInfo.b) }));
/* 122 */       paramStringBuilder.append("%\n");
/*     */       
/* 124 */       if (!profilerInfo.c.equals("unspecified")) {
/*     */         try {
/* 126 */           a(paramInt + 1, paramString + "." + profilerInfo.c, paramStringBuilder);
/* 127 */         } catch (Exception exception) {
/* 128 */           paramStringBuilder.append("[[ EXCEPTION " + exception + " ]]");
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String d() {
/* 136 */     String[] arrayOfString = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 143 */       return arrayOfString[(int)(System.nanoTime() % arrayOfString.length)];
/* 144 */     } catch (Throwable throwable) {
/* 145 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 151 */     if (paramArrayOfString.length == 1) return a(paramArrayOfString, new String[] { "start", "stop" });
/*     */     
/* 153 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandDebug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */