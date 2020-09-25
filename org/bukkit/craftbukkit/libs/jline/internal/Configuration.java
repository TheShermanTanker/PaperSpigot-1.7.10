/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Configuration
/*     */ {
/*     */   public static final String JLINE_INPUTRC = "org.bukkit.craftbukkit.libs.jline.inputrc";
/*     */   public static final String JLINE_RC = ".org.bukkit.craftbukkit.libs.jline.rc";
/*     */   public static final String INPUT_RC = ".inputrc";
/*     */   private static Configuration configuration;
/*     */   private final Properties props;
/*     */   private final URL jlinercUrl;
/*     */   
/*     */   public static Configuration getConfig() {
/*  48 */     return getConfig((URL)null);
/*     */   }
/*     */   
/*     */   public static Configuration getConfig(String fileOrUrl) {
/*  52 */     return getConfig(getUrlFrom(fileOrUrl));
/*     */   }
/*     */   
/*     */   public static Configuration getConfig(URL url) {
/*  56 */     if (url == null) {
/*  57 */       url = getUrlFrom(new File(getUserHome(), ".org.bukkit.craftbukkit.libs.jline.rc"));
/*     */     }
/*  59 */     if (configuration == null || !url.equals(configuration.jlinercUrl)) {
/*  60 */       configuration = new Configuration(url);
/*     */     }
/*  62 */     return configuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Configuration() {
/*  72 */     this(getUrlFrom(new File(getUserHome(), ".org.bukkit.craftbukkit.libs.jline.rc")));
/*     */   }
/*     */   
/*     */   public Configuration(File inputRc) {
/*  76 */     this(getUrlFrom(inputRc));
/*     */   }
/*     */   
/*     */   public Configuration(String fileOrUrl) {
/*  80 */     this(getUrlFrom(fileOrUrl));
/*     */   }
/*     */   
/*     */   public Configuration(URL jlinercUrl) {
/*  84 */     this.jlinercUrl = jlinercUrl;
/*  85 */     this.props = loadProps();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Properties loadProps() {
/*  90 */     Properties props = new Properties();
/*     */     try {
/*  92 */       InputStream input = this.jlinercUrl.openStream();
/*     */       try {
/*  94 */         props.load(new BufferedInputStream(input));
/*     */       } finally {
/*     */         
/*     */         try {
/*  98 */           input.close();
/*  99 */         } catch (IOException e) {}
/*     */       }
/*     */     
/*     */     }
/* 103 */     catch (IOException e) {
/* 104 */       if (this.jlinercUrl.getProtocol().equals("file")) {
/* 105 */         File file = new File(this.jlinercUrl.getPath());
/* 106 */         if (file.exists()) {
/* 107 */           Log.warn(new Object[] { "Unable to read user configuration: ", this.jlinercUrl, e });
/*     */         }
/*     */       } else {
/* 110 */         Log.warn(new Object[] { "Unable to read user configuration: ", this.jlinercUrl, e });
/*     */       } 
/*     */     } 
/* 113 */     return props;
/*     */   }
/*     */   
/*     */   public static URL getUrlFrom(String fileOrUrl) {
/* 117 */     if (fileOrUrl == null) {
/* 118 */       return null;
/*     */     }
/*     */     try {
/* 121 */       return new URL(fileOrUrl);
/* 122 */     } catch (MalformedURLException e) {
/* 123 */       return getUrlFrom(new File(fileOrUrl));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL getUrlFrom(File inputRc) {
/*     */     try {
/* 129 */       return (inputRc != null) ? inputRc.toURI().toURL() : null;
/* 130 */     } catch (MalformedURLException e) {
/* 131 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String string(String name, String defaultValue) {
/* 136 */     assert name != null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     String value = System.getProperty(name);
/*     */     
/* 143 */     if (value == null) {
/*     */       
/* 145 */       value = this.props.getProperty(name);
/*     */       
/* 147 */       if (value == null)
/*     */       {
/* 149 */         value = defaultValue;
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return value;
/*     */   }
/*     */   
/*     */   public String string(String name) {
/* 157 */     return string(name, null);
/*     */   }
/*     */   
/*     */   public boolean bool(String name, boolean defaultValue) {
/* 161 */     String value = string(name, null);
/* 162 */     if (value == null) {
/* 163 */       return defaultValue;
/*     */     }
/* 165 */     return (value.length() == 0 || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bool(String name) {
/* 170 */     return bool(name, false);
/*     */   }
/*     */   
/*     */   public static String getString(String name, String defaultValue) {
/* 174 */     return getConfig().string(name, defaultValue);
/*     */   }
/*     */   
/*     */   public static String getString(String name) {
/* 178 */     return getString(name, null);
/*     */   }
/*     */   
/*     */   public static boolean getBoolean(String name, boolean defaultValue) {
/* 182 */     return getConfig().bool(name, defaultValue);
/*     */   }
/*     */   
/*     */   public static boolean getBoolean(String name) {
/* 186 */     return getBoolean(name, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File getUserHome() {
/* 194 */     return new File(System.getProperty("user.home"));
/*     */   }
/*     */   
/*     */   public static String getOsName() {
/* 198 */     return System.getProperty("os.name").toLowerCase();
/*     */   }
/*     */   
/*     */   public static String getFileEncoding() {
/* 202 */     return System.getProperty("file.encoding");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getEncoding() {
/* 207 */     String ctype = System.getenv("LC_CTYPE");
/* 208 */     if (ctype != null && ctype.indexOf('.') > 0) {
/* 209 */       return ctype.substring(ctype.indexOf('.') + 1);
/*     */     }
/* 211 */     return System.getProperty("input.encoding", Charset.defaultCharset().name());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\Configuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */