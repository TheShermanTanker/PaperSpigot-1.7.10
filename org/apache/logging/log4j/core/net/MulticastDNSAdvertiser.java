/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
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
/*     */ @Plugin(name = "multicastdns", category = "Core", elementType = "advertiser", printObject = false)
/*     */ public class MulticastDNSAdvertiser
/*     */   implements Advertiser
/*     */ {
/*  40 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*  41 */   private static Object jmDNS = initializeJMDNS();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> jmDNSClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> serviceInfoClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object advertise(Map<String, String> properties) {
/*  66 */     Map<String, String> truncatedProperties = new HashMap<String, String>();
/*  67 */     for (Map.Entry<String, String> entry : properties.entrySet()) {
/*     */       
/*  69 */       if (((String)entry.getKey()).length() <= 255 && ((String)entry.getValue()).length() <= 255)
/*     */       {
/*  71 */         truncatedProperties.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*  74 */     String protocol = truncatedProperties.get("protocol");
/*  75 */     String zone = "._log4j._" + ((protocol != null) ? protocol : "tcp") + ".local.";
/*     */     
/*  77 */     String portString = truncatedProperties.get("port");
/*  78 */     int port = Integers.parseInt(portString, 4555);
/*     */     
/*  80 */     String name = truncatedProperties.get("name");
/*     */ 
/*     */     
/*  83 */     if (jmDNS != null) {
/*     */       Object serviceInfo;
/*  85 */       boolean isVersion3 = false;
/*     */       
/*     */       try {
/*  88 */         jmDNSClass.getMethod("create", (Class[])null);
/*  89 */         isVersion3 = true;
/*  90 */       } catch (NoSuchMethodException e) {}
/*     */ 
/*     */ 
/*     */       
/*  94 */       if (isVersion3) {
/*  95 */         serviceInfo = buildServiceInfoVersion3(zone, port, name, truncatedProperties);
/*     */       } else {
/*  97 */         serviceInfo = buildServiceInfoVersion1(zone, port, name, truncatedProperties);
/*     */       } 
/*     */       
/*     */       try {
/* 101 */         Method method = jmDNSClass.getMethod("registerService", new Class[] { serviceInfoClass });
/* 102 */         method.invoke(jmDNS, new Object[] { serviceInfo });
/* 103 */       } catch (IllegalAccessException e) {
/* 104 */         LOGGER.warn("Unable to invoke registerService method", e);
/* 105 */       } catch (NoSuchMethodException e) {
/* 106 */         LOGGER.warn("No registerService method", e);
/* 107 */       } catch (InvocationTargetException e) {
/* 108 */         LOGGER.warn("Unable to invoke registerService method", e);
/*     */       } 
/* 110 */       return serviceInfo;
/*     */     } 
/*     */ 
/*     */     
/* 114 */     LOGGER.warn("JMDNS not available - will not advertise ZeroConf support");
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unadvertise(Object serviceInfo) {
/* 125 */     if (jmDNS != null) {
/*     */       try {
/* 127 */         Method method = jmDNSClass.getMethod("unregisterService", new Class[] { serviceInfoClass });
/* 128 */         method.invoke(jmDNS, new Object[] { serviceInfo });
/* 129 */       } catch (IllegalAccessException e) {
/* 130 */         LOGGER.warn("Unable to invoke unregisterService method", e);
/* 131 */       } catch (NoSuchMethodException e) {
/* 132 */         LOGGER.warn("No unregisterService method", e);
/* 133 */       } catch (InvocationTargetException e) {
/* 134 */         LOGGER.warn("Unable to invoke unregisterService method", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object createJmDNSVersion1() {
/*     */     try {
/* 142 */       return jmDNSClass.newInstance();
/* 143 */     } catch (InstantiationException e) {
/* 144 */       LOGGER.warn("Unable to instantiate JMDNS", e);
/* 145 */     } catch (IllegalAccessException e) {
/* 146 */       LOGGER.warn("Unable to instantiate JMDNS", e);
/*     */     } 
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object createJmDNSVersion3() {
/*     */     try {
/* 154 */       Method jmDNSCreateMethod = jmDNSClass.getMethod("create", (Class[])null);
/* 155 */       return jmDNSCreateMethod.invoke(null, (Object[])null);
/* 156 */     } catch (IllegalAccessException e) {
/* 157 */       LOGGER.warn("Unable to instantiate jmdns class", e);
/* 158 */     } catch (NoSuchMethodException e) {
/* 159 */       LOGGER.warn("Unable to access constructor", e);
/* 160 */     } catch (InvocationTargetException e) {
/* 161 */       LOGGER.warn("Unable to call constructor", e);
/*     */     } 
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object buildServiceInfoVersion1(String zone, int port, String name, Map<String, String> properties) {
/* 168 */     Hashtable<String, String> hashtableProperties = new Hashtable<String, String>(properties);
/*     */     try {
/* 170 */       Class<?>[] args = new Class[6];
/* 171 */       args[0] = String.class;
/* 172 */       args[1] = String.class;
/* 173 */       args[2] = int.class;
/* 174 */       args[3] = int.class;
/* 175 */       args[4] = int.class;
/* 176 */       args[5] = Hashtable.class;
/* 177 */       Constructor<?> constructor = serviceInfoClass.getConstructor(args);
/* 178 */       Object[] values = new Object[6];
/* 179 */       values[0] = zone;
/* 180 */       values[1] = name;
/* 181 */       values[2] = Integer.valueOf(port);
/* 182 */       values[3] = Integer.valueOf(0);
/* 183 */       values[4] = Integer.valueOf(0);
/* 184 */       values[5] = hashtableProperties;
/* 185 */       return constructor.newInstance(values);
/* 186 */     } catch (IllegalAccessException e) {
/* 187 */       LOGGER.warn("Unable to construct ServiceInfo instance", e);
/* 188 */     } catch (NoSuchMethodException e) {
/* 189 */       LOGGER.warn("Unable to get ServiceInfo constructor", e);
/* 190 */     } catch (InstantiationException e) {
/* 191 */       LOGGER.warn("Unable to construct ServiceInfo instance", e);
/* 192 */     } catch (InvocationTargetException e) {
/* 193 */       LOGGER.warn("Unable to construct ServiceInfo instance", e);
/*     */     } 
/* 195 */     return null;
/*     */   }
/*     */   
/*     */   private Object buildServiceInfoVersion3(String zone, int port, String name, Map<String, String> properties) {
/*     */     try {
/* 200 */       Class<?>[] args = new Class[6];
/* 201 */       args[0] = String.class;
/* 202 */       args[1] = String.class;
/* 203 */       args[2] = int.class;
/* 204 */       args[3] = int.class;
/* 205 */       args[4] = int.class;
/* 206 */       args[5] = Map.class;
/* 207 */       Method serviceInfoCreateMethod = serviceInfoClass.getMethod("create", args);
/* 208 */       Object[] values = new Object[6];
/* 209 */       values[0] = zone;
/* 210 */       values[1] = name;
/* 211 */       values[2] = Integer.valueOf(port);
/* 212 */       values[3] = Integer.valueOf(0);
/* 213 */       values[4] = Integer.valueOf(0);
/* 214 */       values[5] = properties;
/* 215 */       return serviceInfoCreateMethod.invoke(null, values);
/* 216 */     } catch (IllegalAccessException e) {
/* 217 */       LOGGER.warn("Unable to invoke create method", e);
/* 218 */     } catch (NoSuchMethodException e) {
/* 219 */       LOGGER.warn("Unable to find create method", e);
/* 220 */     } catch (InvocationTargetException e) {
/* 221 */       LOGGER.warn("Unable to invoke create method", e);
/*     */     } 
/* 223 */     return null;
/*     */   }
/*     */   
/*     */   private static Object initializeJMDNS() {
/*     */     try {
/* 228 */       jmDNSClass = Class.forName("javax.jmdns.JmDNS");
/* 229 */       serviceInfoClass = Class.forName("javax.jmdns.ServiceInfo");
/*     */       
/* 231 */       boolean isVersion3 = false;
/*     */       
/*     */       try {
/* 234 */         jmDNSClass.getMethod("create", (Class[])null);
/* 235 */         isVersion3 = true;
/* 236 */       } catch (NoSuchMethodException e) {}
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (isVersion3) {
/* 241 */         return createJmDNSVersion3();
/*     */       }
/* 243 */       return createJmDNSVersion1();
/*     */     }
/* 245 */     catch (ClassNotFoundException e) {
/* 246 */       LOGGER.warn("JmDNS or serviceInfo class not found", e);
/* 247 */     } catch (ExceptionInInitializerError e2) {
/* 248 */       LOGGER.warn("JmDNS or serviceInfo class not found", e2);
/*     */     } 
/* 250 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\MulticastDNSAdvertiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */