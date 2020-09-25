package org.bukkit.plugin;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public interface PluginLoader {
  Plugin loadPlugin(File paramFile) throws InvalidPluginException, UnknownDependencyException;
  
  PluginDescriptionFile getPluginDescription(File paramFile) throws InvalidDescriptionException;
  
  Pattern[] getPluginFileFilters();
  
  Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener paramListener, Plugin paramPlugin);
  
  void enablePlugin(Plugin paramPlugin);
  
  void disablePlugin(Plugin paramPlugin);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\PluginLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */