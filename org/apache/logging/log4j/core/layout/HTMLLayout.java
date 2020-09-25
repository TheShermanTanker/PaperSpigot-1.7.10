/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Constants;
/*     */ import org.apache.logging.log4j.core.helpers.Transform;
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
/*     */ @Plugin(name = "HTMLLayout", category = "Core", elementType = "layout", printObject = true)
/*     */ public final class HTMLLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final int BUF_SIZE = 256;
/*     */   private static final String TRACE_PREFIX = "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
/*  53 */   private static final String REGEXP = Constants.LINE_SEP.equals("\n") ? "\n" : (Constants.LINE_SEP + "|\n");
/*     */   
/*     */   private static final String DEFAULT_TITLE = "Log4j Log Messages";
/*     */   
/*     */   private static final String DEFAULT_CONTENT_TYPE = "text/html";
/*     */   
/*  59 */   private final long jvmStartTime = ManagementFactory.getRuntimeMXBean().getStartTime();
/*     */   
/*     */   private final boolean locationInfo;
/*     */   private final String title;
/*     */   private final String contentType;
/*     */   private final String font;
/*     */   private final String fontSize;
/*     */   private final String headerSize;
/*     */   
/*     */   private enum FontSize
/*     */   {
/*  70 */     SMALLER("smaller"), XXSMALL("xx-small"), XSMALL("x-small"), SMALL("small"), MEDIUM("medium"), LARGE("large"),
/*  71 */     XLARGE("x-large"), XXLARGE("xx-large"), LARGER("larger");
/*     */     
/*     */     private final String size;
/*     */     
/*     */     FontSize(String size) {
/*  76 */       this.size = size;
/*     */     }
/*     */     
/*     */     public String getFontSize() {
/*  80 */       return this.size;
/*     */     }
/*     */     
/*     */     public static FontSize getFontSize(String size) {
/*  84 */       for (FontSize fontSize : values()) {
/*  85 */         if (fontSize.size.equals(size)) {
/*  86 */           return fontSize;
/*     */         }
/*     */       } 
/*  89 */       return SMALL;
/*     */     }
/*     */     
/*     */     public FontSize larger() {
/*  93 */       return (ordinal() < XXLARGE.ordinal()) ? values()[ordinal() + 1] : this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HTMLLayout(boolean locationInfo, String title, String contentType, Charset charset, String font, String fontSize, String headerSize) {
/* 103 */     super(charset);
/* 104 */     this.locationInfo = locationInfo;
/* 105 */     this.title = title;
/* 106 */     this.contentType = contentType;
/* 107 */     this.font = font;
/* 108 */     this.fontSize = fontSize;
/* 109 */     this.headerSize = headerSize;
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
/* 120 */     StringBuilder sbuf = new StringBuilder(256);
/*     */     
/* 122 */     sbuf.append(Constants.LINE_SEP).append("<tr>").append(Constants.LINE_SEP);
/*     */     
/* 124 */     sbuf.append("<td>");
/* 125 */     sbuf.append(event.getMillis() - this.jvmStartTime);
/* 126 */     sbuf.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 128 */     String escapedThread = Transform.escapeHtmlTags(event.getThreadName());
/* 129 */     sbuf.append("<td title=\"").append(escapedThread).append(" thread\">");
/* 130 */     sbuf.append(escapedThread);
/* 131 */     sbuf.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 133 */     sbuf.append("<td title=\"Level\">");
/* 134 */     if (event.getLevel().equals(Level.DEBUG)) {
/* 135 */       sbuf.append("<font color=\"#339933\">");
/* 136 */       sbuf.append(Transform.escapeHtmlTags(String.valueOf(event.getLevel())));
/* 137 */       sbuf.append("</font>");
/* 138 */     } else if (event.getLevel().isAtLeastAsSpecificAs(Level.WARN)) {
/* 139 */       sbuf.append("<font color=\"#993300\"><strong>");
/* 140 */       sbuf.append(Transform.escapeHtmlTags(String.valueOf(event.getLevel())));
/* 141 */       sbuf.append("</strong></font>");
/*     */     } else {
/* 143 */       sbuf.append(Transform.escapeHtmlTags(String.valueOf(event.getLevel())));
/*     */     } 
/* 145 */     sbuf.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 147 */     String escapedLogger = Transform.escapeHtmlTags(event.getLoggerName());
/* 148 */     if (escapedLogger.isEmpty()) {
/* 149 */       escapedLogger = "root";
/*     */     }
/* 151 */     sbuf.append("<td title=\"").append(escapedLogger).append(" logger\">");
/* 152 */     sbuf.append(escapedLogger);
/* 153 */     sbuf.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 155 */     if (this.locationInfo) {
/* 156 */       StackTraceElement element = event.getSource();
/* 157 */       sbuf.append("<td>");
/* 158 */       sbuf.append(Transform.escapeHtmlTags(element.getFileName()));
/* 159 */       sbuf.append(':');
/* 160 */       sbuf.append(element.getLineNumber());
/* 161 */       sbuf.append("</td>").append(Constants.LINE_SEP);
/*     */     } 
/*     */     
/* 164 */     sbuf.append("<td title=\"Message\">");
/* 165 */     sbuf.append(Transform.escapeHtmlTags(event.getMessage().getFormattedMessage()).replaceAll(REGEXP, "<br />"));
/* 166 */     sbuf.append("</td>").append(Constants.LINE_SEP);
/* 167 */     sbuf.append("</tr>").append(Constants.LINE_SEP);
/*     */     
/* 169 */     if (event.getContextStack().getDepth() > 0) {
/* 170 */       sbuf.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : ").append(this.fontSize);
/* 171 */       sbuf.append(";\" colspan=\"6\" ");
/* 172 */       sbuf.append("title=\"Nested Diagnostic Context\">");
/* 173 */       sbuf.append("NDC: ").append(Transform.escapeHtmlTags(event.getContextStack().toString()));
/* 174 */       sbuf.append("</td></tr>").append(Constants.LINE_SEP);
/*     */     } 
/*     */     
/* 177 */     if (event.getContextMap().size() > 0) {
/* 178 */       sbuf.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : ").append(this.fontSize);
/* 179 */       sbuf.append(";\" colspan=\"6\" ");
/* 180 */       sbuf.append("title=\"Mapped Diagnostic Context\">");
/* 181 */       sbuf.append("MDC: ").append(Transform.escapeHtmlTags(event.getContextMap().toString()));
/* 182 */       sbuf.append("</td></tr>").append(Constants.LINE_SEP);
/*     */     } 
/*     */     
/* 185 */     Throwable throwable = event.getThrown();
/* 186 */     if (throwable != null) {
/* 187 */       sbuf.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : ").append(this.fontSize);
/* 188 */       sbuf.append(";\" colspan=\"6\">");
/* 189 */       appendThrowableAsHTML(throwable, sbuf);
/* 190 */       sbuf.append("</td></tr>").append(Constants.LINE_SEP);
/*     */     } 
/*     */     
/* 193 */     return sbuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 203 */     return new HashMap<String, String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 211 */     return this.contentType;
/*     */   }
/*     */   
/*     */   private void appendThrowableAsHTML(Throwable throwable, StringBuilder sbuf) {
/* 215 */     StringWriter sw = new StringWriter();
/* 216 */     PrintWriter pw = new PrintWriter(sw);
/*     */     try {
/* 218 */       throwable.printStackTrace(pw);
/* 219 */     } catch (RuntimeException ex) {}
/*     */ 
/*     */     
/* 222 */     pw.flush();
/* 223 */     LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
/* 224 */     ArrayList<String> lines = new ArrayList<String>();
/*     */     try {
/* 226 */       String line = reader.readLine();
/* 227 */       while (line != null) {
/* 228 */         lines.add(line);
/* 229 */         line = reader.readLine();
/*     */       } 
/* 231 */     } catch (IOException ex) {
/* 232 */       if (ex instanceof java.io.InterruptedIOException) {
/* 233 */         Thread.currentThread().interrupt();
/*     */       }
/* 235 */       lines.add(ex.toString());
/*     */     } 
/* 237 */     boolean first = true;
/* 238 */     for (String line : lines) {
/* 239 */       if (!first) {
/* 240 */         sbuf.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;");
/*     */       } else {
/* 242 */         first = false;
/*     */       } 
/* 244 */       sbuf.append(Transform.escapeHtmlTags(line));
/* 245 */       sbuf.append(Constants.LINE_SEP);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getHeader() {
/* 255 */     StringBuilder sbuf = new StringBuilder();
/* 256 */     sbuf.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" ");
/* 257 */     sbuf.append("\"http://www.w3.org/TR/html4/loose.dtd\">");
/* 258 */     sbuf.append(Constants.LINE_SEP);
/* 259 */     sbuf.append("<html>").append(Constants.LINE_SEP);
/* 260 */     sbuf.append("<head>").append(Constants.LINE_SEP);
/* 261 */     sbuf.append("<meta charset=\"").append(getCharset()).append("\"/>").append(Constants.LINE_SEP);
/* 262 */     sbuf.append("<title>").append(this.title).append("</title>").append(Constants.LINE_SEP);
/* 263 */     sbuf.append("<style type=\"text/css\">").append(Constants.LINE_SEP);
/* 264 */     sbuf.append("<!--").append(Constants.LINE_SEP);
/* 265 */     sbuf.append("body, table {font-family:").append(this.font).append("; font-size: ");
/* 266 */     sbuf.append(this.headerSize).append(";}").append(Constants.LINE_SEP);
/* 267 */     sbuf.append("th {background: #336699; color: #FFFFFF; text-align: left;}").append(Constants.LINE_SEP);
/* 268 */     sbuf.append("-->").append(Constants.LINE_SEP);
/* 269 */     sbuf.append("</style>").append(Constants.LINE_SEP);
/* 270 */     sbuf.append("</head>").append(Constants.LINE_SEP);
/* 271 */     sbuf.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">").append(Constants.LINE_SEP);
/* 272 */     sbuf.append("<hr size=\"1\" noshade>").append(Constants.LINE_SEP);
/* 273 */     sbuf.append("Log session start time " + new Date() + "<br>").append(Constants.LINE_SEP);
/* 274 */     sbuf.append("<br>").append(Constants.LINE_SEP);
/* 275 */     sbuf.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">");
/*     */     
/* 277 */     sbuf.append(Constants.LINE_SEP);
/* 278 */     sbuf.append("<tr>").append(Constants.LINE_SEP);
/* 279 */     sbuf.append("<th>Time</th>").append(Constants.LINE_SEP);
/* 280 */     sbuf.append("<th>Thread</th>").append(Constants.LINE_SEP);
/* 281 */     sbuf.append("<th>Level</th>").append(Constants.LINE_SEP);
/* 282 */     sbuf.append("<th>Logger</th>").append(Constants.LINE_SEP);
/* 283 */     if (this.locationInfo) {
/* 284 */       sbuf.append("<th>File:Line</th>").append(Constants.LINE_SEP);
/*     */     }
/* 286 */     sbuf.append("<th>Message</th>").append(Constants.LINE_SEP);
/* 287 */     sbuf.append("</tr>").append(Constants.LINE_SEP);
/* 288 */     return sbuf.toString().getBytes(getCharset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getFooter() {
/* 297 */     StringBuilder sbuf = new StringBuilder();
/* 298 */     sbuf.append("</table>").append(Constants.LINE_SEP);
/* 299 */     sbuf.append("<br>").append(Constants.LINE_SEP);
/* 300 */     sbuf.append("</body></html>");
/* 301 */     return sbuf.toString().getBytes(getCharset());
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
/*     */   @PluginFactory
/*     */   public static HTMLLayout createLayout(@PluginAttribute("locationInfo") String locationInfo, @PluginAttribute("title") String title, @PluginAttribute("contentType") String contentType, @PluginAttribute("charset") String charsetName, @PluginAttribute("fontSize") String fontSize, @PluginAttribute("fontName") String font) {
/* 322 */     Charset charset = Charsets.getSupportedCharset(charsetName, Charsets.UTF_8);
/* 323 */     if (font == null) {
/* 324 */       font = "arial,sans-serif";
/*     */     }
/* 326 */     FontSize fs = FontSize.getFontSize(fontSize);
/* 327 */     fontSize = fs.getFontSize();
/* 328 */     String headerSize = fs.larger().getFontSize();
/* 329 */     boolean info = Boolean.parseBoolean(locationInfo);
/* 330 */     if (title == null) {
/* 331 */       title = "Log4j Log Messages";
/*     */     }
/* 333 */     if (contentType == null) {
/* 334 */       contentType = "text/html; charset=" + charset;
/*     */     }
/* 336 */     return new HTMLLayout(info, title, contentType, charset, font, fontSize, headerSize);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\HTMLLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */