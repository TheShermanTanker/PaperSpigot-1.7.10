/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class PacketPlayOutEntityMetadata
/*    */   extends Packet {
/*    */   private int a;
/*    */   private List b;
/*    */   
/*    */   public PacketPlayOutEntityMetadata() {}
/*    */   
/*    */   public PacketPlayOutEntityMetadata(int i, DataWatcher datawatcher, boolean flag) {
/* 13 */     this.a = i;
/* 14 */     if (flag) {
/* 15 */       this.b = datawatcher.c();
/*    */     } else {
/* 17 */       this.b = datawatcher.b();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.readInt();
/* 23 */     this.b = DataWatcher.b(packetdataserializer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 28 */     if (packetdataserializer.version < 16) {
/*    */       
/* 30 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/*    */       
/* 33 */       packetdataserializer.b(this.a);
/*    */     } 
/* 35 */     DataWatcher.a(this.b, packetdataserializer, packetdataserializer.version);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 40 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 44 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */