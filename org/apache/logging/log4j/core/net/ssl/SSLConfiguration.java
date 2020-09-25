/*     */ package org.apache.logging.log4j.core.net.ssl;
/*     */ 
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLServerSocketFactory;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "ssl", category = "Core", printObject = true)
/*     */ public class SSLConfiguration
/*     */ {
/*  32 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   private KeyStoreConfiguration keyStoreConfig;
/*     */   private TrustStoreConfiguration trustStoreConfig;
/*     */   private SSLContext sslContext;
/*     */   
/*     */   private SSLConfiguration(KeyStoreConfiguration keyStoreConfig, TrustStoreConfiguration trustStoreConfig) {
/*  38 */     this.keyStoreConfig = keyStoreConfig;
/*  39 */     this.trustStoreConfig = trustStoreConfig;
/*  40 */     this.sslContext = null;
/*     */   }
/*     */   
/*     */   public SSLSocketFactory getSSLSocketFactory() {
/*  44 */     if (this.sslContext == null) {
/*  45 */       this.sslContext = createSSLContext();
/*     */     }
/*  47 */     return this.sslContext.getSocketFactory();
/*     */   }
/*     */   
/*     */   public SSLServerSocketFactory getSSLServerSocketFactory() {
/*  51 */     if (this.sslContext == null) {
/*  52 */       this.sslContext = createSSLContext();
/*     */     }
/*  54 */     return this.sslContext.getServerSocketFactory();
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContext() {
/*  58 */     SSLContext context = null;
/*     */     
/*     */     try {
/*  61 */       context = createSSLContextBasedOnConfiguration();
/*  62 */       LOGGER.debug("Creating SSLContext with the given parameters");
/*     */     }
/*  64 */     catch (TrustStoreConfigurationException e) {
/*  65 */       context = createSSLContextWithTrustStoreFailure();
/*     */     }
/*  67 */     catch (KeyStoreConfigurationException e) {
/*  68 */       context = createSSLContextWithKeyStoreFailure();
/*     */     } 
/*  70 */     return context;
/*     */   }
/*     */ 
/*     */   
/*     */   private SSLContext createSSLContextWithTrustStoreFailure() {
/*     */     SSLContext sSLContext;
/*     */     try {
/*  77 */       sSLContext = createSSLContextWithDefaultTrustManagerFactory();
/*  78 */       LOGGER.debug("Creating SSLContext with default truststore");
/*     */     }
/*  80 */     catch (KeyStoreConfigurationException e) {
/*  81 */       sSLContext = createDefaultSSLContext();
/*  82 */       LOGGER.debug("Creating SSLContext with default configuration");
/*     */     } 
/*  84 */     return sSLContext;
/*     */   }
/*     */ 
/*     */   
/*     */   private SSLContext createSSLContextWithKeyStoreFailure() {
/*     */     SSLContext sSLContext;
/*     */     try {
/*  91 */       sSLContext = createSSLContextWithDefaultKeyManagerFactory();
/*  92 */       LOGGER.debug("Creating SSLContext with default keystore");
/*     */     }
/*  94 */     catch (TrustStoreConfigurationException e) {
/*  95 */       sSLContext = createDefaultSSLContext();
/*  96 */       LOGGER.debug("Creating SSLContext with default configuration");
/*     */     } 
/*  98 */     return sSLContext;
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextBasedOnConfiguration() throws KeyStoreConfigurationException, TrustStoreConfigurationException {
/* 102 */     return createSSLContext(false, false);
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextWithDefaultKeyManagerFactory() throws TrustStoreConfigurationException {
/*     */     try {
/* 107 */       return createSSLContext(true, false);
/* 108 */     } catch (KeyStoreConfigurationException dummy) {
/* 109 */       LOGGER.debug("Exception occured while using default keystore. This should be a BUG");
/* 110 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextWithDefaultTrustManagerFactory() throws KeyStoreConfigurationException {
/*     */     try {
/* 116 */       return createSSLContext(false, true);
/*     */     }
/* 118 */     catch (TrustStoreConfigurationException dummy) {
/* 119 */       LOGGER.debug("Exception occured while using default truststore. This should be a BUG");
/* 120 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private SSLContext createDefaultSSLContext() {
/*     */     try {
/* 126 */       return SSLContext.getDefault();
/* 127 */     } catch (NoSuchAlgorithmException e) {
/* 128 */       LOGGER.error("Failed to create an SSLContext with default configuration");
/* 129 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SSLContext createSSLContext(boolean loadDefaultKeyManagerFactory, boolean loadDefaultTrustManagerFactory) throws KeyStoreConfigurationException, TrustStoreConfigurationException {
/*     */     try {
/* 136 */       KeyManager[] kManagers = null;
/* 137 */       TrustManager[] tManagers = null;
/*     */       
/* 139 */       SSLContext sslContext = SSLContext.getInstance("SSL");
/* 140 */       if (!loadDefaultKeyManagerFactory) {
/* 141 */         KeyManagerFactory kmFactory = loadKeyManagerFactory();
/* 142 */         kManagers = kmFactory.getKeyManagers();
/*     */       } 
/* 144 */       if (!loadDefaultTrustManagerFactory) {
/* 145 */         TrustManagerFactory tmFactory = loadTrustManagerFactory();
/* 146 */         tManagers = tmFactory.getTrustManagers();
/*     */       } 
/*     */       
/* 149 */       sslContext.init(kManagers, tManagers, null);
/* 150 */       return sslContext;
/*     */     }
/* 152 */     catch (NoSuchAlgorithmException e) {
/* 153 */       LOGGER.error("No Provider supports a TrustManagerFactorySpi implementation for the specified protocol");
/* 154 */       throw new TrustStoreConfigurationException(e);
/*     */     }
/* 156 */     catch (KeyManagementException e) {
/* 157 */       LOGGER.error("Failed to initialize the SSLContext");
/* 158 */       throw new KeyStoreConfigurationException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private TrustManagerFactory loadTrustManagerFactory() throws TrustStoreConfigurationException {
/* 163 */     KeyStore trustStore = null;
/* 164 */     TrustManagerFactory tmFactory = null;
/*     */     
/* 166 */     if (this.trustStoreConfig == null) {
/* 167 */       throw new TrustStoreConfigurationException(new Exception("The trustStoreConfiguration is null"));
/*     */     }
/*     */     try {
/* 170 */       trustStore = this.trustStoreConfig.getTrustStore();
/* 171 */       tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/* 172 */       tmFactory.init(trustStore);
/*     */     }
/* 174 */     catch (NoSuchAlgorithmException e) {
/* 175 */       LOGGER.error("The specified algorithm is not available from the specified provider");
/* 176 */       throw new TrustStoreConfigurationException(e);
/* 177 */     } catch (KeyStoreException e) {
/* 178 */       LOGGER.error("Failed to initialize the TrustManagerFactory");
/* 179 */       throw new TrustStoreConfigurationException(e);
/* 180 */     } catch (StoreConfigurationException e) {
/* 181 */       throw new TrustStoreConfigurationException(e);
/*     */     } 
/*     */     
/* 184 */     return tmFactory;
/*     */   }
/*     */   
/*     */   private KeyManagerFactory loadKeyManagerFactory() throws KeyStoreConfigurationException {
/* 188 */     KeyStore keyStore = null;
/* 189 */     KeyManagerFactory kmFactory = null;
/*     */     
/* 191 */     if (this.keyStoreConfig == null) {
/* 192 */       throw new KeyStoreConfigurationException(new Exception("The keyStoreConfiguration is null"));
/*     */     }
/*     */     try {
/* 195 */       keyStore = this.keyStoreConfig.getKeyStore();
/* 196 */       kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/* 197 */       kmFactory.init(keyStore, this.keyStoreConfig.getPasswordAsCharArray());
/*     */     }
/* 199 */     catch (NoSuchAlgorithmException e) {
/* 200 */       LOGGER.error("The specified algorithm is not available from the specified provider");
/* 201 */       throw new KeyStoreConfigurationException(e);
/* 202 */     } catch (KeyStoreException e) {
/* 203 */       LOGGER.error("Failed to initialize the TrustManagerFactory");
/* 204 */       throw new KeyStoreConfigurationException(e);
/* 205 */     } catch (StoreConfigurationException e) {
/* 206 */       throw new KeyStoreConfigurationException(e);
/* 207 */     } catch (UnrecoverableKeyException e) {
/* 208 */       LOGGER.error("The key cannot be recovered (e.g. the given password is wrong)");
/* 209 */       throw new KeyStoreConfigurationException(e);
/*     */     } 
/*     */     
/* 212 */     return kmFactory;
/*     */   }
/*     */   
/*     */   public boolean equals(SSLConfiguration config) {
/* 216 */     if (config == null) {
/* 217 */       return false;
/*     */     }
/* 219 */     boolean keyStoreEquals = false;
/* 220 */     boolean trustStoreEquals = false;
/*     */     
/* 222 */     if (this.keyStoreConfig != null) {
/* 223 */       keyStoreEquals = this.keyStoreConfig.equals(config.keyStoreConfig);
/*     */     } else {
/* 225 */       keyStoreEquals = (this.keyStoreConfig == config.keyStoreConfig);
/*     */     } 
/* 227 */     if (this.trustStoreConfig != null) {
/* 228 */       trustStoreEquals = this.trustStoreConfig.equals(config.trustStoreConfig);
/*     */     } else {
/* 230 */       trustStoreEquals = (this.trustStoreConfig == config.trustStoreConfig);
/*     */     } 
/* 232 */     return (keyStoreEquals && trustStoreEquals);
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
/*     */   public static SSLConfiguration createSSLConfiguration(@PluginElement("keyStore") KeyStoreConfiguration keyStoreConfig, @PluginElement("trustStore") TrustStoreConfiguration trustStoreConfig) {
/* 245 */     return new SSLConfiguration(keyStoreConfig, trustStoreConfig);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\SSLConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */