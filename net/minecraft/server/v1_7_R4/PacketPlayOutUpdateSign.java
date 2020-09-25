/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
/*    */ 
/*    */ public class PacketPlayOutUpdateSign
/*    */   extends Packet
/*    */ {
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   private String[] lines;
/*    */   
/*    */   public PacketPlayOutUpdateSign() {}
/*    */   
/*    */   public PacketPlayOutUpdateSign(int i, int j, int k, String[] astring) {
/* 17 */     this.x = i;
/* 18 */     this.y = j;
/* 19 */     this.z = k;
/* 20 */     this.lines = new String[] { astring[0], astring[1], astring[2], astring[3] };
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 24 */     this.x = packetdataserializer.readInt();
/* 25 */     this.y = packetdataserializer.readShort();
/* 26 */     this.z = packetdataserializer.readInt();
/* 27 */     this.lines = new String[4];
/*    */     
/* 29 */     for (int i = 0; i < 4; i++) {
/* 30 */       this.lines[i] = packetdataserializer.c(15);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 36 */     if (packetdataserializer.version < 16) {
/*    */       
/* 38 */       packetdataserializer.writeInt(this.x);
/* 39 */       packetdataserializer.writeShort(this.y);
/* 40 */       packetdataserializer.writeInt(this.z);
/*    */     } else {
/*    */       
/* 43 */       packetdataserializer.writePosition(this.x, this.y, this.z);
/*    */     } 
/*    */     
/* 46 */     for (int i = 0; i < 4; i++) {
/* 47 */       if (packetdataserializer.version < 21) {
/*    */         
/* 49 */         packetdataserializer.a(this.lines[i]);
/*    */       } else {
/*    */         
/* 52 */         String line = ChatSerializer.a(CraftChatMessage.fromString(this.lines[i])[0]);
/* 53 */         packetdataserializer.a(line);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 60 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 64 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutUpdateSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */