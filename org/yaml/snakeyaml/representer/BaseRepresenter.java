/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.PropertyUtils;
/*     */ import org.yaml.snakeyaml.nodes.AnchorNode;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
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
/*     */ public abstract class BaseRepresenter
/*     */ {
/*  41 */   protected final Map<Class<?>, Represent> representers = new HashMap<Class<?>, Represent>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected Represent nullRepresenter;
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected final Map<Class<?>, Represent> multiRepresenters = new LinkedHashMap<Class<?>, Represent>();
/*     */   protected Character defaultScalarStyle;
/*  51 */   protected DumperOptions.FlowStyle defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
/*  52 */   protected final Map<Object, Node> representedObjects = new IdentityHashMap<Object, Node>() {
/*     */       private static final long serialVersionUID = -5576159264232131854L;
/*     */       
/*     */       public Node put(Object key, Node value) {
/*  56 */         return (Node)super.put(key, new AnchorNode(value));
/*     */       }
/*     */     };
/*     */   
/*     */   protected Object objectToRepresent;
/*     */   private PropertyUtils propertyUtils;
/*     */   private boolean explicitPropertyUtils = false;
/*     */   
/*     */   public Node represent(Object data) {
/*  65 */     Node node = representData(data);
/*  66 */     this.representedObjects.clear();
/*  67 */     this.objectToRepresent = null;
/*  68 */     return node;
/*     */   }
/*     */   protected final Node representData(Object data) {
/*     */     Node node;
/*  72 */     this.objectToRepresent = data;
/*     */     
/*  74 */     if (this.representedObjects.containsKey(this.objectToRepresent)) {
/*  75 */       node = this.representedObjects.get(this.objectToRepresent);
/*  76 */       return node;
/*     */     } 
/*     */ 
/*     */     
/*  80 */     if (data == null) {
/*  81 */       node = this.nullRepresenter.representData(null);
/*  82 */       return node;
/*     */     } 
/*     */ 
/*     */     
/*  86 */     Class<?> clazz = data.getClass();
/*  87 */     if (this.representers.containsKey(clazz)) {
/*  88 */       Represent representer = this.representers.get(clazz);
/*  89 */       node = representer.representData(data);
/*     */     } else {
/*     */       
/*  92 */       for (Class<?> repr : this.multiRepresenters.keySet()) {
/*  93 */         if (repr.isInstance(data)) {
/*  94 */           Represent representer = this.multiRepresenters.get(repr);
/*  95 */           node = representer.representData(data);
/*  96 */           return node;
/*     */         } 
/*     */       } 
/*     */       
/* 100 */       if (clazz.isArray()) {
/* 101 */         throw new YAMLException("Arrays of primitives are not fully supported.");
/*     */       }
/*     */       
/* 104 */       if (this.multiRepresenters.containsKey(null)) {
/* 105 */         Represent representer = this.multiRepresenters.get(null);
/* 106 */         node = representer.representData(data);
/*     */       } else {
/* 108 */         Represent representer = this.representers.get(null);
/* 109 */         node = representer.representData(data);
/*     */       } 
/*     */     } 
/* 112 */     return node;
/*     */   }
/*     */   
/*     */   protected Node representScalar(Tag tag, String value, Character style) {
/* 116 */     if (style == null) {
/* 117 */       style = this.defaultScalarStyle;
/*     */     }
/* 119 */     return (Node)new ScalarNode(tag, value, null, null, style);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Node representScalar(Tag tag, String value) {
/* 124 */     return representScalar(tag, value, null);
/*     */   }
/*     */   
/*     */   protected Node representSequence(Tag tag, Iterable<? extends Object> sequence, Boolean flowStyle) {
/* 128 */     int size = 10;
/* 129 */     if (sequence instanceof List) {
/* 130 */       size = ((List)sequence).size();
/*     */     }
/* 132 */     List<Node> value = new ArrayList<Node>(size);
/* 133 */     SequenceNode node = new SequenceNode(tag, value, flowStyle);
/* 134 */     this.representedObjects.put(this.objectToRepresent, node);
/* 135 */     boolean bestStyle = true;
/* 136 */     for (Object item : sequence) {
/* 137 */       Node nodeItem = representData(item);
/* 138 */       if (!(nodeItem instanceof ScalarNode) || ((ScalarNode)nodeItem).getStyle() != null) {
/* 139 */         bestStyle = false;
/*     */       }
/* 141 */       value.add(nodeItem);
/*     */     } 
/* 143 */     if (flowStyle == null) {
/* 144 */       if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
/* 145 */         node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
/*     */       } else {
/* 147 */         node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */       } 
/*     */     }
/* 150 */     return (Node)node;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Node representMapping(Tag tag, Map<? extends Object, Object> mapping, Boolean flowStyle) {
/* 155 */     List<NodeTuple> value = new ArrayList<NodeTuple>(mapping.size());
/* 156 */     MappingNode node = new MappingNode(tag, value, flowStyle);
/* 157 */     this.representedObjects.put(this.objectToRepresent, node);
/* 158 */     boolean bestStyle = true;
/* 159 */     for (Map.Entry<? extends Object, Object> entry : mapping.entrySet()) {
/* 160 */       Node nodeKey = representData(entry.getKey());
/* 161 */       Node nodeValue = representData(entry.getValue());
/* 162 */       if (!(nodeKey instanceof ScalarNode) || ((ScalarNode)nodeKey).getStyle() != null) {
/* 163 */         bestStyle = false;
/*     */       }
/* 165 */       if (!(nodeValue instanceof ScalarNode) || ((ScalarNode)nodeValue).getStyle() != null) {
/* 166 */         bestStyle = false;
/*     */       }
/* 168 */       value.add(new NodeTuple(nodeKey, nodeValue));
/*     */     } 
/* 170 */     if (flowStyle == null) {
/* 171 */       if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
/* 172 */         node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
/*     */       } else {
/* 174 */         node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */       } 
/*     */     }
/* 177 */     return (Node)node;
/*     */   }
/*     */   
/*     */   public void setDefaultScalarStyle(DumperOptions.ScalarStyle defaultStyle) {
/* 181 */     this.defaultScalarStyle = defaultStyle.getChar();
/*     */   }
/*     */   
/*     */   public void setDefaultFlowStyle(DumperOptions.FlowStyle defaultFlowStyle) {
/* 185 */     this.defaultFlowStyle = defaultFlowStyle;
/*     */   }
/*     */   
/*     */   public DumperOptions.FlowStyle getDefaultFlowStyle() {
/* 189 */     return this.defaultFlowStyle;
/*     */   }
/*     */   
/*     */   public void setPropertyUtils(PropertyUtils propertyUtils) {
/* 193 */     this.propertyUtils = propertyUtils;
/* 194 */     this.explicitPropertyUtils = true;
/*     */   }
/*     */   
/*     */   public final PropertyUtils getPropertyUtils() {
/* 198 */     if (this.propertyUtils == null) {
/* 199 */       this.propertyUtils = new PropertyUtils();
/*     */     }
/* 201 */     return this.propertyUtils;
/*     */   }
/*     */   
/*     */   public final boolean isExplicitPropertyUtils() {
/* 205 */     return this.explicitPropertyUtils;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\representer\BaseRepresenter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */