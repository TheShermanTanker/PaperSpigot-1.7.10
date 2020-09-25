/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.validation.factory.Validator;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertySimpleCollection;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
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
/*     */ public class DeployBeanPropertyLists
/*     */ {
/*     */   private BeanProperty derivedFirstVersionProp;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final LinkedHashMap<String, BeanProperty> propertyMap;
/*  49 */   private final ArrayList<BeanProperty> ids = new ArrayList<BeanProperty>();
/*     */   
/*  51 */   private final ArrayList<BeanProperty> version = new ArrayList<BeanProperty>();
/*     */   
/*  53 */   private final ArrayList<BeanProperty> local = new ArrayList<BeanProperty>();
/*     */   
/*  55 */   private final ArrayList<BeanProperty> manys = new ArrayList<BeanProperty>();
/*  56 */   private final ArrayList<BeanProperty> nonManys = new ArrayList<BeanProperty>();
/*     */   
/*  58 */   private final ArrayList<BeanProperty> ones = new ArrayList<BeanProperty>();
/*     */   
/*  60 */   private final ArrayList<BeanProperty> onesExported = new ArrayList<BeanProperty>();
/*     */   
/*  62 */   private final ArrayList<BeanProperty> onesImported = new ArrayList<BeanProperty>();
/*     */   
/*  64 */   private final ArrayList<BeanProperty> embedded = new ArrayList<BeanProperty>();
/*     */   
/*  66 */   private final ArrayList<BeanProperty> baseScalar = new ArrayList<BeanProperty>();
/*     */   
/*  68 */   private final ArrayList<BeanPropertyCompound> baseCompound = new ArrayList<BeanPropertyCompound>();
/*     */   
/*  70 */   private final ArrayList<BeanProperty> transients = new ArrayList<BeanProperty>();
/*     */   
/*  72 */   private final ArrayList<BeanProperty> nonTransients = new ArrayList<BeanProperty>();
/*     */   
/*     */   private final TableJoin[] tableJoins;
/*     */   
/*     */   private final BeanPropertyAssocOne<?> unidirectional;
/*     */ 
/*     */   
/*     */   public DeployBeanPropertyLists(BeanDescriptorMap owner, BeanDescriptor<?> desc, DeployBeanDescriptor<?> deploy) {
/*  80 */     this.desc = desc;
/*     */     
/*  82 */     DeployBeanPropertyAssocOne<?> deployUnidirectional = deploy.getUnidirectional();
/*  83 */     if (deployUnidirectional == null) {
/*  84 */       this.unidirectional = null;
/*     */     } else {
/*  86 */       this.unidirectional = new BeanPropertyAssocOne(owner, desc, deployUnidirectional);
/*     */     } 
/*     */     
/*  89 */     this.propertyMap = new LinkedHashMap<String, BeanProperty>();
/*     */     
/*  91 */     Iterator<DeployBeanProperty> deployIt = deploy.propertiesAll();
/*  92 */     while (deployIt.hasNext()) {
/*  93 */       DeployBeanProperty deployProp = deployIt.next();
/*  94 */       BeanProperty beanProp = createBeanProperty(owner, deployProp);
/*  95 */       this.propertyMap.put(beanProp.getName(), beanProp);
/*     */     } 
/*     */     
/*  98 */     Iterator<BeanProperty> it = this.propertyMap.values().iterator();
/*     */     
/* 100 */     int order = 0;
/* 101 */     while (it.hasNext()) {
/* 102 */       BeanProperty prop = it.next();
/* 103 */       prop.setDeployOrder(order++);
/* 104 */       allocateToList(prop);
/*     */     } 
/*     */     
/* 107 */     List<DeployTableJoin> deployTableJoins = deploy.getTableJoins();
/* 108 */     this.tableJoins = new TableJoin[deployTableJoins.size()];
/* 109 */     for (int i = 0; i < deployTableJoins.size(); i++) {
/* 110 */       this.tableJoins[i] = new TableJoin(deployTableJoins.get(i), this.propertyMap);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssocOne<?> getUnidirectional() {
/* 119 */     return this.unidirectional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void allocateToList(BeanProperty prop) {
/* 126 */     if (prop.isTransient()) {
/* 127 */       this.transients.add(prop);
/*     */       return;
/*     */     } 
/* 130 */     if (prop.isId()) {
/* 131 */       this.ids.add(prop);
/*     */       return;
/*     */     } 
/* 134 */     this.nonTransients.add(prop);
/*     */ 
/*     */     
/* 137 */     if (this.desc.getInheritInfo() != null && prop.isLocal()) {
/* 138 */       this.local.add(prop);
/*     */     }
/*     */     
/* 141 */     if (prop instanceof BeanPropertyAssocMany) {
/* 142 */       this.manys.add(prop);
/*     */     } else {
/*     */       
/* 145 */       this.nonManys.add(prop);
/* 146 */       if (prop instanceof BeanPropertyAssocOne) {
/* 147 */         if (prop.isEmbedded()) {
/* 148 */           this.embedded.add(prop);
/*     */         } else {
/* 150 */           this.ones.add(prop);
/* 151 */           BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)prop;
/* 152 */           if (assocOne.isOneToOneExported()) {
/* 153 */             this.onesExported.add(prop);
/*     */           } else {
/* 155 */             this.onesImported.add(prop);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 160 */         if (prop.isVersion()) {
/* 161 */           this.version.add(prop);
/* 162 */           if (this.derivedFirstVersionProp == null) {
/* 163 */             this.derivedFirstVersionProp = prop;
/*     */           }
/*     */         } 
/* 166 */         if (prop instanceof BeanPropertyCompound) {
/* 167 */           this.baseCompound.add((BeanPropertyCompound)prop);
/*     */         } else {
/* 169 */           this.baseScalar.add(prop);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public BeanProperty getFirstVersion() {
/* 176 */     return this.derivedFirstVersionProp;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanProperty[] getPropertiesWithValidators(boolean recurse) {
/* 181 */     ArrayList<BeanProperty> list = new ArrayList<BeanProperty>();
/* 182 */     Iterator<BeanProperty> it = this.propertyMap.values().iterator();
/* 183 */     while (it.hasNext()) {
/* 184 */       BeanProperty property = it.next();
/* 185 */       if (property.hasValidationRules(recurse)) {
/* 186 */         list.add(property);
/*     */       }
/*     */     } 
/* 189 */     return list.<BeanProperty>toArray(new BeanProperty[list.size()]);
/*     */   }
/*     */   
/*     */   public Validator[] getBeanValidators() {
/* 193 */     return new Validator[0];
/*     */   }
/*     */   
/*     */   public LinkedHashMap<String, BeanProperty> getPropertyMap() {
/* 197 */     return this.propertyMap;
/*     */   }
/*     */   
/*     */   public TableJoin[] getTableJoin() {
/* 201 */     return this.tableJoins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty[] getBaseScalar() {
/* 209 */     return this.baseScalar.<BeanProperty>toArray(new BeanProperty[this.baseScalar.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyCompound[] getBaseCompound() {
/* 213 */     return this.baseCompound.<BeanPropertyCompound>toArray(new BeanPropertyCompound[this.baseCompound.size()]);
/*     */   }
/*     */   
/*     */   public BeanProperty getNaturalKey() {
/* 217 */     String naturalKey = this.desc.getCacheOptions().getNaturalKey();
/* 218 */     if (naturalKey != null) {
/* 219 */       return this.propertyMap.get(naturalKey);
/*     */     }
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   public BeanProperty[] getId() {
/* 225 */     return this.ids.<BeanProperty>toArray(new BeanProperty[this.ids.size()]);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getNonTransients() {
/* 229 */     return this.nonTransients.<BeanProperty>toArray(new BeanProperty[this.nonTransients.size()]);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getTransients() {
/* 233 */     return this.transients.<BeanProperty>toArray(new BeanProperty[this.transients.size()]);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getVersion() {
/* 237 */     return this.version.<BeanProperty>toArray(new BeanProperty[this.version.size()]);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getLocal() {
/* 241 */     return this.local.<BeanProperty>toArray(new BeanProperty[this.local.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getEmbedded() {
/* 245 */     return (BeanPropertyAssocOne<?>[])this.embedded.<BeanPropertyAssocOne>toArray(new BeanPropertyAssocOne[this.embedded.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOneExported() {
/* 249 */     return (BeanPropertyAssocOne<?>[])this.onesExported.<BeanPropertyAssocOne>toArray(new BeanPropertyAssocOne[this.onesExported.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOneImported() {
/* 253 */     return (BeanPropertyAssocOne<?>[])this.onesImported.<BeanPropertyAssocOne>toArray(new BeanPropertyAssocOne[this.onesImported.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOnes() {
/* 257 */     return (BeanPropertyAssocOne<?>[])this.ones.<BeanPropertyAssocOne>toArray(new BeanPropertyAssocOne[this.ones.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOneExportedSave() {
/* 261 */     return getOne(false, Mode.Save);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOneExportedDelete() {
/* 265 */     return getOne(false, Mode.Delete);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOneImportedSave() {
/* 269 */     return getOne(true, Mode.Save);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocOne<?>[] getOneImportedDelete() {
/* 273 */     return getOne(true, Mode.Delete);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getNonMany() {
/* 277 */     return this.nonManys.<BeanProperty>toArray(new BeanProperty[this.nonManys.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocMany<?>[] getMany() {
/* 281 */     return (BeanPropertyAssocMany<?>[])this.manys.<BeanPropertyAssocMany>toArray(new BeanPropertyAssocMany[this.manys.size()]);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocMany<?>[] getManySave() {
/* 285 */     return getMany(Mode.Save);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocMany<?>[] getManyDelete() {
/* 289 */     return getMany(Mode.Delete);
/*     */   }
/*     */   
/*     */   public BeanPropertyAssocMany<?>[] getManyToMany() {
/* 293 */     return getMany2Many();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Mode
/*     */   {
/* 300 */     Save, Delete, Validate;
/*     */   }
/*     */   
/*     */   private BeanPropertyAssocOne<?>[] getOne(boolean imported, Mode mode) {
/* 304 */     ArrayList<BeanPropertyAssocOne<?>> list = new ArrayList<BeanPropertyAssocOne<?>>();
/* 305 */     for (int i = 0; i < this.ones.size(); i++) {
/* 306 */       BeanPropertyAssocOne<?> prop = (BeanPropertyAssocOne)this.ones.get(i);
/* 307 */       if (imported != prop.isOneToOneExported()) {
/* 308 */         switch (mode) {
/*     */           case Save:
/* 310 */             if (prop.getCascadeInfo().isSave()) {
/* 311 */               list.add(prop);
/*     */             }
/*     */             break;
/*     */           case Delete:
/* 315 */             if (prop.getCascadeInfo().isDelete()) {
/* 316 */               list.add(prop);
/*     */             }
/*     */             break;
/*     */           case Validate:
/* 320 */             if (prop.getCascadeInfo().isValidate()) {
/* 321 */               list.add(prop);
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */     } 
/* 330 */     return (BeanPropertyAssocOne<?>[])list.<BeanPropertyAssocOne>toArray(new BeanPropertyAssocOne[list.size()]);
/*     */   }
/*     */   
/*     */   private BeanPropertyAssocMany<?>[] getMany2Many() {
/* 334 */     ArrayList<BeanPropertyAssocMany<?>> list = new ArrayList<BeanPropertyAssocMany<?>>();
/* 335 */     for (int i = 0; i < this.manys.size(); i++) {
/* 336 */       BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)this.manys.get(i);
/* 337 */       if (prop.isManyToMany()) {
/* 338 */         list.add(prop);
/*     */       }
/*     */     } 
/*     */     
/* 342 */     return (BeanPropertyAssocMany<?>[])list.<BeanPropertyAssocMany>toArray(new BeanPropertyAssocMany[list.size()]);
/*     */   }
/*     */   
/*     */   private BeanPropertyAssocMany<?>[] getMany(Mode mode) {
/* 346 */     ArrayList<BeanPropertyAssocMany<?>> list = new ArrayList<BeanPropertyAssocMany<?>>();
/* 347 */     for (int i = 0; i < this.manys.size(); i++) {
/* 348 */       BeanPropertyAssocMany<?> prop = (BeanPropertyAssocMany)this.manys.get(i);
/*     */       
/* 350 */       switch (mode) {
/*     */         case Save:
/* 352 */           if (prop.getCascadeInfo().isSave() || prop.isManyToMany() || BeanCollection.ModifyListenMode.REMOVALS.equals(prop.getModifyListenMode()))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 357 */             list.add(prop);
/*     */           }
/*     */           break;
/*     */         case Delete:
/* 361 */           if (prop.getCascadeInfo().isDelete() || BeanCollection.ModifyListenMode.REMOVALS.equals(prop.getModifyListenMode()))
/*     */           {
/*     */             
/* 364 */             list.add(prop);
/*     */           }
/*     */           break;
/*     */         case Validate:
/* 368 */           if (prop.getCascadeInfo().isValidate()) {
/* 369 */             list.add(prop);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 378 */     return (BeanPropertyAssocMany<?>[])list.<BeanPropertyAssocMany>toArray(new BeanPropertyAssocMany[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty createBeanProperty(BeanDescriptorMap owner, DeployBeanProperty deployProp) {
/* 384 */     if (deployProp instanceof DeployBeanPropertyAssocOne)
/*     */     {
/* 386 */       return (BeanProperty)new BeanPropertyAssocOne(owner, this.desc, (DeployBeanPropertyAssocOne)deployProp);
/*     */     }
/* 388 */     if (deployProp instanceof DeployBeanPropertySimpleCollection)
/*     */     {
/* 390 */       return (BeanProperty)new BeanPropertySimpleCollection(owner, this.desc, (DeployBeanPropertySimpleCollection)deployProp);
/*     */     }
/* 392 */     if (deployProp instanceof DeployBeanPropertyAssocMany)
/*     */     {
/* 394 */       return (BeanProperty)new BeanPropertyAssocMany(owner, this.desc, (DeployBeanPropertyAssocMany)deployProp);
/*     */     }
/* 396 */     if (deployProp instanceof DeployBeanPropertyCompound)
/*     */     {
/* 398 */       return (BeanProperty)new BeanPropertyCompound(owner, this.desc, (DeployBeanPropertyCompound)deployProp);
/*     */     }
/*     */     
/* 401 */     return new BeanProperty(owner, this.desc, deployProp);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyLists.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */