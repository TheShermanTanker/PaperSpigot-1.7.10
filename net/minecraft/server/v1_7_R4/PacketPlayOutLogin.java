/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayOutLogin
/*    */   extends Packet {
/*    */   private int a;
/*    */   private boolean b;
/*    */   private EnumGamemode c;
/*    */   private int d;
/*    */   private EnumDifficulty e;
/*    */   private int f;
/*    */   private WorldType g;
/*    */   
/*    */   public PacketPlayOutLogin() {}
/*    */   
/*    */   public PacketPlayOutLogin(int i, EnumGamemode enumgamemode, boolean flag, int j, EnumDifficulty enumdifficulty, int k, WorldType worldtype) {
/* 18 */     this.a = i;
/* 19 */     this.d = j;
/* 20 */     this.e = enumdifficulty;
/* 21 */     this.c = enumgamemode;
/* 22 */     this.f = k;
/* 23 */     this.b = flag;
/* 24 */     this.g = worldtype;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 28 */     this.a = packetdataserializer.readInt();
/* 29 */     short short1 = packetdataserializer.readUnsignedByte();
/*    */     
/* 31 */     this.b = ((short1 & 0x8) == 8);
/* 32 */     int i = short1 & 0xFFFFFFF7;
/*    */     
/* 34 */     this.c = EnumGamemode.getById(i);
/* 35 */     this.d = packetdataserializer.readByte();
/* 36 */     this.e = EnumDifficulty.getById(packetdataserializer.readUnsignedByte());
/* 37 */     this.f = packetdataserializer.readUnsignedByte();
/* 38 */     this.g = WorldType.getType(packetdataserializer.c(16));
/* 39 */     if (this.g == null) {
/* 40 */       this.g = WorldType.NORMAL;
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 45 */     packetdataserializer.writeInt(this.a);
/* 46 */     int i = this.c.getId();
/*    */     
/* 48 */     if (this.b) {
/* 49 */       i |= 0x8;
/*    */     }
/*    */     
/* 52 */     packetdataserializer.writeByte(i);
/* 53 */     packetdataserializer.writeByte(this.d);
/* 54 */     packetdataserializer.writeByte(this.e.a());
/* 55 */     packetdataserializer.writeByte(this.f);
/* 56 */     packetdataserializer.a(this.g.name());
/*    */ 
/*    */     
/* 59 */     if (packetdataserializer.version >= 29)
/*    */     {
/* 61 */       packetdataserializer.writeBoolean(false);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 67 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 71 */     return String.format("eid=%d, gameType=%d, hardcore=%b, dimension=%d, difficulty=%s, maxplayers=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.c.getId()), Boolean.valueOf(this.b), Integer.valueOf(this.d), this.e, Integer.valueOf(this.f) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 75 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutLogin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */