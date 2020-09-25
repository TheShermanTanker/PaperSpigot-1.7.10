/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import net.minecraft.util.com.google.common.collect.HashMultiset;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.google.common.collect.Multiset;
/*     */ import net.minecraft.util.com.google.common.collect.Multisets;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.server.MapInitializeEvent;
/*     */ import org.bukkit.map.MapView;
/*     */ 
/*     */ public class ItemWorldMap
/*     */   extends ItemWorldMapBase {
/*     */   protected ItemWorldMap() {
/*  15 */     a(true);
/*     */   }
/*     */   
/*     */   public WorldMap getSavedMap(ItemStack itemstack, World world) {
/*  19 */     World worldMain = (world.getServer().getServer()).worlds.get(0);
/*  20 */     String s = "map_" + itemstack.getData();
/*  21 */     WorldMap worldmap = (WorldMap)worldMain.a(WorldMap.class, s);
/*     */     
/*  23 */     if (worldmap == null && !world.isStatic) {
/*  24 */       itemstack.setData(worldMain.b("map"));
/*  25 */       s = "map_" + itemstack.getData();
/*  26 */       worldmap = new WorldMap(s);
/*  27 */       worldmap.scale = 3;
/*  28 */       int i = 128 * (1 << worldmap.scale);
/*     */       
/*  30 */       worldmap.centerX = Math.round(world.getWorldData().c() / i) * i;
/*  31 */       worldmap.centerZ = Math.round((world.getWorldData().e() / i)) * i;
/*  32 */       worldmap.map = (byte)((WorldServer)world).dimension;
/*  33 */       worldmap.c();
/*  34 */       worldMain.a(s, worldmap);
/*     */ 
/*     */       
/*  37 */       MapInitializeEvent event = new MapInitializeEvent((MapView)worldmap.mapView);
/*  38 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*     */     } 
/*     */ 
/*     */     
/*  42 */     return worldmap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, Entity entity, WorldMap worldmap) {
/*  47 */     if (((WorldServer)world).dimension == worldmap.map && entity instanceof EntityHuman) {
/*  48 */       int i = 1 << worldmap.scale;
/*  49 */       int j = worldmap.centerX;
/*  50 */       int k = worldmap.centerZ;
/*  51 */       int l = MathHelper.floor(entity.locX - j) / i + 64;
/*  52 */       int i1 = MathHelper.floor(entity.locZ - k) / i + 64;
/*  53 */       int j1 = 128 / i;
/*     */       
/*  55 */       if (world.worldProvider.g) {
/*  56 */         j1 /= 2;
/*     */       }
/*     */       
/*  59 */       WorldMapHumanTracker worldmaphumantracker = worldmap.a((EntityHuman)entity);
/*     */       
/*  61 */       worldmaphumantracker.d++;
/*     */       
/*  63 */       for (int k1 = l - j1 + 1; k1 < l + j1; k1++) {
/*  64 */         if ((k1 & 0xF) == (worldmaphumantracker.d & 0xF)) {
/*  65 */           int l1 = 255;
/*  66 */           int i2 = 0;
/*  67 */           double d0 = 0.0D;
/*     */           
/*  69 */           for (int j2 = i1 - j1 - 1; j2 < i1 + j1; j2++) {
/*  70 */             if (k1 >= 0 && j2 >= -1 && k1 < 128 && j2 < 128) {
/*  71 */               int k2 = k1 - l;
/*  72 */               int l2 = j2 - i1;
/*  73 */               boolean flag = (k2 * k2 + l2 * l2 > (j1 - 2) * (j1 - 2));
/*  74 */               int i3 = (j / i + k1 - 64) * i;
/*  75 */               int j3 = (k / i + j2 - 64) * i;
/*  76 */               HashMultiset hashmultiset = HashMultiset.create();
/*  77 */               Chunk chunk = world.getChunkAtWorldCoords(i3, j3);
/*     */               
/*  79 */               if (!chunk.isEmpty()) {
/*  80 */                 int k3 = i3 & 0xF;
/*  81 */                 int l3 = j3 & 0xF;
/*  82 */                 int i4 = 0;
/*  83 */                 double d1 = 0.0D;
/*     */ 
/*     */                 
/*  86 */                 if (world.worldProvider.g) {
/*  87 */                   int j4 = i3 + j3 * 231871;
/*  88 */                   j4 = j4 * j4 * 31287121 + j4 * 11;
/*  89 */                   if ((j4 >> 20 & 0x1) == 0) {
/*  90 */                     hashmultiset.add(Blocks.DIRT.f(0), 10);
/*     */                   } else {
/*  92 */                     hashmultiset.add(Blocks.STONE.f(0), 100);
/*     */                   } 
/*     */                   
/*  95 */                   d1 = 100.0D;
/*     */                 } else {
/*  97 */                   for (int j4 = 0; j4 < i; j4++) {
/*  98 */                     for (int k4 = 0; k4 < i; k4++) {
/*  99 */                       int l4 = chunk.b(j4 + k3, k4 + l3) + 1;
/* 100 */                       Block block = Blocks.AIR;
/* 101 */                       int i5 = 0;
/*     */                       
/* 103 */                       if (l4 > 1) {
/*     */                         do {
/* 105 */                           l4--;
/* 106 */                           block = chunk.getType(j4 + k3, l4, k4 + l3);
/* 107 */                           i5 = chunk.getData(j4 + k3, l4, k4 + l3);
/* 108 */                         } while (block.f(i5) == MaterialMapColor.b && l4 > 0);
/*     */                         
/* 110 */                         if (l4 > 0 && block.getMaterial().isLiquid()) {
/* 111 */                           Block block1; int j5 = l4 - 1;
/*     */ 
/*     */ 
/*     */                           
/*     */                           do {
/* 116 */                             block1 = chunk.getType(j4 + k3, j5--, k4 + l3);
/* 117 */                             i4++;
/* 118 */                           } while (j5 > 0 && block1.getMaterial().isLiquid());
/*     */                         } 
/*     */                       } 
/*     */                       
/* 122 */                       d1 += l4 / (i * i);
/* 123 */                       hashmultiset.add(block.f(i5));
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 128 */                 i4 /= i * i;
/* 129 */                 double d2 = (d1 - d0) * 4.0D / (i + 4) + ((k1 + j2 & 0x1) - 0.5D) * 0.4D;
/* 130 */                 byte b0 = 1;
/*     */                 
/* 132 */                 if (d2 > 0.6D) {
/* 133 */                   b0 = 2;
/*     */                 }
/*     */                 
/* 136 */                 if (d2 < -0.6D) {
/* 137 */                   b0 = 0;
/*     */                 }
/*     */                 
/* 140 */                 MaterialMapColor materialmapcolor = (MaterialMapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)hashmultiset), MaterialMapColor.b);
/*     */                 
/* 142 */                 if (materialmapcolor == MaterialMapColor.n) {
/* 143 */                   d2 = i4 * 0.1D + (k1 + j2 & 0x1) * 0.2D;
/* 144 */                   b0 = 1;
/* 145 */                   if (d2 < 0.5D) {
/* 146 */                     b0 = 2;
/*     */                   }
/*     */                   
/* 149 */                   if (d2 > 0.9D) {
/* 150 */                     b0 = 0;
/*     */                   }
/*     */                 } 
/*     */                 
/* 154 */                 d0 = d1;
/* 155 */                 if (j2 >= 0 && k2 * k2 + l2 * l2 < j1 * j1 && (!flag || (k1 + j2 & 0x1) != 0)) {
/* 156 */                   byte b1 = worldmap.colors[k1 + j2 * 128];
/* 157 */                   byte b2 = (byte)(materialmapcolor.M * 4 + b0);
/*     */                   
/* 159 */                   if (b1 != b2) {
/* 160 */                     if (l1 > j2) {
/* 161 */                       l1 = j2;
/*     */                     }
/*     */                     
/* 164 */                     if (i2 < j2) {
/* 165 */                       i2 = j2;
/*     */                     }
/*     */                     
/* 168 */                     worldmap.colors[k1 + j2 * 128] = b2;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 175 */           if (l1 <= i2) {
/* 176 */             worldmap.flagDirty(k1, l1, i2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
/* 184 */     if (!world.isStatic) {
/* 185 */       WorldMap worldmap = getSavedMap(itemstack, world);
/*     */       
/* 187 */       if (entity instanceof EntityHuman) {
/* 188 */         EntityHuman entityhuman = (EntityHuman)entity;
/*     */         
/* 190 */         worldmap.a(entityhuman, itemstack);
/*     */       } 
/*     */       
/* 193 */       if (flag) {
/* 194 */         a(world, entity, worldmap);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Packet c(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 200 */     byte[] abyte = getSavedMap(itemstack, world).getUpdatePacket(itemstack, world, entityhuman);
/*     */     
/* 202 */     return (abyte == null) ? null : new PacketPlayOutMap(itemstack.getData(), abyte, (getSavedMap(itemstack, world)).scale);
/*     */   }
/*     */   
/*     */   public void d(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 206 */     if (itemstack.hasTag() && itemstack.getTag().getBoolean("map_is_scaling")) {
/* 207 */       WorldMap worldmap = Items.MAP.getSavedMap(itemstack, world);
/*     */       
/* 209 */       world = (world.getServer().getServer()).worlds.get(0);
/*     */       
/* 211 */       itemstack.setData(world.b("map"));
/* 212 */       WorldMap worldmap1 = new WorldMap("map_" + itemstack.getData());
/*     */       
/* 214 */       worldmap1.scale = (byte)(worldmap.scale + 1);
/* 215 */       if (worldmap1.scale > 4) {
/* 216 */         worldmap1.scale = 4;
/*     */       }
/*     */       
/* 219 */       worldmap1.centerX = worldmap.centerX;
/* 220 */       worldmap1.centerZ = worldmap.centerZ;
/* 221 */       worldmap1.map = worldmap.map;
/* 222 */       worldmap1.c();
/* 223 */       world.a("map_" + itemstack.getData(), worldmap1);
/*     */ 
/*     */       
/* 226 */       MapInitializeEvent event = new MapInitializeEvent((MapView)worldmap1.mapView);
/* 227 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemWorldMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */