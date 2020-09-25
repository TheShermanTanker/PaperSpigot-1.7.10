/*    */ package org.bukkit.map;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MapCursorCollection
/*    */ {
/* 11 */   private List<MapCursor> cursors = new ArrayList<MapCursor>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 19 */     return this.cursors.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapCursor getCursor(int index) {
/* 29 */     return this.cursors.get(index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeCursor(MapCursor cursor) {
/* 39 */     return this.cursors.remove(cursor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapCursor addCursor(MapCursor cursor) {
/* 49 */     this.cursors.add(cursor);
/* 50 */     return cursor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapCursor addCursor(int x, int y, byte direction) {
/* 62 */     return addCursor(x, y, direction, (byte)0, true);
/*    */   }
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
/*    */   @Deprecated
/*    */   public MapCursor addCursor(int x, int y, byte direction, byte type) {
/* 77 */     return addCursor(x, y, direction, type, true);
/*    */   }
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
/*    */   @Deprecated
/*    */   public MapCursor addCursor(int x, int y, byte direction, byte type, boolean visible) {
/* 93 */     return addCursor(new MapCursor((byte)x, (byte)y, direction, type, visible));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapCursorCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */