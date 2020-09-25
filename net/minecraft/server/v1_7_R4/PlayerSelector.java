/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class PlayerSelector
/*     */ {
/*  13 */   private static final Pattern a = Pattern.compile("^@([parf])(?:\\[([\\w=,!-]*)\\])?$");
/*  14 */   private static final Pattern b = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
/*  15 */   private static final Pattern c = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
/*     */   
/*     */   public static EntityPlayer getPlayer(ICommandListener icommandlistener, String s) {
/*  18 */     EntityPlayer[] aentityplayer = getPlayers(icommandlistener, s);
/*     */     
/*  20 */     return (aentityplayer != null && aentityplayer.length == 1) ? aentityplayer[0] : null;
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent getPlayerNames(ICommandListener icommandlistener, String s) {
/*  24 */     EntityPlayer[] aentityplayer = getPlayers(icommandlistener, s);
/*     */     
/*  26 */     if (aentityplayer != null && aentityplayer.length != 0) {
/*  27 */       IChatBaseComponent[] aichatbasecomponent = new IChatBaseComponent[aentityplayer.length];
/*     */       
/*  29 */       for (int i = 0; i < aichatbasecomponent.length; i++) {
/*  30 */         aichatbasecomponent[i] = aentityplayer[i].getScoreboardDisplayName();
/*     */       }
/*     */       
/*  33 */       return CommandAbstract.a(aichatbasecomponent);
/*     */     } 
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayer[] getPlayers(ICommandListener icommandlistener, String s) {
/*  41 */     if (!(icommandlistener instanceof CommandBlockListenerAbstract)) {
/*  42 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  46 */     Matcher matcher = a.matcher(s);
/*     */     
/*  48 */     if (matcher.matches()) {
/*  49 */       Map map = h(matcher.group(2));
/*  50 */       String s1 = matcher.group(1);
/*  51 */       int i = c(s1);
/*  52 */       int j = d(s1);
/*  53 */       int k = f(s1);
/*  54 */       int l = e(s1);
/*  55 */       int i1 = g(s1);
/*  56 */       int j1 = EnumGamemode.NONE.getId();
/*  57 */       ChunkCoordinates chunkcoordinates = icommandlistener.getChunkCoordinates();
/*  58 */       Map map1 = a(map);
/*  59 */       String s2 = null;
/*  60 */       String s3 = null;
/*  61 */       boolean flag = false;
/*     */       
/*  63 */       if (map.containsKey("rm")) {
/*  64 */         i = MathHelper.a((String)map.get("rm"), i);
/*  65 */         flag = true;
/*     */       } 
/*     */       
/*  68 */       if (map.containsKey("r")) {
/*  69 */         j = MathHelper.a((String)map.get("r"), j);
/*  70 */         flag = true;
/*     */       } 
/*     */       
/*  73 */       if (map.containsKey("lm")) {
/*  74 */         k = MathHelper.a((String)map.get("lm"), k);
/*     */       }
/*     */       
/*  77 */       if (map.containsKey("l")) {
/*  78 */         l = MathHelper.a((String)map.get("l"), l);
/*     */       }
/*     */       
/*  81 */       if (map.containsKey("x")) {
/*  82 */         chunkcoordinates.x = MathHelper.a((String)map.get("x"), chunkcoordinates.x);
/*  83 */         flag = true;
/*     */       } 
/*     */       
/*  86 */       if (map.containsKey("y")) {
/*  87 */         chunkcoordinates.y = MathHelper.a((String)map.get("y"), chunkcoordinates.y);
/*  88 */         flag = true;
/*     */       } 
/*     */       
/*  91 */       if (map.containsKey("z")) {
/*  92 */         chunkcoordinates.z = MathHelper.a((String)map.get("z"), chunkcoordinates.z);
/*  93 */         flag = true;
/*     */       } 
/*     */       
/*  96 */       if (map.containsKey("m")) {
/*  97 */         j1 = MathHelper.a((String)map.get("m"), j1);
/*     */       }
/*     */       
/* 100 */       if (map.containsKey("c")) {
/* 101 */         i1 = MathHelper.a((String)map.get("c"), i1);
/*     */       }
/*     */       
/* 104 */       if (map.containsKey("team")) {
/* 105 */         s3 = (String)map.get("team");
/*     */       }
/*     */       
/* 108 */       if (map.containsKey("name")) {
/* 109 */         s2 = (String)map.get("name");
/*     */       }
/*     */       
/* 112 */       World world = flag ? icommandlistener.getWorld() : null;
/*     */ 
/*     */       
/* 115 */       if (!s1.equals("p") && !s1.equals("a")) {
/* 116 */         if (s1.equals("r")) {
/* 117 */           List<?> list1 = MinecraftServer.getServer().getPlayerList().a(chunkcoordinates, i, j, 0, j1, k, l, map1, s2, s3, world);
/* 118 */           Collections.shuffle(list1);
/* 119 */           list1 = list1.subList(0, Math.min(i1, list1.size()));
/* 120 */           return list1.isEmpty() ? new EntityPlayer[0] : list1.<EntityPlayer>toArray(new EntityPlayer[list1.size()]);
/*     */         } 
/* 122 */         return null;
/*     */       } 
/*     */       
/* 125 */       List list = MinecraftServer.getServer().getPlayerList().a(chunkcoordinates, i, j, i1, j1, k, l, map1, s2, s3, world);
/* 126 */       return list.isEmpty() ? new EntityPlayer[0] : (EntityPlayer[])list.toArray((Object[])new EntityPlayer[list.size()]);
/*     */     } 
/*     */     
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map a(Map map) {
/* 134 */     HashMap<Object, Object> hashmap = new HashMap<Object, Object>();
/* 135 */     Iterator<String> iterator = map.keySet().iterator();
/*     */     
/* 137 */     while (iterator.hasNext()) {
/* 138 */       String s = iterator.next();
/*     */       
/* 140 */       if (s.startsWith("score_") && s.length() > "score_".length()) {
/* 141 */         String s1 = s.substring("score_".length());
/*     */         
/* 143 */         hashmap.put(s1, Integer.valueOf(MathHelper.a((String)map.get(s), 1)));
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     return hashmap;
/*     */   }
/*     */   
/*     */   public static boolean isList(String s) {
/* 151 */     Matcher matcher = a.matcher(s);
/*     */     
/* 153 */     if (matcher.matches()) {
/* 154 */       Map map = h(matcher.group(2));
/* 155 */       String s1 = matcher.group(1);
/* 156 */       int i = g(s1);
/*     */       
/* 158 */       if (map.containsKey("c")) {
/* 159 */         i = MathHelper.a((String)map.get("c"), i);
/*     */       }
/*     */       
/* 162 */       return (i != 1);
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPattern(String s, String s1) {
/* 169 */     Matcher matcher = a.matcher(s);
/*     */     
/* 171 */     if (matcher.matches()) {
/* 172 */       String s2 = matcher.group(1);
/*     */       
/* 174 */       return (s1 == null || s1.equals(s2));
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPattern(String s) {
/* 181 */     return isPattern(s, (String)null);
/*     */   }
/*     */   
/*     */   private static final int c(String s) {
/* 185 */     return 0;
/*     */   }
/*     */   
/*     */   private static final int d(String s) {
/* 189 */     return 0;
/*     */   }
/*     */   
/*     */   private static final int e(String s) {
/* 193 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   private static final int f(String s) {
/* 197 */     return 0;
/*     */   }
/*     */   
/*     */   private static final int g(String s) {
/* 201 */     return s.equals("a") ? 0 : 1;
/*     */   }
/*     */   
/*     */   private static Map h(String s) {
/* 205 */     HashMap<Object, Object> hashmap = new HashMap<Object, Object>();
/*     */     
/* 207 */     if (s == null) {
/* 208 */       return hashmap;
/*     */     }
/* 210 */     Matcher matcher = b.matcher(s);
/* 211 */     int i = 0;
/*     */     
/*     */     int j;
/*     */     
/* 215 */     for (j = -1; matcher.find(); j = matcher.end()) {
/* 216 */       String s1 = null;
/*     */       
/* 218 */       switch (i++) {
/*     */         case 0:
/* 220 */           s1 = "x";
/*     */           break;
/*     */         
/*     */         case 1:
/* 224 */           s1 = "y";
/*     */           break;
/*     */         
/*     */         case 2:
/* 228 */           s1 = "z";
/*     */           break;
/*     */         
/*     */         case 3:
/* 232 */           s1 = "r";
/*     */           break;
/*     */       } 
/* 235 */       if (s1 != null && matcher.group(1).length() > 0) {
/* 236 */         hashmap.put(s1, matcher.group(1));
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if (j < s.length()) {
/* 241 */       matcher = c.matcher((j == -1) ? s : s.substring(j));
/*     */       
/* 243 */       while (matcher.find()) {
/* 244 */         hashmap.put(matcher.group(1), matcher.group(2));
/*     */       }
/*     */     } 
/*     */     
/* 248 */     return hashmap;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */