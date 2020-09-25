/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatHoverable
/*    */ {
/*    */   private final EnumHoverAction a;
/*    */   private final IChatBaseComponent b;
/*    */   
/*    */   public ChatHoverable(EnumHoverAction paramEnumHoverAction, IChatBaseComponent paramIChatBaseComponent) {
/* 12 */     this.a = paramEnumHoverAction;
/* 13 */     this.b = paramIChatBaseComponent;
/*    */   }
/*    */   
/*    */   public EnumHoverAction a() {
/* 17 */     return this.a;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent b() {
/* 21 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 26 */     if (this == paramObject) return true; 
/* 27 */     if (paramObject == null || getClass() != paramObject.getClass()) return false;
/*    */     
/* 29 */     ChatHoverable chatHoverable = (ChatHoverable)paramObject;
/*    */     
/* 31 */     if (this.a != chatHoverable.a) return false; 
/* 32 */     if ((this.b != null) ? !this.b.equals(chatHoverable.b) : (chatHoverable.b != null)) return false;
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 39 */     return "HoverEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatHoverable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */