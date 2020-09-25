/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersistentCollection
/*     */ {
/*     */   private IDataManager a;
/*  20 */   private Map b = new HashMap<Object, Object>();
/*  21 */   public List c = new ArrayList();
/*  22 */   private Map d = new HashMap<Object, Object>();
/*     */   
/*     */   public PersistentCollection(IDataManager idatamanager) {
/*  25 */     this.a = idatamanager;
/*  26 */     b();
/*     */   }
/*     */   
/*     */   public PersistentBase get(Class<PersistentBase> oclass, String s) {
/*  30 */     PersistentBase persistentbase = (PersistentBase)this.b.get(s);
/*     */     
/*  32 */     if (persistentbase != null) {
/*  33 */       return persistentbase;
/*     */     }
/*  35 */     if (this.a != null) {
/*     */       try {
/*  37 */         File file1 = this.a.getDataFile(s);
/*     */         
/*  39 */         if (file1 != null && file1.exists()) {
/*     */           try {
/*  41 */             persistentbase = oclass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { s });
/*  42 */           } catch (Exception exception) {
/*  43 */             throw new RuntimeException("Failed to instantiate " + oclass.toString(), exception);
/*     */           } 
/*     */           
/*  46 */           FileInputStream fileinputstream = new FileInputStream(file1);
/*  47 */           NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(fileinputstream);
/*     */           
/*  49 */           fileinputstream.close();
/*  50 */           persistentbase.a(nbttagcompound.getCompound("data"));
/*     */         } 
/*  52 */       } catch (Exception exception1) {
/*  53 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  57 */     if (persistentbase != null) {
/*  58 */       this.b.put(s, persistentbase);
/*  59 */       this.c.add(persistentbase);
/*     */     } 
/*     */     
/*  62 */     return persistentbase;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(String s, PersistentBase persistentbase) {
/*  67 */     if (persistentbase == null) {
/*  68 */       throw new RuntimeException("Can't set null data");
/*     */     }
/*  70 */     if (this.b.containsKey(s)) {
/*  71 */       this.c.remove(this.b.remove(s));
/*     */     }
/*     */     
/*  74 */     this.b.put(s, persistentbase);
/*  75 */     this.c.add(persistentbase);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  80 */     for (int i = 0; i < this.c.size(); i++) {
/*  81 */       PersistentBase persistentbase = this.c.get(i);
/*     */       
/*  83 */       if (persistentbase.d()) {
/*  84 */         a(persistentbase);
/*  85 */         persistentbase.a(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(PersistentBase persistentbase) {
/*  91 */     if (this.a != null) {
/*     */       try {
/*  93 */         File file1 = this.a.getDataFile(persistentbase.id);
/*     */         
/*  95 */         if (file1 != null) {
/*  96 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */           
/*  98 */           persistentbase.b(nbttagcompound);
/*  99 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */           
/* 101 */           nbttagcompound1.set("data", nbttagcompound);
/* 102 */           FileOutputStream fileoutputstream = new FileOutputStream(file1);
/*     */           
/* 104 */           NBTCompressedStreamTools.a(nbttagcompound1, fileoutputstream);
/* 105 */           fileoutputstream.close();
/*     */         } 
/* 107 */       } catch (Exception exception) {
/* 108 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void b() {
/*     */     try {
/* 115 */       this.d.clear();
/* 116 */       if (this.a == null) {
/*     */         return;
/*     */       }
/*     */       
/* 120 */       File file1 = this.a.getDataFile("idcounts");
/*     */       
/* 122 */       if (file1 != null && file1.exists()) {
/* 123 */         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/* 124 */         NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
/*     */         
/* 126 */         datainputstream.close();
/* 127 */         Iterator<String> iterator = nbttagcompound.c().iterator();
/*     */         
/* 129 */         while (iterator.hasNext()) {
/* 130 */           String s = iterator.next();
/* 131 */           NBTBase nbtbase = nbttagcompound.get(s);
/*     */           
/* 133 */           if (nbtbase instanceof NBTTagShort) {
/* 134 */             NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
/* 135 */             short short1 = nbttagshort.e();
/*     */             
/* 137 */             this.d.put(s, Short.valueOf(short1));
/*     */           } 
/*     */         } 
/*     */       } 
/* 141 */     } catch (Exception exception) {
/* 142 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(String s) {
/* 147 */     Short oshort = (Short)this.d.get(s);
/*     */     
/* 149 */     if (oshort == null) {
/* 150 */       oshort = Short.valueOf((short)0);
/*     */     } else {
/* 152 */       oshort = Short.valueOf((short)(oshort.shortValue() + 1));
/*     */     } 
/*     */     
/* 155 */     this.d.put(s, oshort);
/* 156 */     if (this.a == null) {
/* 157 */       return oshort.shortValue();
/*     */     }
/*     */     try {
/* 160 */       File file1 = this.a.getDataFile("idcounts");
/*     */       
/* 162 */       if (file1 != null) {
/* 163 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 164 */         Iterator<String> iterator = this.d.keySet().iterator();
/*     */         
/* 166 */         while (iterator.hasNext()) {
/* 167 */           String s1 = iterator.next();
/* 168 */           short short1 = ((Short)this.d.get(s1)).shortValue();
/*     */           
/* 170 */           nbttagcompound.setShort(s1, short1);
/*     */         } 
/*     */         
/* 173 */         DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/*     */         
/* 175 */         NBTCompressedStreamTools.a(nbttagcompound, dataoutputstream);
/* 176 */         dataoutputstream.close();
/*     */       } 
/* 178 */     } catch (Exception exception) {
/* 179 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 182 */     return oshort.shortValue();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PersistentCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */