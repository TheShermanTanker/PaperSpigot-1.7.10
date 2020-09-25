/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.config.CompoundType;
/*     */ import com.avaje.ebean.config.CompoundTypeProperty;
/*     */ import com.avaje.ebean.text.json.JsonElement;
/*     */ import com.avaje.ebean.text.json.JsonElementObject;
/*     */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CtCompoundType<V>
/*     */   implements ScalarDataReader<V>
/*     */ {
/*     */   private final Class<V> cvoClass;
/*     */   private final CompoundType<V> cvoType;
/*     */   private final Map<String, CompoundTypeProperty<V, ?>> propertyMap;
/*     */   private final ScalarDataReader<Object>[] propReaders;
/*     */   private final CompoundTypeProperty<V, ?>[] properties;
/*     */   
/*     */   public CtCompoundType(Class<V> cvoClass, CompoundType<V> cvoType, ScalarDataReader<Object>[] propReaders) {
/*  57 */     this.cvoClass = cvoClass;
/*  58 */     this.cvoType = cvoType;
/*  59 */     this.properties = (CompoundTypeProperty<V, ?>[])cvoType.getProperties();
/*  60 */     this.propReaders = propReaders;
/*     */     
/*  62 */     this.propertyMap = new LinkedHashMap<String, CompoundTypeProperty<V, ?>>();
/*  63 */     for (CompoundTypeProperty<V, ?> cp : this.properties) {
/*  64 */       this.propertyMap.put(cp.getName(), cp);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/*  69 */     return this.cvoClass.toString();
/*     */   }
/*     */   
/*     */   public Class<V> getCompoundTypeClass() {
/*  73 */     return this.cvoClass;
/*     */   }
/*     */   
/*     */   public V create(Object[] propertyValues) {
/*  77 */     return (V)this.cvoType.create(propertyValues);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V create(Map<String, Object> valueMap) {
/*  83 */     if (valueMap.size() != this.properties.length)
/*     */     {
/*  85 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     Object[] propertyValues = new Object[this.properties.length];
/*  92 */     for (int i = 0; i < this.properties.length; i++) {
/*  93 */       propertyValues[i] = valueMap.get(this.properties[i].getName());
/*  94 */       if (propertyValues[i] == null) {
/*  95 */         String m = "Null value for " + this.properties[i].getName() + " in map " + valueMap;
/*  96 */         throw new RuntimeException(m);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return create(propertyValues);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTypeProperty<V, ?>[] getProperties() {
/* 105 */     return (CompoundTypeProperty<V, ?>[])this.cvoType.getProperties();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getPropertyValues(V valueObject) {
/* 110 */     Object[] values = new Object[this.properties.length];
/* 111 */     for (int i = 0; i < this.properties.length; i++) {
/* 112 */       values[i] = this.properties[i].getValue(valueObject);
/*     */     }
/* 114 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public V read(DataReader source) throws SQLException {
/* 119 */     boolean nullValue = false;
/* 120 */     Object[] values = new Object[this.propReaders.length];
/*     */     
/* 122 */     for (int i = 0; i < this.propReaders.length; i++) {
/* 123 */       Object o = this.propReaders[i].read(source);
/* 124 */       values[i] = o;
/* 125 */       if (o == null) {
/* 126 */         nullValue = true;
/*     */       }
/*     */     } 
/*     */     
/* 130 */     if (nullValue) {
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     return create(values);
/*     */   }
/*     */   
/*     */   public void loadIgnore(DataReader dataReader) {
/* 138 */     for (int i = 0; i < this.propReaders.length; i++) {
/* 139 */       this.propReaders[i].loadIgnore(dataReader);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(DataBind b, V value) throws SQLException {
/* 145 */     CompoundTypeProperty[] arrayOfCompoundTypeProperty = this.cvoType.getProperties();
/*     */     
/* 147 */     for (int i = 0; i < arrayOfCompoundTypeProperty.length; i++) {
/* 148 */       Object o = arrayOfCompoundTypeProperty[i].getValue(value);
/* 149 */       this.propReaders[i].bind(b, o);
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
/*     */   public void accumulateScalarTypes(String parent, CtCompoundTypeScalarList list) {
/* 162 */     CompoundTypeProperty[] arrayOfCompoundTypeProperty = this.cvoType.getProperties();
/*     */     
/* 164 */     for (int i = 0; i < this.propReaders.length; i++) {
/* 165 */       String propName = getFullPropName(parent, arrayOfCompoundTypeProperty[i].getName());
/*     */       
/* 167 */       list.addCompoundProperty(propName, this, arrayOfCompoundTypeProperty[i]);
/*     */       
/* 169 */       this.propReaders[i].accumulateScalarTypes(propName, list);
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
/*     */   private String getFullPropName(String parent, String propName) {
/* 184 */     if (parent == null) {
/* 185 */       return propName;
/*     */     }
/* 187 */     return parent + "." + propName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object jsonRead(ReadJsonContext ctx) {
/* 193 */     if (!ctx.readObjectBegin())
/*     */     {
/* 195 */       return null;
/*     */     }
/*     */     
/* 198 */     JsonElementObject jsonObject = new JsonElementObject();
/*     */     
/* 200 */     while (ctx.readKeyNext()) {
/*     */ 
/*     */ 
/*     */       
/* 204 */       String propName = ctx.getTokenKey();
/* 205 */       JsonElement unmappedJson = ctx.readUnmappedJson(propName);
/* 206 */       jsonObject.put(propName, unmappedJson);
/*     */       
/* 208 */       if (!ctx.readValueNext()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 214 */     return readJsonElementObject(ctx, jsonObject);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object readJsonElementObject(ReadJsonContext ctx, JsonElementObject jsonObject) {
/* 219 */     boolean nullValue = false;
/* 220 */     Object[] values = new Object[this.propReaders.length];
/*     */     
/* 222 */     for (int i = 0; i < this.propReaders.length; i++) {
/* 223 */       String propName = this.properties[i].getName();
/* 224 */       JsonElement jsonElement = jsonObject.get(propName);
/*     */       
/* 226 */       if (this.propReaders[i] instanceof CtCompoundType) {
/* 227 */         values[i] = ((CtCompoundType)this.propReaders[i]).readJsonElementObject(ctx, (JsonElementObject)jsonElement);
/*     */       } else {
/*     */         
/* 230 */         values[i] = ((ScalarType)this.propReaders[i]).jsonFromString(jsonElement.toPrimitiveString(), ctx.getValueAdapter());
/*     */       } 
/* 232 */       if (values[i] == null) {
/* 233 */         nullValue = true;
/*     */       }
/*     */     } 
/*     */     
/* 237 */     if (nullValue) {
/* 238 */       return null;
/*     */     }
/*     */     
/* 241 */     return create(values);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, Object valueObject, String propertyName) {
/* 247 */     if (valueObject == null) {
/* 248 */       ctx.beginAssocOneIsNull(propertyName);
/*     */     } else {
/*     */       
/* 251 */       ctx.pushParentBean(valueObject);
/* 252 */       ctx.beginAssocOne(propertyName);
/* 253 */       jsonWriteProps(ctx, valueObject, propertyName);
/* 254 */       ctx.endAssocOne();
/* 255 */       ctx.popParentBean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void jsonWriteProps(WriteJsonContext ctx, Object valueObject, String propertyName) {
/* 263 */     ctx.appendObjectBegin();
/* 264 */     WriteJsonContext.WriteBeanState prevState = ctx.pushBeanState(valueObject);
/*     */     
/* 266 */     for (int i = 0; i < this.properties.length; i++) {
/* 267 */       String propName = this.properties[i].getName();
/* 268 */       Object value = this.properties[i].getValue(valueObject);
/* 269 */       if (this.propReaders[i] instanceof CtCompoundType) {
/* 270 */         ((CtCompoundType)this.propReaders[i]).jsonWrite(ctx, value, propName);
/*     */       } else {
/*     */         
/* 273 */         ctx.appendNameValue(propName, (ScalarType)this.propReaders[i], value);
/*     */       } 
/*     */     } 
/*     */     
/* 277 */     ctx.pushPreviousState(prevState);
/* 278 */     ctx.appendObjectEnd();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\CtCompoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */