/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class NBTTagIntArray extends NBTBase {
/*    */   private int[] data;
/*    */   
/*    */   NBTTagIntArray() {}
/*    */   
/*    */   public NBTTagIntArray(int[] aint) {
/* 15 */     this.data = aint;
/*    */   }
/*    */   
/*    */   void write(DataOutput dataoutput) throws IOException {
/* 19 */     dataoutput.writeInt(this.data.length);
/*    */     
/* 21 */     for (int i = 0; i < this.data.length; i++) {
/* 22 */       dataoutput.writeInt(this.data[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
/* 27 */     int j = datainput.readInt();
/* 28 */     Preconditions.checkArgument((j < 16777216));
/*    */     
/* 30 */     nbtreadlimiter.a((32 * j));
/* 31 */     this.data = new int[j];
/*    */     
/* 33 */     for (int k = 0; k < j; k++) {
/* 34 */       this.data[k] = datainput.readInt();
/*    */     }
/*    */   }
/*    */   
/*    */   public byte getTypeId() {
/* 39 */     return 11;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 43 */     String s = "[";
/* 44 */     int[] aint = this.data;
/* 45 */     int i = aint.length;
/*    */     
/* 47 */     for (int j = 0; j < i; j++) {
/* 48 */       int k = aint[j];
/*    */       
/* 50 */       s = s + k + ",";
/*    */     } 
/*    */     
/* 53 */     return s + "]";
/*    */   }
/*    */   
/*    */   public NBTBase clone() {
/* 57 */     int[] aint = new int[this.data.length];
/*    */     
/* 59 */     System.arraycopy(this.data, 0, aint, 0, this.data.length);
/* 60 */     return new NBTTagIntArray(aint);
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 64 */     return super.equals(object) ? Arrays.equals(this.data, ((NBTTagIntArray)object).data) : false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 68 */     return super.hashCode() ^ Arrays.hashCode(this.data);
/*    */   }
/*    */   
/*    */   public int[] c() {
/* 72 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagIntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */