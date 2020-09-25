/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MarkerManager
/*     */ {
/*  29 */   private static ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap<String, Marker>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Marker getMarker(String name) {
/*  40 */     markerMap.putIfAbsent(name, new Log4jMarker(name));
/*  41 */     return markerMap.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Marker getMarker(String name, String parent) {
/*  52 */     Marker parentMarker = markerMap.get(parent);
/*  53 */     if (parentMarker == null) {
/*  54 */       throw new IllegalArgumentException("Parent Marker " + parent + " has not been defined");
/*     */     }
/*  56 */     return getMarker(name, parentMarker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Marker getMarker(String name, Marker parent) {
/*  66 */     markerMap.putIfAbsent(name, new Log4jMarker(name, parent));
/*  67 */     return markerMap.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Log4jMarker
/*     */     implements Marker
/*     */   {
/*     */     private static final long serialVersionUID = 100L;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final Marker parent;
/*     */     
/*     */     public Log4jMarker(String name) {
/*  81 */       this.name = name;
/*  82 */       this.parent = null;
/*     */     }
/*     */     
/*     */     public Log4jMarker(String name, Marker parent) {
/*  86 */       this.name = name;
/*  87 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  92 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public Marker getParent() {
/*  97 */       return this.parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isInstanceOf(Marker m) {
/* 102 */       if (m == null) {
/* 103 */         throw new IllegalArgumentException("A marker parameter is required");
/*     */       }
/* 105 */       Marker test = this;
/*     */       while (true) {
/* 107 */         if (test == m) {
/* 108 */           return true;
/*     */         }
/* 110 */         test = test.getParent();
/* 111 */         if (test == null)
/* 112 */           return false; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isInstanceOf(String name) {
/* 117 */       if (name == null) {
/* 118 */         throw new IllegalArgumentException("A marker name is required");
/*     */       }
/* 120 */       Marker toTest = this;
/*     */       while (true) {
/* 122 */         if (name.equals(toTest.getName())) {
/* 123 */           return true;
/*     */         }
/* 125 */         toTest = toTest.getParent();
/* 126 */         if (toTest == null)
/* 127 */           return false; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 132 */       if (this == o) {
/* 133 */         return true;
/*     */       }
/* 135 */       if (o == null || !(o instanceof Marker)) {
/* 136 */         return false;
/*     */       }
/*     */       
/* 139 */       Marker marker = (Marker)o;
/*     */       
/* 141 */       if ((this.name != null) ? !this.name.equals(marker.getName()) : (marker.getName() != null)) {
/* 142 */         return false;
/*     */       }
/*     */       
/* 145 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return (this.name != null) ? this.name.hashCode() : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 155 */       StringBuilder sb = new StringBuilder(this.name);
/* 156 */       if (this.parent != null) {
/* 157 */         Marker m = this.parent;
/* 158 */         sb.append("[ ");
/* 159 */         boolean first = true;
/* 160 */         while (m != null) {
/* 161 */           if (!first) {
/* 162 */             sb.append(", ");
/*     */           }
/* 164 */           sb.append(m.getName());
/* 165 */           first = false;
/* 166 */           m = m.getParent();
/*     */         } 
/* 168 */         sb.append(" ]");
/*     */       } 
/* 170 */       return sb.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\MarkerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */