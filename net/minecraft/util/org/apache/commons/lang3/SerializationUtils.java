/*     */ package net.minecraft.util.org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerializationUtils
/*     */ {
/*     */   public static <T extends Serializable> T clone(T object) {
/*  79 */     if (object == null) {
/*  80 */       return null;
/*     */     }
/*  82 */     byte[] objectData = serialize((Serializable)object);
/*  83 */     ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
/*     */     
/*  85 */     ClassLoaderAwareObjectInputStream in = null;
/*     */     
/*     */     try {
/*  88 */       in = new ClassLoaderAwareObjectInputStream(bais, object.getClass().getClassLoader());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       Serializable serializable = (Serializable)in.readObject();
/*  97 */       return (T)serializable;
/*     */     }
/*  99 */     catch (ClassNotFoundException ex) {
/* 100 */       throw new SerializationException("ClassNotFoundException while reading cloned object data", ex);
/* 101 */     } catch (IOException ex) {
/* 102 */       throw new SerializationException("IOException while reading cloned object data", ex);
/*     */     } finally {
/*     */       try {
/* 105 */         if (in != null) {
/* 106 */           in.close();
/*     */         }
/* 108 */       } catch (IOException ex) {
/* 109 */         throw new SerializationException("IOException on closing cloned object data InputStream.", ex);
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
/*     */   public static void serialize(Serializable obj, OutputStream outputStream) {
/* 132 */     if (outputStream == null) {
/* 133 */       throw new IllegalArgumentException("The OutputStream must not be null");
/*     */     }
/* 135 */     ObjectOutputStream out = null;
/*     */     
/*     */     try {
/* 138 */       out = new ObjectOutputStream(outputStream);
/* 139 */       out.writeObject(obj);
/*     */     }
/* 141 */     catch (IOException ex) {
/* 142 */       throw new SerializationException(ex);
/*     */     } finally {
/*     */       try {
/* 145 */         if (out != null) {
/* 146 */           out.close();
/*     */         }
/* 148 */       } catch (IOException ex) {}
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
/*     */   public static byte[] serialize(Serializable obj) {
/* 163 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
/* 164 */     serialize(obj, baos);
/* 165 */     return baos.toByteArray();
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
/*     */   public static <T> T deserialize(InputStream inputStream) {
/* 201 */     if (inputStream == null) {
/* 202 */       throw new IllegalArgumentException("The InputStream must not be null");
/*     */     }
/* 204 */     ObjectInputStream in = null;
/*     */     
/*     */     try {
/* 207 */       in = new ObjectInputStream(inputStream);
/*     */       
/* 209 */       T obj = (T)in.readObject();
/* 210 */       return obj;
/*     */     }
/* 212 */     catch (ClassCastException ex) {
/* 213 */       throw new SerializationException(ex);
/* 214 */     } catch (ClassNotFoundException ex) {
/* 215 */       throw new SerializationException(ex);
/* 216 */     } catch (IOException ex) {
/* 217 */       throw new SerializationException(ex);
/*     */     } finally {
/*     */       try {
/* 220 */         if (in != null) {
/* 221 */           in.close();
/*     */         }
/* 223 */       } catch (IOException ex) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T deserialize(byte[] objectData) {
/* 250 */     if (objectData == null) {
/* 251 */       throw new IllegalArgumentException("The byte[] must not be null");
/*     */     }
/* 253 */     return deserialize(new ByteArrayInputStream(objectData));
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
/*     */   static class ClassLoaderAwareObjectInputStream
/*     */     extends ObjectInputStream
/*     */   {
/* 270 */     private static final Map<String, Class<?>> primitiveTypes = new HashMap<String, Class<?>>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ClassLoaderAwareObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
/* 282 */       super(in);
/* 283 */       this.classLoader = classLoader;
/*     */       
/* 285 */       primitiveTypes.put("byte", byte.class);
/* 286 */       primitiveTypes.put("short", short.class);
/* 287 */       primitiveTypes.put("int", int.class);
/* 288 */       primitiveTypes.put("long", long.class);
/* 289 */       primitiveTypes.put("float", float.class);
/* 290 */       primitiveTypes.put("double", double.class);
/* 291 */       primitiveTypes.put("boolean", boolean.class);
/* 292 */       primitiveTypes.put("char", char.class);
/* 293 */       primitiveTypes.put("void", void.class);
/*     */     }
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
/*     */     protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/* 306 */       String name = desc.getName();
/*     */       try {
/* 308 */         return Class.forName(name, false, this.classLoader);
/* 309 */       } catch (ClassNotFoundException ex) {
/*     */         try {
/* 311 */           return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
/* 312 */         } catch (ClassNotFoundException cnfe) {
/* 313 */           Class<?> cls = primitiveTypes.get(name);
/* 314 */           if (cls != null) {
/* 315 */             return cls;
/*     */           }
/* 317 */           throw cnfe;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\SerializationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */