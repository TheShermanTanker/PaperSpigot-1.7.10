/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*     */ import com.avaje.ebean.config.dbplatform.DbType;
/*     */ import com.avaje.ebean.config.dbplatform.DbTypeMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DdlGenContext
/*     */ {
/*  31 */   private final StringWriter stringWriter = new StringWriter();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DbTypeMap dbTypeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DbDdlSyntax ddlSyntax;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String newLine;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private final List<String> contentBuffer = new ArrayList<String>();
/*     */   
/*  53 */   private Set<String> intersectionTables = new HashSet<String>();
/*     */   
/*  55 */   private List<String> intersectionTablesCreateDdl = new ArrayList<String>();
/*  56 */   private List<String> intersectionTablesFkDdl = new ArrayList<String>();
/*     */ 
/*     */   
/*     */   private final DatabasePlatform dbPlatform;
/*     */ 
/*     */   
/*     */   private final NamingConvention namingConvention;
/*     */   
/*     */   private int fkCount;
/*     */   
/*     */   private int ixCount;
/*     */ 
/*     */   
/*     */   public DdlGenContext(DatabasePlatform dbPlatform, NamingConvention namingConvention) {
/*  70 */     this.dbPlatform = dbPlatform;
/*  71 */     this.dbTypeMap = dbPlatform.getDbTypeMap();
/*  72 */     this.ddlSyntax = dbPlatform.getDbDdlSyntax();
/*  73 */     this.newLine = this.ddlSyntax.getNewLine();
/*  74 */     this.namingConvention = namingConvention;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabasePlatform getDbPlatform() {
/*  81 */     return this.dbPlatform;
/*     */   }
/*     */   
/*     */   public boolean isProcessIntersectionTable(String tableName) {
/*  85 */     return this.intersectionTables.add(tableName);
/*     */   }
/*     */   
/*     */   public void addCreateIntersectionTable(String createTableDdl) {
/*  89 */     this.intersectionTablesCreateDdl.add(createTableDdl);
/*     */   }
/*     */   
/*     */   public void addIntersectionTableFk(String intTableFk) {
/*  93 */     this.intersectionTablesFkDdl.add(intTableFk);
/*     */   }
/*     */   
/*     */   public void addIntersectionCreateTables() {
/*  97 */     for (String intTableCreate : this.intersectionTablesCreateDdl) {
/*  98 */       write(this.newLine);
/*  99 */       write(intTableCreate);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addIntersectionFkeys() {
/* 104 */     write(this.newLine);
/* 105 */     write(this.newLine);
/* 106 */     for (String intTableFk : this.intersectionTablesFkDdl) {
/* 107 */       write(this.newLine);
/* 108 */       write(intTableFk);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContent() {
/* 116 */     return this.stringWriter.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbTypeMap getDbTypeMap() {
/* 124 */     return this.dbTypeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DbDdlSyntax getDdlSyntax() {
/* 131 */     return this.ddlSyntax;
/*     */   }
/*     */   
/*     */   public String getColumnDefn(BeanProperty p) {
/* 135 */     DbType dbType = getDbType(p);
/* 136 */     return p.renderDbType(dbType);
/*     */   }
/*     */ 
/*     */   
/*     */   private DbType getDbType(BeanProperty p) {
/* 141 */     ScalarType<?> scalarType = p.getScalarType();
/* 142 */     if (scalarType == null) {
/* 143 */       throw new RuntimeException("No scalarType for " + p.getFullBeanName());
/*     */     }
/*     */     
/* 146 */     if (p.isDbEncrypted()) {
/* 147 */       return this.dbTypeMap.get(p.getDbEncryptedType());
/*     */     }
/*     */     
/* 150 */     int jdbcType = scalarType.getJdbcType();
/* 151 */     if (p.isLob() && jdbcType == 12)
/*     */     {
/*     */       
/* 154 */       jdbcType = 2005;
/*     */     }
/* 156 */     return this.dbTypeMap.get(jdbcType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext write(String content, int minWidth) {
/* 163 */     content = pad(content, minWidth);
/*     */     
/* 165 */     this.contentBuffer.add(content);
/*     */     
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext write(String content) {
/* 175 */     return write(content, 0);
/*     */   }
/*     */   
/*     */   public DdlGenContext writeNewLine() {
/* 179 */     write(this.newLine);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext removeLast() {
/* 187 */     if (!this.contentBuffer.isEmpty()) {
/* 188 */       this.contentBuffer.remove(this.contentBuffer.size() - 1);
/*     */     } else {
/* 190 */       throw new RuntimeException("No lastContent to remove?");
/*     */     } 
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext flush() {
/* 199 */     if (!this.contentBuffer.isEmpty()) {
/* 200 */       for (String s : this.contentBuffer) {
/*     */         
/* 202 */         if (s != null) {
/* 203 */           this.stringWriter.write(s);
/*     */         }
/*     */       } 
/* 206 */       this.contentBuffer.clear();
/*     */     } 
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private String padding(int length) {
/* 213 */     StringBuffer sb = new StringBuffer(length);
/* 214 */     for (int i = 0; i < length; i++) {
/* 215 */       sb.append(" ");
/*     */     }
/* 217 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String pad(String content, int minWidth) {
/* 221 */     if (minWidth > 0 && content.length() < minWidth) {
/* 222 */       int padding = minWidth - content.length();
/* 223 */       return content + padding(padding);
/*     */     } 
/* 225 */     return content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingConvention getNamingConvention() {
/* 232 */     return this.namingConvention;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int incrementFkCount() {
/* 239 */     return ++this.fkCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int incrementIxCount() {
/* 246 */     return ++this.ixCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String removeQuotes(String dbColumn) {
/* 254 */     dbColumn = StringHelper.replaceString(dbColumn, this.dbPlatform.getOpenQuote(), "");
/* 255 */     dbColumn = StringHelper.replaceString(dbColumn, this.dbPlatform.getCloseQuote(), "");
/*     */     
/* 257 */     return dbColumn;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\DdlGenContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */