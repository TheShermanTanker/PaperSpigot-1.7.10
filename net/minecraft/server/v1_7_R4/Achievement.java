/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Achievement
/*    */   extends Statistic
/*    */ {
/*    */   public final int a;
/*    */   public final int b;
/*    */   public final Achievement c;
/*    */   private final String k;
/*    */   public final ItemStack d;
/*    */   private boolean m;
/*    */   
/*    */   public Achievement(String paramString1, String paramString2, int paramInt1, int paramInt2, Item paramItem, Achievement paramAchievement) {
/* 19 */     this(paramString1, paramString2, paramInt1, paramInt2, new ItemStack(paramItem), paramAchievement);
/*    */   }
/*    */   
/*    */   public Achievement(String paramString1, String paramString2, int paramInt1, int paramInt2, Block paramBlock, Achievement paramAchievement) {
/* 23 */     this(paramString1, paramString2, paramInt1, paramInt2, new ItemStack(paramBlock), paramAchievement);
/*    */   }
/*    */   
/*    */   public Achievement(String paramString1, String paramString2, int paramInt1, int paramInt2, ItemStack paramItemStack, Achievement paramAchievement) {
/* 27 */     super(paramString1, new ChatMessage("achievement." + paramString2, new Object[0]));
/* 28 */     this.d = paramItemStack;
/*    */     
/* 30 */     this.k = "achievement." + paramString2 + ".desc";
/* 31 */     this.a = paramInt1;
/* 32 */     this.b = paramInt2;
/*    */     
/* 34 */     if (paramInt1 < AchievementList.a) AchievementList.a = paramInt1; 
/* 35 */     if (paramInt2 < AchievementList.b) AchievementList.b = paramInt2; 
/* 36 */     if (paramInt1 > AchievementList.c) AchievementList.c = paramInt1; 
/* 37 */     if (paramInt2 > AchievementList.d) AchievementList.d = paramInt2; 
/* 38 */     this.c = paramAchievement;
/*    */   }
/*    */ 
/*    */   
/*    */   public Achievement a() {
/* 43 */     this.f = true;
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public Achievement b() {
/* 48 */     this.m = true;
/* 49 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Achievement c() {
/* 54 */     super.h();
/*    */     
/* 56 */     AchievementList.e.add(this);
/*    */     
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatBaseComponent e() {
/* 68 */     IChatBaseComponent iChatBaseComponent = super.e();
/* 69 */     iChatBaseComponent.getChatModifier().setColor(g() ? EnumChatFormat.DARK_PURPLE : EnumChatFormat.GREEN);
/* 70 */     return iChatBaseComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public Achievement a(Class paramClass) {
/* 75 */     return (Achievement)super.b(paramClass);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean g() {
/* 91 */     return this.m;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Achievement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */