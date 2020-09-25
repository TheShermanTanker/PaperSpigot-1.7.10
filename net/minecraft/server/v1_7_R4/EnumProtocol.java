/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.google.common.collect.BiMap;
/*     */ import net.minecraft.util.com.google.common.collect.HashBiMap;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.gnu.trove.map.TIntObjectMap;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TIntObjectHashMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EnumProtocol
/*     */ {
/*  22 */   HANDSHAKING(-1),
/*     */ 
/*     */   
/*  25 */   PLAY(0),
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
/*     */   
/* 117 */   STATUS(1),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   LOGIN(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 132 */     e = (TIntObjectMap)new TIntObjectHashMap();
/* 133 */     f = Maps.newHashMap();
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
/* 193 */     for (EnumProtocol enumProtocol : values()) {
/* 194 */       e.put(enumProtocol.c(), enumProtocol);
/*     */       
/* 196 */       for (Class<?> clazz : (Iterable<Class<?>>)Iterables.concat(enumProtocol.b().values(), enumProtocol.a().values())) {
/* 197 */         if (f.containsKey(clazz) && f.get(clazz) != enumProtocol) {
/* 198 */           throw new Error("Packet " + clazz + " is already assigned to protocol " + f.get(clazz) + " - can't reassign to " + enumProtocol);
/*     */         }
/*     */         
/* 201 */         f.put(clazz, enumProtocol);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private final BiMap h = (BiMap)HashBiMap.create(); private final BiMap i = (BiMap)HashBiMap.create(); private static final TIntObjectMap e; private static final Map f; private final int g; EnumProtocol(int paramInt1) {
/*     */     this.g = paramInt1;
/* 207 */   } public static EnumProtocol a(int paramInt) { return (EnumProtocol)e.get(paramInt); }
/*     */   protected EnumProtocol a(int paramInt, Class paramClass) { if (this.h.containsKey(Integer.valueOf(paramInt))) { String str = "Serverbound packet ID " + paramInt + " is already assigned to " + this.h.get(Integer.valueOf(paramInt)) + "; cannot re-assign to " + paramClass; LogManager.getLogger().fatal(str); throw new IllegalArgumentException(str); }  if (this.h.containsValue(paramClass)) { String str = "Serverbound packet " + paramClass + " is already assigned to ID " + this.h.inverse().get(paramClass) + "; cannot re-assign to " + paramInt; LogManager.getLogger().fatal(str); throw new IllegalArgumentException(str); }  this.h.put(Integer.valueOf(paramInt), paramClass); return this; }
/*     */   protected EnumProtocol b(int paramInt, Class paramClass) { if (this.i.containsKey(Integer.valueOf(paramInt))) { String str = "Clientbound packet ID " + paramInt + " is already assigned to " + this.i.get(Integer.valueOf(paramInt)) + "; cannot re-assign to " + paramClass; LogManager.getLogger().fatal(str); throw new IllegalArgumentException(str); }  if (this.i.containsValue(paramClass)) { String str = "Clientbound packet " + paramClass + " is already assigned to ID " + this.i.inverse().get(paramClass) + "; cannot re-assign to " + paramInt; LogManager.getLogger().fatal(str); throw new IllegalArgumentException(str); }  this.i.put(Integer.valueOf(paramInt), paramClass); return this; }
/*     */   public BiMap a() { return this.h; }
/* 211 */   public BiMap b() { return this.i; } public BiMap a(boolean paramBoolean) { return paramBoolean ? b() : a(); } public BiMap b(boolean paramBoolean) { return paramBoolean ? a() : b(); } public int c() { return this.g; } public static EnumProtocol a(Packet paramPacket) { return (EnumProtocol)f.get(paramPacket.getClass()); }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumProtocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */