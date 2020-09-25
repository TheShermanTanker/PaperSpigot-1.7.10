/*     */ package net.minecraft.util.com.google.common.reflect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Function;
/*     */ import net.minecraft.util.com.google.common.base.Joiner;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Predicates;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Types
/*     */ {
/*  53 */   private static final Function<Type, String> TYPE_TO_STRING = new Function<Type, String>()
/*     */     {
/*     */       public String apply(Type from) {
/*  56 */         return Types.toString(from);
/*     */       }
/*     */     };
/*     */   
/*  60 */   private static final Joiner COMMA_JOINER = Joiner.on(", ").useForNull("null");
/*     */ 
/*     */   
/*     */   static Type newArrayType(Type componentType) {
/*  64 */     if (componentType instanceof WildcardType) {
/*  65 */       WildcardType wildcard = (WildcardType)componentType;
/*  66 */       Type[] lowerBounds = wildcard.getLowerBounds();
/*  67 */       Preconditions.checkArgument((lowerBounds.length <= 1), "Wildcard cannot have more than one lower bounds.");
/*  68 */       if (lowerBounds.length == 1) {
/*  69 */         return supertypeOf(newArrayType(lowerBounds[0]));
/*     */       }
/*  71 */       Type[] upperBounds = wildcard.getUpperBounds();
/*  72 */       Preconditions.checkArgument((upperBounds.length == 1), "Wildcard should have only one upper bound.");
/*  73 */       return subtypeOf(newArrayType(upperBounds[0]));
/*     */     } 
/*     */     
/*  76 */     return JavaVersion.CURRENT.newArrayType(componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ParameterizedType newParameterizedTypeWithOwner(@Nullable Type ownerType, Class<?> rawType, Type... arguments) {
/*  85 */     if (ownerType == null) {
/*  86 */       return newParameterizedType(rawType, arguments);
/*     */     }
/*     */     
/*  89 */     Preconditions.checkNotNull(arguments);
/*  90 */     Preconditions.checkArgument((rawType.getEnclosingClass() != null), "Owner type for unenclosed %s", new Object[] { rawType });
/*  91 */     return new ParameterizedTypeImpl(ownerType, rawType, arguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ParameterizedType newParameterizedType(Class<?> rawType, Type... arguments) {
/*  99 */     return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(rawType), rawType, arguments);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum ClassOwnership
/*     */   {
/* 106 */     OWNED_BY_ENCLOSING_CLASS
/*     */     {
/*     */       @Nullable
/*     */       Class<?> getOwnerType(Class<?> rawType) {
/* 110 */         return rawType.getEnclosingClass();
/*     */       }
/*     */     },
/* 113 */     LOCAL_CLASS_HAS_NO_OWNER
/*     */     {
/*     */       @Nullable
/*     */       Class<?> getOwnerType(Class<?> rawType) {
/* 117 */         if (rawType.isLocalClass()) {
/* 118 */           return null;
/*     */         }
/* 120 */         return rawType.getEnclosingClass();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     static final ClassOwnership JVM_BEHAVIOR = detectJvmBehavior();
/*     */ 
/*     */     
/*     */     private static ClassOwnership detectJvmBehavior() {
/* 131 */       Class<?> subclass = (new LocalClass<String>() {  }).getClass();
/* 132 */       ParameterizedType parameterizedType = (ParameterizedType)subclass.getGenericSuperclass();
/*     */       
/* 134 */       for (ClassOwnership behavior : values()) {
/* 135 */         if (behavior.getOwnerType(LocalClass.class) == parameterizedType.getOwnerType()) {
/* 136 */           return behavior;
/*     */         }
/*     */       } 
/* 139 */       throw new AssertionError();
/*     */     }
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     abstract Class<?> getOwnerType(Class<?> param1Class); }
/*     */   
/*     */   static <D extends GenericDeclaration> TypeVariable<D> newTypeVariable(D declaration, String name, Type... bounds) {
/* 149 */     (new Type[1])[0] = Object.class; return new TypeVariableImpl<D>(declaration, name, (bounds.length == 0) ? new Type[1] : bounds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static WildcardType subtypeOf(Type upperBound) {
/* 159 */     return new WildcardTypeImpl(new Type[0], new Type[] { upperBound });
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static WildcardType supertypeOf(Type lowerBound) {
/* 164 */     return new WildcardTypeImpl(new Type[] { lowerBound }, new Type[] { Object.class });
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
/*     */   static String toString(Type type) {
/* 177 */     return (type instanceof Class) ? ((Class)type).getName() : type.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static Type getComponentType(Type type) {
/* 183 */     Preconditions.checkNotNull(type);
/* 184 */     final AtomicReference<Type> result = new AtomicReference<Type>();
/* 185 */     (new TypeVisitor() {
/*     */         void visitTypeVariable(TypeVariable<?> t) {
/* 187 */           result.set(Types.subtypeOfComponentType(t.getBounds()));
/*     */         }
/*     */         void visitWildcardType(WildcardType t) {
/* 190 */           result.set(Types.subtypeOfComponentType(t.getUpperBounds()));
/*     */         }
/*     */         void visitGenericArrayType(GenericArrayType t) {
/* 193 */           result.set(t.getGenericComponentType());
/*     */         }
/*     */         void visitClass(Class<?> t) {
/* 196 */           result.set(t.getComponentType());
/*     */         }
/*     */       }).visit(new Type[] { type });
/* 199 */     return result.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Type subtypeOfComponentType(Type[] bounds) {
/* 207 */     for (Type bound : bounds) {
/* 208 */       Type componentType = getComponentType(bound);
/* 209 */       if (componentType != null) {
/*     */ 
/*     */         
/* 212 */         if (componentType instanceof Class) {
/* 213 */           Class<?> componentClass = (Class)componentType;
/* 214 */           if (componentClass.isPrimitive()) {
/* 215 */             return componentClass;
/*     */           }
/*     */         } 
/* 218 */         return subtypeOf(componentType);
/*     */       } 
/*     */     } 
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   private static final class GenericArrayTypeImpl
/*     */     implements GenericArrayType, Serializable {
/*     */     private final Type componentType;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     GenericArrayTypeImpl(Type componentType) {
/* 230 */       this.componentType = Types.JavaVersion.CURRENT.usedInGenericType(componentType);
/*     */     }
/*     */     
/*     */     public Type getGenericComponentType() {
/* 234 */       return this.componentType;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 238 */       return Types.toString(this.componentType) + "[]";
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 242 */       return this.componentType.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/* 246 */       if (obj instanceof GenericArrayType) {
/* 247 */         GenericArrayType that = (GenericArrayType)obj;
/* 248 */         return Objects.equal(getGenericComponentType(), that.getGenericComponentType());
/*     */       } 
/*     */       
/* 251 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ParameterizedTypeImpl
/*     */     implements ParameterizedType, Serializable
/*     */   {
/*     */     private final Type ownerType;
/*     */     
/*     */     private final ImmutableList<Type> argumentsList;
/*     */     private final Class<?> rawType;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ParameterizedTypeImpl(@Nullable Type ownerType, Class<?> rawType, Type[] typeArguments) {
/* 266 */       Preconditions.checkNotNull(rawType);
/* 267 */       Preconditions.checkArgument((typeArguments.length == (rawType.getTypeParameters()).length));
/* 268 */       Types.disallowPrimitiveType(typeArguments, "type parameter");
/* 269 */       this.ownerType = ownerType;
/* 270 */       this.rawType = rawType;
/* 271 */       this.argumentsList = Types.JavaVersion.CURRENT.usedInGenericType(typeArguments);
/*     */     }
/*     */     
/*     */     public Type[] getActualTypeArguments() {
/* 275 */       return Types.toArray((Collection<Type>)this.argumentsList);
/*     */     }
/*     */     
/*     */     public Type getRawType() {
/* 279 */       return this.rawType;
/*     */     }
/*     */     
/*     */     public Type getOwnerType() {
/* 283 */       return this.ownerType;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 287 */       StringBuilder builder = new StringBuilder();
/* 288 */       if (this.ownerType != null) {
/* 289 */         builder.append(Types.toString(this.ownerType)).append('.');
/*     */       }
/* 291 */       builder.append(this.rawType.getName()).append('<').append(Types.COMMA_JOINER.join(Iterables.transform((Iterable)this.argumentsList, Types.TYPE_TO_STRING))).append('>');
/*     */ 
/*     */ 
/*     */       
/* 295 */       return builder.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 299 */       return ((this.ownerType == null) ? 0 : this.ownerType.hashCode()) ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 304 */       if (!(other instanceof ParameterizedType)) {
/* 305 */         return false;
/*     */       }
/* 307 */       ParameterizedType that = (ParameterizedType)other;
/* 308 */       return (getRawType().equals(that.getRawType()) && Objects.equal(getOwnerType(), that.getOwnerType()) && Arrays.equals((Object[])getActualTypeArguments(), (Object[])that.getActualTypeArguments()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class TypeVariableImpl<D extends GenericDeclaration>
/*     */     implements TypeVariable<D>
/*     */   {
/*     */     private final D genericDeclaration;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final ImmutableList<Type> bounds;
/*     */ 
/*     */     
/*     */     TypeVariableImpl(D genericDeclaration, String name, Type[] bounds) {
/* 325 */       Types.disallowPrimitiveType(bounds, "bound for type variable");
/* 326 */       this.genericDeclaration = (D)Preconditions.checkNotNull(genericDeclaration);
/* 327 */       this.name = (String)Preconditions.checkNotNull(name);
/* 328 */       this.bounds = ImmutableList.copyOf((Object[])bounds);
/*     */     }
/*     */     
/*     */     public Type[] getBounds() {
/* 332 */       return Types.toArray((Collection<Type>)this.bounds);
/*     */     }
/*     */     
/*     */     public D getGenericDeclaration() {
/* 336 */       return this.genericDeclaration;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 340 */       return this.name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 344 */       return this.name;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 348 */       return this.genericDeclaration.hashCode() ^ this.name.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/* 352 */       if (obj instanceof TypeVariable) {
/* 353 */         TypeVariable<?> that = (TypeVariable)obj;
/* 354 */         return (this.name.equals(that.getName()) && this.genericDeclaration.equals(that.getGenericDeclaration()));
/*     */       } 
/*     */       
/* 357 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class WildcardTypeImpl implements WildcardType, Serializable {
/*     */     private final ImmutableList<Type> lowerBounds;
/*     */     private final ImmutableList<Type> upperBounds;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
/* 367 */       Types.disallowPrimitiveType(lowerBounds, "lower bound for wildcard");
/* 368 */       Types.disallowPrimitiveType(upperBounds, "upper bound for wildcard");
/* 369 */       this.lowerBounds = Types.JavaVersion.CURRENT.usedInGenericType(lowerBounds);
/* 370 */       this.upperBounds = Types.JavaVersion.CURRENT.usedInGenericType(upperBounds);
/*     */     }
/*     */     
/*     */     public Type[] getLowerBounds() {
/* 374 */       return Types.toArray((Collection<Type>)this.lowerBounds);
/*     */     }
/*     */     
/*     */     public Type[] getUpperBounds() {
/* 378 */       return Types.toArray((Collection<Type>)this.upperBounds);
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/* 382 */       if (obj instanceof WildcardType) {
/* 383 */         WildcardType that = (WildcardType)obj;
/* 384 */         return (this.lowerBounds.equals(Arrays.asList(that.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(that.getUpperBounds())));
/*     */       } 
/*     */       
/* 387 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 391 */       return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 395 */       StringBuilder builder = new StringBuilder("?");
/* 396 */       for (Type lowerBound : this.lowerBounds) {
/* 397 */         builder.append(" super ").append(Types.toString(lowerBound));
/*     */       }
/* 399 */       for (Type upperBound : Types.filterUpperBounds((Iterable<Type>)this.upperBounds)) {
/* 400 */         builder.append(" extends ").append(Types.toString(upperBound));
/*     */       }
/* 402 */       return builder.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type[] toArray(Collection<Type> types) {
/* 409 */     return types.<Type>toArray(new Type[types.size()]);
/*     */   }
/*     */   
/*     */   private static Iterable<Type> filterUpperBounds(Iterable<Type> bounds) {
/* 413 */     return Iterables.filter(bounds, Predicates.not(Predicates.equalTo(Object.class)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void disallowPrimitiveType(Type[] types, String usedAs) {
/* 418 */     for (Type type : types) {
/* 419 */       if (type instanceof Class) {
/* 420 */         Class<?> cls = (Class)type;
/* 421 */         Preconditions.checkArgument(!cls.isPrimitive(), "Primitive type '%s' used as %s", new Object[] { cls, usedAs });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?> getArrayClass(Class<?> componentType) {
/* 432 */     return Array.newInstance(componentType, 0).getClass();
/*     */   }
/*     */ 
/*     */   
/*     */   enum JavaVersion
/*     */   {
/* 438 */     JAVA6 {
/*     */       GenericArrayType newArrayType(Type componentType) {
/* 440 */         return new Types.GenericArrayTypeImpl(componentType);
/*     */       }
/*     */       Type usedInGenericType(Type type) {
/* 443 */         Preconditions.checkNotNull(type);
/* 444 */         if (type instanceof Class) {
/* 445 */           Class<?> cls = (Class)type;
/* 446 */           if (cls.isArray()) {
/* 447 */             return new Types.GenericArrayTypeImpl(cls.getComponentType());
/*     */           }
/*     */         } 
/* 450 */         return type;
/*     */       }
/*     */     },
/* 453 */     JAVA7 {
/*     */       Type newArrayType(Type componentType) {
/* 455 */         if (componentType instanceof Class) {
/* 456 */           return Types.getArrayClass((Class)componentType);
/*     */         }
/* 458 */         return new Types.GenericArrayTypeImpl(componentType);
/*     */       }
/*     */       
/*     */       Type usedInGenericType(Type type) {
/* 462 */         return (Type)Preconditions.checkNotNull(type);
/*     */       }
/*     */     };
/*     */ 
/*     */     
/* 467 */     static final JavaVersion CURRENT = ((new TypeCapture<int[]>() {  }).capture() instanceof Class) ? JAVA7 : JAVA6;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     final ImmutableList<Type> usedInGenericType(Type[] types) {
/* 474 */       ImmutableList.Builder<Type> builder = ImmutableList.builder();
/* 475 */       for (Type type : types) {
/* 476 */         builder.add(usedInGenericType(type));
/*     */       }
/* 478 */       return builder.build();
/*     */     }
/*     */     
/*     */     abstract Type newArrayType(Type param1Type);
/*     */     
/*     */     abstract Type usedInGenericType(Type param1Type);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\reflect\Types.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */