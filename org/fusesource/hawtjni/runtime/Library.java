/*     */ package org.fusesource.hawtjni.runtime;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
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
/*     */ public class Library
/*     */ {
/*  69 */   static final String SLASH = System.getProperty("file.separator");
/*     */   
/*     */   private final String name;
/*     */   private final String version;
/*     */   private final ClassLoader classLoader;
/*     */   private boolean loaded;
/*     */   
/*     */   public Library(String name) {
/*  77 */     this(name, null, null);
/*     */   }
/*     */   
/*     */   public Library(String name, Class<?> clazz) {
/*  81 */     this(name, version(clazz), clazz.getClassLoader());
/*     */   }
/*     */   
/*     */   public Library(String name, String version) {
/*  85 */     this(name, version, null);
/*     */   }
/*     */   
/*     */   public Library(String name, String version, ClassLoader classLoader) {
/*  89 */     if (name == null) {
/*  90 */       throw new IllegalArgumentException("name cannot be null");
/*     */     }
/*  92 */     this.name = name;
/*  93 */     this.version = version;
/*  94 */     this.classLoader = classLoader;
/*     */   }
/*     */   
/*     */   private static String version(Class<?> clazz) {
/*     */     try {
/*  99 */       return clazz.getPackage().getImplementationVersion();
/* 100 */     } catch (Throwable e) {
/*     */       
/* 102 */       return null;
/*     */     } 
/*     */   }
/*     */   public static String getOperatingSystem() {
/* 106 */     String name = System.getProperty("os.name").toLowerCase().trim();
/* 107 */     if (name.startsWith("linux")) {
/* 108 */       return "linux";
/*     */     }
/* 110 */     if (name.startsWith("mac os x")) {
/* 111 */       return "osx";
/*     */     }
/* 113 */     if (name.startsWith("win")) {
/* 114 */       return "windows";
/*     */     }
/* 116 */     return name.replaceAll("\\W+", "_");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPlatform() {
/* 121 */     return getOperatingSystem() + getBitModel();
/*     */   }
/*     */   
/*     */   public static int getBitModel() {
/* 125 */     String prop = System.getProperty("sun.arch.data.model");
/* 126 */     if (prop == null) {
/* 127 */       prop = System.getProperty("com.ibm.vm.bitmode");
/*     */     }
/* 129 */     if (prop != null) {
/* 130 */       return Integer.parseInt(prop);
/*     */     }
/* 132 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void load() {
/* 139 */     if (this.loaded) {
/*     */       return;
/*     */     }
/* 142 */     doLoad();
/* 143 */     this.loaded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void doLoad() {
/* 148 */     String version = System.getProperty("library." + this.name + ".version");
/* 149 */     if (version == null) {
/* 150 */       version = this.version;
/*     */     }
/* 152 */     ArrayList<String> errors = new ArrayList<String>();
/*     */ 
/*     */     
/* 155 */     String customPath = System.getProperty("library." + this.name + ".path");
/* 156 */     if (customPath != null) {
/* 157 */       if (version != null && load(errors, file(new String[] { customPath, map(this.name + "-" + version) })))
/*     */         return; 
/* 159 */       if (load(errors, file(new String[] { customPath, map(this.name) }))) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     if (version != null && load(errors, this.name + getBitModel() + "-" + version))
/*     */       return; 
/* 166 */     if (version != null && load(errors, this.name + "-" + version))
/*     */       return; 
/* 168 */     if (load(errors, this.name)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 173 */     if (this.classLoader != null) {
/* 174 */       if (exractAndLoad(errors, version, customPath, getPlatformSpecifcResourcePath()))
/*     */         return; 
/* 176 */       if (exractAndLoad(errors, version, customPath, getOperatingSystemSpecifcResourcePath())) {
/*     */         return;
/*     */       }
/* 179 */       if (exractAndLoad(errors, version, customPath, getResorucePath())) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 184 */     throw new UnsatisfiedLinkError("Could not load library. Reasons: " + errors.toString());
/*     */   }
/*     */   
/*     */   public final String getOperatingSystemSpecifcResourcePath() {
/* 188 */     return getPlatformSpecifcResourcePath(getOperatingSystem());
/*     */   }
/*     */   public final String getPlatformSpecifcResourcePath() {
/* 191 */     return getPlatformSpecifcResourcePath(getPlatform());
/*     */   }
/*     */   public final String getPlatformSpecifcResourcePath(String platform) {
/* 194 */     return "META-INF/native/" + platform + "/" + map(this.name);
/*     */   }
/*     */   
/*     */   public final String getResorucePath() {
/* 198 */     return "META-INF/native/" + map(this.name);
/*     */   }
/*     */   
/*     */   public final String getLibraryFileName() {
/* 202 */     return map(this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean exractAndLoad(ArrayList<String> errors, String version, String customPath, String resourcePath) {
/* 207 */     URL resource = this.classLoader.getResource(resourcePath);
/* 208 */     if (resource != null) {
/*     */       
/* 210 */       String libName = this.name + "-" + getBitModel();
/* 211 */       if (version != null) {
/* 212 */         libName = libName + "-" + version;
/*     */       }
/*     */       
/* 215 */       if (customPath != null) {
/*     */         
/* 217 */         File file = file(new String[] { customPath, map(libName) });
/* 218 */         if (extract(errors, resource, file) && 
/* 219 */           load(errors, file)) {
/* 220 */           return true;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 226 */       customPath = System.getProperty("java.io.tmpdir");
/* 227 */       File target = file(new String[] { customPath, map(libName) });
/* 228 */       if (extract(errors, resource, target) && 
/* 229 */         load(errors, target)) {
/* 230 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   private File file(String... paths) {
/* 238 */     File rc = null;
/* 239 */     for (String path : paths) {
/* 240 */       if (rc == null) {
/* 241 */         rc = new File(path);
/*     */       } else {
/* 243 */         rc = new File(rc, path);
/*     */       } 
/*     */     } 
/* 246 */     return rc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String map(String libName) {
/* 254 */     libName = System.mapLibraryName(libName);
/* 255 */     String ext = ".dylib";
/* 256 */     if (libName.endsWith(ext)) {
/* 257 */       libName = libName.substring(0, libName.length() - ext.length()) + ".jnilib";
/*     */     }
/* 259 */     return libName;
/*     */   }
/*     */   
/*     */   private boolean extract(ArrayList<String> errors, URL source, File target) {
/* 263 */     FileOutputStream os = null;
/* 264 */     InputStream is = null;
/* 265 */     boolean extracting = false;
/*     */     try {
/* 267 */       if (!target.exists() || isStale(source, target)) {
/* 268 */         is = source.openStream();
/* 269 */         if (is != null) {
/* 270 */           byte[] buffer = new byte[4096];
/* 271 */           os = new FileOutputStream(target);
/* 272 */           extracting = true;
/*     */           int read;
/* 274 */           while ((read = is.read(buffer)) != -1) {
/* 275 */             os.write(buffer, 0, read);
/*     */           }
/* 277 */           os.close();
/* 278 */           is.close();
/* 279 */           chmod("755", target);
/*     */         } 
/*     */       } 
/* 282 */     } catch (Throwable e) {
/*     */       try {
/* 284 */         if (os != null)
/* 285 */           os.close(); 
/* 286 */       } catch (IOException e1) {}
/*     */       
/*     */       try {
/* 289 */         if (is != null)
/* 290 */           is.close(); 
/* 291 */       } catch (IOException e1) {}
/*     */       
/* 293 */       if (extracting && target.exists())
/* 294 */         target.delete(); 
/* 295 */       errors.add(e.getMessage());
/* 296 */       return false;
/*     */     } 
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isStale(URL source, File target) {
/* 303 */     if (source.getProtocol().equals("jar")) {
/*     */       
/*     */       try {
/* 306 */         String[] parts = source.getFile().split(Pattern.quote("!"));
/* 307 */         source = new URL(parts[0]);
/* 308 */       } catch (MalformedURLException e) {
/* 309 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 313 */     File sourceFile = null;
/* 314 */     if (source.getProtocol().equals("file")) {
/* 315 */       sourceFile = new File(source.getFile());
/*     */     }
/* 317 */     if (sourceFile != null && sourceFile.exists() && 
/* 318 */       sourceFile.lastModified() > target.lastModified()) {
/* 319 */       return true;
/*     */     }
/*     */     
/* 322 */     return false;
/*     */   }
/*     */   
/*     */   private void chmod(String permision, File path) {
/* 326 */     if (getPlatform().startsWith("windows"))
/*     */       return; 
/*     */     try {
/* 329 */       Runtime.getRuntime().exec(new String[] { "chmod", permision, path.getCanonicalPath() }).waitFor();
/* 330 */     } catch (Throwable e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean load(ArrayList<String> errors, File lib) {
/*     */     try {
/* 336 */       System.load(lib.getPath());
/* 337 */       return true;
/* 338 */     } catch (UnsatisfiedLinkError e) {
/* 339 */       errors.add(e.getMessage());
/*     */       
/* 341 */       return false;
/*     */     } 
/*     */   }
/*     */   private boolean load(ArrayList<String> errors, String lib) {
/*     */     try {
/* 346 */       System.loadLibrary(lib);
/* 347 */       return true;
/* 348 */     } catch (UnsatisfiedLinkError e) {
/* 349 */       errors.add(e.getMessage());
/*     */       
/* 351 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\Library.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */