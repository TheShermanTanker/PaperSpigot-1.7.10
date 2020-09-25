/*      */ package org.apache.logging.log4j.core.lookup;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import org.apache.logging.log4j.core.LogEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StrSubstitutor
/*      */ {
/*      */   public static final char DEFAULT_ESCAPE = '$';
/*  113 */   public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
/*      */ 
/*      */ 
/*      */   
/*  117 */   public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BUF_SIZE = 256;
/*      */ 
/*      */ 
/*      */   
/*      */   private char escapeChar;
/*      */ 
/*      */ 
/*      */   
/*      */   private StrMatcher prefixMatcher;
/*      */ 
/*      */ 
/*      */   
/*      */   private StrMatcher suffixMatcher;
/*      */ 
/*      */ 
/*      */   
/*      */   private StrLookup variableResolver;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableSubstitutionInVariables;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor() {
/*  148 */     this((StrLookup)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(Map<String, String> valueMap) {
/*  157 */     this(new MapLookup(valueMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
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
/*      */   public StrSubstitutor(Map<String, String> valueMap, String prefix, String suffix) {
/*  169 */     this(new MapLookup(valueMap), prefix, suffix, '$');
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
/*      */   public StrSubstitutor(Map<String, String> valueMap, String prefix, String suffix, char escape) {
/*  183 */     this(new MapLookup(valueMap), prefix, suffix, escape);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrSubstitutor(StrLookup variableResolver) {
/*  192 */     this(variableResolver, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
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
/*      */   public StrSubstitutor(StrLookup variableResolver, String prefix, String suffix, char escape) {
/*  206 */     setVariableResolver(variableResolver);
/*  207 */     setVariablePrefix(prefix);
/*  208 */     setVariableSuffix(suffix);
/*  209 */     setEscapeChar(escape);
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
/*      */   public StrSubstitutor(StrLookup variableResolver, StrMatcher prefixMatcher, StrMatcher suffixMatcher, char escape) {
/*  224 */     setVariableResolver(variableResolver);
/*  225 */     setVariablePrefixMatcher(prefixMatcher);
/*  226 */     setVariableSuffixMatcher(suffixMatcher);
/*  227 */     setEscapeChar(escape);
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
/*      */   public static String replace(Object source, Map<String, String> valueMap) {
/*  239 */     return (new StrSubstitutor(valueMap)).replace(source);
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
/*      */   public static String replace(Object source, Map<String, String> valueMap, String prefix, String suffix) {
/*  256 */     return (new StrSubstitutor(valueMap, prefix, suffix)).replace(source);
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
/*      */   public static String replace(Object source, Properties valueProperties) {
/*  268 */     if (valueProperties == null) {
/*  269 */       return source.toString();
/*      */     }
/*  271 */     Map<String, String> valueMap = new HashMap<String, String>();
/*  272 */     Enumeration<?> propNames = valueProperties.propertyNames();
/*  273 */     while (propNames.hasMoreElements()) {
/*  274 */       String propName = (String)propNames.nextElement();
/*  275 */       String propValue = valueProperties.getProperty(propName);
/*  276 */       valueMap.put(propName, propValue);
/*      */     } 
/*  278 */     return replace(source, valueMap);
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
/*      */   public String replace(String source) {
/*  290 */     return replace((LogEvent)null, source);
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
/*      */   public String replace(LogEvent event, String source) {
/*  302 */     if (source == null) {
/*  303 */       return null;
/*      */     }
/*  305 */     StringBuilder buf = new StringBuilder(source);
/*  306 */     if (!substitute(event, buf, 0, source.length())) {
/*  307 */       return source;
/*      */     }
/*  309 */     return buf.toString();
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
/*      */   public String replace(String source, int offset, int length) {
/*  325 */     return replace((LogEvent)null, source, offset, length);
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
/*      */   public String replace(LogEvent event, String source, int offset, int length) {
/*  342 */     if (source == null) {
/*  343 */       return null;
/*      */     }
/*  345 */     StringBuilder buf = (new StringBuilder(length)).append(source, offset, length);
/*  346 */     if (!substitute(event, buf, 0, length)) {
/*  347 */       return source.substring(offset, offset + length);
/*      */     }
/*  349 */     return buf.toString();
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
/*      */   public String replace(char[] source) {
/*  362 */     return replace((LogEvent)null, source);
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
/*      */   public String replace(LogEvent event, char[] source) {
/*  376 */     if (source == null) {
/*  377 */       return null;
/*      */     }
/*  379 */     StringBuilder buf = (new StringBuilder(source.length)).append(source);
/*  380 */     substitute(event, buf, 0, source.length);
/*  381 */     return buf.toString();
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
/*      */   public String replace(char[] source, int offset, int length) {
/*  398 */     return replace((LogEvent)null, source, offset, length);
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
/*      */   public String replace(LogEvent event, char[] source, int offset, int length) {
/*  416 */     if (source == null) {
/*  417 */       return null;
/*      */     }
/*  419 */     StringBuilder buf = (new StringBuilder(length)).append(source, offset, length);
/*  420 */     substitute(event, buf, 0, length);
/*  421 */     return buf.toString();
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
/*      */   public String replace(StringBuffer source) {
/*  434 */     return replace((LogEvent)null, source);
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
/*      */   public String replace(LogEvent event, StringBuffer source) {
/*  448 */     if (source == null) {
/*  449 */       return null;
/*      */     }
/*  451 */     StringBuilder buf = (new StringBuilder(source.length())).append(source);
/*  452 */     substitute(event, buf, 0, buf.length());
/*  453 */     return buf.toString();
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
/*      */   public String replace(StringBuffer source, int offset, int length) {
/*  470 */     return replace((LogEvent)null, source, offset, length);
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
/*      */   public String replace(LogEvent event, StringBuffer source, int offset, int length) {
/*  488 */     if (source == null) {
/*  489 */       return null;
/*      */     }
/*  491 */     StringBuilder buf = (new StringBuilder(length)).append(source, offset, length);
/*  492 */     substitute(event, buf, 0, length);
/*  493 */     return buf.toString();
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
/*      */   public String replace(StringBuilder source) {
/*  506 */     return replace((LogEvent)null, source);
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
/*      */   public String replace(LogEvent event, StringBuilder source) {
/*  520 */     if (source == null) {
/*  521 */       return null;
/*      */     }
/*  523 */     StringBuilder buf = (new StringBuilder(source.length())).append(source);
/*  524 */     substitute(event, buf, 0, buf.length());
/*  525 */     return buf.toString();
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
/*      */   public String replace(StringBuilder source, int offset, int length) {
/*  541 */     return replace((LogEvent)null, source, offset, length);
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
/*      */   public String replace(LogEvent event, StringBuilder source, int offset, int length) {
/*  559 */     if (source == null) {
/*  560 */       return null;
/*      */     }
/*  562 */     StringBuilder buf = (new StringBuilder(length)).append(source, offset, length);
/*  563 */     substitute(event, buf, 0, length);
/*  564 */     return buf.toString();
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
/*      */   public String replace(Object source) {
/*  577 */     return replace((LogEvent)null, source);
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
/*      */   public String replace(LogEvent event, Object source) {
/*  590 */     if (source == null) {
/*  591 */       return null;
/*      */     }
/*  593 */     StringBuilder buf = (new StringBuilder()).append(source);
/*  594 */     substitute(event, buf, 0, buf.length());
/*  595 */     return buf.toString();
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
/*      */   public boolean replaceIn(StringBuffer source) {
/*  608 */     if (source == null) {
/*  609 */       return false;
/*      */     }
/*  611 */     return replaceIn(source, 0, source.length());
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
/*      */   public boolean replaceIn(StringBuffer source, int offset, int length) {
/*  628 */     return replaceIn((LogEvent)null, source, offset, length);
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
/*      */   public boolean replaceIn(LogEvent event, StringBuffer source, int offset, int length) {
/*  646 */     if (source == null) {
/*  647 */       return false;
/*      */     }
/*  649 */     StringBuilder buf = (new StringBuilder(length)).append(source, offset, length);
/*  650 */     if (!substitute(event, buf, 0, length)) {
/*  651 */       return false;
/*      */     }
/*  653 */     source.replace(offset, offset + length, buf.toString());
/*  654 */     return true;
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
/*      */   public boolean replaceIn(StringBuilder source) {
/*  666 */     return replaceIn(null, source);
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
/*      */   public boolean replaceIn(LogEvent event, StringBuilder source) {
/*  679 */     if (source == null) {
/*  680 */       return false;
/*      */     }
/*  682 */     return substitute(event, source, 0, source.length());
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
/*      */   public boolean replaceIn(StringBuilder source, int offset, int length) {
/*  697 */     return replaceIn((LogEvent)null, source, offset, length);
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
/*      */   public boolean replaceIn(LogEvent event, StringBuilder source, int offset, int length) {
/*  714 */     if (source == null) {
/*  715 */       return false;
/*      */     }
/*  717 */     return substitute(event, source, offset, length);
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
/*      */   protected boolean substitute(LogEvent event, StringBuilder buf, int offset, int length) {
/*  737 */     return (substitute(event, buf, offset, length, null) > 0);
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
/*      */   private int substitute(LogEvent event, StringBuilder buf, int offset, int length, List<String> priorVariables) {
/*  755 */     StrMatcher prefixMatcher = getVariablePrefixMatcher();
/*  756 */     StrMatcher suffixMatcher = getVariableSuffixMatcher();
/*  757 */     char escape = getEscapeChar();
/*      */     
/*  759 */     boolean top = (priorVariables == null);
/*  760 */     boolean altered = false;
/*  761 */     int lengthChange = 0;
/*  762 */     char[] chars = getChars(buf);
/*  763 */     int bufEnd = offset + length;
/*  764 */     int pos = offset;
/*  765 */     while (pos < bufEnd) {
/*  766 */       int startMatchLen = prefixMatcher.isMatch(chars, pos, offset, bufEnd);
/*      */       
/*  768 */       if (startMatchLen == 0) {
/*  769 */         pos++;
/*      */         continue;
/*      */       } 
/*  772 */       if (pos > offset && chars[pos - 1] == escape) {
/*      */         
/*  774 */         buf.deleteCharAt(pos - 1);
/*  775 */         chars = getChars(buf);
/*  776 */         lengthChange--;
/*  777 */         altered = true;
/*  778 */         bufEnd--;
/*      */         continue;
/*      */       } 
/*  781 */       int startPos = pos;
/*  782 */       pos += startMatchLen;
/*  783 */       int endMatchLen = 0;
/*  784 */       int nestedVarCount = 0;
/*  785 */       while (pos < bufEnd) {
/*  786 */         if (isEnableSubstitutionInVariables() && (endMatchLen = prefixMatcher.isMatch(chars, pos, offset, bufEnd)) != 0) {
/*      */ 
/*      */ 
/*      */           
/*  790 */           nestedVarCount++;
/*  791 */           pos += endMatchLen;
/*      */           
/*      */           continue;
/*      */         } 
/*  795 */         endMatchLen = suffixMatcher.isMatch(chars, pos, offset, bufEnd);
/*      */         
/*  797 */         if (endMatchLen == 0) {
/*  798 */           pos++;
/*      */           continue;
/*      */         } 
/*  801 */         if (nestedVarCount == 0) {
/*  802 */           String varName = new String(chars, startPos + startMatchLen, pos - startPos - startMatchLen);
/*      */ 
/*      */           
/*  805 */           if (isEnableSubstitutionInVariables()) {
/*  806 */             StringBuilder bufName = new StringBuilder(varName);
/*  807 */             substitute(event, bufName, 0, bufName.length());
/*  808 */             varName = bufName.toString();
/*      */           } 
/*  810 */           pos += endMatchLen;
/*  811 */           int endPos = pos;
/*      */ 
/*      */           
/*  814 */           if (priorVariables == null) {
/*  815 */             priorVariables = new ArrayList<String>();
/*  816 */             priorVariables.add(new String(chars, offset, length));
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  821 */           checkCyclicSubstitution(varName, priorVariables);
/*  822 */           priorVariables.add(varName);
/*      */ 
/*      */           
/*  825 */           String varValue = resolveVariable(event, varName, buf, startPos, endPos);
/*      */           
/*  827 */           if (varValue != null) {
/*      */             
/*  829 */             int varLen = varValue.length();
/*  830 */             buf.replace(startPos, endPos, varValue);
/*  831 */             altered = true;
/*  832 */             int change = substitute(event, buf, startPos, varLen, priorVariables);
/*      */             
/*  834 */             change += varLen - endPos - startPos;
/*      */             
/*  836 */             pos += change;
/*  837 */             bufEnd += change;
/*  838 */             lengthChange += change;
/*  839 */             chars = getChars(buf);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  844 */           priorVariables.remove(priorVariables.size() - 1);
/*      */           
/*      */           break;
/*      */         } 
/*  848 */         nestedVarCount--;
/*  849 */         pos += endMatchLen;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  856 */     if (top) {
/*  857 */       return altered ? 1 : 0;
/*      */     }
/*  859 */     return lengthChange;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCyclicSubstitution(String varName, List<String> priorVariables) {
/*  869 */     if (!priorVariables.contains(varName)) {
/*      */       return;
/*      */     }
/*  872 */     StringBuilder buf = new StringBuilder(256);
/*  873 */     buf.append("Infinite loop in property interpolation of ");
/*  874 */     buf.append(priorVariables.remove(0));
/*  875 */     buf.append(": ");
/*  876 */     appendWithSeparators(buf, priorVariables, "->");
/*  877 */     throw new IllegalStateException(buf.toString());
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
/*      */   protected String resolveVariable(LogEvent event, String variableName, StringBuilder buf, int startPos, int endPos) {
/*  900 */     StrLookup resolver = getVariableResolver();
/*  901 */     if (resolver == null) {
/*  902 */       return null;
/*      */     }
/*  904 */     return resolver.lookup(event, variableName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getEscapeChar() {
/*  915 */     return this.escapeChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEscapeChar(char escapeCharacter) {
/*  926 */     this.escapeChar = escapeCharacter;
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
/*      */   public StrMatcher getVariablePrefixMatcher() {
/*  941 */     return this.prefixMatcher;
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
/*      */   public StrSubstitutor setVariablePrefixMatcher(StrMatcher prefixMatcher) {
/*  956 */     if (prefixMatcher == null) {
/*  957 */       throw new IllegalArgumentException("Variable prefix matcher must not be null!");
/*      */     }
/*  959 */     this.prefixMatcher = prefixMatcher;
/*  960 */     return this;
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
/*      */   public StrSubstitutor setVariablePrefix(char prefix) {
/*  974 */     return setVariablePrefixMatcher(StrMatcher.charMatcher(prefix));
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
/*      */   public StrSubstitutor setVariablePrefix(String prefix) {
/*  988 */     if (prefix == null) {
/*  989 */       throw new IllegalArgumentException("Variable prefix must not be null!");
/*      */     }
/*  991 */     return setVariablePrefixMatcher(StrMatcher.stringMatcher(prefix));
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
/*      */   public StrMatcher getVariableSuffixMatcher() {
/* 1006 */     return this.suffixMatcher;
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
/*      */   public StrSubstitutor setVariableSuffixMatcher(StrMatcher suffixMatcher) {
/* 1021 */     if (suffixMatcher == null) {
/* 1022 */       throw new IllegalArgumentException("Variable suffix matcher must not be null!");
/*      */     }
/* 1024 */     this.suffixMatcher = suffixMatcher;
/* 1025 */     return this;
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
/*      */   public StrSubstitutor setVariableSuffix(char suffix) {
/* 1039 */     return setVariableSuffixMatcher(StrMatcher.charMatcher(suffix));
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
/*      */   public StrSubstitutor setVariableSuffix(String suffix) {
/* 1053 */     if (suffix == null) {
/* 1054 */       throw new IllegalArgumentException("Variable suffix must not be null!");
/*      */     }
/* 1056 */     return setVariableSuffixMatcher(StrMatcher.stringMatcher(suffix));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrLookup getVariableResolver() {
/* 1067 */     return this.variableResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVariableResolver(StrLookup variableResolver) {
/* 1076 */     this.variableResolver = variableResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableSubstitutionInVariables() {
/* 1087 */     return this.enableSubstitutionInVariables;
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
/*      */   public void setEnableSubstitutionInVariables(boolean enableSubstitutionInVariables) {
/* 1099 */     this.enableSubstitutionInVariables = enableSubstitutionInVariables;
/*      */   }
/*      */   
/*      */   private char[] getChars(StringBuilder sb) {
/* 1103 */     char[] chars = new char[sb.length()];
/* 1104 */     sb.getChars(0, sb.length(), chars, 0);
/* 1105 */     return chars;
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
/*      */   public void appendWithSeparators(StringBuilder sb, Iterable<?> iterable, String separator) {
/* 1118 */     if (iterable != null) {
/* 1119 */       separator = (separator == null) ? "" : separator;
/* 1120 */       Iterator<?> it = iterable.iterator();
/* 1121 */       while (it.hasNext()) {
/* 1122 */         sb.append(it.next());
/* 1123 */         if (it.hasNext()) {
/* 1124 */           sb.append(separator);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1132 */     return "StrSubstitutor(" + this.variableResolver.toString() + ")";
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\StrSubstitutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */