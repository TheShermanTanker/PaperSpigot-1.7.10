/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagByte
/*    */   extends NBTNumber
/*    */ {
/*    */   private byte data;
/*    */   
/*    */   NBTTagByte() {}
/*    */   
/*    */   public NBTTagByte(byte paramByte) {
/* 16 */     this.data = paramByte;
/*    */   }
/*    */ 
/*    */   
/*    */   void write(DataOutput paramDataOutput) {
/* 21 */     paramDataOutput.writeByte(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 26 */     paramNBTReadLimiter.a(8L);
/* 27 */     this.data = paramDataInput.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getTypeId() {
/* 32 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "" + this.data + "b";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 42 */     return new NBTTagByte(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 47 */     if (super.equals(paramObject)) {
/* 48 */       NBTTagByte nBTTagByte = (NBTTagByte)paramObject;
/* 49 */       return (this.data == nBTTagByte.data);
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
/* 71 */     return (short)this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte f() {
/* 76 */     return this.data;
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */