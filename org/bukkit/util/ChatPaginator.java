/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatPaginator
/*     */ {
/*     */   public static final int GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH = 55;
/*     */   public static final int AVERAGE_CHAT_PAGE_WIDTH = 65;
/*     */   public static final int UNBOUNDED_PAGE_WIDTH = 2147483647;
/*     */   public static final int OPEN_CHAT_PAGE_HEIGHT = 20;
/*     */   public static final int CLOSED_CHAT_PAGE_HEIGHT = 10;
/*     */   public static final int UNBOUNDED_PAGE_HEIGHT = 2147483647;
/*     */   
/*     */   public static ChatPage paginate(String unpaginatedString, int pageNumber) {
/*  29 */     return paginate(unpaginatedString, pageNumber, 55, 10);
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
/*     */   public static ChatPage paginate(String unpaginatedString, int pageNumber, int lineLength, int pageHeight) {
/*  42 */     String[] lines = wordWrap(unpaginatedString, lineLength);
/*     */     
/*  44 */     int totalPages = lines.length / pageHeight + ((lines.length % pageHeight == 0) ? 0 : 1);
/*  45 */     int actualPageNumber = (pageNumber <= totalPages) ? pageNumber : totalPages;
/*     */     
/*  47 */     int from = (actualPageNumber - 1) * pageHeight;
/*  48 */     int to = (from + pageHeight <= lines.length) ? (from + pageHeight) : lines.length;
/*  49 */     String[] selectedLines = Java15Compat.<String>Arrays_copyOfRange(lines, from, to);
/*     */     
/*  51 */     return new ChatPage(selectedLines, actualPageNumber, totalPages);
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
/*     */   public static String[] wordWrap(String rawString, int lineLength) {
/*  64 */     if (rawString == null) {
/*  65 */       return new String[] { "" };
/*     */     }
/*     */ 
/*     */     
/*  69 */     if (rawString.length() <= lineLength && !rawString.contains("\n")) {
/*  70 */       return new String[] { rawString };
/*     */     }
/*     */     
/*  73 */     char[] rawChars = (rawString + ' ').toCharArray();
/*  74 */     StringBuilder word = new StringBuilder();
/*  75 */     StringBuilder line = new StringBuilder();
/*  76 */     List<String> lines = new LinkedList<String>();
/*  77 */     int lineColorChars = 0;
/*     */     int i;
/*  79 */     for (i = 0; i < rawChars.length; i++) {
/*  80 */       char c = rawChars[i];
/*     */ 
/*     */       
/*  83 */       if (c == '§') {
/*  84 */         word.append(ChatColor.getByChar(rawChars[i + 1]));
/*  85 */         lineColorChars += 2;
/*  86 */         i++;
/*     */ 
/*     */       
/*     */       }
/*  90 */       else if (c == ' ' || c == '\n') {
/*  91 */         if (line.length() == 0 && word.length() > lineLength) {
/*  92 */           for (String partialWord : word.toString().split("(?<=\\G.{" + lineLength + "})")) {
/*  93 */             lines.add(partialWord);
/*     */           }
/*  95 */         } else if (line.length() + word.length() - lineColorChars == lineLength) {
/*  96 */           line.append(word);
/*  97 */           lines.add(line.toString());
/*  98 */           line = new StringBuilder();
/*  99 */           lineColorChars = 0;
/* 100 */         } else if (line.length() + 1 + word.length() - lineColorChars > lineLength) {
/* 101 */           for (String partialWord : word.toString().split("(?<=\\G.{" + lineLength + "})")) {
/* 102 */             lines.add(line.toString());
/* 103 */             line = new StringBuilder(partialWord);
/*     */           } 
/* 105 */           lineColorChars = 0;
/*     */         } else {
/* 107 */           if (line.length() > 0) {
/* 108 */             line.append(' ');
/*     */           }
/* 110 */           line.append(word);
/*     */         } 
/* 112 */         word = new StringBuilder();
/*     */         
/* 114 */         if (c == '\n') {
/* 115 */           lines.add(line.toString());
/* 116 */           line = new StringBuilder();
/*     */         } 
/*     */       } else {
/* 119 */         word.append(c);
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     if (line.length() > 0) {
/* 124 */       lines.add(line.toString());
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (((String)lines.get(0)).length() == 0 || ((String)lines.get(0)).charAt(0) != '§') {
/* 129 */       lines.set(0, ChatColor.WHITE + (String)lines.get(0));
/*     */     }
/* 131 */     for (i = 1; i < lines.size(); i++) {
/* 132 */       String pLine = lines.get(i - 1);
/* 133 */       String subLine = lines.get(i);
/*     */       
/* 135 */       char color = pLine.charAt(pLine.lastIndexOf('§') + 1);
/* 136 */       if (subLine.length() == 0 || subLine.charAt(0) != '§') {
/* 137 */         lines.set(i, ChatColor.getByChar(color) + subLine);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return lines.<String>toArray(new String[lines.size()]);
/*     */   }
/*     */   
/*     */   public static class ChatPage
/*     */   {
/*     */     private String[] lines;
/*     */     private int pageNumber;
/*     */     private int totalPages;
/*     */     
/*     */     public ChatPage(String[] lines, int pageNumber, int totalPages) {
/* 151 */       this.lines = lines;
/* 152 */       this.pageNumber = pageNumber;
/* 153 */       this.totalPages = totalPages;
/*     */     }
/*     */     
/*     */     public int getPageNumber() {
/* 157 */       return this.pageNumber;
/*     */     }
/*     */     
/*     */     public int getTotalPages() {
/* 161 */       return this.totalPages;
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] getLines() {
/* 166 */       return this.lines;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\ChatPaginator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */