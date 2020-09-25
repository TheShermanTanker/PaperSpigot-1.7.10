/*     */ package com.avaje.ebean.text;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathProperties
/*     */ {
/*     */   private final Map<String, Props> pathMap;
/*     */   private final Props rootProps;
/*     */   
/*     */   public static PathProperties parse(String source) {
/*  57 */     return PathPropertiesParser.parse(source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathProperties() {
/*  64 */     this.rootProps = new Props(this, null, null);
/*  65 */     this.pathMap = new LinkedHashMap<String, Props>();
/*  66 */     this.pathMap.put(null, this.rootProps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathProperties(PathProperties orig) {
/*  73 */     this.rootProps = orig.rootProps.copy(this);
/*  74 */     this.pathMap = new LinkedHashMap<String, Props>(orig.pathMap.size());
/*  75 */     Set<Map.Entry<String, Props>> entrySet = orig.pathMap.entrySet();
/*  76 */     for (Map.Entry<String, Props> e : entrySet) {
/*  77 */       this.pathMap.put(e.getKey(), ((Props)e.getValue()).copy(this));
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
/*     */   public PathProperties copy() {
/*  92 */     return new PathProperties(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  99 */     return this.pathMap.isEmpty();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     return this.pathMap.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPath(String path) {
/* 110 */     Props props = this.pathMap.get(path);
/* 111 */     return (props != null && !props.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> get(String path) {
/* 118 */     Props props = this.pathMap.get(path);
/* 119 */     return (props == null) ? null : props.getProperties();
/*     */   }
/*     */   
/*     */   public void addToPath(String path, String property) {
/* 123 */     Props props = this.pathMap.get(path);
/* 124 */     if (props == null) {
/* 125 */       props = new Props(this, null, path);
/* 126 */       this.pathMap.put(path, props);
/*     */     } 
/* 128 */     props.getProperties().add(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String path, Set<String> properties) {
/* 135 */     this.pathMap.put(path, new Props(this, null, path, properties));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> remove(String path) {
/* 142 */     Props props = this.pathMap.remove(path);
/* 143 */     return (props == null) ? null : props.getProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getPaths() {
/* 150 */     return new LinkedHashSet<String>(this.pathMap.keySet());
/*     */   }
/*     */   
/*     */   public Collection<Props> getPathProps() {
/* 154 */     return this.pathMap.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Query<?> query) {
/* 162 */     for (Map.Entry<String, Props> entry : this.pathMap.entrySet()) {
/* 163 */       String path = entry.getKey();
/* 164 */       String props = ((Props)entry.getValue()).getPropertiesAsString();
/*     */       
/* 166 */       if (path == null || path.length() == 0) {
/* 167 */         query.select(props); continue;
/*     */       } 
/* 169 */       query.fetch(path, props);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Props getRootProperties() {
/* 175 */     return this.rootProps;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Props
/*     */   {
/*     */     private final PathProperties owner;
/*     */     
/*     */     private final String parentPath;
/*     */     private final String path;
/*     */     private final Set<String> propSet;
/*     */     
/*     */     private Props(PathProperties owner, String parentPath, String path, Set<String> propSet) {
/* 188 */       this.owner = owner;
/* 189 */       this.path = path;
/* 190 */       this.parentPath = parentPath;
/* 191 */       this.propSet = propSet;
/*     */     }
/*     */     
/*     */     private Props(PathProperties owner, String parentPath, String path) {
/* 195 */       this(owner, parentPath, path, new LinkedHashSet<String>());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Props copy(PathProperties newOwner) {
/* 202 */       return new Props(newOwner, this.parentPath, this.path, new LinkedHashSet<String>(this.propSet));
/*     */     }
/*     */     
/*     */     public String getPath() {
/* 206 */       return this.path;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 210 */       return this.propSet.toString();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 214 */       return this.propSet.isEmpty();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> getProperties() {
/* 221 */       return this.propSet;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPropertiesAsString() {
/* 229 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 231 */       Iterator<String> it = this.propSet.iterator();
/* 232 */       boolean hasNext = it.hasNext();
/* 233 */       while (hasNext) {
/* 234 */         sb.append(it.next());
/* 235 */         hasNext = it.hasNext();
/* 236 */         if (hasNext) {
/* 237 */           sb.append(",");
/*     */         }
/*     */       } 
/* 240 */       return sb.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Props getParent() {
/* 247 */       return (Props)this.owner.pathMap.get(this.parentPath);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Props addChild(String subpath) {
/* 255 */       subpath = subpath.trim();
/* 256 */       addProperty(subpath);
/*     */ 
/*     */       
/* 259 */       String p = (this.path == null) ? subpath : (this.path + "." + subpath);
/* 260 */       Props nested = new Props(this.owner, this.path, p);
/* 261 */       this.owner.pathMap.put(p, nested);
/* 262 */       return nested;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void addProperty(String property) {
/* 269 */       this.propSet.add(property.trim());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\PathProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */