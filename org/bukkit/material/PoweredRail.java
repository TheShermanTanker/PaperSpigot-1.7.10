/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class PoweredRail
/*    */   extends ExtendedRails
/*    */   implements Redstone
/*    */ {
/*    */   public PoweredRail() {
/* 10 */     super(Material.POWERED_RAIL);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public PoweredRail(int type) {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public PoweredRail(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public PoweredRail(int type, byte data) {
/* 32 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public PoweredRail(Material type, byte data) {
/* 41 */     super(type, data);
/*    */   }
/*    */   
/*    */   public boolean isPowered() {
/* 45 */     return ((getData() & 0x8) == 8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPowered(boolean isPowered) {
/* 54 */     setData((byte)(isPowered ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*    */   }
/*    */ 
/*    */   
/*    */   public PoweredRail clone() {
/* 59 */     return (PoweredRail)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PoweredRail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */