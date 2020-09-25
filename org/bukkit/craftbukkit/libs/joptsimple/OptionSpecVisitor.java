package org.bukkit.craftbukkit.libs.joptsimple;

interface OptionSpecVisitor {
  void visit(NoArgumentOptionSpec paramNoArgumentOptionSpec);
  
  void visit(RequiredArgumentOptionSpec<?> paramRequiredArgumentOptionSpec);
  
  void visit(OptionalArgumentOptionSpec<?> paramOptionalArgumentOptionSpec);
  
  void visit(AlternativeLongOptionSpec paramAlternativeLongOptionSpec);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionSpecVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */