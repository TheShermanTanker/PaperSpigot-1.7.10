package com.avaje.ebean;

public interface ExampleExpression extends Expression {
  ExampleExpression includeZeros();
  
  ExampleExpression caseInsensitive();
  
  ExampleExpression useStartsWith();
  
  ExampleExpression useContains();
  
  ExampleExpression useEndsWith();
  
  ExampleExpression useEqualTo();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\ExampleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */