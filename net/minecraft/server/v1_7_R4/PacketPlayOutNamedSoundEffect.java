/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutNamedSoundEffect
/*    */   extends Packet
/*    */ {
/*    */   private String a;
/*    */   private int b;
/* 17 */   private int c = Integer.MAX_VALUE;
/*    */   
/*    */   private int d;
/*    */   
/*    */   private float e;
/*    */   
/*    */   private int f;
/*    */ 
/*    */   
/*    */   public PacketPlayOutNamedSoundEffect(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
/* 27 */     Validate.notNull(paramString, "name", new Object[0]);
/* 28 */     this.a = paramString;
/* 29 */     this.b = (int)(paramDouble1 * 8.0D);
/* 30 */     this.c = (int)(paramDouble2 * 8.0D);
/* 31 */     this.d = (int)(paramDouble3 * 8.0D);
/* 32 */     this.e = paramFloat1;
/* 33 */     this.f = (int)(paramFloat2 * 63.0F);
/*    */     
/* 35 */     if (this.f < 0) this.f = 0; 
/* 36 */     if (this.f > 255) this.f = 255;
/*    */   
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 41 */     this.a = paramPacketDataSerializer.c(256);
/* 42 */     this.b = paramPacketDataSerializer.readInt();
/* 43 */     this.c = paramPacketDataSerializer.readInt();
/* 44 */     this.d = paramPacketDataSerializer.readInt();
/* 45 */     this.e = paramPacketDataSerializer.readFloat();
/* 46 */     this.f = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 51 */     paramPacketDataSerializer.a(this.a);
/* 52 */     paramPacketDataSerializer.writeInt(this.b);
/* 53 */     paramPacketDataSerializer.writeInt(this.c);
/* 54 */     paramPacketDataSerializer.writeInt(this.d);
/* 55 */     paramPacketDataSerializer.writeFloat(this.e);
/* 56 */     paramPacketDataSerializer.writeByte(this.f);
/*    */   }
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
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 85 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */   
/*    */   public PacketPlayOutNamedSoundEffect() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutNamedSoundEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */