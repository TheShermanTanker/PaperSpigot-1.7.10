/*     */ package org.fusesource.hawtjni.runtime;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class NativeStats
/*     */ {
/*     */   private final HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot;
/*     */   
/*     */   public static class NativeFunction
/*     */     implements Comparable<NativeFunction>
/*     */   {
/*     */     private final int ordinal;
/*     */     private final String name;
/*     */     private int counter;
/*     */     
/*     */     public NativeFunction(int ordinal, String name, int callCount) {
/*  59 */       this.ordinal = ordinal;
/*  60 */       this.name = name;
/*  61 */       this.counter = callCount;
/*     */     }
/*     */     void subtract(NativeFunction func) {
/*  64 */       this.counter -= func.counter;
/*     */     }
/*     */     
/*     */     public int getCounter() {
/*  68 */       return this.counter;
/*     */     }
/*     */     public void setCounter(int counter) {
/*  71 */       this.counter = counter;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  75 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getOrdinal() {
/*  79 */       return this.ordinal;
/*     */     }
/*     */     
/*     */     public int compareTo(NativeFunction func) {
/*  83 */       return func.counter - this.counter;
/*     */     }
/*     */     
/*     */     public void reset() {
/*  87 */       this.counter = 0;
/*     */     }
/*     */     
/*     */     public NativeFunction copy() {
/*  91 */       return new NativeFunction(this.ordinal, this.name, this.counter);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NativeStats(StatsInterface... classes) {
/*  98 */     this(Arrays.asList(classes));
/*     */   }
/*     */   
/*     */   public NativeStats(Collection<StatsInterface> classes) {
/* 102 */     this(snapshot(classes));
/*     */   }
/*     */   
/*     */   private NativeStats(HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot) {
/* 106 */     this.snapshot = snapshot;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 110 */     for (ArrayList<NativeFunction> functions : this.snapshot.values()) {
/* 111 */       for (NativeFunction function : functions) {
/* 112 */         function.reset();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update() {
/* 118 */     for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
/* 119 */       StatsInterface si = entry.getKey();
/* 120 */       for (NativeFunction function : entry.getValue()) {
/* 121 */         function.setCounter(si.functionCounter(function.getOrdinal()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public NativeStats snapshot() {
/* 127 */     NativeStats copy = copy();
/* 128 */     copy.update();
/* 129 */     return copy;
/*     */   }
/*     */   
/*     */   public NativeStats copy() {
/* 133 */     HashMap<StatsInterface, ArrayList<NativeFunction>> rc = new HashMap<StatsInterface, ArrayList<NativeFunction>>(this.snapshot.size() * 2);
/* 134 */     for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
/* 135 */       ArrayList<NativeFunction> list = new ArrayList<NativeFunction>(((ArrayList)entry.getValue()).size());
/* 136 */       for (NativeFunction function : entry.getValue()) {
/* 137 */         list.add(function.copy());
/*     */       }
/* 139 */       rc.put(entry.getKey(), list);
/*     */     } 
/* 141 */     return new NativeStats(rc);
/*     */   }
/*     */   
/*     */   public NativeStats diff() {
/* 145 */     HashMap<StatsInterface, ArrayList<NativeFunction>> rc = new HashMap<StatsInterface, ArrayList<NativeFunction>>(this.snapshot.size() * 2);
/* 146 */     for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
/* 147 */       StatsInterface si = entry.getKey();
/* 148 */       ArrayList<NativeFunction> list = new ArrayList<NativeFunction>(((ArrayList)entry.getValue()).size());
/* 149 */       for (NativeFunction original : entry.getValue()) {
/* 150 */         NativeFunction copy = original.copy();
/* 151 */         copy.setCounter(si.functionCounter(copy.getOrdinal()));
/* 152 */         copy.subtract(original);
/* 153 */         list.add(copy);
/*     */       } 
/* 155 */       rc.put(si, list);
/*     */     } 
/* 157 */     return new NativeStats(rc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(PrintStream ps) {
/* 165 */     boolean firstSI = true;
/* 166 */     for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
/* 167 */       StatsInterface si = entry.getKey();
/* 168 */       ArrayList<NativeFunction> funcs = entry.getValue();
/*     */       
/* 170 */       int total = 0;
/* 171 */       for (NativeFunction func : funcs) {
/* 172 */         total += func.getCounter();
/*     */       }
/*     */       
/* 175 */       if (!firstSI) {
/* 176 */         ps.print(", ");
/*     */       }
/* 178 */       firstSI = false;
/* 179 */       ps.print("[");
/* 180 */       if (total > 0) {
/* 181 */         ps.println("{ ");
/* 182 */         ps.println("  \"class\": \"" + si.getNativeClass() + "\",");
/* 183 */         ps.println("  \"total\": " + total + ", ");
/* 184 */         ps.print("  \"functions\": {");
/* 185 */         boolean firstFunc = true;
/* 186 */         for (NativeFunction func : funcs) {
/* 187 */           if (func.getCounter() > 0) {
/* 188 */             if (!firstFunc) {
/* 189 */               ps.print(",");
/*     */             }
/* 191 */             firstFunc = false;
/* 192 */             ps.println();
/* 193 */             ps.print("    \"" + func.getName() + "\": " + func.getCounter());
/*     */           } 
/*     */         } 
/* 196 */         ps.println();
/* 197 */         ps.println("  }");
/* 198 */         ps.print("}");
/*     */       } 
/* 200 */       ps.print("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot(Collection<StatsInterface> classes) {
/* 205 */     HashMap<StatsInterface, ArrayList<NativeFunction>> rc = new HashMap<StatsInterface, ArrayList<NativeFunction>>();
/* 206 */     for (StatsInterface sc : classes) {
/* 207 */       int count = sc.functionCount();
/* 208 */       ArrayList<NativeFunction> functions = new ArrayList<NativeFunction>(count);
/* 209 */       for (int i = 0; i < count; i++) {
/* 210 */         String name = sc.functionName(i);
/* 211 */         functions.add(new NativeFunction(i, name, 0));
/*     */       } 
/* 213 */       Collections.sort(functions);
/* 214 */       rc.put(sc, functions);
/*     */     } 
/* 216 */     return rc;
/*     */   }
/*     */   
/*     */   public static interface StatsInterface {
/*     */     String getNativeClass();
/*     */     
/*     */     int functionCount();
/*     */     
/*     */     String functionName(int param1Int);
/*     */     
/*     */     int functionCounter(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\NativeStats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */