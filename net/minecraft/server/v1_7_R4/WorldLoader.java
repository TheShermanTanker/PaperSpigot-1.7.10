/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class WorldLoader
/*     */   implements Convertable
/*     */ {
/*  12 */   private static final Logger b = LogManager.getLogger();
/*     */   
/*     */   protected final File a;
/*     */   
/*     */   public WorldLoader(File paramFile) {
/*  17 */     if (!paramFile.exists()) paramFile.mkdirs(); 
/*  18 */     this.a = paramFile;
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
/*     */   public void d() {}
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
/*     */   public WorldData c(String paramString) {
/*  50 */     File file1 = new File(this.a, paramString);
/*  51 */     if (!file1.exists()) return null;
/*     */     
/*  53 */     File file2 = new File(file1, "level.dat");
/*  54 */     if (file2.exists()) {
/*     */       try {
/*  56 */         NBTTagCompound nBTTagCompound1 = NBTCompressedStreamTools.a(new FileInputStream(file2));
/*  57 */         NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompound("Data");
/*  58 */         return new WorldData(nBTTagCompound2);
/*  59 */       } catch (Exception exception) {
/*  60 */         b.error("Exception reading " + file2, exception);
/*     */       } 
/*     */     }
/*     */     
/*  64 */     file2 = new File(file1, "level.dat_old");
/*  65 */     if (file2.exists()) {
/*     */       try {
/*  67 */         NBTTagCompound nBTTagCompound1 = NBTCompressedStreamTools.a(new FileInputStream(file2));
/*  68 */         NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompound("Data");
/*  69 */         return new WorldData(nBTTagCompound2);
/*  70 */       } catch (Exception exception) {
/*  71 */         b.error("Exception reading " + file2, exception);
/*     */       } 
/*     */     }
/*  74 */     return null;
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
/*     */   public boolean e(String paramString) {
/* 117 */     File file = new File(this.a, paramString);
/* 118 */     if (!file.exists()) return true;
/*     */     
/* 120 */     b.info("Deleting level " + paramString);
/*     */     
/* 122 */     for (byte b = 1; b <= 5; b++) {
/* 123 */       b.info("Attempt " + b + "...");
/*     */       
/* 125 */       if (a(file.listFiles())) {
/*     */         break;
/*     */       }
/* 128 */       b.warn("Unsuccessful in deleting contents.");
/*     */ 
/*     */       
/* 131 */       if (b < 5) {
/*     */         try {
/* 133 */           Thread.sleep(500L);
/* 134 */         } catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return file.delete();
/*     */   }
/*     */   
/*     */   protected static boolean a(File[] paramArrayOfFile) {
/* 143 */     for (byte b = 0; b < paramArrayOfFile.length; b++) {
/* 144 */       File file = paramArrayOfFile[b];
/* 145 */       b.debug("Deleting " + file);
/*     */       
/* 147 */       if (file.isDirectory() && 
/* 148 */         !a(file.listFiles())) {
/* 149 */         b.warn("Couldn't delete directory " + file);
/* 150 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 154 */       if (!file.delete()) {
/* 155 */         b.warn("Couldn't delete file " + file);
/* 156 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public IDataManager a(String paramString, boolean paramBoolean) {
/* 165 */     return new WorldNBTStorage(this.a, paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConvertable(String paramString) {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate) {
/* 180 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */