/*     */ package com.avaje.ebean.enhance.asm;
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
/*     */ public class ClassAdapter
/*     */   implements ClassVisitor
/*     */ {
/*     */   protected ClassVisitor cv;
/*     */   
/*     */   public ClassAdapter(ClassVisitor cv) {
/*  52 */     this.cv = cv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  63 */     this.cv.visit(version, access, name, signature, superName, interfaces);
/*     */   }
/*     */   
/*     */   public void visitSource(String source, String debug) {
/*  67 */     this.cv.visitSource(source, debug);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String owner, String name, String desc) {
/*  75 */     this.cv.visitOuterClass(owner, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  82 */     return this.cv.visitAnnotation(desc, visible);
/*     */   }
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/*  86 */     this.cv.visitAttribute(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/*  95 */     this.cv.visitInnerClass(name, outerName, innerName, access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 105 */     return this.cv.visitField(access, name, desc, signature, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 115 */     return this.cv.visitMethod(access, name, desc, signature, exceptions);
/*     */   }
/*     */   
/*     */   public void visitEnd() {
/* 119 */     this.cv.visitEnd();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\ClassAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */