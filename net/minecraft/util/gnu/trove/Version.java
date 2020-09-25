/*    */ package net.minecraft.util.gnu.trove;
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
/*    */ public class Version
/*    */ {
/*    */   public static void main(String[] args) {
/* 39 */     System.out.println(getVersion());
/*    */   }
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
/*    */   public static String getVersion() {
/* 52 */     String version = Version.class.getPackage().getImplementationVersion();
/*    */     
/* 54 */     if (version != null) {
/* 55 */       return "trove4j version " + version;
/*    */     }
/*    */     
/* 58 */     return "Sorry no Implementation-Version manifest attribute available";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */