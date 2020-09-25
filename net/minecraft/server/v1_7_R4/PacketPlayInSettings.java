/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInSettings
/*    */   extends Packet
/*    */ {
/*    */   private String a;
/*    */   private int b;
/*    */   private EnumChatVisibility c;
/*    */   private boolean d;
/*    */   private EnumDifficulty e;
/*    */   private boolean f;
/*    */   public int version;
/*    */   public int flags;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 22 */     this.a = packetdataserializer.c(7);
/* 23 */     this.b = packetdataserializer.readByte();
/* 24 */     this.c = EnumChatVisibility.a(packetdataserializer.readByte());
/* 25 */     this.d = packetdataserializer.readBoolean();
/*    */     
/* 27 */     if (packetdataserializer.version < 16) {
/*    */       
/* 29 */       this.e = EnumDifficulty.getById(packetdataserializer.readByte());
/* 30 */       this.f = packetdataserializer.readBoolean();
/*    */     } else {
/*    */       
/* 33 */       this.flags = packetdataserializer.readUnsignedByte();
/*    */     } 
/* 35 */     this.version = packetdataserializer.version;
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 40 */     packetdataserializer.a(this.a);
/* 41 */     packetdataserializer.writeByte(this.b);
/* 42 */     packetdataserializer.writeByte(this.c.a());
/* 43 */     packetdataserializer.writeBoolean(this.d);
/* 44 */     packetdataserializer.writeByte(this.e.a());
/* 45 */     packetdataserializer.writeBoolean(this.f);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 49 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public String c() {
/* 53 */     return this.a;
/*    */   }
/*    */   
/*    */   public int d() {
/* 57 */     return this.b;
/*    */   }
/*    */   
/*    */   public EnumChatVisibility e() {
/* 61 */     return this.c;
/*    */   }
/*    */   
/*    */   public boolean f() {
/* 65 */     return this.d;
/*    */   }
/*    */   
/*    */   public EnumDifficulty g() {
/* 69 */     return this.e;
/*    */   }
/*    */   
/*    */   public boolean h() {
/* 73 */     return this.f;
/*    */   }
/*    */   
/*    */   public String b() {
/* 77 */     return String.format("lang='%s', view=%d, chat=%s, col=%b, difficulty=%s, cape=%b", new Object[] { this.a, Integer.valueOf(this.b), this.c, Boolean.valueOf(this.d), this.e, Boolean.valueOf(this.f) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 81 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */