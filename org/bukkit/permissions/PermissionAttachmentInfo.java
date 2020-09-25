/*    */ package org.bukkit.permissions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PermissionAttachmentInfo
/*    */ {
/*    */   private final Permissible permissible;
/*    */   private final String permission;
/*    */   private final PermissionAttachment attachment;
/*    */   private final boolean value;
/*    */   
/*    */   public PermissionAttachmentInfo(Permissible permissible, String permission, PermissionAttachment attachment, boolean value) {
/* 14 */     if (permissible == null)
/* 15 */       throw new IllegalArgumentException("Permissible may not be null"); 
/* 16 */     if (permission == null) {
/* 17 */       throw new IllegalArgumentException("Permissions may not be null");
/*    */     }
/*    */     
/* 20 */     this.permissible = permissible;
/* 21 */     this.permission = permission;
/* 22 */     this.attachment = attachment;
/* 23 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Permissible getPermissible() {
/* 32 */     return this.permissible;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermission() {
/* 41 */     return this.permission;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PermissionAttachment getAttachment() {
/* 51 */     return this.attachment;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getValue() {
/* 60 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\PermissionAttachmentInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */