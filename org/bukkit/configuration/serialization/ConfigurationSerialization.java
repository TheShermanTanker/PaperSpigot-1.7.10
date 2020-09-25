/*     */ package org.bukkit.configuration.serialization;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.util.BlockVector;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationSerialization
/*     */ {
/*     */   public static final String SERIALIZED_TYPE_KEY = "==";
/*     */   private final Class<? extends ConfigurationSerializable> clazz;
/*  27 */   private static Map<String, Class<? extends ConfigurationSerializable>> aliases = new HashMap<String, Class<? extends ConfigurationSerializable>>();
/*     */   
/*     */   static {
/*  30 */     registerClass((Class)Vector.class);
/*  31 */     registerClass((Class)BlockVector.class);
/*  32 */     registerClass((Class)ItemStack.class);
/*  33 */     registerClass((Class)Color.class);
/*  34 */     registerClass((Class)PotionEffect.class);
/*  35 */     registerClass((Class)FireworkEffect.class);
/*     */   }
/*     */   
/*     */   protected ConfigurationSerialization(Class<? extends ConfigurationSerializable> clazz) {
/*  39 */     this.clazz = clazz;
/*     */   }
/*     */   
/*     */   protected Method getMethod(String name, boolean isStatic) {
/*     */     try {
/*  44 */       Method method = this.clazz.getDeclaredMethod(name, new Class[] { Map.class });
/*     */       
/*  46 */       if (!ConfigurationSerializable.class.isAssignableFrom(method.getReturnType())) {
/*  47 */         return null;
/*     */       }
/*  49 */       if (Modifier.isStatic(method.getModifiers()) != isStatic) {
/*  50 */         return null;
/*     */       }
/*     */       
/*  53 */       return method;
/*  54 */     } catch (NoSuchMethodException ex) {
/*  55 */       return null;
/*  56 */     } catch (SecurityException ex) {
/*  57 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Constructor<? extends ConfigurationSerializable> getConstructor() {
/*     */     try {
/*  63 */       return this.clazz.getConstructor(new Class[] { Map.class });
/*  64 */     } catch (NoSuchMethodException ex) {
/*  65 */       return null;
/*  66 */     } catch (SecurityException ex) {
/*  67 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ConfigurationSerializable deserializeViaMethod(Method method, Map<String, ?> args) {
/*     */     try {
/*  73 */       ConfigurationSerializable result = (ConfigurationSerializable)method.invoke(null, new Object[] { args });
/*     */       
/*  75 */       if (result == null) {
/*  76 */         Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization: method returned null");
/*     */       } else {
/*  78 */         return result;
/*     */       } 
/*  80 */     } catch (Throwable ex) {
/*  81 */       Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof java.lang.reflect.InvocationTargetException) ? ex.getCause() : ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   protected ConfigurationSerializable deserializeViaCtor(Constructor<? extends ConfigurationSerializable> ctor, Map<String, ?> args) {
/*     */     try {
/*  92 */       return ctor.newInstance(new Object[] { args });
/*  93 */     } catch (Throwable ex) {
/*  94 */       Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call constructor '" + ctor.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof java.lang.reflect.InvocationTargetException) ? ex.getCause() : ex);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 100 */       return null;
/*     */     } 
/*     */   }
/*     */   public ConfigurationSerializable deserialize(Map<String, ?> args) {
/* 104 */     Validate.notNull(args, "Args must not be null");
/*     */     
/* 106 */     ConfigurationSerializable result = null;
/* 107 */     Method method = null;
/*     */     
/* 109 */     if (result == null) {
/* 110 */       method = getMethod("deserialize", true);
/*     */       
/* 112 */       if (method != null) {
/* 113 */         result = deserializeViaMethod(method, args);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if (result == null) {
/* 118 */       method = getMethod("valueOf", true);
/*     */       
/* 120 */       if (method != null) {
/* 121 */         result = deserializeViaMethod(method, args);
/*     */       }
/*     */     } 
/*     */     
/* 125 */     if (result == null) {
/* 126 */       Constructor<? extends ConfigurationSerializable> constructor = getConstructor();
/*     */       
/* 128 */       if (constructor != null) {
/* 129 */         result = deserializeViaCtor(constructor, args);
/*     */       }
/*     */     } 
/*     */     
/* 133 */     return result;
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
/*     */   public static ConfigurationSerializable deserializeObject(Map<String, ?> args, Class<? extends ConfigurationSerializable> clazz) {
/* 152 */     return (new ConfigurationSerialization(clazz)).deserialize(args);
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
/*     */   public static ConfigurationSerializable deserializeObject(Map<String, ?> args) {
/* 170 */     Class<? extends ConfigurationSerializable> clazz = null;
/*     */     
/* 172 */     if (args.containsKey("==")) {
/*     */       try {
/* 174 */         String alias = (String)args.get("==");
/*     */         
/* 176 */         if (alias == null) {
/* 177 */           throw new IllegalArgumentException("Cannot have null alias");
/*     */         }
/* 179 */         clazz = getClassByAlias(alias);
/* 180 */         if (clazz == null) {
/* 181 */           throw new IllegalArgumentException("Specified class does not exist ('" + alias + "')");
/*     */         }
/* 183 */       } catch (ClassCastException ex) {
/* 184 */         ex.fillInStackTrace();
/* 185 */         throw ex;
/*     */       } 
/*     */     } else {
/* 188 */       throw new IllegalArgumentException("Args doesn't contain type key ('==')");
/*     */     } 
/*     */     
/* 191 */     return (new ConfigurationSerialization(clazz)).deserialize(args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerClass(Class<? extends ConfigurationSerializable> clazz) {
/* 201 */     DelegateDeserialization delegate = clazz.<DelegateDeserialization>getAnnotation(DelegateDeserialization.class);
/*     */     
/* 203 */     if (delegate == null) {
/* 204 */       registerClass(clazz, getAlias(clazz));
/* 205 */       registerClass(clazz, clazz.getName());
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
/*     */   public static void registerClass(Class<? extends ConfigurationSerializable> clazz, String alias) {
/* 218 */     aliases.put(alias, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterClass(String alias) {
/* 227 */     aliases.remove(alias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterClass(Class<? extends ConfigurationSerializable> clazz) {
/* 237 */     while (aliases.values().remove(clazz));
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
/*     */   public static Class<? extends ConfigurationSerializable> getClassByAlias(String alias) {
/* 250 */     return aliases.get(alias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAlias(Class<? extends ConfigurationSerializable> clazz) {
/* 261 */     DelegateDeserialization delegate = clazz.<DelegateDeserialization>getAnnotation(DelegateDeserialization.class);
/*     */     
/* 263 */     if (delegate != null) {
/* 264 */       if (delegate.value() == null || delegate.value() == clazz) {
/* 265 */         delegate = null;
/*     */       } else {
/* 267 */         return getAlias(delegate.value());
/*     */       } 
/*     */     }
/*     */     
/* 271 */     if (delegate == null) {
/* 272 */       SerializableAs alias = clazz.<SerializableAs>getAnnotation(SerializableAs.class);
/*     */       
/* 274 */       if (alias != null && alias.value() != null) {
/* 275 */         return alias.value();
/*     */       }
/*     */     } 
/*     */     
/* 279 */     return clazz.getName();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\serialization\ConfigurationSerialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */