/*     */ package org.yaml.snakeyaml.nodes;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.util.UriEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Tag
/*     */   implements Comparable<Tag>
/*     */ {
/*     */   public static final String PREFIX = "tag:yaml.org,2002:";
/*  33 */   public static final Tag YAML = new Tag("tag:yaml.org,2002:yaml");
/*  34 */   public static final Tag VALUE = new Tag("tag:yaml.org,2002:value");
/*  35 */   public static final Tag MERGE = new Tag("tag:yaml.org,2002:merge");
/*  36 */   public static final Tag SET = new Tag("tag:yaml.org,2002:set");
/*  37 */   public static final Tag PAIRS = new Tag("tag:yaml.org,2002:pairs");
/*  38 */   public static final Tag OMAP = new Tag("tag:yaml.org,2002:omap");
/*  39 */   public static final Tag BINARY = new Tag("tag:yaml.org,2002:binary");
/*  40 */   public static final Tag INT = new Tag("tag:yaml.org,2002:int");
/*  41 */   public static final Tag FLOAT = new Tag("tag:yaml.org,2002:float");
/*  42 */   public static final Tag TIMESTAMP = new Tag("tag:yaml.org,2002:timestamp");
/*  43 */   public static final Tag BOOL = new Tag("tag:yaml.org,2002:bool");
/*  44 */   public static final Tag NULL = new Tag("tag:yaml.org,2002:null");
/*  45 */   public static final Tag STR = new Tag("tag:yaml.org,2002:str");
/*  46 */   public static final Tag SEQ = new Tag("tag:yaml.org,2002:seq");
/*  47 */   public static final Tag MAP = new Tag("tag:yaml.org,2002:map");
/*     */ 
/*     */   
/*  50 */   public static final Map<Tag, Set<Class<?>>> COMPATIBILITY_MAP = new HashMap<Tag, Set<Class<?>>>(); static {
/*  51 */     Set<Class<?>> floatSet = new HashSet<Class<?>>();
/*  52 */     floatSet.add(Double.class);
/*  53 */     floatSet.add(Float.class);
/*  54 */     floatSet.add(BigDecimal.class);
/*  55 */     COMPATIBILITY_MAP.put(FLOAT, floatSet);
/*     */     
/*  57 */     Set<Class<?>> intSet = new HashSet<Class<?>>();
/*  58 */     intSet.add(Integer.class);
/*  59 */     intSet.add(Long.class);
/*  60 */     intSet.add(BigInteger.class);
/*  61 */     COMPATIBILITY_MAP.put(INT, intSet);
/*     */     
/*  63 */     Set<Class<?>> timestampSet = new HashSet<Class<?>>();
/*  64 */     timestampSet.add(Date.class);
/*  65 */     timestampSet.add(Date.class);
/*  66 */     timestampSet.add(Timestamp.class);
/*  67 */     COMPATIBILITY_MAP.put(TIMESTAMP, timestampSet);
/*     */   }
/*     */   
/*     */   private final String value;
/*     */   
/*     */   public Tag(String tag) {
/*  73 */     if (tag == null)
/*  74 */       throw new NullPointerException("Tag must be provided."); 
/*  75 */     if (tag.length() == 0)
/*  76 */       throw new IllegalArgumentException("Tag must not be empty."); 
/*  77 */     if (tag.trim().length() != tag.length()) {
/*  78 */       throw new IllegalArgumentException("Tag must not contain leading or trailing spaces.");
/*     */     }
/*  80 */     this.value = UriEncoder.encode(tag);
/*     */   }
/*     */   
/*     */   public Tag(Class<? extends Object> clazz) {
/*  84 */     if (clazz == null) {
/*  85 */       throw new NullPointerException("Class for tag must be provided.");
/*     */     }
/*  87 */     this.value = "tag:yaml.org,2002:" + UriEncoder.encode(clazz.getName());
/*     */   }
/*     */   
/*     */   public Tag(URI uri) {
/*  91 */     if (uri == null) {
/*  92 */       throw new NullPointerException("URI for tag must be provided.");
/*     */     }
/*  94 */     this.value = uri.toASCIIString();
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  98 */     return this.value;
/*     */   }
/*     */   
/*     */   public boolean startsWith(String prefix) {
/* 102 */     return this.value.startsWith(prefix);
/*     */   }
/*     */   
/*     */   public String getClassName() {
/* 106 */     if (!this.value.startsWith("tag:yaml.org,2002:")) {
/* 107 */       throw new YAMLException("Invalid tag: " + this.value);
/*     */     }
/* 109 */     return UriEncoder.decode(this.value.substring("tag:yaml.org,2002:".length()));
/*     */   }
/*     */   
/*     */   public int getLength() {
/* 113 */     return this.value.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 123 */     if (obj instanceof Tag)
/* 124 */       return this.value.equals(((Tag)obj).getValue()); 
/* 125 */     if (obj instanceof String && 
/* 126 */       this.value.equals(obj.toString())) {
/*     */       
/* 128 */       System.err.println("Comparing Tag and String is deprecated.");
/* 129 */       return true;
/*     */     } 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     return this.value.hashCode();
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
/*     */   public boolean isCompatible(Class<?> clazz) {
/* 150 */     Set<Class<?>> set = COMPATIBILITY_MAP.get(this);
/* 151 */     if (set != null) {
/* 152 */       return set.contains(clazz);
/*     */     }
/* 154 */     return false;
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
/*     */   public boolean matches(Class<? extends Object> clazz) {
/* 166 */     return this.value.equals("tag:yaml.org,2002:" + clazz.getName());
/*     */   }
/*     */   
/*     */   public int compareTo(Tag o) {
/* 170 */     return this.value.compareTo(o.getValue());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\nodes\Tag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */