/*    */ package org.bukkit.craftbukkit.v1_7_R4.map;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.WorldMap;
/*    */ import net.minecraft.server.v1_7_R4.WorldMapDecoration;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.map.MapCanvas;
/*    */ import org.bukkit.map.MapCursorCollection;
/*    */ import org.bukkit.map.MapRenderer;
/*    */ import org.bukkit.map.MapView;
/*    */ 
/*    */ public class CraftMapRenderer
/*    */   extends MapRenderer
/*    */ {
/*    */   private final WorldMap worldMap;
/*    */   
/*    */   public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap) {
/* 18 */     super(false);
/* 19 */     this.worldMap = worldMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(MapView map, MapCanvas canvas, Player player) {
/* 25 */     for (int x = 0; x < 128; x++) {
/* 26 */       for (int y = 0; y < 128; y++) {
/* 27 */         canvas.setPixel(x, y, this.worldMap.colors[y * 128 + x]);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 32 */     MapCursorCollection cursors = canvas.getCursors();
/* 33 */     while (cursors.size() > 0) {
/* 34 */       cursors.removeCursor(cursors.getCursor(0));
/*    */     }
/*    */     
/* 37 */     for (Object key : this.worldMap.decorations.keySet()) {
/*    */       
/* 39 */       Player other = Bukkit.getPlayerExact((String)key);
/* 40 */       if (other != null && !player.canSee(other)) {
/*    */         continue;
/*    */       }
/*    */       
/* 44 */       WorldMapDecoration decoration = (WorldMapDecoration)this.worldMap.decorations.get(key);
/* 45 */       cursors.addCursor(decoration.locX, decoration.locY, (byte)(decoration.rotation & 0xF), decoration.type);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\map\CraftMapRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */