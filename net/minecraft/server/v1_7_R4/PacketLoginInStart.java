/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginInStart
/*    */   extends Packet
/*    */ {
/*    */   private GameProfile a;
/*    */   
/*    */   public PacketLoginInStart() {}
/*    */   
/*    */   public PacketLoginInStart(GameProfile paramGameProfile) {
/* 18 */     this.a = paramGameProfile;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 23 */     this.a = new GameProfile(null, paramPacketDataSerializer.c(16));
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 28 */     paramPacketDataSerializer.a(this.a.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketLoginInListener paramPacketLoginInListener) {
/* 33 */     paramPacketLoginInListener.a(this);
/*    */   }
/*    */   
/*    */   public GameProfile c() {
/* 37 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketLoginInStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */