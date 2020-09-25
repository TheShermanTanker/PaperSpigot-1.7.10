/*     */ package com.mysql.jdbc.util;
/*     */ 
/*     */ import com.mysql.jdbc.StringUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
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
/*     */ public class ServerController
/*     */ {
/*     */   public static final String BASEDIR_KEY = "basedir";
/*     */   public static final String DATADIR_KEY = "datadir";
/*     */   public static final String DEFAULTS_FILE_KEY = "defaults-file";
/*     */   public static final String EXECUTABLE_NAME_KEY = "executable";
/*     */   public static final String EXECUTABLE_PATH_KEY = "executablePath";
/*  79 */   private Process serverProcess = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private Properties serverProps = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private Properties systemProps = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerController(String baseDir) {
/* 100 */     setBaseDir(baseDir);
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
/*     */   public ServerController(String basedir, String datadir) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseDir(String baseDir) {
/* 122 */     getServerProps().setProperty("basedir", baseDir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataDir(String dataDir) {
/* 132 */     getServerProps().setProperty("datadir", dataDir);
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
/*     */   public Process start() throws IOException {
/* 145 */     if (this.serverProcess != null) {
/* 146 */       throw new IllegalArgumentException("Server already started");
/*     */     }
/* 148 */     this.serverProcess = Runtime.getRuntime().exec(getCommandLine());
/*     */     
/* 150 */     return this.serverProcess;
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
/*     */   public void stop(boolean forceIfNecessary) throws IOException {
/* 164 */     if (this.serverProcess != null) {
/*     */       
/* 166 */       String basedir = getServerProps().getProperty("basedir");
/*     */       
/* 168 */       StringBuffer pathBuf = new StringBuffer(basedir);
/*     */       
/* 170 */       if (!basedir.endsWith(File.separator)) {
/* 171 */         pathBuf.append(File.separator);
/*     */       }
/*     */       
/* 174 */       String defaultsFilePath = getServerProps().getProperty("defaults-file");
/*     */ 
/*     */       
/* 177 */       pathBuf.append("bin");
/* 178 */       pathBuf.append(File.separator);
/* 179 */       pathBuf.append("mysqladmin shutdown");
/*     */       
/* 181 */       System.out.println(pathBuf.toString());
/*     */       
/* 183 */       Process mysqladmin = Runtime.getRuntime().exec(pathBuf.toString());
/*     */       
/* 185 */       int exitStatus = -1;
/*     */       
/*     */       try {
/* 188 */         exitStatus = mysqladmin.waitFor();
/* 189 */       } catch (InterruptedException ie) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       if (exitStatus != 0 && forceIfNecessary) {
/* 198 */         forceStop();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceStop() {
/* 207 */     if (this.serverProcess != null) {
/* 208 */       this.serverProcess.destroy();
/* 209 */       this.serverProcess = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Properties getServerProps() {
/* 220 */     if (this.serverProps == null) {
/* 221 */       this.serverProps = new Properties();
/*     */     }
/*     */     
/* 224 */     return this.serverProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCommandLine() {
/* 234 */     StringBuffer commandLine = new StringBuffer(getFullExecutablePath());
/* 235 */     commandLine.append(buildOptionalCommandLine());
/*     */     
/* 237 */     return commandLine.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFullExecutablePath() {
/* 246 */     StringBuffer pathBuf = new StringBuffer();
/*     */     
/* 248 */     String optionalExecutablePath = getServerProps().getProperty("executablePath");
/*     */ 
/*     */     
/* 251 */     if (optionalExecutablePath == null) {
/*     */       
/* 253 */       String basedir = getServerProps().getProperty("basedir");
/* 254 */       pathBuf.append(basedir);
/*     */       
/* 256 */       if (!basedir.endsWith(File.separator)) {
/* 257 */         pathBuf.append(File.separatorChar);
/*     */       }
/*     */       
/* 260 */       if (runningOnWindows()) {
/* 261 */         pathBuf.append("bin");
/*     */       } else {
/* 263 */         pathBuf.append("libexec");
/*     */       } 
/*     */       
/* 266 */       pathBuf.append(File.separatorChar);
/*     */     } else {
/* 268 */       pathBuf.append(optionalExecutablePath);
/*     */       
/* 270 */       if (!optionalExecutablePath.endsWith(File.separator)) {
/* 271 */         pathBuf.append(File.separatorChar);
/*     */       }
/*     */     } 
/*     */     
/* 275 */     String executableName = getServerProps().getProperty("executable", "mysqld");
/*     */ 
/*     */     
/* 278 */     pathBuf.append(executableName);
/*     */     
/* 280 */     return pathBuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildOptionalCommandLine() {
/* 290 */     StringBuffer commandLineBuf = new StringBuffer();
/*     */     
/* 292 */     if (this.serverProps != null) {
/*     */       
/* 294 */       Iterator<String> iter = this.serverProps.keySet().iterator();
/* 295 */       while (iter.hasNext()) {
/* 296 */         String key = iter.next();
/* 297 */         String value = this.serverProps.getProperty(key);
/*     */         
/* 299 */         if (!isNonCommandLineArgument(key)) {
/* 300 */           if (value != null && value.length() > 0) {
/* 301 */             commandLineBuf.append(" \"");
/* 302 */             commandLineBuf.append("--");
/* 303 */             commandLineBuf.append(key);
/* 304 */             commandLineBuf.append("=");
/* 305 */             commandLineBuf.append(value);
/* 306 */             commandLineBuf.append("\""); continue;
/*     */           } 
/* 308 */           commandLineBuf.append(" --");
/* 309 */           commandLineBuf.append(key);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 315 */     return commandLineBuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNonCommandLineArgument(String propName) {
/* 324 */     return (propName.equals("executable") || propName.equals("executablePath"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Properties getSystemProperties() {
/* 334 */     if (this.systemProps == null) {
/* 335 */       this.systemProps = System.getProperties();
/*     */     }
/*     */     
/* 338 */     return this.systemProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean runningOnWindows() {
/* 347 */     return (StringUtils.indexOfIgnoreCase(getSystemProperties().getProperty("os.name"), "WINDOWS") != -1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdb\\util\ServerController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */