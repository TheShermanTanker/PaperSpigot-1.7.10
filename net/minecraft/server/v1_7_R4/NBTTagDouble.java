/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagDouble
/*    */   extends NBTNumber
/*    */ {
/*    */   private double data;
/*    */   
/*    */   NBTTagDouble() {}
/*    */   
/*    */   public NBTTagDouble(double paramDouble) {
/* 17 */     this.data = paramDouble;
/*    */   }
/*    */ 
/*    */   
/*    */   void write(DataOutput paramDataOutput) {
/* 22 */     paramDataOutput.writeDouble(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 27 */     paramNBTReadLimiter.a(64L);
/* 28 */     this.data = paramDataInput.readDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getTypeId() {
/* 33 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "" + this.data + "d";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 43 */     return new NBTTagDouble(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 48 */     if (super.equals(paramObject)) {
/* 49 */       NBTTagDouble nBTTagDouble = (NBTTagDouble)paramObject;
/* 50 */       return (this.data == nBTTagDouble.data);
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 57 */     long l = Double.doubleToLongBits(this.data);
/* 58 */     return super.hashCode() ^ (int)(l ^ l >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public long c() {
/* 63 */     return (long)Math.floor(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int d() {
/* 68 */     return MathHelper.floor(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public short e() {
/* 73 */     return (short)(MathHelper.floor(this.data) & 0xFFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte f() {
/* 78 */     return (byte)(MathHelper.floor(this.data) & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double g() {
/* 83 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float h() {
/* 88 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */