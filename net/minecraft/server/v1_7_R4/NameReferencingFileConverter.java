/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.common.base.Charsets;
/*     */ import net.minecraft.util.com.google.common.collect.Iterators;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.io.Files;
/*     */ import net.minecraft.util.com.mojang.authlib.Agent;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameReferencingFileConverter
/*     */ {
/*  32 */   private static final Logger e = LogManager.getLogger();
/*  33 */   public static final File a = new File("banned-ips.txt");
/*  34 */   public static final File b = new File("banned-players.txt");
/*  35 */   public static final File c = new File("ops.txt");
/*  36 */   public static final File d = new File("white-list.txt");
/*     */   
/*     */   static List a(File file1, Map<String, String[]> map) throws IOException {
/*  39 */     List list = Files.readLines(file1, Charsets.UTF_8);
/*  40 */     Iterator<String> iterator = list.iterator();
/*     */     
/*  42 */     while (iterator.hasNext()) {
/*  43 */       String s = iterator.next();
/*     */       
/*  45 */       s = s.trim();
/*  46 */       if (!s.startsWith("#") && s.length() >= 1) {
/*  47 */         String[] astring = s.split("\\|");
/*     */         
/*  49 */         map.put(astring[0].toLowerCase(Locale.ROOT), astring);
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     return list;
/*     */   }
/*     */   
/*     */   private static void a(MinecraftServer minecraftserver, Collection collection, ProfileLookupCallback profilelookupcallback) {
/*  57 */     String[] astring = (String[])Iterators.toArray((Iterator)Iterators.filter(collection.iterator(), new PredicateEmptyList()), String.class);
/*     */     
/*  59 */     if (minecraftserver.getOnlineMode() || SpigotConfig.bungee) {
/*  60 */       minecraftserver.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, profilelookupcallback);
/*     */     } else {
/*  62 */       String[] astring1 = astring;
/*  63 */       int i = astring.length;
/*     */       
/*  65 */       for (int j = 0; j < i; j++) {
/*  66 */         String s = astring1[j];
/*  67 */         UUID uuid = EntityHuman.a(new GameProfile((UUID)null, s));
/*  68 */         GameProfile gameprofile = new GameProfile(uuid, s);
/*     */         
/*  70 */         profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean a(MinecraftServer minecraftserver) {
/*  76 */     GameProfileBanList gameprofilebanlist = new GameProfileBanList(PlayerList.a);
/*     */     
/*  78 */     if (b.exists() && b.isFile()) {
/*  79 */       if (gameprofilebanlist.c().exists()) {
/*     */         try {
/*  81 */           gameprofilebanlist.load();
/*     */         }
/*  83 */         catch (IOException filenotfoundexception) {
/*  84 */           e.warn("Could not load existing file " + gameprofilebanlist.c().getName() + ", " + filenotfoundexception.getMessage());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  90 */         HashMap hashmap = Maps.newHashMap();
/*     */         
/*  92 */         a(b, hashmap);
/*  93 */         GameProfileBanListEntryConverter gameprofilebanlistentryconverter = new GameProfileBanListEntryConverter(minecraftserver, hashmap, gameprofilebanlist);
/*     */         
/*  95 */         a(minecraftserver, hashmap.keySet(), gameprofilebanlistentryconverter);
/*  96 */         gameprofilebanlist.save();
/*  97 */         c(b);
/*  98 */         return true;
/*  99 */       } catch (IOException ioexception) {
/* 100 */         e.warn("Could not read old user banlist to convert it!", ioexception);
/* 101 */         return false;
/* 102 */       } catch (FileConversionException fileconversionexception) {
/* 103 */         e.error("Conversion failed, please try again later", fileconversionexception);
/* 104 */         return false;
/*     */       } 
/*     */     } 
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean b(MinecraftServer minecraftserver) {
/* 112 */     IpBanList ipbanlist = new IpBanList(PlayerList.b);
/*     */     
/* 114 */     if (a.exists() && a.isFile()) {
/* 115 */       if (ipbanlist.c().exists()) {
/*     */         try {
/* 117 */           ipbanlist.load();
/*     */         }
/* 119 */         catch (IOException filenotfoundexception) {
/* 120 */           e.warn("Could not load existing file " + ipbanlist.c().getName() + ", " + filenotfoundexception.getMessage());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 126 */         HashMap hashmap = Maps.newHashMap();
/*     */         
/* 128 */         a(a, hashmap);
/* 129 */         Iterator<String> iterator = hashmap.keySet().iterator();
/*     */         
/* 131 */         while (iterator.hasNext()) {
/* 132 */           String s = iterator.next();
/* 133 */           String[] astring = (String[])hashmap.get(s);
/* 134 */           Date date = (astring.length > 1) ? b(astring[1], (Date)null) : null;
/* 135 */           String s1 = (astring.length > 2) ? astring[2] : null;
/* 136 */           Date date1 = (astring.length > 3) ? b(astring[3], (Date)null) : null;
/* 137 */           String s2 = (astring.length > 4) ? astring[4] : null;
/*     */           
/* 139 */           ipbanlist.add(new IpBanEntry(s, date, s1, date1, s2));
/*     */         } 
/*     */         
/* 142 */         ipbanlist.save();
/* 143 */         c(a);
/* 144 */         return true;
/* 145 */       } catch (IOException ioexception) {
/* 146 */         e.warn("Could not parse old ip banlist to convert it!", ioexception);
/* 147 */         return false;
/*     */       } 
/*     */     } 
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean c(MinecraftServer minecraftserver) {
/* 155 */     OpList oplist = new OpList(PlayerList.c);
/*     */     
/* 157 */     if (c.exists() && c.isFile()) {
/* 158 */       if (oplist.c().exists()) {
/*     */         try {
/* 160 */           oplist.load();
/*     */         }
/* 162 */         catch (IOException filenotfoundexception) {
/* 163 */           e.warn("Could not load existing file " + oplist.c().getName() + ", " + filenotfoundexception.getMessage());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 169 */         List list = Files.readLines(c, Charsets.UTF_8);
/* 170 */         OpListProfileCallback oplistprofilecallback = new OpListProfileCallback(minecraftserver, oplist);
/*     */         
/* 172 */         a(minecraftserver, list, oplistprofilecallback);
/* 173 */         oplist.save();
/* 174 */         c(c);
/* 175 */         return true;
/* 176 */       } catch (IOException ioexception) {
/* 177 */         e.warn("Could not read old oplist to convert it!", ioexception);
/* 178 */         return false;
/* 179 */       } catch (FileConversionException fileconversionexception) {
/* 180 */         e.error("Conversion failed, please try again later", fileconversionexception);
/* 181 */         return false;
/*     */       } 
/*     */     } 
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean d(MinecraftServer minecraftserver) {
/* 189 */     WhiteList whitelist = new WhiteList(PlayerList.d);
/*     */     
/* 191 */     if (d.exists() && d.isFile()) {
/* 192 */       if (whitelist.c().exists()) {
/*     */         try {
/* 194 */           whitelist.load();
/*     */         }
/* 196 */         catch (IOException filenotfoundexception) {
/* 197 */           e.warn("Could not load existing file " + whitelist.c().getName() + ", " + filenotfoundexception.getMessage());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 203 */         List list = Files.readLines(d, Charsets.UTF_8);
/* 204 */         WhiteListProfileCallback whitelistprofilecallback = new WhiteListProfileCallback(minecraftserver, whitelist);
/*     */         
/* 206 */         a(minecraftserver, list, whitelistprofilecallback);
/* 207 */         whitelist.save();
/* 208 */         c(d);
/* 209 */         return true;
/* 210 */       } catch (IOException ioexception) {
/* 211 */         e.warn("Could not read old whitelist to convert it!", ioexception);
/* 212 */         return false;
/* 213 */       } catch (FileConversionException fileconversionexception) {
/* 214 */         e.error("Conversion failed, please try again later", fileconversionexception);
/* 215 */         return false;
/*     */       } 
/*     */     } 
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String a(String s) {
/* 223 */     if (!UtilColor.b(s) && s.length() <= 16) {
/* 224 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 225 */       GameProfile gameprofile = minecraftserver.getUserCache().getProfile(s);
/*     */       
/* 227 */       if (gameprofile != null && gameprofile.getId() != null)
/* 228 */         return gameprofile.getId().toString(); 
/* 229 */       if (!minecraftserver.N() && minecraftserver.getOnlineMode()) {
/* 230 */         ArrayList<GameProfile> arraylist = Lists.newArrayList();
/* 231 */         GameProfileLookupCallback gameprofilelookupcallback = new GameProfileLookupCallback(minecraftserver, arraylist);
/*     */         
/* 233 */         a(minecraftserver, Lists.newArrayList((Object[])new String[] { s }, ), gameprofilelookupcallback);
/* 234 */         return (arraylist.size() > 0 && ((GameProfile)arraylist.get(0)).getId() != null) ? ((GameProfile)arraylist.get(0)).getId().toString() : "";
/*     */       } 
/* 236 */       return EntityHuman.a(new GameProfile((UUID)null, s)).toString();
/*     */     } 
/*     */     
/* 239 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean a(DedicatedServer dedicatedserver, PropertyManager propertymanager) {
/* 244 */     File file1 = d(propertymanager);
/* 245 */     File file2 = new File(file1.getParentFile(), "playerdata");
/* 246 */     File file3 = new File(file1.getParentFile(), "unknownplayers");
/*     */     
/* 248 */     if (file1.exists() && file1.isDirectory()) {
/* 249 */       File[] afile = file1.listFiles();
/* 250 */       ArrayList<String> arraylist = Lists.newArrayList();
/* 251 */       File[] afile1 = afile;
/* 252 */       int i = afile.length;
/*     */       
/* 254 */       for (int j = 0; j < i; j++) {
/* 255 */         File file4 = afile1[j];
/* 256 */         String s = file4.getName();
/*     */         
/* 258 */         if (s.toLowerCase(Locale.ROOT).endsWith(".dat")) {
/* 259 */           String s1 = s.substring(0, s.length() - ".dat".length());
/*     */           
/* 261 */           if (s1.length() > 0) {
/* 262 */             arraylist.add(s1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 268 */         String[] astring = arraylist.<String>toArray(new String[arraylist.size()]);
/* 269 */         PlayerDatFileConverter playerdatfileconverter = new PlayerDatFileConverter(dedicatedserver, file2, file3, file1, astring);
/*     */         
/* 271 */         a(dedicatedserver, Lists.newArrayList((Object[])astring), playerdatfileconverter);
/* 272 */         return true;
/* 273 */       } catch (FileConversionException fileconversionexception) {
/* 274 */         e.error("Conversion failed, please try again later", fileconversionexception);
/* 275 */         return false;
/*     */       } 
/*     */     } 
/* 278 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void b(File file1) {
/* 283 */     if (file1.exists()) {
/* 284 */       if (!file1.isDirectory()) {
/* 285 */         throw new FileConversionException("Can't create directory " + file1.getName() + " in world save directory.", (PredicateEmptyList)null);
/*     */       }
/* 287 */     } else if (!file1.mkdirs()) {
/* 288 */       throw new FileConversionException("Can't create directory " + file1.getName() + " in world save directory.", (PredicateEmptyList)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean a(PropertyManager propertymanager) {
/* 293 */     boolean flag = b(propertymanager);
/*     */     
/* 295 */     flag = (flag && c(propertymanager));
/* 296 */     return flag;
/*     */   }
/*     */   
/*     */   private static boolean b(PropertyManager propertymanager) {
/* 300 */     boolean flag = false;
/*     */     
/* 302 */     if (b.exists() && b.isFile()) {
/* 303 */       flag = true;
/*     */     }
/*     */     
/* 306 */     boolean flag1 = false;
/*     */     
/* 308 */     if (a.exists() && a.isFile()) {
/* 309 */       flag1 = true;
/*     */     }
/*     */     
/* 312 */     boolean flag2 = false;
/*     */     
/* 314 */     if (c.exists() && c.isFile()) {
/* 315 */       flag2 = true;
/*     */     }
/*     */     
/* 318 */     boolean flag3 = false;
/*     */     
/* 320 */     if (d.exists() && d.isFile()) {
/* 321 */       flag3 = true;
/*     */     }
/*     */     
/* 324 */     if (!flag && !flag1 && !flag2 && !flag3) {
/* 325 */       return true;
/*     */     }
/* 327 */     e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
/* 328 */     e.warn("** please remove the following files and restart the server:");
/* 329 */     if (flag) {
/* 330 */       e.warn("* " + b.getName());
/*     */     }
/*     */     
/* 333 */     if (flag1) {
/* 334 */       e.warn("* " + a.getName());
/*     */     }
/*     */     
/* 337 */     if (flag2) {
/* 338 */       e.warn("* " + c.getName());
/*     */     }
/*     */     
/* 341 */     if (flag3) {
/* 342 */       e.warn("* " + d.getName());
/*     */     }
/*     */     
/* 345 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean c(PropertyManager propertymanager) {
/* 350 */     File file1 = d(propertymanager);
/*     */     
/* 352 */     if (file1.exists() && file1.isDirectory()) {
/* 353 */       String[] astring = file1.list(new DatFilenameFilter());
/*     */       
/* 355 */       if (astring.length > 0) {
/* 356 */         e.warn("**** DETECTED OLD PLAYER FILES IN THE WORLD SAVE");
/* 357 */         e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
/* 358 */         e.warn("** please restart the server and if the problem persists, remove the directory '{}'", new Object[] { file1.getPath() });
/* 359 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 363 */     return true;
/*     */   }
/*     */   
/*     */   private static File d(PropertyManager propertymanager) {
/* 367 */     String s = propertymanager.getString("level-name", "world");
/* 368 */     File file1 = new File((MinecraftServer.getServer()).server.getWorldContainer(), s);
/*     */     
/* 370 */     return new File(file1, "players");
/*     */   }
/*     */   
/*     */   private static void c(File file1) {
/* 374 */     File file2 = new File(file1.getName() + ".converted");
/*     */     
/* 376 */     file1.renameTo(file2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Date b(String s, Date date) {
/*     */     Date date1;
/*     */     try {
/* 383 */       date1 = ExpirableListEntry.a.parse(s);
/* 384 */     } catch (ParseException parseexception) {
/* 385 */       date1 = date;
/*     */     } 
/*     */     
/* 388 */     return date1;
/*     */   }
/*     */   
/*     */   static Logger a() {
/* 392 */     return e;
/*     */   }
/*     */   
/*     */   static Date a(String s, Date date) {
/* 396 */     return b(s, date);
/*     */   }
/*     */   
/*     */   static void a(File file1) {
/* 400 */     b(file1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NameReferencingFileConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */