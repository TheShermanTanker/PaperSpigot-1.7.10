/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
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
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.validation.Validator;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.config.plugins.ResolverUtil;
/*     */ import org.apache.logging.log4j.core.helpers.FileUtils;
/*     */ import org.apache.logging.log4j.status.StatusConsoleListener;
/*     */ import org.apache.logging.log4j.status.StatusListener;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLConfiguration
/*     */   extends BaseConfiguration
/*     */   implements Reconfigurable
/*     */ {
/*     */   private static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
/*     */   private static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
/*  72 */   private static final String[] VERBOSE_CLASSES = new String[] { ResolverUtil.class.getName() };
/*     */   
/*     */   private static final String LOG4J_XSD = "Log4j-config.xsd";
/*     */   
/*     */   private static final int BUF_SIZE = 16384;
/*     */   
/*  78 */   private final List<Status> status = new ArrayList<Status>();
/*     */ 
/*     */   
/*     */   private Element rootElement;
/*     */ 
/*     */   
/*     */   private boolean strict;
/*     */ 
/*     */   
/*     */   private String schema;
/*     */ 
/*     */   
/*     */   private final File configFile;
/*     */ 
/*     */ 
/*     */   
/*     */   static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
/*  95 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  96 */     factory.setNamespaceAware(true);
/*  97 */     enableXInclude(factory);
/*  98 */     return factory.newDocumentBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void enableXInclude(DocumentBuilderFactory factory) {
/*     */     try {
/* 110 */       factory.setXIncludeAware(true);
/* 111 */     } catch (UnsupportedOperationException e) {
/* 112 */       LOGGER.warn("The DocumentBuilderFactory does not support XInclude: " + factory, e);
/* 113 */     } catch (AbstractMethodError err) {
/* 114 */       LOGGER.warn("The DocumentBuilderFactory is out of date and does not support XInclude: " + factory);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 119 */       factory.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", true);
/* 120 */     } catch (ParserConfigurationException e) {
/* 121 */       LOGGER.warn("The DocumentBuilderFactory [" + factory + "] does not support the feature [" + "http://apache.org/xml/features/xinclude/fixup-base-uris" + "]", e);
/*     */     }
/* 123 */     catch (AbstractMethodError err) {
/* 124 */       LOGGER.warn("The DocumentBuilderFactory is out of date and does not support setFeature: " + factory);
/*     */     } 
/*     */     try {
/* 127 */       factory.setFeature("http://apache.org/xml/features/xinclude/fixup-language", true);
/* 128 */     } catch (ParserConfigurationException e) {
/* 129 */       LOGGER.warn("The DocumentBuilderFactory [" + factory + "] does not support the feature [" + "http://apache.org/xml/features/xinclude/fixup-language" + "]", e);
/*     */     }
/* 131 */     catch (AbstractMethodError err) {
/* 132 */       LOGGER.warn("The DocumentBuilderFactory is out of date and does not support setFeature: " + factory);
/*     */     } 
/*     */   }
/*     */   
/*     */   public XMLConfiguration(ConfigurationFactory.ConfigurationSource configSource) {
/* 137 */     this.configFile = configSource.getFile();
/* 138 */     byte[] buffer = null;
/*     */     
/*     */     try {
/* 141 */       List<String> messages = new ArrayList<String>();
/* 142 */       InputStream configStream = configSource.getInputStream();
/* 143 */       buffer = toByteArray(configStream);
/* 144 */       configStream.close();
/* 145 */       InputSource source = new InputSource(new ByteArrayInputStream(buffer));
/* 146 */       Document document = newDocumentBuilder().parse(source);
/* 147 */       this.rootElement = document.getDocumentElement();
/* 148 */       Map<String, String> attrs = processAttributes(this.rootNode, this.rootElement);
/* 149 */       Level status = getDefaultStatus();
/* 150 */       boolean verbose = false;
/* 151 */       PrintStream stream = System.out;
/*     */       
/* 153 */       for (Map.Entry<String, String> entry : attrs.entrySet()) {
/* 154 */         if ("status".equalsIgnoreCase(entry.getKey())) {
/* 155 */           Level stat = Level.toLevel(getStrSubstitutor().replace(entry.getValue()), null);
/* 156 */           if (stat != null) {
/* 157 */             status = stat; continue;
/*     */           } 
/* 159 */           messages.add("Invalid status specified: " + (String)entry.getValue() + ". Defaulting to " + status); continue;
/*     */         } 
/* 161 */         if ("dest".equalsIgnoreCase(entry.getKey())) {
/* 162 */           String dest = getStrSubstitutor().replace(entry.getValue());
/* 163 */           if (dest != null) {
/* 164 */             if (dest.equalsIgnoreCase("err")) {
/* 165 */               stream = System.err; continue;
/*     */             } 
/*     */             try {
/* 168 */               File destFile = FileUtils.fileFromURI(new URI(dest));
/* 169 */               String enc = Charset.defaultCharset().name();
/* 170 */               stream = new PrintStream(new FileOutputStream(destFile), true, enc);
/* 171 */             } catch (URISyntaxException use) {
/* 172 */               System.err.println("Unable to write to " + dest + ". Writing to stdout");
/*     */             } 
/*     */           }  continue;
/*     */         } 
/* 176 */         if ("shutdownHook".equalsIgnoreCase(entry.getKey())) {
/* 177 */           String hook = getStrSubstitutor().replace(entry.getValue());
/* 178 */           this.isShutdownHookEnabled = !hook.equalsIgnoreCase("disable"); continue;
/* 179 */         }  if ("verbose".equalsIgnoreCase(entry.getKey())) {
/* 180 */           verbose = Boolean.parseBoolean(getStrSubstitutor().replace(entry.getValue())); continue;
/* 181 */         }  if ("packages".equalsIgnoreCase(getStrSubstitutor().replace(entry.getKey()))) {
/* 182 */           String[] packages = ((String)entry.getValue()).split(",");
/* 183 */           for (String p : packages)
/* 184 */             PluginManager.addPackage(p);  continue;
/*     */         } 
/* 186 */         if ("name".equalsIgnoreCase(entry.getKey())) {
/* 187 */           setName(getStrSubstitutor().replace(entry.getValue())); continue;
/* 188 */         }  if ("strict".equalsIgnoreCase(entry.getKey())) {
/* 189 */           this.strict = Boolean.parseBoolean(getStrSubstitutor().replace(entry.getValue())); continue;
/* 190 */         }  if ("schema".equalsIgnoreCase(entry.getKey())) {
/* 191 */           this.schema = getStrSubstitutor().replace(entry.getValue()); continue;
/* 192 */         }  if ("monitorInterval".equalsIgnoreCase(entry.getKey())) {
/* 193 */           int interval = Integer.parseInt(getStrSubstitutor().replace(entry.getValue()));
/* 194 */           if (interval > 0 && this.configFile != null)
/* 195 */             this.monitor = new FileConfigurationMonitor(this, this.configFile, this.listeners, interval);  continue;
/*     */         } 
/* 197 */         if ("advertiser".equalsIgnoreCase(entry.getKey())) {
/* 198 */           createAdvertiser(getStrSubstitutor().replace(entry.getValue()), configSource, buffer, "text/xml");
/*     */         }
/*     */       } 
/* 201 */       Iterator<StatusListener> iter = ((StatusLogger)LOGGER).getListeners();
/* 202 */       boolean found = false;
/* 203 */       while (iter.hasNext()) {
/* 204 */         StatusListener listener = iter.next();
/* 205 */         if (listener instanceof StatusConsoleListener) {
/* 206 */           found = true;
/* 207 */           ((StatusConsoleListener)listener).setLevel(status);
/* 208 */           if (!verbose) {
/* 209 */             ((StatusConsoleListener)listener).setFilters(VERBOSE_CLASSES);
/*     */           }
/*     */         } 
/*     */       } 
/* 213 */       if (!found && status != Level.OFF) {
/* 214 */         StatusConsoleListener listener = new StatusConsoleListener(status, stream);
/* 215 */         if (!verbose) {
/* 216 */           listener.setFilters(VERBOSE_CLASSES);
/*     */         }
/* 218 */         ((StatusLogger)LOGGER).registerListener((StatusListener)listener);
/* 219 */         for (String msg : messages) {
/* 220 */           LOGGER.error(msg);
/*     */         }
/*     */       }
/*     */     
/* 224 */     } catch (SAXException domEx) {
/* 225 */       LOGGER.error("Error parsing " + configSource.getLocation(), domEx);
/* 226 */     } catch (IOException ioe) {
/* 227 */       LOGGER.error("Error parsing " + configSource.getLocation(), ioe);
/* 228 */     } catch (ParserConfigurationException pex) {
/* 229 */       LOGGER.error("Error parsing " + configSource.getLocation(), pex);
/*     */     } 
/* 231 */     if (this.strict && this.schema != null && buffer != null) {
/* 232 */       InputStream is = null;
/*     */       try {
/* 234 */         is = getClass().getClassLoader().getResourceAsStream(this.schema);
/* 235 */       } catch (Exception ex) {
/* 236 */         LOGGER.error("Unable to access schema " + this.schema);
/*     */       } 
/* 238 */       if (is != null) {
/* 239 */         Source src = new StreamSource(is, "Log4j-config.xsd");
/* 240 */         SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/* 241 */         Schema schema = null;
/*     */         try {
/* 243 */           schema = factory.newSchema(src);
/* 244 */         } catch (SAXException ex) {
/* 245 */           LOGGER.error("Error parsing Log4j schema", ex);
/*     */         } 
/* 247 */         if (schema != null) {
/* 248 */           Validator validator = schema.newValidator();
/*     */           try {
/* 250 */             validator.validate(new StreamSource(new ByteArrayInputStream(buffer)));
/* 251 */           } catch (IOException ioe) {
/* 252 */             LOGGER.error("Error reading configuration for validation", ioe);
/* 253 */           } catch (SAXException ex) {
/* 254 */             LOGGER.error("Error validating configuration", ex);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 260 */     if (getName() == null) {
/* 261 */       setName(configSource.getLocation());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setup() {
/* 267 */     if (this.rootElement == null) {
/* 268 */       LOGGER.error("No logging configuration");
/*     */       return;
/*     */     } 
/* 271 */     constructHierarchy(this.rootNode, this.rootElement);
/* 272 */     if (this.status.size() > 0) {
/* 273 */       for (Status s : this.status) {
/* 274 */         LOGGER.error("Error processing element " + s.name + ": " + s.errorType);
/*     */       }
/*     */       return;
/*     */     } 
/* 278 */     this.rootElement = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Configuration reconfigure() {
/* 283 */     if (this.configFile != null) {
/*     */       try {
/* 285 */         ConfigurationFactory.ConfigurationSource source = new ConfigurationFactory.ConfigurationSource(new FileInputStream(this.configFile), this.configFile);
/*     */         
/* 287 */         return new XMLConfiguration(source);
/* 288 */       } catch (FileNotFoundException ex) {
/* 289 */         LOGGER.error("Cannot locate file " + this.configFile, ex);
/*     */       } 
/*     */     }
/* 292 */     return null;
/*     */   }
/*     */   
/*     */   private void constructHierarchy(Node node, Element element) {
/* 296 */     processAttributes(node, element);
/* 297 */     StringBuilder buffer = new StringBuilder();
/* 298 */     NodeList list = element.getChildNodes();
/* 299 */     List<Node> children = node.getChildren();
/* 300 */     for (int i = 0; i < list.getLength(); i++) {
/* 301 */       Node w3cNode = list.item(i);
/* 302 */       if (w3cNode instanceof Element) {
/* 303 */         Element child = (Element)w3cNode;
/* 304 */         String name = getType(child);
/* 305 */         PluginType<?> type = this.pluginManager.getPluginType(name);
/* 306 */         Node childNode = new Node(node, name, type);
/* 307 */         constructHierarchy(childNode, child);
/* 308 */         if (type == null) {
/* 309 */           String value = childNode.getValue();
/* 310 */           if (!childNode.hasChildren() && value != null) {
/* 311 */             node.getAttributes().put(name, value);
/*     */           } else {
/* 313 */             this.status.add(new Status(name, element, ErrorType.CLASS_NOT_FOUND));
/*     */           } 
/*     */         } else {
/* 316 */           children.add(childNode);
/*     */         } 
/* 318 */       } else if (w3cNode instanceof Text) {
/* 319 */         Text data = (Text)w3cNode;
/* 320 */         buffer.append(data.getData());
/*     */       } 
/*     */     } 
/*     */     
/* 324 */     String text = buffer.toString().trim();
/* 325 */     if (text.length() > 0 || (!node.hasChildren() && !node.isRoot())) {
/* 326 */       node.setValue(text);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getType(Element element) {
/* 331 */     if (this.strict) {
/* 332 */       NamedNodeMap attrs = element.getAttributes();
/* 333 */       for (int i = 0; i < attrs.getLength(); i++) {
/* 334 */         Node w3cNode = attrs.item(i);
/* 335 */         if (w3cNode instanceof Attr) {
/* 336 */           Attr attr = (Attr)w3cNode;
/* 337 */           if (attr.getName().equalsIgnoreCase("type")) {
/* 338 */             String type = attr.getValue();
/* 339 */             attrs.removeNamedItem(attr.getName());
/* 340 */             return type;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 345 */     return element.getTagName();
/*     */   }
/*     */   
/*     */   private byte[] toByteArray(InputStream is) throws IOException {
/* 349 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */ 
/*     */     
/* 352 */     byte[] data = new byte[16384];
/*     */     int nRead;
/* 354 */     while ((nRead = is.read(data, 0, data.length)) != -1) {
/* 355 */       buffer.write(data, 0, nRead);
/*     */     }
/*     */     
/* 358 */     return buffer.toByteArray();
/*     */   }
/*     */   
/*     */   private Map<String, String> processAttributes(Node node, Element element) {
/* 362 */     NamedNodeMap attrs = element.getAttributes();
/* 363 */     Map<String, String> attributes = node.getAttributes();
/*     */     
/* 365 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 366 */       Node w3cNode = attrs.item(i);
/* 367 */       if (w3cNode instanceof Attr) {
/* 368 */         Attr attr = (Attr)w3cNode;
/* 369 */         if (!attr.getName().equals("xml:base"))
/*     */         {
/*     */           
/* 372 */           attributes.put(attr.getName(), attr.getValue()); } 
/*     */       } 
/*     */     } 
/* 375 */     return attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum ErrorType
/*     */   {
/* 382 */     CLASS_NOT_FOUND;
/*     */   }
/*     */ 
/*     */   
/*     */   private class Status
/*     */   {
/*     */     private final Element element;
/*     */     
/*     */     private final String name;
/*     */     private final XMLConfiguration.ErrorType errorType;
/*     */     
/*     */     public Status(String name, Element element, XMLConfiguration.ErrorType errorType) {
/* 394 */       this.name = name;
/* 395 */       this.element = element;
/* 396 */       this.errorType = errorType;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\XMLConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */