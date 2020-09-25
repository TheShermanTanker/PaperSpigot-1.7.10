/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Properties;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Message;
/*     */ import javax.jms.MessageProducer;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.jms.Session;
/*     */ import javax.jms.TextMessage;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.core.appender.AbstractManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractJMSManager
/*     */   extends AbstractManager
/*     */ {
/*     */   public AbstractJMSManager(String name) {
/*  44 */     super(name);
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
/*     */   protected static Context createContext(String factoryName, String providerURL, String urlPkgPrefixes, String securityPrincipalName, String securityCredentials) throws NamingException {
/*  63 */     Properties props = getEnvironment(factoryName, providerURL, urlPkgPrefixes, securityPrincipalName, securityCredentials);
/*     */     
/*  65 */     return new InitialContext(props);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Object lookup(Context ctx, String name) throws NamingException {
/*     */     try {
/*  77 */       return ctx.lookup(name);
/*  78 */     } catch (NameNotFoundException e) {
/*  79 */       LOGGER.warn("Could not find name [" + name + "].");
/*  80 */       throw e;
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
/*     */ 
/*     */   
/*     */   protected static Properties getEnvironment(String factoryName, String providerURL, String urlPkgPrefixes, String securityPrincipalName, String securityCredentials) {
/*  97 */     Properties props = new Properties();
/*  98 */     if (factoryName != null) {
/*  99 */       props.put("java.naming.factory.initial", factoryName);
/* 100 */       if (providerURL != null) {
/* 101 */         props.put("java.naming.provider.url", providerURL);
/*     */       } else {
/* 103 */         LOGGER.warn("The InitialContext factory name has been provided without a ProviderURL. This is likely to cause problems");
/*     */       } 
/*     */       
/* 106 */       if (urlPkgPrefixes != null) {
/* 107 */         props.put("java.naming.factory.url.pkgs", urlPkgPrefixes);
/*     */       }
/* 109 */       if (securityPrincipalName != null) {
/* 110 */         props.put("java.naming.security.principal", securityPrincipalName);
/* 111 */         if (securityCredentials != null) {
/* 112 */           props.put("java.naming.security.credentials", securityCredentials);
/*     */         } else {
/* 114 */           LOGGER.warn("SecurityPrincipalName has been set without SecurityCredentials. This is likely to cause problems.");
/*     */         } 
/*     */       } 
/*     */       
/* 118 */       return props;
/*     */     } 
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void send(Serializable paramSerializable) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void send(Serializable object, Session session, MessageProducer producer) throws Exception {
/*     */     try {
/*     */       ObjectMessage objectMessage;
/* 141 */       if (object instanceof String) {
/* 142 */         TextMessage textMessage = session.createTextMessage();
/* 143 */         textMessage.setText((String)object);
/*     */       } else {
/* 145 */         objectMessage = session.createObjectMessage();
/* 146 */         objectMessage.setObject(object);
/*     */       } 
/* 148 */       producer.send((Message)objectMessage);
/* 149 */     } catch (JMSException ex) {
/* 150 */       LOGGER.error("Could not publish message via JMS " + getName());
/* 151 */       throw ex;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\AbstractJMSManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */