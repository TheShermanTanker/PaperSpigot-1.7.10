/*     */ package org.bukkit.configuration.file;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.Configuration;
/*     */ import org.bukkit.configuration.ConfigurationOptions;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.MemoryConfigurationOptions;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.representer.Representer;
/*     */ 
/*     */ public class YamlConfiguration
/*     */   extends FileConfiguration
/*     */ {
/*     */   protected static final String COMMENT_PREFIX = "# ";
/*     */   protected static final String BLANK_CONFIG = "{}\n";
/*  28 */   private final DumperOptions yamlOptions = new DumperOptions();
/*  29 */   private final Representer yamlRepresenter = new YamlRepresenter();
/*  30 */   private final Yaml yaml = new Yaml((BaseConstructor)new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);
/*     */ 
/*     */   
/*     */   public String saveToString() {
/*  34 */     this.yamlOptions.setIndent(options().indent());
/*  35 */     this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
/*  36 */     this.yamlOptions.setAllowUnicode(SYSTEM_UTF);
/*  37 */     this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
/*     */     
/*  39 */     String header = buildHeader();
/*  40 */     String dump = this.yaml.dump(getValues(false));
/*     */     
/*  42 */     if (dump.equals("{}\n")) {
/*  43 */       dump = "";
/*     */     }
/*     */     
/*  46 */     return header + dump;
/*     */   }
/*     */   
/*     */   public void loadFromString(String contents) throws InvalidConfigurationException {
/*     */     Map<?, ?> input;
/*  51 */     Validate.notNull(contents, "Contents cannot be null");
/*     */ 
/*     */     
/*     */     try {
/*  55 */       input = (Map<?, ?>)this.yaml.load(contents);
/*  56 */     } catch (YAMLException e) {
/*  57 */       throw new InvalidConfigurationException(e);
/*  58 */     } catch (ClassCastException e) {
/*  59 */       throw new InvalidConfigurationException("Top level is not a Map.");
/*     */     } 
/*     */     
/*  62 */     String header = parseHeader(contents);
/*  63 */     if (header.length() > 0) {
/*  64 */       options().header(header);
/*     */     }
/*     */     
/*  67 */     if (input != null) {
/*  68 */       convertMapsToSections(input, (ConfigurationSection)this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
/*  73 */     for (Map.Entry<?, ?> entry : input.entrySet()) {
/*  74 */       String key = entry.getKey().toString();
/*  75 */       Object value = entry.getValue();
/*     */       
/*  77 */       if (value instanceof Map) {
/*  78 */         convertMapsToSections((Map<?, ?>)value, section.createSection(key)); continue;
/*     */       } 
/*  80 */       section.set(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String parseHeader(String input) {
/*  86 */     String[] lines = input.split("\r?\n", -1);
/*  87 */     StringBuilder result = new StringBuilder();
/*  88 */     boolean readingHeader = true;
/*  89 */     boolean foundHeader = false;
/*     */     
/*  91 */     for (int i = 0; i < lines.length && readingHeader; i++) {
/*  92 */       String line = lines[i];
/*     */       
/*  94 */       if (line.startsWith("# ")) {
/*  95 */         if (i > 0) {
/*  96 */           result.append("\n");
/*     */         }
/*     */         
/*  99 */         if (line.length() > "# ".length()) {
/* 100 */           result.append(line.substring("# ".length()));
/*     */         }
/*     */         
/* 103 */         foundHeader = true;
/* 104 */       } else if (foundHeader && line.length() == 0) {
/* 105 */         result.append("\n");
/* 106 */       } else if (foundHeader) {
/* 107 */         readingHeader = false;
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String buildHeader() {
/* 116 */     String header = options().header();
/*     */     
/* 118 */     if (options().copyHeader()) {
/* 119 */       Configuration def = getDefaults();
/*     */       
/* 121 */       if (def != null && def instanceof FileConfiguration) {
/* 122 */         FileConfiguration filedefaults = (FileConfiguration)def;
/* 123 */         String defaultsHeader = filedefaults.buildHeader();
/*     */         
/* 125 */         if (defaultsHeader != null && defaultsHeader.length() > 0) {
/* 126 */           return defaultsHeader;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     if (header == null) {
/* 132 */       return "";
/*     */     }
/*     */     
/* 135 */     StringBuilder builder = new StringBuilder();
/* 136 */     String[] lines = header.split("\r?\n", -1);
/* 137 */     boolean startedHeader = false;
/*     */     
/* 139 */     for (int i = lines.length - 1; i >= 0; i--) {
/* 140 */       builder.insert(0, "\n");
/*     */       
/* 142 */       if (startedHeader || lines[i].length() != 0) {
/* 143 */         builder.insert(0, lines[i]);
/* 144 */         builder.insert(0, "# ");
/* 145 */         startedHeader = true;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public YamlConfigurationOptions options() {
/* 154 */     if (this.options == null) {
/* 155 */       this.options = new YamlConfigurationOptions(this);
/*     */     }
/*     */     
/* 158 */     return (YamlConfigurationOptions)this.options;
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
/*     */   public static YamlConfiguration loadConfiguration(File file) {
/* 175 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 177 */     YamlConfiguration config = new YamlConfiguration();
/*     */ 
/*     */     
/* 180 */     try { config.load(file); }
/* 181 */     catch (FileNotFoundException ex) {  }
/* 182 */     catch (IOException ex)
/* 183 */     { Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex); }
/* 184 */     catch (InvalidConfigurationException ex)
/* 185 */     { Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, (Throwable)ex); }
/*     */ 
/*     */     
/* 188 */     return config;
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
/*     */   @Deprecated
/*     */   public static YamlConfiguration loadConfiguration(InputStream stream) {
/* 207 */     Validate.notNull(stream, "Stream cannot be null");
/*     */     
/* 209 */     YamlConfiguration config = new YamlConfiguration();
/*     */     
/*     */     try {
/* 212 */       config.load(stream);
/* 213 */     } catch (IOException ex) {
/* 214 */       Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", ex);
/* 215 */     } catch (InvalidConfigurationException ex) {
/* 216 */       Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", (Throwable)ex);
/*     */     } 
/*     */     
/* 219 */     return config;
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
/*     */   public static YamlConfiguration loadConfiguration(Reader reader) {
/* 235 */     Validate.notNull(reader, "Stream cannot be null");
/*     */     
/* 237 */     YamlConfiguration config = new YamlConfiguration();
/*     */     
/*     */     try {
/* 240 */       config.load(reader);
/* 241 */     } catch (IOException ex) {
/* 242 */       Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", ex);
/* 243 */     } catch (InvalidConfigurationException ex) {
/* 244 */       Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", (Throwable)ex);
/*     */     } 
/*     */     
/* 247 */     return config;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\file\YamlConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */