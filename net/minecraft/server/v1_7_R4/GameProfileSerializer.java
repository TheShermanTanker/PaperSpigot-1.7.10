/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GameProfileSerializer
/*    */ {
/*    */   public static GameProfile deserialize(NBTTagCompound paramNBTTagCompound) {
/* 16 */     String str1 = null;
/* 17 */     String str2 = null;
/*    */     
/* 19 */     if (paramNBTTagCompound.hasKeyOfType("Name", 8)) {
/* 20 */       str1 = paramNBTTagCompound.getString("Name");
/*    */     }
/* 22 */     if (paramNBTTagCompound.hasKeyOfType("Id", 8)) {
/* 23 */       str2 = paramNBTTagCompound.getString("Id");
/*    */     }
/*    */     
/* 26 */     if (!UtilColor.b(str1) || !UtilColor.b(str2)) {
/*    */       UUID uUID;
/*    */       try {
/* 29 */         uUID = UUID.fromString(str2);
/* 30 */       } catch (Throwable throwable) {
/* 31 */         uUID = null;
/*    */       } 
/* 33 */       GameProfile gameProfile = new GameProfile(uUID, str1);
/*    */       
/* 35 */       if (paramNBTTagCompound.hasKeyOfType("Properties", 10)) {
/* 36 */         NBTTagCompound nBTTagCompound = paramNBTTagCompound.getCompound("Properties");
/*    */         
/* 38 */         for (String str : nBTTagCompound.c()) {
/* 39 */           NBTTagList nBTTagList = nBTTagCompound.getList(str, 10);
/* 40 */           for (byte b = 0; b < nBTTagList.size(); b++) {
/* 41 */             NBTTagCompound nBTTagCompound1 = nBTTagList.get(b);
/* 42 */             String str3 = nBTTagCompound1.getString("Value");
/*    */             
/* 44 */             if (nBTTagCompound1.hasKeyOfType("Signature", 8)) {
/* 45 */               gameProfile.getProperties().put(str, new Property(str, str3, nBTTagCompound1.getString("Signature")));
/*    */             } else {
/* 47 */               gameProfile.getProperties().put(str, new Property(str, str3));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 53 */       return gameProfile;
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void serialize(NBTTagCompound paramNBTTagCompound, GameProfile paramGameProfile) {
/* 60 */     if (!UtilColor.b(paramGameProfile.getName())) {
/* 61 */       paramNBTTagCompound.setString("Name", paramGameProfile.getName());
/*    */     }
/* 63 */     if (paramGameProfile.getId() != null) {
/* 64 */       paramNBTTagCompound.setString("Id", paramGameProfile.getId().toString());
/*    */     }
/* 66 */     if (!paramGameProfile.getProperties().isEmpty()) {
/* 67 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 68 */       for (String str : paramGameProfile.getProperties().keySet()) {
/* 69 */         NBTTagList nBTTagList = new NBTTagList();
/* 70 */         for (Property property : paramGameProfile.getProperties().get(str)) {
/* 71 */           NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
/* 72 */           nBTTagCompound1.setString("Value", property.getValue());
/* 73 */           if (property.hasSignature()) {
/* 74 */             nBTTagCompound1.setString("Signature", property.getSignature());
/*    */           }
/* 76 */           nBTTagList.add(nBTTagCompound1);
/*    */         } 
/* 78 */         nBTTagCompound.set(str, nBTTagList);
/*    */       } 
/* 80 */       paramNBTTagCompound.set("Properties", nBTTagCompound);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GameProfileSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */