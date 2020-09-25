/*     */ package net.minecraft.util.gnu.trove.decorator;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.gnu.trove.list.TIntList;
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
/*     */ public class TIntListDecorator
/*     */   extends AbstractList<Integer>
/*     */   implements List<Integer>, Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected TIntList list;
/*     */   
/*     */   public TIntListDecorator() {}
/*     */   
/*     */   public TIntListDecorator(TIntList list) {
/*  70 */     this.list = list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntList getList() {
/*  80 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  86 */     return this.list.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer get(int index) {
/*  92 */     int value = this.list.get(index);
/*  93 */     if (value == this.list.getNoEntryValue()) return null; 
/*  94 */     return Integer.valueOf(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer set(int index, Integer value) {
/* 100 */     int previous_value = this.list.set(index, value.intValue());
/* 101 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 102 */     return Integer.valueOf(previous_value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, Integer value) {
/* 108 */     this.list.insert(index, value.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer remove(int index) {
/* 114 */     int previous_value = this.list.removeAt(index);
/* 115 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 116 */     return Integer.valueOf(previous_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 125 */     in.readByte();
/*     */ 
/*     */     
/* 128 */     this.list = (TIntList)in.readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 135 */     out.writeByte(0);
/*     */ 
/*     */     
/* 138 */     out.writeObject(this.list);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\decorator\TIntListDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */