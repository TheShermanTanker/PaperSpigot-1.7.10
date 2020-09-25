package org.bukkit.plugin;

import java.util.Collection;
import java.util.List;

public interface ServicesManager {
  <T> void register(Class<T> paramClass, T paramT, Plugin paramPlugin, ServicePriority paramServicePriority);
  
  void unregisterAll(Plugin paramPlugin);
  
  void unregister(Class<?> paramClass, Object paramObject);
  
  void unregister(Object paramObject);
  
  <T> T load(Class<T> paramClass);
  
  <T> RegisteredServiceProvider<T> getRegistration(Class<T> paramClass);
  
  List<RegisteredServiceProvider<?>> getRegistrations(Plugin paramPlugin);
  
  <T> Collection<RegisteredServiceProvider<T>> getRegistrations(Class<T> paramClass);
  
  Collection<Class<?>> getKnownServices();
  
  <T> boolean isProvidedFor(Class<T> paramClass);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\ServicesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */