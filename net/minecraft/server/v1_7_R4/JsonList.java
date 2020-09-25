/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import net.minecraft.util.com.google.common.base.Charsets;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.io.Files;
/*     */ import net.minecraft.util.com.google.gson.Gson;
/*     */ import net.minecraft.util.com.google.gson.GsonBuilder;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonSyntaxException;
/*     */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ 
/*     */ public class JsonList {
/*  26 */   protected static final Logger a = LogManager.getLogger();
/*     */   protected final Gson b;
/*     */   private final File c;
/*  29 */   private final Map d = Maps.newHashMap();
/*     */   private boolean e = true;
/*  31 */   private static final ParameterizedType f = new JsonListType();
/*     */   
/*     */   public JsonList(File file1) {
/*  34 */     this.c = file1;
/*  35 */     GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
/*     */     
/*  37 */     gsonbuilder.registerTypeHierarchyAdapter(JsonListEntry.class, new JsonListEntrySerializer(this, (JsonListType)null));
/*  38 */     this.b = gsonbuilder.create();
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  42 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/*  46 */     this.e = flag;
/*     */   }
/*     */   
/*     */   public File c() {
/*  50 */     return this.c;
/*     */   }
/*     */   
/*     */   public void add(JsonListEntry jsonlistentry) {
/*  54 */     this.d.put(a(jsonlistentry.getKey()), jsonlistentry);
/*     */     
/*     */     try {
/*  57 */       save();
/*  58 */     } catch (IOException ioexception) {
/*  59 */       a.warn("Could not save the list after adding a user.", ioexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JsonListEntry get(Object object) {
/*  64 */     h();
/*  65 */     return (JsonListEntry)this.d.get(a(object));
/*     */   }
/*     */   
/*     */   public void remove(Object object) {
/*  69 */     this.d.remove(a(object));
/*     */     
/*     */     try {
/*  72 */       save();
/*  73 */     } catch (IOException ioexception) {
/*  74 */       a.warn("Could not save the list after removing a user.", ioexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String[] getEntries() {
/*  79 */     return (String[])this.d.keySet().toArray((Object[])new String[this.d.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<JsonListEntry> getValues() {
/*  84 */     return this.d.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  89 */     return (this.d.size() < 1);
/*     */   }
/*     */   
/*     */   protected String a(Object object) {
/*  93 */     return object.toString();
/*     */   }
/*     */   
/*     */   protected boolean d(Object object) {
/*  97 */     return this.d.containsKey(a(object));
/*     */   }
/*     */   
/*     */   private void h() {
/* 101 */     ArrayList<Object> arraylist = Lists.newArrayList();
/* 102 */     Iterator<JsonListEntry> iterator = this.d.values().iterator();
/*     */     
/* 104 */     while (iterator.hasNext()) {
/* 105 */       JsonListEntry jsonlistentry = iterator.next();
/*     */       
/* 107 */       if (jsonlistentry.hasExpired()) {
/* 108 */         arraylist.add(jsonlistentry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 112 */     iterator = arraylist.iterator();
/*     */     
/* 114 */     while (iterator.hasNext()) {
/* 115 */       Object object = iterator.next();
/*     */       
/* 117 */       this.d.remove(object);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected JsonListEntry a(JsonObject jsonobject) {
/* 122 */     return new JsonListEntry(null, jsonobject);
/*     */   }
/*     */   
/*     */   protected Map e() {
/* 126 */     return this.d;
/*     */   }
/*     */   
/*     */   public void save() throws IOException {
/* 130 */     Collection collection = this.d.values();
/* 131 */     String s = this.b.toJson(collection);
/* 132 */     BufferedWriter bufferedwriter = null;
/*     */     
/*     */     try {
/* 135 */       bufferedwriter = Files.newWriter(this.c, Charsets.UTF_8);
/* 136 */       bufferedwriter.write(s);
/*     */     } finally {
/* 138 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void load() throws IOException {
/* 143 */     Collection collection = null;
/* 144 */     BufferedReader bufferedreader = null;
/*     */     
/*     */     try {
/* 147 */       bufferedreader = Files.newReader(this.c, Charsets.UTF_8);
/* 148 */       collection = (Collection)this.b.fromJson(bufferedreader, f);
/*     */     }
/* 150 */     catch (FileNotFoundException ex) {
/*     */       
/* 152 */       Bukkit.getLogger().log(Level.INFO, "Unable to find file {0}, creating it.", this.c);
/* 153 */     } catch (JsonSyntaxException ex) {
/*     */       
/* 155 */       Bukkit.getLogger().log(Level.WARNING, "Unable to read file {0}, backing it up to {0}.backup and creating new copy.", this.c);
/* 156 */       File backup = new File(this.c + ".backup");
/* 157 */       this.c.renameTo(backup);
/* 158 */       this.c.delete();
/*     */     } finally {
/*     */       
/* 161 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */     
/* 164 */     if (collection != null) {
/* 165 */       this.d.clear();
/* 166 */       Iterator<JsonListEntry> iterator = collection.iterator();
/*     */       
/* 168 */       while (iterator.hasNext()) {
/* 169 */         JsonListEntry jsonlistentry = iterator.next();
/*     */         
/* 171 */         if (jsonlistentry.getKey() != null)
/* 172 */           this.d.put(a(jsonlistentry.getKey()), jsonlistentry); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\JsonList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */