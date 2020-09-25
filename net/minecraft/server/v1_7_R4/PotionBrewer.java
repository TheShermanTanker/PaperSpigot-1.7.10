/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PotionBrewer
/*     */ {
/*     */   public static final String a;
/*     */   public static final String b;
/*     */   public static final String c;
/*     */   public static final String d;
/*     */   public static final String e;
/*     */   public static final String f;
/*     */   public static final String g;
/*     */   public static final String h;
/*     */   public static final String i;
/*     */   public static final String j;
/*     */   public static final String k;
/*     */   public static final String l;
/*     */   public static final String m;
/*  52 */   private static final HashMap effectDurations = new HashMap<Object, Object>();
/*  53 */   private static final HashMap effectAmplifiers = new HashMap<Object, Object>();
/*     */   
/*     */   private static final HashMap p;
/*     */   private static final String[] appearances;
/*     */   
/*     */   static {
/*  59 */     a = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     c = "+0-1-2-3&4-4+13";
/*  82 */     effectDurations.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "0 & !1 & !2 & !3 & 0+6");
/*     */     
/*  84 */     b = "-0+1-2-3&4-4+13";
/*  85 */     effectDurations.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "!0 & 1 & !2 & !3 & 1+6");
/*     */     
/*  87 */     h = "+0+1-2-3&4-4+13";
/*  88 */     effectDurations.put(Integer.valueOf(MobEffectList.FIRE_RESISTANCE.getId()), "0 & 1 & !2 & !3 & 0+6");
/*     */     
/*  90 */     f = "+0-1+2-3&4-4+13";
/*  91 */     effectDurations.put(Integer.valueOf(MobEffectList.HEAL.getId()), "0 & !1 & 2 & !3");
/*     */     
/*  93 */     d = "-0-1+2-3&4-4+13";
/*  94 */     effectDurations.put(Integer.valueOf(MobEffectList.POISON.getId()), "!0 & !1 & 2 & !3 & 2+6");
/*     */     
/*  96 */     e = "-0+3-4+13";
/*  97 */     effectDurations.put(Integer.valueOf(MobEffectList.WEAKNESS.getId()), "!0 & !1 & !2 & 3 & 3+6");
/*  98 */     effectDurations.put(Integer.valueOf(MobEffectList.HARM.getId()), "!0 & !1 & 2 & 3");
/*  99 */     effectDurations.put(Integer.valueOf(MobEffectList.SLOWER_MOVEMENT.getId()), "!0 & 1 & !2 & 3 & 3+6");
/*     */     
/* 101 */     g = "+0-1-2+3&4-4+13";
/* 102 */     effectDurations.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "0 & !1 & !2 & 3 & 3+6");
/*     */     
/* 104 */     l = "-0+1+2-3+13&4-4";
/* 105 */     effectDurations.put(Integer.valueOf(MobEffectList.NIGHT_VISION.getId()), "!0 & 1 & 2 & !3 & 2+6");
/*     */ 
/*     */     
/* 108 */     effectDurations.put(Integer.valueOf(MobEffectList.INVISIBILITY.getId()), "!0 & 1 & 2 & 3 & 2+6");
/*     */     
/* 110 */     m = "+0-1+2+3+13&4-4";
/* 111 */     effectDurations.put(Integer.valueOf(MobEffectList.WATER_BREATHING.getId()), "0 & !1 & 2 & 3 & 2+6");
/*     */ 
/*     */     
/* 114 */     j = "+5-6-7";
/* 115 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "5");
/* 116 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_DIG.getId()), "5");
/* 117 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "5");
/* 118 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "5");
/* 119 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.HARM.getId()), "5");
/* 120 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.HEAL.getId()), "5");
/* 121 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.RESISTANCE.getId()), "5");
/* 122 */     effectAmplifiers.put(Integer.valueOf(MobEffectList.POISON.getId()), "5");
/*     */ 
/*     */     
/* 125 */     i = "-5+6-7";
/*     */ 
/*     */ 
/*     */     
/* 129 */     k = "+14&13-13";
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
/*     */ 
/*     */     
/* 236 */     p = new HashMap<Object, Object>();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     appearances = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean a(int paramInt1, int paramInt2) {
/*     */     return ((paramInt1 & 1 << paramInt2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int c(int paramInt1, int paramInt2) {
/*     */     return a(paramInt1, paramInt2) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int d(int paramInt1, int paramInt2) {
/*     */     return a(paramInt1, paramInt2) ? 0 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String c(int paramInt) {
/* 294 */     int i = a(paramInt);
/* 295 */     return appearances[i];
/*     */   }
/*     */   public static int a(int paramInt) { return a(paramInt, 5, 4, 3, 2, 1); }
/*     */   public static int a(Collection paramCollection) { int i = 3694022; if (paramCollection == null || paramCollection.isEmpty())
/*     */       return i;  float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F; for (MobEffect mobEffect : paramCollection) { int j = MobEffectList.byId[mobEffect.getEffectId()].j(); for (byte b = 0; b <= mobEffect.getAmplifier(); b++) { f1 += (j >> 16 & 0xFF) / 255.0F; f2 += (j >> 8 & 0xFF) / 255.0F; f3 += (j >> 0 & 0xFF) / 255.0F; f4++; }
/*     */        }
/*     */      f1 = f1 / f4 * 255.0F; f2 = f2 / f4 * 255.0F; f3 = f3 / f4 * 255.0F; return (int)f1 << 16 | (int)f2 << 8 | (int)f3; } public static boolean b(Collection paramCollection) { for (MobEffect mobEffect : paramCollection) {
/*     */       if (!mobEffect.isAmbient())
/*     */         return false; 
/* 304 */     }  return true; } private static int a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { int i = 0;
/* 305 */     if (paramBoolean1) {
/* 306 */       i = d(paramInt4, paramInt2);
/* 307 */     } else if (paramInt1 != -1) {
/* 308 */       if (paramInt1 == 0 && h(paramInt4) == paramInt2) {
/* 309 */         i = 1;
/* 310 */       } else if (paramInt1 == 1 && h(paramInt4) > paramInt2) {
/* 311 */         i = 1;
/* 312 */       } else if (paramInt1 == 2 && h(paramInt4) < paramInt2) {
/* 313 */         i = 1;
/*     */       } 
/*     */     } else {
/* 316 */       i = c(paramInt4, paramInt2);
/*     */     } 
/* 318 */     if (paramBoolean2) {
/* 319 */       i *= paramInt3;
/*     */     }
/* 321 */     if (paramBoolean3) {
/* 322 */       i *= -1;
/*     */     }
/* 324 */     return i; }
/*     */ 
/*     */   
/*     */   private static int h(int paramInt) {
/* 328 */     byte b = 0;
/* 329 */     for (; paramInt > 0; b++) {
/* 330 */       paramInt &= paramInt - 1;
/*     */     }
/* 332 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int a(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 337 */     if (paramInt1 >= paramString.length() || paramInt2 < 0 || paramInt1 >= paramInt2) {
/* 338 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 342 */     int i = paramString.indexOf('|', paramInt1);
/* 343 */     if (i >= 0 && i < paramInt2) {
/* 344 */       int i2 = a(paramString, paramInt1, i - 1, paramInt3);
/* 345 */       if (i2 > 0) {
/* 346 */         return i2;
/*     */       }
/*     */       
/* 349 */       int i3 = a(paramString, i + 1, paramInt2, paramInt3);
/* 350 */       if (i3 > 0) {
/* 351 */         return i3;
/*     */       }
/* 353 */       return 0;
/*     */     } 
/*     */     
/* 356 */     int j = paramString.indexOf('&', paramInt1);
/* 357 */     if (j >= 0 && j < paramInt2) {
/* 358 */       int i2 = a(paramString, paramInt1, j - 1, paramInt3);
/* 359 */       if (i2 <= 0) {
/* 360 */         return 0;
/*     */       }
/*     */       
/* 363 */       int i3 = a(paramString, j + 1, paramInt2, paramInt3);
/* 364 */       if (i3 <= 0) {
/* 365 */         return 0;
/*     */       }
/*     */       
/* 368 */       if (i2 > i3) {
/* 369 */         return i2;
/*     */       }
/* 371 */       return i3;
/*     */     } 
/*     */     
/* 374 */     boolean bool1 = false;
/* 375 */     boolean bool2 = false;
/* 376 */     boolean bool3 = false;
/* 377 */     boolean bool4 = false;
/* 378 */     boolean bool5 = false;
/* 379 */     byte b = -1;
/* 380 */     int k = 0;
/* 381 */     int m = 0;
/* 382 */     int n = 0;
/* 383 */     for (int i1 = paramInt1; i1 < paramInt2; i1++) {
/*     */       
/* 385 */       char c = paramString.charAt(i1);
/* 386 */       if (c >= '0' && c <= '9') {
/* 387 */         if (bool1) {
/* 388 */           m = c - 48;
/* 389 */           bool2 = true;
/*     */         } else {
/* 391 */           k *= 10;
/* 392 */           k += c - 48;
/* 393 */           bool3 = true;
/*     */         } 
/* 395 */       } else if (c == '*') {
/* 396 */         bool1 = true;
/* 397 */       } else if (c == '!') {
/* 398 */         if (bool3) {
/* 399 */           n += a(bool4, bool2, bool5, b, k, m, paramInt3);
/* 400 */           bool3 = bool2 = bool1 = bool5 = bool4 = false;
/* 401 */           k = m = 0;
/* 402 */           b = -1;
/*     */         } 
/*     */         
/* 405 */         bool4 = true;
/* 406 */       } else if (c == '-') {
/* 407 */         if (bool3) {
/* 408 */           n += a(bool4, bool2, bool5, b, k, m, paramInt3);
/* 409 */           bool3 = bool2 = bool1 = bool5 = bool4 = false;
/* 410 */           k = m = 0;
/* 411 */           b = -1;
/*     */         } 
/*     */         
/* 414 */         bool5 = true;
/* 415 */       } else if (c == '=' || c == '<' || c == '>') {
/* 416 */         if (bool3) {
/* 417 */           n += a(bool4, bool2, bool5, b, k, m, paramInt3);
/* 418 */           bool3 = bool2 = bool1 = bool5 = bool4 = false;
/* 419 */           k = m = 0;
/* 420 */           b = -1;
/*     */         } 
/*     */         
/* 423 */         if (c == '=') {
/* 424 */           b = 0;
/* 425 */         } else if (c == '<') {
/* 426 */           b = 2;
/* 427 */         } else if (c == '>') {
/* 428 */           b = 1;
/*     */         } 
/* 430 */       } else if (c == '+' && 
/* 431 */         bool3) {
/* 432 */         n += a(bool4, bool2, bool5, b, k, m, paramInt3);
/* 433 */         bool3 = bool2 = bool1 = bool5 = bool4 = false;
/* 434 */         k = m = 0;
/* 435 */         b = -1;
/*     */       } 
/*     */     } 
/*     */     
/* 439 */     if (bool3) {
/* 440 */       n += a(bool4, bool2, bool5, b, k, m, paramInt3);
/*     */     }
/*     */     
/* 443 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List getEffects(int paramInt, boolean paramBoolean) {
/* 448 */     ArrayList<MobEffect> arrayList = null;
/*     */     
/* 450 */     for (MobEffectList mobEffectList : MobEffectList.byId) {
/* 451 */       if (mobEffectList != null && (!mobEffectList.i() || paramBoolean)) {
/*     */ 
/*     */         
/* 454 */         String str = (String)effectDurations.get(Integer.valueOf(mobEffectList.getId()));
/* 455 */         if (str != null) {
/*     */ 
/*     */ 
/*     */           
/* 459 */           int i = a(str, 0, str.length(), paramInt);
/* 460 */           if (i > 0) {
/* 461 */             int j = 0;
/* 462 */             String str1 = (String)effectAmplifiers.get(Integer.valueOf(mobEffectList.getId()));
/* 463 */             if (str1 != null) {
/* 464 */               j = a(str1, 0, str1.length(), paramInt);
/* 465 */               if (j < 0) {
/* 466 */                 j = 0;
/*     */               }
/*     */             } 
/*     */             
/* 470 */             if (mobEffectList.isInstant()) {
/* 471 */               i = 1;
/*     */             } else {
/*     */               
/* 474 */               i = 1200 * (i * 3 + (i - 1) * 2);
/* 475 */               i >>= j;
/* 476 */               i = (int)Math.round(i * mobEffectList.getDurationModifier());
/*     */               
/* 478 */               if ((paramInt & 0x4000) != 0) {
/* 479 */                 i = (int)Math.round(i * 0.75D + 0.5D);
/*     */               }
/*     */             } 
/*     */             
/* 483 */             if (arrayList == null) {
/* 484 */               arrayList = new ArrayList();
/*     */             }
/* 486 */             MobEffect mobEffect = new MobEffect(mobEffectList.getId(), i, j);
/* 487 */             if ((paramInt & 0x4000) != 0) mobEffect.setSplash(true); 
/* 488 */             arrayList.add(mobEffect);
/*     */           } 
/*     */         } 
/*     */       } 
/* 492 */     }  return arrayList;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int a(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 578 */     if (paramBoolean3) {
/* 579 */       if (!a(paramInt1, paramInt2)) {
/* 580 */         return 0;
/*     */       }
/* 582 */     } else if (paramBoolean1) {
/* 583 */       paramInt1 &= 1 << paramInt2 ^ 0xFFFFFFFF;
/* 584 */     } else if (paramBoolean2) {
/* 585 */       if ((paramInt1 & 1 << paramInt2) == 0) {
/* 586 */         paramInt1 |= 1 << paramInt2;
/*     */       } else {
/* 588 */         paramInt1 &= 1 << paramInt2 ^ 0xFFFFFFFF;
/*     */       } 
/*     */     } else {
/* 591 */       paramInt1 |= 1 << paramInt2;
/*     */     } 
/* 593 */     return paramInt1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int a(int paramInt, String paramString) {
/* 598 */     byte b1 = 0;
/* 599 */     int i = paramString.length();
/*     */     
/* 601 */     boolean bool1 = false;
/* 602 */     boolean bool2 = false;
/* 603 */     boolean bool3 = false;
/* 604 */     boolean bool4 = false;
/* 605 */     int j = 0;
/* 606 */     for (byte b2 = b1; b2 < i; b2++) {
/*     */       
/* 608 */       char c = paramString.charAt(b2);
/* 609 */       if (c >= '0' && c <= '9') {
/* 610 */         j *= 10;
/* 611 */         j += c - 48;
/* 612 */         bool1 = true;
/* 613 */       } else if (c == '!') {
/* 614 */         if (bool1) {
/* 615 */           paramInt = a(paramInt, j, bool3, bool2, bool4);
/* 616 */           bool1 = bool3 = bool2 = bool4 = false;
/* 617 */           j = 0;
/*     */         } 
/*     */         
/* 620 */         bool2 = true;
/* 621 */       } else if (c == '-') {
/* 622 */         if (bool1) {
/* 623 */           paramInt = a(paramInt, j, bool3, bool2, bool4);
/* 624 */           bool1 = bool3 = bool2 = bool4 = false;
/* 625 */           j = 0;
/*     */         } 
/*     */         
/* 628 */         bool3 = true;
/* 629 */       } else if (c == '+') {
/* 630 */         if (bool1) {
/* 631 */           paramInt = a(paramInt, j, bool3, bool2, bool4);
/* 632 */           bool1 = bool3 = bool2 = bool4 = false;
/* 633 */           j = 0;
/*     */         } 
/* 635 */       } else if (c == '&') {
/* 636 */         if (bool1) {
/* 637 */           paramInt = a(paramInt, j, bool3, bool2, bool4);
/* 638 */           bool1 = bool3 = bool2 = bool4 = false;
/* 639 */           j = 0;
/*     */         } 
/* 641 */         bool4 = true;
/*     */       } 
/*     */     } 
/* 644 */     if (bool1) {
/* 645 */       paramInt = a(paramInt, j, bool3, bool2, bool4);
/*     */     }
/*     */     
/* 648 */     return paramInt & 0x7FFF;
/*     */   }
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
/*     */   public static int a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 663 */     return (a(paramInt1, paramInt2) ? 16 : 0) | (a(paramInt1, paramInt3) ? 8 : 0) | (a(paramInt1, paramInt4) ? 4 : 0) | (a(paramInt1, paramInt5) ? 2 : 0) | (a(paramInt1, paramInt6) ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PotionBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */