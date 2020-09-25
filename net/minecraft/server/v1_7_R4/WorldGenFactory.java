/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenFactory
/*    */ {
/* 17 */   private static final Logger a = LogManager.getLogger();
/* 18 */   private static Map b = new HashMap<Object, Object>();
/* 19 */   private static Map c = new HashMap<Object, Object>();
/*    */   
/* 21 */   private static Map d = new HashMap<Object, Object>();
/* 22 */   private static Map e = new HashMap<Object, Object>();
/*    */   
/*    */   private static void b(Class<?> paramClass, String paramString) {
/* 25 */     b.put(paramString, paramClass);
/* 26 */     c.put(paramClass, paramString);
/*    */   }
/*    */   
/*    */   static void a(Class<?> paramClass, String paramString) {
/* 30 */     d.put(paramString, paramClass);
/* 31 */     e.put(paramClass, paramString);
/*    */   }
/*    */   
/*    */   static {
/* 35 */     b(WorldGenMineshaftStart.class, "Mineshaft");
/* 36 */     b(WorldGenVillageStart.class, "Village");
/* 37 */     b(WorldGenNetherStart.class, "Fortress");
/* 38 */     b(WorldGenStronghold2Start.class, "Stronghold");
/* 39 */     b(WorldGenLargeFeatureStart.class, "Temple");
/*    */     
/* 41 */     WorldGenMineshaftPieces.a();
/* 42 */     WorldGenVillagePieces.a();
/* 43 */     WorldGenNetherPieces.a();
/* 44 */     WorldGenStrongholdPieces.a();
/* 45 */     WorldGenRegistration.a();
/*    */   }
/*    */   
/*    */   public static String a(StructureStart paramStructureStart) {
/* 49 */     return (String)c.get(paramStructureStart.getClass());
/*    */   }
/*    */   
/*    */   public static String a(StructurePiece paramStructurePiece) {
/* 53 */     return (String)e.get(paramStructurePiece.getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   public static StructureStart a(NBTTagCompound paramNBTTagCompound, World paramWorld) {
/* 58 */     StructureStart structureStart = null;
/*    */     
/*    */     try {
/* 61 */       Class<StructureStart> clazz = (Class)b.get(paramNBTTagCompound.getString("id"));
/* 62 */       if (clazz != null) structureStart = clazz.newInstance();
/*    */     
/* 64 */     } catch (Exception exception) {
/* 65 */       a.warn("Failed Start with id " + paramNBTTagCompound.getString("id"));
/* 66 */       exception.printStackTrace();
/*    */     } 
/* 68 */     if (structureStart != null) {
/* 69 */       structureStart.a(paramWorld, paramNBTTagCompound);
/*    */     } else {
/* 71 */       a.warn("Skipping Structure with id " + paramNBTTagCompound.getString("id"));
/*    */     } 
/* 73 */     return structureStart;
/*    */   }
/*    */   
/*    */   public static StructurePiece b(NBTTagCompound paramNBTTagCompound, World paramWorld) {
/* 77 */     StructurePiece structurePiece = null;
/*    */     
/*    */     try {
/* 80 */       Class<StructurePiece> clazz = (Class)d.get(paramNBTTagCompound.getString("id"));
/* 81 */       if (clazz != null) structurePiece = clazz.newInstance();
/*    */     
/* 83 */     } catch (Exception exception) {
/* 84 */       a.warn("Failed Piece with id " + paramNBTTagCompound.getString("id"));
/* 85 */       exception.printStackTrace();
/*    */     } 
/* 87 */     if (structurePiece != null) {
/* 88 */       structurePiece.a(paramWorld, paramNBTTagCompound);
/*    */     } else {
/* 90 */       a.warn("Skipping Piece with id " + paramNBTTagCompound.getString("id"));
/*    */     } 
/* 92 */     return structurePiece;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */