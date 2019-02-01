package planeApp.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import planeApp.dao.ConnectionManager;
import planeApp.dao.FlightDAO;
import planeApp.impexp.ExportPDF;
import planeApp.impexp.ExportXML;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class ExportPDFView extends JFrame {

	private JPanel contentPane;
	private JLabel lblOutputPath;
	private JTextField txtOutputPath;
	private JButton btnChoosePath;
	private JButton btnExportPdf;
	final JFileChooser fileChooser = new JFileChooser();

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
					ExportPDFView frame = new ExportPDFView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void showErrorDialog(String error) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Create the frame.
	 */
	public ExportPDFView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 157);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblOutputPath());
		contentPane.add(getTxtOutputPath());
		contentPane.add(getBtnChoosePath());
		contentPane.add(getBtnExportPdf());
	}
	private JLabel getLblOutputPath() {
		if (lblOutputPath == null) {
			lblOutputPath = new JLabel("Output Path:");
			lblOutputPath.setBounds(10, 31, 80, 14);
		}
		return lblOutputPath;
	}
	private JTextField getTxtOutputPath() {
		if (txtOutputPath == null) {
			txtOutputPath = new JTextField();
			txtOutputPath.setBounds(89, 25, 411, 27);
			txtOutputPath.setColumns(10);
		}
		return txtOutputPath;
	}
	private JButton getBtnChoosePath() {
		if (btnChoosePath == null) {
			btnChoosePath = new JButton("Choose path");
			btnChoosePath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int returnVal = fileChooser.showOpenDialog(ExportPDFView.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File selected = fileChooser.getSelectedFile();
						txtOutputPath.setText(selected.getAbsolutePath());
						if (!selected.getPath().isEmpty() && !selected.getName().isEmpty() &&!selected.getName().endsWith(".pdf"))
							txtOutputPath.setText(txtOutputPath.getText() + ".pdf");
					}
				}
			});
			btnChoosePath.setBounds(510, 25, 96, 27);
		}
		return btnChoosePath;
	}
	private JButton getBtnExportPdf() {
		if (btnExportPdf == null) {
			btnExportPdf = new JButton("Export PDF");
			btnExportPdf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Connection conn;
					try {
						conn = ConnectionManager.getConnection();
					} catch (Exception e) {
						showErrorDialog("Unable to connect to database: " + e.getMessage());
						return;
					}
					FlightDAO dao = new FlightDAO(conn);
					try {
						ExportPDF.convertToPDF(new File(this.getClass().getResource("/planeApp/res/stylesheet.xslt").getPath()), ExportXML.generateCorrectedXML(dao.getAll()), txtOutputPath.getText());
					} catch (Exception e) {
						showErrorDialog("Unable to export PDF: " + e.getMessage());
						return;
					}
					JOptionPane.showMessageDialog(new JFrame(), "Data exported succesfully to \n" + txtOutputPath.getText(), "Notification", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			btnExportPdf.setBounds(510, 80, 96, 27);
		}
		return btnExportPdf;
	}
}
