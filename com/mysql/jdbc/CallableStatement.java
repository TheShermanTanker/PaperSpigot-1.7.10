/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Clob;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.Date;
/*      */ import java.sql.ParameterMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CallableStatement
/*      */   extends PreparedStatement
/*      */   implements CallableStatement
/*      */ {
/*      */   protected static final Constructor JDBC_4_CSTMT_2_ARGS_CTOR;
/*      */   protected static final Constructor JDBC_4_CSTMT_4_ARGS_CTOR;
/*      */   private static final int NOT_OUTPUT_PARAMETER_INDICATOR = -2147483648;
/*      */   private static final String PARAMETER_NAMESPACE_PREFIX = "@com_mysql_jdbc_outparam_";
/*      */   
/*      */   static {
/*   64 */     if (Util.isJdbc4()) {
/*      */       try {
/*   66 */         JDBC_4_CSTMT_2_ARGS_CTOR = Class.forName("com.mysql.jdbc.JDBC4CallableStatement").getConstructor(new Class[] { MySQLConnection.class, CallableStatementParamInfo.class });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*   71 */         JDBC_4_CSTMT_4_ARGS_CTOR = Class.forName("com.mysql.jdbc.JDBC4CallableStatement").getConstructor(new Class[] { MySQLConnection.class, String.class, String.class, boolean.class });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*   77 */       catch (SecurityException e) {
/*   78 */         throw new RuntimeException(e);
/*   79 */       } catch (NoSuchMethodException e) {
/*   80 */         throw new RuntimeException(e);
/*   81 */       } catch (ClassNotFoundException e) {
/*   82 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*   85 */       JDBC_4_CSTMT_4_ARGS_CTOR = null;
/*   86 */       JDBC_4_CSTMT_2_ARGS_CTOR = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected class CallableStatementParam
/*      */   {
/*      */     int desiredJdbcType;
/*      */     
/*      */     int index;
/*      */     
/*      */     int inOutModifier;
/*      */     
/*      */     boolean isIn;
/*      */     
/*      */     boolean isOut;
/*      */     
/*      */     int jdbcType;
/*      */     
/*      */     short nullability;
/*      */     
/*      */     String paramName;
/*      */     
/*      */     int precision;
/*      */     
/*      */     int scale;
/*      */     
/*      */     String typeName;
/*      */     
/*      */     CallableStatementParam(String name, int idx, boolean in, boolean out, int jdbcType, String typeName, int precision, int scale, short nullability, int inOutModifier) {
/*  116 */       this.paramName = name;
/*  117 */       this.isIn = in;
/*  118 */       this.isOut = out;
/*  119 */       this.index = idx;
/*      */       
/*  121 */       this.jdbcType = jdbcType;
/*  122 */       this.typeName = typeName;
/*  123 */       this.precision = precision;
/*  124 */       this.scale = scale;
/*  125 */       this.nullability = nullability;
/*  126 */       this.inOutModifier = inOutModifier;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object clone() throws CloneNotSupportedException {
/*  135 */       return super.clone();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class CallableStatementParamInfo
/*      */   {
/*      */     String catalogInUse;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isFunctionCall;
/*      */ 
/*      */     
/*      */     String nativeSql;
/*      */ 
/*      */     
/*      */     int numParameters;
/*      */ 
/*      */     
/*      */     List parameterList;
/*      */ 
/*      */     
/*      */     Map parameterMap;
/*      */ 
/*      */     
/*      */     boolean isReadOnlySafeProcedure = false;
/*      */ 
/*      */     
/*      */     boolean isReadOnlySafeChecked = false;
/*      */ 
/*      */ 
/*      */     
/*      */     CallableStatementParamInfo(CallableStatementParamInfo fullParamInfo) {
/*  171 */       this.nativeSql = CallableStatement.this.originalSql;
/*  172 */       this.catalogInUse = CallableStatement.this.currentCatalog;
/*  173 */       this.isFunctionCall = fullParamInfo.isFunctionCall;
/*  174 */       int[] localParameterMap = CallableStatement.this.placeholderToParameterIndexMap;
/*  175 */       int parameterMapLength = localParameterMap.length;
/*      */       
/*  177 */       this.isReadOnlySafeProcedure = fullParamInfo.isReadOnlySafeProcedure;
/*  178 */       this.isReadOnlySafeChecked = fullParamInfo.isReadOnlySafeChecked;
/*  179 */       this.parameterList = new ArrayList(fullParamInfo.numParameters);
/*  180 */       this.parameterMap = new HashMap<Object, Object>(fullParamInfo.numParameters);
/*      */       
/*  182 */       if (this.isFunctionCall)
/*      */       {
/*  184 */         this.parameterList.add(fullParamInfo.parameterList.get(0));
/*      */       }
/*      */       
/*  187 */       int offset = this.isFunctionCall ? 1 : 0;
/*      */       
/*  189 */       for (int i = 0; i < parameterMapLength; i++) {
/*  190 */         if (localParameterMap[i] != 0) {
/*  191 */           CallableStatement.CallableStatementParam param = fullParamInfo.parameterList.get(localParameterMap[i] + offset);
/*      */           
/*  193 */           this.parameterList.add(param);
/*  194 */           this.parameterMap.put(param.paramName, param);
/*      */         } 
/*      */       } 
/*      */       
/*  198 */       this.numParameters = this.parameterList.size();
/*      */     }
/*      */ 
/*      */     
/*      */     CallableStatementParamInfo(ResultSet paramTypesRs) throws SQLException {
/*  203 */       boolean hadRows = paramTypesRs.last();
/*      */       
/*  205 */       this.nativeSql = CallableStatement.this.originalSql;
/*  206 */       this.catalogInUse = CallableStatement.this.currentCatalog;
/*  207 */       this.isFunctionCall = CallableStatement.this.callingStoredFunction;
/*      */       
/*  209 */       if (hadRows) {
/*  210 */         this.numParameters = paramTypesRs.getRow();
/*      */         
/*  212 */         this.parameterList = new ArrayList(this.numParameters);
/*  213 */         this.parameterMap = new HashMap<Object, Object>(this.numParameters);
/*      */         
/*  215 */         paramTypesRs.beforeFirst();
/*      */         
/*  217 */         addParametersFromDBMD(paramTypesRs);
/*      */       } else {
/*  219 */         this.numParameters = 0;
/*      */       } 
/*      */       
/*  222 */       if (this.isFunctionCall) {
/*  223 */         this.numParameters++;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void addParametersFromDBMD(ResultSet paramTypesRs) throws SQLException {
/*  229 */       int i = 0;
/*      */       
/*  231 */       while (paramTypesRs.next()) {
/*  232 */         String paramName = paramTypesRs.getString(4);
/*  233 */         int inOutModifier = paramTypesRs.getInt(5);
/*      */         
/*  235 */         boolean isOutParameter = false;
/*  236 */         boolean isInParameter = false;
/*      */         
/*  238 */         if (i == 0 && this.isFunctionCall) {
/*  239 */           isOutParameter = true;
/*  240 */           isInParameter = false;
/*  241 */         } else if (inOutModifier == 2) {
/*  242 */           isOutParameter = true;
/*  243 */           isInParameter = true;
/*  244 */         } else if (inOutModifier == 1) {
/*  245 */           isOutParameter = false;
/*  246 */           isInParameter = true;
/*  247 */         } else if (inOutModifier == 4) {
/*  248 */           isOutParameter = true;
/*  249 */           isInParameter = false;
/*      */         } 
/*      */         
/*  252 */         int jdbcType = paramTypesRs.getInt(6);
/*  253 */         String typeName = paramTypesRs.getString(7);
/*  254 */         int precision = paramTypesRs.getInt(8);
/*  255 */         int scale = paramTypesRs.getInt(10);
/*  256 */         short nullability = paramTypesRs.getShort(12);
/*      */         
/*  258 */         CallableStatement.CallableStatementParam paramInfoToAdd = new CallableStatement.CallableStatementParam(paramName, i++, isInParameter, isOutParameter, jdbcType, typeName, precision, scale, nullability, inOutModifier);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  263 */         this.parameterList.add(paramInfoToAdd);
/*  264 */         this.parameterMap.put(paramName, paramInfoToAdd);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void checkBounds(int paramIndex) throws SQLException {
/*  269 */       int localParamIndex = paramIndex - 1;
/*      */       
/*  271 */       if (paramIndex < 0 || localParamIndex >= this.numParameters) {
/*  272 */         throw SQLError.createSQLException(Messages.getString("CallableStatement.11") + paramIndex + Messages.getString("CallableStatement.12") + this.numParameters + Messages.getString("CallableStatement.13"), "S1009", CallableStatement.this.getExceptionInterceptor());
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object clone() throws CloneNotSupportedException {
/*  285 */       return super.clone();
/*      */     }
/*      */     
/*      */     CallableStatement.CallableStatementParam getParameter(int index) {
/*  289 */       return this.parameterList.get(index);
/*      */     }
/*      */     
/*      */     CallableStatement.CallableStatementParam getParameter(String name) {
/*  293 */       return (CallableStatement.CallableStatementParam)this.parameterMap.get(name);
/*      */     }
/*      */     
/*      */     public String getParameterClassName(int arg0) throws SQLException {
/*  297 */       String mysqlTypeName = getParameterTypeName(arg0);
/*      */       
/*  299 */       boolean isBinaryOrBlob = (StringUtils.indexOfIgnoreCase(mysqlTypeName, "BLOB") != -1 || StringUtils.indexOfIgnoreCase(mysqlTypeName, "BINARY") != -1);
/*      */ 
/*      */       
/*  302 */       boolean isUnsigned = (StringUtils.indexOfIgnoreCase(mysqlTypeName, "UNSIGNED") != -1);
/*      */       
/*  304 */       int mysqlTypeIfKnown = 0;
/*      */       
/*  306 */       if (StringUtils.startsWithIgnoreCase(mysqlTypeName, "MEDIUMINT")) {
/*  307 */         mysqlTypeIfKnown = 9;
/*      */       }
/*      */       
/*  310 */       return ResultSetMetaData.getClassNameForJavaType(getParameterType(arg0), isUnsigned, mysqlTypeIfKnown, isBinaryOrBlob, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getParameterCount() throws SQLException {
/*  315 */       if (this.parameterList == null) {
/*  316 */         return 0;
/*      */       }
/*      */       
/*  319 */       return this.parameterList.size();
/*      */     }
/*      */     
/*      */     public int getParameterMode(int arg0) throws SQLException {
/*  323 */       checkBounds(arg0);
/*      */       
/*  325 */       return (getParameter(arg0 - 1)).inOutModifier;
/*      */     }
/*      */     
/*      */     public int getParameterType(int arg0) throws SQLException {
/*  329 */       checkBounds(arg0);
/*      */       
/*  331 */       return (getParameter(arg0 - 1)).jdbcType;
/*      */     }
/*      */     
/*      */     public String getParameterTypeName(int arg0) throws SQLException {
/*  335 */       checkBounds(arg0);
/*      */       
/*  337 */       return (getParameter(arg0 - 1)).typeName;
/*      */     }
/*      */     
/*      */     public int getPrecision(int arg0) throws SQLException {
/*  341 */       checkBounds(arg0);
/*      */       
/*  343 */       return (getParameter(arg0 - 1)).precision;
/*      */     }
/*      */     
/*      */     public int getScale(int arg0) throws SQLException {
/*  347 */       checkBounds(arg0);
/*      */       
/*  349 */       return (getParameter(arg0 - 1)).scale;
/*      */     }
/*      */     
/*      */     public int isNullable(int arg0) throws SQLException {
/*  353 */       checkBounds(arg0);
/*      */       
/*  355 */       return (getParameter(arg0 - 1)).nullability;
/*      */     }
/*      */     
/*      */     public boolean isSigned(int arg0) throws SQLException {
/*  359 */       checkBounds(arg0);
/*      */       
/*  361 */       return false;
/*      */     }
/*      */     
/*      */     Iterator iterator() {
/*  365 */       return this.parameterList.iterator();
/*      */     }
/*      */     
/*      */     int numberOfParameters() {
/*  369 */       return this.numParameters;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class CallableStatementParamInfoJDBC3
/*      */     extends CallableStatementParamInfo
/*      */     implements ParameterMetaData
/*      */   {
/*      */     CallableStatementParamInfoJDBC3(ResultSet paramTypesRs) throws SQLException {
/*  384 */       super(paramTypesRs);
/*      */     }
/*      */     
/*      */     public CallableStatementParamInfoJDBC3(CallableStatement.CallableStatementParamInfo paramInfo) {
/*  388 */       super(paramInfo);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isWrapperFor(Class iface) throws SQLException {
/*  407 */       CallableStatement.this.checkClosed();
/*      */ 
/*      */ 
/*      */       
/*  411 */       return iface.isInstance(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object unwrap(Class iface) throws SQLException {
/*      */       try {
/*  432 */         return Util.cast(iface, this);
/*  433 */       } catch (ClassCastException cce) {
/*  434 */         throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", CallableStatement.this.getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String mangleParameterName(String origParameterName) {
/*  446 */     if (origParameterName == null) {
/*  447 */       return null;
/*      */     }
/*      */     
/*  450 */     int offset = 0;
/*      */     
/*  452 */     if (origParameterName.length() > 0 && origParameterName.charAt(0) == '@')
/*      */     {
/*  454 */       offset = 1;
/*      */     }
/*      */     
/*  457 */     StringBuffer paramNameBuf = new StringBuffer("@com_mysql_jdbc_outparam_".length() + origParameterName.length());
/*      */ 
/*      */     
/*  460 */     paramNameBuf.append("@com_mysql_jdbc_outparam_");
/*  461 */     paramNameBuf.append(origParameterName.substring(offset));
/*      */     
/*  463 */     return paramNameBuf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean callingStoredFunction = false;
/*      */ 
/*      */   
/*      */   private ResultSetInternalMethods functionReturnValueResults;
/*      */ 
/*      */   
/*      */   private boolean hasOutputParams = false;
/*      */ 
/*      */   
/*      */   private ResultSetInternalMethods outputParameterResults;
/*      */ 
/*      */   
/*      */   protected boolean outputParamWasNull = false;
/*      */ 
/*      */   
/*      */   private int[] parameterIndexToRsIndex;
/*      */ 
/*      */   
/*      */   protected CallableStatementParamInfo paramInfo;
/*      */ 
/*      */   
/*      */   private CallableStatementParam returnValueParam;
/*      */ 
/*      */   
/*      */   private int[] placeholderToParameterIndexMap;
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement(MySQLConnection conn, CallableStatementParamInfo paramInfo) throws SQLException {
/*  497 */     super(conn, paramInfo.nativeSql, paramInfo.catalogInUse);
/*      */     
/*  499 */     this.paramInfo = paramInfo;
/*  500 */     this.callingStoredFunction = this.paramInfo.isFunctionCall;
/*      */     
/*  502 */     if (this.callingStoredFunction) {
/*  503 */       this.parameterCount++;
/*      */     }
/*      */     
/*  506 */     this.retrieveGeneratedKeys = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static CallableStatement getInstance(MySQLConnection conn, String sql, String catalog, boolean isFunctionCall) throws SQLException {
/*  518 */     if (!Util.isJdbc4()) {
/*  519 */       return new CallableStatement(conn, sql, catalog, isFunctionCall);
/*      */     }
/*      */     
/*  522 */     return (CallableStatement)Util.handleNewInstance(JDBC_4_CSTMT_4_ARGS_CTOR, new Object[] { conn, sql, catalog, Boolean.valueOf(isFunctionCall) }, conn.getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static CallableStatement getInstance(MySQLConnection conn, CallableStatementParamInfo paramInfo) throws SQLException {
/*  536 */     if (!Util.isJdbc4()) {
/*  537 */       return new CallableStatement(conn, paramInfo);
/*      */     }
/*      */     
/*  540 */     return (CallableStatement)Util.handleNewInstance(JDBC_4_CSTMT_2_ARGS_CTOR, new Object[] { conn, paramInfo }, conn.getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateParameterMap() throws SQLException {
/*  548 */     if (this.paramInfo == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  556 */     int parameterCountFromMetaData = this.paramInfo.getParameterCount();
/*      */ 
/*      */ 
/*      */     
/*  560 */     if (this.callingStoredFunction) {
/*  561 */       parameterCountFromMetaData--;
/*      */     }
/*      */     
/*  564 */     if (this.paramInfo != null && this.parameterCount != parameterCountFromMetaData) {
/*      */       
/*  566 */       this.placeholderToParameterIndexMap = new int[this.parameterCount];
/*      */       
/*  568 */       int startPos = this.callingStoredFunction ? StringUtils.indexOfIgnoreCase(this.originalSql, "SELECT") : StringUtils.indexOfIgnoreCase(this.originalSql, "CALL");
/*      */ 
/*      */       
/*  571 */       if (startPos != -1) {
/*  572 */         int parenOpenPos = this.originalSql.indexOf('(', startPos + 4);
/*      */         
/*  574 */         if (parenOpenPos != -1) {
/*  575 */           int parenClosePos = StringUtils.indexOfIgnoreCaseRespectQuotes(parenOpenPos, this.originalSql, ")", '\'', true);
/*      */ 
/*      */           
/*  578 */           if (parenClosePos != -1) {
/*  579 */             List<String> parsedParameters = StringUtils.split(this.originalSql.substring(parenOpenPos + 1, parenClosePos), ",", "'\"", "'\"", true);
/*      */             
/*  581 */             int numParsedParameters = parsedParameters.size();
/*      */ 
/*      */ 
/*      */             
/*  585 */             if (numParsedParameters != this.parameterCount);
/*      */ 
/*      */ 
/*      */             
/*  589 */             int placeholderCount = 0;
/*      */             
/*  591 */             for (int i = 0; i < numParsedParameters; i++) {
/*  592 */               if (((String)parsedParameters.get(i)).equals("?")) {
/*  593 */                 this.placeholderToParameterIndexMap[placeholderCount++] = i;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement(MySQLConnection conn, String sql, String catalog, boolean isFunctionCall) throws SQLException {
/*  617 */     super(conn, sql, catalog);
/*      */     
/*  619 */     this.callingStoredFunction = isFunctionCall;
/*      */     
/*  621 */     if (!this.callingStoredFunction) {
/*  622 */       if (!StringUtils.startsWithIgnoreCaseAndWs(sql, "CALL")) {
/*      */         
/*  624 */         fakeParameterTypes(false);
/*      */       } else {
/*  626 */         determineParameterTypes();
/*      */       } 
/*      */       
/*  629 */       generateParameterMap();
/*      */     } else {
/*  631 */       determineParameterTypes();
/*  632 */       generateParameterMap();
/*      */       
/*  634 */       this.parameterCount++;
/*      */     } 
/*      */     
/*  637 */     this.retrieveGeneratedKeys = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBatch() throws SQLException {
/*  646 */     setOutParams();
/*      */     
/*  648 */     super.addBatch();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private CallableStatementParam checkIsOutputParam(int paramIndex) throws SQLException {
/*  654 */     if (this.callingStoredFunction) {
/*  655 */       if (paramIndex == 1) {
/*      */         
/*  657 */         if (this.returnValueParam == null) {
/*  658 */           this.returnValueParam = new CallableStatementParam("", 0, false, true, 12, "VARCHAR", 0, 0, (short)2, 5);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  664 */         return this.returnValueParam;
/*      */       } 
/*      */ 
/*      */       
/*  668 */       paramIndex--;
/*      */     } 
/*      */     
/*  671 */     checkParameterIndexBounds(paramIndex);
/*      */     
/*  673 */     int localParamIndex = paramIndex - 1;
/*      */     
/*  675 */     if (this.placeholderToParameterIndexMap != null) {
/*  676 */       localParamIndex = this.placeholderToParameterIndexMap[localParamIndex];
/*      */     }
/*      */     
/*  679 */     CallableStatementParam paramDescriptor = this.paramInfo.getParameter(localParamIndex);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  685 */     if (this.connection.getNoAccessToProcedureBodies()) {
/*  686 */       paramDescriptor.isOut = true;
/*  687 */       paramDescriptor.isIn = true;
/*  688 */       paramDescriptor.inOutModifier = 2;
/*  689 */     } else if (!paramDescriptor.isOut) {
/*  690 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.9") + paramIndex + Messages.getString("CallableStatement.10"), "S1009", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  696 */     this.hasOutputParams = true;
/*      */     
/*  698 */     return paramDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkParameterIndexBounds(int paramIndex) throws SQLException {
/*  709 */     this.paramInfo.checkBounds(paramIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkStreamability() throws SQLException {
/*  721 */     if (this.hasOutputParams && createStreamingResultSet()) {
/*  722 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.14"), "S1C00", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void clearParameters() throws SQLException {
/*  728 */     super.clearParameters();
/*      */     
/*      */     try {
/*  731 */       if (this.outputParameterResults != null) {
/*  732 */         this.outputParameterResults.close();
/*      */       }
/*      */     } finally {
/*  735 */       this.outputParameterResults = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fakeParameterTypes(boolean isReallyProcedure) throws SQLException {
/*  746 */     Field[] fields = new Field[13];
/*      */     
/*  748 */     fields[0] = new Field("", "PROCEDURE_CAT", 1, 0);
/*  749 */     fields[1] = new Field("", "PROCEDURE_SCHEM", 1, 0);
/*  750 */     fields[2] = new Field("", "PROCEDURE_NAME", 1, 0);
/*  751 */     fields[3] = new Field("", "COLUMN_NAME", 1, 0);
/*  752 */     fields[4] = new Field("", "COLUMN_TYPE", 1, 0);
/*  753 */     fields[5] = new Field("", "DATA_TYPE", 5, 0);
/*  754 */     fields[6] = new Field("", "TYPE_NAME", 1, 0);
/*  755 */     fields[7] = new Field("", "PRECISION", 4, 0);
/*  756 */     fields[8] = new Field("", "LENGTH", 4, 0);
/*  757 */     fields[9] = new Field("", "SCALE", 5, 0);
/*  758 */     fields[10] = new Field("", "RADIX", 5, 0);
/*  759 */     fields[11] = new Field("", "NULLABLE", 5, 0);
/*  760 */     fields[12] = new Field("", "REMARKS", 1, 0);
/*      */     
/*  762 */     String procName = isReallyProcedure ? extractProcedureName() : null;
/*      */     
/*  764 */     byte[] procNameAsBytes = null;
/*      */     
/*      */     try {
/*  767 */       procNameAsBytes = (procName == null) ? null : procName.getBytes("UTF-8");
/*  768 */     } catch (UnsupportedEncodingException ueEx) {
/*  769 */       procNameAsBytes = StringUtils.s2b(procName, this.connection);
/*      */     } 
/*      */     
/*  772 */     ArrayList<ByteArrayRow> resultRows = new ArrayList();
/*      */     
/*  774 */     for (int i = 0; i < this.parameterCount; i++) {
/*  775 */       byte[][] row = new byte[13][];
/*  776 */       row[0] = null;
/*  777 */       row[1] = null;
/*  778 */       row[2] = procNameAsBytes;
/*  779 */       row[3] = StringUtils.s2b(String.valueOf(i), this.connection);
/*      */       
/*  781 */       row[4] = StringUtils.s2b(String.valueOf(1), this.connection);
/*      */ 
/*      */ 
/*      */       
/*  785 */       row[5] = StringUtils.s2b(String.valueOf(12), this.connection);
/*      */       
/*  787 */       row[6] = StringUtils.s2b("VARCHAR", this.connection);
/*  788 */       row[7] = StringUtils.s2b(Integer.toString(65535), this.connection);
/*  789 */       row[8] = StringUtils.s2b(Integer.toString(65535), this.connection);
/*  790 */       row[9] = StringUtils.s2b(Integer.toString(0), this.connection);
/*  791 */       row[10] = StringUtils.s2b(Integer.toString(10), this.connection);
/*      */       
/*  793 */       row[11] = StringUtils.s2b(Integer.toString(2), this.connection);
/*      */ 
/*      */ 
/*      */       
/*  797 */       row[12] = null;
/*      */       
/*  799 */       resultRows.add(new ByteArrayRow(row, getExceptionInterceptor()));
/*      */     } 
/*      */     
/*  802 */     ResultSet paramTypesRs = DatabaseMetaData.buildResultSet(fields, resultRows, this.connection);
/*      */ 
/*      */     
/*  805 */     convertGetProcedureColumnsToInternalDescriptors(paramTypesRs);
/*      */   }
/*      */ 
/*      */   
/*      */   private void determineParameterTypes() throws SQLException {
/*  810 */     ResultSet paramTypesRs = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  815 */       String procName = extractProcedureName();
/*  816 */       String quotedId = "";
/*      */       try {
/*  818 */         quotedId = this.connection.supportsQuotedIdentifiers() ? this.connection.getMetaData().getIdentifierQuoteString() : "";
/*      */       }
/*  820 */       catch (SQLException sqlEx) {
/*      */ 
/*      */         
/*  823 */         AssertionFailedException.shouldNotHappen(sqlEx);
/*      */       } 
/*      */       
/*  826 */       List<String> parseList = StringUtils.splitDBdotName(procName, "", quotedId, this.connection.isNoBackslashEscapesSet());
/*      */       
/*  828 */       String tmpCatalog = "";
/*      */       
/*  830 */       if (parseList.size() == 2) {
/*  831 */         tmpCatalog = parseList.get(0);
/*  832 */         procName = parseList.get(1);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  837 */       DatabaseMetaData dbmd = this.connection.getMetaData();
/*      */       
/*  839 */       boolean useCatalog = false;
/*      */       
/*  841 */       if (tmpCatalog.length() <= 0) {
/*  842 */         useCatalog = true;
/*      */       }
/*      */       
/*  845 */       paramTypesRs = dbmd.getProcedureColumns((this.connection.versionMeetsMinimum(5, 0, 2) && useCatalog) ? this.currentCatalog : tmpCatalog, null, procName, "%");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  850 */       boolean hasResults = false;
/*      */       try {
/*  852 */         if (paramTypesRs.next()) {
/*  853 */           paramTypesRs.previous();
/*  854 */           hasResults = true;
/*      */         } 
/*  856 */       } catch (Exception e) {}
/*      */ 
/*      */       
/*  859 */       if (hasResults) {
/*  860 */         convertGetProcedureColumnsToInternalDescriptors(paramTypesRs);
/*      */       } else {
/*  862 */         fakeParameterTypes(true);
/*      */       } 
/*      */     } finally {
/*  865 */       SQLException sqlExRethrow = null;
/*      */       
/*  867 */       if (paramTypesRs != null) {
/*      */         try {
/*  869 */           paramTypesRs.close();
/*  870 */         } catch (SQLException sqlEx) {
/*  871 */           sqlExRethrow = sqlEx;
/*      */         } 
/*      */         
/*  874 */         paramTypesRs = null;
/*      */       } 
/*      */       
/*  877 */       if (sqlExRethrow != null) {
/*  878 */         throw sqlExRethrow;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void convertGetProcedureColumnsToInternalDescriptors(ResultSet paramTypesRs) throws SQLException {
/*  884 */     if (!this.connection.isRunningOnJDK13()) {
/*  885 */       this.paramInfo = new CallableStatementParamInfoJDBC3(paramTypesRs);
/*      */     } else {
/*      */       
/*  888 */       this.paramInfo = new CallableStatementParamInfo(paramTypesRs);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute() throws SQLException {
/*  898 */     boolean returnVal = false;
/*      */     
/*  900 */     checkClosed();
/*      */     
/*  902 */     checkStreamability();
/*      */     
/*  904 */     synchronized (this.connection.getMutex()) {
/*  905 */       setInOutParamsOnServer();
/*  906 */       setOutParams();
/*      */       
/*  908 */       returnVal = super.execute();
/*      */       
/*  910 */       if (this.callingStoredFunction) {
/*  911 */         this.functionReturnValueResults = this.results;
/*  912 */         this.functionReturnValueResults.next();
/*  913 */         this.results = null;
/*      */       } 
/*      */       
/*  916 */       retrieveOutParams();
/*      */     } 
/*      */     
/*  919 */     if (!this.callingStoredFunction) {
/*  920 */       return returnVal;
/*      */     }
/*      */ 
/*      */     
/*  924 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet executeQuery() throws SQLException {
/*  933 */     checkClosed();
/*      */     
/*  935 */     checkStreamability();
/*      */     
/*  937 */     ResultSet execResults = null;
/*      */     
/*  939 */     synchronized (this.connection.getMutex()) {
/*  940 */       setInOutParamsOnServer();
/*  941 */       setOutParams();
/*      */       
/*  943 */       execResults = super.executeQuery();
/*      */       
/*  945 */       retrieveOutParams();
/*      */     } 
/*      */     
/*  948 */     return execResults;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeUpdate() throws SQLException {
/*  957 */     int returnVal = -1;
/*      */     
/*  959 */     checkClosed();
/*      */     
/*  961 */     checkStreamability();
/*      */     
/*  963 */     if (this.callingStoredFunction) {
/*  964 */       execute();
/*      */       
/*  966 */       return -1;
/*      */     } 
/*      */     
/*  969 */     synchronized (this.connection.getMutex()) {
/*  970 */       setInOutParamsOnServer();
/*  971 */       setOutParams();
/*      */       
/*  973 */       returnVal = super.executeUpdate();
/*      */       
/*  975 */       retrieveOutParams();
/*      */     } 
/*      */     
/*  978 */     return returnVal;
/*      */   }
/*      */   
/*      */   private String extractProcedureName() throws SQLException {
/*  982 */     String sanitizedSql = StringUtils.stripComments(this.originalSql, "`\"'", "`\"'", true, false, true, true);
/*      */ 
/*      */ 
/*      */     
/*  986 */     int endCallIndex = StringUtils.indexOfIgnoreCase(sanitizedSql, "CALL ");
/*      */     
/*  988 */     int offset = 5;
/*      */     
/*  990 */     if (endCallIndex == -1) {
/*  991 */       endCallIndex = StringUtils.indexOfIgnoreCase(sanitizedSql, "SELECT ");
/*      */       
/*  993 */       offset = 7;
/*      */     } 
/*      */     
/*  996 */     if (endCallIndex != -1) {
/*  997 */       StringBuffer nameBuf = new StringBuffer();
/*      */       
/*  999 */       String trimmedStatement = sanitizedSql.substring(endCallIndex + offset).trim();
/*      */ 
/*      */       
/* 1002 */       int statementLength = trimmedStatement.length();
/*      */       
/* 1004 */       for (int i = 0; i < statementLength; i++) {
/* 1005 */         char c = trimmedStatement.charAt(i);
/*      */         
/* 1007 */         if (Character.isWhitespace(c) || c == '(' || c == '?') {
/*      */           break;
/*      */         }
/* 1010 */         nameBuf.append(c);
/*      */       } 
/*      */ 
/*      */       
/* 1014 */       return nameBuf.toString();
/*      */     } 
/*      */     
/* 1017 */     throw SQLError.createSQLException(Messages.getString("CallableStatement.1"), "S1000", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String fixParameterName(String paramNameIn) throws SQLException {
/* 1034 */     if ((paramNameIn == null || paramNameIn.length() == 0) && !hasParametersView()) {
/* 1035 */       throw SQLError.createSQLException((Messages.getString("CallableStatement.0") + paramNameIn == null) ? Messages.getString("CallableStatement.15") : Messages.getString("CallableStatement.16"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1041 */     if (paramNameIn == null && hasParametersView()) {
/* 1042 */       paramNameIn = "nullpn";
/*      */     }
/*      */     
/* 1045 */     if (this.connection.getNoAccessToProcedureBodies()) {
/* 1046 */       throw SQLError.createSQLException("No access to parameters by name when connection has been configured not to access procedure bodies", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 1050 */     return mangleParameterName(paramNameIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Array getArray(int i) throws SQLException {
/* 1065 */     ResultSetInternalMethods rs = getOutputParameters(i);
/*      */     
/* 1067 */     Array retValue = rs.getArray(mapOutputParameterIndexToRsIndex(i));
/*      */     
/* 1069 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1071 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Array getArray(String parameterName) throws SQLException {
/* 1079 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1082 */     Array retValue = rs.getArray(fixParameterName(parameterName));
/*      */     
/* 1084 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1086 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
/* 1094 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1096 */     BigDecimal retValue = rs.getBigDecimal(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1099 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1101 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
/* 1122 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1124 */     BigDecimal retValue = rs.getBigDecimal(mapOutputParameterIndexToRsIndex(parameterIndex), scale);
/*      */ 
/*      */     
/* 1127 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1129 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized BigDecimal getBigDecimal(String parameterName) throws SQLException {
/* 1137 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1140 */     BigDecimal retValue = rs.getBigDecimal(fixParameterName(parameterName));
/*      */     
/* 1142 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1144 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Blob getBlob(int parameterIndex) throws SQLException {
/* 1151 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1153 */     Blob retValue = rs.getBlob(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1156 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1158 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Blob getBlob(String parameterName) throws SQLException {
/* 1165 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1168 */     Blob retValue = rs.getBlob(fixParameterName(parameterName));
/*      */     
/* 1170 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1172 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean getBoolean(int parameterIndex) throws SQLException {
/* 1180 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1182 */     boolean retValue = rs.getBoolean(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1185 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1187 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean getBoolean(String parameterName) throws SQLException {
/* 1195 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1198 */     boolean retValue = rs.getBoolean(fixParameterName(parameterName));
/*      */     
/* 1200 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1202 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized byte getByte(int parameterIndex) throws SQLException {
/* 1209 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1211 */     byte retValue = rs.getByte(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1214 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1216 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized byte getByte(String parameterName) throws SQLException {
/* 1223 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1226 */     byte retValue = rs.getByte(fixParameterName(parameterName));
/*      */     
/* 1228 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1230 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized byte[] getBytes(int parameterIndex) throws SQLException {
/* 1237 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1239 */     byte[] retValue = rs.getBytes(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1242 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1244 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized byte[] getBytes(String parameterName) throws SQLException {
/* 1252 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1255 */     byte[] retValue = rs.getBytes(fixParameterName(parameterName));
/*      */     
/* 1257 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1259 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Clob getClob(int parameterIndex) throws SQLException {
/* 1266 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1268 */     Clob retValue = rs.getClob(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1271 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1273 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Clob getClob(String parameterName) throws SQLException {
/* 1280 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1283 */     Clob retValue = rs.getClob(fixParameterName(parameterName));
/*      */     
/* 1285 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1287 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Date getDate(int parameterIndex) throws SQLException {
/* 1294 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1296 */     Date retValue = rs.getDate(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1299 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1301 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Date getDate(int parameterIndex, Calendar cal) throws SQLException {
/* 1309 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1311 */     Date retValue = rs.getDate(mapOutputParameterIndexToRsIndex(parameterIndex), cal);
/*      */ 
/*      */     
/* 1314 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1316 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Date getDate(String parameterName) throws SQLException {
/* 1323 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1326 */     Date retValue = rs.getDate(fixParameterName(parameterName));
/*      */     
/* 1328 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1330 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Date getDate(String parameterName, Calendar cal) throws SQLException {
/* 1339 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1342 */     Date retValue = rs.getDate(fixParameterName(parameterName), cal);
/*      */     
/* 1344 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1346 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized double getDouble(int parameterIndex) throws SQLException {
/* 1354 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1356 */     double retValue = rs.getDouble(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1359 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1361 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized double getDouble(String parameterName) throws SQLException {
/* 1369 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1372 */     double retValue = rs.getDouble(fixParameterName(parameterName));
/*      */     
/* 1374 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1376 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized float getFloat(int parameterIndex) throws SQLException {
/* 1383 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1385 */     float retValue = rs.getFloat(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1388 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1390 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized float getFloat(String parameterName) throws SQLException {
/* 1398 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1401 */     float retValue = rs.getFloat(fixParameterName(parameterName));
/*      */     
/* 1403 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1405 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getInt(int parameterIndex) throws SQLException {
/* 1412 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1414 */     int retValue = rs.getInt(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1417 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1419 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getInt(String parameterName) throws SQLException {
/* 1426 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1429 */     int retValue = rs.getInt(fixParameterName(parameterName));
/*      */     
/* 1431 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1433 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized long getLong(int parameterIndex) throws SQLException {
/* 1440 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1442 */     long retValue = rs.getLong(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1445 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1447 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized long getLong(String parameterName) throws SQLException {
/* 1454 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1457 */     long retValue = rs.getLong(fixParameterName(parameterName));
/*      */     
/* 1459 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1461 */     return retValue;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getNamedParamIndex(String paramName, boolean forOut) throws SQLException {
/* 1466 */     if (this.connection.getNoAccessToProcedureBodies()) {
/* 1467 */       throw SQLError.createSQLException("No access to parameters by name when connection has been configured not to access procedure bodies", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1472 */     if (paramName == null || paramName.length() == 0) {
/* 1473 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.2"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 1477 */     if (this.paramInfo == null) {
/* 1478 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.3") + paramName + Messages.getString("CallableStatement.4"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1483 */     CallableStatementParam namedParamInfo = this.paramInfo.getParameter(paramName);
/*      */ 
/*      */     
/* 1486 */     if (forOut && !namedParamInfo.isOut) {
/* 1487 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.5") + paramName + Messages.getString("CallableStatement.6"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1494 */     if (this.placeholderToParameterIndexMap == null) {
/* 1495 */       return namedParamInfo.index + 1;
/*      */     }
/*      */     
/* 1498 */     for (int i = 0; i < this.placeholderToParameterIndexMap.length; i++) {
/* 1499 */       if (this.placeholderToParameterIndexMap[i] == namedParamInfo.index) {
/* 1500 */         return i + 1;
/*      */       }
/*      */     } 
/*      */     
/* 1504 */     throw SQLError.createSQLException("Can't find local placeholder mapping for parameter named \"" + paramName + "\".", "S1009", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getObject(int parameterIndex) throws SQLException {
/* 1513 */     CallableStatementParam paramDescriptor = checkIsOutputParam(parameterIndex);
/*      */     
/* 1515 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1517 */     Object retVal = rs.getObjectStoredProc(mapOutputParameterIndexToRsIndex(parameterIndex), paramDescriptor.desiredJdbcType);
/*      */ 
/*      */ 
/*      */     
/* 1521 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1523 */     return retVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
/* 1531 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1533 */     Object retVal = rs.getObject(mapOutputParameterIndexToRsIndex(parameterIndex), map);
/*      */ 
/*      */     
/* 1536 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1538 */     return retVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getObject(String parameterName) throws SQLException {
/* 1546 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1549 */     Object retValue = rs.getObject(fixParameterName(parameterName));
/*      */     
/* 1551 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1553 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
/* 1562 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1565 */     Object retValue = rs.getObject(fixParameterName(parameterName), map);
/*      */     
/* 1567 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1569 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResultSetInternalMethods getOutputParameters(int paramIndex) throws SQLException {
/* 1583 */     this.outputParamWasNull = false;
/*      */     
/* 1585 */     if (paramIndex == 1 && this.callingStoredFunction && this.returnValueParam != null)
/*      */     {
/* 1587 */       return this.functionReturnValueResults;
/*      */     }
/*      */     
/* 1590 */     if (this.outputParameterResults == null) {
/* 1591 */       if (this.paramInfo.numberOfParameters() == 0) {
/* 1592 */         throw SQLError.createSQLException(Messages.getString("CallableStatement.7"), "S1009", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */       
/* 1596 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.8"), "S1000", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */     
/* 1600 */     return this.outputParameterResults;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized ParameterMetaData getParameterMetaData() throws SQLException {
/* 1606 */     if (this.placeholderToParameterIndexMap == null) {
/* 1607 */       return (CallableStatementParamInfoJDBC3)this.paramInfo;
/*      */     }
/* 1609 */     return new CallableStatementParamInfoJDBC3(this.paramInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Ref getRef(int parameterIndex) throws SQLException {
/* 1617 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1619 */     Ref retValue = rs.getRef(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1622 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1624 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Ref getRef(String parameterName) throws SQLException {
/* 1631 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1634 */     Ref retValue = rs.getRef(fixParameterName(parameterName));
/*      */     
/* 1636 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1638 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized short getShort(int parameterIndex) throws SQLException {
/* 1645 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1647 */     short retValue = rs.getShort(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1650 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1652 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized short getShort(String parameterName) throws SQLException {
/* 1660 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1663 */     short retValue = rs.getShort(fixParameterName(parameterName));
/*      */     
/* 1665 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1667 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String getString(int parameterIndex) throws SQLException {
/* 1675 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1677 */     String retValue = rs.getString(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1680 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1682 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String getString(String parameterName) throws SQLException {
/* 1690 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1693 */     String retValue = rs.getString(fixParameterName(parameterName));
/*      */     
/* 1695 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1697 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Time getTime(int parameterIndex) throws SQLException {
/* 1704 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1706 */     Time retValue = rs.getTime(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1709 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1711 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Time getTime(int parameterIndex, Calendar cal) throws SQLException {
/* 1719 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1721 */     Time retValue = rs.getTime(mapOutputParameterIndexToRsIndex(parameterIndex), cal);
/*      */ 
/*      */     
/* 1724 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1726 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Time getTime(String parameterName) throws SQLException {
/* 1733 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1736 */     Time retValue = rs.getTime(fixParameterName(parameterName));
/*      */     
/* 1738 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1740 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Time getTime(String parameterName, Calendar cal) throws SQLException {
/* 1749 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1752 */     Time retValue = rs.getTime(fixParameterName(parameterName), cal);
/*      */     
/* 1754 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1756 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Timestamp getTimestamp(int parameterIndex) throws SQLException {
/* 1764 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1766 */     Timestamp retValue = rs.getTimestamp(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1769 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1771 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
/* 1779 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1781 */     Timestamp retValue = rs.getTimestamp(mapOutputParameterIndexToRsIndex(parameterIndex), cal);
/*      */ 
/*      */     
/* 1784 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1786 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Timestamp getTimestamp(String parameterName) throws SQLException {
/* 1794 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1797 */     Timestamp retValue = rs.getTimestamp(fixParameterName(parameterName));
/*      */     
/* 1799 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1801 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
/* 1810 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1813 */     Timestamp retValue = rs.getTimestamp(fixParameterName(parameterName), cal);
/*      */ 
/*      */     
/* 1816 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1818 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized URL getURL(int parameterIndex) throws SQLException {
/* 1825 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*      */     
/* 1827 */     URL retValue = rs.getURL(mapOutputParameterIndexToRsIndex(parameterIndex));
/*      */ 
/*      */     
/* 1830 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1832 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized URL getURL(String parameterName) throws SQLException {
/* 1839 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*      */ 
/*      */     
/* 1842 */     URL retValue = rs.getURL(fixParameterName(parameterName));
/*      */     
/* 1844 */     this.outputParamWasNull = rs.wasNull();
/*      */     
/* 1846 */     return retValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int mapOutputParameterIndexToRsIndex(int paramIndex) throws SQLException {
/* 1852 */     if (this.returnValueParam != null && paramIndex == 1) {
/* 1853 */       return 1;
/*      */     }
/*      */     
/* 1856 */     checkParameterIndexBounds(paramIndex);
/*      */     
/* 1858 */     int localParamIndex = paramIndex - 1;
/*      */     
/* 1860 */     if (this.placeholderToParameterIndexMap != null) {
/* 1861 */       localParamIndex = this.placeholderToParameterIndexMap[localParamIndex];
/*      */     }
/*      */     
/* 1864 */     int rsIndex = this.parameterIndexToRsIndex[localParamIndex];
/*      */     
/* 1866 */     if (rsIndex == Integer.MIN_VALUE) {
/* 1867 */       throw SQLError.createSQLException(Messages.getString("CallableStatement.21") + paramIndex + Messages.getString("CallableStatement.22"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1873 */     return rsIndex + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
/* 1881 */     CallableStatementParam paramDescriptor = checkIsOutputParam(parameterIndex);
/* 1882 */     paramDescriptor.desiredJdbcType = sqlType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
/* 1890 */     registerOutParameter(parameterIndex, sqlType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
/* 1899 */     checkIsOutputParam(parameterIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void registerOutParameter(String parameterName, int sqlType) throws SQLException {
/* 1908 */     registerOutParameter(getNamedParamIndex(parameterName, true), sqlType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
/* 1917 */     registerOutParameter(getNamedParamIndex(parameterName, true), sqlType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
/* 1926 */     registerOutParameter(getNamedParamIndex(parameterName, true), sqlType, typeName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void retrieveOutParams() throws SQLException {
/* 1937 */     int numParameters = this.paramInfo.numberOfParameters();
/*      */     
/* 1939 */     this.parameterIndexToRsIndex = new int[numParameters];
/*      */     
/* 1941 */     for (int i = 0; i < numParameters; i++) {
/* 1942 */       this.parameterIndexToRsIndex[i] = Integer.MIN_VALUE;
/*      */     }
/*      */     
/* 1945 */     int localParamIndex = 0;
/*      */     
/* 1947 */     if (numParameters > 0) {
/* 1948 */       StringBuffer outParameterQuery = new StringBuffer("SELECT ");
/*      */       
/* 1950 */       boolean firstParam = true;
/* 1951 */       boolean hadOutputParams = false;
/*      */       
/* 1953 */       Iterator<CallableStatementParam> paramIter = this.paramInfo.iterator();
/* 1954 */       while (paramIter.hasNext()) {
/* 1955 */         CallableStatementParam retrParamInfo = paramIter.next();
/*      */ 
/*      */         
/* 1958 */         if (retrParamInfo.isOut) {
/* 1959 */           hadOutputParams = true;
/*      */           
/* 1961 */           this.parameterIndexToRsIndex[retrParamInfo.index] = localParamIndex++;
/*      */           
/* 1963 */           if (retrParamInfo.paramName == null && hasParametersView()) {
/* 1964 */             retrParamInfo.paramName = "nullnp" + retrParamInfo.index;
/*      */           }
/*      */           
/* 1967 */           String outParameterName = mangleParameterName(retrParamInfo.paramName);
/*      */           
/* 1969 */           if (!firstParam) {
/* 1970 */             outParameterQuery.append(",");
/*      */           } else {
/* 1972 */             firstParam = false;
/*      */           } 
/*      */           
/* 1975 */           if (!outParameterName.startsWith("@")) {
/* 1976 */             outParameterQuery.append('@');
/*      */           }
/*      */           
/* 1979 */           outParameterQuery.append(outParameterName);
/*      */         } 
/*      */       } 
/*      */       
/* 1983 */       if (hadOutputParams) {
/*      */ 
/*      */         
/* 1986 */         Statement outParameterStmt = null;
/* 1987 */         ResultSet outParamRs = null;
/*      */         
/*      */         try {
/* 1990 */           outParameterStmt = this.connection.createStatement();
/* 1991 */           outParamRs = outParameterStmt.executeQuery(outParameterQuery.toString());
/*      */           
/* 1993 */           this.outputParameterResults = ((ResultSetInternalMethods)outParamRs).copy();
/*      */ 
/*      */           
/* 1996 */           if (!this.outputParameterResults.next()) {
/* 1997 */             this.outputParameterResults.close();
/* 1998 */             this.outputParameterResults = null;
/*      */           } 
/*      */         } finally {
/* 2001 */           if (outParameterStmt != null) {
/* 2002 */             outParameterStmt.close();
/*      */           }
/*      */         } 
/*      */       } else {
/* 2006 */         this.outputParameterResults = null;
/*      */       } 
/*      */     } else {
/* 2009 */       this.outputParameterResults = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
/* 2019 */     setAsciiStream(getNamedParamIndex(parameterName, false), x, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
/* 2028 */     setBigDecimal(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
/* 2037 */     setBinaryStream(getNamedParamIndex(parameterName, false), x, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBoolean(String parameterName, boolean x) throws SQLException {
/* 2044 */     setBoolean(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setByte(String parameterName, byte x) throws SQLException {
/* 2051 */     setByte(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBytes(String parameterName, byte[] x) throws SQLException {
/* 2058 */     setBytes(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
/* 2067 */     setCharacterStream(getNamedParamIndex(parameterName, false), reader, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDate(String parameterName, Date x) throws SQLException {
/* 2075 */     setDate(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
/* 2084 */     setDate(getNamedParamIndex(parameterName, false), x, cal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDouble(String parameterName, double x) throws SQLException {
/* 2091 */     setDouble(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFloat(String parameterName, float x) throws SQLException {
/* 2098 */     setFloat(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setInOutParamsOnServer() throws SQLException {
/* 2105 */     if (this.paramInfo.numParameters > 0) {
/* 2106 */       int parameterIndex = 0;
/*      */       
/* 2108 */       Iterator<CallableStatementParam> paramIter = this.paramInfo.iterator();
/* 2109 */       while (paramIter.hasNext()) {
/*      */         
/* 2111 */         CallableStatementParam inParamInfo = paramIter.next();
/*      */ 
/*      */ 
/*      */         
/* 2115 */         if (inParamInfo.isOut && inParamInfo.isIn) {
/* 2116 */           if (inParamInfo.paramName == null && hasParametersView()) {
/* 2117 */             inParamInfo.paramName = "nullnp" + inParamInfo.index;
/*      */           }
/*      */           
/* 2120 */           String inOutParameterName = mangleParameterName(inParamInfo.paramName);
/* 2121 */           StringBuffer queryBuf = new StringBuffer(4 + inOutParameterName.length() + 1 + 1);
/*      */           
/* 2123 */           queryBuf.append("SET ");
/* 2124 */           queryBuf.append(inOutParameterName);
/* 2125 */           queryBuf.append("=?");
/*      */           
/* 2127 */           PreparedStatement setPstmt = null;
/*      */           
/*      */           try {
/* 2130 */             setPstmt = (PreparedStatement)this.connection.clientPrepareStatement(queryBuf.toString());
/*      */ 
/*      */             
/* 2133 */             byte[] parameterAsBytes = getBytesRepresentation(inParamInfo.index);
/*      */ 
/*      */             
/* 2136 */             if (parameterAsBytes != null) {
/* 2137 */               if (parameterAsBytes.length > 8 && parameterAsBytes[0] == 95 && parameterAsBytes[1] == 98 && parameterAsBytes[2] == 105 && parameterAsBytes[3] == 110 && parameterAsBytes[4] == 97 && parameterAsBytes[5] == 114 && parameterAsBytes[6] == 121 && parameterAsBytes[7] == 39) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2146 */                 setPstmt.setBytesNoEscapeNoQuotes(1, parameterAsBytes);
/*      */               } else {
/*      */                 
/* 2149 */                 int sqlType = inParamInfo.desiredJdbcType;
/*      */                 
/* 2151 */                 switch (sqlType) {
/*      */                   case -7:
/*      */                   case -4:
/*      */                   case -3:
/*      */                   case -2:
/*      */                   case 2000:
/*      */                   case 2004:
/* 2158 */                     setPstmt.setBytes(1, parameterAsBytes);
/*      */                     break;
/*      */ 
/*      */                   
/*      */                   default:
/* 2163 */                     setPstmt.setBytesNoEscape(1, parameterAsBytes); break;
/*      */                 } 
/*      */               } 
/*      */             } else {
/* 2167 */               setPstmt.setNull(1, 0);
/*      */             } 
/*      */             
/* 2170 */             setPstmt.executeUpdate();
/*      */           } finally {
/* 2172 */             if (setPstmt != null) {
/* 2173 */               setPstmt.close();
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2178 */         parameterIndex++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInt(String parameterName, int x) throws SQLException {
/* 2187 */     setInt(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLong(String parameterName, long x) throws SQLException {
/* 2194 */     setLong(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNull(String parameterName, int sqlType) throws SQLException {
/* 2201 */     setNull(getNamedParamIndex(parameterName, false), sqlType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
/* 2210 */     setNull(getNamedParamIndex(parameterName, false), sqlType, typeName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(String parameterName, Object x) throws SQLException {
/* 2218 */     setObject(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
/* 2227 */     setObject(getNamedParamIndex(parameterName, false), x, targetSqlType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setOutParams() throws SQLException {
/* 2239 */     if (this.paramInfo.numParameters > 0) {
/* 2240 */       Iterator<CallableStatementParam> paramIter = this.paramInfo.iterator();
/* 2241 */       while (paramIter.hasNext()) {
/* 2242 */         CallableStatementParam outParamInfo = paramIter.next();
/*      */ 
/*      */         
/* 2245 */         if (!this.callingStoredFunction && outParamInfo.isOut) {
/*      */           
/* 2247 */           if (outParamInfo.paramName == null && hasParametersView()) {
/* 2248 */             outParamInfo.paramName = "nullnp" + outParamInfo.index;
/*      */           }
/*      */           
/* 2251 */           String outParameterName = mangleParameterName(outParamInfo.paramName);
/*      */           
/* 2253 */           int outParamIndex = 0;
/*      */           
/* 2255 */           if (this.placeholderToParameterIndexMap == null) {
/* 2256 */             outParamIndex = outParamInfo.index + 1;
/*      */           } else {
/*      */             
/* 2259 */             boolean found = false;
/*      */             
/* 2261 */             for (int i = 0; i < this.placeholderToParameterIndexMap.length; i++) {
/* 2262 */               if (this.placeholderToParameterIndexMap[i] == outParamInfo.index) {
/* 2263 */                 outParamIndex = i + 1;
/* 2264 */                 found = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 2269 */             if (!found) {
/* 2270 */               throw SQLError.createSQLException("boo!", "S1000", this.connection.getExceptionInterceptor());
/*      */             }
/*      */           } 
/*      */           
/* 2274 */           setBytesNoEscapeNoQuotes(outParamIndex, StringUtils.getBytes(outParameterName, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShort(String parameterName, short x) throws SQLException {
/* 2289 */     setShort(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(String parameterName, String x) throws SQLException {
/* 2297 */     setString(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTime(String parameterName, Time x) throws SQLException {
/* 2304 */     setTime(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
/* 2313 */     setTime(getNamedParamIndex(parameterName, false), x, cal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
/* 2322 */     setTimestamp(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
/* 2331 */     setTimestamp(getNamedParamIndex(parameterName, false), x, cal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURL(String parameterName, URL val) throws SQLException {
/* 2338 */     setURL(getNamedParamIndex(parameterName, false), val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean wasNull() throws SQLException {
/* 2345 */     return this.outputParamWasNull;
/*      */   }
/*      */   
/*      */   public int[] executeBatch() throws SQLException {
/* 2349 */     if (this.hasOutputParams) {
/* 2350 */       throw SQLError.createSQLException("Can't call executeBatch() on CallableStatement with OUTPUT parameters", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 2354 */     return super.executeBatch();
/*      */   }
/*      */   
/*      */   protected int getParameterIndexOffset() {
/* 2358 */     if (this.callingStoredFunction) {
/* 2359 */       return -1;
/*      */     }
/*      */     
/* 2362 */     return super.getParameterIndexOffset();
/*      */   }
/*      */   
/*      */   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
/* 2366 */     setAsciiStream(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
/* 2371 */     setAsciiStream(getNamedParamIndex(parameterName, false), x, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
/* 2376 */     setBinaryStream(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
/* 2381 */     setBinaryStream(getNamedParamIndex(parameterName, false), x, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlob(String parameterName, Blob x) throws SQLException {
/* 2386 */     setBlob(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
/* 2391 */     setBlob(getNamedParamIndex(parameterName, false), inputStream);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
/* 2396 */     setBlob(getNamedParamIndex(parameterName, false), inputStream, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
/* 2401 */     setCharacterStream(getNamedParamIndex(parameterName, false), reader);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
/* 2406 */     setCharacterStream(getNamedParamIndex(parameterName, false), reader, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setClob(String parameterName, Clob x) throws SQLException {
/* 2411 */     setClob(getNamedParamIndex(parameterName, false), x);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setClob(String parameterName, Reader reader) throws SQLException {
/* 2416 */     setClob(getNamedParamIndex(parameterName, false), reader);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
/* 2421 */     setClob(getNamedParamIndex(parameterName, false), reader, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
/* 2426 */     setNCharacterStream(getNamedParamIndex(parameterName, false), value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
/* 2431 */     setNCharacterStream(getNamedParamIndex(parameterName, false), value, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkReadOnlyProcedure() throws SQLException {
/* 2442 */     if (this.connection.getNoAccessToProcedureBodies()) {
/* 2443 */       return false;
/*      */     }
/*      */     
/* 2446 */     synchronized (this.paramInfo) {
/* 2447 */       if (this.paramInfo.isReadOnlySafeChecked) {
/* 2448 */         return this.paramInfo.isReadOnlySafeProcedure;
/*      */       }
/*      */       
/* 2451 */       ResultSet rs = null;
/* 2452 */       PreparedStatement ps = null;
/*      */       
/*      */       try {
/* 2455 */         String procName = extractProcedureName();
/*      */         
/* 2457 */         String catalog = this.currentCatalog;
/*      */         
/* 2459 */         if (procName.indexOf(".") != -1) {
/* 2460 */           catalog = procName.substring(0, procName.indexOf("."));
/*      */           
/* 2462 */           if (StringUtils.startsWithIgnoreCaseAndWs(catalog, "`") && catalog.trim().endsWith("`")) {
/* 2463 */             catalog = catalog.substring(1, catalog.length() - 1);
/*      */           }
/*      */           
/* 2466 */           procName = procName.substring(procName.indexOf(".") + 1);
/* 2467 */           procName = new String(StringUtils.stripEnclosure(procName.getBytes(), "`", "`"));
/*      */         } 
/*      */         
/* 2470 */         ps = this.connection.prepareStatement("SELECT SQL_DATA_ACCESS FROM  information_schema.routines  WHERE routine_schema = ?  AND routine_name = ?");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2475 */         ps.setMaxRows(0);
/* 2476 */         ps.setFetchSize(0);
/*      */         
/* 2478 */         ps.setString(1, catalog);
/* 2479 */         ps.setString(2, procName);
/* 2480 */         rs = ps.executeQuery();
/* 2481 */         if (rs.next()) {
/* 2482 */           String sqlDataAccess = rs.getString(1);
/* 2483 */           if ("READS SQL DATA".equalsIgnoreCase(sqlDataAccess) || "NO SQL".equalsIgnoreCase(sqlDataAccess)) {
/*      */             
/* 2485 */             synchronized (this.paramInfo) {
/* 2486 */               this.paramInfo.isReadOnlySafeChecked = true;
/* 2487 */               this.paramInfo.isReadOnlySafeProcedure = true;
/*      */             } 
/* 2489 */             return true;
/*      */           } 
/*      */         } 
/* 2492 */       } catch (SQLException e) {
/*      */       
/*      */       } finally {
/* 2495 */         if (rs != null) {
/* 2496 */           rs.close();
/*      */         }
/* 2498 */         if (ps != null) {
/* 2499 */           ps.close();
/*      */         }
/*      */       } 
/*      */       
/* 2503 */       this.paramInfo.isReadOnlySafeChecked = false;
/* 2504 */       this.paramInfo.isReadOnlySafeProcedure = false;
/*      */     } 
/* 2506 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean checkReadOnlySafeStatement() throws SQLException {
/* 2511 */     return (super.checkReadOnlySafeStatement() || checkReadOnlyProcedure());
/*      */   }
/*      */   
/*      */   private boolean hasParametersView() throws SQLException {
/*      */     try {
/* 2516 */       if (this.connection.versionMeetsMinimum(5, 5, 0)) {
/* 2517 */         DatabaseMetaData dbmd1 = new DatabaseMetaDataUsingInfoSchema(this.connection, this.connection.getCatalog());
/* 2518 */         return ((DatabaseMetaDataUsingInfoSchema)dbmd1).gethasParametersView();
/*      */       } 
/* 2520 */       return false;
/*      */     }
/* 2522 */     catch (SQLException e) {
/* 2523 */       return false;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\CallableStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */