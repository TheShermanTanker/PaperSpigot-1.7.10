package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.util.ArrayList;
import java.util.Iterator;

public interface BeanCollectionHelp<T> {
  void setLoader(BeanCollectionLoader paramBeanCollectionLoader);
  
  BeanCollectionAdd getBeanCollectionAdd(Object paramObject, String paramString);
  
  Object createEmpty(boolean paramBoolean);
  
  Iterator<?> getIterator(Object paramObject);
  
  void add(BeanCollection<?> paramBeanCollection, Object paramObject);
  
  BeanCollection<T> createReference(Object paramObject, String paramString);
  
  ArrayList<InvalidValue> validate(Object paramObject);
  
  void refresh(EbeanServer paramEbeanServer, Query<?> paramQuery, Transaction paramTransaction, Object paramObject);
  
  void refresh(BeanCollection<?> paramBeanCollection, Object paramObject);
  
  void jsonWrite(WriteJsonContext paramWriteJsonContext, String paramString, Object paramObject, boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanCollectionHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */