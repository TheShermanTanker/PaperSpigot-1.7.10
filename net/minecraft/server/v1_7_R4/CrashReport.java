/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftCrashReport;
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  21 */   private static final Logger a = LogManager.getLogger();
/*     */   private final String b;
/*     */   private final Throwable c;
/*  24 */   private final CrashReportSystemDetails d = new CrashReportSystemDetails(this, "System Details");
/*  25 */   private final List e = new ArrayList();
/*     */   private File f;
/*     */   private boolean g = true;
/*  28 */   private StackTraceElement[] h = new StackTraceElement[0];
/*     */   
/*     */   public CrashReport(String s, Throwable throwable) {
/*  31 */     this.b = s;
/*  32 */     this.c = throwable;
/*  33 */     h();
/*     */   }
/*     */   
/*     */   private void h() {
/*  37 */     this.d.a("Minecraft Version", new CrashReportVersion(this));
/*  38 */     this.d.a("Operating System", new CrashReportOperatingSystem(this));
/*  39 */     this.d.a("Java Version", new CrashReportJavaVersion(this));
/*  40 */     this.d.a("Java VM Version", new CrashReportJavaVMVersion(this));
/*  41 */     this.d.a("Memory", new CrashReportMemory(this));
/*  42 */     this.d.a("JVM Flags", new CrashReportJVMFlags(this));
/*  43 */     this.d.a("AABB Pool Size", new CrashReportAABBPoolSize(this));
/*  44 */     this.d.a("IntCache", new CrashReportIntCacheSize(this));
/*  45 */     this.d.a("CraftBukkit Information", (Callable)new CraftCrashReport());
/*     */   }
/*     */   
/*     */   public String a() {
/*  49 */     return this.b;
/*     */   }
/*     */   
/*     */   public Throwable b() {
/*  53 */     return this.c;
/*     */   }
/*     */   
/*     */   public void a(StringBuilder stringbuilder) {
/*  57 */     if ((this.h == null || this.h.length <= 0) && this.e.size() > 0) {
/*  58 */       this.h = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportSystemDetails)this.e.get(0)).a(), 0, 1);
/*     */     }
/*     */     
/*  61 */     if (this.h != null && this.h.length > 0) {
/*  62 */       stringbuilder.append("-- Head --\n");
/*  63 */       stringbuilder.append("Stacktrace:\n");
/*  64 */       StackTraceElement[] astacktraceelement = this.h;
/*  65 */       int i = astacktraceelement.length;
/*     */       
/*  67 */       for (int j = 0; j < i; j++) {
/*  68 */         StackTraceElement stacktraceelement = astacktraceelement[j];
/*     */         
/*  70 */         stringbuilder.append("\t").append("at ").append(stacktraceelement.toString());
/*  71 */         stringbuilder.append("\n");
/*     */       } 
/*     */       
/*  74 */       stringbuilder.append("\n");
/*     */     } 
/*     */     
/*  77 */     Iterator<CrashReportSystemDetails> iterator = this.e.iterator();
/*     */     
/*  79 */     while (iterator.hasNext()) {
/*  80 */       CrashReportSystemDetails crashreportsystemdetails = iterator.next();
/*     */       
/*  82 */       crashreportsystemdetails.a(stringbuilder);
/*  83 */       stringbuilder.append("\n\n");
/*     */     } 
/*     */     
/*  86 */     this.d.a(stringbuilder);
/*     */   }
/*     */   
/*     */   public String d() {
/*  90 */     StringWriter stringwriter = null;
/*  91 */     PrintWriter printwriter = null;
/*  92 */     Object object = this.c;
/*     */     
/*  94 */     if (((Throwable)object).getMessage() == null) {
/*  95 */       if (object instanceof NullPointerException) {
/*  96 */         object = new NullPointerException(this.b);
/*  97 */       } else if (object instanceof StackOverflowError) {
/*  98 */         object = new StackOverflowError(this.b);
/*  99 */       } else if (object instanceof OutOfMemoryError) {
/* 100 */         object = new OutOfMemoryError(this.b);
/*     */       } 
/*     */       
/* 103 */       ((Throwable)object).setStackTrace(this.c.getStackTrace());
/*     */     } 
/*     */     
/* 106 */     String s = ((Throwable)object).toString();
/*     */     
/*     */     try {
/* 109 */       stringwriter = new StringWriter();
/* 110 */       printwriter = new PrintWriter(stringwriter);
/* 111 */       ((Throwable)object).printStackTrace(printwriter);
/* 112 */       s = stringwriter.toString();
/*     */     } finally {
/* 114 */       IOUtils.closeQuietly(stringwriter);
/* 115 */       IOUtils.closeQuietly(printwriter);
/*     */     } 
/*     */     
/* 118 */     return s;
/*     */   }
/*     */   
/*     */   public String e() {
/* 122 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 124 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 125 */     stringbuilder.append("// ");
/* 126 */     stringbuilder.append(i());
/* 127 */     stringbuilder.append("\n\n");
/* 128 */     stringbuilder.append("Time: ");
/* 129 */     stringbuilder.append((new SimpleDateFormat()).format(new Date()));
/* 130 */     stringbuilder.append("\n");
/* 131 */     stringbuilder.append("Description: ");
/* 132 */     stringbuilder.append(this.b);
/* 133 */     stringbuilder.append("\n\n");
/* 134 */     stringbuilder.append(d());
/* 135 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 137 */     for (int i = 0; i < 87; i++) {
/* 138 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 141 */     stringbuilder.append("\n\n");
/* 142 */     a(stringbuilder);
/* 143 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public boolean a(File file1) {
/* 147 */     if (this.f != null) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (file1.getParentFile() != null) {
/* 151 */       file1.getParentFile().mkdirs();
/*     */     }
/*     */     
/*     */     try {
/* 155 */       FileWriter filewriter = new FileWriter(file1);
/*     */       
/* 157 */       filewriter.write(e());
/* 158 */       filewriter.close();
/* 159 */       this.f = file1;
/* 160 */       return true;
/* 161 */     } catch (Throwable throwable) {
/* 162 */       a.error("Could not save crash report to " + file1, throwable);
/* 163 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CrashReportSystemDetails g() {
/* 169 */     return this.d;
/*     */   }
/*     */   
/*     */   public CrashReportSystemDetails a(String s) {
/* 173 */     return a(s, 1);
/*     */   }
/*     */   
/*     */   public CrashReportSystemDetails a(String s, int i) {
/* 177 */     CrashReportSystemDetails crashreportsystemdetails = new CrashReportSystemDetails(this, s);
/*     */     
/* 179 */     if (this.g) {
/* 180 */       int j = crashreportsystemdetails.a(i);
/* 181 */       StackTraceElement[] astacktraceelement = this.c.getStackTrace();
/* 182 */       StackTraceElement stacktraceelement = null;
/* 183 */       StackTraceElement stacktraceelement1 = null;
/* 184 */       int k = astacktraceelement.length - j;
/*     */       
/* 186 */       if (k < 0) {
/* 187 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + j + ")");
/*     */       }
/*     */       
/* 190 */       if (astacktraceelement != null && 0 <= k && k < astacktraceelement.length) {
/* 191 */         stacktraceelement = astacktraceelement[k];
/* 192 */         if (astacktraceelement.length + 1 - j < astacktraceelement.length) {
/* 193 */           stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - j];
/*     */         }
/*     */       } 
/*     */       
/* 197 */       this.g = crashreportsystemdetails.a(stacktraceelement, stacktraceelement1);
/* 198 */       if (j > 0 && !this.e.isEmpty()) {
/* 199 */         CrashReportSystemDetails crashreportsystemdetails1 = this.e.get(this.e.size() - 1);
/*     */         
/* 201 */         crashreportsystemdetails1.b(j);
/* 202 */       } else if (astacktraceelement != null && astacktraceelement.length >= j && 0 <= k && k < astacktraceelement.length) {
/* 203 */         this.h = new StackTraceElement[astacktraceelement.length - j];
/* 204 */         System.arraycopy(astacktraceelement, 0, this.h, 0, this.h.length);
/*     */       } else {
/* 206 */         this.g = false;
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     this.e.add(crashreportsystemdetails);
/* 211 */     return crashreportsystemdetails;
/*     */   }
/*     */   
/*     */   private static String i() {
/* 215 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */     
/*     */     try {
/* 218 */       return astring[(int)(System.nanoTime() % astring.length)];
/* 219 */     } catch (Throwable throwable) {
/* 220 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static CrashReport a(Throwable throwable, String s) {
/*     */     CrashReport crashreport;
/* 227 */     if (throwable instanceof ReportedException) {
/* 228 */       crashreport = ((ReportedException)throwable).a();
/*     */     } else {
/* 230 */       crashreport = new CrashReport(s, throwable);
/*     */     } 
/*     */     
/* 233 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CrashReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */