/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Collection;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.world.PortalCreateEvent;
/*     */ 
/*     */ public class PortalCreator {
/*     */   private final World a;
/*     */   private final int b;
/*  11 */   private int e = 0; private final int c; private final int d;
/*     */   private ChunkCoordinates f;
/*     */   private int g;
/*     */   private int h;
/*  15 */   Collection<Block> blocks = new HashSet<Block>();
/*     */   
/*     */   public PortalCreator(World world, int i, int j, int k, int l) {
/*  18 */     this.a = world;
/*  19 */     this.b = l;
/*  20 */     this.d = BlockPortal.a[l][0];
/*  21 */     this.c = BlockPortal.a[l][1];
/*     */     
/*  23 */     for (int i1 = j; j > i1 - 21 && j > 0 && a(world.getType(i, j - 1, k)); j--);
/*     */ 
/*     */ 
/*     */     
/*  27 */     int j1 = a(i, j, k, this.d) - 1;
/*     */     
/*  29 */     if (j1 >= 0) {
/*  30 */       this.f = new ChunkCoordinates(i + j1 * Direction.a[this.d], j, k + j1 * Direction.b[this.d]);
/*  31 */       this.h = a(this.f.x, this.f.y, this.f.z, this.c);
/*  32 */       if (this.h < 2 || this.h > 21) {
/*  33 */         this.f = null;
/*  34 */         this.h = 0;
/*     */       } 
/*     */     } 
/*     */     
/*  38 */     if (this.f != null) {
/*  39 */       this.g = a();
/*     */     }
/*     */   }
/*     */   
/*     */   protected int a(int i, int j, int k, int l) {
/*  44 */     int i1 = Direction.a[l];
/*  45 */     int j1 = Direction.b[l];
/*     */ 
/*     */     
/*     */     int k1;
/*     */     
/*  50 */     for (k1 = 0; k1 < 22; k1++) {
/*  51 */       Block block2 = this.a.getType(i + i1 * k1, j, k + j1 * k1);
/*  52 */       if (!a(block2)) {
/*     */         break;
/*     */       }
/*     */       
/*  56 */       Block block1 = this.a.getType(i + i1 * k1, j - 1, k + j1 * k1);
/*     */       
/*  58 */       if (block1 != Blocks.OBSIDIAN) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     Block block = this.a.getType(i + i1 * k1, j, k + j1 * k1);
/*  64 */     return (block == Blocks.OBSIDIAN) ? k1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int a() {
/*  69 */     this.blocks.clear();
/*  70 */     CraftWorld craftWorld = this.a.getWorld();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     label43: for (this.g = 0; this.g < 21; this.g++) {
/*  79 */       int k = this.f.y + this.g;
/*     */       
/*  81 */       for (int j = 0; j < this.h; j++) {
/*  82 */         int m = this.f.x + j * Direction.a[BlockPortal.a[this.b][1]];
/*  83 */         int l = this.f.z + j * Direction.b[BlockPortal.a[this.b][1]];
/*  84 */         Block block = this.a.getType(m, k, l);
/*     */         
/*  86 */         if (!a(block)) {
/*     */           break label43;
/*     */         }
/*     */         
/*  90 */         if (block == Blocks.PORTAL) {
/*  91 */           this.e++;
/*     */         }
/*     */         
/*  94 */         if (j == 0) {
/*  95 */           block = this.a.getType(m + Direction.a[BlockPortal.a[this.b][0]], k, l + Direction.b[BlockPortal.a[this.b][0]]);
/*  96 */           if (block != Blocks.OBSIDIAN) {
/*     */             break label43;
/*     */           }
/*     */           
/* 100 */           this.blocks.add(craftWorld.getBlockAt(m + Direction.a[BlockPortal.a[this.b][0]], k, l + Direction.b[BlockPortal.a[this.b][0]]));
/*     */         
/*     */         }
/* 103 */         else if (j == this.h - 1) {
/* 104 */           block = this.a.getType(m + Direction.a[BlockPortal.a[this.b][1]], k, l + Direction.b[BlockPortal.a[this.b][1]]);
/* 105 */           if (block != Blocks.OBSIDIAN) {
/*     */             break label43;
/*     */           }
/*     */           
/* 109 */           this.blocks.add(craftWorld.getBlockAt(m + Direction.a[BlockPortal.a[this.b][1]], k, l + Direction.b[BlockPortal.a[this.b][1]]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     for (int i = 0; i < this.h; i++) {
/* 117 */       int j = this.f.x + i * Direction.a[BlockPortal.a[this.b][1]];
/* 118 */       int k = this.f.y + this.g;
/* 119 */       int l = this.f.z + i * Direction.b[BlockPortal.a[this.b][1]];
/* 120 */       if (this.a.getType(j, k, l) != Blocks.OBSIDIAN) {
/* 121 */         this.g = 0;
/*     */         
/*     */         break;
/*     */       } 
/* 125 */       this.blocks.add(craftWorld.getBlockAt(j, k, l));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     if (this.g <= 21 && this.g >= 3) {
/* 131 */       return this.g;
/*     */     }
/* 133 */     this.f = null;
/* 134 */     this.h = 0;
/* 135 */     this.g = 0;
/* 136 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(Block block) {
/* 141 */     return (block.material == Material.AIR || block == Blocks.FIRE || block == Blocks.PORTAL);
/*     */   }
/*     */   
/*     */   public boolean b() {
/* 145 */     return (this.f != null && this.h >= 2 && this.h <= 21 && this.g >= 3 && this.g <= 21);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/* 150 */     CraftWorld craftWorld = this.a.getWorld();
/*     */ 
/*     */     
/* 153 */     for (int i = 0; i < this.h; i++) {
/* 154 */       int m = this.f.x + Direction.a[this.c] * i;
/* 155 */       int k = this.f.z + Direction.b[this.c] * i;
/*     */       
/* 157 */       for (int l = 0; l < this.g; l++) {
/* 158 */         int i1 = this.f.y + l;
/*     */         
/* 160 */         this.blocks.add(craftWorld.getBlockAt(m, i1, k));
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     PortalCreateEvent event = new PortalCreateEvent(this.blocks, (World)craftWorld, PortalCreateEvent.CreateReason.FIRE);
/* 165 */     this.a.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 167 */     if (event.isCancelled()) {
/* 168 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 172 */     for (int j = 0; j < this.h; j++) {
/* 173 */       int m = this.f.x + Direction.a[this.c] * j;
/* 174 */       int k = this.f.z + Direction.b[this.c] * j;
/*     */       
/* 176 */       for (int l = 0; l < this.g; l++) {
/* 177 */         int i1 = this.f.y + l;
/*     */         
/* 179 */         this.a.setTypeAndData(m, i1, k, Blocks.PORTAL, this.b, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     return true;
/*     */   }
/*     */   
/*     */   static int a(PortalCreator portalcreator) {
/* 187 */     return portalcreator.e;
/*     */   }
/*     */   
/*     */   static int b(PortalCreator portalcreator) {
/* 191 */     return portalcreator.h;
/*     */   }
/*     */   
/*     */   static int c(PortalCreator portalcreator) {
/* 195 */     return portalcreator.g;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PortalCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */