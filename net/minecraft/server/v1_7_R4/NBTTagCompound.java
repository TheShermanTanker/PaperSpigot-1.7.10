/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NBTTagCompound
/*     */   extends NBTBase {
/*  14 */   private static final Logger b = LogManager.getLogger();
/*     */   
/*  16 */   private Map map = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput paramDataOutput) {
/*  23 */     for (String str : this.map.keySet()) {
/*  24 */       NBTBase nBTBase = (NBTBase)this.map.get(str);
/*  25 */       a(str, nBTBase, paramDataOutput);
/*     */     } 
/*  27 */     paramDataOutput.writeByte(0);
/*     */   }
/*     */ 
/*     */   
/*     */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/*  32 */     if (paramInt > 512) {
/*  33 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*  35 */     this.map.clear();
/*     */     byte b;
/*  37 */     while ((b = a(paramDataInput, paramNBTReadLimiter)) != 0) {
/*  38 */       String str = b(paramDataInput, paramNBTReadLimiter);
/*     */       
/*  40 */       paramNBTReadLimiter.a((16 * str.length()));
/*  41 */       NBTBase nBTBase = a(b, str, paramDataInput, paramInt + 1, paramNBTReadLimiter);
/*  42 */       this.map.put(str, nBTBase);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set c() {
/*  47 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getTypeId() {
/*  52 */     return 10;
/*     */   }
/*     */   
/*     */   public void set(String paramString, NBTBase paramNBTBase) {
/*  56 */     this.map.put(paramString, paramNBTBase);
/*     */   }
/*     */   
/*     */   public void setByte(String paramString, byte paramByte) {
/*  60 */     this.map.put(paramString, new NBTTagByte(paramByte));
/*     */   }
/*     */   
/*     */   public void setShort(String paramString, short paramShort) {
/*  64 */     this.map.put(paramString, new NBTTagShort(paramShort));
/*     */   }
/*     */   
/*     */   public void setInt(String paramString, int paramInt) {
/*  68 */     this.map.put(paramString, new NBTTagInt(paramInt));
/*     */   }
/*     */   
/*     */   public void setLong(String paramString, long paramLong) {
/*  72 */     this.map.put(paramString, new NBTTagLong(paramLong));
/*     */   }
/*     */   
/*     */   public void setFloat(String paramString, float paramFloat) {
/*  76 */     this.map.put(paramString, new NBTTagFloat(paramFloat));
/*     */   }
/*     */   
/*     */   public void setDouble(String paramString, double paramDouble) {
/*  80 */     this.map.put(paramString, new NBTTagDouble(paramDouble));
/*     */   }
/*     */   
/*     */   public void setString(String paramString1, String paramString2) {
/*  84 */     this.map.put(paramString1, new NBTTagString(paramString2));
/*     */   }
/*     */   
/*     */   public void setByteArray(String paramString, byte[] paramArrayOfbyte) {
/*  88 */     this.map.put(paramString, new NBTTagByteArray(paramArrayOfbyte));
/*     */   }
/*     */   
/*     */   public void setIntArray(String paramString, int[] paramArrayOfint) {
/*  92 */     this.map.put(paramString, new NBTTagIntArray(paramArrayOfint));
/*     */   }
/*     */   
/*     */   public void setBoolean(String paramString, boolean paramBoolean) {
/*  96 */     setByte(paramString, paramBoolean ? 1 : 0);
/*     */   }
/*     */   
/*     */   public NBTBase get(String paramString) {
/* 100 */     return (NBTBase)this.map.get(paramString);
/*     */   }
/*     */   
/*     */   public byte b(String paramString) {
/* 104 */     NBTBase nBTBase = (NBTBase)this.map.get(paramString);
/* 105 */     if (nBTBase != null) {
/* 106 */       return nBTBase.getTypeId();
/*     */     }
/* 108 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean hasKey(String paramString) {
/* 112 */     return this.map.containsKey(paramString);
/*     */   }
/*     */   
/*     */   public boolean hasKeyOfType(String paramString, int paramInt) {
/* 116 */     byte b = b(paramString);
/* 117 */     if (b == paramInt) {
/* 118 */       return true;
/*     */     }
/* 120 */     if (paramInt == 99) {
/* 121 */       return (b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6);
/*     */     }
/*     */     
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public byte getByte(String paramString) {
/*     */     try {
/* 129 */       if (!this.map.containsKey(paramString)) return 0; 
/* 130 */       return ((NBTNumber)this.map.get(paramString)).f();
/* 131 */     } catch (ClassCastException classCastException) {
/* 132 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public short getShort(String paramString) {
/*     */     try {
/* 138 */       if (!this.map.containsKey(paramString)) return 0; 
/* 139 */       return ((NBTNumber)this.map.get(paramString)).e();
/* 140 */     } catch (ClassCastException classCastException) {
/* 141 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getInt(String paramString) {
/*     */     try {
/* 147 */       if (!this.map.containsKey(paramString)) return 0; 
/* 148 */       return ((NBTNumber)this.map.get(paramString)).d();
/* 149 */     } catch (ClassCastException classCastException) {
/* 150 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLong(String paramString) {
/*     */     try {
/* 156 */       if (!this.map.containsKey(paramString)) return 0L; 
/* 157 */       return ((NBTNumber)this.map.get(paramString)).c();
/* 158 */     } catch (ClassCastException classCastException) {
/* 159 */       return 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getFloat(String paramString) {
/*     */     try {
/* 165 */       if (!this.map.containsKey(paramString)) return 0.0F; 
/* 166 */       return ((NBTNumber)this.map.get(paramString)).h();
/* 167 */     } catch (ClassCastException classCastException) {
/* 168 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getDouble(String paramString) {
/*     */     try {
/* 174 */       if (!this.map.containsKey(paramString)) return 0.0D; 
/* 175 */       return ((NBTNumber)this.map.get(paramString)).g();
/* 176 */     } catch (ClassCastException classCastException) {
/* 177 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getString(String paramString) {
/*     */     try {
/* 183 */       if (!this.map.containsKey(paramString)) return ""; 
/* 184 */       return ((NBTBase)this.map.get(paramString)).a_();
/* 185 */     } catch (ClassCastException classCastException) {
/* 186 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(String paramString) {
/*     */     try {
/* 192 */       if (!this.map.containsKey(paramString)) return new byte[0]; 
/* 193 */       return ((NBTTagByteArray)this.map.get(paramString)).c();
/* 194 */     } catch (ClassCastException classCastException) {
/* 195 */       throw new ReportedException(a(paramString, 7, classCastException));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int[] getIntArray(String paramString) {
/*     */     try {
/* 201 */       if (!this.map.containsKey(paramString)) return new int[0]; 
/* 202 */       return ((NBTTagIntArray)this.map.get(paramString)).c();
/* 203 */     } catch (ClassCastException classCastException) {
/* 204 */       throw new ReportedException(a(paramString, 11, classCastException));
/*     */     } 
/*     */   }
/*     */   
/*     */   public NBTTagCompound getCompound(String paramString) {
/*     */     try {
/* 210 */       if (!this.map.containsKey(paramString)) return new NBTTagCompound(); 
/* 211 */       return (NBTTagCompound)this.map.get(paramString);
/* 212 */     } catch (ClassCastException classCastException) {
/* 213 */       throw new ReportedException(a(paramString, 10, classCastException));
/*     */     } 
/*     */   }
/*     */   
/*     */   public NBTTagList getList(String paramString, int paramInt) {
/*     */     try {
/* 219 */       if (b(paramString) != 9) return new NBTTagList(); 
/* 220 */       NBTTagList nBTTagList = (NBTTagList)this.map.get(paramString);
/* 221 */       if (nBTTagList.size() > 0 && nBTTagList.d() != paramInt) {
/* 222 */         return new NBTTagList();
/*     */       }
/* 224 */       return nBTTagList;
/* 225 */     } catch (ClassCastException classCastException) {
/* 226 */       throw new ReportedException(a(paramString, 9, classCastException));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String paramString) {
/* 231 */     return (getByte(paramString) != 0);
/*     */   }
/*     */   
/*     */   public void remove(String paramString) {
/* 235 */     this.map.remove(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 240 */     String str = "{";
/* 241 */     for (String str1 : this.map.keySet()) {
/* 242 */       str = str + str1 + ':' + this.map.get(str1) + ',';
/*     */     }
/* 244 */     return str + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 261 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */   private CrashReport a(String paramString, int paramInt, ClassCastException paramClassCastException) {
/* 265 */     CrashReport crashReport = CrashReport.a(paramClassCastException, "Reading NBT data");
/* 266 */     CrashReportSystemDetails crashReportSystemDetails = crashReport.a("Corrupt NBT tag", 1);
/*     */     
/* 268 */     crashReportSystemDetails.a("Tag type found", new CrashReportCorruptNBTTag(this, paramString));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     crashReportSystemDetails.a("Tag type expected", new CrashReportCorruptNBTTag2(this, paramInt));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     crashReportSystemDetails.a("Tag name", paramString);
/*     */     
/* 282 */     return crashReport;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTBase clone() {
/* 287 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 288 */     for (String str : this.map.keySet()) {
/* 289 */       nBTTagCompound.set(str, ((NBTBase)this.map.get(str)).clone());
/*     */     }
/* 291 */     return nBTTagCompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 296 */     if (super.equals(paramObject)) {
/* 297 */       NBTTagCompound nBTTagCompound = (NBTTagCompound)paramObject;
/* 298 */       return this.map.entrySet().equals(nBTTagCompound.map.entrySet());
/*     */     } 
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 305 */     return super.hashCode() ^ this.map.hashCode();
/*     */   }
/*     */   
/*     */   private static void a(String paramString, NBTBase paramNBTBase, DataOutput paramDataOutput) {
/* 309 */     paramDataOutput.writeByte(paramNBTBase.getTypeId());
/* 310 */     if (paramNBTBase.getTypeId() == 0)
/*     */       return; 
/* 312 */     paramDataOutput.writeUTF(paramString);
/*     */     
/* 314 */     paramNBTBase.write(paramDataOutput);
/*     */   }
/*     */   
/*     */   private static byte a(DataInput paramDataInput, NBTReadLimiter paramNBTReadLimiter) {
/* 318 */     return paramDataInput.readByte();
/*     */   }
/*     */   
/*     */   private static String b(DataInput paramDataInput, NBTReadLimiter paramNBTReadLimiter) {
/* 322 */     return paramDataInput.readUTF();
/*     */   }
/*     */   
/*     */   static NBTBase a(byte paramByte, String paramString, DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) {
/* 326 */     NBTBase nBTBase = NBTBase.createTag(paramByte);
/*     */     
/*     */     try {
/* 329 */       nBTBase.load(paramDataInput, paramInt, paramNBTReadLimiter);
/* 330 */     } catch (IOException iOException) {
/* 331 */       CrashReport crashReport = CrashReport.a(iOException, "Loading NBT data");
/* 332 */       CrashReportSystemDetails crashReportSystemDetails = crashReport.a("NBT Tag");
/* 333 */       crashReportSystemDetails.a("Tag name", paramString);
/* 334 */       crashReportSystemDetails.a("Tag type", Byte.valueOf(paramByte));
/* 335 */       throw new ReportedException(crashReport);
/*     */     } 
/*     */     
/* 338 */     return nBTBase;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTTagCompound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */