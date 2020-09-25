/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenFlatInfo
/*     */ {
/*  22 */   private final List layers = new ArrayList();
/*  23 */   private final Map structures = new HashMap<Object, Object>();
/*     */   private int c;
/*     */   
/*     */   public int a() {
/*  27 */     return this.c;
/*     */   }
/*     */   
/*     */   public void a(int paramInt) {
/*  31 */     this.c = paramInt;
/*     */   }
/*     */   
/*     */   public Map b() {
/*  35 */     return this.structures;
/*     */   }
/*     */   
/*     */   public List c() {
/*  39 */     return this.layers;
/*     */   }
/*     */   
/*     */   public void d() {
/*  43 */     int i = 0;
/*     */     
/*  45 */     for (WorldGenFlatLayerInfo worldGenFlatLayerInfo : this.layers) {
/*  46 */       worldGenFlatLayerInfo.c(i);
/*  47 */       i += worldGenFlatLayerInfo.a();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  53 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*  55 */     stringBuilder.append(2);
/*  56 */     stringBuilder.append(";");
/*     */     byte b;
/*  58 */     for (b = 0; b < this.layers.size(); b++) {
/*  59 */       if (b > 0) stringBuilder.append(","); 
/*  60 */       stringBuilder.append(((WorldGenFlatLayerInfo)this.layers.get(b)).toString());
/*     */     } 
/*     */     
/*  63 */     stringBuilder.append(";");
/*  64 */     stringBuilder.append(this.c);
/*     */     
/*  66 */     if (!this.structures.isEmpty()) {
/*  67 */       stringBuilder.append(";");
/*  68 */       b = 0;
/*     */       
/*  70 */       for (Map.Entry entry : this.structures.entrySet()) {
/*  71 */         if (b++ > 0) stringBuilder.append(","); 
/*  72 */         stringBuilder.append(((String)entry.getKey()).toLowerCase());
/*     */         
/*  74 */         Map map = (Map)entry.getValue();
/*  75 */         if (!map.isEmpty()) {
/*  76 */           stringBuilder.append("(");
/*  77 */           byte b1 = 0;
/*     */           
/*  79 */           for (Map.Entry entry1 : map.entrySet()) {
/*  80 */             if (b1++ > 0) stringBuilder.append(" "); 
/*  81 */             stringBuilder.append((String)entry1.getKey());
/*  82 */             stringBuilder.append("=");
/*  83 */             stringBuilder.append((String)entry1.getValue());
/*     */           } 
/*     */           
/*  86 */           stringBuilder.append(")");
/*     */         } 
/*     */       } 
/*     */     } else {
/*  90 */       stringBuilder.append(";");
/*     */     } 
/*     */     
/*  93 */     return stringBuilder.toString();
/*     */   }
/*     */   private static WorldGenFlatLayerInfo a(String paramString, int paramInt) {
/*     */     int k;
/*  97 */     String[] arrayOfString = paramString.split("x", 2);
/*  98 */     int i = 1;
/*     */     
/* 100 */     int j = 0;
/*     */     
/* 102 */     if (arrayOfString.length == 2) {
/*     */       try {
/* 104 */         i = Integer.parseInt(arrayOfString[0]);
/* 105 */         if (paramInt + i >= 256) i = 256 - paramInt; 
/* 106 */         if (i < 0) i = 0; 
/* 107 */       } catch (Throwable throwable) {
/* 108 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 113 */       String str = arrayOfString[arrayOfString.length - 1];
/* 114 */       arrayOfString = str.split(":", 2);
/* 115 */       k = Integer.parseInt(arrayOfString[0]);
/* 116 */       if (arrayOfString.length > 1) j = Integer.parseInt(arrayOfString[1]);
/*     */       
/* 118 */       if (Block.getById(k) == Blocks.AIR) {
/* 119 */         k = 0;
/* 120 */         j = 0;
/*     */       } 
/*     */       
/* 123 */       if (j < 0 || j > 15) j = 0; 
/* 124 */     } catch (Throwable throwable) {
/* 125 */       return null;
/*     */     } 
/*     */     
/* 128 */     WorldGenFlatLayerInfo worldGenFlatLayerInfo = new WorldGenFlatLayerInfo(i, Block.getById(k), j);
/* 129 */     worldGenFlatLayerInfo.c(paramInt);
/* 130 */     return worldGenFlatLayerInfo;
/*     */   }
/*     */   
/*     */   private static List b(String paramString) {
/* 134 */     if (paramString == null || paramString.length() < 1) return null;
/*     */     
/* 136 */     ArrayList<WorldGenFlatLayerInfo> arrayList = new ArrayList();
/* 137 */     String[] arrayOfString = paramString.split(",");
/* 138 */     int i = 0;
/*     */     
/* 140 */     for (String str : arrayOfString) {
/* 141 */       WorldGenFlatLayerInfo worldGenFlatLayerInfo = a(str, i);
/* 142 */       if (worldGenFlatLayerInfo == null) return null; 
/* 143 */       arrayList.add(worldGenFlatLayerInfo);
/* 144 */       i += worldGenFlatLayerInfo.a();
/*     */     } 
/*     */     
/* 147 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static WorldGenFlatInfo a(String paramString) {
/* 151 */     if (paramString == null) return e(); 
/* 152 */     String[] arrayOfString = paramString.split(";", -1);
/* 153 */     byte b1 = (arrayOfString.length == 1) ? 0 : MathHelper.a(arrayOfString[0], 0);
/* 154 */     if (!b1 || b1 > 2) return e();
/*     */     
/* 156 */     WorldGenFlatInfo worldGenFlatInfo = new WorldGenFlatInfo();
/* 157 */     byte b2 = (arrayOfString.length == 1) ? 0 : 1;
/* 158 */     List list = b(arrayOfString[b2++]);
/*     */     
/* 160 */     if (list == null || list.isEmpty()) {
/* 161 */       return e();
/*     */     }
/*     */     
/* 164 */     worldGenFlatInfo.c().addAll(list);
/* 165 */     worldGenFlatInfo.d();
/*     */     
/* 167 */     int i = BiomeBase.PLAINS.id;
/* 168 */     if (b1 > 0 && arrayOfString.length > b2) i = MathHelper.a(arrayOfString[b2++], i); 
/* 169 */     worldGenFlatInfo.a(i);
/*     */     
/* 171 */     if (b1 > 0 && arrayOfString.length > b2) {
/* 172 */       String[] arrayOfString1 = arrayOfString[b2++].toLowerCase().split(",");
/*     */       
/* 174 */       for (String str : arrayOfString1) {
/* 175 */         String[] arrayOfString2 = str.split("\\(", 2);
/* 176 */         HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
/*     */         
/* 178 */         if (arrayOfString2[0].length() > 0) {
/* 179 */           worldGenFlatInfo.b().put(arrayOfString2[0], hashMap);
/*     */           
/* 181 */           if (arrayOfString2.length > 1 && arrayOfString2[1].endsWith(")") && arrayOfString2[1].length() > 1) {
/* 182 */             String[] arrayOfString3 = arrayOfString2[1].substring(0, arrayOfString2[1].length() - 1).split(" ");
/*     */             
/* 184 */             for (byte b = 0; b < arrayOfString3.length; b++) {
/* 185 */               String[] arrayOfString4 = arrayOfString3[b].split("=", 2);
/* 186 */               if (arrayOfString4.length == 2) hashMap.put(arrayOfString4[0], arrayOfString4[1]); 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 192 */       worldGenFlatInfo.b().put("village", new HashMap<Object, Object>());
/*     */     } 
/*     */     
/* 195 */     return worldGenFlatInfo;
/*     */   }
/*     */   
/*     */   public static WorldGenFlatInfo e() {
/* 199 */     WorldGenFlatInfo worldGenFlatInfo = new WorldGenFlatInfo();
/*     */     
/* 201 */     worldGenFlatInfo.a(BiomeBase.PLAINS.id);
/* 202 */     worldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.BEDROCK));
/* 203 */     worldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(2, Blocks.DIRT));
/* 204 */     worldGenFlatInfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.GRASS));
/* 205 */     worldGenFlatInfo.d();
/* 206 */     worldGenFlatInfo.b().put("village", new HashMap<Object, Object>());
/*     */     
/* 208 */     return worldGenFlatInfo;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenFlatInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */