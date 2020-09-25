package org.bukkit.plugin.messaging;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface PluginMessageRecipient {
  void sendPluginMessage(Plugin paramPlugin, String paramString, byte[] paramArrayOfbyte);
  
  Set<String> getListeningPluginChannels();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\PluginMessageRecipient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */