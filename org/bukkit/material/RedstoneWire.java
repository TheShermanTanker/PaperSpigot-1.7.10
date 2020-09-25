/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class RedstoneWire
/*    */   extends MaterialData
/*    */   implements Redstone
/*    */ {
/*    */   public RedstoneWire() {
/* 10 */     super(Material.REDSTONE_WIRE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public RedstoneWire(int type) {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public RedstoneWire(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public RedstoneWire(int type, byte data) {
/* 32 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public RedstoneWire(Material type, byte data) {
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
/* 51 */     return (getData() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneWire clone() {
/* 61 */     return (RedstoneWire)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\RedstoneWire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */