/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VisitMethodParams
/*    */ {
/*    */   private final ClassVisitor cv;
/*    */   private int access;
/*    */   private final String name;
/*    */   private final String desc;
/*    */   private final String signiture;
/*    */   private final String[] exceptions;
/*    */   
/*    */   public VisitMethodParams(ClassVisitor cv, int access, String name, String desc, String signiture, String[] exceptions) {
/* 48 */     this.cv = cv;
/* 49 */     this.access = access;
/* 50 */     this.name = name;
/* 51 */     this.desc = desc;
/* 52 */     this.exceptions = exceptions;
/* 53 */     this.signiture = signiture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean forcePublic() {
/* 60 */     if (this.access != 1) {
/* 61 */       this.access = 1;
/* 62 */       return true;
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodVisitor visitMethod() {
/* 72 */     return this.cv.visitMethod(this.access, this.name, this.desc, this.signiture, this.exceptions);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 79 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDesc() {
/* 86 */     return this.desc;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\VisitMethodParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */