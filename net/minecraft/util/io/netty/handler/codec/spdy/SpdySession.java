/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SpdySession
/*     */ {
/*  31 */   private final Map<Integer, StreamState> activeStreams = PlatformDependent.newConcurrentHashMap();
/*     */   
/*     */   int numActiveStreams() {
/*  34 */     return this.activeStreams.size();
/*     */   }
/*     */   
/*     */   boolean noActiveStreams() {
/*  38 */     return this.activeStreams.isEmpty();
/*     */   }
/*     */   
/*     */   boolean isActiveStream(int streamId) {
/*  42 */     return this.activeStreams.containsKey(Integer.valueOf(streamId));
/*     */   }
/*     */ 
/*     */   
/*     */   Set<Integer> getActiveStreams() {
/*  47 */     TreeSet<Integer> streamIds = new TreeSet<Integer>(new PriorityComparator());
/*  48 */     streamIds.addAll(this.activeStreams.keySet());
/*  49 */     return streamIds;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void acceptStream(int streamId, byte priority, boolean remoteSideClosed, boolean localSideClosed, int sendWindowSize, int receiveWindowSize) {
/*  55 */     if (!remoteSideClosed || !localSideClosed) {
/*  56 */       this.activeStreams.put(Integer.valueOf(streamId), new StreamState(priority, remoteSideClosed, localSideClosed, sendWindowSize, receiveWindowSize));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void removeStream(int streamId, Throwable cause) {
/*  62 */     StreamState state = this.activeStreams.remove(Integer.valueOf(streamId));
/*  63 */     if (state != null) {
/*  64 */       state.clearPendingWrites(cause);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isRemoteSideClosed(int streamId) {
/*  69 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/*  70 */     return (state == null || state.isRemoteSideClosed());
/*     */   }
/*     */   
/*     */   void closeRemoteSide(int streamId) {
/*  74 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/*  75 */     if (state != null) {
/*  76 */       state.closeRemoteSide();
/*  77 */       if (state.isLocalSideClosed()) {
/*  78 */         this.activeStreams.remove(Integer.valueOf(streamId));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isLocalSideClosed(int streamId) {
/*  84 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/*  85 */     return (state == null || state.isLocalSideClosed());
/*     */   }
/*     */   
/*     */   void closeLocalSide(int streamId) {
/*  89 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/*  90 */     if (state != null) {
/*  91 */       state.closeLocalSide();
/*  92 */       if (state.isRemoteSideClosed()) {
/*  93 */         this.activeStreams.remove(Integer.valueOf(streamId));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasReceivedReply(int streamId) {
/* 103 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 104 */     return (state != null && state.hasReceivedReply());
/*     */   }
/*     */   
/*     */   void receivedReply(int streamId) {
/* 108 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 109 */     if (state != null) {
/* 110 */       state.receivedReply();
/*     */     }
/*     */   }
/*     */   
/*     */   int getSendWindowSize(int streamId) {
/* 115 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 116 */     return (state != null) ? state.getSendWindowSize() : -1;
/*     */   }
/*     */   
/*     */   int updateSendWindowSize(int streamId, int deltaWindowSize) {
/* 120 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 121 */     return (state != null) ? state.updateSendWindowSize(deltaWindowSize) : -1;
/*     */   }
/*     */   
/*     */   int updateReceiveWindowSize(int streamId, int deltaWindowSize) {
/* 125 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 126 */     if (deltaWindowSize > 0) {
/* 127 */       state.setReceiveWindowSizeLowerBound(0);
/*     */     }
/* 129 */     return (state != null) ? state.updateReceiveWindowSize(deltaWindowSize) : -1;
/*     */   }
/*     */   
/*     */   int getReceiveWindowSizeLowerBound(int streamId) {
/* 133 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 134 */     return (state != null) ? state.getReceiveWindowSizeLowerBound() : 0;
/*     */   }
/*     */   
/*     */   void updateAllSendWindowSizes(int deltaWindowSize) {
/* 138 */     for (StreamState state : this.activeStreams.values()) {
/* 139 */       state.updateSendWindowSize(deltaWindowSize);
/*     */     }
/*     */   }
/*     */   
/*     */   void updateAllReceiveWindowSizes(int deltaWindowSize) {
/* 144 */     for (StreamState state : this.activeStreams.values()) {
/* 145 */       state.updateReceiveWindowSize(deltaWindowSize);
/* 146 */       if (deltaWindowSize < 0) {
/* 147 */         state.setReceiveWindowSizeLowerBound(deltaWindowSize);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean putPendingWrite(int streamId, PendingWrite pendingWrite) {
/* 153 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 154 */     return (state != null && state.putPendingWrite(pendingWrite));
/*     */   }
/*     */   
/*     */   PendingWrite getPendingWrite(int streamId) {
/* 158 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 159 */     return (state != null) ? state.getPendingWrite() : null;
/*     */   }
/*     */   
/*     */   PendingWrite removePendingWrite(int streamId) {
/* 163 */     StreamState state = this.activeStreams.get(Integer.valueOf(streamId));
/* 164 */     return (state != null) ? state.removePendingWrite() : null;
/*     */   }
/*     */   
/*     */   private static final class StreamState
/*     */   {
/*     */     private final byte priority;
/*     */     private boolean remoteSideClosed;
/*     */     private boolean localSideClosed;
/*     */     private boolean receivedReply;
/*     */     private final AtomicInteger sendWindowSize;
/*     */     private final AtomicInteger receiveWindowSize;
/*     */     private int receiveWindowSizeLowerBound;
/* 176 */     private final Queue<SpdySession.PendingWrite> pendingWriteQueue = new ConcurrentLinkedQueue<SpdySession.PendingWrite>();
/*     */ 
/*     */ 
/*     */     
/*     */     StreamState(byte priority, boolean remoteSideClosed, boolean localSideClosed, int sendWindowSize, int receiveWindowSize) {
/* 181 */       this.priority = priority;
/* 182 */       this.remoteSideClosed = remoteSideClosed;
/* 183 */       this.localSideClosed = localSideClosed;
/* 184 */       this.sendWindowSize = new AtomicInteger(sendWindowSize);
/* 185 */       this.receiveWindowSize = new AtomicInteger(receiveWindowSize);
/*     */     }
/*     */     
/*     */     byte getPriority() {
/* 189 */       return this.priority;
/*     */     }
/*     */     
/*     */     boolean isRemoteSideClosed() {
/* 193 */       return this.remoteSideClosed;
/*     */     }
/*     */     
/*     */     void closeRemoteSide() {
/* 197 */       this.remoteSideClosed = true;
/*     */     }
/*     */     
/*     */     boolean isLocalSideClosed() {
/* 201 */       return this.localSideClosed;
/*     */     }
/*     */     
/*     */     void closeLocalSide() {
/* 205 */       this.localSideClosed = true;
/*     */     }
/*     */     
/*     */     boolean hasReceivedReply() {
/* 209 */       return this.receivedReply;
/*     */     }
/*     */     
/*     */     void receivedReply() {
/* 213 */       this.receivedReply = true;
/*     */     }
/*     */     
/*     */     int getSendWindowSize() {
/* 217 */       return this.sendWindowSize.get();
/*     */     }
/*     */     
/*     */     int updateSendWindowSize(int deltaWindowSize) {
/* 221 */       return this.sendWindowSize.addAndGet(deltaWindowSize);
/*     */     }
/*     */     
/*     */     int updateReceiveWindowSize(int deltaWindowSize) {
/* 225 */       return this.receiveWindowSize.addAndGet(deltaWindowSize);
/*     */     }
/*     */     
/*     */     int getReceiveWindowSizeLowerBound() {
/* 229 */       return this.receiveWindowSizeLowerBound;
/*     */     }
/*     */     
/*     */     void setReceiveWindowSizeLowerBound(int receiveWindowSizeLowerBound) {
/* 233 */       this.receiveWindowSizeLowerBound = receiveWindowSizeLowerBound;
/*     */     }
/*     */     
/*     */     boolean putPendingWrite(SpdySession.PendingWrite msg) {
/* 237 */       return this.pendingWriteQueue.offer(msg);
/*     */     }
/*     */     
/*     */     SpdySession.PendingWrite getPendingWrite() {
/* 241 */       return this.pendingWriteQueue.peek();
/*     */     }
/*     */     
/*     */     SpdySession.PendingWrite removePendingWrite() {
/* 245 */       return this.pendingWriteQueue.poll();
/*     */     }
/*     */     
/*     */     void clearPendingWrites(Throwable cause) {
/*     */       while (true) {
/* 250 */         SpdySession.PendingWrite pendingWrite = this.pendingWriteQueue.poll();
/* 251 */         if (pendingWrite == null) {
/*     */           break;
/*     */         }
/* 254 */         pendingWrite.fail(cause);
/*     */       } 
/*     */     } }
/*     */   
/*     */   private final class PriorityComparator implements Comparator<Integer> {
/*     */     private PriorityComparator() {}
/*     */     
/*     */     public int compare(Integer id1, Integer id2) {
/* 262 */       SpdySession.StreamState state1 = (SpdySession.StreamState)SpdySession.this.activeStreams.get(id1);
/* 263 */       SpdySession.StreamState state2 = (SpdySession.StreamState)SpdySession.this.activeStreams.get(id2);
/* 264 */       return state1.getPriority() - state2.getPriority();
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class PendingWrite {
/*     */     final SpdyDataFrame spdyDataFrame;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     PendingWrite(SpdyDataFrame spdyDataFrame, ChannelPromise promise) {
/* 273 */       this.spdyDataFrame = spdyDataFrame;
/* 274 */       this.promise = promise;
/*     */     }
/*     */     
/*     */     void fail(Throwable cause) {
/* 278 */       this.spdyDataFrame.release();
/* 279 */       this.promise.setFailure(cause);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdySession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */