/*     */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CraftBlockState implements BlockState {
/*     */   private final CraftWorld world;
/*     */   private final CraftChunk chunk;
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final int z;
/*     */   protected int type;
/*     */   protected MaterialData data;
/*     */   protected int flag;
/*     */   protected final byte light;
/*     */   
/*     */   public CraftBlockState(Block block) {
/*  29 */     this.world = (CraftWorld)block.getWorld();
/*  30 */     this.x = block.getX();
/*  31 */     this.y = block.getY();
/*  32 */     this.z = block.getZ();
/*  33 */     this.type = block.getTypeId();
/*  34 */     this.light = block.getLightLevel();
/*  35 */     this.chunk = (CraftChunk)block.getChunk();
/*  36 */     this.flag = 3;
/*     */     
/*  38 */     createData(block.getData());
/*     */   }
/*     */   
/*     */   public CraftBlockState(Block block, int flag) {
/*  42 */     this(block);
/*  43 */     this.flag = flag;
/*     */   }
/*     */   
/*     */   public static CraftBlockState getBlockState(World world, int x, int y, int z) {
/*  47 */     return new CraftBlockState(world.getWorld().getBlockAt(x, y, z));
/*     */   }
/*     */   
/*     */   public static CraftBlockState getBlockState(World world, int x, int y, int z, int flag) {
/*  51 */     return new CraftBlockState(world.getWorld().getBlockAt(x, y, z), flag);
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*  55 */     return (World)this.world;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  59 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  63 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  67 */     return this.z;
/*     */   }
/*     */   
/*     */   public Chunk getChunk() {
/*  71 */     return (Chunk)this.chunk;
/*     */   }
/*     */   
/*     */   public void setData(MaterialData data) {
/*  75 */     Material mat = getType();
/*     */     
/*  77 */     if (mat == null || mat.getData() == null) {
/*  78 */       this.data = data;
/*     */     }
/*  80 */     else if (data.getClass() == mat.getData() || data.getClass() == MaterialData.class) {
/*  81 */       this.data = data;
/*     */     } else {
/*  83 */       throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialData getData() {
/*  90 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setType(Material type) {
/*  94 */     setTypeId(type.getId());
/*     */   }
/*     */   
/*     */   public boolean setTypeId(int type) {
/*  98 */     if (this.type != type) {
/*  99 */       this.type = type;
/*     */       
/* 101 */       createData((byte)0);
/*     */     } 
/* 103 */     return true;
/*     */   }
/*     */   
/*     */   public Material getType() {
/* 107 */     return Material.getMaterial(getTypeId());
/*     */   }
/*     */   
/*     */   public void setFlag(int flag) {
/* 111 */     this.flag = flag;
/*     */   }
/*     */   
/*     */   public int getFlag() {
/* 115 */     return this.flag;
/*     */   }
/*     */   
/*     */   public int getTypeId() {
/* 119 */     return this.type;
/*     */   }
/*     */   
/*     */   public byte getLightLevel() {
/* 123 */     return this.light;
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/* 127 */     return this.world.getBlockAt(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public boolean update() {
/* 131 */     return update(false);
/*     */   }
/*     */   
/*     */   public boolean update(boolean force) {
/* 135 */     return update(force, true);
/*     */   }
/*     */   
/*     */   public boolean update(boolean force, boolean applyPhysics) {
/* 139 */     Block block = getBlock();
/*     */     
/* 141 */     if (block.getType() != getType()) {
/* 142 */       if (force) {
/* 143 */         block.setTypeId(getTypeId(), applyPhysics);
/*     */       } else {
/* 145 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 149 */     block.setData(getRawData(), applyPhysics);
/* 150 */     this.world.getHandle().notify(this.x, this.y, this.z);
/*     */     
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   private void createData(byte data) {
/* 156 */     Material mat = getType();
/* 157 */     if (mat == null || mat.getData() == null) {
/* 158 */       this.data = new MaterialData(this.type, data);
/*     */     } else {
/* 160 */       this.data = mat.getNewData(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte getRawData() {
/* 165 */     return this.data.getData();
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 169 */     return new Location((World)this.world, this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Location getLocation(Location loc) {
/* 173 */     if (loc != null) {
/* 174 */       loc.setWorld((World)this.world);
/* 175 */       loc.setX(this.x);
/* 176 */       loc.setY(this.y);
/* 177 */       loc.setZ(this.z);
/* 178 */       loc.setYaw(0.0F);
/* 179 */       loc.setPitch(0.0F);
/*     */     } 
/*     */     
/* 182 */     return loc;
/*     */   }
/*     */   
/*     */   public void setRawData(byte data) {
/* 186 */     this.data.setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 191 */     if (obj == null) {
/* 192 */       return false;
/*     */     }
/* 194 */     if (getClass() != obj.getClass()) {
/* 195 */       return false;
/*     */     }
/* 197 */     CraftBlockState other = (CraftBlockState)obj;
/* 198 */     if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
/* 199 */       return false;
/*     */     }
/* 201 */     if (this.x != other.x) {
/* 202 */       return false;
/*     */     }
/* 204 */     if (this.y != other.y) {
/* 205 */       return false;
/*     */     }
/* 207 */     if (this.z != other.z) {
/* 208 */       return false;
/*     */     }
/* 210 */     if (this.type != other.type) {
/* 211 */       return false;
/*     */     }
/* 213 */     if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
/* 214 */       return false;
/*     */     }
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     int hash = 7;
/* 222 */     hash = 73 * hash + ((this.world != null) ? this.world.hashCode() : 0);
/* 223 */     hash = 73 * hash + this.x;
/* 224 */     hash = 73 * hash + this.y;
/* 225 */     hash = 73 * hash + this.z;
/* 226 */     hash = 73 * hash + this.type;
/* 227 */     hash = 73 * hash + ((this.data != null) ? this.data.hashCode() : 0);
/* 228 */     return hash;
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/* 232 */     this.chunk.getCraftWorld().getBlockMetadata().setMetadata(getBlock(), metadataKey, newMetadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 236 */     return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(getBlock(), metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 240 */     return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(getBlock(), metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 244 */     this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(getBlock(), metadataKey, owningPlugin);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftBlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */