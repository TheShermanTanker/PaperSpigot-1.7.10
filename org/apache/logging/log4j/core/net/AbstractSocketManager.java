/*    */ package org.apache.logging.log4j.core.net;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
/*    */ import java.net.InetAddress;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.Layout;
/*    */ import org.apache.logging.log4j.core.appender.OutputStreamManager;
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
/*    */ public abstract class AbstractSocketManager
/*    */   extends OutputStreamManager
/*    */ {
/*    */   protected final InetAddress address;
/*    */   protected final String host;
/*    */   protected final int port;
/*    */   
/*    */   public AbstractSocketManager(String name, OutputStream os, InetAddress addr, String host, int port, Layout<? extends Serializable> layout) {
/* 56 */     super(os, name, layout);
/* 57 */     this.address = addr;
/* 58 */     this.host = host;
/* 59 */     this.port = port;
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
/*    */   public Map<String, String> getContentFormat() {
/* 71 */     Map<String, String> result = new HashMap<String, String>(super.getContentFormat());
/* 72 */     result.put("port", Integer.toString(this.port));
/* 73 */     result.put("address", this.address.getHostAddress());
/*    */     
/* 75 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\AbstractSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */