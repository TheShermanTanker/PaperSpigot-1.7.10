/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.MessageProducer;
/*     */ import javax.jms.Session;
/*     */ import javax.jms.Topic;
/*     */ import javax.jms.TopicConnection;
/*     */ import javax.jms.TopicConnectionFactory;
/*     */ import javax.jms.TopicPublisher;
/*     */ import javax.jms.TopicSession;
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
/*     */ public class JMSTopicManager
/*     */   extends AbstractJMSManager
/*     */ {
/*  37 */   private static final JMSTopicManagerFactory FACTORY = new JMSTopicManagerFactory();
/*     */ 
/*     */   
/*     */   private TopicInfo info;
/*     */ 
/*     */   
/*     */   private final String factoryBindingName;
/*     */ 
/*     */   
/*     */   private final String topicBindingName;
/*     */ 
/*     */   
/*     */   private final String userName;
/*     */ 
/*     */   
/*     */   private final String password;
/*     */   
/*     */   private final Context context;
/*     */ 
/*     */   
/*     */   protected JMSTopicManager(String name, Context context, String factoryBindingName, String topicBindingName, String userName, String password, TopicInfo info) {
/*  58 */     super(name);
/*  59 */     this.context = context;
/*  60 */     this.factoryBindingName = factoryBindingName;
/*  61 */     this.topicBindingName = topicBindingName;
/*  62 */     this.userName = userName;
/*  63 */     this.password = password;
/*  64 */     this.info = info;
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
/*     */   public static JMSTopicManager getJMSTopicManager(String factoryName, String providerURL, String urlPkgPrefixes, String securityPrincipalName, String securityCredentials, String factoryBindingName, String topicBindingName, String userName, String password) {
/*  87 */     if (factoryBindingName == null) {
/*  88 */       LOGGER.error("No factory name provided for JMSTopicManager");
/*  89 */       return null;
/*     */     } 
/*  91 */     if (topicBindingName == null) {
/*  92 */       LOGGER.error("No topic name provided for JMSTopicManager");
/*  93 */       return null;
/*     */     } 
/*     */     
/*  96 */     String name = "JMSTopic:" + factoryBindingName + '.' + topicBindingName;
/*  97 */     return (JMSTopicManager)getManager(name, FACTORY, new FactoryData(factoryName, providerURL, urlPkgPrefixes, securityPrincipalName, securityCredentials, factoryBindingName, topicBindingName, userName, password));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(Serializable object) throws Exception {
/* 104 */     if (this.info == null) {
/* 105 */       this.info = connect(this.context, this.factoryBindingName, this.topicBindingName, this.userName, this.password, false);
/*     */     }
/*     */     try {
/* 108 */       send(object, (Session)this.info.session, (MessageProducer)this.info.publisher);
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
/*     */     private final String topicBindingName;
/*     */     private final String userName;
/*     */     private final String password;
/*     */     
/*     */     public FactoryData(String factoryName, String providerURL, String urlPkgPrefixes, String securityPrincipalName, String securityCredentials, String factoryBindingName, String topicBindingName, String userName, String password) {
/* 158 */       this.factoryName = factoryName;
/* 159 */       this.providerURL = providerURL;
/* 160 */       this.urlPkgPrefixes = urlPkgPrefixes;
/* 161 */       this.securityPrincipalName = securityPrincipalName;
/* 162 */       this.securityCredentials = securityCredentials;
/* 163 */       this.factoryBindingName = factoryBindingName;
/* 164 */       this.topicBindingName = topicBindingName;
/* 165 */       this.userName = userName;
/* 166 */       this.password = password;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static TopicInfo connect(Context context, String factoryBindingName, String queueBindingName, String userName, String password, boolean suppress) throws Exception {
/*     */     try {
/*     */       TopicConnection conn;
/* 174 */       TopicConnectionFactory factory = (TopicConnectionFactory)lookup(context, factoryBindingName);
/*     */       
/* 176 */       if (userName != null) {
/* 177 */         conn = factory.createTopicConnection(userName, password);
/*     */       } else {
/* 179 */         conn = factory.createTopicConnection();
/*     */       } 
/* 181 */       TopicSession sess = conn.createTopicSession(false, 1);
/* 182 */       Topic topic = (Topic)lookup(context, queueBindingName);
/* 183 */       TopicPublisher publisher = sess.createPublisher(topic);
/* 184 */       conn.start();
/* 185 */       return new TopicInfo(conn, sess, publisher);
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
/*     */   private static class TopicInfo
/*     */   {
/*     */     private final TopicConnection conn;
/*     */     private final TopicSession session;
/*     */     private final TopicPublisher publisher;
/*     */     
/*     */     public TopicInfo(TopicConnection conn, TopicSession session, TopicPublisher publisher) {
/* 207 */       this.conn = conn;
/* 208 */       this.session = session;
/* 209 */       this.publisher = publisher;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class JMSTopicManagerFactory
/*     */     implements ManagerFactory<JMSTopicManager, FactoryData>
/*     */   {
/*     */     private JMSTopicManagerFactory() {}
/*     */     
/*     */     public JMSTopicManager createManager(String name, JMSTopicManager.FactoryData data) {
/*     */       try {
/* 221 */         Context ctx = AbstractJMSManager.createContext(data.factoryName, data.providerURL, data.urlPkgPrefixes, data.securityPrincipalName, data.securityCredentials);
/*     */         
/* 223 */         JMSTopicManager.TopicInfo info = JMSTopicManager.connect(ctx, data.factoryBindingName, data.topicBindingName, data.userName, data.password, true);
/*     */         
/* 225 */         return new JMSTopicManager(name, ctx, data.factoryBindingName, data.topicBindingName, data.userName, data.password, info);
/*     */       }
/* 227 */       catch (NamingException ex) {
/* 228 */         JMSTopicManager.LOGGER.error("Unable to locate resource", ex);
/* 229 */       } catch (Exception ex) {
/* 230 */         JMSTopicManager.LOGGER.error("Unable to connect", ex);
/*     */       } 
/*     */       
/* 233 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSTopicManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */