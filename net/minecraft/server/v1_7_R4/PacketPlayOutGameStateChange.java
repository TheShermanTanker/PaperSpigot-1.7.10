/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutGameStateChange
/*    */   extends Packet
/*    */ {
/* 25 */   public static final String[] a = new String[] { "tile.bed.notValid", null, null, "gameMode.changed" };
/*    */ 
/*    */   
/*    */   private int b;
/*    */   
/*    */   private float c;
/*    */ 
/*    */   
/*    */   public PacketPlayOutGameStateChange() {}
/*    */ 
/*    */   
/*    */   public PacketPlayOutGameStateChange(int paramInt, float paramFloat) {
/* 37 */     this.b = paramInt;
/* 38 */     this.c = paramFloat;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 43 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/* 44 */     this.c = paramPacketDataSerializer.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 49 */     paramPacketDataSerializer.writeByte(this.b);
/* 50 */     paramPacketDataSerializer.writeFloat(this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 55 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutGameStateChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */