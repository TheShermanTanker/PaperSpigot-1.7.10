package com.avaje.ebean.enhance.asm;

public interface AnnotationVisitor {
  void visit(String paramString, Object paramObject);
  
  void visitEnum(String paramString1, String paramString2, String paramString3);
  
  AnnotationVisitor visitAnnotation(String paramString1, String paramString2);
  
  AnnotationVisitor visitArray(String paramString);
  
  void visitEnd();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\AnnotationVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */