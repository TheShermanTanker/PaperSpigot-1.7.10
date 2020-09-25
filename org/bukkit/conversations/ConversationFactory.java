/*     */ package org.bukkit.conversations;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class ConversationFactory
/*     */ {
/*     */   protected Plugin plugin;
/*     */   protected boolean isModal;
/*     */   protected boolean localEchoEnabled;
/*     */   protected ConversationPrefix prefix;
/*     */   protected Prompt firstPrompt;
/*     */   protected Map<Object, Object> initialSessionData;
/*     */   protected String playerOnlyMessage;
/*     */   protected List<ConversationCanceller> cancellers;
/*     */   protected List<ConversationAbandonedListener> abandonedListeners;
/*     */   
/*     */   public ConversationFactory(Plugin plugin) {
/*  40 */     this.plugin = plugin;
/*  41 */     this.isModal = true;
/*  42 */     this.localEchoEnabled = true;
/*  43 */     this.prefix = new NullConversationPrefix();
/*  44 */     this.firstPrompt = Prompt.END_OF_CONVERSATION;
/*  45 */     this.initialSessionData = new HashMap<Object, Object>();
/*  46 */     this.playerOnlyMessage = null;
/*  47 */     this.cancellers = new ArrayList<ConversationCanceller>();
/*  48 */     this.abandonedListeners = new ArrayList<ConversationAbandonedListener>();
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
/*     */   public ConversationFactory withModality(boolean modal) {
/*  63 */     this.isModal = modal;
/*  64 */     return this;
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
/*     */   public ConversationFactory withLocalEcho(boolean localEchoEnabled) {
/*  76 */     this.localEchoEnabled = localEchoEnabled;
/*  77 */     return this;
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
/*     */   public ConversationFactory withPrefix(ConversationPrefix prefix) {
/*  90 */     this.prefix = prefix;
/*  91 */     return this;
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
/*     */   public ConversationFactory withTimeout(int timeoutSeconds) {
/* 104 */     return withConversationCanceller(new InactivityConversationCanceller(this.plugin, timeoutSeconds));
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
/*     */   public ConversationFactory withFirstPrompt(Prompt firstPrompt) {
/* 116 */     this.firstPrompt = firstPrompt;
/* 117 */     return this;
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
/*     */   public ConversationFactory withInitialSessionData(Map<Object, Object> initialSessionData) {
/* 129 */     this.initialSessionData = initialSessionData;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConversationFactory withEscapeSequence(String escapeSequence) {
/* 141 */     return withConversationCanceller(new ExactMatchConversationCanceller(escapeSequence));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConversationFactory withConversationCanceller(ConversationCanceller canceller) {
/* 152 */     this.cancellers.add(canceller);
/* 153 */     return this;
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
/*     */   public ConversationFactory thatExcludesNonPlayersWithMessage(String playerOnlyMessage) {
/* 165 */     this.playerOnlyMessage = playerOnlyMessage;
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConversationFactory addConversationAbandonedListener(ConversationAbandonedListener listener) {
/* 177 */     this.abandonedListeners.add(listener);
/* 178 */     return this;
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
/*     */   public Conversation buildConversation(Conversable forWhom) {
/* 190 */     if (this.playerOnlyMessage != null && !(forWhom instanceof org.bukkit.entity.Player)) {
/* 191 */       return new Conversation(this.plugin, forWhom, new NotPlayerMessagePrompt());
/*     */     }
/*     */ 
/*     */     
/* 195 */     Map<Object, Object> copiedInitialSessionData = new HashMap<Object, Object>();
/* 196 */     copiedInitialSessionData.putAll(this.initialSessionData);
/*     */ 
/*     */     
/* 199 */     Conversation conversation = new Conversation(this.plugin, forWhom, this.firstPrompt, copiedInitialSessionData);
/* 200 */     conversation.setModal(this.isModal);
/* 201 */     conversation.setLocalEchoEnabled(this.localEchoEnabled);
/* 202 */     conversation.setPrefix(this.prefix);
/*     */ 
/*     */     
/* 205 */     for (ConversationCanceller canceller : this.cancellers) {
/* 206 */       conversation.addConversationCanceller(canceller.clone());
/*     */     }
/*     */ 
/*     */     
/* 210 */     for (ConversationAbandonedListener listener : this.abandonedListeners) {
/* 211 */       conversation.addConversationAbandonedListener(listener);
/*     */     }
/*     */     
/* 214 */     return conversation;
/*     */   }
/*     */   
/*     */   private class NotPlayerMessagePrompt
/*     */     extends MessagePrompt {
/*     */     public String getPromptText(ConversationContext context) {
/* 220 */       return ConversationFactory.this.playerOnlyMessage;
/*     */     }
/*     */     private NotPlayerMessagePrompt() {}
/*     */     
/*     */     protected Prompt getNextPrompt(ConversationContext context) {
/* 225 */       return Prompt.END_OF_CONVERSATION;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\ConversationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */