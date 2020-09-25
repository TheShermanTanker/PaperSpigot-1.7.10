/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.UnsafeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalSelector
/*     */ {
/*  14 */   private static final Logger a = LogManager.getLogger();
/*     */   
/*  16 */   private List b = (List)new UnsafeList();
/*  17 */   private List c = (List)new UnsafeList();
/*     */   
/*     */   private final MethodProfiler d;
/*     */   private int e;
/*  21 */   private int f = 3;
/*     */   
/*     */   public PathfinderGoalSelector(MethodProfiler methodprofiler) {
/*  24 */     this.d = methodprofiler;
/*     */   }
/*     */   
/*     */   public void a(int i, PathfinderGoal pathfindergoal) {
/*  28 */     this.b.add(new PathfinderGoalSelectorItem(this, i, pathfindergoal));
/*     */   }
/*     */   
/*     */   public void a(PathfinderGoal pathfindergoal) {
/*  32 */     Iterator<PathfinderGoalSelectorItem> iterator = this.b.iterator();
/*     */     
/*  34 */     while (iterator.hasNext()) {
/*  35 */       PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator.next();
/*  36 */       PathfinderGoal pathfindergoal1 = pathfindergoalselectoritem.a;
/*     */       
/*  38 */       if (pathfindergoal1 == pathfindergoal) {
/*  39 */         if (this.c.contains(pathfindergoalselectoritem)) {
/*  40 */           pathfindergoal1.d();
/*  41 */           this.c.remove(pathfindergoalselectoritem);
/*     */         } 
/*     */         
/*  44 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*  54 */     if (this.e++ % this.f == 0) {
/*  55 */       Iterator<PathfinderGoalSelectorItem> iterator1 = this.b.iterator();
/*     */       
/*  57 */       while (iterator1.hasNext()) {
/*  58 */         PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator1.next();
/*  59 */         boolean flag = this.c.contains(pathfindergoalselectoritem);
/*     */         
/*  61 */         if (flag) {
/*  62 */           if (b(pathfindergoalselectoritem) && a(pathfindergoalselectoritem)) {
/*     */             continue;
/*     */           }
/*     */           
/*  66 */           pathfindergoalselectoritem.a.d();
/*  67 */           this.c.remove(pathfindergoalselectoritem);
/*     */         } 
/*     */         
/*  70 */         if (b(pathfindergoalselectoritem) && pathfindergoalselectoritem.a.a()) {
/*     */ 
/*     */           
/*  73 */           pathfindergoalselectoritem.a.c();
/*     */           
/*  75 */           this.c.add(pathfindergoalselectoritem);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  79 */       Iterator<PathfinderGoalSelectorItem> iterator1 = this.c.iterator();
/*     */       
/*  81 */       while (iterator1.hasNext()) {
/*  82 */         PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator1.next();
/*  83 */         if (!pathfindergoalselectoritem.a.b()) {
/*  84 */           pathfindergoalselectoritem.a.d();
/*  85 */           iterator1.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     this.d.a("goalStart");
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
/* 102 */     this.d.b();
/* 103 */     this.d.a("goalTick");
/* 104 */     Iterator<PathfinderGoalSelectorItem> iterator = this.c.iterator();
/*     */     
/* 106 */     while (iterator.hasNext()) {
/* 107 */       PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator.next();
/* 108 */       pathfindergoalselectoritem.a.e();
/*     */     } 
/*     */     
/* 111 */     this.d.b();
/*     */   }
/*     */   
/*     */   private boolean a(PathfinderGoalSelectorItem pathfindergoalselectoritem) {
/* 115 */     this.d.a("canContinue");
/* 116 */     boolean flag = pathfindergoalselectoritem.a.b();
/*     */     
/* 118 */     this.d.b();
/* 119 */     return flag;
/*     */   }
/*     */   
/*     */   private boolean b(PathfinderGoalSelectorItem pathfindergoalselectoritem) {
/* 123 */     this.d.a("canUse");
/* 124 */     Iterator<PathfinderGoalSelectorItem> iterator = this.b.iterator();
/*     */     
/* 126 */     while (iterator.hasNext()) {
/* 127 */       PathfinderGoalSelectorItem pathfindergoalselectoritem1 = iterator.next();
/*     */       
/* 129 */       if (pathfindergoalselectoritem1 != pathfindergoalselectoritem) {
/* 130 */         if (pathfindergoalselectoritem.b >= pathfindergoalselectoritem1.b) {
/*     */           
/* 132 */           if (!a(pathfindergoalselectoritem, pathfindergoalselectoritem1) && this.c.contains(pathfindergoalselectoritem1)) {
/* 133 */             this.d.b();
/* 134 */             ((UnsafeList.Itr)iterator).valid = false;
/* 135 */             return false;
/*     */           }  continue;
/*     */         } 
/* 138 */         if (!pathfindergoalselectoritem1.a.i() && this.c.contains(pathfindergoalselectoritem1)) {
/* 139 */           this.d.b();
/* 140 */           ((UnsafeList.Itr)iterator).valid = false;
/* 141 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     this.d.b();
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   private boolean a(PathfinderGoalSelectorItem pathfindergoalselectoritem, PathfinderGoalSelectorItem pathfindergoalselectoritem1) {
/* 151 */     return ((pathfindergoalselectoritem.a.j() & pathfindergoalselectoritem1.a.j()) == 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */