/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class TerminalLineSettings
/*     */ {
/*     */   public static final String JLINE_STTY = "org.bukkit.craftbukkit.libs.jline.stty";
/*     */   public static final String DEFAULT_STTY = "stty";
/*     */   public static final String JLINE_SH = "org.bukkit.craftbukkit.libs.jline.sh";
/*     */   public static final String DEFAULT_SH = "sh";
/*     */   private String sttyCommand;
/*     */   private String shCommand;
/*     */   private String config;
/*     */   private long configLastFetched;
/*     */   
/*     */   public TerminalLineSettings() throws IOException, InterruptedException {
/*  47 */     this.sttyCommand = Configuration.getString("org.bukkit.craftbukkit.libs.jline.stty", "stty");
/*  48 */     this.shCommand = Configuration.getString("org.bukkit.craftbukkit.libs.jline.sh", "sh");
/*  49 */     this.config = get("-a");
/*  50 */     this.configLastFetched = System.currentTimeMillis();
/*     */     
/*  52 */     Log.debug(new Object[] { "Config: ", this.config });
/*     */ 
/*     */     
/*  55 */     if (this.config.length() == 0) {
/*  56 */       throw new IOException(MessageFormat.format("Unrecognized stty code: {0}", new Object[] { this.config }));
/*     */     }
/*     */   }
/*     */   
/*     */   public String getConfig() {
/*  61 */     return this.config;
/*     */   }
/*     */   
/*     */   public void restore() throws IOException, InterruptedException {
/*  65 */     set("sane");
/*     */   }
/*     */   
/*     */   public String get(String args) throws IOException, InterruptedException {
/*  69 */     return stty(args);
/*     */   }
/*     */   
/*     */   public void set(String args) throws IOException, InterruptedException {
/*  73 */     stty(args);
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
/*     */   public int getProperty(String name) {
/*  85 */     assert name != null;
/*     */     
/*  87 */     long currentTime = System.currentTimeMillis();
/*     */ 
/*     */     
/*     */     try {
/*  91 */       if (this.config == null || currentTime - this.configLastFetched > 1000L) {
/*  92 */         this.config = get("-a");
/*     */       }
/*  94 */     } catch (Exception e) {
/*  95 */       Log.debug(new Object[] { "Failed to query stty ", name, "\n", e });
/*     */     } 
/*     */ 
/*     */     
/*  99 */     if (currentTime - this.configLastFetched > 1000L) {
/* 100 */       this.configLastFetched = currentTime;
/*     */     }
/*     */     
/* 103 */     this; return getProperty(name, this.config);
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
/*     */   protected static int getProperty(String name, String stty) {
/* 118 */     Pattern pattern = Pattern.compile(name + "\\s+=\\s+([^;]*)[;\\n\\r]");
/* 119 */     Matcher matcher = pattern.matcher(stty);
/* 120 */     if (!matcher.find()) {
/*     */       
/* 122 */       pattern = Pattern.compile(name + "\\s+([^;]*)[;\\n\\r]");
/* 123 */       matcher = pattern.matcher(stty);
/* 124 */       if (!matcher.find()) {
/*     */         
/* 126 */         pattern = Pattern.compile("(\\S*)\\s+" + name);
/* 127 */         matcher = pattern.matcher(stty);
/* 128 */         if (!matcher.find()) {
/* 129 */           return -1;
/*     */         }
/*     */       } 
/*     */     } 
/* 133 */     return parseControlChar(matcher.group(1));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseControlChar(String str) {
/* 138 */     if ("<undef>".equals(str)) {
/* 139 */       return -1;
/*     */     }
/*     */     
/* 142 */     if (str.charAt(0) == '0') {
/* 143 */       return Integer.parseInt(str, 8);
/*     */     }
/*     */     
/* 146 */     if (str.charAt(0) >= '1' && str.charAt(0) <= '9') {
/* 147 */       return Integer.parseInt(str, 10);
/*     */     }
/*     */     
/* 150 */     if (str.charAt(0) == '^') {
/* 151 */       if (str.charAt(1) == '?') {
/* 152 */         return 127;
/*     */       }
/* 154 */       return str.charAt(1) - 64;
/*     */     } 
/* 156 */     if (str.charAt(0) == 'M' && str.charAt(1) == '-') {
/* 157 */       if (str.charAt(2) == '^') {
/* 158 */         if (str.charAt(3) == '?') {
/* 159 */           return 255;
/*     */         }
/* 161 */         return str.charAt(3) - 64 + 128;
/*     */       } 
/*     */       
/* 164 */       return str.charAt(2) + 128;
/*     */     } 
/*     */     
/* 167 */     return str.charAt(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private String stty(String args) throws IOException, InterruptedException {
/* 172 */     assert args != null;
/* 173 */     return exec(String.format("%s %s < /dev/tty", new Object[] { this.sttyCommand, args }));
/*     */   }
/*     */   
/*     */   private String exec(String cmd) throws IOException, InterruptedException {
/* 177 */     assert cmd != null;
/* 178 */     return exec(new String[] { this.shCommand, "-c", cmd });
/*     */   }
/*     */   
/*     */   private String exec(String... cmd) throws IOException, InterruptedException {
/* 182 */     assert cmd != null;
/*     */     
/* 184 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*     */     
/* 186 */     Log.trace(new Object[] { "Running: ", cmd });
/*     */     
/* 188 */     Process p = Runtime.getRuntime().exec(cmd);
/*     */     
/* 190 */     InputStream in = null;
/* 191 */     InputStream err = null;
/* 192 */     OutputStream out = null;
/*     */     
/*     */     try {
/* 195 */       in = p.getInputStream(); int c;
/* 196 */       while ((c = in.read()) != -1) {
/* 197 */         bout.write(c);
/*     */       }
/* 199 */       err = p.getErrorStream();
/* 200 */       while ((c = err.read()) != -1) {
/* 201 */         bout.write(c);
/*     */       }
/* 203 */       out = p.getOutputStream();
/* 204 */       p.waitFor();
/*     */     } finally {
/*     */       
/* 207 */       close(new Closeable[] { in, out, err });
/*     */     } 
/*     */     
/* 210 */     String result = bout.toString();
/*     */     
/* 212 */     Log.trace(new Object[] { "Result: ", result });
/*     */     
/* 214 */     return result;
/*     */   }
/*     */   
/*     */   private static void close(Closeable... closeables) {
/* 218 */     for (Closeable c : closeables) {
/*     */       try {
/* 220 */         c.close();
/*     */       }
/* 222 */       catch (Exception e) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\TerminalLineSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */