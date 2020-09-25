/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Font;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.EtchedBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.Document;
/*     */ 
/*     */ public class ServerGUI extends JComponent {
/*  18 */   private static final Font a = new Font("Monospaced", 0, 12);
/*  19 */   private static final Logger b = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private DedicatedServer c;
/*     */ 
/*     */   
/*     */   public static void a(DedicatedServer paramDedicatedServer) {
/*     */     try {
/*  27 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  28 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  31 */     ServerGUI serverGUI = new ServerGUI(paramDedicatedServer);
/*  32 */     JFrame jFrame = new JFrame("Minecraft server");
/*  33 */     jFrame.add(serverGUI);
/*  34 */     jFrame.pack();
/*  35 */     jFrame.setLocationRelativeTo((Component)null);
/*  36 */     jFrame.setVisible(true);
/*  37 */     jFrame.addWindowListener(new ServerWindowAdapter(paramDedicatedServer));
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
/*     */ 
/*     */   
/*     */   public ServerGUI(DedicatedServer paramDedicatedServer) {
/*  54 */     this.c = paramDedicatedServer;
/*  55 */     setPreferredSize(new Dimension(854, 480));
/*     */     
/*  57 */     setLayout(new BorderLayout());
/*     */     try {
/*  59 */       add(c(), "Center");
/*  60 */       add(a(), "West");
/*  61 */     } catch (Exception exception) {
/*  62 */       b.error("Couldn't build server GUI", exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private JComponent a() {
/*  67 */     JPanel jPanel = new JPanel(new BorderLayout());
/*  68 */     jPanel.add(new GuiStatsComponent(this.c), "North");
/*  69 */     jPanel.add(b(), "Center");
/*  70 */     jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
/*  71 */     return jPanel;
/*     */   }
/*     */   
/*     */   private JComponent b() {
/*  75 */     PlayerListBox playerListBox = new PlayerListBox(this.c);
/*  76 */     JScrollPane jScrollPane = new JScrollPane(playerListBox, 22, 30);
/*  77 */     jScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
/*     */     
/*  79 */     return jScrollPane;
/*     */   }
/*     */   
/*     */   private JComponent c() {
/*  83 */     JPanel jPanel = new JPanel(new BorderLayout());
/*  84 */     JTextArea jTextArea = new JTextArea();
/*  85 */     JScrollPane jScrollPane = new JScrollPane(jTextArea, 22, 30);
/*  86 */     jTextArea.setEditable(false);
/*  87 */     jTextArea.setFont(a);
/*     */     
/*  89 */     JTextField jTextField = new JTextField();
/*  90 */     jTextField.addActionListener(new ServerGuiCommandListener(this, jTextField));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     jTextArea.addFocusListener(new ServerGuiFocusAdapter(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     jPanel.add(jScrollPane, "Center");
/* 108 */     jPanel.add(jTextField, "South");
/* 109 */     jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
/*     */     
/* 111 */     Thread thread = new Thread(new ServerGuiThreadRunnable(this, jTextArea, jScrollPane));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     thread.setDaemon(true);
/* 121 */     thread.start();
/*     */     
/* 123 */     return jPanel;
/*     */   }
/*     */   
/*     */   public void a(JTextArea paramJTextArea, JScrollPane paramJScrollPane, String paramString) {
/* 127 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 128 */       SwingUtilities.invokeLater(new ServerGuiInvokeRunnable(this, paramJTextArea, paramJScrollPane, paramString));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     Document document = paramJTextArea.getDocument();
/* 138 */     JScrollBar jScrollBar = paramJScrollPane.getVerticalScrollBar();
/* 139 */     boolean bool = false;
/*     */     
/* 141 */     if (paramJScrollPane.getViewport().getView() == paramJTextArea) {
/* 142 */       bool = (jScrollBar.getValue() + jScrollBar.getSize().getHeight() + (a.getSize() * 4) > jScrollBar.getMaximum()) ? true : false;
/*     */     }
/*     */     
/*     */     try {
/* 146 */       document.insertString(document.getLength(), paramString, null);
/* 147 */     } catch (BadLocationException badLocationException) {}
/*     */     
/* 149 */     if (bool)
/* 150 */       jScrollBar.setValue(2147483647); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */