/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tripwire
/*    */   extends MaterialData
/*    */ {
/*    */   public Tripwire() {
/* 11 */     super(Material.TRIPWIRE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Tripwire(int type) {
/* 20 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Tripwire(int type, byte data) {
/* 29 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isActivated() {
/* 38 */     return ((getData() & 0x4) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setActivated(boolean act) {
/* 47 */     int dat = getData() & 0xB;
/* 48 */     if (act) {
/* 49 */       dat |= 0x4;
/*    */     }
/* 51 */     setData((byte)dat);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isObjectTriggering() {
/* 60 */     return ((getData() & 0x1) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setObjectTriggering(boolean trig) {
/* 69 */     int dat = getData() & 0xE;
/* 70 */     if (trig) {
/* 71 */       dat |= 0x1;
/*    */     }
/* 73 */     setData((byte)dat);
/*    */   }
/*    */ 
/*    */   
/*    */   public Tripwire clone() {
/* 78 */     return (Tripwire)super.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 83 */     return super.toString() + (isActivated() ? " Activated" : "") + (isObjectTriggering() ? " Triggered" : "");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Tripwire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */