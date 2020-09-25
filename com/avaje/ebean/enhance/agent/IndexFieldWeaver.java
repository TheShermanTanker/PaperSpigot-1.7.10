/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
/*     */ import java.util.List;
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
/*     */ public class IndexFieldWeaver
/*     */   implements Opcodes
/*     */ {
/*     */   public static void addMethods(ClassVisitor cv, ClassMeta classMeta) {
/*  39 */     List<FieldMeta> fields = classMeta.getAllFields();
/*  40 */     if (fields.size() == 0) {
/*  41 */       classMeta.log("Has no fields?");
/*     */       
/*     */       return;
/*     */     } 
/*  45 */     if (classMeta.isLog(3)) {
/*  46 */       classMeta.log("fields size:" + fields.size() + " " + fields.toString());
/*     */     }
/*     */     
/*  49 */     generateCreateCopy(cv, classMeta, fields);
/*  50 */     generateGetField(cv, classMeta, fields, false);
/*  51 */     generateGetField(cv, classMeta, fields, true);
/*     */     
/*  53 */     generateSetField(cv, classMeta, fields, false);
/*  54 */     generateSetField(cv, classMeta, fields, true);
/*     */     
/*  56 */     generateGetDesc(cv, classMeta, fields);
/*     */     
/*  58 */     if (classMeta.hasEqualsOrHashCode()) {
/*     */       
/*  60 */       if (classMeta.isLog(1)) {
/*  61 */         classMeta.log("... skipping add equals() ... already has equals() hashcode() methods");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  67 */     int idIndex = -1;
/*  68 */     FieldMeta idFieldMeta = null;
/*     */ 
/*     */     
/*  71 */     for (int i = 0; i < fields.size(); i++) {
/*  72 */       FieldMeta fieldMeta = fields.get(i);
/*  73 */       if (fieldMeta.isId() && fieldMeta.isLocalField(classMeta)) {
/*  74 */         if (idIndex == -1) {
/*     */           
/*  76 */           idIndex = i;
/*  77 */           idFieldMeta = fieldMeta;
/*     */         } else {
/*     */           
/*  80 */           idIndex = -2;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  85 */     if (idIndex == -2) {
/*     */       
/*  87 */       if (classMeta.isLog(1)) {
/*  88 */         classMeta.log("has 2 or more id fields. Not adding equals() method.");
/*     */       }
/*     */     }
/*  91 */     else if (idIndex == -1) {
/*     */       
/*  93 */       if (classMeta.isLog(1)) {
/*  94 */         classMeta.log("has no id fields on this type. Not adding equals() method. Expected when Id property on superclass.");
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  99 */       MethodEquals.addMethods(cv, classMeta, idIndex, idFieldMeta);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateGetField(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields, boolean intercept) {
/* 109 */     String className = classMeta.getClassName();
/*     */     
/* 111 */     MethodVisitor mv = null;
/* 112 */     if (intercept) {
/* 113 */       mv = cv.visitMethod(1, "_ebean_getFieldIntercept", "(ILjava/lang/Object;)Ljava/lang/Object;", null, null);
/*     */     } else {
/* 115 */       mv = cv.visitMethod(1, "_ebean_getField", "(ILjava/lang/Object;)Ljava/lang/Object;", null, null);
/*     */     } 
/*     */     
/* 118 */     mv.visitCode();
/* 119 */     Label l0 = new Label();
/* 120 */     mv.visitLabel(l0);
/* 121 */     mv.visitLineNumber(1, l0);
/* 122 */     mv.visitVarInsn(25, 2);
/* 123 */     mv.visitTypeInsn(192, className);
/* 124 */     mv.visitVarInsn(58, 3);
/* 125 */     Label l1 = new Label();
/* 126 */     mv.visitLabel(l1);
/*     */     
/* 128 */     mv.visitLineNumber(1, l1);
/* 129 */     mv.visitVarInsn(21, 1);
/*     */     
/* 131 */     Label[] switchLabels = new Label[fields.size()];
/* 132 */     for (int i = 0; i < switchLabels.length; i++) {
/* 133 */       switchLabels[i] = new Label();
/*     */     }
/*     */     
/* 136 */     int maxIndex = switchLabels.length - 1;
/*     */     
/* 138 */     Label labelException = new Label();
/* 139 */     mv.visitTableSwitchInsn(0, maxIndex, labelException, switchLabels);
/*     */     
/* 141 */     for (int j = 0; j < fields.size(); j++) {
/*     */       
/* 143 */       FieldMeta fieldMeta = fields.get(j);
/*     */       
/* 145 */       mv.visitLabel(switchLabels[j]);
/* 146 */       mv.visitLineNumber(1, switchLabels[j]);
/* 147 */       mv.visitVarInsn(25, 3);
/*     */       
/* 149 */       fieldMeta.appendSwitchGet(mv, classMeta, intercept);
/*     */       
/* 151 */       mv.visitInsn(176);
/*     */     } 
/*     */     
/* 154 */     mv.visitLabel(labelException);
/* 155 */     mv.visitLineNumber(1, labelException);
/* 156 */     mv.visitTypeInsn(187, "java/lang/RuntimeException");
/* 157 */     mv.visitInsn(89);
/* 158 */     mv.visitTypeInsn(187, "java/lang/StringBuilder");
/* 159 */     mv.visitInsn(89);
/* 160 */     mv.visitLdcInsn("Invalid index ");
/* 161 */     mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
/* 162 */     mv.visitVarInsn(21, 1);
/* 163 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
/* 164 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
/* 165 */     mv.visitMethodInsn(183, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
/* 166 */     mv.visitInsn(191);
/* 167 */     Label l5 = new Label();
/* 168 */     mv.visitLabel(l5);
/* 169 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l5, 0);
/* 170 */     mv.visitLocalVariable("index", "I", null, l0, l5, 1);
/* 171 */     mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l5, 2);
/* 172 */     mv.visitLocalVariable("p", "L" + className + ";", null, l1, l5, 3);
/* 173 */     mv.visitMaxs(5, 4);
/* 174 */     mv.visitEnd();
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
/*     */   private static void generateSetField(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields, boolean intercept) {
/* 188 */     String className = classMeta.getClassName();
/*     */     
/* 190 */     MethodVisitor mv = null;
/* 191 */     if (intercept) {
/* 192 */       mv = cv.visitMethod(1, "_ebean_setFieldIntercept", "(ILjava/lang/Object;Ljava/lang/Object;)V", null, null);
/*     */     } else {
/*     */       
/* 195 */       mv = cv.visitMethod(1, "_ebean_setField", "(ILjava/lang/Object;Ljava/lang/Object;)V", null, null);
/*     */     } 
/*     */     
/* 198 */     mv.visitCode();
/* 199 */     Label l0 = new Label();
/* 200 */     mv.visitLabel(l0);
/* 201 */     mv.visitLineNumber(1, l0);
/* 202 */     mv.visitVarInsn(25, 2);
/* 203 */     mv.visitTypeInsn(192, className);
/* 204 */     mv.visitVarInsn(58, 4);
/* 205 */     Label l1 = new Label();
/* 206 */     mv.visitLabel(l1);
/* 207 */     mv.visitLineNumber(1, l1);
/* 208 */     mv.visitVarInsn(21, 1);
/*     */     
/* 210 */     Label[] switchLabels = new Label[fields.size()];
/* 211 */     for (int i = 0; i < switchLabels.length; i++) {
/* 212 */       switchLabels[i] = new Label();
/*     */     }
/*     */     
/* 215 */     Label labelException = new Label();
/*     */     
/* 217 */     int maxIndex = switchLabels.length - 1;
/*     */     
/* 219 */     mv.visitTableSwitchInsn(0, maxIndex, labelException, switchLabels);
/*     */     
/* 221 */     for (int j = 0; j < fields.size(); j++) {
/* 222 */       FieldMeta fieldMeta = fields.get(j);
/*     */       
/* 224 */       mv.visitLabel(switchLabels[j]);
/* 225 */       mv.visitLineNumber(1, switchLabels[j]);
/* 226 */       mv.visitVarInsn(25, 4);
/* 227 */       mv.visitVarInsn(25, 3);
/*     */       
/* 229 */       fieldMeta.appendSwitchSet(mv, classMeta, intercept);
/*     */       
/* 231 */       Label l6 = new Label();
/* 232 */       mv.visitLabel(l6);
/* 233 */       mv.visitLineNumber(1, l6);
/* 234 */       mv.visitInsn(177);
/*     */     } 
/*     */ 
/*     */     
/* 238 */     mv.visitLabel(labelException);
/* 239 */     mv.visitLineNumber(1, labelException);
/* 240 */     mv.visitTypeInsn(187, "java/lang/RuntimeException");
/* 241 */     mv.visitInsn(89);
/* 242 */     mv.visitTypeInsn(187, "java/lang/StringBuilder");
/* 243 */     mv.visitInsn(89);
/* 244 */     mv.visitLdcInsn("Invalid index ");
/* 245 */     mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
/* 246 */     mv.visitVarInsn(21, 1);
/* 247 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
/* 248 */     mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
/* 249 */     mv.visitMethodInsn(183, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
/* 250 */     mv.visitInsn(191);
/* 251 */     Label l9 = new Label();
/* 252 */     mv.visitLabel(l9);
/* 253 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l9, 0);
/* 254 */     mv.visitLocalVariable("index", "I", null, l0, l9, 1);
/* 255 */     mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l9, 2);
/* 256 */     mv.visitLocalVariable("arg", "Ljava/lang/Object;", null, l0, l9, 3);
/* 257 */     mv.visitLocalVariable("p", "L" + className + ";", null, l1, l9, 4);
/* 258 */     mv.visitMaxs(5, 5);
/* 259 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateCreateCopy(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields) {
/* 267 */     String className = classMeta.getClassName();
/*     */     
/* 269 */     String copyClassName = className;
/* 270 */     if (classMeta.isSubclassing()) {
/* 271 */       copyClassName = classMeta.getSuperClassName();
/*     */     }
/*     */     
/* 274 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_createCopy", "()Ljava/lang/Object;", null, null);
/* 275 */     mv.visitCode();
/* 276 */     Label l0 = new Label();
/* 277 */     mv.visitLabel(l0);
/* 278 */     mv.visitLineNumber(1, l0);
/* 279 */     mv.visitTypeInsn(187, copyClassName);
/* 280 */     mv.visitInsn(89);
/* 281 */     mv.visitMethodInsn(183, copyClassName, "<init>", "()V");
/* 282 */     mv.visitVarInsn(58, 1);
/*     */     
/* 284 */     Label l1 = null;
/* 285 */     for (int i = 0; i < fields.size(); i++) {
/*     */       
/* 287 */       FieldMeta fieldMeta = fields.get(i);
/* 288 */       if (fieldMeta.isPersistent()) {
/*     */         
/* 290 */         Label label = new Label();
/* 291 */         if (i == 0) {
/* 292 */           l1 = label;
/*     */         }
/* 294 */         mv.visitLabel(label);
/* 295 */         mv.visitLineNumber(1, label);
/* 296 */         mv.visitVarInsn(25, 1);
/* 297 */         mv.visitVarInsn(25, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 302 */         fieldMeta.addFieldCopy(mv, classMeta);
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     Label l4 = new Label();
/* 307 */     mv.visitLabel(l4);
/* 308 */     mv.visitLineNumber(1, l4);
/* 309 */     mv.visitVarInsn(25, 1);
/* 310 */     mv.visitInsn(176);
/* 311 */     Label l5 = new Label();
/* 312 */     mv.visitLabel(l5);
/* 313 */     if (l1 == null) {
/* 314 */       l1 = l4;
/*     */     }
/* 316 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l5, 0);
/* 317 */     mv.visitLocalVariable("p", "L" + copyClassName + ";", null, l1, l5, 1);
/* 318 */     mv.visitMaxs(2, 2);
/* 319 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void generateGetDesc(ClassVisitor cv, ClassMeta classMeta, List<FieldMeta> fields) {
/* 324 */     String className = classMeta.getClassName();
/*     */     
/* 326 */     int size = fields.size();
/*     */     
/* 328 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_getFieldNames", "()[Ljava/lang/String;", null, null);
/* 329 */     mv.visitCode();
/* 330 */     Label l0 = new Label();
/* 331 */     mv.visitLabel(l0);
/* 332 */     mv.visitLineNumber(1, l0);
/* 333 */     visitIntInsn(mv, size);
/* 334 */     mv.visitTypeInsn(189, "java/lang/String");
/*     */     
/* 336 */     for (int i = 0; i < size; i++) {
/* 337 */       FieldMeta fieldMeta = fields.get(i);
/* 338 */       mv.visitInsn(89);
/* 339 */       visitIntInsn(mv, i);
/* 340 */       mv.visitLdcInsn(fieldMeta.getName());
/* 341 */       mv.visitInsn(83);
/*     */     } 
/*     */     
/* 344 */     mv.visitInsn(176);
/* 345 */     Label l1 = new Label();
/* 346 */     mv.visitLabel(l1);
/* 347 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l1, 0);
/* 348 */     mv.visitMaxs(4, 1);
/* 349 */     mv.visitEnd();
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
/*     */   public static void visitIntInsn(MethodVisitor mv, int value) {
/* 361 */     switch (value) {
/*     */       case 0:
/* 363 */         mv.visitInsn(3);
/*     */         return;
/*     */       case 1:
/* 366 */         mv.visitInsn(4);
/*     */         return;
/*     */       case 2:
/* 369 */         mv.visitInsn(5);
/*     */         return;
/*     */       case 3:
/* 372 */         mv.visitInsn(6);
/*     */         return;
/*     */       case 4:
/* 375 */         mv.visitInsn(7);
/*     */         return;
/*     */       case 5:
/* 378 */         mv.visitInsn(8);
/*     */         return;
/*     */     } 
/* 381 */     if (value <= 127) {
/* 382 */       mv.visitIntInsn(16, value);
/*     */     } else {
/* 384 */       mv.visitIntInsn(17, value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\IndexFieldWeaver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */