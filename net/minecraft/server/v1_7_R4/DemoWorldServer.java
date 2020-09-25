/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DemoWorldServer
/*    */   extends WorldServer
/*    */ {
/* 12 */   private static final long J = "North Carolina".hashCode();
/*    */   
/* 14 */   public static final WorldSettings a = (new WorldSettings(J, EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL)).a();
/*    */   
/*    */   public DemoWorldServer(MinecraftServer paramMinecraftServer, IDataManager paramIDataManager, String paramString, int paramInt, MethodProfiler paramMethodProfiler) {
/* 17 */     super(paramMinecraftServer, paramIDataManager, paramString, paramInt, a, paramMethodProfiler);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DemoWorldServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */