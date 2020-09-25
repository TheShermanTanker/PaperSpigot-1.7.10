package javax.persistence.spi;

import java.util.Map;
import javax.persistence.EntityManagerFactory;

public interface PersistenceProvider {
  EntityManagerFactory createEntityManagerFactory(String paramString, Map paramMap);
  
  EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo paramPersistenceUnitInfo, Map paramMap);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\spi\PersistenceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */