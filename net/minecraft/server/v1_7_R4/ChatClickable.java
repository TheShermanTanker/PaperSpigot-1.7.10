/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatClickable
/*    */ {
/*    */   private final EnumClickAction a;
/*    */   private final String b;
/*    */   
/*    */   public ChatClickable(EnumClickAction paramEnumClickAction, String paramString) {
/* 12 */     this.a = paramEnumClickAction;
/* 13 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public EnumClickAction a() {
/* 17 */     return this.a;
/*    */   }
/*    */   
/*    */   public String b() {
/* 21 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 26 */     if (this == paramObject) return true; 
/* 27 */     if (paramObject == null || getClass() != paramObject.getClass()) return false;
/*    */     
/* 29 */     ChatClickable chatClickable = (ChatClickable)paramObject;
/*    */     
/* 31 */     if (this.a != chatClickable.a) return false; 
/* 32 */     if ((this.b != null) ? !this.b.equals(chatClickable.b) : (chatClickable.b != null)) return false;
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 39 */     return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     int i = this.a.hashCode();
/* 48 */     i = 31 * i + ((this.b != null) ? this.b.hashCode() : 0);
/* 49 */     return i;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatClickable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */