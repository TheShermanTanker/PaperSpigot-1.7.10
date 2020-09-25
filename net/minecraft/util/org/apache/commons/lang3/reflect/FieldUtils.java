/*     */ package net.minecraft.util.org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ClassUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.Validate;
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
/*     */ public class FieldUtils
/*     */ {
/*     */   public static Field getField(Class<?> cls, String fieldName) {
/*  61 */     Field field = getField(cls, fieldName, false);
/*  62 */     MemberUtils.setAccessibleWorkaround(field);
/*  63 */     return field;
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
/*     */   public static Field getField(Class<?> cls, String fieldName, boolean forceAccess) {
/*  84 */     Validate.isTrue((cls != null), "The class must not be null", new Object[0]);
/*  85 */     Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
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
/* 100 */     for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
/*     */       try {
/* 102 */         Field field = acls.getDeclaredField(fieldName);
/*     */ 
/*     */         
/* 105 */         if (!Modifier.isPublic(field.getModifiers()))
/* 106 */         { if (forceAccess)
/* 107 */           { field.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 112 */             return field; }  } else { return field; } 
/* 113 */       } catch (NoSuchFieldException ex) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     Field match = null;
/* 121 */     for (Class<?> class1 : (Iterable<Class<?>>)ClassUtils.getAllInterfaces(cls)) {
/*     */       try {
/* 123 */         Field test = class1.getField(fieldName);
/* 124 */         Validate.isTrue((match == null), "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", new Object[] { fieldName, cls });
/*     */         
/* 126 */         match = test;
/* 127 */       } catch (NoSuchFieldException ex) {}
/*     */     } 
/*     */ 
/*     */     
/* 131 */     return match;
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
/*     */   public static Field getDeclaredField(Class<?> cls, String fieldName) {
/* 146 */     return getDeclaredField(cls, fieldName, false);
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
/*     */   public static Field getDeclaredField(Class<?> cls, String fieldName, boolean forceAccess) {
/* 166 */     Validate.isTrue((cls != null), "The class must not be null", new Object[0]);
/* 167 */     Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
/*     */     
/*     */     try {
/* 170 */       Field field = cls.getDeclaredField(fieldName);
/* 171 */       if (!MemberUtils.isAccessible(field)) {
/* 172 */         if (forceAccess) {
/* 173 */           field.setAccessible(true);
/*     */         } else {
/* 175 */           return null;
/*     */         } 
/*     */       }
/* 178 */       return field;
/* 179 */     } catch (NoSuchFieldException e) {
/*     */ 
/*     */       
/* 182 */       return null;
/*     */     } 
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
/*     */   public static Field[] getAllFields(Class<?> cls) {
/* 196 */     List<Field> allFieldsList = getAllFieldsList(cls);
/* 197 */     return allFieldsList.<Field>toArray(new Field[allFieldsList.size()]);
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
/*     */   public static List<Field> getAllFieldsList(Class<?> cls) {
/* 211 */     Validate.isTrue((cls != null), "The class must not be null", new Object[0]);
/* 212 */     List<Field> allFields = new ArrayList<Field>();
/* 213 */     Class<?> currentClass = cls;
/* 214 */     while (currentClass != null) {
/* 215 */       Field[] declaredFields = currentClass.getDeclaredFields();
/* 216 */       for (Field field : declaredFields) {
/* 217 */         allFields.add(field);
/*     */       }
/* 219 */       currentClass = currentClass.getSuperclass();
/*     */     } 
/* 221 */     return allFields;
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
/*     */   public static Object readStaticField(Field field) throws IllegalAccessException {
/* 236 */     return readStaticField(field, false);
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
/*     */   public static Object readStaticField(Field field, boolean forceAccess) throws IllegalAccessException {
/* 254 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 255 */     Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field '%s' is not static", new Object[] { field.getName() });
/* 256 */     return readField(field, (Object)null, forceAccess);
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
/*     */   public static Object readStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
/* 274 */     return readStaticField(cls, fieldName, false);
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
/*     */   public static Object readStaticField(Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 296 */     Field field = getField(cls, fieldName, forceAccess);
/* 297 */     Validate.isTrue((field != null), "Cannot locate field '%s' on %s", new Object[] { fieldName, cls });
/*     */     
/* 299 */     return readStaticField(field, false);
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
/*     */   public static Object readDeclaredStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
/* 318 */     return readDeclaredStaticField(cls, fieldName, false);
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
/*     */   public static Object readDeclaredStaticField(Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 341 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 342 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 344 */     return readStaticField(field, false);
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
/*     */   public static Object readField(Field field, Object target) throws IllegalAccessException {
/* 361 */     return readField(field, target, false);
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
/*     */   public static Object readField(Field field, Object target, boolean forceAccess) throws IllegalAccessException {
/* 381 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 382 */     if (forceAccess && !field.isAccessible()) {
/* 383 */       field.setAccessible(true);
/*     */     } else {
/* 385 */       MemberUtils.setAccessibleWorkaround(field);
/*     */     } 
/* 387 */     return field.get(target);
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
/*     */   public static Object readField(Object target, String fieldName) throws IllegalAccessException {
/* 405 */     return readField(target, fieldName, false);
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
/*     */   public static Object readField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 427 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 428 */     Class<?> cls = target.getClass();
/* 429 */     Field field = getField(cls, fieldName, forceAccess);
/* 430 */     Validate.isTrue((field != null), "Cannot locate field %s on %s", new Object[] { fieldName, cls });
/*     */     
/* 432 */     return readField(field, target);
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
/*     */   public static Object readDeclaredField(Object target, String fieldName) throws IllegalAccessException {
/* 450 */     return readDeclaredField(target, fieldName, false);
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
/*     */   public static Object readDeclaredField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 472 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 473 */     Class<?> cls = target.getClass();
/* 474 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 475 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls, fieldName });
/*     */     
/* 477 */     return readField(field, target);
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
/*     */   public static void writeStaticField(Field field, Object value) throws IllegalAccessException {
/* 494 */     writeStaticField(field, value, false);
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
/*     */   public static void writeStaticField(Field field, Object value, boolean forceAccess) throws IllegalAccessException {
/* 515 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 516 */     Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field %s.%s is not static", new Object[] { field.getDeclaringClass().getName(), field.getName() });
/*     */     
/* 518 */     writeField(field, (Object)null, value, forceAccess);
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
/*     */   public static void writeStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
/* 539 */     writeStaticField(cls, fieldName, value, false);
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
/*     */   public static void writeStaticField(Class<?> cls, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 565 */     Field field = getField(cls, fieldName, forceAccess);
/* 566 */     Validate.isTrue((field != null), "Cannot locate field %s on %s", new Object[] { fieldName, cls });
/*     */     
/* 568 */     writeStaticField(field, value);
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
/*     */   public static void writeDeclaredStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
/* 589 */     writeDeclaredStaticField(cls, fieldName, value, false);
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
/*     */   public static void writeDeclaredStaticField(Class<?> cls, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 614 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 615 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 617 */     writeField(field, (Object)null, value);
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
/*     */   public static void writeField(Field field, Object target, Object value) throws IllegalAccessException {
/* 635 */     writeField(field, target, value, false);
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
/*     */   public static void writeField(Field field, Object target, Object value, boolean forceAccess) throws IllegalAccessException {
/* 659 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 660 */     if (forceAccess && !field.isAccessible()) {
/* 661 */       field.setAccessible(true);
/*     */     } else {
/* 663 */       MemberUtils.setAccessibleWorkaround(field);
/*     */     } 
/* 665 */     field.set(target, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeFinalModifier(Field field) {
/* 675 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/*     */     
/*     */     try {
/* 678 */       if (Modifier.isFinal(field.getModifiers())) {
/* 679 */         Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 680 */         modifiersField.setAccessible(true);
/* 681 */         modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/*     */       } 
/* 683 */     } catch (NoSuchFieldException ignored) {
/*     */     
/* 685 */     } catch (IllegalAccessException ignored) {}
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
/*     */   public static void writeField(Object target, String fieldName, Object value) throws IllegalAccessException {
/* 707 */     writeField(target, fieldName, value, false);
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
/*     */   public static void writeField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 732 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 733 */     Class<?> cls = target.getClass();
/* 734 */     Field field = getField(cls, fieldName, forceAccess);
/* 735 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 737 */     writeField(field, target, value);
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
/*     */   public static void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
/* 757 */     writeDeclaredField(target, fieldName, value, false);
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
/*     */   public static void writeDeclaredField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 782 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 783 */     Class<?> cls = target.getClass();
/* 784 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 785 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 787 */     writeField(field, target, value);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\reflect\FieldUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */