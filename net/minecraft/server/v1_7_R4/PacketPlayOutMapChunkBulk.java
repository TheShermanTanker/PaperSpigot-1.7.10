/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ public class PacketPlayOutMapChunkBulk
/*     */   extends Packet {
/*     */   private int[] a;
/*     */   private int[] b;
/*     */   private int[] c;
/*     */   private int[] d;
/*     */   private byte[] buffer;
/*     */   private byte[][] inflatedBuffers;
/*     */   private int size;
/*     */   private boolean h;
/*  19 */   private byte[] buildBuffer = new byte[0];
/*     */   
/*  21 */   static final ThreadLocal<Deflater> localDeflater = new ThreadLocal<Deflater>()
/*     */     {
/*     */       protected Deflater initialValue()
/*     */       {
/*  25 */         return new Deflater(4);
/*     */       }
/*     */     };
/*     */   
/*     */   private World world;
/*     */   
/*     */   public PacketPlayOutMapChunkBulk() {}
/*     */   
/*     */   public PacketPlayOutMapChunkBulk(List<Chunk> list, int version) {
/*  34 */     int i = list.size();
/*     */     
/*  36 */     this.a = new int[i];
/*  37 */     this.b = new int[i];
/*  38 */     this.c = new int[i];
/*  39 */     this.d = new int[i];
/*  40 */     this.inflatedBuffers = new byte[i][];
/*  41 */     this.h = (!list.isEmpty() && !((Chunk)list.get(0)).world.worldProvider.g);
/*  42 */     int j = 0;
/*     */     
/*  44 */     for (int k = 0; k < i; k++) {
/*  45 */       Chunk chunk = list.get(k);
/*  46 */       ChunkMap chunkmap = chunk.getChunkMap(true, 65535, version);
/*     */ 
/*     */       
/*  49 */       this.world = chunk.world;
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
/*  61 */       j += chunkmap.a.length;
/*  62 */       this.a[k] = chunk.locX;
/*  63 */       this.b[k] = chunk.locZ;
/*  64 */       this.c[k] = chunkmap.b;
/*  65 */       this.d[k] = chunkmap.c;
/*  66 */       this.inflatedBuffers[k] = chunkmap.a;
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public void compress() {
/*  85 */     if (this.buffer != null) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     int finalBufferSize = 0;
/*     */     
/*  91 */     for (int i = 0; i < this.a.length; i++) {
/*  92 */       this.world.spigotConfig.antiXrayInstance.obfuscate(this.a[i], this.b[i], this.c[i], this.inflatedBuffers[i], this.world, false);
/*  93 */       finalBufferSize += (this.inflatedBuffers[i]).length;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     this.buildBuffer = new byte[finalBufferSize];
/*  98 */     int bufferLocation = 0;
/*  99 */     for (int j = 0; j < this.a.length; j++) {
/* 100 */       System.arraycopy(this.inflatedBuffers[j], 0, this.buildBuffer, bufferLocation, (this.inflatedBuffers[j]).length);
/* 101 */       bufferLocation += (this.inflatedBuffers[j]).length;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     Deflater deflater = localDeflater.get();
/* 106 */     deflater.reset();
/* 107 */     deflater.setInput(this.buildBuffer);
/* 108 */     deflater.finish();
/*     */     
/* 110 */     this.buffer = new byte[this.buildBuffer.length + 100];
/* 111 */     this.size = deflater.deflate(this.buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int c() {
/* 116 */     return 5;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 120 */     short short1 = packetdataserializer.readShort();
/*     */     
/* 122 */     this.size = packetdataserializer.readInt();
/* 123 */     this.h = packetdataserializer.readBoolean();
/* 124 */     this.a = new int[short1];
/* 125 */     this.b = new int[short1];
/* 126 */     this.c = new int[short1];
/* 127 */     this.d = new int[short1];
/* 128 */     this.inflatedBuffers = new byte[short1][];
/* 129 */     if (this.buildBuffer.length < this.size) {
/* 130 */       this.buildBuffer = new byte[this.size];
/*     */     }
/*     */     
/* 133 */     packetdataserializer.readBytes(this.buildBuffer, 0, this.size);
/* 134 */     byte[] abyte = new byte[PacketPlayOutMapChunk.c() * short1];
/* 135 */     Inflater inflater = new Inflater();
/*     */     
/* 137 */     inflater.setInput(this.buildBuffer, 0, this.size);
/*     */     
/*     */     try {
/* 140 */       inflater.inflate(abyte);
/* 141 */     } catch (DataFormatException dataformatexception) {
/* 142 */       throw new IOException("Bad compressed data format");
/*     */     } finally {
/* 144 */       inflater.end();
/*     */     } 
/*     */     
/* 147 */     int i = 0;
/*     */     
/* 149 */     for (int j = 0; j < short1; j++) {
/* 150 */       this.a[j] = packetdataserializer.readInt();
/* 151 */       this.b[j] = packetdataserializer.readInt();
/* 152 */       this.c[j] = packetdataserializer.readShort();
/* 153 */       this.d[j] = packetdataserializer.readShort();
/* 154 */       int k = 0;
/* 155 */       int l = 0;
/*     */       
/*     */       int i1;
/*     */       
/* 159 */       for (i1 = 0; i1 < 16; i1++) {
/* 160 */         k += this.c[j] >> i1 & 0x1;
/* 161 */         l += this.d[j] >> i1 & 0x1;
/*     */       } 
/*     */       
/* 164 */       i1 = 8192 * k + 256;
/* 165 */       i1 += 2048 * l;
/* 166 */       if (this.h) {
/* 167 */         i1 += 2048 * k;
/*     */       }
/*     */       
/* 170 */       this.inflatedBuffers[j] = new byte[i1];
/* 171 */       System.arraycopy(abyte, i, this.inflatedBuffers[j], 0, i1);
/* 172 */       i += i1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 177 */     if (packetdataserializer.version < 27) {
/*     */       
/* 179 */       compress();
/* 180 */       packetdataserializer.writeShort(this.a.length);
/* 181 */       packetdataserializer.writeInt(this.size);
/* 182 */       packetdataserializer.writeBoolean(this.h);
/* 183 */       packetdataserializer.writeBytes(this.buffer, 0, this.size);
/*     */       
/* 185 */       for (int i = 0; i < this.a.length; i++) {
/* 186 */         packetdataserializer.writeInt(this.a[i]);
/* 187 */         packetdataserializer.writeInt(this.b[i]);
/* 188 */         packetdataserializer.writeShort((short)(this.c[i] & 0xFFFF));
/* 189 */         packetdataserializer.writeShort((short)(this.d[i] & 0xFFFF));
/*     */       } 
/*     */     } else {
/*     */       
/* 193 */       packetdataserializer.writeBoolean(this.h);
/* 194 */       packetdataserializer.b(this.a.length);
/*     */       int i;
/* 196 */       for (i = 0; i < this.a.length; i++) {
/* 197 */         packetdataserializer.writeInt(this.a[i]);
/* 198 */         packetdataserializer.writeInt(this.b[i]);
/* 199 */         packetdataserializer.writeShort((short)(this.c[i] & 0xFFFF));
/*     */       } 
/* 201 */       for (i = 0; i < this.a.length; i++) {
/* 202 */         this.world.spigotConfig.antiXrayInstance.obfuscate(this.a[i], this.b[i], this.c[i], this.inflatedBuffers[i], this.world, true);
/* 203 */         packetdataserializer.writeBytes(this.inflatedBuffers[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 209 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public String b() {
/* 213 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 215 */     for (int i = 0; i < this.a.length; i++) {
/* 216 */       if (i > 0) {
/* 217 */         stringbuilder.append(", ");
/*     */       }
/*     */       
/* 220 */       stringbuilder.append(String.format("{x=%d, z=%d, sections=%d, adds=%d, data=%d}", new Object[] { Integer.valueOf(this.a[i]), Integer.valueOf(this.b[i]), Integer.valueOf(this.c[i]), Integer.valueOf(this.d[i]), Integer.valueOf((this.inflatedBuffers[i]).length) }));
/*     */     } 
/*     */     
/* 223 */     return String.format("size=%d, chunks=%d[%s]", new Object[] { Integer.valueOf(this.size), Integer.valueOf(this.a.length), stringbuilder });
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 227 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutMapChunkBulk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */