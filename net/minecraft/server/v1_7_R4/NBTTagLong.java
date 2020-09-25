/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagLong
/*    */   extends NBTNumber
/*    */ {
/*    */   private long data;
/*    */   
/*    */   NBTTagLong() {}
/*    */   
/*    */   public NBTTagLong(long paramLong) {
/* 16 */     this.data = paramLong;
/*    */   }
/*    */ 
/*    */   
/*    */   void write(DataOutput paramDataOutput) {
/* 21 */     paramDataOutput.writeLong(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 26 */     paramNBTReadLimiter.a(64L);
/* 27 */     this.data = paramDataInput.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getTypeId() {
/* 32 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "" + this.data + "L";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 42 */     return new NBTTagLong(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 47 */     if (super.equals(paramObject)) {
/* 48 */       NBTTagLong nBTTagLong = (NBTTagLong)paramObject;
/* 49 */       return (this.data == nBTTagLong.data);
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 56 */     return super.hashCode() ^ (int)(this.data ^ this.data >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public long c() {
/* 61 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int d() {
/* 66 */     return (int)(this.data & 0xFFFFFFFFFFFFFFFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public short e() {
/* 71 */     return (short)(int)(this.data & 0xFFFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte f() {
/* 76 */     return (byte)(int)(this.data & 0xFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public double g() {
/* 81 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float h() {
/* 86 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */