package org.apache.logging.log4j.core.pattern;

public interface ArrayPatternConverter extends PatternConverter {
  void format(StringBuilder paramStringBuilder, Object... paramVarArgs);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ArrayPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */