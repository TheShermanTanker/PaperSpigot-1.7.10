package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface Nestable {
  Throwable getCause();
  
  String getMessage();
  
  String getMessage(int paramInt);
  
  String[] getMessages();
  
  Throwable getThrowable(int paramInt);
  
  int getThrowableCount();
  
  Throwable[] getThrowables();
  
  int indexOfThrowable(Class paramClass);
  
  int indexOfThrowable(Class paramClass, int paramInt);
  
  void printStackTrace(PrintWriter paramPrintWriter);
  
  void printStackTrace(PrintStream paramPrintStream);
  
  void printPartialStackTrace(PrintWriter paramPrintWriter);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\exception\Nestable.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */