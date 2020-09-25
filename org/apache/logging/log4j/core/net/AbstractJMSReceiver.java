/*    */ package org.apache.logging.log4j.core.net;
/*    */ 
/*    */ import javax.jms.JMSException;
/*    */ import javax.jms.Message;
/*    */ import javax.jms.MessageListener;
/*    */ import javax.jms.ObjectMessage;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.NameNotFoundException;
/*    */ import javax.naming.NamingException;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.AbstractServer;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractJMSReceiver
/*    */   extends AbstractServer
/*    */   implements MessageListener
/*    */ {
/* 38 */   protected Logger logger = LogManager.getLogger(getClass().getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onMessage(Message message) {
/*    */     try {
/* 48 */       if (message instanceof ObjectMessage) {
/* 49 */         ObjectMessage objectMessage = (ObjectMessage)message;
/* 50 */         log((LogEvent)objectMessage.getObject());
/*    */       } else {
/* 52 */         this.logger.warn("Received message is of type " + message.getJMSType() + ", was expecting ObjectMessage.");
/*    */       }
/*    */     
/* 55 */     } catch (JMSException jmse) {
/* 56 */       this.logger.error("Exception thrown while processing incoming message.", (Throwable)jmse);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object lookup(Context ctx, String name) throws NamingException {
/*    */     try {
/* 70 */       return ctx.lookup(name);
/* 71 */     } catch (NameNotFoundException e) {
/* 72 */       this.logger.error("Could not find name [" + name + "].");
/* 73 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\AbstractJMSReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */