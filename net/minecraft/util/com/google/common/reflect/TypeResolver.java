/*     */ package net.minecraft.util.com.google.common.reflect;
/*     */ 
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Joiner;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class TypeResolver
/*     */ {
/*     */   private final TypeTable typeTable;
/*     */   
/*     */   public TypeResolver() {
/*  58 */     this.typeTable = new TypeTable();
/*     */   }
/*     */   
/*     */   private TypeResolver(TypeTable typeTable) {
/*  62 */     this.typeTable = typeTable;
/*     */   }
/*     */   
/*     */   static TypeResolver accordingTo(Type type) {
/*  66 */     return (new TypeResolver()).where((Map<? extends TypeVariable<?>, ? extends Type>)TypeMappingIntrospector.getTypeMappings(type));
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
/*     */   public TypeResolver where(Type formal, Type actual) {
/*  89 */     Map<TypeVariable<?>, Type> mappings = Maps.newHashMap();
/*  90 */     populateTypeMappings(mappings, (Type)Preconditions.checkNotNull(formal), (Type)Preconditions.checkNotNull(actual));
/*  91 */     return where(mappings);
/*     */   }
/*     */ 
/*     */   
/*     */   TypeResolver where(Map<? extends TypeVariable<?>, ? extends Type> mappings) {
/*  96 */     return new TypeResolver(this.typeTable.where(mappings));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void populateTypeMappings(final Map<TypeVariable<?>, Type> mappings, Type from, final Type to) {
/* 101 */     if (from.equals(to)) {
/*     */       return;
/*     */     }
/* 104 */     (new TypeVisitor() {
/*     */         void visitTypeVariable(TypeVariable<?> typeVariable) {
/* 106 */           mappings.put(typeVariable, to);
/*     */         }
/*     */         void visitWildcardType(WildcardType fromWildcardType) {
/* 109 */           WildcardType toWildcardType = (WildcardType)TypeResolver.expectArgument((Class)WildcardType.class, to);
/* 110 */           Type[] fromUpperBounds = fromWildcardType.getUpperBounds();
/* 111 */           Type[] toUpperBounds = toWildcardType.getUpperBounds();
/* 112 */           Type[] fromLowerBounds = fromWildcardType.getLowerBounds();
/* 113 */           Type[] toLowerBounds = toWildcardType.getLowerBounds();
/* 114 */           Preconditions.checkArgument((fromUpperBounds.length == toUpperBounds.length && fromLowerBounds.length == toLowerBounds.length), "Incompatible type: %s vs. %s", new Object[] { fromWildcardType, this.val$to });
/*     */           
/*     */           int i;
/*     */           
/* 118 */           for (i = 0; i < fromUpperBounds.length; i++) {
/* 119 */             TypeResolver.populateTypeMappings(mappings, fromUpperBounds[i], toUpperBounds[i]);
/*     */           }
/* 121 */           for (i = 0; i < fromLowerBounds.length; i++)
/* 122 */             TypeResolver.populateTypeMappings(mappings, fromLowerBounds[i], toLowerBounds[i]); 
/*     */         }
/*     */         
/*     */         void visitParameterizedType(ParameterizedType fromParameterizedType) {
/* 126 */           ParameterizedType toParameterizedType = (ParameterizedType)TypeResolver.expectArgument((Class)ParameterizedType.class, to);
/* 127 */           Preconditions.checkArgument(fromParameterizedType.getRawType().equals(toParameterizedType.getRawType()), "Inconsistent raw type: %s vs. %s", new Object[] { fromParameterizedType, this.val$to });
/*     */           
/* 129 */           Type[] fromArgs = fromParameterizedType.getActualTypeArguments();
/* 130 */           Type[] toArgs = toParameterizedType.getActualTypeArguments();
/* 131 */           Preconditions.checkArgument((fromArgs.length == toArgs.length), "%s not compatible with %s", new Object[] { fromParameterizedType, toParameterizedType });
/*     */           
/* 133 */           for (int i = 0; i < fromArgs.length; i++)
/* 134 */             TypeResolver.populateTypeMappings(mappings, fromArgs[i], toArgs[i]); 
/*     */         }
/*     */         
/*     */         void visitGenericArrayType(GenericArrayType fromArrayType) {
/* 138 */           Type componentType = Types.getComponentType(to);
/* 139 */           Preconditions.checkArgument((componentType != null), "%s is not an array type.", new Object[] { this.val$to });
/* 140 */           TypeResolver.populateTypeMappings(mappings, fromArrayType.getGenericComponentType(), componentType);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         void visitClass(Class<?> fromClass) {
/* 146 */           throw new IllegalArgumentException("No type mapping from " + fromClass);
/*     */         }
/*     */       }).visit(new Type[] { from });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type resolveType(Type type) {
/* 156 */     Preconditions.checkNotNull(type);
/* 157 */     if (type instanceof TypeVariable)
/* 158 */       return this.typeTable.resolve((TypeVariable)type); 
/* 159 */     if (type instanceof ParameterizedType)
/* 160 */       return resolveParameterizedType((ParameterizedType)type); 
/* 161 */     if (type instanceof GenericArrayType)
/* 162 */       return resolveGenericArrayType((GenericArrayType)type); 
/* 163 */     if (type instanceof WildcardType) {
/* 164 */       WildcardType wildcardType = (WildcardType)type;
/* 165 */       return new Types.WildcardTypeImpl(resolveTypes(wildcardType.getLowerBounds()), resolveTypes(wildcardType.getUpperBounds()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 170 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   private Type[] resolveTypes(Type[] types) {
/* 175 */     Type[] result = new Type[types.length];
/* 176 */     for (int i = 0; i < types.length; i++) {
/* 177 */       result[i] = resolveType(types[i]);
/*     */     }
/* 179 */     return result;
/*     */   }
/*     */   
/*     */   private Type resolveGenericArrayType(GenericArrayType type) {
/* 183 */     Type componentType = resolveType(type.getGenericComponentType());
/* 184 */     return Types.newArrayType(componentType);
/*     */   }
/*     */   
/*     */   private ParameterizedType resolveParameterizedType(ParameterizedType type) {
/* 188 */     Type owner = type.getOwnerType();
/* 189 */     Type resolvedOwner = (owner == null) ? null : resolveType(owner);
/* 190 */     Type resolvedRawType = resolveType(type.getRawType());
/*     */     
/* 192 */     Type[] vars = type.getActualTypeArguments();
/* 193 */     Type[] resolvedArgs = new Type[vars.length];
/* 194 */     for (int i = 0; i < vars.length; i++) {
/* 195 */       resolvedArgs[i] = resolveType(vars[i]);
/*     */     }
/* 197 */     return Types.newParameterizedTypeWithOwner(resolvedOwner, (Class)resolvedRawType, resolvedArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T expectArgument(Class<T> type, Object arg) {
/*     */     try {
/* 203 */       return type.cast(arg);
/* 204 */     } catch (ClassCastException e) {
/* 205 */       throw new IllegalArgumentException(arg + " is not a " + type.getSimpleName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class TypeTable
/*     */   {
/*     */     private final ImmutableMap<TypeVariable<?>, Type> map;
/*     */     
/*     */     TypeTable() {
/* 214 */       this.map = ImmutableMap.of();
/*     */     }
/*     */     
/*     */     private TypeTable(ImmutableMap<TypeVariable<?>, Type> map) {
/* 218 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     final TypeTable where(Map<? extends TypeVariable<?>, ? extends Type> mappings) {
/* 223 */       ImmutableMap.Builder<TypeVariable<?>, Type> builder = ImmutableMap.builder();
/* 224 */       builder.putAll((Map)this.map);
/* 225 */       for (Map.Entry<? extends TypeVariable<?>, ? extends Type> mapping : mappings.entrySet()) {
/* 226 */         TypeVariable<?> variable = mapping.getKey();
/* 227 */         Type type = mapping.getValue();
/* 228 */         Preconditions.checkArgument(!variable.equals(type), "Type variable %s bound to itself", new Object[] { variable });
/* 229 */         builder.put(variable, type);
/*     */       } 
/* 231 */       return new TypeTable(builder.build());
/*     */     }
/*     */     
/*     */     final Type resolve(final TypeVariable<?> var) {
/* 235 */       final TypeTable unguarded = this;
/* 236 */       TypeTable guarded = new TypeTable()
/*     */         {
/*     */           public Type resolveInternal(TypeVariable<?> intermediateVar, TypeResolver.TypeTable forDependent) {
/* 239 */             if (intermediateVar.getGenericDeclaration().equals(var.getGenericDeclaration())) {
/* 240 */               return intermediateVar;
/*     */             }
/* 242 */             return unguarded.resolveInternal(intermediateVar, forDependent);
/*     */           }
/*     */         };
/* 245 */       return resolveInternal(var, guarded);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Type resolveInternal(TypeVariable<?> var, TypeTable forDependants) {
/* 257 */       Type type = (Type)this.map.get(var);
/* 258 */       if (type == null) {
/* 259 */         Type[] bounds = var.getBounds();
/* 260 */         if (bounds.length == 0) {
/* 261 */           return var;
/*     */         }
/* 263 */         return Types.newTypeVariable((GenericDeclaration)var.getGenericDeclaration(), var.getName(), (new TypeResolver(forDependants)).resolveTypes(bounds));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       return (new TypeResolver(forDependants)).resolveType(type);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TypeMappingIntrospector
/*     */     extends TypeVisitor {
/* 275 */     private static final TypeResolver.WildcardCapturer wildcardCapturer = new TypeResolver.WildcardCapturer();
/*     */     
/* 277 */     private final Map<TypeVariable<?>, Type> mappings = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static ImmutableMap<TypeVariable<?>, Type> getTypeMappings(Type contextType) {
/* 285 */       TypeMappingIntrospector introspector = new TypeMappingIntrospector();
/* 286 */       introspector.visit(new Type[] { wildcardCapturer.capture(contextType) });
/* 287 */       return ImmutableMap.copyOf(introspector.mappings);
/*     */     }
/*     */     
/*     */     void visitClass(Class<?> clazz) {
/* 291 */       visit(new Type[] { clazz.getGenericSuperclass() });
/* 292 */       visit(clazz.getGenericInterfaces());
/*     */     }
/*     */     
/*     */     void visitParameterizedType(ParameterizedType parameterizedType) {
/* 296 */       Class<?> rawClass = (Class)parameterizedType.getRawType();
/* 297 */       TypeVariable[] arrayOfTypeVariable = (TypeVariable[])rawClass.getTypeParameters();
/* 298 */       Type[] typeArgs = parameterizedType.getActualTypeArguments();
/* 299 */       Preconditions.checkState((arrayOfTypeVariable.length == typeArgs.length));
/* 300 */       for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/* 301 */         map(arrayOfTypeVariable[i], typeArgs[i]);
/*     */       }
/* 303 */       visit(new Type[] { rawClass });
/* 304 */       visit(new Type[] { parameterizedType.getOwnerType() });
/*     */     }
/*     */     
/*     */     void visitTypeVariable(TypeVariable<?> t) {
/* 308 */       visit(t.getBounds());
/*     */     }
/*     */     
/*     */     void visitWildcardType(WildcardType t) {
/* 312 */       visit(t.getUpperBounds());
/*     */     }
/*     */     
/*     */     private void map(TypeVariable<?> var, Type arg) {
/* 316 */       if (this.mappings.containsKey(var)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 325 */       for (Type t = arg; t != null; t = this.mappings.get(t)) {
/* 326 */         if (var.equals(t)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 331 */           for (Type x = arg; x != null; x = this.mappings.remove(x));
/*     */           return;
/*     */         } 
/*     */       } 
/* 335 */       this.mappings.put(var, arg);
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
/*     */   private static final class WildcardCapturer
/*     */   {
/* 348 */     private final AtomicInteger id = new AtomicInteger();
/*     */     
/*     */     Type capture(Type type) {
/* 351 */       Preconditions.checkNotNull(type);
/* 352 */       if (type instanceof Class) {
/* 353 */         return type;
/*     */       }
/* 355 */       if (type instanceof TypeVariable) {
/* 356 */         return type;
/*     */       }
/* 358 */       if (type instanceof GenericArrayType) {
/* 359 */         GenericArrayType arrayType = (GenericArrayType)type;
/* 360 */         return Types.newArrayType(capture(arrayType.getGenericComponentType()));
/*     */       } 
/* 362 */       if (type instanceof ParameterizedType) {
/* 363 */         ParameterizedType parameterizedType = (ParameterizedType)type;
/* 364 */         return Types.newParameterizedTypeWithOwner(captureNullable(parameterizedType.getOwnerType()), (Class)parameterizedType.getRawType(), capture(parameterizedType.getActualTypeArguments()));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 369 */       if (type instanceof WildcardType) {
/* 370 */         WildcardType wildcardType = (WildcardType)type;
/* 371 */         Type[] lowerBounds = wildcardType.getLowerBounds();
/* 372 */         if (lowerBounds.length == 0) {
/* 373 */           Type[] upperBounds = wildcardType.getUpperBounds();
/* 374 */           String name = "capture#" + this.id.incrementAndGet() + "-of ? extends " + Joiner.on('&').join((Object[])upperBounds);
/*     */           
/* 376 */           return Types.newTypeVariable(WildcardCapturer.class, name, wildcardType.getUpperBounds());
/*     */         } 
/*     */ 
/*     */         
/* 380 */         return type;
/*     */       } 
/*     */       
/* 383 */       throw new AssertionError("must have been one of the known types");
/*     */     }
/*     */     
/*     */     private Type captureNullable(@Nullable Type type) {
/* 387 */       if (type == null) {
/* 388 */         return null;
/*     */       }
/* 390 */       return capture(type);
/*     */     }
/*     */     
/*     */     private Type[] capture(Type[] types) {
/* 394 */       Type[] result = new Type[types.length];
/* 395 */       for (int i = 0; i < types.length; i++) {
/* 396 */         result[i] = capture(types[i]);
/*     */       }
/* 398 */       return result;
/*     */     }
/*     */     
/*     */     private WildcardCapturer() {}
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\reflect\TypeResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */