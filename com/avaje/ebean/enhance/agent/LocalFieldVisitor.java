/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Attribute;
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.EmptyVisitor;
/*    */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalFieldVisitor
/*    */   implements FieldVisitor
/*    */ {
/* 14 */   private static final EmptyVisitor emptyVisitor = new EmptyVisitor();
/*    */ 
/*    */   
/*    */   private final FieldVisitor fv;
/*    */ 
/*    */   
/*    */   private final FieldMeta fieldMeta;
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalFieldVisitor(FieldMeta fieldMeta) {
/* 25 */     this.fv = null;
/* 26 */     this.fieldMeta = fieldMeta;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalFieldVisitor(ClassVisitor cv, FieldVisitor fv, FieldMeta fieldMeta) {
/* 36 */     this.fv = fv;
/* 37 */     this.fieldMeta = fieldMeta;
/*    */   }
/*    */   
/*    */   public boolean isPersistentSetter(String methodDesc) {
/* 41 */     return this.fieldMeta.isPersistentSetter(methodDesc);
/*    */   }
/*    */   
/*    */   public boolean isPersistentGetter(String methodDesc) {
/* 45 */     return this.fieldMeta.isPersistentGetter(methodDesc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 52 */     return this.fieldMeta.getFieldName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldMeta getFieldMeta() {
/* 59 */     return this.fieldMeta;
/*    */   }
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 63 */     this.fieldMeta.addAnnotationDesc(desc);
/* 64 */     if (this.fv != null) {
/* 65 */       return this.fv.visitAnnotation(desc, visible);
/*    */     }
/* 67 */     return (AnnotationVisitor)emptyVisitor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitAttribute(Attribute attr) {
/* 72 */     if (this.fv != null) {
/* 73 */       this.fv.visitAttribute(attr);
/*    */     }
/*    */   }
/*    */   
/*    */   public void visitEnd() {
/* 78 */     if (this.fv != null)
/* 79 */       this.fv.visitEnd(); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\LocalFieldVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */