/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.map.RenderData;
/*     */ import org.bukkit.map.MapCursor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMapHumanTracker
/*     */ {
/*     */   public final EntityHuman trackee;
/*     */   public int[] b;
/*     */   public int[] c;
/*     */   private int f;
/*     */   
/*     */   public WorldMapHumanTracker(WorldMap worldmap, EntityHuman entityhuman) {
/*  18 */     this.worldMap = worldmap;
/*  19 */     this.b = new int[128];
/*  20 */     this.c = new int[128];
/*  21 */     this.trackee = entityhuman;
/*     */     
/*  23 */     for (int i = 0; i < this.b.length; i++) {
/*  24 */       this.b[i] = 0;
/*  25 */       this.c[i] = 127;
/*     */     } 
/*     */   }
/*     */   private int g; private byte[] h; public int d; private boolean i;
/*     */   final WorldMap worldMap;
/*     */   
/*     */   public byte[] a(ItemStack itemstack) {
/*  32 */     if (!this.i) {
/*  33 */       byte[] abyte = { 2, this.worldMap.scale };
/*  34 */       this.i = true;
/*  35 */       return abyte;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     boolean custom = (this.worldMap.mapView.renderers.size() > 1 || !(this.worldMap.mapView.renderers.get(0) instanceof org.bukkit.craftbukkit.v1_7_R4.map.CraftMapRenderer));
/*  42 */     RenderData render = custom ? this.worldMap.mapView.render((CraftPlayer)this.trackee.getBukkitEntity()) : null;
/*     */     
/*  44 */     if (--this.g < 0) {
/*  45 */       this.g = 4;
/*  46 */       byte[] abyte = new byte[(custom ? render.cursors.size() : this.worldMap.decorations.size()) * 3 + 1];
/*  47 */       abyte[0] = 1;
/*  48 */       int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  53 */       for (Iterator<E> iterator = custom ? render.cursors.iterator() : this.worldMap.decorations.values().iterator(); iterator.hasNext(); i++) {
/*  54 */         MapCursor cursor = custom ? (MapCursor)iterator.next() : null;
/*  55 */         if (cursor == null || cursor.isVisible()) {
/*  56 */           WorldMapDecoration deco = custom ? null : (WorldMapDecoration)iterator.next();
/*     */           
/*  58 */           abyte[i * 3 + 1] = (byte)((custom ? cursor.getRawType() : deco.type) << 4 | (custom ? cursor.getDirection() : deco.rotation) & 0xF);
/*  59 */           abyte[i * 3 + 2] = custom ? cursor.getX() : deco.locX;
/*  60 */           abyte[i * 3 + 3] = custom ? cursor.getY() : deco.locY;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  65 */       boolean flag = !itemstack.A();
/*     */       
/*  67 */       if (this.h != null && this.h.length == abyte.length) {
/*  68 */         for (int j = 0; j < abyte.length; j++) {
/*  69 */           if (abyte[j] != this.h[j]) {
/*  70 */             flag = false;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/*  75 */         flag = false;
/*     */       } 
/*     */       
/*  78 */       if (!flag) {
/*  79 */         this.h = abyte;
/*  80 */         return abyte;
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     for (int k = 0; k < 1; k++) {
/*  85 */       int i = this.f++ * 11 % 128;
/*  86 */       if (this.b[i] >= 0) {
/*  87 */         int l = this.c[i] - this.b[i] + 1;
/*     */         
/*  89 */         int j = this.b[i];
/*  90 */         byte[] abyte1 = new byte[l + 3];
/*     */         
/*  92 */         abyte1[0] = 0;
/*  93 */         abyte1[1] = (byte)i;
/*  94 */         abyte1[2] = (byte)j;
/*     */         
/*  96 */         for (int i1 = 0; i1 < abyte1.length - 3; i1++) {
/*  97 */           abyte1[i1 + 3] = (custom ? render.buffer : this.worldMap.colors)[(i1 + j) * 128 + i];
/*     */         }
/*     */         
/* 100 */         this.c[i] = -1;
/* 101 */         this.b[i] = -1;
/* 102 */         return abyte1;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldMapHumanTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */