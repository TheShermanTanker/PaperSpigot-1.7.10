/*   */ package net.minecraft.server.v1_7_R4;
/*   */ import org.bukkit.generator.ChunkGenerator;
/*   */ 
/*   */ public class SecondaryWorldServer extends WorldServer {
/*   */   public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, WorldServer worldserver, MethodProfiler methodprofiler, World.Environment env, ChunkGenerator gen) {
/* 6 */     super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, env, gen);
/*   */     
/* 8 */     this.worldMaps = worldserver.worldMaps;
/* 9 */     this.scoreboard = worldserver.getScoreboard();
/*   */   }
/*   */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SecondaryWorldServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */