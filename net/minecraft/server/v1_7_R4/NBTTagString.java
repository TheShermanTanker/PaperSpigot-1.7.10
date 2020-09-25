/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagString extends NBTBase {
/*    */   private String data;
/*    */   
/*    */   public NBTTagString() {
/* 10 */     this.data = "";
/*    */   }
/*    */   
/*    */   public NBTTagString(String paramString) {
/* 14 */     this.data = paramString;
/* 15 */     if (paramString == null) throw new IllegalArgumentException("Empty string not allowed");
/*    */   
/*    */   }
/*    */   
/*    */   void write(DataOutput paramDataOutput) {
/* 20 */     paramDataOutput.writeUTF(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 25 */     this.data = paramDataInput.readUTF();
/* 26 */     paramNBTReadLimiter.a((16 * this.data.length()));
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getTypeId() {
/* 31 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return "\"" + this.data + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 41 */     return new NBTTagString(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     if (super.equals(paramObject)) {
/* 47 */       NBTTagString nBTTagString = (NBTTagString)paramObject;
/* 48 */       return ((this.data == null && nBTTagString.data == null) || (this.data != null && this.data.equals(nBTTagString.data)));
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     return super.hashCode() ^ this.data.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String a_() {
/* 60 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */