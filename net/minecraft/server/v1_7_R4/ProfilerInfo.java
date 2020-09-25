/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ public final class ProfilerInfo
/*    */   implements Comparable
/*    */ {
/*    */   public double a;
/*    */   public double b;
/*    */   public String c;
/*    */   
/*    */   public ProfilerInfo(String paramString, double paramDouble1, double paramDouble2) {
/* 22 */     this.c = paramString;
/* 23 */     this.a = paramDouble1;
/* 24 */     this.b = paramDouble2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(ProfilerInfo paramProfilerInfo) {
/* 29 */     if (paramProfilerInfo.a < this.a) return -1; 
/* 30 */     if (paramProfilerInfo.a > this.a) return 1; 
/* 31 */     return paramProfilerInfo.c.compareTo(this.c);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ProfilerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */