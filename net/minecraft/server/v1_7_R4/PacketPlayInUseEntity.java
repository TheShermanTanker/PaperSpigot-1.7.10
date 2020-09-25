/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInUseEntity
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private EnumEntityUseAction action;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 12 */     if (packetdataserializer.version < 16) {
/*    */       
/* 14 */       this.a = packetdataserializer.readInt();
/* 15 */       this.action = EnumEntityUseAction.values()[packetdataserializer.readByte() % (EnumEntityUseAction.values()).length];
/*    */     } else {
/* 17 */       this.a = packetdataserializer.a();
/* 18 */       int val = packetdataserializer.a();
/* 19 */       if (val == 2) {
/* 20 */         packetdataserializer.readFloat();
/* 21 */         packetdataserializer.readFloat();
/* 22 */         packetdataserializer.readFloat();
/*    */       } else {
/*    */         
/* 25 */         this.action = EnumEntityUseAction.values()[val % (EnumEntityUseAction.values()).length];
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 32 */     packetdataserializer.writeInt(this.a);
/* 33 */     packetdataserializer.writeByte(this.action.ordinal());
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 37 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public Entity a(World world) {
/* 41 */     return world.getEntity(this.a);
/*    */   }
/*    */   
/*    */   public EnumEntityUseAction c() {
/* 45 */     return this.action;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 49 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInUseEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */