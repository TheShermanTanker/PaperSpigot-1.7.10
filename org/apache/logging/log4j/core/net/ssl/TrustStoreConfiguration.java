/*     */ package org.apache.logging.log4j.core.net.ssl;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ @Plugin(name = "trustStore", category = "Core", printObject = true)
/*     */ public class TrustStoreConfiguration
/*     */   extends StoreConfiguration
/*     */ {
/*     */   private KeyStore trustStore;
/*     */   private String trustStoreType;
/*     */   
/*     */   public TrustStoreConfiguration(String location, String password) {
/*  39 */     super(location, password);
/*  40 */     this.trustStoreType = "JKS";
/*  41 */     this.trustStore = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void load() throws StoreConfigurationException {
/*  46 */     KeyStore ts = null;
/*  47 */     InputStream in = null;
/*     */     
/*  49 */     LOGGER.debug("Loading truststore from file with params(location={})", new Object[] { getLocation() });
/*     */     try {
/*  51 */       if (getLocation() == null) {
/*  52 */         throw new IOException("The location is null");
/*     */       }
/*  54 */       ts = KeyStore.getInstance(this.trustStoreType);
/*  55 */       in = new FileInputStream(getLocation());
/*  56 */       ts.load(in, getPasswordAsCharArray());
/*     */     }
/*  58 */     catch (CertificateException e) {
/*  59 */       LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type {}", new Object[] { this.trustStoreType });
/*  60 */       throw new StoreConfigurationException(e);
/*  61 */     } catch (NoSuchAlgorithmException e) {
/*  62 */       LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found");
/*  63 */       throw new StoreConfigurationException(e);
/*  64 */     } catch (KeyStoreException e) {
/*  65 */       LOGGER.error(e);
/*  66 */       throw new StoreConfigurationException(e);
/*  67 */     } catch (FileNotFoundException e) {
/*  68 */       LOGGER.error("The keystore file({}) is not found", new Object[] { getLocation() });
/*  69 */       throw new StoreConfigurationException(e);
/*  70 */     } catch (IOException e) {
/*  71 */       LOGGER.error("Something is wrong with the format of the truststore or the given password: {}", new Object[] { e.getMessage() });
/*  72 */       throw new StoreConfigurationException(e);
/*     */     } finally {
/*     */       try {
/*  75 */         if (in != null) {
/*  76 */           in.close();
/*     */         }
/*     */       }
/*  79 */       catch (Exception e) {
/*  80 */         LOGGER.warn("Error closing {}", new Object[] { getLocation(), e });
/*     */       } 
/*     */     } 
/*  83 */     this.trustStore = ts;
/*  84 */     LOGGER.debug("Truststore successfully loaded with params(location={})", new Object[] { getLocation() });
/*     */   }
/*     */   
/*     */   public KeyStore getTrustStore() throws StoreConfigurationException {
/*  88 */     if (this.trustStore == null) {
/*  89 */       load();
/*     */     }
/*  91 */     return this.trustStore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static TrustStoreConfiguration createTrustStoreConfiguration(@PluginAttribute("location") String location, @PluginAttribute("password") String password) {
/* 103 */     return new TrustStoreConfiguration(location, password);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\TrustStoreConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */