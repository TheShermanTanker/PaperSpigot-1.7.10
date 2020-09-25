/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "JSONLayout", category = "Core", elementType = "layout", printObject = true)
/*     */ public class JSONLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final int DEFAULT_SIZE = 256;
/*     */   private static final String DEFAULT_EOL = "\r\n";
/*     */   private static final String COMPACT_EOL = "";
/*     */   private static final String DEFAULT_INDENT = "  ";
/*     */   private static final String COMPACT_INDENT = "";
/*  96 */   private static final String[] FORMATS = new String[] { "json" };
/*     */   
/*     */   private final boolean locationInfo;
/*     */   
/*     */   private final boolean properties;
/*     */   private final boolean complete;
/*     */   private final String eol;
/*     */   private final String indent1;
/*     */   private final String indent2;
/*     */   private final String indent3;
/*     */   private final String indent4;
/*     */   private volatile boolean firstLayoutDone;
/*     */   
/*     */   protected JSONLayout(boolean locationInfo, boolean properties, boolean complete, boolean compact, Charset charset) {
/* 110 */     super(charset);
/* 111 */     this.locationInfo = locationInfo;
/* 112 */     this.properties = properties;
/* 113 */     this.complete = complete;
/* 114 */     this.eol = compact ? "" : "\r\n";
/* 115 */     this.indent1 = compact ? "" : "  ";
/* 116 */     this.indent2 = this.indent1 + this.indent1;
/* 117 */     this.indent3 = this.indent2 + this.indent1;
/* 118 */     this.indent4 = this.indent3 + this.indent1;
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
/*     */   public String toSerializable(LogEvent event) {
/* 130 */     StringBuilder buf = new StringBuilder(256);
/*     */     
/* 132 */     boolean check = this.firstLayoutDone;
/* 133 */     if (!this.firstLayoutDone) {
/* 134 */       synchronized (this) {
/* 135 */         check = this.firstLayoutDone;
/* 136 */         if (!check) {
/* 137 */           this.firstLayoutDone = true;
/*     */         } else {
/* 139 */           buf.append(',');
/* 140 */           buf.append(this.eol);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 144 */       buf.append(',');
/* 145 */       buf.append(this.eol);
/*     */     } 
/* 147 */     buf.append(this.indent1);
/* 148 */     buf.append('{');
/* 149 */     buf.append(this.eol);
/* 150 */     buf.append(this.indent2);
/* 151 */     buf.append("\"logger\":\"");
/* 152 */     String name = event.getLoggerName();
/* 153 */     if (name.isEmpty()) {
/* 154 */       name = "root";
/*     */     }
/* 156 */     buf.append(Transform.escapeJsonControlCharacters(name));
/* 157 */     buf.append("\",");
/* 158 */     buf.append(this.eol);
/* 159 */     buf.append(this.indent2);
/* 160 */     buf.append("\"timestamp\":\"");
/* 161 */     buf.append(event.getMillis());
/* 162 */     buf.append("\",");
/* 163 */     buf.append(this.eol);
/* 164 */     buf.append(this.indent2);
/* 165 */     buf.append("\"level\":\"");
/* 166 */     buf.append(Transform.escapeJsonControlCharacters(String.valueOf(event.getLevel())));
/* 167 */     buf.append("\",");
/* 168 */     buf.append(this.eol);
/* 169 */     buf.append(this.indent2);
/* 170 */     buf.append("\"thread\":\"");
/* 171 */     buf.append(Transform.escapeJsonControlCharacters(event.getThreadName()));
/* 172 */     buf.append("\",");
/* 173 */     buf.append(this.eol);
/*     */     
/* 175 */     Message msg = event.getMessage();
/* 176 */     if (msg != null) {
/* 177 */       boolean jsonSupported = false;
/* 178 */       if (msg instanceof MultiformatMessage) {
/* 179 */         String[] formats = ((MultiformatMessage)msg).getFormats();
/* 180 */         for (String format : formats) {
/* 181 */           if (format.equalsIgnoreCase("JSON")) {
/* 182 */             jsonSupported = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 187 */       buf.append(this.indent2);
/* 188 */       buf.append("\"message\":\"");
/* 189 */       if (jsonSupported) {
/* 190 */         buf.append(((MultiformatMessage)msg).getFormattedMessage(FORMATS));
/*     */       } else {
/* 192 */         Transform.appendEscapingCDATA(buf, event.getMessage().getFormattedMessage());
/*     */       } 
/* 194 */       buf.append('"');
/*     */     } 
/*     */     
/* 197 */     if (event.getContextStack().getDepth() > 0) {
/* 198 */       buf.append(",");
/* 199 */       buf.append(this.eol);
/* 200 */       buf.append("\"ndc\":");
/* 201 */       Transform.appendEscapingCDATA(buf, event.getContextStack().toString());
/* 202 */       buf.append("\"");
/*     */     } 
/*     */     
/* 205 */     Throwable throwable = event.getThrown();
/* 206 */     if (throwable != null) {
/* 207 */       buf.append(",");
/* 208 */       buf.append(this.eol);
/* 209 */       buf.append(this.indent2);
/* 210 */       buf.append("\"throwable\":\"");
/* 211 */       List<String> list = Throwables.toStringList(throwable);
/* 212 */       for (String str : list) {
/* 213 */         buf.append(Transform.escapeJsonControlCharacters(str));
/* 214 */         buf.append("\\\\n");
/*     */       } 
/* 216 */       buf.append("\"");
/*     */     } 
/*     */     
/* 219 */     if (this.locationInfo) {
/* 220 */       StackTraceElement element = event.getSource();
/* 221 */       buf.append(",");
/* 222 */       buf.append(this.eol);
/* 223 */       buf.append(this.indent2);
/* 224 */       buf.append("\"LocationInfo\":{");
/* 225 */       buf.append(this.eol);
/* 226 */       buf.append(this.indent3);
/* 227 */       buf.append("\"class\":\"");
/* 228 */       buf.append(Transform.escapeJsonControlCharacters(element.getClassName()));
/* 229 */       buf.append("\",");
/* 230 */       buf.append(this.eol);
/* 231 */       buf.append(this.indent3);
/* 232 */       buf.append("\"method\":\"");
/* 233 */       buf.append(Transform.escapeJsonControlCharacters(element.getMethodName()));
/* 234 */       buf.append("\",");
/* 235 */       buf.append(this.eol);
/* 236 */       buf.append(this.indent3);
/* 237 */       buf.append("\"file\":\"");
/* 238 */       buf.append(Transform.escapeJsonControlCharacters(element.getFileName()));
/* 239 */       buf.append("\",");
/* 240 */       buf.append(this.eol);
/* 241 */       buf.append(this.indent3);
/* 242 */       buf.append("\"line\":\"");
/* 243 */       buf.append(element.getLineNumber());
/* 244 */       buf.append("\"");
/* 245 */       buf.append(this.eol);
/* 246 */       buf.append(this.indent2);
/* 247 */       buf.append("}");
/*     */     } 
/*     */     
/* 250 */     if (this.properties && event.getContextMap().size() > 0) {
/* 251 */       buf.append(",");
/* 252 */       buf.append(this.eol);
/* 253 */       buf.append(this.indent2);
/* 254 */       buf.append("\"Properties\":[");
/* 255 */       buf.append(this.eol);
/* 256 */       Set<Map.Entry<String, String>> entrySet = event.getContextMap().entrySet();
/* 257 */       int i = 1;
/* 258 */       for (Map.Entry<String, String> entry : entrySet) {
/* 259 */         buf.append(this.indent3);
/* 260 */         buf.append('{');
/* 261 */         buf.append(this.eol);
/* 262 */         buf.append(this.indent4);
/* 263 */         buf.append("\"name\":\"");
/* 264 */         buf.append(Transform.escapeJsonControlCharacters(entry.getKey()));
/* 265 */         buf.append("\",");
/* 266 */         buf.append(this.eol);
/* 267 */         buf.append(this.indent4);
/* 268 */         buf.append("\"value\":\"");
/* 269 */         buf.append(Transform.escapeJsonControlCharacters(String.valueOf(entry.getValue())));
/* 270 */         buf.append("\"");
/* 271 */         buf.append(this.eol);
/* 272 */         buf.append(this.indent3);
/* 273 */         buf.append("}");
/* 274 */         if (i < entrySet.size()) {
/* 275 */           buf.append(",");
/*     */         }
/* 277 */         buf.append(this.eol);
/* 278 */         i++;
/*     */       } 
/* 280 */       buf.append(this.indent2);
/* 281 */       buf.append("]");
/*     */     } 
/*     */     
/* 284 */     buf.append(this.eol);
/* 285 */     buf.append(this.indent1);
/* 286 */     buf.append("}");
/*     */     
/* 288 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getHeader() {
/* 298 */     if (!this.complete) {
/* 299 */       return null;
/*     */     }
/* 301 */     StringBuilder buf = new StringBuilder();
/* 302 */     buf.append('[');
/* 303 */     buf.append(this.eol);
/* 304 */     return buf.toString().getBytes(getCharset());
/*     */   }
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
/* 317 */     return (this.eol + "]" + this.eol).getBytes(getCharset());
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
/* 331 */     Map<String, String> result = new HashMap<String, String>();
/* 332 */     result.put("version", "2.0");
/* 333 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 341 */     return "application/json; charset=" + getCharset();
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
/*     */   @PluginFactory
/*     */   public static JSONLayout createLayout(@PluginAttribute("locationInfo") String locationInfo, @PluginAttribute("properties") String properties, @PluginAttribute("complete") String completeStr, @PluginAttribute("compact") String compactStr, @PluginAttribute("charset") String charsetName) {
/* 366 */     Charset charset = Charsets.getSupportedCharset(charsetName, Charsets.UTF_8);
/* 367 */     boolean info = Boolean.parseBoolean(locationInfo);
/* 368 */     boolean props = Boolean.parseBoolean(properties);
/* 369 */     boolean complete = Boolean.parseBoolean(completeStr);
/* 370 */     boolean compact = Boolean.parseBoolean(compactStr);
/* 371 */     return new JSONLayout(info, props, complete, compact, charset);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\JSONLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */