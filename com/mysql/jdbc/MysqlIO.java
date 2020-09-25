/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import com.mysql.jdbc.util.ReadAheadInputStream;
/*      */ import com.mysql.jdbc.util.ResultSetUtil;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.Socket;
/*      */ import java.net.SocketException;
/*      */ import java.net.URL;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.zip.Deflater;
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
/*      */ public class MysqlIO
/*      */ {
/*      */   private static final int UTF8_CHARSET_INDEX = 33;
/*      */   private static final String CODE_PAGE_1252 = "Cp1252";
/*      */   protected static final int NULL_LENGTH = -1;
/*      */   protected static final int COMP_HEADER_LENGTH = 3;
/*      */   protected static final int MIN_COMPRESS_LEN = 50;
/*      */   protected static final int HEADER_LENGTH = 4;
/*      */   protected static final int AUTH_411_OVERHEAD = 33;
/*   80 */   private static int maxBufferSize = 65535;
/*      */   
/*      */   private static final int CLIENT_COMPRESS = 32;
/*      */   
/*      */   protected static final int CLIENT_CONNECT_WITH_DB = 8;
/*      */   
/*      */   private static final int CLIENT_FOUND_ROWS = 2;
/*      */   
/*      */   private static final int CLIENT_LOCAL_FILES = 128;
/*      */   
/*      */   private static final int CLIENT_LONG_FLAG = 4;
/*      */   
/*      */   private static final int CLIENT_LONG_PASSWORD = 1;
/*      */   
/*      */   private static final int CLIENT_PROTOCOL_41 = 512;
/*      */   
/*      */   private static final int CLIENT_INTERACTIVE = 1024;
/*      */   
/*      */   protected static final int CLIENT_SSL = 2048;
/*      */   
/*      */   private static final int CLIENT_TRANSACTIONS = 8192;
/*      */   protected static final int CLIENT_RESERVED = 16384;
/*      */   protected static final int CLIENT_SECURE_CONNECTION = 32768;
/*      */   private static final int CLIENT_MULTI_QUERIES = 65536;
/*      */   private static final int CLIENT_MULTI_RESULTS = 131072;
/*      */   private static final int SERVER_STATUS_IN_TRANS = 1;
/*      */   private static final int SERVER_STATUS_AUTOCOMMIT = 2;
/*      */   static final int SERVER_MORE_RESULTS_EXISTS = 8;
/*      */   private static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
/*      */   private static final int SERVER_QUERY_NO_INDEX_USED = 32;
/*      */   private static final int SERVER_QUERY_WAS_SLOW = 2048;
/*      */   private static final int SERVER_STATUS_CURSOR_EXISTS = 64;
/*      */   private static final String FALSE_SCRAMBLE = "xxxxxxxx";
/*      */   protected static final int MAX_QUERY_SIZE_TO_LOG = 1024;
/*      */   protected static final int MAX_QUERY_SIZE_TO_EXPLAIN = 1048576;
/*      */   protected static final int INITIAL_PACKET_SIZE = 1024;
/*  116 */   private static String jvmPlatformCharset = null;
/*      */   
/*      */   protected static final String ZERO_DATE_VALUE_MARKER = "0000-00-00";
/*      */   
/*      */   protected static final String ZERO_DATETIME_VALUE_MARKER = "0000-00-00 00:00:00";
/*      */   
/*      */   private static final int MAX_PACKET_DUMP_LENGTH = 1024;
/*      */ 
/*      */   
/*      */   static {
/*  126 */     OutputStreamWriter outWriter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  134 */       outWriter = new OutputStreamWriter(new ByteArrayOutputStream());
/*  135 */       jvmPlatformCharset = outWriter.getEncoding();
/*      */     } finally {
/*      */       try {
/*  138 */         if (outWriter != null) {
/*  139 */           outWriter.close();
/*      */         }
/*  141 */       } catch (IOException ioEx) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean packetSequenceReset = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int serverCharsetIndex;
/*      */ 
/*      */ 
/*      */   
/*  157 */   private Buffer reusablePacket = null;
/*  158 */   private Buffer sendPacket = null;
/*  159 */   private Buffer sharedSendPacket = null;
/*      */ 
/*      */   
/*  162 */   protected BufferedOutputStream mysqlOutput = null;
/*      */   protected MySQLConnection connection;
/*  164 */   private Deflater deflater = null;
/*  165 */   protected InputStream mysqlInput = null;
/*  166 */   private LinkedList packetDebugRingBuffer = null;
/*  167 */   private RowData streamingData = null;
/*      */ 
/*      */   
/*  170 */   protected Socket mysqlConnection = null;
/*  171 */   private SocketFactory socketFactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SoftReference loadFileBufRef;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SoftReference splitBufRef;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  187 */   protected String host = null;
/*      */   protected String seed;
/*  189 */   private String serverVersion = null;
/*  190 */   private String socketFactoryClassName = null;
/*  191 */   private byte[] packetHeaderBuf = new byte[4];
/*      */   
/*      */   private boolean colDecimalNeedsBump = false;
/*      */   
/*      */   private boolean hadWarnings = false;
/*      */   
/*      */   private boolean has41NewNewProt = false;
/*      */   
/*      */   private boolean hasLongColumnInfo = false;
/*      */   
/*      */   private boolean isInteractiveClient = false;
/*      */   
/*      */   private boolean logSlowQueries = false;
/*      */   
/*      */   private boolean platformDbCharsetMatches = true;
/*      */   
/*      */   private boolean profileSql = false;
/*      */   
/*      */   private boolean queryBadIndexUsed = false;
/*      */   private boolean queryNoIndexUsed = false;
/*      */   private boolean serverQueryWasSlow = false;
/*      */   private boolean use41Extensions = false;
/*      */   private boolean useCompression = false;
/*      */   private boolean useNewLargePackets = false;
/*      */   private boolean useNewUpdateCounts = false;
/*  216 */   private byte packetSequence = 0;
/*  217 */   private byte readPacketSequence = -1;
/*      */   private boolean checkPacketSequence = false;
/*  219 */   private byte protocolVersion = 0;
/*  220 */   private int maxAllowedPacket = 1048576;
/*  221 */   protected int maxThreeBytes = 16581375;
/*  222 */   protected int port = 3306;
/*      */   protected int serverCapabilities;
/*  224 */   private int serverMajorVersion = 0;
/*  225 */   private int serverMinorVersion = 0;
/*  226 */   private int oldServerStatus = 0;
/*  227 */   private int serverStatus = 0;
/*  228 */   private int serverSubMinorVersion = 0;
/*  229 */   private int warningCount = 0;
/*  230 */   protected long clientParam = 0L;
/*  231 */   protected long lastPacketSentTimeMs = 0L;
/*  232 */   protected long lastPacketReceivedTimeMs = 0L;
/*      */   private boolean traceProtocol = false;
/*      */   private boolean enablePacketDebug = false;
/*      */   private Calendar sessionCalendar;
/*      */   private boolean useConnectWithDb;
/*      */   private boolean needToGrabQueryFromPacket;
/*      */   private boolean autoGenerateTestcaseScript;
/*      */   private long threadId;
/*      */   private boolean useNanosForElapsedTime;
/*      */   private long slowQueryThreshold;
/*      */   private String queryTimingUnits;
/*      */   private boolean useDirectRowUnpack = true;
/*      */   private int useBufferRowSizeThreshold;
/*  245 */   private int commandCount = 0;
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
/*      */   private List statementInterceptors;
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
/*      */   private ExceptionInterceptor exceptionInterceptor;
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
/*      */   private int statementExecutionDepth;
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
/*      */   private boolean useAutoSlowLog;
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
/*      */   public boolean hasLongColumnInfo() {
/*  353 */     return this.hasLongColumnInfo;
/*      */   }
/*      */   
/*      */   protected boolean isDataAvailable() throws SQLException {
/*      */     try {
/*  358 */       return (this.mysqlInput.available() > 0);
/*  359 */     } catch (IOException ioEx) {
/*  360 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long getLastPacketSentTimeMs() {
/*  371 */     return this.lastPacketSentTimeMs;
/*      */   }
/*      */   
/*      */   protected long getLastPacketReceivedTimeMs() {
/*  375 */     return this.lastPacketReceivedTimeMs;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResultSetImpl getResultSet(StatementImpl callingStatement, long columnCount, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, boolean isBinaryEncoded, Field[] metadataFromCache) throws SQLException {
/*  405 */     Field[] fields = null;
/*      */ 
/*      */ 
/*      */     
/*  409 */     if (metadataFromCache == null) {
/*  410 */       fields = new Field[(int)columnCount];
/*      */       
/*  412 */       for (int i = 0; i < columnCount; i++) {
/*  413 */         Buffer fieldPacket = null;
/*      */         
/*  415 */         fieldPacket = readPacket();
/*  416 */         fields[i] = unpackField(fieldPacket, false);
/*      */       } 
/*      */     } else {
/*  419 */       for (int i = 0; i < columnCount; i++) {
/*  420 */         skipPacket();
/*      */       }
/*      */     } 
/*      */     
/*  424 */     Buffer packet = reuseAndReadPacket(this.reusablePacket);
/*      */     
/*  426 */     readServerStatusForResultSets(packet);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  432 */     if (this.connection.versionMeetsMinimum(5, 0, 2) && this.connection.getUseCursorFetch() && isBinaryEncoded && callingStatement != null && callingStatement.getFetchSize() != 0 && callingStatement.getResultSetType() == 1003) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  438 */       ServerPreparedStatement prepStmt = (ServerPreparedStatement)callingStatement;
/*      */       
/*  440 */       boolean usingCursor = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  448 */       if (this.connection.versionMeetsMinimum(5, 0, 5)) {
/*  449 */         usingCursor = ((this.serverStatus & 0x40) != 0);
/*      */       }
/*      */ 
/*      */       
/*  453 */       if (usingCursor) {
/*  454 */         RowData rows = new RowDataCursor(this, prepStmt, fields);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  459 */         ResultSetImpl resultSetImpl = buildResultSetWithRows(callingStatement, catalog, fields, rows, resultSetType, resultSetConcurrency, isBinaryEncoded);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  465 */         if (usingCursor) {
/*  466 */           resultSetImpl.setFetchSize(callingStatement.getFetchSize());
/*      */         }
/*      */         
/*  469 */         return resultSetImpl;
/*      */       } 
/*      */     } 
/*      */     
/*  473 */     RowData rowData = null;
/*      */     
/*  475 */     if (!streamResults) {
/*  476 */       rowData = readSingleRowSet(columnCount, maxRows, resultSetConcurrency, isBinaryEncoded, (metadataFromCache == null) ? fields : metadataFromCache);
/*      */     }
/*      */     else {
/*      */       
/*  480 */       rowData = new RowDataDynamic(this, (int)columnCount, (metadataFromCache == null) ? fields : metadataFromCache, isBinaryEncoded);
/*      */ 
/*      */       
/*  483 */       this.streamingData = rowData;
/*      */     } 
/*      */     
/*  486 */     ResultSetImpl rs = buildResultSetWithRows(callingStatement, catalog, (metadataFromCache == null) ? fields : metadataFromCache, rowData, resultSetType, resultSetConcurrency, isBinaryEncoded);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  492 */     return rs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void forceClose() {
/*      */     try {
/*      */       try {
/*  501 */         if (this.mysqlInput != null) {
/*  502 */           this.mysqlInput.close();
/*      */         }
/*      */       } finally {
/*  505 */         if (!this.mysqlConnection.isClosed() && !this.mysqlConnection.isInputShutdown()) {
/*  506 */           this.mysqlConnection.shutdownInput();
/*      */         }
/*      */       } 
/*  509 */     } catch (IOException ioEx) {
/*      */ 
/*      */       
/*  512 */       this.mysqlInput = null;
/*      */     } 
/*      */     
/*      */     try {
/*      */       try {
/*  517 */         if (this.mysqlOutput != null) {
/*  518 */           this.mysqlOutput.close();
/*      */         }
/*      */       } finally {
/*  521 */         if (!this.mysqlConnection.isClosed() && !this.mysqlConnection.isOutputShutdown()) {
/*  522 */           this.mysqlConnection.shutdownOutput();
/*      */         }
/*      */       } 
/*  525 */     } catch (IOException ioEx) {
/*      */ 
/*      */       
/*  528 */       this.mysqlOutput = null;
/*      */     } 
/*      */     
/*      */     try {
/*  532 */       if (this.mysqlConnection != null) {
/*  533 */         this.mysqlConnection.close();
/*      */       }
/*  535 */     } catch (IOException ioEx) {
/*      */ 
/*      */       
/*  538 */       this.mysqlConnection = null;
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
/*      */   protected final void skipPacket() throws SQLException {
/*      */     try {
/*  551 */       int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */       
/*  554 */       if (lengthRead < 4) {
/*  555 */         forceClose();
/*  556 */         throw new IOException(Messages.getString("MysqlIO.1"));
/*      */       } 
/*      */       
/*  559 */       int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */ 
/*      */ 
/*      */       
/*  563 */       if (this.traceProtocol) {
/*  564 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/*  566 */         traceMessageBuf.append(Messages.getString("MysqlIO.2"));
/*  567 */         traceMessageBuf.append(packetLength);
/*  568 */         traceMessageBuf.append(Messages.getString("MysqlIO.3"));
/*  569 */         traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
/*      */ 
/*      */         
/*  572 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/*  575 */       byte multiPacketSeq = this.packetHeaderBuf[3];
/*      */       
/*  577 */       if (!this.packetSequenceReset) {
/*  578 */         if (this.enablePacketDebug && this.checkPacketSequence) {
/*  579 */           checkPacketSequencing(multiPacketSeq);
/*      */         }
/*      */       } else {
/*  582 */         this.packetSequenceReset = false;
/*      */       } 
/*      */       
/*  585 */       this.readPacketSequence = multiPacketSeq;
/*      */       
/*  587 */       skipFully(this.mysqlInput, packetLength);
/*  588 */     } catch (IOException ioEx) {
/*  589 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     }
/*  591 */     catch (OutOfMemoryError oom) {
/*      */       try {
/*  593 */         this.connection.realClose(false, false, true, oom);
/*      */       } finally {
/*  595 */         throw oom;
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
/*      */   protected final Buffer readPacket() throws SQLException {
/*      */     try {
/*  611 */       int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */       
/*  614 */       if (lengthRead < 4) {
/*  615 */         forceClose();
/*  616 */         throw new IOException(Messages.getString("MysqlIO.1"));
/*      */       } 
/*      */       
/*  619 */       int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */ 
/*      */ 
/*      */       
/*  623 */       if (packetLength > this.maxAllowedPacket) {
/*  624 */         throw new PacketTooBigException(packetLength, this.maxAllowedPacket);
/*      */       }
/*      */       
/*  627 */       if (this.traceProtocol) {
/*  628 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/*  630 */         traceMessageBuf.append(Messages.getString("MysqlIO.2"));
/*  631 */         traceMessageBuf.append(packetLength);
/*  632 */         traceMessageBuf.append(Messages.getString("MysqlIO.3"));
/*  633 */         traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
/*      */ 
/*      */         
/*  636 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/*  639 */       byte multiPacketSeq = this.packetHeaderBuf[3];
/*      */       
/*  641 */       if (!this.packetSequenceReset) {
/*  642 */         if (this.enablePacketDebug && this.checkPacketSequence) {
/*  643 */           checkPacketSequencing(multiPacketSeq);
/*      */         }
/*      */       } else {
/*  646 */         this.packetSequenceReset = false;
/*      */       } 
/*      */       
/*  649 */       this.readPacketSequence = multiPacketSeq;
/*      */ 
/*      */       
/*  652 */       byte[] buffer = new byte[packetLength + 1];
/*  653 */       int numBytesRead = readFully(this.mysqlInput, buffer, 0, packetLength);
/*      */ 
/*      */       
/*  656 */       if (numBytesRead != packetLength) {
/*  657 */         throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);
/*      */       }
/*      */ 
/*      */       
/*  661 */       buffer[packetLength] = 0;
/*      */       
/*  663 */       Buffer packet = new Buffer(buffer);
/*  664 */       packet.setBufLength(packetLength + 1);
/*      */       
/*  666 */       if (this.traceProtocol) {
/*  667 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/*  669 */         traceMessageBuf.append(Messages.getString("MysqlIO.4"));
/*  670 */         traceMessageBuf.append(getPacketDumpToLog(packet, packetLength));
/*      */ 
/*      */         
/*  673 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/*  676 */       if (this.enablePacketDebug) {
/*  677 */         enqueuePacketForDebugging(false, false, 0, this.packetHeaderBuf, packet);
/*      */       }
/*      */ 
/*      */       
/*  681 */       if (this.connection.getMaintainTimeStats()) {
/*  682 */         this.lastPacketReceivedTimeMs = System.currentTimeMillis();
/*      */       }
/*      */       
/*  685 */       return packet;
/*  686 */     } catch (IOException ioEx) {
/*  687 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     }
/*  689 */     catch (OutOfMemoryError oom) {
/*      */       try {
/*  691 */         this.connection.realClose(false, false, true, oom);
/*      */       } finally {
/*  693 */         throw oom;
/*      */       } 
/*      */       while (true);
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
/*      */   protected final Field unpackField(Buffer packet, boolean extractDefaultValues) throws SQLException {
/*  711 */     if (this.use41Extensions) {
/*      */ 
/*      */       
/*  714 */       if (this.has41NewNewProt) {
/*      */         
/*  716 */         int catalogNameStart = packet.getPosition() + 1;
/*  717 */         int catalogNameLength = packet.fastSkipLenString();
/*  718 */         catalogNameStart = adjustStartForFieldLength(catalogNameStart, catalogNameLength);
/*      */       } 
/*      */       
/*  721 */       int databaseNameStart = packet.getPosition() + 1;
/*  722 */       int databaseNameLength = packet.fastSkipLenString();
/*  723 */       databaseNameStart = adjustStartForFieldLength(databaseNameStart, databaseNameLength);
/*      */       
/*  725 */       int i = packet.getPosition() + 1;
/*  726 */       int j = packet.fastSkipLenString();
/*  727 */       i = adjustStartForFieldLength(i, j);
/*      */ 
/*      */       
/*  730 */       int originalTableNameStart = packet.getPosition() + 1;
/*  731 */       int originalTableNameLength = packet.fastSkipLenString();
/*  732 */       originalTableNameStart = adjustStartForFieldLength(originalTableNameStart, originalTableNameLength);
/*      */ 
/*      */       
/*  735 */       int k = packet.getPosition() + 1;
/*  736 */       int m = packet.fastSkipLenString();
/*      */       
/*  738 */       k = adjustStartForFieldLength(k, m);
/*      */ 
/*      */       
/*  741 */       int originalColumnNameStart = packet.getPosition() + 1;
/*  742 */       int originalColumnNameLength = packet.fastSkipLenString();
/*  743 */       originalColumnNameStart = adjustStartForFieldLength(originalColumnNameStart, originalColumnNameLength);
/*      */       
/*  745 */       packet.readByte();
/*      */       
/*  747 */       short charSetNumber = (short)packet.readInt();
/*      */       
/*  749 */       long l = 0L;
/*      */       
/*  751 */       if (this.has41NewNewProt) {
/*  752 */         l = packet.readLong();
/*      */       } else {
/*  754 */         l = packet.readLongInt();
/*      */       } 
/*      */       
/*  757 */       int n = packet.readByte() & 0xFF;
/*      */       
/*  759 */       short s1 = 0;
/*      */       
/*  761 */       if (this.hasLongColumnInfo) {
/*  762 */         s1 = (short)packet.readInt();
/*      */       } else {
/*  764 */         s1 = (short)(packet.readByte() & 0xFF);
/*      */       } 
/*      */       
/*  767 */       int i1 = packet.readByte() & 0xFF;
/*      */       
/*  769 */       int defaultValueStart = -1;
/*  770 */       int defaultValueLength = -1;
/*      */       
/*  772 */       if (extractDefaultValues) {
/*  773 */         defaultValueStart = packet.getPosition() + 1;
/*  774 */         defaultValueLength = packet.fastSkipLenString();
/*      */       } 
/*      */       
/*  777 */       Field field1 = new Field(this.connection, packet.getByteBuffer(), databaseNameStart, databaseNameLength, i, j, originalTableNameStart, originalTableNameLength, k, m, originalColumnNameStart, originalColumnNameLength, l, n, s1, i1, defaultValueStart, defaultValueLength, charSetNumber);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  785 */       return field1;
/*      */     } 
/*      */     
/*  788 */     int tableNameStart = packet.getPosition() + 1;
/*  789 */     int tableNameLength = packet.fastSkipLenString();
/*  790 */     tableNameStart = adjustStartForFieldLength(tableNameStart, tableNameLength);
/*      */     
/*  792 */     int nameStart = packet.getPosition() + 1;
/*  793 */     int nameLength = packet.fastSkipLenString();
/*  794 */     nameStart = adjustStartForFieldLength(nameStart, nameLength);
/*      */     
/*  796 */     int colLength = packet.readnBytes();
/*  797 */     int colType = packet.readnBytes();
/*  798 */     packet.readByte();
/*      */     
/*  800 */     short colFlag = 0;
/*      */     
/*  802 */     if (this.hasLongColumnInfo) {
/*  803 */       colFlag = (short)packet.readInt();
/*      */     } else {
/*  805 */       colFlag = (short)(packet.readByte() & 0xFF);
/*      */     } 
/*      */     
/*  808 */     int colDecimals = packet.readByte() & 0xFF;
/*      */     
/*  810 */     if (this.colDecimalNeedsBump) {
/*  811 */       colDecimals++;
/*      */     }
/*      */     
/*  814 */     Field field = new Field(this.connection, packet.getByteBuffer(), nameStart, nameLength, tableNameStart, tableNameLength, colLength, colType, colFlag, colDecimals);
/*      */ 
/*      */ 
/*      */     
/*  818 */     return field;
/*      */   }
/*      */   
/*      */   private int adjustStartForFieldLength(int nameStart, int nameLength) {
/*  822 */     if (nameLength < 251) {
/*  823 */       return nameStart;
/*      */     }
/*      */     
/*  826 */     if (nameLength >= 251 && nameLength < 65536) {
/*  827 */       return nameStart + 2;
/*      */     }
/*      */     
/*  830 */     if (nameLength >= 65536 && nameLength < 16777216) {
/*  831 */       return nameStart + 3;
/*      */     }
/*      */     
/*  834 */     return nameStart + 8;
/*      */   }
/*      */   
/*      */   protected boolean isSetNeededForAutoCommitMode(boolean autoCommitFlag) {
/*  838 */     if (this.use41Extensions && this.connection.getElideSetAutoCommits()) {
/*  839 */       boolean autoCommitModeOnServer = ((this.serverStatus & 0x2) != 0);
/*      */ 
/*      */       
/*  842 */       if (!autoCommitFlag && versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */ 
/*      */         
/*  846 */         boolean inTransactionOnServer = ((this.serverStatus & 0x1) != 0);
/*      */ 
/*      */         
/*  849 */         return !inTransactionOnServer;
/*      */       } 
/*      */       
/*  852 */       return (autoCommitModeOnServer != autoCommitFlag);
/*      */     } 
/*      */     
/*  855 */     return true;
/*      */   }
/*      */   
/*      */   protected boolean inTransactionOnServer() {
/*  859 */     return ((this.serverStatus & 0x1) != 0);
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
/*      */   protected void changeUser(String userName, String password, String database) throws SQLException {
/*  873 */     this.packetSequence = -1;
/*      */     
/*  875 */     int passwordLength = 16;
/*  876 */     int userLength = (userName != null) ? userName.length() : 0;
/*  877 */     int databaseLength = (database != null) ? database.length() : 0;
/*      */     
/*  879 */     int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33;
/*      */     
/*  881 */     if ((this.serverCapabilities & 0x8000) != 0) {
/*  882 */       Buffer changeUserPacket = new Buffer(packLength + 1);
/*  883 */       changeUserPacket.writeByte((byte)17);
/*      */       
/*  885 */       if (versionMeetsMinimum(4, 1, 1)) {
/*  886 */         secureAuth411(changeUserPacket, packLength, userName, password, database, false);
/*      */       } else {
/*      */         
/*  889 */         secureAuth(changeUserPacket, packLength, userName, password, database, false);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  894 */       Buffer packet = new Buffer(packLength);
/*  895 */       packet.writeByte((byte)17);
/*      */ 
/*      */       
/*  898 */       packet.writeString(userName);
/*      */       
/*  900 */       if (this.protocolVersion > 9) {
/*  901 */         packet.writeString(Util.newCrypt(password, this.seed));
/*      */       } else {
/*  903 */         packet.writeString(Util.oldCrypt(password, this.seed));
/*      */       } 
/*      */       
/*  906 */       boolean localUseConnectWithDb = (this.useConnectWithDb && database != null && database.length() > 0);
/*      */ 
/*      */       
/*  909 */       if (localUseConnectWithDb) {
/*  910 */         packet.writeString(database);
/*      */       }
/*      */       
/*  913 */       send(packet, packet.getPosition());
/*  914 */       checkErrorPacket();
/*      */       
/*  916 */       if (!localUseConnectWithDb) {
/*  917 */         changeDatabaseTo(database);
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
/*      */   protected Buffer checkErrorPacket() throws SQLException {
/*  931 */     return checkErrorPacket(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkForCharsetMismatch() {
/*  938 */     if (this.connection.getUseUnicode() && this.connection.getEncoding() != null) {
/*      */       
/*  940 */       String encodingToCheck = jvmPlatformCharset;
/*      */       
/*  942 */       if (encodingToCheck == null) {
/*  943 */         encodingToCheck = System.getProperty("file.encoding");
/*      */       }
/*      */       
/*  946 */       if (encodingToCheck == null) {
/*  947 */         this.platformDbCharsetMatches = false;
/*      */       } else {
/*  949 */         this.platformDbCharsetMatches = encodingToCheck.equals(this.connection.getEncoding());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void clearInputStream() throws SQLException {
/*      */     try {
/*  957 */       int len = this.mysqlInput.available();
/*      */       
/*  959 */       while (len > 0) {
/*  960 */         this.mysqlInput.skip(len);
/*  961 */         len = this.mysqlInput.available();
/*      */       } 
/*  963 */     } catch (IOException ioEx) {
/*  964 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void resetReadPacketSequence() {
/*  970 */     this.readPacketSequence = 0;
/*      */   }
/*      */   
/*      */   protected void dumpPacketRingBuffer() throws SQLException {
/*  974 */     if (this.packetDebugRingBuffer != null && this.connection.getEnablePacketDebug()) {
/*      */       
/*  976 */       StringBuffer dumpBuffer = new StringBuffer();
/*      */       
/*  978 */       dumpBuffer.append("Last " + this.packetDebugRingBuffer.size() + " packets received from server, from oldest->newest:\n");
/*      */       
/*  980 */       dumpBuffer.append("\n");
/*      */       
/*  982 */       Iterator<StringBuffer> ringBufIter = this.packetDebugRingBuffer.iterator();
/*  983 */       while (ringBufIter.hasNext()) {
/*  984 */         dumpBuffer.append(ringBufIter.next());
/*  985 */         dumpBuffer.append("\n");
/*      */       } 
/*      */       
/*  988 */       this.connection.getLog().logTrace(dumpBuffer.toString());
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
/*      */   protected void explainSlowQuery(byte[] querySQL, String truncatedQuery) throws SQLException {
/* 1002 */     if (StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, "SELECT")) {
/*      */       
/* 1004 */       PreparedStatement stmt = null;
/* 1005 */       ResultSet rs = null;
/*      */ 
/*      */       
/* 1008 */       try { stmt = (PreparedStatement)this.connection.clientPrepareStatement("EXPLAIN ?");
/* 1009 */         stmt.setBytesNoEscapeNoQuotes(1, querySQL);
/* 1010 */         rs = stmt.executeQuery();
/*      */         
/* 1012 */         StringBuffer explainResults = new StringBuffer(Messages.getString("MysqlIO.8") + truncatedQuery + Messages.getString("MysqlIO.9"));
/*      */ 
/*      */ 
/*      */         
/* 1016 */         ResultSetUtil.appendResultSetSlashGStyle(explainResults, rs);
/*      */         
/* 1018 */         this.connection.getLog().logWarn(explainResults.toString()); }
/* 1019 */       catch (SQLException sqlEx) {  }
/*      */       finally
/* 1021 */       { if (rs != null) {
/* 1022 */           rs.close();
/*      */         }
/*      */         
/* 1025 */         if (stmt != null) {
/* 1026 */           stmt.close();
/*      */         } }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static int getMaxBuf() {
/* 1034 */     return maxBufferSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int getServerMajorVersion() {
/* 1043 */     return this.serverMajorVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int getServerMinorVersion() {
/* 1052 */     return this.serverMinorVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int getServerSubMinorVersion() {
/* 1061 */     return this.serverSubMinorVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getServerVersion() {
/* 1070 */     return this.serverVersion;
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
/*      */   void doHandshake(String user, String password, String database) throws SQLException {
/* 1087 */     this.checkPacketSequence = false;
/* 1088 */     this.readPacketSequence = 0;
/*      */     
/* 1090 */     Buffer buf = readPacket();
/*      */ 
/*      */     
/* 1093 */     this.protocolVersion = buf.readByte();
/*      */     
/* 1095 */     if (this.protocolVersion == -1) {
/*      */       try {
/* 1097 */         this.mysqlConnection.close();
/* 1098 */       } catch (Exception e) {}
/*      */ 
/*      */ 
/*      */       
/* 1102 */       int errno = 2000;
/*      */       
/* 1104 */       errno = buf.readInt();
/*      */       
/* 1106 */       String serverErrorMessage = buf.readString("ASCII", getExceptionInterceptor());
/*      */       
/* 1108 */       StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.10"));
/*      */       
/* 1110 */       errorBuf.append(serverErrorMessage);
/* 1111 */       errorBuf.append("\"");
/*      */       
/* 1113 */       String xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */ 
/*      */       
/* 1116 */       throw SQLError.createSQLException(SQLError.get(xOpen) + ", " + errorBuf.toString(), xOpen, errno, getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */     
/* 1120 */     this.serverVersion = buf.readString("ASCII", getExceptionInterceptor());
/*      */ 
/*      */     
/* 1123 */     int point = this.serverVersion.indexOf('.');
/*      */     
/* 1125 */     if (point != -1) {
/*      */       try {
/* 1127 */         int n = Integer.parseInt(this.serverVersion.substring(0, point));
/* 1128 */         this.serverMajorVersion = n;
/* 1129 */       } catch (NumberFormatException NFE1) {}
/*      */ 
/*      */ 
/*      */       
/* 1133 */       String remaining = this.serverVersion.substring(point + 1, this.serverVersion.length());
/*      */       
/* 1135 */       point = remaining.indexOf('.');
/*      */       
/* 1137 */       if (point != -1) {
/*      */         try {
/* 1139 */           int n = Integer.parseInt(remaining.substring(0, point));
/* 1140 */           this.serverMinorVersion = n;
/* 1141 */         } catch (NumberFormatException nfe) {}
/*      */ 
/*      */ 
/*      */         
/* 1145 */         remaining = remaining.substring(point + 1, remaining.length());
/*      */         
/* 1147 */         int pos = 0;
/*      */         
/* 1149 */         while (pos < remaining.length() && 
/* 1150 */           remaining.charAt(pos) >= '0' && remaining.charAt(pos) <= '9')
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1155 */           pos++;
/*      */         }
/*      */         
/*      */         try {
/* 1159 */           int n = Integer.parseInt(remaining.substring(0, pos));
/* 1160 */           this.serverSubMinorVersion = n;
/* 1161 */         } catch (NumberFormatException nfe) {}
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1167 */     if (versionMeetsMinimum(4, 0, 8)) {
/* 1168 */       this.maxThreeBytes = 16777215;
/* 1169 */       this.useNewLargePackets = true;
/*      */     } else {
/* 1171 */       this.maxThreeBytes = 16581375;
/* 1172 */       this.useNewLargePackets = false;
/*      */     } 
/*      */     
/* 1175 */     this.colDecimalNeedsBump = versionMeetsMinimum(3, 23, 0);
/* 1176 */     this.colDecimalNeedsBump = !versionMeetsMinimum(3, 23, 15);
/* 1177 */     this.useNewUpdateCounts = versionMeetsMinimum(3, 22, 5);
/*      */     
/* 1179 */     this.threadId = buf.readLong();
/* 1180 */     this.seed = buf.readString("ASCII", getExceptionInterceptor());
/*      */     
/* 1182 */     this.serverCapabilities = 0;
/*      */     
/* 1184 */     if (buf.getPosition() < buf.getBufLength()) {
/* 1185 */       this.serverCapabilities = buf.readInt();
/*      */     }
/*      */     
/* 1188 */     if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) {
/* 1189 */       int position = buf.getPosition();
/*      */ 
/*      */       
/* 1192 */       this.serverCharsetIndex = buf.readByte() & 0xFF;
/* 1193 */       this.serverStatus = buf.readInt();
/* 1194 */       checkTransactionState(0);
/* 1195 */       buf.setPosition(position + 16);
/*      */       
/* 1197 */       String seedPart2 = buf.readString("ASCII", getExceptionInterceptor());
/* 1198 */       StringBuffer newSeed = new StringBuffer(20);
/* 1199 */       newSeed.append(this.seed);
/* 1200 */       newSeed.append(seedPart2);
/* 1201 */       this.seed = newSeed.toString();
/*      */     } 
/*      */     
/* 1204 */     if ((this.serverCapabilities & 0x20) != 0 && this.connection.getUseCompression())
/*      */     {
/* 1206 */       this.clientParam |= 0x20L;
/*      */     }
/*      */     
/* 1209 */     this.useConnectWithDb = (database != null && database.length() > 0 && !this.connection.getCreateDatabaseIfNotExist());
/*      */ 
/*      */ 
/*      */     
/* 1213 */     if (this.useConnectWithDb) {
/* 1214 */       this.clientParam |= 0x8L;
/*      */     }
/*      */     
/* 1217 */     if ((this.serverCapabilities & 0x800) == 0 && this.connection.getUseSSL()) {
/*      */       
/* 1219 */       if (this.connection.getRequireSSL()) {
/* 1220 */         this.connection.close();
/* 1221 */         forceClose();
/* 1222 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.15"), "08001", getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */       
/* 1226 */       this.connection.setUseSSL(false);
/*      */     } 
/*      */     
/* 1229 */     if ((this.serverCapabilities & 0x4) != 0) {
/*      */       
/* 1231 */       this.clientParam |= 0x4L;
/* 1232 */       this.hasLongColumnInfo = true;
/*      */     } 
/*      */ 
/*      */     
/* 1236 */     if (!this.connection.getUseAffectedRows()) {
/* 1237 */       this.clientParam |= 0x2L;
/*      */     }
/*      */     
/* 1240 */     if (this.connection.getAllowLoadLocalInfile()) {
/* 1241 */       this.clientParam |= 0x80L;
/*      */     }
/*      */     
/* 1244 */     if (this.isInteractiveClient) {
/* 1245 */       this.clientParam |= 0x400L;
/*      */     }
/*      */ 
/*      */     
/* 1249 */     if (this.protocolVersion > 9) {
/* 1250 */       this.clientParam |= 0x1L;
/*      */     } else {
/* 1252 */       this.clientParam &= 0xFFFFFFFFFFFFFFFEL;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1258 */     if (versionMeetsMinimum(4, 1, 0) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x4000) != 0)) {
/* 1259 */       if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) {
/* 1260 */         this.clientParam |= 0x200L;
/* 1261 */         this.has41NewNewProt = true;
/*      */ 
/*      */         
/* 1264 */         this.clientParam |= 0x2000L;
/*      */ 
/*      */         
/* 1267 */         this.clientParam |= 0x20000L;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1272 */         if (this.connection.getAllowMultiQueries()) {
/* 1273 */           this.clientParam |= 0x10000L;
/*      */         }
/*      */       } else {
/* 1276 */         this.clientParam |= 0x4000L;
/* 1277 */         this.has41NewNewProt = false;
/*      */       } 
/*      */       
/* 1280 */       this.use41Extensions = true;
/*      */     } 
/*      */     
/* 1283 */     int passwordLength = 16;
/* 1284 */     int userLength = (user != null) ? user.length() : 0;
/* 1285 */     int databaseLength = (database != null) ? database.length() : 0;
/*      */     
/* 1287 */     int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33;
/*      */     
/* 1289 */     Buffer packet = null;
/*      */     
/* 1291 */     if (!this.connection.getUseSSL()) {
/* 1292 */       if ((this.serverCapabilities & 0x8000) != 0) {
/* 1293 */         this.clientParam |= 0x8000L;
/*      */         
/* 1295 */         if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) {
/* 1296 */           secureAuth411(null, packLength, user, password, database, true);
/*      */         } else {
/*      */           
/* 1299 */           secureAuth(null, packLength, user, password, database, true);
/*      */         } 
/*      */       } else {
/*      */         
/* 1303 */         packet = new Buffer(packLength);
/*      */         
/* 1305 */         if ((this.clientParam & 0x4000L) != 0L) {
/* 1306 */           if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) {
/* 1307 */             packet.writeLong(this.clientParam);
/* 1308 */             packet.writeLong(this.maxThreeBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1313 */             packet.writeByte((byte)8);
/*      */ 
/*      */             
/* 1316 */             packet.writeBytesNoNull(new byte[23]);
/*      */           } else {
/* 1318 */             packet.writeLong(this.clientParam);
/* 1319 */             packet.writeLong(this.maxThreeBytes);
/*      */           } 
/*      */         } else {
/* 1322 */           packet.writeInt((int)this.clientParam);
/* 1323 */           packet.writeLongInt(this.maxThreeBytes);
/*      */         } 
/*      */ 
/*      */         
/* 1327 */         packet.writeString(user, "Cp1252", this.connection);
/*      */         
/* 1329 */         if (this.protocolVersion > 9) {
/* 1330 */           packet.writeString(Util.newCrypt(password, this.seed), "Cp1252", this.connection);
/*      */         } else {
/* 1332 */           packet.writeString(Util.oldCrypt(password, this.seed), "Cp1252", this.connection);
/*      */         } 
/*      */         
/* 1335 */         if (this.useConnectWithDb) {
/* 1336 */           packet.writeString(database, "Cp1252", this.connection);
/*      */         }
/*      */         
/* 1339 */         send(packet, packet.getPosition());
/*      */       } 
/*      */     } else {
/* 1342 */       negotiateSSLConnection(user, password, database, packLength);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1349 */     if (!versionMeetsMinimum(4, 1, 1) && this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0) {
/* 1350 */       checkErrorPacket();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1356 */     if ((this.serverCapabilities & 0x20) != 0 && this.connection.getUseCompression()) {
/*      */ 
/*      */ 
/*      */       
/* 1360 */       this.deflater = new Deflater();
/* 1361 */       this.useCompression = true;
/* 1362 */       this.mysqlInput = new CompressedInputStream(this.connection, this.mysqlInput);
/*      */     } 
/*      */ 
/*      */     
/* 1366 */     if (!this.useConnectWithDb) {
/* 1367 */       changeDatabaseTo(database);
/*      */     }
/*      */     
/*      */     try {
/* 1371 */       this.mysqlConnection = this.socketFactory.afterHandshake();
/* 1372 */     } catch (IOException ioEx) {
/* 1373 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void changeDatabaseTo(String database) throws SQLException {
/* 1378 */     if (database == null || database.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 1383 */       sendCommand(2, database, null, false, null, 0);
/* 1384 */     } catch (Exception ex) {
/* 1385 */       if (this.connection.getCreateDatabaseIfNotExist()) {
/* 1386 */         sendCommand(3, "CREATE DATABASE IF NOT EXISTS " + database, null, false, null, 0);
/*      */ 
/*      */         
/* 1389 */         sendCommand(2, database, null, false, null, 0);
/*      */       } else {
/* 1391 */         throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ex, getExceptionInterceptor());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final ResultSetRow nextRow(Field[] fields, int columnCount, boolean isBinaryEncoded, int resultSetConcurrency, boolean useBufferRowIfPossible, boolean useBufferRowExplicit, boolean canReuseRowPacketForBufferRow, Buffer existingRowPacket) throws SQLException {
/* 1419 */     if (this.useDirectRowUnpack && existingRowPacket == null && !isBinaryEncoded && !useBufferRowIfPossible && !useBufferRowExplicit)
/*      */     {
/*      */       
/* 1422 */       return nextRowFast(fields, columnCount, isBinaryEncoded, resultSetConcurrency, useBufferRowIfPossible, useBufferRowExplicit, canReuseRowPacketForBufferRow);
/*      */     }
/*      */ 
/*      */     
/* 1426 */     Buffer rowPacket = null;
/*      */     
/* 1428 */     if (existingRowPacket == null) {
/* 1429 */       rowPacket = checkErrorPacket();
/*      */       
/* 1431 */       if (!useBufferRowExplicit && useBufferRowIfPossible && 
/* 1432 */         rowPacket.getBufLength() > this.useBufferRowSizeThreshold) {
/* 1433 */         useBufferRowExplicit = true;
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1439 */       rowPacket = existingRowPacket;
/* 1440 */       checkErrorPacket(existingRowPacket);
/*      */     } 
/*      */ 
/*      */     
/* 1444 */     if (!isBinaryEncoded) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1449 */       rowPacket.setPosition(rowPacket.getPosition() - 1);
/*      */       
/* 1451 */       if (!rowPacket.isLastDataPacket()) {
/* 1452 */         if (resultSetConcurrency == 1008 || (!useBufferRowIfPossible && !useBufferRowExplicit)) {
/*      */ 
/*      */           
/* 1455 */           byte[][] rowData = new byte[columnCount][];
/*      */           
/* 1457 */           for (int i = 0; i < columnCount; i++) {
/* 1458 */             rowData[i] = rowPacket.readLenByteArray(0);
/*      */           }
/*      */           
/* 1461 */           return new ByteArrayRow(rowData, getExceptionInterceptor());
/*      */         } 
/*      */         
/* 1464 */         if (!canReuseRowPacketForBufferRow) {
/* 1465 */           this.reusablePacket = new Buffer(rowPacket.getBufLength());
/*      */         }
/*      */         
/* 1468 */         return new BufferRow(rowPacket, fields, false, getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */       
/* 1472 */       readServerStatusForResultSets(rowPacket);
/*      */       
/* 1474 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1481 */     if (!rowPacket.isLastDataPacket()) {
/* 1482 */       if (resultSetConcurrency == 1008 || (!useBufferRowIfPossible && !useBufferRowExplicit))
/*      */       {
/* 1484 */         return unpackBinaryResultSetRow(fields, rowPacket, resultSetConcurrency);
/*      */       }
/*      */ 
/*      */       
/* 1488 */       if (!canReuseRowPacketForBufferRow) {
/* 1489 */         this.reusablePacket = new Buffer(rowPacket.getBufLength());
/*      */       }
/*      */       
/* 1492 */       return new BufferRow(rowPacket, fields, true, getExceptionInterceptor());
/*      */     } 
/*      */     
/* 1495 */     rowPacket.setPosition(rowPacket.getPosition() - 1);
/* 1496 */     readServerStatusForResultSets(rowPacket);
/*      */     
/* 1498 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final ResultSetRow nextRowFast(Field[] fields, int columnCount, boolean isBinaryEncoded, int resultSetConcurrency, boolean useBufferRowIfPossible, boolean useBufferRowExplicit, boolean canReuseRowPacket) throws SQLException {
/*      */     try {
/* 1507 */       int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */       
/* 1510 */       if (lengthRead < 4) {
/* 1511 */         forceClose();
/* 1512 */         throw new RuntimeException(Messages.getString("MysqlIO.43"));
/*      */       } 
/*      */       
/* 1515 */       int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1520 */       if (packetLength == this.maxThreeBytes) {
/* 1521 */         reuseAndReadPacket(this.reusablePacket, packetLength);
/*      */ 
/*      */         
/* 1524 */         return nextRow(fields, columnCount, isBinaryEncoded, resultSetConcurrency, useBufferRowIfPossible, useBufferRowExplicit, canReuseRowPacket, this.reusablePacket);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1531 */       if (packetLength > this.useBufferRowSizeThreshold) {
/* 1532 */         reuseAndReadPacket(this.reusablePacket, packetLength);
/*      */ 
/*      */         
/* 1535 */         return nextRow(fields, columnCount, isBinaryEncoded, resultSetConcurrency, true, true, false, this.reusablePacket);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1540 */       int remaining = packetLength;
/*      */       
/* 1542 */       boolean firstTime = true;
/*      */       
/* 1544 */       byte[][] rowData = (byte[][])null;
/*      */       
/* 1546 */       for (int i = 0; i < columnCount; i++) {
/*      */         
/* 1548 */         int sw = this.mysqlInput.read() & 0xFF;
/* 1549 */         remaining--;
/*      */         
/* 1551 */         if (firstTime) {
/* 1552 */           if (sw == 255) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1557 */             Buffer errorPacket = new Buffer(packetLength + 4);
/* 1558 */             errorPacket.setPosition(0);
/* 1559 */             errorPacket.writeByte(this.packetHeaderBuf[0]);
/* 1560 */             errorPacket.writeByte(this.packetHeaderBuf[1]);
/* 1561 */             errorPacket.writeByte(this.packetHeaderBuf[2]);
/* 1562 */             errorPacket.writeByte((byte)1);
/* 1563 */             errorPacket.writeByte((byte)sw);
/* 1564 */             readFully(this.mysqlInput, errorPacket.getByteBuffer(), 5, packetLength - 1);
/* 1565 */             errorPacket.setPosition(4);
/* 1566 */             checkErrorPacket(errorPacket);
/*      */           } 
/*      */           
/* 1569 */           if (sw == 254 && packetLength < 9) {
/* 1570 */             if (this.use41Extensions) {
/* 1571 */               this.warningCount = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8;
/*      */               
/* 1573 */               remaining -= 2;
/*      */               
/* 1575 */               if (this.warningCount > 0) {
/* 1576 */                 this.hadWarnings = true;
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1582 */               this.oldServerStatus = this.serverStatus;
/*      */               
/* 1584 */               this.serverStatus = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8;
/*      */               
/* 1586 */               checkTransactionState(this.oldServerStatus);
/*      */               
/* 1588 */               remaining -= 2;
/*      */               
/* 1590 */               if (remaining > 0) {
/* 1591 */                 skipFully(this.mysqlInput, remaining);
/*      */               }
/*      */             } 
/*      */             
/* 1595 */             return null;
/*      */           } 
/*      */           
/* 1598 */           rowData = new byte[columnCount][];
/*      */           
/* 1600 */           firstTime = false;
/*      */         } 
/*      */         
/* 1603 */         int len = 0;
/*      */         
/* 1605 */         switch (sw) {
/*      */           case 251:
/* 1607 */             len = -1;
/*      */             break;
/*      */           
/*      */           case 252:
/* 1611 */             len = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8;
/*      */             
/* 1613 */             remaining -= 2;
/*      */             break;
/*      */           
/*      */           case 253:
/* 1617 */             len = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8 | (this.mysqlInput.read() & 0xFF) << 16;
/*      */ 
/*      */ 
/*      */             
/* 1621 */             remaining -= 3;
/*      */             break;
/*      */           
/*      */           case 254:
/* 1625 */             len = (int)((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8L | (this.mysqlInput.read() & 0xFF) << 16L | (this.mysqlInput.read() & 0xFF) << 24L | (this.mysqlInput.read() & 0xFF) << 32L | (this.mysqlInput.read() & 0xFF) << 40L | (this.mysqlInput.read() & 0xFF) << 48L | (this.mysqlInput.read() & 0xFF) << 56L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1633 */             remaining -= 8;
/*      */             break;
/*      */           
/*      */           default:
/* 1637 */             len = sw;
/*      */             break;
/*      */         } 
/* 1640 */         if (len == -1) {
/* 1641 */           rowData[i] = null;
/* 1642 */         } else if (len == 0) {
/* 1643 */           rowData[i] = Constants.EMPTY_BYTE_ARRAY;
/*      */         } else {
/* 1645 */           rowData[i] = new byte[len];
/*      */           
/* 1647 */           int bytesRead = readFully(this.mysqlInput, rowData[i], 0, len);
/*      */ 
/*      */           
/* 1650 */           if (bytesRead != len) {
/* 1651 */             throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException(Messages.getString("MysqlIO.43")), getExceptionInterceptor());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1656 */           remaining -= bytesRead;
/*      */         } 
/*      */       } 
/*      */       
/* 1660 */       if (remaining > 0) {
/* 1661 */         skipFully(this.mysqlInput, remaining);
/*      */       }
/*      */       
/* 1664 */       return new ByteArrayRow(rowData, getExceptionInterceptor());
/* 1665 */     } catch (IOException ioEx) {
/* 1666 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
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
/*      */   final void quit() throws SQLException {
/*      */     try {
/* 1682 */       if (!this.mysqlConnection.isClosed()) {
/* 1683 */         this.mysqlConnection.shutdownInput();
/*      */       }
/* 1685 */     } catch (IOException ioEx) {
/* 1686 */       this.connection.getLog().logWarn("Caught while disconnecting...", ioEx);
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */       
/* 1694 */       forceClose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Buffer getSharedSendPacket() {
/* 1705 */     if (this.sharedSendPacket == null) {
/* 1706 */       this.sharedSendPacket = new Buffer(1024);
/*      */     }
/*      */     
/* 1709 */     return this.sharedSendPacket;
/*      */   }
/*      */   
/*      */   void closeStreamer(RowData streamer) throws SQLException {
/* 1713 */     if (this.streamingData == null) {
/* 1714 */       throw SQLError.createSQLException(Messages.getString("MysqlIO.17") + streamer + Messages.getString("MysqlIO.18"), getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 1718 */     if (streamer != this.streamingData) {
/* 1719 */       throw SQLError.createSQLException(Messages.getString("MysqlIO.19") + streamer + Messages.getString("MysqlIO.20") + Messages.getString("MysqlIO.21") + Messages.getString("MysqlIO.22"), getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1725 */     this.streamingData = null;
/*      */   }
/*      */   
/*      */   boolean tackOnMoreStreamingResults(ResultSetImpl addingTo) throws SQLException {
/* 1729 */     if ((this.serverStatus & 0x8) != 0) {
/*      */       
/* 1731 */       boolean moreRowSetsExist = true;
/* 1732 */       ResultSetImpl currentResultSet = addingTo;
/* 1733 */       boolean firstTime = true;
/*      */       
/* 1735 */       while (moreRowSetsExist && (
/* 1736 */         firstTime || !currentResultSet.reallyResult())) {
/*      */ 
/*      */ 
/*      */         
/* 1740 */         firstTime = false;
/*      */         
/* 1742 */         Buffer fieldPacket = checkErrorPacket();
/* 1743 */         fieldPacket.setPosition(0);
/*      */         
/* 1745 */         Statement owningStatement = addingTo.getStatement();
/*      */         
/* 1747 */         int maxRows = owningStatement.getMaxRows();
/*      */ 
/*      */ 
/*      */         
/* 1751 */         ResultSetImpl newResultSet = readResultsForQueryOrUpdate((StatementImpl)owningStatement, maxRows, owningStatement.getResultSetType(), owningStatement.getResultSetConcurrency(), true, owningStatement.getConnection().getCatalog(), fieldPacket, addingTo.isBinaryEncoded, -1L, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1759 */         currentResultSet.setNextResultSet(newResultSet);
/*      */         
/* 1761 */         currentResultSet = newResultSet;
/*      */         
/* 1763 */         moreRowSetsExist = ((this.serverStatus & 0x8) != 0);
/*      */         
/* 1765 */         if (!currentResultSet.reallyResult() && !moreRowSetsExist)
/*      */         {
/* 1767 */           return false;
/*      */         }
/*      */       } 
/*      */       
/* 1771 */       return true;
/*      */     } 
/*      */     
/* 1774 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ResultSetImpl readAllResults(StatementImpl callingStatement, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Buffer resultPacket, boolean isBinaryEncoded, long preSentColumnCount, Field[] metadataFromCache) throws SQLException {
/* 1782 */     resultPacket.setPosition(resultPacket.getPosition() - 1);
/*      */     
/* 1784 */     ResultSetImpl topLevelResultSet = readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, isBinaryEncoded, preSentColumnCount, metadataFromCache);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1789 */     ResultSetImpl currentResultSet = topLevelResultSet;
/*      */     
/* 1791 */     boolean checkForMoreResults = ((this.clientParam & 0x20000L) != 0L);
/*      */ 
/*      */     
/* 1794 */     boolean serverHasMoreResults = ((this.serverStatus & 0x8) != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1800 */     if (serverHasMoreResults && streamResults) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1805 */       if (topLevelResultSet.getUpdateCount() != -1L) {
/* 1806 */         tackOnMoreStreamingResults(topLevelResultSet);
/*      */       }
/*      */       
/* 1809 */       reclaimLargeReusablePacket();
/*      */       
/* 1811 */       return topLevelResultSet;
/*      */     } 
/*      */     
/* 1814 */     boolean moreRowSetsExist = checkForMoreResults & serverHasMoreResults;
/*      */     
/* 1816 */     while (moreRowSetsExist) {
/* 1817 */       Buffer fieldPacket = checkErrorPacket();
/* 1818 */       fieldPacket.setPosition(0);
/*      */       
/* 1820 */       ResultSetImpl newResultSet = readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, fieldPacket, isBinaryEncoded, preSentColumnCount, metadataFromCache);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1825 */       currentResultSet.setNextResultSet(newResultSet);
/*      */       
/* 1827 */       currentResultSet = newResultSet;
/*      */       
/* 1829 */       moreRowSetsExist = ((this.serverStatus & 0x8) != 0);
/*      */     } 
/*      */     
/* 1832 */     if (!streamResults) {
/* 1833 */       clearInputStream();
/*      */     }
/*      */     
/* 1836 */     reclaimLargeReusablePacket();
/*      */     
/* 1838 */     return topLevelResultSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void resetMaxBuf() {
/* 1845 */     this.maxAllowedPacket = this.connection.getMaxAllowedPacket();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Buffer sendCommand(int command, String extraData, Buffer queryPacket, boolean skipCheck, String extraDataCharEncoding, int timeoutMillis) throws SQLException {
/* 1871 */     this.commandCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1878 */     this.enablePacketDebug = this.connection.getEnablePacketDebug();
/* 1879 */     this.readPacketSequence = 0;
/*      */     
/* 1881 */     int oldTimeout = 0;
/*      */     
/* 1883 */     if (timeoutMillis != 0) {
/*      */       try {
/* 1885 */         oldTimeout = this.mysqlConnection.getSoTimeout();
/* 1886 */         this.mysqlConnection.setSoTimeout(timeoutMillis);
/* 1887 */       } catch (SocketException e) {
/* 1888 */         throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, e, getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1895 */       checkForOutstandingStreamingData();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1900 */       this.oldServerStatus = this.serverStatus;
/* 1901 */       this.serverStatus = 0;
/* 1902 */       this.hadWarnings = false;
/* 1903 */       this.warningCount = 0;
/*      */       
/* 1905 */       this.queryNoIndexUsed = false;
/* 1906 */       this.queryBadIndexUsed = false;
/* 1907 */       this.serverQueryWasSlow = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1913 */       if (this.useCompression) {
/* 1914 */         int bytesLeft = this.mysqlInput.available();
/*      */         
/* 1916 */         if (bytesLeft > 0) {
/* 1917 */           this.mysqlInput.skip(bytesLeft);
/*      */         }
/*      */       } 
/*      */       
/*      */       try {
/* 1922 */         clearInputStream();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1931 */         if (queryPacket == null) {
/* 1932 */           int packLength = 8 + ((extraData != null) ? extraData.length() : 0) + 2;
/*      */ 
/*      */           
/* 1935 */           if (this.sendPacket == null) {
/* 1936 */             this.sendPacket = new Buffer(packLength);
/*      */           }
/*      */           
/* 1939 */           this.packetSequence = -1;
/* 1940 */           this.readPacketSequence = 0;
/* 1941 */           this.checkPacketSequence = true;
/* 1942 */           this.sendPacket.clear();
/*      */           
/* 1944 */           this.sendPacket.writeByte((byte)command);
/*      */           
/* 1946 */           if (command == 2 || command == 5 || command == 6 || command == 3 || command == 22) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1951 */             if (extraDataCharEncoding == null) {
/* 1952 */               this.sendPacket.writeStringNoNull(extraData);
/*      */             } else {
/* 1954 */               this.sendPacket.writeStringNoNull(extraData, extraDataCharEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 1959 */           else if (command == 12) {
/* 1960 */             long id = Long.parseLong(extraData);
/* 1961 */             this.sendPacket.writeLong(id);
/*      */           } 
/*      */           
/* 1964 */           send(this.sendPacket, this.sendPacket.getPosition());
/*      */         } else {
/* 1966 */           this.packetSequence = -1;
/* 1967 */           send(queryPacket, queryPacket.getPosition());
/*      */         } 
/* 1969 */       } catch (SQLException sqlEx) {
/*      */         
/* 1971 */         throw sqlEx;
/* 1972 */       } catch (Exception ex) {
/* 1973 */         throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ex, getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */       
/* 1977 */       Buffer returnPacket = null;
/*      */       
/* 1979 */       if (!skipCheck) {
/* 1980 */         if (command == 23 || command == 26) {
/*      */           
/* 1982 */           this.readPacketSequence = 0;
/* 1983 */           this.packetSequenceReset = true;
/*      */         } 
/*      */         
/* 1986 */         returnPacket = checkErrorPacket(command);
/*      */       } 
/*      */       
/* 1989 */       return returnPacket;
/* 1990 */     } catch (IOException ioEx) {
/* 1991 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     } finally {
/*      */       
/* 1994 */       if (timeoutMillis != 0) {
/*      */         try {
/* 1996 */           this.mysqlConnection.setSoTimeout(oldTimeout);
/* 1997 */         } catch (SocketException e) {
/* 1998 */           throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, e, getExceptionInterceptor());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public MysqlIO(String host, int port, Properties props, String socketFactoryClassName, MySQLConnection conn, int socketTimeout, int useBufferRowSizeThreshold) throws IOException, SQLException {
/* 2005 */     this.statementExecutionDepth = 0; this.connection = conn; if (this.connection.getEnablePacketDebug())
/*      */       this.packetDebugRingBuffer = new LinkedList();  this.traceProtocol = this.connection.getTraceProtocol(); this.useAutoSlowLog = this.connection.getAutoSlowLog(); this.useBufferRowSizeThreshold = useBufferRowSizeThreshold; this.useDirectRowUnpack = this.connection.getUseDirectRowUnpack(); this.logSlowQueries = this.connection.getLogSlowQueries(); this.reusablePacket = new Buffer(1024); this.sendPacket = new Buffer(1024); this.port = port; this.host = host; this.socketFactoryClassName = socketFactoryClassName; this.socketFactory = createSocketFactory(); this.exceptionInterceptor = this.connection.getExceptionInterceptor(); try { this.mysqlConnection = this.socketFactory.connect(this.host, this.port, props); if (socketTimeout != 0)
/*      */         try { this.mysqlConnection.setSoTimeout(socketTimeout); } catch (Exception ex) {}  this.mysqlConnection = this.socketFactory.beforeHandshake(); if (this.connection.getUseReadAheadInput()) { this.mysqlInput = (InputStream)new ReadAheadInputStream(this.mysqlConnection.getInputStream(), 16384, this.connection.getTraceProtocol(), this.connection.getLog()); } else if (this.connection.useUnbufferedInput()) { this.mysqlInput = this.mysqlConnection.getInputStream(); } else { this.mysqlInput = new BufferedInputStream(this.mysqlConnection.getInputStream(), 16384); }  this.mysqlOutput = new BufferedOutputStream(this.mysqlConnection.getOutputStream(), 16384); this.isInteractiveClient = this.connection.getInteractiveClient(); this.profileSql = this.connection.getProfileSql(); this.sessionCalendar = Calendar.getInstance(); this.autoGenerateTestcaseScript = this.connection.getAutoGenerateTestcaseScript(); this.needToGrabQueryFromPacket = (this.profileSql || this.logSlowQueries || this.autoGenerateTestcaseScript); if (this.connection.getUseNanosForElapsedTime() && Util.nanoTimeAvailable()) { this.useNanosForElapsedTime = true; this.queryTimingUnits = Messages.getString("Nanoseconds"); } else { this.queryTimingUnits = Messages.getString("Milliseconds"); }  if (this.connection.getLogSlowQueries())
/*      */         calculateSlowQueryThreshold();  } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, 0L, 0L, ioEx, getExceptionInterceptor()); }
/* 2009 */      } protected boolean shouldIntercept() { return (this.statementInterceptors != null); }
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
/*      */   final ResultSetInternalMethods sqlQueryDirect(StatementImpl callingStatement, String query, String characterEncoding, Buffer queryPacket, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata) throws Exception {
/* 2036 */     this.statementExecutionDepth++;
/*      */     
/*      */     try {
/* 2039 */       if (this.statementInterceptors != null) {
/* 2040 */         ResultSetInternalMethods interceptedResults = invokeStatementInterceptorsPre(query, callingStatement, false);
/*      */ 
/*      */         
/* 2043 */         if (interceptedResults != null) {
/* 2044 */           return interceptedResults;
/*      */         }
/*      */       } 
/*      */       
/* 2048 */       long queryStartTime = 0L;
/* 2049 */       long queryEndTime = 0L;
/*      */       
/* 2051 */       if (query != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2056 */         int packLength = 5 + query.length() * 2 + 2;
/*      */         
/* 2058 */         String statementComment = this.connection.getStatementComment();
/*      */         
/* 2060 */         byte[] commentAsBytes = null;
/*      */         
/* 2062 */         if (statementComment != null) {
/* 2063 */           commentAsBytes = StringUtils.getBytes(statementComment, (SingleByteCharsetConverter)null, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2068 */           packLength += commentAsBytes.length;
/* 2069 */           packLength += 6;
/*      */         } 
/*      */         
/* 2072 */         if (this.sendPacket == null) {
/* 2073 */           this.sendPacket = new Buffer(packLength);
/*      */         } else {
/* 2075 */           this.sendPacket.clear();
/*      */         } 
/*      */         
/* 2078 */         this.sendPacket.writeByte((byte)3);
/*      */         
/* 2080 */         if (commentAsBytes != null) {
/* 2081 */           this.sendPacket.writeBytesNoNull(Constants.SLASH_STAR_SPACE_AS_BYTES);
/* 2082 */           this.sendPacket.writeBytesNoNull(commentAsBytes);
/* 2083 */           this.sendPacket.writeBytesNoNull(Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
/*      */         } 
/*      */         
/* 2086 */         if (characterEncoding != null) {
/* 2087 */           if (this.platformDbCharsetMatches) {
/* 2088 */             this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 2093 */           else if (StringUtils.startsWithIgnoreCaseAndWs(query, "LOAD DATA")) {
/* 2094 */             this.sendPacket.writeBytesNoNull(query.getBytes());
/*      */           } else {
/* 2096 */             this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2104 */           this.sendPacket.writeStringNoNull(query);
/*      */         } 
/*      */         
/* 2107 */         queryPacket = this.sendPacket;
/*      */       } 
/*      */       
/* 2110 */       byte[] queryBuf = null;
/* 2111 */       int oldPacketPosition = 0;
/*      */       
/* 2113 */       if (this.needToGrabQueryFromPacket) {
/* 2114 */         queryBuf = queryPacket.getByteBuffer();
/*      */ 
/*      */         
/* 2117 */         oldPacketPosition = queryPacket.getPosition();
/*      */         
/* 2119 */         queryStartTime = getCurrentTimeNanosOrMillis();
/*      */       } 
/*      */       
/* 2122 */       if (this.autoGenerateTestcaseScript) {
/* 2123 */         String testcaseQuery = null;
/*      */         
/* 2125 */         if (query != null) {
/* 2126 */           testcaseQuery = query;
/*      */         } else {
/* 2128 */           testcaseQuery = new String(queryBuf, 5, oldPacketPosition - 5);
/*      */         } 
/*      */ 
/*      */         
/* 2132 */         StringBuffer debugBuf = new StringBuffer(testcaseQuery.length() + 32);
/* 2133 */         this.connection.generateConnectionCommentBlock(debugBuf);
/* 2134 */         debugBuf.append(testcaseQuery);
/* 2135 */         debugBuf.append(';');
/* 2136 */         this.connection.dumpTestcaseQuery(debugBuf.toString());
/*      */       } 
/*      */ 
/*      */       
/* 2140 */       Buffer resultPacket = sendCommand(3, null, queryPacket, false, null, 0);
/*      */ 
/*      */       
/* 2143 */       long fetchBeginTime = 0L;
/* 2144 */       long fetchEndTime = 0L;
/*      */       
/* 2146 */       String profileQueryToLog = null;
/*      */       
/* 2148 */       boolean queryWasSlow = false;
/*      */       
/* 2150 */       if (this.profileSql || this.logSlowQueries) {
/* 2151 */         queryEndTime = System.currentTimeMillis();
/*      */         
/* 2153 */         boolean shouldExtractQuery = false;
/*      */         
/* 2155 */         if (this.profileSql) {
/* 2156 */           shouldExtractQuery = true;
/* 2157 */         } else if (this.logSlowQueries) {
/* 2158 */           long queryTime = queryEndTime - queryStartTime;
/*      */           
/* 2160 */           boolean logSlow = false;
/*      */           
/* 2162 */           if (this.useAutoSlowLog) {
/* 2163 */             logSlow = (queryTime > this.connection.getSlowQueryThresholdMillis());
/*      */           } else {
/* 2165 */             logSlow = this.connection.isAbonormallyLongQuery(queryTime);
/*      */             
/* 2167 */             this.connection.reportQueryTime(queryTime);
/*      */           } 
/*      */           
/* 2170 */           if (logSlow) {
/* 2171 */             shouldExtractQuery = true;
/* 2172 */             queryWasSlow = true;
/*      */           } 
/*      */         } 
/*      */         
/* 2176 */         if (shouldExtractQuery) {
/*      */           
/* 2178 */           boolean truncated = false;
/*      */           
/* 2180 */           int extractPosition = oldPacketPosition;
/*      */           
/* 2182 */           if (oldPacketPosition > this.connection.getMaxQuerySizeToLog()) {
/* 2183 */             extractPosition = this.connection.getMaxQuerySizeToLog() + 5;
/* 2184 */             truncated = true;
/*      */           } 
/*      */           
/* 2187 */           profileQueryToLog = new String(queryBuf, 5, extractPosition - 5);
/*      */ 
/*      */           
/* 2190 */           if (truncated) {
/* 2191 */             profileQueryToLog = profileQueryToLog + Messages.getString("MysqlIO.25");
/*      */           }
/*      */         } 
/*      */         
/* 2195 */         fetchBeginTime = queryEndTime;
/*      */       } 
/*      */       
/* 2198 */       ResultSetInternalMethods rs = readAllResults(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, false, -1L, cachedMetadata);
/*      */ 
/*      */ 
/*      */       
/* 2202 */       if (queryWasSlow && !this.serverQueryWasSlow) {
/* 2203 */         StringBuffer mesgBuf = new StringBuffer(48 + profileQueryToLog.length());
/*      */ 
/*      */         
/* 2206 */         mesgBuf.append(Messages.getString("MysqlIO.SlowQuery", new Object[] { new Long(this.slowQueryThreshold), this.queryTimingUnits, new Long(queryEndTime - queryStartTime) }));
/*      */ 
/*      */ 
/*      */         
/* 2210 */         mesgBuf.append(profileQueryToLog);
/*      */         
/* 2212 */         ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */         
/* 2214 */         eventSink.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), (int)(queryEndTime - queryStartTime), this.queryTimingUnits, null, new Throwable(), mesgBuf.toString()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2221 */         if (this.connection.getExplainSlowQueries()) {
/* 2222 */           if (oldPacketPosition < 1048576) {
/* 2223 */             explainSlowQuery(queryPacket.getBytes(5, oldPacketPosition - 5), profileQueryToLog);
/*      */           } else {
/*      */             
/* 2226 */             this.connection.getLog().logWarn(Messages.getString("MysqlIO.28") + 1048576 + Messages.getString("MysqlIO.29"));
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2234 */       if (this.logSlowQueries) {
/*      */         
/* 2236 */         ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */         
/* 2238 */         if (this.queryBadIndexUsed && this.profileSql) {
/* 2239 */           eventSink.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.33") + profileQueryToLog));
/*      */         }
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
/* 2252 */         if (this.queryNoIndexUsed && this.profileSql) {
/* 2253 */           eventSink.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.35") + profileQueryToLog));
/*      */         }
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
/* 2266 */         if (this.serverQueryWasSlow && this.profileSql) {
/* 2267 */           eventSink.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.ServerSlowQuery") + profileQueryToLog));
/*      */         }
/*      */       } 
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
/* 2281 */       if (this.profileSql) {
/* 2282 */         fetchEndTime = getCurrentTimeNanosOrMillis();
/*      */         
/* 2284 */         ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */         
/* 2286 */         eventSink.consumeEvent(new ProfilerEvent((byte)3, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), profileQueryToLog));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2294 */         eventSink.consumeEvent(new ProfilerEvent((byte)5, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), fetchEndTime - fetchBeginTime, this.queryTimingUnits, null, new Throwable(), null));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2303 */       if (this.hadWarnings) {
/* 2304 */         scanForAndThrowDataTruncation();
/*      */       }
/*      */       
/* 2307 */       if (this.statementInterceptors != null) {
/* 2308 */         ResultSetInternalMethods interceptedResults = invokeStatementInterceptorsPost(query, callingStatement, rs, false, null);
/*      */ 
/*      */         
/* 2311 */         if (interceptedResults != null) {
/* 2312 */           rs = interceptedResults;
/*      */         }
/*      */       } 
/*      */       
/* 2316 */       return rs;
/* 2317 */     } catch (SQLException sqlEx) {
/* 2318 */       if (this.statementInterceptors != null) {
/* 2319 */         invokeStatementInterceptorsPost(query, callingStatement, null, false, sqlEx);
/*      */       }
/*      */ 
/*      */       
/* 2323 */       if (callingStatement != null) {
/* 2324 */         synchronized (callingStatement.cancelTimeoutMutex) {
/* 2325 */           if (callingStatement.wasCancelled) {
/* 2326 */             MySQLStatementCancelledException mySQLStatementCancelledException; SQLException cause = null;
/*      */             
/* 2328 */             if (callingStatement.wasCancelledByTimeout) {
/* 2329 */               MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException();
/*      */             } else {
/* 2331 */               mySQLStatementCancelledException = new MySQLStatementCancelledException();
/*      */             } 
/*      */             
/* 2334 */             callingStatement.resetCancelledState();
/*      */             
/* 2336 */             throw mySQLStatementCancelledException;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 2341 */       throw sqlEx;
/*      */     } finally {
/* 2343 */       this.statementExecutionDepth--;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   ResultSetInternalMethods invokeStatementInterceptorsPre(String sql, Statement interceptedStatement, boolean forceExecute) throws SQLException {
/* 2349 */     ResultSetInternalMethods previousResultSet = null;
/*      */     
/* 2351 */     Iterator<StatementInterceptorV2> interceptors = this.statementInterceptors.iterator();
/*      */     
/* 2353 */     while (interceptors.hasNext()) {
/* 2354 */       StatementInterceptorV2 interceptor = interceptors.next();
/*      */ 
/*      */       
/* 2357 */       boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
/* 2358 */       boolean shouldExecute = ((executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly);
/*      */ 
/*      */       
/* 2361 */       if (shouldExecute) {
/* 2362 */         String sqlToInterceptor = sql;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2369 */         ResultSetInternalMethods interceptedResultSet = interceptor.preProcess(sqlToInterceptor, interceptedStatement, this.connection);
/*      */ 
/*      */ 
/*      */         
/* 2373 */         if (interceptedResultSet != null) {
/* 2374 */           previousResultSet = interceptedResultSet;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2379 */     return previousResultSet;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   ResultSetInternalMethods invokeStatementInterceptorsPost(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, boolean forceExecute, SQLException statementException) throws SQLException {
/* 2385 */     Iterator<StatementInterceptorV2> interceptors = this.statementInterceptors.iterator();
/*      */     
/* 2387 */     while (interceptors.hasNext()) {
/* 2388 */       StatementInterceptorV2 interceptor = interceptors.next();
/*      */ 
/*      */       
/* 2391 */       boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
/* 2392 */       boolean shouldExecute = ((executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly);
/*      */ 
/*      */       
/* 2395 */       if (shouldExecute) {
/* 2396 */         String sqlToInterceptor = sql;
/*      */         
/* 2398 */         ResultSetInternalMethods interceptedResultSet = interceptor.postProcess(sqlToInterceptor, interceptedStatement, originalResultSet, this.connection, this.warningCount, this.queryNoIndexUsed, this.queryBadIndexUsed, statementException);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2403 */         if (interceptedResultSet != null) {
/* 2404 */           originalResultSet = interceptedResultSet;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2409 */     return originalResultSet;
/*      */   }
/*      */   
/*      */   private void calculateSlowQueryThreshold() {
/* 2413 */     this.slowQueryThreshold = this.connection.getSlowQueryThresholdMillis();
/*      */     
/* 2415 */     if (this.connection.getUseNanosForElapsedTime()) {
/* 2416 */       long nanosThreshold = this.connection.getSlowQueryThresholdNanos();
/*      */       
/* 2418 */       if (nanosThreshold != 0L) {
/* 2419 */         this.slowQueryThreshold = nanosThreshold;
/*      */       } else {
/* 2421 */         this.slowQueryThreshold *= 1000000L;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected long getCurrentTimeNanosOrMillis() {
/* 2427 */     if (this.useNanosForElapsedTime) {
/* 2428 */       return Util.getCurrentTimeNanosOrMillis();
/*      */     }
/*      */     
/* 2431 */     return System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getHost() {
/* 2440 */     return this.host;
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
/*      */   boolean isVersion(int major, int minor, int subminor) {
/* 2455 */     return (major == getServerMajorVersion() && minor == getServerMinorVersion() && subminor == getServerSubMinorVersion());
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
/*      */   boolean versionMeetsMinimum(int major, int minor, int subminor) {
/* 2471 */     if (getServerMajorVersion() >= major) {
/* 2472 */       if (getServerMajorVersion() == major) {
/* 2473 */         if (getServerMinorVersion() >= minor) {
/* 2474 */           if (getServerMinorVersion() == minor) {
/* 2475 */             return (getServerSubMinorVersion() >= subminor);
/*      */           }
/*      */ 
/*      */           
/* 2479 */           return true;
/*      */         } 
/*      */ 
/*      */         
/* 2483 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 2487 */       return true;
/*      */     } 
/*      */     
/* 2490 */     return false;
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
/*      */   private static final String getPacketDumpToLog(Buffer packetToDump, int packetLength) {
/* 2504 */     if (packetLength < 1024) {
/* 2505 */       return packetToDump.dump(packetLength);
/*      */     }
/*      */     
/* 2508 */     StringBuffer packetDumpBuf = new StringBuffer(4096);
/* 2509 */     packetDumpBuf.append(packetToDump.dump(1024));
/* 2510 */     packetDumpBuf.append(Messages.getString("MysqlIO.36"));
/* 2511 */     packetDumpBuf.append(1024);
/* 2512 */     packetDumpBuf.append(Messages.getString("MysqlIO.37"));
/*      */     
/* 2514 */     return packetDumpBuf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private final int readFully(InputStream in, byte[] b, int off, int len) throws IOException {
/* 2519 */     if (len < 0) {
/* 2520 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/* 2523 */     int n = 0;
/*      */     
/* 2525 */     while (n < len) {
/* 2526 */       int count = in.read(b, off + n, len - n);
/*      */       
/* 2528 */       if (count < 0) {
/* 2529 */         throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { new Integer(len), new Integer(n) }));
/*      */       }
/*      */ 
/*      */       
/* 2533 */       n += count;
/*      */     } 
/*      */     
/* 2536 */     return n;
/*      */   }
/*      */   
/*      */   private final long skipFully(InputStream in, long len) throws IOException {
/* 2540 */     if (len < 0L) {
/* 2541 */       throw new IOException("Negative skip length not allowed");
/*      */     }
/*      */     
/* 2544 */     long n = 0L;
/*      */     
/* 2546 */     while (n < len) {
/* 2547 */       long count = in.skip(len - n);
/*      */       
/* 2549 */       if (count < 0L) {
/* 2550 */         throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { new Long(len), new Long(n) }));
/*      */       }
/*      */ 
/*      */       
/* 2554 */       n += count;
/*      */     } 
/*      */     
/* 2557 */     return n;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ResultSetImpl readResultsForQueryOrUpdate(StatementImpl callingStatement, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Buffer resultPacket, boolean isBinaryEncoded, long preSentColumnCount, Field[] metadataFromCache) throws SQLException {
/* 2585 */     long columnCount = resultPacket.readFieldLength();
/*      */     
/* 2587 */     if (columnCount == 0L)
/* 2588 */       return buildResultSetWithUpdates(callingStatement, resultPacket); 
/* 2589 */     if (columnCount == -1L) {
/* 2590 */       String charEncoding = null;
/*      */       
/* 2592 */       if (this.connection.getUseUnicode()) {
/* 2593 */         charEncoding = this.connection.getEncoding();
/*      */       }
/*      */       
/* 2596 */       String fileName = null;
/*      */       
/* 2598 */       if (this.platformDbCharsetMatches) {
/* 2599 */         fileName = (charEncoding != null) ? resultPacket.readString(charEncoding, getExceptionInterceptor()) : resultPacket.readString();
/*      */       }
/*      */       else {
/*      */         
/* 2603 */         fileName = resultPacket.readString();
/*      */       } 
/*      */       
/* 2606 */       return sendFileToServer(callingStatement, fileName);
/*      */     } 
/* 2608 */     ResultSetImpl results = getResultSet(callingStatement, columnCount, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, isBinaryEncoded, metadataFromCache);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2613 */     return results;
/*      */   }
/*      */ 
/*      */   
/*      */   private int alignPacketSize(int a, int l) {
/* 2618 */     return a + l - 1 & (l - 1 ^ 0xFFFFFFFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResultSetImpl buildResultSetWithRows(StatementImpl callingStatement, String catalog, Field[] fields, RowData rows, int resultSetType, int resultSetConcurrency, boolean isBinaryEncoded) throws SQLException {
/* 2626 */     ResultSetImpl rs = null;
/*      */     
/* 2628 */     switch (resultSetConcurrency) {
/*      */       case 1007:
/* 2630 */         rs = ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, false);
/*      */ 
/*      */         
/* 2633 */         if (isBinaryEncoded) {
/* 2634 */           rs.setBinaryEncoded();
/*      */         }
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
/* 2650 */         rs.setResultSetType(resultSetType);
/* 2651 */         rs.setResultSetConcurrency(resultSetConcurrency);
/*      */         
/* 2653 */         return rs;case 1008: rs = ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, true); rs.setResultSetType(resultSetType); rs.setResultSetConcurrency(resultSetConcurrency); return rs;
/*      */     } 
/*      */     return ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, false);
/*      */   }
/*      */   
/*      */   private ResultSetImpl buildResultSetWithUpdates(StatementImpl callingStatement, Buffer resultPacket) throws SQLException {
/* 2659 */     long updateCount = -1L;
/* 2660 */     long updateID = -1L;
/* 2661 */     String info = null;
/*      */     
/*      */     try {
/* 2664 */       if (this.useNewUpdateCounts) {
/* 2665 */         updateCount = resultPacket.newReadLength();
/* 2666 */         updateID = resultPacket.newReadLength();
/*      */       } else {
/* 2668 */         updateCount = resultPacket.readLength();
/* 2669 */         updateID = resultPacket.readLength();
/*      */       } 
/*      */       
/* 2672 */       if (this.use41Extensions) {
/*      */         
/* 2674 */         this.serverStatus = resultPacket.readInt();
/*      */         
/* 2676 */         checkTransactionState(this.oldServerStatus);
/*      */         
/* 2678 */         this.warningCount = resultPacket.readInt();
/*      */         
/* 2680 */         if (this.warningCount > 0) {
/* 2681 */           this.hadWarnings = true;
/*      */         }
/*      */         
/* 2684 */         resultPacket.readByte();
/*      */         
/* 2686 */         setServerSlowQueryFlags();
/*      */       } 
/*      */       
/* 2689 */       if (this.connection.isReadInfoMsgEnabled()) {
/* 2690 */         info = resultPacket.readString(this.connection.getErrorMessageEncoding(), getExceptionInterceptor());
/*      */       }
/* 2692 */     } catch (Exception ex) {
/* 2693 */       SQLException sqlEx = SQLError.createSQLException(SQLError.get("S1000"), "S1000", -1, getExceptionInterceptor());
/*      */       
/* 2695 */       sqlEx.initCause(ex);
/*      */       
/* 2697 */       throw sqlEx;
/*      */     } 
/*      */     
/* 2700 */     ResultSetInternalMethods updateRs = ResultSetImpl.getInstance(updateCount, updateID, this.connection, callingStatement);
/*      */ 
/*      */     
/* 2703 */     if (info != null) {
/* 2704 */       ((ResultSetImpl)updateRs).setServerInfo(info);
/*      */     }
/*      */     
/* 2707 */     return (ResultSetImpl)updateRs;
/*      */   }
/*      */   
/*      */   private void setServerSlowQueryFlags() {
/* 2711 */     this.queryBadIndexUsed = ((this.serverStatus & 0x10) != 0);
/*      */     
/* 2713 */     this.queryNoIndexUsed = ((this.serverStatus & 0x20) != 0);
/*      */     
/* 2715 */     this.serverQueryWasSlow = ((this.serverStatus & 0x800) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkForOutstandingStreamingData() throws SQLException {
/* 2720 */     if (this.streamingData != null) {
/* 2721 */       boolean shouldClobber = this.connection.getClobberStreamingResults();
/*      */       
/* 2723 */       if (!shouldClobber) {
/* 2724 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.39") + this.streamingData + Messages.getString("MysqlIO.40") + Messages.getString("MysqlIO.41") + Messages.getString("MysqlIO.42"), getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2732 */       this.streamingData.getOwner().realClose(false);
/*      */ 
/*      */       
/* 2735 */       clearInputStream();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Buffer compressPacket(Buffer packet, int offset, int packetLen, int headerLength) throws SQLException {
/* 2741 */     packet.writeLongInt(packetLen - headerLength);
/* 2742 */     packet.writeByte((byte)0);
/*      */     
/* 2744 */     int lengthToWrite = 0;
/* 2745 */     int compressedLength = 0;
/* 2746 */     byte[] bytesToCompress = packet.getByteBuffer();
/* 2747 */     byte[] compressedBytes = null;
/* 2748 */     int offsetWrite = 0;
/*      */     
/* 2750 */     if (packetLen < 50) {
/* 2751 */       lengthToWrite = packetLen;
/* 2752 */       compressedBytes = packet.getByteBuffer();
/* 2753 */       compressedLength = 0;
/* 2754 */       offsetWrite = offset;
/*      */     } else {
/* 2756 */       compressedBytes = new byte[bytesToCompress.length * 2];
/*      */       
/* 2758 */       this.deflater.reset();
/* 2759 */       this.deflater.setInput(bytesToCompress, offset, packetLen);
/* 2760 */       this.deflater.finish();
/*      */       
/* 2762 */       int compLen = this.deflater.deflate(compressedBytes);
/*      */       
/* 2764 */       if (compLen > packetLen) {
/* 2765 */         lengthToWrite = packetLen;
/* 2766 */         compressedBytes = packet.getByteBuffer();
/* 2767 */         compressedLength = 0;
/* 2768 */         offsetWrite = offset;
/*      */       } else {
/* 2770 */         lengthToWrite = compLen;
/* 2771 */         headerLength += 3;
/* 2772 */         compressedLength = packetLen;
/*      */       } 
/*      */     } 
/*      */     
/* 2776 */     Buffer compressedPacket = new Buffer(packetLen + headerLength);
/*      */     
/* 2778 */     compressedPacket.setPosition(0);
/* 2779 */     compressedPacket.writeLongInt(lengthToWrite);
/* 2780 */     compressedPacket.writeByte(this.packetSequence);
/* 2781 */     compressedPacket.writeLongInt(compressedLength);
/* 2782 */     compressedPacket.writeBytesNoNull(compressedBytes, offsetWrite, lengthToWrite);
/*      */ 
/*      */     
/* 2785 */     return compressedPacket;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void readServerStatusForResultSets(Buffer rowPacket) throws SQLException {
/* 2790 */     if (this.use41Extensions) {
/* 2791 */       rowPacket.readByte();
/*      */       
/* 2793 */       this.warningCount = rowPacket.readInt();
/*      */       
/* 2795 */       if (this.warningCount > 0) {
/* 2796 */         this.hadWarnings = true;
/*      */       }
/*      */       
/* 2799 */       this.oldServerStatus = this.serverStatus;
/* 2800 */       this.serverStatus = rowPacket.readInt();
/* 2801 */       checkTransactionState(this.oldServerStatus);
/*      */       
/* 2803 */       setServerSlowQueryFlags();
/*      */     } 
/*      */   }
/*      */   
/*      */   private SocketFactory createSocketFactory() throws SQLException {
/*      */     try {
/* 2809 */       if (this.socketFactoryClassName == null) {
/* 2810 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.75"), "08001", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */       
/* 2814 */       return (SocketFactory)Class.forName(this.socketFactoryClassName).newInstance();
/*      */     }
/* 2816 */     catch (Exception ex) {
/* 2817 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("MysqlIO.76") + this.socketFactoryClassName + Messages.getString("MysqlIO.77"), "08001", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2822 */       sqlEx.initCause(ex);
/*      */       
/* 2824 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueuePacketForDebugging(boolean isPacketBeingSent, boolean isPacketReused, int sendLength, byte[] header, Buffer packet) throws SQLException {
/* 2831 */     if (this.packetDebugRingBuffer.size() + 1 > this.connection.getPacketDebugBufferSize()) {
/* 2832 */       this.packetDebugRingBuffer.removeFirst();
/*      */     }
/*      */     
/* 2835 */     StringBuffer packetDump = null;
/*      */     
/* 2837 */     if (!isPacketBeingSent) {
/* 2838 */       int bytesToDump = Math.min(1024, packet.getBufLength());
/*      */ 
/*      */       
/* 2841 */       Buffer packetToDump = new Buffer(4 + bytesToDump);
/*      */       
/* 2843 */       packetToDump.setPosition(0);
/* 2844 */       packetToDump.writeBytesNoNull(header);
/* 2845 */       packetToDump.writeBytesNoNull(packet.getBytes(0, bytesToDump));
/*      */       
/* 2847 */       String packetPayload = packetToDump.dump(bytesToDump);
/*      */       
/* 2849 */       packetDump = new StringBuffer(96 + packetPayload.length());
/*      */       
/* 2851 */       packetDump.append("Server ");
/*      */       
/* 2853 */       if (isPacketReused) {
/* 2854 */         packetDump.append("(re-used)");
/*      */       } else {
/* 2856 */         packetDump.append("(new)");
/*      */       } 
/*      */       
/* 2859 */       packetDump.append(" ");
/* 2860 */       packetDump.append(packet.toSuperString());
/* 2861 */       packetDump.append(" --------------------> Client\n");
/* 2862 */       packetDump.append("\nPacket payload:\n\n");
/* 2863 */       packetDump.append(packetPayload);
/*      */       
/* 2865 */       if (bytesToDump == 1024) {
/* 2866 */         packetDump.append("\nNote: Packet of " + packet.getBufLength() + " bytes truncated to " + 'Ѐ' + " bytes.\n");
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2871 */       int bytesToDump = Math.min(1024, sendLength);
/*      */       
/* 2873 */       String packetPayload = packet.dump(bytesToDump);
/*      */       
/* 2875 */       packetDump = new StringBuffer(68 + packetPayload.length());
/*      */       
/* 2877 */       packetDump.append("Client ");
/* 2878 */       packetDump.append(packet.toSuperString());
/* 2879 */       packetDump.append("--------------------> Server\n");
/* 2880 */       packetDump.append("\nPacket payload:\n\n");
/* 2881 */       packetDump.append(packetPayload);
/*      */       
/* 2883 */       if (bytesToDump == 1024) {
/* 2884 */         packetDump.append("\nNote: Packet of " + sendLength + " bytes truncated to " + 'Ѐ' + " bytes.\n");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2890 */     this.packetDebugRingBuffer.addLast(packetDump);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RowData readSingleRowSet(long columnCount, int maxRows, int resultSetConcurrency, boolean isBinaryEncoded, Field[] fields) throws SQLException {
/* 2897 */     ArrayList<ResultSetRow> rows = new ArrayList();
/*      */     
/* 2899 */     boolean useBufferRowExplicit = useBufferRowExplicit(fields);
/*      */ 
/*      */     
/* 2902 */     ResultSetRow row = nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency, false, useBufferRowExplicit, false, null);
/*      */ 
/*      */     
/* 2905 */     int rowCount = 0;
/*      */     
/* 2907 */     if (row != null) {
/* 2908 */       rows.add(row);
/* 2909 */       rowCount = 1;
/*      */     } 
/*      */     
/* 2912 */     while (row != null) {
/* 2913 */       row = nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency, false, useBufferRowExplicit, false, null);
/*      */ 
/*      */       
/* 2916 */       if (row != null && (
/* 2917 */         maxRows == -1 || rowCount < maxRows)) {
/* 2918 */         rows.add(row);
/* 2919 */         rowCount++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2924 */     RowData rowData = new RowDataStatic(rows);
/*      */     
/* 2926 */     return rowData;
/*      */   }
/*      */   
/*      */   public static boolean useBufferRowExplicit(Field[] fields) {
/* 2930 */     if (fields == null) {
/* 2931 */       return false;
/*      */     }
/*      */     
/* 2934 */     for (int i = 0; i < fields.length; i++) {
/* 2935 */       switch (fields[i].getSQLType()) {
/*      */         case -4:
/*      */         case -1:
/*      */         case 2004:
/*      */         case 2005:
/* 2940 */           return true;
/*      */       } 
/*      */     
/*      */     } 
/* 2944 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reclaimLargeReusablePacket() {
/* 2951 */     if (this.reusablePacket != null && this.reusablePacket.getCapacity() > 1048576)
/*      */     {
/* 2953 */       this.reusablePacket = new Buffer(1024);
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
/*      */   private final Buffer reuseAndReadPacket(Buffer reuse) throws SQLException {
/* 2968 */     return reuseAndReadPacket(reuse, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final Buffer reuseAndReadPacket(Buffer reuse, int existingPacketLength) throws SQLException {
/*      */     try {
/* 2975 */       reuse.setWasMultiPacket(false);
/* 2976 */       int packetLength = 0;
/*      */       
/* 2978 */       if (existingPacketLength == -1) {
/* 2979 */         int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */         
/* 2982 */         if (lengthRead < 4) {
/* 2983 */           forceClose();
/* 2984 */           throw new IOException(Messages.getString("MysqlIO.43"));
/*      */         } 
/*      */         
/* 2987 */         packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */       }
/*      */       else {
/*      */         
/* 2991 */         packetLength = existingPacketLength;
/*      */       } 
/*      */       
/* 2994 */       if (this.traceProtocol) {
/* 2995 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/* 2997 */         traceMessageBuf.append(Messages.getString("MysqlIO.44"));
/* 2998 */         traceMessageBuf.append(packetLength);
/* 2999 */         traceMessageBuf.append(Messages.getString("MysqlIO.45"));
/* 3000 */         traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
/*      */ 
/*      */         
/* 3003 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/* 3006 */       byte multiPacketSeq = this.packetHeaderBuf[3];
/*      */       
/* 3008 */       if (!this.packetSequenceReset) {
/* 3009 */         if (this.enablePacketDebug && this.checkPacketSequence) {
/* 3010 */           checkPacketSequencing(multiPacketSeq);
/*      */         }
/*      */       } else {
/* 3013 */         this.packetSequenceReset = false;
/*      */       } 
/*      */       
/* 3016 */       this.readPacketSequence = multiPacketSeq;
/*      */ 
/*      */       
/* 3019 */       reuse.setPosition(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3027 */       if ((reuse.getByteBuffer()).length <= packetLength) {
/* 3028 */         reuse.setByteBuffer(new byte[packetLength + 1]);
/*      */       }
/*      */ 
/*      */       
/* 3032 */       reuse.setBufLength(packetLength);
/*      */ 
/*      */       
/* 3035 */       int numBytesRead = readFully(this.mysqlInput, reuse.getByteBuffer(), 0, packetLength);
/*      */ 
/*      */       
/* 3038 */       if (numBytesRead != packetLength) {
/* 3039 */         throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);
/*      */       }
/*      */ 
/*      */       
/* 3043 */       if (this.traceProtocol) {
/* 3044 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/* 3046 */         traceMessageBuf.append(Messages.getString("MysqlIO.46"));
/* 3047 */         traceMessageBuf.append(getPacketDumpToLog(reuse, packetLength));
/*      */ 
/*      */         
/* 3050 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/* 3053 */       if (this.enablePacketDebug) {
/* 3054 */         enqueuePacketForDebugging(false, true, 0, this.packetHeaderBuf, reuse);
/*      */       }
/*      */ 
/*      */       
/* 3058 */       boolean isMultiPacket = false;
/*      */       
/* 3060 */       if (packetLength == this.maxThreeBytes) {
/* 3061 */         reuse.setPosition(this.maxThreeBytes);
/*      */         
/* 3063 */         int packetEndPoint = packetLength;
/*      */ 
/*      */         
/* 3066 */         isMultiPacket = true;
/*      */         
/* 3068 */         packetLength = readRemainingMultiPackets(reuse, multiPacketSeq, packetEndPoint);
/*      */       } 
/*      */ 
/*      */       
/* 3072 */       if (!isMultiPacket) {
/* 3073 */         reuse.getByteBuffer()[packetLength] = 0;
/*      */       }
/*      */       
/* 3076 */       if (this.connection.getMaintainTimeStats()) {
/* 3077 */         this.lastPacketReceivedTimeMs = System.currentTimeMillis();
/*      */       }
/*      */       
/* 3080 */       return reuse;
/* 3081 */     } catch (IOException ioEx) {
/* 3082 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     }
/* 3084 */     catch (OutOfMemoryError oom) {
/*      */       
/*      */       try {
/* 3087 */         clearInputStream();
/*      */       } finally {
/*      */         try {
/* 3090 */           this.connection.realClose(false, false, true, oom);
/*      */         } finally {
/* 3092 */           throw oom;
/*      */         } 
/*      */       } 
/*      */       while (true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readRemainingMultiPackets(Buffer reuse, byte multiPacketSeq, int packetEndPoint) throws IOException, SQLException {
/* 3103 */     int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */     
/* 3106 */     if (lengthRead < 4) {
/* 3107 */       forceClose();
/* 3108 */       throw new IOException(Messages.getString("MysqlIO.47"));
/*      */     } 
/*      */     
/* 3111 */     int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */ 
/*      */ 
/*      */     
/* 3115 */     Buffer multiPacket = new Buffer(packetLength);
/* 3116 */     boolean firstMultiPkt = true;
/*      */     
/*      */     while (true) {
/* 3119 */       if (!firstMultiPkt) {
/* 3120 */         lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */         
/* 3123 */         if (lengthRead < 4) {
/* 3124 */           forceClose();
/* 3125 */           throw new IOException(Messages.getString("MysqlIO.48"));
/*      */         } 
/*      */ 
/*      */         
/* 3129 */         packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */       }
/*      */       else {
/*      */         
/* 3133 */         firstMultiPkt = false;
/*      */       } 
/*      */       
/* 3136 */       if (!this.useNewLargePackets && packetLength == 1) {
/* 3137 */         clearInputStream();
/*      */         break;
/*      */       } 
/* 3140 */       if (packetLength < this.maxThreeBytes) {
/* 3141 */         byte b = this.packetHeaderBuf[3];
/*      */         
/* 3143 */         if (b != multiPacketSeq + 1) {
/* 3144 */           throw new IOException(Messages.getString("MysqlIO.49"));
/*      */         }
/*      */ 
/*      */         
/* 3148 */         multiPacketSeq = b;
/*      */ 
/*      */         
/* 3151 */         multiPacket.setPosition(0);
/*      */ 
/*      */         
/* 3154 */         multiPacket.setBufLength(packetLength);
/*      */ 
/*      */         
/* 3157 */         byte[] arrayOfByte = multiPacket.getByteBuffer();
/* 3158 */         int i = packetLength;
/*      */         
/* 3160 */         int j = readFully(this.mysqlInput, arrayOfByte, 0, packetLength);
/*      */ 
/*      */         
/* 3163 */         if (j != i) {
/* 3164 */           throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.50") + i + Messages.getString("MysqlIO.51") + j + ".", getExceptionInterceptor()), getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3174 */         reuse.writeBytesNoNull(arrayOfByte, 0, i);
/*      */         
/* 3176 */         packetEndPoint += i;
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 3181 */       byte newPacketSeq = this.packetHeaderBuf[3];
/*      */       
/* 3183 */       if (newPacketSeq != multiPacketSeq + 1) {
/* 3184 */         throw new IOException(Messages.getString("MysqlIO.53"));
/*      */       }
/*      */ 
/*      */       
/* 3188 */       multiPacketSeq = newPacketSeq;
/*      */ 
/*      */       
/* 3191 */       multiPacket.setPosition(0);
/*      */ 
/*      */       
/* 3194 */       multiPacket.setBufLength(packetLength);
/*      */ 
/*      */       
/* 3197 */       byte[] byteBuf = multiPacket.getByteBuffer();
/* 3198 */       int lengthToWrite = packetLength;
/*      */       
/* 3200 */       int bytesRead = readFully(this.mysqlInput, byteBuf, 0, packetLength);
/*      */ 
/*      */       
/* 3203 */       if (bytesRead != lengthToWrite) {
/* 3204 */         throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.54") + lengthToWrite + Messages.getString("MysqlIO.55") + bytesRead + ".", getExceptionInterceptor()), getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3213 */       reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
/*      */       
/* 3215 */       packetEndPoint += lengthToWrite;
/*      */     } 
/*      */     
/* 3218 */     reuse.setPosition(0);
/* 3219 */     reuse.setWasMultiPacket(true);
/* 3220 */     return packetLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPacketSequencing(byte multiPacketSeq) throws SQLException {
/* 3229 */     if (multiPacketSeq == Byte.MIN_VALUE && this.readPacketSequence != Byte.MAX_VALUE) {
/* 3230 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # -128, but received packet # " + multiPacketSeq), getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3236 */     if (this.readPacketSequence == -1 && multiPacketSeq != 0) {
/* 3237 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # -1, but received packet # " + multiPacketSeq), getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3243 */     if (multiPacketSeq != Byte.MIN_VALUE && this.readPacketSequence != -1 && multiPacketSeq != this.readPacketSequence + 1)
/*      */     {
/* 3245 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # " + (this.readPacketSequence + 1) + ", but received packet # " + multiPacketSeq), getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void enableMultiQueries() throws SQLException {
/* 3254 */     Buffer buf = getSharedSendPacket();
/*      */     
/* 3256 */     buf.clear();
/* 3257 */     buf.writeByte((byte)27);
/* 3258 */     buf.writeInt(0);
/* 3259 */     sendCommand(27, null, buf, false, null, 0);
/*      */   }
/*      */   
/*      */   void disableMultiQueries() throws SQLException {
/* 3263 */     Buffer buf = getSharedSendPacket();
/*      */     
/* 3265 */     buf.clear();
/* 3266 */     buf.writeByte((byte)27);
/* 3267 */     buf.writeInt(1);
/* 3268 */     sendCommand(27, null, buf, false, null, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void send(Buffer packet, int packetLen) throws SQLException {
/*      */     try {
/* 3274 */       if (this.maxAllowedPacket > 0 && packetLen > this.maxAllowedPacket) {
/* 3275 */         throw new PacketTooBigException(packetLen, this.maxAllowedPacket);
/*      */       }
/*      */       
/* 3278 */       if (this.serverMajorVersion >= 4 && packetLen >= this.maxThreeBytes) {
/*      */         
/* 3280 */         sendSplitPackets(packet);
/*      */       } else {
/* 3282 */         this.packetSequence = (byte)(this.packetSequence + 1);
/*      */         
/* 3284 */         Buffer packetToSend = packet;
/*      */         
/* 3286 */         packetToSend.setPosition(0);
/*      */         
/* 3288 */         if (this.useCompression) {
/* 3289 */           int originalPacketLen = packetLen;
/*      */           
/* 3291 */           packetToSend = compressPacket(packet, 0, packetLen, 4);
/*      */           
/* 3293 */           packetLen = packetToSend.getPosition();
/*      */           
/* 3295 */           if (this.traceProtocol) {
/* 3296 */             StringBuffer traceMessageBuf = new StringBuffer();
/*      */             
/* 3298 */             traceMessageBuf.append(Messages.getString("MysqlIO.57"));
/* 3299 */             traceMessageBuf.append(getPacketDumpToLog(packetToSend, packetLen));
/*      */             
/* 3301 */             traceMessageBuf.append(Messages.getString("MysqlIO.58"));
/* 3302 */             traceMessageBuf.append(getPacketDumpToLog(packet, originalPacketLen));
/*      */ 
/*      */             
/* 3305 */             this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */           } 
/*      */         } else {
/* 3308 */           packetToSend.writeLongInt(packetLen - 4);
/* 3309 */           packetToSend.writeByte(this.packetSequence);
/*      */           
/* 3311 */           if (this.traceProtocol) {
/* 3312 */             StringBuffer traceMessageBuf = new StringBuffer();
/*      */             
/* 3314 */             traceMessageBuf.append(Messages.getString("MysqlIO.59"));
/* 3315 */             traceMessageBuf.append("host: '");
/* 3316 */             traceMessageBuf.append(this.host);
/* 3317 */             traceMessageBuf.append("' threadId: '");
/* 3318 */             traceMessageBuf.append(this.threadId);
/* 3319 */             traceMessageBuf.append("'\n");
/* 3320 */             traceMessageBuf.append(packetToSend.dump(packetLen));
/*      */             
/* 3322 */             this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3327 */         this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
/*      */         
/* 3329 */         this.mysqlOutput.flush();
/*      */       } 
/*      */       
/* 3332 */       if (this.enablePacketDebug) {
/* 3333 */         enqueuePacketForDebugging(true, false, packetLen + 5, this.packetHeaderBuf, packet);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3340 */       if (packet == this.sharedSendPacket) {
/* 3341 */         reclaimLargeSharedSendPacket();
/*      */       }
/*      */       
/* 3344 */       if (this.connection.getMaintainTimeStats()) {
/* 3345 */         this.lastPacketSentTimeMs = System.currentTimeMillis();
/*      */       }
/* 3347 */     } catch (IOException ioEx) {
/* 3348 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
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
/*      */   private final ResultSetImpl sendFileToServer(StatementImpl callingStatement, String fileName) throws SQLException {
/* 3366 */     Buffer filePacket = (this.loadFileBufRef == null) ? null : this.loadFileBufRef.get();
/*      */ 
/*      */     
/* 3369 */     int bigPacketLength = Math.min(this.connection.getMaxAllowedPacket() - 12, alignPacketSize(this.connection.getMaxAllowedPacket() - 16, 4096) - 12);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3374 */     int oneMeg = 1048576;
/*      */     
/* 3376 */     int smallerPacketSizeAligned = Math.min(oneMeg - 12, alignPacketSize(oneMeg - 16, 4096) - 12);
/*      */ 
/*      */     
/* 3379 */     int packetLength = Math.min(smallerPacketSizeAligned, bigPacketLength);
/*      */     
/* 3381 */     if (filePacket == null) {
/*      */       try {
/* 3383 */         filePacket = new Buffer(packetLength + 4);
/* 3384 */         this.loadFileBufRef = new SoftReference<Buffer>(filePacket);
/* 3385 */       } catch (OutOfMemoryError oom) {
/* 3386 */         throw SQLError.createSQLException("Could not allocate packet of " + packetLength + " bytes required for LOAD DATA LOCAL INFILE operation." + " Try increasing max heap allocation for JVM or decreasing server variable " + "'max_allowed_packet'", "S1001", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3394 */     filePacket.clear();
/* 3395 */     send(filePacket, 0);
/*      */     
/* 3397 */     byte[] fileBuf = new byte[packetLength];
/*      */     
/* 3399 */     BufferedInputStream fileIn = null;
/*      */     
/*      */     try {
/* 3402 */       if (!this.connection.getAllowLoadLocalInfile()) {
/* 3403 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.LoadDataLocalNotAllowed"), "S1000", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3408 */       InputStream hookedStream = null;
/*      */       
/* 3410 */       if (callingStatement != null) {
/* 3411 */         hookedStream = callingStatement.getLocalInfileInputStream();
/*      */       }
/*      */       
/* 3414 */       if (hookedStream != null) {
/* 3415 */         fileIn = new BufferedInputStream(hookedStream);
/* 3416 */       } else if (!this.connection.getAllowUrlInLocalInfile()) {
/* 3417 */         fileIn = new BufferedInputStream(new FileInputStream(fileName));
/*      */       
/*      */       }
/* 3420 */       else if (fileName.indexOf(':') != -1) {
/*      */         try {
/* 3422 */           URL urlFromFileName = new URL(fileName);
/* 3423 */           fileIn = new BufferedInputStream(urlFromFileName.openStream());
/* 3424 */         } catch (MalformedURLException badUrlEx) {
/*      */           
/* 3426 */           fileIn = new BufferedInputStream(new FileInputStream(fileName));
/*      */         } 
/*      */       } else {
/*      */         
/* 3430 */         fileIn = new BufferedInputStream(new FileInputStream(fileName));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3435 */       int bytesRead = 0;
/*      */       
/* 3437 */       while ((bytesRead = fileIn.read(fileBuf)) != -1) {
/* 3438 */         filePacket.clear();
/* 3439 */         filePacket.writeBytesNoNull(fileBuf, 0, bytesRead);
/* 3440 */         send(filePacket, filePacket.getPosition());
/*      */       } 
/* 3442 */     } catch (IOException ioEx) {
/* 3443 */       StringBuffer messageBuf = new StringBuffer(Messages.getString("MysqlIO.60"));
/*      */ 
/*      */       
/* 3446 */       if (!this.connection.getParanoid()) {
/* 3447 */         messageBuf.append("'");
/*      */         
/* 3449 */         if (fileName != null) {
/* 3450 */           messageBuf.append(fileName);
/*      */         }
/*      */         
/* 3453 */         messageBuf.append("'");
/*      */       } 
/*      */       
/* 3456 */       messageBuf.append(Messages.getString("MysqlIO.63"));
/*      */       
/* 3458 */       if (!this.connection.getParanoid()) {
/* 3459 */         messageBuf.append(Messages.getString("MysqlIO.64"));
/* 3460 */         messageBuf.append(Util.stackTraceToString(ioEx));
/*      */       } 
/*      */       
/* 3463 */       throw SQLError.createSQLException(messageBuf.toString(), "S1009", getExceptionInterceptor());
/*      */     } finally {
/*      */       
/* 3466 */       if (fileIn != null) {
/*      */         try {
/* 3468 */           fileIn.close();
/* 3469 */         } catch (Exception ex) {
/* 3470 */           SQLException sqlEx = SQLError.createSQLException(Messages.getString("MysqlIO.65"), "S1000", getExceptionInterceptor());
/*      */           
/* 3472 */           sqlEx.initCause(ex);
/*      */           
/* 3474 */           throw sqlEx;
/*      */         } 
/*      */         
/* 3477 */         fileIn = null;
/*      */       } else {
/*      */         
/* 3480 */         filePacket.clear();
/* 3481 */         send(filePacket, filePacket.getPosition());
/* 3482 */         checkErrorPacket();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3487 */     filePacket.clear();
/* 3488 */     send(filePacket, filePacket.getPosition());
/*      */     
/* 3490 */     Buffer resultPacket = checkErrorPacket();
/*      */     
/* 3492 */     return buildResultSetWithUpdates(callingStatement, resultPacket);
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
/*      */   private Buffer checkErrorPacket(int command) throws SQLException {
/* 3507 */     int statusCode = 0;
/* 3508 */     Buffer resultPacket = null;
/* 3509 */     this.serverStatus = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3516 */       resultPacket = reuseAndReadPacket(this.reusablePacket);
/* 3517 */     } catch (SQLException sqlEx) {
/*      */       
/* 3519 */       throw sqlEx;
/* 3520 */     } catch (Exception fallThru) {
/* 3521 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, fallThru, getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */     
/* 3525 */     checkErrorPacket(resultPacket);
/*      */     
/* 3527 */     return resultPacket;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkErrorPacket(Buffer resultPacket) throws SQLException {
/* 3532 */     int statusCode = resultPacket.readByte();
/*      */ 
/*      */     
/* 3535 */     if (statusCode == -1) {
/*      */       
/* 3537 */       int errno = 2000;
/*      */       
/* 3539 */       if (this.protocolVersion > 9) {
/* 3540 */         errno = resultPacket.readInt();
/*      */         
/* 3542 */         String xOpen = null;
/*      */         
/* 3544 */         String str1 = resultPacket.readString(this.connection.getErrorMessageEncoding(), getExceptionInterceptor());
/*      */ 
/*      */         
/* 3547 */         if (str1.charAt(0) == '#') {
/*      */ 
/*      */           
/* 3550 */           if (str1.length() > 6) {
/* 3551 */             xOpen = str1.substring(1, 6);
/* 3552 */             str1 = str1.substring(6);
/*      */             
/* 3554 */             if (xOpen.equals("HY000")) {
/* 3555 */               xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */             }
/*      */           } else {
/*      */             
/* 3559 */             xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */           } 
/*      */         } else {
/*      */           
/* 3563 */           xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */         } 
/*      */ 
/*      */         
/* 3567 */         clearInputStream();
/*      */         
/* 3569 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/* 3571 */         String xOpenErrorMessage = SQLError.get(xOpen);
/*      */         
/* 3573 */         if (!this.connection.getUseOnlyServerErrorMessages() && 
/* 3574 */           xOpenErrorMessage != null) {
/* 3575 */           stringBuffer.append(xOpenErrorMessage);
/* 3576 */           stringBuffer.append(Messages.getString("MysqlIO.68"));
/*      */         } 
/*      */ 
/*      */         
/* 3580 */         stringBuffer.append(str1);
/*      */         
/* 3582 */         if (!this.connection.getUseOnlyServerErrorMessages() && 
/* 3583 */           xOpenErrorMessage != null) {
/* 3584 */           stringBuffer.append("\"");
/*      */         }
/*      */ 
/*      */         
/* 3588 */         appendInnodbStatusInformation(xOpen, stringBuffer);
/*      */         
/* 3590 */         if (xOpen != null && xOpen.startsWith("22")) {
/* 3591 */           throw new MysqlDataTruncation(stringBuffer.toString(), 0, true, false, 0, 0, errno);
/*      */         }
/* 3593 */         throw SQLError.createSQLException(stringBuffer.toString(), xOpen, errno, false, getExceptionInterceptor(), this.connection);
/*      */       } 
/*      */ 
/*      */       
/* 3597 */       String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding(), getExceptionInterceptor());
/*      */       
/* 3599 */       clearInputStream();
/*      */       
/* 3601 */       if (serverErrorMessage.indexOf(Messages.getString("MysqlIO.70")) != -1) {
/* 3602 */         throw SQLError.createSQLException(SQLError.get("S0022") + ", " + serverErrorMessage, "S0022", -1, false, getExceptionInterceptor(), this.connection);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3609 */       StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.72"));
/*      */       
/* 3611 */       errorBuf.append(serverErrorMessage);
/* 3612 */       errorBuf.append("\"");
/*      */       
/* 3614 */       throw SQLError.createSQLException(SQLError.get("S1000") + ", " + errorBuf.toString(), "S1000", -1, false, getExceptionInterceptor(), this.connection);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendInnodbStatusInformation(String xOpen, StringBuffer errorBuf) throws SQLException {
/* 3622 */     if (this.connection.getIncludeInnodbStatusInDeadlockExceptions() && xOpen != null && (xOpen.startsWith("40") || xOpen.startsWith("41")) && this.streamingData == null) {
/*      */ 
/*      */ 
/*      */       
/* 3626 */       ResultSet rs = null;
/*      */       
/*      */       try {
/* 3629 */         rs = sqlQueryDirect(null, "SHOW ENGINE INNODB STATUS", this.connection.getEncoding(), null, -1, 1003, 1007, false, this.connection.getCatalog(), null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3635 */         if (rs.next()) {
/* 3636 */           errorBuf.append("\n\n");
/* 3637 */           errorBuf.append(rs.getString("Status"));
/*      */         } else {
/* 3639 */           errorBuf.append("\n\n");
/* 3640 */           errorBuf.append(Messages.getString("MysqlIO.NoInnoDBStatusFound"));
/*      */         }
/*      */       
/* 3643 */       } catch (Exception ex) {
/* 3644 */         errorBuf.append("\n\n");
/* 3645 */         errorBuf.append(Messages.getString("MysqlIO.InnoDBStatusFailed"));
/*      */         
/* 3647 */         errorBuf.append("\n\n");
/* 3648 */         errorBuf.append(Util.stackTraceToString(ex));
/*      */       } finally {
/* 3650 */         if (rs != null) {
/* 3651 */           rs.close();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void sendSplitPackets(Buffer packet) throws SQLException {
/*      */     try {
/* 3677 */       Buffer headerPacket = (this.splitBufRef == null) ? null : this.splitBufRef.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3685 */       if (headerPacket == null) {
/* 3686 */         headerPacket = new Buffer(this.maxThreeBytes + 4);
/*      */         
/* 3688 */         this.splitBufRef = new SoftReference<Buffer>(headerPacket);
/*      */       } 
/*      */       
/* 3691 */       int len = packet.getPosition();
/* 3692 */       int splitSize = this.maxThreeBytes;
/* 3693 */       int originalPacketPos = 4;
/* 3694 */       byte[] origPacketBytes = packet.getByteBuffer();
/* 3695 */       byte[] headerPacketBytes = headerPacket.getByteBuffer();
/*      */       
/* 3697 */       while (len >= this.maxThreeBytes) {
/* 3698 */         this.packetSequence = (byte)(this.packetSequence + 1);
/*      */         
/* 3700 */         headerPacket.setPosition(0);
/* 3701 */         headerPacket.writeLongInt(splitSize);
/*      */         
/* 3703 */         headerPacket.writeByte(this.packetSequence);
/* 3704 */         System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, splitSize);
/*      */ 
/*      */         
/* 3707 */         int i = splitSize + 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3713 */         if (!this.useCompression) {
/* 3714 */           this.mysqlOutput.write(headerPacketBytes, 0, splitSize + 4);
/*      */           
/* 3716 */           this.mysqlOutput.flush();
/*      */         }
/*      */         else {
/*      */           
/* 3720 */           headerPacket.setPosition(0);
/* 3721 */           Buffer packetToSend = compressPacket(headerPacket, 4, splitSize, 4);
/*      */           
/* 3723 */           i = packetToSend.getPosition();
/*      */           
/* 3725 */           this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, i);
/*      */           
/* 3727 */           this.mysqlOutput.flush();
/*      */         } 
/*      */         
/* 3730 */         originalPacketPos += splitSize;
/* 3731 */         len -= splitSize;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3737 */       headerPacket.clear();
/* 3738 */       headerPacket.setPosition(0);
/* 3739 */       headerPacket.writeLongInt(len - 4);
/* 3740 */       this.packetSequence = (byte)(this.packetSequence + 1);
/* 3741 */       headerPacket.writeByte(this.packetSequence);
/*      */       
/* 3743 */       if (len != 0) {
/* 3744 */         System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, len - 4);
/*      */       }
/*      */ 
/*      */       
/* 3748 */       int packetLen = len - 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3754 */       if (!this.useCompression) {
/* 3755 */         this.mysqlOutput.write(headerPacket.getByteBuffer(), 0, len);
/* 3756 */         this.mysqlOutput.flush();
/*      */       }
/*      */       else {
/*      */         
/* 3760 */         headerPacket.setPosition(0);
/* 3761 */         Buffer packetToSend = compressPacket(headerPacket, 4, packetLen, 4);
/*      */         
/* 3763 */         packetLen = packetToSend.getPosition();
/*      */         
/* 3765 */         this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
/*      */         
/* 3767 */         this.mysqlOutput.flush();
/*      */       } 
/* 3769 */     } catch (IOException ioEx) {
/* 3770 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void reclaimLargeSharedSendPacket() {
/* 3776 */     if (this.sharedSendPacket != null && this.sharedSendPacket.getCapacity() > 1048576)
/*      */     {
/* 3778 */       this.sharedSendPacket = new Buffer(1024);
/*      */     }
/*      */   }
/*      */   
/*      */   boolean hadWarnings() {
/* 3783 */     return this.hadWarnings;
/*      */   }
/*      */   
/*      */   void scanForAndThrowDataTruncation() throws SQLException {
/* 3787 */     if (this.streamingData == null && versionMeetsMinimum(4, 1, 0) && this.connection.getJdbcCompliantTruncation() && this.warningCount > 0)
/*      */     {
/* 3789 */       SQLError.convertShowWarningsToSQLWarnings(this.connection, this.warningCount, true);
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
/*      */ 
/*      */   
/*      */   private void secureAuth(Buffer packet, int packLength, String user, String password, String database, boolean writeClientParams) throws SQLException {
/* 3810 */     if (packet == null) {
/* 3811 */       packet = new Buffer(packLength);
/*      */     }
/*      */     
/* 3814 */     if (writeClientParams) {
/* 3815 */       if (this.use41Extensions) {
/* 3816 */         if (versionMeetsMinimum(4, 1, 1)) {
/* 3817 */           packet.writeLong(this.clientParam);
/* 3818 */           packet.writeLong(this.maxThreeBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3823 */           packet.writeByte((byte)8);
/*      */ 
/*      */           
/* 3826 */           packet.writeBytesNoNull(new byte[23]);
/*      */         } else {
/* 3828 */           packet.writeLong(this.clientParam);
/* 3829 */           packet.writeLong(this.maxThreeBytes);
/*      */         } 
/*      */       } else {
/* 3832 */         packet.writeInt((int)this.clientParam);
/* 3833 */         packet.writeLongInt(this.maxThreeBytes);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3838 */     packet.writeString(user, "Cp1252", this.connection);
/*      */     
/* 3840 */     if (password.length() != 0) {
/*      */       
/* 3842 */       packet.writeString("xxxxxxxx", "Cp1252", this.connection);
/*      */     } else {
/*      */       
/* 3845 */       packet.writeString("", "Cp1252", this.connection);
/*      */     } 
/*      */     
/* 3848 */     if (this.useConnectWithDb) {
/* 3849 */       packet.writeString(database, "Cp1252", this.connection);
/*      */     }
/*      */     
/* 3852 */     send(packet, packet.getPosition());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3857 */     if (password.length() > 0) {
/* 3858 */       Buffer b = readPacket();
/*      */       
/* 3860 */       b.setPosition(0);
/*      */       
/* 3862 */       byte[] replyAsBytes = b.getByteBuffer();
/*      */       
/* 3864 */       if (replyAsBytes.length == 25 && replyAsBytes[0] != 0)
/*      */       {
/* 3866 */         if (replyAsBytes[0] != 42) {
/*      */           
/*      */           try {
/* 3869 */             byte[] buff = Security.passwordHashStage1(password);
/*      */ 
/*      */             
/* 3872 */             byte[] passwordHash = new byte[buff.length];
/* 3873 */             System.arraycopy(buff, 0, passwordHash, 0, buff.length);
/*      */ 
/*      */             
/* 3876 */             passwordHash = Security.passwordHashStage2(passwordHash, replyAsBytes);
/*      */ 
/*      */             
/* 3879 */             byte[] packetDataAfterSalt = new byte[replyAsBytes.length - 5];
/*      */ 
/*      */             
/* 3882 */             System.arraycopy(replyAsBytes, 4, packetDataAfterSalt, 0, replyAsBytes.length - 5);
/*      */ 
/*      */             
/* 3885 */             byte[] mysqlScrambleBuff = new byte[20];
/*      */ 
/*      */             
/* 3888 */             Security.passwordCrypt(packetDataAfterSalt, mysqlScrambleBuff, passwordHash, 20);
/*      */ 
/*      */ 
/*      */             
/* 3892 */             Security.passwordCrypt(mysqlScrambleBuff, buff, buff, 20);
/*      */             
/* 3894 */             Buffer packet2 = new Buffer(25);
/* 3895 */             packet2.writeBytesNoNull(buff);
/*      */             
/* 3897 */             this.packetSequence = (byte)(this.packetSequence + 1);
/*      */             
/* 3899 */             send(packet2, 24);
/* 3900 */           } catch (NoSuchAlgorithmException nse) {
/* 3901 */             throw SQLError.createSQLException(Messages.getString("MysqlIO.91") + Messages.getString("MysqlIO.92"), "S1000", getExceptionInterceptor());
/*      */           } 
/*      */         } else {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/* 3908 */             byte[] passwordHash = Security.createKeyFromOldPassword(password);
/*      */ 
/*      */             
/* 3911 */             byte[] netReadPos4 = new byte[replyAsBytes.length - 5];
/*      */             
/* 3913 */             System.arraycopy(replyAsBytes, 4, netReadPos4, 0, replyAsBytes.length - 5);
/*      */ 
/*      */             
/* 3916 */             byte[] mysqlScrambleBuff = new byte[20];
/*      */ 
/*      */             
/* 3919 */             Security.passwordCrypt(netReadPos4, mysqlScrambleBuff, passwordHash, 20);
/*      */ 
/*      */ 
/*      */             
/* 3923 */             String scrambledPassword = Util.scramble(new String(mysqlScrambleBuff), password);
/*      */ 
/*      */             
/* 3926 */             Buffer packet2 = new Buffer(packLength);
/* 3927 */             packet2.writeString(scrambledPassword, "Cp1252", this.connection);
/* 3928 */             this.packetSequence = (byte)(this.packetSequence + 1);
/*      */             
/* 3930 */             send(packet2, 24);
/* 3931 */           } catch (NoSuchAlgorithmException nse) {
/* 3932 */             throw SQLError.createSQLException(Messages.getString("MysqlIO.93") + Messages.getString("MysqlIO.94"), "S1000", getExceptionInterceptor());
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
/*      */   void secureAuth411(Buffer packet, int packLength, String user, String password, String database, boolean writeClientParams) throws SQLException {
/* 3974 */     if (packet == null) {
/* 3975 */       packet = new Buffer(packLength);
/*      */     }
/*      */     
/* 3978 */     if (writeClientParams) {
/* 3979 */       if (this.use41Extensions) {
/* 3980 */         if (versionMeetsMinimum(4, 1, 1)) {
/* 3981 */           packet.writeLong(this.clientParam);
/* 3982 */           packet.writeLong(this.maxThreeBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3987 */           packet.writeByte((byte)33);
/*      */ 
/*      */           
/* 3990 */           packet.writeBytesNoNull(new byte[23]);
/*      */         } else {
/* 3992 */           packet.writeLong(this.clientParam);
/* 3993 */           packet.writeLong(this.maxThreeBytes);
/*      */         } 
/*      */       } else {
/* 3996 */         packet.writeInt((int)this.clientParam);
/* 3997 */         packet.writeLongInt(this.maxThreeBytes);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 4002 */     packet.writeString(user, "utf-8", this.connection);
/*      */     
/* 4004 */     if (password.length() != 0) {
/* 4005 */       packet.writeByte((byte)20);
/*      */       
/*      */       try {
/* 4008 */         packet.writeBytesNoNull(Security.scramble411(password, this.seed, this.connection));
/* 4009 */       } catch (NoSuchAlgorithmException nse) {
/* 4010 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000", getExceptionInterceptor());
/*      */       
/*      */       }
/* 4013 */       catch (UnsupportedEncodingException e) {
/* 4014 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000", getExceptionInterceptor());
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4020 */       packet.writeByte((byte)0);
/*      */     } 
/*      */     
/* 4023 */     if (this.useConnectWithDb) {
/* 4024 */       packet.writeString(database, "utf-8", this.connection);
/*      */     }
/*      */     
/* 4027 */     send(packet, packet.getPosition());
/*      */     
/* 4029 */     byte savePacketSequence = this.packetSequence = (byte)(this.packetSequence + 1);
/*      */     
/* 4031 */     Buffer reply = checkErrorPacket();
/*      */     
/* 4033 */     if (reply.isLastDataPacket()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4038 */       this.packetSequence = savePacketSequence = (byte)(savePacketSequence + 1);
/* 4039 */       packet.clear();
/*      */       
/* 4041 */       String seed323 = this.seed.substring(0, 8);
/* 4042 */       packet.writeString(Util.newCrypt(password, seed323));
/* 4043 */       send(packet, packet.getPosition());
/*      */ 
/*      */       
/* 4046 */       checkErrorPacket();
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
/*      */   private final ResultSetRow unpackBinaryResultSetRow(Field[] fields, Buffer binaryData, int resultSetConcurrency) throws SQLException {
/* 4063 */     int numFields = fields.length;
/*      */     
/* 4065 */     byte[][] unpackedRowData = new byte[numFields][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4072 */     int nullCount = (numFields + 9) / 8;
/*      */     
/* 4074 */     byte[] nullBitMask = new byte[nullCount];
/*      */     
/* 4076 */     for (int i = 0; i < nullCount; i++) {
/* 4077 */       nullBitMask[i] = binaryData.readByte();
/*      */     }
/*      */     
/* 4080 */     int nullMaskPos = 0;
/* 4081 */     int bit = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4088 */     for (int j = 0; j < numFields; j++) {
/* 4089 */       if ((nullBitMask[nullMaskPos] & bit) != 0) {
/* 4090 */         unpackedRowData[j] = null;
/*      */       }
/* 4092 */       else if (resultSetConcurrency != 1008) {
/* 4093 */         extractNativeEncodedColumn(binaryData, fields, j, unpackedRowData);
/*      */       } else {
/*      */         
/* 4096 */         unpackNativeEncodedColumn(binaryData, fields, j, unpackedRowData);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4101 */       if (((bit <<= 1) & 0xFF) == 0) {
/* 4102 */         bit = 1;
/*      */         
/* 4104 */         nullMaskPos++;
/*      */       } 
/*      */     } 
/*      */     
/* 4108 */     return new ByteArrayRow(unpackedRowData, getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */   
/*      */   private final void extractNativeEncodedColumn(Buffer binaryData, Field[] fields, int columnIndex, byte[][] unpackedRowData) throws SQLException {
/*      */     int length;
/* 4114 */     Field curField = fields[columnIndex];
/*      */     
/* 4116 */     switch (curField.getMysqlType()) {
/*      */       case 6:
/*      */         return;
/*      */ 
/*      */       
/*      */       case 1:
/* 4122 */         (new byte[1])[0] = binaryData.readByte(); unpackedRowData[columnIndex] = new byte[1];
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 13:
/* 4128 */         unpackedRowData[columnIndex] = binaryData.getBytes(2);
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 9:
/* 4133 */         unpackedRowData[columnIndex] = binaryData.getBytes(4);
/*      */ 
/*      */       
/*      */       case 8:
/* 4137 */         unpackedRowData[columnIndex] = binaryData.getBytes(8);
/*      */ 
/*      */       
/*      */       case 4:
/* 4141 */         unpackedRowData[columnIndex] = binaryData.getBytes(4);
/*      */ 
/*      */       
/*      */       case 5:
/* 4145 */         unpackedRowData[columnIndex] = binaryData.getBytes(8);
/*      */ 
/*      */       
/*      */       case 11:
/* 4149 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4151 */         unpackedRowData[columnIndex] = binaryData.getBytes(length);
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 4156 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4158 */         unpackedRowData[columnIndex] = binaryData.getBytes(length);
/*      */ 
/*      */       
/*      */       case 7:
/*      */       case 12:
/* 4163 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4165 */         unpackedRowData[columnIndex] = binaryData.getBytes(length);
/*      */       
/*      */       case 0:
/*      */       case 15:
/*      */       case 246:
/*      */       case 249:
/*      */       case 250:
/*      */       case 251:
/*      */       case 252:
/*      */       case 253:
/*      */       case 254:
/*      */       case 255:
/* 4177 */         unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
/*      */ 
/*      */       
/*      */       case 16:
/* 4181 */         unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
/*      */     } 
/*      */ 
/*      */     
/* 4185 */     throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000", getExceptionInterceptor()); } private final void unpackNativeEncodedColumn(Buffer binaryData, Field[] fields, int columnIndex, byte[][] unpackedRowData) throws SQLException { byte tinyVal; short shortVal; int intVal;
/*      */     long longVal;
/*      */     float floatVal;
/*      */     double doubleVal;
/*      */     int length, hour, minute, seconds;
/*      */     byte[] timeAsBytes;
/*      */     int year, month, day;
/*      */     byte[] arrayOfByte1;
/*      */     int i, j, nanos, k;
/*      */     byte[] arrayOfByte2, arrayOfByte3;
/*      */     byte b;
/*      */     boolean bool;
/* 4197 */     Field curField = fields[columnIndex];
/*      */     
/* 4199 */     switch (curField.getMysqlType()) {
/*      */       case 6:
/*      */         return;
/*      */ 
/*      */       
/*      */       case 1:
/* 4205 */         tinyVal = binaryData.readByte();
/*      */         
/* 4207 */         if (!curField.isUnsigned()) {
/* 4208 */           unpackedRowData[columnIndex] = String.valueOf(tinyVal).getBytes();
/*      */         } else {
/*      */           
/* 4211 */           short unsignedTinyVal = (short)(tinyVal & 0xFF);
/*      */           
/* 4213 */           unpackedRowData[columnIndex] = String.valueOf(unsignedTinyVal).getBytes();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 13:
/* 4222 */         shortVal = (short)binaryData.readInt();
/*      */         
/* 4224 */         if (!curField.isUnsigned()) {
/* 4225 */           unpackedRowData[columnIndex] = String.valueOf(shortVal).getBytes();
/*      */         } else {
/*      */           
/* 4228 */           int unsignedShortVal = shortVal & 0xFFFF;
/*      */           
/* 4230 */           unpackedRowData[columnIndex] = String.valueOf(unsignedShortVal).getBytes();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 9:
/* 4239 */         intVal = (int)binaryData.readLong();
/*      */         
/* 4241 */         if (!curField.isUnsigned()) {
/* 4242 */           unpackedRowData[columnIndex] = String.valueOf(intVal).getBytes();
/*      */         } else {
/*      */           
/* 4245 */           long l = intVal & 0xFFFFFFFFL;
/*      */           
/* 4247 */           unpackedRowData[columnIndex] = String.valueOf(l).getBytes();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 4255 */         longVal = binaryData.readLongLong();
/*      */         
/* 4257 */         if (!curField.isUnsigned()) {
/* 4258 */           unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
/*      */         } else {
/*      */           
/* 4261 */           BigInteger asBigInteger = ResultSetImpl.convertLongToUlong(longVal);
/*      */           
/* 4263 */           unpackedRowData[columnIndex] = asBigInteger.toString().getBytes();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 4271 */         floatVal = Float.intBitsToFloat(binaryData.readIntAsLong());
/*      */         
/* 4273 */         unpackedRowData[columnIndex] = String.valueOf(floatVal).getBytes();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 4279 */         doubleVal = Double.longBitsToDouble(binaryData.readLongLong());
/*      */         
/* 4281 */         unpackedRowData[columnIndex] = String.valueOf(doubleVal).getBytes();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/* 4287 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4289 */         hour = 0;
/* 4290 */         minute = 0;
/* 4291 */         seconds = 0;
/*      */         
/* 4293 */         if (length != 0) {
/* 4294 */           binaryData.readByte();
/* 4295 */           binaryData.readLong();
/* 4296 */           hour = binaryData.readByte();
/* 4297 */           minute = binaryData.readByte();
/* 4298 */           seconds = binaryData.readByte();
/*      */           
/* 4300 */           if (length > 8) {
/* 4301 */             binaryData.readLong();
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 4306 */         timeAsBytes = new byte[8];
/*      */         
/* 4308 */         timeAsBytes[0] = (byte)Character.forDigit(hour / 10, 10);
/* 4309 */         timeAsBytes[1] = (byte)Character.forDigit(hour % 10, 10);
/*      */         
/* 4311 */         timeAsBytes[2] = 58;
/*      */         
/* 4313 */         timeAsBytes[3] = (byte)Character.forDigit(minute / 10, 10);
/*      */         
/* 4315 */         timeAsBytes[4] = (byte)Character.forDigit(minute % 10, 10);
/*      */ 
/*      */         
/* 4318 */         timeAsBytes[5] = 58;
/*      */         
/* 4320 */         timeAsBytes[6] = (byte)Character.forDigit(seconds / 10, 10);
/*      */         
/* 4322 */         timeAsBytes[7] = (byte)Character.forDigit(seconds % 10, 10);
/*      */ 
/*      */         
/* 4325 */         unpackedRowData[columnIndex] = timeAsBytes;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 4331 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4333 */         year = 0;
/* 4334 */         month = 0;
/* 4335 */         day = 0;
/*      */         
/* 4337 */         hour = 0;
/* 4338 */         minute = 0;
/* 4339 */         seconds = 0;
/*      */         
/* 4341 */         if (length != 0) {
/* 4342 */           year = binaryData.readInt();
/* 4343 */           month = binaryData.readByte();
/* 4344 */           day = binaryData.readByte();
/*      */         } 
/*      */         
/* 4347 */         if (year == 0 && month == 0 && day == 0)
/* 4348 */           if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior()))
/*      */           
/* 4350 */           { unpackedRowData[columnIndex] = null; }
/*      */           else
/*      */           
/* 4353 */           { if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */             {
/* 4355 */               throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009", getExceptionInterceptor());
/*      */             }
/*      */ 
/*      */             
/* 4359 */             year = 1;
/* 4360 */             month = 1;
/* 4361 */             day = 1;
/*      */ 
/*      */ 
/*      */             
/* 4365 */             byte[] dateAsBytes = new byte[10];
/*      */             
/* 4367 */             dateAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
/*      */ 
/*      */             
/* 4370 */             int after1000 = year % 1000;
/*      */             
/* 4372 */             dateAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
/*      */ 
/*      */             
/* 4375 */             int after100 = after1000 % 100;
/*      */             
/* 4377 */             dateAsBytes[2] = (byte)Character.forDigit(after100 / 10, 10);
/*      */             
/* 4379 */             dateAsBytes[3] = (byte)Character.forDigit(after100 % 10, 10);
/*      */ 
/*      */             
/* 4382 */             dateAsBytes[4] = 45;
/*      */             
/* 4384 */             dateAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
/*      */             
/* 4386 */             dateAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
/*      */ 
/*      */             
/* 4389 */             dateAsBytes[7] = 45;
/*      */             
/* 4391 */             dateAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
/* 4392 */             dateAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
/*      */             
/* 4394 */             unpackedRowData[columnIndex] = dateAsBytes; }   arrayOfByte1 = new byte[10]; arrayOfByte1[0] = (byte)Character.forDigit(year / 1000, 10); i = year % 1000; arrayOfByte1[1] = (byte)Character.forDigit(i / 100, 10); j = i % 100; arrayOfByte1[2] = (byte)Character.forDigit(j / 10, 10); arrayOfByte1[3] = (byte)Character.forDigit(j % 10, 10); arrayOfByte1[4] = 45; arrayOfByte1[5] = (byte)Character.forDigit(month / 10, 10); arrayOfByte1[6] = (byte)Character.forDigit(month % 10, 10); arrayOfByte1[7] = 45; arrayOfByte1[8] = (byte)Character.forDigit(day / 10, 10); arrayOfByte1[9] = (byte)Character.forDigit(day % 10, 10); unpackedRowData[columnIndex] = arrayOfByte1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/*      */       case 12:
/* 4401 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4403 */         year = 0;
/* 4404 */         month = 0;
/* 4405 */         day = 0;
/*      */         
/* 4407 */         hour = 0;
/* 4408 */         minute = 0;
/* 4409 */         seconds = 0;
/*      */         
/* 4411 */         nanos = 0;
/*      */         
/* 4413 */         if (length != 0) {
/* 4414 */           year = binaryData.readInt();
/* 4415 */           month = binaryData.readByte();
/* 4416 */           day = binaryData.readByte();
/*      */           
/* 4418 */           if (length > 4) {
/* 4419 */             hour = binaryData.readByte();
/* 4420 */             minute = binaryData.readByte();
/* 4421 */             seconds = binaryData.readByte();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4429 */         if (year == 0 && month == 0 && day == 0)
/* 4430 */           if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior()))
/*      */           
/* 4432 */           { unpackedRowData[columnIndex] = null; }
/*      */           else
/*      */           
/* 4435 */           { if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */             {
/* 4437 */               throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009", getExceptionInterceptor());
/*      */             }
/*      */ 
/*      */             
/* 4441 */             year = 1;
/* 4442 */             month = 1;
/* 4443 */             day = 1;
/*      */ 
/*      */ 
/*      */             
/* 4447 */             int stringLength = 19;
/*      */             
/* 4449 */             byte[] nanosAsBytes = Integer.toString(nanos).getBytes();
/*      */             
/* 4451 */             stringLength += 1 + nanosAsBytes.length;
/*      */             
/* 4453 */             byte[] datetimeAsBytes = new byte[stringLength];
/*      */             
/* 4455 */             datetimeAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
/*      */ 
/*      */             
/* 4458 */             i = year % 1000;
/*      */             
/* 4460 */             datetimeAsBytes[1] = (byte)Character.forDigit(i / 100, 10);
/*      */ 
/*      */             
/* 4463 */             j = i % 100;
/*      */             
/* 4465 */             datetimeAsBytes[2] = (byte)Character.forDigit(j / 10, 10);
/*      */             
/* 4467 */             datetimeAsBytes[3] = (byte)Character.forDigit(j % 10, 10);
/*      */ 
/*      */             
/* 4470 */             datetimeAsBytes[4] = 45;
/*      */             
/* 4472 */             datetimeAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
/*      */             
/* 4474 */             datetimeAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
/*      */ 
/*      */             
/* 4477 */             datetimeAsBytes[7] = 45;
/*      */             
/* 4479 */             datetimeAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
/*      */             
/* 4481 */             datetimeAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
/*      */ 
/*      */             
/* 4484 */             datetimeAsBytes[10] = 32;
/*      */             
/* 4486 */             datetimeAsBytes[11] = (byte)Character.forDigit(hour / 10, 10);
/*      */             
/* 4488 */             datetimeAsBytes[12] = (byte)Character.forDigit(hour % 10, 10);
/*      */ 
/*      */             
/* 4491 */             datetimeAsBytes[13] = 58;
/*      */             
/* 4493 */             datetimeAsBytes[14] = (byte)Character.forDigit(minute / 10, 10);
/*      */             
/* 4495 */             datetimeAsBytes[15] = (byte)Character.forDigit(minute % 10, 10);
/*      */ 
/*      */             
/* 4498 */             datetimeAsBytes[16] = 58;
/*      */             
/* 4500 */             datetimeAsBytes[17] = (byte)Character.forDigit(seconds / 10, 10);
/*      */             
/* 4502 */             datetimeAsBytes[18] = (byte)Character.forDigit(seconds % 10, 10);
/*      */ 
/*      */             
/* 4505 */             datetimeAsBytes[19] = 46;
/*      */             
/* 4507 */             int nanosOffset = 20;
/*      */             
/* 4509 */             int m = 0; }   k = 19; arrayOfByte2 = Integer.toString(nanos).getBytes(); k += 1 + arrayOfByte2.length; arrayOfByte3 = new byte[k]; arrayOfByte3[0] = (byte)Character.forDigit(year / 1000, 10); i = year % 1000; arrayOfByte3[1] = (byte)Character.forDigit(i / 100, 10); j = i % 100; arrayOfByte3[2] = (byte)Character.forDigit(j / 10, 10); arrayOfByte3[3] = (byte)Character.forDigit(j % 10, 10); arrayOfByte3[4] = 45; arrayOfByte3[5] = (byte)Character.forDigit(month / 10, 10); arrayOfByte3[6] = (byte)Character.forDigit(month % 10, 10); arrayOfByte3[7] = 45; arrayOfByte3[8] = (byte)Character.forDigit(day / 10, 10); arrayOfByte3[9] = (byte)Character.forDigit(day % 10, 10); arrayOfByte3[10] = 32; arrayOfByte3[11] = (byte)Character.forDigit(hour / 10, 10); arrayOfByte3[12] = (byte)Character.forDigit(hour % 10, 10); arrayOfByte3[13] = 58; arrayOfByte3[14] = (byte)Character.forDigit(minute / 10, 10); arrayOfByte3[15] = (byte)Character.forDigit(minute % 10, 10); arrayOfByte3[16] = 58; arrayOfByte3[17] = (byte)Character.forDigit(seconds / 10, 10); arrayOfByte3[18] = (byte)Character.forDigit(seconds % 10, 10); arrayOfByte3[19] = 46; b = 20; bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*      */       case 15:
/*      */       case 16:
/*      */       case 246:
/*      */       case 249:
/*      */       case 250:
/*      */       case 251:
/*      */       case 252:
/*      */       case 253:
/*      */       case 254:
/* 4528 */         unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4533 */     throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000", getExceptionInterceptor()); }
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
/*      */   private void negotiateSSLConnection(String user, String password, String database, int packLength) throws SQLException {
/* 4556 */     if (!ExportControlled.enabled()) {
/* 4557 */       throw new ConnectionFeatureNotAvailableException(this.connection, this.lastPacketSentTimeMs, null);
/*      */     }
/*      */ 
/*      */     
/* 4561 */     boolean doSecureAuth = false;
/*      */     
/* 4563 */     if ((this.serverCapabilities & 0x8000) != 0) {
/* 4564 */       this.clientParam |= 0x8000L;
/* 4565 */       doSecureAuth = true;
/*      */     } 
/*      */     
/* 4568 */     this.clientParam |= 0x800L;
/*      */     
/* 4570 */     Buffer packet = new Buffer(packLength);
/*      */     
/* 4572 */     if (this.use41Extensions) {
/* 4573 */       packet.writeLong(this.clientParam);
/*      */     } else {
/* 4575 */       packet.writeInt((int)this.clientParam);
/*      */     } 
/*      */     
/* 4578 */     send(packet, packet.getPosition());
/*      */     
/* 4580 */     ExportControlled.transformSocketToSSLSocket(this);
/*      */     
/* 4582 */     packet.clear();
/*      */     
/* 4584 */     if (doSecureAuth) {
/* 4585 */       if (versionMeetsMinimum(4, 1, 1)) {
/* 4586 */         secureAuth411(null, packLength, user, password, database, true);
/*      */       } else {
/* 4588 */         secureAuth411(null, packLength, user, password, database, true);
/*      */       } 
/*      */     } else {
/* 4591 */       if (this.use41Extensions) {
/* 4592 */         packet.writeLong(this.clientParam);
/* 4593 */         packet.writeLong(this.maxThreeBytes);
/*      */       } else {
/* 4595 */         packet.writeInt((int)this.clientParam);
/* 4596 */         packet.writeLongInt(this.maxThreeBytes);
/*      */       } 
/*      */ 
/*      */       
/* 4600 */       packet.writeString(user);
/*      */       
/* 4602 */       if (this.protocolVersion > 9) {
/* 4603 */         packet.writeString(Util.newCrypt(password, this.seed));
/*      */       } else {
/* 4605 */         packet.writeString(Util.oldCrypt(password, this.seed));
/*      */       } 
/*      */       
/* 4608 */       if ((this.serverCapabilities & 0x8) != 0 && database != null && database.length() > 0)
/*      */       {
/* 4610 */         packet.writeString(database);
/*      */       }
/*      */       
/* 4613 */       send(packet, packet.getPosition());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected int getServerStatus() {
/* 4618 */     return this.serverStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List fetchRowsViaCursor(List<ResultSetRow> fetchedRows, long statementId, Field[] columnTypes, int fetchSize, boolean useBufferRowExplicit) throws SQLException {
/* 4624 */     if (fetchedRows == null) {
/* 4625 */       fetchedRows = new ArrayList(fetchSize);
/*      */     } else {
/* 4627 */       fetchedRows.clear();
/*      */     } 
/*      */     
/* 4630 */     this.sharedSendPacket.clear();
/*      */     
/* 4632 */     this.sharedSendPacket.writeByte((byte)28);
/* 4633 */     this.sharedSendPacket.writeLong(statementId);
/* 4634 */     this.sharedSendPacket.writeLong(fetchSize);
/*      */     
/* 4636 */     sendCommand(28, null, this.sharedSendPacket, true, null, 0);
/*      */ 
/*      */     
/* 4639 */     ResultSetRow row = null;
/*      */ 
/*      */     
/* 4642 */     while ((row = nextRow(columnTypes, columnTypes.length, true, 1007, false, useBufferRowExplicit, false, null)) != null) {
/* 4643 */       fetchedRows.add(row);
/*      */     }
/*      */     
/* 4646 */     return fetchedRows;
/*      */   }
/*      */   
/*      */   protected long getThreadId() {
/* 4650 */     return this.threadId;
/*      */   }
/*      */   
/*      */   protected boolean useNanosForElapsedTime() {
/* 4654 */     return this.useNanosForElapsedTime;
/*      */   }
/*      */   
/*      */   protected long getSlowQueryThreshold() {
/* 4658 */     return this.slowQueryThreshold;
/*      */   }
/*      */   
/*      */   protected String getQueryTimingUnits() {
/* 4662 */     return this.queryTimingUnits;
/*      */   }
/*      */   
/*      */   protected int getCommandCount() {
/* 4666 */     return this.commandCount;
/*      */   }
/*      */   
/*      */   private void checkTransactionState(int oldStatus) throws SQLException {
/* 4670 */     boolean previouslyInTrans = ((oldStatus & 0x1) != 0);
/* 4671 */     boolean currentlyInTrans = ((this.serverStatus & 0x1) != 0);
/*      */     
/* 4673 */     if (previouslyInTrans && !currentlyInTrans) {
/* 4674 */       this.connection.transactionCompleted();
/* 4675 */     } else if (!previouslyInTrans && currentlyInTrans) {
/* 4676 */       this.connection.transactionBegun();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void setStatementInterceptors(List statementInterceptors) {
/* 4681 */     this.statementInterceptors = statementInterceptors;
/*      */   }
/*      */   
/*      */   protected ExceptionInterceptor getExceptionInterceptor() {
/* 4685 */     return this.exceptionInterceptor;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\MysqlIO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */