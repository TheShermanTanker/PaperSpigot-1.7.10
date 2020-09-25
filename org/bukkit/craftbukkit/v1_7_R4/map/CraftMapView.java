/*     */ package org.bukkit.craftbukkit.v1_7_R4.map;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.WorldMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.map.MapRenderer;
/*     */ import org.bukkit.map.MapView;
/*     */ 
/*     */ public final class CraftMapView
/*     */   implements MapView
/*     */ {
/*  20 */   private final Map<CraftPlayer, RenderData> renderCache = new HashMap<CraftPlayer, RenderData>();
/*  21 */   public final List<MapRenderer> renderers = new ArrayList<MapRenderer>();
/*  22 */   private final Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases = new HashMap<MapRenderer, Map<CraftPlayer, CraftMapCanvas>>();
/*     */   protected final WorldMap worldMap;
/*     */   
/*     */   public CraftMapView(WorldMap worldMap) {
/*  26 */     this.worldMap = worldMap;
/*  27 */     addRenderer(new CraftMapRenderer(this, worldMap));
/*     */   }
/*     */   
/*     */   public short getId() {
/*  31 */     String text = this.worldMap.id;
/*  32 */     if (text.startsWith("map_")) {
/*     */       try {
/*  34 */         return Short.parseShort(text.substring("map_".length()));
/*     */       }
/*  36 */       catch (NumberFormatException ex) {
/*  37 */         throw new IllegalStateException("Map has non-numeric ID");
/*     */       } 
/*     */     }
/*  40 */     throw new IllegalStateException("Map has invalid ID");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVirtual() {
/*  45 */     return (this.renderers.size() > 0 && !(this.renderers.get(0) instanceof CraftMapRenderer));
/*     */   }
/*     */   
/*     */   public MapView.Scale getScale() {
/*  49 */     return MapView.Scale.valueOf(this.worldMap.scale);
/*     */   }
/*     */   
/*     */   public void setScale(MapView.Scale scale) {
/*  53 */     this.worldMap.scale = scale.getValue();
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*  57 */     byte dimension = this.worldMap.map;
/*  58 */     for (World world : Bukkit.getServer().getWorlds()) {
/*  59 */       if ((((CraftWorld)world).getHandle()).dimension == dimension) {
/*  60 */         return world;
/*     */       }
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */   
/*     */   public void setWorld(World world) {
/*  67 */     this.worldMap.map = (byte)(((CraftWorld)world).getHandle()).dimension;
/*     */   }
/*     */   
/*     */   public int getCenterX() {
/*  71 */     return this.worldMap.centerX;
/*     */   }
/*     */   
/*     */   public int getCenterZ() {
/*  75 */     return this.worldMap.centerZ;
/*     */   }
/*     */   
/*     */   public void setCenterX(int x) {
/*  79 */     this.worldMap.centerX = x;
/*     */   }
/*     */   
/*     */   public void setCenterZ(int z) {
/*  83 */     this.worldMap.centerZ = z;
/*     */   }
/*     */   
/*     */   public List<MapRenderer> getRenderers() {
/*  87 */     return new ArrayList<MapRenderer>(this.renderers);
/*     */   }
/*     */   
/*     */   public void addRenderer(MapRenderer renderer) {
/*  91 */     if (!this.renderers.contains(renderer)) {
/*  92 */       this.renderers.add(renderer);
/*  93 */       this.canvases.put(renderer, new HashMap<CraftPlayer, CraftMapCanvas>());
/*  94 */       renderer.initialize(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean removeRenderer(MapRenderer renderer) {
/*  99 */     if (this.renderers.contains(renderer)) {
/* 100 */       this.renderers.remove(renderer);
/* 101 */       for (Map.Entry<CraftPlayer, CraftMapCanvas> entry : (Iterable<Map.Entry<CraftPlayer, CraftMapCanvas>>)((Map)this.canvases.get(renderer)).entrySet()) {
/* 102 */         for (int x = 0; x < 128; x++) {
/* 103 */           for (int y = 0; y < 128; y++) {
/* 104 */             ((CraftMapCanvas)entry.getValue()).setPixel(x, y, (byte)-1);
/*     */           }
/*     */         } 
/*     */       } 
/* 108 */       this.canvases.remove(renderer);
/* 109 */       return true;
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isContextual() {
/* 116 */     for (MapRenderer renderer : this.renderers) {
/* 117 */       if (renderer.isContextual()) return true; 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public RenderData render(CraftPlayer player) {
/* 123 */     boolean context = isContextual();
/* 124 */     RenderData render = this.renderCache.get(context ? player : null);
/*     */     
/* 126 */     if (render == null) {
/* 127 */       render = new RenderData();
/* 128 */       this.renderCache.put(context ? player : null, render);
/*     */     } 
/*     */     
/* 131 */     if (context && this.renderCache.containsKey(null)) {
/* 132 */       this.renderCache.remove(null);
/*     */     }
/*     */     
/* 135 */     Arrays.fill(render.buffer, (byte)0);
/* 136 */     render.cursors.clear();
/*     */     
/* 138 */     for (MapRenderer renderer : this.renderers) {
/* 139 */       CraftMapCanvas canvas = (CraftMapCanvas)((Map)this.canvases.get(renderer)).get(renderer.isContextual() ? player : null);
/* 140 */       if (canvas == null) {
/* 141 */         canvas = new CraftMapCanvas(this);
/* 142 */         ((Map<CraftPlayer, CraftMapCanvas>)this.canvases.get(renderer)).put(renderer.isContextual() ? player : null, canvas);
/*     */       } 
/*     */       
/* 145 */       canvas.setBase(render.buffer);
/* 146 */       renderer.render(this, canvas, (Player)player);
/*     */       
/* 148 */       byte[] buf = canvas.getBuffer(); int i;
/* 149 */       for (i = 0; i < buf.length; i++) {
/* 150 */         byte color = buf[i];
/*     */         
/* 152 */         if (color >= 0 || color <= -113) render.buffer[i] = color;
/*     */       
/*     */       } 
/* 155 */       for (i = 0; i < canvas.getCursors().size(); i++) {
/* 156 */         render.cursors.add(canvas.getCursors().getCursor(i));
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return render;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\map\CraftMapView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */