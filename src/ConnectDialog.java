import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;

public class ConnectDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTextField tfIP;
	private JTextField tfPort;
	private JTextField tfName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConnectDialog dialog = new ConnectDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConnectDialog() {
		setBounds(100, 100, 450, 300);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{338, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblServer = new JLabel("Server IP:");
			GridBagConstraints gbc_lblServer = new GridBagConstraints();
			gbc_lblServer.anchor = GridBagConstraints.WEST;
			gbc_lblServer.insets = new Insets(0, 0, 5, 0);
			gbc_lblServer.gridx = 0;
			gbc_lblServer.gridy = 0;
			contentPanel.add(lblServer, gbc_lblServer);
		}
		{
			tfIP = new JTextField();
			GridBagConstraints gbc_tfIP = new GridBagConstraints();
			gbc_tfIP.insets = new Insets(0, 0, 5, 0);
			gbc_tfIP.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfIP.gridx = 0;
			gbc_tfIP.gridy = 1;
			contentPanel.add(tfIP, gbc_tfIP);
			tfIP.setColumns(10);
		}
		{
			JLabel lblServerPort = new JLabel("Server Port:");
			GridBagConstraints gbc_lblServerPort = new GridBagConstraints();
			gbc_lblServerPort.anchor = GridBagConstraints.WEST;
			gbc_lblServerPort.insets = new Insets(0, 0, 5, 0);
			gbc_lblServerPort.gridx = 0;
			gbc_lblServerPort.gridy = 2;
			contentPanel.add(lblServerPort, gbc_lblServerPort);
		}
		{
			tfPort = new JTextField();
			GridBagConstraints gbc_tfPort = new GridBagConstraints();
			gbc_tfPort.insets = new Insets(0, 0, 5, 0);
			gbc_tfPort.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfPort.gridx = 0;
			gbc_tfPort.gridy = 3;
			contentPanel.add(tfPort, gbc_tfPort);
			tfPort.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Name:");
			GridBagConstraints gbc_lblName = new GridBagConstraints();
			gbc_lblName.anchor = GridBagConstraints.WEST;
			gbc_lblName.insets = new Insets(0, 0, 5, 0);
			gbc_lblName.gridx = 0;
			gbc_lblName.gridy = 4;
			contentPanel.add(lblName, gbc_lblName);
		}
		{
			tfName = new JTextField();
			GridBagConstraints gbc_tfName = new GridBagConstraints();
			gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfName.gridx = 0;
			gbc_tfName.gridy = 5;
			contentPanel.add(tfName, gbc_tfName);
			tfName.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public String getIP(){
		return tfIP.getText();
	}
	
	public int getPort(){
		return Integer.parseInt(tfPort.getText());
	}
	
	public String getName(){
		return tfName.getText();
	}

}
