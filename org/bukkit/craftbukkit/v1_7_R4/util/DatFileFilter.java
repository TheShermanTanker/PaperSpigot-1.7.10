/*   */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*   */ 
/*   */ import java.io.File;
/*   */ import java.io.FilenameFilter;
/*   */ 
/*   */ public class DatFileFilter implements FilenameFilter {
/*   */   public boolean accept(File dir, String name) {
/* 8 */     return name.endsWith(".dat");
/*   */   }
/*   */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\DatFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */