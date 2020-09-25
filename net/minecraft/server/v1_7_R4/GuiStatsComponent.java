/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.text.DecimalFormat;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.Timer;
/*    */ 
/*    */ public class GuiStatsComponent
/*    */   extends JComponent
/*    */ {
/* 13 */   private static final DecimalFormat a = new DecimalFormat("########0.000");
/*    */ 
/*    */   
/* 16 */   private int[] b = new int[256];
/*    */   private int c;
/* 18 */   private String[] d = new String[11];
/*    */   private final MinecraftServer e;
/*    */   
/*    */   public GuiStatsComponent(MinecraftServer paramMinecraftServer) {
/* 22 */     this.e = paramMinecraftServer;
/* 23 */     setPreferredSize(new Dimension(456, 246));
/* 24 */     setMinimumSize(new Dimension(456, 246));
/* 25 */     setMaximumSize(new Dimension(456, 246));
/* 26 */     (new Timer(500, new GuiStatsListener(this))).start();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     setBackground(Color.BLACK);
/*    */   }
/*    */   
/*    */   private void a() {
/* 36 */     long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
/* 37 */     System.gc();
/* 38 */     this.d[0] = "Memory use: " + (l / 1024L / 1024L) + " mb (" + (Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory()) + "% free)";
/* 39 */     this.d[1] = "Avg tick: " + a.format(a(this.e.g) * 1.0E-6D) + " ms";
/* 40 */     repaint();
/*    */   }
/*    */   
/*    */   private double a(long[] paramArrayOflong) {
/* 44 */     long l = 0L;
/* 45 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 46 */       l += paramArrayOflong[b];
/*    */     }
/* 48 */     return l / paramArrayOflong.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics paramGraphics) {
/* 53 */     paramGraphics.setColor(new Color(16777215));
/* 54 */     paramGraphics.fillRect(0, 0, 456, 246);
/*    */     byte b;
/* 56 */     for (b = 0; b < 'Ā'; b++) {
/* 57 */       int i = this.b[b + this.c & 0xFF];
/* 58 */       paramGraphics.setColor(new Color(i + 28 << 16));
/* 59 */       paramGraphics.fillRect(b, 100 - i, 1, i);
/*    */     } 
/* 61 */     paramGraphics.setColor(Color.BLACK);
/* 62 */     for (b = 0; b < this.d.length; b++) {
/* 63 */       String str = this.d[b];
/* 64 */       if (str != null) paramGraphics.drawString(str, 32, 116 + b * 16); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GuiStatsComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */