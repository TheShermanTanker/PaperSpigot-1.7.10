/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ public class Dnode
/*     */ {
/*     */   int level;
/*     */   String nodeName;
/*     */   String nodeContent;
/*     */   ArrayList<Dnode> children;
/*  47 */   LinkedHashMap<String, String> attrList = new LinkedHashMap<String, String>();
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
/*     */   public static Dnode parse(String s) {
/*  59 */     DnodeReader r = new DnodeReader();
/*  60 */     return r.parseXml(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toXml() {
/*  67 */     StringBuilder sb = new StringBuilder();
/*  68 */     generate(sb);
/*  69 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder generate(StringBuilder sb) {
/*  76 */     if (sb == null) {
/*  77 */       sb = new StringBuilder();
/*     */     }
/*  79 */     sb.append("<").append(this.nodeName);
/*  80 */     Iterator<String> it = attributeNames();
/*  81 */     while (it.hasNext()) {
/*  82 */       String attr = it.next();
/*  83 */       Object attrValue = getAttribute(attr);
/*  84 */       sb.append(" ").append(attr).append("=\"");
/*  85 */       if (attrValue != null) {
/*  86 */         sb.append(attrValue);
/*     */       }
/*  88 */       sb.append("\"");
/*     */     } 
/*     */     
/*  91 */     if (this.nodeContent == null && !hasChildren()) {
/*  92 */       sb.append(" />");
/*     */     } else {
/*     */       
/*  95 */       sb.append(">");
/*  96 */       if (this.children != null && this.children.size() > 0) {
/*  97 */         for (int i = 0; i < this.children.size(); i++) {
/*  98 */           Dnode child = this.children.get(i);
/*  99 */           child.generate(sb);
/*     */         } 
/*     */       }
/* 102 */       if (this.nodeContent != null) {
/* 103 */         sb.append(this.nodeContent);
/*     */       }
/* 105 */       sb.append("</").append(this.nodeName).append(">");
/*     */     } 
/* 107 */     return sb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeName() {
/* 114 */     return this.nodeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNodeName(String nodeName) {
/* 121 */     this.nodeName = nodeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeContent() {
/* 128 */     return this.nodeContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNodeContent(String nodeContent) {
/* 135 */     this.nodeContent = nodeContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChildren() {
/* 142 */     return (getChildrenCount() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChildrenCount() {
/* 149 */     if (this.children == null) {
/* 150 */       return 0;
/*     */     }
/* 152 */     return this.children.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Dnode node) {
/* 159 */     if (this.children == null) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (this.children.remove(node)) {
/* 163 */       return true;
/*     */     }
/* 165 */     Iterator<Dnode> it = this.children.iterator();
/* 166 */     while (it.hasNext()) {
/* 167 */       Dnode child = it.next();
/* 168 */       if (child.remove(node)) {
/* 169 */         return true;
/*     */       }
/*     */     } 
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Dnode> children() {
/* 179 */     if (this.children == null) {
/* 180 */       return null;
/*     */     }
/* 182 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(Dnode child) {
/* 189 */     if (this.children == null) {
/* 190 */       this.children = new ArrayList<Dnode>();
/*     */     }
/* 192 */     this.children.add(child);
/* 193 */     child.setLevel(this.level + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 200 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/* 207 */     this.level = level;
/* 208 */     if (this.children != null) {
/* 209 */       for (int i = 0; i < this.children.size(); i++) {
/* 210 */         Dnode child = this.children.get(i);
/* 211 */         child.setLevel(level + 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dnode find(String nodeName) {
/* 221 */     return find(nodeName, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dnode find(String nodeName, String attrName, Object value) {
/* 230 */     return find(nodeName, attrName, value, -1);
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
/*     */   public Dnode find(String nodeName, String attrName, Object value, int maxLevel) {
/* 242 */     ArrayList<Dnode> list = new ArrayList<Dnode>();
/* 243 */     findByNode(list, nodeName, true, attrName, value, maxLevel);
/* 244 */     if (list.size() >= 1) {
/* 245 */       return list.get(0);
/*     */     }
/* 247 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Dnode> findAll(String nodeName, int maxLevel) {
/* 255 */     int level = -1;
/* 256 */     if (maxLevel > 0) {
/* 257 */       level = this.level + maxLevel;
/*     */     }
/* 259 */     return findAll(nodeName, null, null, level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Dnode> findAll(String nodeName, String attrName, Object value, int maxLevel) {
/* 267 */     if (nodeName == null && attrName == null) {
/* 268 */       throw new RuntimeException("You can not have both nodeName and attrName null");
/*     */     }
/* 270 */     ArrayList<Dnode> list = new ArrayList<Dnode>();
/* 271 */     findByNode(list, nodeName, false, attrName, value, maxLevel);
/* 272 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findByNode(List<Dnode> list, String node, boolean findOne, String attrName, Object value, int maxLevel) {
/* 280 */     if (findOne && list.size() == 1) {
/*     */       return;
/*     */     }
/* 283 */     if ((node == null || node.equals(this.nodeName)) && (
/* 284 */       attrName == null || value.equals(getAttribute(attrName)))) {
/* 285 */       list.add(this);
/* 286 */       if (findOne) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     if (maxLevel <= 0 || this.level < maxLevel)
/*     */     {
/*     */       
/* 294 */       if (this.children != null)
/*     */       {
/* 296 */         for (int i = 0; i < this.children.size(); i++) {
/* 297 */           Dnode child = this.children.get(i);
/* 298 */           child.findByNode(list, node, findOne, attrName, value, maxLevel);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<String> attributeNames() {
/* 307 */     return this.attrList.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String name) {
/* 314 */     return this.attrList.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringAttr(String name, String defaultValue) {
/* 324 */     Object o = this.attrList.get(name);
/* 325 */     if (o == null) {
/* 326 */       return defaultValue;
/*     */     }
/* 328 */     return o.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String name, String value) {
/* 336 */     this.attrList.put(name, value);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 340 */     StringBuilder sb = new StringBuilder();
/* 341 */     sb.append("[").append(getNodeName()).append(" ").append(this.attrList).append("]");
/* 342 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\li\\util\Dnode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */