/*     */ package org.bukkit.plugin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.server.ServiceRegisterEvent;
/*     */ import org.bukkit.event.server.ServiceUnregisterEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleServicesManager
/*     */   implements ServicesManager
/*     */ {
/*  27 */   private final Map<Class<?>, List<RegisteredServiceProvider<?>>> providers = new HashMap<Class<?>, List<RegisteredServiceProvider<?>>>();
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
/*     */   public <T> void register(Class<T> service, T provider, Plugin plugin, ServicePriority priority) {
/*  39 */     RegisteredServiceProvider<T> registeredProvider = null;
/*  40 */     synchronized (this.providers) {
/*  41 */       List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
/*  42 */       if (registered == null) {
/*  43 */         registered = new ArrayList<RegisteredServiceProvider<?>>();
/*  44 */         this.providers.put(service, registered);
/*     */       } 
/*     */       
/*  47 */       registeredProvider = new RegisteredServiceProvider<T>(service, provider, priority, plugin);
/*     */ 
/*     */       
/*  50 */       int position = Collections.binarySearch((List)registered, registeredProvider);
/*  51 */       if (position < 0) {
/*  52 */         registered.add(-(position + 1), registeredProvider);
/*     */       } else {
/*  54 */         registered.add(position, registeredProvider);
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     Bukkit.getServer().getPluginManager().callEvent((Event)new ServiceRegisterEvent(registeredProvider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterAll(Plugin plugin) {
/*  67 */     ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList<ServiceUnregisterEvent>();
/*  68 */     synchronized (this.providers) {
/*  69 */       Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
/*     */       
/*     */       try {
/*  72 */         while (it.hasNext()) {
/*  73 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = it.next();
/*  74 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List<RegisteredServiceProvider<?>>)entry.getValue()).iterator();
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*  79 */             while (it2.hasNext()) {
/*  80 */               RegisteredServiceProvider<?> registered = it2.next();
/*     */               
/*  82 */               if (registered.getPlugin().equals(plugin)) {
/*  83 */                 it2.remove();
/*  84 */                 unregisteredEvents.add(new ServiceUnregisterEvent(registered));
/*     */               } 
/*     */             } 
/*  87 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */ 
/*     */           
/*  91 */           if (((List)entry.getValue()).size() == 0) {
/*  92 */             it.remove();
/*     */           }
/*     */         } 
/*  95 */       } catch (NoSuchElementException e) {}
/*     */     } 
/*  97 */     for (ServiceUnregisterEvent event : unregisteredEvents) {
/*  98 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(Class<?> service, Object provider) {
/* 109 */     ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList<ServiceUnregisterEvent>();
/* 110 */     synchronized (this.providers) {
/* 111 */       Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
/*     */       
/*     */       try {
/* 114 */         while (it.hasNext()) {
/* 115 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = it.next();
/*     */ 
/*     */           
/* 118 */           if (entry.getKey() != service) {
/*     */             continue;
/*     */           }
/*     */           
/* 122 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List<RegisteredServiceProvider<?>>)entry.getValue()).iterator();
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 127 */             while (it2.hasNext()) {
/* 128 */               RegisteredServiceProvider<?> registered = it2.next();
/*     */               
/* 130 */               if (registered.getProvider() == provider) {
/* 131 */                 it2.remove();
/* 132 */                 unregisteredEvents.add(new ServiceUnregisterEvent(registered));
/*     */               } 
/*     */             } 
/* 135 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */ 
/*     */           
/* 139 */           if (((List)entry.getValue()).size() == 0) {
/* 140 */             it.remove();
/*     */           }
/*     */         } 
/* 143 */       } catch (NoSuchElementException e) {}
/*     */     } 
/* 145 */     for (ServiceUnregisterEvent event : unregisteredEvents) {
/* 146 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(Object provider) {
/* 156 */     ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList<ServiceUnregisterEvent>();
/* 157 */     synchronized (this.providers) {
/* 158 */       Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
/*     */       
/*     */       try {
/* 161 */         while (it.hasNext()) {
/* 162 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = it.next();
/* 163 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List<RegisteredServiceProvider<?>>)entry.getValue()).iterator();
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 168 */             while (it2.hasNext()) {
/* 169 */               RegisteredServiceProvider<?> registered = it2.next();
/*     */               
/* 171 */               if (registered.getProvider().equals(provider)) {
/* 172 */                 it2.remove();
/* 173 */                 unregisteredEvents.add(new ServiceUnregisterEvent(registered));
/*     */               } 
/*     */             } 
/* 176 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */ 
/*     */           
/* 180 */           if (((List)entry.getValue()).size() == 0) {
/* 181 */             it.remove();
/*     */           }
/*     */         } 
/* 184 */       } catch (NoSuchElementException e) {}
/*     */     } 
/* 186 */     for (ServiceUnregisterEvent event : unregisteredEvents) {
/* 187 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
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
/*     */   public <T> T load(Class<T> service) {
/* 200 */     synchronized (this.providers) {
/* 201 */       List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
/*     */       
/* 203 */       if (registered == null) {
/* 204 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 208 */       return service.cast(((RegisteredServiceProvider)registered.get(0)).getProvider());
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
/*     */   public <T> RegisteredServiceProvider<T> getRegistration(Class<T> service) {
/* 222 */     synchronized (this.providers) {
/* 223 */       List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
/*     */       
/* 225 */       if (registered == null) {
/* 226 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 230 */       return (RegisteredServiceProvider<T>)registered.get(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RegisteredServiceProvider<?>> getRegistrations(Plugin plugin) {
/* 241 */     ImmutableList.Builder<RegisteredServiceProvider<?>> ret = ImmutableList.builder();
/* 242 */     synchronized (this.providers) {
/* 243 */       for (List<RegisteredServiceProvider<?>> registered : this.providers.values()) {
/* 244 */         for (RegisteredServiceProvider<?> provider : registered) {
/* 245 */           if (provider.getPlugin().equals(plugin)) {
/* 246 */             ret.add(provider);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 251 */     return (List<RegisteredServiceProvider<?>>)ret.build();
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
/*     */   public <T> List<RegisteredServiceProvider<T>> getRegistrations(Class<T> service) {
/*     */     ImmutableList.Builder<RegisteredServiceProvider<T>> ret;
/* 265 */     synchronized (this.providers) {
/* 266 */       List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
/*     */       
/* 268 */       if (registered == null) {
/* 269 */         return (List<RegisteredServiceProvider<T>>)ImmutableList.of();
/*     */       }
/*     */       
/* 272 */       ret = ImmutableList.builder();
/*     */       
/* 274 */       for (RegisteredServiceProvider<?> provider : registered) {
/* 275 */         ret.add(provider);
/*     */       }
/*     */     } 
/*     */     
/* 279 */     return (List<RegisteredServiceProvider<T>>)ret.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Class<?>> getKnownServices() {
/* 289 */     synchronized (this.providers) {
/* 290 */       return (Set<Class<?>>)ImmutableSet.copyOf(this.providers.keySet());
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
/*     */   public <T> boolean isProvidedFor(Class<T> service) {
/* 302 */     synchronized (this.providers) {
/* 303 */       return this.providers.containsKey(service);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\SimpleServicesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */