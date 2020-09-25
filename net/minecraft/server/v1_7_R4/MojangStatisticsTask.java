/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.TimerTask;
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
/*    */ class MojangStatisticsTask
/*    */   extends TimerTask
/*    */ {
/*    */   MojangStatisticsTask(MojangStatisticsGenerator paramMojangStatisticsGenerator) {}
/*    */   
/*    */   public void run() {
/*    */     HashMap<Object, Object> hashMap;
/* 48 */     if (!MojangStatisticsGenerator.a(this.a).getSnooperEnabled()) {
/*    */       return;
/*    */     }
/* 51 */     synchronized (MojangStatisticsGenerator.b(this.a)) {
/* 52 */       hashMap = new HashMap<Object, Object>(MojangStatisticsGenerator.c(this.a));
/* 53 */       if (MojangStatisticsGenerator.d(this.a) == 0) hashMap.putAll(MojangStatisticsGenerator.e(this.a)); 
/* 54 */       hashMap.put("snooper_count", Integer.valueOf(MojangStatisticsGenerator.f(this.a)));
/* 55 */       hashMap.put("snooper_token", MojangStatisticsGenerator.g(this.a));
/*    */     } 
/*    */     
/* 58 */     HttpUtilities.a(MojangStatisticsGenerator.h(this.a), hashMap, true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MojangStatisticsTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */