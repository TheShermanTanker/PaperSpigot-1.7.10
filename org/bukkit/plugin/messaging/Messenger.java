package org.bukkit.plugin.messaging;

import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface Messenger {
  public static final int MAX_MESSAGE_SIZE = 32766;
  
  public static final int MAX_CHANNEL_SIZE = 16;
  
  boolean isReservedChannel(String paramString);
  
  void registerOutgoingPluginChannel(Plugin paramPlugin, String paramString);
  
  void unregisterOutgoingPluginChannel(Plugin paramPlugin, String paramString);
  
  void unregisterOutgoingPluginChannel(Plugin paramPlugin);
  
  PluginMessageListenerRegistration registerIncomingPluginChannel(Plugin paramPlugin, String paramString, PluginMessageListener paramPluginMessageListener);
  
  void unregisterIncomingPluginChannel(Plugin paramPlugin, String paramString, PluginMessageListener paramPluginMessageListener);
  
  void unregisterIncomingPluginChannel(Plugin paramPlugin, String paramString);
  
  void unregisterIncomingPluginChannel(Plugin paramPlugin);
  
  Set<String> getOutgoingChannels();
  
  Set<String> getOutgoingChannels(Plugin paramPlugin);
  
  Set<String> getIncomingChannels();
  
  Set<String> getIncomingChannels(Plugin paramPlugin);
  
  Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin paramPlugin);
  
  Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(String paramString);
  
  Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin paramPlugin, String paramString);
  
  boolean isRegistrationValid(PluginMessageListenerRegistration paramPluginMessageListenerRegistration);
  
  boolean isIncomingChannelRegistered(Plugin paramPlugin, String paramString);
  
  boolean isOutgoingChannelRegistered(Plugin paramPlugin, String paramString);
  
  void dispatchIncomingMessage(Player paramPlayer, String paramString, byte[] paramArrayOfbyte);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\Messenger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */