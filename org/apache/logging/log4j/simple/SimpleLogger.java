/*     */ package org.apache.logging.log4j.simple;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public class SimpleLogger
/*     */   extends AbstractLogger
/*     */ {
/*     */   private static final char SPACE = ' ';
/*     */   private DateFormat dateFormatter;
/*     */   private Level level;
/*     */   private final boolean showDateTime;
/*     */   private final boolean showContextMap;
/*     */   private PrintStream stream;
/*     */   private final String logName;
/*     */   
/*     */   public SimpleLogger(String name, Level defaultLevel, boolean showLogName, boolean showShortLogName, boolean showDateTime, boolean showContextMap, String dateTimeFormat, MessageFactory messageFactory, PropertiesUtil props, PrintStream stream) {
/*  64 */     super(name, messageFactory);
/*  65 */     String lvl = props.getStringProperty("org.apache.logging.log4j.simplelog." + name + ".level");
/*  66 */     this.level = Level.toLevel(lvl, defaultLevel);
/*  67 */     if (showShortLogName) {
/*  68 */       int index = name.lastIndexOf(".");
/*  69 */       if (index > 0 && index < name.length()) {
/*  70 */         this.logName = name.substring(index + 1);
/*     */       } else {
/*  72 */         this.logName = name;
/*     */       } 
/*  74 */     } else if (showLogName) {
/*  75 */       this.logName = name;
/*     */     } else {
/*  77 */       this.logName = null;
/*     */     } 
/*  79 */     this.showDateTime = showDateTime;
/*  80 */     this.showContextMap = showContextMap;
/*  81 */     this.stream = stream;
/*     */     
/*  83 */     if (showDateTime) {
/*     */       try {
/*  85 */         this.dateFormatter = new SimpleDateFormat(dateTimeFormat);
/*  86 */       } catch (IllegalArgumentException e) {
/*     */         
/*  88 */         this.dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzz");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setStream(PrintStream stream) {
/*  94 */     this.stream = stream;
/*     */   }
/*     */   
/*     */   public Level getLevel() {
/*  98 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(Level level) {
/* 102 */     if (level != null) {
/* 103 */       this.level = level;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(Marker marker, String fqcn, Level level, Message msg, Throwable throwable) {
/*     */     Throwable t;
/* 110 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 112 */     if (this.showDateTime) {
/* 113 */       String dateText; Date now = new Date();
/*     */       
/* 115 */       synchronized (this.dateFormatter) {
/* 116 */         dateText = this.dateFormatter.format(now);
/*     */       } 
/* 118 */       sb.append(dateText);
/* 119 */       sb.append(' ');
/*     */     } 
/*     */     
/* 122 */     sb.append(level.toString());
/* 123 */     sb.append(' ');
/* 124 */     if (this.logName != null && this.logName.length() > 0) {
/* 125 */       sb.append(this.logName);
/* 126 */       sb.append(' ');
/*     */     } 
/* 128 */     sb.append(msg.getFormattedMessage());
/* 129 */     if (this.showContextMap) {
/* 130 */       Map<String, String> mdc = ThreadContext.getContext();
/* 131 */       if (mdc.size() > 0) {
/* 132 */         sb.append(' ');
/* 133 */         sb.append(mdc.toString());
/* 134 */         sb.append(' ');
/*     */       } 
/*     */     } 
/* 137 */     Object[] params = msg.getParameters();
/*     */     
/* 139 */     if (throwable == null && params != null && params[params.length - 1] instanceof Throwable) {
/* 140 */       t = (Throwable)params[params.length - 1];
/*     */     } else {
/* 142 */       t = throwable;
/*     */     } 
/* 144 */     if (t != null) {
/* 145 */       sb.append(' ');
/* 146 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 147 */       t.printStackTrace(new PrintStream(baos));
/* 148 */       sb.append(baos.toString());
/*     */     } 
/* 150 */     this.stream.println(sb.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, String msg) {
/* 155 */     return (this.level.intLevel() >= level.intLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, String msg, Throwable t) {
/* 161 */     return (this.level.intLevel() >= level.intLevel());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, String msg, Object... p1) {
/* 166 */     return (this.level.intLevel() >= level.intLevel());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, Object msg, Throwable t) {
/* 171 */     return (this.level.intLevel() >= level.intLevel());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, Message msg, Throwable t) {
/* 176 */     return (this.level.intLevel() >= level.intLevel());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\simple\SimpleLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */