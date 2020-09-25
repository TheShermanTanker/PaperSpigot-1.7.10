/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.MessageProducer;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueSender;
/*     */ import javax.jms.QueueSession;
/*     */ import javax.jms.Session;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMSQueueManager
/*     */   extends AbstractJMSManager
/*     */ {
/*  37 */   private static final JMSQueueManagerFactory FACTORY = new JMSQueueManagerFactory();
/*     */ 
/*     */   
/*     */   private QueueInfo info;
/*     */ 
/*     */   
/*     */   private final String factoryBindingName;
/*     */ 
/*     */   
/*     */   private final String queueBindingName;
/*     */ 
/*     */   
/*     */   private final String userName;
/*     */ 
/*     */   
/*     */   private final String password;
/*     */ 
/*     */   
/*     */   private final Context context;
/*     */ 
/*     */   
/*     */   protected JMSQueueManager(String name, Context context, String factoryBindingName, String queueBindingName, String userName, String password, QueueInfo info) {
/*  59 */     super(name);
/*  60 */     this.context = context;
/*  61 */     this.factoryBindingName = factoryBindingName;
/*  62 */     this.queueBindingName = queueBindingName;
/*  63 */     this.userName = userName;
/*  64 */     this.password = password;
/*  65 */     this.info = info;
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
/*     */   public static JMSQueueManager getJMSQueueManager(String factoryName, String providerURL, String urlPkgPrefixes, String securityPrincipalName, String securityCredentials, String factoryBindingName, String queueBindingName, String userName, String password) {
/*  88 */     if (factoryBindingName == null) {
/*  89 */       LOGGER.error("No factory name provided for JMSQueueManager");
/*  90 */       return null;
/*     */     } 
/*  92 */     if (queueBindingName == null) {
/*  93 */       LOGGER.error("No topic name provided for JMSQueueManager");
/*  94 */       return null;
/*     */     } 
/*     */     
/*  97 */     String name = "JMSQueue:" + factoryBindingName + '.' + queueBindingName;
/*  98 */     return (JMSQueueManager)getManager(name, FACTORY, new FactoryData(factoryName, providerURL, urlPkgPrefixes, securityPrincipalName, securityCredentials, factoryBindingName, queueBindingName, userName, password));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void send(Serializable object) throws Exception {
/* 104 */     if (this.info == null) {
/* 105 */       this.info = connect(this.context, this.factoryBindingName, this.queueBindingName, this.userName, this.password, false);
/*     */     }
/*     */     try {
/* 108 */       send(object, (Session)this.info.session, (MessageProducer)this.info.sender);
/* 109 */     } catch (Exception ex) {
/* 110 */       cleanup(true);
/* 111 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseSub() {
/* 117 */     if (this.info != null) {
/* 118 */       cleanup(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void cleanup(boolean quiet) {
/*     */     try {
/* 124 */       this.info.session.close();
/* 125 */     } catch (Exception e) {
/* 126 */       if (!quiet) {
/* 127 */         LOGGER.error("Error closing session for " + getName(), e);
/*     */       }
/*     */     } 
/*     */     try {
/* 131 */       this.info.conn.close();
/* 132 */     } catch (Exception e) {
/* 133 */       if (!quiet) {
/* 134 */         LOGGER.error("Error closing connection for " + getName(), e);
/*     */       }
/*     */     } 
/* 137 */     this.info = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String factoryName;
/*     */     
/*     */     private final String providerURL;
/*     */     
/*     */     private final String urlPkgPrefixes;
/*     */     
/*     */     private final String securityPrincipalName;
/*     */     
/*     */     private final String securityCredentials;
/*     */     private final String factoryBindingName;
/*     */     private final String queueBindingName;
/*     */     private final String userName;
/*     */     private final String password;
/*     */     
/*     */     public FactoryData(String factoryName, String providerURL, String urlPkgPrefixes, String securityPrincipalName, String securityCredentials, String factoryBindingName, String queueBindingName, String userName, String password) {
/* 158 */       this.factoryName = factoryName;
/* 159 */       this.providerURL = providerURL;
/* 160 */       this.urlPkgPrefixes = urlPkgPrefixes;
/* 161 */       this.securityPrincipalName = securityPrincipalName;
/* 162 */       this.securityCredentials = securityCredentials;
/* 163 */       this.factoryBindingName = factoryBindingName;
/* 164 */       this.queueBindingName = queueBindingName;
/* 165 */       this.userName = userName;
/* 166 */       this.password = password;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static QueueInfo connect(Context context, String factoryBindingName, String queueBindingName, String userName, String password, boolean suppress) throws Exception {
/*     */     try {
/*     */       QueueConnection conn;
/* 174 */       QueueConnectionFactory factory = (QueueConnectionFactory)lookup(context, factoryBindingName);
/*     */       
/* 176 */       if (userName != null) {
/* 177 */         conn = factory.createQueueConnection(userName, password);
/*     */       } else {
/* 179 */         conn = factory.createQueueConnection();
/*     */       } 
/* 181 */       QueueSession sess = conn.createQueueSession(false, 1);
/* 182 */       Queue queue = (Queue)lookup(context, queueBindingName);
/* 183 */       QueueSender sender = sess.createSender(queue);
/* 184 */       conn.start();
/* 185 */       return new QueueInfo(conn, sess, sender);
/* 186 */     } catch (NamingException ex) {
/* 187 */       LOGGER.warn("Unable to locate connection factory " + factoryBindingName, ex);
/* 188 */       if (!suppress) {
/* 189 */         throw ex;
/*     */       }
/* 191 */     } catch (JMSException ex) {
/* 192 */       LOGGER.warn("Unable to create connection to queue " + queueBindingName, (Throwable)ex);
/* 193 */       if (!suppress) {
/* 194 */         throw ex;
/*     */       }
/*     */     } 
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   private static class QueueInfo
/*     */   {
/*     */     private final QueueConnection conn;
/*     */     private final QueueSession session;
/*     */     private final QueueSender sender;
/*     */     
/*     */     public QueueInfo(QueueConnection conn, QueueSession session, QueueSender sender) {
/* 207 */       this.conn = conn;
/* 208 */       this.session = session;
/* 209 */       this.sender = sender;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class JMSQueueManagerFactory
/*     */     implements ManagerFactory<JMSQueueManager, FactoryData>
/*     */   {
/*     */     private JMSQueueManagerFactory() {}
/*     */     
/*     */     public JMSQueueManager createManager(String name, JMSQueueManager.FactoryData data) {
/*     */       try {
/* 221 */         Context ctx = AbstractJMSManager.createContext(data.factoryName, data.providerURL, data.urlPkgPrefixes, data.securityPrincipalName, data.securityCredentials);
/*     */         
/* 223 */         JMSQueueManager.QueueInfo info = JMSQueueManager.connect(ctx, data.factoryBindingName, data.queueBindingName, data.userName, data.password, true);
/*     */         
/* 225 */         return new JMSQueueManager(name, ctx, data.factoryBindingName, data.queueBindingName, data.userName, data.password, info);
/*     */       }
/* 227 */       catch (NamingException ex) {
/* 228 */         JMSQueueManager.LOGGER.error("Unable to locate resource", ex);
/* 229 */       } catch (Exception ex) {
/* 230 */         JMSQueueManager.LOGGER.error("Unable to connect", ex);
/*     */       } 
/*     */       
/* 233 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSQueueManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */