/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
/*     */ import com.avaje.ebean.text.json.JsonWriteOptions;
/*     */ import com.avaje.ebean.text.json.JsonWriter;
/*     */ import com.avaje.ebeaninternal.server.type.EscapeJson;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.util.ArrayStack;
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
/*     */ public class WriteJsonContext
/*     */   implements JsonWriter
/*     */ {
/*     */   private final WriteJsonBuffer buffer;
/*     */   private final boolean pretty;
/*     */   private final JsonValueAdapter valueAdapter;
/*  45 */   private final ArrayStack<Object> parentBeans = new ArrayStack();
/*     */ 
/*     */   
/*     */   private final PathProperties pathProperties;
/*     */   
/*     */   private final Map<String, JsonWriteBeanVisitor<?>> visitorMap;
/*     */   
/*     */   private final String callback;
/*     */   
/*     */   private final PathStack pathStack;
/*     */   
/*     */   private WriteBeanState beanState;
/*     */   
/*     */   private int depthOffset;
/*     */   
/*     */   boolean assocOne;
/*     */ 
/*     */   
/*     */   public WriteJsonContext(WriteJsonBuffer buffer, boolean pretty, JsonValueAdapter dfltValueAdapter, JsonWriteOptions options, String requestCallback) {
/*  64 */     this.buffer = buffer;
/*  65 */     this.pretty = pretty;
/*  66 */     this.pathStack = new PathStack();
/*  67 */     this.callback = getCallback(requestCallback, options);
/*  68 */     if (options == null) {
/*  69 */       this.valueAdapter = dfltValueAdapter;
/*  70 */       this.visitorMap = null;
/*  71 */       this.pathProperties = null;
/*     */     } else {
/*     */       
/*  74 */       this.valueAdapter = getValueAdapter(dfltValueAdapter, options.getValueAdapter());
/*  75 */       this.visitorMap = emptyToNull(options.getVisitorMap());
/*  76 */       this.pathProperties = emptyToNull(options.getPathProperties());
/*     */     } 
/*     */     
/*  79 */     if (this.callback != null) {
/*  80 */       buffer.append(requestCallback).append("(");
/*     */     }
/*     */   }
/*     */   
/*     */   public void appendRawValue(String key, String rawJsonValue) {
/*  85 */     appendKeyWithComma(key, true);
/*  86 */     this.buffer.append(rawJsonValue);
/*     */   }
/*     */   
/*     */   public void appendQuoteEscapeValue(String key, String valueToEscape) {
/*  90 */     appendKeyWithComma(key, true);
/*  91 */     EscapeJson.escapeQuote(valueToEscape, this.buffer);
/*     */   }
/*     */   
/*     */   public void end() {
/*  95 */     if (this.callback != null) {
/*  96 */       this.buffer.append(")");
/*     */     }
/*     */   }
/*     */   
/*     */   private <MK, MV> Map<MK, MV> emptyToNull(Map<MK, MV> m) {
/* 101 */     if (m == null || m.isEmpty()) {
/* 102 */       return null;
/*     */     }
/* 104 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathProperties emptyToNull(PathProperties m) {
/* 109 */     if (m == null || m.isEmpty()) {
/* 110 */       return null;
/*     */     }
/* 112 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getCallback(String requestCallback, JsonWriteOptions options) {
/* 117 */     if (requestCallback != null) {
/* 118 */       return requestCallback;
/*     */     }
/* 120 */     if (options != null) {
/* 121 */       return options.getCallback();
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   private JsonValueAdapter getValueAdapter(JsonValueAdapter dfltValueAdapter, JsonValueAdapter valueAdapter) {
/* 127 */     return (valueAdapter == null) ? dfltValueAdapter : valueAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIncludeProperties() {
/* 135 */     if (this.pathProperties != null) {
/* 136 */       String path = (String)this.pathStack.peekWithNull();
/* 137 */       return this.pathProperties.get(path);
/*     */     } 
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   public JsonWriteBeanVisitor<?> getBeanVisitor() {
/* 143 */     if (this.visitorMap != null) {
/* 144 */       String path = (String)this.pathStack.peekWithNull();
/* 145 */       return this.visitorMap.get(path);
/*     */     } 
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   public String getJson() {
/* 151 */     return this.buffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendIndent() {
/* 156 */     this.buffer.append("\n");
/* 157 */     int depth = this.depthOffset + this.parentBeans.size();
/* 158 */     for (int i = 0; i < depth; i++) {
/* 159 */       this.buffer.append("    ");
/*     */     }
/*     */   }
/*     */   
/*     */   public void appendObjectBegin() {
/* 164 */     if (this.pretty && !this.assocOne) {
/* 165 */       appendIndent();
/*     */     }
/* 167 */     this.buffer.append("{");
/*     */   }
/*     */   public void appendObjectEnd() {
/* 170 */     this.buffer.append("}");
/*     */   }
/*     */   
/*     */   public void appendArrayBegin() {
/* 174 */     if (this.pretty) {
/* 175 */       appendIndent();
/*     */     }
/* 177 */     this.buffer.append("[");
/* 178 */     this.depthOffset++;
/*     */   }
/*     */   
/*     */   public void appendArrayEnd() {
/* 182 */     this.depthOffset--;
/* 183 */     if (this.pretty) {
/* 184 */       appendIndent();
/*     */     }
/* 186 */     this.buffer.append("]");
/*     */   }
/*     */   
/*     */   public void appendComma() {
/* 190 */     this.buffer.append(",");
/*     */   }
/*     */   
/*     */   public void addDepthOffset(int offset) {
/* 194 */     this.depthOffset += offset;
/*     */   }
/*     */   
/*     */   public void beginAssocOneIsNull(String key) {
/* 198 */     this.depthOffset++;
/* 199 */     internalAppendKeyBegin(key);
/* 200 */     appendNull();
/* 201 */     this.depthOffset--;
/*     */   }
/*     */   
/*     */   public void beginAssocOne(String key) {
/* 205 */     this.pathStack.pushPathKey(key);
/*     */     
/* 207 */     internalAppendKeyBegin(key);
/* 208 */     this.assocOne = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endAssocOne() {
/* 213 */     this.pathStack.pop();
/* 214 */     this.assocOne = false;
/*     */   }
/*     */   
/*     */   public Boolean includeMany(String key) {
/* 218 */     if (this.pathProperties != null) {
/* 219 */       String fullPath = this.pathStack.peekFullPath(key);
/* 220 */       return Boolean.valueOf(this.pathProperties.hasPath(fullPath));
/*     */     } 
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginAssocMany(String key) {
/* 227 */     this.pathStack.pushPathKey(key);
/*     */     
/* 229 */     this.depthOffset--;
/* 230 */     internalAppendKeyBegin(key);
/* 231 */     this.depthOffset++;
/* 232 */     this.buffer.append("[");
/*     */   }
/*     */ 
/*     */   
/*     */   public void endAssocMany() {
/* 237 */     this.pathStack.pop();
/*     */     
/* 239 */     if (this.pretty) {
/* 240 */       this.depthOffset--;
/* 241 */       appendIndent();
/* 242 */       this.depthOffset++;
/*     */     } 
/* 244 */     this.buffer.append("]");
/*     */   }
/*     */   
/*     */   private void internalAppendKeyBegin(String key) {
/* 248 */     if (!this.beanState.isFirstKey()) {
/* 249 */       this.buffer.append(",");
/*     */     }
/* 251 */     if (this.pretty) {
/* 252 */       appendIndent();
/*     */     }
/* 254 */     appendKeyWithComma(key, false);
/*     */   }
/*     */   
/*     */   public <T> void appendNameValue(String key, ScalarType<T> scalarType, T value) {
/* 258 */     appendKeyWithComma(key, true);
/* 259 */     scalarType.jsonWrite(this.buffer, value, getValueAdapter());
/*     */   }
/*     */   
/*     */   public void appendDiscriminator(String key, String discValue) {
/* 263 */     appendKeyWithComma(key, true);
/* 264 */     this.buffer.append("\"");
/* 265 */     this.buffer.append(discValue);
/* 266 */     this.buffer.append("\"");
/*     */   }
/*     */   
/*     */   private void appendKeyWithComma(String key, boolean withComma) {
/* 270 */     if (withComma && 
/* 271 */       !this.beanState.isFirstKey()) {
/* 272 */       this.buffer.append(",");
/*     */     }
/*     */     
/* 275 */     this.buffer.append("\"");
/* 276 */     if (key == null) {
/* 277 */       this.buffer.append("null");
/*     */     } else {
/* 279 */       this.buffer.append(key);
/*     */     } 
/* 281 */     this.buffer.append("\":");
/*     */   }
/*     */   
/*     */   public void appendNull(String key) {
/* 285 */     appendKeyWithComma(key, true);
/* 286 */     this.buffer.append("null");
/*     */   }
/*     */   
/*     */   public void appendNull() {
/* 290 */     this.buffer.append("null");
/*     */   }
/*     */   
/*     */   public JsonValueAdapter getValueAdapter() {
/* 294 */     return this.valueAdapter;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 298 */     return this.buffer.toString();
/*     */   }
/*     */   
/*     */   public void popParentBean() {
/* 302 */     this.parentBeans.pop();
/*     */   }
/*     */   
/*     */   public void pushParentBean(Object parentBean) {
/* 306 */     this.parentBeans.push(parentBean);
/*     */   }
/*     */   
/*     */   public void popParentBeanMany() {
/* 310 */     this.parentBeans.pop();
/* 311 */     this.depthOffset--;
/*     */   }
/*     */   
/*     */   public void pushParentBeanMany(Object parentBean) {
/* 315 */     this.parentBeans.push(parentBean);
/* 316 */     this.depthOffset++;
/*     */   }
/*     */   
/*     */   public boolean isParentBean(Object bean) {
/* 320 */     if (this.parentBeans.isEmpty()) {
/* 321 */       return false;
/*     */     }
/* 323 */     return this.parentBeans.contains(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public WriteBeanState pushBeanState(Object bean) {
/* 328 */     WriteBeanState newState = new WriteBeanState(bean);
/* 329 */     WriteBeanState prevState = this.beanState;
/* 330 */     this.beanState = newState;
/* 331 */     return prevState;
/*     */   }
/*     */   
/*     */   public void pushPreviousState(WriteBeanState previousState) {
/* 335 */     this.beanState = previousState;
/*     */   }
/*     */   
/*     */   public boolean isReferenceBean() {
/* 339 */     return this.beanState.isReferenceBean();
/*     */   }
/*     */   
/*     */   public boolean includedProp(String name) {
/* 343 */     return this.beanState.includedProp(name);
/*     */   }
/*     */   
/*     */   public Set<String> getLoadedProps() {
/* 347 */     return this.beanState.getLoadedProps();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WriteBeanState
/*     */   {
/*     */     private final EntityBeanIntercept ebi;
/*     */     private final Set<String> loadedProps;
/*     */     private final boolean referenceBean;
/*     */     private boolean firstKeyOut;
/*     */     
/*     */     public WriteBeanState(Object bean) {
/* 359 */       if (bean instanceof EntityBean) {
/* 360 */         this.ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 361 */         this.loadedProps = this.ebi.getLoadedProps();
/* 362 */         this.referenceBean = this.ebi.isReference();
/*     */       } else {
/* 364 */         this.ebi = null;
/* 365 */         this.loadedProps = null;
/* 366 */         this.referenceBean = false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Set<String> getLoadedProps() {
/* 371 */       return this.loadedProps;
/*     */     }
/*     */     
/*     */     public boolean includedProp(String name) {
/* 375 */       if (this.loadedProps == null || this.loadedProps.contains(name)) {
/* 376 */         return true;
/*     */       }
/* 378 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isReferenceBean() {
/* 382 */       return this.referenceBean;
/*     */     }
/*     */     
/*     */     public boolean isFirstKey() {
/* 386 */       if (!this.firstKeyOut) {
/* 387 */         this.firstKeyOut = true;
/* 388 */         return true;
/*     */       } 
/* 390 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\WriteJsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */