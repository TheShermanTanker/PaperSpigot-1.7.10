/*    */ package com.avaje.ebean.enhance.ant;
/*    */ 
/*    */ import com.avaje.ebean.enhance.agent.Transformer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainTransform
/*    */ {
/*    */   public static void main(String[] args) {
/* 12 */     if (isHelp(args)) {
/* 13 */       printHelp();
/*    */       
/*    */       return;
/*    */     } 
/* 17 */     String transformArgs = "debug=1";
/* 18 */     String inDir = "D:/dev/workspace/ebeanExample/bin";
/* 19 */     String pkg = "app/data/test";
/*    */     
/* 21 */     if (args.length > 0) {
/* 22 */       inDir = args[0];
/*    */     }
/* 24 */     if (args.length > 1) {
/* 25 */       pkg = args[1];
/*    */     }
/*    */     
/* 28 */     if (args.length > 2) {
/* 29 */       transformArgs = args[2];
/*    */     }
/*    */     
/* 32 */     ClassLoader cl = ClassLoader.getSystemClassLoader();
/*    */     
/* 34 */     Transformer t = new Transformer("", transformArgs);
/*    */     
/* 36 */     OfflineFileTransform ft = new OfflineFileTransform(t, cl, inDir, inDir);
/*    */     
/* 38 */     ft.process(pkg);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void printHelp() {
/* 43 */     System.out.println("Usage: [inputDirectory] [packages] [transformArguments]");
/*    */   }
/*    */   
/*    */   private static boolean isHelp(String[] args) {
/* 47 */     for (int i = 0; i < args.length; i++) {
/* 48 */       if (args[i].equalsIgnoreCase("help")) {
/* 49 */         return true;
/*    */       }
/* 51 */       if (args[i].equalsIgnoreCase("-h")) {
/* 52 */         return true;
/*    */       }
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\ant\MainTransform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */