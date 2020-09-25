/*     */ package org.bukkit.conversations;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Conversation
/*     */ {
/*     */   private Prompt firstPrompt;
/*     */   private boolean abandoned;
/*     */   protected Prompt currentPrompt;
/*     */   protected ConversationContext context;
/*     */   protected boolean modal;
/*     */   protected boolean localEchoEnabled;
/*     */   protected ConversationPrefix prefix;
/*     */   protected List<ConversationCanceller> cancellers;
/*     */   protected List<ConversationAbandonedListener> abandonedListeners;
/*     */   
/*     */   public Conversation(Plugin plugin, Conversable forWhom, Prompt firstPrompt) {
/*  55 */     this(plugin, forWhom, firstPrompt, new HashMap<Object, Object>());
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
/*     */   public Conversation(Plugin plugin, Conversable forWhom, Prompt firstPrompt, Map<Object, Object> initialSessionData) {
/*  68 */     this.firstPrompt = firstPrompt;
/*  69 */     this.context = new ConversationContext(plugin, forWhom, initialSessionData);
/*  70 */     this.modal = true;
/*  71 */     this.localEchoEnabled = true;
/*  72 */     this.prefix = new NullConversationPrefix();
/*  73 */     this.cancellers = new ArrayList<ConversationCanceller>();
/*  74 */     this.abandonedListeners = new ArrayList<ConversationAbandonedListener>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conversable getForWhom() {
/*  83 */     return this.context.getForWhom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModal() {
/*  94 */     return this.modal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setModal(boolean modal) {
/* 105 */     this.modal = modal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalEchoEnabled() {
/* 116 */     return this.localEchoEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalEchoEnabled(boolean localEchoEnabled) {
/* 127 */     this.localEchoEnabled = localEchoEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConversationPrefix getPrefix() {
/* 137 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPrefix(ConversationPrefix prefix) {
/* 147 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addConversationCanceller(ConversationCanceller canceller) {
/* 156 */     canceller.setConversation(this);
/* 157 */     this.cancellers.add(canceller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ConversationCanceller> getCancellers() {
/* 166 */     return this.cancellers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConversationContext getContext() {
/* 175 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void begin() {
/* 183 */     if (this.currentPrompt == null) {
/* 184 */       this.abandoned = false;
/* 185 */       this.currentPrompt = this.firstPrompt;
/* 186 */       this.context.getForWhom().beginConversation(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConversationState getState() {
/* 196 */     if (this.currentPrompt != null)
/* 197 */       return ConversationState.STARTED; 
/* 198 */     if (this.abandoned) {
/* 199 */       return ConversationState.ABANDONED;
/*     */     }
/* 201 */     return ConversationState.UNSTARTED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptInput(String input) {
/*     */     try {
/* 213 */       if (this.currentPrompt != null)
/*     */       {
/*     */         
/* 216 */         if (this.localEchoEnabled) {
/* 217 */           this.context.getForWhom().sendRawMessage(this.prefix.getPrefix(this.context) + input);
/*     */         }
/*     */ 
/*     */         
/* 221 */         for (ConversationCanceller canceller : this.cancellers) {
/* 222 */           if (canceller.cancelBasedOnInput(this.context, input)) {
/* 223 */             abandon(new ConversationAbandonedEvent(this, canceller));
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 229 */         this.currentPrompt = this.currentPrompt.acceptInput(this.context, input);
/* 230 */         outputNextPrompt();
/*     */       }
/*     */     
/* 233 */     } catch (Throwable t) {
/*     */       
/* 235 */       Bukkit.getLogger().log(Level.SEVERE, "Error handling conversation prompt", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addConversationAbandonedListener(ConversationAbandonedListener listener) {
/* 246 */     this.abandonedListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeConversationAbandonedListener(ConversationAbandonedListener listener) {
/* 255 */     this.abandonedListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abandon() {
/* 263 */     abandon(new ConversationAbandonedEvent(this, new ManuallyAbandonedConversationCanceller()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void abandon(ConversationAbandonedEvent details) {
/* 273 */     if (!this.abandoned) {
/* 274 */       this.abandoned = true;
/* 275 */       this.currentPrompt = null;
/* 276 */       this.context.getForWhom().abandonConversation(this);
/* 277 */       for (ConversationAbandonedListener listener : this.abandonedListeners) {
/* 278 */         listener.conversationAbandoned(details);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void outputNextPrompt() {
/* 288 */     if (this.currentPrompt == null) {
/* 289 */       abandon(new ConversationAbandonedEvent(this));
/*     */     } else {
/* 291 */       this.context.getForWhom().sendRawMessage(this.prefix.getPrefix(this.context) + this.currentPrompt.getPromptText(this.context));
/* 292 */       if (!this.currentPrompt.blocksForInput(this.context)) {
/* 293 */         this.currentPrompt = this.currentPrompt.acceptInput(this.context, null);
/* 294 */         outputNextPrompt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum ConversationState {
/* 300 */     UNSTARTED,
/* 301 */     STARTED,
/* 302 */     ABANDONED;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\Conversation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */