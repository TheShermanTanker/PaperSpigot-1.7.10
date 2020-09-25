/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ 
/*     */ 
/*     */ public class MojangStatisticsGenerator
/*     */ {
/*  16 */   private final Map a = Maps.newHashMap();
/*  17 */   private final Map b = Maps.newHashMap();
/*     */   
/*  19 */   private final String c = UUID.randomUUID().toString();
/*     */   private final URL d;
/*     */   private final IMojangStatistics e;
/*  22 */   private final Timer f = new Timer("Snooper Timer", true);
/*  23 */   private final Object g = new Object();
/*     */   private final long h;
/*     */   private boolean i;
/*     */   private int j;
/*     */   
/*     */   public MojangStatisticsGenerator(String paramString, IMojangStatistics paramIMojangStatistics, long paramLong) {
/*     */     try {
/*  30 */       this.d = new URL("http://snoop.minecraft.net/" + paramString + "?version=" + '\002');
/*  31 */     } catch (MalformedURLException malformedURLException) {
/*  32 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/*  35 */     this.e = paramIMojangStatistics;
/*  36 */     this.h = paramLong;
/*     */   }
/*     */   
/*     */   public void a() {
/*  40 */     if (this.i)
/*  41 */       return;  this.i = true;
/*     */     
/*  43 */     h();
/*     */     
/*  45 */     this.f.schedule(new MojangStatisticsTask(this), 0L, 900000L);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void h() {
/*  64 */     i();
/*     */     
/*  66 */     a("snooper_token", this.c);
/*  67 */     b("snooper_token", this.c);
/*  68 */     b("os_name", System.getProperty("os.name"));
/*  69 */     b("os_version", System.getProperty("os.version"));
/*  70 */     b("os_architecture", System.getProperty("os.arch"));
/*  71 */     b("java_version", System.getProperty("java.version"));
/*  72 */     b("version", "1.7.10");
/*     */     
/*  74 */     this.e.b(this);
/*     */   }
/*     */   
/*     */   private void i() {
/*  78 */     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
/*  79 */     List<String> list = runtimeMXBean.getInputArguments();
/*  80 */     byte b = 0;
/*     */     
/*  82 */     for (String str : list) {
/*  83 */       if (str.startsWith("-X")) {
/*  84 */         a("jvm_arg[" + b++ + "]", str);
/*     */       }
/*     */     } 
/*     */     
/*  88 */     a("jvm_args", Integer.valueOf(b));
/*     */   }
/*     */   
/*     */   public void b() {
/*  92 */     b("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
/*  93 */     b("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
/*  94 */     b("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
/*  95 */     b("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
/*     */     
/*  97 */     this.e.a(this);
/*     */   }
/*     */   
/*     */   public void a(String paramString, Object paramObject) {
/* 101 */     synchronized (this.g) {
/* 102 */       this.b.put(paramString, paramObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(String paramString, Object paramObject) {
/* 107 */     synchronized (this.g) {
/* 108 */       this.a.put(paramString, paramObject);
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 131 */     return this.i;
/*     */   }
/*     */   
/*     */   public void e() {
/* 135 */     this.f.cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long g() {
/* 143 */     return this.h;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MojangStatisticsGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */