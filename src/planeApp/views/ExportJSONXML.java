package planeApp.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import planeApp.common.Flight;
import planeApp.dao.ConnectionManager;
import planeApp.dao.FlightDAO;
import planeApp.impexp.ExportJSON;
import planeApp.impexp.ExportXML;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import java.awt.event.ActionEvent;

public class ExportJSONXML extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btnGenerateJSON;
	private JTextPane txtpnAsd;
	private JButton btnGenerateXML;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExportJSONXML frame = new ExportJSONXML();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExportJSONXML() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 689, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPane());
		contentPane.add(getBtnGenerateJSON());
		contentPane.add(getBtnGenerateXML());
	}
	
	private void showErrorDialog(String error) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 653, 211);
			scrollPane.setViewportView(getTxtpnAsd());
			scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return scrollPane;
	}
	private JButton getBtnGenerateJSON() {
		if (btnGenerateJSON == null) {
			btnGenerateJSON = new JButton("Generate JSON");
			btnGenerateJSON.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Connection conn = null;
					try {
						conn = ConnectionManager.getConnection();
						FlightDAO dao = new FlightDAO(conn);
						txtpnAsd.setText(ExportJSON.generateJSON(dao.getAll()));
					} catch (Exception e) {
						showErrorDialog("Unable to generate JSON, check DB: " + e.getMessage());
					}
				}
			});
			btnGenerateJSON.setBounds(546, 233, 117, 29);
		}
		return btnGenerateJSON;
	}
	private JTextPane getTxtpnAsd() {
		if (txtpnAsd == null) {
			txtpnAsd = new JTextPane();
			txtpnAsd.setEditable(false);
		}
		return txtpnAsd;
	}
	private JButton getBtnGenerateXML() {
		if (btnGenerateXML == null) {
			btnGenerateXML = new JButton("Generate XML");
			btnGenerateXML.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Connection conn = null;
					try {
						conn = ConnectionManager.getConnection();
						FlightDAO dao = new FlightDAO(conn);
						List<Flight> flights = dao.getAll();
						txtpnAsd.setText(ExportXML.generateXML(flights).replaceAll("ArrayList", "flights"));
					} catch (Exception e) {
						showErrorDialog("Unable to generate XML, check DB: " + e.getMessage());
					}
				}
			});
			btnGenerateXML.setBounds(419, 233, 117, 29);
		}
		return btnGenerateXML;
	}
}
