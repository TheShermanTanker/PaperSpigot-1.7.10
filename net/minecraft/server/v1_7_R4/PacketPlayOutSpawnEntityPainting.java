/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayOutSpawnEntityPainting
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   private String f;
/*    */   
/*    */   public PacketPlayOutSpawnEntityPainting() {}
/*    */   
/*    */   public PacketPlayOutSpawnEntityPainting(EntityPainting entitypainting) {
/* 17 */     this.a = entitypainting.getId();
/* 18 */     this.b = entitypainting.x;
/* 19 */     this.c = entitypainting.y;
/* 20 */     this.d = entitypainting.z;
/* 21 */     this.e = entitypainting.direction;
/* 22 */     this.f = entitypainting.art.B;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 26 */     this.a = packetdataserializer.a();
/* 27 */     this.f = packetdataserializer.c(EnumArt.A);
/* 28 */     this.b = packetdataserializer.readInt();
/* 29 */     this.c = packetdataserializer.readInt();
/* 30 */     this.d = packetdataserializer.readInt();
/* 31 */     this.e = packetdataserializer.readInt();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 35 */     packetdataserializer.b(this.a);
/* 36 */     packetdataserializer.a(this.f);
/*    */     
/* 38 */     if (packetdataserializer.version >= 28)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 44 */       switch (this.e) {
/*    */         case 0:
/* 46 */           this.d++;
/*    */           break;
/*    */         case 1:
/* 49 */           this.b--;
/*    */           break;
/*    */         case 2:
/* 52 */           this.d--;
/*    */           break;
/*    */         case 3:
/* 55 */           this.b++;
/*    */           break;
/*    */       } 
/*    */     }
/* 59 */     if (packetdataserializer.version < 16) {
/*    */       
/* 61 */       packetdataserializer.writeInt(this.b);
/* 62 */       packetdataserializer.writeInt(this.c);
/* 63 */       packetdataserializer.writeInt(this.d);
/* 64 */       packetdataserializer.writeInt(this.e);
/*    */     } else {
/*    */       
/* 67 */       packetdataserializer.writePosition(this.b, this.c, this.d);
/* 68 */       packetdataserializer.writeByte(this.e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 74 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 78 */     return String.format("id=%d, type=%s, x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(this.a), this.f, Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.d) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 82 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSpawnEntityPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */