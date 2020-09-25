/*     */ package com.avaje.ebean.enhance.ant;
/*     */ 
/*     */ import com.avaje.ebean.enhance.agent.Transformer;
/*     */ import java.io.File;
/*     */ import org.apache.tools.ant.BuildException;
/*     */ import org.apache.tools.ant.Task;
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
/*     */ public class AntEnhanceTask
/*     */   extends Task
/*     */ {
/*     */   String classpath;
/*     */   String classSource;
/*     */   String classDestination;
/*     */   String transformArgs;
/*     */   String packages;
/*     */   
/*     */   public void execute() throws BuildException {
/*  59 */     File f = new File("");
/*  60 */     System.out.println("Current Directory: " + f.getAbsolutePath());
/*     */     
/*  62 */     StringBuilder extraClassPath = new StringBuilder();
/*  63 */     extraClassPath.append(this.classSource);
/*  64 */     if (this.classpath != null) {
/*     */       
/*  66 */       if (!extraClassPath.toString().endsWith(";"))
/*     */       {
/*  68 */         extraClassPath.append(";");
/*     */       }
/*  70 */       extraClassPath.append(this.classpath);
/*     */     } 
/*  72 */     Transformer t = new Transformer(extraClassPath.toString(), this.transformArgs);
/*     */     
/*  74 */     ClassLoader cl = AntEnhanceTask.class.getClassLoader();
/*  75 */     OfflineFileTransform ft = new OfflineFileTransform(t, cl, this.classSource, this.classDestination);
/*     */     
/*  77 */     ft.process(this.packages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClasspath() {
/*  84 */     return this.classpath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClasspath(String classpath) {
/*  91 */     this.classpath = classpath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClassSource(String source) {
/*  98 */     this.classSource = source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClassDestination(String destination) {
/* 108 */     this.classDestination = destination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransformArgs(String transformArgs) {
/* 115 */     this.transformArgs = transformArgs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPackages(String packages) {
/* 126 */     this.packages = packages;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\ant\AntEnhanceTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */