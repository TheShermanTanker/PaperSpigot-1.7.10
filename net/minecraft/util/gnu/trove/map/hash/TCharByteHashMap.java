/*      */ package net.minecraft.util.gnu.trove.map.hash;
/*      */ 
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Map;
/*      */ import net.minecraft.util.gnu.trove.TByteCollection;
/*      */ import net.minecraft.util.gnu.trove.TCharCollection;
/*      */ import net.minecraft.util.gnu.trove.function.TByteFunction;
/*      */ import net.minecraft.util.gnu.trove.impl.HashFunctions;
/*      */ import net.minecraft.util.gnu.trove.impl.hash.TCharByteHash;
/*      */ import net.minecraft.util.gnu.trove.impl.hash.THashPrimitiveIterator;
/*      */ import net.minecraft.util.gnu.trove.impl.hash.TPrimitiveHash;
/*      */ import net.minecraft.util.gnu.trove.iterator.TByteIterator;
/*      */ import net.minecraft.util.gnu.trove.iterator.TCharByteIterator;
/*      */ import net.minecraft.util.gnu.trove.iterator.TCharIterator;
/*      */ import net.minecraft.util.gnu.trove.map.TCharByteMap;
/*      */ import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
/*      */ import net.minecraft.util.gnu.trove.procedure.TCharByteProcedure;
/*      */ import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
/*      */ import net.minecraft.util.gnu.trove.set.TCharSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TCharByteHashMap
/*      */   extends TCharByteHash
/*      */   implements TCharByteMap, Externalizable
/*      */ {
/*      */   static final long serialVersionUID = 1L;
/*      */   protected transient byte[] _values;
/*      */   
/*      */   public TCharByteHashMap() {}
/*      */   
/*      */   public TCharByteHashMap(int initialCapacity) {
/*   73 */     super(initialCapacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TCharByteHashMap(int initialCapacity, float loadFactor) {
/*   86 */     super(initialCapacity, loadFactor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TCharByteHashMap(int initialCapacity, float loadFactor, char noEntryKey, byte noEntryValue) {
/*  104 */     super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TCharByteHashMap(char[] keys, byte[] values) {
/*  116 */     super(Math.max(keys.length, values.length));
/*      */     
/*  118 */     int size = Math.min(keys.length, values.length);
/*  119 */     for (int i = 0; i < size; i++) {
/*  120 */       put(keys[i], values[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TCharByteHashMap(TCharByteMap map) {
/*  132 */     super(map.size());
/*  133 */     if (map instanceof TCharByteHashMap) {
/*  134 */       TCharByteHashMap hashmap = (TCharByteHashMap)map;
/*  135 */       this._loadFactor = hashmap._loadFactor;
/*  136 */       this.no_entry_key = hashmap.no_entry_key;
/*  137 */       this.no_entry_value = hashmap.no_entry_value;
/*      */       
/*  139 */       if (this.no_entry_key != '\000') {
/*  140 */         Arrays.fill(this._set, this.no_entry_key);
/*      */       }
/*      */       
/*  143 */       if (this.no_entry_value != 0) {
/*  144 */         Arrays.fill(this._values, this.no_entry_value);
/*      */       }
/*  146 */       setUp((int)Math.ceil((10.0F / this._loadFactor)));
/*      */     } 
/*  148 */     putAll(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int setUp(int initialCapacity) {
/*  162 */     int capacity = super.setUp(initialCapacity);
/*  163 */     this._values = new byte[capacity];
/*  164 */     return capacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void rehash(int newCapacity) {
/*  175 */     int oldCapacity = this._set.length;
/*      */     
/*  177 */     char[] oldKeys = this._set;
/*  178 */     byte[] oldVals = this._values;
/*  179 */     byte[] oldStates = this._states;
/*      */     
/*  181 */     this._set = new char[newCapacity];
/*  182 */     this._values = new byte[newCapacity];
/*  183 */     this._states = new byte[newCapacity];
/*      */     
/*  185 */     for (int i = oldCapacity; i-- > 0;) {
/*  186 */       if (oldStates[i] == 1) {
/*  187 */         char o = oldKeys[i];
/*  188 */         int index = insertKey(o);
/*  189 */         this._values[index] = oldVals[i];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte put(char key, byte value) {
/*  197 */     int index = insertKey(key);
/*  198 */     return doPut(key, value, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(char key, byte value) {
/*  204 */     int index = insertKey(key);
/*  205 */     if (index < 0)
/*  206 */       return this._values[-index - 1]; 
/*  207 */     return doPut(key, value, index);
/*      */   }
/*      */ 
/*      */   
/*      */   private byte doPut(char key, byte value, int index) {
/*  212 */     byte previous = this.no_entry_value;
/*  213 */     boolean isNewMapping = true;
/*  214 */     if (index < 0) {
/*  215 */       index = -index - 1;
/*  216 */       previous = this._values[index];
/*  217 */       isNewMapping = false;
/*      */     } 
/*  219 */     this._values[index] = value;
/*      */     
/*  221 */     if (isNewMapping) {
/*  222 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  225 */     return previous;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends Byte> map) {
/*  231 */     ensureCapacity(map.size());
/*      */     
/*  233 */     for (Map.Entry<? extends Character, ? extends Byte> entry : map.entrySet()) {
/*  234 */       put(((Character)entry.getKey()).charValue(), ((Byte)entry.getValue()).byteValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(TCharByteMap map) {
/*  241 */     ensureCapacity(map.size());
/*  242 */     TCharByteIterator iter = map.iterator();
/*  243 */     while (iter.hasNext()) {
/*  244 */       iter.advance();
/*  245 */       put(iter.key(), iter.value());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte get(char key) {
/*  252 */     int index = index(key);
/*  253 */     return (index < 0) ? this.no_entry_value : this._values[index];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  259 */     super.clear();
/*  260 */     Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
/*  261 */     Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
/*  262 */     Arrays.fill(this._states, 0, this._states.length, (byte)0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  268 */     return (0 == this._size);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(char key) {
/*  274 */     byte prev = this.no_entry_value;
/*  275 */     int index = index(key);
/*  276 */     if (index >= 0) {
/*  277 */       prev = this._values[index];
/*  278 */       removeAt(index);
/*      */     } 
/*  280 */     return prev;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeAt(int index) {
/*  286 */     this._values[index] = this.no_entry_value;
/*  287 */     super.removeAt(index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TCharSet keySet() {
/*  293 */     return new TKeyView();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] keys() {
/*  299 */     char[] keys = new char[size()];
/*  300 */     char[] k = this._set;
/*  301 */     byte[] states = this._states;
/*      */     
/*  303 */     for (int i = k.length, j = 0; i-- > 0;) {
/*  304 */       if (states[i] == 1) {
/*  305 */         keys[j++] = k[i];
/*      */       }
/*      */     } 
/*  308 */     return keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] keys(char[] array) {
/*  314 */     int size = size();
/*  315 */     if (array.length < size) {
/*  316 */       array = new char[size];
/*      */     }
/*      */     
/*  319 */     char[] keys = this._set;
/*  320 */     byte[] states = this._states;
/*      */     
/*  322 */     for (int i = keys.length, j = 0; i-- > 0;) {
/*  323 */       if (states[i] == 1) {
/*  324 */         array[j++] = keys[i];
/*      */       }
/*      */     } 
/*  327 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TByteCollection valueCollection() {
/*  333 */     return new TValueView();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] values() {
/*  339 */     byte[] vals = new byte[size()];
/*  340 */     byte[] v = this._values;
/*  341 */     byte[] states = this._states;
/*      */     
/*  343 */     for (int i = v.length, j = 0; i-- > 0;) {
/*  344 */       if (states[i] == 1) {
/*  345 */         vals[j++] = v[i];
/*      */       }
/*      */     } 
/*  348 */     return vals;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] values(byte[] array) {
/*  354 */     int size = size();
/*  355 */     if (array.length < size) {
/*  356 */       array = new byte[size];
/*      */     }
/*      */     
/*  359 */     byte[] v = this._values;
/*  360 */     byte[] states = this._states;
/*      */     
/*  362 */     for (int i = v.length, j = 0; i-- > 0;) {
/*  363 */       if (states[i] == 1) {
/*  364 */         array[j++] = v[i];
/*      */       }
/*      */     } 
/*  367 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsValue(byte val) {
/*  373 */     byte[] states = this._states;
/*  374 */     byte[] vals = this._values;
/*      */     
/*  376 */     for (int i = vals.length; i-- > 0;) {
/*  377 */       if (states[i] == 1 && val == vals[i]) {
/*  378 */         return true;
/*      */       }
/*      */     } 
/*  381 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(char key) {
/*  387 */     return contains(key);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TCharByteIterator iterator() {
/*  393 */     return new TCharByteHashIterator(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachKey(TCharProcedure procedure) {
/*  399 */     return forEach(procedure);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachValue(TByteProcedure procedure) {
/*  405 */     byte[] states = this._states;
/*  406 */     byte[] values = this._values;
/*  407 */     for (int i = values.length; i-- > 0;) {
/*  408 */       if (states[i] == 1 && !procedure.execute(values[i])) {
/*  409 */         return false;
/*      */       }
/*      */     } 
/*  412 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachEntry(TCharByteProcedure procedure) {
/*  418 */     byte[] states = this._states;
/*  419 */     char[] keys = this._set;
/*  420 */     byte[] values = this._values;
/*  421 */     for (int i = keys.length; i-- > 0;) {
/*  422 */       if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
/*  423 */         return false;
/*      */       }
/*      */     } 
/*  426 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformValues(TByteFunction function) {
/*  432 */     byte[] states = this._states;
/*  433 */     byte[] values = this._values;
/*  434 */     for (int i = values.length; i-- > 0;) {
/*  435 */       if (states[i] == 1) {
/*  436 */         values[i] = function.execute(values[i]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean retainEntries(TCharByteProcedure procedure) {
/*  444 */     boolean modified = false;
/*  445 */     byte[] states = this._states;
/*  446 */     char[] keys = this._set;
/*  447 */     byte[] values = this._values;
/*      */ 
/*      */ 
/*      */     
/*  451 */     tempDisableAutoCompaction();
/*      */     try {
/*  453 */       for (int i = keys.length; i-- > 0;) {
/*  454 */         if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
/*  455 */           removeAt(i);
/*  456 */           modified = true;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  461 */       reenableAutoCompaction(true);
/*      */     } 
/*      */     
/*  464 */     return modified;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean increment(char key) {
/*  470 */     return adjustValue(key, (byte)1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean adjustValue(char key, byte amount) {
/*  476 */     int index = index(key);
/*  477 */     if (index < 0) {
/*  478 */       return false;
/*      */     }
/*  480 */     this._values[index] = (byte)(this._values[index] + amount);
/*  481 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte adjustOrPutValue(char key, byte adjust_amount, byte put_amount) {
/*      */     byte newValue;
/*      */     boolean isNewMapping;
/*  488 */     int index = insertKey(key);
/*      */ 
/*      */     
/*  491 */     if (index < 0) {
/*  492 */       index = -index - 1;
/*  493 */       newValue = this._values[index] = (byte)(this._values[index] + adjust_amount);
/*  494 */       isNewMapping = false;
/*      */     } else {
/*  496 */       newValue = this._values[index] = put_amount;
/*  497 */       isNewMapping = true;
/*      */     } 
/*      */     
/*  500 */     byte previousState = this._states[index];
/*      */     
/*  502 */     if (isNewMapping) {
/*  503 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  506 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class TKeyView
/*      */     implements TCharSet
/*      */   {
/*      */     public TCharIterator iterator() {
/*  515 */       return new TCharByteHashMap.TCharByteKeyHashIterator((TPrimitiveHash)TCharByteHashMap.this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char getNoEntryValue() {
/*  521 */       return TCharByteHashMap.this.no_entry_key;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/*  527 */       return TCharByteHashMap.this._size;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/*  533 */       return (0 == TCharByteHashMap.this._size);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(char entry) {
/*  539 */       return TCharByteHashMap.this.contains(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char[] toArray() {
/*  545 */       return TCharByteHashMap.this.keys();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char[] toArray(char[] dest) {
/*  551 */       return TCharByteHashMap.this.keys(dest);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean add(char entry) {
/*  561 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(char entry) {
/*  567 */       return (TCharByteHashMap.this.no_entry_value != TCharByteHashMap.this.remove(entry));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAll(Collection<?> collection) {
/*  573 */       for (Object element : collection) {
/*  574 */         if (element instanceof Character) {
/*  575 */           char ele = ((Character)element).charValue();
/*  576 */           if (!TCharByteHashMap.this.containsKey(ele))
/*  577 */             return false; 
/*      */           continue;
/*      */         } 
/*  580 */         return false;
/*      */       } 
/*      */       
/*  583 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAll(TCharCollection collection) {
/*  589 */       TCharIterator iter = collection.iterator();
/*  590 */       while (iter.hasNext()) {
/*  591 */         if (!TCharByteHashMap.this.containsKey(iter.next())) {
/*  592 */           return false;
/*      */         }
/*      */       } 
/*  595 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAll(char[] array) {
/*  601 */       for (char element : array) {
/*  602 */         if (!TCharByteHashMap.this.contains(element)) {
/*  603 */           return false;
/*      */         }
/*      */       } 
/*  606 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Character> collection) {
/*  616 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(TCharCollection collection) {
/*  626 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(char[] array) {
/*  636 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> collection) {
/*  643 */       boolean modified = false;
/*  644 */       TCharIterator iter = iterator();
/*  645 */       while (iter.hasNext()) {
/*  646 */         if (!collection.contains(Character.valueOf(iter.next()))) {
/*  647 */           iter.remove();
/*  648 */           modified = true;
/*      */         } 
/*      */       } 
/*  651 */       return modified;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(TCharCollection collection) {
/*  657 */       if (this == collection) {
/*  658 */         return false;
/*      */       }
/*  660 */       boolean modified = false;
/*  661 */       TCharIterator iter = iterator();
/*  662 */       while (iter.hasNext()) {
/*  663 */         if (!collection.contains(iter.next())) {
/*  664 */           iter.remove();
/*  665 */           modified = true;
/*      */         } 
/*      */       } 
/*  668 */       return modified;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(char[] array) {
/*  674 */       boolean changed = false;
/*  675 */       Arrays.sort(array);
/*  676 */       char[] set = TCharByteHashMap.this._set;
/*  677 */       byte[] states = TCharByteHashMap.this._states;
/*      */       
/*  679 */       for (int i = set.length; i-- > 0;) {
/*  680 */         if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
/*  681 */           TCharByteHashMap.this.removeAt(i);
/*  682 */           changed = true;
/*      */         } 
/*      */       } 
/*  685 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> collection) {
/*  691 */       boolean changed = false;
/*  692 */       for (Object element : collection) {
/*  693 */         if (element instanceof Character) {
/*  694 */           char c = ((Character)element).charValue();
/*  695 */           if (remove(c)) {
/*  696 */             changed = true;
/*      */           }
/*      */         } 
/*      */       } 
/*  700 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(TCharCollection collection) {
/*  706 */       if (this == collection) {
/*  707 */         clear();
/*  708 */         return true;
/*      */       } 
/*  710 */       boolean changed = false;
/*  711 */       TCharIterator iter = collection.iterator();
/*  712 */       while (iter.hasNext()) {
/*  713 */         char element = iter.next();
/*  714 */         if (remove(element)) {
/*  715 */           changed = true;
/*      */         }
/*      */       } 
/*  718 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(char[] array) {
/*  724 */       boolean changed = false;
/*  725 */       for (int i = array.length; i-- > 0;) {
/*  726 */         if (remove(array[i])) {
/*  727 */           changed = true;
/*      */         }
/*      */       } 
/*  730 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/*  736 */       TCharByteHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean forEach(TCharProcedure procedure) {
/*  742 */       return TCharByteHashMap.this.forEachKey(procedure);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object other) {
/*  748 */       if (!(other instanceof TCharSet)) {
/*  749 */         return false;
/*      */       }
/*  751 */       TCharSet that = (TCharSet)other;
/*  752 */       if (that.size() != size()) {
/*  753 */         return false;
/*      */       }
/*  755 */       for (int i = TCharByteHashMap.this._states.length; i-- > 0;) {
/*  756 */         if (TCharByteHashMap.this._states[i] == 1 && 
/*  757 */           !that.contains(TCharByteHashMap.this._set[i])) {
/*  758 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  762 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  768 */       int hashcode = 0;
/*  769 */       for (int i = TCharByteHashMap.this._states.length; i-- > 0;) {
/*  770 */         if (TCharByteHashMap.this._states[i] == 1) {
/*  771 */           hashcode += HashFunctions.hash(TCharByteHashMap.this._set[i]);
/*      */         }
/*      */       } 
/*  774 */       return hashcode;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  780 */       final StringBuilder buf = new StringBuilder("{");
/*  781 */       TCharByteHashMap.this.forEachKey(new TCharProcedure()
/*      */           {
/*      */             private boolean first = true;
/*      */             
/*      */             public boolean execute(char key) {
/*  786 */               if (this.first) {
/*  787 */                 this.first = false;
/*      */               } else {
/*  789 */                 buf.append(", ");
/*      */               } 
/*      */               
/*  792 */               buf.append(key);
/*  793 */               return true;
/*      */             }
/*      */           });
/*  796 */       buf.append("}");
/*  797 */       return buf.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class TValueView
/*      */     implements TByteCollection
/*      */   {
/*      */     public TByteIterator iterator() {
/*  807 */       return new TCharByteHashMap.TCharByteValueHashIterator((TPrimitiveHash)TCharByteHashMap.this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte getNoEntryValue() {
/*  813 */       return TCharByteHashMap.this.no_entry_value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/*  819 */       return TCharByteHashMap.this._size;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/*  825 */       return (0 == TCharByteHashMap.this._size);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(byte entry) {
/*  831 */       return TCharByteHashMap.this.containsValue(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] toArray() {
/*  837 */       return TCharByteHashMap.this.values();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] toArray(byte[] dest) {
/*  843 */       return TCharByteHashMap.this.values(dest);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean add(byte entry) {
/*  849 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(byte entry) {
/*  855 */       byte[] values = TCharByteHashMap.this._values;
/*  856 */       char[] set = TCharByteHashMap.this._set;
/*      */       
/*  858 */       for (int i = values.length; i-- > 0;) {
/*  859 */         if (set[i] != '\000' && set[i] != '\002' && entry == values[i]) {
/*  860 */           TCharByteHashMap.this.removeAt(i);
/*  861 */           return true;
/*      */         } 
/*      */       } 
/*  864 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAll(Collection<?> collection) {
/*  870 */       for (Object element : collection) {
/*  871 */         if (element instanceof Byte) {
/*  872 */           byte ele = ((Byte)element).byteValue();
/*  873 */           if (!TCharByteHashMap.this.containsValue(ele))
/*  874 */             return false; 
/*      */           continue;
/*      */         } 
/*  877 */         return false;
/*      */       } 
/*      */       
/*  880 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAll(TByteCollection collection) {
/*  886 */       TByteIterator iter = collection.iterator();
/*  887 */       while (iter.hasNext()) {
/*  888 */         if (!TCharByteHashMap.this.containsValue(iter.next())) {
/*  889 */           return false;
/*      */         }
/*      */       } 
/*  892 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsAll(byte[] array) {
/*  898 */       for (byte element : array) {
/*  899 */         if (!TCharByteHashMap.this.containsValue(element)) {
/*  900 */           return false;
/*      */         }
/*      */       } 
/*  903 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Byte> collection) {
/*  909 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(TByteCollection collection) {
/*  915 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(byte[] array) {
/*  921 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> collection) {
/*  928 */       boolean modified = false;
/*  929 */       TByteIterator iter = iterator();
/*  930 */       while (iter.hasNext()) {
/*  931 */         if (!collection.contains(Byte.valueOf(iter.next()))) {
/*  932 */           iter.remove();
/*  933 */           modified = true;
/*      */         } 
/*      */       } 
/*  936 */       return modified;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(TByteCollection collection) {
/*  942 */       if (this == collection) {
/*  943 */         return false;
/*      */       }
/*  945 */       boolean modified = false;
/*  946 */       TByteIterator iter = iterator();
/*  947 */       while (iter.hasNext()) {
/*  948 */         if (!collection.contains(iter.next())) {
/*  949 */           iter.remove();
/*  950 */           modified = true;
/*      */         } 
/*      */       } 
/*  953 */       return modified;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(byte[] array) {
/*  959 */       boolean changed = false;
/*  960 */       Arrays.sort(array);
/*  961 */       byte[] values = TCharByteHashMap.this._values;
/*  962 */       byte[] states = TCharByteHashMap.this._states;
/*      */       
/*  964 */       for (int i = values.length; i-- > 0;) {
/*  965 */         if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
/*  966 */           TCharByteHashMap.this.removeAt(i);
/*  967 */           changed = true;
/*      */         } 
/*      */       } 
/*  970 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> collection) {
/*  976 */       boolean changed = false;
/*  977 */       for (Object element : collection) {
/*  978 */         if (element instanceof Byte) {
/*  979 */           byte c = ((Byte)element).byteValue();
/*  980 */           if (remove(c)) {
/*  981 */             changed = true;
/*      */           }
/*      */         } 
/*      */       } 
/*  985 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(TByteCollection collection) {
/*  991 */       if (this == collection) {
/*  992 */         clear();
/*  993 */         return true;
/*      */       } 
/*  995 */       boolean changed = false;
/*  996 */       TByteIterator iter = collection.iterator();
/*  997 */       while (iter.hasNext()) {
/*  998 */         byte element = iter.next();
/*  999 */         if (remove(element)) {
/* 1000 */           changed = true;
/*      */         }
/*      */       } 
/* 1003 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(byte[] array) {
/* 1009 */       boolean changed = false;
/* 1010 */       for (int i = array.length; i-- > 0;) {
/* 1011 */         if (remove(array[i])) {
/* 1012 */           changed = true;
/*      */         }
/*      */       } 
/* 1015 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1021 */       TCharByteHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean forEach(TByteProcedure procedure) {
/* 1027 */       return TCharByteHashMap.this.forEachValue(procedure);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1034 */       final StringBuilder buf = new StringBuilder("{");
/* 1035 */       TCharByteHashMap.this.forEachValue(new TByteProcedure() {
/*      */             private boolean first = true;
/*      */             
/*      */             public boolean execute(byte value) {
/* 1039 */               if (this.first) {
/* 1040 */                 this.first = false;
/*      */               } else {
/* 1042 */                 buf.append(", ");
/*      */               } 
/*      */               
/* 1045 */               buf.append(value);
/* 1046 */               return true;
/*      */             }
/*      */           });
/* 1049 */       buf.append("}");
/* 1050 */       return buf.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class TCharByteKeyHashIterator
/*      */     extends THashPrimitiveIterator
/*      */     implements TCharIterator
/*      */   {
/*      */     TCharByteKeyHashIterator(TPrimitiveHash hash) {
/* 1063 */       super(hash);
/*      */     }
/*      */ 
/*      */     
/*      */     public char next() {
/* 1068 */       moveToNextIndex();
/* 1069 */       return TCharByteHashMap.this._set[this._index];
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1074 */       if (this._expectedSize != this._hash.size()) {
/* 1075 */         throw new ConcurrentModificationException();
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1080 */         this._hash.tempDisableAutoCompaction();
/* 1081 */         TCharByteHashMap.this.removeAt(this._index);
/*      */       } finally {
/*      */         
/* 1084 */         this._hash.reenableAutoCompaction(false);
/*      */       } 
/*      */       
/* 1087 */       this._expectedSize--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class TCharByteValueHashIterator
/*      */     extends THashPrimitiveIterator
/*      */     implements TByteIterator
/*      */   {
/*      */     TCharByteValueHashIterator(TPrimitiveHash hash) {
/* 1101 */       super(hash);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte next() {
/* 1106 */       moveToNextIndex();
/* 1107 */       return TCharByteHashMap.this._values[this._index];
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1112 */       if (this._expectedSize != this._hash.size()) {
/* 1113 */         throw new ConcurrentModificationException();
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1118 */         this._hash.tempDisableAutoCompaction();
/* 1119 */         TCharByteHashMap.this.removeAt(this._index);
/*      */       } finally {
/*      */         
/* 1122 */         this._hash.reenableAutoCompaction(false);
/*      */       } 
/*      */       
/* 1125 */       this._expectedSize--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class TCharByteHashIterator
/*      */     extends THashPrimitiveIterator
/*      */     implements TCharByteIterator
/*      */   {
/*      */     TCharByteHashIterator(TCharByteHashMap map) {
/* 1138 */       super((TPrimitiveHash)map);
/*      */     }
/*      */ 
/*      */     
/*      */     public void advance() {
/* 1143 */       moveToNextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public char key() {
/* 1148 */       return TCharByteHashMap.this._set[this._index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte value() {
/* 1153 */       return TCharByteHashMap.this._values[this._index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte val) {
/* 1158 */       byte old = value();
/* 1159 */       TCharByteHashMap.this._values[this._index] = val;
/* 1160 */       return old;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1165 */       if (this._expectedSize != this._hash.size()) {
/* 1166 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/*      */       try {
/* 1170 */         this._hash.tempDisableAutoCompaction();
/* 1171 */         TCharByteHashMap.this.removeAt(this._index);
/*      */       } finally {
/*      */         
/* 1174 */         this._hash.reenableAutoCompaction(false);
/*      */       } 
/* 1176 */       this._expectedSize--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/* 1184 */     if (!(other instanceof TCharByteMap)) {
/* 1185 */       return false;
/*      */     }
/* 1187 */     TCharByteMap that = (TCharByteMap)other;
/* 1188 */     if (that.size() != size()) {
/* 1189 */       return false;
/*      */     }
/* 1191 */     byte[] values = this._values;
/* 1192 */     byte[] states = this._states;
/* 1193 */     byte this_no_entry_value = getNoEntryValue();
/* 1194 */     byte that_no_entry_value = that.getNoEntryValue();
/* 1195 */     for (int i = values.length; i-- > 0;) {
/* 1196 */       if (states[i] == 1) {
/* 1197 */         char key = this._set[i];
/* 1198 */         byte that_value = that.get(key);
/* 1199 */         byte this_value = values[i];
/* 1200 */         if (this_value != that_value && this_value != this_no_entry_value && that_value != that_no_entry_value)
/*      */         {
/*      */           
/* 1203 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 1207 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1214 */     int hashcode = 0;
/* 1215 */     byte[] states = this._states;
/* 1216 */     for (int i = this._values.length; i-- > 0;) {
/* 1217 */       if (states[i] == 1) {
/* 1218 */         hashcode += HashFunctions.hash(this._set[i]) ^ HashFunctions.hash(this._values[i]);
/*      */       }
/*      */     } 
/*      */     
/* 1222 */     return hashcode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1229 */     final StringBuilder buf = new StringBuilder("{");
/* 1230 */     forEachEntry(new TCharByteProcedure()
/*      */         {
/*      */           public boolean execute(char key, byte value) {
/* 1233 */             if (this.first) { this.first = false; }
/* 1234 */             else { buf.append(", "); }
/*      */             
/* 1236 */             buf.append(key);
/* 1237 */             buf.append("=");
/* 1238 */             buf.append(value);
/* 1239 */             return true;
/*      */           } private boolean first = true;
/*      */         });
/* 1242 */     buf.append("}");
/* 1243 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeExternal(ObjectOutput out) throws IOException {
/* 1250 */     out.writeByte(0);
/*      */ 
/*      */     
/* 1253 */     super.writeExternal(out);
/*      */ 
/*      */     
/* 1256 */     out.writeInt(this._size);
/*      */ 
/*      */     
/* 1259 */     for (int i = this._states.length; i-- > 0;) {
/* 1260 */       if (this._states[i] == 1) {
/* 1261 */         out.writeChar(this._set[i]);
/* 1262 */         out.writeByte(this._values[i]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 1271 */     in.readByte();
/*      */ 
/*      */     
/* 1274 */     super.readExternal(in);
/*      */ 
/*      */     
/* 1277 */     int size = in.readInt();
/* 1278 */     setUp(size);
/*      */ 
/*      */     
/* 1281 */     while (size-- > 0) {
/* 1282 */       char key = in.readChar();
/* 1283 */       byte val = in.readByte();
/* 1284 */       put(key, val);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\hash\TCharByteHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */