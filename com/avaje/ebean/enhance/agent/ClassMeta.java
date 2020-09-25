/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.EmptyVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassMeta
/*     */ {
/*  24 */   private static final Logger logger = Logger.getLogger(ClassMeta.class.getName());
/*     */   
/*  26 */   private static final String OBJECT_CLASS = Object.class.getName().replace('.', '/');
/*     */ 
/*     */   
/*     */   private final PrintStream logout;
/*     */ 
/*     */   
/*     */   private final int logLevel;
/*     */ 
/*     */   
/*     */   private final boolean subclassing;
/*     */ 
/*     */   
/*     */   private String className;
/*     */ 
/*     */   
/*     */   private String superClassName;
/*     */ 
/*     */   
/*     */   private ClassMeta superMeta;
/*     */ 
/*     */   
/*     */   private boolean hasGroovyInterface;
/*     */ 
/*     */   
/*     */   private boolean hasScalaInterface;
/*     */ 
/*     */   
/*     */   private boolean hasEntityBeanInterface;
/*     */   
/*     */   private boolean alreadyEnhanced;
/*     */   
/*     */   private boolean hasEqualsOrHashcode;
/*     */   
/*     */   private boolean hasDefaultConstructor;
/*     */   
/*  61 */   private HashSet<String> existingMethods = new HashSet<String>();
/*     */   
/*  63 */   private HashSet<String> existingSuperMethods = new HashSet<String>();
/*     */   
/*  65 */   private LinkedHashMap<String, FieldMeta> fields = new LinkedHashMap<String, FieldMeta>();
/*     */   
/*  67 */   private HashSet<String> classAnnotation = new HashSet<String>();
/*     */   
/*  69 */   private AnnotationInfo annotationInfo = new AnnotationInfo(null);
/*     */   
/*  71 */   private ArrayList<MethodMeta> methodMetaList = new ArrayList<MethodMeta>();
/*     */   
/*     */   private final EnhanceContext enhanceContext;
/*     */   
/*     */   public ClassMeta(EnhanceContext enhanceContext, boolean subclassing, int logLevel, PrintStream logout) {
/*  76 */     this.enhanceContext = enhanceContext;
/*  77 */     this.subclassing = subclassing;
/*  78 */     this.logLevel = logLevel;
/*  79 */     this.logout = logout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnhanceContext getEnhanceContext() {
/*  86 */     return this.enhanceContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getClassAnnotations() {
/*  93 */     return this.classAnnotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationInfo getAnnotationInfo() {
/* 101 */     return this.annotationInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationInfo getInterfaceTransactionalInfo(String methodName, String methodDesc) {
/* 109 */     AnnotationInfo annotationInfo = null;
/*     */     
/* 111 */     for (int i = 0; i < this.methodMetaList.size(); i++) {
/* 112 */       MethodMeta meta = this.methodMetaList.get(i);
/* 113 */       if (meta.isMatch(methodName, methodDesc)) {
/* 114 */         if (annotationInfo != null) {
/* 115 */           String msg = "Error in [" + this.className + "] searching the transactional methods[" + this.methodMetaList + "] found more than one match for the transactional method:" + methodName + " " + methodDesc;
/*     */ 
/*     */ 
/*     */           
/* 119 */           logger.log(Level.SEVERE, msg);
/* 120 */           log(msg);
/*     */         } else {
/*     */           
/* 123 */           annotationInfo = meta.getAnnotationInfo();
/* 124 */           if (isLog(9)) {
/* 125 */             log("... found transactional info from interface " + this.className + " " + methodName + " " + methodDesc);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return annotationInfo;
/*     */   }
/*     */   
/*     */   public boolean isCheckSuperClassForEntity() {
/* 135 */     if (isEntity())
/*     */     {
/* 137 */       return !this.superClassName.equals(OBJECT_CLASS);
/*     */     }
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 143 */     return this.className;
/*     */   }
/*     */   
/*     */   public boolean isTransactional() {
/* 147 */     if (this.classAnnotation.contains("Lcom/avaje/ebean/annotation/Transactional;")) {
/* 148 */       return true;
/*     */     }
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   public ArrayList<MethodMeta> getMethodMeta() {
/* 154 */     return this.methodMetaList;
/*     */   }
/*     */   
/*     */   public void setClassName(String className, String superClassName) {
/* 158 */     this.className = className;
/* 159 */     this.superClassName = superClassName;
/*     */   }
/*     */   
/*     */   public String getSuperClassName() {
/* 163 */     return this.superClassName;
/*     */   }
/*     */   
/*     */   public boolean isSubclassing() {
/* 167 */     return this.subclassing;
/*     */   }
/*     */   
/*     */   public boolean isLog(int level) {
/* 171 */     return (level <= this.logLevel);
/*     */   }
/*     */   
/*     */   public void log(String msg) {
/* 175 */     if (this.className != null) {
/* 176 */       msg = "cls: " + this.className + "  msg: " + msg;
/*     */     }
/* 178 */     this.logout.println("transform> " + msg);
/*     */   }
/*     */   
/*     */   public void logEnhanced() {
/* 182 */     String m = "enhanced ";
/* 183 */     if (hasScalaInterface()) {
/* 184 */       m = m + " (scala)";
/*     */     }
/* 186 */     if (hasGroovyInterface()) {
/* 187 */       m = m + " (groovy)";
/*     */     }
/* 189 */     log(m);
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
/*     */   public boolean isInheritEqualsFromSuper() {
/* 201 */     return (!this.subclassing && isSuperClassEntity());
/*     */   }
/*     */   
/*     */   public ClassMeta getSuperMeta() {
/* 205 */     return this.superMeta;
/*     */   }
/*     */   
/*     */   public void setSuperMeta(ClassMeta superMeta) {
/* 209 */     this.superMeta = superMeta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHasEqualsOrHashcode(boolean hasEqualsOrHashcode) {
/* 216 */     this.hasEqualsOrHashcode = hasEqualsOrHashcode;
/*     */   }
/*     */   
/*     */   public boolean hasEqualsOrHashCode() {
/* 220 */     return this.hasEqualsOrHashcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldPersistent(String fieldName) {
/* 228 */     FieldMeta f = this.fields.get(fieldName);
/* 229 */     if (f != null) {
/* 230 */       return f.isPersistent();
/*     */     }
/* 232 */     if (this.superMeta == null)
/*     */     {
/* 234 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 238 */     return this.superMeta.isFieldPersistent(fieldName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FieldMeta> getLocalFields() {
/* 247 */     ArrayList<FieldMeta> list = new ArrayList<FieldMeta>();
/*     */     
/* 249 */     Iterator<FieldMeta> it = this.fields.values().iterator();
/* 250 */     while (it.hasNext()) {
/* 251 */       FieldMeta fm = it.next();
/* 252 */       if (!fm.isObjectArray())
/*     */       {
/* 254 */         list.add(fm);
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FieldMeta> getInheritedFields() {
/* 266 */     return getInheritedFields(new ArrayList<FieldMeta>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<FieldMeta> getInheritedFields(List<FieldMeta> list) {
/* 274 */     if (list == null) {
/* 275 */       list = new ArrayList<FieldMeta>();
/*     */     }
/*     */     
/* 278 */     if (this.superMeta != null) {
/* 279 */       this.superMeta.addFieldsForInheritance(list);
/*     */     }
/* 281 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFieldsForInheritance(List<FieldMeta> list) {
/* 288 */     if (isEntity()) {
/* 289 */       list.addAll(0, this.fields.values());
/* 290 */       if (this.superMeta != null) {
/* 291 */         this.superMeta.addFieldsForInheritance(list);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<FieldMeta> getAllFields() {
/* 302 */     List<FieldMeta> list = getLocalFields();
/* 303 */     getInheritedFields(list);
/*     */     
/* 305 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFieldGetSetMethods(ClassVisitor cv) {
/* 312 */     if (isEntityEnhancementRequired()) {
/*     */       
/* 314 */       Iterator<FieldMeta> it = this.fields.values().iterator();
/* 315 */       while (it.hasNext()) {
/* 316 */         FieldMeta fm = it.next();
/* 317 */         fm.addGetSetMethods(cv, this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntity() {
/* 326 */     if (this.classAnnotation.contains("Ljavax/persistence/Entity;")) {
/* 327 */       return true;
/*     */     }
/* 329 */     if (this.classAnnotation.contains("Ljavax/persistence/Embeddable;")) {
/* 330 */       return true;
/*     */     }
/* 332 */     if (this.classAnnotation.contains("Ljavax/persistence/MappedSuperclass;")) {
/* 333 */       return true;
/*     */     }
/* 335 */     if (this.classAnnotation.contains("Lcom/avaje/ebean/annotation/LdapDomain;")) {
/* 336 */       return true;
/*     */     }
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntityEnhancementRequired() {
/* 345 */     if (this.alreadyEnhanced) {
/* 346 */       return false;
/*     */     }
/* 348 */     if (isEntity()) {
/* 349 */       return true;
/*     */     }
/* 351 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 358 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSuperClassEntity() {
/* 365 */     if (this.superMeta == null) {
/* 366 */       return false;
/*     */     }
/* 368 */     return this.superMeta.isEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addClassAnnotation(String desc) {
/* 376 */     this.classAnnotation.add(desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExistingSuperMethod(String methodName, String methodDesc) {
/* 387 */     this.existingSuperMethods.add(methodName + methodDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExistingMethod(String methodName, String methodDesc) {
/* 394 */     this.existingMethods.add(methodName + methodDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExistingMethod(String methodName, String methodDesc) {
/* 401 */     return this.existingMethods.contains(methodName + methodDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExistingSuperMethod(String methodName, String methodDesc) {
/* 409 */     return this.existingSuperMethods.contains(methodName + methodDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public MethodVisitor createMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
/* 414 */     MethodMeta methodMeta = new MethodMeta(this.annotationInfo, access, name, desc);
/* 415 */     this.methodMetaList.add(methodMeta);
/*     */     
/* 417 */     return (MethodVisitor)new MethodReader(mv, methodMeta);
/*     */   }
/*     */   
/*     */   private static final class MethodReader extends EmptyVisitor {
/*     */     final MethodVisitor mv;
/*     */     final MethodMeta methodMeta;
/*     */     
/*     */     MethodReader(MethodVisitor mv, MethodMeta methodMeta) {
/* 425 */       this.mv = mv;
/* 426 */       this.methodMeta = methodMeta;
/*     */     }
/*     */     
/*     */     public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 430 */       AnnotationVisitor av = this.mv.visitAnnotation(desc, visible);
/*     */       
/* 432 */       return new AnnotationInfoVisitor(null, this.methodMeta.annotationInfo, av);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor createLocalFieldVisitor(String name, String desc) {
/* 441 */     return createLocalFieldVisitor(null, null, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor createLocalFieldVisitor(ClassVisitor cv, FieldVisitor fv, String name, String desc) {
/* 449 */     String fieldClass = this.subclassing ? this.superClassName : this.className;
/* 450 */     FieldMeta fieldMeta = new FieldMeta(this, name, desc, fieldClass);
/* 451 */     LocalFieldVisitor localField = new LocalFieldVisitor(cv, fv, fieldMeta);
/* 452 */     if (name.startsWith("_ebean")) {
/*     */ 
/*     */       
/* 455 */       if (isLog(0)) {
/* 456 */         log("... ignore field " + name);
/*     */       }
/*     */     } else {
/* 459 */       this.fields.put(localField.getName(), fieldMeta);
/*     */     } 
/* 461 */     return localField;
/*     */   }
/*     */   
/*     */   public boolean isAlreadyEnhanced() {
/* 465 */     return this.alreadyEnhanced;
/*     */   }
/*     */   
/*     */   public void setAlreadyEnhanced(boolean alreadyEnhanced) {
/* 469 */     this.alreadyEnhanced = alreadyEnhanced;
/*     */   }
/*     */   
/*     */   public boolean hasDefaultConstructor() {
/* 473 */     return this.hasDefaultConstructor;
/*     */   }
/*     */   
/*     */   public void setHasDefaultConstructor(boolean hasDefaultConstructor) {
/* 477 */     this.hasDefaultConstructor = hasDefaultConstructor;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 481 */     StringBuilder sb = new StringBuilder();
/* 482 */     appendDescription(sb);
/* 483 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private void appendDescription(StringBuilder sb) {
/* 487 */     sb.append(this.className);
/* 488 */     if (this.superMeta != null) {
/* 489 */       sb.append(" : ");
/* 490 */       this.superMeta.appendDescription(sb);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasScalaInterface() {
/* 495 */     return this.hasScalaInterface;
/*     */   }
/*     */   
/*     */   public void setScalaInterface(boolean hasScalaInterface) {
/* 499 */     this.hasScalaInterface = hasScalaInterface;
/*     */   }
/*     */   
/*     */   public boolean hasEntityBeanInterface() {
/* 503 */     return this.hasEntityBeanInterface;
/*     */   }
/*     */   
/*     */   public void setEntityBeanInterface(boolean hasEntityBeanInterface) {
/* 507 */     this.hasEntityBeanInterface = hasEntityBeanInterface;
/*     */   }
/*     */   
/*     */   public boolean hasGroovyInterface() {
/* 511 */     return this.hasGroovyInterface;
/*     */   }
/*     */   
/*     */   public void setGroovyInterface(boolean hasGroovyInterface) {
/* 515 */     this.hasGroovyInterface = hasGroovyInterface;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\ClassMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */