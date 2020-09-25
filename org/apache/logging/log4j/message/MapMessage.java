/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.util.EnglishEnums;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapMessage
/*     */   implements MultiformatMessage
/*     */ {
/*     */   private static final long serialVersionUID = -5031471831131487120L;
/*     */   private final SortedMap<String, String> data;
/*     */   
/*     */   public enum MapFormat
/*     */   {
/*  36 */     XML,
/*     */     
/*  38 */     JSON,
/*     */     
/*  40 */     JAVA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapMessage() {
/*  51 */     this.data = new TreeMap<String, String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapMessage(Map<String, String> map) {
/*  59 */     this.data = (map instanceof SortedMap) ? (SortedMap<String, String>)map : new TreeMap<String, String>(map);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getFormats() {
/*  64 */     String[] formats = new String[(MapFormat.values()).length];
/*  65 */     int i = 0;
/*  66 */     for (MapFormat format : MapFormat.values()) {
/*  67 */       formats[i++] = format.name();
/*     */     }
/*  69 */     return formats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getParameters() {
/*  78 */     return this.data.values().toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormat() {
/*  87 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getData() {
/*  95 */     return Collections.unmodifiableMap(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 102 */     this.data.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String key, String value) {
/* 111 */     if (value == null) {
/* 112 */       throw new IllegalArgumentException("No value provided for key " + key);
/*     */     }
/* 114 */     validate(key, value);
/* 115 */     this.data.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validate(String key, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<String, String> map) {
/* 127 */     this.data.putAll(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String get(String key) {
/* 136 */     return this.data.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remove(String key) {
/* 145 */     return this.data.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String asString() {
/* 154 */     return asString((MapFormat)null);
/*     */   }
/*     */   
/*     */   public String asString(String format) {
/*     */     try {
/* 159 */       return asString((MapFormat)EnglishEnums.valueOf(MapFormat.class, format));
/* 160 */     } catch (IllegalArgumentException ex) {
/* 161 */       return asString();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String asString(MapFormat format) {
/* 171 */     StringBuilder sb = new StringBuilder();
/* 172 */     if (format == null)
/* 173 */     { appendMap(sb); }
/*     */     else
/* 175 */     { switch (format)
/*     */       { case XML:
/* 177 */           asXML(sb);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 193 */           return sb.toString();case JSON: asJSON(sb); return sb.toString();case JAVA: asJava(sb); return sb.toString(); }  appendMap(sb); }  return sb.toString();
/*     */   }
/*     */   
/*     */   public void asXML(StringBuilder sb) {
/* 197 */     sb.append("<Map>\n");
/* 198 */     for (Map.Entry<String, String> entry : this.data.entrySet()) {
/* 199 */       sb.append("  <Entry key=\"").append(entry.getKey()).append("\">").append(entry.getValue()).append("</Entry>\n");
/*     */     }
/*     */     
/* 202 */     sb.append("</Map>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedMessage() {
/* 211 */     return asString();
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
/*     */   public String getFormattedMessage(String[] formats) {
/* 224 */     if (formats == null || formats.length == 0) {
/* 225 */       return asString();
/*     */     }
/* 227 */     for (String format : formats) {
/* 228 */       for (MapFormat mapFormat : MapFormat.values()) {
/* 229 */         if (mapFormat.name().equalsIgnoreCase(format)) {
/* 230 */           return asString(mapFormat);
/*     */         }
/*     */       } 
/*     */     } 
/* 234 */     return asString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendMap(StringBuilder sb) {
/* 239 */     boolean first = true;
/* 240 */     for (Map.Entry<String, String> entry : this.data.entrySet()) {
/* 241 */       if (!first) {
/* 242 */         sb.append(" ");
/*     */       }
/* 244 */       first = false;
/* 245 */       sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void asJSON(StringBuilder sb) {
/* 250 */     boolean first = true;
/* 251 */     sb.append("{");
/* 252 */     for (Map.Entry<String, String> entry : this.data.entrySet()) {
/* 253 */       if (!first) {
/* 254 */         sb.append(", ");
/*     */       }
/* 256 */       first = false;
/* 257 */       sb.append("\"").append(entry.getKey()).append("\":");
/* 258 */       sb.append("\"").append(entry.getValue()).append("\"");
/*     */     } 
/* 260 */     sb.append("}");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void asJava(StringBuilder sb) {
/* 265 */     boolean first = true;
/* 266 */     sb.append("{");
/* 267 */     for (Map.Entry<String, String> entry : this.data.entrySet()) {
/* 268 */       if (!first) {
/* 269 */         sb.append(", ");
/*     */       }
/* 271 */       first = false;
/* 272 */       sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
/*     */     } 
/* 274 */     sb.append("}");
/*     */   }
/*     */   
/*     */   public MapMessage newInstance(Map<String, String> map) {
/* 278 */     return new MapMessage(map);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 283 */     return asString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 288 */     if (this == o) {
/* 289 */       return true;
/*     */     }
/* 291 */     if (o == null || getClass() != o.getClass()) {
/* 292 */       return false;
/*     */     }
/*     */     
/* 295 */     MapMessage that = (MapMessage)o;
/*     */     
/* 297 */     return this.data.equals(that.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 302 */     return this.data.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 312 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\MapMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */