/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.deploy.DetermineManyType;
/*     */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundType;
/*     */ import com.avaje.ebeaninternal.server.type.ScalaOptionTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.persistence.Transient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployCreateProperties
/*     */ {
/*  59 */   private static final Logger logger = Logger.getLogger(DeployCreateProperties.class.getName());
/*     */ 
/*     */   
/*     */   private final Class<?> scalaOptionClass;
/*     */ 
/*     */   
/*     */   private final ScalarTypeConverter scalaOptionTypeConverter;
/*     */ 
/*     */   
/*     */   private final DetermineManyType determineManyType;
/*     */   
/*     */   private final TypeManager typeManager;
/*     */ 
/*     */   
/*     */   public DeployCreateProperties(TypeManager typeManager) {
/*  74 */     this.typeManager = typeManager;
/*     */     
/*  76 */     Class<?> tmpOptionClass = DetectScala.getScalaOptionClass();
/*     */     
/*  78 */     if (tmpOptionClass == null) {
/*  79 */       this.scalaOptionClass = null;
/*  80 */       this.scalaOptionTypeConverter = null;
/*     */     } else {
/*  82 */       this.scalaOptionClass = tmpOptionClass;
/*  83 */       this.scalaOptionTypeConverter = (ScalarTypeConverter)new ScalaOptionTypeConverter();
/*     */     } 
/*     */     
/*  86 */     this.determineManyType = new DetermineManyType((tmpOptionClass != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createProperties(DeployBeanDescriptor<?> desc) {
/*  94 */     createProperties(desc, desc.getBeanType(), 0);
/*  95 */     desc.sortProperties();
/*     */ 
/*     */     
/*  98 */     Iterator<DeployBeanProperty> it = desc.propertiesAll();
/*     */     
/* 100 */     while (it.hasNext()) {
/* 101 */       DeployBeanProperty prop = it.next();
/* 102 */       if (prop.isTransient()) {
/* 103 */         if (prop.getWriteMethod() == null || prop.getReadMethod() == null) {
/*     */           
/* 105 */           logger.finest("... transient: " + prop.getFullBeanName());
/*     */           continue;
/*     */         } 
/* 108 */         String msg = Message.msg("deploy.property.nofield", desc.getFullName(), prop.getName());
/* 109 */         logger.warning(msg);
/*     */       } 
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
/*     */   private boolean ignoreFieldByName(String fieldName) {
/* 122 */     if (fieldName.startsWith("_ebean_"))
/*     */     {
/* 124 */       return true;
/*     */     }
/* 126 */     if (fieldName.startsWith("ajc$instance$"))
/*     */     {
/* 128 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createProperties(DeployBeanDescriptor<?> desc, Class<?> beanType, int level) {
/* 141 */     boolean scalaObject = desc.isScalaObject();
/*     */     
/*     */     try {
/* 144 */       Method[] declaredMethods = beanType.getDeclaredMethods();
/* 145 */       Field[] fields = beanType.getDeclaredFields();
/*     */       
/* 147 */       for (int i = 0; i < fields.length; i++) {
/*     */         
/* 149 */         Field field = fields[i];
/* 150 */         if (!Modifier.isStatic(field.getModifiers()))
/*     */         {
/*     */           
/* 153 */           if (Modifier.isTransient(field.getModifiers())) {
/*     */             
/* 155 */             logger.finer("Skipping transient field " + field.getName() + " in " + beanType.getName());
/*     */           }
/* 157 */           else if (!ignoreFieldByName(field.getName())) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 162 */             String fieldName = getFieldName(field, beanType);
/* 163 */             String initFieldName = initCap(fieldName);
/*     */             
/* 165 */             Method getter = findGetter(field, initFieldName, declaredMethods, scalaObject);
/* 166 */             Method setter = findSetter(field, initFieldName, declaredMethods, scalaObject);
/*     */             
/* 168 */             DeployBeanProperty prop = createProp(level, desc, field, beanType, getter, setter);
/* 169 */             if (prop != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 175 */               int sortOverride = prop.getSortOverride();
/* 176 */               prop.setSortOrder(level * 10000 + 100 - i + sortOverride);
/*     */               
/* 178 */               DeployBeanProperty replaced = desc.addBeanProperty(prop);
/* 179 */               if (replaced != null && 
/* 180 */                 !replaced.isTransient()) {
/*     */ 
/*     */                 
/* 183 */                 String msg = "Huh??? property " + prop.getFullBeanName() + " being defined twice";
/* 184 */                 msg = msg + " but replaced property was not transient? This is not expected?";
/* 185 */                 logger.warning(msg);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 192 */       Class<?> superClass = beanType.getSuperclass();
/*     */       
/* 194 */       if (!superClass.equals(Object.class))
/*     */       {
/*     */         
/* 197 */         createProperties(desc, superClass, level + 1);
/*     */       }
/*     */     }
/* 200 */     catch (PersistenceException ex) {
/* 201 */       throw ex;
/*     */     }
/* 203 */     catch (Exception ex) {
/* 204 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String initCap(String str) {
/* 212 */     if (str.length() > 1) {
/* 213 */       return Character.toUpperCase(str.charAt(0)) + str.substring(1);
/*     */     }
/*     */     
/* 216 */     return str.toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFieldName(Field field, Class<?> beanType) {
/* 225 */     String name = field.getName();
/*     */     
/* 227 */     if ((Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) && name.startsWith("is") && name.length() > 2) {
/*     */ 
/*     */ 
/*     */       
/* 231 */       char c = name.charAt(2);
/* 232 */       if (Character.isUpperCase(c)) {
/* 233 */         String msg = "trimming off 'is' from boolean field name " + name + " in class " + beanType.getName();
/* 234 */         logger.log(Level.INFO, msg);
/*     */         
/* 236 */         return name.substring(2);
/*     */       } 
/*     */     } 
/* 239 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method findGetter(Field field, String initFieldName, Method[] declaredMethods, boolean scalaObject) {
/* 247 */     String methGetName = "get" + initFieldName;
/* 248 */     String methIsName = "is" + initFieldName;
/* 249 */     String scalaGet = field.getName();
/*     */     
/* 251 */     for (int i = 0; i < declaredMethods.length; i++) {
/* 252 */       Method m = declaredMethods[i];
/* 253 */       if ((scalaObject && m.getName().equals(scalaGet)) || m.getName().equals(methGetName) || m.getName().equals(methIsName)) {
/*     */ 
/*     */         
/* 256 */         Class<?>[] params = m.getParameterTypes();
/* 257 */         if (params.length == 0 && 
/* 258 */           field.getType().equals(m.getReturnType())) {
/* 259 */           int modifiers = m.getModifiers();
/* 260 */           if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers))
/*     */           {
/* 262 */             return m;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method findSetter(Field field, String initFieldName, Method[] declaredMethods, boolean scalaObject) {
/* 276 */     String methSetName = "set" + initFieldName;
/* 277 */     String scalaSetName = field.getName() + "_$eq";
/*     */     
/* 279 */     for (int i = 0; i < declaredMethods.length; i++) {
/* 280 */       Method m = declaredMethods[i];
/*     */       
/* 282 */       if ((scalaObject && m.getName().equals(scalaSetName)) || m.getName().equals(methSetName)) {
/*     */ 
/*     */         
/* 285 */         Class<?>[] params = m.getParameterTypes();
/* 286 */         if (params.length == 1 && field.getType().equals(params[0]) && 
/* 287 */           void.class.equals(m.getReturnType())) {
/* 288 */           int modifiers = m.getModifiers();
/* 289 */           if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
/* 290 */             return m;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DeployBeanProperty createManyType(DeployBeanDescriptor<?> desc, Class<?> targetType, ManyType manyType) {
/* 302 */     ScalarType<?> scalarType = this.typeManager.getScalarType(targetType);
/* 303 */     if (scalarType != null) {
/* 304 */       return (DeployBeanProperty)new DeployBeanPropertySimpleCollection(desc, targetType, scalarType, manyType);
/*     */     }
/*     */     
/* 307 */     return (DeployBeanProperty)new DeployBeanPropertyAssocMany(desc, targetType, manyType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DeployBeanProperty createProp(DeployBeanDescriptor<?> desc, Field field) {
/* 313 */     Class<?> propertyType = field.getType();
/* 314 */     Class<?> innerType = propertyType;
/* 315 */     ScalarTypeConverter<?, ?> typeConverter = null;
/*     */     
/* 317 */     if (propertyType.equals(this.scalaOptionClass)) {
/* 318 */       innerType = determineTargetType(field);
/* 319 */       typeConverter = this.scalaOptionTypeConverter;
/*     */     } 
/*     */ 
/*     */     
/* 323 */     ManyType manyType = this.determineManyType.getManyType(propertyType);
/*     */     
/* 325 */     if (manyType != null) {
/*     */       
/* 327 */       Class<?> targetType = determineTargetType(field);
/* 328 */       if (targetType == null) {
/* 329 */         Transient transAnnotation = field.<Transient>getAnnotation(Transient.class);
/* 330 */         if (transAnnotation != null)
/*     */         {
/* 332 */           return null;
/*     */         }
/* 334 */         logger.warning("Could not find parameter type (via reflection) on " + desc.getFullName() + " " + field.getName());
/*     */       } 
/* 336 */       return createManyType(desc, targetType, manyType);
/*     */     } 
/*     */     
/* 339 */     if (innerType.isEnum() || innerType.isPrimitive()) {
/* 340 */       return new DeployBeanProperty(desc, propertyType, null, typeConverter);
/*     */     }
/*     */     
/* 343 */     ScalarType<?> scalarType = this.typeManager.getScalarType(innerType);
/* 344 */     if (scalarType != null) {
/* 345 */       return new DeployBeanProperty(desc, propertyType, scalarType, typeConverter);
/*     */     }
/*     */     
/* 348 */     CtCompoundType<?> compoundType = this.typeManager.getCompoundType(innerType);
/* 349 */     if (compoundType != null) {
/* 350 */       return (DeployBeanProperty)new DeployBeanPropertyCompound(desc, propertyType, compoundType, typeConverter);
/*     */     }
/*     */     
/* 353 */     if (!isTransientField(field)) {
/*     */       try {
/* 355 */         CheckImmutableResponse checkImmutable = this.typeManager.checkImmutable(innerType);
/* 356 */         if (checkImmutable.isImmutable()) {
/* 357 */           if (checkImmutable.isCompoundType()) {
/*     */             
/* 359 */             this.typeManager.recursiveCreateScalarDataReader(innerType);
/* 360 */             compoundType = this.typeManager.getCompoundType(innerType);
/* 361 */             if (compoundType != null) {
/* 362 */               return (DeployBeanProperty)new DeployBeanPropertyCompound(desc, propertyType, compoundType, typeConverter);
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 367 */             scalarType = this.typeManager.recursiveCreateScalarTypes(innerType);
/* 368 */             return new DeployBeanProperty(desc, propertyType, scalarType, typeConverter);
/*     */           } 
/*     */         }
/* 371 */       } catch (Exception e) {
/* 372 */         logger.log(Level.SEVERE, "Error with " + desc + " field:" + field.getName(), e);
/*     */       } 
/*     */     }
/*     */     
/* 376 */     return (DeployBeanProperty)new DeployBeanPropertyAssocOne(desc, propertyType);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTransientField(Field field) {
/* 381 */     Transient t = field.<Transient>getAnnotation(Transient.class);
/* 382 */     return (t != null);
/*     */   }
/*     */ 
/*     */   
/*     */   private DeployBeanProperty createProp(int level, DeployBeanDescriptor<?> desc, Field field, Class<?> beanType, Method getter, Method setter) {
/* 387 */     DeployBeanProperty prop = createProp(desc, field);
/* 388 */     if (prop == null)
/*     */     {
/* 390 */       return null;
/*     */     }
/* 392 */     prop.setOwningType(beanType);
/* 393 */     prop.setName(field.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     prop.setReadMethod(getter);
/* 399 */     prop.setWriteMethod(setter);
/* 400 */     prop.setField(field);
/* 401 */     return prop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> determineTargetType(Field field) {
/* 411 */     Type genType = field.getGenericType();
/* 412 */     if (genType instanceof ParameterizedType) {
/* 413 */       ParameterizedType ptype = (ParameterizedType)genType;
/*     */       
/* 415 */       Type[] typeArgs = ptype.getActualTypeArguments();
/* 416 */       if (typeArgs.length == 1) {
/*     */         
/* 418 */         if (typeArgs[0] instanceof Class) {
/* 419 */           return (Class)typeArgs[0];
/*     */         }
/* 421 */         throw new RuntimeException("Unexpected Parameterised Type? " + typeArgs[0]);
/*     */       } 
/* 423 */       if (typeArgs.length == 2) {
/*     */         
/* 425 */         if (typeArgs[1] instanceof ParameterizedType)
/*     */         {
/* 427 */           return null;
/*     */         }
/* 429 */         return (Class)typeArgs[1];
/*     */       } 
/*     */     } 
/*     */     
/* 433 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployCreateProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */