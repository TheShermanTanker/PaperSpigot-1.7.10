/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.Container;
/*     */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*     */ import net.minecraft.server.v1_7_R4.IInventory;
/*     */ import net.minecraft.server.v1_7_R4.Packet;
/*     */ import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
/*     */ import net.minecraft.server.v1_7_R4.Slot;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class CraftContainer extends Container {
/*     */   private final InventoryView view;
/*     */   private InventoryType cachedType;
/*     */   private String cachedTitle;
/*     */   private final int cachedSize;
/*     */   
/*     */   public CraftContainer(InventoryView view, int id) {
/*  22 */     this.view = view;
/*  23 */     this.windowId = id;
/*     */     
/*  25 */     IInventory top = ((CraftInventory)view.getTopInventory()).getInventory();
/*  26 */     IInventory bottom = ((CraftInventory)view.getBottomInventory()).getInventory();
/*  27 */     this.cachedType = view.getType();
/*  28 */     this.cachedTitle = view.getTitle();
/*  29 */     this.cachedSize = getSize();
/*  30 */     setupSlots(top, bottom);
/*     */   }
/*     */   
/*     */   public CraftContainer(Inventory inventory, HumanEntity player, int id) {
/*  34 */     this(new InventoryView(inventory, player)
/*     */         {
/*     */           public Inventory getTopInventory() {
/*  37 */             return inventory;
/*     */           }
/*     */ 
/*     */           
/*     */           public Inventory getBottomInventory() {
/*  42 */             return (Inventory)player.getInventory();
/*     */           }
/*     */ 
/*     */           
/*     */           public HumanEntity getPlayer() {
/*  47 */             return player;
/*     */           }
/*     */ 
/*     */           
/*     */           public InventoryType getType() {
/*  52 */             return inventory.getType();
/*     */           }
/*     */         }id);
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryView getBukkitView() {
/*  59 */     return this.view;
/*     */   }
/*     */   
/*     */   private int getSize() {
/*  63 */     return this.view.getTopInventory().getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/*  68 */     if (this.cachedType == this.view.getType() && this.cachedSize == getSize() && this.cachedTitle.equals(this.view.getTitle())) {
/*  69 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  74 */     boolean typeChanged = (this.cachedType != this.view.getType());
/*  75 */     this.cachedType = this.view.getType();
/*  76 */     this.cachedTitle = this.view.getTitle();
/*  77 */     if (this.view.getPlayer() instanceof CraftPlayer) {
/*  78 */       CraftPlayer player = (CraftPlayer)this.view.getPlayer();
/*  79 */       int type = getNotchInventoryType(this.cachedType);
/*  80 */       IInventory top = ((CraftInventory)this.view.getTopInventory()).getInventory();
/*  81 */       IInventory bottom = ((CraftInventory)this.view.getBottomInventory()).getInventory();
/*  82 */       this.b.clear();
/*  83 */       this.c.clear();
/*  84 */       if (typeChanged) {
/*  85 */         setupSlots(top, bottom);
/*     */       }
/*  87 */       int size = getSize();
/*  88 */       (player.getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutOpenWindow(this.windowId, type, this.cachedTitle, size, true));
/*  89 */       player.updateInventory();
/*     */     } 
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getNotchInventoryType(InventoryType type) {
/*  96 */     switch (type)
/*     */     { case WORKBENCH:
/*  98 */         typeID = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         return typeID;case FURNACE: typeID = 2; return typeID;case DISPENSER: typeID = 3; return typeID;case ENCHANTING: typeID = 4; return typeID;case BREWING: typeID = 5; return typeID;case BEACON: typeID = 7; return typeID;case ANVIL: typeID = 8; return typeID;case HOPPER: typeID = 9; return typeID; }  int typeID = 0; return typeID;
/*     */   }
/*     */   
/*     */   private void setupSlots(IInventory top, IInventory bottom) {
/* 129 */     switch (this.cachedType) {
/*     */ 
/*     */       
/*     */       case PLAYER:
/*     */       case CHEST:
/* 134 */         setupChest(top, bottom);
/*     */         break;
/*     */       case DISPENSER:
/* 137 */         setupDispenser(top, bottom);
/*     */         break;
/*     */       case FURNACE:
/* 140 */         setupFurnace(top, bottom);
/*     */         break;
/*     */       case WORKBENCH:
/*     */       case CRAFTING:
/* 144 */         setupWorkbench(top, bottom);
/*     */         break;
/*     */       case ENCHANTING:
/* 147 */         setupEnchanting(top, bottom);
/*     */         break;
/*     */       case BREWING:
/* 150 */         setupBrewing(top, bottom);
/*     */         break;
/*     */       case HOPPER:
/* 153 */         setupHopper(top, bottom);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupChest(IInventory top, IInventory bottom) {
/* 159 */     int rows = top.getSize() / 9;
/*     */ 
/*     */ 
/*     */     
/* 163 */     int i = (rows - 4) * 18; int row;
/* 164 */     for (row = 0; row < rows; row++) {
/* 165 */       for (int j = 0; j < 9; j++) {
/* 166 */         a(new Slot(top, j + row * 9, 8 + j * 18, 18 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 170 */     for (row = 0; row < 3; row++) {
/* 171 */       for (int j = 0; j < 9; j++) {
/* 172 */         a(new Slot(bottom, j + row * 9 + 9, 8 + j * 18, 103 + row * 18 + i));
/*     */       }
/*     */     } 
/*     */     
/* 176 */     for (int col = 0; col < 9; col++) {
/* 177 */       a(new Slot(bottom, col, 8 + col * 18, 161 + i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupWorkbench(IInventory top, IInventory bottom) {
/* 184 */     a(new Slot(top, 0, 124, 35));
/*     */ 
/*     */     
/*     */     int row;
/*     */     
/* 189 */     for (row = 0; row < 3; row++) {
/* 190 */       for (int i = 0; i < 3; i++) {
/* 191 */         a(new Slot(top, 1 + i + row * 3, 30 + i * 18, 17 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 195 */     for (row = 0; row < 3; row++) {
/* 196 */       for (int i = 0; i < 9; i++) {
/* 197 */         a(new Slot(bottom, i + row * 9 + 9, 8 + i * 18, 84 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 201 */     for (int col = 0; col < 9; col++) {
/* 202 */       a(new Slot(bottom, col, 8 + col * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupFurnace(IInventory top, IInventory bottom) {
/* 209 */     a(new Slot(top, 0, 56, 17));
/* 210 */     a(new Slot(top, 1, 56, 53));
/* 211 */     a(new Slot(top, 2, 116, 35));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     for (int row = 0; row < 3; row++) {
/* 217 */       for (int i = 0; i < 9; i++) {
/* 218 */         a(new Slot(bottom, i + row * 9 + 9, 8 + i * 18, 84 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 222 */     for (int col = 0; col < 9; col++) {
/* 223 */       a(new Slot(bottom, col, 8 + col * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupDispenser(IInventory top, IInventory bottom) {
/*     */     int row;
/* 233 */     for (row = 0; row < 3; row++) {
/* 234 */       for (int i = 0; i < 3; i++) {
/* 235 */         a(new Slot(top, i + row * 3, 61 + i * 18, 17 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 239 */     for (row = 0; row < 3; row++) {
/* 240 */       for (int i = 0; i < 9; i++) {
/* 241 */         a(new Slot(bottom, i + row * 9 + 9, 8 + i * 18, 84 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 245 */     for (int col = 0; col < 9; col++) {
/* 246 */       a(new Slot(bottom, col, 8 + col * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupEnchanting(IInventory top, IInventory bottom) {
/* 253 */     a(new Slot(top, 0, 25, 47));
/*     */     
/*     */     int row;
/*     */     
/* 257 */     for (row = 0; row < 3; row++) {
/* 258 */       for (int i1 = 0; i1 < 9; i1++) {
/* 259 */         a(new Slot(bottom, i1 + row * 9 + 9, 8 + i1 * 18, 84 + row * 18));
/*     */       }
/*     */     } 
/*     */     
/* 263 */     for (row = 0; row < 9; row++) {
/* 264 */       a(new Slot(bottom, row, 8 + row * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupBrewing(IInventory top, IInventory bottom) {
/* 271 */     a(new Slot(top, 0, 56, 46));
/* 272 */     a(new Slot(top, 1, 79, 53));
/* 273 */     a(new Slot(top, 2, 102, 46));
/* 274 */     a(new Slot(top, 3, 79, 17));
/*     */     
/*     */     int i;
/*     */     
/* 278 */     for (i = 0; i < 3; i++) {
/* 279 */       for (int j = 0; j < 9; j++) {
/* 280 */         a(new Slot(bottom, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/* 284 */     for (i = 0; i < 9; i++) {
/* 285 */       a(new Slot(bottom, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupHopper(IInventory top, IInventory bottom) {
/* 292 */     byte b0 = 51;
/*     */     
/*     */     int i;
/*     */     
/* 296 */     for (i = 0; i < top.getSize(); i++) {
/* 297 */       a(new Slot(top, i, 44 + i * 18, 20));
/*     */     }
/*     */     
/* 300 */     for (i = 0; i < 3; i++) {
/* 301 */       for (int j = 0; j < 9; j++) {
/* 302 */         a(new Slot(bottom, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
/*     */       }
/*     */     } 
/*     */     
/* 306 */     for (i = 0; i < 9; i++) {
/* 307 */       a(new Slot(bottom, i, 8 + i * 18, 58 + b0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(EntityHuman entity) {
/* 313 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */