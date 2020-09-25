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
/*     */ 
/*     */ public class MethodAdapter
/*     */   implements MethodVisitor
/*     */ {
/*     */   protected MethodVisitor mv;
/*     */   
/*     */   public MethodAdapter(MethodVisitor mv) {
/*  53 */     this.mv = mv;
/*     */   }
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/*  57 */     return this.mv.visitAnnotationDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  64 */     return this.mv.visitAnnotation(desc, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  72 */     return this.mv.visitParameterAnnotation(parameter, desc, visible);
/*     */   }
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/*  76 */     this.mv.visitAttribute(attr);
/*     */   }
/*     */   
/*     */   public void visitCode() {
/*  80 */     this.mv.visitCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*  90 */     this.mv.visitFrame(type, nLocal, local, nStack, stack);
/*     */   }
/*     */   
/*     */   public void visitInsn(int opcode) {
/*  94 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {
/*  98 */     this.mv.visitIntInsn(opcode, operand);
/*     */   }
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/* 102 */     this.mv.visitVarInsn(opcode, var);
/*     */   }
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 106 */     this.mv.visitTypeInsn(opcode, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 115 */     this.mv.visitFieldInsn(opcode, owner, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 124 */     this.mv.visitMethodInsn(opcode, owner, name, desc);
/*     */   }
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 128 */     this.mv.visitJumpInsn(opcode, label);
/*     */   }
/*     */   
/*     */   public void visitLabel(Label label) {
/* 132 */     this.mv.visitLabel(label);
/*     */   }
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 136 */     this.mv.visitLdcInsn(cst);
/*     */   }
/*     */   
/*     */   public void visitIincInsn(int var, int increment) {
/* 140 */     this.mv.visitIincInsn(var, increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
/* 149 */     this.mv.visitTableSwitchInsn(min, max, dflt, labels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 157 */     this.mv.visitLookupSwitchInsn(dflt, keys, labels);
/*     */   }
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 161 */     this.mv.visitMultiANewArrayInsn(desc, dims);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 170 */     this.mv.visitTryCatchBlock(start, end, handler, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 181 */     this.mv.visitLocalVariable(name, desc, signature, start, end, index);
/*     */   }
/*     */   
/*     */   public void visitLineNumber(int line, Label start) {
/* 185 */     this.mv.visitLineNumber(line, start);
/*     */   }
/*     */   
/*     */   public void visitMaxs(int maxStack, int maxLocals) {
/* 189 */     this.mv.visitMaxs(maxStack, maxLocals);
/*     */   }
/*     */   
/*     */   public void visitEnd() {
/* 193 */     this.mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\MethodAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */