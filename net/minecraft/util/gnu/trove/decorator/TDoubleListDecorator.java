/*     */ package net.minecraft.util.gnu.trove.decorator;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.gnu.trove.list.TDoubleList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TDoubleListDecorator
/*     */   extends AbstractList<Double>
/*     */   implements List<Double>, Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected TDoubleList list;
/*     */   
/*     */   public TDoubleListDecorator() {}
/*     */   
/*     */   public TDoubleListDecorator(TDoubleList list) {
/*  70 */     this.list = list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TDoubleList getList() {
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
/*     */   public Double get(int index) {
/*  92 */     double value = this.list.get(index);
/*  93 */     if (value == this.list.getNoEntryValue()) return null; 
/*  94 */     return Double.valueOf(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Double set(int index, Double value) {
/* 100 */     double previous_value = this.list.set(index, value.doubleValue());
/* 101 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 102 */     return Double.valueOf(previous_value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, Double value) {
/* 108 */     this.list.insert(index, value.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Double remove(int index) {
/* 114 */     double previous_value = this.list.removeAt(index);
/* 115 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 116 */     return Double.valueOf(previous_value);
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
/* 128 */     this.list = (TDoubleList)in.readObject();
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\decorator\TDoubleListDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */