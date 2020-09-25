/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.TileEntitySign;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Sign;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ 
/*    */ public class CraftSign extends CraftBlockState implements Sign {
/*    */   private final TileEntitySign sign;
/*    */   private final String[] lines;
/*    */   
/*    */   public CraftSign(Block block) {
/* 13 */     super(block);
/*    */     
/* 15 */     CraftWorld world = (CraftWorld)block.getWorld();
/* 16 */     this.sign = (TileEntitySign)world.getTileEntityAt(getX(), getY(), getZ());
/*    */     
/* 18 */     if (this.sign == null) {
/* 19 */       this.lines = new String[] { "", "", "", "" };
/*    */       
/*    */       return;
/*    */     } 
/* 23 */     this.lines = new String[this.sign.lines.length];
/* 24 */     System.arraycopy(this.sign.lines, 0, this.lines, 0, this.lines.length);
/*    */   }
/*    */   
/*    */   public String[] getLines() {
/* 28 */     return this.lines;
/*    */   }
/*    */   
/*    */   public String getLine(int index) throws IndexOutOfBoundsException {
/* 32 */     return this.lines[index];
/*    */   }
/*    */   
/*    */   public void setLine(int index, String line) throws IndexOutOfBoundsException {
/* 36 */     this.lines[index] = line;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 41 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 43 */     if (result && this.sign != null) {
/* 44 */       this.sign.lines = sanitizeLines(this.lines);
/* 45 */       this.sign.update();
/*    */     } 
/*    */     
/* 48 */     return result;
/*    */   }
/*    */   
/*    */   public static String[] sanitizeLines(String[] lines) {
/* 52 */     String[] astring = new String[4];
/*    */     
/* 54 */     for (int i = 0; i < 4; i++) {
/* 55 */       if (i < lines.length && lines[i] != null) {
/* 56 */         astring[i] = lines[i];
/*    */       } else {
/* 58 */         astring[i] = "";
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return TileEntitySign.sanitizeLines(astring);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */