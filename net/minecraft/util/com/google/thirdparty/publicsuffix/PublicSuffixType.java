/*    */ package net.minecraft.util.com.google.thirdparty.publicsuffix;
/*    */ 
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible
/*    */ enum PublicSuffixType
/*    */ {
/* 28 */   PRIVATE(':', ','),
/*    */   
/* 30 */   ICANN('!', '?');
/*    */ 
/*    */   
/*    */   private final char innerNodeCode;
/*    */   
/*    */   private final char leafNodeCode;
/*    */ 
/*    */   
/*    */   PublicSuffixType(char innerNodeCode, char leafNodeCode) {
/* 39 */     this.innerNodeCode = innerNodeCode;
/* 40 */     this.leafNodeCode = leafNodeCode;
/*    */   }
/*    */   
/*    */   char getLeafNodeCode() {
/* 44 */     return this.leafNodeCode;
/*    */   }
/*    */   
/*    */   char getInnerNodeCode() {
/* 48 */     return this.innerNodeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   static PublicSuffixType fromCode(char code) {
/* 53 */     for (PublicSuffixType value : values()) {
/* 54 */       if (value.getInnerNodeCode() == code || value.getLeafNodeCode() == code) {
/* 55 */         return value;
/*    */       }
/*    */     } 
/* 58 */     throw new IllegalArgumentException("No enum corresponding to given code: " + code);
/*    */   }
/*    */   
/*    */   static PublicSuffixType fromIsPrivate(boolean isPrivate) {
/* 62 */     return isPrivate ? PRIVATE : ICANN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\thirdparty\publicsuffix\PublicSuffixType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */