/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.NetherWartsState;
/*    */ 
/*    */ 
/*    */ public class NetherWarts
/*    */   extends MaterialData
/*    */ {
/*    */   public NetherWarts() {
/* 11 */     super(Material.NETHER_WARTS);
/*    */   }
/*    */   
/*    */   public NetherWarts(NetherWartsState state) {
/* 15 */     this();
/* 16 */     setState(state);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public NetherWarts(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public NetherWarts(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public NetherWarts(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public NetherWarts(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NetherWartsState getState() {
/* 56 */     switch (getData()) {
/*    */       case 0:
/* 58 */         return NetherWartsState.SEEDED;
/*    */       case 1:
/* 60 */         return NetherWartsState.STAGE_ONE;
/*    */       case 2:
/* 62 */         return NetherWartsState.STAGE_TWO;
/*    */     } 
/* 64 */     return NetherWartsState.RIPE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setState(NetherWartsState state) {
/* 74 */     switch (state) {
/*    */       case SEEDED:
/* 76 */         setData((byte)0);
/*    */         return;
/*    */       case STAGE_ONE:
/* 79 */         setData((byte)1);
/*    */         return;
/*    */       case STAGE_TWO:
/* 82 */         setData((byte)2);
/*    */         return;
/*    */       case RIPE:
/* 85 */         setData((byte)3);
/*    */         return;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return getState() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public NetherWarts clone() {
/* 97 */     return (NetherWarts)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\NetherWarts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */