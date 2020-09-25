/*     */ package com.avaje.ebean.enhance.asm.commons;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
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
/*     */ public abstract class MethodAdviceAdapter
/*     */   extends GeneratorAdapter
/*     */   implements Opcodes
/*     */ {
/*     */   protected int methodAccess;
/*     */   protected String methodName;
/*     */   protected String methodDesc;
/*     */   
/*     */   protected MethodAdviceAdapter(MethodVisitor mv, int access, String name, String desc) {
/*  69 */     super(mv, access, name, desc);
/*  70 */     this.methodAccess = access;
/*  71 */     this.methodDesc = desc;
/*  72 */     this.methodName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCode() {
/*  77 */     this.mv.visitCode();
/*  78 */     onMethodEnter();
/*     */   }
/*     */   
/*     */   public void visitLabel(Label label) {
/*  82 */     this.mv.visitLabel(label);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int opcode) {
/*  87 */     switch (opcode) {
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/*     */       case 177:
/*     */       case 191:
/*  95 */         onMethodExit(opcode);
/*     */         break;
/*     */     } 
/*  98 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/* 102 */     super.visitVarInsn(opcode, var);
/*     */   }
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 106 */     this.mv.visitFieldInsn(opcode, owner, name, desc);
/*     */   }
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {
/* 110 */     this.mv.visitIntInsn(opcode, operand);
/*     */   }
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 114 */     this.mv.visitLdcInsn(cst);
/*     */   }
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 118 */     this.mv.visitMultiANewArrayInsn(desc, dims);
/*     */   }
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 122 */     this.mv.visitTypeInsn(opcode, type);
/*     */   }
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 126 */     this.mv.visitMethodInsn(opcode, owner, name, desc);
/*     */   }
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 130 */     this.mv.visitJumpInsn(opcode, label);
/*     */   }
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 134 */     this.mv.visitLookupSwitchInsn(dflt, keys, labels);
/*     */   }
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
/* 138 */     this.mv.visitTableSwitchInsn(min, max, dflt, labels);
/*     */   }
/*     */   
/*     */   protected void onMethodEnter() {}
/*     */   
/*     */   protected void onMethodExit(int opcode) {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\commons\MethodAdviceAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */