/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class PacketPlayOutSpawnEntity
/*     */   extends Packet {
/*     */   private int a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   private int i;
/*     */   private int j;
/*     */   private int k;
/*     */   
/*     */   public PacketPlayOutSpawnEntity() {}
/*     */   
/*     */   public PacketPlayOutSpawnEntity(Entity entity, int i) {
/*  20 */     this(entity, i, 0);
/*     */   }
/*     */   
/*     */   public PacketPlayOutSpawnEntity(Entity entity, int i, int j) {
/*  24 */     this.a = entity.getId();
/*  25 */     this.b = MathHelper.floor(entity.locX * 32.0D);
/*  26 */     this.c = MathHelper.floor(entity.locY * 32.0D);
/*  27 */     this.d = MathHelper.floor(entity.locZ * 32.0D);
/*  28 */     this.h = MathHelper.d(entity.pitch * 256.0F / 360.0F);
/*  29 */     this.i = MathHelper.d(entity.yaw * 256.0F / 360.0F);
/*  30 */     this.j = i;
/*  31 */     this.k = j;
/*  32 */     if (j > 0) {
/*  33 */       double d0 = entity.motX;
/*  34 */       double d1 = entity.motY;
/*  35 */       double d2 = entity.motZ;
/*  36 */       double d3 = 3.9D;
/*     */       
/*  38 */       if (d0 < -d3) {
/*  39 */         d0 = -d3;
/*     */       }
/*     */       
/*  42 */       if (d1 < -d3) {
/*  43 */         d1 = -d3;
/*     */       }
/*     */       
/*  46 */       if (d2 < -d3) {
/*  47 */         d2 = -d3;
/*     */       }
/*     */       
/*  50 */       if (d0 > d3) {
/*  51 */         d0 = d3;
/*     */       }
/*     */       
/*  54 */       if (d1 > d3) {
/*  55 */         d1 = d3;
/*     */       }
/*     */       
/*  58 */       if (d2 > d3) {
/*  59 */         d2 = d3;
/*     */       }
/*     */       
/*  62 */       this.e = (int)(d0 * 8000.0D);
/*  63 */       this.f = (int)(d1 * 8000.0D);
/*  64 */       this.g = (int)(d2 * 8000.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) {
/*  69 */     this.a = packetdataserializer.a();
/*  70 */     this.j = packetdataserializer.readByte();
/*  71 */     this.b = packetdataserializer.readInt();
/*  72 */     this.c = packetdataserializer.readInt();
/*  73 */     this.d = packetdataserializer.readInt();
/*  74 */     this.h = packetdataserializer.readByte();
/*  75 */     this.i = packetdataserializer.readByte();
/*  76 */     this.k = packetdataserializer.readInt();
/*  77 */     if (this.k > 0) {
/*  78 */       this.e = packetdataserializer.readShort();
/*  79 */       this.f = packetdataserializer.readShort();
/*  80 */       this.g = packetdataserializer.readShort();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) {
/*  85 */     packetdataserializer.b(this.a);
/*  86 */     packetdataserializer.writeByte(this.j);
/*     */     
/*  88 */     if (this.j == 71 && packetdataserializer.version >= 28)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       switch (this.k) {
/*     */         case 0:
/*  96 */           this.d += 32;
/*  97 */           this.i = 0;
/*     */           break;
/*     */         case 1:
/* 100 */           this.b -= 32;
/* 101 */           this.i = 64;
/*     */           break;
/*     */         case 2:
/* 104 */           this.d -= 32;
/* 105 */           this.i = 128;
/*     */           break;
/*     */         case 3:
/* 108 */           this.b += 32;
/* 109 */           this.i = 192;
/*     */           break;
/*     */       } 
/*     */     }
/* 113 */     if (this.j == 70 && packetdataserializer.version >= 36) {
/*     */       
/* 115 */       int id = this.k & 0xFFFF;
/* 116 */       int data = this.k >> 16;
/* 117 */       this.k = id | data << 12;
/*     */     } 
/* 119 */     if ((this.j == 50 || this.j == 70 || this.j == 74) && packetdataserializer.version >= 16) {
/* 120 */       this.c -= 16;
/*     */     }
/*     */     
/* 123 */     packetdataserializer.writeInt(this.b);
/* 124 */     packetdataserializer.writeInt(this.c);
/* 125 */     packetdataserializer.writeInt(this.d);
/* 126 */     packetdataserializer.writeByte(this.h);
/* 127 */     packetdataserializer.writeByte(this.i);
/* 128 */     packetdataserializer.writeInt(this.k);
/* 129 */     if (this.k > 0) {
/* 130 */       packetdataserializer.writeShort(this.e);
/* 131 */       packetdataserializer.writeShort(this.f);
/* 132 */       packetdataserializer.writeShort(this.g);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 137 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public String b() {
/* 141 */     return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.j), Float.valueOf(this.b / 32.0F), Float.valueOf(this.c / 32.0F), Float.valueOf(this.d / 32.0F) });
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 145 */     this.b = i;
/*     */   }
/*     */   
/*     */   public void b(int i) {
/* 149 */     this.c = i;
/*     */   }
/*     */   
/*     */   public void c(int i) {
/* 153 */     this.d = i;
/*     */   }
/*     */   
/*     */   public void d(int i) {
/* 157 */     this.e = i;
/*     */   }
/*     */   
/*     */   public void e(int i) {
/* 161 */     this.f = i;
/*     */   }
/*     */   
/*     */   public void f(int i) {
/* 165 */     this.g = i;
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 169 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSpawnEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */