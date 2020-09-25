/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SafeRepresenter
/*     */   extends BaseRepresenter
/*     */ {
/*     */   protected Map<Class<? extends Object>, Tag> classTags;
/*  44 */   protected TimeZone timeZone = null;
/*     */   
/*     */   public SafeRepresenter() {
/*  47 */     this.nullRepresenter = new RepresentNull();
/*  48 */     this.representers.put(String.class, new RepresentString());
/*  49 */     this.representers.put(Boolean.class, new RepresentBoolean());
/*  50 */     this.representers.put(Character.class, new RepresentString());
/*  51 */     this.representers.put(byte[].class, new RepresentByteArray());
/*  52 */     this.multiRepresenters.put(Number.class, new RepresentNumber());
/*  53 */     this.multiRepresenters.put(List.class, new RepresentList());
/*  54 */     this.multiRepresenters.put(Map.class, new RepresentMap());
/*  55 */     this.multiRepresenters.put(Set.class, new RepresentSet());
/*  56 */     this.multiRepresenters.put(Iterator.class, new RepresentIterator());
/*  57 */     this.multiRepresenters.put((new Object[0]).getClass(), new RepresentArray());
/*  58 */     this.multiRepresenters.put(Date.class, new RepresentDate());
/*  59 */     this.multiRepresenters.put(Enum.class, new RepresentEnum());
/*  60 */     this.multiRepresenters.put(Calendar.class, new RepresentDate());
/*  61 */     this.classTags = new HashMap<Class<? extends Object>, Tag>();
/*     */   }
/*     */   
/*     */   protected Tag getTag(Class<?> clazz, Tag defaultTag) {
/*  65 */     if (this.classTags.containsKey(clazz)) {
/*  66 */       return this.classTags.get(clazz);
/*     */     }
/*  68 */     return defaultTag;
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
/*     */   
/*     */   public Tag addClassTag(Class<? extends Object> clazz, String tag) {
/*  84 */     return addClassTag(clazz, new Tag(tag));
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
/*     */   public Tag addClassTag(Class<? extends Object> clazz, Tag tag) {
/*  98 */     if (tag == null) {
/*  99 */       throw new NullPointerException("Tag must be provided.");
/*     */     }
/* 101 */     return this.classTags.put(clazz, tag);
/*     */   }
/*     */   
/*     */   protected class RepresentNull implements Represent {
/*     */     public Node representData(Object data) {
/* 106 */       return SafeRepresenter.this.representScalar(Tag.NULL, "null");
/*     */     }
/*     */   }
/*     */   
/* 110 */   public static Pattern MULTILINE_PATTERN = Pattern.compile("\n|| | ");
/*     */   
/*     */   protected class RepresentString implements Represent {
/*     */     public Node representData(Object data) {
/* 114 */       Tag tag = Tag.STR;
/* 115 */       Character style = null;
/* 116 */       String value = data.toString();
/* 117 */       if (StreamReader.NON_PRINTABLE.matcher(value).find()) {
/* 118 */         char[] binary; tag = Tag.BINARY;
/*     */         
/*     */         try {
/* 121 */           binary = Base64Coder.encode(value.getBytes("UTF-8"));
/* 122 */         } catch (UnsupportedEncodingException e) {
/* 123 */           throw new YAMLException(e);
/*     */         } 
/* 125 */         value = String.valueOf(binary);
/* 126 */         style = Character.valueOf('|');
/*     */       } 
/*     */ 
/*     */       
/* 130 */       if (SafeRepresenter.this.defaultScalarStyle == null && SafeRepresenter.MULTILINE_PATTERN.matcher(value).find()) {
/* 131 */         style = Character.valueOf('|');
/*     */       }
/* 133 */       return SafeRepresenter.this.representScalar(tag, value, style);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentBoolean implements Represent {
/*     */     public Node representData(Object data) {
/*     */       String value;
/* 140 */       if (Boolean.TRUE.equals(data)) {
/* 141 */         value = "true";
/*     */       } else {
/* 143 */         value = "false";
/*     */       } 
/* 145 */       return SafeRepresenter.this.representScalar(Tag.BOOL, value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentNumber implements Represent {
/*     */     public Node representData(Object data) {
/*     */       Tag tag;
/*     */       String value;
/* 153 */       if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof java.math.BigInteger) {
/*     */         
/* 155 */         tag = Tag.INT;
/* 156 */         value = data.toString();
/*     */       } else {
/* 158 */         Number number = (Number)data;
/* 159 */         tag = Tag.FLOAT;
/* 160 */         if (number.equals(Double.valueOf(Double.NaN))) {
/* 161 */           value = ".NaN";
/* 162 */         } else if (number.equals(Double.valueOf(Double.POSITIVE_INFINITY))) {
/* 163 */           value = ".inf";
/* 164 */         } else if (number.equals(Double.valueOf(Double.NEGATIVE_INFINITY))) {
/* 165 */           value = "-.inf";
/*     */         } else {
/* 167 */           value = number.toString();
/*     */         } 
/*     */       } 
/* 170 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentList
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 177 */       return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (List)data, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentIterator
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 184 */       Iterator<Object> iter = (Iterator<Object>)data;
/* 185 */       return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new SafeRepresenter.IteratorWrapper(iter), null);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IteratorWrapper
/*     */     implements Iterable<Object> {
/*     */     private Iterator<Object> iter;
/*     */     
/*     */     public IteratorWrapper(Iterator<Object> iter) {
/* 194 */       this.iter = iter;
/*     */     }
/*     */     
/*     */     public Iterator<Object> iterator() {
/* 198 */       return this.iter;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentArray implements Represent {
/*     */     public Node representData(Object data) {
/* 204 */       Object[] array = (Object[])data;
/* 205 */       List<Object> list = Arrays.asList(array);
/* 206 */       return SafeRepresenter.this.representSequence(Tag.SEQ, list, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentMap
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 213 */       return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map<? extends Object, Object>)data, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentSet
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 221 */       Map<Object, Object> value = new LinkedHashMap<Object, Object>();
/* 222 */       Set<Object> set = (Set<Object>)data;
/* 223 */       for (Object key : set) {
/* 224 */         value.put(key, null);
/*     */       }
/* 226 */       return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentDate
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/*     */       Calendar calendar;
/* 234 */       if (data instanceof Calendar) {
/* 235 */         calendar = (Calendar)data;
/*     */       } else {
/* 237 */         calendar = Calendar.getInstance((SafeRepresenter.this.getTimeZone() == null) ? TimeZone.getTimeZone("UTC") : SafeRepresenter.this.timeZone);
/*     */         
/* 239 */         calendar.setTime((Date)data);
/*     */       } 
/* 241 */       int years = calendar.get(1);
/* 242 */       int months = calendar.get(2) + 1;
/* 243 */       int days = calendar.get(5);
/* 244 */       int hour24 = calendar.get(11);
/* 245 */       int minutes = calendar.get(12);
/* 246 */       int seconds = calendar.get(13);
/* 247 */       int millis = calendar.get(14);
/* 248 */       StringBuilder buffer = new StringBuilder(String.valueOf(years));
/* 249 */       while (buffer.length() < 4)
/*     */       {
/* 251 */         buffer.insert(0, "0");
/*     */       }
/* 253 */       buffer.append("-");
/* 254 */       if (months < 10) {
/* 255 */         buffer.append("0");
/*     */       }
/* 257 */       buffer.append(String.valueOf(months));
/* 258 */       buffer.append("-");
/* 259 */       if (days < 10) {
/* 260 */         buffer.append("0");
/*     */       }
/* 262 */       buffer.append(String.valueOf(days));
/* 263 */       buffer.append("T");
/* 264 */       if (hour24 < 10) {
/* 265 */         buffer.append("0");
/*     */       }
/* 267 */       buffer.append(String.valueOf(hour24));
/* 268 */       buffer.append(":");
/* 269 */       if (minutes < 10) {
/* 270 */         buffer.append("0");
/*     */       }
/* 272 */       buffer.append(String.valueOf(minutes));
/* 273 */       buffer.append(":");
/* 274 */       if (seconds < 10) {
/* 275 */         buffer.append("0");
/*     */       }
/* 277 */       buffer.append(String.valueOf(seconds));
/* 278 */       if (millis > 0) {
/* 279 */         if (millis < 10) {
/* 280 */           buffer.append(".00");
/* 281 */         } else if (millis < 100) {
/* 282 */           buffer.append(".0");
/*     */         } else {
/* 284 */           buffer.append(".");
/*     */         } 
/* 286 */         buffer.append(String.valueOf(millis));
/*     */       } 
/* 288 */       if (TimeZone.getTimeZone("UTC").equals(calendar.getTimeZone())) {
/* 289 */         buffer.append("Z");
/*     */       } else {
/*     */         
/* 292 */         int gmtOffset = calendar.getTimeZone().getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
/*     */ 
/*     */ 
/*     */         
/* 296 */         int minutesOffset = gmtOffset / 60000;
/* 297 */         int hoursOffset = minutesOffset / 60;
/* 298 */         int partOfHour = minutesOffset % 60;
/* 299 */         buffer.append(((hoursOffset > 0) ? "+" : "") + hoursOffset + ":" + ((partOfHour < 10) ? ("0" + partOfHour) : (String)Integer.valueOf(partOfHour)));
/*     */       } 
/*     */       
/* 302 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentEnum implements Represent {
/*     */     public Node representData(Object data) {
/* 308 */       Tag tag = new Tag(data.getClass());
/* 309 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), ((Enum)data).name());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentByteArray implements Represent {
/*     */     public Node representData(Object data) {
/* 315 */       char[] binary = Base64Coder.encode((byte[])data);
/* 316 */       return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), Character.valueOf('|'));
/*     */     }
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 321 */     return this.timeZone;
/*     */   }
/*     */   
/*     */   public void setTimeZone(TimeZone timeZone) {
/* 325 */     this.timeZone = timeZone;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\representer\SafeRepresenter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */