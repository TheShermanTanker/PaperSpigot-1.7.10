/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.common.base.Charsets;
/*     */ import net.minecraft.util.com.google.common.collect.Iterators;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.io.Files;
/*     */ import net.minecraft.util.com.google.gson.Gson;
/*     */ import net.minecraft.util.com.google.gson.GsonBuilder;
/*     */ import net.minecraft.util.com.google.gson.JsonSyntaxException;
/*     */ import net.minecraft.util.com.mojang.authlib.Agent;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class UserCache {
/*  33 */   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*  34 */   private final Map c = Maps.newHashMap();
/*  35 */   private final Map d = Maps.newHashMap();
/*  36 */   private final LinkedList e = Lists.newLinkedList();
/*     */   private final MinecraftServer f;
/*     */   protected final Gson b;
/*     */   private final File g;
/*  40 */   private static final ParameterizedType h = new UserCacheEntryType();
/*     */   
/*     */   public UserCache(MinecraftServer minecraftserver, File file1) {
/*  43 */     this.f = minecraftserver;
/*  44 */     this.g = file1;
/*  45 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*     */     
/*  47 */     gsonbuilder.registerTypeHierarchyAdapter(UserCacheEntry.class, new BanEntrySerializer(this, (GameProfileLookup)null));
/*  48 */     this.b = gsonbuilder.create();
/*  49 */     b();
/*     */   }
/*     */   
/*     */   private static GameProfile a(MinecraftServer minecraftserver, String s) {
/*  53 */     GameProfile[] agameprofile = new GameProfile[1];
/*  54 */     GameProfileLookup gameprofilelookup = new GameProfileLookup(agameprofile);
/*     */     
/*  56 */     minecraftserver.getGameProfileRepository().findProfilesByNames(new String[] { s }, Agent.MINECRAFT, gameprofilelookup);
/*  57 */     if (!minecraftserver.getOnlineMode() && agameprofile[0] == null) {
/*  58 */       UUID uuid = EntityHuman.a(new GameProfile((UUID)null, s));
/*  59 */       GameProfile gameprofile = new GameProfile(uuid, s);
/*     */       
/*  61 */       gameprofilelookup.onProfileLookupSucceeded(gameprofile);
/*     */     } 
/*     */     
/*  64 */     return agameprofile[0];
/*     */   }
/*     */   
/*     */   public void a(GameProfile gameprofile) {
/*  68 */     a(gameprofile, (Date)null);
/*     */   }
/*     */   
/*     */   private void a(GameProfile gameprofile, Date date) {
/*  72 */     UUID uuid = gameprofile.getId();
/*     */     
/*  74 */     if (date == null) {
/*  75 */       Calendar calendar = Calendar.getInstance();
/*     */       
/*  77 */       calendar.setTime(new Date());
/*  78 */       calendar.add(2, 1);
/*  79 */       date = calendar.getTime();
/*     */     } 
/*     */     
/*  82 */     String s = gameprofile.getName().toLowerCase(Locale.ROOT);
/*  83 */     UserCacheEntry usercacheentry = new UserCacheEntry(this, gameprofile, date, (GameProfileLookup)null);
/*  84 */     LinkedList linkedlist = this.e;
/*     */     
/*  86 */     synchronized (this.e) {
/*  87 */       if (this.d.containsKey(uuid)) {
/*  88 */         UserCacheEntry usercacheentry1 = (UserCacheEntry)this.d.get(uuid);
/*     */         
/*  90 */         this.c.remove(usercacheentry1.a().getName().toLowerCase(Locale.ROOT));
/*  91 */         this.c.put(gameprofile.getName().toLowerCase(Locale.ROOT), usercacheentry);
/*  92 */         this.e.remove(gameprofile);
/*     */       } else {
/*  94 */         this.d.put(uuid, usercacheentry);
/*  95 */         this.c.put(s, usercacheentry);
/*     */       } 
/*     */       
/*  98 */       this.e.addFirst(gameprofile);
/*     */     } 
/*     */   }
/*     */   
/*     */   public GameProfile getProfile(String s) {
/* 103 */     String s1 = s.toLowerCase(Locale.ROOT);
/* 104 */     UserCacheEntry usercacheentry = (UserCacheEntry)this.c.get(s1);
/*     */     
/* 106 */     if (usercacheentry != null && (new Date()).getTime() >= UserCacheEntry.a(usercacheentry).getTime()) {
/* 107 */       this.d.remove(usercacheentry.a().getId());
/* 108 */       this.c.remove(usercacheentry.a().getName().toLowerCase(Locale.ROOT));
/* 109 */       LinkedList linkedlist = this.e;
/*     */       
/* 111 */       synchronized (this.e) {
/* 112 */         this.e.remove(usercacheentry.a());
/*     */       } 
/*     */       
/* 115 */       usercacheentry = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (usercacheentry != null) {
/* 121 */       GameProfile gameprofile = usercacheentry.a();
/* 122 */       LinkedList linkedlist1 = this.e;
/*     */       
/* 124 */       synchronized (this.e) {
/* 125 */         this.e.remove(gameprofile);
/* 126 */         this.e.addFirst(gameprofile);
/*     */       } 
/*     */     } else {
/* 129 */       GameProfile gameprofile = a(this.f, s);
/* 130 */       if (gameprofile != null) {
/* 131 */         a(gameprofile);
/* 132 */         usercacheentry = (UserCacheEntry)this.c.get(s1);
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     if (!SpigotConfig.saveUserCacheOnStopOnly) c(); 
/* 137 */     return (usercacheentry == null) ? null : usercacheentry.a();
/*     */   }
/*     */   
/*     */   public String[] a() {
/* 141 */     ArrayList arraylist = Lists.newArrayList(this.c.keySet());
/*     */     
/* 143 */     return (String[])arraylist.toArray((Object[])new String[arraylist.size()]);
/*     */   }
/*     */   
/*     */   public GameProfile a(UUID uuid) {
/* 147 */     UserCacheEntry usercacheentry = (UserCacheEntry)this.d.get(uuid);
/*     */     
/* 149 */     return (usercacheentry == null) ? null : usercacheentry.a();
/*     */   }
/*     */   
/*     */   private UserCacheEntry b(UUID uuid) {
/* 153 */     UserCacheEntry usercacheentry = (UserCacheEntry)this.d.get(uuid);
/*     */     
/* 155 */     if (usercacheentry != null) {
/* 156 */       GameProfile gameprofile = usercacheentry.a();
/* 157 */       LinkedList linkedlist = this.e;
/*     */       
/* 159 */       synchronized (this.e) {
/* 160 */         this.e.remove(gameprofile);
/* 161 */         this.e.addFirst(gameprofile);
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     return usercacheentry;
/*     */   }
/*     */   
/*     */   public void b() {
/* 169 */     List list = null;
/* 170 */     BufferedReader bufferedreader = null;
/*     */ 
/*     */     
/*     */     try {
/* 174 */       bufferedreader = Files.newReader(this.g, Charsets.UTF_8);
/* 175 */       list = (List)this.b.fromJson(bufferedreader, h);
/*     */     }
/* 177 */     catch (FileNotFoundException filenotfoundexception) {
/*     */ 
/*     */     
/* 180 */     } catch (JsonSyntaxException ex) {
/* 181 */       JsonList.a.warn("Usercache.json is corrupted or has bad formatting. Deleting it to prevent further issues.");
/* 182 */       this.g.delete();
/*     */     } finally {
/*     */       
/* 185 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (list != null) {
/* 192 */       this.c.clear();
/* 193 */       this.d.clear();
/* 194 */       LinkedList linkedlist = this.e;
/*     */       
/* 196 */       synchronized (this.e) {
/* 197 */         this.e.clear();
/*     */       } 
/*     */       
/* 200 */       list = Lists.reverse(list);
/* 201 */       Iterator<UserCacheEntry> iterator = list.iterator();
/*     */       
/* 203 */       while (iterator.hasNext()) {
/* 204 */         UserCacheEntry usercacheentry = iterator.next();
/*     */         
/* 206 */         if (usercacheentry != null) {
/* 207 */           a(usercacheentry.a(), usercacheentry.b());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void c() {
/* 214 */     String s = this.b.toJson(a(SpigotConfig.userCacheCap));
/* 215 */     BufferedWriter bufferedwriter = null;
/*     */     
/*     */     try {
/* 218 */       bufferedwriter = Files.newWriter(this.g, Charsets.UTF_8);
/* 219 */       bufferedwriter.write(s);
/*     */       return;
/* 221 */     } catch (FileNotFoundException filenotfoundexception) {
/*     */       return;
/* 223 */     } catch (IOException ioexception) {
/*     */     
/*     */     } finally {
/* 226 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */   private List a(int i) {
/*     */     ArrayList arraylist1;
/* 231 */     ArrayList<UserCacheEntry> arraylist = Lists.newArrayList();
/* 232 */     LinkedList linkedlist = this.e;
/*     */ 
/*     */     
/* 235 */     synchronized (this.e) {
/* 236 */       arraylist1 = Lists.newArrayList(Iterators.limit(this.e.iterator(), i));
/*     */     } 
/*     */     
/* 239 */     Iterator<GameProfile> iterator = arraylist1.iterator();
/*     */     
/* 241 */     while (iterator.hasNext()) {
/* 242 */       GameProfile gameprofile = iterator.next();
/* 243 */       UserCacheEntry usercacheentry = b(gameprofile.getId());
/*     */       
/* 245 */       if (usercacheentry != null) {
/* 246 */         arraylist.add(usercacheentry);
/*     */       }
/*     */     } 
/*     */     
/* 250 */     return arraylist;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\UserCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */