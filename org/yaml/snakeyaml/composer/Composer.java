/*     */ package org.yaml.snakeyaml.composer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.events.AliasEvent;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*     */ import org.yaml.snakeyaml.events.NodeEvent;
/*     */ import org.yaml.snakeyaml.events.ScalarEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.parser.Parser;
/*     */ import org.yaml.snakeyaml.resolver.Resolver;
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
/*     */ public class Composer
/*     */ {
/*     */   private final Parser parser;
/*     */   private final Resolver resolver;
/*     */   private final Map<String, Node> anchors;
/*     */   private final Set<Node> recursiveNodes;
/*     */   
/*     */   public Composer(Parser parser, Resolver resolver) {
/*  55 */     this.parser = parser;
/*  56 */     this.resolver = resolver;
/*  57 */     this.anchors = new HashMap<String, Node>();
/*  58 */     this.recursiveNodes = new HashSet<Node>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkNode() {
/*  68 */     if (this.parser.checkEvent(Event.ID.StreamStart)) {
/*  69 */       this.parser.getEvent();
/*     */     }
/*     */     
/*  72 */     return !this.parser.checkEvent(Event.ID.StreamEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode() {
/*  83 */     if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
/*  84 */       return composeDocument();
/*     */     }
/*  86 */     return null;
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
/*     */   public Node getSingleNode() {
/* 101 */     this.parser.getEvent();
/*     */     
/* 103 */     Node document = null;
/* 104 */     if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
/* 105 */       document = composeDocument();
/*     */     }
/*     */     
/* 108 */     if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
/* 109 */       Event event = this.parser.getEvent();
/* 110 */       throw new ComposerException("expected a single document in the stream", document.getStartMark(), "but found another document", event.getStartMark());
/*     */     } 
/*     */ 
/*     */     
/* 114 */     this.parser.getEvent();
/* 115 */     return document;
/*     */   }
/*     */ 
/*     */   
/*     */   private Node composeDocument() {
/* 120 */     this.parser.getEvent();
/*     */     
/* 122 */     Node node = composeNode(null);
/*     */     
/* 124 */     this.parser.getEvent();
/* 125 */     this.anchors.clear();
/* 126 */     this.recursiveNodes.clear();
/* 127 */     return node;
/*     */   }
/*     */   
/*     */   private Node composeNode(Node parent) {
/* 131 */     this.recursiveNodes.add(parent);
/* 132 */     if (this.parser.checkEvent(Event.ID.Alias)) {
/* 133 */       AliasEvent aliasEvent = (AliasEvent)this.parser.getEvent();
/* 134 */       String str = aliasEvent.getAnchor();
/* 135 */       if (!this.anchors.containsKey(str)) {
/* 136 */         throw new ComposerException(null, null, "found undefined alias " + str, aliasEvent.getStartMark());
/*     */       }
/*     */       
/* 139 */       Node result = this.anchors.get(str);
/* 140 */       if (this.recursiveNodes.remove(result)) {
/* 141 */         result.setTwoStepsConstruction(true);
/*     */       }
/* 143 */       return result;
/*     */     } 
/* 145 */     NodeEvent event = (NodeEvent)this.parser.peekEvent();
/* 146 */     String anchor = null;
/* 147 */     anchor = event.getAnchor();
/* 148 */     if (anchor != null && this.anchors.containsKey(anchor)) {
/* 149 */       throw new ComposerException("found duplicate anchor " + anchor + "; first occurence", ((Node)this.anchors.get(anchor)).getStartMark(), "second occurence", event.getStartMark());
/*     */     }
/*     */ 
/*     */     
/* 153 */     Node node = null;
/* 154 */     if (this.parser.checkEvent(Event.ID.Scalar)) {
/* 155 */       node = composeScalarNode(anchor);
/* 156 */     } else if (this.parser.checkEvent(Event.ID.SequenceStart)) {
/* 157 */       node = composeSequenceNode(anchor);
/*     */     } else {
/* 159 */       node = composeMappingNode(anchor);
/*     */     } 
/* 161 */     this.recursiveNodes.remove(parent);
/* 162 */     return node;
/*     */   }
/*     */   private Node composeScalarNode(String anchor) {
/*     */     Tag nodeTag;
/* 166 */     ScalarEvent ev = (ScalarEvent)this.parser.getEvent();
/* 167 */     String tag = ev.getTag();
/* 168 */     boolean resolved = false;
/*     */     
/* 170 */     if (tag == null || tag.equals("!")) {
/* 171 */       nodeTag = this.resolver.resolve(NodeId.scalar, ev.getValue(), ev.getImplicit().canOmitTagInPlainScalar());
/*     */       
/* 173 */       resolved = true;
/*     */     } else {
/* 175 */       nodeTag = new Tag(tag);
/*     */     } 
/* 177 */     ScalarNode scalarNode = new ScalarNode(nodeTag, resolved, ev.getValue(), ev.getStartMark(), ev.getEndMark(), ev.getStyle());
/*     */     
/* 179 */     if (anchor != null) {
/* 180 */       this.anchors.put(anchor, scalarNode);
/*     */     }
/* 182 */     return (Node)scalarNode;
/*     */   }
/*     */   private Node composeSequenceNode(String anchor) {
/*     */     Tag nodeTag;
/* 186 */     SequenceStartEvent startEvent = (SequenceStartEvent)this.parser.getEvent();
/* 187 */     String tag = startEvent.getTag();
/*     */     
/* 189 */     boolean resolved = false;
/* 190 */     if (tag == null || tag.equals("!")) {
/* 191 */       nodeTag = this.resolver.resolve(NodeId.sequence, null, startEvent.getImplicit());
/* 192 */       resolved = true;
/*     */     } else {
/* 194 */       nodeTag = new Tag(tag);
/*     */     } 
/* 196 */     ArrayList<Node> children = new ArrayList<Node>();
/* 197 */     SequenceNode node = new SequenceNode(nodeTag, resolved, children, startEvent.getStartMark(), null, startEvent.getFlowStyle());
/*     */     
/* 199 */     if (anchor != null) {
/* 200 */       this.anchors.put(anchor, node);
/*     */     }
/* 202 */     int index = 0;
/* 203 */     while (!this.parser.checkEvent(Event.ID.SequenceEnd)) {
/* 204 */       children.add(composeNode((Node)node));
/* 205 */       index++;
/*     */     } 
/* 207 */     Event endEvent = this.parser.getEvent();
/* 208 */     node.setEndMark(endEvent.getEndMark());
/* 209 */     return (Node)node;
/*     */   }
/*     */   private Node composeMappingNode(String anchor) {
/*     */     Tag nodeTag;
/* 213 */     MappingStartEvent startEvent = (MappingStartEvent)this.parser.getEvent();
/* 214 */     String tag = startEvent.getTag();
/*     */     
/* 216 */     boolean resolved = false;
/* 217 */     if (tag == null || tag.equals("!")) {
/* 218 */       nodeTag = this.resolver.resolve(NodeId.mapping, null, startEvent.getImplicit());
/* 219 */       resolved = true;
/*     */     } else {
/* 221 */       nodeTag = new Tag(tag);
/*     */     } 
/*     */     
/* 224 */     List<NodeTuple> children = new ArrayList<NodeTuple>();
/* 225 */     MappingNode node = new MappingNode(nodeTag, resolved, children, startEvent.getStartMark(), null, startEvent.getFlowStyle());
/*     */     
/* 227 */     if (anchor != null) {
/* 228 */       this.anchors.put(anchor, node);
/*     */     }
/* 230 */     while (!this.parser.checkEvent(Event.ID.MappingEnd)) {
/* 231 */       Node itemKey = composeNode((Node)node);
/* 232 */       if (itemKey.getTag().equals(Tag.MERGE)) {
/* 233 */         node.setMerged(true);
/* 234 */       } else if (itemKey.getTag().equals(Tag.VALUE)) {
/* 235 */         itemKey.setTag(Tag.STR);
/*     */       } 
/* 237 */       Node itemValue = composeNode((Node)node);
/* 238 */       children.add(new NodeTuple(itemKey, itemValue));
/*     */     } 
/* 240 */     Event endEvent = this.parser.getEvent();
/* 241 */     node.setEndMark(endEvent.getEndMark());
/* 242 */     return (Node)node;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\composer\Composer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */