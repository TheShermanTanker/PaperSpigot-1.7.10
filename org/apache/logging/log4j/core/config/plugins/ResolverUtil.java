/*     */ package org.apache.logging.log4j.core.config.plugins;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarInputStream;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.osgi.framework.FrameworkUtil;
/*     */ import org.osgi.framework.wiring.BundleWiring;
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
/*     */ public class ResolverUtil
/*     */ {
/*  76 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   private static final String VFSZIP = "vfszip";
/*     */   
/*     */   private static final String BUNDLE_RESOURCE = "bundleresource";
/*     */   
/*  83 */   private final Set<Class<?>> classMatches = new HashSet<Class<?>>();
/*     */ 
/*     */   
/*  86 */   private final Set<URI> resourceMatches = new HashSet<URI>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassLoader classloader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Class<?>> getClasses() {
/* 101 */     return this.classMatches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<URI> getResources() {
/* 109 */     return this.resourceMatches;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader() {
/* 120 */     return (this.classloader != null) ? this.classloader : (this.classloader = Loader.getClassLoader(ResolverUtil.class, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClassLoader(ClassLoader classloader) {
/* 129 */     this.classloader = classloader;
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
/*     */   public void findImplementations(Class<?> parent, String... packageNames) {
/* 141 */     if (packageNames == null) {
/*     */       return;
/*     */     }
/*     */     
/* 145 */     Test test = new IsA(parent);
/* 146 */     for (String pkg : packageNames) {
/* 147 */       findInPackage(test, pkg);
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
/*     */   public void findSuffix(String suffix, String... packageNames) {
/* 159 */     if (packageNames == null) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     Test test = new NameEndsWith(suffix);
/* 164 */     for (String pkg : packageNames) {
/* 165 */       findInPackage(test, pkg);
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
/*     */   public void findAnnotated(Class<? extends Annotation> annotation, String... packageNames) {
/* 177 */     if (packageNames == null) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     Test test = new AnnotatedWith(annotation);
/* 182 */     for (String pkg : packageNames) {
/* 183 */       findInPackage(test, pkg);
/*     */     }
/*     */   }
/*     */   
/*     */   public void findNamedResource(String name, String... pathNames) {
/* 188 */     if (pathNames == null) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     Test test = new NameIs(name);
/* 193 */     for (String pkg : pathNames) {
/* 194 */       findInPackage(test, pkg);
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
/*     */   public void find(Test test, String... packageNames) {
/* 206 */     if (packageNames == null) {
/*     */       return;
/*     */     }
/*     */     
/* 210 */     for (String pkg : packageNames) {
/* 211 */       findInPackage(test, pkg);
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
/*     */   public void findInPackage(Test test, String packageName) {
/*     */     Enumeration<URL> urls;
/* 226 */     packageName = packageName.replace('.', '/');
/* 227 */     ClassLoader loader = getClassLoader();
/*     */ 
/*     */     
/*     */     try {
/* 231 */       urls = loader.getResources(packageName);
/* 232 */     } catch (IOException ioe) {
/* 233 */       LOGGER.warn("Could not read package: " + packageName, ioe);
/*     */       
/*     */       return;
/*     */     } 
/* 237 */     while (urls.hasMoreElements()) {
/*     */       try {
/* 239 */         URL url = urls.nextElement();
/* 240 */         String urlPath = url.getFile();
/* 241 */         urlPath = URLDecoder.decode(urlPath, Charsets.UTF_8.name());
/*     */ 
/*     */         
/* 244 */         if (urlPath.startsWith("file:")) {
/* 245 */           urlPath = urlPath.substring(5);
/*     */         }
/*     */ 
/*     */         
/* 249 */         if (urlPath.indexOf('!') > 0) {
/* 250 */           urlPath = urlPath.substring(0, urlPath.indexOf('!'));
/*     */         }
/*     */         
/* 253 */         LOGGER.info("Scanning for classes in [" + urlPath + "] matching criteria: " + test);
/*     */         
/* 255 */         if ("vfszip".equals(url.getProtocol())) {
/* 256 */           String path = urlPath.substring(0, urlPath.length() - packageName.length() - 2);
/* 257 */           URL newURL = new URL(url.getProtocol(), url.getHost(), path);
/*     */           
/* 259 */           JarInputStream stream = new JarInputStream(newURL.openStream());
/*     */           try {
/* 261 */             loadImplementationsInJar(test, packageName, path, stream);
/*     */           } finally {
/* 263 */             close(stream, newURL);
/*     */           }  continue;
/* 265 */         }  if ("bundleresource".equals(url.getProtocol())) {
/* 266 */           loadImplementationsInBundle(test, packageName); continue;
/*     */         } 
/* 268 */         File file = new File(urlPath);
/* 269 */         if (file.isDirectory()) {
/* 270 */           loadImplementationsInDirectory(test, packageName, file); continue;
/*     */         } 
/* 272 */         loadImplementationsInJar(test, packageName, file);
/*     */       
/*     */       }
/* 275 */       catch (IOException ioe) {
/* 276 */         LOGGER.warn("could not read entries", ioe);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadImplementationsInBundle(Test test, String packageName) {
/* 283 */     BundleWiring wiring = (BundleWiring)FrameworkUtil.getBundle(ResolverUtil.class).adapt(BundleWiring.class);
/*     */     
/* 285 */     Collection<String> list = wiring.listResources(packageName, "*.class", 1);
/*     */     
/* 287 */     for (String name : list) {
/* 288 */       addIfMatching(test, name);
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
/*     */   private void loadImplementationsInDirectory(Test test, String parent, File location) {
/* 306 */     File[] files = location.listFiles();
/* 307 */     if (files == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 312 */     for (File file : files) {
/* 313 */       StringBuilder builder = new StringBuilder();
/* 314 */       builder.append(parent).append("/").append(file.getName());
/* 315 */       String packageOrClass = (parent == null) ? file.getName() : builder.toString();
/*     */       
/* 317 */       if (file.isDirectory()) {
/* 318 */         loadImplementationsInDirectory(test, packageOrClass, file);
/* 319 */       } else if (isTestApplicable(test, file.getName())) {
/* 320 */         addIfMatching(test, packageOrClass);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isTestApplicable(Test test, String path) {
/* 326 */     return (test.doesMatchResource() || (path.endsWith(".class") && test.doesMatchClass()));
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
/*     */   private void loadImplementationsInJar(Test test, String parent, File jarFile) {
/* 340 */     JarInputStream jarStream = null;
/*     */     try {
/* 342 */       jarStream = new JarInputStream(new FileInputStream(jarFile));
/* 343 */       loadImplementationsInJar(test, parent, jarFile.getPath(), jarStream);
/* 344 */     } catch (FileNotFoundException ex) {
/* 345 */       LOGGER.error("Could not search jar file '" + jarFile + "' for classes matching criteria: " + test + " file not found");
/*     */     }
/* 347 */     catch (IOException ioe) {
/* 348 */       LOGGER.error("Could not search jar file '" + jarFile + "' for classes matching criteria: " + test + " due to an IOException", ioe);
/*     */     } finally {
/*     */       
/* 351 */       close(jarStream, jarFile);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close(JarInputStream jarStream, Object source) {
/* 360 */     if (jarStream != null) {
/*     */       try {
/* 362 */         jarStream.close();
/* 363 */       } catch (IOException e) {
/* 364 */         LOGGER.error("Error closing JAR file stream for {}", new Object[] { source, e });
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
/*     */   private void loadImplementationsInJar(Test test, String parent, String path, JarInputStream stream) {
/*     */     try {
/*     */       JarEntry entry;
/* 384 */       while ((entry = stream.getNextJarEntry()) != null) {
/* 385 */         String name = entry.getName();
/* 386 */         if (!entry.isDirectory() && name.startsWith(parent) && isTestApplicable(test, name)) {
/* 387 */           addIfMatching(test, name);
/*     */         }
/*     */       } 
/* 390 */     } catch (IOException ioe) {
/* 391 */       LOGGER.error("Could not search jar file '" + path + "' for classes matching criteria: " + test + " due to an IOException", ioe);
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
/*     */   protected void addIfMatching(Test test, String fqn) {
/*     */     try {
/* 405 */       ClassLoader loader = getClassLoader();
/* 406 */       if (test.doesMatchClass()) {
/* 407 */         String externalName = fqn.substring(0, fqn.indexOf('.')).replace('/', '.');
/* 408 */         if (LOGGER.isDebugEnabled()) {
/* 409 */           LOGGER.debug("Checking to see if class " + externalName + " matches criteria [" + test + "]");
/*     */         }
/*     */         
/* 412 */         Class<?> type = loader.loadClass(externalName);
/* 413 */         if (test.matches(type)) {
/* 414 */           this.classMatches.add(type);
/*     */         }
/*     */       } 
/* 417 */       if (test.doesMatchResource()) {
/* 418 */         URL url = loader.getResource(fqn);
/* 419 */         if (url == null) {
/* 420 */           url = loader.getResource(fqn.substring(1));
/*     */         }
/* 422 */         if (url != null && test.matches(url.toURI())) {
/* 423 */           this.resourceMatches.add(url.toURI());
/*     */         }
/*     */       } 
/* 426 */     } catch (Throwable t) {
/* 427 */       LOGGER.warn("Could not examine class '" + fqn + "' due to a " + t.getClass().getName() + " with message: " + t.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Test
/*     */   {
/*     */     boolean matches(Class<?> param1Class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean matches(URI param1URI);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean doesMatchClass();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean doesMatchResource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class ClassTest
/*     */     implements Test
/*     */   {
/*     */     public boolean matches(URI resource) {
/* 463 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean doesMatchClass() {
/* 468 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean doesMatchResource() {
/* 473 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class ResourceTest
/*     */     implements Test
/*     */   {
/*     */     public boolean matches(Class<?> cls) {
/* 483 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean doesMatchClass() {
/* 488 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean doesMatchResource() {
/* 493 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IsA
/*     */     extends ClassTest
/*     */   {
/*     */     private final Class<?> parent;
/*     */ 
/*     */ 
/*     */     
/*     */     public IsA(Class<?> parentType) {
/* 508 */       this.parent = parentType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(Class<?> type) {
/* 517 */       return (type != null && this.parent.isAssignableFrom(type));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 522 */       return "is assignable to " + this.parent.getSimpleName();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NameEndsWith
/*     */     extends ClassTest
/*     */   {
/*     */     private final String suffix;
/*     */ 
/*     */ 
/*     */     
/*     */     public NameEndsWith(String suffix) {
/* 536 */       this.suffix = suffix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(Class<?> type) {
/* 545 */       return (type != null && type.getName().endsWith(this.suffix));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 550 */       return "ends with the suffix " + this.suffix;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AnnotatedWith
/*     */     extends ClassTest
/*     */   {
/*     */     private final Class<? extends Annotation> annotation;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AnnotatedWith(Class<? extends Annotation> annotation) {
/* 566 */       this.annotation = annotation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(Class<?> type) {
/* 576 */       return (type != null && type.isAnnotationPresent(this.annotation));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 581 */       return "annotated with @" + this.annotation.getSimpleName();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NameIs
/*     */     extends ResourceTest
/*     */   {
/*     */     private final String name;
/*     */     
/*     */     public NameIs(String name) {
/* 591 */       this.name = "/" + name;
/*     */     }
/*     */     
/*     */     public boolean matches(URI resource) {
/* 595 */       return resource.getPath().endsWith(this.name);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 599 */       return "named " + this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\ResolverUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */