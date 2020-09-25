/*     */ package org.spigotmc;
/*     */ import com.google.common.collect.ArrayListMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.TileEntity;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityDispenser;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityFlowerPot;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityRecordPlayer;
/*     */ import net.minecraft.server.v1_7_R4.WorldServer;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
/*     */ 
/*     */ public class WorldTileEntityList extends HashSet<TileEntity> {
/*  15 */   private static final TObjectIntHashMap<Class<? extends TileEntity>> tileEntityTickIntervals = new TObjectIntHashMap<Class<? extends TileEntity>>()
/*     */     {
/*     */     
/*     */     };
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getInterval(Class<? extends TileEntity> cls) {
/*  50 */     int tickInterval = tileEntityTickIntervals.get(cls);
/*  51 */     return (tickInterval != 0) ? tickInterval : 1;
/*     */   }
/*     */   
/*     */   private static int getBucketId(TileEntity entity, Integer interval) {
/*  55 */     return entity.tileId % interval.intValue();
/*     */   }
/*     */   
/*  58 */   private final Map<Integer, Multimap<Integer, TileEntity>> tickList = Maps.newHashMap();
/*     */   private final WorldServer world;
/*     */   
/*     */   public WorldTileEntityList(World world) {
/*  62 */     this.world = (WorldServer)world;
/*     */   }
/*     */   
/*     */   private Multimap<Integer, TileEntity> getBucket(int interval) {
/*     */     ArrayListMultimap arrayListMultimap;
/*  67 */     Multimap<Integer, TileEntity> intervalBucket = this.tickList.get(Integer.valueOf(interval));
/*  68 */     if (intervalBucket == null) {
/*  69 */       arrayListMultimap = ArrayListMultimap.create();
/*  70 */       this.tickList.put(Integer.valueOf(interval), arrayListMultimap);
/*     */     } 
/*  72 */     return (Multimap<Integer, TileEntity>)arrayListMultimap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(TileEntity entity) {
/*  80 */     if (entity.isAdded) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     int interval = getInterval((Class)entity.getClass());
/*  85 */     if (interval > 0) {
/*  86 */       entity.isAdded = true;
/*  87 */       int bucket = getBucketId(entity, Integer.valueOf(interval));
/*  88 */       Multimap<Integer, TileEntity> typeBucket = getBucket(interval);
/*  89 */       return typeBucket.put(Integer.valueOf(bucket), entity);
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  96 */     if (!(o instanceof TileEntity)) {
/*  97 */       return false;
/*     */     }
/*  99 */     TileEntity entity = (TileEntity)o;
/* 100 */     if (!entity.isAdded) {
/* 101 */       return false;
/*     */     }
/* 103 */     entity.isAdded = false;
/* 104 */     int interval = getInterval((Class)entity.getClass());
/* 105 */     int bucket = getBucketId(entity, Integer.valueOf(interval));
/* 106 */     Multimap<Integer, TileEntity> typeBucket = getBucket(interval);
/* 107 */     return typeBucket.remove(Integer.valueOf(bucket), entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 112 */     return new WorldTileEntityIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 117 */     return (o instanceof TileEntity && ((TileEntity)o).isAdded);
/*     */   }
/*     */   
/*     */   private class WorldTileEntityIterator implements Iterator<TileEntity> {
/*     */     private final Iterator<Map.Entry<Integer, Multimap<Integer, TileEntity>>> intervalIterator;
/* 122 */     private Map.Entry<Integer, Multimap<Integer, TileEntity>> intervalMap = null;
/* 123 */     private Iterator<TileEntity> listIterator = null;
/*     */     
/*     */     protected WorldTileEntityIterator() {
/* 126 */       this.intervalIterator = WorldTileEntityList.this.tickList.entrySet().iterator();
/* 127 */       nextInterval();
/*     */     }
/*     */     
/*     */     private boolean nextInterval() {
/* 131 */       this.listIterator = null;
/* 132 */       if (this.intervalIterator.hasNext()) {
/* 133 */         this.intervalMap = this.intervalIterator.next();
/*     */         
/* 135 */         Integer interval = this.intervalMap.getKey();
/* 136 */         Multimap<Integer, TileEntity> buckets = this.intervalMap.getValue();
/*     */         
/* 138 */         int bucket = (int)(WorldTileEntityList.this.world.getTime() % interval.intValue());
/*     */         
/* 140 */         if (!buckets.isEmpty() && buckets.containsKey(Integer.valueOf(bucket))) {
/* 141 */           Collection<TileEntity> tileList = buckets.get(Integer.valueOf(bucket));
/*     */           
/* 143 */           if (tileList != null && !tileList.isEmpty()) {
/* 144 */             this.listIterator = tileList.iterator();
/* 145 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 150 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*     */       while (true) {
/* 157 */         if (this.listIterator != null && this.listIterator.hasNext()) {
/* 158 */           return true;
/*     */         }
/* 160 */         if (!nextInterval())
/* 161 */           return false; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public TileEntity next() {
/* 166 */       return this.listIterator.next();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 171 */       this.listIterator.remove();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\WorldTileEntityList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */