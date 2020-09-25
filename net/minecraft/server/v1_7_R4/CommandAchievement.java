/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandAchievement
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  17 */     return "achievement";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  22 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  27 */     return "commands.achievement.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  32 */     if (paramArrayOfString.length >= 2) {
/*  33 */       EntityPlayer entityPlayer; Statistic statistic = StatisticList.getStatistic(paramArrayOfString[1]);
/*     */ 
/*     */       
/*  36 */       if (statistic == null && !paramArrayOfString[1].equals("*")) {
/*  37 */         throw new CommandException("commands.achievement.unknownAchievement", new Object[] { paramArrayOfString[1] });
/*     */       }
/*     */       
/*  40 */       if (paramArrayOfString.length >= 3) {
/*  41 */         entityPlayer = d(paramICommandListener, paramArrayOfString[2]);
/*     */       } else {
/*  43 */         entityPlayer = b(paramICommandListener);
/*     */       } 
/*     */       
/*  46 */       if (paramArrayOfString[0].equalsIgnoreCase("give")) {
/*  47 */         if (statistic == null) {
/*  48 */           for (Achievement achievement : AchievementList.e) {
/*  49 */             entityPlayer.a(achievement);
/*     */           }
/*     */           
/*  52 */           a(paramICommandListener, this, "commands.achievement.give.success.all", new Object[] { entityPlayer.getName() });
/*     */         } else {
/*  54 */           if (statistic instanceof Achievement) {
/*  55 */             Achievement achievement = (Achievement)statistic;
/*  56 */             ArrayList<Achievement> arrayList = Lists.newArrayList();
/*     */             
/*  58 */             while (achievement.c != null && !entityPlayer.getStatisticManager().hasAchievement(achievement.c)) {
/*  59 */               arrayList.add(achievement.c);
/*  60 */               achievement = achievement.c;
/*     */             } 
/*     */             
/*  63 */             for (Achievement achievement1 : Lists.reverse(arrayList)) {
/*  64 */               entityPlayer.a(achievement1);
/*     */             }
/*     */           } 
/*     */           
/*  68 */           entityPlayer.a(statistic);
/*     */           
/*  70 */           a(paramICommandListener, this, "commands.achievement.give.success.one", new Object[] { entityPlayer.getName(), statistic.j() });
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     throw new ExceptionUsage("commands.achievement.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  82 */     if (paramArrayOfString.length == 1) {
/*  83 */       return a(paramArrayOfString, new String[] { "give" });
/*     */     }
/*     */     
/*  86 */     if (paramArrayOfString.length == 2) {
/*  87 */       ArrayList<String> arrayList = Lists.newArrayList();
/*     */       
/*  89 */       for (Statistic statistic : StatisticList.stats) {
/*  90 */         arrayList.add(statistic.name);
/*     */       }
/*     */       
/*  93 */       return a(paramArrayOfString, arrayList);
/*     */     } 
/*     */     
/*  96 */     if (paramArrayOfString.length == 3) {
/*  97 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*     */     }
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 105 */     return (paramInt == 2);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandAchievement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */