/*     */ package net.minecraft.util.com.google.common.net;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Ascii;
/*     */ import net.minecraft.util.com.google.common.base.CharMatcher;
/*     */ import net.minecraft.util.com.google.common.base.Charsets;
/*     */ import net.minecraft.util.com.google.common.base.Function;
/*     */ import net.minecraft.util.com.google.common.base.Joiner;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
/*     */ import net.minecraft.util.com.google.common.base.Optional;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableListMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableMultiset;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableSet;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.google.common.collect.ListMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.collect.Multimap;
/*     */ import net.minecraft.util.com.google.common.collect.Multimaps;
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
/*     */ @Beta
/*     */ @GwtCompatible
/*     */ @Immutable
/*     */ public final class MediaType
/*     */ {
/*     */   private static final String CHARSET_ATTRIBUTE = "charset";
/*  84 */   private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final CharMatcher TOKEN_MATCHER = CharMatcher.ASCII.and(CharMatcher.JAVA_ISO_CONTROL.negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
/*     */ 
/*     */   
/*  91 */   private static final CharMatcher QUOTED_TEXT_MATCHER = CharMatcher.ASCII.and(CharMatcher.noneOf("\"\\\r"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final CharMatcher LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
/*     */   
/*     */   private static final String APPLICATION_TYPE = "application";
/*     */   
/*     */   private static final String AUDIO_TYPE = "audio";
/*     */   
/*     */   private static final String IMAGE_TYPE = "image";
/*     */   
/*     */   private static final String TEXT_TYPE = "text";
/*     */   private static final String VIDEO_TYPE = "video";
/*     */   private static final String WILDCARD = "*";
/* 108 */   private static final Map<MediaType, MediaType> KNOWN_TYPES = Maps.newHashMap();
/*     */   
/*     */   private static MediaType createConstant(String type, String subtype) {
/* 111 */     return addKnownType(new MediaType(type, subtype, ImmutableListMultimap.of()));
/*     */   }
/*     */   
/*     */   private static MediaType createConstantUtf8(String type, String subtype) {
/* 115 */     return addKnownType(new MediaType(type, subtype, UTF_8_CONSTANT_PARAMETERS));
/*     */   }
/*     */   
/*     */   private static MediaType addKnownType(MediaType mediaType) {
/* 119 */     KNOWN_TYPES.put(mediaType, mediaType);
/* 120 */     return mediaType;
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
/* 133 */   public static final MediaType ANY_TYPE = createConstant("*", "*");
/* 134 */   public static final MediaType ANY_TEXT_TYPE = createConstant("text", "*");
/* 135 */   public static final MediaType ANY_IMAGE_TYPE = createConstant("image", "*");
/* 136 */   public static final MediaType ANY_AUDIO_TYPE = createConstant("audio", "*");
/* 137 */   public static final MediaType ANY_VIDEO_TYPE = createConstant("video", "*");
/* 138 */   public static final MediaType ANY_APPLICATION_TYPE = createConstant("application", "*");
/*     */ 
/*     */   
/* 141 */   public static final MediaType CACHE_MANIFEST_UTF_8 = createConstantUtf8("text", "cache-manifest");
/*     */   
/* 143 */   public static final MediaType CSS_UTF_8 = createConstantUtf8("text", "css");
/* 144 */   public static final MediaType CSV_UTF_8 = createConstantUtf8("text", "csv");
/* 145 */   public static final MediaType HTML_UTF_8 = createConstantUtf8("text", "html");
/* 146 */   public static final MediaType I_CALENDAR_UTF_8 = createConstantUtf8("text", "calendar");
/* 147 */   public static final MediaType PLAIN_TEXT_UTF_8 = createConstantUtf8("text", "plain");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public static final MediaType TEXT_JAVASCRIPT_UTF_8 = createConstantUtf8("text", "javascript");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final MediaType TSV_UTF_8 = createConstantUtf8("text", "tab-separated-values");
/* 161 */   public static final MediaType VCARD_UTF_8 = createConstantUtf8("text", "vcard");
/* 162 */   public static final MediaType WML_UTF_8 = createConstantUtf8("text", "vnd.wap.wml");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static final MediaType XML_UTF_8 = createConstantUtf8("text", "xml");
/*     */ 
/*     */   
/* 171 */   public static final MediaType BMP = createConstant("image", "bmp");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public static final MediaType CRW = createConstant("image", "x-canon-crw");
/* 182 */   public static final MediaType GIF = createConstant("image", "gif");
/* 183 */   public static final MediaType ICO = createConstant("image", "vnd.microsoft.icon");
/* 184 */   public static final MediaType JPEG = createConstant("image", "jpeg");
/* 185 */   public static final MediaType PNG = createConstant("image", "png");
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
/* 202 */   public static final MediaType PSD = createConstant("image", "vnd.adobe.photoshop");
/* 203 */   public static final MediaType SVG_UTF_8 = createConstantUtf8("image", "svg+xml");
/* 204 */   public static final MediaType TIFF = createConstant("image", "tiff");
/* 205 */   public static final MediaType WEBP = createConstant("image", "webp");
/*     */ 
/*     */   
/* 208 */   public static final MediaType MP4_AUDIO = createConstant("audio", "mp4");
/* 209 */   public static final MediaType MPEG_AUDIO = createConstant("audio", "mpeg");
/* 210 */   public static final MediaType OGG_AUDIO = createConstant("audio", "ogg");
/* 211 */   public static final MediaType WEBM_AUDIO = createConstant("audio", "webm");
/*     */ 
/*     */   
/* 214 */   public static final MediaType MP4_VIDEO = createConstant("video", "mp4");
/* 215 */   public static final MediaType MPEG_VIDEO = createConstant("video", "mpeg");
/* 216 */   public static final MediaType OGG_VIDEO = createConstant("video", "ogg");
/* 217 */   public static final MediaType QUICKTIME = createConstant("video", "quicktime");
/* 218 */   public static final MediaType WEBM_VIDEO = createConstant("video", "webm");
/* 219 */   public static final MediaType WMV = createConstant("video", "x-ms-wmv");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public static final MediaType APPLICATION_XML_UTF_8 = createConstantUtf8("application", "xml");
/* 228 */   public static final MediaType ATOM_UTF_8 = createConstantUtf8("application", "atom+xml");
/* 229 */   public static final MediaType BZIP2 = createConstant("application", "x-bzip2");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static final MediaType EPUB = createConstant("application", "epub+zip");
/* 240 */   public static final MediaType FORM_DATA = createConstant("application", "x-www-form-urlencoded");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public static final MediaType KEY_ARCHIVE = createConstant("application", "pkcs12");
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
/* 261 */   public static final MediaType APPLICATION_BINARY = createConstant("application", "binary");
/* 262 */   public static final MediaType GZIP = createConstant("application", "x-gzip");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public static final MediaType JAVASCRIPT_UTF_8 = createConstantUtf8("application", "javascript");
/*     */   
/* 270 */   public static final MediaType JSON_UTF_8 = createConstantUtf8("application", "json");
/* 271 */   public static final MediaType KML = createConstant("application", "vnd.google-earth.kml+xml");
/* 272 */   public static final MediaType KMZ = createConstant("application", "vnd.google-earth.kmz");
/* 273 */   public static final MediaType MBOX = createConstant("application", "mbox");
/* 274 */   public static final MediaType MICROSOFT_EXCEL = createConstant("application", "vnd.ms-excel");
/* 275 */   public static final MediaType MICROSOFT_POWERPOINT = createConstant("application", "vnd.ms-powerpoint");
/*     */   
/* 277 */   public static final MediaType MICROSOFT_WORD = createConstant("application", "msword");
/* 278 */   public static final MediaType OCTET_STREAM = createConstant("application", "octet-stream");
/* 279 */   public static final MediaType OGG_CONTAINER = createConstant("application", "ogg");
/* 280 */   public static final MediaType OOXML_DOCUMENT = createConstant("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
/*     */   
/* 282 */   public static final MediaType OOXML_PRESENTATION = createConstant("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
/*     */   
/* 284 */   public static final MediaType OOXML_SHEET = createConstant("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
/*     */   
/* 286 */   public static final MediaType OPENDOCUMENT_GRAPHICS = createConstant("application", "vnd.oasis.opendocument.graphics");
/*     */   
/* 288 */   public static final MediaType OPENDOCUMENT_PRESENTATION = createConstant("application", "vnd.oasis.opendocument.presentation");
/*     */   
/* 290 */   public static final MediaType OPENDOCUMENT_SPREADSHEET = createConstant("application", "vnd.oasis.opendocument.spreadsheet");
/*     */   
/* 292 */   public static final MediaType OPENDOCUMENT_TEXT = createConstant("application", "vnd.oasis.opendocument.text");
/*     */   
/* 294 */   public static final MediaType PDF = createConstant("application", "pdf");
/* 295 */   public static final MediaType POSTSCRIPT = createConstant("application", "postscript");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 301 */   public static final MediaType PROTOBUF = createConstant("application", "protobuf");
/* 302 */   public static final MediaType RDF_XML_UTF_8 = createConstantUtf8("application", "rdf+xml");
/* 303 */   public static final MediaType RTF_UTF_8 = createConstantUtf8("application", "rtf");
/* 304 */   public static final MediaType SHOCKWAVE_FLASH = createConstant("application", "x-shockwave-flash");
/*     */   
/* 306 */   public static final MediaType SKETCHUP = createConstant("application", "vnd.sketchup.skp");
/* 307 */   public static final MediaType TAR = createConstant("application", "x-tar");
/* 308 */   public static final MediaType XHTML_UTF_8 = createConstantUtf8("application", "xhtml+xml");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 316 */   public static final MediaType XRD_UTF_8 = createConstantUtf8("application", "xrd+xml");
/* 317 */   public static final MediaType ZIP = createConstant("application", "zip");
/*     */   
/*     */   private final String type;
/*     */   
/*     */   private final String subtype;
/*     */   private final ImmutableListMultimap<String, String> parameters;
/*     */   
/*     */   private MediaType(String type, String subtype, ImmutableListMultimap<String, String> parameters) {
/* 325 */     this.type = type;
/* 326 */     this.subtype = subtype;
/* 327 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public String type() {
/* 332 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String subtype() {
/* 337 */     return this.subtype;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableListMultimap<String, String> parameters() {
/* 342 */     return this.parameters;
/*     */   }
/*     */   
/*     */   private Map<String, ImmutableMultiset<String>> parametersAsMap() {
/* 346 */     return Maps.transformValues((Map)this.parameters.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>()
/*     */         {
/*     */           public ImmutableMultiset<String> apply(Collection<String> input) {
/* 349 */             return ImmutableMultiset.copyOf(input);
/*     */           }
/*     */         });
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
/*     */   public Optional<Charset> charset() {
/* 363 */     ImmutableSet<String> charsetValues = ImmutableSet.copyOf((Collection)this.parameters.get("charset"));
/* 364 */     switch (charsetValues.size()) {
/*     */       case 0:
/* 366 */         return Optional.absent();
/*     */       case 1:
/* 368 */         return Optional.of(Charset.forName((String)Iterables.getOnlyElement((Iterable)charsetValues)));
/*     */     } 
/* 370 */     throw new IllegalStateException("Multiple charset values defined: " + charsetValues);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaType withoutParameters() {
/* 379 */     return this.parameters.isEmpty() ? this : create(this.type, this.subtype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaType withParameters(Multimap<String, String> parameters) {
/* 388 */     return create(this.type, this.subtype, parameters);
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
/*     */   public MediaType withParameter(String attribute, String value) {
/* 400 */     Preconditions.checkNotNull(attribute);
/* 401 */     Preconditions.checkNotNull(value);
/* 402 */     String normalizedAttribute = normalizeToken(attribute);
/* 403 */     ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
/* 404 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)this.parameters.entries()) {
/* 405 */       String key = entry.getKey();
/* 406 */       if (!normalizedAttribute.equals(key)) {
/* 407 */         builder.put(key, entry.getValue());
/*     */       }
/*     */     } 
/* 410 */     builder.put(normalizedAttribute, normalizeParameterValue(normalizedAttribute, value));
/* 411 */     MediaType mediaType = new MediaType(this.type, this.subtype, builder.build());
/*     */     
/* 413 */     return (MediaType)Objects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
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
/*     */   public MediaType withCharset(Charset charset) {
/* 426 */     Preconditions.checkNotNull(charset);
/* 427 */     return withParameter("charset", charset.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWildcard() {
/* 432 */     return ("*".equals(this.type) || "*".equals(this.subtype));
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
/*     */   public boolean is(MediaType mediaTypeRange) {
/* 462 */     return ((mediaTypeRange.type.equals("*") || mediaTypeRange.type.equals(this.type)) && (mediaTypeRange.subtype.equals("*") || mediaTypeRange.subtype.equals(this.subtype)) && this.parameters.entries().containsAll((Collection)mediaTypeRange.parameters.entries()));
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
/*     */   public static MediaType create(String type, String subtype) {
/* 474 */     return create(type, subtype, (Multimap<String, String>)ImmutableListMultimap.of());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MediaType createApplicationType(String subtype) {
/* 483 */     return create("application", subtype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MediaType createAudioType(String subtype) {
/* 492 */     return create("audio", subtype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MediaType createImageType(String subtype) {
/* 501 */     return create("image", subtype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MediaType createTextType(String subtype) {
/* 510 */     return create("text", subtype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MediaType createVideoType(String subtype) {
/* 519 */     return create("video", subtype);
/*     */   }
/*     */ 
/*     */   
/*     */   private static MediaType create(String type, String subtype, Multimap<String, String> parameters) {
/* 524 */     Preconditions.checkNotNull(type);
/* 525 */     Preconditions.checkNotNull(subtype);
/* 526 */     Preconditions.checkNotNull(parameters);
/* 527 */     String normalizedType = normalizeToken(type);
/* 528 */     String normalizedSubtype = normalizeToken(subtype);
/* 529 */     Preconditions.checkArgument((!"*".equals(normalizedType) || "*".equals(normalizedSubtype)), "A wildcard type cannot be used with a non-wildcard subtype");
/*     */     
/* 531 */     ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
/* 532 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)parameters.entries()) {
/* 533 */       String attribute = normalizeToken(entry.getKey());
/* 534 */       builder.put(attribute, normalizeParameterValue(attribute, entry.getValue()));
/*     */     } 
/* 536 */     MediaType mediaType = new MediaType(normalizedType, normalizedSubtype, builder.build());
/*     */     
/* 538 */     return (MediaType)Objects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
/*     */   }
/*     */   
/*     */   private static String normalizeToken(String token) {
/* 542 */     Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(token));
/* 543 */     return Ascii.toLowerCase(token);
/*     */   }
/*     */   
/*     */   private static String normalizeParameterValue(String attribute, String value) {
/* 547 */     return "charset".equals(attribute) ? Ascii.toLowerCase(value) : value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MediaType parse(String input) {
/* 556 */     Preconditions.checkNotNull(input);
/* 557 */     Tokenizer tokenizer = new Tokenizer(input);
/*     */     try {
/* 559 */       String type = tokenizer.consumeToken(TOKEN_MATCHER);
/* 560 */       tokenizer.consumeCharacter('/');
/* 561 */       String subtype = tokenizer.consumeToken(TOKEN_MATCHER);
/* 562 */       ImmutableListMultimap.Builder<String, String> parameters = ImmutableListMultimap.builder();
/* 563 */       while (tokenizer.hasMore()) {
/* 564 */         String value; tokenizer.consumeCharacter(';');
/* 565 */         tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
/* 566 */         String attribute = tokenizer.consumeToken(TOKEN_MATCHER);
/* 567 */         tokenizer.consumeCharacter('=');
/*     */         
/* 569 */         if ('"' == tokenizer.previewChar()) {
/* 570 */           tokenizer.consumeCharacter('"');
/* 571 */           StringBuilder valueBuilder = new StringBuilder();
/* 572 */           while ('"' != tokenizer.previewChar()) {
/* 573 */             if ('\\' == tokenizer.previewChar()) {
/* 574 */               tokenizer.consumeCharacter('\\');
/* 575 */               valueBuilder.append(tokenizer.consumeCharacter(CharMatcher.ASCII)); continue;
/*     */             } 
/* 577 */             valueBuilder.append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
/*     */           } 
/*     */           
/* 580 */           value = valueBuilder.toString();
/* 581 */           tokenizer.consumeCharacter('"');
/*     */         } else {
/* 583 */           value = tokenizer.consumeToken(TOKEN_MATCHER);
/*     */         } 
/* 585 */         parameters.put(attribute, value);
/*     */       } 
/* 587 */       return create(type, subtype, (Multimap<String, String>)parameters.build());
/* 588 */     } catch (IllegalStateException e) {
/* 589 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class Tokenizer {
/*     */     final String input;
/* 595 */     int position = 0;
/*     */     
/*     */     Tokenizer(String input) {
/* 598 */       this.input = input;
/*     */     }
/*     */     
/*     */     String consumeTokenIfPresent(CharMatcher matcher) {
/* 602 */       Preconditions.checkState(hasMore());
/* 603 */       int startPosition = this.position;
/* 604 */       this.position = matcher.negate().indexIn(this.input, startPosition);
/* 605 */       return hasMore() ? this.input.substring(startPosition, this.position) : this.input.substring(startPosition);
/*     */     }
/*     */     
/*     */     String consumeToken(CharMatcher matcher) {
/* 609 */       int startPosition = this.position;
/* 610 */       String token = consumeTokenIfPresent(matcher);
/* 611 */       Preconditions.checkState((this.position != startPosition));
/* 612 */       return token;
/*     */     }
/*     */     
/*     */     char consumeCharacter(CharMatcher matcher) {
/* 616 */       Preconditions.checkState(hasMore());
/* 617 */       char c = previewChar();
/* 618 */       Preconditions.checkState(matcher.matches(c));
/* 619 */       this.position++;
/* 620 */       return c;
/*     */     }
/*     */     
/*     */     char consumeCharacter(char c) {
/* 624 */       Preconditions.checkState(hasMore());
/* 625 */       Preconditions.checkState((previewChar() == c));
/* 626 */       this.position++;
/* 627 */       return c;
/*     */     }
/*     */     
/*     */     char previewChar() {
/* 631 */       Preconditions.checkState(hasMore());
/* 632 */       return this.input.charAt(this.position);
/*     */     }
/*     */     
/*     */     boolean hasMore() {
/* 636 */       return (this.position >= 0 && this.position < this.input.length());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 641 */     if (obj == this)
/* 642 */       return true; 
/* 643 */     if (obj instanceof MediaType) {
/* 644 */       MediaType that = (MediaType)obj;
/* 645 */       return (this.type.equals(that.type) && this.subtype.equals(that.subtype) && parametersAsMap().equals(that.parametersAsMap()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 650 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 655 */     return Objects.hashCode(new Object[] { this.type, this.subtype, parametersAsMap() });
/*     */   }
/*     */   
/* 658 */   private static final Joiner.MapJoiner PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 665 */     StringBuilder builder = (new StringBuilder()).append(this.type).append('/').append(this.subtype);
/* 666 */     if (!this.parameters.isEmpty()) {
/* 667 */       builder.append("; ");
/* 668 */       ListMultimap listMultimap = Multimaps.transformValues((ListMultimap)this.parameters, new Function<String, String>()
/*     */           {
/*     */             public String apply(String value) {
/* 671 */               return MediaType.TOKEN_MATCHER.matchesAllOf(value) ? value : MediaType.escapeAndQuote(value);
/*     */             }
/*     */           });
/* 674 */       PARAMETER_JOINER.appendTo(builder, listMultimap.entries());
/*     */     } 
/* 676 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private static String escapeAndQuote(String value) {
/* 680 */     StringBuilder escaped = (new StringBuilder(value.length() + 16)).append('"');
/* 681 */     for (char ch : value.toCharArray()) {
/* 682 */       if (ch == '\r' || ch == '\\' || ch == '"') {
/* 683 */         escaped.append('\\');
/*     */       }
/* 685 */       escaped.append(ch);
/*     */     } 
/* 687 */     return escaped.append('"').toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\net\MediaType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */