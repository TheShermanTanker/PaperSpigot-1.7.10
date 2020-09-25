/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaterialMapColor
/*    */ {
/*  7 */   public static final MaterialMapColor[] a = new MaterialMapColor[64];
/*    */   
/*  9 */   public static final MaterialMapColor b = new MaterialMapColor(0, 0);
/* 10 */   public static final MaterialMapColor c = new MaterialMapColor(1, 8368696);
/* 11 */   public static final MaterialMapColor d = new MaterialMapColor(2, 16247203);
/* 12 */   public static final MaterialMapColor e = new MaterialMapColor(3, 10987431);
/* 13 */   public static final MaterialMapColor f = new MaterialMapColor(4, 16711680);
/* 14 */   public static final MaterialMapColor g = new MaterialMapColor(5, 10526975);
/* 15 */   public static final MaterialMapColor h = new MaterialMapColor(6, 10987431);
/* 16 */   public static final MaterialMapColor i = new MaterialMapColor(7, 31744);
/* 17 */   public static final MaterialMapColor j = new MaterialMapColor(8, 16777215);
/* 18 */   public static final MaterialMapColor k = new MaterialMapColor(9, 10791096);
/* 19 */   public static final MaterialMapColor l = new MaterialMapColor(10, 12020271);
/* 20 */   public static final MaterialMapColor m = new MaterialMapColor(11, 7368816);
/* 21 */   public static final MaterialMapColor n = new MaterialMapColor(12, 4210943);
/* 22 */   public static final MaterialMapColor o = new MaterialMapColor(13, 6837042);
/* 23 */   public static final MaterialMapColor p = new MaterialMapColor(14, 16776437);
/* 24 */   public static final MaterialMapColor q = new MaterialMapColor(15, 14188339);
/* 25 */   public static final MaterialMapColor r = new MaterialMapColor(16, 11685080);
/* 26 */   public static final MaterialMapColor s = new MaterialMapColor(17, 6724056);
/* 27 */   public static final MaterialMapColor t = new MaterialMapColor(18, 15066419);
/* 28 */   public static final MaterialMapColor u = new MaterialMapColor(19, 8375321);
/* 29 */   public static final MaterialMapColor v = new MaterialMapColor(20, 15892389);
/* 30 */   public static final MaterialMapColor w = new MaterialMapColor(21, 5000268);
/* 31 */   public static final MaterialMapColor x = new MaterialMapColor(22, 10066329);
/* 32 */   public static final MaterialMapColor y = new MaterialMapColor(23, 5013401);
/* 33 */   public static final MaterialMapColor z = new MaterialMapColor(24, 8339378);
/* 34 */   public static final MaterialMapColor A = new MaterialMapColor(25, 3361970);
/* 35 */   public static final MaterialMapColor B = new MaterialMapColor(26, 6704179);
/* 36 */   public static final MaterialMapColor C = new MaterialMapColor(27, 6717235);
/* 37 */   public static final MaterialMapColor D = new MaterialMapColor(28, 10040115);
/* 38 */   public static final MaterialMapColor E = new MaterialMapColor(29, 1644825);
/* 39 */   public static final MaterialMapColor F = new MaterialMapColor(30, 16445005);
/* 40 */   public static final MaterialMapColor G = new MaterialMapColor(31, 6085589);
/* 41 */   public static final MaterialMapColor H = new MaterialMapColor(32, 4882687);
/* 42 */   public static final MaterialMapColor I = new MaterialMapColor(33, 55610);
/* 43 */   public static final MaterialMapColor J = new MaterialMapColor(34, 1381407);
/* 44 */   public static final MaterialMapColor K = new MaterialMapColor(35, 7340544);
/*    */   
/*    */   public final int L;
/*    */   public final int M;
/*    */   
/*    */   private MaterialMapColor(int paramInt1, int paramInt2) {
/* 50 */     if (paramInt1 < 0 || paramInt1 > 63) throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)"); 
/* 51 */     this.M = paramInt1;
/* 52 */     this.L = paramInt2;
/* 53 */     a[paramInt1] = this;
/*    */   }
/*    */   
/*    */   public static MaterialMapColor a(int paramInt) {
/* 57 */     switch (BlockCloth.c(paramInt)) {
/*    */       case 0:
/* 59 */         return E;
/*    */       case 1:
/* 61 */         return D;
/*    */       case 2:
/* 63 */         return C;
/*    */       case 3:
/* 65 */         return B;
/*    */       case 4:
/* 67 */         return A;
/*    */       case 5:
/* 69 */         return z;
/*    */       case 6:
/* 71 */         return y;
/*    */       case 7:
/* 73 */         return x;
/*    */       case 8:
/* 75 */         return w;
/*    */       case 9:
/* 77 */         return v;
/*    */       case 10:
/* 79 */         return u;
/*    */       case 11:
/* 81 */         return t;
/*    */       case 12:
/* 83 */         return s;
/*    */       case 13:
/* 85 */         return r;
/*    */       case 14:
/* 87 */         return q;
/*    */       case 15:
/* 89 */         return j;
/*    */     } 
/*    */     
/* 92 */     return b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MaterialMapColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */