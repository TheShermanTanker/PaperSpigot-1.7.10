package net.minecraft.util.com.mojang.authlib.minecraft;

import java.util.Map;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationUnavailableException;

public interface MinecraftSessionService {
  void joinServer(GameProfile paramGameProfile, String paramString1, String paramString2) throws AuthenticationException;
  
  GameProfile hasJoinedServer(GameProfile paramGameProfile, String paramString) throws AuthenticationUnavailableException;
  
  Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile paramGameProfile, boolean paramBoolean);
  
  GameProfile fillProfileProperties(GameProfile paramGameProfile, boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\minecraft\MinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */