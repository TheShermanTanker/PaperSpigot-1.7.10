/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Command
/*    */   extends MaterialData
/*    */   implements Redstone
/*    */ {
/*    */   public Command() {
/* 10 */     super(Material.COMMAND);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Command(int type) {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public Command(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Command(int type, byte data) {
/* 32 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Command(Material type, byte data) {
/* 41 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPowered() {
/* 51 */     return ((getData() & 0x1) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPowered(boolean bool) {
/* 61 */     setData((byte)(bool ? (getData() | 0x1) : (getData() & 0xFFFFFFFE)));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*    */   }
/*    */ 
/*    */   
/*    */   public Command clone() {
/* 71 */     return (Command)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */