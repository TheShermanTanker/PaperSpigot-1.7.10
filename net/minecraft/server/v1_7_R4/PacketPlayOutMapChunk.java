/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.Inflater;
/*     */ import org.spigotmc.SpigotDebreakifier;
/*     */ 
/*     */ public class PacketPlayOutMapChunk extends Packet {
/*     */   private int a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int d;
/*     */   private byte[] e;
/*     */   private byte[] f;
/*     */   private boolean g;
/*     */   private int h;
/*  18 */   private static byte[] i = new byte[196864];
/*     */   
/*     */   private Chunk chunk;
/*     */   
/*     */   private int mask;
/*     */   
/*     */   public PacketPlayOutMapChunk() {}
/*     */   
/*     */   public PacketPlayOutMapChunk(Chunk chunk, boolean flag, int i, int version) {
/*  27 */     this.chunk = chunk;
/*  28 */     this.mask = i;
/*  29 */     this.a = chunk.locX;
/*  30 */     this.b = chunk.locZ;
/*  31 */     this.g = flag;
/*  32 */     ChunkMap chunkmap = chunk.getChunkMap(flag, i, version);
/*     */     
/*  34 */     this.d = chunkmap.c;
/*  35 */     this.c = chunkmap.b;
/*     */     
/*  37 */     this.f = chunkmap.a;
/*     */   }
/*     */   
/*     */   public static int c() {
/*  41 */     return 196864;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/*  45 */     this.a = packetdataserializer.readInt();
/*  46 */     this.b = packetdataserializer.readInt();
/*  47 */     this.g = packetdataserializer.readBoolean();
/*  48 */     this.c = packetdataserializer.readShort();
/*  49 */     this.d = packetdataserializer.readShort();
/*  50 */     this.h = packetdataserializer.readInt();
/*  51 */     if (PacketPlayOutMapChunk.i.length < this.h) {
/*  52 */       PacketPlayOutMapChunk.i = new byte[this.h];
/*     */     }
/*     */     
/*  55 */     packetdataserializer.readBytes(PacketPlayOutMapChunk.i, 0, this.h);
/*  56 */     int i = 0;
/*     */     
/*     */     int j;
/*     */     
/*  60 */     for (j = 0; j < 16; j++) {
/*  61 */       i += this.c >> j & 0x1;
/*     */     }
/*     */     
/*  64 */     j = 12288 * i;
/*  65 */     if (this.g) {
/*  66 */       j += 256;
/*     */     }
/*     */     
/*  69 */     this.f = new byte[j];
/*  70 */     Inflater inflater = new Inflater();
/*     */     
/*  72 */     inflater.setInput(PacketPlayOutMapChunk.i, 0, this.h);
/*     */     
/*     */     try {
/*  75 */       inflater.inflate(this.f);
/*  76 */     } catch (DataFormatException dataformatexception) {
/*  77 */       throw new IOException("Bad compressed data format");
/*     */     } finally {
/*  79 */       inflater.end();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) {
/*  84 */     packetdataserializer.writeInt(this.a);
/*  85 */     packetdataserializer.writeInt(this.b);
/*  86 */     packetdataserializer.writeBoolean(this.g);
/*  87 */     packetdataserializer.writeShort((short)(this.c & 0xFFFF));
/*     */     
/*  89 */     if (packetdataserializer.version < 27) {
/*     */       
/*  91 */       this.chunk.world.spigotConfig.antiXrayInstance.obfuscate(this.chunk.locX, this.chunk.locZ, this.mask, this.f, this.chunk.world, false);
/*  92 */       Deflater deflater = new Deflater(4);
/*     */       try {
/*  94 */         deflater.setInput(this.f, 0, this.f.length);
/*  95 */         deflater.finish();
/*  96 */         this.e = new byte[this.f.length];
/*  97 */         this.h = deflater.deflate(this.e);
/*     */       } finally {
/*  99 */         deflater.end();
/*     */       } 
/* 101 */       packetdataserializer.writeShort((short)(this.d & 0xFFFF));
/* 102 */       packetdataserializer.writeInt(this.h);
/* 103 */       packetdataserializer.writeBytes(this.e, 0, this.h);
/*     */     } else {
/*     */       
/* 106 */       this.chunk.world.spigotConfig.antiXrayInstance.obfuscate(this.chunk.locX, this.chunk.locZ, this.mask, this.f, this.chunk.world, true);
/* 107 */       a(packetdataserializer, this.f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 113 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public String b() {
/* 117 */     return String.format("x=%d, z=%d, full=%b, sects=%d, add=%d, size=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b), Boolean.valueOf(this.g), Integer.valueOf(this.c), Integer.valueOf(this.d), Integer.valueOf(this.h) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChunkMap a(Chunk chunk, boolean flag, int i, int version) {
/* 122 */     int j = 0;
/* 123 */     ChunkSection[] achunksection = chunk.getSections();
/* 124 */     int k = 0;
/* 125 */     ChunkMap chunkmap = new ChunkMap();
/* 126 */     byte[] abyte = PacketPlayOutMapChunk.i;
/*     */     
/* 128 */     if (flag) {
/* 129 */       chunk.q = true;
/*     */     }
/*     */     
/*     */     int l;
/*     */     
/* 134 */     for (l = 0; l < achunksection.length; l++) {
/* 135 */       if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0) {
/* 136 */         chunkmap.b |= 1 << l;
/* 137 */         if (achunksection[l].getExtendedIdArray() != null) {
/* 138 */           chunkmap.c |= 1 << l;
/* 139 */           k++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     if (version < 24) {
/*     */       
/* 146 */       for (l = 0; l < achunksection.length; l++) {
/*     */         
/* 148 */         if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0) {
/*     */           
/* 150 */           byte[] abyte1 = achunksection[l].getIdArray();
/*     */           
/* 152 */           System.arraycopy(abyte1, 0, abyte, j, abyte1.length);
/* 153 */           j += abyte1.length;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 157 */       for (l = 0; l < achunksection.length; l++) {
/*     */         
/* 159 */         if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0) {
/*     */           
/* 161 */           byte[] abyte1 = achunksection[l].getIdArray();
/* 162 */           NibbleArray nibblearray = achunksection[l].getDataArray();
/* 163 */           for (int ind = 0; ind < abyte1.length; ind++) {
/*     */             
/* 165 */             int id = abyte1[ind] & 0xFF;
/* 166 */             int px = ind & 0xF;
/* 167 */             int py = ind >> 8 & 0xF;
/* 168 */             int pz = ind >> 4 & 0xF;
/* 169 */             int data = nibblearray.a(px, py, pz);
/* 170 */             if (id == 90 && data == 0) {
/*     */               
/* 172 */               Blocks.PORTAL.updateShape(chunk.world, (chunk.locX << 4) + px, (l << 4) + py, (chunk.locZ << 4) + pz);
/*     */             } else {
/*     */               
/* 175 */               data = SpigotDebreakifier.getCorrectedData(id, data);
/*     */             } 
/* 177 */             char val = (char)(id << 4 | data);
/* 178 */             abyte[j++] = (byte)(val & 0xFF);
/* 179 */             abyte[j++] = (byte)(val >> 8 & 0xFF);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (version < 24)
/*     */     {
/* 189 */       for (l = 0; l < achunksection.length; l++) {
/*     */         
/* 191 */         if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0) {
/*     */           
/* 193 */           NibbleArray nibblearray = achunksection[l].getDataArray();
/* 194 */           System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
/* 195 */           j += nibblearray.a.length;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 200 */     for (l = 0; l < achunksection.length; l++) {
/* 201 */       if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0) {
/* 202 */         NibbleArray nibblearray = achunksection[l].getEmittedLightArray();
/* 203 */         System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
/* 204 */         j += nibblearray.a.length;
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     if (!chunk.world.worldProvider.g) {
/* 209 */       for (l = 0; l < achunksection.length; l++) {
/* 210 */         if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0) {
/* 211 */           NibbleArray nibblearray = achunksection[l].getSkyLightArray();
/* 212 */           System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
/* 213 */           j += nibblearray.a.length;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 218 */     if (k > 0 && version < 24) {
/* 219 */       for (l = 0; l < achunksection.length; l++) {
/* 220 */         if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && achunksection[l].getExtendedIdArray() != null && (i & 1 << l) != 0) {
/* 221 */           NibbleArray nibblearray = achunksection[l].getExtendedIdArray();
/* 222 */           System.arraycopy(nibblearray.a, 0, abyte, j, nibblearray.a.length);
/* 223 */           j += nibblearray.a.length;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 228 */     if (flag) {
/* 229 */       byte[] abyte2 = chunk.m();
/*     */       
/* 231 */       System.arraycopy(abyte2, 0, abyte, j, abyte2.length);
/* 232 */       j += abyte2.length;
/*     */     } 
/*     */     
/* 235 */     chunkmap.a = new byte[j];
/* 236 */     System.arraycopy(abyte, 0, chunkmap.a, 0, j);
/* 237 */     return chunkmap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 243 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutMapChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */