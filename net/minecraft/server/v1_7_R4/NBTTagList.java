/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class NBTTagList
/*     */   extends NBTBase {
/*  12 */   private List list = new ArrayList();
/*  13 */   private byte type = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput dataoutput) throws IOException {
/*  18 */     if (!this.list.isEmpty()) {
/*  19 */       this.type = ((NBTBase)this.list.get(0)).getTypeId();
/*     */     } else {
/*  21 */       this.type = 0;
/*     */     } 
/*     */     
/*  24 */     dataoutput.writeByte(this.type);
/*  25 */     dataoutput.writeInt(this.list.size());
/*     */     
/*  27 */     for (int i = 0; i < this.list.size(); i++) {
/*  28 */       ((NBTBase)this.list.get(i)).write(dataoutput);
/*     */     }
/*     */   }
/*     */   
/*     */   void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
/*  33 */     if (i > 512) {
/*  34 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*  36 */     nbtreadlimiter.a(8L);
/*  37 */     this.type = datainput.readByte();
/*  38 */     int j = datainput.readInt();
/*     */     
/*  40 */     this.list = new ArrayList();
/*     */     
/*  42 */     for (int k = 0; k < j; k++) {
/*  43 */       NBTBase nbtbase = NBTBase.createTag(this.type);
/*  44 */       nbtreadlimiter.a(8L);
/*     */       
/*  46 */       nbtbase.load(datainput, i + 1, nbtreadlimiter);
/*  47 */       this.list.add(nbtbase);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getTypeId() {
/*  53 */     return 9;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  57 */     String s = "[";
/*  58 */     int i = 0;
/*     */     
/*  60 */     for (Iterator<NBTBase> iterator = this.list.iterator(); iterator.hasNext(); i++) {
/*  61 */       NBTBase nbtbase = iterator.next();
/*     */       
/*  63 */       s = s + "" + i + ':' + nbtbase + ',';
/*     */     } 
/*     */     
/*  66 */     return s + "]";
/*     */   }
/*     */   
/*     */   public void add(NBTBase nbtbase) {
/*  70 */     if (this.type == 0) {
/*  71 */       this.type = nbtbase.getTypeId();
/*  72 */     } else if (this.type != nbtbase.getTypeId()) {
/*  73 */       System.err.println("WARNING: Adding mismatching tag types to tag list");
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     this.list.add(nbtbase);
/*     */   }
/*     */   
/*     */   public NBTTagCompound get(int i) {
/*  81 */     if (i >= 0 && i < this.list.size()) {
/*  82 */       NBTBase nbtbase = this.list.get(i);
/*     */       
/*  84 */       return (nbtbase.getTypeId() == 10) ? (NBTTagCompound)nbtbase : new NBTTagCompound();
/*     */     } 
/*  86 */     return new NBTTagCompound();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] c(int i) {
/*  91 */     if (i >= 0 && i < this.list.size()) {
/*  92 */       NBTBase nbtbase = this.list.get(i);
/*     */       
/*  94 */       return (nbtbase.getTypeId() == 11) ? ((NBTTagIntArray)nbtbase).c() : new int[0];
/*     */     } 
/*  96 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public double d(int i) {
/* 101 */     if (i >= 0 && i < this.list.size()) {
/* 102 */       NBTBase nbtbase = this.list.get(i);
/*     */       
/* 104 */       return (nbtbase.getTypeId() == 6) ? ((NBTTagDouble)nbtbase).g() : 0.0D;
/*     */     } 
/* 106 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float e(int i) {
/* 111 */     if (i >= 0 && i < this.list.size()) {
/* 112 */       NBTBase nbtbase = this.list.get(i);
/*     */       
/* 114 */       return (nbtbase.getTypeId() == 5) ? ((NBTTagFloat)nbtbase).h() : 0.0F;
/*     */     } 
/* 116 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(int i) {
/* 121 */     if (i >= 0 && i < this.list.size()) {
/* 122 */       NBTBase nbtbase = this.list.get(i);
/*     */       
/* 124 */       return (nbtbase.getTypeId() == 8) ? nbtbase.a_() : nbtbase.toString();
/*     */     } 
/* 126 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 131 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public NBTBase clone() {
/* 135 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 137 */     nbttaglist.type = this.type;
/* 138 */     Iterator<NBTBase> iterator = this.list.iterator();
/*     */     
/* 140 */     while (iterator.hasNext()) {
/* 141 */       NBTBase nbtbase = iterator.next();
/* 142 */       NBTBase nbtbase1 = nbtbase.clone();
/*     */       
/* 144 */       nbttaglist.list.add(nbtbase1);
/*     */     } 
/*     */     
/* 147 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 151 */     if (super.equals(object)) {
/* 152 */       NBTTagList nbttaglist = (NBTTagList)object;
/*     */       
/* 154 */       if (this.type == nbttaglist.type) {
/* 155 */         return this.list.equals(nbttaglist.list);
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 163 */     return super.hashCode() ^ this.list.hashCode();
/*     */   }
/*     */   
/*     */   public int d() {
/* 167 */     return this.type;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */