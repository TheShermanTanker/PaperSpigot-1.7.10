/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.composer.Composer;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.PropertyUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseConstructor
/*     */ {
/*  47 */   protected final Map<NodeId, Construct> yamlClassConstructors = new EnumMap<NodeId, Construct>(NodeId.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected final Map<Tag, Construct> yamlConstructors = new HashMap<Tag, Construct>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   protected final Map<String, Construct> yamlMultiConstructors = new HashMap<String, Construct>();
/*     */   
/*     */   private Composer composer;
/*     */   
/*     */   private final Map<Node, Object> constructedObjects;
/*     */   private final Set<Node> recursiveObjects;
/*     */   private final ArrayList<RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>>> maps2fill;
/*     */   private final ArrayList<RecursiveTuple<Set<Object>, Object>> sets2fill;
/*     */   protected Tag rootTag;
/*     */   private PropertyUtils propertyUtils;
/*     */   private boolean explicitPropertyUtils;
/*     */   
/*     */   public BaseConstructor() {
/*  74 */     this.constructedObjects = new HashMap<Node, Object>();
/*  75 */     this.recursiveObjects = new HashSet<Node>();
/*  76 */     this.maps2fill = new ArrayList<RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>>>();
/*  77 */     this.sets2fill = new ArrayList<RecursiveTuple<Set<Object>, Object>>();
/*  78 */     this.rootTag = null;
/*  79 */     this.explicitPropertyUtils = false;
/*     */   }
/*     */   
/*     */   public void setComposer(Composer composer) {
/*  83 */     this.composer = composer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkData() {
/*  93 */     return this.composer.checkNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getData() {
/* 103 */     this.composer.checkNode();
/* 104 */     Node node = this.composer.getNode();
/* 105 */     if (this.rootTag != null) {
/* 106 */       node.setTag(this.rootTag);
/*     */     }
/* 108 */     return constructDocument(node);
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
/*     */   public Object getSingleData(Class<?> type) {
/* 120 */     Node node = this.composer.getSingleNode();
/* 121 */     if (node != null) {
/* 122 */       if (Object.class != type) {
/* 123 */         node.setTag(new Tag(type));
/* 124 */       } else if (this.rootTag != null) {
/* 125 */         node.setTag(this.rootTag);
/*     */       } 
/* 127 */       return constructDocument(node);
/*     */     } 
/* 129 */     return null;
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
/*     */   private Object constructDocument(Node node) {
/* 141 */     Object data = constructObject(node);
/* 142 */     fillRecursive();
/* 143 */     this.constructedObjects.clear();
/* 144 */     this.recursiveObjects.clear();
/* 145 */     return data;
/*     */   }
/*     */   
/*     */   private void fillRecursive() {
/* 149 */     if (!this.maps2fill.isEmpty()) {
/* 150 */       for (RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>> entry : this.maps2fill) {
/* 151 */         RecursiveTuple<Object, Object> key_value = entry._2();
/* 152 */         ((Map)entry._1()).put(key_value._1(), key_value._2());
/*     */       } 
/* 154 */       this.maps2fill.clear();
/*     */     } 
/* 156 */     if (!this.sets2fill.isEmpty()) {
/* 157 */       for (RecursiveTuple<Set<Object>, Object> value : this.sets2fill) {
/* 158 */         ((Set)value._1()).add(value._2());
/*     */       }
/* 160 */       this.sets2fill.clear();
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
/*     */   protected Object constructObject(Node node) {
/* 173 */     if (this.constructedObjects.containsKey(node)) {
/* 174 */       return this.constructedObjects.get(node);
/*     */     }
/* 176 */     if (this.recursiveObjects.contains(node)) {
/* 177 */       throw new ConstructorException(null, null, "found unconstructable recursive node", node.getStartMark());
/*     */     }
/*     */     
/* 180 */     this.recursiveObjects.add(node);
/* 181 */     Construct constructor = getConstructor(node);
/* 182 */     Object data = constructor.construct(node);
/* 183 */     this.constructedObjects.put(node, data);
/* 184 */     this.recursiveObjects.remove(node);
/* 185 */     if (node.isTwoStepsConstruction()) {
/* 186 */       constructor.construct2ndStep(node, data);
/*     */     }
/* 188 */     return data;
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
/*     */   protected Construct getConstructor(Node node) {
/* 201 */     if (node.useClassConstructor()) {
/* 202 */       return this.yamlClassConstructors.get(node.getNodeId());
/*     */     }
/* 204 */     Construct constructor = this.yamlConstructors.get(node.getTag());
/* 205 */     if (constructor == null) {
/* 206 */       for (String prefix : this.yamlMultiConstructors.keySet()) {
/* 207 */         if (node.getTag().startsWith(prefix)) {
/* 208 */           return this.yamlMultiConstructors.get(prefix);
/*     */         }
/*     */       } 
/* 211 */       return this.yamlConstructors.get(null);
/*     */     } 
/* 213 */     return constructor;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object constructScalar(ScalarNode node) {
/* 218 */     return node.getValue();
/*     */   }
/*     */   
/*     */   protected List<Object> createDefaultList(int initSize) {
/* 222 */     return new ArrayList(initSize);
/*     */   }
/*     */   
/*     */   protected Set<Object> createDefaultSet(int initSize) {
/* 226 */     return new LinkedHashSet(initSize);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> T[] createArray(Class<T> type, int size) {
/* 231 */     return (T[])Array.newInstance(type.getComponentType(), size);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<? extends Object> constructSequence(SequenceNode node) {
/*     */     List<Object> list;
/* 237 */     if (List.class.isAssignableFrom(node.getType()) && !node.getType().isInterface()) {
/*     */       
/*     */       try {
/* 240 */         list = node.getType().newInstance();
/* 241 */       } catch (Exception e) {
/* 242 */         throw new YAMLException(e);
/*     */       } 
/*     */     } else {
/* 245 */       list = createDefaultList(node.getValue().size());
/*     */     } 
/* 247 */     constructSequenceStep2(node, list);
/* 248 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Set<? extends Object> constructSet(SequenceNode node) {
/*     */     Set<Object> set;
/* 255 */     if (!node.getType().isInterface()) {
/*     */       
/*     */       try {
/* 258 */         set = node.getType().newInstance();
/* 259 */       } catch (Exception e) {
/* 260 */         throw new YAMLException(e);
/*     */       } 
/*     */     } else {
/* 263 */       set = createDefaultSet(node.getValue().size());
/*     */     } 
/* 265 */     constructSequenceStep2(node, set);
/* 266 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object constructArray(SequenceNode node) {
/* 271 */     return constructArrayStep2(node, createArray(node.getType(), node.getValue().size()));
/*     */   }
/*     */   
/*     */   protected void constructSequenceStep2(SequenceNode node, Collection<Object> collection) {
/* 275 */     for (Node child : node.getValue()) {
/* 276 */       collection.add(constructObject(child));
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object constructArrayStep2(SequenceNode node, Object array) {
/* 281 */     int index = 0;
/* 282 */     for (Node child : node.getValue()) {
/* 283 */       Array.set(array, index++, constructObject(child));
/*     */     }
/* 285 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<Object, Object> createDefaultMap() {
/* 290 */     return new LinkedHashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<Object> createDefaultSet() {
/* 295 */     return new LinkedHashSet();
/*     */   }
/*     */   
/*     */   protected Set<Object> constructSet(MappingNode node) {
/* 299 */     Set<Object> set = createDefaultSet();
/* 300 */     constructSet2ndStep(node, set);
/* 301 */     return set;
/*     */   }
/*     */   
/*     */   protected Map<Object, Object> constructMapping(MappingNode node) {
/* 305 */     Map<Object, Object> mapping = createDefaultMap();
/* 306 */     constructMapping2ndStep(node, mapping);
/* 307 */     return mapping;
/*     */   }
/*     */   
/*     */   protected void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping) {
/* 311 */     List<NodeTuple> nodeValue = node.getValue();
/* 312 */     for (NodeTuple tuple : nodeValue) {
/* 313 */       Node keyNode = tuple.getKeyNode();
/* 314 */       Node valueNode = tuple.getValueNode();
/* 315 */       Object key = constructObject(keyNode);
/* 316 */       if (key != null) {
/*     */         try {
/* 318 */           key.hashCode();
/* 319 */         } catch (Exception e) {
/* 320 */           throw new ConstructorException("while constructing a mapping", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 325 */       Object value = constructObject(valueNode);
/* 326 */       if (keyNode.isTwoStepsConstruction()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 333 */         this.maps2fill.add(0, new RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>>(mapping, new RecursiveTuple<Object, Object>(key, value)));
/*     */         
/*     */         continue;
/*     */       } 
/* 337 */       mapping.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void constructSet2ndStep(MappingNode node, Set<Object> set) {
/* 343 */     List<NodeTuple> nodeValue = node.getValue();
/* 344 */     for (NodeTuple tuple : nodeValue) {
/* 345 */       Node keyNode = tuple.getKeyNode();
/* 346 */       Object key = constructObject(keyNode);
/* 347 */       if (key != null) {
/*     */         try {
/* 349 */           key.hashCode();
/* 350 */         } catch (Exception e) {
/* 351 */           throw new ConstructorException("while constructing a Set", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
/*     */         } 
/*     */       }
/*     */       
/* 355 */       if (keyNode.isTwoStepsConstruction()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 362 */         this.sets2fill.add(0, new RecursiveTuple<Set<Object>, Object>(set, key)); continue;
/*     */       } 
/* 364 */       set.add(key);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyUtils(PropertyUtils propertyUtils) {
/* 382 */     this.propertyUtils = propertyUtils;
/* 383 */     this.explicitPropertyUtils = true;
/*     */   }
/*     */   
/*     */   public final PropertyUtils getPropertyUtils() {
/* 387 */     if (this.propertyUtils == null) {
/* 388 */       this.propertyUtils = new PropertyUtils();
/*     */     }
/* 390 */     return this.propertyUtils;
/*     */   }
/*     */   
/*     */   private static class RecursiveTuple<T, K> {
/*     */     private final T _1;
/*     */     private final K _2;
/*     */     
/*     */     public RecursiveTuple(T _1, K _2) {
/* 398 */       this._1 = _1;
/* 399 */       this._2 = _2;
/*     */     }
/*     */     
/*     */     public K _2() {
/* 403 */       return this._2;
/*     */     }
/*     */     
/*     */     public T _1() {
/* 407 */       return this._1;
/*     */     }
/*     */   }
/*     */   
/*     */   public final boolean isExplicitPropertyUtils() {
/* 412 */     return this.explicitPropertyUtils;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\constructor\BaseConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */