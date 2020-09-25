/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.Action;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.FileRenameAction;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.GZCompressAction;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.ZipCompressAction;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name = "DefaultRolloverStrategy", category = "Core", printObject = true)
/*     */ public class DefaultRolloverStrategy
/*     */   implements RolloverStrategy
/*     */ {
/*  82 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_WINDOW_SIZE = 1;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_WINDOW_SIZE = 7;
/*     */ 
/*     */   
/*     */   private final int maxIndex;
/*     */ 
/*     */   
/*     */   private final int minIndex;
/*     */ 
/*     */   
/*     */   private final boolean useMax;
/*     */ 
/*     */   
/*     */   private final StrSubstitutor subst;
/*     */ 
/*     */   
/*     */   private final int compressionLevel;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DefaultRolloverStrategy(int minIndex, int maxIndex, boolean useMax, int compressionLevel, StrSubstitutor subst) {
/* 109 */     this.minIndex = minIndex;
/* 110 */     this.maxIndex = maxIndex;
/* 111 */     this.useMax = useMax;
/* 112 */     this.compressionLevel = compressionLevel;
/* 113 */     this.subst = subst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RolloverDescription rollover(RollingFileManager manager) throws SecurityException {
/* 124 */     if (this.maxIndex >= 0) {
/*     */       ZipCompressAction zipCompressAction;
/*     */       int fileIndex;
/* 127 */       if ((fileIndex = purge(this.minIndex, this.maxIndex, manager)) < 0) {
/* 128 */         return null;
/*     */       }
/*     */       
/* 131 */       StringBuilder buf = new StringBuilder();
/* 132 */       manager.getPatternProcessor().formatFileName(this.subst, buf, Integer.valueOf(fileIndex));
/* 133 */       String currentFileName = manager.getFileName();
/*     */       
/* 135 */       String renameTo = buf.toString();
/* 136 */       String compressedName = renameTo;
/* 137 */       Action compressAction = null;
/*     */       
/* 139 */       if (renameTo.endsWith(".gz")) {
/* 140 */         renameTo = renameTo.substring(0, renameTo.length() - 3);
/* 141 */         GZCompressAction gZCompressAction = new GZCompressAction(new File(renameTo), new File(compressedName), true);
/* 142 */       } else if (renameTo.endsWith(".zip")) {
/* 143 */         renameTo = renameTo.substring(0, renameTo.length() - 4);
/* 144 */         zipCompressAction = new ZipCompressAction(new File(renameTo), new File(compressedName), true, this.compressionLevel);
/*     */       } 
/*     */ 
/*     */       
/* 148 */       FileRenameAction renameAction = new FileRenameAction(new File(currentFileName), new File(renameTo), false);
/*     */ 
/*     */       
/* 151 */       return new RolloverDescriptionImpl(currentFileName, false, (Action)renameAction, (Action)zipCompressAction);
/*     */     } 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */   
/*     */   private int purge(int lowIndex, int highIndex, RollingFileManager manager) {
/* 158 */     return this.useMax ? purgeAscending(lowIndex, highIndex, manager) : purgeDescending(lowIndex, highIndex, manager);
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
/*     */   private int purgeDescending(int lowIndex, int highIndex, RollingFileManager manager) {
/* 172 */     int suffixLength = 0;
/*     */     
/* 174 */     List<FileRenameAction> renames = new ArrayList<FileRenameAction>();
/* 175 */     StringBuilder buf = new StringBuilder();
/* 176 */     manager.getPatternProcessor().formatFileName(buf, Integer.valueOf(lowIndex));
/*     */     
/* 178 */     String lowFilename = this.subst.replace(buf);
/*     */     
/* 180 */     if (lowFilename.endsWith(".gz")) {
/* 181 */       suffixLength = 3;
/* 182 */     } else if (lowFilename.endsWith(".zip")) {
/* 183 */       suffixLength = 4;
/*     */     } 
/*     */     int i;
/* 186 */     for (i = lowIndex; i <= highIndex; ) {
/* 187 */       File toRename = new File(lowFilename);
/* 188 */       boolean isBase = false;
/*     */       
/* 190 */       if (suffixLength > 0) {
/* 191 */         File toRenameBase = new File(lowFilename.substring(0, lowFilename.length() - suffixLength));
/*     */ 
/*     */         
/* 194 */         if (toRename.exists()) {
/* 195 */           if (toRenameBase.exists()) {
/* 196 */             toRenameBase.delete();
/*     */           }
/*     */         } else {
/* 199 */           toRename = toRenameBase;
/* 200 */           isBase = true;
/*     */         } 
/*     */       } 
/*     */       
/* 204 */       if (toRename.exists()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         if (i == highIndex) {
/* 210 */           if (!toRename.delete()) {
/* 211 */             return -1;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 220 */         buf.setLength(0);
/* 221 */         manager.getPatternProcessor().formatFileName(buf, Integer.valueOf(i + 1));
/*     */         
/* 223 */         String highFilename = this.subst.replace(buf);
/* 224 */         String renameTo = highFilename;
/*     */         
/* 226 */         if (isBase) {
/* 227 */           renameTo = highFilename.substring(0, highFilename.length() - suffixLength);
/*     */         }
/*     */         
/* 230 */         renames.add(new FileRenameAction(toRename, new File(renameTo), true));
/* 231 */         lowFilename = highFilename;
/*     */ 
/*     */ 
/*     */         
/*     */         i++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 240 */     for (i = renames.size() - 1; i >= 0; i--) {
/* 241 */       Action action = (Action)renames.get(i);
/*     */       
/*     */       try {
/* 244 */         if (!action.execute()) {
/* 245 */           return -1;
/*     */         }
/* 247 */       } catch (Exception ex) {
/* 248 */         LOGGER.warn("Exception during purge in RollingFileAppender", ex);
/* 249 */         return -1;
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     return lowIndex;
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
/*     */   private int purgeAscending(int lowIndex, int highIndex, RollingFileManager manager) {
/* 266 */     int suffixLength = 0;
/*     */     
/* 268 */     List<FileRenameAction> renames = new ArrayList<FileRenameAction>();
/* 269 */     StringBuilder buf = new StringBuilder();
/* 270 */     manager.getPatternProcessor().formatFileName(buf, Integer.valueOf(highIndex));
/*     */     
/* 272 */     String highFilename = this.subst.replace(buf);
/*     */     
/* 274 */     if (highFilename.endsWith(".gz")) {
/* 275 */       suffixLength = 3;
/* 276 */     } else if (highFilename.endsWith(".zip")) {
/* 277 */       suffixLength = 4;
/*     */     } 
/*     */     
/* 280 */     int maxIndex = 0;
/*     */     int i;
/* 282 */     for (i = highIndex; i >= lowIndex; i--) {
/* 283 */       File toRename = new File(highFilename);
/* 284 */       if (i == highIndex && toRename.exists()) {
/* 285 */         maxIndex = highIndex;
/* 286 */       } else if (maxIndex == 0 && toRename.exists()) {
/* 287 */         maxIndex = i + 1;
/*     */         
/*     */         break;
/*     */       } 
/* 291 */       boolean isBase = false;
/*     */       
/* 293 */       if (suffixLength > 0) {
/* 294 */         File toRenameBase = new File(highFilename.substring(0, highFilename.length() - suffixLength));
/*     */ 
/*     */         
/* 297 */         if (toRename.exists()) {
/* 298 */           if (toRenameBase.exists()) {
/* 299 */             toRenameBase.delete();
/*     */           }
/*     */         } else {
/* 302 */           toRename = toRenameBase;
/* 303 */           isBase = true;
/*     */         } 
/*     */       } 
/*     */       
/* 307 */       if (toRename.exists()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         if (i == lowIndex) {
/* 313 */           if (!toRename.delete()) {
/* 314 */             return -1;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 323 */         buf.setLength(0);
/* 324 */         manager.getPatternProcessor().formatFileName(buf, Integer.valueOf(i - 1));
/*     */         
/* 326 */         String lowFilename = this.subst.replace(buf);
/* 327 */         String renameTo = lowFilename;
/*     */         
/* 329 */         if (isBase) {
/* 330 */           renameTo = lowFilename.substring(0, lowFilename.length() - suffixLength);
/*     */         }
/*     */         
/* 333 */         renames.add(new FileRenameAction(toRename, new File(renameTo), true));
/* 334 */         highFilename = lowFilename;
/*     */       } else {
/* 336 */         buf.setLength(0);
/* 337 */         manager.getPatternProcessor().formatFileName(buf, Integer.valueOf(i - 1));
/*     */         
/* 339 */         highFilename = this.subst.replace(buf);
/*     */       } 
/*     */     } 
/* 342 */     if (maxIndex == 0) {
/* 343 */       maxIndex = lowIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     for (i = renames.size() - 1; i >= 0; i--) {
/* 350 */       Action action = (Action)renames.get(i);
/*     */       
/*     */       try {
/* 353 */         if (!action.execute()) {
/* 354 */           return -1;
/*     */         }
/* 356 */       } catch (Exception ex) {
/* 357 */         LOGGER.warn("Exception during purge in RollingFileAppender", ex);
/* 358 */         return -1;
/*     */       } 
/*     */     } 
/* 361 */     return maxIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 366 */     return "DefaultRolloverStrategy(min=" + this.minIndex + ", max=" + this.maxIndex + ")";
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
/*     */   @PluginFactory
/*     */   public static DefaultRolloverStrategy createStrategy(@PluginAttribute("max") String max, @PluginAttribute("min") String min, @PluginAttribute("fileIndex") String fileIndex, @PluginAttribute("compressionLevel") String compressionLevelStr, @PluginConfiguration Configuration config) {
/*     */     int minIndex, maxIndex;
/* 386 */     boolean useMax = (fileIndex == null) ? true : fileIndex.equalsIgnoreCase("max");
/*     */     
/* 388 */     if (min != null) {
/* 389 */       minIndex = Integer.parseInt(min);
/* 390 */       if (minIndex < 1) {
/* 391 */         LOGGER.error("Minimum window size too small. Limited to 1");
/* 392 */         minIndex = 1;
/*     */       } 
/*     */     } else {
/* 395 */       minIndex = 1;
/*     */     } 
/*     */     
/* 398 */     if (max != null) {
/* 399 */       maxIndex = Integer.parseInt(max);
/* 400 */       if (maxIndex < minIndex) {
/* 401 */         maxIndex = (minIndex < 7) ? 7 : minIndex;
/* 402 */         LOGGER.error("Maximum window size must be greater than the minimum windows size. Set to " + maxIndex);
/*     */       } 
/*     */     } else {
/* 405 */       maxIndex = 7;
/*     */     } 
/* 407 */     int compressionLevel = Integers.parseInt(compressionLevelStr, -1);
/* 408 */     return new DefaultRolloverStrategy(minIndex, maxIndex, useMax, compressionLevel, config.getStrSubstitutor());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\DefaultRolloverStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */