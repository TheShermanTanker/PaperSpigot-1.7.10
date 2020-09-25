/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class CounterStatistic
/*    */   extends Statistic
/*    */ {
/*    */   public CounterStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Counter paramCounter) {
/*  8 */     super(paramString, paramIChatBaseComponent, paramCounter);
/*    */   }
/*    */   
/*    */   public CounterStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent) {
/* 12 */     super(paramString, paramIChatBaseComponent);
/*    */   }
/*    */ 
/*    */   
/*    */   public Statistic h() {
/* 17 */     super.h();
/*    */     
/* 19 */     StatisticList.c.add(this);
/*    */     
/* 21 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CounterStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */