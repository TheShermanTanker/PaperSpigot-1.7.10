/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.net.Facility;
/*     */ import org.apache.logging.log4j.core.net.Priority;
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
/*     */ @Plugin(name = "SyslogLayout", category = "Core", elementType = "layout", printObject = true)
/*     */ public class SyslogLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*  47 */   public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
/*     */ 
/*     */   
/*     */   private final Facility facility;
/*     */   
/*     */   private final boolean includeNewLine;
/*     */   
/*     */   private final String escapeNewLine;
/*     */   
/*  56 */   private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss ", Locale.ENGLISH);
/*     */ 
/*     */ 
/*     */   
/*  60 */   private final String localHostname = getLocalHostname();
/*     */ 
/*     */ 
/*     */   
/*     */   protected SyslogLayout(Facility facility, boolean includeNL, String escapeNL, Charset charset) {
/*  65 */     super(charset);
/*  66 */     this.facility = facility;
/*  67 */     this.includeNewLine = includeNL;
/*  68 */     this.escapeNewLine = (escapeNL == null) ? null : Matcher.quoteReplacement(escapeNL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toSerializable(LogEvent event) {
/*  79 */     StringBuilder buf = new StringBuilder();
/*     */     
/*  81 */     buf.append("<");
/*  82 */     buf.append(Priority.getPriority(this.facility, event.getLevel()));
/*  83 */     buf.append(">");
/*  84 */     addDate(event.getMillis(), buf);
/*  85 */     buf.append(" ");
/*  86 */     buf.append(this.localHostname);
/*  87 */     buf.append(" ");
/*     */     
/*  89 */     String message = event.getMessage().getFormattedMessage();
/*  90 */     if (null != this.escapeNewLine) {
/*  91 */       message = NEWLINE_PATTERN.matcher(message).replaceAll(this.escapeNewLine);
/*     */     }
/*  93 */     buf.append(message);
/*     */     
/*  95 */     if (this.includeNewLine) {
/*  96 */       buf.append("\n");
/*     */     }
/*  98 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLocalHostname() {
/*     */     try {
/* 110 */       InetAddress addr = InetAddress.getLocalHost();
/* 111 */       return addr.getHostName();
/* 112 */     } catch (UnknownHostException uhe) {
/* 113 */       LOGGER.error("Could not determine local host name", uhe);
/* 114 */       return "UNKNOWN_LOCALHOST";
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void addDate(long timestamp, StringBuilder buf) {
/* 119 */     int index = buf.length() + 4;
/* 120 */     buf.append(this.dateFormat.format(new Date(timestamp)));
/*     */     
/* 122 */     if (buf.charAt(index) == '0') {
/* 123 */       buf.setCharAt(index, ' ');
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
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 138 */     Map<String, String> result = new HashMap<String, String>();
/* 139 */     result.put("structured", "false");
/* 140 */     result.put("formatType", "logfilepatternreceiver");
/* 141 */     result.put("dateFormat", this.dateFormat.toPattern());
/* 142 */     result.put("format", "<LEVEL>TIMESTAMP PROP(HOSTNAME) MESSAGE");
/* 143 */     return result;
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
/*     */   @PluginFactory
/*     */   public static SyslogLayout createLayout(@PluginAttribute("facility") String facility, @PluginAttribute("newLine") String includeNL, @PluginAttribute("newLineEscape") String escapeNL, @PluginAttribute("charset") String charsetName) {
/* 160 */     Charset charset = Charsets.getSupportedCharset(charsetName);
/* 161 */     boolean includeNewLine = Boolean.parseBoolean(includeNL);
/* 162 */     Facility f = Facility.toFacility(facility, Facility.LOCAL0);
/* 163 */     return new SyslogLayout(f, includeNewLine, escapeNL, charset);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\SyslogLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */