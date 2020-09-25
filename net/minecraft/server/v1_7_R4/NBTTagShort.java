/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagShort
/*    */   extends NBTNumber
/*    */ {
/*    */   private short data;
/*    */   
/*    */   public NBTTagShort() {}
/*    */   
/*    */   public NBTTagShort(short paramShort) {
/* 16 */     this.data = paramShort;
/*    */   }
/*    */ 
/*    */   
/*    */   void write(DataOutput paramDataOutput) {
/* 21 */     paramDataOutput.writeShort(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 26 */     paramNBTReadLimiter.a(16L);
/* 27 */     this.data = paramDataInput.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getTypeId() {
/* 32 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "" + this.data + "s";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 42 */     return new NBTTagShort(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 47 */     if (super.equals(paramObject)) {
/* 48 */       NBTTagShort nBTTagShort = (NBTTagShort)paramObject;
/* 49 */       return (this.data == nBTTagShort.data);
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 56 */     return super.hashCode() ^ this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public long c() {
/* 61 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int d() {
/* 66 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public short e() {
/* 71 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte f() {
/* 76 */     return (byte)(this.data & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double g() {
/* 81 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float h() {
/* 86 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */