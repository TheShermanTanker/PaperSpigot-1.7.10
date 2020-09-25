/*     */ package net.minecraft.util.gnu.trove.decorator;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.gnu.trove.list.TShortList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TShortListDecorator
/*     */   extends AbstractList<Short>
/*     */   implements List<Short>, Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected TShortList list;
/*     */   
/*     */   public TShortListDecorator() {}
/*     */   
/*     */   public TShortListDecorator(TShortList list) {
/*  70 */     this.list = list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TShortList getList() {
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
/*     */   public Short get(int index) {
/*  92 */     short value = this.list.get(index);
/*  93 */     if (value == this.list.getNoEntryValue()) return null; 
/*  94 */     return Short.valueOf(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Short set(int index, Short value) {
/* 100 */     short previous_value = this.list.set(index, value.shortValue());
/* 101 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 102 */     return Short.valueOf(previous_value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, Short value) {
/* 108 */     this.list.insert(index, value.shortValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Short remove(int index) {
/* 114 */     short previous_value = this.list.removeAt(index);
/* 115 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 116 */     return Short.valueOf(previous_value);
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
/* 128 */     this.list = (TShortList)in.readObject();
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\decorator\TShortListDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */