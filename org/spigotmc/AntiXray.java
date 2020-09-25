/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_7_R4.Block;
/*     */ import net.minecraft.server.v1_7_R4.Blocks;
/*     */ import net.minecraft.server.v1_7_R4.ChunkPosition;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import net.minecraft.util.gnu.trove.set.hash.TByteHashSet;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AntiXray
/*     */ {
/*  19 */   private static final CustomTimingsHandler update = new CustomTimingsHandler("xray - update");
/*  20 */   private static final CustomTimingsHandler obfuscate = new CustomTimingsHandler("xray - obfuscate");
/*     */ 
/*     */   
/*  23 */   private final boolean[] obfuscateBlocks = new boolean[32767];
/*     */   
/*     */   private final byte[] replacementOres;
/*     */   
/*     */   public boolean queueUpdates = true;
/*  28 */   public final Set<ChunkPosition> pendingUpdates = new HashSet<ChunkPosition>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AntiXray(SpigotWorldConfig config) {
/*  34 */     for (Iterator<Integer> i$ = ((config.engineMode == 1) ? config.hiddenBlocks : config.replaceBlocks).iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
/*     */       
/*  36 */       this.obfuscateBlocks[id] = true; }
/*     */ 
/*     */ 
/*     */     
/*  40 */     TByteHashSet tByteHashSet = new TByteHashSet();
/*  41 */     for (Integer i : config.hiddenBlocks) {
/*     */       
/*  43 */       Block block = Block.getById(i.intValue());
/*     */       
/*  45 */       if (block != null && !block.isTileEntity())
/*     */       {
/*     */         
/*  48 */         tByteHashSet.add((byte)i.intValue());
/*     */       }
/*     */     } 
/*     */     
/*  52 */     this.replacementOres = tByteHashSet.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushUpdates(World world) {
/*  60 */     if (world.spigotConfig.antiXray && !this.pendingUpdates.isEmpty()) {
/*     */       
/*  62 */       this.queueUpdates = false;
/*     */       
/*  64 */       for (ChunkPosition position : this.pendingUpdates)
/*     */       {
/*  66 */         updateNearbyBlocks(world, position.x, position.y, position.z);
/*     */       }
/*     */       
/*  69 */       this.pendingUpdates.clear();
/*  70 */       this.queueUpdates = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNearbyBlocks(World world, int x, int y, int z) {
/*  80 */     if (world.spigotConfig.antiXray) {
/*     */ 
/*     */       
/*  83 */       if (this.queueUpdates) {
/*     */         
/*  85 */         this.pendingUpdates.add(new ChunkPosition(x, y, z));
/*     */         
/*     */         return;
/*     */       } 
/*  89 */       update.startTiming();
/*  90 */       updateNearbyBlocks(world, x, y, z, 2, false);
/*  91 */       update.stopTiming();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void obfuscateSync(int chunkX, int chunkY, int bitmask, byte[] buffer, World world) {
/* 101 */     if (world.spigotConfig.antiXray) {
/*     */       
/* 103 */       obfuscate.startTiming();
/* 104 */       obfuscate(chunkX, chunkY, bitmask, buffer, world, false);
/* 105 */       obfuscate.stopTiming();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void obfuscate(int chunkX, int chunkY, int bitmask, byte[] buffer, World world, boolean newFormat) {
/* 115 */     if (world.spigotConfig.antiXray) {
/*     */       byte replaceWithTypeId;
/*     */       
/* 118 */       int initialRadius = 1;
/*     */       
/* 120 */       int index = 0;
/*     */       
/* 122 */       int randomOre = 0;
/*     */ 
/*     */       
/* 125 */       int startX = chunkX << 4;
/* 126 */       int startZ = chunkY << 4;
/*     */ 
/*     */       
/* 129 */       switch (world.getWorld().getEnvironment()) {
/*     */         
/*     */         case NETHER:
/* 132 */           replaceWithTypeId = (byte)CraftMagicNumbers.getId(Blocks.NETHERRACK);
/*     */           break;
/*     */         case THE_END:
/* 135 */           replaceWithTypeId = (byte)CraftMagicNumbers.getId(Blocks.WHITESTONE);
/*     */           break;
/*     */         default:
/* 138 */           replaceWithTypeId = (byte)CraftMagicNumbers.getId(Blocks.STONE);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 143 */       for (int i = 0; i < 16; i++) {
/*     */ 
/*     */         
/* 146 */         if ((bitmask & 1 << i) != 0)
/*     */         {
/*     */           
/* 149 */           for (int y = 0; y < 16; y++) {
/*     */             
/* 151 */             for (int z = 0; z < 16; z++) {
/*     */               
/* 153 */               int x = 0; while (true) { if (x < 16)
/*     */                 { int blockId;
/*     */                   
/* 156 */                   if (index >= buffer.length) {
/*     */                     
/* 158 */                     index++;
/* 159 */                     if (newFormat) index++;
/*     */ 
/*     */                     
/*     */                     continue;
/*     */                   } 
/*     */                   
/* 165 */                   int data = 0;
/* 166 */                   if (newFormat) {
/*     */                     
/* 168 */                     blockId = buffer[index] & 0xFF | (buffer[index + 1] & 0xFF) << 8;
/* 169 */                     data = blockId & 0xF;
/* 170 */                     blockId >>>= 4;
/*     */                   } else {
/*     */                     
/* 173 */                     blockId = buffer[index] & 0xFF;
/*     */                   } 
/*     */                   
/* 176 */                   if (this.obfuscateBlocks[blockId])
/*     */                   
/*     */                   { 
/* 179 */                     if (!isLoaded(world, startX + x, (i << 4) + y, startZ + z, initialRadius))
/*     */                     
/* 181 */                     { index++;
/* 182 */                       if (newFormat) index++;
/*     */                        }
/*     */                     else
/*     */                     
/* 186 */                     { if (!hasTransparentBlockAdjacent(world, startX + x, (i << 4) + y, startZ + z, initialRadius))
/*     */                       {
/* 188 */                         switch (world.spigotConfig.engineMode) {
/*     */ 
/*     */                           
/*     */                           case 1:
/* 192 */                             if (newFormat) {
/*     */                               
/* 194 */                               char replace = (char)(replaceWithTypeId << 4 | data);
/* 195 */                               buffer[index] = (byte)(replace & 0xFF);
/* 196 */                               buffer[index + 1] = (byte)(replace >> 8 & 0xFF);
/*     */                               break;
/*     */                             } 
/* 199 */                             buffer[index] = replaceWithTypeId;
/*     */                             break;
/*     */ 
/*     */                           
/*     */                           case 2:
/* 204 */                             if (randomOre >= this.replacementOres.length)
/*     */                             {
/* 206 */                               randomOre = 0;
/*     */                             }
/* 208 */                             if (newFormat) {
/*     */                               
/* 210 */                               char replace = (char)(this.replacementOres[randomOre++] & 0xFF);
/* 211 */                               replace = (char)(replace << 4 | data);
/* 212 */                               buffer[index] = (byte)(replace & 0xFF);
/* 213 */                               buffer[index + 1] = (byte)(replace >> 8 & 0xFF);
/*     */                               break;
/*     */                             } 
/* 216 */                             buffer[index] = this.replacementOres[randomOre++];
/*     */                             break;
/*     */                         } 
/*     */ 
/*     */ 
/*     */                       
/*     */                       }
/* 223 */                       index++; }  continue; }  } else { break; }  index++;
/*     */                 x++; }
/*     */             
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateNearbyBlocks(World world, int x, int y, int z, int radius, boolean updateSelf) {
/* 236 */     if (world.isLoaded(x, y, z)) {
/*     */ 
/*     */       
/* 239 */       Block block = world.getType(x, y, z);
/*     */ 
/*     */       
/* 242 */       if (updateSelf && this.obfuscateBlocks[Block.getId(block)])
/*     */       {
/*     */         
/* 245 */         world.notify(x, y, z);
/*     */       }
/*     */ 
/*     */       
/* 249 */       if (radius > 0) {
/*     */         
/* 251 */         updateNearbyBlocks(world, x + 1, y, z, radius - 1, true);
/* 252 */         updateNearbyBlocks(world, x - 1, y, z, radius - 1, true);
/* 253 */         updateNearbyBlocks(world, x, y + 1, z, radius - 1, true);
/* 254 */         updateNearbyBlocks(world, x, y - 1, z, radius - 1, true);
/* 255 */         updateNearbyBlocks(world, x, y, z + 1, radius - 1, true);
/* 256 */         updateNearbyBlocks(world, x, y, z - 1, radius - 1, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isLoaded(World world, int x, int y, int z, int radius) {
/* 263 */     return (world.isLoaded(x, y, z) && (radius == 0 || (isLoaded(world, x + 1, y, z, radius - 1) && isLoaded(world, x - 1, y, z, radius - 1) && isLoaded(world, x, y + 1, z, radius - 1) && isLoaded(world, x, y - 1, z, radius - 1) && isLoaded(world, x, y, z + 1, radius - 1) && isLoaded(world, x, y, z - 1, radius - 1))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasTransparentBlockAdjacent(World world, int x, int y, int z, int radius) {
/* 275 */     return (!isSolidBlock(world.getType(x, y, z, false)) || (radius > 0 && (hasTransparentBlockAdjacent(world, x + 1, y, z, radius - 1) || hasTransparentBlockAdjacent(world, x - 1, y, z, radius - 1) || hasTransparentBlockAdjacent(world, x, y + 1, z, radius - 1) || hasTransparentBlockAdjacent(world, x, y - 1, z, radius - 1) || hasTransparentBlockAdjacent(world, x, y, z + 1, radius - 1) || hasTransparentBlockAdjacent(world, x, y, z - 1, radius - 1))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSolidBlock(Block block) {
/* 291 */     return (block.r() && block != Blocks.MOB_SPAWNER);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\AntiXray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */