/*     */ package com.avaje.ebean.enhance.asm.commons;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class Method
/*     */ {
/*     */   private final String name;
/*     */   private final String desc;
/*  63 */   private static final Map DESCRIPTORS = new HashMap<Object, Object>(); static {
/*  64 */     DESCRIPTORS.put("void", "V");
/*  65 */     DESCRIPTORS.put("byte", "B");
/*  66 */     DESCRIPTORS.put("char", "C");
/*  67 */     DESCRIPTORS.put("double", "D");
/*  68 */     DESCRIPTORS.put("float", "F");
/*  69 */     DESCRIPTORS.put("int", "I");
/*  70 */     DESCRIPTORS.put("long", "J");
/*  71 */     DESCRIPTORS.put("short", "S");
/*  72 */     DESCRIPTORS.put("boolean", "Z");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method(String name, String desc) {
/*  82 */     this.name = name;
/*  83 */     this.desc = desc;
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
/*     */   
/*     */   public Method(String name, Type returnType, Type[] argumentTypes) {
/*  98 */     this(name, Type.getMethodDescriptor(returnType, argumentTypes));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getMethod(String method) throws IllegalArgumentException {
/* 119 */     return getMethod(method, false);
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
/*     */   public static Method getMethod(String method, boolean defaultPackage) throws IllegalArgumentException {
/* 146 */     int space = method.indexOf(' ');
/* 147 */     int start = method.indexOf('(', space) + 1;
/* 148 */     int end = method.indexOf(')', start);
/* 149 */     if (space == -1 || start == -1 || end == -1) {
/* 150 */       throw new IllegalArgumentException();
/*     */     }
/* 152 */     String returnType = method.substring(0, space);
/* 153 */     String methodName = method.substring(space + 1, start - 1).trim();
/* 154 */     StringBuffer sb = new StringBuffer();
/* 155 */     sb.append('(');
/*     */     
/*     */     while (true) {
/*     */       String s;
/* 159 */       int p = method.indexOf(',', start);
/* 160 */       if (p == -1) {
/* 161 */         s = map(method.substring(start, end).trim(), defaultPackage);
/*     */       } else {
/* 163 */         s = map(method.substring(start, p).trim(), defaultPackage);
/* 164 */         start = p + 1;
/*     */       } 
/* 166 */       sb.append(s);
/* 167 */       if (p == -1) {
/* 168 */         sb.append(')');
/* 169 */         sb.append(map(returnType, defaultPackage));
/* 170 */         return new Method(methodName, sb.toString());
/*     */       } 
/*     */     } 
/*     */   } private static String map(String type, boolean defaultPackage) {
/* 174 */     if ("".equals(type)) {
/* 175 */       return type;
/*     */     }
/*     */     
/* 178 */     StringBuffer sb = new StringBuffer();
/* 179 */     int index = 0;
/* 180 */     while ((index = type.indexOf("[]", index) + 1) > 0) {
/* 181 */       sb.append('[');
/*     */     }
/*     */     
/* 184 */     String t = type.substring(0, type.length() - sb.length() * 2);
/* 185 */     String desc = (String)DESCRIPTORS.get(t);
/* 186 */     if (desc != null) {
/* 187 */       sb.append(desc);
/*     */     } else {
/* 189 */       sb.append('L');
/* 190 */       if (t.indexOf('.') < 0) {
/* 191 */         if (!defaultPackage) {
/* 192 */           sb.append("java/lang/");
/*     */         }
/* 194 */         sb.append(t);
/*     */       } else {
/* 196 */         sb.append(t.replace('.', '/'));
/*     */       } 
/* 198 */       sb.append(';');
/*     */     } 
/* 200 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 209 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescriptor() {
/* 218 */     return this.desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getReturnType() {
/* 227 */     return Type.getReturnType(this.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getArgumentTypes() {
/* 236 */     return Type.getArgumentTypes(this.desc);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 240 */     return this.name + this.desc;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 244 */     if (!(o instanceof Method)) {
/* 245 */       return false;
/*     */     }
/* 247 */     Method other = (Method)o;
/* 248 */     return (this.name.equals(other.name) && this.desc.equals(other.desc));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 252 */     return this.name.hashCode() ^ this.desc.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\commons\Method.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */