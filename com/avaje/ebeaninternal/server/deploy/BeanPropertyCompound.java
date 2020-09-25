/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*     */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundPropertyElAdapter;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundType;
/*     */ import java.sql.SQLException;
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
/*     */ public class BeanPropertyCompound
/*     */   extends BeanProperty
/*     */ {
/*     */   private final CtCompoundType<?> compoundType;
/*     */   private final ScalarTypeConverter typeConverter;
/*     */   private final BeanProperty[] scalarProperties;
/*  56 */   private final LinkedHashMap<String, BeanProperty> propertyMap = new LinkedHashMap<String, BeanProperty>();
/*     */   
/*  58 */   private final LinkedHashMap<String, CtCompoundPropertyElAdapter> nonScalarMap = new LinkedHashMap<String, CtCompoundPropertyElAdapter>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final BeanPropertyCompoundRoot root;
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyCompound(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyCompound deploy) {
/*  67 */     super(owner, descriptor, (DeployBeanProperty)deploy);
/*     */     
/*  69 */     this.compoundType = deploy.getCompoundType();
/*  70 */     this.typeConverter = deploy.getTypeConverter();
/*     */     
/*  72 */     this.root = deploy.getFlatProperties(owner, descriptor);
/*     */     
/*  74 */     this.scalarProperties = this.root.getScalarProperties();
/*     */     
/*  76 */     for (int i = 0; i < this.scalarProperties.length; i++) {
/*  77 */       this.propertyMap.put(this.scalarProperties[i].getName(), this.scalarProperties[i]);
/*     */     }
/*     */     
/*  80 */     List<CtCompoundProperty> nonScalarPropsList = this.root.getNonScalarProperties();
/*     */     
/*  82 */     for (int j = 0; j < nonScalarPropsList.size(); j++) {
/*  83 */       CtCompoundProperty ctProp = nonScalarPropsList.get(j);
/*  84 */       CtCompoundPropertyElAdapter adapter = new CtCompoundPropertyElAdapter(ctProp);
/*  85 */       this.nonScalarMap.put(ctProp.getRelativeName(), adapter);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {
/*  93 */     if (!this.isTransient && this.compoundType == null) {
/*  94 */       String msg = "No cvoInternalType assigned to " + this.descriptor.getFullName() + "." + getName();
/*  95 */       throw new RuntimeException(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDeployOrder(int deployOrder) {
/* 101 */     this.deployOrder = deployOrder;
/* 102 */     for (CtCompoundPropertyElAdapter adapter : this.nonScalarMap.values()) {
/* 103 */       adapter.setDeployOrder(deployOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValueUnderlying(Object bean) {
/* 113 */     Object value = getValue(bean);
/* 114 */     if (this.typeConverter != null) {
/* 115 */       value = this.typeConverter.unwrapValue(value);
/*     */     }
/* 117 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue(Object bean) {
/* 122 */     return super.getValue(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValueIntercept(Object bean) {
/* 127 */     return super.getValueIntercept(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(Object bean, Object value) {
/* 132 */     super.setValue(bean, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueIntercept(Object bean, Object value) {
/* 137 */     super.setValueIntercept(bean, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 142 */     if (chain == null) {
/* 143 */       chain = new ElPropertyChainBuilder(true, propName);
/*     */     }
/*     */ 
/*     */     
/* 147 */     chain.add(this);
/*     */ 
/*     */ 
/*     */     
/* 151 */     BeanProperty p = this.propertyMap.get(remainder);
/* 152 */     if (p != null) {
/* 153 */       return (ElPropertyValue)chain.add(p).build();
/*     */     }
/* 155 */     CtCompoundPropertyElAdapter elAdapter = this.nonScalarMap.get(remainder);
/* 156 */     if (elAdapter == null) {
/* 157 */       throw new RuntimeException("property [" + remainder + "] not found in " + getFullBeanName());
/*     */     }
/* 159 */     return (ElPropertyValue)chain.add((ElPropertyValue)elAdapter).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 164 */     if (!this.isTransient) {
/* 165 */       for (int i = 0; i < this.scalarProperties.length; i++) {
/* 166 */         this.scalarProperties[i].appendSelect(ctx, subQuery);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public BeanProperty[] getScalarProperties() {
/* 172 */     return this.scalarProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/* 178 */     boolean assignable = (type == null || this.owningType.isAssignableFrom(type));
/*     */     
/* 180 */     Object v = this.compoundType.read(ctx.getDataReader());
/* 181 */     if (assignable) {
/* 182 */       setValue(bean, v);
/*     */     }
/*     */     
/* 185 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read(DbReadContext ctx) throws SQLException {
/* 196 */     Object v = this.compoundType.read(ctx.getDataReader());
/* 197 */     if (this.typeConverter != null) {
/* 198 */       v = this.typeConverter.wrapValue(v);
/*     */     }
/* 200 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {
/* 205 */     this.compoundType.loadIgnore(ctx.getDataReader());
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
/* 210 */     sqlBeanLoad.load(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 215 */     return bean;
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 220 */     Object valueObject = getValueIntercept(bean);
/* 221 */     this.compoundType.jsonWrite(ctx, valueObject, this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/* 226 */     Object objValue = this.compoundType.jsonRead(ctx);
/* 227 */     setValue(bean, objValue);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyCompound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */