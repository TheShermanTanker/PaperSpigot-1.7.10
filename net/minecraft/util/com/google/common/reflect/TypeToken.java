/*      */ package net.minecraft.util.com.google.common.reflect;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.lang.reflect.WildcardType;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.util.com.google.common.annotations.Beta;
/*      */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*      */ import net.minecraft.util.com.google.common.base.Joiner;
/*      */ import net.minecraft.util.com.google.common.base.Preconditions;
/*      */ import net.minecraft.util.com.google.common.base.Predicate;
/*      */ import net.minecraft.util.com.google.common.collect.FluentIterable;
/*      */ import net.minecraft.util.com.google.common.collect.ForwardingSet;
/*      */ import net.minecraft.util.com.google.common.collect.ImmutableList;
/*      */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
/*      */ import net.minecraft.util.com.google.common.collect.ImmutableSet;
/*      */ import net.minecraft.util.com.google.common.collect.Maps;
/*      */ import net.minecraft.util.com.google.common.collect.Ordering;
/*      */ import net.minecraft.util.com.google.common.primitives.Primitives;
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
/*      */ 
/*      */ 
/*      */ @Beta
/*      */ public abstract class TypeToken<T>
/*      */   extends TypeCapture<T>
/*      */   implements Serializable
/*      */ {
/*      */   private final Type runtimeType;
/*      */   private transient TypeResolver typeResolver;
/*      */   
/*      */   protected TypeToken() {
/*  113 */     this.runtimeType = capture();
/*  114 */     Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", new Object[] { this.runtimeType });
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
/*      */   protected TypeToken(Class<?> declaringClass) {
/*  140 */     Type captured = capture();
/*  141 */     if (captured instanceof Class) {
/*  142 */       this.runtimeType = captured;
/*      */     } else {
/*  144 */       this.runtimeType = (of((Class)declaringClass).resolveType(captured)).runtimeType;
/*      */     } 
/*      */   }
/*      */   
/*      */   private TypeToken(Type type) {
/*  149 */     this.runtimeType = (Type)Preconditions.checkNotNull(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public static <T> TypeToken<T> of(Class<T> type) {
/*  154 */     return new SimpleTypeToken<T>(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public static TypeToken<?> of(Type type) {
/*  159 */     return new SimpleTypeToken(type);
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
/*      */   public final Class<? super T> getRawType() {
/*  177 */     Class<?> rawType = getRawType(this.runtimeType);
/*      */     
/*  179 */     Class<? super T> result = (Class)rawType;
/*  180 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ImmutableSet<Class<? super T>> getImmediateRawTypes() {
/*  190 */     ImmutableSet<Class<? super T>> result = (ImmutableSet)getRawTypes(this.runtimeType);
/*  191 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public final Type getType() {
/*  196 */     return this.runtimeType;
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
/*      */   public final <X> TypeToken<T> where(TypeParameter<X> typeParam, TypeToken<X> typeArg) {
/*  215 */     TypeResolver resolver = (new TypeResolver()).where((Map<? extends TypeVariable<?>, ? extends Type>)ImmutableMap.of(typeParam.typeVariable, typeArg.runtimeType));
/*      */ 
/*      */     
/*  218 */     return new SimpleTypeToken<T>(resolver.resolveType(this.runtimeType));
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
/*      */   public final <X> TypeToken<T> where(TypeParameter<X> typeParam, Class<X> typeArg) {
/*  237 */     return where(typeParam, of(typeArg));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final TypeToken<?> resolveType(Type type) {
/*  248 */     Preconditions.checkNotNull(type);
/*  249 */     TypeResolver resolver = this.typeResolver;
/*  250 */     if (resolver == null) {
/*  251 */       resolver = this.typeResolver = TypeResolver.accordingTo(this.runtimeType);
/*      */     }
/*  253 */     return of(resolver.resolveType(type));
/*      */   }
/*      */   
/*      */   private Type[] resolveInPlace(Type[] types) {
/*  257 */     for (int i = 0; i < types.length; i++) {
/*  258 */       types[i] = resolveType(types[i]).getType();
/*      */     }
/*  260 */     return types;
/*      */   }
/*      */   
/*      */   private TypeToken<?> resolveSupertype(Type type) {
/*  264 */     TypeToken<?> supertype = resolveType(type);
/*      */     
/*  266 */     supertype.typeResolver = this.typeResolver;
/*  267 */     return supertype;
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
/*      */   @Nullable
/*      */   final TypeToken<? super T> getGenericSuperclass() {
/*  285 */     if (this.runtimeType instanceof TypeVariable)
/*      */     {
/*  287 */       return boundAsSuperclass(((TypeVariable)this.runtimeType).getBounds()[0]);
/*      */     }
/*  289 */     if (this.runtimeType instanceof WildcardType)
/*      */     {
/*  291 */       return boundAsSuperclass(((WildcardType)this.runtimeType).getUpperBounds()[0]);
/*      */     }
/*  293 */     Type superclass = getRawType().getGenericSuperclass();
/*  294 */     if (superclass == null) {
/*  295 */       return null;
/*      */     }
/*      */     
/*  298 */     TypeToken<? super T> superToken = (TypeToken)resolveSupertype(superclass);
/*  299 */     return superToken;
/*      */   }
/*      */   @Nullable
/*      */   private TypeToken<? super T> boundAsSuperclass(Type bound) {
/*  303 */     TypeToken<?> token = of(bound);
/*  304 */     if (token.getRawType().isInterface()) {
/*  305 */       return null;
/*      */     }
/*      */     
/*  308 */     TypeToken<? super T> superclass = (TypeToken)token;
/*  309 */     return superclass;
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
/*      */   final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
/*  325 */     if (this.runtimeType instanceof TypeVariable) {
/*  326 */       return boundsAsInterfaces(((TypeVariable)this.runtimeType).getBounds());
/*      */     }
/*  328 */     if (this.runtimeType instanceof WildcardType) {
/*  329 */       return boundsAsInterfaces(((WildcardType)this.runtimeType).getUpperBounds());
/*      */     }
/*  331 */     ImmutableList.Builder<TypeToken<? super T>> builder = ImmutableList.builder();
/*  332 */     for (Type interfaceType : getRawType().getGenericInterfaces()) {
/*      */       
/*  334 */       TypeToken<? super T> resolvedInterface = (TypeToken)resolveSupertype(interfaceType);
/*      */       
/*  336 */       builder.add(resolvedInterface);
/*      */     } 
/*  338 */     return builder.build();
/*      */   }
/*      */   
/*      */   private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] bounds) {
/*  342 */     ImmutableList.Builder<TypeToken<? super T>> builder = ImmutableList.builder();
/*  343 */     for (Type bound : bounds) {
/*      */       
/*  345 */       TypeToken<? super T> boundType = (TypeToken)of(bound);
/*  346 */       if (boundType.getRawType().isInterface()) {
/*  347 */         builder.add(boundType);
/*      */       }
/*      */     } 
/*  350 */     return builder.build();
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
/*      */   public final TypeSet getTypes() {
/*  365 */     return new TypeSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final TypeToken<? super T> getSupertype(Class<? super T> superclass) {
/*  374 */     Preconditions.checkArgument(superclass.isAssignableFrom(getRawType()), "%s is not a super class of %s", new Object[] { superclass, this });
/*      */     
/*  376 */     if (this.runtimeType instanceof TypeVariable) {
/*  377 */       return getSupertypeFromUpperBounds(superclass, ((TypeVariable)this.runtimeType).getBounds());
/*      */     }
/*  379 */     if (this.runtimeType instanceof WildcardType) {
/*  380 */       return getSupertypeFromUpperBounds(superclass, ((WildcardType)this.runtimeType).getUpperBounds());
/*      */     }
/*  382 */     if (superclass.isArray()) {
/*  383 */       return getArraySupertype(superclass);
/*      */     }
/*      */     
/*  386 */     TypeToken<? super T> supertype = (TypeToken)resolveSupertype((toGenericType((Class)superclass)).runtimeType);
/*      */     
/*  388 */     return supertype;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final TypeToken<? extends T> getSubtype(Class<?> subclass) {
/*  397 */     Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", new Object[] { this });
/*      */     
/*  399 */     if (this.runtimeType instanceof WildcardType) {
/*  400 */       return getSubtypeFromLowerBounds(subclass, ((WildcardType)this.runtimeType).getLowerBounds());
/*      */     }
/*  402 */     Preconditions.checkArgument(getRawType().isAssignableFrom(subclass), "%s isn't a subclass of %s", new Object[] { subclass, this });
/*      */ 
/*      */     
/*  405 */     if (isArray()) {
/*  406 */       return getArraySubtype(subclass);
/*      */     }
/*      */     
/*  409 */     TypeToken<? extends T> subtype = (TypeToken)of(resolveTypeArgsForSubclass(subclass));
/*      */     
/*  411 */     return subtype;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isAssignableFrom(TypeToken<?> type) {
/*  416 */     return isAssignableFrom(type.runtimeType);
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isAssignableFrom(Type type) {
/*  421 */     return isAssignable((Type)Preconditions.checkNotNull(type), this.runtimeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isArray() {
/*  429 */     return (getComponentType() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isPrimitive() {
/*  438 */     return (this.runtimeType instanceof Class && ((Class)this.runtimeType).isPrimitive());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final TypeToken<T> wrap() {
/*  448 */     if (isPrimitive()) {
/*      */       
/*  450 */       Class<T> type = (Class<T>)this.runtimeType;
/*  451 */       return of(Primitives.wrap(type));
/*      */     } 
/*  453 */     return this;
/*      */   }
/*      */   
/*      */   private boolean isWrapper() {
/*  457 */     return Primitives.allWrapperTypes().contains(this.runtimeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final TypeToken<T> unwrap() {
/*  467 */     if (isWrapper()) {
/*      */       
/*  469 */       Class<T> type = (Class<T>)this.runtimeType;
/*  470 */       return of(Primitives.unwrap(type));
/*      */     } 
/*  472 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public final TypeToken<?> getComponentType() {
/*  480 */     Type componentType = Types.getComponentType(this.runtimeType);
/*  481 */     if (componentType == null) {
/*  482 */       return null;
/*      */     }
/*  484 */     return of(componentType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Invokable<T, Object> method(Method method) {
/*  493 */     Preconditions.checkArgument(of(method.getDeclaringClass()).isAssignableFrom(this), "%s not declared by %s", new Object[] { method, this });
/*      */     
/*  495 */     return new Invokable.MethodInvokable<T>(method) {
/*      */         Type getGenericReturnType() {
/*  497 */           return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
/*      */         }
/*      */         Type[] getGenericParameterTypes() {
/*  500 */           return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
/*      */         }
/*      */         Type[] getGenericExceptionTypes() {
/*  503 */           return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
/*      */         }
/*      */         public TypeToken<T> getOwnerType() {
/*  506 */           return TypeToken.this;
/*      */         }
/*      */         public String toString() {
/*  509 */           return getOwnerType() + "." + super.toString();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Invokable<T, T> constructor(Constructor<?> constructor) {
/*  520 */     Preconditions.checkArgument((constructor.getDeclaringClass() == getRawType()), "%s not declared by %s", new Object[] { constructor, getRawType() });
/*      */     
/*  522 */     return new Invokable.ConstructorInvokable<T>(constructor) {
/*      */         Type getGenericReturnType() {
/*  524 */           return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
/*      */         }
/*      */         Type[] getGenericParameterTypes() {
/*  527 */           return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
/*      */         }
/*      */         Type[] getGenericExceptionTypes() {
/*  530 */           return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
/*      */         }
/*      */         public TypeToken<T> getOwnerType() {
/*  533 */           return TypeToken.this;
/*      */         }
/*      */         public String toString() {
/*  536 */           return getOwnerType() + "(" + Joiner.on(", ").join((Object[])getGenericParameterTypes()) + ")";
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class TypeSet
/*      */     extends ForwardingSet<TypeToken<? super T>>
/*      */     implements Serializable
/*      */   {
/*      */     private transient ImmutableSet<TypeToken<? super T>> types;
/*      */     
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */     
/*      */     public TypeSet interfaces() {
/*  553 */       return new TypeToken.InterfaceSet(this);
/*      */     }
/*      */ 
/*      */     
/*      */     public TypeSet classes() {
/*  558 */       return new TypeToken.ClassSet();
/*      */     }
/*      */     
/*      */     protected Set<TypeToken<? super T>> delegate() {
/*  562 */       ImmutableSet<TypeToken<? super T>> filteredTypes = this.types;
/*  563 */       if (filteredTypes == null) {
/*      */ 
/*      */         
/*  566 */         ImmutableList<TypeToken<? super T>> collectedTypes = (ImmutableList)TypeToken.TypeCollector.FOR_GENERIC_TYPE.collectTypes(TypeToken.this);
/*      */         
/*  568 */         return (Set<TypeToken<? super T>>)(this.types = FluentIterable.from((Iterable)collectedTypes).filter(TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet());
/*      */       } 
/*      */ 
/*      */       
/*  572 */       return (Set<TypeToken<? super T>>)filteredTypes;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Class<? super T>> rawTypes() {
/*  580 */       ImmutableList<Class<? super T>> collectedTypes = (ImmutableList)TypeToken.TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable<? extends Class<?>>)TypeToken.this.getImmediateRawTypes());
/*      */       
/*  582 */       return (Set<Class<? super T>>)ImmutableSet.copyOf((Collection)collectedTypes);
/*      */     }
/*      */   }
/*      */   
/*      */   private final class InterfaceSet
/*      */     extends TypeSet
/*      */   {
/*      */     private final transient TypeToken<T>.TypeSet allTypes;
/*      */     private transient ImmutableSet<TypeToken<? super T>> interfaces;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     InterfaceSet(TypeToken<T>.TypeSet allTypes) {
/*  594 */       this.allTypes = allTypes;
/*      */     }
/*      */     
/*      */     protected Set<TypeToken<? super T>> delegate() {
/*  598 */       ImmutableSet<TypeToken<? super T>> result = this.interfaces;
/*  599 */       if (result == null) {
/*  600 */         return (Set<TypeToken<? super T>>)(this.interfaces = FluentIterable.from((Iterable)this.allTypes).filter(TypeToken.TypeFilter.INTERFACE_ONLY).toSet());
/*      */       }
/*      */ 
/*      */       
/*  604 */       return (Set<TypeToken<? super T>>)result;
/*      */     }
/*      */ 
/*      */     
/*      */     public TypeToken<T>.TypeSet interfaces() {
/*  609 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Class<? super T>> rawTypes() {
/*  615 */       ImmutableList<Class<? super T>> collectedTypes = (ImmutableList)TypeToken.TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable<? extends Class<?>>)TypeToken.this.getImmediateRawTypes());
/*      */       
/*  617 */       return (Set<Class<? super T>>)FluentIterable.from((Iterable)collectedTypes).filter(new Predicate<Class<?>>()
/*      */           {
/*      */             public boolean apply(Class<?> type) {
/*  620 */               return type.isInterface();
/*      */             }
/*      */           }).toSet();
/*      */     }
/*      */ 
/*      */     
/*      */     public TypeToken<T>.TypeSet classes() {
/*  627 */       throw new UnsupportedOperationException("interfaces().classes() not supported.");
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  631 */       return TypeToken.this.getTypes().interfaces();
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ClassSet extends TypeSet {
/*      */     private transient ImmutableSet<TypeToken<? super T>> classes;
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     private ClassSet() {}
/*      */     
/*      */     protected Set<TypeToken<? super T>> delegate() {
/*  642 */       ImmutableSet<TypeToken<? super T>> result = this.classes;
/*  643 */       if (result == null) {
/*      */         
/*  645 */         ImmutableList<TypeToken<? super T>> collectedTypes = TypeToken.TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes(TypeToken.this);
/*      */         
/*  647 */         return (Set<TypeToken<? super T>>)(this.classes = FluentIterable.from((Iterable)collectedTypes).filter(TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet());
/*      */       } 
/*      */ 
/*      */       
/*  651 */       return (Set<TypeToken<? super T>>)result;
/*      */     }
/*      */ 
/*      */     
/*      */     public TypeToken<T>.TypeSet classes() {
/*  656 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Class<? super T>> rawTypes() {
/*  662 */       ImmutableList<Class<? super T>> collectedTypes = TypeToken.TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes((Iterable<? extends Class<? super T>>)TypeToken.this.getImmediateRawTypes());
/*      */       
/*  664 */       return (Set<Class<? super T>>)ImmutableSet.copyOf((Collection)collectedTypes);
/*      */     }
/*      */     
/*      */     public TypeToken<T>.TypeSet interfaces() {
/*  668 */       throw new UnsupportedOperationException("classes().interfaces() not supported.");
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  672 */       return TypeToken.this.getTypes().classes();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private enum TypeFilter
/*      */     implements Predicate<TypeToken<?>>
/*      */   {
/*  680 */     IGNORE_TYPE_VARIABLE_OR_WILDCARD {
/*      */       public boolean apply(TypeToken<?> type) {
/*  682 */         return (!(type.runtimeType instanceof TypeVariable) && !(type.runtimeType instanceof WildcardType));
/*      */       }
/*      */     },
/*      */     
/*  686 */     INTERFACE_ONLY {
/*      */       public boolean apply(TypeToken<?> type) {
/*  688 */         return type.getRawType().isInterface();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object o) {
/*  697 */     if (o instanceof TypeToken) {
/*  698 */       TypeToken<?> that = (TypeToken)o;
/*  699 */       return this.runtimeType.equals(that.runtimeType);
/*      */     } 
/*  701 */     return false;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  705 */     return this.runtimeType.hashCode();
/*      */   }
/*      */   
/*      */   public String toString() {
/*  709 */     return Types.toString(this.runtimeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object writeReplace() {
/*  716 */     return of((new TypeResolver()).resolveType(this.runtimeType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final TypeToken<T> rejectTypeVariables() {
/*  724 */     (new TypeVisitor() {
/*      */         void visitTypeVariable(TypeVariable<?> type) {
/*  726 */           throw new IllegalArgumentException(TypeToken.this.runtimeType + "contains a type variable and is not safe for the operation");
/*      */         }
/*      */         
/*      */         void visitWildcardType(WildcardType type) {
/*  730 */           visit(type.getLowerBounds());
/*  731 */           visit(type.getUpperBounds());
/*      */         }
/*      */         void visitParameterizedType(ParameterizedType type) {
/*  734 */           visit(type.getActualTypeArguments());
/*  735 */           visit(new Type[] { type.getOwnerType() });
/*      */         }
/*      */         void visitGenericArrayType(GenericArrayType type) {
/*  738 */           visit(new Type[] { type.getGenericComponentType() });
/*      */         }
/*      */       }).visit(new Type[] { this.runtimeType });
/*  741 */     return this;
/*      */   }
/*      */   
/*      */   private static boolean isAssignable(Type from, Type to) {
/*  745 */     if (to.equals(from)) {
/*  746 */       return true;
/*      */     }
/*  748 */     if (to instanceof WildcardType) {
/*  749 */       return isAssignableToWildcardType(from, (WildcardType)to);
/*      */     }
/*      */ 
/*      */     
/*  753 */     if (from instanceof TypeVariable) {
/*  754 */       return isAssignableFromAny(((TypeVariable)from).getBounds(), to);
/*      */     }
/*      */ 
/*      */     
/*  758 */     if (from instanceof WildcardType) {
/*  759 */       return isAssignableFromAny(((WildcardType)from).getUpperBounds(), to);
/*      */     }
/*  761 */     if (from instanceof GenericArrayType) {
/*  762 */       return isAssignableFromGenericArrayType((GenericArrayType)from, to);
/*      */     }
/*      */     
/*  765 */     if (to instanceof Class)
/*  766 */       return isAssignableToClass(from, (Class)to); 
/*  767 */     if (to instanceof ParameterizedType)
/*  768 */       return isAssignableToParameterizedType(from, (ParameterizedType)to); 
/*  769 */     if (to instanceof GenericArrayType) {
/*  770 */       return isAssignableToGenericArrayType(from, (GenericArrayType)to);
/*      */     }
/*  772 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isAssignableFromAny(Type[] fromTypes, Type to) {
/*  777 */     for (Type from : fromTypes) {
/*  778 */       if (isAssignable(from, to)) {
/*  779 */         return true;
/*      */       }
/*      */     } 
/*  782 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isAssignableToClass(Type from, Class<?> to) {
/*  786 */     return to.isAssignableFrom(getRawType(from));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAssignableToWildcardType(Type from, WildcardType to) {
/*  796 */     return (isAssignable(from, supertypeBound(to)) && isAssignableBySubtypeBound(from, to));
/*      */   }
/*      */   
/*      */   private static boolean isAssignableBySubtypeBound(Type from, WildcardType to) {
/*  800 */     Type toSubtypeBound = subtypeBound(to);
/*  801 */     if (toSubtypeBound == null) {
/*  802 */       return true;
/*      */     }
/*  804 */     Type fromSubtypeBound = subtypeBound(from);
/*  805 */     if (fromSubtypeBound == null) {
/*  806 */       return false;
/*      */     }
/*  808 */     return isAssignable(toSubtypeBound, fromSubtypeBound);
/*      */   }
/*      */   
/*      */   private static boolean isAssignableToParameterizedType(Type from, ParameterizedType to) {
/*  812 */     Class<?> matchedClass = getRawType(to);
/*  813 */     if (!matchedClass.isAssignableFrom(getRawType(from))) {
/*  814 */       return false;
/*      */     }
/*  816 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])matchedClass.getTypeParameters();
/*  817 */     Type[] toTypeArgs = to.getActualTypeArguments();
/*  818 */     TypeToken<?> fromTypeToken = of(from);
/*  819 */     for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  827 */       Type fromTypeArg = (fromTypeToken.resolveType(arrayOfTypeVariable[i])).runtimeType;
/*  828 */       if (!matchTypeArgument(fromTypeArg, toTypeArgs[i])) {
/*  829 */         return false;
/*      */       }
/*      */     } 
/*  832 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean isAssignableToGenericArrayType(Type from, GenericArrayType to) {
/*  836 */     if (from instanceof Class) {
/*  837 */       Class<?> fromClass = (Class)from;
/*  838 */       if (!fromClass.isArray()) {
/*  839 */         return false;
/*      */       }
/*  841 */       return isAssignable(fromClass.getComponentType(), to.getGenericComponentType());
/*  842 */     }  if (from instanceof GenericArrayType) {
/*  843 */       GenericArrayType fromArrayType = (GenericArrayType)from;
/*  844 */       return isAssignable(fromArrayType.getGenericComponentType(), to.getGenericComponentType());
/*      */     } 
/*  846 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isAssignableFromGenericArrayType(GenericArrayType from, Type to) {
/*  851 */     if (to instanceof Class) {
/*  852 */       Class<?> toClass = (Class)to;
/*  853 */       if (!toClass.isArray()) {
/*  854 */         return (toClass == Object.class);
/*      */       }
/*  856 */       return isAssignable(from.getGenericComponentType(), toClass.getComponentType());
/*  857 */     }  if (to instanceof GenericArrayType) {
/*  858 */       GenericArrayType toArrayType = (GenericArrayType)to;
/*  859 */       return isAssignable(from.getGenericComponentType(), toArrayType.getGenericComponentType());
/*      */     } 
/*  861 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean matchTypeArgument(Type from, Type to) {
/*  866 */     if (from.equals(to)) {
/*  867 */       return true;
/*      */     }
/*  869 */     if (to instanceof WildcardType) {
/*  870 */       return isAssignableToWildcardType(from, (WildcardType)to);
/*      */     }
/*  872 */     return false;
/*      */   }
/*      */   
/*      */   private static Type supertypeBound(Type type) {
/*  876 */     if (type instanceof WildcardType) {
/*  877 */       return supertypeBound((WildcardType)type);
/*      */     }
/*  879 */     return type;
/*      */   }
/*      */   
/*      */   private static Type supertypeBound(WildcardType type) {
/*  883 */     Type[] upperBounds = type.getUpperBounds();
/*  884 */     if (upperBounds.length == 1)
/*  885 */       return supertypeBound(upperBounds[0]); 
/*  886 */     if (upperBounds.length == 0) {
/*  887 */       return Object.class;
/*      */     }
/*  889 */     throw new AssertionError("There should be at most one upper bound for wildcard type: " + type);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private static Type subtypeBound(Type type) {
/*  895 */     if (type instanceof WildcardType) {
/*  896 */       return subtypeBound((WildcardType)type);
/*      */     }
/*  898 */     return type;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private static Type subtypeBound(WildcardType type) {
/*  903 */     Type[] lowerBounds = type.getLowerBounds();
/*  904 */     if (lowerBounds.length == 1)
/*  905 */       return subtypeBound(lowerBounds[0]); 
/*  906 */     if (lowerBounds.length == 0) {
/*  907 */       return null;
/*      */     }
/*  909 */     throw new AssertionError("Wildcard should have at most one lower bound: " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   static Class<?> getRawType(Type type) {
/*  916 */     return (Class)getRawTypes(type).iterator().next();
/*      */   }
/*      */   @VisibleForTesting
/*      */   static ImmutableSet<Class<?>> getRawTypes(Type type) {
/*  920 */     Preconditions.checkNotNull(type);
/*  921 */     final ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
/*  922 */     (new TypeVisitor() {
/*      */         void visitTypeVariable(TypeVariable<?> t) {
/*  924 */           visit(t.getBounds());
/*      */         }
/*      */         void visitWildcardType(WildcardType t) {
/*  927 */           visit(t.getUpperBounds());
/*      */         }
/*      */         void visitParameterizedType(ParameterizedType t) {
/*  930 */           builder.add(t.getRawType());
/*      */         }
/*      */         void visitClass(Class<?> t) {
/*  933 */           builder.add(t);
/*      */         }
/*      */         void visitGenericArrayType(GenericArrayType t) {
/*  936 */           builder.add(Types.getArrayClass(TypeToken.getRawType(t.getGenericComponentType())));
/*      */         }
/*      */       }).visit(new Type[] { type });
/*      */     
/*  940 */     return builder.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   static <T> TypeToken<? extends T> toGenericType(Class<T> cls) {
/*  951 */     if (cls.isArray()) {
/*  952 */       Type arrayOfGenericType = Types.newArrayType((toGenericType((Class)cls.getComponentType())).runtimeType);
/*      */ 
/*      */ 
/*      */       
/*  956 */       TypeToken<? extends T> result = (TypeToken)of(arrayOfGenericType);
/*  957 */       return result;
/*      */     } 
/*  959 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])cls.getTypeParameters();
/*  960 */     if (arrayOfTypeVariable.length > 0) {
/*      */       
/*  962 */       TypeToken<? extends T> type = (TypeToken)of(Types.newParameterizedType(cls, (Type[])arrayOfTypeVariable));
/*      */       
/*  964 */       return type;
/*      */     } 
/*  966 */     return of(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> supertype, Type[] upperBounds) {
/*  972 */     for (Type upperBound : upperBounds) {
/*      */       
/*  974 */       TypeToken<? super T> bound = (TypeToken)of(upperBound);
/*  975 */       if (of(supertype).isAssignableFrom(bound)) {
/*      */         
/*  977 */         TypeToken<? super T> result = bound.getSupertype(supertype);
/*  978 */         return result;
/*      */       } 
/*      */     } 
/*  981 */     throw new IllegalArgumentException(supertype + " isn't a super type of " + this);
/*      */   }
/*      */   
/*      */   private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> subclass, Type[] lowerBounds) {
/*  985 */     Type[] arr$ = lowerBounds; int len$ = arr$.length, i$ = 0; if (i$ < len$) { Type lowerBound = arr$[i$];
/*      */       
/*  987 */       TypeToken<? extends T> bound = (TypeToken)of(lowerBound);
/*      */       
/*  989 */       return bound.getSubtype(subclass); }
/*      */     
/*  991 */     throw new IllegalArgumentException(subclass + " isn't a subclass of " + this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TypeToken<? super T> getArraySupertype(Class<? super T> supertype) {
/*  998 */     TypeToken<?> componentType = (TypeToken)Preconditions.checkNotNull(getComponentType(), "%s isn't a super type of %s", new Object[] { supertype, this });
/*      */ 
/*      */ 
/*      */     
/* 1002 */     TypeToken<?> componentSupertype = componentType.getSupertype(supertype.getComponentType());
/*      */     
/* 1004 */     TypeToken<? super T> result = (TypeToken)of(newArrayClassOrGenericArrayType(componentSupertype.runtimeType));
/*      */ 
/*      */     
/* 1007 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private TypeToken<? extends T> getArraySubtype(Class<?> subclass) {
/* 1012 */     TypeToken<?> componentSubtype = getComponentType().getSubtype(subclass.getComponentType());
/*      */ 
/*      */     
/* 1015 */     TypeToken<? extends T> result = (TypeToken)of(newArrayClassOrGenericArrayType(componentSubtype.runtimeType));
/*      */ 
/*      */     
/* 1018 */     return result;
/*      */   }
/*      */   
/*      */   private Type resolveTypeArgsForSubclass(Class<?> subclass) {
/* 1022 */     if (this.runtimeType instanceof Class)
/*      */     {
/* 1024 */       return subclass;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1033 */     TypeToken<?> genericSubtype = toGenericType(subclass);
/*      */     
/* 1035 */     Type supertypeWithArgsFromSubtype = (genericSubtype.getSupertype(getRawType())).runtimeType;
/*      */ 
/*      */     
/* 1038 */     return (new TypeResolver()).where(supertypeWithArgsFromSubtype, this.runtimeType).resolveType(genericSubtype.runtimeType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type newArrayClassOrGenericArrayType(Type componentType) {
/* 1048 */     return Types.JavaVersion.JAVA7.newArrayType(componentType);
/*      */   }
/*      */   
/*      */   private static final class SimpleTypeToken<T> extends TypeToken<T> { private static final long serialVersionUID = 0L;
/*      */     
/*      */     SimpleTypeToken(Type type) {
/* 1054 */       super(type);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class TypeCollector<K>
/*      */   {
/*      */     private TypeCollector() {}
/*      */ 
/*      */ 
/*      */     
/* 1067 */     static final TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new TypeCollector<TypeToken<?>>()
/*      */       {
/*      */         Class<?> getRawType(TypeToken<?> type) {
/* 1070 */           return type.getRawType();
/*      */         }
/*      */         
/*      */         Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> type) {
/* 1074 */           return (Iterable<? extends TypeToken<?>>)type.getGenericInterfaces();
/*      */         }
/*      */         
/*      */         @Nullable
/*      */         TypeToken<?> getSuperclass(TypeToken<?> type) {
/* 1079 */           return type.getGenericSuperclass();
/*      */         }
/*      */       };
/*      */     
/* 1083 */     static final TypeCollector<Class<?>> FOR_RAW_TYPE = new TypeCollector<Class<?>>()
/*      */       {
/*      */         Class<?> getRawType(Class<?> type) {
/* 1086 */           return type;
/*      */         }
/*      */         
/*      */         Iterable<? extends Class<?>> getInterfaces(Class<?> type) {
/* 1090 */           return Arrays.asList(type.getInterfaces());
/*      */         }
/*      */         
/*      */         @Nullable
/*      */         Class<?> getSuperclass(Class<?> type) {
/* 1095 */           return type.getSuperclass();
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*      */     final TypeCollector<K> classesOnly() {
/* 1101 */       return new ForwardingTypeCollector<K>(this) {
/*      */           Iterable<? extends K> getInterfaces(K type) {
/* 1103 */             return (Iterable<? extends K>)ImmutableSet.of();
/*      */           }
/*      */           ImmutableList<K> collectTypes(Iterable<? extends K> types) {
/* 1106 */             ImmutableList.Builder<K> builder = ImmutableList.builder();
/* 1107 */             for (K type : types) {
/* 1108 */               if (!getRawType(type).isInterface()) {
/* 1109 */                 builder.add(type);
/*      */               }
/*      */             } 
/* 1112 */             return super.collectTypes((Iterable<? extends K>)builder.build());
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     final ImmutableList<K> collectTypes(K type) {
/* 1118 */       return collectTypes((Iterable<? extends K>)ImmutableList.of(type));
/*      */     }
/*      */ 
/*      */     
/*      */     ImmutableList<K> collectTypes(Iterable<? extends K> types) {
/* 1123 */       Map<K, Integer> map = Maps.newHashMap();
/* 1124 */       for (K type : types) {
/* 1125 */         collectTypes(type, map);
/*      */       }
/* 1127 */       return sortKeysByValue(map, (Comparator<? super Integer>)Ordering.natural().reverse());
/*      */     }
/*      */ 
/*      */     
/*      */     private int collectTypes(K type, Map<? super K, Integer> map) {
/* 1132 */       Integer existing = map.get(this);
/* 1133 */       if (existing != null)
/*      */       {
/* 1135 */         return existing.intValue();
/*      */       }
/* 1137 */       int aboveMe = getRawType(type).isInterface() ? 1 : 0;
/*      */ 
/*      */       
/* 1140 */       for (K interfaceType : getInterfaces(type)) {
/* 1141 */         aboveMe = Math.max(aboveMe, collectTypes(interfaceType, map));
/*      */       }
/* 1143 */       K superclass = getSuperclass(type);
/* 1144 */       if (superclass != null) {
/* 1145 */         aboveMe = Math.max(aboveMe, collectTypes(superclass, map));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1152 */       map.put(type, Integer.valueOf(aboveMe + 1));
/* 1153 */       return aboveMe + 1;
/*      */     }
/*      */ 
/*      */     
/*      */     private static <K, V> ImmutableList<K> sortKeysByValue(final Map<K, V> map, final Comparator<? super V> valueComparator) {
/* 1158 */       Ordering<K> keyOrdering = new Ordering<K>() {
/*      */           public int compare(K left, K right) {
/* 1160 */             return valueComparator.compare(map.get(left), map.get(right));
/*      */           }
/*      */         };
/* 1163 */       return keyOrdering.immutableSortedCopy(map.keySet());
/*      */     }
/*      */     
/*      */     abstract Class<?> getRawType(K param1K);
/*      */     
/*      */     abstract Iterable<? extends K> getInterfaces(K param1K);
/*      */     
/*      */     @Nullable
/*      */     abstract K getSuperclass(K param1K);
/*      */     
/*      */     private static class ForwardingTypeCollector<K> extends TypeCollector<K> {
/*      */       ForwardingTypeCollector(TypeToken.TypeCollector<K> delegate) {
/* 1175 */         this.delegate = delegate;
/*      */       }
/*      */       private final TypeToken.TypeCollector<K> delegate;
/*      */       Class<?> getRawType(K type) {
/* 1179 */         return this.delegate.getRawType(type);
/*      */       }
/*      */       
/*      */       Iterable<? extends K> getInterfaces(K type) {
/* 1183 */         return this.delegate.getInterfaces(type);
/*      */       }
/*      */       
/*      */       K getSuperclass(K type) {
/* 1187 */         return this.delegate.getSuperclass(type);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\reflect\TypeToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */