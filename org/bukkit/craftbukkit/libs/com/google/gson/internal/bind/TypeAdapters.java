/*     */ package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.BitSet;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonIOException;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonNull;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonPrimitive;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapterFactory;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.LazilyParsedNumber;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeAdapters
/*     */ {
/*  59 */   public static final TypeAdapter<Class> CLASS = new TypeAdapter<Class>()
/*     */     {
/*     */       public void write(JsonWriter out, Class value) throws IOException {
/*  62 */         throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value.getName() + ". Forgot to register a type adapter?");
/*     */       }
/*     */ 
/*     */       
/*     */       public Class read(JsonReader in) throws IOException {
/*  67 */         throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
/*     */       }
/*     */     };
/*     */   
/*  71 */   public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
/*     */   
/*  73 */   public static final TypeAdapter<BitSet> BIT_SET = new TypeAdapter<BitSet>() {
/*     */       public BitSet read(JsonReader in) throws IOException {
/*  75 */         if (in.peek() == JsonToken.NULL) {
/*  76 */           in.nextNull();
/*  77 */           return null;
/*     */         } 
/*     */         
/*  80 */         BitSet bitset = new BitSet();
/*  81 */         in.beginArray();
/*  82 */         int i = 0;
/*  83 */         JsonToken tokenType = in.peek();
/*  84 */         while (tokenType != JsonToken.END_ARRAY) {
/*     */           boolean set; String stringValue;
/*  86 */           switch (tokenType) {
/*     */             case NUMBER:
/*  88 */               set = (in.nextInt() != 0);
/*     */               break;
/*     */             case BOOLEAN:
/*  91 */               set = in.nextBoolean();
/*     */               break;
/*     */             case STRING:
/*  94 */               stringValue = in.nextString();
/*     */               try {
/*  96 */                 set = (Integer.parseInt(stringValue) != 0);
/*  97 */               } catch (NumberFormatException e) {
/*  98 */                 throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + stringValue);
/*     */               } 
/*     */               break;
/*     */             
/*     */             default:
/* 103 */               throw new JsonSyntaxException("Invalid bitset value type: " + tokenType);
/*     */           } 
/* 105 */           if (set) {
/* 106 */             bitset.set(i);
/*     */           }
/* 108 */           i++;
/* 109 */           tokenType = in.peek();
/*     */         } 
/* 111 */         in.endArray();
/* 112 */         return bitset;
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, BitSet src) throws IOException {
/* 116 */         if (src == null) {
/* 117 */           out.nullValue();
/*     */           
/*     */           return;
/*     */         } 
/* 121 */         out.beginArray();
/* 122 */         for (int i = 0; i < src.length(); i++) {
/* 123 */           int value = src.get(i) ? 1 : 0;
/* 124 */           out.value(value);
/*     */         } 
/* 126 */         out.endArray();
/*     */       }
/*     */     };
/*     */   
/* 130 */   public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
/*     */   
/* 132 */   public static final TypeAdapter<Boolean> BOOLEAN = new TypeAdapter<Boolean>()
/*     */     {
/*     */       public Boolean read(JsonReader in) throws IOException {
/* 135 */         if (in.peek() == JsonToken.NULL) {
/* 136 */           in.nextNull();
/* 137 */           return null;
/* 138 */         }  if (in.peek() == JsonToken.STRING)
/*     */         {
/* 140 */           return Boolean.valueOf(Boolean.parseBoolean(in.nextString()));
/*     */         }
/* 142 */         return Boolean.valueOf(in.nextBoolean());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Boolean value) throws IOException {
/* 146 */         if (value == null) {
/* 147 */           out.nullValue();
/*     */           return;
/*     */         } 
/* 150 */         out.value(value.booleanValue());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new TypeAdapter<Boolean>() {
/*     */       public Boolean read(JsonReader in) throws IOException {
/* 160 */         if (in.peek() == JsonToken.NULL) {
/* 161 */           in.nextNull();
/* 162 */           return null;
/*     */         } 
/* 164 */         return Boolean.valueOf(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Boolean value) throws IOException {
/* 168 */         out.value((value == null) ? "null" : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 172 */   public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(boolean.class, (Class)Boolean.class, (TypeAdapter)BOOLEAN);
/*     */ 
/*     */   
/* 175 */   public static final TypeAdapter<Number> BYTE = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 178 */         if (in.peek() == JsonToken.NULL) {
/* 179 */           in.nextNull();
/* 180 */           return null;
/*     */         } 
/*     */         try {
/* 183 */           int intValue = in.nextInt();
/* 184 */           return Byte.valueOf((byte)intValue);
/* 185 */         } catch (NumberFormatException e) {
/* 186 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 191 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 195 */   public static final TypeAdapterFactory BYTE_FACTORY = newFactory(byte.class, (Class)Byte.class, (TypeAdapter)BYTE);
/*     */ 
/*     */   
/* 198 */   public static final TypeAdapter<Number> SHORT = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 201 */         if (in.peek() == JsonToken.NULL) {
/* 202 */           in.nextNull();
/* 203 */           return null;
/*     */         } 
/*     */         try {
/* 206 */           return Short.valueOf((short)in.nextInt());
/* 207 */         } catch (NumberFormatException e) {
/* 208 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 213 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 217 */   public static final TypeAdapterFactory SHORT_FACTORY = newFactory(short.class, (Class)Short.class, (TypeAdapter)SHORT);
/*     */ 
/*     */   
/* 220 */   public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 223 */         if (in.peek() == JsonToken.NULL) {
/* 224 */           in.nextNull();
/* 225 */           return null;
/*     */         } 
/*     */         try {
/* 228 */           return Integer.valueOf(in.nextInt());
/* 229 */         } catch (NumberFormatException e) {
/* 230 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 235 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 239 */   public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(int.class, (Class)Integer.class, (TypeAdapter)INTEGER);
/*     */ 
/*     */   
/* 242 */   public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 245 */         if (in.peek() == JsonToken.NULL) {
/* 246 */           in.nextNull();
/* 247 */           return null;
/*     */         } 
/*     */         try {
/* 250 */           return Long.valueOf(in.nextLong());
/* 251 */         } catch (NumberFormatException e) {
/* 252 */           throw new JsonSyntaxException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 257 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 261 */   public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 264 */         if (in.peek() == JsonToken.NULL) {
/* 265 */           in.nextNull();
/* 266 */           return null;
/*     */         } 
/* 268 */         return Float.valueOf((float)in.nextDouble());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 272 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 276 */   public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 279 */         if (in.peek() == JsonToken.NULL) {
/* 280 */           in.nextNull();
/* 281 */           return null;
/*     */         } 
/* 283 */         return Double.valueOf(in.nextDouble());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 287 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 291 */   public static final TypeAdapter<Number> NUMBER = new TypeAdapter<Number>()
/*     */     {
/*     */       public Number read(JsonReader in) throws IOException {
/* 294 */         JsonToken jsonToken = in.peek();
/* 295 */         switch (jsonToken) {
/*     */           case NULL:
/* 297 */             in.nextNull();
/* 298 */             return null;
/*     */           case NUMBER:
/* 300 */             return (Number)new LazilyParsedNumber(in.nextString());
/*     */         } 
/* 302 */         throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Number value) throws IOException {
/* 307 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 311 */   public static final TypeAdapterFactory NUMBER_FACTORY = newFactory(Number.class, NUMBER);
/*     */   
/* 313 */   public static final TypeAdapter<Character> CHARACTER = new TypeAdapter<Character>()
/*     */     {
/*     */       public Character read(JsonReader in) throws IOException {
/* 316 */         if (in.peek() == JsonToken.NULL) {
/* 317 */           in.nextNull();
/* 318 */           return null;
/*     */         } 
/* 320 */         String str = in.nextString();
/* 321 */         if (str.length() != 1) {
/* 322 */           throw new JsonSyntaxException("Expecting character, got: " + str);
/*     */         }
/* 324 */         return Character.valueOf(str.charAt(0));
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, Character value) throws IOException {
/* 328 */         out.value((value == null) ? null : String.valueOf(value));
/*     */       }
/*     */     };
/*     */   
/* 332 */   public static final TypeAdapterFactory CHARACTER_FACTORY = newFactory(char.class, (Class)Character.class, (TypeAdapter)CHARACTER);
/*     */ 
/*     */   
/* 335 */   public static final TypeAdapter<String> STRING = new TypeAdapter<String>()
/*     */     {
/*     */       public String read(JsonReader in) throws IOException {
/* 338 */         JsonToken peek = in.peek();
/* 339 */         if (peek == JsonToken.NULL) {
/* 340 */           in.nextNull();
/* 341 */           return null;
/*     */         } 
/*     */         
/* 344 */         if (peek == JsonToken.BOOLEAN) {
/* 345 */           return Boolean.toString(in.nextBoolean());
/*     */         }
/* 347 */         return in.nextString();
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, String value) throws IOException {
/* 351 */         out.value(value);
/*     */       }
/*     */     };
/*     */   
/* 355 */   public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);
/*     */   
/* 357 */   public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>()
/*     */     {
/*     */       public StringBuilder read(JsonReader in) throws IOException {
/* 360 */         if (in.peek() == JsonToken.NULL) {
/* 361 */           in.nextNull();
/* 362 */           return null;
/*     */         } 
/* 364 */         return new StringBuilder(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, StringBuilder value) throws IOException {
/* 368 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 372 */   public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
/*     */ 
/*     */   
/* 375 */   public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>()
/*     */     {
/*     */       public StringBuffer read(JsonReader in) throws IOException {
/* 378 */         if (in.peek() == JsonToken.NULL) {
/* 379 */           in.nextNull();
/* 380 */           return null;
/*     */         } 
/* 382 */         return new StringBuffer(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, StringBuffer value) throws IOException {
/* 386 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 390 */   public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
/*     */ 
/*     */   
/* 393 */   public static final TypeAdapter<URL> URL = new TypeAdapter<URL>()
/*     */     {
/*     */       public URL read(JsonReader in) throws IOException {
/* 396 */         if (in.peek() == JsonToken.NULL) {
/* 397 */           in.nextNull();
/* 398 */           return null;
/*     */         } 
/* 400 */         String nextString = in.nextString();
/* 401 */         return "null".equals(nextString) ? null : new URL(nextString);
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, URL value) throws IOException {
/* 405 */         out.value((value == null) ? null : value.toExternalForm());
/*     */       }
/*     */     };
/*     */   
/* 409 */   public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);
/*     */   
/* 411 */   public static final TypeAdapter<URI> URI = new TypeAdapter<URI>()
/*     */     {
/*     */       public URI read(JsonReader in) throws IOException {
/* 414 */         if (in.peek() == JsonToken.NULL) {
/* 415 */           in.nextNull();
/* 416 */           return null;
/*     */         } 
/*     */         try {
/* 419 */           String nextString = in.nextString();
/* 420 */           return "null".equals(nextString) ? null : new URI(nextString);
/* 421 */         } catch (URISyntaxException e) {
/* 422 */           throw new JsonIOException(e);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, URI value) throws IOException {
/* 427 */         out.value((value == null) ? null : value.toASCIIString());
/*     */       }
/*     */     };
/*     */   
/* 431 */   public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);
/*     */   
/* 433 */   public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>()
/*     */     {
/*     */       public InetAddress read(JsonReader in) throws IOException {
/* 436 */         if (in.peek() == JsonToken.NULL) {
/* 437 */           in.nextNull();
/* 438 */           return null;
/*     */         } 
/*     */         
/* 441 */         return InetAddress.getByName(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, InetAddress value) throws IOException {
/* 445 */         out.value((value == null) ? null : value.getHostAddress());
/*     */       }
/*     */     };
/*     */   
/* 449 */   public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
/*     */ 
/*     */   
/* 452 */   public static final TypeAdapter<UUID> UUID = new TypeAdapter<UUID>()
/*     */     {
/*     */       public UUID read(JsonReader in) throws IOException {
/* 455 */         if (in.peek() == JsonToken.NULL) {
/* 456 */           in.nextNull();
/* 457 */           return null;
/*     */         } 
/* 459 */         return UUID.fromString(in.nextString());
/*     */       }
/*     */       
/*     */       public void write(JsonWriter out, UUID value) throws IOException {
/* 463 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 467 */   public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);
/*     */   
/* 469 */   public static final TypeAdapterFactory TIMESTAMP_FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 472 */         if (typeToken.getRawType() != Timestamp.class) {
/* 473 */           return null;
/*     */         }
/*     */         
/* 476 */         final TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
/* 477 */         return (TypeAdapter)new TypeAdapter<Timestamp>() {
/*     */             public Timestamp read(JsonReader in) throws IOException {
/* 479 */               Date date = (Date)dateTypeAdapter.read(in);
/* 480 */               return (date != null) ? new Timestamp(date.getTime()) : null;
/*     */             }
/*     */             
/*     */             public void write(JsonWriter out, Timestamp value) throws IOException {
/* 484 */               dateTypeAdapter.write(out, value);
/*     */             }
/*     */           };
/*     */       }
/*     */     };
/*     */   
/* 490 */   public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>()
/*     */     {
/*     */       private static final String YEAR = "year";
/*     */       private static final String MONTH = "month";
/*     */       private static final String DAY_OF_MONTH = "dayOfMonth";
/*     */       private static final String HOUR_OF_DAY = "hourOfDay";
/*     */       private static final String MINUTE = "minute";
/*     */       private static final String SECOND = "second";
/*     */       
/*     */       public Calendar read(JsonReader in) throws IOException {
/* 500 */         if (in.peek() == JsonToken.NULL) {
/* 501 */           in.nextNull();
/* 502 */           return null;
/*     */         } 
/* 504 */         in.beginObject();
/* 505 */         int year = 0;
/* 506 */         int month = 0;
/* 507 */         int dayOfMonth = 0;
/* 508 */         int hourOfDay = 0;
/* 509 */         int minute = 0;
/* 510 */         int second = 0;
/* 511 */         while (in.peek() != JsonToken.END_OBJECT) {
/* 512 */           String name = in.nextName();
/* 513 */           int value = in.nextInt();
/* 514 */           if ("year".equals(name)) {
/* 515 */             year = value; continue;
/* 516 */           }  if ("month".equals(name)) {
/* 517 */             month = value; continue;
/* 518 */           }  if ("dayOfMonth".equals(name)) {
/* 519 */             dayOfMonth = value; continue;
/* 520 */           }  if ("hourOfDay".equals(name)) {
/* 521 */             hourOfDay = value; continue;
/* 522 */           }  if ("minute".equals(name)) {
/* 523 */             minute = value; continue;
/* 524 */           }  if ("second".equals(name)) {
/* 525 */             second = value;
/*     */           }
/*     */         } 
/* 528 */         in.endObject();
/* 529 */         return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Calendar value) throws IOException {
/* 534 */         if (value == null) {
/* 535 */           out.nullValue();
/*     */           return;
/*     */         } 
/* 538 */         out.beginObject();
/* 539 */         out.name("year");
/* 540 */         out.value(value.get(1));
/* 541 */         out.name("month");
/* 542 */         out.value(value.get(2));
/* 543 */         out.name("dayOfMonth");
/* 544 */         out.value(value.get(5));
/* 545 */         out.name("hourOfDay");
/* 546 */         out.value(value.get(11));
/* 547 */         out.name("minute");
/* 548 */         out.value(value.get(12));
/* 549 */         out.name("second");
/* 550 */         out.value(value.get(13));
/* 551 */         out.endObject();
/*     */       }
/*     */     };
/*     */   
/* 555 */   public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, (Class)GregorianCalendar.class, CALENDAR);
/*     */ 
/*     */   
/* 558 */   public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>()
/*     */     {
/*     */       public Locale read(JsonReader in) throws IOException {
/* 561 */         if (in.peek() == JsonToken.NULL) {
/* 562 */           in.nextNull();
/* 563 */           return null;
/*     */         } 
/* 565 */         String locale = in.nextString();
/* 566 */         StringTokenizer tokenizer = new StringTokenizer(locale, "_");
/* 567 */         String language = null;
/* 568 */         String country = null;
/* 569 */         String variant = null;
/* 570 */         if (tokenizer.hasMoreElements()) {
/* 571 */           language = tokenizer.nextToken();
/*     */         }
/* 573 */         if (tokenizer.hasMoreElements()) {
/* 574 */           country = tokenizer.nextToken();
/*     */         }
/* 576 */         if (tokenizer.hasMoreElements()) {
/* 577 */           variant = tokenizer.nextToken();
/*     */         }
/* 579 */         if (country == null && variant == null)
/* 580 */           return new Locale(language); 
/* 581 */         if (variant == null) {
/* 582 */           return new Locale(language, country);
/*     */         }
/* 584 */         return new Locale(language, country, variant);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, Locale value) throws IOException {
/* 589 */         out.value((value == null) ? null : value.toString());
/*     */       }
/*     */     };
/*     */   
/* 593 */   public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
/*     */   
/* 595 */   public static final TypeAdapter<JsonElement> JSON_ELEMENT = new TypeAdapter<JsonElement>() { public JsonElement read(JsonReader in) throws IOException { String number; JsonArray array;
/*     */         JsonObject object;
/* 597 */         switch (in.peek()) {
/*     */           case STRING:
/* 599 */             return (JsonElement)new JsonPrimitive(in.nextString());
/*     */           case NUMBER:
/* 601 */             number = in.nextString();
/* 602 */             return (JsonElement)new JsonPrimitive((Number)new LazilyParsedNumber(number));
/*     */           case BOOLEAN:
/* 604 */             return (JsonElement)new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
/*     */           case NULL:
/* 606 */             in.nextNull();
/* 607 */             return (JsonElement)JsonNull.INSTANCE;
/*     */           case BEGIN_ARRAY:
/* 609 */             array = new JsonArray();
/* 610 */             in.beginArray();
/* 611 */             while (in.hasNext()) {
/* 612 */               array.add(read(in));
/*     */             }
/* 614 */             in.endArray();
/* 615 */             return (JsonElement)array;
/*     */           case BEGIN_OBJECT:
/* 617 */             object = new JsonObject();
/* 618 */             in.beginObject();
/* 619 */             while (in.hasNext()) {
/* 620 */               object.add(in.nextName(), read(in));
/*     */             }
/* 622 */             in.endObject();
/* 623 */             return (JsonElement)object;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 629 */         throw new IllegalArgumentException(); }
/*     */ 
/*     */ 
/*     */       
/*     */       public void write(JsonWriter out, JsonElement value) throws IOException {
/* 634 */         if (value == null || value.isJsonNull()) {
/* 635 */           out.nullValue();
/* 636 */         } else if (value.isJsonPrimitive()) {
/* 637 */           JsonPrimitive primitive = value.getAsJsonPrimitive();
/* 638 */           if (primitive.isNumber()) {
/* 639 */             out.value(primitive.getAsNumber());
/* 640 */           } else if (primitive.isBoolean()) {
/* 641 */             out.value(primitive.getAsBoolean());
/*     */           } else {
/* 643 */             out.value(primitive.getAsString());
/*     */           }
/*     */         
/* 646 */         } else if (value.isJsonArray()) {
/* 647 */           out.beginArray();
/* 648 */           for (JsonElement e : value.getAsJsonArray()) {
/* 649 */             write(out, e);
/*     */           }
/* 651 */           out.endArray();
/*     */         }
/* 653 */         else if (value.isJsonObject()) {
/* 654 */           out.beginObject();
/* 655 */           for (Map.Entry<String, JsonElement> e : (Iterable<Map.Entry<String, JsonElement>>)value.getAsJsonObject().entrySet()) {
/* 656 */             out.name(e.getKey());
/* 657 */             write(out, e.getValue());
/*     */           } 
/* 659 */           out.endObject();
/*     */         } else {
/*     */           
/* 662 */           throw new IllegalArgumentException("Couldn't write " + value.getClass());
/*     */         } 
/*     */       } }
/*     */   ;
/*     */   
/* 667 */   public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = newFactory(JsonElement.class, JSON_ELEMENT);
/*     */   
/*     */   private static final class EnumTypeAdapter<T extends Enum<T>>
/*     */     extends TypeAdapter<T> {
/* 671 */     private final Map<String, T> nameToConstant = new HashMap<String, T>();
/* 672 */     private final Map<T, String> constantToName = new HashMap<T, String>();
/*     */     
/*     */     public EnumTypeAdapter(Class<T> classOfT) {
/*     */       try {
/* 676 */         for (Enum enum_ : (Enum[])classOfT.getEnumConstants()) {
/* 677 */           String name = enum_.name();
/* 678 */           SerializedName annotation = classOfT.getField(name).<SerializedName>getAnnotation(SerializedName.class);
/* 679 */           if (annotation != null) {
/* 680 */             name = annotation.value();
/*     */           }
/* 682 */           this.nameToConstant.put(name, (T)enum_);
/* 683 */           this.constantToName.put((T)enum_, name);
/*     */         } 
/* 685 */       } catch (NoSuchFieldException e) {
/* 686 */         throw new AssertionError();
/*     */       } 
/*     */     }
/*     */     public T read(JsonReader in) throws IOException {
/* 690 */       if (in.peek() == JsonToken.NULL) {
/* 691 */         in.nextNull();
/* 692 */         return null;
/*     */       } 
/* 694 */       return this.nameToConstant.get(in.nextString());
/*     */     }
/*     */     
/*     */     public void write(JsonWriter out, T value) throws IOException {
/* 698 */       out.value((value == null) ? null : this.constantToName.get(value));
/*     */     }
/*     */   }
/*     */   
/* 702 */   public static final TypeAdapterFactory ENUM_FACTORY = newEnumTypeHierarchyFactory();
/*     */   
/*     */   public static <TT> TypeAdapterFactory newEnumTypeHierarchyFactory() {
/* 705 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 708 */           Class<? super T> rawType = typeToken.getRawType();
/* 709 */           if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
/* 710 */             return null;
/*     */           }
/* 712 */           if (!rawType.isEnum()) {
/* 713 */             rawType = rawType.getSuperclass();
/*     */           }
/* 715 */           return new TypeAdapters.EnumTypeAdapter<T>(rawType);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
/* 722 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 725 */           return typeToken.equals(type) ? typeAdapter : null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
/* 732 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 735 */           return (typeToken.getRawType() == type) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 738 */           return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactory(final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
/* 745 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 748 */           Class<? super T> rawType = typeToken.getRawType();
/* 749 */           return (rawType == unboxed || rawType == boxed) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 752 */           return "Factory[type=" + boxed.getName() + "+" + unboxed.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base, final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
/* 760 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 763 */           Class<? super T> rawType = typeToken.getRawType();
/* 764 */           return (rawType == base || rawType == sub) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 767 */           return "Factory[type=" + base.getName() + "+" + sub.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <TT> TypeAdapterFactory newTypeHierarchyFactory(final Class<TT> clazz, final TypeAdapter<TT> typeAdapter) {
/* 775 */     return new TypeAdapterFactory()
/*     */       {
/*     */         public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 778 */           return clazz.isAssignableFrom(typeToken.getRawType()) ? typeAdapter : null;
/*     */         }
/*     */         public String toString() {
/* 781 */           return "Factory[typeHierarchy=" + clazz.getName() + ",adapter=" + typeAdapter + "]";
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\bind\TypeAdapters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */