/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandScoreboard
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  24 */     return "scoreboard";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  34 */     return "commands.scoreboard.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  39 */     if (paramArrayOfString.length >= 1) {
/*  40 */       if (paramArrayOfString[0].equalsIgnoreCase("objectives")) {
/*  41 */         if (paramArrayOfString.length == 1)
/*  42 */           throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]); 
/*  43 */         if (paramArrayOfString[1].equalsIgnoreCase("list")) {
/*  44 */           d(paramICommandListener);
/*  45 */         } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/*  46 */           if (paramArrayOfString.length >= 4) {
/*  47 */             c(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  49 */             throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
/*     */           } 
/*  51 */         } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/*  52 */           if (paramArrayOfString.length == 3) {
/*  53 */             h(paramICommandListener, paramArrayOfString[2]);
/*     */           } else {
/*  55 */             throw new ExceptionUsage("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*     */           } 
/*  57 */         } else if (paramArrayOfString[1].equalsIgnoreCase("setdisplay")) {
/*  58 */           if (paramArrayOfString.length == 3 || paramArrayOfString.length == 4) {
/*  59 */             k(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  61 */             throw new ExceptionUsage("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*     */           } 
/*     */         } else {
/*  64 */           throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
/*     */         } 
/*     */         return;
/*     */       } 
/*  68 */       if (paramArrayOfString[0].equalsIgnoreCase("players")) {
/*  69 */         if (paramArrayOfString.length == 1)
/*  70 */           throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]); 
/*  71 */         if (paramArrayOfString[1].equalsIgnoreCase("list")) {
/*  72 */           if (paramArrayOfString.length <= 3) {
/*  73 */             l(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  75 */             throw new ExceptionUsage("commands.scoreboard.players.list.usage", new Object[0]);
/*     */           } 
/*  77 */         } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/*  78 */           if (paramArrayOfString.length == 5) {
/*  79 */             m(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  81 */             throw new ExceptionUsage("commands.scoreboard.players.add.usage", new Object[0]);
/*     */           } 
/*  83 */         } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/*  84 */           if (paramArrayOfString.length == 5) {
/*  85 */             m(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  87 */             throw new ExceptionUsage("commands.scoreboard.players.remove.usage", new Object[0]);
/*     */           } 
/*  89 */         } else if (paramArrayOfString[1].equalsIgnoreCase("set")) {
/*  90 */           if (paramArrayOfString.length == 5) {
/*  91 */             m(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  93 */             throw new ExceptionUsage("commands.scoreboard.players.set.usage", new Object[0]);
/*     */           } 
/*  95 */         } else if (paramArrayOfString[1].equalsIgnoreCase("reset")) {
/*  96 */           if (paramArrayOfString.length == 3) {
/*  97 */             n(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/*  99 */             throw new ExceptionUsage("commands.scoreboard.players.reset.usage", new Object[0]);
/*     */           } 
/*     */         } else {
/* 102 */           throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
/*     */         } 
/*     */         return;
/*     */       } 
/* 106 */       if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
/* 107 */         if (paramArrayOfString.length == 1)
/* 108 */           throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]); 
/* 109 */         if (paramArrayOfString[1].equalsIgnoreCase("list")) {
/* 110 */           if (paramArrayOfString.length <= 3) {
/* 111 */             g(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 113 */             throw new ExceptionUsage("commands.scoreboard.teams.list.usage", new Object[0]);
/*     */           } 
/* 115 */         } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/* 116 */           if (paramArrayOfString.length >= 3) {
/* 117 */             d(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 119 */             throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
/*     */           } 
/* 121 */         } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/* 122 */           if (paramArrayOfString.length == 3) {
/* 123 */             f(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 125 */             throw new ExceptionUsage("commands.scoreboard.teams.remove.usage", new Object[0]);
/*     */           } 
/* 127 */         } else if (paramArrayOfString[1].equalsIgnoreCase("empty")) {
/* 128 */           if (paramArrayOfString.length == 3) {
/* 129 */             j(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 131 */             throw new ExceptionUsage("commands.scoreboard.teams.empty.usage", new Object[0]);
/*     */           } 
/* 133 */         } else if (paramArrayOfString[1].equalsIgnoreCase("join")) {
/* 134 */           if (paramArrayOfString.length >= 4 || (paramArrayOfString.length == 3 && paramICommandListener instanceof EntityHuman)) {
/* 135 */             h(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 137 */             throw new ExceptionUsage("commands.scoreboard.teams.join.usage", new Object[0]);
/*     */           } 
/* 139 */         } else if (paramArrayOfString[1].equalsIgnoreCase("leave")) {
/* 140 */           if (paramArrayOfString.length >= 3 || paramICommandListener instanceof EntityHuman) {
/* 141 */             i(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 143 */             throw new ExceptionUsage("commands.scoreboard.teams.leave.usage", new Object[0]);
/*     */           } 
/* 145 */         } else if (paramArrayOfString[1].equalsIgnoreCase("option")) {
/* 146 */           if (paramArrayOfString.length == 4 || paramArrayOfString.length == 5) {
/* 147 */             e(paramICommandListener, paramArrayOfString, 2);
/*     */           } else {
/* 149 */             throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */           } 
/*     */         } else {
/* 152 */           throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     throw new ExceptionUsage("commands.scoreboard.usage", new Object[0]);
/*     */   }
/*     */   
/*     */   protected Scoreboard d() {
/* 163 */     return MinecraftServer.getServer().getWorldServer(0).getScoreboard();
/*     */   }
/*     */   
/*     */   protected ScoreboardObjective a(String paramString, boolean paramBoolean) {
/* 167 */     Scoreboard scoreboard = d();
/* 168 */     ScoreboardObjective scoreboardObjective = scoreboard.getObjective(paramString);
/*     */     
/* 170 */     if (scoreboardObjective == null)
/* 171 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { paramString }); 
/* 172 */     if (paramBoolean && scoreboardObjective.getCriteria().isReadOnly()) {
/* 173 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { paramString });
/*     */     }
/*     */     
/* 176 */     return scoreboardObjective;
/*     */   }
/*     */   
/*     */   protected ScoreboardTeam a(String paramString) {
/* 180 */     Scoreboard scoreboard = d();
/* 181 */     ScoreboardTeam scoreboardTeam = scoreboard.getTeam(paramString);
/*     */     
/* 183 */     if (scoreboardTeam == null) {
/* 184 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { paramString });
/*     */     }
/*     */     
/* 187 */     return scoreboardTeam;
/*     */   }
/*     */   
/*     */   protected void c(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 191 */     String str1 = paramArrayOfString[paramInt++];
/* 192 */     String str2 = paramArrayOfString[paramInt++];
/* 193 */     Scoreboard scoreboard = d();
/* 194 */     IScoreboardCriteria iScoreboardCriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(str2);
/*     */     
/* 196 */     if (iScoreboardCriteria == null) {
/* 197 */       throw new ExceptionUsage("commands.scoreboard.objectives.add.wrongType", new Object[] { str2 });
/*     */     }
/* 199 */     if (scoreboard.getObjective(str1) != null) {
/* 200 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { str1 });
/*     */     }
/* 202 */     if (str1.length() > 16) {
/* 203 */       throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.tooLong", new Object[] { str1, Integer.valueOf(16) });
/*     */     }
/* 205 */     if (str1.length() == 0) {
/* 206 */       throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
/*     */     }
/*     */     
/* 209 */     if (paramArrayOfString.length > paramInt) {
/* 210 */       String str = a(paramICommandListener, paramArrayOfString, paramInt).c();
/*     */       
/* 212 */       if (str.length() > 32) {
/* 213 */         throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.displayTooLong", new Object[] { str, Integer.valueOf(32) });
/*     */       }
/* 215 */       if (str.length() > 0) {
/* 216 */         scoreboard.registerObjective(str1, iScoreboardCriteria).setDisplayName(str);
/*     */       } else {
/* 218 */         scoreboard.registerObjective(str1, iScoreboardCriteria);
/*     */       } 
/*     */     } else {
/* 221 */       scoreboard.registerObjective(str1, iScoreboardCriteria);
/*     */     } 
/*     */     
/* 224 */     a(paramICommandListener, this, "commands.scoreboard.objectives.add.success", new Object[] { str1 });
/*     */   }
/*     */   
/*     */   protected void d(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 228 */     String str = paramArrayOfString[paramInt++];
/* 229 */     Scoreboard scoreboard = d();
/*     */     
/* 231 */     if (scoreboard.getTeam(str) != null) {
/* 232 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { str });
/*     */     }
/* 234 */     if (str.length() > 16) {
/* 235 */       throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.tooLong", new Object[] { str, Integer.valueOf(16) });
/*     */     }
/* 237 */     if (str.length() == 0) {
/* 238 */       throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
/*     */     }
/*     */     
/* 241 */     if (paramArrayOfString.length > paramInt) {
/* 242 */       String str1 = a(paramICommandListener, paramArrayOfString, paramInt).c();
/*     */       
/* 244 */       if (str1.length() > 32) {
/* 245 */         throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.displayTooLong", new Object[] { str1, Integer.valueOf(32) });
/*     */       }
/* 247 */       if (str1.length() > 0) {
/* 248 */         scoreboard.createTeam(str).setDisplayName(str1);
/*     */       } else {
/* 250 */         scoreboard.createTeam(str);
/*     */       } 
/*     */     } else {
/* 253 */       scoreboard.createTeam(str);
/*     */     } 
/*     */     
/* 256 */     a(paramICommandListener, this, "commands.scoreboard.teams.add.success", new Object[] { str });
/*     */   }
/*     */   
/*     */   protected void e(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 260 */     ScoreboardTeam scoreboardTeam = a(paramArrayOfString[paramInt++]);
/* 261 */     if (scoreboardTeam == null)
/* 262 */       return;  String str1 = paramArrayOfString[paramInt++].toLowerCase();
/*     */     
/* 264 */     if (!str1.equalsIgnoreCase("color") && !str1.equalsIgnoreCase("friendlyfire") && !str1.equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 265 */       throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */     }
/*     */     
/* 268 */     if (paramArrayOfString.length == 4) {
/* 269 */       if (str1.equalsIgnoreCase("color"))
/* 270 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(EnumChatFormat.a(true, false)) }); 
/* 271 */       if (str1.equalsIgnoreCase("friendlyfire") || str1.equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 272 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
/*     */       }
/*     */       
/* 275 */       throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */     } 
/*     */     
/* 278 */     String str2 = paramArrayOfString[paramInt++];
/*     */     
/* 280 */     if (str1.equalsIgnoreCase("color")) {
/* 281 */       EnumChatFormat enumChatFormat = EnumChatFormat.b(str2);
/* 282 */       if (enumChatFormat == null || enumChatFormat.isFormat()) throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(EnumChatFormat.a(true, false)) }); 
/* 283 */       scoreboardTeam.setPrefix(enumChatFormat.toString());
/* 284 */       scoreboardTeam.setSuffix(EnumChatFormat.RESET.toString());
/* 285 */     } else if (str1.equalsIgnoreCase("friendlyfire")) {
/* 286 */       if (!str2.equalsIgnoreCase("true") && !str2.equalsIgnoreCase("false")) throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) }); 
/* 287 */       scoreboardTeam.setAllowFriendlyFire(str2.equalsIgnoreCase("true"));
/* 288 */     } else if (str1.equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 289 */       if (!str2.equalsIgnoreCase("true") && !str2.equalsIgnoreCase("false")) throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) }); 
/* 290 */       scoreboardTeam.setCanSeeFriendlyInvisibles(str2.equalsIgnoreCase("true"));
/*     */     } 
/*     */     
/* 293 */     a(paramICommandListener, this, "commands.scoreboard.teams.option.success", new Object[] { str1, scoreboardTeam.getName(), str2 });
/*     */   }
/*     */   
/*     */   protected void f(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 297 */     Scoreboard scoreboard = d();
/* 298 */     ScoreboardTeam scoreboardTeam = a(paramArrayOfString[paramInt++]);
/* 299 */     if (scoreboardTeam == null)
/*     */       return; 
/* 301 */     scoreboard.removeTeam(scoreboardTeam);
/*     */     
/* 303 */     a(paramICommandListener, this, "commands.scoreboard.teams.remove.success", new Object[] { scoreboardTeam.getName() });
/*     */   }
/*     */   
/*     */   protected void g(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 307 */     Scoreboard scoreboard = d();
/*     */     
/* 309 */     if (paramArrayOfString.length > paramInt) {
/* 310 */       ScoreboardTeam scoreboardTeam = a(paramArrayOfString[paramInt++]);
/* 311 */       if (scoreboardTeam == null)
/*     */         return; 
/* 313 */       Collection collection = scoreboardTeam.getPlayerNameSet();
/*     */       
/* 315 */       if (collection.size() > 0) {
/* 316 */         ChatMessage chatMessage = new ChatMessage("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(collection.size()), scoreboardTeam.getName() });
/* 317 */         chatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 318 */         paramICommandListener.sendMessage(chatMessage);
/* 319 */         paramICommandListener.sendMessage(new ChatComponentText(a(collection.toArray())));
/*     */       } else {
/* 321 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { scoreboardTeam.getName() });
/*     */       } 
/*     */     } else {
/* 324 */       Collection collection = scoreboard.getTeams();
/*     */       
/* 326 */       if (collection.size() > 0) {
/* 327 */         ChatMessage chatMessage = new ChatMessage("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(collection.size()) });
/* 328 */         chatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 329 */         paramICommandListener.sendMessage(chatMessage);
/*     */         
/* 331 */         for (ScoreboardTeam scoreboardTeam : collection) {
/* 332 */           paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.teams.list.entry", new Object[] { scoreboardTeam.getName(), scoreboardTeam.getDisplayName(), Integer.valueOf(scoreboardTeam.getPlayerNameSet().size()) }));
/*     */         } 
/*     */       } else {
/* 335 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void h(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 341 */     Scoreboard scoreboard = d();
/* 342 */     String str = paramArrayOfString[paramInt++];
/* 343 */     HashSet<String> hashSet1 = new HashSet();
/* 344 */     HashSet<String> hashSet2 = new HashSet();
/*     */     
/* 346 */     if (paramICommandListener instanceof EntityHuman && paramInt == paramArrayOfString.length) {
/* 347 */       String str1 = b(paramICommandListener).getName();
/*     */       
/* 349 */       if (scoreboard.addPlayerToTeam(str1, str)) {
/* 350 */         hashSet1.add(str1);
/*     */       } else {
/* 352 */         hashSet2.add(str1);
/*     */       } 
/*     */     } else {
/* 355 */       while (paramInt < paramArrayOfString.length) {
/* 356 */         String str1 = e(paramICommandListener, paramArrayOfString[paramInt++]);
/*     */         
/* 358 */         if (scoreboard.addPlayerToTeam(str1, str)) {
/* 359 */           hashSet1.add(str1); continue;
/*     */         } 
/* 361 */         hashSet2.add(str1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 366 */     if (!hashSet1.isEmpty()) a(paramICommandListener, this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(hashSet1.size()), str, a(hashSet1.toArray((Object[])new String[0])) }); 
/* 367 */     if (!hashSet2.isEmpty()) throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(hashSet2.size()), str, a(hashSet2.toArray(new String[0])) }); 
/*     */   }
/*     */   
/*     */   protected void i(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 371 */     Scoreboard scoreboard = d();
/* 372 */     HashSet<String> hashSet1 = new HashSet();
/* 373 */     HashSet<String> hashSet2 = new HashSet();
/*     */     
/* 375 */     if (paramICommandListener instanceof EntityHuman && paramInt == paramArrayOfString.length) {
/* 376 */       String str = b(paramICommandListener).getName();
/*     */       
/* 378 */       if (scoreboard.removePlayerFromTeam(str)) {
/* 379 */         hashSet1.add(str);
/*     */       } else {
/* 381 */         hashSet2.add(str);
/*     */       } 
/*     */     } else {
/* 384 */       while (paramInt < paramArrayOfString.length) {
/* 385 */         String str = e(paramICommandListener, paramArrayOfString[paramInt++]);
/*     */         
/* 387 */         if (scoreboard.removePlayerFromTeam(str)) {
/* 388 */           hashSet1.add(str); continue;
/*     */         } 
/* 390 */         hashSet2.add(str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 395 */     if (!hashSet1.isEmpty()) a(paramICommandListener, this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(hashSet1.size()), a(hashSet1.toArray((Object[])new String[0])) }); 
/* 396 */     if (!hashSet2.isEmpty()) throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(hashSet2.size()), a(hashSet2.toArray(new String[0])) }); 
/*     */   }
/*     */   
/*     */   protected void j(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 400 */     Scoreboard scoreboard = d();
/* 401 */     ScoreboardTeam scoreboardTeam = a(paramArrayOfString[paramInt++]);
/* 402 */     if (scoreboardTeam == null)
/*     */       return; 
/* 404 */     ArrayList arrayList = new ArrayList(scoreboardTeam.getPlayerNameSet());
/*     */     
/* 406 */     if (arrayList.isEmpty()) {
/* 407 */       throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { scoreboardTeam.getName() });
/*     */     }
/*     */     
/* 410 */     for (String str : arrayList) {
/* 411 */       scoreboard.removePlayerFromTeam(str, scoreboardTeam);
/*     */     }
/*     */     
/* 414 */     a(paramICommandListener, this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(arrayList.size()), scoreboardTeam.getName() });
/*     */   }
/*     */   
/*     */   protected void h(ICommandListener paramICommandListener, String paramString) {
/* 418 */     Scoreboard scoreboard = d();
/* 419 */     ScoreboardObjective scoreboardObjective = a(paramString, false);
/*     */     
/* 421 */     scoreboard.unregisterObjective(scoreboardObjective);
/*     */     
/* 423 */     a(paramICommandListener, this, "commands.scoreboard.objectives.remove.success", new Object[] { paramString });
/*     */   }
/*     */   
/*     */   protected void d(ICommandListener paramICommandListener) {
/* 427 */     Scoreboard scoreboard = d();
/* 428 */     Collection collection = scoreboard.getObjectives();
/*     */     
/* 430 */     if (collection.size() > 0) {
/* 431 */       ChatMessage chatMessage = new ChatMessage("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(collection.size()) });
/* 432 */       chatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 433 */       paramICommandListener.sendMessage(chatMessage);
/*     */       
/* 435 */       for (ScoreboardObjective scoreboardObjective : collection) {
/* 436 */         paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.objectives.list.entry", new Object[] { scoreboardObjective.getName(), scoreboardObjective.getDisplayName(), scoreboardObjective.getCriteria().getName() }));
/*     */       } 
/*     */     } else {
/* 439 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void k(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 444 */     Scoreboard scoreboard = d();
/* 445 */     String str = paramArrayOfString[paramInt++];
/* 446 */     int i = Scoreboard.getSlotForName(str);
/* 447 */     ScoreboardObjective scoreboardObjective = null;
/*     */     
/* 449 */     if (paramArrayOfString.length == 4) {
/* 450 */       scoreboardObjective = a(paramArrayOfString[paramInt++], false);
/*     */     }
/*     */     
/* 453 */     if (i < 0) {
/* 454 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { str });
/*     */     }
/*     */     
/* 457 */     scoreboard.setDisplaySlot(i, scoreboardObjective);
/*     */     
/* 459 */     if (scoreboardObjective != null) {
/* 460 */       a(paramICommandListener, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getSlotName(i), scoreboardObjective.getName() });
/*     */     } else {
/* 462 */       a(paramICommandListener, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getSlotName(i) });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void l(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 467 */     Scoreboard scoreboard = d();
/*     */     
/* 469 */     if (paramArrayOfString.length > paramInt) {
/* 470 */       String str = e(paramICommandListener, paramArrayOfString[paramInt++]);
/* 471 */       Map map = scoreboard.getPlayerObjectives(str);
/*     */       
/* 473 */       if (map.size() > 0) {
/* 474 */         ChatMessage chatMessage = new ChatMessage("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(map.size()), str });
/* 475 */         chatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 476 */         paramICommandListener.sendMessage(chatMessage);
/*     */         
/* 478 */         for (ScoreboardScore scoreboardScore : map.values()) {
/* 479 */           paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(scoreboardScore.getScore()), scoreboardScore.getObjective().getDisplayName(), scoreboardScore.getObjective().getName() }));
/*     */         } 
/*     */       } else {
/* 482 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { str });
/*     */       } 
/*     */     } else {
/* 485 */       Collection collection = scoreboard.getPlayers();
/*     */       
/* 487 */       if (collection.size() > 0) {
/* 488 */         ChatMessage chatMessage = new ChatMessage("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(collection.size()) });
/* 489 */         chatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 490 */         paramICommandListener.sendMessage(chatMessage);
/* 491 */         paramICommandListener.sendMessage(new ChatComponentText(a(collection.toArray())));
/*     */       } else {
/* 493 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void m(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 499 */     String str1 = paramArrayOfString[paramInt - 1];
/* 500 */     String str2 = e(paramICommandListener, paramArrayOfString[paramInt++]);
/* 501 */     ScoreboardObjective scoreboardObjective = a(paramArrayOfString[paramInt++], true);
/* 502 */     int i = str1.equalsIgnoreCase("set") ? a(paramICommandListener, paramArrayOfString[paramInt++]) : a(paramICommandListener, paramArrayOfString[paramInt++], 1);
/* 503 */     Scoreboard scoreboard = d();
/* 504 */     ScoreboardScore scoreboardScore = scoreboard.getPlayerScoreForObjective(str2, scoreboardObjective);
/*     */     
/* 506 */     if (str1.equalsIgnoreCase("set")) {
/* 507 */       scoreboardScore.setScore(i);
/* 508 */     } else if (str1.equalsIgnoreCase("add")) {
/* 509 */       scoreboardScore.addScore(i);
/*     */     } else {
/* 511 */       scoreboardScore.removeScore(i);
/*     */     } 
/*     */     
/* 514 */     a(paramICommandListener, this, "commands.scoreboard.players.set.success", new Object[] { scoreboardObjective.getName(), str2, Integer.valueOf(scoreboardScore.getScore()) });
/*     */   }
/*     */   
/*     */   protected void n(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 518 */     Scoreboard scoreboard = d();
/* 519 */     String str = e(paramICommandListener, paramArrayOfString[paramInt++]);
/*     */     
/* 521 */     scoreboard.resetPlayerScores(str);
/*     */     
/* 523 */     a(paramICommandListener, this, "commands.scoreboard.players.reset.success", new Object[] { str });
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 528 */     if (paramArrayOfString.length == 1) {
/* 529 */       return a(paramArrayOfString, new String[] { "objectives", "players", "teams" });
/*     */     }
/*     */     
/* 532 */     if (paramArrayOfString[0].equalsIgnoreCase("objectives")) {
/* 533 */       if (paramArrayOfString.length == 2) {
/* 534 */         return a(paramArrayOfString, new String[] { "list", "add", "remove", "setdisplay" });
/*     */       }
/*     */       
/* 537 */       if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/* 538 */         if (paramArrayOfString.length == 4) {
/* 539 */           Set set = IScoreboardCriteria.criteria.keySet();
/* 540 */           return a(paramArrayOfString, set);
/*     */         } 
/* 542 */       } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/* 543 */         if (paramArrayOfString.length == 3) {
/* 544 */           return a(paramArrayOfString, a(false));
/*     */         }
/* 546 */       } else if (paramArrayOfString[1].equalsIgnoreCase("setdisplay")) {
/* 547 */         if (paramArrayOfString.length == 3)
/* 548 */           return a(paramArrayOfString, new String[] { "list", "sidebar", "belowName" }); 
/* 549 */         if (paramArrayOfString.length == 4) {
/* 550 */           return a(paramArrayOfString, a(false));
/*     */         }
/*     */       } 
/* 553 */     } else if (paramArrayOfString[0].equalsIgnoreCase("players")) {
/* 554 */       if (paramArrayOfString.length == 2) {
/* 555 */         return a(paramArrayOfString, new String[] { "set", "add", "remove", "reset", "list" });
/*     */       }
/*     */       
/* 558 */       if (paramArrayOfString[1].equalsIgnoreCase("set") || paramArrayOfString[1].equalsIgnoreCase("add") || paramArrayOfString[1].equalsIgnoreCase("remove")) {
/* 559 */         if (paramArrayOfString.length == 3)
/* 560 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers()); 
/* 561 */         if (paramArrayOfString.length == 4) {
/* 562 */           return a(paramArrayOfString, a(true));
/*     */         }
/* 564 */       } else if ((paramArrayOfString[1].equalsIgnoreCase("reset") || paramArrayOfString[1].equalsIgnoreCase("list")) && 
/* 565 */         paramArrayOfString.length == 3) {
/* 566 */         return a(paramArrayOfString, d().getPlayers());
/*     */       }
/*     */     
/* 569 */     } else if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
/* 570 */       if (paramArrayOfString.length == 2) {
/* 571 */         return a(paramArrayOfString, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/*     */       }
/*     */       
/* 574 */       if (paramArrayOfString[1].equalsIgnoreCase("join")) {
/* 575 */         if (paramArrayOfString.length == 3)
/* 576 */           return a(paramArrayOfString, d().getTeamNames()); 
/* 577 */         if (paramArrayOfString.length >= 4)
/* 578 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers()); 
/*     */       } else {
/* 580 */         if (paramArrayOfString[1].equalsIgnoreCase("leave"))
/* 581 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers()); 
/* 582 */         if (paramArrayOfString[1].equalsIgnoreCase("empty") || paramArrayOfString[1].equalsIgnoreCase("list") || paramArrayOfString[1].equalsIgnoreCase("remove")) {
/* 583 */           if (paramArrayOfString.length == 3) {
/* 584 */             return a(paramArrayOfString, d().getTeamNames());
/*     */           }
/* 586 */         } else if (paramArrayOfString[1].equalsIgnoreCase("option")) {
/* 587 */           if (paramArrayOfString.length == 3)
/* 588 */             return a(paramArrayOfString, d().getTeamNames()); 
/* 589 */           if (paramArrayOfString.length == 4)
/* 590 */             return a(paramArrayOfString, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles" }); 
/* 591 */           if (paramArrayOfString.length == 5) {
/* 592 */             if (paramArrayOfString[3].equalsIgnoreCase("color"))
/* 593 */               return a(paramArrayOfString, EnumChatFormat.a(true, false)); 
/* 594 */             if (paramArrayOfString[3].equalsIgnoreCase("friendlyfire") || paramArrayOfString[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 595 */               return a(paramArrayOfString, new String[] { "true", "false" });
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 601 */     return null;
/*     */   }
/*     */   
/*     */   protected List a(boolean paramBoolean) {
/* 605 */     Collection collection = d().getObjectives();
/* 606 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 608 */     for (ScoreboardObjective scoreboardObjective : collection) {
/* 609 */       if (!paramBoolean || !scoreboardObjective.getCriteria().isReadOnly()) arrayList.add(scoreboardObjective.getName());
/*     */     
/*     */     } 
/* 612 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 617 */     if (paramArrayOfString[0].equalsIgnoreCase("players"))
/* 618 */       return (paramInt == 2); 
/* 619 */     if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
/* 620 */       return (paramInt == 2 || paramInt == 3);
/*     */     }
/*     */     
/* 623 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandScoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */