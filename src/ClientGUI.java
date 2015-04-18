import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;


public class ClientGUI extends JFrame {

    private ChatClient client;
    
    private JPanel contentPane;
    private JTextField tfText;
    private JTextArea taChat;
    private JTextField tfDST;

    /**
     * Create the frame.
     */
    public ClientGUI(ChatClient pClient){
        client = pClient;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        
        taChat = new JTextArea();
        taChat.setEditable(false);
        GridBagConstraints gbc_taChat = new GridBagConstraints();
        gbc_taChat.insets = new Insets(0, 0, 5, 5);
        gbc_taChat.fill = GridBagConstraints.BOTH;
        gbc_taChat.gridx = 0;
        gbc_taChat.gridy = 0;
        contentPane.add(taChat, gbc_taChat);
        
        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 0;
        contentPane.add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        Button btnList = new Button("list users");
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send("LIST");
            }
        });
        btnList.setActionCommand("LIST");
        GridBagConstraints gbc_btnList = new GridBagConstraints();
        gbc_btnList.fill = GridBagConstraints.BOTH;
        gbc_btnList.insets = new Insets(0, 0, 5, 0);
        gbc_btnList.gridx = 0;
        gbc_btnList.gridy = 0;
        panel.add(btnList, gbc_btnList);
        
        Button btnQuit = new Button("quit");
        btnQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send("QUIT");
            }
        });
        btnQuit.setActionCommand("QUIT");
        GridBagConstraints gbc_btnQuit = new GridBagConstraints();
        gbc_btnQuit.fill = GridBagConstraints.BOTH;
        gbc_btnQuit.insets = new Insets(0, 0, 5, 0);
        gbc_btnQuit.gridx = 0;
        gbc_btnQuit.gridy = 1;
        panel.add(btnQuit, gbc_btnQuit);
        
        Panel panel_1 = new Panel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.insets = new Insets(0, 0, 0, 5);
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;
        contentPane.add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{72, 31, 72, 38, 63, 0};
        gbl_panel_1.rowHeights = new int[]{22, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);
        
        tfText = new JTextField();
        GridBagConstraints gbc_tfText = new GridBagConstraints();
        gbc_tfText.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfText.insets = new Insets(0, 0, 0, 5);
        gbc_tfText.gridx = 0;
        gbc_tfText.gridy = 0;
        panel_1.add(tfText, gbc_tfText);
        tfText.setColumns(10);
        
        Label lblTo = new Label("TO:");
        GridBagConstraints gbc_lblTo = new GridBagConstraints();
        gbc_lblTo.insets = new Insets(0, 0, 0, 5);
        gbc_lblTo.gridx = 1;
        gbc_lblTo.gridy = 0;
        panel_1.add(lblTo, gbc_lblTo);
        
        tfDST = new JTextField();
        GridBagConstraints gbc_tfDST = new GridBagConstraints();
        gbc_tfDST.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfDST.insets = new Insets(0, 0, 0, 5);
        gbc_tfDST.gridx = 2;
        gbc_tfDST.gridy = 0;
        panel_1.add(tfDST, gbc_tfDST);
        tfDST.setColumns(10);
        
        Button btnMSG = new Button("send");
        GridBagConstraints gbc_btnMSG = new GridBagConstraints();
        gbc_btnMSG.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnMSG.insets = new Insets(0, 0, 0, 5);
        gbc_btnMSG.gridx = 3;
        gbc_btnMSG.gridy = 0;
        panel_1.add(btnMSG, gbc_btnMSG);
            btnMSG.setActionCommand("MSG");
        btnMSG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                send("MSG "+tfText.getText()+" TO "+tfDST.getText());
            }
        });
        
        Button btnBRD = new Button("broadcast");
        btnBRD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send("BRD "+tfText.getText());
            }
        });
        GridBagConstraints gbc_btnBRD = new GridBagConstraints();
        gbc_btnBRD.fill = GridBagConstraints.BOTH;
        gbc_btnBRD.gridx = 4;
        gbc_btnBRD.gridy = 0;
        panel_1.add(btnBRD, gbc_btnBRD);
        btnBRD.setActionCommand("BRD");
        
        setVisible(true);
    }
    
    private void send(String msg){
        client.send(msg);
    }
    
    public void write(String text){
        taChat.append(text+"\n");
    }

}
