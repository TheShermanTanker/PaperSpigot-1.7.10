/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.common.collect.Maps;
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
/*    */ public enum EnumClickAction
/*    */ {
/*    */   private static final Map f;
/*    */   private final boolean g;
/* 53 */   OPEN_URL("open_url", true),
/* 54 */   OPEN_FILE("open_file", false),
/* 55 */   RUN_COMMAND("run_command", true),
/* 56 */   TWITCH_USER_INFO("twitch_user_info", false),
/* 57 */   SUGGEST_COMMAND("suggest_command", true);
/*    */   static {
/* 59 */     f = Maps.newHashMap();
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
/* 77 */     for (EnumClickAction enumClickAction : values())
/* 78 */       f.put(enumClickAction.b(), enumClickAction); 
/*    */   }
/*    */   private final String h;
/*    */   
/*    */   public static EnumClickAction a(String paramString) {
/* 83 */     return (EnumClickAction)f.get(paramString);
/*    */   }
/*    */   
/*    */   EnumClickAction(String paramString1, boolean paramBoolean) {
/*    */     this.h = paramString1;
/*    */     this.g = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean a() {
/*    */     return this.g;
/*    */   }
/*    */   
/*    */   public String b() {
/*    */     return this.h;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumClickAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */