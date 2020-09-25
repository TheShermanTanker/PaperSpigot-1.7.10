/*     */ package org.apache.logging.log4j.core.appender.routing;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.Node;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "Routing", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class RoutingAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final String DEFAULT_KEY = "ROUTING_APPENDER_DEFAULT";
/*     */   private final Routes routes;
/*     */   private final Route defaultRoute;
/*     */   private final Configuration config;
/*  52 */   private final ConcurrentMap<String, AppenderControl> appenders = new ConcurrentHashMap<String, AppenderControl>();
/*     */   
/*     */   private final RewritePolicy rewritePolicy;
/*     */ 
/*     */   
/*     */   private RoutingAppender(String name, Filter filter, boolean ignoreExceptions, Routes routes, RewritePolicy rewritePolicy, Configuration config) {
/*  58 */     super(name, filter, null, ignoreExceptions);
/*  59 */     this.routes = routes;
/*  60 */     this.config = config;
/*  61 */     this.rewritePolicy = rewritePolicy;
/*  62 */     Route defRoute = null;
/*  63 */     for (Route route : routes.getRoutes()) {
/*  64 */       if (route.getKey() == null) {
/*  65 */         if (defRoute == null) {
/*  66 */           defRoute = route;
/*     */         } else {
/*  68 */           error("Multiple default routes. Route " + route.toString() + " will be ignored");
/*     */         } 
/*     */       }
/*     */     } 
/*  72 */     this.defaultRoute = defRoute;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  77 */     Map<String, Appender> map = this.config.getAppenders();
/*     */     
/*  79 */     for (Route route : this.routes.getRoutes()) {
/*  80 */       if (route.getAppenderRef() != null) {
/*  81 */         Appender appender = map.get(route.getAppenderRef());
/*  82 */         if (appender != null) {
/*  83 */           String key = (route == this.defaultRoute) ? "ROUTING_APPENDER_DEFAULT" : route.getKey();
/*  84 */           this.appenders.put(key, new AppenderControl(appender, null, null));
/*     */         } else {
/*  86 */           LOGGER.error("Appender " + route.getAppenderRef() + " cannot be located. Route ignored");
/*     */         } 
/*     */       } 
/*     */     } 
/*  90 */     super.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  95 */     super.stop();
/*  96 */     Map<String, Appender> map = this.config.getAppenders();
/*  97 */     for (Map.Entry<String, AppenderControl> entry : this.appenders.entrySet()) {
/*  98 */       String name = ((AppenderControl)entry.getValue()).getAppender().getName();
/*  99 */       if (!map.containsKey(name)) {
/* 100 */         ((AppenderControl)entry.getValue()).getAppender().stop();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/* 107 */     if (this.rewritePolicy != null) {
/* 108 */       event = this.rewritePolicy.rewrite(event);
/*     */     }
/* 110 */     String key = this.config.getStrSubstitutor().replace(event, this.routes.getPattern());
/* 111 */     AppenderControl control = getControl(key, event);
/* 112 */     if (control != null) {
/* 113 */       control.callAppender(event);
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized AppenderControl getControl(String key, LogEvent event) {
/* 118 */     AppenderControl control = this.appenders.get(key);
/* 119 */     if (control != null) {
/* 120 */       return control;
/*     */     }
/* 122 */     Route route = null;
/* 123 */     for (Route r : this.routes.getRoutes()) {
/* 124 */       if (r.getAppenderRef() == null && key.equals(r.getKey())) {
/* 125 */         route = r;
/*     */         break;
/*     */       } 
/*     */     } 
/* 129 */     if (route == null) {
/* 130 */       route = this.defaultRoute;
/* 131 */       control = this.appenders.get("ROUTING_APPENDER_DEFAULT");
/* 132 */       if (control != null) {
/* 133 */         return control;
/*     */       }
/*     */     } 
/* 136 */     if (route != null) {
/* 137 */       Appender app = createAppender(route, event);
/* 138 */       if (app == null) {
/* 139 */         return null;
/*     */       }
/* 141 */       control = new AppenderControl(app, null, null);
/* 142 */       this.appenders.put(key, control);
/*     */     } 
/*     */     
/* 145 */     return control;
/*     */   }
/*     */   
/*     */   private Appender createAppender(Route route, LogEvent event) {
/* 149 */     Node routeNode = route.getNode();
/* 150 */     for (Node node : routeNode.getChildren()) {
/* 151 */       if (node.getType().getElementName().equals("appender")) {
/* 152 */         Node appNode = new Node(node);
/* 153 */         this.config.createConfiguration(appNode, event);
/* 154 */         if (appNode.getObject() instanceof Appender) {
/* 155 */           Appender app = (Appender)appNode.getObject();
/* 156 */           app.start();
/* 157 */           return app;
/*     */         } 
/* 159 */         LOGGER.error("Unable to create Appender of type " + node.getName());
/* 160 */         return null;
/*     */       } 
/*     */     } 
/* 163 */     LOGGER.error("No Appender was configured for route " + route.getKey());
/* 164 */     return null;
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
/*     */   @PluginFactory
/*     */   public static RoutingAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Routes") Routes routes, @PluginConfiguration Configuration config, @PluginElement("RewritePolicy") RewritePolicy rewritePolicy, @PluginElement("Filters") Filter filter) {
/* 187 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 188 */     if (name == null) {
/* 189 */       LOGGER.error("No name provided for RoutingAppender");
/* 190 */       return null;
/*     */     } 
/* 192 */     if (routes == null) {
/* 193 */       LOGGER.error("No routes defined for RoutingAppender");
/* 194 */       return null;
/*     */     } 
/* 196 */     return new RoutingAppender(name, filter, ignoreExceptions, routes, rewritePolicy, config);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\routing\RoutingAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */