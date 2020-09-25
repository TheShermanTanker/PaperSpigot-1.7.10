/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import com.fasterxml.jackson.core.JsonParser;
/*     */ import com.fasterxml.jackson.databind.JsonNode;
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.config.plugins.ResolverUtil;
/*     */ import org.apache.logging.log4j.core.helpers.FileUtils;
/*     */ import org.apache.logging.log4j.status.StatusConsoleListener;
/*     */ import org.apache.logging.log4j.status.StatusListener;
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
/*     */ public class JSONConfiguration
/*     */   extends BaseConfiguration
/*     */   implements Reconfigurable
/*     */ {
/*  54 */   private static final String[] VERBOSE_CLASSES = new String[] { ResolverUtil.class.getName() };
/*     */   
/*     */   private static final int BUF_SIZE = 16384;
/*     */   
/*  58 */   private final List<Status> status = new ArrayList<Status>();
/*     */   
/*     */   private JsonNode root;
/*     */   
/*  62 */   private final List<String> messages = new ArrayList<String>();
/*     */   
/*     */   private final File configFile;
/*     */   
/*     */   public JSONConfiguration(ConfigurationFactory.ConfigurationSource configSource) {
/*  67 */     this.configFile = configSource.getFile();
/*     */ 
/*     */     
/*     */     try {
/*  71 */       InputStream configStream = configSource.getInputStream();
/*  72 */       byte[] buffer = toByteArray(configStream);
/*  73 */       configStream.close();
/*  74 */       InputStream is = new ByteArrayInputStream(buffer);
/*  75 */       ObjectMapper mapper = (new ObjectMapper()).configure(JsonParser.Feature.ALLOW_COMMENTS, true);
/*  76 */       this.root = mapper.readTree(is);
/*  77 */       if (this.root.size() == 1) {
/*  78 */         Iterator<JsonNode> i = this.root.elements();
/*  79 */         this.root = i.next();
/*     */       } 
/*  81 */       processAttributes(this.rootNode, this.root);
/*  82 */       Level status = getDefaultStatus();
/*  83 */       boolean verbose = false;
/*  84 */       PrintStream stream = System.out;
/*  85 */       for (Map.Entry<String, String> entry : this.rootNode.getAttributes().entrySet()) {
/*  86 */         if ("status".equalsIgnoreCase(entry.getKey())) {
/*  87 */           status = Level.toLevel(getStrSubstitutor().replace(entry.getValue()), null);
/*  88 */           if (status == null) {
/*  89 */             status = Level.ERROR;
/*  90 */             this.messages.add("Invalid status specified: " + (String)entry.getValue() + ". Defaulting to ERROR");
/*     */           }  continue;
/*  92 */         }  if ("dest".equalsIgnoreCase(entry.getKey())) {
/*  93 */           String dest = entry.getValue();
/*  94 */           if (dest != null) {
/*  95 */             if (dest.equalsIgnoreCase("err")) {
/*  96 */               stream = System.err; continue;
/*     */             } 
/*     */             try {
/*  99 */               File destFile = FileUtils.fileFromURI(new URI(dest));
/* 100 */               String enc = Charset.defaultCharset().name();
/* 101 */               stream = new PrintStream(new FileOutputStream(destFile), true, enc);
/* 102 */             } catch (URISyntaxException use) {
/* 103 */               System.err.println("Unable to write to " + dest + ". Writing to stdout");
/*     */             } 
/*     */           }  continue;
/*     */         } 
/* 107 */         if ("shutdownHook".equalsIgnoreCase(entry.getKey())) {
/* 108 */           String hook = getStrSubstitutor().replace(entry.getValue());
/* 109 */           this.isShutdownHookEnabled = !hook.equalsIgnoreCase("disable"); continue;
/* 110 */         }  if ("verbose".equalsIgnoreCase(entry.getKey())) {
/* 111 */           verbose = Boolean.parseBoolean(getStrSubstitutor().replace(entry.getValue())); continue;
/* 112 */         }  if ("packages".equalsIgnoreCase(entry.getKey())) {
/* 113 */           String[] packages = getStrSubstitutor().replace(entry.getValue()).split(",");
/* 114 */           for (String p : packages)
/* 115 */             PluginManager.addPackage(p);  continue;
/*     */         } 
/* 117 */         if ("name".equalsIgnoreCase(entry.getKey())) {
/* 118 */           setName(getStrSubstitutor().replace(entry.getValue())); continue;
/* 119 */         }  if ("monitorInterval".equalsIgnoreCase(entry.getKey())) {
/* 120 */           int interval = Integer.parseInt(getStrSubstitutor().replace(entry.getValue()));
/* 121 */           if (interval > 0 && this.configFile != null)
/* 122 */             this.monitor = new FileConfigurationMonitor(this, this.configFile, this.listeners, interval);  continue;
/*     */         } 
/* 124 */         if ("advertiser".equalsIgnoreCase(entry.getKey())) {
/* 125 */           createAdvertiser(getStrSubstitutor().replace(entry.getValue()), configSource, buffer, "application/json");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 130 */       Iterator<StatusListener> statusIter = ((StatusLogger)LOGGER).getListeners();
/* 131 */       boolean found = false;
/* 132 */       while (statusIter.hasNext()) {
/* 133 */         StatusListener listener = statusIter.next();
/* 134 */         if (listener instanceof StatusConsoleListener) {
/* 135 */           found = true;
/* 136 */           ((StatusConsoleListener)listener).setLevel(status);
/* 137 */           if (!verbose) {
/* 138 */             ((StatusConsoleListener)listener).setFilters(VERBOSE_CLASSES);
/*     */           }
/*     */         } 
/*     */       } 
/* 142 */       if (!found && status != Level.OFF) {
/* 143 */         StatusConsoleListener listener = new StatusConsoleListener(status, stream);
/* 144 */         if (!verbose) {
/* 145 */           listener.setFilters(VERBOSE_CLASSES);
/*     */         }
/* 147 */         ((StatusLogger)LOGGER).registerListener((StatusListener)listener);
/* 148 */         for (String msg : this.messages) {
/* 149 */           LOGGER.error(msg);
/*     */         }
/*     */       } 
/* 152 */       if (getName() == null) {
/* 153 */         setName(configSource.getLocation());
/*     */       }
/* 155 */     } catch (Exception ex) {
/* 156 */       LOGGER.error("Error parsing " + configSource.getLocation(), ex);
/* 157 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 163 */     super.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setup() {
/* 168 */     Iterator<Map.Entry<String, JsonNode>> iter = this.root.fields();
/* 169 */     List<Node> children = this.rootNode.getChildren();
/* 170 */     while (iter.hasNext()) {
/* 171 */       Map.Entry<String, JsonNode> entry = iter.next();
/* 172 */       JsonNode n = entry.getValue();
/* 173 */       if (n.isObject()) {
/* 174 */         LOGGER.debug("Processing node for object " + (String)entry.getKey());
/* 175 */         children.add(constructNode(entry.getKey(), this.rootNode, n)); continue;
/* 176 */       }  if (n.isArray()) {
/* 177 */         LOGGER.error("Arrays are not supported at the root configuration.");
/*     */       }
/*     */     } 
/* 180 */     LOGGER.debug("Completed parsing configuration");
/* 181 */     if (this.status.size() > 0) {
/* 182 */       for (Status s : this.status) {
/* 183 */         LOGGER.error("Error processing element " + s.name + ": " + s.errorType);
/*     */       }
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Configuration reconfigure() {
/* 191 */     if (this.configFile != null) {
/*     */       try {
/* 193 */         ConfigurationFactory.ConfigurationSource source = new ConfigurationFactory.ConfigurationSource(new FileInputStream(this.configFile), this.configFile);
/*     */         
/* 195 */         return new JSONConfiguration(source);
/* 196 */       } catch (FileNotFoundException ex) {
/* 197 */         LOGGER.error("Cannot locate file " + this.configFile, ex);
/*     */       } 
/*     */     }
/* 200 */     return null;
/*     */   }
/*     */   private Node constructNode(String name, Node parent, JsonNode jsonNode) {
/*     */     String t;
/* 204 */     PluginType<?> type = this.pluginManager.getPluginType(name);
/* 205 */     Node node = new Node(parent, name, type);
/* 206 */     processAttributes(node, jsonNode);
/* 207 */     Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();
/* 208 */     List<Node> children = node.getChildren();
/* 209 */     while (iter.hasNext()) {
/* 210 */       Map.Entry<String, JsonNode> entry = iter.next();
/* 211 */       JsonNode n = entry.getValue();
/* 212 */       if (n.isArray() || n.isObject()) {
/* 213 */         if (type == null) {
/* 214 */           this.status.add(new Status(name, n, ErrorType.CLASS_NOT_FOUND));
/*     */         }
/* 216 */         if (n.isArray()) {
/* 217 */           LOGGER.debug("Processing node for array " + (String)entry.getKey());
/* 218 */           for (int i = 0; i < n.size(); i++) {
/* 219 */             String pluginType = getType(n.get(i), entry.getKey());
/* 220 */             PluginType<?> entryType = this.pluginManager.getPluginType(pluginType);
/* 221 */             Node item = new Node(node, entry.getKey(), entryType);
/* 222 */             processAttributes(item, n.get(i));
/* 223 */             if (pluginType.equals(entry.getKey())) {
/* 224 */               LOGGER.debug("Processing " + (String)entry.getKey() + "[" + i + "]");
/*     */             } else {
/* 226 */               LOGGER.debug("Processing " + pluginType + " " + (String)entry.getKey() + "[" + i + "]");
/*     */             } 
/* 228 */             Iterator<Map.Entry<String, JsonNode>> itemIter = n.get(i).fields();
/* 229 */             List<Node> itemChildren = item.getChildren();
/* 230 */             while (itemIter.hasNext()) {
/* 231 */               Map.Entry<String, JsonNode> itemEntry = itemIter.next();
/* 232 */               if (((JsonNode)itemEntry.getValue()).isObject()) {
/* 233 */                 LOGGER.debug("Processing node for object " + (String)itemEntry.getKey());
/* 234 */                 itemChildren.add(constructNode(itemEntry.getKey(), item, itemEntry.getValue()));
/*     */               } 
/*     */             } 
/* 237 */             children.add(item);
/*     */           }  continue;
/*     */         } 
/* 240 */         LOGGER.debug("Processing node for object " + (String)entry.getKey());
/* 241 */         children.add(constructNode(entry.getKey(), node, n));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (type == null) {
/* 248 */       t = "null";
/*     */     } else {
/* 250 */       t = type.getElementName() + ":" + type.getPluginClass();
/*     */     } 
/*     */     
/* 253 */     String p = (node.getParent() == null) ? "null" : ((node.getParent().getName() == null) ? "root" : node.getParent().getName());
/*     */     
/* 255 */     LOGGER.debug("Returning " + node.getName() + " with parent " + p + " of type " + t);
/* 256 */     return node;
/*     */   }
/*     */   
/*     */   private String getType(JsonNode node, String name) {
/* 260 */     Iterator<Map.Entry<String, JsonNode>> iter = node.fields();
/* 261 */     while (iter.hasNext()) {
/* 262 */       Map.Entry<String, JsonNode> entry = iter.next();
/* 263 */       if (((String)entry.getKey()).equalsIgnoreCase("type")) {
/* 264 */         JsonNode n = entry.getValue();
/* 265 */         if (n.isValueNode()) {
/* 266 */           return n.asText();
/*     */         }
/*     */       } 
/*     */     } 
/* 270 */     return name;
/*     */   }
/*     */   
/*     */   private void processAttributes(Node parent, JsonNode node) {
/* 274 */     Map<String, String> attrs = parent.getAttributes();
/* 275 */     Iterator<Map.Entry<String, JsonNode>> iter = node.fields();
/* 276 */     while (iter.hasNext()) {
/* 277 */       Map.Entry<String, JsonNode> entry = iter.next();
/* 278 */       if (!((String)entry.getKey()).equalsIgnoreCase("type")) {
/* 279 */         JsonNode n = entry.getValue();
/* 280 */         if (n.isValueNode()) {
/* 281 */           attrs.put(entry.getKey(), n.asText());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected byte[] toByteArray(InputStream is) throws IOException {
/* 288 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */ 
/*     */     
/* 291 */     byte[] data = new byte[16384];
/*     */     int nRead;
/* 293 */     while ((nRead = is.read(data, 0, data.length)) != -1) {
/* 294 */       buffer.write(data, 0, nRead);
/*     */     }
/*     */     
/* 297 */     return buffer.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum ErrorType
/*     */   {
/* 304 */     CLASS_NOT_FOUND;
/*     */   }
/*     */ 
/*     */   
/*     */   private class Status
/*     */   {
/*     */     private final JsonNode node;
/*     */     
/*     */     private final String name;
/*     */     private final JSONConfiguration.ErrorType errorType;
/*     */     
/*     */     public Status(String name, JsonNode node, JSONConfiguration.ErrorType errorType) {
/* 316 */       this.name = name;
/* 317 */       this.node = node;
/* 318 */       this.errorType = errorType;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\JSONConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */