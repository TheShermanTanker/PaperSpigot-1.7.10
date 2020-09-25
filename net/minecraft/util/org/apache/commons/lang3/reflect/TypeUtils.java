/*      */ package net.minecraft.util.org.apache.commons.lang3.reflect;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.GenericDeclaration;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.lang.reflect.WildcardType;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ClassUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ObjectUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*      */ import net.minecraft.util.org.apache.commons.lang3.builder.Builder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TypeUtils
/*      */ {
/*      */   public static class WildcardTypeBuilder
/*      */     implements Builder<WildcardType>
/*      */   {
/*      */     private Type[] upperBounds;
/*      */     private Type[] lowerBounds;
/*      */     
/*      */     private WildcardTypeBuilder() {}
/*      */     
/*      */     public WildcardTypeBuilder withUpperBounds(Type... bounds) {
/*   69 */       this.upperBounds = bounds;
/*   70 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WildcardTypeBuilder withLowerBounds(Type... bounds) {
/*   79 */       this.lowerBounds = bounds;
/*   80 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WildcardType build() {
/*   88 */       return new TypeUtils.WildcardTypeImpl(this.upperBounds, this.lowerBounds);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class GenericArrayTypeImpl
/*      */     implements GenericArrayType
/*      */   {
/*      */     private final Type componentType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private GenericArrayTypeImpl(Type componentType) {
/*  104 */       this.componentType = componentType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getGenericComponentType() {
/*  112 */       return this.componentType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  120 */       return TypeUtils.toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  128 */       return (obj == this || (obj instanceof GenericArrayType && TypeUtils.equals(this, (GenericArrayType)obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  136 */       int result = 1072;
/*  137 */       result |= this.componentType.hashCode();
/*  138 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ParameterizedTypeImpl
/*      */     implements ParameterizedType
/*      */   {
/*      */     private final Class<?> raw;
/*      */ 
/*      */     
/*      */     private final Type useOwner;
/*      */ 
/*      */     
/*      */     private final Type[] typeArguments;
/*      */ 
/*      */ 
/*      */     
/*      */     private ParameterizedTypeImpl(Class<?> raw, Type useOwner, Type[] typeArguments) {
/*  158 */       this.raw = raw;
/*  159 */       this.useOwner = useOwner;
/*  160 */       this.typeArguments = typeArguments;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getRawType() {
/*  168 */       return this.raw;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type getOwnerType() {
/*  176 */       return this.useOwner;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type[] getActualTypeArguments() {
/*  184 */       return (Type[])this.typeArguments.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  192 */       return TypeUtils.toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  200 */       return (obj == this || (obj instanceof ParameterizedType && TypeUtils.equals(this, (ParameterizedType)obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  208 */       int result = 1136;
/*  209 */       result |= this.raw.hashCode();
/*  210 */       result <<= 4;
/*  211 */       result |= ObjectUtils.hashCode(this.useOwner);
/*  212 */       result <<= 8;
/*  213 */       result |= Arrays.hashCode((Object[])this.typeArguments);
/*  214 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class WildcardTypeImpl
/*      */     implements WildcardType
/*      */   {
/*  223 */     private static final Type[] EMPTY_BOUNDS = new Type[0];
/*      */ 
/*      */     
/*      */     private final Type[] upperBounds;
/*      */ 
/*      */     
/*      */     private final Type[] lowerBounds;
/*      */ 
/*      */ 
/*      */     
/*      */     private WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
/*  234 */       this.upperBounds = (Type[])ObjectUtils.defaultIfNull(upperBounds, EMPTY_BOUNDS);
/*  235 */       this.lowerBounds = (Type[])ObjectUtils.defaultIfNull(lowerBounds, EMPTY_BOUNDS);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type[] getUpperBounds() {
/*  243 */       return (Type[])this.upperBounds.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Type[] getLowerBounds() {
/*  251 */       return (Type[])this.lowerBounds.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  259 */       return TypeUtils.toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  267 */       return (obj == this || (obj instanceof WildcardType && TypeUtils.equals(this, (WildcardType)obj)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  275 */       int result = 18688;
/*  276 */       result |= Arrays.hashCode((Object[])this.upperBounds);
/*  277 */       result <<= 8;
/*  278 */       result |= Arrays.hashCode((Object[])this.lowerBounds);
/*  279 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  287 */   public static final WildcardType WILDCARD_ALL = wildcardType().withUpperBounds(new Type[] { Object.class }).build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Type type, Type toType) {
/*  311 */     return isAssignable(type, toType, (Map<TypeVariable<?>, Type>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignable(Type type, Type toType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  325 */     if (toType == null || toType instanceof Class) {
/*  326 */       return isAssignable(type, (Class)toType);
/*      */     }
/*      */     
/*  329 */     if (toType instanceof ParameterizedType) {
/*  330 */       return isAssignable(type, (ParameterizedType)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  333 */     if (toType instanceof GenericArrayType) {
/*  334 */       return isAssignable(type, (GenericArrayType)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  337 */     if (toType instanceof WildcardType) {
/*  338 */       return isAssignable(type, (WildcardType)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  341 */     if (toType instanceof TypeVariable) {
/*  342 */       return isAssignable(type, (TypeVariable)toType, typeVarAssigns);
/*      */     }
/*      */     
/*  345 */     throw new IllegalStateException("found an unhandled type: " + toType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignable(Type type, Class<?> toClass) {
/*  357 */     if (type == null)
/*      */     {
/*  359 */       return (toClass == null || !toClass.isPrimitive());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  364 */     if (toClass == null) {
/*  365 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  369 */     if (toClass.equals(type)) {
/*  370 */       return true;
/*      */     }
/*      */     
/*  373 */     if (type instanceof Class)
/*      */     {
/*  375 */       return ClassUtils.isAssignable((Class)type, toClass);
/*      */     }
/*      */     
/*  378 */     if (type instanceof ParameterizedType)
/*      */     {
/*  380 */       return isAssignable(getRawType((ParameterizedType)type), toClass);
/*      */     }
/*      */ 
/*      */     
/*  384 */     if (type instanceof TypeVariable) {
/*      */ 
/*      */       
/*  387 */       for (Type bound : ((TypeVariable)type).getBounds()) {
/*  388 */         if (isAssignable(bound, toClass)) {
/*  389 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  393 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  398 */     if (type instanceof GenericArrayType) {
/*  399 */       return (toClass.equals(Object.class) || (toClass.isArray() && isAssignable(((GenericArrayType)type).getGenericComponentType(), toClass.getComponentType())));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  407 */     if (type instanceof WildcardType) {
/*  408 */       return false;
/*      */     }
/*      */     
/*  411 */     throw new IllegalStateException("found an unhandled type: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignable(Type type, ParameterizedType toParameterizedType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  425 */     if (type == null) {
/*  426 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  431 */     if (toParameterizedType == null) {
/*  432 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  436 */     if (toParameterizedType.equals(type)) {
/*  437 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  441 */     Class<?> toClass = getRawType(toParameterizedType);
/*      */ 
/*      */     
/*  444 */     Map<TypeVariable<?>, Type> fromTypeVarAssigns = getTypeArguments(type, toClass, (Map<TypeVariable<?>, Type>)null);
/*      */ 
/*      */     
/*  447 */     if (fromTypeVarAssigns == null) {
/*  448 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  454 */     if (fromTypeVarAssigns.isEmpty()) {
/*  455 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  459 */     Map<TypeVariable<?>, Type> toTypeVarAssigns = getTypeArguments(toParameterizedType, toClass, typeVarAssigns);
/*      */ 
/*      */ 
/*      */     
/*  463 */     for (TypeVariable<?> var : toTypeVarAssigns.keySet()) {
/*  464 */       Type toTypeArg = unrollVariableAssignments(var, toTypeVarAssigns);
/*  465 */       Type fromTypeArg = unrollVariableAssignments(var, fromTypeVarAssigns);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  470 */       if (fromTypeArg != null && !toTypeArg.equals(fromTypeArg) && (!(toTypeArg instanceof WildcardType) || !isAssignable(fromTypeArg, toTypeArg, typeVarAssigns)))
/*      */       {
/*      */ 
/*      */         
/*  474 */         return false;
/*      */       }
/*      */     } 
/*  477 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type unrollVariableAssignments(TypeVariable<?> var, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*      */     Type result;
/*      */     while (true) {
/*  491 */       result = typeVarAssigns.get(var);
/*  492 */       if (result instanceof TypeVariable && !result.equals(var)) {
/*  493 */         var = (TypeVariable)result;
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  498 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignable(Type type, GenericArrayType toGenericArrayType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  513 */     if (type == null) {
/*  514 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  519 */     if (toGenericArrayType == null) {
/*  520 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  524 */     if (toGenericArrayType.equals(type)) {
/*  525 */       return true;
/*      */     }
/*      */     
/*  528 */     Type toComponentType = toGenericArrayType.getGenericComponentType();
/*      */     
/*  530 */     if (type instanceof Class) {
/*  531 */       Class<?> cls = (Class)type;
/*      */ 
/*      */       
/*  534 */       return (cls.isArray() && isAssignable(cls.getComponentType(), toComponentType, typeVarAssigns));
/*      */     } 
/*      */ 
/*      */     
/*  538 */     if (type instanceof GenericArrayType)
/*      */     {
/*  540 */       return isAssignable(((GenericArrayType)type).getGenericComponentType(), toComponentType, typeVarAssigns);
/*      */     }
/*      */ 
/*      */     
/*  544 */     if (type instanceof WildcardType) {
/*      */       
/*  546 */       for (Type bound : getImplicitUpperBounds((WildcardType)type)) {
/*  547 */         if (isAssignable(bound, toGenericArrayType)) {
/*  548 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  552 */       return false;
/*      */     } 
/*      */     
/*  555 */     if (type instanceof TypeVariable) {
/*      */ 
/*      */       
/*  558 */       for (Type bound : getImplicitBounds((TypeVariable)type)) {
/*  559 */         if (isAssignable(bound, toGenericArrayType)) {
/*  560 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  564 */       return false;
/*      */     } 
/*      */     
/*  567 */     if (type instanceof ParameterizedType)
/*      */     {
/*      */ 
/*      */       
/*  571 */       return false;
/*      */     }
/*      */     
/*  574 */     throw new IllegalStateException("found an unhandled type: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignable(Type type, WildcardType toWildcardType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  589 */     if (type == null) {
/*  590 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  595 */     if (toWildcardType == null) {
/*  596 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  600 */     if (toWildcardType.equals(type)) {
/*  601 */       return true;
/*      */     }
/*      */     
/*  604 */     Type[] toUpperBounds = getImplicitUpperBounds(toWildcardType);
/*  605 */     Type[] toLowerBounds = getImplicitLowerBounds(toWildcardType);
/*      */     
/*  607 */     if (type instanceof WildcardType) {
/*  608 */       WildcardType wildcardType = (WildcardType)type;
/*  609 */       Type[] upperBounds = getImplicitUpperBounds(wildcardType);
/*  610 */       Type[] lowerBounds = getImplicitLowerBounds(wildcardType);
/*      */       
/*  612 */       for (Type toBound : toUpperBounds) {
/*      */ 
/*      */         
/*  615 */         toBound = substituteTypeVariables(toBound, typeVarAssigns);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  620 */         for (Type bound : upperBounds) {
/*  621 */           if (!isAssignable(bound, toBound, typeVarAssigns)) {
/*  622 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  627 */       for (Type toBound : toLowerBounds) {
/*      */ 
/*      */         
/*  630 */         toBound = substituteTypeVariables(toBound, typeVarAssigns);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  635 */         for (Type bound : lowerBounds) {
/*  636 */           if (!isAssignable(toBound, bound, typeVarAssigns)) {
/*  637 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*  641 */       return true;
/*      */     } 
/*      */     
/*  644 */     for (Type toBound : toUpperBounds) {
/*      */ 
/*      */       
/*  647 */       if (!isAssignable(type, substituteTypeVariables(toBound, typeVarAssigns), typeVarAssigns))
/*      */       {
/*  649 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  653 */     for (Type toBound : toLowerBounds) {
/*      */ 
/*      */       
/*  656 */       if (!isAssignable(substituteTypeVariables(toBound, typeVarAssigns), type, typeVarAssigns))
/*      */       {
/*  658 */         return false;
/*      */       }
/*      */     } 
/*  661 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignable(Type type, TypeVariable<?> toTypeVariable, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  676 */     if (type == null) {
/*  677 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  682 */     if (toTypeVariable == null) {
/*  683 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  687 */     if (toTypeVariable.equals(type)) {
/*  688 */       return true;
/*      */     }
/*      */     
/*  691 */     if (type instanceof TypeVariable) {
/*      */ 
/*      */ 
/*      */       
/*  695 */       Type[] bounds = getImplicitBounds((TypeVariable)type);
/*      */       
/*  697 */       for (Type bound : bounds) {
/*  698 */         if (isAssignable(bound, toTypeVariable, typeVarAssigns)) {
/*  699 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  704 */     if (type instanceof Class || type instanceof ParameterizedType || type instanceof GenericArrayType || type instanceof WildcardType)
/*      */     {
/*  706 */       return false;
/*      */     }
/*      */     
/*  709 */     throw new IllegalStateException("found an unhandled type: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type substituteTypeVariables(Type type, Map<TypeVariable<?>, Type> typeVarAssigns) {
/*  721 */     if (type instanceof TypeVariable && typeVarAssigns != null) {
/*  722 */       Type replacementType = typeVarAssigns.get(type);
/*      */       
/*  724 */       if (replacementType == null) {
/*  725 */         throw new IllegalArgumentException("missing assignment type for type variable " + type);
/*      */       }
/*      */       
/*  728 */       return replacementType;
/*      */     } 
/*  730 */     return type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType type) {
/*  747 */     return getTypeArguments(type, getRawType(type), (Map<TypeVariable<?>, Type>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<TypeVariable<?>, Type> getTypeArguments(Type type, Class<?> toClass) {
/*  783 */     return getTypeArguments(type, toClass, (Map<TypeVariable<?>, Type>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(Type type, Class<?> toClass, Map<TypeVariable<?>, Type> subtypeVarAssigns) {
/*  796 */     if (type instanceof Class) {
/*  797 */       return getTypeArguments((Class)type, toClass, subtypeVarAssigns);
/*      */     }
/*      */     
/*  800 */     if (type instanceof ParameterizedType) {
/*  801 */       return getTypeArguments((ParameterizedType)type, toClass, subtypeVarAssigns);
/*      */     }
/*      */     
/*  804 */     if (type instanceof GenericArrayType) {
/*  805 */       return getTypeArguments(((GenericArrayType)type).getGenericComponentType(), toClass.isArray() ? toClass.getComponentType() : toClass, subtypeVarAssigns);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  811 */     if (type instanceof WildcardType) {
/*  812 */       for (Type bound : getImplicitUpperBounds((WildcardType)type)) {
/*      */         
/*  814 */         if (isAssignable(bound, toClass)) {
/*  815 */           return getTypeArguments(bound, toClass, subtypeVarAssigns);
/*      */         }
/*      */       } 
/*      */       
/*  819 */       return null;
/*      */     } 
/*      */     
/*  822 */     if (type instanceof TypeVariable) {
/*  823 */       for (Type bound : getImplicitBounds((TypeVariable)type)) {
/*      */         
/*  825 */         if (isAssignable(bound, toClass)) {
/*  826 */           return getTypeArguments(bound, toClass, subtypeVarAssigns);
/*      */         }
/*      */       } 
/*      */       
/*  830 */       return null;
/*      */     } 
/*  832 */     throw new IllegalStateException("found an unhandled type: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType parameterizedType, Class<?> toClass, Map<TypeVariable<?>, Type> subtypeVarAssigns) {
/*      */     Map<TypeVariable<?>, Type> typeVarAssigns;
/*  846 */     Class<?> cls = getRawType(parameterizedType);
/*      */ 
/*      */     
/*  849 */     if (!isAssignable(cls, toClass)) {
/*  850 */       return null;
/*      */     }
/*      */     
/*  853 */     Type ownerType = parameterizedType.getOwnerType();
/*      */ 
/*      */     
/*  856 */     if (ownerType instanceof ParameterizedType) {
/*      */       
/*  858 */       ParameterizedType parameterizedOwnerType = (ParameterizedType)ownerType;
/*  859 */       typeVarAssigns = getTypeArguments(parameterizedOwnerType, getRawType(parameterizedOwnerType), subtypeVarAssigns);
/*      */     }
/*      */     else {
/*      */       
/*  863 */       typeVarAssigns = (subtypeVarAssigns == null) ? new HashMap<TypeVariable<?>, Type>() : new HashMap<TypeVariable<?>, Type>(subtypeVarAssigns);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  868 */     Type[] typeArgs = parameterizedType.getActualTypeArguments();
/*      */     
/*  870 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])cls.getTypeParameters();
/*      */ 
/*      */     
/*  873 */     for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/*  874 */       Type typeArg = typeArgs[i];
/*  875 */       typeVarAssigns.put(arrayOfTypeVariable[i], typeVarAssigns.containsKey(typeArg) ? typeVarAssigns.get(typeArg) : typeArg);
/*      */     } 
/*      */ 
/*      */     
/*  879 */     if (toClass.equals(cls))
/*      */     {
/*  881 */       return typeVarAssigns;
/*      */     }
/*      */ 
/*      */     
/*  885 */     return getTypeArguments(getClosestParentType(cls, toClass), toClass, typeVarAssigns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(Class<?> cls, Class<?> toClass, Map<TypeVariable<?>, Type> subtypeVarAssigns) {
/*  899 */     if (!isAssignable(cls, toClass)) {
/*  900 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  904 */     if (cls.isPrimitive()) {
/*      */       
/*  906 */       if (toClass.isPrimitive())
/*      */       {
/*      */         
/*  909 */         return new HashMap<TypeVariable<?>, Type>();
/*      */       }
/*      */ 
/*      */       
/*  913 */       cls = ClassUtils.primitiveToWrapper(cls);
/*      */     } 
/*      */ 
/*      */     
/*  917 */     HashMap<TypeVariable<?>, Type> typeVarAssigns = (subtypeVarAssigns == null) ? new HashMap<TypeVariable<?>, Type>() : new HashMap<TypeVariable<?>, Type>(subtypeVarAssigns);
/*      */ 
/*      */ 
/*      */     
/*  921 */     if (toClass.equals(cls)) {
/*  922 */       return typeVarAssigns;
/*      */     }
/*      */ 
/*      */     
/*  926 */     return getTypeArguments(getClosestParentType(cls, toClass), toClass, typeVarAssigns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<TypeVariable<?>, Type> determineTypeArguments(Class<?> cls, ParameterizedType superType) {
/*  958 */     Validate.notNull(cls, "cls is null", new Object[0]);
/*  959 */     Validate.notNull(superType, "superType is null", new Object[0]);
/*      */     
/*  961 */     Class<?> superClass = getRawType(superType);
/*      */ 
/*      */     
/*  964 */     if (!isAssignable(cls, superClass)) {
/*  965 */       return null;
/*      */     }
/*      */     
/*  968 */     if (cls.equals(superClass)) {
/*  969 */       return getTypeArguments(superType, superClass, (Map<TypeVariable<?>, Type>)null);
/*      */     }
/*      */ 
/*      */     
/*  973 */     Type midType = getClosestParentType(cls, superClass);
/*      */ 
/*      */     
/*  976 */     if (midType instanceof Class) {
/*  977 */       return determineTypeArguments((Class)midType, superType);
/*      */     }
/*      */     
/*  980 */     ParameterizedType midParameterizedType = (ParameterizedType)midType;
/*  981 */     Class<?> midClass = getRawType(midParameterizedType);
/*      */ 
/*      */     
/*  984 */     Map<TypeVariable<?>, Type> typeVarAssigns = determineTypeArguments(midClass, superType);
/*      */     
/*  986 */     mapTypeVariablesToArguments(cls, midParameterizedType, typeVarAssigns);
/*      */     
/*  988 */     return typeVarAssigns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> void mapTypeVariablesToArguments(Class<T> cls, ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> typeVarAssigns) {
/* 1002 */     Type ownerType = parameterizedType.getOwnerType();
/*      */     
/* 1004 */     if (ownerType instanceof ParameterizedType)
/*      */     {
/* 1006 */       mapTypeVariablesToArguments(cls, (ParameterizedType)ownerType, typeVarAssigns);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1013 */     Type[] typeArgs = parameterizedType.getActualTypeArguments();
/*      */ 
/*      */ 
/*      */     
/* 1017 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])getRawType(parameterizedType).getTypeParameters();
/*      */ 
/*      */     
/* 1020 */     List<TypeVariable<Class<T>>> typeVarList = Arrays.asList(cls.getTypeParameters());
/*      */ 
/*      */     
/* 1023 */     for (int i = 0; i < typeArgs.length; i++) {
/* 1024 */       TypeVariable<?> typeVar = arrayOfTypeVariable[i];
/* 1025 */       Type typeArg = typeArgs[i];
/*      */ 
/*      */       
/* 1028 */       if (typeVarList.contains(typeArg) && typeVarAssigns.containsKey(typeVar))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1033 */         typeVarAssigns.put((TypeVariable)typeArg, typeVarAssigns.get(typeVar));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type getClosestParentType(Class<?> cls, Class<?> superClass) {
/* 1048 */     if (superClass.isInterface()) {
/*      */       
/* 1050 */       Type[] interfaceTypes = cls.getGenericInterfaces();
/*      */       
/* 1052 */       Type genericInterface = null;
/*      */ 
/*      */       
/* 1055 */       for (Type midType : interfaceTypes) {
/* 1056 */         Class<?> midClass = null;
/*      */         
/* 1058 */         if (midType instanceof ParameterizedType) {
/* 1059 */           midClass = getRawType((ParameterizedType)midType);
/* 1060 */         } else if (midType instanceof Class) {
/* 1061 */           midClass = (Class)midType;
/*      */         } else {
/* 1063 */           throw new IllegalStateException("Unexpected generic interface type found: " + midType);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1069 */         if (isAssignable(midClass, superClass) && isAssignable(genericInterface, midClass))
/*      */         {
/* 1071 */           genericInterface = midType;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1076 */       if (genericInterface != null) {
/* 1077 */         return genericInterface;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1083 */     return cls.getGenericSuperclass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInstance(Object value, Type type) {
/* 1095 */     if (type == null) {
/* 1096 */       return false;
/*      */     }
/*      */     
/* 1099 */     return (value == null) ? ((!(type instanceof Class) || !((Class)type).isPrimitive())) : isAssignable(value.getClass(), type, (Map<TypeVariable<?>, Type>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type[] normalizeUpperBounds(Type[] bounds) {
/* 1125 */     Validate.notNull(bounds, "null value specified for bounds array", new Object[0]);
/*      */     
/* 1127 */     if (bounds.length < 2) {
/* 1128 */       return bounds;
/*      */     }
/*      */     
/* 1131 */     Set<Type> types = new HashSet<Type>(bounds.length);
/*      */     
/* 1133 */     for (Type type1 : bounds) {
/* 1134 */       boolean subtypeFound = false;
/*      */       
/* 1136 */       for (Type type2 : bounds) {
/* 1137 */         if (type1 != type2 && isAssignable(type2, type1, (Map<TypeVariable<?>, Type>)null)) {
/* 1138 */           subtypeFound = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1143 */       if (!subtypeFound) {
/* 1144 */         types.add(type1);
/*      */       }
/*      */     } 
/*      */     
/* 1148 */     return types.<Type>toArray(new Type[types.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type[] getImplicitBounds(TypeVariable<?> typeVariable) {
/* 1161 */     Validate.notNull(typeVariable, "typeVariable is null", new Object[0]);
/* 1162 */     Type[] bounds = typeVariable.getBounds();
/*      */     
/* 1164 */     (new Type[1])[0] = Object.class; return (bounds.length == 0) ? new Type[1] : normalizeUpperBounds(bounds);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type[] getImplicitUpperBounds(WildcardType wildcardType) {
/* 1178 */     Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
/* 1179 */     Type[] bounds = wildcardType.getUpperBounds();
/*      */     
/* 1181 */     (new Type[1])[0] = Object.class; return (bounds.length == 0) ? new Type[1] : normalizeUpperBounds(bounds);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type[] getImplicitLowerBounds(WildcardType wildcardType) {
/* 1194 */     Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
/* 1195 */     Type[] bounds = wildcardType.getLowerBounds();
/*      */     
/* 1197 */     (new Type[1])[0] = null; return (bounds.length == 0) ? new Type[1] : bounds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean typesSatisfyVariables(Map<TypeVariable<?>, Type> typeVarAssigns) {
/* 1214 */     Validate.notNull(typeVarAssigns, "typeVarAssigns is null", new Object[0]);
/*      */ 
/*      */     
/* 1217 */     for (Map.Entry<TypeVariable<?>, Type> entry : typeVarAssigns.entrySet()) {
/* 1218 */       TypeVariable<?> typeVar = entry.getKey();
/* 1219 */       Type type = entry.getValue();
/*      */       
/* 1221 */       for (Type bound : getImplicitBounds(typeVar)) {
/* 1222 */         if (!isAssignable(type, substituteTypeVariables(bound, typeVarAssigns), typeVarAssigns))
/*      */         {
/* 1224 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 1228 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> getRawType(ParameterizedType parameterizedType) {
/* 1239 */     Type rawType = parameterizedType.getRawType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     if (!(rawType instanceof Class)) {
/* 1247 */       throw new IllegalStateException("Wait... What!? Type of rawType: " + rawType);
/*      */     }
/*      */     
/* 1250 */     return (Class)rawType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getRawType(Type type, Type assigningType) {
/* 1266 */     if (type instanceof Class)
/*      */     {
/* 1268 */       return (Class)type;
/*      */     }
/*      */     
/* 1271 */     if (type instanceof ParameterizedType)
/*      */     {
/* 1273 */       return getRawType((ParameterizedType)type);
/*      */     }
/*      */     
/* 1276 */     if (type instanceof TypeVariable) {
/* 1277 */       if (assigningType == null) {
/* 1278 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1282 */       Object genericDeclaration = ((TypeVariable)type).getGenericDeclaration();
/*      */ 
/*      */ 
/*      */       
/* 1286 */       if (!(genericDeclaration instanceof Class)) {
/* 1287 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1292 */       Map<TypeVariable<?>, Type> typeVarAssigns = getTypeArguments(assigningType, (Class)genericDeclaration);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       if (typeVarAssigns == null) {
/* 1298 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1302 */       Type typeArgument = typeVarAssigns.get(type);
/*      */       
/* 1304 */       if (typeArgument == null) {
/* 1305 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1309 */       return getRawType(typeArgument, assigningType);
/*      */     } 
/*      */     
/* 1312 */     if (type instanceof GenericArrayType) {
/*      */       
/* 1314 */       Class<?> rawComponentType = getRawType(((GenericArrayType)type).getGenericComponentType(), assigningType);
/*      */ 
/*      */ 
/*      */       
/* 1318 */       return Array.newInstance(rawComponentType, 0).getClass();
/*      */     } 
/*      */ 
/*      */     
/* 1322 */     if (type instanceof WildcardType) {
/* 1323 */       return null;
/*      */     }
/*      */     
/* 1326 */     throw new IllegalArgumentException("unknown type: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isArrayType(Type type) {
/* 1335 */     return (type instanceof GenericArrayType || (type instanceof Class && ((Class)type).isArray()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type getArrayComponentType(Type type) {
/* 1344 */     if (type instanceof Class) {
/* 1345 */       Class<?> clazz = (Class)type;
/* 1346 */       return clazz.isArray() ? clazz.getComponentType() : null;
/*      */     } 
/* 1348 */     if (type instanceof GenericArrayType) {
/* 1349 */       return ((GenericArrayType)type).getGenericComponentType();
/*      */     }
/* 1351 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type unrollVariables(Map<TypeVariable<?>, Type> typeArguments, Type type) {
/* 1363 */     if (typeArguments == null) {
/* 1364 */       typeArguments = Collections.emptyMap();
/*      */     }
/* 1366 */     if (containsTypeVariables(type)) {
/* 1367 */       if (type instanceof TypeVariable) {
/* 1368 */         return unrollVariables(typeArguments, typeArguments.get(type));
/*      */       }
/* 1370 */       if (type instanceof ParameterizedType) {
/* 1371 */         Map<TypeVariable<?>, Type> parameterizedTypeArguments; ParameterizedType p = (ParameterizedType)type;
/*      */         
/* 1373 */         if (p.getOwnerType() == null) {
/* 1374 */           parameterizedTypeArguments = typeArguments;
/*      */         } else {
/* 1376 */           parameterizedTypeArguments = new HashMap<TypeVariable<?>, Type>(typeArguments);
/* 1377 */           parameterizedTypeArguments.putAll(getTypeArguments(p));
/*      */         } 
/* 1379 */         Type[] args = p.getActualTypeArguments();
/* 1380 */         for (int i = 0; i < args.length; i++) {
/* 1381 */           Type unrolled = unrollVariables(parameterizedTypeArguments, args[i]);
/* 1382 */           if (unrolled != null) {
/* 1383 */             args[i] = unrolled;
/*      */           }
/*      */         } 
/* 1386 */         return parameterizeWithOwner(p.getOwnerType(), (Class)p.getRawType(), args);
/*      */       } 
/* 1388 */       if (type instanceof WildcardType) {
/* 1389 */         WildcardType wild = (WildcardType)type;
/* 1390 */         return wildcardType().withUpperBounds(unrollBounds(typeArguments, wild.getUpperBounds())).withLowerBounds(unrollBounds(typeArguments, wild.getLowerBounds())).build();
/*      */       } 
/*      */     } 
/*      */     
/* 1394 */     return type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type[] unrollBounds(Map<TypeVariable<?>, Type> typeArguments, Type[] bounds) {
/* 1406 */     Type[] result = bounds;
/* 1407 */     int i = 0;
/* 1408 */     for (; i < result.length; i++) {
/* 1409 */       Type unrolled = unrollVariables(typeArguments, result[i]);
/* 1410 */       if (unrolled == null) {
/* 1411 */         result = (Type[])ArrayUtils.remove((Object[])result, i--);
/*      */       } else {
/* 1413 */         result[i] = unrolled;
/*      */       } 
/*      */     } 
/* 1416 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean containsTypeVariables(Type type) {
/* 1427 */     if (type instanceof TypeVariable) {
/* 1428 */       return true;
/*      */     }
/* 1430 */     if (type instanceof Class) {
/* 1431 */       return ((((Class)type).getTypeParameters()).length > 0);
/*      */     }
/* 1433 */     if (type instanceof ParameterizedType) {
/* 1434 */       for (Type arg : ((ParameterizedType)type).getActualTypeArguments()) {
/* 1435 */         if (containsTypeVariables(arg)) {
/* 1436 */           return true;
/*      */         }
/*      */       } 
/* 1439 */       return false;
/*      */     } 
/* 1441 */     if (type instanceof WildcardType) {
/* 1442 */       WildcardType wild = (WildcardType)type;
/* 1443 */       return (containsTypeVariables(getImplicitLowerBounds(wild)[0]) || containsTypeVariables(getImplicitUpperBounds(wild)[0]));
/*      */     } 
/*      */     
/* 1446 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final ParameterizedType parameterize(Class<?> raw, Type... typeArguments) {
/* 1458 */     return parameterizeWithOwner((Type)null, raw, typeArguments);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final ParameterizedType parameterize(Class<?> raw, Map<TypeVariable<?>, Type> typeArgMappings) {
/* 1471 */     Validate.notNull(raw, "raw class is null", new Object[0]);
/* 1472 */     Validate.notNull(typeArgMappings, "typeArgMappings is null", new Object[0]);
/* 1473 */     return parameterizeWithOwner((Type)null, raw, extractTypeArgumentsFrom(typeArgMappings, (TypeVariable<?>[])raw.getTypeParameters()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final ParameterizedType parameterizeWithOwner(Type owner, Class<?> raw, Type... typeArguments) {
/*      */     Type useOwner;
/* 1488 */     Validate.notNull(raw, "raw class is null", new Object[0]);
/*      */     
/* 1490 */     if (raw.getEnclosingClass() == null) {
/* 1491 */       Validate.isTrue((owner == null), "no owner allowed for top-level %s", new Object[] { raw });
/* 1492 */       useOwner = null;
/* 1493 */     } else if (owner == null) {
/* 1494 */       useOwner = raw.getEnclosingClass();
/*      */     } else {
/* 1496 */       Validate.isTrue(isAssignable(owner, raw.getEnclosingClass()), "%s is invalid owner type for parameterized %s", new Object[] { owner, raw });
/*      */       
/* 1498 */       useOwner = owner;
/*      */     } 
/* 1500 */     Validate.noNullElements((Object[])typeArguments, "null type argument at index %s", new Object[0]);
/* 1501 */     Validate.isTrue(((raw.getTypeParameters()).length == typeArguments.length), "invalid number of type parameters specified: expected %s, got %s", new Object[] { Integer.valueOf((raw.getTypeParameters()).length), Integer.valueOf(typeArguments.length) });
/*      */ 
/*      */ 
/*      */     
/* 1505 */     return new ParameterizedTypeImpl(raw, useOwner, typeArguments);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final ParameterizedType parameterizeWithOwner(Type owner, Class<?> raw, Map<TypeVariable<?>, Type> typeArgMappings) {
/* 1519 */     Validate.notNull(raw, "raw class is null", new Object[0]);
/* 1520 */     Validate.notNull(typeArgMappings, "typeArgMappings is null", new Object[0]);
/* 1521 */     return parameterizeWithOwner(owner, raw, extractTypeArgumentsFrom(typeArgMappings, (TypeVariable<?>[])raw.getTypeParameters()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type[] extractTypeArgumentsFrom(Map<TypeVariable<?>, Type> mappings, TypeVariable<?>[] variables) {
/* 1531 */     Type[] result = new Type[variables.length];
/* 1532 */     int index = 0;
/* 1533 */     for (TypeVariable<?> var : variables) {
/* 1534 */       Validate.isTrue(mappings.containsKey(var), "missing argument mapping for %s", new Object[] { toString(var) });
/* 1535 */       result[index++] = mappings.get(var);
/*      */     } 
/* 1537 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WildcardTypeBuilder wildcardType() {
/* 1546 */     return new WildcardTypeBuilder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static GenericArrayType genericArrayType(Type componentType) {
/* 1558 */     return new GenericArrayTypeImpl((Type)Validate.notNull(componentType, "componentType is null", new Object[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(Type t1, Type t2) {
/* 1570 */     if (ObjectUtils.equals(t1, t2)) {
/* 1571 */       return true;
/*      */     }
/* 1573 */     if (t1 instanceof ParameterizedType) {
/* 1574 */       return equals((ParameterizedType)t1, t2);
/*      */     }
/* 1576 */     if (t1 instanceof GenericArrayType) {
/* 1577 */       return equals((GenericArrayType)t1, t2);
/*      */     }
/* 1579 */     if (t1 instanceof WildcardType) {
/* 1580 */       return equals((WildcardType)t1, t2);
/*      */     }
/* 1582 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(ParameterizedType p, Type t) {
/* 1593 */     if (t instanceof ParameterizedType) {
/* 1594 */       ParameterizedType other = (ParameterizedType)t;
/* 1595 */       if (equals(p.getRawType(), other.getRawType()) && equals(p.getOwnerType(), other.getOwnerType())) {
/* 1596 */         return equals(p.getActualTypeArguments(), other.getActualTypeArguments());
/*      */       }
/*      */     } 
/* 1599 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(GenericArrayType a, Type t) {
/* 1610 */     return (t instanceof GenericArrayType && equals(a.getGenericComponentType(), ((GenericArrayType)t).getGenericComponentType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(WildcardType w, Type t) {
/* 1622 */     if (t instanceof WildcardType) {
/* 1623 */       WildcardType other = (WildcardType)t;
/* 1624 */       return (equals(w.getLowerBounds(), other.getLowerBounds()) && equals(getImplicitUpperBounds(w), getImplicitUpperBounds(other)));
/*      */     } 
/*      */     
/* 1627 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equals(Type[] t1, Type[] t2) {
/* 1638 */     if (t1.length == t2.length) {
/* 1639 */       for (int i = 0; i < t1.length; i++) {
/* 1640 */         if (!equals(t1[i], t2[i])) {
/* 1641 */           return false;
/*      */         }
/*      */       } 
/* 1644 */       return true;
/*      */     } 
/* 1646 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(Type type) {
/* 1657 */     Validate.notNull(type);
/* 1658 */     if (type instanceof Class) {
/* 1659 */       return classToString((Class)type);
/*      */     }
/* 1661 */     if (type instanceof ParameterizedType) {
/* 1662 */       return parameterizedTypeToString((ParameterizedType)type);
/*      */     }
/* 1664 */     if (type instanceof WildcardType) {
/* 1665 */       return wildcardTypeToString((WildcardType)type);
/*      */     }
/* 1667 */     if (type instanceof TypeVariable) {
/* 1668 */       return typeVariableToString((TypeVariable)type);
/*      */     }
/* 1670 */     if (type instanceof GenericArrayType) {
/* 1671 */       return genericArrayTypeToString((GenericArrayType)type);
/*      */     }
/* 1673 */     throw new IllegalArgumentException(ObjectUtils.identityToString(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toLongString(TypeVariable<?> var) {
/* 1684 */     Validate.notNull(var, "var is null", new Object[0]);
/* 1685 */     StringBuilder buf = new StringBuilder();
/* 1686 */     GenericDeclaration d = (GenericDeclaration)var.getGenericDeclaration();
/* 1687 */     if (d instanceof Class) {
/* 1688 */       Class<?> c = (Class)d;
/*      */       while (true) {
/* 1690 */         if (c.getEnclosingClass() == null) {
/* 1691 */           buf.insert(0, c.getName());
/*      */           break;
/*      */         } 
/* 1694 */         buf.insert(0, c.getSimpleName()).insert(0, '.');
/* 1695 */         c = c.getEnclosingClass();
/*      */       } 
/* 1697 */     } else if (d instanceof Type) {
/* 1698 */       buf.append(toString((Type)d));
/*      */     } else {
/* 1700 */       buf.append(d);
/*      */     } 
/* 1702 */     return buf.append(':').append(typeVariableToString(var)).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Typed<T> wrap(final Type type) {
/* 1714 */     return new Typed<T>()
/*      */       {
/*      */         public Type getType() {
/* 1717 */           return type;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Typed<T> wrap(Class<T> type) {
/* 1731 */     return wrap(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String classToString(Class<?> c) {
/* 1741 */     StringBuilder buf = new StringBuilder();
/*      */     
/* 1743 */     if (c.getEnclosingClass() != null) {
/* 1744 */       buf.append(classToString(c.getEnclosingClass())).append('.').append(c.getSimpleName());
/*      */     } else {
/* 1746 */       buf.append(c.getName());
/*      */     } 
/* 1748 */     if ((c.getTypeParameters()).length > 0) {
/* 1749 */       buf.append('<');
/* 1750 */       appendAllTo(buf, ", ", (Type[])c.getTypeParameters());
/* 1751 */       buf.append('>');
/*      */     } 
/* 1753 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String typeVariableToString(TypeVariable<?> v) {
/* 1763 */     StringBuilder buf = new StringBuilder(v.getName());
/* 1764 */     Type[] bounds = v.getBounds();
/* 1765 */     if (bounds.length > 0 && (bounds.length != 1 || !Object.class.equals(bounds[0]))) {
/* 1766 */       buf.append(" extends ");
/* 1767 */       appendAllTo(buf, " & ", v.getBounds());
/*      */     } 
/* 1769 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parameterizedTypeToString(ParameterizedType p) {
/* 1779 */     StringBuilder buf = new StringBuilder();
/*      */     
/* 1781 */     Type useOwner = p.getOwnerType();
/* 1782 */     Class<?> raw = (Class)p.getRawType();
/* 1783 */     Type[] typeArguments = p.getActualTypeArguments();
/* 1784 */     if (useOwner == null) {
/* 1785 */       buf.append(raw.getName());
/*      */     } else {
/* 1787 */       if (useOwner instanceof Class) {
/* 1788 */         buf.append(((Class)useOwner).getName());
/*      */       } else {
/* 1790 */         buf.append(useOwner.toString());
/*      */       } 
/* 1792 */       buf.append('.').append(raw.getSimpleName());
/*      */     } 
/*      */     
/* 1795 */     appendAllTo(buf.append('<'), ", ", typeArguments).append('>');
/* 1796 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String wildcardTypeToString(WildcardType w) {
/* 1806 */     StringBuilder buf = (new StringBuilder()).append('?');
/* 1807 */     Type[] lowerBounds = w.getLowerBounds();
/* 1808 */     Type[] upperBounds = w.getUpperBounds();
/* 1809 */     if (lowerBounds.length > 0) {
/* 1810 */       appendAllTo(buf.append(" super "), " & ", lowerBounds);
/* 1811 */     } else if (upperBounds.length != 1 || !Object.class.equals(upperBounds[0])) {
/* 1812 */       appendAllTo(buf.append(" extends "), " & ", upperBounds);
/*      */     } 
/* 1814 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String genericArrayTypeToString(GenericArrayType g) {
/* 1824 */     return String.format("%s[]", new Object[] { toString(g.getGenericComponentType()) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static StringBuilder appendAllTo(StringBuilder buf, String sep, Type... types) {
/* 1836 */     Validate.notEmpty(Validate.noNullElements((Object[])types));
/* 1837 */     if (types.length > 0) {
/* 1838 */       buf.append(toString(types[0]));
/* 1839 */       for (int i = 1; i < types.length; i++) {
/* 1840 */         buf.append(sep).append(toString(types[i]));
/*      */       }
/*      */     } 
/* 1843 */     return buf;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\reflect\TypeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */