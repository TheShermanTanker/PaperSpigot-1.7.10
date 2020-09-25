package javax.persistence;

public interface EntityTransaction {
  void begin();
  
  void commit();
  
  void rollback();
  
  void setRollbackOnly();
  
  boolean getRollbackOnly();
  
  boolean isActive();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\EntityTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */