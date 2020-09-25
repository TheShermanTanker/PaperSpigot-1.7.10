/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.RuntimeMXBean;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.Callable;
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
/*    */ class CrashReportJVMFlags
/*    */   implements Callable
/*    */ {
/*    */   CrashReportJVMFlags(CrashReport paramCrashReport) {}
/*    */   
/*    */   public String a() {
/* 81 */     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
/* 82 */     List<String> list = runtimeMXBean.getInputArguments();
/* 83 */     byte b = 0;
/* 84 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/* 86 */     for (String str : list) {
/* 87 */       if (str.startsWith("-X")) {
/* 88 */         if (b++ > 0) {
/* 89 */           stringBuilder.append(" ");
/*    */         }
/*    */         
/* 92 */         stringBuilder.append(str);
/*    */       } 
/*    */     } 
/*    */     
/* 96 */     return String.format("%d total; %s", new Object[] { Integer.valueOf(b), stringBuilder.toString() });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CrashReportJVMFlags.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */