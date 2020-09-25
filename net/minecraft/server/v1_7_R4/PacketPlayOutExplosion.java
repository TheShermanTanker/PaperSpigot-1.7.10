/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutExplosion
/*    */   extends Packet
/*    */ {
/*    */   private double a;
/*    */   private double b;
/*    */   private double c;
/*    */   private float d;
/*    */   private List e;
/*    */   private float f;
/*    */   private float g;
/*    */   private float h;
/*    */   
/*    */   public PacketPlayOutExplosion() {}
/*    */   
/*    */   public PacketPlayOutExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, List<?> paramList, Vec3D paramVec3D) {
/* 28 */     this.a = paramDouble1;
/* 29 */     this.b = paramDouble2;
/* 30 */     this.c = paramDouble3;
/* 31 */     this.d = paramFloat;
/* 32 */     this.e = new ArrayList(paramList);
/*    */     
/* 34 */     if (paramVec3D != null) {
/* 35 */       this.f = (float)paramVec3D.a;
/* 36 */       this.g = (float)paramVec3D.b;
/* 37 */       this.h = (float)paramVec3D.c;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 43 */     this.a = paramPacketDataSerializer.readFloat();
/* 44 */     this.b = paramPacketDataSerializer.readFloat();
/* 45 */     this.c = paramPacketDataSerializer.readFloat();
/* 46 */     this.d = paramPacketDataSerializer.readFloat();
/* 47 */     int i = paramPacketDataSerializer.readInt();
/*    */     
/* 49 */     this.e = new ArrayList(i);
/*    */     
/* 51 */     int j = (int)this.a;
/* 52 */     int k = (int)this.b;
/* 53 */     int m = (int)this.c;
/* 54 */     for (byte b = 0; b < i; b++) {
/* 55 */       int n = paramPacketDataSerializer.readByte() + j;
/* 56 */       int i1 = paramPacketDataSerializer.readByte() + k;
/* 57 */       int i2 = paramPacketDataSerializer.readByte() + m;
/* 58 */       this.e.add(new ChunkPosition(n, i1, i2));
/*    */     } 
/*    */     
/* 61 */     this.f = paramPacketDataSerializer.readFloat();
/* 62 */     this.g = paramPacketDataSerializer.readFloat();
/* 63 */     this.h = paramPacketDataSerializer.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 68 */     paramPacketDataSerializer.writeFloat((float)this.a);
/* 69 */     paramPacketDataSerializer.writeFloat((float)this.b);
/* 70 */     paramPacketDataSerializer.writeFloat((float)this.c);
/* 71 */     paramPacketDataSerializer.writeFloat(this.d);
/* 72 */     paramPacketDataSerializer.writeInt(this.e.size());
/*    */     
/* 74 */     int i = (int)this.a;
/* 75 */     int j = (int)this.b;
/* 76 */     int k = (int)this.c;
/* 77 */     for (ChunkPosition chunkPosition : this.e) {
/* 78 */       int m = chunkPosition.x - i;
/* 79 */       int n = chunkPosition.y - j;
/* 80 */       int i1 = chunkPosition.z - k;
/* 81 */       paramPacketDataSerializer.writeByte(m);
/* 82 */       paramPacketDataSerializer.writeByte(n);
/* 83 */       paramPacketDataSerializer.writeByte(i1);
/*    */     } 
/*    */     
/* 86 */     paramPacketDataSerializer.writeFloat(this.f);
/* 87 */     paramPacketDataSerializer.writeFloat(this.g);
/* 88 */     paramPacketDataSerializer.writeFloat(this.h);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 93 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutExplosion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */