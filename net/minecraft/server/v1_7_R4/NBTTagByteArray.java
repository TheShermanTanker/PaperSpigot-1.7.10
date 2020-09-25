/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class NBTTagByteArray extends NBTBase {
/*    */   private byte[] data;
/*    */   
/*    */   NBTTagByteArray() {}
/*    */   
/*    */   public NBTTagByteArray(byte[] abyte) {
/* 15 */     this.data = abyte;
/*    */   }
/*    */   
/*    */   void write(DataOutput dataoutput) throws IOException {
/* 19 */     dataoutput.writeInt(this.data.length);
/* 20 */     dataoutput.write(this.data);
/*    */   }
/*    */   
/*    */   void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
/* 24 */     int j = datainput.readInt();
/* 25 */     Preconditions.checkArgument((j < 16777216));
/*    */     
/* 27 */     nbtreadlimiter.a((8 * j));
/* 28 */     this.data = new byte[j];
/* 29 */     datainput.readFully(this.data);
/*    */   }
/*    */   
/*    */   public byte getTypeId() {
/* 33 */     return 7;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     return "[" + this.data.length + " bytes]";
/*    */   }
/*    */   
/*    */   public NBTBase clone() {
/* 41 */     byte[] abyte = new byte[this.data.length];
/*    */     
/* 43 */     System.arraycopy(this.data, 0, abyte, 0, this.data.length);
/* 44 */     return new NBTTagByteArray(abyte);
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 48 */     return super.equals(object) ? Arrays.equals(this.data, ((NBTTagByteArray)object).data) : false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 52 */     return super.hashCode() ^ Arrays.hashCode(this.data);
/*    */   }
/*    */   
/*    */   public byte[] c() {
/* 56 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagByteArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */