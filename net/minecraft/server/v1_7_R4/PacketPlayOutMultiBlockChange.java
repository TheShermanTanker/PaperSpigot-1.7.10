/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotDebreakifier;
/*     */ 
/*     */ public class PacketPlayOutMultiBlockChange
/*     */   extends Packet {
/*  12 */   private static final Logger a = LogManager.getLogger();
/*     */   
/*     */   private ChunkCoordIntPair b;
/*     */   
/*     */   private byte[] c;
/*     */   
/*     */   private int d;
/*     */   
/*     */   private short[] ashort;
/*     */   private int[] blocks;
/*     */   private Chunk chunk;
/*     */   
/*     */   public PacketPlayOutMultiBlockChange() {}
/*     */   
/*     */   public PacketPlayOutMultiBlockChange(int i, short[] ashort, Chunk chunk) {
/*  27 */     this.ashort = new short[ashort.length];
/*  28 */     System.arraycopy(ashort, 0, this.ashort, 0, ashort.length);
/*     */     
/*  30 */     this.chunk = chunk;
/*     */     
/*  32 */     this.b = new ChunkCoordIntPair(chunk.locX, chunk.locZ);
/*  33 */     this.d = i;
/*  34 */     int j = 4 * i;
/*     */ 
/*     */     
/*     */     try {
/*  38 */       ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(j);
/*  39 */       DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
/*     */ 
/*     */       
/*  42 */       this.blocks = new int[i];
/*  43 */       for (int k = 0; k < i; k++) {
/*  44 */         int l = ashort[k] >> 12 & 0xF;
/*  45 */         int i1 = ashort[k] >> 8 & 0xF;
/*  46 */         int j1 = ashort[k] & 0xFF;
/*     */         
/*  48 */         dataoutputstream.writeShort(ashort[k]);
/*  49 */         int blockId = Block.getId(chunk.getType(l, j1, i1));
/*  50 */         int data = chunk.getData(l, j1, i1);
/*  51 */         data = SpigotDebreakifier.getCorrectedData(blockId, data);
/*  52 */         int id = (blockId & 0xFFF) << 4 | data & 0xF;
/*  53 */         dataoutputstream.writeShort((short)id);
/*  54 */         this.blocks[k] = id;
/*     */       } 
/*     */ 
/*     */       
/*  58 */       this.c = bytearrayoutputstream.toByteArray();
/*  59 */       if (this.c.length != j) {
/*  60 */         throw new RuntimeException("Expected length " + j + " doesn't match received length " + this.c.length);
/*     */       }
/*  62 */     } catch (IOException ioexception) {
/*  63 */       a.error("Couldn't create bulk block update packet", ioexception);
/*  64 */       this.c = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) {
/*  69 */     this.b = new ChunkCoordIntPair(packetdataserializer.readInt(), packetdataserializer.readInt());
/*  70 */     this.d = packetdataserializer.readShort() & 0xFFFF;
/*  71 */     int i = packetdataserializer.readInt();
/*     */     
/*  73 */     if (i > 0) {
/*  74 */       this.c = new byte[i];
/*  75 */       packetdataserializer.readBytes(this.c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) {
/*  81 */     if (packetdataserializer.version < 25) {
/*     */       
/*  83 */       packetdataserializer.writeInt(this.b.x);
/*  84 */       packetdataserializer.writeInt(this.b.z);
/*  85 */       packetdataserializer.writeShort((short)this.d);
/*  86 */       if (this.c != null) {
/*     */         
/*  88 */         packetdataserializer.writeInt(this.c.length);
/*  89 */         packetdataserializer.writeBytes(this.c);
/*     */       } else {
/*     */         
/*  92 */         packetdataserializer.writeInt(0);
/*     */       } 
/*     */     } else {
/*  95 */       packetdataserializer.writeInt(this.b.x);
/*  96 */       packetdataserializer.writeInt(this.b.z);
/*  97 */       packetdataserializer.b(this.d);
/*  98 */       for (int i = 0; i < this.d; i++) {
/*     */         
/* 100 */         packetdataserializer.writeShort(this.ashort[i]);
/* 101 */         packetdataserializer.b(this.blocks[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 108 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public String b() {
/* 112 */     return String.format("xc=%d, zc=%d, count=%d", new Object[] { Integer.valueOf(this.b.x), Integer.valueOf(this.b.z), Integer.valueOf(this.d) });
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 116 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutMultiBlockChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */