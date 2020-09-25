/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.ClassUtil;
/*    */ import java.util.logging.Logger;
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
/*    */ public class DetectScala
/*    */ {
/* 33 */   private static final Logger logger = Logger.getLogger(DetectScala.class.getName());
/*    */   
/* 35 */   private static Class<?> scalaOptionClass = initScalaOptionClass();
/*    */   
/* 37 */   private static boolean hasScalaSupport = (scalaOptionClass != null);
/*    */   
/*    */   private static Class<?> initScalaOptionClass() {
/*    */     try {
/* 41 */       return ClassUtil.forName("scala.Option");
/* 42 */     } catch (ClassNotFoundException e) {
/*    */       
/* 44 */       logger.fine("Scala type 'scala.Option' not found. Scala Support disabled.");
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean hasScalaSupport() {
/* 53 */     return hasScalaSupport;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Class<?> getScalaOptionClass() {
/* 60 */     return scalaOptionClass;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\DetectScala.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */