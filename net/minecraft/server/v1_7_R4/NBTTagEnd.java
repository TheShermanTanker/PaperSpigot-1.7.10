/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagEnd
/*    */   extends NBTBase
/*    */ {
/*    */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {}
/*    */   
/*    */   void write(DataOutput paramDataOutput) {}
/*    */   
/*    */   public byte getTypeId() {
/* 21 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "END";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTBase clone() {
/* 31 */     return new NBTTagEnd();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */