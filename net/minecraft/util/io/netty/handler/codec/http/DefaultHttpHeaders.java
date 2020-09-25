/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
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
/*     */ public class DefaultHttpHeaders
/*     */   extends HttpHeaders
/*     */ {
/*     */   private static final int BUCKET_SIZE = 17;
/*     */   
/*     */   private static int hash(String name) {
/*  33 */     int h = 0;
/*  34 */     for (int i = name.length() - 1; i >= 0; i--) {
/*  35 */       char c = name.charAt(i);
/*  36 */       if (c >= 'A' && c <= 'Z') {
/*  37 */         c = (char)(c + 32);
/*     */       }
/*  39 */       h = 31 * h + c;
/*     */     } 
/*     */     
/*  42 */     if (h > 0)
/*  43 */       return h; 
/*  44 */     if (h == Integer.MIN_VALUE) {
/*  45 */       return Integer.MAX_VALUE;
/*     */     }
/*  47 */     return -h;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean eq(String name1, String name2) {
/*  52 */     int nameLen = name1.length();
/*  53 */     if (nameLen != name2.length()) {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     for (int i = nameLen - 1; i >= 0; i--) {
/*  58 */       char c1 = name1.charAt(i);
/*  59 */       char c2 = name2.charAt(i);
/*  60 */       if (c1 != c2) {
/*  61 */         if (c1 >= 'A' && c1 <= 'Z') {
/*  62 */           c1 = (char)(c1 + 32);
/*     */         }
/*  64 */         if (c2 >= 'A' && c2 <= 'Z') {
/*  65 */           c2 = (char)(c2 + 32);
/*     */         }
/*  67 */         if (c1 != c2) {
/*  68 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   private static int index(int hash) {
/*  76 */     return hash % 17;
/*     */   }
/*     */   
/*  79 */   private final HeaderEntry[] entries = new HeaderEntry[17];
/*  80 */   private final HeaderEntry head = new HeaderEntry(-1, null, null);
/*     */   
/*     */   public DefaultHttpHeaders() {
/*  83 */     this.head.before = this.head.after = this.head;
/*     */   }
/*     */   
/*     */   void validateHeaderName0(String headerName) {
/*  87 */     validateHeaderName(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(String name, Object value) {
/*  92 */     validateHeaderName0(name);
/*  93 */     String strVal = toString(value);
/*  94 */     validateHeaderValue(strVal);
/*  95 */     int h = hash(name);
/*  96 */     int i = index(h);
/*  97 */     add0(h, i, name, strVal);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders add(String name, Iterable<?> values) {
/* 103 */     validateHeaderName0(name);
/* 104 */     int h = hash(name);
/* 105 */     int i = index(h);
/* 106 */     for (Object v : values) {
/* 107 */       String vstr = toString(v);
/* 108 */       validateHeaderValue(vstr);
/* 109 */       add0(h, i, name, vstr);
/*     */     } 
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void add0(int h, int i, String name, String value) {
/* 116 */     HeaderEntry e = this.entries[i];
/*     */     
/* 118 */     HeaderEntry newEntry = new HeaderEntry(h, name, value);
/* 119 */     newEntry.next = e;
/*     */ 
/*     */     
/* 122 */     newEntry.addBefore(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders remove(String name) {
/* 127 */     if (name == null) {
/* 128 */       throw new NullPointerException("name");
/*     */     }
/* 130 */     int h = hash(name);
/* 131 */     int i = index(h);
/* 132 */     remove0(h, i, name);
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   private void remove0(int h, int i, String name) {
/* 137 */     HeaderEntry e = this.entries[i];
/* 138 */     if (e == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 143 */     while (e.hash == h && eq(name, e.key)) {
/* 144 */       e.remove();
/* 145 */       HeaderEntry next = e.next;
/* 146 */       if (next != null) {
/* 147 */         this.entries[i] = next;
/* 148 */         e = next; continue;
/*     */       } 
/* 150 */       this.entries[i] = null;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*     */     while (true) {
/* 159 */       HeaderEntry next = e.next;
/* 160 */       if (next == null) {
/*     */         break;
/*     */       }
/* 163 */       if (next.hash == h && eq(name, next.key)) {
/* 164 */         e.next = next.next;
/* 165 */         next.remove(); continue;
/*     */       } 
/* 167 */       e = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders set(String name, Object value) {
/* 174 */     validateHeaderName0(name);
/* 175 */     String strVal = toString(value);
/* 176 */     validateHeaderValue(strVal);
/* 177 */     int h = hash(name);
/* 178 */     int i = index(h);
/* 179 */     remove0(h, i, name);
/* 180 */     add0(h, i, name, strVal);
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders set(String name, Iterable<?> values) {
/* 186 */     if (values == null) {
/* 187 */       throw new NullPointerException("values");
/*     */     }
/*     */     
/* 190 */     validateHeaderName0(name);
/*     */     
/* 192 */     int h = hash(name);
/* 193 */     int i = index(h);
/*     */     
/* 195 */     remove0(h, i, name);
/* 196 */     for (Object v : values) {
/* 197 */       if (v == null) {
/*     */         break;
/*     */       }
/* 200 */       String strVal = toString(v);
/* 201 */       validateHeaderValue(strVal);
/* 202 */       add0(h, i, name, strVal);
/*     */     } 
/*     */     
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders clear() {
/* 210 */     Arrays.fill((Object[])this.entries, (Object)null);
/* 211 */     this.head.before = this.head.after = this.head;
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(String name) {
/* 217 */     if (name == null) {
/* 218 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 221 */     int h = hash(name);
/* 222 */     int i = index(h);
/* 223 */     HeaderEntry e = this.entries[i];
/* 224 */     String value = null;
/*     */     
/* 226 */     while (e != null) {
/* 227 */       if (e.hash == h && eq(name, e.key)) {
/* 228 */         value = e.value;
/*     */       }
/*     */       
/* 231 */       e = e.next;
/*     */     } 
/* 233 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAll(String name) {
/* 238 */     if (name == null) {
/* 239 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 242 */     LinkedList<String> values = new LinkedList<String>();
/*     */     
/* 244 */     int h = hash(name);
/* 245 */     int i = index(h);
/* 246 */     HeaderEntry e = this.entries[i];
/* 247 */     while (e != null) {
/* 248 */       if (e.hash == h && eq(name, e.key)) {
/* 249 */         values.addFirst(e.value);
/*     */       }
/* 251 */       e = e.next;
/*     */     } 
/* 253 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Map.Entry<String, String>> entries() {
/* 258 */     List<Map.Entry<String, String>> all = new LinkedList<Map.Entry<String, String>>();
/*     */ 
/*     */     
/* 261 */     HeaderEntry e = this.head.after;
/* 262 */     while (e != this.head) {
/* 263 */       all.add(e);
/* 264 */       e = e.after;
/*     */     } 
/* 266 */     return all;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator() {
/* 271 */     return entries().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 276 */     return (get(name) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 281 */     return (this.head == this.head.after);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> names() {
/* 287 */     Set<String> names = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
/*     */     
/* 289 */     HeaderEntry e = this.head.after;
/* 290 */     while (e != this.head) {
/* 291 */       names.add(e.key);
/* 292 */       e = e.after;
/*     */     } 
/* 294 */     return names;
/*     */   }
/*     */   
/*     */   private static String toString(Object value) {
/* 298 */     if (value == null) {
/* 299 */       return null;
/*     */     }
/* 301 */     if (value instanceof String) {
/* 302 */       return (String)value;
/*     */     }
/* 304 */     if (value instanceof Number) {
/* 305 */       return value.toString();
/*     */     }
/* 307 */     if (value instanceof Date) {
/* 308 */       return HttpHeaderDateFormat.get().format((Date)value);
/*     */     }
/* 310 */     if (value instanceof Calendar) {
/* 311 */       return HttpHeaderDateFormat.get().format(((Calendar)value).getTime());
/*     */     }
/* 313 */     return value.toString();
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
/* 324 */       this.hash = hash;
/* 325 */       this.key = key;
/* 326 */       this.value = value;
/*     */     }
/*     */     
/*     */     void remove() {
/* 330 */       this.before.after = this.after;
/* 331 */       this.after.before = this.before;
/*     */     }
/*     */     
/*     */     void addBefore(HeaderEntry e) {
/* 335 */       this.after = e;
/* 336 */       this.before = e.before;
/* 337 */       this.before.after = this;
/* 338 */       this.after.before = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 343 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 348 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String setValue(String value) {
/* 353 */       if (value == null) {
/* 354 */         throw new NullPointerException("value");
/*     */       }
/* 356 */       HttpHeaders.validateHeaderValue(value);
/* 357 */       String oldValue = this.value;
/* 358 */       this.value = value;
/* 359 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 364 */       return this.key + '=' + this.value;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultHttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */