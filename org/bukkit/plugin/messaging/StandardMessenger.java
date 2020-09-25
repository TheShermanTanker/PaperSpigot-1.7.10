/*     */ package org.bukkit.plugin.messaging;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class StandardMessenger
/*     */   implements Messenger
/*     */ {
/*  16 */   private final Map<String, Set<PluginMessageListenerRegistration>> incomingByChannel = new HashMap<String, Set<PluginMessageListenerRegistration>>();
/*  17 */   private final Map<Plugin, Set<PluginMessageListenerRegistration>> incomingByPlugin = new HashMap<Plugin, Set<PluginMessageListenerRegistration>>();
/*  18 */   private final Map<String, Set<Plugin>> outgoingByChannel = new HashMap<String, Set<Plugin>>();
/*  19 */   private final Map<Plugin, Set<String>> outgoingByPlugin = (Map<Plugin, Set<String>>)new HashMap<Plugin, Set<String>>();
/*  20 */   private final Object incomingLock = new Object();
/*  21 */   private final Object outgoingLock = new Object();
/*     */   
/*     */   private void addToOutgoing(Plugin plugin, String channel) {
/*  24 */     synchronized (this.outgoingLock) {
/*  25 */       Set<Plugin> plugins = this.outgoingByChannel.get(channel);
/*  26 */       Set<String> channels = this.outgoingByPlugin.get(plugin);
/*     */       
/*  28 */       if (plugins == null) {
/*  29 */         plugins = new HashSet<Plugin>();
/*  30 */         this.outgoingByChannel.put(channel, plugins);
/*     */       } 
/*     */       
/*  33 */       if (channels == null) {
/*  34 */         channels = new HashSet<String>();
/*  35 */         this.outgoingByPlugin.put(plugin, channels);
/*     */       } 
/*     */       
/*  38 */       plugins.add(plugin);
/*  39 */       channels.add(channel);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromOutgoing(Plugin plugin, String channel) {
/*  44 */     synchronized (this.outgoingLock) {
/*  45 */       Set<Plugin> plugins = this.outgoingByChannel.get(channel);
/*  46 */       Set<String> channels = this.outgoingByPlugin.get(plugin);
/*     */       
/*  48 */       if (plugins != null) {
/*  49 */         plugins.remove(plugin);
/*     */         
/*  51 */         if (plugins.isEmpty()) {
/*  52 */           this.outgoingByChannel.remove(channel);
/*     */         }
/*     */       } 
/*     */       
/*  56 */       if (channels != null) {
/*  57 */         channels.remove(channel);
/*     */         
/*  59 */         if (channels.isEmpty()) {
/*  60 */           this.outgoingByChannel.remove(channel);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromOutgoing(Plugin plugin) {
/*  67 */     synchronized (this.outgoingLock) {
/*  68 */       Set<String> channels = this.outgoingByPlugin.get(plugin);
/*     */       
/*  70 */       if (channels != null) {
/*  71 */         String[] toRemove = channels.<String>toArray(new String[0]);
/*     */         
/*  73 */         this.outgoingByPlugin.remove(plugin);
/*     */         
/*  75 */         for (String channel : toRemove) {
/*  76 */           removeFromOutgoing(plugin, channel);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addToIncoming(PluginMessageListenerRegistration registration) {
/*  83 */     synchronized (this.incomingLock) {
/*  84 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(registration.getChannel());
/*     */       
/*  86 */       if (registrations == null) {
/*  87 */         registrations = new HashSet<PluginMessageListenerRegistration>();
/*  88 */         this.incomingByChannel.put(registration.getChannel(), registrations);
/*     */       }
/*  90 */       else if (registrations.contains(registration)) {
/*  91 */         throw new IllegalArgumentException("This registration already exists");
/*     */       } 
/*     */ 
/*     */       
/*  95 */       registrations.add(registration);
/*     */       
/*  97 */       registrations = this.incomingByPlugin.get(registration.getPlugin());
/*     */       
/*  99 */       if (registrations == null) {
/* 100 */         registrations = new HashSet<PluginMessageListenerRegistration>();
/* 101 */         this.incomingByPlugin.put(registration.getPlugin(), registrations);
/*     */       }
/* 103 */       else if (registrations.contains(registration)) {
/* 104 */         throw new IllegalArgumentException("This registration already exists");
/*     */       } 
/*     */ 
/*     */       
/* 108 */       registrations.add(registration);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromIncoming(PluginMessageListenerRegistration registration) {
/* 113 */     synchronized (this.incomingLock) {
/* 114 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(registration.getChannel());
/*     */       
/* 116 */       if (registrations != null) {
/* 117 */         registrations.remove(registration);
/*     */         
/* 119 */         if (registrations.isEmpty()) {
/* 120 */           this.incomingByChannel.remove(registration.getChannel());
/*     */         }
/*     */       } 
/*     */       
/* 124 */       registrations = this.incomingByPlugin.get(registration.getPlugin());
/*     */       
/* 126 */       if (registrations != null) {
/* 127 */         registrations.remove(registration);
/*     */         
/* 129 */         if (registrations.isEmpty()) {
/* 130 */           this.incomingByPlugin.remove(registration.getPlugin());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromIncoming(Plugin plugin, String channel) {
/* 137 */     synchronized (this.incomingLock) {
/* 138 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
/*     */       
/* 140 */       if (registrations != null) {
/* 141 */         PluginMessageListenerRegistration[] toRemove = registrations.<PluginMessageListenerRegistration>toArray(new PluginMessageListenerRegistration[0]);
/*     */         
/* 143 */         for (PluginMessageListenerRegistration registration : toRemove) {
/* 144 */           if (registration.getChannel().equals(channel)) {
/* 145 */             removeFromIncoming(registration);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromIncoming(Plugin plugin) {
/* 153 */     synchronized (this.incomingLock) {
/* 154 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
/*     */       
/* 156 */       if (registrations != null) {
/* 157 */         PluginMessageListenerRegistration[] toRemove = registrations.<PluginMessageListenerRegistration>toArray(new PluginMessageListenerRegistration[0]);
/*     */         
/* 159 */         this.incomingByPlugin.remove(plugin);
/*     */         
/* 161 */         for (PluginMessageListenerRegistration registration : toRemove) {
/* 162 */           removeFromIncoming(registration);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isReservedChannel(String channel) {
/* 169 */     validateChannel(channel);
/*     */     
/* 171 */     return (channel.equals("REGISTER") || channel.equals("UNREGISTER"));
/*     */   }
/*     */   
/*     */   public void registerOutgoingPluginChannel(Plugin plugin, String channel) {
/* 175 */     if (plugin == null) {
/* 176 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 178 */     validateChannel(channel);
/* 179 */     if (isReservedChannel(channel)) {
/* 180 */       throw new ReservedChannelException(channel);
/*     */     }
/*     */     
/* 183 */     addToOutgoing(plugin, channel);
/*     */   }
/*     */   
/*     */   public void unregisterOutgoingPluginChannel(Plugin plugin, String channel) {
/* 187 */     if (plugin == null) {
/* 188 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 190 */     validateChannel(channel);
/*     */     
/* 192 */     removeFromOutgoing(plugin, channel);
/*     */   }
/*     */   
/*     */   public void unregisterOutgoingPluginChannel(Plugin plugin) {
/* 196 */     if (plugin == null) {
/* 197 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 200 */     removeFromOutgoing(plugin);
/*     */   }
/*     */   
/*     */   public PluginMessageListenerRegistration registerIncomingPluginChannel(Plugin plugin, String channel, PluginMessageListener listener) {
/* 204 */     if (plugin == null) {
/* 205 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 207 */     validateChannel(channel);
/* 208 */     if (isReservedChannel(channel)) {
/* 209 */       throw new ReservedChannelException(channel);
/*     */     }
/* 211 */     if (listener == null) {
/* 212 */       throw new IllegalArgumentException("Listener cannot be null");
/*     */     }
/*     */     
/* 215 */     PluginMessageListenerRegistration result = new PluginMessageListenerRegistration(this, plugin, channel, listener);
/*     */     
/* 217 */     addToIncoming(result);
/*     */     
/* 219 */     return result;
/*     */   }
/*     */   
/*     */   public void unregisterIncomingPluginChannel(Plugin plugin, String channel, PluginMessageListener listener) {
/* 223 */     if (plugin == null) {
/* 224 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 226 */     if (listener == null) {
/* 227 */       throw new IllegalArgumentException("Listener cannot be null");
/*     */     }
/* 229 */     validateChannel(channel);
/*     */     
/* 231 */     removeFromIncoming(new PluginMessageListenerRegistration(this, plugin, channel, listener));
/*     */   }
/*     */   
/*     */   public void unregisterIncomingPluginChannel(Plugin plugin, String channel) {
/* 235 */     if (plugin == null) {
/* 236 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 238 */     validateChannel(channel);
/*     */     
/* 240 */     removeFromIncoming(plugin, channel);
/*     */   }
/*     */   
/*     */   public void unregisterIncomingPluginChannel(Plugin plugin) {
/* 244 */     if (plugin == null) {
/* 245 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 248 */     removeFromIncoming(plugin);
/*     */   }
/*     */   
/*     */   public Set<String> getOutgoingChannels() {
/* 252 */     synchronized (this.outgoingLock) {
/* 253 */       Set<String> keys = this.outgoingByChannel.keySet();
/* 254 */       return (Set<String>)ImmutableSet.copyOf(keys);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> getOutgoingChannels(Plugin plugin) {
/* 259 */     if (plugin == null) {
/* 260 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 263 */     synchronized (this.outgoingLock) {
/* 264 */       Set<String> channels = this.outgoingByPlugin.get(plugin);
/*     */       
/* 266 */       if (channels != null) {
/* 267 */         return (Set<String>)ImmutableSet.copyOf(channels);
/*     */       }
/* 269 */       return (Set<String>)ImmutableSet.of();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getIncomingChannels() {
/* 275 */     synchronized (this.incomingLock) {
/* 276 */       Set<String> keys = this.incomingByChannel.keySet();
/* 277 */       return (Set<String>)ImmutableSet.copyOf(keys);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> getIncomingChannels(Plugin plugin) {
/* 282 */     if (plugin == null) {
/* 283 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 286 */     synchronized (this.incomingLock) {
/* 287 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
/*     */       
/* 289 */       if (registrations != null) {
/* 290 */         ImmutableSet.Builder<String> builder = ImmutableSet.builder();
/*     */         
/* 292 */         for (PluginMessageListenerRegistration registration : registrations) {
/* 293 */           builder.add(registration.getChannel());
/*     */         }
/*     */         
/* 296 */         return (Set<String>)builder.build();
/*     */       } 
/* 298 */       return (Set<String>)ImmutableSet.of();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin plugin) {
/* 304 */     if (plugin == null) {
/* 305 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 308 */     synchronized (this.incomingLock) {
/* 309 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
/*     */       
/* 311 */       if (registrations != null) {
/* 312 */         return (Set<PluginMessageListenerRegistration>)ImmutableSet.copyOf(registrations);
/*     */       }
/* 314 */       return (Set<PluginMessageListenerRegistration>)ImmutableSet.of();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(String channel) {
/* 320 */     validateChannel(channel);
/*     */     
/* 322 */     synchronized (this.incomingLock) {
/* 323 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(channel);
/*     */       
/* 325 */       if (registrations != null) {
/* 326 */         return (Set<PluginMessageListenerRegistration>)ImmutableSet.copyOf(registrations);
/*     */       }
/* 328 */       return (Set<PluginMessageListenerRegistration>)ImmutableSet.of();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin plugin, String channel) {
/* 334 */     if (plugin == null) {
/* 335 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 337 */     validateChannel(channel);
/*     */     
/* 339 */     synchronized (this.incomingLock) {
/* 340 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
/*     */       
/* 342 */       if (registrations != null) {
/* 343 */         ImmutableSet.Builder<PluginMessageListenerRegistration> builder = ImmutableSet.builder();
/*     */         
/* 345 */         for (PluginMessageListenerRegistration registration : registrations) {
/* 346 */           if (registration.getChannel().equals(channel)) {
/* 347 */             builder.add(registration);
/*     */           }
/*     */         } 
/*     */         
/* 351 */         return (Set<PluginMessageListenerRegistration>)builder.build();
/*     */       } 
/* 353 */       return (Set<PluginMessageListenerRegistration>)ImmutableSet.of();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRegistrationValid(PluginMessageListenerRegistration registration) {
/* 359 */     if (registration == null) {
/* 360 */       throw new IllegalArgumentException("Registration cannot be null");
/*     */     }
/*     */     
/* 363 */     synchronized (this.incomingLock) {
/* 364 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(registration.getPlugin());
/*     */       
/* 366 */       if (registrations != null) {
/* 367 */         return registrations.contains(registration);
/*     */       }
/*     */       
/* 370 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isIncomingChannelRegistered(Plugin plugin, String channel) {
/* 375 */     if (plugin == null) {
/* 376 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 378 */     validateChannel(channel);
/*     */     
/* 380 */     synchronized (this.incomingLock) {
/* 381 */       Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
/*     */       
/* 383 */       if (registrations != null) {
/* 384 */         for (PluginMessageListenerRegistration registration : registrations) {
/* 385 */           if (registration.getChannel().equals(channel)) {
/* 386 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 391 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isOutgoingChannelRegistered(Plugin plugin, String channel) {
/* 396 */     if (plugin == null) {
/* 397 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/* 399 */     validateChannel(channel);
/*     */     
/* 401 */     synchronized (this.outgoingLock) {
/* 402 */       Set<String> channels = this.outgoingByPlugin.get(plugin);
/*     */       
/* 404 */       if (channels != null) {
/* 405 */         return channels.contains(channel);
/*     */       }
/*     */       
/* 408 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispatchIncomingMessage(Player source, String channel, byte[] message) {
/* 413 */     if (source == null) {
/* 414 */       throw new IllegalArgumentException("Player source cannot be null");
/*     */     }
/* 416 */     if (message == null) {
/* 417 */       throw new IllegalArgumentException("Message cannot be null");
/*     */     }
/* 419 */     validateChannel(channel);
/*     */     
/* 421 */     Set<PluginMessageListenerRegistration> registrations = getIncomingChannelRegistrations(channel);
/*     */     
/* 423 */     for (PluginMessageListenerRegistration registration : registrations) {
/*     */ 
/*     */       
/*     */       try {
/* 427 */         registration.getListener().onPluginMessageReceived(channel, source, message);
/* 428 */       } catch (Throwable t) {
/*     */         
/* 430 */         Bukkit.getLogger().log(Level.WARNING, "Could not pass incoming plugin message to " + registration.getPlugin(), t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateChannel(String channel) {
/* 442 */     if (channel == null) {
/* 443 */       throw new IllegalArgumentException("Channel cannot be null");
/*     */     }
/* 445 */     if (channel.length() > 16) {
/* 446 */       throw new ChannelNameTooLongException(channel);
/*     */     }
/*     */   }
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
/*     */   public static void validatePluginMessage(Messenger messenger, Plugin source, String channel, byte[] message) {
/* 469 */     if (messenger == null) {
/* 470 */       throw new IllegalArgumentException("Messenger cannot be null");
/*     */     }
/* 472 */     if (source == null) {
/* 473 */       throw new IllegalArgumentException("Plugin source cannot be null");
/*     */     }
/* 475 */     if (!source.isEnabled()) {
/* 476 */       throw new IllegalArgumentException("Plugin must be enabled to send messages");
/*     */     }
/* 478 */     if (message == null) {
/* 479 */       throw new IllegalArgumentException("Message cannot be null");
/*     */     }
/* 481 */     if (!messenger.isOutgoingChannelRegistered(source, channel)) {
/* 482 */       throw new ChannelNotRegisteredException(channel);
/*     */     }
/* 484 */     if (message.length > 32766) {
/* 485 */       throw new MessageTooLargeException(message);
/*     */     }
/* 487 */     validateChannel(channel);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\StandardMessenger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */