package javax.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface Query {
  List getResultList();
  
  Object getSingleResult();
  
  int executeUpdate();
  
  Query setMaxResults(int paramInt);
  
  Query setFirstResult(int paramInt);
  
  Query setHint(String paramString, Object paramObject);
  
  Query setParameter(String paramString, Object paramObject);
  
  Query setParameter(String paramString, Date paramDate, TemporalType paramTemporalType);
  
  Query setParameter(String paramString, Calendar paramCalendar, TemporalType paramTemporalType);
  
  Query setParameter(int paramInt, Object paramObject);
  
  Query setParameter(int paramInt, Date paramDate, TemporalType paramTemporalType);
  
  Query setParameter(int paramInt, Calendar paramCalendar, TemporalType paramTemporalType);
  
  Query setFlushMode(FlushModeType paramFlushModeType);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\Query.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */