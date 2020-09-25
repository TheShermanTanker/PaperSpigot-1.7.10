package javax.persistence;

public interface EntityManager {
  void persist(Object paramObject);
  
  <T> T merge(T paramT);
  
  void remove(Object paramObject);
  
  <T> T find(Class<T> paramClass, Object paramObject);
  
  <T> T getReference(Class<T> paramClass, Object paramObject);
  
  void flush();
  
  void setFlushMode(FlushModeType paramFlushModeType);
  
  FlushModeType getFlushMode();
  
  void lock(Object paramObject, LockModeType paramLockModeType);
  
  void refresh(Object paramObject);
  
  void clear();
  
  boolean contains(Object paramObject);
  
  Query createQuery(String paramString);
  
  Query createNamedQuery(String paramString);
  
  Query createNativeQuery(String paramString);
  
  Query createNativeQuery(String paramString, Class paramClass);
  
  Query createNativeQuery(String paramString1, String paramString2);
  
  void joinTransaction();
  
  Object getDelegate();
  
  void close();
  
  boolean isOpen();
  
  EntityTransaction getTransaction();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\EntityManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */