/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityVelocity
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public PacketPlayOutEntityVelocity() {}
/*    */   
/*    */   public PacketPlayOutEntityVelocity(Entity entity) {
/* 13 */     this(entity.getId(), entity.motX, entity.motY, entity.motZ);
/*    */   }
/*    */   
/*    */   public PacketPlayOutEntityVelocity(int i, double d0, double d1, double d2) {
/* 17 */     this.a = i;
/* 18 */     double d3 = 3.9D;
/*    */     
/* 20 */     if (d0 < -d3) {
/* 21 */       d0 = -d3;
/*    */     }
/*    */     
/* 24 */     if (d1 < -d3) {
/* 25 */       d1 = -d3;
/*    */     }
/*    */     
/* 28 */     if (d2 < -d3) {
/* 29 */       d2 = -d3;
/*    */     }
/*    */     
/* 32 */     if (d0 > d3) {
/* 33 */       d0 = d3;
/*    */     }
/*    */     
/* 36 */     if (d1 > d3) {
/* 37 */       d1 = d3;
/*    */     }
/*    */     
/* 40 */     if (d2 > d3) {
/* 41 */       d2 = d3;
/*    */     }
/*    */     
/* 44 */     this.b = (int)(d0 * 8000.0D);
/* 45 */     this.c = (int)(d1 * 8000.0D);
/* 46 */     this.d = (int)(d2 * 8000.0D);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 50 */     this.a = packetdataserializer.readInt();
/* 51 */     this.b = packetdataserializer.readShort();
/* 52 */     this.c = packetdataserializer.readShort();
/* 53 */     this.d = packetdataserializer.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 58 */     if (packetdataserializer.version < 16) {
/*    */       
/* 60 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/*    */       
/* 63 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */     
/* 66 */     packetdataserializer.writeShort(this.b);
/* 67 */     packetdataserializer.writeShort(this.c);
/* 68 */     packetdataserializer.writeShort(this.d);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 72 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 76 */     return String.format("id=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.a), Float.valueOf(this.b / 8000.0F), Float.valueOf(this.c / 8000.0F), Float.valueOf(this.d / 8000.0F) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 80 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityVelocity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */