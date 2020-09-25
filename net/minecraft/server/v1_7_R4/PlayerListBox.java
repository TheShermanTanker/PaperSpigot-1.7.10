/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import javax.swing.JList;
/*    */ 
/*    */ 
/*    */ public class PlayerListBox
/*    */   extends JList
/*    */   implements IUpdatePlayerListBox
/*    */ {
/*    */   private MinecraftServer a;
/*    */   private int b;
/*    */   
/*    */   public PlayerListBox(MinecraftServer paramMinecraftServer) {
/* 15 */     this.a = paramMinecraftServer;
/* 16 */     paramMinecraftServer.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a() {
/* 21 */     if (this.b++ % 20 == 0) {
/* 22 */       Vector<String> vector = new Vector();
/* 23 */       for (byte b = 0; b < (this.a.getPlayerList()).players.size(); b++) {
/* 24 */         vector.add(((EntityPlayer)(this.a.getPlayerList()).players.get(b)).getName());
/*    */       }
/* 26 */       setListData((Vector)vector);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerListBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */