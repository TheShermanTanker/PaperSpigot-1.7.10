/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueReceiver;
/*     */ import javax.jms.QueueSession;
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
/*     */ 
/*     */ public class JMSQueueReceiver
/*     */   extends AbstractJMSReceiver
/*     */ {
/*     */   public JMSQueueReceiver(String qcfBindingName, String queueBindingName, String username, String password) {
/*     */     try {
/*  50 */       Context ctx = new InitialContext();
/*     */       
/*  52 */       QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory)lookup(ctx, qcfBindingName);
/*  53 */       QueueConnection queueConnection = queueConnectionFactory.createQueueConnection(username, password);
/*  54 */       queueConnection.start();
/*  55 */       QueueSession queueSession = queueConnection.createQueueSession(false, 1);
/*  56 */       Queue queue = (Queue)ctx.lookup(queueBindingName);
/*  57 */       QueueReceiver queueReceiver = queueSession.createReceiver(queue);
/*  58 */       queueReceiver.setMessageListener(this);
/*  59 */     } catch (JMSException e) {
/*  60 */       this.logger.error("Could not read JMS message.", (Throwable)e);
/*  61 */     } catch (NamingException e) {
/*  62 */       this.logger.error("Could not read JMS message.", e);
/*  63 */     } catch (RuntimeException e) {
/*  64 */       this.logger.error("Could not read JMS message.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*     */     String line;
/*  74 */     if (args.length != 4) {
/*  75 */       usage("Wrong number of arguments.");
/*     */     }
/*     */     
/*  78 */     String qcfBindingName = args[0];
/*  79 */     String queueBindingName = args[1];
/*  80 */     String username = args[2];
/*  81 */     String password = args[3];
/*     */     
/*  83 */     new JMSQueueReceiver(qcfBindingName, queueBindingName, username, password);
/*     */     
/*  85 */     Charset enc = Charset.defaultCharset();
/*  86 */     BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, enc));
/*     */     
/*  88 */     System.out.println("Type \"exit\" to quit JMSQueueReceiver.");
/*     */     do {
/*  90 */       line = stdin.readLine();
/*  91 */     } while (line != null && !line.equalsIgnoreCase("exit"));
/*  92 */     System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void usage(String msg) {
/* 101 */     System.err.println(msg);
/* 102 */     System.err.println("Usage: java " + JMSQueueReceiver.class.getName() + " QueueConnectionFactoryBindingName QueueBindingName username password");
/*     */     
/* 104 */     System.exit(1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSQueueReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */