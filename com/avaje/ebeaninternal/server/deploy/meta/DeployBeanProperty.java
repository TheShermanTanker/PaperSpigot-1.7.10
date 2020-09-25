/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.annotation.CreatedTimestamp;
/*     */ import com.avaje.ebean.annotation.UpdatedTimestamp;
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
/*     */ import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
/*     */ import com.avaje.ebean.validation.factory.Validator;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*     */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*     */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeEnum;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.persistence.EmbeddedId;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Version;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployBeanProperty
/*     */ {
/*     */   private static final int ID_ORDER = 1000000;
/*     */   private static final int UNIDIRECTIONAL_ORDER = 100000;
/*     */   private static final int AUDITCOLUMN_ORDER = -1000000;
/*     */   private static final int VERSIONCOLUMN_ORDER = -1000000;
/*     */   public static final String EXCLUDE_FROM_UPDATE_WHERE = "EXCLUDE_FROM_UPDATE_WHERE";
/*     */   public static final String EXCLUDE_FROM_DELETE_WHERE = "EXCLUDE_FROM_DELETE_WHERE";
/*     */   public static final String EXCLUDE_FROM_INSERT = "EXCLUDE_FROM_INSERT";
/*     */   public static final String EXCLUDE_FROM_UPDATE = "EXCLUDE_FROM_UPDATE";
/*     */   private boolean id;
/*     */   private boolean embedded;
/*     */   private boolean versionColumn;
/*     */   private boolean fetchEager = true;
/*     */   private boolean nullable = true;
/*     */   private boolean unique;
/*     */   private LdapAttributeAdapter ldapAttributeAdapter;
/*     */   private int dbLength;
/*     */   private int dbScale;
/*     */   private String dbColumnDefn;
/*     */   private boolean isTransient;
/*     */   private boolean localEncrypted;
/*     */   private boolean dbEncrypted;
/*     */   private DbEncryptFunction dbEncryptFunction;
/*     */   private int dbEncryptedType;
/* 133 */   private String dbBind = "?";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dbRead;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dbInsertable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dbUpdateable;
/*     */ 
/*     */ 
/*     */   
/*     */   private DeployTableJoin secondaryTableJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   private String secondaryTableJoinPrefix;
/*     */ 
/*     */ 
/*     */   
/*     */   private String secondaryTable;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> owningType;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean lob;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean naturalKey;
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private Field field;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> propertyType;
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarType<?> scalarType;
/*     */ 
/*     */ 
/*     */   
/*     */   private String dbColumn;
/*     */ 
/*     */ 
/*     */   
/*     */   private String sqlFormulaSelect;
/*     */ 
/*     */ 
/*     */   
/*     */   private String sqlFormulaJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   private int dbType;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object defaultValue;
/*     */ 
/*     */ 
/*     */   
/* 212 */   private HashMap<String, String> extraAttributeMap = new HashMap<String, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private Method readMethod;
/*     */ 
/*     */ 
/*     */   
/*     */   private Method writeMethod;
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanReflectGetter getter;
/*     */ 
/*     */   
/*     */   private BeanReflectSetter setter;
/*     */ 
/*     */   
/*     */   private GeneratedProperty generatedProperty;
/*     */ 
/*     */   
/* 233 */   private List<Validator> validators = new ArrayList<Validator>();
/*     */   
/*     */   private final DeployBeanDescriptor<?> desc;
/*     */   
/*     */   private boolean undirectionalShadow;
/*     */   
/*     */   private int sortOrder;
/*     */   
/*     */   public DeployBeanProperty(DeployBeanDescriptor<?> desc, Class<?> propertyType, ScalarType<?> scalarType, ScalarTypeConverter<?, ?> typeConverter) {
/* 242 */     this.desc = desc;
/* 243 */     this.propertyType = propertyType;
/* 244 */     this.scalarType = wrapScalarType(propertyType, scalarType, typeConverter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarType<?> wrapScalarType(Class<?> propertyType, ScalarType<?> scalarType, ScalarTypeConverter<?, ?> typeConverter) {
/* 252 */     if (typeConverter == null) {
/* 253 */       return scalarType;
/*     */     }
/* 255 */     return (ScalarType<?>)new ScalarTypeWrapper(propertyType, scalarType, typeConverter);
/*     */   }
/*     */   
/*     */   public int getSortOverride() {
/* 259 */     if (this.field == null) {
/* 260 */       return 0;
/*     */     }
/* 262 */     if (this.field.getAnnotation(Id.class) != null)
/* 263 */       return 1000000; 
/* 264 */     if (this.field.getAnnotation(EmbeddedId.class) != null)
/* 265 */       return 1000000; 
/* 266 */     if (this.undirectionalShadow)
/* 267 */       return 100000; 
/* 268 */     if (this.field.getAnnotation(CreatedTimestamp.class) != null)
/* 269 */       return -1000000; 
/* 270 */     if (this.field.getAnnotation(UpdatedTimestamp.class) != null)
/* 271 */       return -1000000; 
/* 272 */     if (this.field.getAnnotation(Version.class) != null) {
/* 273 */       return -1000000;
/*     */     }
/* 275 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScalar() {
/* 282 */     return true;
/*     */   }
/*     */   
/*     */   public String getFullBeanName() {
/* 286 */     return this.desc.getFullName() + "." + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullablePrimitive() {
/* 296 */     if (this.nullable && this.propertyType.isPrimitive()) {
/* 297 */       return true;
/*     */     }
/* 299 */     return false;
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
/*     */   public int getDbLength() {
/* 311 */     if (this.dbLength == 0 && this.scalarType != null) {
/* 312 */       return this.scalarType.getLength();
/*     */     }
/*     */     
/* 315 */     return this.dbLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSortOrder() {
/* 322 */     return this.sortOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSortOrder(int sortOrder) {
/* 329 */     this.sortOrder = sortOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUndirectionalShadow() {
/* 336 */     return this.undirectionalShadow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUndirectionalShadow(boolean undirectionalShadow) {
/* 343 */     this.undirectionalShadow = undirectionalShadow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalEncrypted() {
/* 350 */     return this.localEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalEncrypted(boolean localEncrypted) {
/* 357 */     this.localEncrypted = localEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbLength(int dbLength) {
/* 364 */     this.dbLength = dbLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDbScale() {
/* 371 */     return this.dbScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbScale(int dbScale) {
/* 378 */     this.dbScale = dbScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbColumnDefn() {
/* 385 */     return this.dbColumnDefn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbColumnDefn(String dbColumnDefn) {
/* 392 */     if (dbColumnDefn == null || dbColumnDefn.trim().length() == 0) {
/* 393 */       this.dbColumnDefn = null;
/*     */     } else {
/* 395 */       this.dbColumnDefn = InternString.intern(dbColumnDefn);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getDbConstraintExpression() {
/* 400 */     if (this.scalarType instanceof ScalarTypeEnum) {
/*     */       
/* 402 */       ScalarTypeEnum etype = (ScalarTypeEnum)this.scalarType;
/*     */ 
/*     */       
/* 405 */       return "check (" + this.dbColumn + " in " + etype.getContraintInValues() + ")";
/*     */     } 
/* 407 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValidator(Validator validator) {
/* 414 */     this.validators.add(validator);
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
/*     */   public boolean containsValidatorType(Class<?> type) {
/* 426 */     Iterator<Validator> it = this.validators.iterator();
/* 427 */     while (it.hasNext()) {
/* 428 */       Validator validator = it.next();
/* 429 */       if (validator.getClass().equals(type)) {
/* 430 */         return true;
/*     */       }
/*     */     } 
/* 433 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Validator[] getValidators() {
/* 440 */     return this.validators.<Validator>toArray(new Validator[this.validators.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScalarType<?> getScalarType() {
/* 448 */     return this.scalarType;
/*     */   }
/*     */   
/*     */   public void setScalarType(ScalarType<?> scalarType) {
/* 452 */     this.scalarType = scalarType;
/*     */   }
/*     */   
/*     */   public BeanReflectGetter getGetter() {
/* 456 */     return this.getter;
/*     */   }
/*     */   
/*     */   public BeanReflectSetter getSetter() {
/* 460 */     return this.setter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getReadMethod() {
/* 467 */     return this.readMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getWriteMethod() {
/* 474 */     return this.writeMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwningType(Class<?> owningType) {
/* 481 */     this.owningType = owningType;
/*     */   }
/*     */   
/*     */   public Class<?> getOwningType() {
/* 485 */     return this.owningType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocal() {
/* 492 */     return (this.owningType == null || this.owningType.equals(this.desc.getBeanType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGetter(BeanReflectGetter getter) {
/* 499 */     this.getter = getter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSetter(BeanReflectSetter setter) {
/* 506 */     this.setter = setter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 513 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 520 */     this.name = InternString.intern(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field getField() {
/* 527 */     return this.field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(Field field) {
/* 534 */     this.field = field;
/*     */   }
/*     */   
/*     */   public boolean isNaturalKey() {
/* 538 */     return this.naturalKey;
/*     */   }
/*     */   
/*     */   public void setNaturalKey(boolean naturalKey) {
/* 542 */     this.naturalKey = naturalKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGenerated() {
/* 550 */     return (this.generatedProperty != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratedProperty getGeneratedProperty() {
/* 557 */     return this.generatedProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeneratedProperty(GeneratedProperty generatedValue) {
/* 564 */     this.generatedProperty = generatedValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullable() {
/* 571 */     return this.nullable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullable(boolean isNullable) {
/* 578 */     this.nullable = isNullable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnique() {
/* 585 */     return this.unique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnique(boolean unique) {
/* 592 */     this.unique = unique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LdapAttributeAdapter getLdapAttributeAdapter() {
/* 599 */     return this.ldapAttributeAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLdapAttributeAdapter(LdapAttributeAdapter ldapAttributeAdapter) {
/* 606 */     this.ldapAttributeAdapter = ldapAttributeAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVersionColumn() {
/* 613 */     return this.versionColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersionColumn(boolean isVersionColumn) {
/* 620 */     this.versionColumn = isVersionColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFetchEager() {
/* 627 */     return this.fetchEager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchType(FetchType fetchType) {
/* 634 */     this.fetchEager = FetchType.EAGER.equals(fetchType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSqlFormulaSelect() {
/* 641 */     return this.sqlFormulaSelect;
/*     */   }
/*     */   
/*     */   public String getSqlFormulaJoin() {
/* 645 */     return this.sqlFormulaJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSqlFormula(String formulaSelect, String formulaJoin) {
/* 652 */     this.sqlFormulaSelect = formulaSelect;
/* 653 */     this.sqlFormulaJoin = formulaJoin.equals("") ? null : formulaJoin;
/* 654 */     this.dbRead = true;
/* 655 */     this.dbInsertable = false;
/* 656 */     this.dbUpdateable = false;
/*     */   }
/*     */   
/*     */   public String getElPlaceHolder(BeanDescriptor.EntityType et) {
/* 660 */     if (this.sqlFormulaSelect != null)
/* 661 */       return this.sqlFormulaSelect; 
/* 662 */     if (BeanDescriptor.EntityType.LDAP.equals(et)) {
/* 663 */       return getDbColumn();
/*     */     }
/* 665 */     if (this.secondaryTableJoinPrefix != null) {
/* 666 */       return "${" + this.secondaryTableJoinPrefix + "}" + getDbColumn();
/*     */     }
/*     */     
/* 669 */     return "${}" + getDbColumn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbColumn() {
/* 677 */     if (this.sqlFormulaSelect != null) {
/* 678 */       return this.sqlFormulaSelect;
/*     */     }
/* 680 */     return this.dbColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbColumn(String dbColumn) {
/* 687 */     this.dbColumn = InternString.intern(dbColumn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDbType() {
/* 694 */     return this.dbType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbType(int dbType) {
/* 701 */     this.dbType = dbType;
/* 702 */     this.lob = isLobType(dbType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLob() {
/* 710 */     return this.lob;
/*     */   }
/*     */   
/*     */   private boolean isLobType(int type) {
/* 714 */     switch (type) {
/*     */       case 2005:
/* 716 */         return true;
/*     */       case 2004:
/* 718 */         return true;
/*     */       case -4:
/* 720 */         return true;
/*     */       case -1:
/* 722 */         return true;
/*     */     } 
/*     */     
/* 725 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSecondaryTable() {
/* 733 */     return (this.secondaryTable != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecondaryTable() {
/* 740 */     return this.secondaryTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecondaryTable(String secondaryTable) {
/* 747 */     this.secondaryTable = secondaryTable;
/* 748 */     this.dbInsertable = false;
/* 749 */     this.dbUpdateable = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecondaryTableJoinPrefix() {
/* 756 */     return this.secondaryTableJoinPrefix;
/*     */   }
/*     */   
/*     */   public DeployTableJoin getSecondaryTableJoin() {
/* 760 */     return this.secondaryTableJoin;
/*     */   }
/*     */   
/*     */   public void setSecondaryTableJoin(DeployTableJoin secondaryTableJoin, String prefix) {
/* 764 */     this.secondaryTableJoin = secondaryTableJoin;
/* 765 */     this.secondaryTableJoinPrefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbBind() {
/* 773 */     return this.dbBind;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbBind(String dbBind) {
/* 780 */     this.dbBind = dbBind;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDbEncrypted() {
/* 787 */     return this.dbEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbEncryptFunction getDbEncryptFunction() {
/* 798 */     return this.dbEncryptFunction;
/*     */   }
/*     */   
/*     */   public void setDbEncryptFunction(DbEncryptFunction dbEncryptFunction, DbEncrypt dbEncrypt, int dbLen) {
/* 802 */     this.dbEncryptFunction = dbEncryptFunction;
/* 803 */     this.dbEncrypted = true;
/* 804 */     this.dbBind = dbEncryptFunction.getEncryptBindSql();
/*     */     
/* 806 */     this.dbEncryptedType = isLob() ? 2004 : dbEncrypt.getEncryptDbType();
/* 807 */     if (dbLen > 0) {
/* 808 */       setDbLength(dbLen);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDbEncryptedType() {
/* 817 */     return this.dbEncryptedType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbEncryptedType(int dbEncryptedType) {
/* 824 */     this.dbEncryptedType = dbEncryptedType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDbRead() {
/* 831 */     return this.dbRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbRead(boolean isDBRead) {
/* 838 */     this.dbRead = isDBRead;
/*     */   }
/*     */   
/*     */   public boolean isDbInsertable() {
/* 842 */     return this.dbInsertable;
/*     */   }
/*     */   
/*     */   public void setDbInsertable(boolean insertable) {
/* 846 */     this.dbInsertable = insertable;
/*     */   }
/*     */   
/*     */   public boolean isDbUpdateable() {
/* 850 */     return this.dbUpdateable;
/*     */   }
/*     */   
/*     */   public void setDbUpdateable(boolean updateable) {
/* 854 */     this.dbUpdateable = updateable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTransient() {
/* 861 */     return this.isTransient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransient(boolean isTransient) {
/* 868 */     this.isTransient = isTransient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadMethod(Method readMethod) {
/* 879 */     this.readMethod = readMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWriteMethod(Method writeMethod) {
/* 890 */     this.writeMethod = writeMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getPropertyType() {
/* 897 */     return this.propertyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isId() {
/* 904 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(boolean id) {
/* 911 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmbedded() {
/* 919 */     return this.embedded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmbedded(boolean embedded) {
/* 926 */     this.embedded = embedded;
/*     */   }
/*     */   
/*     */   public Map<String, String> getExtraAttributeMap() {
/* 930 */     return this.extraAttributeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtraAttribute(String key) {
/* 937 */     return this.extraAttributeMap.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtraAttribute(String key, String value) {
/* 944 */     this.extraAttributeMap.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getDefaultValue() {
/* 951 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultValue(Object defaultValue) {
/* 958 */     this.defaultValue = defaultValue;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 962 */     return this.desc.getFullName() + "." + this.name;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */