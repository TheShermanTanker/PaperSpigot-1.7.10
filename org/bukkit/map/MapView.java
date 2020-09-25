/*    */ package org.bukkit.map;public interface MapView { @Deprecated
/*    */   short getId();
/*    */   boolean isVirtual();
/*    */   Scale getScale();
/*    */   void setScale(Scale paramScale);
/*    */   int getCenterX();
/*    */   int getCenterZ();
/*    */   void setCenterX(int paramInt);
/*    */   void setCenterZ(int paramInt);
/*    */   World getWorld();
/*    */   void setWorld(World paramWorld);
/*    */   List<MapRenderer> getRenderers();
/*    */   void addRenderer(MapRenderer paramMapRenderer);
/*    */   boolean removeRenderer(MapRenderer paramMapRenderer);
/* 15 */   public enum Scale { CLOSEST(0),
/* 16 */     CLOSE(1),
/* 17 */     NORMAL(2),
/* 18 */     FAR(3),
/* 19 */     FARTHEST(4);
/*    */     
/*    */     private byte value;
/*    */     
/*    */     Scale(int value) {
/* 24 */       this.value = (byte)value;
/*    */     }
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
/*    */     @Deprecated
/*    */     public byte getValue() {
/* 54 */       return this.value;
/*    */     } }
/*    */    }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */