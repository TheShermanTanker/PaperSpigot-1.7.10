/*     */ package com.avaje.ebeaninternal.server.util;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class ClassPathSearchFilter
/*     */ {
/*     */   private static final String COM_AVAJE_EBEANINTERNAL_SERVER_BEAN = "com.avaje.ebeaninternal.server.bean";
/*     */   private static final String COM_AVAJE_EBEAN_META = "com.avaje.ebean.meta";
/*     */   private boolean defaultPackageMatch = true;
/*     */   private boolean defaultJarMatch = false;
/*  39 */   private String ebeanJarPrefix = "ebean";
/*     */   
/*  41 */   private HashSet<String> includePackageSet = new HashSet<String>();
/*     */   
/*  43 */   private HashSet<String> excludePackageSet = new HashSet<String>();
/*     */   
/*  45 */   private HashSet<String> includeJarSet = new HashSet<String>();
/*     */   
/*  47 */   private HashSet<String> excludeJarSet = new HashSet<String>();
/*     */   
/*     */   public ClassPathSearchFilter() {
/*  50 */     addDefaultExcludePackages();
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
/*     */   public void setEbeanJarPrefix(String ebeanJarPrefix) {
/*  62 */     this.ebeanJarPrefix = ebeanJarPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIncludePackages() {
/*  69 */     return this.includePackageSet;
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
/*     */   public void addDefaultExcludePackages() {
/*  83 */     excludePackage("sun");
/*  84 */     excludePackage("com.sun");
/*  85 */     excludePackage("java");
/*  86 */     excludePackage("javax");
/*  87 */     excludePackage("junit");
/*  88 */     excludePackage("org.w3c");
/*  89 */     excludePackage("org.xml");
/*  90 */     excludePackage("org.apache");
/*  91 */     excludePackage("com.mysql");
/*  92 */     excludePackage("oracle.jdbc");
/*  93 */     excludePackage("com.microsoft.sqlserver");
/*  94 */     excludePackage("com.avaje.ebean");
/*  95 */     excludePackage("com.avaje.lib");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearExcludePackages() {
/* 106 */     this.excludePackageSet.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultJarMatch(boolean defaultJarMatch) {
/* 114 */     this.defaultJarMatch = defaultJarMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultPackageMatch(boolean defaultPackageMatch) {
/* 122 */     this.defaultPackageMatch = defaultPackageMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void includePackage(String pckgName) {
/* 129 */     this.includePackageSet.add(pckgName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void excludePackage(String pckgName) {
/* 136 */     this.excludePackageSet.add(pckgName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void excludeJar(String jarName) {
/* 143 */     this.includeJarSet.add(jarName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void includeJar(String jarName) {
/* 150 */     this.includeJarSet.add(jarName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSearchPackage(String packageName) {
/* 159 */     if ("com.avaje.ebean.meta".equals(packageName)) {
/* 160 */       return true;
/*     */     }
/*     */     
/* 163 */     if ("com.avaje.ebeaninternal.server.bean".equals(packageName)) {
/* 164 */       return true;
/*     */     }
/* 166 */     if (this.includePackageSet != null && !this.includePackageSet.isEmpty()) {
/* 167 */       return containedIn(this.includePackageSet, packageName);
/*     */     }
/* 169 */     if (containedIn(this.excludePackageSet, packageName)) {
/* 170 */       return false;
/*     */     }
/* 172 */     return this.defaultPackageMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSearchJar(String jarName) {
/* 179 */     if (jarName.startsWith(this.ebeanJarPrefix)) {
/* 180 */       return true;
/*     */     }
/*     */     
/* 183 */     if (containedIn(this.includeJarSet, jarName)) {
/* 184 */       return true;
/*     */     }
/*     */     
/* 187 */     if (containedIn(this.excludeJarSet, jarName)) {
/* 188 */       return false;
/*     */     }
/* 190 */     return this.defaultJarMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean containedIn(HashSet<String> set, String match) {
/* 197 */     if (set.contains(match)) {
/* 198 */       return true;
/*     */     }
/* 200 */     Iterator<String> incIt = set.iterator();
/* 201 */     while (incIt.hasNext()) {
/* 202 */       String val = incIt.next();
/* 203 */       if (match.startsWith(val)) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/* 207 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\serve\\util\ClassPathSearchFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */