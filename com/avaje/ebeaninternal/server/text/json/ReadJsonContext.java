/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.text.json.JsonElement;
/*     */ import com.avaje.ebean.text.json.JsonReadBeanVisitor;
/*     */ import com.avaje.ebean.text.json.JsonReadOptions;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.util.ArrayStack;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class ReadJsonContext
/*     */   extends ReadBasicJsonContext
/*     */ {
/*     */   private final Map<String, JsonReadBeanVisitor<?>> visitorMap;
/*     */   private final JsonValueAdapter valueAdapter;
/*     */   private final PathStack pathStack;
/*     */   private final ArrayStack<ReadBeanState> beanState;
/*     */   private ReadBeanState currentState;
/*     */   
/*     */   public ReadJsonContext(ReadJsonSource src, JsonValueAdapter dfltValueAdapter, JsonReadOptions options) {
/*  50 */     super(src);
/*  51 */     this.beanState = new ArrayStack();
/*  52 */     if (options == null) {
/*  53 */       this.valueAdapter = dfltValueAdapter;
/*  54 */       this.visitorMap = null;
/*  55 */       this.pathStack = null;
/*     */     } else {
/*  57 */       this.valueAdapter = getValueAdapter(dfltValueAdapter, options.getValueAdapter());
/*  58 */       this.visitorMap = options.getVisitorMap();
/*  59 */       this.pathStack = (this.visitorMap == null || this.visitorMap.isEmpty()) ? null : new PathStack();
/*     */     } 
/*     */   }
/*     */   
/*     */   private JsonValueAdapter getValueAdapter(JsonValueAdapter dfltValueAdapter, JsonValueAdapter valueAdapter) {
/*  64 */     return (valueAdapter == null) ? dfltValueAdapter : valueAdapter;
/*     */   }
/*     */   
/*     */   public JsonValueAdapter getValueAdapter() {
/*  68 */     return this.valueAdapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public String readScalarValue() {
/*  73 */     ignoreWhiteSpace();
/*     */     
/*  75 */     char prevChar = nextChar();
/*  76 */     if ('"' == prevChar) {
/*  77 */       return readQuotedValue();
/*     */     }
/*  79 */     return readUnquotedValue(prevChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushBean(Object bean, String path, BeanDescriptor<?> beanDescriptor) {
/*  84 */     this.currentState = new ReadBeanState(bean, beanDescriptor);
/*  85 */     this.beanState.push(this.currentState);
/*  86 */     if (this.pathStack != null) {
/*  87 */       this.pathStack.pushPathKey(path);
/*     */     }
/*     */   }
/*     */   
/*     */   public ReadBeanState popBeanState() {
/*  92 */     if (this.pathStack != null) {
/*  93 */       String path = (String)this.pathStack.peekWithNull();
/*  94 */       JsonReadBeanVisitor<?> beanVisitor = this.visitorMap.get(path);
/*  95 */       if (beanVisitor != null) {
/*  96 */         this.currentState.visit((JsonReadBeanVisitor)beanVisitor);
/*     */       }
/*  98 */       this.pathStack.pop();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     ReadBeanState s = this.currentState;
/*     */     
/* 106 */     this.beanState.pop();
/* 107 */     this.currentState = (ReadBeanState)this.beanState.peekWithNull();
/* 108 */     return s;
/*     */   }
/*     */   
/*     */   public void setProperty(String propertyName) {
/* 112 */     this.currentState.setLoaded(propertyName);
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
/*     */   public JsonElement readUnmappedJson(String key) {
/* 124 */     JsonElement rawJsonValue = ReadJsonRawReader.readJsonElement(this);
/* 125 */     if (this.visitorMap != null) {
/* 126 */       this.currentState.addUnmappedJson(key, rawJsonValue);
/*     */     }
/* 128 */     return rawJsonValue;
/*     */   }
/*     */   
/*     */   public static class ReadBeanState
/*     */     implements PropertyChangeListener {
/*     */     private final Object bean;
/*     */     private final BeanDescriptor<?> beanDescriptor;
/*     */     private final EntityBeanIntercept ebi;
/*     */     private final Set<String> loadedProps;
/*     */     private Map<String, JsonElement> unmapped;
/*     */     
/*     */     private ReadBeanState(Object bean, BeanDescriptor<?> beanDescriptor) {
/* 140 */       this.bean = bean;
/* 141 */       this.beanDescriptor = beanDescriptor;
/* 142 */       if (bean instanceof EntityBean) {
/* 143 */         this.ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 144 */         this.loadedProps = new HashSet<String>();
/*     */       } else {
/* 146 */         this.ebi = null;
/* 147 */         this.loadedProps = null;
/*     */       } 
/*     */     }
/*     */     public String toString() {
/* 151 */       return this.bean.getClass().getSimpleName() + " loaded:" + this.loadedProps;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLoaded(String propertyName) {
/* 158 */       if (this.ebi != null) {
/* 159 */         this.loadedProps.add(propertyName);
/*     */       }
/*     */     }
/*     */     
/*     */     private void addUnmappedJson(String key, JsonElement value) {
/* 164 */       if (this.unmapped == null) {
/* 165 */         this.unmapped = new LinkedHashMap<String, JsonElement>();
/*     */       }
/* 167 */       this.unmapped.put(key, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private <T> void visit(JsonReadBeanVisitor<T> beanVisitor) {
/* 174 */       if (this.ebi != null) {
/* 175 */         this.ebi.addPropertyChangeListener(this);
/*     */       }
/* 177 */       beanVisitor.visit(this.bean, this.unmapped);
/* 178 */       if (this.ebi != null) {
/* 179 */         this.ebi.removePropertyChangeListener(this);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setLoadedState() {
/* 184 */       if (this.ebi != null)
/*     */       {
/* 186 */         this.beanDescriptor.setLoadedProps(this.ebi, this.loadedProps);
/*     */       }
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/* 191 */       String propName = evt.getPropertyName();
/* 192 */       this.loadedProps.add(propName);
/*     */     }
/*     */     
/*     */     public Object getBean() {
/* 196 */       return this.bean;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */