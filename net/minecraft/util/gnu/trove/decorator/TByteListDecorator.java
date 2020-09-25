/*     */ package net.minecraft.util.gnu.trove.decorator;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.gnu.trove.list.TByteList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TByteListDecorator
/*     */   extends AbstractList<Byte>
/*     */   implements List<Byte>, Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected TByteList list;
/*     */   
/*     */   public TByteListDecorator() {}
/*     */   
/*     */   public TByteListDecorator(TByteList list) {
/*  70 */     this.list = list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TByteList getList() {
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
/*     */   public Byte get(int index) {
/*  92 */     byte value = this.list.get(index);
/*  93 */     if (value == this.list.getNoEntryValue()) return null; 
/*  94 */     return Byte.valueOf(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte set(int index, Byte value) {
/* 100 */     byte previous_value = this.list.set(index, value.byteValue());
/* 101 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 102 */     return Byte.valueOf(previous_value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, Byte value) {
/* 108 */     this.list.insert(index, value.byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte remove(int index) {
/* 114 */     byte previous_value = this.list.removeAt(index);
/* 115 */     if (previous_value == this.list.getNoEntryValue()) return null; 
/* 116 */     return Byte.valueOf(previous_value);
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
/* 128 */     this.list = (TByteList)in.readObject();
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\decorator\TByteListDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */