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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInClientCommand
/*    */   extends Packet
/*    */ {
/*    */   private EnumClientCommand a;
/*    */   
/*    */   public PacketPlayInClientCommand() {}
/*    */   
/*    */   public PacketPlayInClientCommand(EnumClientCommand paramEnumClientCommand) {
/* 35 */     this.a = paramEnumClientCommand;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 40 */     this.a = EnumClientCommand.a()[paramPacketDataSerializer.readByte() % (EnumClientCommand.a()).length];
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 45 */     paramPacketDataSerializer.writeByte(EnumClientCommand.a(this.a));
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayInListener paramPacketPlayInListener) {
/* 50 */     paramPacketPlayInListener.a(this);
/*    */   }
/*    */   
/*    */   public EnumClientCommand c() {
/* 54 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInClientCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */