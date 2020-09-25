/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.annotation.Nullable;
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
/*     */ final class SortedTreeMultiset<E>
/*     */   extends AbstractSortedMultiset<E>
/*     */ {
/*     */   private final GeneralRange<E> range;
/*     */   private final AtomicReference<Node> rootReference;
/*     */   private final transient BstPathFactory<Node, BstInOrderPath<Node>> pathFactory;
/*     */   private final transient BstAggregate<Node> distinctAggregate;
/*     */   private final transient BstAggregate<Node> sizeAggregate;
/*     */   private final transient BstNodeFactory<Node> nodeFactory;
/*     */   
/*     */   public static <E extends Comparable> SortedTreeMultiset<E> create() {
/*  57 */     return new SortedTreeMultiset<E>(Ordering.natural());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> SortedTreeMultiset<E> create(Comparator<? super E> comparator) {
/*  64 */     return new SortedTreeMultiset<E>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Comparable> SortedTreeMultiset<E> create(Iterable<? extends E> elements) {
/*  73 */     SortedTreeMultiset<E> multiset = create();
/*  74 */     Iterables.addAll(multiset, elements);
/*  75 */     return multiset;
/*     */   }
/*     */   
/*     */   private SortedTreeMultiset(Comparator<? super E> comparator) {
/*  79 */     super(comparator);
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
/* 182 */     this.pathFactory = BstInOrderPath.inOrderFactory();
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
/* 290 */     this.distinctAggregate = new BstAggregate<Node>()
/*     */       {
/*     */         public int entryValue(SortedTreeMultiset<E>.Node entry) {
/* 293 */           return 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public int treeValue(@Nullable SortedTreeMultiset<E>.Node tree) {
/* 298 */           return SortedTreeMultiset.this.distinctOrZero(tree);
/*     */         }
/*     */       };
/*     */     
/* 302 */     this.sizeAggregate = new BstAggregate<Node>()
/*     */       {
/*     */         public int entryValue(SortedTreeMultiset<E>.Node entry) {
/* 305 */           return entry.elemOccurrences;
/*     */         }
/*     */ 
/*     */         
/*     */         public int treeValue(@Nullable SortedTreeMultiset<E>.Node tree) {
/* 310 */           return SortedTreeMultiset.this.sizeOrZero(tree);
/*     */         }
/*     */       };
/*     */     
/* 314 */     this.nodeFactory = new BstNodeFactory<Node>()
/*     */       {
/*     */         public SortedTreeMultiset<E>.Node createNode(SortedTreeMultiset<E>.Node source, @Nullable SortedTreeMultiset<E>.Node left, @Nullable SortedTreeMultiset<E>.Node right) {
/* 317 */           return new SortedTreeMultiset.Node(source.getKey(), source.elemOccurrences, left, right); } }; this.range = GeneralRange.all(comparator); this.rootReference = new AtomicReference<Node>(); } private SortedTreeMultiset(GeneralRange<E> range, AtomicReference<Node> root) { super(range.comparator()); this.pathFactory = BstInOrderPath.inOrderFactory(); this.distinctAggregate = new BstAggregate<Node>() { public int entryValue(SortedTreeMultiset<E>.Node entry) { return 1; } public int treeValue(@Nullable SortedTreeMultiset<E>.Node tree) { return SortedTreeMultiset.this.distinctOrZero(tree); } }; this.sizeAggregate = new BstAggregate<Node>() { public int entryValue(SortedTreeMultiset<E>.Node entry) { return entry.elemOccurrences; } public int treeValue(@Nullable SortedTreeMultiset<E>.Node tree) { return SortedTreeMultiset.this.sizeOrZero(tree); } }; this.nodeFactory = new BstNodeFactory<Node>() { public SortedTreeMultiset<E>.Node createNode(SortedTreeMultiset<E>.Node source, @Nullable SortedTreeMultiset<E>.Node left, @Nullable SortedTreeMultiset<E>.Node right) { return new SortedTreeMultiset.Node(source.getKey(), source.elemOccurrences, left, right); } }; this.range = range; this.rootReference = root; }
/*     */   E checkElement(Object o) { Preconditions.checkNotNull(o); return (E)o; }
/*     */   int distinctElements() { Node root = this.rootReference.get(); return BstRangeOps.totalInRange(this.distinctAggregate, this.range, root); }
/*     */   public int size() { Node root = this.rootReference.get(); return BstRangeOps.totalInRange(this.sizeAggregate, this.range, root); } public int count(@Nullable Object element) { if (element == null) return 0;  try { E e = checkElement(element); if (this.range.contains(e)) { Node node = (Node)BstOperations.<E, BstNode>seek(comparator(), this.rootReference.get(), e); return (node == null) ? 0 : node.elemOccurrences; }  return 0; } catch (ClassCastException e) { return 0; }  } private int mutate(E e, MultisetModifier modifier) { BstMutationRule<E, Node> mutationRule = BstMutationRule.createRule(modifier, BstCountBasedBalancePolicies.singleRebalancePolicy(this.distinctAggregate), this.nodeFactory); BstMutationResult<E, Node> mutationResult = (BstMutationResult)BstOperations.mutate(comparator(), (BstMutationRule)mutationRule, this.rootReference.get(), e); if (!this.rootReference.compareAndSet(mutationResult.getOriginalRoot(), mutationResult.getChangedRoot())) throw new ConcurrentModificationException();  Node original = mutationResult.getOriginalTarget(); return (original == null) ? 0 : original.elemOccurrences; } public int add(E element, int occurrences) { Preconditions.checkNotNull(element); if (occurrences == 0) return count(element);  Preconditions.checkArgument(this.range.contains(element)); return mutate(element, new AddModifier(occurrences)); } public int remove(@Nullable Object element, int occurrences) { if (element == null) return 0;  if (occurrences == 0) return count(element);  try { E e = checkElement(element); return this.range.contains(e) ? mutate(e, new RemoveModifier(occurrences)) : 0; } catch (ClassCastException e) { return 0; }  } public boolean setCount(E element, int oldCount, int newCount) { Preconditions.checkNotNull(element); Preconditions.checkArgument(this.range.contains(element)); return (mutate(element, new ConditionalSetCountModifier(oldCount, newCount)) == oldCount); } public int setCount(E element, int count) { Preconditions.checkNotNull(element); Preconditions.checkArgument(this.range.contains(element)); return mutate(element, new SetCountModifier(count)); } Iterator<Multiset.Entry<E>> entryIterator() { Node root = this.rootReference.get(); BstInOrderPath<Node> startingPath = BstRangeOps.<E, Node, BstInOrderPath<Node>>furthestPath(this.range, BstSide.LEFT, this.pathFactory, root); return iteratorInDirection(startingPath, BstSide.RIGHT); } Iterator<Multiset.Entry<E>> descendingEntryIterator() { Node root = this.rootReference.get(); BstInOrderPath<Node> startingPath = BstRangeOps.<E, Node, BstInOrderPath<Node>>furthestPath(this.range, BstSide.RIGHT, this.pathFactory, root); return iteratorInDirection(startingPath, BstSide.LEFT); } private Iterator<Multiset.Entry<E>> iteratorInDirection(@Nullable BstInOrderPath<Node> start, final BstSide direction) { final Iterator<BstInOrderPath<Node>> pathIterator = new AbstractLinkedIterator<BstInOrderPath<Node>>(start) {
/*     */         protected BstInOrderPath<SortedTreeMultiset<E>.Node> computeNext(BstInOrderPath<SortedTreeMultiset<E>.Node> previous) { if (!previous.hasNext(direction)) return null;  BstInOrderPath<SortedTreeMultiset<E>.Node> next = previous.next(direction); return SortedTreeMultiset.this.range.contains(((SortedTreeMultiset.Node)next.getTip()).getKey()) ? next : null; }
/*     */       }; return (Iterator)new Iterator<Multiset.Entry<Multiset.Entry<E>>>()
/*     */       {
/*     */         E toRemove = null; public boolean hasNext() { return pathIterator.hasNext(); } public Multiset.Entry<E> next() { BstInOrderPath<SortedTreeMultiset<E>.Node> path = pathIterator.next(); return Multisets.immutableEntry(this.toRemove = ((SortedTreeMultiset.Node)path.getTip()).getKey(), (path.getTip()).elemOccurrences); } public void remove() { Preconditions.checkState((this.toRemove != null)); SortedTreeMultiset.this.setCount(this.toRemove, 0); this.toRemove = null; }
/*     */       }; } public void clear() { Node root = this.rootReference.get(); Node cleared = BstRangeOps.<E, Node>minusRange(this.range, BstCountBasedBalancePolicies.fullRebalancePolicy(this.distinctAggregate), this.nodeFactory, root); if (!this.rootReference.compareAndSet(root, cleared)) throw new ConcurrentModificationException();  } public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) { Preconditions.checkNotNull(upperBound); return new SortedTreeMultiset(this.range.intersect(GeneralRange.upTo(this.comparator, upperBound, boundType)), this.rootReference); } public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) { Preconditions.checkNotNull(lowerBound); return new SortedTreeMultiset(this.range.intersect(GeneralRange.downTo(this.comparator, lowerBound, boundType)), this.rootReference); } private final class Node extends BstNode<E, Node> {
/*     */     private final int elemOccurrences; private final int size; private final int distinct; private Node(E key, @Nullable int elemCount, @Nullable Node left, Node right) { super(SortedTreeMultiset.this.checkElement(key), left, right); Preconditions.checkArgument((elemCount > 0)); this.elemOccurrences = elemCount; this.size = elemCount + SortedTreeMultiset.this.sizeOrZero(left) + SortedTreeMultiset.this.sizeOrZero(right); this.distinct = 1 + SortedTreeMultiset.this.distinctOrZero(left) + SortedTreeMultiset.this.distinctOrZero(right); } private Node(E key, int elemCount) { this(key, elemCount, (Node)null, (Node)null); }
/* 327 */   } private int sizeOrZero(@Nullable Node node) { return (node == null) ? 0 : node.size; } private int distinctOrZero(@Nullable Node node) { return (node == null) ? 0 : node.distinct; } private abstract class MultisetModifier implements BstModifier<E, Node> { @Nullable public BstModificationResult<SortedTreeMultiset<E>.Node> modify(E key, @Nullable SortedTreeMultiset<E>.Node originalEntry) { int oldCount = (originalEntry == null) ? 0 : originalEntry.elemOccurrences;
/* 328 */       int newCount = newCount(oldCount);
/* 329 */       if (oldCount == newCount)
/* 330 */         return BstModificationResult.identity(originalEntry); 
/* 331 */       if (newCount == 0)
/* 332 */         return BstModificationResult.rebalancingChange(originalEntry, null); 
/* 333 */       if (oldCount == 0) {
/* 334 */         return BstModificationResult.rebalancingChange(null, new SortedTreeMultiset.Node(key, newCount));
/*     */       }
/* 336 */       return BstModificationResult.rebuildingChange(originalEntry, new SortedTreeMultiset.Node(key, newCount)); }
/*     */     
/*     */     private MultisetModifier() {}
/*     */     
/*     */     abstract int newCount(int param1Int); }
/*     */   
/*     */   private final class AddModifier extends MultisetModifier { private final int countToAdd;
/*     */     
/*     */     private AddModifier(int countToAdd) {
/* 345 */       Preconditions.checkArgument((countToAdd > 0));
/* 346 */       this.countToAdd = countToAdd;
/*     */     }
/*     */ 
/*     */     
/*     */     int newCount(int oldCount) {
/* 351 */       return oldCount + this.countToAdd;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class RemoveModifier extends MultisetModifier {
/*     */     private final int countToRemove;
/*     */     
/*     */     private RemoveModifier(int countToRemove) {
/* 359 */       Preconditions.checkArgument((countToRemove > 0));
/* 360 */       this.countToRemove = countToRemove;
/*     */     }
/*     */ 
/*     */     
/*     */     int newCount(int oldCount) {
/* 365 */       return Math.max(0, oldCount - this.countToRemove);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class SetCountModifier extends MultisetModifier {
/*     */     private final int countToSet;
/*     */     
/*     */     private SetCountModifier(int countToSet) {
/* 373 */       Preconditions.checkArgument((countToSet >= 0));
/* 374 */       this.countToSet = countToSet;
/*     */     }
/*     */ 
/*     */     
/*     */     int newCount(int oldCount) {
/* 379 */       return this.countToSet;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ConditionalSetCountModifier extends MultisetModifier {
/*     */     private final int expectedCount;
/*     */     private final int setCount;
/*     */     
/*     */     private ConditionalSetCountModifier(int expectedCount, int setCount) {
/* 388 */       Preconditions.checkArgument(((setCount >= 0)) & ((expectedCount >= 0)));
/* 389 */       this.expectedCount = expectedCount;
/* 390 */       this.setCount = setCount;
/*     */     }
/*     */ 
/*     */     
/*     */     int newCount(int oldCount) {
/* 395 */       return (oldCount == this.expectedCount) ? this.setCount : oldCount;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedTreeMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */