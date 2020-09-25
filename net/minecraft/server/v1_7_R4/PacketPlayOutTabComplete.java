/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutTabComplete
/*    */   extends Packet
/*    */ {
/*    */   private String[] a;
/*    */   
/*    */   public PacketPlayOutTabComplete() {}
/*    */   
/*    */   public PacketPlayOutTabComplete(String[] paramArrayOfString) {
/* 18 */     this.a = paramArrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 23 */     this.a = new String[paramPacketDataSerializer.a()];
/*    */     
/* 25 */     for (byte b = 0; b < this.a.length; b++) {
/* 26 */       this.a[b] = paramPacketDataSerializer.c(32767);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 32 */     paramPacketDataSerializer.b(this.a.length);
/*    */     
/* 34 */     for (String str : this.a) {
/* 35 */       paramPacketDataSerializer.a(str);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 41 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String b() {
/* 50 */     return String.format("candidates='%s'", new Object[] { ArrayUtils.toString(this.a) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutTabComplete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */