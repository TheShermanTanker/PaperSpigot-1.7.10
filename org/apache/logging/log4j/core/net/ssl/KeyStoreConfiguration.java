/*     */ package org.apache.logging.log4j.core.net.ssl;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateException;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*     */ @Plugin(name = "keyStore", category = "Core", printObject = true)
/*     */ public class KeyStoreConfiguration
/*     */   extends StoreConfiguration
/*     */ {
/*     */   private KeyStore keyStore;
/*     */   private String keyStoreType;
/*     */   
/*     */   public KeyStoreConfiguration(String location, String password) {
/*  42 */     super(location, password);
/*  43 */     this.keyStoreType = "JKS";
/*  44 */     this.keyStore = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void load() throws StoreConfigurationException {
/*  49 */     FileInputStream fin = null;
/*     */     
/*  51 */     LOGGER.debug("Loading keystore from file with params(location={})", new Object[] { getLocation() });
/*     */     try {
/*  53 */       if (getLocation() == null) {
/*  54 */         throw new IOException("The location is null");
/*     */       }
/*  56 */       fin = new FileInputStream(getLocation());
/*  57 */       KeyStore ks = KeyStore.getInstance(this.keyStoreType);
/*  58 */       ks.load(fin, getPasswordAsCharArray());
/*  59 */       this.keyStore = ks;
/*     */     }
/*  61 */     catch (CertificateException e) {
/*  62 */       LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type {}", new Object[] { this.keyStoreType });
/*  63 */       throw new StoreConfigurationException(e);
/*  64 */     } catch (NoSuchAlgorithmException e) {
/*  65 */       LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found");
/*  66 */       throw new StoreConfigurationException(e);
/*  67 */     } catch (KeyStoreException e) {
/*  68 */       LOGGER.error(e);
/*  69 */       throw new StoreConfigurationException(e);
/*  70 */     } catch (FileNotFoundException e) {
/*  71 */       LOGGER.error("The keystore file({}) is not found", new Object[] { getLocation() });
/*  72 */       throw new StoreConfigurationException(e);
/*  73 */     } catch (IOException e) {
/*  74 */       LOGGER.error("Something is wrong with the format of the keystore or the given password");
/*  75 */       throw new StoreConfigurationException(e);
/*     */     } finally {
/*     */       
/*     */       try {
/*  79 */         if (fin != null)
/*  80 */           fin.close(); 
/*  81 */       } catch (IOException e) {}
/*     */     } 
/*     */     
/*  84 */     LOGGER.debug("Keystore successfully loaded with params(location={})", new Object[] { getLocation() });
/*     */   }
/*     */   
/*     */   public KeyStore getKeyStore() throws StoreConfigurationException {
/*  88 */     if (this.keyStore == null) {
/*  89 */       load();
/*     */     }
/*  91 */     return this.keyStore;
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
/*     */   @PluginFactory
/*     */   public static KeyStoreConfiguration createKeyStoreConfiguration(@PluginAttribute("location") String location, @PluginAttribute("password") String password) {
/* 104 */     return new KeyStoreConfiguration(location, password);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\KeyStoreConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */