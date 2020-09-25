/*      */ package org.apache.logging.log4j.spi;
/*      */ 
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.logging.log4j.Marker;
/*      */ import org.apache.logging.log4j.MarkerManager;
/*      */ import org.apache.logging.log4j.message.Message;
/*      */ import org.apache.logging.log4j.message.MessageFactory;
/*      */ import org.apache.logging.log4j.message.ParameterizedMessageFactory;
/*      */ import org.apache.logging.log4j.message.StringFormattedMessage;
/*      */ import org.apache.logging.log4j.status.StatusLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractLogger
/*      */   implements Logger
/*      */ {
/*   38 */   public static final Marker FLOW_MARKER = MarkerManager.getMarker("FLOW");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   43 */   public static final Marker ENTRY_MARKER = MarkerManager.getMarker("ENTRY", FLOW_MARKER);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   48 */   public static final Marker EXIT_MARKER = MarkerManager.getMarker("EXIT", FLOW_MARKER);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   54 */   public static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   public static final Marker THROWING_MARKER = MarkerManager.getMarker("THROWING", EXCEPTION_MARKER);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public static final Marker CATCHING_MARKER = MarkerManager.getMarker("CATCHING", EXCEPTION_MARKER);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final Class<? extends MessageFactory> DEFAULT_MESSAGE_FACTORY_CLASS = (Class)ParameterizedMessageFactory.class;
/*      */ 
/*      */   
/*   72 */   private static final String FQCN = AbstractLogger.class.getName();
/*      */ 
/*      */   
/*      */   private static final String THROWING = "throwing";
/*      */ 
/*      */   
/*      */   private static final String CATCHING = "catching";
/*      */   
/*      */   private final String name;
/*      */   
/*      */   private final MessageFactory messageFactory;
/*      */ 
/*      */   
/*      */   public AbstractLogger() {
/*   86 */     this.name = getClass().getName();
/*   87 */     this.messageFactory = createDefaultMessageFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractLogger(String name) {
/*   96 */     this.name = name;
/*   97 */     this.messageFactory = createDefaultMessageFactory();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractLogger(String name, MessageFactory messageFactory) {
/*  107 */     this.name = name;
/*  108 */     this.messageFactory = (messageFactory == null) ? createDefaultMessageFactory() : messageFactory;
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
/*      */   public static void checkMessageFactory(Logger logger, MessageFactory messageFactory) {
/*  122 */     String name = logger.getName();
/*  123 */     MessageFactory loggerMessageFactory = logger.getMessageFactory();
/*  124 */     if (messageFactory != null && !loggerMessageFactory.equals(messageFactory)) {
/*  125 */       StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with the message factory {}, which may create log events with unexpected formatting.", new Object[] { name, loggerMessageFactory, messageFactory });
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  130 */     else if (messageFactory == null && !loggerMessageFactory.getClass().equals(DEFAULT_MESSAGE_FACTORY_CLASS)) {
/*      */       
/*  132 */       StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with a null message factory (defaults to {}), which may create log events with unexpected formatting.", new Object[] { name, loggerMessageFactory, DEFAULT_MESSAGE_FACTORY_CLASS.getName() });
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
/*      */   public void catching(Level level, Throwable t) {
/*  148 */     catching(FQCN, level, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void catching(Throwable t) {
/*  158 */     catching(FQCN, Level.ERROR, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void catching(String fqcn, Level level, Throwable t) {
/*  169 */     if (isEnabled(level, CATCHING_MARKER, (Object)null, (Throwable)null)) {
/*  170 */       log(CATCHING_MARKER, fqcn, level, this.messageFactory.newMessage("catching"), t);
/*      */     }
/*      */   }
/*      */   
/*      */   private MessageFactory createDefaultMessageFactory() {
/*      */     try {
/*  176 */       return DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
/*  177 */     } catch (InstantiationException e) {
/*  178 */       throw new IllegalStateException(e);
/*  179 */     } catch (IllegalAccessException e) {
/*  180 */       throw new IllegalStateException(e);
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
/*      */   public void debug(Marker marker, Message msg) {
/*  192 */     if (isEnabled(Level.DEBUG, marker, msg, (Throwable)null)) {
/*  193 */       log(marker, FQCN, Level.DEBUG, msg, null);
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
/*      */   public void debug(Marker marker, Message msg, Throwable t) {
/*  206 */     if (isEnabled(Level.DEBUG, marker, msg, t)) {
/*  207 */       log(marker, FQCN, Level.DEBUG, msg, t);
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
/*      */   public void debug(Marker marker, Object message) {
/*  219 */     if (isEnabled(Level.DEBUG, marker, message, (Throwable)null)) {
/*  220 */       log(marker, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), null);
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
/*      */   public void debug(Marker marker, Object message, Throwable t) {
/*  234 */     if (isEnabled(Level.DEBUG, marker, message, t)) {
/*  235 */       log(marker, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), t);
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
/*      */   public void debug(Marker marker, String message) {
/*  247 */     if (isEnabled(Level.DEBUG, marker, message)) {
/*  248 */       log(marker, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), null);
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
/*      */   public void debug(Marker marker, String message, Object... params) {
/*  261 */     if (isEnabled(Level.DEBUG, marker, message, params)) {
/*  262 */       Message msg = this.messageFactory.newMessage(message, params);
/*  263 */       log(marker, FQCN, Level.DEBUG, msg, msg.getThrowable());
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
/*      */   public void debug(Marker marker, String message, Throwable t) {
/*  277 */     if (isEnabled(Level.DEBUG, marker, message, t)) {
/*  278 */       log(marker, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void debug(Message msg) {
/*  289 */     if (isEnabled(Level.DEBUG, (Marker)null, msg, (Throwable)null)) {
/*  290 */       log(null, FQCN, Level.DEBUG, msg, null);
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
/*      */   public void debug(Message msg, Throwable t) {
/*  302 */     if (isEnabled(Level.DEBUG, (Marker)null, msg, t)) {
/*  303 */       log(null, FQCN, Level.DEBUG, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void debug(Object message) {
/*  314 */     if (isEnabled(Level.DEBUG, (Marker)null, message, (Throwable)null)) {
/*  315 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), null);
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
/*      */   public void debug(Object message, Throwable t) {
/*  328 */     if (isEnabled(Level.DEBUG, (Marker)null, message, t)) {
/*  329 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void debug(String message) {
/*  340 */     if (isEnabled(Level.DEBUG, null, message)) {
/*  341 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), null);
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
/*      */   public void debug(String message, Object... params) {
/*  353 */     if (isEnabled(Level.DEBUG, (Marker)null, message, params)) {
/*  354 */       Message msg = this.messageFactory.newMessage(message, params);
/*  355 */       log(null, FQCN, Level.DEBUG, msg, msg.getThrowable());
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
/*      */   public void debug(String message, Throwable t) {
/*  368 */     if (isEnabled(Level.DEBUG, (Marker)null, message, t)) {
/*  369 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void entry() {
/*  378 */     entry(FQCN, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void entry(Object... params) {
/*  388 */     entry(FQCN, params);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entry(String fqcn, Object... params) {
/*  398 */     if (isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, (Throwable)null)) {
/*  399 */       log(ENTRY_MARKER, fqcn, Level.TRACE, entryMsg(params.length, params), null);
/*      */     }
/*      */   }
/*      */   
/*      */   private Message entryMsg(int count, Object... params) {
/*  404 */     if (count == 0) {
/*  405 */       return this.messageFactory.newMessage("entry");
/*      */     }
/*  407 */     StringBuilder sb = new StringBuilder("entry params(");
/*  408 */     int i = 0;
/*  409 */     for (Object parm : params) {
/*  410 */       if (parm != null) {
/*  411 */         sb.append(parm.toString());
/*      */       } else {
/*  413 */         sb.append("null");
/*      */       } 
/*  415 */       if (++i < params.length) {
/*  416 */         sb.append(", ");
/*      */       }
/*      */     } 
/*  419 */     sb.append(")");
/*  420 */     return this.messageFactory.newMessage(sb.toString());
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
/*      */   public void error(Marker marker, Message msg) {
/*  432 */     if (isEnabled(Level.ERROR, marker, msg, (Throwable)null)) {
/*  433 */       log(marker, FQCN, Level.ERROR, msg, null);
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
/*      */   public void error(Marker marker, Message msg, Throwable t) {
/*  446 */     if (isEnabled(Level.ERROR, marker, msg, t)) {
/*  447 */       log(marker, FQCN, Level.ERROR, msg, t);
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
/*      */   public void error(Marker marker, Object message) {
/*  459 */     if (isEnabled(Level.ERROR, marker, message, (Throwable)null)) {
/*  460 */       log(marker, FQCN, Level.ERROR, this.messageFactory.newMessage(message), null);
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
/*      */   public void error(Marker marker, Object message, Throwable t) {
/*  474 */     if (isEnabled(Level.ERROR, marker, message, t)) {
/*  475 */       log(marker, FQCN, Level.ERROR, this.messageFactory.newMessage(message), t);
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
/*      */   public void error(Marker marker, String message) {
/*  487 */     if (isEnabled(Level.ERROR, marker, message)) {
/*  488 */       log(marker, FQCN, Level.ERROR, this.messageFactory.newMessage(message), null);
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
/*      */   public void error(Marker marker, String message, Object... params) {
/*  501 */     if (isEnabled(Level.ERROR, marker, message, params)) {
/*  502 */       Message msg = this.messageFactory.newMessage(message, params);
/*  503 */       log(marker, FQCN, Level.ERROR, msg, msg.getThrowable());
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
/*      */   public void error(Marker marker, String message, Throwable t) {
/*  517 */     if (isEnabled(Level.ERROR, marker, message, t)) {
/*  518 */       log(marker, FQCN, Level.ERROR, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void error(Message msg) {
/*  529 */     if (isEnabled(Level.ERROR, (Marker)null, msg, (Throwable)null)) {
/*  530 */       log(null, FQCN, Level.ERROR, msg, null);
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
/*      */   public void error(Message msg, Throwable t) {
/*  542 */     if (isEnabled(Level.ERROR, (Marker)null, msg, t)) {
/*  543 */       log(null, FQCN, Level.ERROR, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void error(Object message) {
/*  554 */     if (isEnabled(Level.ERROR, (Marker)null, message, (Throwable)null)) {
/*  555 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(message), null);
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
/*      */   public void error(Object message, Throwable t) {
/*  568 */     if (isEnabled(Level.ERROR, (Marker)null, message, t)) {
/*  569 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void error(String message) {
/*  580 */     if (isEnabled(Level.ERROR, null, message)) {
/*  581 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(message), null);
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
/*      */   public void error(String message, Object... params) {
/*  593 */     if (isEnabled(Level.ERROR, (Marker)null, message, params)) {
/*  594 */       Message msg = this.messageFactory.newMessage(message, params);
/*  595 */       log(null, FQCN, Level.ERROR, msg, msg.getThrowable());
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
/*      */   public void error(String message, Throwable t) {
/*  608 */     if (isEnabled(Level.ERROR, (Marker)null, message, t)) {
/*  609 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void exit() {
/*  618 */     exit(FQCN, null);
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
/*      */   public <R> R exit(R result) {
/*  630 */     return exit(FQCN, result);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <R> R exit(String fqcn, R result) {
/*  641 */     if (isEnabled(Level.TRACE, EXIT_MARKER, (Object)null, (Throwable)null)) {
/*  642 */       log(EXIT_MARKER, fqcn, Level.TRACE, toExitMsg(result), null);
/*      */     }
/*  644 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fatal(Marker marker, Message msg) {
/*  655 */     if (isEnabled(Level.FATAL, marker, msg, (Throwable)null)) {
/*  656 */       log(marker, FQCN, Level.FATAL, msg, null);
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
/*      */   public void fatal(Marker marker, Message msg, Throwable t) {
/*  669 */     if (isEnabled(Level.FATAL, marker, msg, t)) {
/*  670 */       log(marker, FQCN, Level.FATAL, msg, t);
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
/*      */   public void fatal(Marker marker, Object message) {
/*  682 */     if (isEnabled(Level.FATAL, marker, message, (Throwable)null)) {
/*  683 */       log(marker, FQCN, Level.FATAL, this.messageFactory.newMessage(message), null);
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
/*      */   public void fatal(Marker marker, Object message, Throwable t) {
/*  697 */     if (isEnabled(Level.FATAL, marker, message, t)) {
/*  698 */       log(marker, FQCN, Level.FATAL, this.messageFactory.newMessage(message), t);
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
/*      */   public void fatal(Marker marker, String message) {
/*  710 */     if (isEnabled(Level.FATAL, marker, message)) {
/*  711 */       log(marker, FQCN, Level.FATAL, this.messageFactory.newMessage(message), null);
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
/*      */   public void fatal(Marker marker, String message, Object... params) {
/*  724 */     if (isEnabled(Level.FATAL, marker, message, params)) {
/*  725 */       Message msg = this.messageFactory.newMessage(message, params);
/*  726 */       log(marker, FQCN, Level.FATAL, msg, msg.getThrowable());
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
/*      */   public void fatal(Marker marker, String message, Throwable t) {
/*  740 */     if (isEnabled(Level.FATAL, marker, message, t)) {
/*  741 */       log(marker, FQCN, Level.FATAL, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fatal(Message msg) {
/*  751 */     if (isEnabled(Level.FATAL, (Marker)null, msg, (Throwable)null)) {
/*  752 */       log(null, FQCN, Level.FATAL, msg, null);
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
/*      */   public void fatal(Message msg, Throwable t) {
/*  764 */     if (isEnabled(Level.FATAL, (Marker)null, msg, t)) {
/*  765 */       log(null, FQCN, Level.FATAL, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fatal(Object message) {
/*  776 */     if (isEnabled(Level.FATAL, (Marker)null, message, (Throwable)null)) {
/*  777 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(message), null);
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
/*      */   public void fatal(Object message, Throwable t) {
/*  790 */     if (isEnabled(Level.FATAL, (Marker)null, message, t)) {
/*  791 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fatal(String message) {
/*  802 */     if (isEnabled(Level.FATAL, null, message)) {
/*  803 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(message), null);
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
/*      */   public void fatal(String message, Object... params) {
/*  815 */     if (isEnabled(Level.FATAL, (Marker)null, message, params)) {
/*  816 */       Message msg = this.messageFactory.newMessage(message, params);
/*  817 */       log(null, FQCN, Level.FATAL, msg, msg.getThrowable());
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
/*      */   public void fatal(String message, Throwable t) {
/*  830 */     if (isEnabled(Level.FATAL, (Marker)null, message, t)) {
/*  831 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MessageFactory getMessageFactory() {
/*  842 */     return this.messageFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  850 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void info(Marker marker, Message msg) {
/*  861 */     if (isEnabled(Level.INFO, marker, msg, (Throwable)null)) {
/*  862 */       log(marker, FQCN, Level.INFO, msg, null);
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
/*      */   public void info(Marker marker, Message msg, Throwable t) {
/*  875 */     if (isEnabled(Level.INFO, marker, msg, t)) {
/*  876 */       log(marker, FQCN, Level.INFO, msg, t);
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
/*      */   public void info(Marker marker, Object message) {
/*  888 */     if (isEnabled(Level.INFO, marker, message, (Throwable)null)) {
/*  889 */       log(marker, FQCN, Level.INFO, this.messageFactory.newMessage(message), null);
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
/*      */   public void info(Marker marker, Object message, Throwable t) {
/*  903 */     if (isEnabled(Level.INFO, marker, message, t)) {
/*  904 */       log(marker, FQCN, Level.INFO, this.messageFactory.newMessage(message), t);
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
/*      */   public void info(Marker marker, String message) {
/*  916 */     if (isEnabled(Level.INFO, marker, message)) {
/*  917 */       log(marker, FQCN, Level.INFO, this.messageFactory.newMessage(message), null);
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
/*      */   public void info(Marker marker, String message, Object... params) {
/*  930 */     if (isEnabled(Level.INFO, marker, message, params)) {
/*  931 */       Message msg = this.messageFactory.newMessage(message, params);
/*  932 */       log(marker, FQCN, Level.INFO, msg, msg.getThrowable());
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
/*      */   public void info(Marker marker, String message, Throwable t) {
/*  946 */     if (isEnabled(Level.INFO, marker, message, t)) {
/*  947 */       log(marker, FQCN, Level.INFO, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void info(Message msg) {
/*  958 */     if (isEnabled(Level.INFO, (Marker)null, msg, (Throwable)null)) {
/*  959 */       log(null, FQCN, Level.INFO, msg, null);
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
/*      */   public void info(Message msg, Throwable t) {
/*  971 */     if (isEnabled(Level.INFO, (Marker)null, msg, t)) {
/*  972 */       log(null, FQCN, Level.INFO, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void info(Object message) {
/*  983 */     if (isEnabled(Level.INFO, (Marker)null, message, (Throwable)null)) {
/*  984 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(message), null);
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
/*      */   public void info(Object message, Throwable t) {
/*  998 */     if (isEnabled(Level.INFO, (Marker)null, message, t)) {
/*  999 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void info(String message) {
/* 1010 */     if (isEnabled(Level.INFO, null, message)) {
/* 1011 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(message), null);
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
/*      */   public void info(String message, Object... params) {
/* 1023 */     if (isEnabled(Level.INFO, (Marker)null, message, params)) {
/* 1024 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1025 */       log(null, FQCN, Level.INFO, msg, msg.getThrowable());
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
/*      */   public void info(String message, Throwable t) {
/* 1038 */     if (isEnabled(Level.INFO, (Marker)null, message, t)) {
/* 1039 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(message), t);
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
/*      */   public boolean isDebugEnabled() {
/* 1051 */     return isEnabled(Level.DEBUG, null, null);
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
/*      */   public boolean isDebugEnabled(Marker marker) {
/* 1063 */     return isEnabled(Level.DEBUG, marker, (Object)null, (Throwable)null);
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
/*      */   public boolean isEnabled(Level level) {
/* 1076 */     return isEnabled(level, (Marker)null, (Object)null, (Throwable)null);
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
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isErrorEnabled() {
/* 1136 */     return isEnabled(Level.ERROR, (Marker)null, (Object)null, (Throwable)null);
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
/*      */   public boolean isErrorEnabled(Marker marker) {
/* 1148 */     return isEnabled(Level.ERROR, marker, (Object)null, (Throwable)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFatalEnabled() {
/* 1159 */     return isEnabled(Level.FATAL, (Marker)null, (Object)null, (Throwable)null);
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
/*      */   public boolean isFatalEnabled(Marker marker) {
/* 1171 */     return isEnabled(Level.FATAL, marker, (Object)null, (Throwable)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInfoEnabled() {
/* 1182 */     return isEnabled(Level.INFO, (Marker)null, (Object)null, (Throwable)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInfoEnabled(Marker marker) {
/* 1193 */     return isEnabled(Level.INFO, marker, (Object)null, (Throwable)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTraceEnabled() {
/* 1204 */     return isEnabled(Level.TRACE, (Marker)null, (Object)null, (Throwable)null);
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
/*      */   public boolean isTraceEnabled(Marker marker) {
/* 1216 */     return isEnabled(Level.TRACE, marker, (Object)null, (Throwable)null);
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
/*      */   public boolean isWarnEnabled() {
/* 1228 */     return isEnabled(Level.WARN, (Marker)null, (Object)null, (Throwable)null);
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
/*      */   public boolean isWarnEnabled(Marker marker) {
/* 1240 */     return isEnabled(Level.WARN, marker, (Object)null, (Throwable)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEnabled(Level level, Marker marker) {
/* 1245 */     return isEnabled(level, marker, (Object)null, (Throwable)null);
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
/*      */   public void log(Level level, Marker marker, Message msg) {
/* 1257 */     if (isEnabled(level, marker, msg, (Throwable)null)) {
/* 1258 */       log(marker, FQCN, level, msg, null);
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
/*      */   public void log(Level level, Marker marker, Message msg, Throwable t) {
/* 1272 */     if (isEnabled(level, marker, msg, t)) {
/* 1273 */       log(marker, FQCN, level, msg, t);
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
/*      */   public void log(Level level, Marker marker, Object message) {
/* 1286 */     if (isEnabled(level, marker, message, (Throwable)null)) {
/* 1287 */       log(marker, FQCN, level, this.messageFactory.newMessage(message), null);
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
/*      */   public void log(Level level, Marker marker, Object message, Throwable t) {
/* 1302 */     if (isEnabled(level, marker, message, t)) {
/* 1303 */       log(marker, FQCN, level, this.messageFactory.newMessage(message), t);
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
/*      */   public void log(Level level, Marker marker, String message) {
/* 1316 */     if (isEnabled(level, marker, message)) {
/* 1317 */       log(marker, FQCN, level, this.messageFactory.newMessage(message), null);
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
/*      */   public void log(Level level, Marker marker, String message, Object... params) {
/* 1331 */     if (isEnabled(level, marker, message, params)) {
/* 1332 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1333 */       log(marker, FQCN, level, msg, msg.getThrowable());
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
/*      */   public void log(Level level, Marker marker, String message, Throwable t) {
/* 1348 */     if (isEnabled(level, marker, message, t)) {
/* 1349 */       log(marker, FQCN, level, this.messageFactory.newMessage(message), t);
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
/*      */   public void log(Level level, Message msg) {
/* 1361 */     if (isEnabled(level, (Marker)null, msg, (Throwable)null)) {
/* 1362 */       log(null, FQCN, level, msg, null);
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
/*      */   public void log(Level level, Message msg, Throwable t) {
/* 1375 */     if (isEnabled(level, (Marker)null, msg, t)) {
/* 1376 */       log(null, FQCN, level, msg, t);
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
/*      */   public void log(Level level, Object message) {
/* 1388 */     if (isEnabled(level, (Marker)null, message, (Throwable)null)) {
/* 1389 */       log(null, FQCN, level, this.messageFactory.newMessage(message), null);
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
/*      */   public void log(Level level, Object message, Throwable t) {
/* 1403 */     if (isEnabled(level, (Marker)null, message, t)) {
/* 1404 */       log(null, FQCN, level, this.messageFactory.newMessage(message), t);
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
/*      */   public void log(Level level, String message) {
/* 1416 */     if (isEnabled(level, null, message)) {
/* 1417 */       log(null, FQCN, level, this.messageFactory.newMessage(message), null);
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
/*      */   public void log(Level level, String message, Object... params) {
/* 1430 */     if (isEnabled(level, (Marker)null, message, params)) {
/* 1431 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1432 */       log(null, FQCN, level, msg, msg.getThrowable());
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
/*      */   public void log(Level level, String message, Throwable t) {
/* 1447 */     if (isEnabled(level, (Marker)null, message, t)) {
/* 1448 */       log(null, FQCN, level, this.messageFactory.newMessage(message), t);
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
/*      */   public void printf(Level level, String format, Object... params) {
/* 1460 */     if (isEnabled(level, (Marker)null, format, params)) {
/* 1461 */       StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(format, params);
/* 1462 */       log(null, FQCN, level, (Message)stringFormattedMessage, stringFormattedMessage.getThrowable());
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
/*      */   public void printf(Level level, Marker marker, String format, Object... params) {
/* 1475 */     if (isEnabled(level, marker, format, params)) {
/* 1476 */       StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(format, params);
/* 1477 */       log(marker, FQCN, level, (Message)stringFormattedMessage, stringFormattedMessage.getThrowable());
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
/*      */   public abstract void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Throwable> T throwing(Level level, T t) {
/* 1502 */     return throwing(FQCN, level, t);
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
/*      */   public <T extends Throwable> T throwing(T t) {
/* 1514 */     return throwing(FQCN, Level.ERROR, t);
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
/*      */   protected <T extends Throwable> T throwing(String fqcn, Level level, T t) {
/* 1527 */     if (isEnabled(level, THROWING_MARKER, (Object)null, (Throwable)null)) {
/* 1528 */       log(THROWING_MARKER, fqcn, level, this.messageFactory.newMessage("throwing"), (Throwable)t);
/*      */     }
/* 1530 */     return t;
/*      */   }
/*      */   
/*      */   private Message toExitMsg(Object result) {
/* 1534 */     if (result == null) {
/* 1535 */       return this.messageFactory.newMessage("exit");
/*      */     }
/* 1537 */     return this.messageFactory.newMessage("exit with(" + result + ")");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1546 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trace(Marker marker, Message msg) {
/* 1557 */     if (isEnabled(Level.TRACE, marker, msg, (Throwable)null)) {
/* 1558 */       log(marker, FQCN, Level.TRACE, msg, null);
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
/*      */   public void trace(Marker marker, Message msg, Throwable t) {
/* 1572 */     if (isEnabled(Level.TRACE, marker, msg, t)) {
/* 1573 */       log(marker, FQCN, Level.TRACE, msg, t);
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
/*      */   public void trace(Marker marker, Object message) {
/* 1585 */     if (isEnabled(Level.TRACE, marker, message, (Throwable)null)) {
/* 1586 */       log(marker, FQCN, Level.TRACE, this.messageFactory.newMessage(message), null);
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
/*      */   public void trace(Marker marker, Object message, Throwable t) {
/* 1604 */     if (isEnabled(Level.TRACE, marker, message, t)) {
/* 1605 */       log(marker, FQCN, Level.TRACE, this.messageFactory.newMessage(message), t);
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
/*      */   public void trace(Marker marker, String message) {
/* 1617 */     if (isEnabled(Level.TRACE, marker, message)) {
/* 1618 */       log(marker, FQCN, Level.TRACE, this.messageFactory.newMessage(message), null);
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
/*      */   public void trace(Marker marker, String message, Object... params) {
/* 1631 */     if (isEnabled(Level.TRACE, marker, message, params)) {
/* 1632 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1633 */       log(marker, FQCN, Level.TRACE, msg, msg.getThrowable());
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
/*      */   public void trace(Marker marker, String message, Throwable t) {
/* 1651 */     if (isEnabled(Level.TRACE, marker, message, t)) {
/* 1652 */       log(marker, FQCN, Level.TRACE, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trace(Message msg) {
/* 1663 */     if (isEnabled(Level.TRACE, (Marker)null, msg, (Throwable)null)) {
/* 1664 */       log(null, FQCN, Level.TRACE, msg, null);
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
/*      */   public void trace(Message msg, Throwable t) {
/* 1676 */     if (isEnabled(Level.TRACE, (Marker)null, msg, t)) {
/* 1677 */       log(null, FQCN, Level.TRACE, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trace(Object message) {
/* 1688 */     if (isEnabled(Level.TRACE, (Marker)null, message, (Throwable)null)) {
/* 1689 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(message), null);
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
/*      */   public void trace(Object message, Throwable t) {
/* 1706 */     if (isEnabled(Level.TRACE, (Marker)null, message, t)) {
/* 1707 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trace(String message) {
/* 1718 */     if (isEnabled(Level.TRACE, null, message)) {
/* 1719 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(message), null);
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
/*      */   public void trace(String message, Object... params) {
/* 1731 */     if (isEnabled(Level.TRACE, (Marker)null, message, params)) {
/* 1732 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1733 */       log(null, FQCN, Level.TRACE, msg, msg.getThrowable());
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
/*      */   public void trace(String message, Throwable t) {
/* 1750 */     if (isEnabled(Level.TRACE, (Marker)null, message, t)) {
/* 1751 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(message), t);
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
/*      */   public void warn(Marker marker, Message msg) {
/* 1763 */     if (isEnabled(Level.WARN, marker, msg, (Throwable)null)) {
/* 1764 */       log(marker, FQCN, Level.WARN, msg, null);
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
/*      */   public void warn(Marker marker, Message msg, Throwable t) {
/* 1777 */     if (isEnabled(Level.WARN, marker, msg, t)) {
/* 1778 */       log(marker, FQCN, Level.WARN, msg, t);
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
/*      */   public void warn(Marker marker, Object message) {
/* 1790 */     if (isEnabled(Level.WARN, marker, message, (Throwable)null)) {
/* 1791 */       log(marker, FQCN, Level.WARN, this.messageFactory.newMessage(message), null);
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
/*      */   public void warn(Marker marker, Object message, Throwable t) {
/* 1811 */     if (isEnabled(Level.WARN, marker, message, t)) {
/* 1812 */       log(marker, FQCN, Level.WARN, this.messageFactory.newMessage(message), t);
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
/*      */   public void warn(Marker marker, String message) {
/* 1824 */     if (isEnabled(Level.WARN, marker, message)) {
/* 1825 */       log(marker, FQCN, Level.WARN, this.messageFactory.newMessage(message), null);
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
/*      */   public void warn(Marker marker, String message, Object... params) {
/* 1838 */     if (isEnabled(Level.WARN, marker, message, params)) {
/* 1839 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1840 */       log(marker, FQCN, Level.WARN, msg, msg.getThrowable());
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
/*      */   public void warn(Marker marker, String message, Throwable t) {
/* 1854 */     if (isEnabled(Level.WARN, marker, message, t)) {
/* 1855 */       log(marker, FQCN, Level.WARN, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void warn(Message msg) {
/* 1866 */     if (isEnabled(Level.WARN, (Marker)null, msg, (Throwable)null)) {
/* 1867 */       log(null, FQCN, Level.WARN, msg, null);
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
/*      */   public void warn(Message msg, Throwable t) {
/* 1879 */     if (isEnabled(Level.WARN, (Marker)null, msg, t)) {
/* 1880 */       log(null, FQCN, Level.WARN, msg, t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void warn(Object message) {
/* 1891 */     if (isEnabled(Level.WARN, (Marker)null, message, (Throwable)null)) {
/* 1892 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(message), null);
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
/*      */   public void warn(Object message, Throwable t) {
/* 1905 */     if (isEnabled(Level.WARN, (Marker)null, message, t)) {
/* 1906 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(message), t);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void warn(String message) {
/* 1917 */     if (isEnabled(Level.WARN, null, message)) {
/* 1918 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(message), null);
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
/*      */   public void warn(String message, Object... params) {
/* 1930 */     if (isEnabled(Level.WARN, (Marker)null, message, params)) {
/* 1931 */       Message msg = this.messageFactory.newMessage(message, params);
/* 1932 */       log(null, FQCN, Level.WARN, msg, msg.getThrowable());
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
/*      */   public void warn(String message, Throwable t) {
/* 1945 */     if (isEnabled(Level.WARN, (Marker)null, message, t))
/* 1946 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(message), t); 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\AbstractLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */