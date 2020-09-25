/*     */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javax.persistence.AttributeConverter;
/*     */ import javax.persistence.Converter;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Converter(autoApply = false)
/*     */ public class ThrowableAttributeConverter
/*     */   implements AttributeConverter<Throwable, String>
/*     */ {
/*     */   private static final int CAUSED_BY_STRING_LENGTH = 10;
/*     */   private static final Field THROWABLE_CAUSE;
/*     */   private static final Field THROWABLE_MESSAGE;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       THROWABLE_CAUSE = Throwable.class.getDeclaredField("cause");
/*  46 */       THROWABLE_CAUSE.setAccessible(true);
/*  47 */       THROWABLE_MESSAGE = Throwable.class.getDeclaredField("detailMessage");
/*  48 */       THROWABLE_MESSAGE.setAccessible(true);
/*  49 */     } catch (NoSuchFieldException e) {
/*  50 */       throw new IllegalStateException("Something is wrong with java.lang.Throwable.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String convertToDatabaseColumn(Throwable throwable) {
/*  56 */     if (throwable == null) {
/*  57 */       return null;
/*     */     }
/*     */     
/*  60 */     StringBuilder builder = new StringBuilder();
/*  61 */     convertThrowable(builder, throwable);
/*  62 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private void convertThrowable(StringBuilder builder, Throwable throwable) {
/*  66 */     builder.append(throwable.toString()).append('\n');
/*  67 */     for (StackTraceElement element : throwable.getStackTrace()) {
/*  68 */       builder.append("\tat ").append(element).append('\n');
/*     */     }
/*  70 */     if (throwable.getCause() != null) {
/*  71 */       builder.append("Caused by ");
/*  72 */       convertThrowable(builder, throwable.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Throwable convertToEntityAttribute(String s) {
/*  78 */     if (Strings.isEmpty(s)) {
/*  79 */       return null;
/*     */     }
/*     */     
/*  82 */     List<String> lines = Arrays.asList(s.split("(\n|\r\n)"));
/*  83 */     return convertString(lines.listIterator(), false);
/*     */   }
/*     */   
/*     */   private Throwable convertString(ListIterator<String> lines, boolean removeCausedBy) {
/*  87 */     String throwableClassName, firstLine = lines.next();
/*  88 */     if (removeCausedBy) {
/*  89 */       firstLine = firstLine.substring(10);
/*     */     }
/*  91 */     int colon = firstLine.indexOf(":");
/*     */     
/*  93 */     String message = null;
/*  94 */     if (colon > 1) {
/*  95 */       throwableClassName = firstLine.substring(0, colon);
/*  96 */       if (firstLine.length() > colon + 1) {
/*  97 */         message = firstLine.substring(colon + 1).trim();
/*     */       }
/*     */     } else {
/* 100 */       throwableClassName = firstLine;
/*     */     } 
/*     */     
/* 103 */     List<StackTraceElement> stackTrace = new ArrayList<StackTraceElement>();
/* 104 */     Throwable cause = null;
/* 105 */     while (lines.hasNext()) {
/* 106 */       String line = lines.next();
/*     */       
/* 108 */       if (line.startsWith("Caused by ")) {
/* 109 */         lines.previous();
/* 110 */         cause = convertString(lines, true);
/*     */         
/*     */         break;
/*     */       } 
/* 114 */       stackTrace.add(StackTraceElementAttributeConverter.convertString(line.trim().substring(3).trim()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 119 */     return getThrowable(throwableClassName, message, cause, stackTrace.<StackTraceElement>toArray(new StackTraceElement[stackTrace.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Throwable getThrowable(String throwableClassName, String message, Throwable cause, StackTraceElement[] stackTrace) {
/*     */     try {
/*     */       Throwable throwable;
/* 128 */       Class<Throwable> throwableClass = (Class)Class.forName(throwableClassName);
/*     */       
/* 130 */       if (!Throwable.class.isAssignableFrom(throwableClass)) {
/* 131 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 135 */       if (message != null && cause != null) {
/* 136 */         throwable = getThrowable(throwableClass, message, cause);
/* 137 */         if (throwable == null) {
/* 138 */           throwable = getThrowable(throwableClass, cause);
/* 139 */           if (throwable == null) {
/* 140 */             throwable = getThrowable(throwableClass, message);
/* 141 */             if (throwable == null) {
/* 142 */               throwable = getThrowable(throwableClass);
/* 143 */               if (throwable != null) {
/* 144 */                 THROWABLE_MESSAGE.set(throwable, message);
/* 145 */                 THROWABLE_CAUSE.set(throwable, cause);
/*     */               } 
/*     */             } else {
/* 148 */               THROWABLE_CAUSE.set(throwable, cause);
/*     */             } 
/*     */           } else {
/* 151 */             THROWABLE_MESSAGE.set(throwable, message);
/*     */           } 
/*     */         } 
/* 154 */       } else if (cause != null) {
/* 155 */         throwable = getThrowable(throwableClass, cause);
/* 156 */         if (throwable == null) {
/* 157 */           throwable = getThrowable(throwableClass);
/* 158 */           if (throwable != null) {
/* 159 */             THROWABLE_CAUSE.set(throwable, cause);
/*     */           }
/*     */         } 
/* 162 */       } else if (message != null) {
/* 163 */         throwable = getThrowable(throwableClass, message);
/* 164 */         if (throwable == null) {
/* 165 */           throwable = getThrowable(throwableClass);
/* 166 */           if (throwable != null) {
/* 167 */             THROWABLE_MESSAGE.set(throwable, cause);
/*     */           }
/*     */         } 
/*     */       } else {
/* 171 */         throwable = getThrowable(throwableClass);
/*     */       } 
/*     */       
/* 174 */       if (throwable == null) {
/* 175 */         return null;
/*     */       }
/* 177 */       throwable.setStackTrace(stackTrace);
/* 178 */       return throwable;
/* 179 */     } catch (Exception e) {
/* 180 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Throwable getThrowable(Class<Throwable> throwableClass, String message, Throwable cause) {
/*     */     try {
/* 188 */       Constructor[] arrayOfConstructor = (Constructor[])throwableClass.getConstructors();
/* 189 */       for (Constructor<Throwable> constructor : arrayOfConstructor) {
/* 190 */         Class<?>[] parameterTypes = constructor.getParameterTypes();
/* 191 */         if (parameterTypes.length == 2) {
/* 192 */           if (String.class == parameterTypes[0] && Throwable.class.isAssignableFrom(parameterTypes[1]))
/* 193 */             return constructor.newInstance(new Object[] { message, cause }); 
/* 194 */           if (String.class == parameterTypes[1] && Throwable.class.isAssignableFrom(parameterTypes[0]))
/*     */           {
/* 196 */             return constructor.newInstance(new Object[] { cause, message });
/*     */           }
/*     */         } 
/*     */       } 
/* 200 */       return null;
/* 201 */     } catch (Exception e) {
/* 202 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Throwable getThrowable(Class<Throwable> throwableClass, Throwable cause) {
/*     */     try {
/* 210 */       Constructor[] arrayOfConstructor = (Constructor[])throwableClass.getConstructors();
/* 211 */       for (Constructor<Throwable> constructor : arrayOfConstructor) {
/* 212 */         Class<?>[] parameterTypes = constructor.getParameterTypes();
/* 213 */         if (parameterTypes.length == 1 && Throwable.class.isAssignableFrom(parameterTypes[0])) {
/* 214 */           return constructor.newInstance(new Object[] { cause });
/*     */         }
/*     */       } 
/* 217 */       return null;
/* 218 */     } catch (Exception e) {
/* 219 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Throwable getThrowable(Class<Throwable> throwableClass, String message) {
/*     */     try {
/* 225 */       return throwableClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
/* 226 */     } catch (Exception e) {
/* 227 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Throwable getThrowable(Class<Throwable> throwableClass) {
/*     */     try {
/* 233 */       return throwableClass.newInstance();
/* 234 */     } catch (Exception e) {
/* 235 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ThrowableAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */