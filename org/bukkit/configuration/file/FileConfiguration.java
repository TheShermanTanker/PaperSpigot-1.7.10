/*     */ package org.bukkit.configuration.file;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.configuration.Configuration;
/*     */ import org.bukkit.configuration.ConfigurationOptions;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.MemoryConfiguration;
/*     */ import org.bukkit.configuration.MemoryConfigurationOptions;
/*     */ import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FileConfiguration
/*     */   extends MemoryConfiguration
/*     */ {
/*     */   @Deprecated
/*     */   public static final boolean UTF8_OVERRIDE;
/*     */   @Deprecated
/*     */   public static final boolean UTF_BIG;
/*     */   @Deprecated
/*     */   public static final boolean SYSTEM_UTF;
/*     */   
/*     */   static {
/*  56 */     byte[] testBytes = Base64Coder.decode("ICEiIyQlJicoKSorLC0uLzAxMjM0NTY3ODk6Ozw9Pj9AQUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVpbXF1eX2BhYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5ent8fX4NCg==");
/*  57 */     String testString = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\r\n";
/*  58 */     Charset defaultCharset = Charset.defaultCharset();
/*  59 */     String resultString = new String(testBytes, defaultCharset);
/*  60 */     boolean trueUTF = defaultCharset.name().contains("UTF");
/*  61 */     UTF8_OVERRIDE = (!" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\r\n".equals(resultString) || defaultCharset.equals(Charset.forName("US-ASCII")));
/*  62 */     SYSTEM_UTF = (trueUTF || UTF8_OVERRIDE);
/*  63 */     UTF_BIG = (trueUTF && UTF8_OVERRIDE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileConfiguration() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileConfiguration(Configuration defaults) {
/*  80 */     super(defaults);
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
/*     */   public void save(File file) throws IOException {
/*  99 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 101 */     Files.createParentDirs(file);
/*     */     
/* 103 */     String data = saveToString();
/*     */     
/* 105 */     Writer writer = new OutputStreamWriter(new FileOutputStream(file), (UTF8_OVERRIDE && !UTF_BIG) ? Charsets.UTF_8 : Charset.defaultCharset());
/*     */     
/*     */     try {
/* 108 */       writer.write(data);
/*     */     } finally {
/* 110 */       writer.close();
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
/*     */   public void save(String file) throws IOException {
/* 130 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 132 */     save(new File(file));
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
/*     */   public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
/* 165 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 167 */     FileInputStream stream = new FileInputStream(file);
/*     */     
/* 169 */     load(new InputStreamReader(stream, (UTF8_OVERRIDE && !UTF_BIG) ? Charsets.UTF_8 : Charset.defaultCharset()));
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
/*     */   @Deprecated
/*     */   public void load(InputStream stream) throws IOException, InvalidConfigurationException {
/* 192 */     Validate.notNull(stream, "Stream cannot be null");
/*     */     
/* 194 */     load(new InputStreamReader(stream, UTF8_OVERRIDE ? Charsets.UTF_8 : Charset.defaultCharset()));
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
/*     */   public void load(Reader reader) throws IOException, InvalidConfigurationException {
/* 211 */     BufferedReader input = (reader instanceof BufferedReader) ? (BufferedReader)reader : new BufferedReader(reader);
/*     */     
/* 213 */     StringBuilder builder = new StringBuilder();
/*     */     
/*     */     try {
/*     */       String line;
/*     */       
/* 218 */       while ((line = input.readLine()) != null) {
/* 219 */         builder.append(line);
/* 220 */         builder.append('\n');
/*     */       } 
/*     */     } finally {
/* 223 */       input.close();
/*     */     } 
/*     */     
/* 226 */     loadFromString(builder.toString());
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
/*     */   public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
/* 248 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 250 */     load(new File(file));
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
/*     */   public FileConfigurationOptions options() {
/* 284 */     if (this.options == null) {
/* 285 */       this.options = new FileConfigurationOptions(this);
/*     */     }
/*     */     
/* 288 */     return (FileConfigurationOptions)this.options;
/*     */   }
/*     */   
/*     */   public abstract String saveToString();
/*     */   
/*     */   public abstract void loadFromString(String paramString) throws InvalidConfigurationException;
/*     */   
/*     */   protected abstract String buildHeader();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\file\FileConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */