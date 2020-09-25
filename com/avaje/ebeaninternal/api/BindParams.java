/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.querydefn.NaturalKeyBindParam;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class BindParams
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4541081933302086285L;
/*  44 */   private ArrayList<Param> positionedParameters = new ArrayList<Param>();
/*     */   
/*  46 */   private HashMap<String, Param> namedParameters = new HashMap<String, Param>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private int queryPlanHash = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String preparedSql;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BindParams copy() {
/*  64 */     BindParams copy = new BindParams();
/*  65 */     for (Param p : this.positionedParameters) {
/*  66 */       copy.positionedParameters.add(p.copy());
/*     */     }
/*  68 */     Iterator<Map.Entry<String, Param>> it = this.namedParameters.entrySet().iterator();
/*  69 */     while (it.hasNext()) {
/*  70 */       Map.Entry<String, Param> entry = it.next();
/*  71 */       copy.namedParameters.put(entry.getKey(), ((Param)entry.getValue()).copy());
/*     */     } 
/*  73 */     return copy;
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/*  77 */     int hc = this.namedParameters.hashCode();
/*  78 */     for (int i = 0; i < this.positionedParameters.size(); i++) {
/*  79 */       hc = hc * 31 + ((Param)this.positionedParameters.get(i)).hashCode();
/*     */     }
/*  81 */     return hc;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  85 */     int hc = getClass().hashCode();
/*  86 */     hc = hc * 31 + this.namedParameters.hashCode();
/*  87 */     for (int i = 0; i < this.positionedParameters.size(); i++) {
/*  88 */       hc = hc * 31 + ((Param)this.positionedParameters.get(i)).hashCode();
/*     */     }
/*  90 */     hc = hc * 31 + ((this.preparedSql == null) ? 0 : this.preparedSql.hashCode());
/*  91 */     return hc;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*  95 */     if (o == null) {
/*  96 */       return false;
/*     */     }
/*  98 */     if (o == this) {
/*  99 */       return true;
/*     */     }
/* 101 */     if (o instanceof BindParams) {
/* 102 */       return (hashCode() == o.hashCode());
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 111 */     return (this.positionedParameters.isEmpty() && this.namedParameters.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NaturalKeyBindParam getNaturalKeyBindParam() {
/* 118 */     if (this.positionedParameters != null) {
/* 119 */       return null;
/*     */     }
/* 121 */     if (this.namedParameters != null && this.namedParameters.size() == 1) {
/* 122 */       Map.Entry<String, Param> e = this.namedParameters.entrySet().iterator().next();
/* 123 */       return new NaturalKeyBindParam(e.getKey(), ((Param)e.getValue()).getInValue());
/*     */     } 
/* 125 */     return null;
/*     */   }
/*     */   
/*     */   public int size() {
/* 129 */     return this.positionedParameters.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requiresNamedParamsPrepare() {
/* 138 */     return (!this.namedParameters.isEmpty() && this.positionedParameters.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullParameter(int position, int jdbcType) {
/* 145 */     Param p = getParam(position);
/* 146 */     p.setInNullType(jdbcType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(int position, Object value, int outType) {
/* 154 */     addToQueryPlanHash(String.valueOf(position), value);
/*     */     
/* 156 */     Param p = getParam(position);
/* 157 */     p.setInValue(value);
/* 158 */     p.setOutType(outType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(int position, Object value) {
/* 167 */     addToQueryPlanHash(String.valueOf(position), value);
/*     */     
/* 169 */     Param p = getParam(position);
/* 170 */     p.setInValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOut(int position, int outType) {
/* 177 */     Param p = getParam(position);
/* 178 */     p.setOutType(outType);
/*     */   }
/*     */   
/*     */   private Param getParam(String name) {
/* 182 */     Param p = this.namedParameters.get(name);
/* 183 */     if (p == null) {
/* 184 */       p = new Param();
/* 185 */       this.namedParameters.put(name, p);
/*     */     } 
/* 187 */     return p;
/*     */   }
/*     */   
/*     */   private Param getParam(int position) {
/* 191 */     int more = position - this.positionedParameters.size();
/* 192 */     if (more > 0) {
/* 193 */       for (int i = 0; i < more; i++) {
/* 194 */         this.positionedParameters.add(new Param());
/*     */       }
/*     */     }
/* 197 */     return this.positionedParameters.get(position - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(String name, Object value, int outType) {
/* 205 */     addToQueryPlanHash(name, value);
/*     */     
/* 207 */     Param p = getParam(name);
/* 208 */     p.setInValue(value);
/* 209 */     p.setOutType(outType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullParameter(String name, int jdbcType) {
/* 216 */     Param p = getParam(name);
/* 217 */     p.setInNullType(jdbcType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Param setParameter(String name, Object value) {
/* 225 */     addToQueryPlanHash(name, value);
/*     */     
/* 227 */     Param p = getParam(name);
/* 228 */     p.setInValue(value);
/* 229 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToQueryPlanHash(String name, Object value) {
/* 236 */     if (value != null && 
/* 237 */       value instanceof Collection) {
/* 238 */       this.queryPlanHash = this.queryPlanHash * 31 + name.hashCode();
/* 239 */       this.queryPlanHash = this.queryPlanHash * 31 + ((Collection)value).size();
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
/*     */   public int getQueryPlanHash() {
/* 253 */     return this.queryPlanHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Param setEncryptionKey(String name, Object value) {
/* 263 */     Param p = getParam(name);
/* 264 */     p.setEncryptionKey(value);
/* 265 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOut(String name, int outType) {
/* 272 */     Param p = getParam(name);
/* 273 */     p.setOutType(outType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Param getParameter(int position) {
/* 281 */     return getParam(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Param getParameter(String name) {
/* 288 */     return getParam(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Param> positionedParameters() {
/* 295 */     return this.positionedParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreparedSql(String preparedSql) {
/* 302 */     this.preparedSql = preparedSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreparedSql() {
/* 310 */     return this.preparedSql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class OrderedList
/*     */   {
/*     */     final List<BindParams.Param> paramList;
/*     */ 
/*     */ 
/*     */     
/*     */     final StringBuilder preparedSql;
/*     */ 
/*     */ 
/*     */     
/*     */     public OrderedList() {
/* 327 */       this(new ArrayList<BindParams.Param>());
/*     */     }
/*     */     
/*     */     public OrderedList(List<BindParams.Param> paramList) {
/* 331 */       this.paramList = paramList;
/* 332 */       this.preparedSql = new StringBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(BindParams.Param param) {
/* 339 */       this.paramList.add(param);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 346 */       return this.paramList.size();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<BindParams.Param> list() {
/* 353 */       return this.paramList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void appendSql(String parsedSql) {
/* 360 */       this.preparedSql.append(parsedSql);
/*     */     }
/*     */     
/*     */     public String getPreparedSql() {
/* 364 */       return this.preparedSql.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Param
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     private boolean encryptionKey;
/*     */ 
/*     */     
/*     */     private boolean isInParam;
/*     */ 
/*     */     
/*     */     private boolean isOutParam;
/*     */ 
/*     */     
/*     */     private int type;
/*     */ 
/*     */     
/*     */     private Object inValue;
/*     */ 
/*     */     
/*     */     private Object outValue;
/*     */ 
/*     */     
/*     */     private int textLocation;
/*     */ 
/*     */ 
/*     */     
/*     */     public Param copy() {
/* 399 */       Param copy = new Param();
/* 400 */       copy.isInParam = this.isInParam;
/* 401 */       copy.isOutParam = this.isOutParam;
/* 402 */       copy.type = this.type;
/* 403 */       copy.inValue = this.inValue;
/* 404 */       copy.outValue = this.outValue;
/* 405 */       return copy;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 409 */       int hc = getClass().hashCode();
/* 410 */       hc = hc * 31 + (this.isInParam ? 0 : 1);
/* 411 */       hc = hc * 31 + (this.isOutParam ? 0 : 1);
/* 412 */       hc = hc * 31 + this.type;
/* 413 */       hc = hc * 31 + ((this.inValue == null) ? 0 : this.inValue.hashCode());
/* 414 */       return hc;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 418 */       if (o == null) {
/* 419 */         return false;
/*     */       }
/* 421 */       if (o == this) {
/* 422 */         return true;
/*     */       }
/* 424 */       if (o instanceof Param) {
/* 425 */         return (hashCode() == o.hashCode());
/*     */       }
/* 427 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isInParam() {
/* 435 */       return this.isInParam;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isOutParam() {
/* 443 */       return this.isOutParam;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getType() {
/* 451 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOutType(int type) {
/* 458 */       this.type = type;
/* 459 */       this.isOutParam = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInValue(Object in) {
/* 466 */       this.inValue = in;
/* 467 */       this.isInParam = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setEncryptionKey(Object in) {
/* 474 */       this.inValue = in;
/* 475 */       this.isInParam = true;
/* 476 */       this.encryptionKey = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInNullType(int type) {
/* 484 */       this.type = type;
/* 485 */       this.inValue = null;
/* 486 */       this.isInParam = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getOutValue() {
/* 494 */       return this.outValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getInValue() {
/* 502 */       return this.inValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOutValue(Object out) {
/* 510 */       this.outValue = out;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTextLocation() {
/* 517 */       return this.textLocation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTextLocation(int textLocation) {
/* 525 */       this.textLocation = textLocation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEncryptionKey() {
/* 532 */       return this.encryptionKey;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\BindParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */