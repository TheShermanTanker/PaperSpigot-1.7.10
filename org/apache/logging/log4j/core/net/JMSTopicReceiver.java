/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Topic;
/*     */ import javax.jms.TopicConnection;
/*     */ import javax.jms.TopicConnectionFactory;
/*     */ import javax.jms.TopicSession;
/*     */ import javax.jms.TopicSubscriber;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMSTopicReceiver
/*     */   extends AbstractJMSReceiver
/*     */ {
/*     */   public JMSTopicReceiver(String tcfBindingName, String topicBindingName, String username, String password) {
/*     */     try {
/*  49 */       Context ctx = new InitialContext();
/*     */       
/*  51 */       TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)lookup(ctx, tcfBindingName);
/*  52 */       TopicConnection topicConnection = topicConnectionFactory.createTopicConnection(username, password);
/*  53 */       topicConnection.start();
/*  54 */       TopicSession topicSession = topicConnection.createTopicSession(false, 1);
/*  55 */       Topic topic = (Topic)ctx.lookup(topicBindingName);
/*  56 */       TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
/*  57 */       topicSubscriber.setMessageListener(this);
/*  58 */     } catch (JMSException e) {
/*  59 */       this.logger.error("Could not read JMS message.", (Throwable)e);
/*  60 */     } catch (NamingException e) {
/*  61 */       this.logger.error("Could not read JMS message.", e);
/*  62 */     } catch (RuntimeException e) {
/*  63 */       this.logger.error("Could not read JMS message.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*     */     String line;
/*  73 */     if (args.length != 4) {
/*  74 */       usage("Wrong number of arguments.");
/*     */     }
/*     */     
/*  77 */     String tcfBindingName = args[0];
/*  78 */     String topicBindingName = args[1];
/*  79 */     String username = args[2];
/*  80 */     String password = args[3];
/*     */     
/*  82 */     new JMSTopicReceiver(tcfBindingName, topicBindingName, username, password);
/*     */     
/*  84 */     Charset enc = Charset.defaultCharset();
/*  85 */     BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, enc));
/*     */     
/*  87 */     System.out.println("Type \"exit\" to quit JMSTopicReceiver.");
/*     */     do {
/*  89 */       line = stdin.readLine();
/*  90 */     } while (line != null && !line.equalsIgnoreCase("exit"));
/*  91 */     System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void usage(String msg) {
/*  99 */     System.err.println(msg);
/* 100 */     System.err.println("Usage: java " + JMSTopicReceiver.class.getName() + " TopicConnectionFactoryBindingName TopicBindingName username password");
/*     */     
/* 102 */     System.exit(1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSTopicReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */