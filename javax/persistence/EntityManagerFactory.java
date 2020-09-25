package javax.persistence;

import java.util.Map;

public interface EntityManagerFactory {
  EntityManager createEntityManager();
  
  EntityManager createEntityManager(Map paramMap);
  
  void close();
  
  boolean isOpen();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\EntityManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */