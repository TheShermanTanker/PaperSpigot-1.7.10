/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockIterator
/*     */   implements Iterator<Block>
/*     */ {
/*     */   private final World world;
/*     */   private final int maxDistance;
/*     */   private static final int gridSize = 16777216;
/*     */   private boolean end = false;
/*  26 */   private Block[] blockQueue = new Block[3];
/*  27 */   private int currentBlock = 0;
/*  28 */   private int currentDistance = 0;
/*     */ 
/*     */   
/*     */   private int maxDistanceInt;
/*     */ 
/*     */   
/*     */   private int secondError;
/*     */ 
/*     */   
/*     */   private int thirdError;
/*     */ 
/*     */   
/*     */   private int secondStep;
/*     */ 
/*     */   
/*     */   private int thirdStep;
/*     */ 
/*     */   
/*     */   private BlockFace mainFace;
/*     */ 
/*     */   
/*     */   private BlockFace secondFace;
/*     */   
/*     */   private BlockFace thirdFace;
/*     */ 
/*     */   
/*     */   public BlockIterator(World world, Vector start, Vector direction, double yOffset, int maxDistance) {
/*  55 */     this.world = world;
/*  56 */     this.maxDistance = maxDistance;
/*     */     
/*  58 */     Vector startClone = start.clone();
/*     */     
/*  60 */     startClone.setY(startClone.getY() + yOffset);
/*     */     
/*  62 */     this.currentDistance = 0;
/*     */     
/*  64 */     double mainDirection = 0.0D;
/*  65 */     double secondDirection = 0.0D;
/*  66 */     double thirdDirection = 0.0D;
/*     */     
/*  68 */     double mainPosition = 0.0D;
/*  69 */     double secondPosition = 0.0D;
/*  70 */     double thirdPosition = 0.0D;
/*     */     
/*  72 */     Block startBlock = this.world.getBlockAt(NumberConversions.floor(startClone.getX()), NumberConversions.floor(startClone.getY()), NumberConversions.floor(startClone.getZ()));
/*     */     
/*  74 */     if (getXLength(direction) > mainDirection) {
/*  75 */       this.mainFace = getXFace(direction);
/*  76 */       mainDirection = getXLength(direction);
/*  77 */       mainPosition = getXPosition(direction, startClone, startBlock);
/*     */       
/*  79 */       this.secondFace = getYFace(direction);
/*  80 */       secondDirection = getYLength(direction);
/*  81 */       secondPosition = getYPosition(direction, startClone, startBlock);
/*     */       
/*  83 */       this.thirdFace = getZFace(direction);
/*  84 */       thirdDirection = getZLength(direction);
/*  85 */       thirdPosition = getZPosition(direction, startClone, startBlock);
/*     */     } 
/*  87 */     if (getYLength(direction) > mainDirection) {
/*  88 */       this.mainFace = getYFace(direction);
/*  89 */       mainDirection = getYLength(direction);
/*  90 */       mainPosition = getYPosition(direction, startClone, startBlock);
/*     */       
/*  92 */       this.secondFace = getZFace(direction);
/*  93 */       secondDirection = getZLength(direction);
/*  94 */       secondPosition = getZPosition(direction, startClone, startBlock);
/*     */       
/*  96 */       this.thirdFace = getXFace(direction);
/*  97 */       thirdDirection = getXLength(direction);
/*  98 */       thirdPosition = getXPosition(direction, startClone, startBlock);
/*     */     } 
/* 100 */     if (getZLength(direction) > mainDirection) {
/* 101 */       this.mainFace = getZFace(direction);
/* 102 */       mainDirection = getZLength(direction);
/* 103 */       mainPosition = getZPosition(direction, startClone, startBlock);
/*     */       
/* 105 */       this.secondFace = getXFace(direction);
/* 106 */       secondDirection = getXLength(direction);
/* 107 */       secondPosition = getXPosition(direction, startClone, startBlock);
/*     */       
/* 109 */       this.thirdFace = getYFace(direction);
/* 110 */       thirdDirection = getYLength(direction);
/* 111 */       thirdPosition = getYPosition(direction, startClone, startBlock);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     double d = mainPosition / mainDirection;
/* 117 */     double secondd = secondPosition - secondDirection * d;
/* 118 */     double thirdd = thirdPosition - thirdDirection * d;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.secondError = NumberConversions.floor(secondd * 1.6777216E7D);
/* 124 */     this.secondStep = NumberConversions.round(secondDirection / mainDirection * 1.6777216E7D);
/* 125 */     this.thirdError = NumberConversions.floor(thirdd * 1.6777216E7D);
/* 126 */     this.thirdStep = NumberConversions.round(thirdDirection / mainDirection * 1.6777216E7D);
/*     */     
/* 128 */     if (this.secondError + this.secondStep <= 0) {
/* 129 */       this.secondError = -this.secondStep + 1;
/*     */     }
/*     */     
/* 132 */     if (this.thirdError + this.thirdStep <= 0) {
/* 133 */       this.thirdError = -this.thirdStep + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 138 */     Block lastBlock = startBlock.getRelative(this.mainFace.getOppositeFace());
/*     */     
/* 140 */     if (this.secondError < 0) {
/* 141 */       this.secondError += 16777216;
/* 142 */       lastBlock = lastBlock.getRelative(this.secondFace.getOppositeFace());
/*     */     } 
/*     */     
/* 145 */     if (this.thirdError < 0) {
/* 146 */       this.thirdError += 16777216;
/* 147 */       lastBlock = lastBlock.getRelative(this.thirdFace.getOppositeFace());
/*     */     } 
/*     */ 
/*     */     
/* 151 */     this.secondError -= 16777216;
/* 152 */     this.thirdError -= 16777216;
/*     */     
/* 154 */     this.blockQueue[0] = lastBlock;
/* 155 */     this.currentBlock = -1;
/*     */     
/* 157 */     scan();
/*     */     
/* 159 */     boolean startBlockFound = false;
/*     */     
/* 161 */     for (int cnt = this.currentBlock; cnt >= 0; cnt--) {
/* 162 */       if (blockEquals(this.blockQueue[cnt], startBlock)) {
/* 163 */         this.currentBlock = cnt;
/* 164 */         startBlockFound = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 169 */     if (!startBlockFound) {
/* 170 */       throw new IllegalStateException("Start block missed in BlockIterator");
/*     */     }
/*     */ 
/*     */     
/* 174 */     this.maxDistanceInt = NumberConversions.round(maxDistance / Math.sqrt(mainDirection * mainDirection + secondDirection * secondDirection + thirdDirection * thirdDirection) / mainDirection);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean blockEquals(Block a, Block b) {
/* 179 */     return (a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ());
/*     */   }
/*     */   
/*     */   private BlockFace getXFace(Vector direction) {
/* 183 */     return (direction.getX() > 0.0D) ? BlockFace.EAST : BlockFace.WEST;
/*     */   }
/*     */   
/*     */   private BlockFace getYFace(Vector direction) {
/* 187 */     return (direction.getY() > 0.0D) ? BlockFace.UP : BlockFace.DOWN;
/*     */   }
/*     */   
/*     */   private BlockFace getZFace(Vector direction) {
/* 191 */     return (direction.getZ() > 0.0D) ? BlockFace.SOUTH : BlockFace.NORTH;
/*     */   }
/*     */   
/*     */   private double getXLength(Vector direction) {
/* 195 */     return Math.abs(direction.getX());
/*     */   }
/*     */   
/*     */   private double getYLength(Vector direction) {
/* 199 */     return Math.abs(direction.getY());
/*     */   }
/*     */   
/*     */   private double getZLength(Vector direction) {
/* 203 */     return Math.abs(direction.getZ());
/*     */   }
/*     */   
/*     */   private double getPosition(double direction, double position, int blockPosition) {
/* 207 */     return (direction > 0.0D) ? (position - blockPosition) : ((blockPosition + 1) - position);
/*     */   }
/*     */   
/*     */   private double getXPosition(Vector direction, Vector position, Block block) {
/* 211 */     return getPosition(direction.getX(), position.getX(), block.getX());
/*     */   }
/*     */   
/*     */   private double getYPosition(Vector direction, Vector position, Block block) {
/* 215 */     return getPosition(direction.getY(), position.getY(), block.getY());
/*     */   }
/*     */   
/*     */   private double getZPosition(Vector direction, Vector position, Block block) {
/* 219 */     return getPosition(direction.getZ(), position.getZ(), block.getZ());
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
/*     */   public BlockIterator(Location loc, double yOffset, int maxDistance) {
/* 233 */     this(loc.getWorld(), loc.toVector(), loc.getDirection(), yOffset, maxDistance);
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
/*     */   public BlockIterator(Location loc, double yOffset) {
/* 245 */     this(loc.getWorld(), loc.toVector(), loc.getDirection(), yOffset, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockIterator(Location loc) {
/* 255 */     this(loc, 0.0D);
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
/*     */   public BlockIterator(LivingEntity entity, int maxDistance) {
/* 268 */     this(entity.getLocation(), entity.getEyeHeight(), maxDistance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockIterator(LivingEntity entity) {
/* 278 */     this(entity, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 286 */     scan();
/* 287 */     return (this.currentBlock != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block next() {
/* 297 */     scan();
/* 298 */     if (this.currentBlock <= -1) {
/* 299 */       throw new NoSuchElementException();
/*     */     }
/* 301 */     return this.blockQueue[this.currentBlock--];
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 306 */     throw new UnsupportedOperationException("[BlockIterator] doesn't support block removal");
/*     */   }
/*     */   
/*     */   private void scan() {
/* 310 */     if (this.currentBlock >= 0) {
/*     */       return;
/*     */     }
/* 313 */     if (this.maxDistance != 0 && this.currentDistance > this.maxDistanceInt) {
/* 314 */       this.end = true;
/*     */       return;
/*     */     } 
/* 317 */     if (this.end) {
/*     */       return;
/*     */     }
/*     */     
/* 321 */     this.currentDistance++;
/*     */     
/* 323 */     this.secondError += this.secondStep;
/* 324 */     this.thirdError += this.thirdStep;
/*     */     
/* 326 */     if (this.secondError > 0 && this.thirdError > 0) {
/* 327 */       this.blockQueue[2] = this.blockQueue[0].getRelative(this.mainFace);
/* 328 */       if (this.secondStep * this.thirdError < this.thirdStep * this.secondError) {
/* 329 */         this.blockQueue[1] = this.blockQueue[2].getRelative(this.secondFace);
/* 330 */         this.blockQueue[0] = this.blockQueue[1].getRelative(this.thirdFace);
/*     */       } else {
/* 332 */         this.blockQueue[1] = this.blockQueue[2].getRelative(this.thirdFace);
/* 333 */         this.blockQueue[0] = this.blockQueue[1].getRelative(this.secondFace);
/*     */       } 
/* 335 */       this.thirdError -= 16777216;
/* 336 */       this.secondError -= 16777216;
/* 337 */       this.currentBlock = 2; return;
/*     */     } 
/* 339 */     if (this.secondError > 0) {
/* 340 */       this.blockQueue[1] = this.blockQueue[0].getRelative(this.mainFace);
/* 341 */       this.blockQueue[0] = this.blockQueue[1].getRelative(this.secondFace);
/* 342 */       this.secondError -= 16777216;
/* 343 */       this.currentBlock = 1; return;
/*     */     } 
/* 345 */     if (this.thirdError > 0) {
/* 346 */       this.blockQueue[1] = this.blockQueue[0].getRelative(this.mainFace);
/* 347 */       this.blockQueue[0] = this.blockQueue[1].getRelative(this.thirdFace);
/* 348 */       this.thirdError -= 16777216;
/* 349 */       this.currentBlock = 1;
/*     */       return;
/*     */     } 
/* 352 */     this.blockQueue[0] = this.blockQueue[0].getRelative(this.mainFace);
/* 353 */     this.currentBlock = 0;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\BlockIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */