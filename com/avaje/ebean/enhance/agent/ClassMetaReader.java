/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.ClassReader;
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassMetaReader
/*    */ {
/* 18 */   private Map<String, ClassMeta> cache = new HashMap<String, ClassMeta>();
/*    */   
/*    */   private final EnhanceContext enhanceContext;
/*    */   
/*    */   public ClassMetaReader(EnhanceContext enhanceContext) {
/* 23 */     this.enhanceContext = enhanceContext;
/*    */   }
/*    */   
/*    */   public ClassMeta get(boolean readMethodAnnotations, String name, ClassLoader classLoader) throws ClassNotFoundException {
/* 27 */     return getWithCache(readMethodAnnotations, name, classLoader);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private ClassMeta getWithCache(boolean readMethodAnnotations, String name, ClassLoader classLoader) throws ClassNotFoundException {
/* 33 */     synchronized (this.cache) {
/* 34 */       ClassMeta meta = this.cache.get(name);
/* 35 */       if (meta == null) {
/* 36 */         meta = readFromResource(readMethodAnnotations, name, classLoader);
/* 37 */         if (meta != null) {
/* 38 */           if (meta.isCheckSuperClassForEntity()) {
/* 39 */             ClassMeta superMeta = getWithCache(readMethodAnnotations, meta.getSuperClassName(), classLoader);
/* 40 */             if (superMeta != null && superMeta.isEntity()) {
/* 41 */               meta.setSuperMeta(superMeta);
/*    */             }
/*    */           } 
/* 44 */           this.cache.put(name, meta);
/*    */         } 
/*    */       } 
/* 47 */       return meta;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ClassMeta readFromResource(boolean readMethodAnnotations, String className, ClassLoader classLoader) throws ClassNotFoundException {
/* 55 */     byte[] classBytes = this.enhanceContext.getClassBytes(className, classLoader);
/* 56 */     if (classBytes == null) {
/* 57 */       this.enhanceContext.log(1, "Class [" + className + "] not found.");
/* 58 */       return null;
/*    */     } 
/* 60 */     if (this.enhanceContext.isLog(3)) {
/* 61 */       this.enhanceContext.log(className, "read ClassMeta");
/*    */     }
/*    */     
/* 64 */     ClassReader cr = new ClassReader(classBytes);
/* 65 */     ClassMetaReaderVisitor ca = new ClassMetaReaderVisitor(readMethodAnnotations, this.enhanceContext);
/*    */     
/* 67 */     cr.accept((ClassVisitor)ca, 0);
/*    */     
/* 69 */     return ca.getClassMeta();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\ClassMetaReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */