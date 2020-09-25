/*     */ package net.minecraft.util.gnu.trove.set.hash;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.util.gnu.trove.TByteCollection;
/*     */ import net.minecraft.util.gnu.trove.impl.HashFunctions;
/*     */ import net.minecraft.util.gnu.trove.impl.hash.TByteHash;
/*     */ import net.minecraft.util.gnu.trove.impl.hash.THashPrimitiveIterator;
/*     */ import net.minecraft.util.gnu.trove.impl.hash.TPrimitiveHash;
/*     */ import net.minecraft.util.gnu.trove.iterator.TByteIterator;
/*     */ import net.minecraft.util.gnu.trove.set.TByteSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TByteHashSet
/*     */   extends TByteHash
/*     */   implements TByteSet, Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   
/*     */   public TByteHashSet() {}
/*     */   
/*     */   public TByteHashSet(int initialCapacity) {
/*  71 */     super(initialCapacity);
/*     */   }
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
/*     */   public TByteHashSet(int initialCapacity, float load_factor) {
/*  84 */     super(initialCapacity, load_factor);
/*     */   }
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
/*     */   public TByteHashSet(int initial_capacity, float load_factor, byte no_entry_value) {
/*  99 */     super(initial_capacity, load_factor, no_entry_value);
/*     */     
/* 101 */     if (no_entry_value != 0) {
/* 102 */       Arrays.fill(this._set, no_entry_value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TByteHashSet(Collection<? extends Byte> collection) {
/* 114 */     this(Math.max(collection.size(), 10));
/* 115 */     addAll(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TByteHashSet(TByteCollection collection) {
/* 126 */     this(Math.max(collection.size(), 10));
/* 127 */     if (collection instanceof TByteHashSet) {
/* 128 */       TByteHashSet hashset = (TByteHashSet)collection;
/* 129 */       this._loadFactor = hashset._loadFactor;
/* 130 */       this.no_entry_value = hashset.no_entry_value;
/*     */       
/* 132 */       if (this.no_entry_value != 0) {
/* 133 */         Arrays.fill(this._set, this.no_entry_value);
/*     */       }
/* 135 */       setUp((int)Math.ceil((10.0F / this._loadFactor)));
/*     */     } 
/* 137 */     addAll(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TByteHashSet(byte[] array) {
/* 148 */     this(Math.max(array.length, 10));
/* 149 */     addAll(array);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TByteIterator iterator() {
/* 155 */     return new TByteHashIterator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toArray() {
/* 161 */     byte[] result = new byte[size()];
/* 162 */     byte[] set = this._set;
/* 163 */     byte[] states = this._states;
/*     */     
/* 165 */     for (int i = states.length, j = 0; i-- > 0;) {
/* 166 */       if (states[i] == 1) {
/* 167 */         result[j++] = set[i];
/*     */       }
/*     */     } 
/* 170 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toArray(byte[] dest) {
/* 176 */     byte[] set = this._set;
/* 177 */     byte[] states = this._states;
/*     */     
/* 179 */     for (int i = states.length, j = 0; i-- > 0;) {
/* 180 */       if (states[i] == 1) {
/* 181 */         dest[j++] = set[i];
/*     */       }
/*     */     } 
/*     */     
/* 185 */     if (dest.length > this._size) {
/* 186 */       dest[this._size] = this.no_entry_value;
/*     */     }
/* 188 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(byte val) {
/* 194 */     int index = insertKey(val);
/*     */     
/* 196 */     if (index < 0) {
/* 197 */       return false;
/*     */     }
/*     */     
/* 200 */     postInsertHook(this.consumeFreeSlot);
/*     */     
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(byte val) {
/* 208 */     int index = index(val);
/* 209 */     if (index >= 0) {
/* 210 */       removeAt(index);
/* 211 */       return true;
/*     */     } 
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> collection) {
/* 219 */     for (Object element : collection) {
/* 220 */       if (element instanceof Byte) {
/* 221 */         byte c = ((Byte)element).byteValue();
/* 222 */         if (!contains(c))
/* 223 */           return false; 
/*     */         continue;
/*     */       } 
/* 226 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(TByteCollection collection) {
/* 236 */     TByteIterator iter = collection.iterator();
/* 237 */     while (iter.hasNext()) {
/* 238 */       byte element = iter.next();
/* 239 */       if (!contains(element)) {
/* 240 */         return false;
/*     */       }
/*     */     } 
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(byte[] array) {
/* 249 */     for (int i = array.length; i-- > 0;) {
/* 250 */       if (!contains(array[i])) {
/* 251 */         return false;
/*     */       }
/*     */     } 
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Byte> collection) {
/* 260 */     boolean changed = false;
/* 261 */     for (Byte element : collection) {
/* 262 */       byte e = element.byteValue();
/* 263 */       if (add(e)) {
/* 264 */         changed = true;
/*     */       }
/*     */     } 
/* 267 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(TByteCollection collection) {
/* 273 */     boolean changed = false;
/* 274 */     TByteIterator iter = collection.iterator();
/* 275 */     while (iter.hasNext()) {
/* 276 */       byte element = iter.next();
/* 277 */       if (add(element)) {
/* 278 */         changed = true;
/*     */       }
/*     */     } 
/* 281 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(byte[] array) {
/* 287 */     boolean changed = false;
/* 288 */     for (int i = array.length; i-- > 0;) {
/* 289 */       if (add(array[i])) {
/* 290 */         changed = true;
/*     */       }
/*     */     } 
/* 293 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> collection) {
/* 300 */     boolean modified = false;
/* 301 */     TByteIterator iter = iterator();
/* 302 */     while (iter.hasNext()) {
/* 303 */       if (!collection.contains(Byte.valueOf(iter.next()))) {
/* 304 */         iter.remove();
/* 305 */         modified = true;
/*     */       } 
/*     */     } 
/* 308 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(TByteCollection collection) {
/* 314 */     if (this == collection) {
/* 315 */       return false;
/*     */     }
/* 317 */     boolean modified = false;
/* 318 */     TByteIterator iter = iterator();
/* 319 */     while (iter.hasNext()) {
/* 320 */       if (!collection.contains(iter.next())) {
/* 321 */         iter.remove();
/* 322 */         modified = true;
/*     */       } 
/*     */     } 
/* 325 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(byte[] array) {
/* 331 */     boolean changed = false;
/* 332 */     Arrays.sort(array);
/* 333 */     byte[] set = this._set;
/* 334 */     byte[] states = this._states;
/*     */     
/* 336 */     this._autoCompactTemporaryDisable = true;
/* 337 */     for (int i = set.length; i-- > 0;) {
/* 338 */       if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
/* 339 */         removeAt(i);
/* 340 */         changed = true;
/*     */       } 
/*     */     } 
/* 343 */     this._autoCompactTemporaryDisable = false;
/*     */     
/* 345 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> collection) {
/* 351 */     boolean changed = false;
/* 352 */     for (Object element : collection) {
/* 353 */       if (element instanceof Byte) {
/* 354 */         byte c = ((Byte)element).byteValue();
/* 355 */         if (remove(c)) {
/* 356 */           changed = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 360 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(TByteCollection collection) {
/* 366 */     boolean changed = false;
/* 367 */     TByteIterator iter = collection.iterator();
/* 368 */     while (iter.hasNext()) {
/* 369 */       byte element = iter.next();
/* 370 */       if (remove(element)) {
/* 371 */         changed = true;
/*     */       }
/*     */     } 
/* 374 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(byte[] array) {
/* 380 */     boolean changed = false;
/* 381 */     for (int i = array.length; i-- > 0;) {
/* 382 */       if (remove(array[i])) {
/* 383 */         changed = true;
/*     */       }
/*     */     } 
/* 386 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 392 */     super.clear();
/* 393 */     byte[] set = this._set;
/* 394 */     byte[] states = this._states;
/*     */     
/* 396 */     for (int i = set.length; i-- > 0; ) {
/* 397 */       set[i] = this.no_entry_value;
/* 398 */       states[i] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash(int newCapacity) {
/* 405 */     int oldCapacity = this._set.length;
/*     */     
/* 407 */     byte[] oldSet = this._set;
/* 408 */     byte[] oldStates = this._states;
/*     */     
/* 410 */     this._set = new byte[newCapacity];
/* 411 */     this._states = new byte[newCapacity];
/*     */     
/* 413 */     for (int i = oldCapacity; i-- > 0;) {
/* 414 */       if (oldStates[i] == 1) {
/* 415 */         byte o = oldSet[i];
/* 416 */         int index = insertKey(o);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 424 */     if (!(other instanceof TByteSet)) {
/* 425 */       return false;
/*     */     }
/* 427 */     TByteSet that = (TByteSet)other;
/* 428 */     if (that.size() != size()) {
/* 429 */       return false;
/*     */     }
/* 431 */     for (int i = this._states.length; i-- > 0;) {
/* 432 */       if (this._states[i] == 1 && 
/* 433 */         !that.contains(this._set[i])) {
/* 434 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 438 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 444 */     int hashcode = 0;
/* 445 */     for (int i = this._states.length; i-- > 0;) {
/* 446 */       if (this._states[i] == 1) {
/* 447 */         hashcode += HashFunctions.hash(this._set[i]);
/*     */       }
/*     */     } 
/* 450 */     return hashcode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 456 */     StringBuilder buffy = new StringBuilder(this._size * 2 + 2);
/* 457 */     buffy.append("{");
/* 458 */     for (int i = this._states.length, j = 1; i-- > 0;) {
/* 459 */       if (this._states[i] == 1) {
/* 460 */         buffy.append(this._set[i]);
/* 461 */         if (j++ < this._size) {
/* 462 */           buffy.append(",");
/*     */         }
/*     */       } 
/*     */     } 
/* 466 */     buffy.append("}");
/* 467 */     return buffy.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   class TByteHashIterator
/*     */     extends THashPrimitiveIterator
/*     */     implements TByteIterator
/*     */   {
/*     */     private final TByteHash _hash;
/*     */     
/*     */     public TByteHashIterator(TByteHash hash) {
/* 478 */       super((TPrimitiveHash)hash);
/* 479 */       this._hash = hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte next() {
/* 484 */       moveToNextIndex();
/* 485 */       return this._hash._set[this._index];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 494 */     out.writeByte(1);
/*     */ 
/*     */     
/* 497 */     super.writeExternal(out);
/*     */ 
/*     */     
/* 500 */     out.writeInt(this._size);
/*     */ 
/*     */     
/* 503 */     out.writeFloat(this._loadFactor);
/*     */ 
/*     */     
/* 506 */     out.writeByte(this.no_entry_value);
/*     */ 
/*     */     
/* 509 */     for (int i = this._states.length; i-- > 0;) {
/* 510 */       if (this._states[i] == 1) {
/* 511 */         out.writeByte(this._set[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 522 */     int version = in.readByte();
/*     */ 
/*     */     
/* 525 */     super.readExternal(in);
/*     */ 
/*     */     
/* 528 */     int size = in.readInt();
/*     */     
/* 530 */     if (version >= 1) {
/*     */       
/* 532 */       this._loadFactor = in.readFloat();
/*     */ 
/*     */       
/* 535 */       this.no_entry_value = in.readByte();
/*     */       
/* 537 */       if (this.no_entry_value != 0) {
/* 538 */         Arrays.fill(this._set, this.no_entry_value);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 543 */     setUp(size);
/* 544 */     while (size-- > 0) {
/* 545 */       byte val = in.readByte();
/* 546 */       add(val);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\set\hash\TByteHashSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */