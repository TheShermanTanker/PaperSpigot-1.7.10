/*     */ package com.avaje.ebean.text.json;
/*     */ 
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonWriteOptions
/*     */ {
/*     */   protected String callback;
/*     */   protected JsonValueAdapter valueAdapter;
/*     */   protected Map<String, JsonWriteBeanVisitor<?>> visitorMap;
/*     */   protected PathProperties pathProperties;
/*     */   
/*     */   public static JsonWriteOptions parsePath(String pathProperties) {
/* 102 */     PathProperties p = PathProperties.parse(pathProperties);
/* 103 */     JsonWriteOptions o = new JsonWriteOptions();
/* 104 */     o.setPathProperties(p);
/* 105 */     return o;
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
/*     */   public JsonWriteOptions copy() {
/* 117 */     JsonWriteOptions copy = new JsonWriteOptions();
/* 118 */     copy.callback = this.callback;
/* 119 */     copy.valueAdapter = this.valueAdapter;
/* 120 */     copy.pathProperties = this.pathProperties;
/* 121 */     if (this.visitorMap != null) {
/* 122 */       copy.visitorMap = new HashMap<String, JsonWriteBeanVisitor<?>>(this.visitorMap);
/*     */     }
/* 124 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCallback() {
/* 131 */     return this.callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setCallback(String callback) {
/* 138 */     this.callback = callback;
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonValueAdapter getValueAdapter() {
/* 146 */     return this.valueAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setValueAdapter(JsonValueAdapter valueAdapter) {
/* 153 */     this.valueAdapter = valueAdapter;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setRootPathVisitor(JsonWriteBeanVisitor<?> visitor) {
/* 161 */     return setPathVisitor(null, visitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setPathVisitor(String path, JsonWriteBeanVisitor<?> visitor) {
/* 168 */     if (this.visitorMap == null) {
/* 169 */       this.visitorMap = new HashMap<String, JsonWriteBeanVisitor<?>>();
/*     */     }
/* 171 */     this.visitorMap.put(path, visitor);
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setPathProperties(String path, Set<String> propertiesToInclude) {
/* 182 */     if (this.pathProperties == null) {
/* 183 */       this.pathProperties = new PathProperties();
/*     */     }
/* 185 */     this.pathProperties.put(path, propertiesToInclude);
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setPathProperties(String path, String propertiesToInclude) {
/* 196 */     return setPathProperties(path, parseProps(propertiesToInclude));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setRootPathProperties(String propertiesToInclude) {
/* 206 */     return setPathProperties((String)null, parseProps(propertiesToInclude));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setRootPathProperties(Set<String> propertiesToInclude) {
/* 216 */     return setPathProperties((String)null, propertiesToInclude);
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<String> parseProps(String propertiesToInclude) {
/* 221 */     LinkedHashSet<String> props = new LinkedHashSet<String>();
/*     */     
/* 223 */     String[] split = propertiesToInclude.split(",");
/* 224 */     for (int i = 0; i < split.length; i++) {
/* 225 */       String s = split[i].trim();
/* 226 */       if (s.length() > 0) {
/* 227 */         props.add(s);
/*     */       }
/*     */     } 
/* 230 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, JsonWriteBeanVisitor<?>> getVisitorMap() {
/* 237 */     return this.visitorMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPathProperties(PathProperties pathProperties) {
/* 244 */     this.pathProperties = pathProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathProperties getPathProperties() {
/* 251 */     return this.pathProperties;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonWriteOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */