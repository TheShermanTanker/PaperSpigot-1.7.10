package javax.persistence.spi;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

public interface PersistenceUnitInfo {
  String getPersistenceUnitName();
  
  String getPersistenceProviderClassName();
  
  PersistenceUnitTransactionType getTransactionType();
  
  DataSource getJtaDataSource();
  
  DataSource getNonJtaDataSource();
  
  List<String> getMappingFileNames();
  
  List<URL> getJarFileUrls();
  
  URL getPersistenceUnitRootUrl();
  
  List<String> getManagedClassNames();
  
  boolean excludeUnlistedClasses();
  
  Properties getProperties();
  
  ClassLoader getClassLoader();
  
  void addTransformer(ClassTransformer paramClassTransformer);
  
  ClassLoader getNewTempClassLoader();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\spi\PersistenceUnitInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */