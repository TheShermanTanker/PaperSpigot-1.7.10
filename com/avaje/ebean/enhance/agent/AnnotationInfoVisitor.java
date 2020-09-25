/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnnotationInfoVisitor
/*    */   implements AnnotationVisitor
/*    */ {
/*    */   final AnnotationVisitor av;
/*    */   final AnnotationInfo info;
/*    */   final String prefix;
/*    */   
/*    */   public AnnotationInfoVisitor(String prefix, AnnotationInfo info, AnnotationVisitor av) {
/* 17 */     this.av = av;
/* 18 */     this.info = info;
/* 19 */     this.prefix = prefix;
/*    */   }
/*    */   
/*    */   public void visit(String name, Object value) {
/* 23 */     this.info.add(this.prefix, name, value);
/*    */   }
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/* 27 */     return create(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitArray(String name) {
/* 32 */     return create(name);
/*    */   }
/*    */   
/*    */   private AnnotationInfoVisitor create(String name) {
/* 36 */     String newPrefix = (this.prefix == null) ? name : (this.prefix + "." + name);
/* 37 */     return new AnnotationInfoVisitor(newPrefix, this.info, this.av);
/*    */   }
/*    */   
/*    */   public void visitEnd() {
/* 41 */     this.av.visitEnd();
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitEnum(String name, String desc, String value) {
/* 46 */     this.info.addEnum(this.prefix, name, desc, value);
/* 47 */     this.av.visitEnum(name, desc, value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\AnnotationInfoVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */