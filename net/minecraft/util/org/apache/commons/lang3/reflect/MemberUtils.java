/*     */ package net.minecraft.util.org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Modifier;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ClassUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MemberUtils
/*     */ {
/*     */   private static final int ACCESS_TEST = 7;
/*  39 */   private static final Class<?>[] ORDERED_PRIMITIVE_TYPES = new Class[] { byte.class, short.class, char.class, int.class, long.class, float.class, double.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setAccessibleWorkaround(AccessibleObject o) {
/*  55 */     if (o == null || o.isAccessible()) {
/*     */       return;
/*     */     }
/*  58 */     Member m = (Member)o;
/*  59 */     if (Modifier.isPublic(m.getModifiers()) && isPackageAccess(m.getDeclaringClass().getModifiers())) {
/*     */       
/*     */       try {
/*  62 */         o.setAccessible(true);
/*  63 */       } catch (SecurityException e) {}
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
/*     */   static boolean isPackageAccess(int modifiers) {
/*  75 */     return ((modifiers & 0x7) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isAccessible(Member m) {
/*  84 */     return (m != null && Modifier.isPublic(m.getModifiers()) && !m.isSynthetic());
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
/*     */   static int compareParameterTypes(Class<?>[] left, Class<?>[] right, Class<?>[] actual) {
/* 100 */     float leftCost = getTotalTransformationCost(actual, left);
/* 101 */     float rightCost = getTotalTransformationCost(actual, right);
/* 102 */     return (leftCost < rightCost) ? -1 : ((rightCost < leftCost) ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getTotalTransformationCost(Class<?>[] srcArgs, Class<?>[] destArgs) {
/* 113 */     float totalCost = 0.0F;
/* 114 */     for (int i = 0; i < srcArgs.length; i++) {
/*     */       
/* 116 */       Class<?> srcClass = srcArgs[i];
/* 117 */       Class<?> destClass = destArgs[i];
/* 118 */       totalCost += getObjectTransformationCost(srcClass, destClass);
/*     */     } 
/* 120 */     return totalCost;
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
/*     */   private static float getObjectTransformationCost(Class<?> srcClass, Class<?> destClass) {
/* 132 */     if (destClass.isPrimitive()) {
/* 133 */       return getPrimitivePromotionCost(srcClass, destClass);
/*     */     }
/* 135 */     float cost = 0.0F;
/* 136 */     while (srcClass != null && !destClass.equals(srcClass)) {
/* 137 */       if (destClass.isInterface() && ClassUtils.isAssignable(srcClass, destClass)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 143 */         cost += 0.25F;
/*     */         break;
/*     */       } 
/* 146 */       cost++;
/* 147 */       srcClass = srcClass.getSuperclass();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (srcClass == null) {
/* 154 */       cost += 1.5F;
/*     */     }
/* 156 */     return cost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getPrimitivePromotionCost(Class<?> srcClass, Class<?> destClass) {
/* 167 */     float cost = 0.0F;
/* 168 */     Class<?> cls = srcClass;
/* 169 */     if (!cls.isPrimitive()) {
/*     */       
/* 171 */       cost += 0.1F;
/* 172 */       cls = ClassUtils.wrapperToPrimitive(cls);
/*     */     } 
/* 174 */     for (int i = 0; cls != destClass && i < ORDERED_PRIMITIVE_TYPES.length; i++) {
/* 175 */       if (cls == ORDERED_PRIMITIVE_TYPES[i]) {
/* 176 */         cost += 0.1F;
/* 177 */         if (i < ORDERED_PRIMITIVE_TYPES.length - 1) {
/* 178 */           cls = ORDERED_PRIMITIVE_TYPES[i + 1];
/*     */         }
/*     */       } 
/*     */     } 
/* 182 */     return cost;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\reflect\MemberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */