/*    */ package com.avaje.ebeaninternal.api;public interface SpiUpdate<T> extends Update<T> { Class<?> getBeanType();
/*    */   OrmUpdateType getOrmUpdateType();
/*    */   String getBaseTable();
/*    */   String getUpdateStatement();
/*    */   
/*    */   int getTimeout();
/*    */   
/*    */   boolean isNotifyCache();
/*    */   
/*    */   BindParams getBindParams();
/*    */   
/*    */   void setGeneratedSql(String paramString);
/*    */   
/* 14 */   public enum OrmUpdateType { INSERT {
/*    */       public String toString() {
/* 16 */         return "Insert";
/*    */       }
/*    */     },
/* 19 */     UPDATE {
/*    */       public String toString() {
/* 21 */         return "Update";
/*    */       }
/*    */     },
/* 24 */     DELETE {
/*    */       public String toString() {
/* 26 */         return "Delete";
/*    */       }
/*    */     },
/* 29 */     UNKNOWN {
/*    */       public String toString() {
/* 31 */         return "Unknown";
/*    */       }
/*    */     }; }
/*    */    }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */