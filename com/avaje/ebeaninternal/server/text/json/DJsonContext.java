/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.json.JsonContext;
/*     */ import com.avaje.ebean.text.json.JsonElement;
/*     */ import com.avaje.ebean.text.json.JsonReadOptions;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebean.text.json.JsonWriteOptions;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.type.EscapeJson;
/*     */ import com.avaje.ebeaninternal.util.ParamTypeHelper;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class DJsonContext
/*     */   implements JsonContext
/*     */ {
/*     */   private final SpiEbeanServer server;
/*     */   private final JsonValueAdapter dfltValueAdapter;
/*     */   private final boolean dfltPretty;
/*     */   
/*     */   public DJsonContext(SpiEbeanServer server, JsonValueAdapter dfltValueAdapter, boolean dfltPretty) {
/*  60 */     this.server = server;
/*  61 */     this.dfltValueAdapter = dfltValueAdapter;
/*  62 */     this.dfltPretty = dfltPretty;
/*     */   }
/*     */   
/*     */   public boolean isSupportedType(Type genericType) {
/*  66 */     return this.server.isSupportedType(genericType);
/*     */   }
/*     */   
/*     */   private ReadJsonSource createReader(Reader jsonReader) {
/*  70 */     return new ReadJsonSourceReader(jsonReader, 256, 512);
/*     */   }
/*     */   
/*     */   public <T> T toBean(Class<T> cls, String json) {
/*  74 */     return toBean(cls, new ReadJsonSourceString(json), (JsonReadOptions)null);
/*     */   }
/*     */   
/*     */   public <T> T toBean(Class<T> cls, Reader jsonReader) {
/*  78 */     return toBean(cls, createReader(jsonReader), (JsonReadOptions)null);
/*     */   }
/*     */   
/*     */   public <T> T toBean(Class<T> cls, String json, JsonReadOptions options) {
/*  82 */     return toBean(cls, new ReadJsonSourceString(json), options);
/*     */   }
/*     */   
/*     */   public <T> T toBean(Class<T> cls, Reader jsonReader, JsonReadOptions options) {
/*  86 */     return toBean(cls, createReader(jsonReader), options);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> T toBean(Class<T> cls, ReadJsonSource src, JsonReadOptions options) {
/*  91 */     BeanDescriptor<T> d = getDecriptor(cls);
/*  92 */     ReadJsonContext ctx = new ReadJsonContext(src, this.dfltValueAdapter, options);
/*  93 */     return (T)d.jsonReadBean(ctx, null);
/*     */   }
/*     */   
/*     */   public <T> List<T> toList(Class<T> cls, String json) {
/*  97 */     return toList(cls, new ReadJsonSourceString(json), (JsonReadOptions)null);
/*     */   }
/*     */   
/*     */   public <T> List<T> toList(Class<T> cls, String json, JsonReadOptions options) {
/* 101 */     return toList(cls, new ReadJsonSourceString(json), options);
/*     */   }
/*     */   
/*     */   public <T> List<T> toList(Class<T> cls, Reader jsonReader) {
/* 105 */     return toList(cls, createReader(jsonReader), (JsonReadOptions)null);
/*     */   }
/*     */   
/*     */   public <T> List<T> toList(Class<T> cls, Reader jsonReader, JsonReadOptions options) {
/* 109 */     return toList(cls, createReader(jsonReader), options);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> List<T> toList(Class<T> cls, ReadJsonSource src, JsonReadOptions options) {
/*     */     try {
/* 115 */       BeanDescriptor<T> d = getDecriptor(cls);
/*     */       
/* 117 */       List<T> list = new ArrayList<T>();
/*     */       
/* 119 */       ReadJsonContext ctx = new ReadJsonContext(src, this.dfltValueAdapter, options);
/* 120 */       ctx.readArrayBegin();
/*     */       do {
/* 122 */         T bean = (T)d.jsonReadBean(ctx, null);
/* 123 */         if (bean == null)
/* 124 */           continue;  list.add(bean);
/*     */       }
/* 126 */       while (ctx.readArrayNext());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       return list;
/* 132 */     } catch (RuntimeException e) {
/* 133 */       throw new TextException("Error parsing " + src, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object toObject(Type genericType, String json, JsonReadOptions options) {
/* 140 */     ParamTypeHelper.TypeInfo info = ParamTypeHelper.getTypeInfo(genericType);
/* 141 */     Class<?> beanType = info.getBeanType();
/* 142 */     if (JsonElement.class.isAssignableFrom(beanType)) {
/* 143 */       return InternalJsonParser.parse(json);
/*     */     }
/*     */     
/* 146 */     ParamTypeHelper.ManyType manyType = info.getManyType();
/* 147 */     switch (manyType) {
/*     */       case NONE:
/* 149 */         return toBean(info.getBeanType(), json, options);
/*     */       
/*     */       case LIST:
/* 152 */         return toList(info.getBeanType(), json, options);
/*     */     } 
/*     */     
/* 155 */     String msg = "ManyType " + manyType + " not supported yet";
/* 156 */     throw new TextException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object toObject(Type genericType, Reader json, JsonReadOptions options) {
/* 162 */     ParamTypeHelper.TypeInfo info = ParamTypeHelper.getTypeInfo(genericType);
/* 163 */     Class<?> beanType = info.getBeanType();
/* 164 */     if (JsonElement.class.isAssignableFrom(beanType)) {
/* 165 */       return InternalJsonParser.parse(json);
/*     */     }
/*     */     
/* 168 */     ParamTypeHelper.ManyType manyType = info.getManyType();
/* 169 */     switch (manyType) {
/*     */       case NONE:
/* 171 */         return toBean(info.getBeanType(), json, options);
/*     */       
/*     */       case LIST:
/* 174 */         return toList(info.getBeanType(), json, options);
/*     */     } 
/*     */     
/* 177 */     String msg = "ManyType " + manyType + " not supported yet";
/* 178 */     throw new TextException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toJsonWriter(Object o, Writer writer) {
/* 184 */     toJsonWriter(o, writer, this.dfltPretty, null, null);
/*     */   }
/*     */   
/*     */   public void toJsonWriter(Object o, Writer writer, boolean pretty) {
/* 188 */     toJsonWriter(o, writer, pretty, null, null);
/*     */   }
/*     */   
/*     */   public void toJsonWriter(Object o, Writer writer, boolean pretty, JsonWriteOptions options) {
/* 192 */     toJsonWriter(o, writer, pretty, null, null);
/*     */   }
/*     */   
/*     */   public void toJsonWriter(Object o, Writer writer, boolean pretty, JsonWriteOptions options, String callback) {
/* 196 */     toJsonInternal(o, new WriteJsonBufferWriter(writer), pretty, options, callback);
/*     */   }
/*     */   
/*     */   public String toJsonString(Object o) {
/* 200 */     return toJsonString(o, this.dfltPretty, null);
/*     */   }
/*     */   
/*     */   public String toJsonString(Object o, boolean pretty) {
/* 204 */     return toJsonString(o, pretty, null);
/*     */   }
/*     */   
/*     */   public String toJsonString(Object o, boolean pretty, JsonWriteOptions options) {
/* 208 */     return toJsonString(o, pretty, options, null);
/*     */   }
/*     */   
/*     */   public String toJsonString(Object o, boolean pretty, JsonWriteOptions options, String callback) {
/* 212 */     WriteJsonBufferString b = new WriteJsonBufferString();
/* 213 */     toJsonInternal(o, b, pretty, options, callback);
/* 214 */     return b.getBufferOutput();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void toJsonInternal(Object o, WriteJsonBuffer buffer, boolean pretty, JsonWriteOptions options, String requestCallback) {
/* 220 */     if (o == null) {
/* 221 */       buffer.append("null");
/* 222 */     } else if (o instanceof Number) {
/* 223 */       buffer.append(o.toString());
/* 224 */     } else if (o instanceof Boolean) {
/* 225 */       buffer.append(o.toString());
/* 226 */     } else if (o instanceof String) {
/* 227 */       EscapeJson.escapeQuote(o.toString(), buffer);
/* 228 */     } else if (!(o instanceof JsonElement)) {
/*     */       
/* 230 */       if (o instanceof Map) {
/* 231 */         toJsonFromMap((Map<Object, Object>)o, buffer, pretty, options, requestCallback);
/*     */       }
/* 233 */       else if (o instanceof Collection) {
/* 234 */         toJsonFromCollection((Collection)o, buffer, pretty, options, requestCallback);
/*     */       } else {
/*     */         
/* 237 */         BeanDescriptor<?> d = getDecriptor(o.getClass());
/* 238 */         WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
/* 239 */         d.jsonWrite(ctx, o);
/* 240 */         ctx.end();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void toJsonFromCollection(Collection<T> c, WriteJsonBuffer buffer, boolean pretty, JsonWriteOptions options, String requestCallback) {
/* 248 */     Iterator<T> it = c.iterator();
/* 249 */     if (!it.hasNext()) {
/* 250 */       buffer.append("[]");
/*     */       
/*     */       return;
/*     */     } 
/* 254 */     WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
/*     */     
/* 256 */     Object o = it.next();
/* 257 */     BeanDescriptor<?> d = getDecriptor(o.getClass());
/*     */     
/* 259 */     ctx.appendArrayBegin();
/* 260 */     d.jsonWrite(ctx, o);
/* 261 */     while (it.hasNext()) {
/* 262 */       ctx.appendComma();
/* 263 */       T t = it.next();
/* 264 */       d.jsonWrite(ctx, t);
/*     */     } 
/* 266 */     ctx.appendArrayEnd();
/* 267 */     ctx.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void toJsonFromMap(Map<Object, Object> map, WriteJsonBuffer buffer, boolean pretty, JsonWriteOptions options, String requestCallback) {
/* 273 */     if (map.isEmpty()) {
/* 274 */       buffer.append("{}");
/*     */       
/*     */       return;
/*     */     } 
/* 278 */     WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
/*     */     
/* 280 */     Set<Map.Entry<Object, Object>> entrySet = map.entrySet();
/* 281 */     Iterator<Map.Entry<Object, Object>> it = entrySet.iterator();
/*     */     
/* 283 */     Map.Entry<Object, Object> entry = it.next();
/*     */     
/* 285 */     ctx.appendObjectBegin();
/* 286 */     toJsonMapKey(buffer, false, entry.getKey());
/* 287 */     toJsonMapValue(buffer, pretty, options, requestCallback, entry.getValue());
/*     */     
/* 289 */     while (it.hasNext()) {
/* 290 */       entry = it.next();
/* 291 */       ctx.appendComma();
/* 292 */       toJsonMapKey(buffer, pretty, entry.getKey());
/* 293 */       toJsonMapValue(buffer, pretty, options, requestCallback, entry.getValue());
/*     */     } 
/* 295 */     ctx.appendObjectEnd();
/* 296 */     ctx.end();
/*     */   }
/*     */   
/*     */   private void toJsonMapKey(WriteJsonBuffer buffer, boolean pretty, Object key) {
/* 300 */     if (pretty) {
/* 301 */       buffer.append("\n");
/*     */     }
/* 303 */     buffer.append("\"");
/* 304 */     buffer.append(key.toString());
/* 305 */     buffer.append("\":");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void toJsonMapValue(WriteJsonBuffer buffer, boolean pretty, JsonWriteOptions options, String requestCallback, Object value) {
/* 311 */     if (value == null) {
/* 312 */       buffer.append("null");
/*     */     } else {
/* 314 */       toJsonInternal(value, buffer, pretty, options, requestCallback);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> BeanDescriptor<T> getDecriptor(Class<T> cls) {
/* 319 */     BeanDescriptor<T> d = this.server.getBeanDescriptor(cls);
/* 320 */     if (d == null) {
/* 321 */       String msg = "No BeanDescriptor found for " + cls;
/* 322 */       throw new RuntimeException(msg);
/*     */     } 
/* 324 */     return d;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\DJsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */