/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FreeConnectionBuffer
/*     */ {
/*     */   private PooledConnection[] conns;
/*     */   private int removeIndex;
/*     */   private int addIndex;
/*     */   private int size;
/*     */   
/*     */   protected FreeConnectionBuffer(int capacity) {
/*  51 */     this.conns = new PooledConnection[capacity];
/*     */   }
/*     */   
/*     */   protected int getCapacity() {
/*  55 */     return this.conns.length;
/*     */   }
/*     */   
/*     */   protected int size() {
/*  59 */     return this.size;
/*     */   }
/*     */   
/*     */   protected boolean isEmpty() {
/*  63 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void add(PooledConnection pc) {
/*  70 */     this.conns[this.addIndex] = pc;
/*  71 */     this.addIndex = inc(this.addIndex);
/*  72 */     this.size++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PooledConnection remove() {
/*  79 */     PooledConnection[] items = this.conns;
/*  80 */     PooledConnection pc = items[this.removeIndex];
/*  81 */     items[this.removeIndex] = null;
/*  82 */     this.removeIndex = inc(this.removeIndex);
/*  83 */     this.size--;
/*  84 */     return pc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<PooledConnection> getShallowCopy() {
/*  92 */     List<PooledConnection> copy = new ArrayList<PooledConnection>(this.conns.length);
/*  93 */     for (int i = 0; i < this.conns.length; i++) {
/*  94 */       if (this.conns[i] != null) {
/*  95 */         copy.add(this.conns[i]);
/*     */       }
/*     */     } 
/*  98 */     return copy;
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
/*     */   protected void setShallowCopy(List<PooledConnection> copy) {
/* 112 */     this.removeIndex = 0;
/* 113 */     this.addIndex = 0;
/* 114 */     this.size = 0;
/*     */     
/*     */     int i;
/* 117 */     for (i = 0; i < this.conns.length; i++) {
/* 118 */       this.conns[i] = null;
/*     */     }
/*     */ 
/*     */     
/* 122 */     for (i = 0; i < copy.size(); i++) {
/* 123 */       add(copy.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCapacity(int newCapacity) {
/* 132 */     if (newCapacity > this.conns.length) {
/*     */       
/* 134 */       List<PooledConnection> copy = getShallowCopy();
/*     */ 
/*     */       
/* 137 */       this.removeIndex = 0;
/* 138 */       this.addIndex = 0;
/* 139 */       this.size = 0;
/*     */       
/* 141 */       this.conns = new PooledConnection[newCapacity];
/*     */ 
/*     */       
/* 144 */       for (int i = 0; i < copy.size(); i++) {
/* 145 */         add(copy.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int inc(int i) {
/* 154 */     return (++i == this.conns.length) ? 0 : i;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\FreeConnectionBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */