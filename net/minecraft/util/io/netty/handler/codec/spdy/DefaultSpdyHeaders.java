/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSpdyHeaders
/*     */   extends SpdyHeaders
/*     */ {
/*     */   private static final int BUCKET_SIZE = 17;
/*     */   
/*     */   private static int hash(String name) {
/*  30 */     int h = 0;
/*  31 */     for (int i = name.length() - 1; i >= 0; i--) {
/*  32 */       char c = name.charAt(i);
/*  33 */       if (c >= 'A' && c <= 'Z') {
/*  34 */         c = (char)(c + 32);
/*     */       }
/*  36 */       h = 31 * h + c;
/*     */     } 
/*     */     
/*  39 */     if (h > 0)
/*  40 */       return h; 
/*  41 */     if (h == Integer.MIN_VALUE) {
/*  42 */       return Integer.MAX_VALUE;
/*     */     }
/*  44 */     return -h;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean eq(String name1, String name2) {
/*  49 */     int nameLen = name1.length();
/*  50 */     if (nameLen != name2.length()) {
/*  51 */       return false;
/*     */     }
/*     */     
/*  54 */     for (int i = nameLen - 1; i >= 0; i--) {
/*  55 */       char c1 = name1.charAt(i);
/*  56 */       char c2 = name2.charAt(i);
/*  57 */       if (c1 != c2) {
/*  58 */         if (c1 >= 'A' && c1 <= 'Z') {
/*  59 */           c1 = (char)(c1 + 32);
/*     */         }
/*  61 */         if (c2 >= 'A' && c2 <= 'Z') {
/*  62 */           c2 = (char)(c2 + 32);
/*     */         }
/*  64 */         if (c1 != c2) {
/*  65 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   private static int index(int hash) {
/*  73 */     return hash % 17;
/*     */   }
/*     */   
/*  76 */   private final HeaderEntry[] entries = new HeaderEntry[17];
/*  77 */   private final HeaderEntry head = new HeaderEntry(-1, null, null);
/*     */   
/*     */   DefaultSpdyHeaders() {
/*  80 */     this.head.before = this.head.after = this.head;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders add(String name, Object value) {
/*  85 */     String lowerCaseName = name.toLowerCase();
/*  86 */     SpdyCodecUtil.validateHeaderName(lowerCaseName);
/*  87 */     String strVal = toString(value);
/*  88 */     SpdyCodecUtil.validateHeaderValue(strVal);
/*  89 */     int h = hash(lowerCaseName);
/*  90 */     int i = index(h);
/*  91 */     add0(h, i, lowerCaseName, strVal);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void add0(int h, int i, String name, String value) {
/*  97 */     HeaderEntry e = this.entries[i];
/*     */     
/*  99 */     HeaderEntry newEntry = new HeaderEntry(h, name, value);
/* 100 */     newEntry.next = e;
/*     */ 
/*     */     
/* 103 */     newEntry.addBefore(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders remove(String name) {
/* 108 */     if (name == null) {
/* 109 */       throw new NullPointerException("name");
/*     */     }
/* 111 */     String lowerCaseName = name.toLowerCase();
/* 112 */     int h = hash(lowerCaseName);
/* 113 */     int i = index(h);
/* 114 */     remove0(h, i, lowerCaseName);
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   private void remove0(int h, int i, String name) {
/* 119 */     HeaderEntry e = this.entries[i];
/* 120 */     if (e == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 125 */     while (e.hash == h && eq(name, e.key)) {
/* 126 */       e.remove();
/* 127 */       HeaderEntry next = e.next;
/* 128 */       if (next != null) {
/* 129 */         this.entries[i] = next;
/* 130 */         e = next; continue;
/*     */       } 
/* 132 */       this.entries[i] = null;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     while (true) {
/* 141 */       HeaderEntry next = e.next;
/* 142 */       if (next == null) {
/*     */         break;
/*     */       }
/* 145 */       if (next.hash == h && eq(name, next.key)) {
/* 146 */         e.next = next.next;
/* 147 */         next.remove(); continue;
/*     */       } 
/* 149 */       e = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyHeaders set(String name, Object value) {
/* 156 */     String lowerCaseName = name.toLowerCase();
/* 157 */     SpdyCodecUtil.validateHeaderName(lowerCaseName);
/* 158 */     String strVal = toString(value);
/* 159 */     SpdyCodecUtil.validateHeaderValue(strVal);
/* 160 */     int h = hash(lowerCaseName);
/* 161 */     int i = index(h);
/* 162 */     remove0(h, i, lowerCaseName);
/* 163 */     add0(h, i, lowerCaseName, strVal);
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders set(String name, Iterable<?> values) {
/* 169 */     if (values == null) {
/* 170 */       throw new NullPointerException("values");
/*     */     }
/*     */     
/* 173 */     String lowerCaseName = name.toLowerCase();
/* 174 */     SpdyCodecUtil.validateHeaderName(lowerCaseName);
/*     */     
/* 176 */     int h = hash(lowerCaseName);
/* 177 */     int i = index(h);
/*     */     
/* 179 */     remove0(h, i, lowerCaseName);
/* 180 */     for (Object v : values) {
/* 181 */       if (v == null) {
/*     */         break;
/*     */       }
/* 184 */       String strVal = toString(v);
/* 185 */       SpdyCodecUtil.validateHeaderValue(strVal);
/* 186 */       add0(h, i, lowerCaseName, strVal);
/*     */     } 
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders clear() {
/* 193 */     for (int i = 0; i < this.entries.length; i++) {
/* 194 */       this.entries[i] = null;
/*     */     }
/* 196 */     this.head.before = this.head.after = this.head;
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(String name) {
/* 202 */     if (name == null) {
/* 203 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 206 */     int h = hash(name);
/* 207 */     int i = index(h);
/* 208 */     HeaderEntry e = this.entries[i];
/* 209 */     while (e != null) {
/* 210 */       if (e.hash == h && eq(name, e.key)) {
/* 211 */         return e.value;
/*     */       }
/*     */       
/* 214 */       e = e.next;
/*     */     } 
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(String name) {
/* 221 */     if (name == null) {
/* 222 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 225 */     LinkedList<String> values = new LinkedList<String>();
/*     */     
/* 227 */     int h = hash(name);
/* 228 */     int i = index(h);
/* 229 */     HeaderEntry e = this.entries[i];
/* 230 */     while (e != null) {
/* 231 */       if (e.hash == h && eq(name, e.key)) {
/* 232 */         values.addFirst(e.value);
/*     */       }
/* 234 */       e = e.next;
/*     */     } 
/* 236 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Map.Entry<String, String>> entries() {
/* 241 */     List<Map.Entry<String, String>> all = new LinkedList<Map.Entry<String, String>>();
/*     */ 
/*     */     
/* 244 */     HeaderEntry e = this.head.after;
/* 245 */     while (e != this.head) {
/* 246 */       all.add(e);
/* 247 */       e = e.after;
/*     */     } 
/* 249 */     return all;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 254 */     return (get(name) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> names() {
/* 259 */     Set<String> names = new TreeSet<String>();
/*     */     
/* 261 */     HeaderEntry e = this.head.after;
/* 262 */     while (e != this.head) {
/* 263 */       names.add(e.key);
/* 264 */       e = e.after;
/*     */     } 
/* 266 */     return names;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders add(String name, Iterable<?> values) {
/* 271 */     SpdyCodecUtil.validateHeaderValue(name);
/* 272 */     int h = hash(name);
/* 273 */     int i = index(h);
/* 274 */     for (Object v : values) {
/* 275 */       String vstr = toString(v);
/* 276 */       SpdyCodecUtil.validateHeaderValue(vstr);
/* 277 */       add0(h, i, name, vstr);
/*     */     } 
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 284 */     return entries().isEmpty();
/*     */   }
/*     */   
/*     */   private static String toString(Object value) {
/* 288 */     if (value == null) {
/* 289 */       return null;
/*     */     }
/* 291 */     return value.toString();
/*     */   }
/*     */   
/*     */   private static final class HeaderEntry implements Map.Entry<String, String> { final int hash;
/*     */     final String key;
/*     */     String value;
/*     */     HeaderEntry next;
/*     */     HeaderEntry before;
/*     */     HeaderEntry after;
/*     */     
/*     */     HeaderEntry(int hash, String key, String value) {
/* 302 */       this.hash = hash;
/* 303 */       this.key = key;
/* 304 */       this.value = value;
/*     */     }
/*     */     
/*     */     void remove() {
/* 308 */       this.before.after = this.after;
/* 309 */       this.after.before = this.before;
/*     */     }
/*     */     
/*     */     void addBefore(HeaderEntry e) {
/* 313 */       this.after = e;
/* 314 */       this.before = e.before;
/* 315 */       this.before.after = this;
/* 316 */       this.after.before = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 321 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 326 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String setValue(String value) {
/* 331 */       if (value == null) {
/* 332 */         throw new NullPointerException("value");
/*     */       }
/* 334 */       SpdyCodecUtil.validateHeaderValue(value);
/* 335 */       String oldValue = this.value;
/* 336 */       this.value = value;
/* 337 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 342 */       return this.key + '=' + this.value;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */