/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
/*    */ 
/*    */ final class PlayerDatFileConverter
/*    */   implements ProfileLookupCallback {
/*    */   final DedicatedServer a;
/*    */   final File b;
/*    */   final File c;
/*    */   final File d;
/*    */   final String[] e;
/*    */   
/*    */   PlayerDatFileConverter(DedicatedServer dedicatedserver, File file1, File file2, File file3, String[] astring) {
/* 19 */     this.a = dedicatedserver;
/* 20 */     this.b = file1;
/* 21 */     this.c = file2;
/* 22 */     this.d = file3;
/* 23 */     this.e = astring;
/*    */   }
/*    */   
/*    */   public void onProfileLookupSucceeded(GameProfile gameprofile) {
/* 27 */     this.a.getUserCache().a(gameprofile);
/* 28 */     UUID uuid = gameprofile.getId();
/*    */     
/* 30 */     if (uuid == null) {
/* 31 */       throw new FileConversionException("Missing UUID for user profile " + gameprofile.getName(), (PredicateEmptyList)null);
/*    */     }
/* 33 */     a(this.b, a(gameprofile), uuid.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
/* 38 */     NameReferencingFileConverter.a().warn("Could not lookup user uuid for " + gameprofile.getName(), exception);
/* 39 */     if (exception instanceof net.minecraft.util.com.mojang.authlib.yggdrasil.ProfileNotFoundException) {
/* 40 */       String s = a(gameprofile);
/*    */       
/* 42 */       a(this.c, s, s);
/*    */     } else {
/* 44 */       throw new FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception, (PredicateEmptyList)null);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void a(File file1, String s, String s1) {
/* 49 */     File file2 = new File(this.d, s + ".dat");
/* 50 */     File file3 = new File(file1, s1 + ".dat");
/*    */ 
/*    */     
/* 53 */     NBTTagCompound root = null;
/*    */     
/*    */     try {
/* 56 */       root = NBTCompressedStreamTools.a(new FileInputStream(file2));
/* 57 */     } catch (Exception exception) {
/* 58 */       exception.printStackTrace();
/*    */     } 
/*    */     
/* 61 */     if (root != null) {
/* 62 */       if (!root.hasKey("bukkit")) {
/* 63 */         root.set("bukkit", new NBTTagCompound());
/*    */       }
/* 65 */       NBTTagCompound data = root.getCompound("bukkit");
/* 66 */       data.setString("lastKnownName", s);
/*    */       
/*    */       try {
/* 69 */         NBTCompressedStreamTools.a(root, new FileOutputStream(file2));
/* 70 */       } catch (Exception exception) {
/* 71 */         exception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 76 */     NameReferencingFileConverter.a(file1);
/* 77 */     if (!file2.renameTo(file3)) {
/* 78 */       throw new FileConversionException("Could not convert file for " + s, (PredicateEmptyList)null);
/*    */     }
/*    */   }
/*    */   
/*    */   private String a(GameProfile gameprofile) {
/* 83 */     String s = null;
/*    */     
/* 85 */     for (int i = 0; i < this.e.length; i++) {
/* 86 */       if (this.e[i] != null && this.e[i].equalsIgnoreCase(gameprofile.getName())) {
/* 87 */         s = this.e[i];
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 92 */     if (s == null) {
/* 93 */       throw new FileConversionException("Could not find the filename for " + gameprofile.getName() + " anymore", (PredicateEmptyList)null);
/*    */     }
/* 95 */     return s;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerDatFileConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */