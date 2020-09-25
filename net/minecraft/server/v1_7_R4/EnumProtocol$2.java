/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ enum EnumProtocol$2
/*     */ {
/*     */   EnumProtocol$2(int paramInt1) {
/*  26 */     b(0, PacketPlayOutKeepAlive.class);
/*  27 */     b(1, PacketPlayOutLogin.class);
/*  28 */     b(2, PacketPlayOutChat.class);
/*  29 */     b(3, PacketPlayOutUpdateTime.class);
/*  30 */     b(4, PacketPlayOutEntityEquipment.class);
/*  31 */     b(5, PacketPlayOutSpawnPosition.class);
/*  32 */     b(6, PacketPlayOutUpdateHealth.class);
/*  33 */     b(7, PacketPlayOutRespawn.class);
/*  34 */     b(8, PacketPlayOutPosition.class);
/*  35 */     b(9, PacketPlayOutHeldItemSlot.class);
/*  36 */     b(10, PacketPlayOutBed.class);
/*  37 */     b(11, PacketPlayOutAnimation.class);
/*  38 */     b(12, PacketPlayOutNamedEntitySpawn.class);
/*  39 */     b(13, PacketPlayOutCollect.class);
/*  40 */     b(14, PacketPlayOutSpawnEntity.class);
/*  41 */     b(15, PacketPlayOutSpawnEntityLiving.class);
/*  42 */     b(16, PacketPlayOutSpawnEntityPainting.class);
/*  43 */     b(17, PacketPlayOutSpawnEntityExperienceOrb.class);
/*  44 */     b(18, PacketPlayOutEntityVelocity.class);
/*  45 */     b(19, PacketPlayOutEntityDestroy.class);
/*  46 */     b(20, PacketPlayOutEntity.class);
/*  47 */     b(21, PacketPlayOutRelEntityMove.class);
/*  48 */     b(22, PacketPlayOutEntityLook.class);
/*  49 */     b(23, PacketPlayOutRelEntityMoveLook.class);
/*  50 */     b(24, PacketPlayOutEntityTeleport.class);
/*  51 */     b(25, PacketPlayOutEntityHeadRotation.class);
/*  52 */     b(26, PacketPlayOutEntityStatus.class);
/*  53 */     b(27, PacketPlayOutAttachEntity.class);
/*  54 */     b(28, PacketPlayOutEntityMetadata.class);
/*  55 */     b(29, PacketPlayOutEntityEffect.class);
/*  56 */     b(30, PacketPlayOutRemoveEntityEffect.class);
/*  57 */     b(31, PacketPlayOutExperience.class);
/*  58 */     b(32, PacketPlayOutUpdateAttributes.class);
/*  59 */     b(33, PacketPlayOutMapChunk.class);
/*  60 */     b(34, PacketPlayOutMultiBlockChange.class);
/*  61 */     b(35, PacketPlayOutBlockChange.class);
/*  62 */     b(36, PacketPlayOutBlockAction.class);
/*  63 */     b(37, PacketPlayOutBlockBreakAnimation.class);
/*  64 */     b(38, PacketPlayOutMapChunkBulk.class);
/*  65 */     b(39, PacketPlayOutExplosion.class);
/*  66 */     b(40, PacketPlayOutWorldEvent.class);
/*  67 */     b(41, PacketPlayOutNamedSoundEffect.class);
/*  68 */     b(42, PacketPlayOutWorldParticles.class);
/*  69 */     b(43, PacketPlayOutGameStateChange.class);
/*  70 */     b(44, PacketPlayOutSpawnEntityWeather.class);
/*  71 */     b(45, PacketPlayOutOpenWindow.class);
/*  72 */     b(46, PacketPlayOutCloseWindow.class);
/*  73 */     b(47, PacketPlayOutSetSlot.class);
/*  74 */     b(48, PacketPlayOutWindowItems.class);
/*  75 */     b(49, PacketPlayOutWindowData.class);
/*  76 */     b(50, PacketPlayOutTransaction.class);
/*  77 */     b(51, PacketPlayOutUpdateSign.class);
/*  78 */     b(52, PacketPlayOutMap.class);
/*  79 */     b(53, PacketPlayOutTileEntityData.class);
/*  80 */     b(54, PacketPlayOutOpenSignEditor.class);
/*  81 */     b(55, PacketPlayOutStatistic.class);
/*  82 */     b(56, PacketPlayOutPlayerInfo.class);
/*  83 */     b(57, PacketPlayOutAbilities.class);
/*  84 */     b(58, PacketPlayOutTabComplete.class);
/*  85 */     b(59, PacketPlayOutScoreboardObjective.class);
/*  86 */     b(60, PacketPlayOutScoreboardScore.class);
/*  87 */     b(61, PacketPlayOutScoreboardDisplayObjective.class);
/*  88 */     b(62, PacketPlayOutScoreboardTeam.class);
/*  89 */     b(63, PacketPlayOutCustomPayload.class);
/*  90 */     b(64, PacketPlayOutKickDisconnect.class);
/*     */     
/*  92 */     a(0, PacketPlayInKeepAlive.class);
/*  93 */     a(1, PacketPlayInChat.class);
/*  94 */     a(2, PacketPlayInUseEntity.class);
/*  95 */     a(3, PacketPlayInFlying.class);
/*  96 */     a(4, PacketPlayInPosition.class);
/*  97 */     a(5, PacketPlayInLook.class);
/*  98 */     a(6, PacketPlayInPositionLook.class);
/*  99 */     a(7, PacketPlayInBlockDig.class);
/* 100 */     a(8, PacketPlayInBlockPlace.class);
/* 101 */     a(9, PacketPlayInHeldItemSlot.class);
/* 102 */     a(10, PacketPlayInArmAnimation.class);
/* 103 */     a(11, PacketPlayInEntityAction.class);
/* 104 */     a(12, PacketPlayInSteerVehicle.class);
/* 105 */     a(13, PacketPlayInCloseWindow.class);
/* 106 */     a(14, PacketPlayInWindowClick.class);
/* 107 */     a(15, PacketPlayInTransaction.class);
/* 108 */     a(16, PacketPlayInSetCreativeSlot.class);
/* 109 */     a(17, PacketPlayInEnchantItem.class);
/* 110 */     a(18, PacketPlayInUpdateSign.class);
/* 111 */     a(19, PacketPlayInAbilities.class);
/* 112 */     a(20, PacketPlayInTabComplete.class);
/* 113 */     a(21, PacketPlayInSettings.class);
/* 114 */     a(22, PacketPlayInClientCommand.class);
/* 115 */     a(23, PacketPlayInCustomPayload.class);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumProtocol$2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */