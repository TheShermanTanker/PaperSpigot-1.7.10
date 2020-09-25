/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ public class RegionFile
/*     */ {
/*  17 */   private static final byte[] a = new byte[4096];
/*     */   private final File b;
/*     */   private RandomAccessFile c;
/*  20 */   private final int[] d = new int[1024];
/*  21 */   private final int[] e = new int[1024];
/*     */   private ArrayList f;
/*     */   private int g;
/*     */   private long h;
/*     */   
/*     */   public RegionFile(File file1) {
/*  27 */     this.b = file1;
/*  28 */     this.g = 0;
/*     */     
/*     */     try {
/*  31 */       if (file1.exists()) {
/*  32 */         this.h = file1.lastModified();
/*     */       }
/*     */       
/*  35 */       this.c = new RandomAccessFile(file1, "rw");
/*     */ 
/*     */       
/*  38 */       if (this.c.length() < 4096L) {
/*  39 */         int k; for (k = 0; k < 1024; k++) {
/*  40 */           this.c.writeInt(0);
/*     */         }
/*     */         
/*  43 */         for (k = 0; k < 1024; k++) {
/*  44 */           this.c.writeInt(0);
/*     */         }
/*     */         
/*  47 */         this.g += 8192;
/*     */       } 
/*     */       
/*  50 */       if ((this.c.length() & 0xFFFL) != 0L) {
/*  51 */         for (int k = 0; k < (this.c.length() & 0xFFFL); k++) {
/*  52 */           this.c.write(0);
/*     */         }
/*     */       }
/*     */       
/*  56 */       int i = (int)this.c.length() / 4096;
/*  57 */       this.f = new ArrayList(i);
/*     */       
/*     */       int j;
/*     */       
/*  61 */       for (j = 0; j < i; j++) {
/*  62 */         this.f.add(Boolean.valueOf(true));
/*     */       }
/*     */       
/*  65 */       this.f.set(0, Boolean.valueOf(false));
/*  66 */       this.f.set(1, Boolean.valueOf(false));
/*  67 */       this.c.seek(0L);
/*     */ 
/*     */ 
/*     */       
/*  71 */       for (j = 0; j < 1024; j++) {
/*  72 */         int k = this.c.readInt();
/*  73 */         this.d[j] = k;
/*  74 */         if (k != 0 && (k >> 8) + (k & 0xFF) <= this.f.size()) {
/*  75 */           for (int l = 0; l < (k & 0xFF); l++) {
/*  76 */             this.f.set((k >> 8) + l, Boolean.valueOf(false));
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/*  81 */       for (j = 0; j < 1024; j++) {
/*  82 */         int k = this.c.readInt();
/*  83 */         this.e[j] = k;
/*     */       } 
/*  85 */     } catch (IOException ioexception) {
/*  86 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean chunkExists(int i, int j) {
/*  92 */     if (d(i, j)) {
/*  93 */       return false;
/*     */     }
/*     */     try {
/*  96 */       int k = e(i, j);
/*     */       
/*  98 */       if (k == 0) {
/*  99 */         return false;
/*     */       }
/* 101 */       int l = k >> 8;
/* 102 */       int i1 = k & 0xFF;
/*     */       
/* 104 */       if (l + i1 > this.f.size()) {
/* 105 */         return false;
/*     */       }
/*     */       
/* 108 */       this.c.seek((l * 4096));
/* 109 */       int j1 = this.c.readInt();
/*     */       
/* 111 */       if (j1 > 4096 * i1 || j1 <= 0) {
/* 112 */         return false;
/*     */       }
/*     */       
/* 115 */       byte b0 = this.c.readByte();
/* 116 */       if (b0 == 1 || b0 == 2) {
/* 117 */         return true;
/*     */       }
/*     */     }
/* 120 */     catch (IOException ioexception) {
/* 121 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized DataInputStream a(int i, int j) {
/* 130 */     if (d(i, j)) {
/* 131 */       return null;
/*     */     }
/*     */     try {
/* 134 */       int k = e(i, j);
/*     */       
/* 136 */       if (k == 0) {
/* 137 */         return null;
/*     */       }
/* 139 */       int l = k >> 8;
/* 140 */       int i1 = k & 0xFF;
/*     */       
/* 142 */       if (l + i1 > this.f.size()) {
/* 143 */         return null;
/*     */       }
/* 145 */       this.c.seek((l * 4096));
/* 146 */       int j1 = this.c.readInt();
/*     */       
/* 148 */       if (j1 > 4096 * i1)
/* 149 */         return null; 
/* 150 */       if (j1 <= 0) {
/* 151 */         return null;
/*     */       }
/* 153 */       byte b0 = this.c.readByte();
/*     */ 
/*     */       
/* 156 */       if (b0 == 1) {
/* 157 */         byte[] abyte = new byte[j1 - 1];
/* 158 */         this.c.read(abyte);
/* 159 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte))));
/* 160 */       }  if (b0 == 2) {
/* 161 */         byte[] abyte = new byte[j1 - 1];
/* 162 */         this.c.read(abyte);
/* 163 */         return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
/*     */       } 
/* 165 */       return null;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 170 */     catch (IOException ioexception) {
/* 171 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DataOutputStream b(int i, int j) {
/* 177 */     return d(i, j) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(this, i, j)));
/*     */   }
/*     */   
/*     */   protected synchronized void a(int i, int j, byte[] abyte, int k) {
/*     */     try {
/* 182 */       int l = e(i, j);
/* 183 */       int i1 = l >> 8;
/* 184 */       int j1 = l & 0xFF;
/* 185 */       int k1 = (k + 5) / 4096 + 1;
/*     */       
/* 187 */       if (k1 >= 256) {
/*     */         return;
/*     */       }
/*     */       
/* 191 */       if (i1 != 0 && j1 == k1) {
/* 192 */         a(i1, abyte, k);
/*     */       } else {
/*     */         int l1;
/*     */         
/* 196 */         for (l1 = 0; l1 < j1; l1++) {
/* 197 */           this.f.set(i1 + l1, Boolean.valueOf(true));
/*     */         }
/*     */         
/* 200 */         l1 = this.f.indexOf(Boolean.valueOf(true));
/* 201 */         int i2 = 0;
/*     */ 
/*     */         
/* 204 */         if (l1 != -1) {
/* 205 */           for (int j2 = l1; j2 < this.f.size(); j2++) {
/* 206 */             if (i2 != 0) {
/* 207 */               if (((Boolean)this.f.get(j2)).booleanValue()) {
/* 208 */                 i2++;
/*     */               } else {
/* 210 */                 i2 = 0;
/*     */               } 
/* 212 */             } else if (((Boolean)this.f.get(j2)).booleanValue()) {
/* 213 */               l1 = j2;
/* 214 */               i2 = 1;
/*     */             } 
/*     */             
/* 217 */             if (i2 >= k1) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 223 */         if (i2 >= k1) {
/* 224 */           i1 = l1;
/* 225 */           a(i, j, l1 << 8 | k1);
/*     */           
/* 227 */           for (int j2 = 0; j2 < k1; j2++) {
/* 228 */             this.f.set(i1 + j2, Boolean.valueOf(false));
/*     */           }
/*     */           
/* 231 */           a(i1, abyte, k);
/*     */         } else {
/* 233 */           this.c.seek(this.c.length());
/* 234 */           i1 = this.f.size();
/*     */           
/* 236 */           for (int j2 = 0; j2 < k1; j2++) {
/* 237 */             this.c.write(a);
/* 238 */             this.f.add(Boolean.valueOf(false));
/*     */           } 
/*     */           
/* 241 */           this.g += 4096 * k1;
/* 242 */           a(i1, abyte, k);
/* 243 */           a(i, j, i1 << 8 | k1);
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       b(i, j, (int)(MinecraftServer.ar() / 1000L));
/* 248 */     } catch (IOException ioexception) {
/* 249 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(int i, byte[] abyte, int j) throws IOException {
/* 254 */     this.c.seek((i * 4096));
/* 255 */     this.c.writeInt(j + 1);
/* 256 */     this.c.writeByte(2);
/* 257 */     this.c.write(abyte, 0, j);
/*     */   }
/*     */   
/*     */   private boolean d(int i, int j) {
/* 261 */     return (i < 0 || i >= 32 || j < 0 || j >= 32);
/*     */   }
/*     */   
/*     */   private int e(int i, int j) {
/* 265 */     return this.d[i + j * 32];
/*     */   }
/*     */   
/*     */   public boolean c(int i, int j) {
/* 269 */     return (e(i, j) != 0);
/*     */   }
/*     */   
/*     */   private void a(int i, int j, int k) throws IOException {
/* 273 */     this.d[i + j * 32] = k;
/* 274 */     this.c.seek(((i + j * 32) * 4));
/* 275 */     this.c.writeInt(k);
/*     */   }
/*     */   
/*     */   private void b(int i, int j, int k) throws IOException {
/* 279 */     this.e[i + j * 32] = k;
/* 280 */     this.c.seek((4096 + (i + j * 32) * 4));
/* 281 */     this.c.writeInt(k);
/*     */   }
/*     */   
/*     */   public void c() throws IOException {
/* 285 */     if (this.c != null)
/* 286 */       this.c.close(); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegionFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */