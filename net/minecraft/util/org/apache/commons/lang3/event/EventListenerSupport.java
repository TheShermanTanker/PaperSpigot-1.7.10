/*     */ package net.minecraft.util.org.apache.commons.lang3.event;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.util.org.apache.commons.lang3.Validate;
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
/*     */ public class EventListenerSupport<L>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3593265990380473632L;
/*  76 */   private List<L> listeners = new CopyOnWriteArrayList<L>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient L proxy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient L[] prototypeArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> EventListenerSupport<T> create(Class<T> listenerInterface) {
/* 106 */     return new EventListenerSupport<T>(listenerInterface);
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
/*     */   public EventListenerSupport(Class<L> listenerInterface) {
/* 122 */     this(listenerInterface, Thread.currentThread().getContextClassLoader());
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
/*     */   public EventListenerSupport(Class<L> listenerInterface, ClassLoader classLoader) {
/* 139 */     this();
/* 140 */     Validate.notNull(listenerInterface, "Listener interface cannot be null.", new Object[0]);
/* 141 */     Validate.notNull(classLoader, "ClassLoader cannot be null.", new Object[0]);
/* 142 */     Validate.isTrue(listenerInterface.isInterface(), "Class {0} is not an interface", new Object[] { listenerInterface.getName() });
/*     */     
/* 144 */     initializeTransientFields(listenerInterface, classLoader);
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
/*     */   public L fire() {
/* 163 */     return this.proxy;
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
/*     */   public void addListener(L listener) {
/* 179 */     Validate.notNull(listener, "Listener object cannot be null.", new Object[0]);
/* 180 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getListenerCount() {
/* 189 */     return this.listeners.size();
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
/*     */   public void removeListener(L listener) {
/* 201 */     Validate.notNull(listener, "Listener object cannot be null.", new Object[0]);
/* 202 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L[] getListeners() {
/* 212 */     return this.listeners.toArray(this.prototypeArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
/* 221 */     ArrayList<L> serializableListeners = new ArrayList<L>();
/*     */ 
/*     */     
/* 224 */     ObjectOutputStream testObjectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
/* 225 */     for (L listener : this.listeners) {
/*     */       try {
/* 227 */         testObjectOutputStream.writeObject(listener);
/* 228 */         serializableListeners.add(listener);
/* 229 */       } catch (IOException exception) {
/*     */         
/* 231 */         testObjectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     objectOutputStream.writeObject(serializableListeners.toArray(this.prototypeArray));
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
/*     */   private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
/* 250 */     L[] srcListeners = (L[])objectInputStream.readObject();
/*     */     
/* 252 */     this.listeners = new CopyOnWriteArrayList<L>(srcListeners);
/*     */ 
/*     */ 
/*     */     
/* 256 */     Class<L> listenerInterface = (Class)srcListeners.getClass().getComponentType();
/*     */     
/* 258 */     initializeTransientFields(listenerInterface, Thread.currentThread().getContextClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeTransientFields(Class<L> listenerInterface, ClassLoader classLoader) {
/* 269 */     L[] array = (L[])Array.newInstance(listenerInterface, 0);
/* 270 */     this.prototypeArray = array;
/* 271 */     createProxy(listenerInterface, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createProxy(Class<L> listenerInterface, ClassLoader classLoader) {
/* 280 */     this.proxy = listenerInterface.cast(Proxy.newProxyInstance(classLoader, new Class[] { listenerInterface }, createInvocationHandler()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InvocationHandler createInvocationHandler() {
/* 290 */     return new ProxyInvocationHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EventListenerSupport() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class ProxyInvocationHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     public Object invoke(Object unusedProxy, Method method, Object[] args) throws Throwable {
/* 312 */       for (L listener : EventListenerSupport.this.listeners) {
/* 313 */         method.invoke(listener, args);
/*     */       }
/* 315 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\event\EventListenerSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */