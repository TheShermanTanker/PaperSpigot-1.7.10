/*     */ package com.avaje.ebean.bean;
/*     */ 
/*     */ import com.avaje.ebean.Ebean;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.persistence.EntityNotFoundException;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EntityBeanIntercept
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3664031775464862648L;
/*     */   private transient NodeUsageCollector nodeUsageCollector;
/*     */   private transient PropertyChangeSupport pcs;
/*     */   private transient PersistenceContext persistenceContext;
/*     */   private transient BeanLoader beanLoader;
/*     */   private int beanLoaderIndex;
/*     */   private String ebeanServerName;
/*     */   private EntityBean owner;
/*     */   private Object parentBean;
/*     */   private volatile boolean loaded;
/*     */   private boolean disableLazyLoad;
/*     */   private boolean lazyLoadFailure;
/*     */   private boolean intercepting;
/*     */   private boolean readOnly;
/*     */   private Object oldValues;
/*     */   private volatile Set<String> loadedProps;
/*     */   private HashSet<String> changedProps;
/*     */   private String lazyLoadProperty;
/*     */   
/*     */   public EntityBeanIntercept(Object owner) {
/* 124 */     this.owner = (EntityBean)owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyStateTo(EntityBeanIntercept dest) {
/* 131 */     dest.loadedProps = this.loadedProps;
/* 132 */     dest.ebeanServerName = this.ebeanServerName;
/*     */     
/* 134 */     if (this.loaded) {
/* 135 */       dest.setLoaded();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityBean getOwner() {
/* 143 */     return this.owner;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 147 */     if (!this.loaded) {
/* 148 */       return "Reference...";
/*     */     }
/* 150 */     return "OldValues: " + this.oldValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistenceContext getPersistenceContext() {
/* 157 */     return this.persistenceContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistenceContext(PersistenceContext persistenceContext) {
/* 164 */     this.persistenceContext = persistenceContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener listener) {
/* 171 */     if (this.pcs == null) {
/* 172 */       this.pcs = new PropertyChangeSupport(this.owner);
/*     */     }
/* 174 */     this.pcs.addPropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
/* 181 */     if (this.pcs == null) {
/* 182 */       this.pcs = new PropertyChangeSupport(this.owner);
/*     */     }
/* 184 */     this.pcs.addPropertyChangeListener(propertyName, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener listener) {
/* 191 */     if (this.pcs != null) {
/* 192 */       this.pcs.removePropertyChangeListener(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
/* 200 */     if (this.pcs != null) {
/* 201 */       this.pcs.removePropertyChangeListener(propertyName, listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNodeUsageCollector(NodeUsageCollector usageCollector) {
/* 209 */     this.nodeUsageCollector = usageCollector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getParentBean() {
/* 216 */     return this.parentBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentBean(Object parentBean) {
/* 224 */     this.parentBean = parentBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBeanLoaderIndex() {
/* 231 */     return this.beanLoaderIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeanLoaderByServerName(String ebeanServerName) {
/* 241 */     this.beanLoaderIndex = 0;
/* 242 */     this.beanLoader = null;
/* 243 */     this.ebeanServerName = ebeanServerName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeanLoader(int index, BeanLoader beanLoader, PersistenceContext ctx) {
/* 250 */     this.beanLoaderIndex = index;
/* 251 */     this.beanLoader = beanLoader;
/* 252 */     this.persistenceContext = ctx;
/* 253 */     this.ebeanServerName = beanLoader.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 262 */     if (this.oldValues != null) {
/* 263 */       return true;
/*     */     }
/*     */     
/* 266 */     return this.owner._ebean_isEmbeddedNewOrDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNew() {
/* 273 */     return (!this.intercepting && !this.loaded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNewOrDirty() {
/* 280 */     return (isNew() || isDirty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReference() {
/* 287 */     return (this.intercepting && !this.loaded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference() {
/* 294 */     this.loaded = false;
/* 295 */     this.intercepting = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOldValues() {
/* 302 */     return this.oldValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 310 */     return this.readOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 318 */     this.readOnly = readOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntercepting() {
/* 328 */     return this.intercepting;
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
/*     */   public void setIntercepting(boolean intercepting) {
/* 340 */     this.intercepting = intercepting;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoaded() {
/* 347 */     return this.loaded;
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
/*     */   
/*     */   public void setLoaded() {
/* 362 */     this.loaded = true;
/* 363 */     this.oldValues = null;
/* 364 */     this.intercepting = true;
/* 365 */     this.owner._ebean_setEmbeddedLoaded();
/* 366 */     this.lazyLoadProperty = null;
/* 367 */     this.changedProps = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadedLazy() {
/* 375 */     this.loaded = true;
/* 376 */     this.intercepting = true;
/* 377 */     this.lazyLoadProperty = null;
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
/*     */   public void setLazyLoadFailure() {
/* 390 */     this.lazyLoadFailure = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLazyLoadFailure() {
/* 397 */     return this.lazyLoadFailure;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisableLazyLoad() {
/* 404 */     return this.disableLazyLoad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisableLazyLoad(boolean disableLazyLoad) {
/* 414 */     this.disableLazyLoad = disableLazyLoad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmbeddedLoaded(Object embeddedBean) {
/* 421 */     if (embeddedBean instanceof EntityBean) {
/* 422 */       EntityBean eb = (EntityBean)embeddedBean;
/* 423 */       eb._ebean_getIntercept().setLoaded();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmbeddedNewOrDirty(Object embeddedBean) {
/* 432 */     if (embeddedBean == null)
/*     */     {
/*     */       
/* 435 */       return false;
/*     */     }
/* 437 */     if (embeddedBean instanceof EntityBean) {
/* 438 */       return ((EntityBean)embeddedBean)._ebean_getIntercept().isNewOrDirty();
/*     */     }
/*     */ 
/*     */     
/* 442 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadedProps(Set<String> loadedPropertyNames) {
/* 453 */     this.loadedProps = loadedPropertyNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getLoadedProps() {
/* 460 */     return this.loadedProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getChangedProps() {
/* 467 */     return this.changedProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLazyLoadProperty() {
/* 474 */     return this.lazyLoadProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadBean(String loadProperty) {
/* 482 */     synchronized (this) {
/* 483 */       if (this.beanLoader == null) {
/* 484 */         BeanLoader serverLoader = (BeanLoader)Ebean.getServer(this.ebeanServerName);
/* 485 */         if (serverLoader == null) {
/* 486 */           throw new PersistenceException("Server [" + this.ebeanServerName + "] was not found?");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 491 */         loadBeanInternal(loadProperty, serverLoader);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 496 */     synchronized (this.beanLoader) {
/*     */ 
/*     */       
/* 499 */       loadBeanInternal(loadProperty, this.beanLoader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadBeanInternal(String loadProperty, BeanLoader loader) {
/* 508 */     if (this.loaded && (this.loadedProps == null || this.loadedProps.contains(loadProperty))) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 513 */     if (this.disableLazyLoad) {
/* 514 */       this.loaded = true;
/*     */       
/*     */       return;
/*     */     } 
/* 518 */     if (this.lazyLoadFailure)
/*     */     {
/* 520 */       throw new EntityNotFoundException("Bean has been deleted - lazy loading failed");
/*     */     }
/*     */     
/* 523 */     if (this.lazyLoadProperty == null) {
/*     */       
/* 525 */       this.lazyLoadProperty = loadProperty;
/*     */       
/* 527 */       if (this.nodeUsageCollector != null) {
/* 528 */         this.nodeUsageCollector.setLoadProperty(this.lazyLoadProperty);
/*     */       }
/*     */       
/* 531 */       loader.loadBean(this);
/*     */       
/* 533 */       if (this.lazyLoadFailure)
/*     */       {
/* 535 */         throw new EntityNotFoundException("Bean has been deleted - lazy loading failed");
/*     */       }
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
/*     */   protected void createOldValues() {
/* 550 */     this.oldValues = this.owner._ebean_createCopy();
/*     */     
/* 552 */     if (this.nodeUsageCollector != null) {
/* 553 */       this.nodeUsageCollector.setModified();
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
/*     */   
/*     */   public Object writeReplaceIntercept() throws ObjectStreamException {
/* 569 */     if (!SerializeControl.isVanillaBeans()) {
/* 570 */       return this.owner;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 575 */     return this.owner._ebean_createCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean areEqual(Object obj1, Object obj2) {
/* 583 */     if (obj1 == null) {
/* 584 */       return (obj2 == null);
/*     */     }
/* 586 */     if (obj2 == null) {
/* 587 */       return false;
/*     */     }
/* 589 */     if (obj1 == obj2) {
/* 590 */       return true;
/*     */     }
/* 592 */     if (obj1 instanceof java.math.BigDecimal) {
/*     */ 
/*     */       
/* 595 */       if (obj2 instanceof java.math.BigDecimal) {
/* 596 */         Comparable<Object> com1 = (Comparable)obj1;
/* 597 */         return (com1.compareTo(obj2) == 0);
/*     */       } 
/*     */       
/* 600 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 604 */     if (obj1 instanceof java.net.URL)
/*     */     {
/* 606 */       return obj1.toString().equals(obj2.toString());
/*     */     }
/* 608 */     return obj1.equals(obj2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preGetter(String propertyName) {
/* 618 */     if (!this.intercepting) {
/*     */       return;
/*     */     }
/*     */     
/* 622 */     if (!this.loaded) {
/* 623 */       loadBean(propertyName);
/* 624 */     } else if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
/* 625 */       loadBean(propertyName);
/*     */     } 
/*     */     
/* 628 */     if (this.nodeUsageCollector != null && this.loaded) {
/* 629 */       this.nodeUsageCollector.addUsed(propertyName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postSetter(PropertyChangeEvent event) {
/* 638 */     if (this.pcs != null && event != null) {
/* 639 */       this.pcs.firePropertyChange(event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postSetter(PropertyChangeEvent event, Object newValue) {
/* 649 */     if (this.pcs != null && event != null) {
/* 650 */       if (newValue != null && newValue.equals(event.getNewValue())) {
/* 651 */         this.pcs.firePropertyChange(event);
/*     */       } else {
/* 653 */         this.pcs.firePropertyChange(event.getPropertyName(), event.getOldValue(), newValue);
/*     */       } 
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
/*     */   public PropertyChangeEvent preSetterMany(boolean interceptField, String propertyName, Object oldValue, Object newValue) {
/* 667 */     if (this.pcs != null) {
/* 668 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/* 670 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void addDirty(String propertyName) {
/* 676 */     if (!this.intercepting) {
/*     */       return;
/*     */     }
/* 679 */     if (this.readOnly) {
/* 680 */       throw new IllegalStateException("This bean is readOnly");
/*     */     }
/*     */     
/* 683 */     if (this.loaded) {
/* 684 */       if (this.oldValues == null)
/*     */       {
/* 686 */         createOldValues();
/*     */       }
/* 688 */       if (this.changedProps == null) {
/* 689 */         this.changedProps = new HashSet<String>();
/*     */       }
/* 691 */       this.changedProps.add(propertyName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, Object oldValue, Object newValue) {
/* 701 */     boolean changed = !areEqual(oldValue, newValue);
/*     */     
/* 703 */     if (intercept && changed) {
/* 704 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 707 */     if (changed && this.pcs != null) {
/* 708 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/*     */     
/* 711 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, boolean oldValue, boolean newValue) {
/* 719 */     boolean changed = (oldValue != newValue);
/*     */     
/* 721 */     if (intercept && changed) {
/* 722 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 725 */     if (changed && this.pcs != null) {
/* 726 */       return new PropertyChangeEvent(this.owner, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
/*     */     }
/*     */     
/* 729 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, int oldValue, int newValue) {
/* 737 */     boolean changed = (oldValue != newValue);
/*     */     
/* 739 */     if (intercept && changed) {
/* 740 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 743 */     if (changed && this.pcs != null) {
/* 744 */       return new PropertyChangeEvent(this.owner, propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
/*     */     }
/* 746 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, long oldValue, long newValue) {
/* 754 */     boolean changed = (oldValue != newValue);
/*     */     
/* 756 */     if (intercept && changed) {
/* 757 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 760 */     if (changed && this.pcs != null) {
/* 761 */       return new PropertyChangeEvent(this.owner, propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
/*     */     }
/* 763 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, double oldValue, double newValue) {
/* 771 */     boolean changed = (oldValue != newValue);
/*     */     
/* 773 */     if (intercept && changed) {
/* 774 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 777 */     if (changed && this.pcs != null) {
/* 778 */       return new PropertyChangeEvent(this.owner, propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
/*     */     }
/* 780 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, float oldValue, float newValue) {
/* 788 */     boolean changed = (oldValue != newValue);
/*     */     
/* 790 */     if (intercept && changed) {
/* 791 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 794 */     if (changed && this.pcs != null) {
/* 795 */       return new PropertyChangeEvent(this.owner, propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
/*     */     }
/* 797 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, short oldValue, short newValue) {
/* 805 */     boolean changed = (oldValue != newValue);
/*     */     
/* 807 */     if (intercept && changed) {
/* 808 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 811 */     if (changed && this.pcs != null) {
/* 812 */       return new PropertyChangeEvent(this.owner, propertyName, Short.valueOf(oldValue), Short.valueOf(newValue));
/*     */     }
/* 814 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, char oldValue, char newValue) {
/* 822 */     boolean changed = (oldValue != newValue);
/*     */     
/* 824 */     if (intercept && changed) {
/* 825 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 828 */     if (changed && this.pcs != null) {
/* 829 */       return new PropertyChangeEvent(this.owner, propertyName, Character.valueOf(oldValue), Character.valueOf(newValue));
/*     */     }
/* 831 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, byte oldValue, byte newValue) {
/* 839 */     boolean changed = (oldValue != newValue);
/*     */     
/* 841 */     if (intercept && changed) {
/* 842 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 845 */     if (changed && this.pcs != null) {
/* 846 */       return new PropertyChangeEvent(this.owner, propertyName, Byte.valueOf(oldValue), Byte.valueOf(newValue));
/*     */     }
/* 848 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, char[] oldValue, char[] newValue) {
/* 856 */     boolean changed = !areEqualChars(oldValue, newValue);
/*     */     
/* 858 */     if (intercept && changed) {
/* 859 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 862 */     if (changed && this.pcs != null) {
/* 863 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/* 865 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, byte[] oldValue, byte[] newValue) {
/* 873 */     boolean changed = !areEqualBytes(oldValue, newValue);
/*     */     
/* 875 */     if (intercept && changed) {
/* 876 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 879 */     if (changed && this.pcs != null) {
/* 880 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/* 882 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean areEqualBytes(byte[] b1, byte[] b2) {
/* 888 */     if (b1 == null) {
/* 889 */       return (b2 == null);
/*     */     }
/* 891 */     if (b2 == null) {
/* 892 */       return false;
/*     */     }
/* 894 */     if (b1 == b2) {
/* 895 */       return true;
/*     */     }
/* 897 */     if (b1.length != b2.length) {
/* 898 */       return false;
/*     */     }
/* 900 */     for (int i = 0; i < b1.length; i++) {
/* 901 */       if (b1[i] != b2[i]) {
/* 902 */         return false;
/*     */       }
/*     */     } 
/* 905 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean areEqualChars(char[] b1, char[] b2) {
/* 909 */     if (b1 == null) {
/* 910 */       return (b2 == null);
/*     */     }
/* 912 */     if (b2 == null) {
/* 913 */       return false;
/*     */     }
/* 915 */     if (b1 == b2) {
/* 916 */       return true;
/*     */     }
/* 918 */     if (b1.length != b2.length) {
/* 919 */       return false;
/*     */     }
/* 921 */     for (int i = 0; i < b1.length; i++) {
/* 922 */       if (b1[i] != b2[i]) {
/* 923 */         return false;
/*     */       }
/*     */     } 
/* 926 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\EntityBeanIntercept.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */