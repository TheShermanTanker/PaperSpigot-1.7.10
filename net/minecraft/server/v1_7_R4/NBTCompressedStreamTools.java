/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import org.spigotmc.LimitStream;
/*     */ import org.spigotmc.SneakyThrow;
/*     */ 
/*     */ public class NBTCompressedStreamTools {
/*     */   public static NBTTagCompound a(InputStream inputstream) {
/*     */     
/*     */     try { NBTTagCompound nbttagcompound;
/*  21 */       DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputstream)));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  26 */         nbttagcompound = a(datainputstream, NBTReadLimiter.a);
/*     */       } finally {
/*  28 */         datainputstream.close();
/*     */       } 
/*     */       
/*  31 */       return nbttagcompound; }
/*  32 */     catch (IOException ex) { SneakyThrow.sneaky(ex); return null; }
/*     */   
/*     */   }
/*     */   public static void a(NBTTagCompound nbttagcompound, OutputStream outputstream) {
/*     */     
/*  37 */     try { DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputstream)));
/*     */       
/*     */       try {
/*  40 */         a(nbttagcompound, dataoutputstream);
/*     */       } finally {
/*  42 */         dataoutputstream.close();
/*     */       }  }
/*  44 */     catch (IOException ex) { SneakyThrow.sneaky(ex); }
/*     */   
/*     */   } public static NBTTagCompound a(byte[] abyte, NBTReadLimiter nbtreadlimiter) {
/*     */     
/*     */     try { NBTTagCompound nbttagcompound;
/*  49 */       DataInputStream datainputstream = new DataInputStream(new BufferedInputStream((InputStream)new LimitStream(new GZIPInputStream(new ByteArrayInputStream(abyte)), nbtreadlimiter)));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  54 */         nbttagcompound = a(datainputstream, nbtreadlimiter);
/*     */       } finally {
/*  56 */         datainputstream.close();
/*     */       } 
/*     */       
/*  59 */       return nbttagcompound; }
/*  60 */     catch (IOException ex) { SneakyThrow.sneaky(ex); return null; }
/*     */   
/*     */   }
/*     */   public static byte[] a(NBTTagCompound nbttagcompound) {
/*     */     
/*  65 */     try { ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*  66 */       DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
/*     */       
/*     */       try {
/*  69 */         a(nbttagcompound, dataoutputstream);
/*     */       } finally {
/*  71 */         dataoutputstream.close();
/*     */       } 
/*     */       
/*  74 */       return bytearrayoutputstream.toByteArray(); }
/*  75 */     catch (IOException ex) { SneakyThrow.sneaky(ex); return null; }
/*     */   
/*     */   }
/*     */   public static NBTTagCompound a(DataInputStream datainputstream) {
/*  79 */     return a(datainputstream, NBTReadLimiter.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagCompound a(DataInput datainput, NBTReadLimiter nbtreadlimiter) {
/*     */     try {
/*  85 */       if (datainput instanceof net.minecraft.util.io.netty.buffer.ByteBufInputStream)
/*     */       {
/*  87 */         datainput = new DataInputStream((InputStream)new LimitStream((InputStream)datainput, nbtreadlimiter));
/*     */       }
/*     */       
/*  90 */       NBTBase nbtbase = a(datainput, 0, nbtreadlimiter);
/*     */       
/*  92 */       if (nbtbase instanceof NBTTagCompound) {
/*  93 */         return (NBTTagCompound)nbtbase;
/*     */       }
/*  95 */       throw new IOException("Root tag must be a named compound tag");
/*     */     } catch (IOException ex) {
/*  97 */       SneakyThrow.sneaky(ex); return null;
/*     */     } 
/*     */   }
/*     */   public static void a(NBTTagCompound nbttagcompound, DataOutput dataoutput) {
/* 101 */     a(nbttagcompound, dataoutput);
/*     */   }
/*     */   
/*     */   private static void a(NBTBase nbtbase, DataOutput dataoutput) {
/*     */     
/* 106 */     try { dataoutput.writeByte(nbtbase.getTypeId());
/* 107 */       if (nbtbase.getTypeId() != 0) {
/* 108 */         dataoutput.writeUTF("");
/* 109 */         nbtbase.write(dataoutput);
/*     */       }  }
/* 111 */     catch (IOException ex) { SneakyThrow.sneaky(ex); }
/*     */   
/*     */   }
/*     */   private static NBTBase a(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) {
/*     */     try {
/* 116 */       byte b0 = datainput.readByte();
/*     */       
/* 118 */       if (b0 == 0) {
/* 119 */         return new NBTTagEnd();
/*     */       }
/* 121 */       datainput.readUTF();
/* 122 */       NBTBase nbtbase = NBTBase.createTag(b0);
/*     */       
/*     */       try {
/* 125 */         nbtbase.load(datainput, i, nbtreadlimiter);
/* 126 */         return nbtbase;
/* 127 */       } catch (IOException ioexception) {
/* 128 */         CrashReport crashreport = CrashReport.a(ioexception, "Loading NBT data");
/* 129 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("NBT Tag");
/*     */         
/* 131 */         crashreportsystemdetails.a("Tag name", "[UNNAMED TAG]");
/* 132 */         crashreportsystemdetails.a("Tag type", Byte.valueOf(b0));
/* 133 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } catch (IOException ex) {
/* 136 */       SneakyThrow.sneaky(ex); return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NBTCompressedStreamTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */