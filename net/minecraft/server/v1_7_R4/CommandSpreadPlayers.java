/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.collect.Sets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSpreadPlayers
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand() {
/*  24 */     return "spreadplayers";
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String c(ICommandListener paramICommandListener) {
/*  34 */     return "commands.spreadplayers.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  39 */     if (paramArrayOfString.length < 6) throw new ExceptionUsage("commands.spreadplayers.usage", new Object[0]); 
/*  40 */     byte b = 0;
/*  41 */     double d1 = a(paramICommandListener, Double.NaN, paramArrayOfString[b++]);
/*  42 */     double d2 = a(paramICommandListener, Double.NaN, paramArrayOfString[b++]);
/*  43 */     double d3 = a(paramICommandListener, paramArrayOfString[b++], 0.0D);
/*  44 */     double d4 = a(paramICommandListener, paramArrayOfString[b++], d3 + 1.0D);
/*  45 */     boolean bool = c(paramICommandListener, paramArrayOfString[b++]);
/*     */     
/*  47 */     ArrayList<? super EntityPlayer> arrayList = Lists.newArrayList();
/*     */     
/*  49 */     while (b < paramArrayOfString.length) {
/*  50 */       String str = paramArrayOfString[b++];
/*     */       
/*  52 */       if (PlayerSelector.isPattern(str)) {
/*  53 */         EntityPlayer[] arrayOfEntityPlayer = PlayerSelector.getPlayers(paramICommandListener, str);
/*     */         
/*  55 */         if (arrayOfEntityPlayer != null && arrayOfEntityPlayer.length != 0) {
/*  56 */           Collections.addAll(arrayList, arrayOfEntityPlayer); continue;
/*     */         } 
/*  58 */         throw new ExceptionPlayerNotFound();
/*     */       } 
/*     */       
/*  61 */       EntityPlayer entityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(str);
/*     */       
/*  63 */       if (entityPlayer != null) {
/*  64 */         arrayList.add(entityPlayer); continue;
/*     */       } 
/*  66 */       throw new ExceptionPlayerNotFound();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (arrayList.isEmpty()) {
/*  72 */       throw new ExceptionPlayerNotFound();
/*     */     }
/*     */     
/*  75 */     paramICommandListener.sendMessage(new ChatMessage("commands.spreadplayers.spreading." + (bool ? "teams" : "players"), new Object[] { Integer.valueOf(arrayList.size()), Double.valueOf(d4), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3) }));
/*     */     
/*  77 */     a(paramICommandListener, arrayList, new Location2D(d1, d2), d3, d4, ((EntityLiving)arrayList.get(0)).world, bool);
/*     */   }
/*     */   
/*     */   private void a(ICommandListener paramICommandListener, List paramList, Location2D paramLocation2D, double paramDouble1, double paramDouble2, World paramWorld, boolean paramBoolean) {
/*  81 */     Random random = new Random();
/*  82 */     double d1 = paramLocation2D.a - paramDouble2;
/*  83 */     double d2 = paramLocation2D.b - paramDouble2;
/*  84 */     double d3 = paramLocation2D.a + paramDouble2;
/*  85 */     double d4 = paramLocation2D.b + paramDouble2;
/*     */     
/*  87 */     Location2D[] arrayOfLocation2D = a(random, paramBoolean ? a(paramList) : paramList.size(), d1, d2, d3, d4);
/*  88 */     int i = a(paramLocation2D, paramDouble1, paramWorld, random, d1, d2, d3, d4, arrayOfLocation2D, paramBoolean);
/*  89 */     double d5 = a(paramList, paramWorld, arrayOfLocation2D, paramBoolean);
/*     */     
/*  91 */     a(paramICommandListener, this, "commands.spreadplayers.success." + (paramBoolean ? "teams" : "players"), new Object[] { Integer.valueOf(arrayOfLocation2D.length), Double.valueOf(paramLocation2D.a), Double.valueOf(paramLocation2D.b) });
/*  92 */     if (arrayOfLocation2D.length > 1) paramICommandListener.sendMessage(new ChatMessage("commands.spreadplayers.info." + (paramBoolean ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d5) }), Integer.valueOf(i) })); 
/*     */   }
/*     */   
/*     */   private int a(List paramList) {
/*  96 */     HashSet<ScoreboardTeamBase> hashSet = Sets.newHashSet();
/*     */     
/*  98 */     for (EntityLiving entityLiving : paramList) {
/*  99 */       if (entityLiving instanceof EntityHuman) {
/* 100 */         hashSet.add(entityLiving.getScoreboardTeam()); continue;
/*     */       } 
/* 102 */       hashSet.add(null);
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return hashSet.size();
/*     */   }
/*     */   
/*     */   private int a(Location2D paramLocation2D, double paramDouble1, World paramWorld, Random paramRandom, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, Location2D[] paramArrayOfLocation2D, boolean paramBoolean) {
/* 110 */     boolean bool = true;
/*     */     
/* 112 */     double d = 3.4028234663852886E38D;
/*     */     byte b;
/* 114 */     for (b = 0; b < '✐' && bool; b++) {
/* 115 */       bool = false;
/* 116 */       d = 3.4028234663852886E38D;
/*     */       
/* 118 */       for (byte b1 = 0; b1 < paramArrayOfLocation2D.length; b1++) {
/* 119 */         Location2D location2D1 = paramArrayOfLocation2D[b1];
/* 120 */         byte b2 = 0;
/* 121 */         Location2D location2D2 = new Location2D();
/*     */         
/* 123 */         for (byte b3 = 0; b3 < paramArrayOfLocation2D.length; b3++) {
/* 124 */           if (b1 != b3) {
/* 125 */             Location2D location2D = paramArrayOfLocation2D[b3];
/*     */             
/* 127 */             double d1 = location2D1.a(location2D);
/* 128 */             d = Math.min(d1, d);
/* 129 */             if (d1 < paramDouble1) {
/* 130 */               b2++;
/* 131 */               location2D2.a += location2D.a - location2D1.a;
/* 132 */               location2D2.b += location2D.b - location2D1.b;
/*     */             } 
/*     */           } 
/*     */         } 
/* 136 */         if (b2 > 0) {
/* 137 */           location2D2.a /= b2;
/* 138 */           location2D2.b /= b2;
/* 139 */           double d1 = location2D2.b();
/*     */           
/* 141 */           if (d1 > 0.0D) {
/* 142 */             location2D2.a();
/*     */             
/* 144 */             location2D1.b(location2D2);
/*     */           } else {
/* 146 */             location2D1.a(paramRandom, paramDouble2, paramDouble3, paramDouble4, paramDouble5);
/*     */           } 
/*     */           
/* 149 */           bool = true;
/*     */         } 
/*     */         
/* 152 */         if (location2D1.a(paramDouble2, paramDouble3, paramDouble4, paramDouble5)) {
/* 153 */           bool = true;
/*     */         }
/*     */       } 
/*     */       
/* 157 */       if (!bool) {
/* 158 */         for (Location2D location2D : paramArrayOfLocation2D) {
/* 159 */           if (!location2D.b(paramWorld)) {
/* 160 */             location2D.a(paramRandom, paramDouble2, paramDouble3, paramDouble4, paramDouble5);
/* 161 */             bool = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 167 */     if (b >= '✐') {
/* 168 */       throw new CommandException("commands.spreadplayers.failure." + (paramBoolean ? "teams" : "players"), new Object[] { Integer.valueOf(paramArrayOfLocation2D.length), Double.valueOf(paramLocation2D.a), Double.valueOf(paramLocation2D.b), String.format("%.2f", new Object[] { Double.valueOf(d) }) });
/*     */     }
/*     */     
/* 171 */     return b;
/*     */   }
/*     */   
/*     */   private double a(List<EntityLiving> paramList, World paramWorld, Location2D[] paramArrayOfLocation2D, boolean paramBoolean) {
/* 175 */     double d = 0.0D;
/* 176 */     byte b1 = 0;
/* 177 */     HashMap<ScoreboardTeamBase, Location2D> hashMap = Maps.newHashMap();
/*     */     
/* 179 */     for (byte b2 = 0; b2 < paramList.size(); b2++) {
/* 180 */       Location2D location2D; EntityLiving entityLiving = paramList.get(b2);
/*     */ 
/*     */       
/* 183 */       if (paramBoolean) {
/* 184 */         ScoreboardTeamBase scoreboardTeamBase = (entityLiving instanceof EntityHuman) ? entityLiving.getScoreboardTeam() : null;
/*     */         
/* 186 */         if (!hashMap.containsKey(scoreboardTeamBase)) {
/* 187 */           hashMap.put(scoreboardTeamBase, paramArrayOfLocation2D[b1++]);
/*     */         }
/*     */         
/* 190 */         location2D = hashMap.get(scoreboardTeamBase);
/*     */       } else {
/* 192 */         location2D = paramArrayOfLocation2D[b1++];
/*     */       } 
/*     */       
/* 195 */       entityLiving.enderTeleportTo((MathHelper.floor(location2D.a) + 0.5F), location2D.a(paramWorld), MathHelper.floor(location2D.b) + 0.5D);
/*     */       
/* 197 */       double d1 = Double.MAX_VALUE;
/* 198 */       for (byte b = 0; b < paramArrayOfLocation2D.length; b++) {
/* 199 */         if (location2D != paramArrayOfLocation2D[b]) {
/*     */           
/* 201 */           double d2 = location2D.a(paramArrayOfLocation2D[b]);
/* 202 */           d1 = Math.min(d2, d1);
/*     */         } 
/* 204 */       }  d += d1;
/*     */     } 
/*     */     
/* 207 */     d /= paramList.size();
/* 208 */     return d;
/*     */   }
/*     */   
/*     */   private Location2D[] a(Random paramRandom, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 212 */     Location2D[] arrayOfLocation2D = new Location2D[paramInt];
/*     */     
/* 214 */     for (byte b = 0; b < arrayOfLocation2D.length; b++) {
/* 215 */       Location2D location2D = new Location2D();
/*     */       
/* 217 */       location2D.a(paramRandom, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */       
/* 219 */       arrayOfLocation2D[b] = location2D;
/*     */     } 
/*     */     
/* 222 */     return arrayOfLocation2D;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSpreadPlayers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */