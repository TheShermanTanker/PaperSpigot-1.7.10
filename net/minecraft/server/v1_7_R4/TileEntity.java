/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ public class TileEntity {
/*  15 */   public CustomTimingsHandler tickTimer = SpigotTimings.getTileEntityTimings(this);
/*  16 */   private static final Logger a = LogManager.getLogger();
/*  17 */   private static Map i = new HashMap<Object, Object>();
/*  18 */   private static Map j = new HashMap<Object, Object>();
/*     */   protected World world;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   protected boolean f;
/*  24 */   public int g = -1;
/*     */ 
/*     */   
/*     */   public Block h;
/*     */ 
/*     */   
/*     */   private void scheduleTick(int x0, int y0, int z0) {
/*  31 */     TileEntity tileEntity = this.world.getTileEntity(x0, y0, z0);
/*  32 */     if (tileEntity instanceof TileEntityHopper && tileEntity.world != null) {
/*     */       
/*  34 */       int i = BlockHopper.b(tileEntity.p());
/*     */       
/*  36 */       if (tileEntity.x + Facing.b[i] == this.x && tileEntity.y + Facing.c[i] == this.y && tileEntity.z + Facing.d[i] == this.z) {
/*  37 */         ((TileEntityHopper)tileEntity).makeTick();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleTicks() {
/*  45 */     if (this.world != null && this.world.spigotConfig.altHopperTicking) {
/*     */       
/*  47 */       scheduleTick(this.x, this.y + 1, this.z);
/*     */       
/*  49 */       for (int i = 2; i < 6; i++) {
/*  50 */         scheduleTick(this.x + Facing.b[i], this.y, this.z + Facing.d[i]);
/*     */       }
/*     */       
/*  53 */       TileEntity tileEntity = this.world.getTileEntity(this.x, this.y - 1, this.z);
/*  54 */       if (tileEntity instanceof TileEntityHopper && tileEntity.world != null) {
/*  55 */         ((TileEntityHopper)tileEntity).makeTick();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  61 */   private static int tileEntityCounter = 0;
/*     */   public boolean isAdded = false;
/*  63 */   public int tileId = tileEntityCounter++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void a(Class<?> oclass, String s) {
/*  70 */     if (i.containsKey(s)) {
/*  71 */       throw new IllegalArgumentException("Duplicate id: " + s);
/*     */     }
/*  73 */     i.put(s, oclass);
/*  74 */     j.put(oclass, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  79 */     return this.world;
/*     */   }
/*     */   
/*     */   public void a(World world) {
/*  83 */     this.world = world;
/*     */   }
/*     */   
/*     */   public boolean o() {
/*  87 */     return (this.world != null);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  91 */     this.x = nbttagcompound.getInt("x");
/*  92 */     this.y = nbttagcompound.getInt("y");
/*  93 */     this.z = nbttagcompound.getInt("z");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  97 */     String s = (String)j.get(getClass());
/*     */     
/*  99 */     if (s == null) {
/* 100 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/* 102 */     nbttagcompound.setString("id", s);
/* 103 */     nbttagcompound.setInt("x", this.x);
/* 104 */     nbttagcompound.setInt("y", this.y);
/* 105 */     nbttagcompound.setInt("z", this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {}
/*     */   
/*     */   public static TileEntity c(NBTTagCompound nbttagcompound) {
/* 112 */     TileEntity tileentity = null;
/*     */     
/*     */     try {
/* 115 */       Class<TileEntity> oclass = (Class)i.get(nbttagcompound.getString("id"));
/*     */       
/* 117 */       if (oclass != null) {
/* 118 */         tileentity = oclass.newInstance();
/*     */       }
/* 120 */     } catch (Exception exception) {
/* 121 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 124 */     if (tileentity != null) {
/* 125 */       tileentity.a(nbttagcompound);
/*     */     } else {
/* 127 */       a.warn("Skipping BlockEntity with id " + nbttagcompound.getString("id"));
/*     */     } 
/*     */     
/* 130 */     return tileentity;
/*     */   }
/*     */   
/*     */   public int p() {
/* 134 */     if (this.g == -1) {
/* 135 */       this.g = this.world.getData(this.x, this.y, this.z);
/*     */     }
/*     */     
/* 138 */     return this.g;
/*     */   }
/*     */   
/*     */   public void update() {
/* 142 */     if (this.world != null) {
/* 143 */       this.g = this.world.getData(this.x, this.y, this.z);
/* 144 */       this.world.b(this.x, this.y, this.z, this);
/* 145 */       if (q() != Blocks.AIR) {
/* 146 */         this.world.updateAdjacentComparators(this.x, this.y, this.z, q());
/*     */       }
/*     */ 
/*     */       
/* 150 */       scheduleTicks();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Block q() {
/* 156 */     if (this.h == null) {
/* 157 */       this.h = this.world.getType(this.x, this.y, this.z);
/*     */     }
/*     */     
/* 160 */     return this.h;
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public boolean r() {
/* 168 */     return this.f;
/*     */   }
/*     */   
/*     */   public void s() {
/* 172 */     this.f = true;
/*     */   }
/*     */   
/*     */   public void t() {
/* 176 */     this.f = false;
/*     */   }
/*     */   
/*     */   public boolean c(int i, int j) {
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   public void u() {
/* 184 */     this.h = null;
/* 185 */     this.g = -1;
/*     */   }
/*     */   
/*     */   public void a(CrashReportSystemDetails crashreportsystemdetails) {
/* 189 */     crashreportsystemdetails.a("Name", new CrashReportTileEntityName(this));
/* 190 */     Block block = q();
/* 191 */     if (block != null) {
/* 192 */       CrashReportSystemDetails.a(crashreportsystemdetails, this.x, this.y, this.z, q(), p());
/*     */     }
/* 194 */     crashreportsystemdetails.a("Actual block type", new CrashReportTileEntityType(this));
/* 195 */     crashreportsystemdetails.a("Actual block data value", new CrashReportTileEntityData(this));
/*     */   }
/*     */   
/*     */   static Map v() {
/* 199 */     return j;
/*     */   }
/*     */   
/*     */   static {
/* 203 */     a(TileEntityFurnace.class, "Furnace");
/* 204 */     a(TileEntityChest.class, "Chest");
/* 205 */     a(TileEntityEnderChest.class, "EnderChest");
/* 206 */     a(TileEntityRecordPlayer.class, "RecordPlayer");
/* 207 */     a(TileEntityDispenser.class, "Trap");
/* 208 */     a(TileEntityDropper.class, "Dropper");
/* 209 */     a(TileEntitySign.class, "Sign");
/* 210 */     a(TileEntityMobSpawner.class, "MobSpawner");
/* 211 */     a(TileEntityNote.class, "Music");
/* 212 */     a(TileEntityPiston.class, "Piston");
/* 213 */     a(TileEntityBrewingStand.class, "Cauldron");
/* 214 */     a(TileEntityEnchantTable.class, "EnchantTable");
/* 215 */     a(TileEntityEnderPortal.class, "Airportal");
/* 216 */     a(TileEntityCommand.class, "Control");
/* 217 */     a(TileEntityBeacon.class, "Beacon");
/* 218 */     a(TileEntitySkull.class, "Skull");
/* 219 */     a(TileEntityLightDetector.class, "DLDetector");
/* 220 */     a(TileEntityHopper.class, "Hopper");
/* 221 */     a(TileEntityComparator.class, "Comparator");
/* 222 */     a(TileEntityFlowerPot.class, "FlowerPot");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InventoryHolder getOwner() {
/* 228 */     Block block = this.world.getWorld().getBlockAt(this.x, this.y, this.z);
/* 229 */     if (block == null) {
/* 230 */       Bukkit.getLogger().log(Level.WARNING, "No block for owner at %s %d %d %d", new Object[] { this.world.getWorld(), Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
/* 231 */       return null;
/*     */     } 
/*     */     
/* 234 */     BlockState state = block.getState();
/* 235 */     if (state instanceof InventoryHolder) return (InventoryHolder)state; 
/* 236 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */