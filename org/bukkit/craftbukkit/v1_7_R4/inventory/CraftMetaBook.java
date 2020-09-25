/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ import org.spigotmc.ValidateUtils;
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaBook
/*     */   extends CraftMetaItem
/*     */   implements BookMeta
/*     */ {
/*  26 */   static final CraftMetaItem.ItemMetaKey BOOK_TITLE = new CraftMetaItem.ItemMetaKey("title");
/*  27 */   static final CraftMetaItem.ItemMetaKey BOOK_AUTHOR = new CraftMetaItem.ItemMetaKey("author");
/*  28 */   static final CraftMetaItem.ItemMetaKey BOOK_PAGES = new CraftMetaItem.ItemMetaKey("pages");
/*     */   
/*     */   static final int MAX_PAGE_LENGTH = 256;
/*     */   static final int MAX_TITLE_LENGTH = 65535;
/*     */   private String title;
/*     */   private String author;
/*  34 */   private List<String> pages = new ArrayList<String>();
/*     */   
/*     */   CraftMetaBook(CraftMetaItem meta) {
/*  37 */     super(meta);
/*     */     
/*  39 */     if (!(meta instanceof CraftMetaBook)) {
/*     */       return;
/*     */     }
/*  42 */     CraftMetaBook bookMeta = (CraftMetaBook)meta;
/*  43 */     this.title = bookMeta.title;
/*  44 */     this.author = bookMeta.author;
/*  45 */     this.pages.addAll(bookMeta.pages);
/*     */   }
/*     */   
/*     */   CraftMetaBook(NBTTagCompound tag) {
/*  49 */     super(tag);
/*     */     
/*  51 */     if (tag.hasKey(BOOK_TITLE.NBT)) {
/*  52 */       this.title = ValidateUtils.limit(tag.getString(BOOK_TITLE.NBT), 1024);
/*     */     }
/*     */     
/*  55 */     if (tag.hasKey(BOOK_AUTHOR.NBT)) {
/*  56 */       this.author = ValidateUtils.limit(tag.getString(BOOK_AUTHOR.NBT), 1024);
/*     */     }
/*     */     
/*  59 */     if (tag.hasKey(BOOK_PAGES.NBT)) {
/*  60 */       NBTTagList pages = tag.getList(BOOK_PAGES.NBT, 8);
/*  61 */       String[] pageArray = new String[pages.size()];
/*     */       
/*  63 */       for (int i = 0; i < pages.size(); i++) {
/*  64 */         String page = ValidateUtils.limit(pages.getString(i), 2048);
/*  65 */         pageArray[i] = page;
/*     */       } 
/*     */       
/*  68 */       addPage(pageArray);
/*     */     } 
/*     */   }
/*     */   
/*     */   CraftMetaBook(Map<String, Object> map) {
/*  73 */     super(map);
/*     */     
/*  75 */     setAuthor(CraftMetaItem.SerializableMeta.getString(map, BOOK_AUTHOR.BUKKIT, true));
/*     */     
/*  77 */     setTitle(CraftMetaItem.SerializableMeta.getString(map, BOOK_TITLE.BUKKIT, true));
/*     */     
/*  79 */     Iterable<?> pages = CraftMetaItem.SerializableMeta.<Iterable>getObject(Iterable.class, map, BOOK_PAGES.BUKKIT, true);
/*  80 */     CraftMetaItem.safelyAdd(pages, this.pages, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   void applyToItem(NBTTagCompound itemData) {
/*  85 */     super.applyToItem(itemData);
/*     */     
/*  87 */     if (hasTitle()) {
/*  88 */       itemData.setString(BOOK_TITLE.NBT, this.title);
/*     */     }
/*     */     
/*  91 */     if (hasAuthor()) {
/*  92 */       itemData.setString(BOOK_AUTHOR.NBT, this.author);
/*     */     }
/*     */     
/*  95 */     if (hasPages()) {
/*  96 */       itemData.set(BOOK_PAGES.NBT, (NBTBase)createStringList(this.pages));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/* 102 */     return (super.isEmpty() && isBookEmpty());
/*     */   }
/*     */   
/*     */   boolean isBookEmpty() {
/* 106 */     return (!hasPages() && !hasAuthor() && !hasTitle());
/*     */   }
/*     */ 
/*     */   
/*     */   boolean applicableTo(Material type) {
/* 111 */     switch (type) {
/*     */       case WRITTEN_BOOK:
/*     */       case BOOK_AND_QUILL:
/* 114 */         return true;
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAuthor() {
/* 121 */     return !Strings.isNullOrEmpty(this.author);
/*     */   }
/*     */   
/*     */   public boolean hasTitle() {
/* 125 */     return !Strings.isNullOrEmpty(this.title);
/*     */   }
/*     */   
/*     */   public boolean hasPages() {
/* 129 */     return !this.pages.isEmpty();
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 133 */     return this.title;
/*     */   }
/*     */   
/*     */   public boolean setTitle(String title) {
/* 137 */     if (title == null) {
/* 138 */       this.title = null;
/* 139 */       return true;
/* 140 */     }  if (title.length() > 65535) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     this.title = title;
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   public String getAuthor() {
/* 149 */     return this.author;
/*     */   }
/*     */   
/*     */   public void setAuthor(String author) {
/* 153 */     this.author = author;
/*     */   }
/*     */   
/*     */   public String getPage(int page) {
/* 157 */     Validate.isTrue(isValidPage(page), "Invalid page number");
/* 158 */     return this.pages.get(page - 1);
/*     */   }
/*     */   
/*     */   public void setPage(int page, String text) {
/* 162 */     if (!isValidPage(page)) {
/* 163 */       throw new IllegalArgumentException("Invalid page number " + page + "/" + this.pages.size());
/*     */     }
/*     */     
/* 166 */     this.pages.set(page - 1, (text == null) ? "" : ((text.length() > 256) ? text.substring(0, 256) : text));
/*     */   }
/*     */   
/*     */   public void setPages(String... pages) {
/* 170 */     this.pages.clear();
/*     */     
/* 172 */     addPage(pages);
/*     */   }
/*     */   
/*     */   public void addPage(String... pages) {
/* 176 */     for (String page : pages) {
/* 177 */       if (page == null) {
/* 178 */         page = "";
/* 179 */       } else if (page.length() > 256) {
/* 180 */         page = page.substring(0, 256);
/*     */       } 
/*     */       
/* 183 */       this.pages.add(page);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getPageCount() {
/* 188 */     return this.pages.size();
/*     */   }
/*     */   
/*     */   public List<String> getPages() {
/* 192 */     return (List<String>)ImmutableList.copyOf(this.pages);
/*     */   }
/*     */   
/*     */   public void setPages(List<String> pages) {
/* 196 */     this.pages.clear();
/* 197 */     CraftMetaItem.safelyAdd(pages, this.pages, 256);
/*     */   }
/*     */   
/*     */   private boolean isValidPage(int page) {
/* 201 */     return (page > 0 && page <= this.pages.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftMetaBook clone() {
/* 206 */     CraftMetaBook meta = (CraftMetaBook)super.clone();
/* 207 */     meta.pages = new ArrayList<String>(this.pages);
/* 208 */     return meta;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int applyHash() {
/* 214 */     int original = super.applyHash(), hash = original;
/* 215 */     if (hasTitle()) {
/* 216 */       hash = 61 * hash + this.title.hashCode();
/*     */     }
/* 218 */     if (hasAuthor()) {
/* 219 */       hash = 61 * hash + 13 * this.author.hashCode();
/*     */     }
/* 221 */     if (hasPages()) {
/* 222 */       hash = 61 * hash + 17 * this.pages.hashCode();
/*     */     }
/* 224 */     return (original != hash) ? (CraftMetaBook.class.hashCode() ^ hash) : hash;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta) {
/* 229 */     if (!super.equalsCommon(meta)) {
/* 230 */       return false;
/*     */     }
/* 232 */     if (meta instanceof CraftMetaBook) {
/* 233 */       CraftMetaBook that = (CraftMetaBook)meta;
/*     */       
/* 235 */       if (hasTitle() ? (that.hasTitle() && this.title.equals(that.title)) : !that.hasTitle()) if ((hasAuthor() ? (that.hasAuthor() && this.author.equals(that.author)) : !that.hasAuthor()) && (hasPages() ? (that.hasPages() && this.pages.equals(that.pages)) : !that.hasPages()));  return false;
/*     */     } 
/*     */ 
/*     */     
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta) {
/* 244 */     return (super.notUncommon(meta) && (meta instanceof CraftMetaBook || isBookEmpty()));
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/* 249 */     super.serialize(builder);
/*     */     
/* 251 */     if (hasTitle()) {
/* 252 */       builder.put(BOOK_TITLE.BUKKIT, this.title);
/*     */     }
/*     */     
/* 255 */     if (hasAuthor()) {
/* 256 */       builder.put(BOOK_AUTHOR.BUKKIT, this.author);
/*     */     }
/*     */     
/* 259 */     if (hasPages()) {
/* 260 */       builder.put(BOOK_PAGES.BUKKIT, this.pages);
/*     */     }
/*     */     
/* 263 */     return builder;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */