/*     */ package org.bukkit.event;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.RegisteredListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandlerList
/*     */ {
/*  18 */   private volatile RegisteredListener[] handlers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EnumMap<EventPriority, ArrayList<RegisteredListener>> handlerslots;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private static ArrayList<HandlerList> allLists = new ArrayList<HandlerList>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bakeAll() {
/*  38 */     synchronized (allLists) {
/*  39 */       for (HandlerList h : allLists) {
/*  40 */         h.bake();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterAll() {
/*  49 */     synchronized (allLists) {
/*  50 */       for (HandlerList h : allLists) {
/*  51 */         synchronized (h) {
/*  52 */           for (List<RegisteredListener> list : h.handlerslots.values()) {
/*  53 */             list.clear();
/*     */           }
/*  55 */           h.handlers = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterAll(Plugin plugin) {
/*  67 */     synchronized (allLists) {
/*  68 */       for (HandlerList h : allLists) {
/*  69 */         h.unregister(plugin);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterAll(Listener listener) {
/*  80 */     synchronized (allLists) {
/*  81 */       for (HandlerList h : allLists) {
/*  82 */         h.unregister(listener);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerList() {
/*  93 */     this.handlerslots = new EnumMap<EventPriority, ArrayList<RegisteredListener>>(EventPriority.class);
/*  94 */     for (EventPriority o : EventPriority.values()) {
/*  95 */       this.handlerslots.put(o, new ArrayList<RegisteredListener>());
/*     */     }
/*  97 */     synchronized (allLists) {
/*  98 */       allLists.add(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void register(RegisteredListener listener) {
/* 108 */     if (((ArrayList)this.handlerslots.get(listener.getPriority())).contains(listener))
/* 109 */       throw new IllegalStateException("This listener is already registered to priority " + listener.getPriority().toString()); 
/* 110 */     this.handlers = null;
/* 111 */     ((ArrayList<RegisteredListener>)this.handlerslots.get(listener.getPriority())).add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAll(Collection<RegisteredListener> listeners) {
/* 120 */     for (RegisteredListener listener : listeners) {
/* 121 */       register(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void unregister(RegisteredListener listener) {
/* 131 */     if (((ArrayList)this.handlerslots.get(listener.getPriority())).remove(listener)) {
/* 132 */       this.handlers = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void unregister(Plugin plugin) {
/* 142 */     boolean changed = false;
/* 143 */     for (List<RegisteredListener> list : this.handlerslots.values()) {
/* 144 */       for (ListIterator<RegisteredListener> i = list.listIterator(); i.hasNext();) {
/* 145 */         if (((RegisteredListener)i.next()).getPlugin().equals(plugin)) {
/* 146 */           i.remove();
/* 147 */           changed = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 151 */     if (changed) this.handlers = null;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void unregister(Listener listener) {
/* 160 */     boolean changed = false;
/* 161 */     for (List<RegisteredListener> list : this.handlerslots.values()) {
/* 162 */       for (ListIterator<RegisteredListener> i = list.listIterator(); i.hasNext();) {
/* 163 */         if (((RegisteredListener)i.next()).getListener().equals(listener)) {
/* 164 */           i.remove();
/* 165 */           changed = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 169 */     if (changed) this.handlers = null;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void bake() {
/* 176 */     if (this.handlers != null)
/* 177 */       return;  List<RegisteredListener> entries = new ArrayList<RegisteredListener>();
/* 178 */     for (Map.Entry<EventPriority, ArrayList<RegisteredListener>> entry : this.handlerslots.entrySet()) {
/* 179 */       entries.addAll(entry.getValue());
/*     */     }
/* 181 */     this.handlers = entries.<RegisteredListener>toArray(new RegisteredListener[entries.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegisteredListener[] getRegisteredListeners() {
/*     */     RegisteredListener[] handlers;
/* 191 */     for (; (handlers = this.handlers) == null; bake());
/* 192 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<RegisteredListener> getRegisteredListeners(Plugin plugin) {
/* 203 */     ArrayList<RegisteredListener> listeners = new ArrayList<RegisteredListener>();
/* 204 */     synchronized (allLists) {
/* 205 */       for (HandlerList h : allLists) {
/* 206 */         synchronized (h) {
/* 207 */           for (List<RegisteredListener> list : h.handlerslots.values()) {
/* 208 */             for (RegisteredListener listener : list) {
/* 209 */               if (listener.getPlugin().equals(plugin)) {
/* 210 */                 listeners.add(listener);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 217 */     return listeners;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<HandlerList> getHandlerLists() {
/* 227 */     synchronized (allLists) {
/* 228 */       return (ArrayList<HandlerList>)allLists.clone();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\HandlerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */