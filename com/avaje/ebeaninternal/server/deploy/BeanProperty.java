/*      */ package com.avaje.ebeaninternal.server.deploy;
/*      */ 
/*      */ import com.avaje.ebean.InvalidValue;
/*      */ import com.avaje.ebean.config.EncryptKey;
/*      */ import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
/*      */ import com.avaje.ebean.config.dbplatform.DbType;
/*      */ import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
/*      */ import com.avaje.ebean.text.StringFormatter;
/*      */ import com.avaje.ebean.text.StringParser;
/*      */ import com.avaje.ebean.text.TextException;
/*      */ import com.avaje.ebean.validation.factory.Validator;
/*      */ import com.avaje.ebeaninternal.server.core.InternString;
/*      */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*      */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*      */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*      */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*      */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*      */ import com.avaje.ebeaninternal.server.type.DataBind;
/*      */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*      */ import com.avaje.ebeaninternal.util.ValueUtil;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.BasicAttribute;
/*      */ import javax.persistence.PersistenceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BeanProperty
/*      */   implements ElPropertyValue
/*      */ {
/*      */   public static final String EXCLUDE_FROM_UPDATE_WHERE = "EXCLUDE_FROM_UPDATE_WHERE";
/*      */   public static final String EXCLUDE_FROM_DELETE_WHERE = "EXCLUDE_FROM_DELETE_WHERE";
/*      */   public static final String EXCLUDE_FROM_INSERT = "EXCLUDE_FROM_INSERT";
/*      */   public static final String EXCLUDE_FROM_UPDATE = "EXCLUDE_FROM_UPDATE";
/*      */   final boolean id;
/*      */   final boolean unidirectionalShadow;
/*      */   final boolean embedded;
/*      */   final boolean version;
/*      */   final boolean naturalKey;
/*      */   final boolean nullable;
/*      */   final boolean unique;
/*      */   final boolean dbRead;
/*      */   final boolean dbInsertable;
/*      */   final boolean dbUpdatable;
/*      */   final boolean secondaryTable;
/*      */   final TableJoin secondaryTableJoin;
/*      */   final String secondaryTableJoinPrefix;
/*      */   final boolean inherited;
/*      */   final Class<?> owningType;
/*      */   final boolean local;
/*      */   final boolean lob;
/*      */   final boolean fetchEager;
/*      */   final boolean isTransient;
/*      */   final String name;
/*      */   final Field field;
/*      */   final Class<?> propertyType;
/*      */   final String dbBind;
/*      */   final String dbColumn;
/*      */   final String elPlaceHolder;
/*      */   final String elPlaceHolderEncrypted;
/*      */   final String sqlFormulaSelect;
/*      */   final String sqlFormulaJoin;
/*      */   final boolean formula;
/*      */   final boolean dbEncrypted;
/*      */   final boolean localEncrypted;
/*      */   final int dbEncryptedType;
/*      */   final int dbType;
/*      */   final Object defaultValue;
/*      */   final Map<String, String> extraAttributeMap;
/*      */   final Method readMethod;
/*      */   final Method writeMethod;
/*      */   final GeneratedProperty generatedProperty;
/*      */   final BeanReflectGetter getter;
/*      */   final BeanReflectSetter setter;
/*      */   final BeanDescriptor<?> descriptor;
/*      */   final ScalarType scalarType;
/*      */   final LdapAttributeAdapter ldapAttributeAdapter;
/*      */   final Validator[] validators;
/*      */   final boolean hasLocalValidators;
/*      */   boolean cascadeValidate;
/*      */   final int dbLength;
/*      */   final int dbScale;
/*      */   final String dbColumnDefn;
/*      */   final String dbConstraintExpression;
/*      */   final DbEncryptFunction dbEncryptFunction;
/*      */   final boolean dynamicSubclassWithInheritance;
/*      */   int deployOrder;
/*      */   
/*      */   public BeanProperty(DeployBeanProperty deploy) {
/*  293 */     this(null, null, deploy);
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanProperty(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanProperty deploy) {
/*  298 */     this.descriptor = descriptor;
/*  299 */     this.name = InternString.intern(deploy.getName());
/*  300 */     if (descriptor != null) {
/*  301 */       this.dynamicSubclassWithInheritance = (descriptor.isDynamicSubclass() && descriptor.hasInheritance());
/*      */     } else {
/*  303 */       this.dynamicSubclassWithInheritance = false;
/*      */     } 
/*  305 */     this.unidirectionalShadow = deploy.isUndirectionalShadow();
/*  306 */     this.localEncrypted = deploy.isLocalEncrypted();
/*  307 */     this.dbEncrypted = deploy.isDbEncrypted();
/*  308 */     this.dbEncryptedType = deploy.getDbEncryptedType();
/*  309 */     this.dbEncryptFunction = deploy.getDbEncryptFunction();
/*  310 */     this.dbBind = deploy.getDbBind();
/*  311 */     this.dbRead = deploy.isDbRead();
/*  312 */     this.dbInsertable = deploy.isDbInsertable();
/*  313 */     this.dbUpdatable = deploy.isDbUpdateable();
/*      */     
/*  315 */     this.secondaryTable = deploy.isSecondaryTable();
/*  316 */     if (this.secondaryTable) {
/*  317 */       this.secondaryTableJoin = new TableJoin(deploy.getSecondaryTableJoin(), null);
/*  318 */       this.secondaryTableJoinPrefix = deploy.getSecondaryTableJoinPrefix();
/*      */     } else {
/*  320 */       this.secondaryTableJoin = null;
/*  321 */       this.secondaryTableJoinPrefix = null;
/*      */     } 
/*  323 */     this.fetchEager = deploy.isFetchEager();
/*  324 */     this.isTransient = deploy.isTransient();
/*  325 */     this.nullable = deploy.isNullable();
/*  326 */     this.unique = deploy.isUnique();
/*  327 */     this.naturalKey = deploy.isNaturalKey();
/*  328 */     this.dbLength = deploy.getDbLength();
/*  329 */     this.dbScale = deploy.getDbScale();
/*  330 */     this.dbColumnDefn = InternString.intern(deploy.getDbColumnDefn());
/*  331 */     this.dbConstraintExpression = InternString.intern(deploy.getDbConstraintExpression());
/*      */     
/*  333 */     this.inherited = false;
/*  334 */     this.owningType = deploy.getOwningType();
/*  335 */     this.local = deploy.isLocal();
/*      */     
/*  337 */     this.version = deploy.isVersionColumn();
/*  338 */     this.embedded = deploy.isEmbedded();
/*  339 */     this.id = deploy.isId();
/*  340 */     this.generatedProperty = deploy.getGeneratedProperty();
/*  341 */     this.readMethod = deploy.getReadMethod();
/*  342 */     this.writeMethod = deploy.getWriteMethod();
/*  343 */     this.getter = deploy.getGetter();
/*  344 */     if (descriptor != null && this.getter == null && 
/*  345 */       !this.unidirectionalShadow) {
/*  346 */       String m = "Null Getter for: " + getFullBeanName();
/*  347 */       throw new RuntimeException(m);
/*      */     } 
/*      */     
/*  350 */     this.setter = deploy.getSetter();
/*      */     
/*  352 */     this.dbColumn = tableAliasIntern(descriptor, deploy.getDbColumn(), false, null);
/*  353 */     this.sqlFormulaJoin = InternString.intern(deploy.getSqlFormulaJoin());
/*  354 */     this.sqlFormulaSelect = InternString.intern(deploy.getSqlFormulaSelect());
/*  355 */     this.formula = (this.sqlFormulaSelect != null);
/*      */     
/*  357 */     this.extraAttributeMap = deploy.getExtraAttributeMap();
/*  358 */     this.defaultValue = deploy.getDefaultValue();
/*  359 */     this.dbType = deploy.getDbType();
/*  360 */     this.scalarType = deploy.getScalarType();
/*  361 */     this.ldapAttributeAdapter = deploy.getLdapAttributeAdapter();
/*  362 */     this.lob = isLobType(this.dbType);
/*  363 */     this.propertyType = deploy.getPropertyType();
/*  364 */     this.field = deploy.getField();
/*  365 */     this.validators = deploy.getValidators();
/*  366 */     this.hasLocalValidators = (this.validators.length > 0);
/*      */     
/*  368 */     BeanDescriptor.EntityType et = (descriptor == null) ? null : descriptor.getEntityType();
/*  369 */     this.elPlaceHolder = tableAliasIntern(descriptor, deploy.getElPlaceHolder(et), false, null);
/*  370 */     this.elPlaceHolderEncrypted = tableAliasIntern(descriptor, deploy.getElPlaceHolder(et), this.dbEncrypted, this.dbColumn);
/*      */   }
/*      */   
/*      */   private String tableAliasIntern(BeanDescriptor<?> descriptor, String s, boolean dbEncrypted, String dbColumn) {
/*  374 */     if (descriptor != null) {
/*  375 */       s = StringHelper.replaceString(s, "${ta}.", "${}");
/*  376 */       s = StringHelper.replaceString(s, "${ta}", "${}");
/*      */       
/*  378 */       if (dbEncrypted) {
/*  379 */         s = this.dbEncryptFunction.getDecryptSql(s);
/*  380 */         String namedParam = ":encryptkey_" + descriptor.getBaseTable() + "___" + dbColumn;
/*  381 */         s = StringHelper.replaceString(s, "?", namedParam);
/*      */       } 
/*      */     } 
/*  384 */     return InternString.intern(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty(BeanProperty source, BeanPropertyOverride override) {
/*  396 */     this.descriptor = source.descriptor;
/*  397 */     this.name = InternString.intern(source.getName());
/*  398 */     this.dynamicSubclassWithInheritance = source.dynamicSubclassWithInheritance;
/*      */     
/*  400 */     this.dbColumn = InternString.intern(override.getDbColumn());
/*  401 */     this.sqlFormulaJoin = InternString.intern(override.getSqlFormulaJoin());
/*  402 */     this.sqlFormulaSelect = InternString.intern(override.getSqlFormulaSelect());
/*  403 */     this.formula = (this.sqlFormulaSelect != null);
/*      */     
/*  405 */     this.fetchEager = source.fetchEager;
/*  406 */     this.unidirectionalShadow = source.unidirectionalShadow;
/*  407 */     this.localEncrypted = source.isLocalEncrypted();
/*  408 */     this.isTransient = source.isTransient();
/*  409 */     this.secondaryTable = source.isSecondaryTable();
/*  410 */     this.secondaryTableJoin = source.secondaryTableJoin;
/*  411 */     this.secondaryTableJoinPrefix = source.secondaryTableJoinPrefix;
/*      */     
/*  413 */     this.dbBind = source.getDbBind();
/*  414 */     this.dbEncrypted = source.isDbEncrypted();
/*  415 */     this.dbEncryptedType = source.getDbEncryptedType();
/*  416 */     this.dbEncryptFunction = source.dbEncryptFunction;
/*  417 */     this.dbRead = source.isDbRead();
/*  418 */     this.dbInsertable = source.isDbInsertable();
/*  419 */     this.dbUpdatable = source.isDbUpdatable();
/*  420 */     this.nullable = source.isNullable();
/*  421 */     this.unique = source.isUnique();
/*  422 */     this.naturalKey = source.isNaturalKey();
/*  423 */     this.dbLength = source.getDbLength();
/*  424 */     this.dbScale = source.getDbScale();
/*  425 */     this.dbColumnDefn = InternString.intern(source.getDbColumnDefn());
/*  426 */     this.dbConstraintExpression = InternString.intern(source.getDbConstraintExpression());
/*      */     
/*  428 */     this.inherited = source.isInherited();
/*  429 */     this.owningType = source.owningType;
/*  430 */     this.local = this.owningType.equals(this.descriptor.getBeanType());
/*      */     
/*  432 */     this.version = source.isVersion();
/*  433 */     this.embedded = source.isEmbedded();
/*  434 */     this.id = source.isId();
/*  435 */     this.generatedProperty = source.getGeneratedProperty();
/*  436 */     this.readMethod = source.getReadMethod();
/*  437 */     this.writeMethod = source.getWriteMethod();
/*  438 */     this.getter = source.getter;
/*  439 */     this.setter = source.setter;
/*  440 */     this.extraAttributeMap = source.extraAttributeMap;
/*  441 */     this.defaultValue = source.getDefaultValue();
/*  442 */     this.dbType = source.getDbType();
/*  443 */     this.scalarType = source.scalarType;
/*  444 */     this.ldapAttributeAdapter = source.ldapAttributeAdapter;
/*  445 */     this.lob = isLobType(this.dbType);
/*  446 */     this.propertyType = source.getPropertyType();
/*  447 */     this.field = source.getField();
/*  448 */     this.validators = source.getValidators();
/*  449 */     this.hasLocalValidators = (this.validators.length > 0);
/*      */     
/*  451 */     this.elPlaceHolder = override.replace(source.elPlaceHolder, source.dbColumn);
/*  452 */     this.elPlaceHolderEncrypted = override.replace(source.elPlaceHolderEncrypted, source.dbColumn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialise() {
/*  462 */     if (!this.isTransient && this.scalarType == null) {
/*  463 */       String msg = "No ScalarType assigned to " + this.descriptor.getFullName() + "." + getName();
/*  464 */       throw new RuntimeException(msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDeployOrder() {
/*  472 */     return this.deployOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDeployOrder(int deployOrder) {
/*  479 */     this.deployOrder = deployOrder;
/*      */   }
/*      */ 
/*      */   
/*      */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/*  484 */     throw new PersistenceException("Not valid on scalar bean property " + getFullBeanName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanDescriptor<?> getBeanDescriptor() {
/*  491 */     return this.descriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isScalar() {
/*  498 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFormula() {
/*  505 */     return this.formula;
/*      */   }
/*      */   
/*      */   public boolean hasChanged(Object bean, Object oldValues) {
/*  509 */     Object value = getValue(bean);
/*  510 */     Object oldVal = getValue(oldValues);
/*      */     
/*  512 */     return !ValueUtil.areEqual(value, oldVal);
/*      */   }
/*      */   
/*      */   public void copyProperty(Object sourceBean, Object destBean) {
/*  516 */     Object value = getValue(sourceBean);
/*  517 */     setValue(destBean, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptKey getEncryptKey() {
/*  524 */     return this.descriptor.getEncryptKey(this);
/*      */   }
/*      */   
/*      */   public String getDecryptProperty() {
/*  528 */     return this.dbEncryptFunction.getDecryptSql(getName());
/*      */   }
/*      */   
/*      */   public String getDecryptProperty(String propertyName) {
/*  532 */     return this.dbEncryptFunction.getDecryptSql(propertyName);
/*      */   }
/*      */   
/*      */   public String getDecryptSql() {
/*  536 */     return this.dbEncryptFunction.getDecryptSql(getDbColumn());
/*      */   }
/*      */   
/*      */   public String getDecryptSql(String tableAlias) {
/*  540 */     return this.dbEncryptFunction.getDecryptSql(tableAlias + "." + getDbColumn());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/*  548 */     if (this.formula && this.sqlFormulaJoin != null) {
/*  549 */       ctx.appendFormulaJoin(this.sqlFormulaJoin, forceOuterJoin);
/*      */     }
/*  551 */     else if (this.secondaryTableJoin != null) {
/*      */       
/*  553 */       String relativePrefix = ctx.getRelativePrefix(this.secondaryTableJoinPrefix);
/*  554 */       this.secondaryTableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSecondaryTableJoinPrefix() {
/*  563 */     return this.secondaryTableJoinPrefix;
/*      */   }
/*      */   
/*      */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/*  567 */     if (this.formula) {
/*  568 */       ctx.appendFormulaSelect(this.sqlFormulaSelect);
/*      */     }
/*  570 */     else if (!this.isTransient) {
/*      */       
/*  572 */       if (this.secondaryTableJoin != null) {
/*  573 */         String relativePrefix = ctx.getRelativePrefix(this.secondaryTableJoinPrefix);
/*  574 */         ctx.pushTableAlias(relativePrefix);
/*      */       } 
/*      */       
/*  577 */       if (this.dbEncrypted) {
/*  578 */         String decryptSql = getDecryptSql(ctx.peekTableAlias());
/*  579 */         ctx.appendRawColumn(decryptSql);
/*  580 */         ctx.addEncryptedProp(this);
/*      */       } else {
/*      */         
/*  583 */         ctx.appendColumn(this.dbColumn);
/*      */       } 
/*      */       
/*  586 */       if (this.secondaryTableJoin != null) {
/*  587 */         ctx.popTableAlias();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isAssignableFrom(Class<?> type) {
/*  593 */     return this.owningType.isAssignableFrom(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object readSetOwning(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/*      */     try {
/*  599 */       Object value = this.scalarType.read(ctx.getDataReader());
/*  600 */       if (value != null && bean != null)
/*      */       {
/*      */         
/*  603 */         if (this.owningType.equals(type)) {
/*  604 */           setValue(bean, value);
/*      */         }
/*      */       }
/*  607 */       return value;
/*  608 */     } catch (Exception e) {
/*  609 */       String msg = "Error readSet on " + this.descriptor + "." + this.name;
/*  610 */       throw new PersistenceException(msg, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadIgnore(DbReadContext ctx) {
/*  615 */     this.scalarType.loadIgnore(ctx.getDataReader());
/*      */   }
/*      */   
/*      */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
/*  619 */     sqlBeanLoad.load(this);
/*      */   }
/*      */   
/*      */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
/*  623 */     if (prefix == null) {
/*  624 */       selectChain.add(this.name);
/*      */     } else {
/*  626 */       selectChain.add(prefix + "." + this.name);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object read(DbReadContext ctx) throws SQLException {
/*  631 */     return this.scalarType.read(ctx.getDataReader());
/*      */   }
/*      */ 
/*      */   
/*      */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/*      */     try {
/*  637 */       Object value = this.scalarType.read(ctx.getDataReader());
/*  638 */       if (bean != null && (type == null || this.owningType.isAssignableFrom(type)))
/*      */       {
/*      */         
/*  641 */         setValue(bean, value);
/*      */       }
/*  643 */       return value;
/*  644 */     } catch (Exception e) {
/*  645 */       String msg = "Error readSet on " + this.descriptor + "." + this.name;
/*  646 */       throw new PersistenceException(msg, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object toBeanType(Object value) {
/*  658 */     return this.scalarType.toBeanType(value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void bind(DataBind b, Object value) throws SQLException {
/*  663 */     this.scalarType.bind(b, value);
/*      */   }
/*      */   
/*      */   public void writeData(DataOutput dataOutput, Object value) throws IOException {
/*  667 */     this.scalarType.writeData(dataOutput, value);
/*      */   }
/*      */   
/*      */   public Object readData(DataInput dataInput) throws IOException {
/*  671 */     return this.scalarType.readData(dataInput);
/*      */   }
/*      */   
/*      */   Validator[] getValidators() {
/*  675 */     return this.validators;
/*      */   }
/*      */   
/*      */   public boolean isCascadeValidate() {
/*  679 */     return this.cascadeValidate;
/*      */   }
/*      */   
/*      */   public boolean hasLocalValidators() {
/*  683 */     return this.hasLocalValidators;
/*      */   }
/*      */   
/*      */   public boolean hasValidationRules(boolean cascade) {
/*  687 */     return (this.hasLocalValidators || (cascade && this.cascadeValidate));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValueLoaded(Object value) {
/*  698 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InvalidValue validateCascade(Object value) {
/*  705 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final List<InvalidValue> validate(boolean cascade, Object value) {
/*  719 */     if (!isValueLoaded(value)) {
/*  720 */       return null;
/*      */     }
/*      */     
/*  723 */     ArrayList<InvalidValue> list = null;
/*  724 */     for (int i = 0; i < this.validators.length; i++) {
/*  725 */       if (!this.validators[i].isValid(value)) {
/*  726 */         if (list == null) {
/*  727 */           list = new ArrayList<InvalidValue>();
/*      */         }
/*  729 */         Validator v = this.validators[i];
/*  730 */         list.add(new InvalidValue(v.getKey(), v.getAttributes(), this.descriptor.getFullName(), this.name, value));
/*      */       } 
/*      */     } 
/*      */     
/*  734 */     if (list == null && cascade && this.cascadeValidate) {
/*      */       
/*  736 */       InvalidValue recursive = validateCascade(value);
/*  737 */       if (recursive != null) {
/*  738 */         return InvalidValue.toList(recursive);
/*      */       }
/*      */     } 
/*      */     
/*  742 */     return list;
/*      */   }
/*      */   
/*      */   public BeanProperty getBeanProperty() {
/*  746 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method getReadMethod() {
/*  753 */     return this.readMethod;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method getWriteMethod() {
/*  760 */     return this.writeMethod;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInherited() {
/*  767 */     return this.inherited;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLocal() {
/*  774 */     return this.local;
/*      */   }
/*      */   
/*      */   public Attribute createAttribute(Object bean) {
/*  778 */     Object v = getValue(bean);
/*  779 */     if (v == null) {
/*  780 */       return null;
/*      */     }
/*  782 */     if (this.ldapAttributeAdapter != null) {
/*  783 */       return this.ldapAttributeAdapter.createAttribute(v);
/*      */     }
/*  785 */     Object ldapValue = this.scalarType.toJdbcType(v);
/*  786 */     return new BasicAttribute(this.dbColumn, ldapValue);
/*      */   }
/*      */   
/*      */   public void setAttributeValue(Object bean, Attribute attr) {
/*      */     try {
/*  791 */       if (attr != null) {
/*      */         Object beanValue;
/*  793 */         if (this.ldapAttributeAdapter != null) {
/*  794 */           beanValue = this.ldapAttributeAdapter.readAttribute(attr);
/*      */         } else {
/*  796 */           beanValue = this.scalarType.toBeanType(attr.get());
/*      */         } 
/*      */         
/*  799 */         setValue(bean, beanValue);
/*      */       } 
/*  801 */     } catch (NamingException e) {
/*  802 */       throw new LdapPersistenceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValue(Object bean, Object value) {
/*      */     try {
/*  812 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  813 */         this.setter.set(bean, value);
/*      */       } else {
/*  815 */         Object[] args = new Object[1];
/*  816 */         args[0] = value;
/*  817 */         this.writeMethod.invoke(bean, args);
/*      */       } 
/*  819 */     } catch (Exception ex) {
/*  820 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  821 */       String msg = "set " + this.name + " on [" + this.descriptor + "] arg[" + value + "] type[" + beanType + "] threw error";
/*      */       
/*  823 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValueIntercept(Object bean, Object value) {
/*      */     try {
/*  832 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  833 */         this.setter.setIntercept(bean, value);
/*      */       } else {
/*  835 */         Object[] args = new Object[1];
/*  836 */         args[0] = value;
/*  837 */         this.writeMethod.invoke(bean, args);
/*      */       } 
/*  839 */     } catch (Exception ex) {
/*  840 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  841 */       String msg = "setIntercept " + this.name + " on [" + this.descriptor + "] arg[" + value + "] type[" + beanType + "] threw error";
/*      */       
/*  843 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */   
/*  847 */   private static Object[] NO_ARGS = new Object[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getValueWithInheritance(Object bean) {
/*  853 */     if (this.dynamicSubclassWithInheritance) {
/*  854 */       return this.descriptor.getBeanPropertyWithInheritance(bean, this.name);
/*      */     }
/*  856 */     return getValue(bean);
/*      */   }
/*      */   
/*      */   public Object getCacheDataValue(Object bean) {
/*  860 */     return getValue(bean);
/*      */   }
/*      */   
/*      */   public void setCacheDataValue(Object bean, Object cacheData, Object oldValues, boolean readOnly) {
/*  864 */     setValue(bean, cacheData);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getValue(Object bean) {
/*      */     try {
/*  872 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  873 */         return this.getter.get(bean);
/*      */       }
/*  875 */       return this.readMethod.invoke(bean, NO_ARGS);
/*      */     }
/*  877 */     catch (Exception ex) {
/*  878 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  879 */       String msg = "get " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
/*  880 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getValueViaReflection(Object bean) {
/*      */     try {
/*  889 */       return this.readMethod.invoke(bean, NO_ARGS);
/*  890 */     } catch (Exception ex) {
/*  891 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  892 */       String msg = "get " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
/*  893 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object getValueIntercept(Object bean) {
/*      */     try {
/*  899 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  900 */         return this.getter.getIntercept(bean);
/*      */       }
/*  902 */       return this.readMethod.invoke(bean, NO_ARGS);
/*      */     }
/*  904 */     catch (Exception ex) {
/*  905 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  906 */       String msg = "getIntercept " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
/*  907 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object elConvertType(Object value) {
/*  912 */     if (value == null) {
/*  913 */       return null;
/*      */     }
/*  915 */     return convertToLogicalType(value);
/*      */   }
/*      */   
/*      */   public void elSetReference(Object bean) {
/*  919 */     throw new RuntimeException("Should not be called");
/*      */   }
/*      */   
/*      */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/*  923 */     if (bean != null) {
/*  924 */       setValueIntercept(bean, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public Object elGetValue(Object bean) {
/*  929 */     if (bean == null) {
/*  930 */       return null;
/*      */     }
/*  932 */     return getValueIntercept(bean);
/*      */   }
/*      */   
/*      */   public Object elGetReference(Object bean) {
/*  936 */     throw new RuntimeException("Not expected to call this");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  943 */     return this.name;
/*      */   }
/*      */   
/*      */   public String getElName() {
/*  947 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDeployOnly() {
/*  954 */     return false;
/*      */   }
/*      */   
/*      */   public boolean containsManySince(String sinceProperty) {
/*  958 */     return containsMany();
/*      */   }
/*      */   
/*      */   public boolean containsMany() {
/*  962 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object[] getAssocOneIdValues(Object bean) {
/*  967 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getAssocOneIdExpr(String prefix, String operator) {
/*  972 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getAssocIdInExpr(String prefix) {
/*  977 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getAssocIdInValueExpr(int size) {
/*  982 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAssocId() {
/*  987 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAssocProperty() {
/*  992 */     return false;
/*      */   }
/*      */   
/*      */   public String getElPlaceholder(boolean encrypted) {
/*  996 */     return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder;
/*      */   }
/*      */   
/*      */   public String getElPrefix() {
/* 1000 */     return this.secondaryTableJoinPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFullBeanName() {
/* 1007 */     return this.descriptor.getFullName() + "." + this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScalarType<?> getScalarType() {
/* 1014 */     return this.scalarType;
/*      */   }
/*      */   
/*      */   public StringFormatter getStringFormatter() {
/* 1018 */     return (StringFormatter)this.scalarType;
/*      */   }
/*      */   
/*      */   public StringParser getStringParser() {
/* 1022 */     return (StringParser)this.scalarType;
/*      */   }
/*      */   
/*      */   public boolean isDateTimeCapable() {
/* 1026 */     return (this.scalarType != null && this.scalarType.isDateTimeCapable());
/*      */   }
/*      */   
/*      */   public int getJdbcType() {
/* 1030 */     return (this.scalarType == null) ? 0 : this.scalarType.getJdbcType();
/*      */   }
/*      */   
/*      */   public Object parseDateTime(long systemTimeMillis) {
/* 1034 */     return this.scalarType.parseDateTime(systemTimeMillis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDbLength() {
/* 1041 */     return this.dbLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDbScale() {
/* 1048 */     return this.dbScale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDbColumnDefn() {
/* 1055 */     return this.dbColumnDefn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDbConstraintExpression() {
/* 1065 */     return this.dbConstraintExpression;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String renderDbType(DbType dbType) {
/* 1072 */     if (this.dbColumnDefn != null) {
/* 1073 */       return this.dbColumnDefn;
/*      */     }
/* 1075 */     return dbType.renderType(this.dbLength, this.dbScale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field getField() {
/* 1082 */     return this.field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratedProperty getGeneratedProperty() {
/* 1089 */     return this.generatedProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNaturalKey() {
/* 1096 */     return this.naturalKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNullable() {
/* 1103 */     return this.nullable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDDLNotNull() {
/* 1111 */     return (isVersion() || (this.generatedProperty != null && this.generatedProperty.isDDLNotNullable()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUnique() {
/* 1118 */     return this.unique;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTransient() {
/* 1125 */     return this.isTransient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVersion() {
/* 1132 */     return this.version;
/*      */   }
/*      */   
/*      */   public String getDeployProperty() {
/* 1136 */     return this.dbColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDbColumn() {
/* 1143 */     return this.dbColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDbType() {
/* 1150 */     return this.dbType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object convertToLogicalType(Object value) {
/* 1157 */     if (this.scalarType != null) {
/* 1158 */       return this.scalarType.toBeanType(value);
/*      */     }
/* 1160 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFetchEager() {
/* 1181 */     return this.fetchEager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLob() {
/* 1189 */     return this.lob;
/*      */   }
/*      */   
/*      */   private boolean isLobType(int type) {
/* 1193 */     switch (type) {
/*      */       case 2005:
/* 1195 */         return true;
/*      */       case 2004:
/* 1197 */         return true;
/*      */       case -4:
/* 1199 */         return true;
/*      */       case -1:
/* 1201 */         return true;
/*      */     } 
/*      */     
/* 1204 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDbBind() {
/* 1213 */     return this.dbBind;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLocalEncrypted() {
/* 1220 */     return this.localEncrypted;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDbEncrypted() {
/* 1227 */     return this.dbEncrypted;
/*      */   }
/*      */   
/*      */   public int getDbEncryptedType() {
/* 1231 */     return this.dbEncryptedType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDbInsertable() {
/* 1238 */     return this.dbInsertable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDbUpdatable() {
/* 1245 */     return this.dbUpdatable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDbRead() {
/* 1252 */     return this.dbRead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSecondaryTable() {
/* 1260 */     return this.secondaryTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getPropertyType() {
/* 1267 */     return this.propertyType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isId() {
/* 1274 */     return this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmbedded() {
/* 1282 */     return this.embedded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExtraAttribute(String key) {
/* 1289 */     return this.extraAttributeMap.get(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDefaultValue() {
/* 1296 */     return this.defaultValue;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1300 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 1306 */     Object value = getValueIntercept(bean);
/* 1307 */     if (value == null) {
/* 1308 */       ctx.appendNull(this.name);
/*      */     } else {
/* 1310 */       ctx.appendNameValue(this.name, this.scalarType, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/*      */     String jsonValue;
/*      */     Object objValue;
/*      */     try {
/* 1318 */       jsonValue = ctx.readScalarValue();
/* 1319 */     } catch (TextException e) {
/* 1320 */       throw new TextException("Error reading property " + getFullBeanName(), e);
/*      */     } 
/*      */     
/* 1323 */     if (jsonValue == null) {
/* 1324 */       objValue = null;
/*      */     } else {
/* 1326 */       objValue = this.scalarType.jsonFromString(jsonValue, ctx.getValueAdapter());
/*      */     } 
/* 1328 */     setValue(bean, objValue);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */