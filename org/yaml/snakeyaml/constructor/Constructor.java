/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import org.yaml.snakeyaml.TypeDescription;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.Property;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Constructor
/*     */   extends SafeConstructor
/*     */ {
/*     */   private final Map<Tag, Class<? extends Object>> typeTags;
/*     */   protected final Map<Class<? extends Object>, TypeDescription> typeDefinitions;
/*     */   
/*     */   public Constructor() {
/*  54 */     this(Object.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Constructor(Class<? extends Object> theRoot) {
/*  64 */     this(new TypeDescription(checkRoot(theRoot)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<? extends Object> checkRoot(Class<? extends Object> theRoot) {
/*  71 */     if (theRoot == null) {
/*  72 */       throw new NullPointerException("Root class must be provided.");
/*     */     }
/*  74 */     return theRoot;
/*     */   }
/*     */   
/*     */   public Constructor(TypeDescription theRoot) {
/*  78 */     if (theRoot == null) {
/*  79 */       throw new NullPointerException("Root type must be provided.");
/*     */     }
/*  81 */     this.yamlConstructors.put(null, new ConstructYamlObject());
/*  82 */     if (!Object.class.equals(theRoot.getType())) {
/*  83 */       this.rootTag = new Tag(theRoot.getType());
/*     */     }
/*  85 */     this.typeTags = new HashMap<Tag, Class<? extends Object>>();
/*  86 */     this.typeDefinitions = new HashMap<Class<? extends Object>, TypeDescription>();
/*  87 */     this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalar());
/*  88 */     this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping());
/*  89 */     this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence());
/*  90 */     addTypeDescription(theRoot);
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
/*     */   public Constructor(String theRoot) throws ClassNotFoundException {
/* 103 */     this((Class)Class.forName(check(theRoot)));
/*     */   }
/*     */   
/*     */   private static final String check(String s) {
/* 107 */     if (s == null) {
/* 108 */       throw new NullPointerException("Root type must be provided.");
/*     */     }
/* 110 */     if (s.trim().length() == 0) {
/* 111 */       throw new YAMLException("Root type must be provided.");
/*     */     }
/* 113 */     return s;
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
/*     */   public TypeDescription addTypeDescription(TypeDescription definition) {
/* 127 */     if (definition == null) {
/* 128 */       throw new NullPointerException("TypeDescription is required.");
/*     */     }
/* 130 */     Tag tag = definition.getTag();
/* 131 */     this.typeTags.put(tag, definition.getType());
/* 132 */     return this.typeDefinitions.put(definition.getType(), definition);
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
/*     */   protected class ConstructMapping
/*     */     implements Construct
/*     */   {
/*     */     public Object construct(Node node) {
/* 151 */       MappingNode mnode = (MappingNode)node;
/* 152 */       if (Properties.class.isAssignableFrom(node.getType())) {
/* 153 */         Properties properties = new Properties();
/* 154 */         if (!node.isTwoStepsConstruction()) {
/* 155 */           Constructor.this.constructMapping2ndStep(mnode, properties);
/*     */         } else {
/* 157 */           throw new YAMLException("Properties must not be recursive.");
/*     */         } 
/* 159 */         return properties;
/* 160 */       }  if (SortedMap.class.isAssignableFrom(node.getType())) {
/* 161 */         SortedMap<Object, Object> map = new TreeMap<Object, Object>();
/* 162 */         if (!node.isTwoStepsConstruction()) {
/* 163 */           Constructor.this.constructMapping2ndStep(mnode, map);
/*     */         }
/* 165 */         return map;
/* 166 */       }  if (Map.class.isAssignableFrom(node.getType())) {
/* 167 */         if (node.isTwoStepsConstruction()) {
/* 168 */           return Constructor.this.createDefaultMap();
/*     */         }
/* 170 */         return Constructor.this.constructMapping(mnode);
/*     */       } 
/* 172 */       if (SortedSet.class.isAssignableFrom(node.getType())) {
/* 173 */         SortedSet<Object> set = new TreeSet();
/*     */ 
/*     */         
/* 176 */         Constructor.this.constructSet2ndStep(mnode, set);
/*     */         
/* 178 */         return set;
/* 179 */       }  if (Collection.class.isAssignableFrom(node.getType())) {
/* 180 */         if (node.isTwoStepsConstruction()) {
/* 181 */           return Constructor.this.createDefaultSet();
/*     */         }
/* 183 */         return Constructor.this.constructSet(mnode);
/*     */       } 
/*     */       
/* 186 */       if (node.isTwoStepsConstruction()) {
/* 187 */         return createEmptyJavaBean(mnode);
/*     */       }
/* 189 */       return constructJavaBean2ndStep(mnode, createEmptyJavaBean(mnode));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 196 */       if (Map.class.isAssignableFrom(node.getType())) {
/* 197 */         Constructor.this.constructMapping2ndStep((MappingNode)node, (Map<Object, Object>)object);
/* 198 */       } else if (Set.class.isAssignableFrom(node.getType())) {
/* 199 */         Constructor.this.constructSet2ndStep((MappingNode)node, (Set<Object>)object);
/*     */       } else {
/* 201 */         constructJavaBean2ndStep((MappingNode)node, object);
/*     */       } 
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
/*     */     protected Object createEmptyJavaBean(MappingNode node) {
/*     */       try {
/* 215 */         Constructor<?> c = node.getType().getDeclaredConstructor(new Class[0]);
/* 216 */         c.setAccessible(true);
/* 217 */         return c.newInstance(new Object[0]);
/* 218 */       } catch (Exception e) {
/* 219 */         throw new YAMLException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
/* 224 */       Constructor.this.flattenMapping(node);
/* 225 */       Class<? extends Object> beanType = node.getType();
/* 226 */       List<NodeTuple> nodeValue = node.getValue();
/* 227 */       for (NodeTuple tuple : nodeValue) {
/*     */         ScalarNode keyNode;
/* 229 */         if (tuple.getKeyNode() instanceof ScalarNode) {
/*     */           
/* 231 */           keyNode = (ScalarNode)tuple.getKeyNode();
/*     */         } else {
/* 233 */           throw new YAMLException("Keys must be scalars but found: " + tuple.getKeyNode());
/*     */         } 
/* 235 */         Node valueNode = tuple.getValueNode();
/*     */         
/* 237 */         keyNode.setType(String.class);
/* 238 */         String key = (String)Constructor.this.constructObject((Node)keyNode);
/*     */         try {
/* 240 */           Property property = getProperty(beanType, key);
/* 241 */           valueNode.setType(property.getType());
/* 242 */           TypeDescription memberDescription = Constructor.this.typeDefinitions.get(beanType);
/* 243 */           boolean typeDetected = false;
/* 244 */           if (memberDescription != null) {
/* 245 */             SequenceNode snode; Class<? extends Object> memberType; MappingNode mnode; Class<? extends Object> keyType; switch (valueNode.getNodeId()) {
/*     */               case sequence:
/* 247 */                 snode = (SequenceNode)valueNode;
/* 248 */                 memberType = memberDescription.getListPropertyType(key);
/*     */                 
/* 250 */                 if (memberType != null) {
/* 251 */                   snode.setListType(memberType);
/* 252 */                   typeDetected = true; break;
/* 253 */                 }  if (property.getType().isArray()) {
/* 254 */                   snode.setListType(property.getType().getComponentType());
/* 255 */                   typeDetected = true;
/*     */                 } 
/*     */                 break;
/*     */               case mapping:
/* 259 */                 mnode = (MappingNode)valueNode;
/* 260 */                 keyType = memberDescription.getMapKeyType(key);
/* 261 */                 if (keyType != null) {
/* 262 */                   mnode.setTypes(keyType, memberDescription.getMapValueType(key));
/* 263 */                   typeDetected = true;
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           
/*     */           } 
/* 269 */           if (!typeDetected && valueNode.getNodeId() != NodeId.scalar) {
/*     */             
/* 271 */             Class<?>[] arguments = property.getActualTypeArguments();
/* 272 */             if (arguments != null && arguments.length > 0)
/*     */             {
/*     */               
/* 275 */               if (valueNode.getNodeId() == NodeId.sequence) {
/* 276 */                 Class<?> t = arguments[0];
/* 277 */                 SequenceNode snode = (SequenceNode)valueNode;
/* 278 */                 snode.setListType(t);
/* 279 */               } else if (valueNode.getTag().equals(Tag.SET)) {
/* 280 */                 Class<?> t = arguments[0];
/* 281 */                 MappingNode mnode = (MappingNode)valueNode;
/* 282 */                 mnode.setOnlyKeyType(t);
/* 283 */                 mnode.setUseClassConstructor(Boolean.valueOf(true));
/* 284 */               } else if (property.getType().isAssignableFrom(Map.class)) {
/* 285 */                 Class<?> ketType = arguments[0];
/* 286 */                 Class<?> valueType = arguments[1];
/* 287 */                 MappingNode mnode = (MappingNode)valueNode;
/* 288 */                 mnode.setTypes(ketType, valueType);
/* 289 */                 mnode.setUseClassConstructor(Boolean.valueOf(true));
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 296 */           Object value = Constructor.this.constructObject(valueNode);
/* 297 */           property.set(object, value);
/* 298 */         } catch (Exception e) {
/* 299 */           throw new YAMLException("Cannot create property=" + key + " for JavaBean=" + object + "; " + e.getMessage(), e);
/*     */         } 
/*     */       } 
/*     */       
/* 303 */       return object;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Property getProperty(Class<? extends Object> type, String name) throws IntrospectionException {
/* 308 */       return Constructor.this.getPropertyUtils().getProperty(type, name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class ConstructYamlObject
/*     */     implements Construct
/*     */   {
/*     */     private Construct getConstructor(Node node) {
/* 321 */       Class<?> cl = Constructor.this.getClassForNode(node);
/* 322 */       node.setType(cl);
/*     */       
/* 324 */       Construct constructor = Constructor.this.yamlClassConstructors.get(node.getNodeId());
/* 325 */       return constructor;
/*     */     }
/*     */     
/*     */     public Object construct(Node node) {
/* 329 */       Object result = null;
/*     */       try {
/* 331 */         result = getConstructor(node).construct(node);
/* 332 */       } catch (Exception e) {
/* 333 */         throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */       } 
/*     */       
/* 336 */       return result;
/*     */     }
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/*     */       try {
/* 341 */         getConstructor(node).construct2ndStep(node, object);
/* 342 */       } catch (Exception e) {
/* 343 */         throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class ConstructScalar
/*     */     extends AbstractConstruct
/*     */   {
/*     */     public Object construct(Node nnode) {
/*     */       Object result;
/* 356 */       ScalarNode node = (ScalarNode)nnode;
/* 357 */       Class<?> type = node.getType();
/*     */       
/* 359 */       if (type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type) || type == Boolean.class || Date.class.isAssignableFrom(type) || type == Character.class || type == BigInteger.class || type == BigDecimal.class || Enum.class.isAssignableFrom(type) || Tag.BINARY.equals(node.getTag()) || Calendar.class.isAssignableFrom(type)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 365 */         result = constructStandardJavaInstance(type, node);
/*     */       } else {
/*     */         Object argument;
/* 368 */         Constructor[] arrayOfConstructor = (Constructor[])type.getConstructors();
/* 369 */         int oneArgCount = 0;
/* 370 */         Constructor<?> javaConstructor = null;
/* 371 */         for (Constructor<?> c : arrayOfConstructor) {
/* 372 */           if ((c.getParameterTypes()).length == 1) {
/* 373 */             oneArgCount++;
/* 374 */             javaConstructor = c;
/*     */           } 
/*     */         } 
/*     */         
/* 378 */         if (javaConstructor == null)
/* 379 */           throw new YAMLException("No single argument constructor found for " + type); 
/* 380 */         if (oneArgCount == 1) {
/* 381 */           argument = constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 390 */           argument = Constructor.this.constructScalar(node);
/*     */           try {
/* 392 */             javaConstructor = type.getConstructor(new Class[] { String.class });
/* 393 */           } catch (Exception e) {
/* 394 */             throw new YAMLException("Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), e);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 400 */           result = javaConstructor.newInstance(new Object[] { argument });
/* 401 */         } catch (Exception e) {
/* 402 */           throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 407 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object constructStandardJavaInstance(Class<String> type, ScalarNode node) {
/*     */       Object result;
/* 414 */       if (type == String.class) {
/* 415 */         Construct stringConstructor = Constructor.this.yamlConstructors.get(Tag.STR);
/* 416 */         result = stringConstructor.construct((Node)node);
/* 417 */       } else if (type == Boolean.class || type == boolean.class) {
/* 418 */         Construct boolConstructor = Constructor.this.yamlConstructors.get(Tag.BOOL);
/* 419 */         result = boolConstructor.construct((Node)node);
/* 420 */       } else if (type == Character.class || type == char.class) {
/* 421 */         Construct charConstructor = Constructor.this.yamlConstructors.get(Tag.STR);
/* 422 */         String ch = (String)charConstructor.construct((Node)node);
/* 423 */         if (ch.length() == 0)
/* 424 */         { result = null; }
/* 425 */         else { if (ch.length() != 1) {
/* 426 */             throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
/*     */           }
/*     */           
/* 429 */           result = Character.valueOf(ch.charAt(0)); }
/*     */       
/* 431 */       } else if (Date.class.isAssignableFrom(type)) {
/* 432 */         Construct dateConstructor = Constructor.this.yamlConstructors.get(Tag.TIMESTAMP);
/* 433 */         Date date = (Date)dateConstructor.construct((Node)node);
/* 434 */         if (type == Date.class) {
/* 435 */           result = date;
/*     */         } else {
/*     */           try {
/* 438 */             Constructor<?> constr = type.getConstructor(new Class[] { long.class });
/* 439 */             result = constr.newInstance(new Object[] { Long.valueOf(date.getTime()) });
/* 440 */           } catch (Exception e) {
/* 441 */             throw new YAMLException("Cannot construct: '" + type + "'");
/*     */           } 
/*     */         } 
/* 444 */       } else if (type == Float.class || type == Double.class || type == float.class || type == double.class || type == BigDecimal.class) {
/*     */         
/* 446 */         if (type == BigDecimal.class) {
/* 447 */           result = new BigDecimal(node.getValue());
/*     */         } else {
/* 449 */           Construct doubleConstructor = Constructor.this.yamlConstructors.get(Tag.FLOAT);
/* 450 */           result = doubleConstructor.construct((Node)node);
/* 451 */           if (type == Float.class || type == float.class) {
/* 452 */             result = new Float(((Double)result).doubleValue());
/*     */           }
/*     */         } 
/* 455 */       } else if (type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == BigInteger.class || type == byte.class || type == short.class || type == int.class || type == long.class) {
/*     */ 
/*     */         
/* 458 */         Construct intConstructor = Constructor.this.yamlConstructors.get(Tag.INT);
/* 459 */         result = intConstructor.construct((Node)node);
/* 460 */         if (type == Byte.class || type == byte.class) {
/* 461 */           result = new Byte(result.toString());
/* 462 */         } else if (type == Short.class || type == short.class) {
/* 463 */           result = new Short(result.toString());
/* 464 */         } else if (type == Integer.class || type == int.class) {
/* 465 */           result = Integer.valueOf(Integer.parseInt(result.toString()));
/* 466 */         } else if (type == Long.class || type == long.class) {
/* 467 */           result = new Long(result.toString());
/*     */         } else {
/*     */           
/* 470 */           result = new BigInteger(result.toString());
/*     */         } 
/* 472 */       } else if (Enum.class.isAssignableFrom(type)) {
/* 473 */         String enumValueName = node.getValue();
/*     */         try {
/* 475 */           result = Enum.valueOf(type, enumValueName);
/* 476 */         } catch (Exception ex) {
/* 477 */           throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
/*     */         }
/*     */       
/* 480 */       } else if (Calendar.class.isAssignableFrom(type)) {
/* 481 */         SafeConstructor.ConstructYamlTimestamp contr = new SafeConstructor.ConstructYamlTimestamp();
/* 482 */         contr.construct((Node)node);
/* 483 */         result = contr.getCalendar();
/*     */       } else {
/* 485 */         throw new YAMLException("Unsupported class: " + type);
/*     */       } 
/* 487 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class ConstructSequence
/*     */     implements Construct
/*     */   {
/*     */     public Object construct(Node node) {
/* 498 */       SequenceNode snode = (SequenceNode)node;
/* 499 */       if (Set.class.isAssignableFrom(node.getType())) {
/* 500 */         if (node.isTwoStepsConstruction()) {
/* 501 */           throw new YAMLException("Set cannot be recursive.");
/*     */         }
/* 503 */         return Constructor.this.constructSet(snode);
/*     */       } 
/* 505 */       if (Collection.class.isAssignableFrom(node.getType())) {
/* 506 */         if (node.isTwoStepsConstruction()) {
/* 507 */           return Constructor.this.createDefaultList(snode.getValue().size());
/*     */         }
/* 509 */         return Constructor.this.constructSequence(snode);
/*     */       } 
/* 511 */       if (node.getType().isArray()) {
/* 512 */         if (node.isTwoStepsConstruction()) {
/* 513 */           return Constructor.this.createArray(node.getType(), snode.getValue().size());
/*     */         }
/* 515 */         return Constructor.this.constructArray(snode);
/*     */       } 
/*     */ 
/*     */       
/* 519 */       List<Constructor<?>> possibleConstructors = new ArrayList<Constructor<?>>(snode.getValue().size());
/*     */       
/* 521 */       for (Constructor<?> constructor : node.getType().getConstructors()) {
/*     */         
/* 523 */         if (snode.getValue().size() == (constructor.getParameterTypes()).length) {
/* 524 */           possibleConstructors.add(constructor);
/*     */         }
/*     */       } 
/* 527 */       if (!possibleConstructors.isEmpty()) {
/* 528 */         if (possibleConstructors.size() == 1) {
/* 529 */           Object[] arrayOfObject = new Object[snode.getValue().size()];
/* 530 */           Constructor<?> c = possibleConstructors.get(0);
/* 531 */           int i = 0;
/* 532 */           for (Node argumentNode : snode.getValue()) {
/* 533 */             Class<?> type = c.getParameterTypes()[i];
/*     */             
/* 535 */             argumentNode.setType(type);
/* 536 */             arrayOfObject[i++] = Constructor.this.constructObject(argumentNode);
/*     */           } 
/*     */           
/*     */           try {
/* 540 */             return c.newInstance(arrayOfObject);
/* 541 */           } catch (Exception e) {
/* 542 */             throw new YAMLException(e);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 547 */         List<Object> argumentList = (List)Constructor.this.constructSequence(snode);
/* 548 */         Class<?>[] parameterTypes = new Class[argumentList.size()];
/* 549 */         int index = 0;
/* 550 */         for (Object parameter : argumentList) {
/* 551 */           parameterTypes[index] = parameter.getClass();
/* 552 */           index++;
/*     */         } 
/*     */         
/* 555 */         for (Constructor<?> c : possibleConstructors) {
/* 556 */           Class<?>[] argTypes = c.getParameterTypes();
/* 557 */           boolean foundConstructor = true;
/* 558 */           for (int i = 0; i < argTypes.length; i++) {
/* 559 */             if (!wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) {
/* 560 */               foundConstructor = false;
/*     */               break;
/*     */             } 
/*     */           } 
/* 564 */           if (foundConstructor) {
/*     */             try {
/* 566 */               return c.newInstance(argumentList.toArray());
/* 567 */             } catch (Exception e) {
/* 568 */               throw new YAMLException(e);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 573 */       throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + node.getType());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class<? extends Object> wrapIfPrimitive(Class<?> clazz) {
/* 581 */       if (!clazz.isPrimitive()) {
/* 582 */         return (Class)clazz;
/*     */       }
/* 584 */       if (clazz == int.class) {
/* 585 */         return (Class)Integer.class;
/*     */       }
/* 587 */       if (clazz == float.class) {
/* 588 */         return (Class)Float.class;
/*     */       }
/* 590 */       if (clazz == double.class) {
/* 591 */         return (Class)Double.class;
/*     */       }
/* 593 */       if (clazz == boolean.class) {
/* 594 */         return (Class)Boolean.class;
/*     */       }
/* 596 */       if (clazz == long.class) {
/* 597 */         return (Class)Long.class;
/*     */       }
/* 599 */       if (clazz == char.class) {
/* 600 */         return (Class)Character.class;
/*     */       }
/* 602 */       if (clazz == short.class) {
/* 603 */         return (Class)Short.class;
/*     */       }
/* 605 */       if (clazz == byte.class) {
/* 606 */         return (Class)Byte.class;
/*     */       }
/* 608 */       throw new YAMLException("Unexpected primitive " + clazz);
/*     */     }
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 613 */       SequenceNode snode = (SequenceNode)node;
/* 614 */       if (List.class.isAssignableFrom(node.getType())) {
/* 615 */         List<Object> list = (List<Object>)object;
/* 616 */         Constructor.this.constructSequenceStep2(snode, list);
/* 617 */       } else if (node.getType().isArray()) {
/* 618 */         Constructor.this.constructArrayStep2(snode, object);
/*     */       } else {
/* 620 */         throw new YAMLException("Immutable objects cannot be recursive.");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected Class<?> getClassForNode(Node node) {
/* 626 */     Class<? extends Object> classForTag = this.typeTags.get(node.getTag());
/* 627 */     if (classForTag == null) {
/* 628 */       Class<?> cl; String name = node.getTag().getClassName();
/*     */       
/*     */       try {
/* 631 */         cl = getClassForName(name);
/* 632 */       } catch (ClassNotFoundException e) {
/* 633 */         throw new YAMLException("Class not found: " + name);
/*     */       } 
/* 635 */       this.typeTags.put(node.getTag(), cl);
/* 636 */       return cl;
/*     */     } 
/* 638 */     return classForTag;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> getClassForName(String name) throws ClassNotFoundException {
/* 643 */     return Class.forName(name);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\constructor\Constructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */