/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class TileEntitySign
/*    */   extends TileEntity {
/*  5 */   public String[] lines = new String[] { "", "", "", "" };
/*  6 */   public int i = -1;
/*    */   
/*    */   public boolean isEditable = true;
/*    */   
/*    */   private EntityHuman k;
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 13 */     super.b(nbttagcompound);
/* 14 */     nbttagcompound.setString("Text1", this.lines[0]);
/* 15 */     nbttagcompound.setString("Text2", this.lines[1]);
/* 16 */     nbttagcompound.setString("Text3", this.lines[2]);
/* 17 */     nbttagcompound.setString("Text4", this.lines[3]);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 21 */     this.isEditable = false;
/* 22 */     super.a(nbttagcompound);
/*    */     
/* 24 */     for (int i = 0; i < 4; i++) {
/* 25 */       this.lines[i] = nbttagcompound.getString("Text" + (i + 1));
/* 26 */       if (this.lines[i].length() > 15) {
/* 27 */         this.lines[i] = this.lines[i].substring(0, 15);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public Packet getUpdatePacket() {
/* 33 */     String[] astring = sanitizeLines(this.lines);
/*    */     
/* 35 */     return new PacketPlayOutUpdateSign(this.x, this.y, this.z, astring);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 39 */     return this.isEditable;
/*    */   }
/*    */   
/*    */   public void a(EntityHuman entityhuman) {
/* 43 */     this.k = entityhuman;
/*    */   }
/*    */   
/*    */   public EntityHuman b() {
/* 47 */     return this.k;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] sanitizeLines(String[] lines) {
/* 52 */     String[] astring = new String[4];
/* 53 */     for (int i = 0; i < 4; i++) {
/* 54 */       astring[i] = lines[i];
/*    */       
/* 56 */       if (lines[i].length() > 15) {
/* 57 */         astring[i] = lines[i].substring(0, 15);
/*    */       }
/*    */     } 
/* 60 */     return astring;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntitySign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */