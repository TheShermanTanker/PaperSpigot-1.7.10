/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalizedMessage
/*     */   implements Message, LoggerNameAwareMessage
/*     */ {
/*     */   private static final long serialVersionUID = 3893703791567290742L;
/*     */   private String bundleId;
/*     */   private transient ResourceBundle bundle;
/*     */   private Locale locale;
/*  46 */   private transient StatusLogger logger = StatusLogger.getLogger();
/*     */   
/*     */   private String loggerName;
/*     */   
/*     */   private String messagePattern;
/*     */   
/*     */   private String[] stringArgs;
/*     */   
/*     */   private transient Object[] argArray;
/*     */   
/*     */   private String formattedMessage;
/*     */   
/*     */   private transient Throwable throwable;
/*     */ 
/*     */   
/*     */   public LocalizedMessage(String messagePattern, Object[] arguments) {
/*  62 */     this((ResourceBundle)null, (Locale)null, messagePattern, arguments);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String bundleId, String key, Object[] arguments) {
/*  66 */     this(bundleId, (Locale)null, key, arguments);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle bundle, String key, Object[] arguments) {
/*  70 */     this(bundle, (Locale)null, key, arguments);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String bundleId, Locale locale, String key, Object[] arguments) {
/*  74 */     this.messagePattern = key;
/*  75 */     this.argArray = arguments;
/*  76 */     this.throwable = null;
/*  77 */     setup(bundleId, null, locale);
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalizedMessage(ResourceBundle bundle, Locale locale, String key, Object[] arguments) {
/*  82 */     this.messagePattern = key;
/*  83 */     this.argArray = arguments;
/*  84 */     this.throwable = null;
/*  85 */     setup(null, bundle, locale);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(Locale locale, String key, Object[] arguments) {
/*  89 */     this((ResourceBundle)null, locale, key, arguments);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String messagePattern, Object arg) {
/*  93 */     this((ResourceBundle)null, (Locale)null, messagePattern, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String bundleId, String key, Object arg) {
/*  97 */     this(bundleId, (Locale)null, key, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle bundle, String key, Object arg) {
/* 101 */     this(bundle, (Locale)null, key, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String bundleId, Locale locale, String key, Object arg) {
/* 105 */     this(bundleId, locale, key, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle bundle, Locale locale, String key, Object arg) {
/* 109 */     this(bundle, locale, key, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(Locale locale, String key, Object arg) {
/* 113 */     this((ResourceBundle)null, locale, key, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String messagePattern, Object arg1, Object arg2) {
/* 117 */     this((ResourceBundle)null, (Locale)null, messagePattern, new Object[] { arg1, arg2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String bundleId, String key, Object arg1, Object arg2) {
/* 121 */     this(bundleId, (Locale)null, key, new Object[] { arg1, arg2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle bundle, String key, Object arg1, Object arg2) {
/* 125 */     this(bundle, (Locale)null, key, new Object[] { arg1, arg2 });
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalizedMessage(String bundleId, Locale locale, String key, Object arg1, Object arg2) {
/* 130 */     this(bundleId, locale, key, new Object[] { arg1, arg2 });
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalizedMessage(ResourceBundle bundle, Locale locale, String key, Object arg1, Object arg2) {
/* 135 */     this(bundle, locale, key, new Object[] { arg1, arg2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(Locale locale, String key, Object arg1, Object arg2) {
/* 139 */     this((ResourceBundle)null, locale, key, new Object[] { arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoggerName(String name) {
/* 148 */     this.loggerName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLoggerName() {
/* 157 */     return this.loggerName;
/*     */   }
/*     */   
/*     */   private void setup(String bundleId, ResourceBundle bundle, Locale locale) {
/* 161 */     this.bundleId = bundleId;
/* 162 */     this.bundle = bundle;
/* 163 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedMessage() {
/* 172 */     if (this.formattedMessage != null) {
/* 173 */       return this.formattedMessage;
/*     */     }
/* 175 */     ResourceBundle bundle = this.bundle;
/* 176 */     if (bundle == null) {
/* 177 */       if (this.bundleId != null) {
/* 178 */         bundle = getBundle(this.bundleId, this.locale, false);
/*     */       } else {
/* 180 */         bundle = getBundle(this.loggerName, this.locale, true);
/*     */       } 
/*     */     }
/* 183 */     String messagePattern = getFormat();
/* 184 */     String msgPattern = (bundle == null || !bundle.containsKey(messagePattern)) ? messagePattern : bundle.getString(messagePattern);
/*     */     
/* 186 */     Object[] array = (this.argArray == null) ? (Object[])this.stringArgs : this.argArray;
/* 187 */     FormattedMessage msg = new FormattedMessage(msgPattern, array);
/* 188 */     this.formattedMessage = msg.getFormattedMessage();
/* 189 */     this.throwable = msg.getThrowable();
/* 190 */     return this.formattedMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 195 */     return this.messagePattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getParameters() {
/* 200 */     if (this.argArray != null) {
/* 201 */       return this.argArray;
/*     */     }
/* 203 */     return (Object[])this.stringArgs;
/*     */   }
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 208 */     return this.throwable;
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
/*     */   protected ResourceBundle getBundle(String key, Locale locale, boolean loop) {
/* 220 */     ResourceBundle rb = null;
/*     */     
/* 222 */     if (key == null) {
/* 223 */       return null;
/*     */     }
/*     */     try {
/* 226 */       if (locale != null) {
/* 227 */         rb = ResourceBundle.getBundle(key, locale);
/*     */       } else {
/* 229 */         rb = ResourceBundle.getBundle(key);
/*     */       } 
/* 231 */     } catch (MissingResourceException ex) {
/* 232 */       if (!loop) {
/* 233 */         this.logger.debug("Unable to locate ResourceBundle " + key);
/* 234 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     String substr = key;
/*     */     int i;
/* 240 */     while (rb == null && (i = substr.lastIndexOf('.')) > 0) {
/* 241 */       substr = substr.substring(0, i);
/*     */       try {
/* 243 */         if (locale != null) {
/* 244 */           rb = ResourceBundle.getBundle(substr, locale); continue;
/*     */         } 
/* 246 */         rb = ResourceBundle.getBundle(substr);
/*     */       }
/* 248 */       catch (MissingResourceException ex) {
/* 249 */         this.logger.debug("Unable to locate ResourceBundle " + substr);
/*     */       } 
/*     */     } 
/* 252 */     return rb;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 256 */     out.defaultWriteObject();
/* 257 */     getFormattedMessage();
/* 258 */     out.writeUTF(this.formattedMessage);
/* 259 */     out.writeUTF(this.messagePattern);
/* 260 */     out.writeUTF(this.bundleId);
/* 261 */     out.writeInt(this.argArray.length);
/* 262 */     this.stringArgs = new String[this.argArray.length];
/* 263 */     int i = 0;
/* 264 */     for (Object obj : this.argArray) {
/* 265 */       this.stringArgs[i] = obj.toString();
/* 266 */       i++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 271 */     in.defaultReadObject();
/* 272 */     this.formattedMessage = in.readUTF();
/* 273 */     this.messagePattern = in.readUTF();
/* 274 */     this.bundleId = in.readUTF();
/* 275 */     int length = in.readInt();
/* 276 */     this.stringArgs = new String[length];
/* 277 */     for (int i = 0; i < length; i++) {
/* 278 */       this.stringArgs[i] = in.readUTF();
/*     */     }
/* 280 */     this.logger = StatusLogger.getLogger();
/* 281 */     this.bundle = null;
/* 282 */     this.argArray = null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\LocalizedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */