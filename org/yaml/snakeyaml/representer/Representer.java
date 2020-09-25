/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import org.yaml.snakeyaml.DumperOptions;
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
/*     */ 
/*     */ 
/*     */ public class Representer
/*     */   extends SafeRepresenter
/*     */ {
/*     */   public Representer() {
/*  43 */     this.representers.put(null, new RepresentJavaBean());
/*     */   }
/*     */   
/*     */   protected class RepresentJavaBean implements Represent {
/*     */     public Node representData(Object data) {
/*     */       try {
/*  49 */         return (Node)Representer.this.representJavaBean(Representer.this.getProperties((Class)data.getClass()), data);
/*  50 */       } catch (IntrospectionException e) {
/*  51 */         throw new YAMLException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
/*  71 */     List<NodeTuple> value = new ArrayList<NodeTuple>(properties.size());
/*     */     
/*  73 */     Tag customTag = this.classTags.get(javaBean.getClass());
/*  74 */     Tag tag = (customTag != null) ? customTag : new Tag(javaBean.getClass());
/*     */     
/*  76 */     MappingNode node = new MappingNode(tag, value, null);
/*  77 */     this.representedObjects.put(javaBean, node);
/*  78 */     boolean bestStyle = true;
/*  79 */     for (Property property : properties) {
/*  80 */       Object memberValue = property.get(javaBean);
/*  81 */       Tag customPropertyTag = (memberValue == null) ? null : this.classTags.get(memberValue.getClass());
/*     */       
/*  83 */       NodeTuple tuple = representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
/*     */       
/*  85 */       if (tuple == null) {
/*     */         continue;
/*     */       }
/*  88 */       if (((ScalarNode)tuple.getKeyNode()).getStyle() != null) {
/*  89 */         bestStyle = false;
/*     */       }
/*  91 */       Node nodeValue = tuple.getValueNode();
/*  92 */       if (!(nodeValue instanceof ScalarNode) || ((ScalarNode)nodeValue).getStyle() != null) {
/*  93 */         bestStyle = false;
/*     */       }
/*  95 */       value.add(tuple);
/*     */     } 
/*  97 */     if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
/*  98 */       node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
/*     */     } else {
/* 100 */       node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */     } 
/* 102 */     return node;
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
/*     */   protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
/* 121 */     ScalarNode nodeKey = (ScalarNode)representData(property.getName());
/*     */     
/* 123 */     boolean hasAlias = this.representedObjects.containsKey(propertyValue);
/*     */     
/* 125 */     Node nodeValue = representData(propertyValue);
/*     */     
/* 127 */     if (propertyValue != null && !hasAlias) {
/* 128 */       NodeId nodeId = nodeValue.getNodeId();
/* 129 */       if (customTag == null) {
/* 130 */         if (nodeId == NodeId.scalar) {
/* 131 */           if (propertyValue instanceof Enum) {
/* 132 */             nodeValue.setTag(Tag.STR);
/*     */           }
/*     */         } else {
/* 135 */           if (nodeId == NodeId.mapping && 
/* 136 */             property.getType() == propertyValue.getClass() && 
/* 137 */             !(propertyValue instanceof java.util.Map) && 
/* 138 */             !nodeValue.getTag().equals(Tag.SET)) {
/* 139 */             nodeValue.setTag(Tag.MAP);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 144 */           checkGlobalTag(property, nodeValue, propertyValue);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return new NodeTuple((Node)nodeKey, nodeValue);
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
/*     */   protected void checkGlobalTag(Property property, Node node, Object object) {
/* 165 */     Class<?>[] arguments = property.getActualTypeArguments();
/* 166 */     if (arguments != null) {
/* 167 */       if (node.getNodeId() == NodeId.sequence) {
/*     */         Iterable<Object> memberList;
/* 169 */         Class<? extends Object> t = (Class)arguments[0];
/* 170 */         SequenceNode snode = (SequenceNode)node;
/*     */         
/* 172 */         if (object.getClass().isArray()) {
/* 173 */           memberList = Arrays.asList((Object[])object);
/*     */         } else {
/*     */           
/* 176 */           memberList = (Iterable<Object>)object;
/*     */         } 
/* 178 */         Iterator<Object> iter = memberList.iterator();
/* 179 */         for (Node childNode : snode.getValue()) {
/* 180 */           Object member = iter.next();
/* 181 */           if (member != null && 
/* 182 */             t.equals(member.getClass()) && 
/* 183 */             childNode.getNodeId() == NodeId.mapping) {
/* 184 */             childNode.setTag(Tag.MAP);
/*     */           }
/*     */         }
/*     */       
/* 188 */       } else if (object instanceof Set) {
/* 189 */         Class<?> t = arguments[0];
/* 190 */         MappingNode mnode = (MappingNode)node;
/* 191 */         Iterator<NodeTuple> iter = mnode.getValue().iterator();
/* 192 */         Set<?> set = (Set)object;
/* 193 */         for (Object member : set) {
/* 194 */           NodeTuple tuple = iter.next();
/* 195 */           Node keyNode = tuple.getKeyNode();
/* 196 */           if (t.equals(member.getClass()) && 
/* 197 */             keyNode.getNodeId() == NodeId.mapping) {
/* 198 */             keyNode.setTag(Tag.MAP);
/*     */           }
/*     */         }
/*     */       
/* 202 */       } else if (object instanceof java.util.Map) {
/* 203 */         Class<?> keyType = arguments[0];
/* 204 */         Class<?> valueType = arguments[1];
/* 205 */         MappingNode mnode = (MappingNode)node;
/* 206 */         for (NodeTuple tuple : mnode.getValue()) {
/* 207 */           resetTag((Class)keyType, tuple.getKeyNode());
/* 208 */           resetTag((Class)valueType, tuple.getValueNode());
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetTag(Class<? extends Object> type, Node node) {
/* 218 */     Tag tag = node.getTag();
/* 219 */     if (tag.matches(type)) {
/* 220 */       if (Enum.class.isAssignableFrom(type)) {
/* 221 */         node.setTag(Tag.STR);
/*     */       } else {
/* 223 */         node.setTag(Tag.MAP);
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
/*     */ 
/*     */   
/*     */   protected Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException {
/* 238 */     return getPropertyUtils().getProperties(type);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\representer\Representer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */