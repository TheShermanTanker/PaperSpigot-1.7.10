/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.helpers.Throwables;
/*     */ import org.apache.logging.log4j.core.helpers.Transform;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MultiformatMessage;
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
/*     */ @Plugin(name = "XMLLayout", category = "Core", elementType = "layout", printObject = true)
/*     */ public class XMLLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final String XML_NAMESPACE = "http://logging.apache.org/log4j/2.0/events";
/*     */   private static final String ROOT_TAG = "Events";
/*     */   private static final int DEFAULT_SIZE = 256;
/*     */   private static final String DEFAULT_EOL = "\r\n";
/*     */   private static final String COMPACT_EOL = "";
/*     */   private static final String DEFAULT_INDENT = "  ";
/*     */   private static final String COMPACT_INDENT = "";
/*     */   private static final String DEFAULT_NS_PREFIX = "log4j";
/*  93 */   private static final String[] FORMATS = new String[] { "xml" };
/*     */   
/*     */   private final boolean locationInfo;
/*     */   
/*     */   private final boolean properties;
/*     */   private final boolean complete;
/*     */   private final String namespacePrefix;
/*     */   private final String eol;
/*     */   private final String indent1;
/*     */   private final String indent2;
/*     */   private final String indent3;
/*     */   
/*     */   protected XMLLayout(boolean locationInfo, boolean properties, boolean complete, boolean compact, String nsPrefix, Charset charset) {
/* 106 */     super(charset);
/* 107 */     this.locationInfo = locationInfo;
/* 108 */     this.properties = properties;
/* 109 */     this.complete = complete;
/* 110 */     this.eol = compact ? "" : "\r\n";
/* 111 */     this.indent1 = compact ? "" : "  ";
/* 112 */     this.indent2 = this.indent1 + this.indent1;
/* 113 */     this.indent3 = this.indent2 + this.indent1;
/* 114 */     this.namespacePrefix = (Strings.isEmpty(nsPrefix) ? "log4j" : nsPrefix) + ":";
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
/* 125 */     StringBuilder buf = new StringBuilder(256);
/*     */     
/* 127 */     buf.append(this.indent1);
/* 128 */     buf.append('<');
/* 129 */     if (!this.complete) {
/* 130 */       buf.append(this.namespacePrefix);
/*     */     }
/* 132 */     buf.append("Event logger=\"");
/* 133 */     String name = event.getLoggerName();
/* 134 */     if (name.isEmpty()) {
/* 135 */       name = "root";
/*     */     }
/* 137 */     buf.append(Transform.escapeHtmlTags(name));
/* 138 */     buf.append("\" timestamp=\"");
/* 139 */     buf.append(event.getMillis());
/* 140 */     buf.append("\" level=\"");
/* 141 */     buf.append(Transform.escapeHtmlTags(String.valueOf(event.getLevel())));
/* 142 */     buf.append("\" thread=\"");
/* 143 */     buf.append(Transform.escapeHtmlTags(event.getThreadName()));
/* 144 */     buf.append("\">");
/* 145 */     buf.append(this.eol);
/*     */     
/* 147 */     Message msg = event.getMessage();
/* 148 */     if (msg != null) {
/* 149 */       boolean xmlSupported = false;
/* 150 */       if (msg instanceof MultiformatMessage) {
/* 151 */         String[] formats = ((MultiformatMessage)msg).getFormats();
/* 152 */         for (String format : formats) {
/* 153 */           if (format.equalsIgnoreCase("XML")) {
/* 154 */             xmlSupported = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 159 */       buf.append(this.indent2);
/* 160 */       buf.append('<');
/* 161 */       if (!this.complete) {
/* 162 */         buf.append(this.namespacePrefix);
/*     */       }
/* 164 */       buf.append("Message>");
/* 165 */       if (xmlSupported) {
/* 166 */         buf.append(((MultiformatMessage)msg).getFormattedMessage(FORMATS));
/*     */       } else {
/* 168 */         buf.append("<![CDATA[");
/*     */ 
/*     */         
/* 171 */         Transform.appendEscapingCDATA(buf, event.getMessage().getFormattedMessage());
/* 172 */         buf.append("]]>");
/*     */       } 
/* 174 */       buf.append("</");
/* 175 */       if (!this.complete) {
/* 176 */         buf.append(this.namespacePrefix);
/*     */       }
/* 178 */       buf.append("Message>");
/* 179 */       buf.append(this.eol);
/*     */     } 
/*     */     
/* 182 */     if (event.getContextStack().getDepth() > 0) {
/* 183 */       buf.append(this.indent2);
/* 184 */       buf.append('<');
/* 185 */       if (!this.complete) {
/* 186 */         buf.append(this.namespacePrefix);
/*     */       }
/* 188 */       buf.append("NDC><![CDATA[");
/* 189 */       Transform.appendEscapingCDATA(buf, event.getContextStack().toString());
/* 190 */       buf.append("]]></");
/* 191 */       if (!this.complete) {
/* 192 */         buf.append(this.namespacePrefix);
/*     */       }
/* 194 */       buf.append("NDC>");
/* 195 */       buf.append(this.eol);
/*     */     } 
/*     */     
/* 198 */     Throwable throwable = event.getThrown();
/* 199 */     if (throwable != null) {
/* 200 */       List<String> s = Throwables.toStringList(throwable);
/* 201 */       buf.append(this.indent2);
/* 202 */       buf.append('<');
/* 203 */       if (!this.complete) {
/* 204 */         buf.append(this.namespacePrefix);
/*     */       }
/* 206 */       buf.append("Throwable><![CDATA[");
/* 207 */       for (String str : s) {
/* 208 */         Transform.appendEscapingCDATA(buf, str);
/* 209 */         buf.append(this.eol);
/*     */       } 
/* 211 */       buf.append("]]></");
/* 212 */       if (!this.complete) {
/* 213 */         buf.append(this.namespacePrefix);
/*     */       }
/* 215 */       buf.append("Throwable>");
/* 216 */       buf.append(this.eol);
/*     */     } 
/*     */     
/* 219 */     if (this.locationInfo) {
/* 220 */       StackTraceElement element = event.getSource();
/* 221 */       buf.append(this.indent2);
/* 222 */       buf.append('<');
/* 223 */       if (!this.complete) {
/* 224 */         buf.append(this.namespacePrefix);
/*     */       }
/* 226 */       buf.append("LocationInfo class=\"");
/* 227 */       buf.append(Transform.escapeHtmlTags(element.getClassName()));
/* 228 */       buf.append("\" method=\"");
/* 229 */       buf.append(Transform.escapeHtmlTags(element.getMethodName()));
/* 230 */       buf.append("\" file=\"");
/* 231 */       buf.append(Transform.escapeHtmlTags(element.getFileName()));
/* 232 */       buf.append("\" line=\"");
/* 233 */       buf.append(element.getLineNumber());
/* 234 */       buf.append("\"/>");
/* 235 */       buf.append(this.eol);
/*     */     } 
/*     */     
/* 238 */     if (this.properties && event.getContextMap().size() > 0) {
/* 239 */       buf.append(this.indent2);
/* 240 */       buf.append('<');
/* 241 */       if (!this.complete) {
/* 242 */         buf.append(this.namespacePrefix);
/*     */       }
/* 244 */       buf.append("Properties>");
/* 245 */       buf.append(this.eol);
/* 246 */       for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)event.getContextMap().entrySet()) {
/* 247 */         buf.append(this.indent3);
/* 248 */         buf.append('<');
/* 249 */         if (!this.complete) {
/* 250 */           buf.append(this.namespacePrefix);
/*     */         }
/* 252 */         buf.append("Data name=\"");
/* 253 */         buf.append(Transform.escapeHtmlTags(entry.getKey()));
/* 254 */         buf.append("\" value=\"");
/* 255 */         buf.append(Transform.escapeHtmlTags(String.valueOf(entry.getValue())));
/* 256 */         buf.append("\"/>");
/* 257 */         buf.append(this.eol);
/*     */       } 
/* 259 */       buf.append(this.indent2);
/* 260 */       buf.append("</");
/* 261 */       if (!this.complete) {
/* 262 */         buf.append(this.namespacePrefix);
/*     */       }
/* 264 */       buf.append("Properties>");
/* 265 */       buf.append(this.eol);
/*     */     } 
/*     */     
/* 268 */     buf.append(this.indent1);
/* 269 */     buf.append("</");
/* 270 */     if (!this.complete) {
/* 271 */       buf.append(this.namespacePrefix);
/*     */     }
/* 273 */     buf.append("Event>");
/* 274 */     buf.append(this.eol);
/*     */     
/* 276 */     return buf.toString();
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
/*     */   public byte[] getHeader() {
/* 290 */     if (!this.complete) {
/* 291 */       return null;
/*     */     }
/* 293 */     StringBuilder buf = new StringBuilder();
/* 294 */     buf.append("<?xml version=\"1.0\" encoding=\"");
/* 295 */     buf.append(getCharset().name());
/* 296 */     buf.append("\"?>");
/* 297 */     buf.append(this.eol);
/*     */     
/* 299 */     buf.append('<');
/* 300 */     buf.append("Events");
/* 301 */     buf.append(" xmlns=\"http://logging.apache.org/log4j/2.0/events\">");
/* 302 */     buf.append(this.eol);
/* 303 */     return buf.toString().getBytes(getCharset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getFooter() {
/* 314 */     if (!this.complete) {
/* 315 */       return null;
/*     */     }
/* 317 */     return ("</Events>" + this.eol).getBytes(getCharset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 328 */     Map<String, String> result = new HashMap<String, String>();
/*     */     
/* 330 */     result.put("xsd", "log4j-events.xsd");
/* 331 */     result.put("version", "2.0");
/* 332 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 340 */     return "text/xml; charset=" + getCharset();
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
/*     */   @PluginFactory
/*     */   public static XMLLayout createLayout(@PluginAttribute("locationInfo") String locationInfo, @PluginAttribute("properties") String properties, @PluginAttribute("complete") String completeStr, @PluginAttribute("compact") String compactStr, @PluginAttribute("namespacePrefix") String namespacePrefix, @PluginAttribute("charset") String charsetName) {
/* 362 */     Charset charset = Charsets.getSupportedCharset(charsetName, Charsets.UTF_8);
/* 363 */     boolean info = Boolean.parseBoolean(locationInfo);
/* 364 */     boolean props = Boolean.parseBoolean(properties);
/* 365 */     boolean complete = Boolean.parseBoolean(completeStr);
/* 366 */     boolean compact = Boolean.parseBoolean(compactStr);
/* 367 */     return new XMLLayout(info, props, complete, compact, namespacePrefix, charset);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\XMLLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */