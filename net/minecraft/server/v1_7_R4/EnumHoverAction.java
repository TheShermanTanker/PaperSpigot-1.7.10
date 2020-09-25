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
/*    */ public enum EnumHoverAction
/*    */ {
/*    */   private static final Map d;
/*    */   private final boolean e;
/* 53 */   SHOW_TEXT("show_text", true),
/* 54 */   SHOW_ACHIEVEMENT("show_achievement", true),
/* 55 */   SHOW_ITEM("show_item", true);
/*    */   static {
/* 57 */     d = Maps.newHashMap();
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
/* 75 */     for (EnumHoverAction enumHoverAction : values())
/* 76 */       d.put(enumHoverAction.b(), enumHoverAction); 
/*    */   }
/*    */   private final String f;
/*    */   
/*    */   public static EnumHoverAction a(String paramString) {
/* 81 */     return (EnumHoverAction)d.get(paramString);
/*    */   }
/*    */   
/*    */   EnumHoverAction(String paramString1, boolean paramBoolean) {
/*    */     this.f = paramString1;
/*    */     this.e = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean a() {
/*    */     return this.e;
/*    */   }
/*    */   
/*    */   public String b() {
/*    */     return this.f;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumHoverAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */