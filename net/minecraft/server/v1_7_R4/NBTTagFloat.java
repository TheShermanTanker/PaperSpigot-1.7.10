/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagFloat
/*    */   extends NBTNumber
/*    */ {
/*    */   private float data;
/*    */   
/*    */   NBTTagFloat() {}
/*    */   
/*    */   public NBTTagFloat(float paramFloat) {
/* 17 */     this.data = paramFloat;
/*    */   }
/*    */ 
/*    */   
/*    */   void write(DataOutput paramDataOutput) {
/* 22 */     paramDataOutput.writeFloat(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 27 */     paramNBTReadLimiter.a(32L);
/* 28 */     this.data = paramDataInput.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getTypeId() {
/* 33 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "" + this.data + "f";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 43 */     return new NBTTagFloat(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 48 */     if (super.equals(paramObject)) {
/* 49 */       NBTTagFloat nBTTagFloat = (NBTTagFloat)paramObject;
/* 50 */       return (this.data == nBTTagFloat.data);
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 57 */     return super.hashCode() ^ Float.floatToIntBits(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public long c() {
/* 62 */     return (long)this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int d() {
/* 67 */     return MathHelper.d(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public short e() {
/* 72 */     return (short)(MathHelper.d(this.data) & 0xFFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte f() {
/* 77 */     return (byte)(MathHelper.d(this.data) & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double g() {
/* 82 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float h() {
/* 87 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */