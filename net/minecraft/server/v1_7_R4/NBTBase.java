/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public abstract class NBTBase
/*    */ {
/*  9 */   public static final String[] a = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static NBTBase createTag(byte b0) {
/* 22 */     switch (b0) {
/*    */       case 0:
/* 24 */         return new NBTTagEnd();
/*    */       
/*    */       case 1:
/* 27 */         return new NBTTagByte();
/*    */       
/*    */       case 2:
/* 30 */         return new NBTTagShort();
/*    */       
/*    */       case 3:
/* 33 */         return new NBTTagInt();
/*    */       
/*    */       case 4:
/* 36 */         return new NBTTagLong();
/*    */       
/*    */       case 5:
/* 39 */         return new NBTTagFloat();
/*    */       
/*    */       case 6:
/* 42 */         return new NBTTagDouble();
/*    */       
/*    */       case 7:
/* 45 */         return new NBTTagByteArray();
/*    */       
/*    */       case 8:
/* 48 */         return new NBTTagString();
/*    */       
/*    */       case 9:
/* 51 */         return new NBTTagList();
/*    */       
/*    */       case 10:
/* 54 */         return new NBTTagCompound();
/*    */       
/*    */       case 11:
/* 57 */         return new NBTTagIntArray();
/*    */     } 
/*    */     
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object object) {
/* 67 */     if (!(object instanceof NBTBase)) {
/* 68 */       return false;
/*    */     }
/* 70 */     NBTBase nbtbase = (NBTBase)object;
/*    */     
/* 72 */     return (getTypeId() == nbtbase.getTypeId());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return getTypeId();
/*    */   }
/*    */   
/*    */   protected String a_() {
/* 81 */     return toString();
/*    */   }
/*    */   
/*    */   abstract void write(DataOutput paramDataOutput) throws IOException;
/*    */   
/*    */   abstract void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException;
/*    */   
/*    */   public abstract String toString();
/*    */   
/*    */   public abstract byte getTypeId();
/*    */   
/*    */   public abstract NBTBase clone();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */