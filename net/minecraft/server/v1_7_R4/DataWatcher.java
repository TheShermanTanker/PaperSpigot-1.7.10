/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import net.minecraft.util.gnu.trove.TDecorators;
/*     */ import net.minecraft.util.gnu.trove.map.TIntObjectMap;
/*     */ import net.minecraft.util.gnu.trove.map.TObjectIntMap;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TIntObjectHashMap;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ObjectUtils;
/*     */ import org.spigotmc.ProtocolData;
/*     */ 
/*     */ public class DataWatcher {
/*     */   private final Entity a;
/*  19 */   private static final TObjectIntMap classToId = (TObjectIntMap)new TObjectIntHashMap(10, 0.5F, -1); private boolean b = true;
/*  20 */   private final TIntObjectMap dataValues = (TIntObjectMap)new TIntObjectHashMap(10, 0.5F, -1);
/*     */   
/*  22 */   private static final Map c = TDecorators.wrap(classToId);
/*  23 */   private final Map d = TDecorators.wrap(this.dataValues);
/*     */   
/*     */   private boolean e;
/*  26 */   private ReadWriteLock f = new ReentrantReadWriteLock();
/*     */   
/*     */   public DataWatcher(Entity entity) {
/*  29 */     this.a = entity;
/*     */   }
/*     */   
/*     */   public void a(int i, Object object) {
/*  33 */     int integer = classToId.get(object.getClass());
/*     */ 
/*     */     
/*  36 */     if (object instanceof ProtocolData.ByteShort || object instanceof ProtocolData.DualByte || object instanceof ProtocolData.HiddenByte)
/*     */     {
/*     */ 
/*     */       
/*  40 */       integer = classToId.get(Byte.class);
/*     */     }
/*  42 */     if (object instanceof ProtocolData.IntByte || object instanceof ProtocolData.DualInt)
/*     */     {
/*  44 */       integer = classToId.get(Integer.class);
/*     */     }
/*     */ 
/*     */     
/*  48 */     if (integer == -1)
/*  49 */       throw new IllegalArgumentException("Unknown data type: " + object.getClass()); 
/*  50 */     if (i > 31)
/*  51 */       throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + '\037' + ")"); 
/*  52 */     if (this.dataValues.containsKey(i)) {
/*  53 */       throw new IllegalArgumentException("Duplicate id value for " + i + "!");
/*     */     }
/*  55 */     WatchableObject watchableobject = new WatchableObject(integer, i, object);
/*     */     
/*  57 */     this.f.writeLock().lock();
/*  58 */     this.dataValues.put(i, watchableobject);
/*  59 */     this.f.writeLock().unlock();
/*  60 */     this.b = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int i, int j) {
/*  65 */     WatchableObject watchableobject = new WatchableObject(j, i, null);
/*     */     
/*  67 */     this.f.writeLock().lock();
/*  68 */     this.dataValues.put(i, watchableobject);
/*  69 */     this.f.writeLock().unlock();
/*  70 */     this.b = false;
/*     */   }
/*     */   
/*     */   public byte getByte(int i) {
/*  74 */     return ((Number)i(i).b()).byteValue();
/*     */   }
/*     */   
/*     */   public short getShort(int i) {
/*  78 */     return ((Number)i(i).b()).shortValue();
/*     */   }
/*     */   
/*     */   public int getInt(int i) {
/*  82 */     return ((Number)i(i).b()).intValue();
/*     */   }
/*     */   
/*     */   public float getFloat(int i) {
/*  86 */     return ((Number)i(i).b()).floatValue();
/*     */   }
/*     */   
/*     */   public String getString(int i) {
/*  90 */     return (String)i(i).b();
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(int i) {
/*  94 */     return (ItemStack)i(i).b();
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolData.DualByte getDualByte(int i) {
/*  99 */     return (ProtocolData.DualByte)i(i).b();
/*     */   }
/*     */   public ProtocolData.IntByte getIntByte(int i) {
/* 102 */     return (ProtocolData.IntByte)i(i).b();
/*     */   }
/*     */   public ProtocolData.DualInt getDualInt(int i) {
/* 105 */     return (ProtocolData.DualInt)i(i).b();
/*     */   }
/*     */   
/*     */   private WatchableObject i(int i) {
/*     */     WatchableObject watchableobject;
/* 110 */     this.f.readLock().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       watchableobject = (WatchableObject)this.dataValues.get(i);
/* 116 */     } catch (Throwable throwable) {
/* 117 */       CrashReport crashreport = CrashReport.a(throwable, "Getting synched entity data");
/* 118 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Synched entity data");
/*     */       
/* 120 */       crashreportsystemdetails.a("Data ID", Integer.valueOf(i));
/* 121 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 124 */     this.f.readLock().unlock();
/* 125 */     return watchableobject;
/*     */   }
/*     */   
/*     */   public void watch(int i, Object object) {
/* 129 */     WatchableObject watchableobject = i(i);
/*     */     
/* 131 */     if (ObjectUtils.notEqual(object, watchableobject.b())) {
/* 132 */       watchableobject.a(object);
/* 133 */       this.a.i(i);
/* 134 */       watchableobject.a(true);
/* 135 */       this.e = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(int i) {
/* 140 */     WatchableObject.a(i(i), true);
/* 141 */     this.e = true;
/*     */   }
/*     */   
/*     */   public boolean a() {
/* 145 */     return this.e;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a(List list, PacketDataSerializer packetdataserializer) {
/* 150 */     a(list, packetdataserializer, 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a(List list, PacketDataSerializer packetdataserializer, int version) {
/* 155 */     if (list != null) {
/* 156 */       Iterator<WatchableObject> iterator = list.iterator();
/*     */       
/* 158 */       while (iterator.hasNext()) {
/* 159 */         WatchableObject watchableobject = iterator.next();
/*     */         
/* 161 */         a(packetdataserializer, watchableobject, version);
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     packetdataserializer.writeByte(127);
/*     */   }
/*     */   
/*     */   public List b() {
/* 169 */     ArrayList<WatchableObject> arraylist = null;
/*     */     
/* 171 */     if (this.e) {
/* 172 */       this.f.readLock().lock();
/* 173 */       Iterator<WatchableObject> iterator = this.dataValues.valueCollection().iterator();
/*     */       
/* 175 */       while (iterator.hasNext()) {
/* 176 */         WatchableObject watchableobject = iterator.next();
/*     */         
/* 178 */         if (watchableobject.d()) {
/* 179 */           watchableobject.a(false);
/* 180 */           if (arraylist == null) {
/* 181 */             arraylist = new ArrayList();
/*     */           }
/*     */ 
/*     */           
/* 185 */           if (watchableobject.b() instanceof ItemStack)
/*     */           {
/* 187 */             watchableobject = new WatchableObject(watchableobject.c(), watchableobject.a(), ((ItemStack)watchableobject.b()).cloneItemStack());
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 195 */           arraylist.add(watchableobject);
/*     */         } 
/*     */       } 
/*     */       
/* 199 */       this.f.readLock().unlock();
/*     */     } 
/*     */     
/* 202 */     this.e = false;
/* 203 */     return arraylist;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) {
/* 208 */     a(packetdataserializer, 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer, int version) {
/* 213 */     this.f.readLock().lock();
/* 214 */     Iterator<WatchableObject> iterator = this.dataValues.valueCollection().iterator();
/*     */     
/* 216 */     while (iterator.hasNext()) {
/* 217 */       WatchableObject watchableobject = iterator.next();
/*     */       
/* 219 */       a(packetdataserializer, watchableobject, version);
/*     */     } 
/*     */     
/* 222 */     this.f.readLock().unlock();
/* 223 */     packetdataserializer.writeByte(127);
/*     */   }
/*     */   
/*     */   public List c() {
/* 227 */     ArrayList<WatchableObject> arraylist = new ArrayList();
/*     */     
/* 229 */     this.f.readLock().lock();
/*     */     
/* 231 */     arraylist.addAll(this.dataValues.valueCollection());
/*     */     
/* 233 */     for (int i = 0; i < arraylist.size(); i++) {
/*     */       
/* 235 */       WatchableObject watchableobject = arraylist.get(i);
/* 236 */       if (watchableobject.b() instanceof ItemStack) {
/*     */         
/* 238 */         watchableobject = new WatchableObject(watchableobject.c(), watchableobject.a(), ((ItemStack)watchableobject.b()).cloneItemStack());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         arraylist.set(i, watchableobject);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 248 */     this.f.readLock().unlock();
/* 249 */     return arraylist;
/*     */   } private static void a(PacketDataSerializer packetdataserializer, WatchableObject watchableobject, int version) {
/*     */     int val;
/*     */     ItemStack itemstack;
/*     */     ChunkCoordinates chunkcoordinates;
/* 254 */     int type = watchableobject.c();
/* 255 */     if (watchableobject.b() instanceof ProtocolData.ByteShort && version >= 16) {
/* 256 */       type = 1;
/*     */     }
/* 258 */     if (watchableobject.b() instanceof ProtocolData.IntByte && version >= 28) {
/* 259 */       type = 0;
/*     */     }
/* 261 */     if (version < 16 && watchableobject.b() instanceof ProtocolData.HiddenByte)
/*     */       return; 
/* 263 */     int i = (type << 5 | watchableobject.a() & 0x1F) & 0xFF;
/*     */     
/* 265 */     packetdataserializer.writeByte(i);
/* 266 */     switch (type) {
/*     */       case 0:
/* 268 */         if (watchableobject.b() instanceof ProtocolData.DualByte) {
/*     */           
/* 270 */           ProtocolData.DualByte dualByte = (ProtocolData.DualByte)watchableobject.b();
/* 271 */           packetdataserializer.writeByte((version >= 16) ? dualByte.value2 : dualByte.value);
/*     */           break;
/*     */         } 
/* 274 */         packetdataserializer.writeByte(((Number)watchableobject.b()).byteValue());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 279 */         packetdataserializer.writeShort(((Number)watchableobject.b()).shortValue());
/*     */         break;
/*     */       
/*     */       case 2:
/* 283 */         val = ((Number)watchableobject.b()).intValue();
/* 284 */         if (watchableobject.b() instanceof ProtocolData.DualInt && version >= 46) {
/* 285 */           val = ((ProtocolData.DualInt)watchableobject.b()).value2;
/*     */         }
/* 287 */         packetdataserializer.writeInt(val);
/*     */         break;
/*     */       
/*     */       case 3:
/* 291 */         packetdataserializer.writeFloat(((Number)watchableobject.b()).floatValue());
/*     */         break;
/*     */       
/*     */       case 4:
/*     */         try {
/* 296 */           packetdataserializer.a((String)watchableobject.b());
/* 297 */         } catch (IOException ex) {
/* 298 */           throw new RuntimeException(ex);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 5:
/* 303 */         itemstack = (ItemStack)watchableobject.b();
/*     */         
/* 305 */         packetdataserializer.a(itemstack);
/*     */         break;
/*     */       
/*     */       case 6:
/* 309 */         chunkcoordinates = (ChunkCoordinates)watchableobject.b();
/*     */         
/* 311 */         packetdataserializer.writeInt(chunkcoordinates.x);
/* 312 */         packetdataserializer.writeInt(chunkcoordinates.y);
/* 313 */         packetdataserializer.writeInt(chunkcoordinates.z);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List b(PacketDataSerializer packetdataserializer) {
/* 319 */     ArrayList<WatchableObject> arraylist = null;
/*     */     
/* 321 */     for (byte b0 = packetdataserializer.readByte(); b0 != Byte.MAX_VALUE; b0 = packetdataserializer.readByte()) {
/* 322 */       int k, l, i1; if (arraylist == null) {
/* 323 */         arraylist = new ArrayList();
/*     */       }
/*     */       
/* 326 */       int i = (b0 & 0xE0) >> 5;
/* 327 */       int j = b0 & 0x1F;
/* 328 */       WatchableObject watchableobject = null;
/*     */       
/* 330 */       switch (i) {
/*     */         case 0:
/* 332 */           watchableobject = new WatchableObject(i, j, Byte.valueOf(packetdataserializer.readByte()));
/*     */           break;
/*     */         
/*     */         case 1:
/* 336 */           watchableobject = new WatchableObject(i, j, Short.valueOf(packetdataserializer.readShort()));
/*     */           break;
/*     */         
/*     */         case 2:
/* 340 */           watchableobject = new WatchableObject(i, j, Integer.valueOf(packetdataserializer.readInt()));
/*     */           break;
/*     */         
/*     */         case 3:
/* 344 */           watchableobject = new WatchableObject(i, j, Float.valueOf(packetdataserializer.readFloat()));
/*     */           break;
/*     */         
/*     */         case 4:
/*     */           try {
/* 349 */             watchableobject = new WatchableObject(i, j, packetdataserializer.c(32767));
/* 350 */           } catch (IOException ex) {
/* 351 */             throw new RuntimeException(ex);
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 5:
/* 356 */           watchableobject = new WatchableObject(i, j, packetdataserializer.c());
/*     */           break;
/*     */         
/*     */         case 6:
/* 360 */           k = packetdataserializer.readInt();
/* 361 */           l = packetdataserializer.readInt();
/* 362 */           i1 = packetdataserializer.readInt();
/*     */           
/* 364 */           watchableobject = new WatchableObject(i, j, new ChunkCoordinates(k, l, i1));
/*     */           break;
/*     */       } 
/* 367 */       arraylist.add(watchableobject);
/*     */     } 
/*     */     
/* 370 */     return arraylist;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 374 */     return this.b;
/*     */   }
/*     */   
/*     */   public void e() {
/* 378 */     this.e = false;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 383 */     classToId.put(Byte.class, 0);
/* 384 */     classToId.put(Short.class, 1);
/* 385 */     classToId.put(Integer.class, 2);
/* 386 */     classToId.put(Float.class, 3);
/* 387 */     classToId.put(String.class, 4);
/* 388 */     classToId.put(ItemStack.class, 5);
/* 389 */     classToId.put(ChunkCoordinates.class, 6);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DataWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */