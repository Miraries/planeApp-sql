package planeApp.views;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import planeApp.impexp.ExcelImport;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;

public class ImportData extends JFrame {
	private static final long serialVersionUID = 3606024790180391535L;
	private JPanel contentPane;
	final JFileChooser fileChooser = new JFileChooser();
	private JLabel lblSelectFileFor;
	private JButton btnChooseFile;
	private JTextField txtFilePath;
	private JButton btnStartImport;
	private File selectedFile;
	private JScrollPane scrollPane;
	private JTextPane txtLog;

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
					ImportData frame = new ImportData();
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
	public ImportData() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 668, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblSelectFileFor());
		contentPane.add(getBtnChooseFile());
		contentPane.add(getTxtFilePath());
		contentPane.add(getBtnStartImport());
		contentPane.add(getScrollPane());
	}

	private JLabel getLblSelectFileFor() {
		if (lblSelectFileFor == null) {
			lblSelectFileFor = new JLabel("Select File (.xlsx) for data import:");
			lblSelectFileFor.setBounds(10, 11, 168, 32);
		}
		return lblSelectFileFor;
	}

	private JButton getBtnChooseFile() {
		if (btnChooseFile == null) {
			btnChooseFile = new JButton("Choose file");
			btnChooseFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int returnVal = fileChooser.showOpenDialog(ImportData.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						selectedFile = fileChooser.getSelectedFile();
						if (!selectedFile.getName().endsWith("xlsx"))
							showErrorDialog("Invalid file");
						else
							txtFilePath.setText(selectedFile.getAbsolutePath());
					}
				}
			});
			btnChooseFile.setBounds(539, 53, 103, 30);
		}
		return btnChooseFile;
	}

	private JTextField getTxtFilePath() {
		if (txtFilePath == null) {
			txtFilePath = new JTextField();
			txtFilePath.setBounds(10, 54, 519, 29);
			txtFilePath.setColumns(10);
		}
		return txtFilePath;
	}

	private JButton getBtnStartImport() {
		if (btnStartImport == null) {
			btnStartImport = new JButton("Import Data");
			btnStartImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					txtLog.setText("Output log:");
					if (!txtFilePath.getText().isEmpty())
						selectedFile = new File(txtFilePath.getText());
					if (!selectedFile.getName().endsWith("xlsx"))
						showErrorDialog("Invalid file");
					else {
						System.out.println("Importing data from " + selectedFile.getAbsolutePath());
						try {
							ExcelImport excelImport = new ExcelImport(txtLog);
							excelImport.importData(selectedFile, txtLog);
						} catch (InvalidFormatException | IOException e) {
							showErrorDialog("Unable to import excel file: " + e.getMessage());
						}
					}
				}
			});
			btnStartImport.setBounds(539, 488, 103, 32);
		}
		return btnStartImport;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 94, 632, 383);
			scrollPane.setViewportView(getTextPane_1());
		}
		return scrollPane;
	}
	private JTextPane getTextPane_1() {
		if (txtLog == null) {
			txtLog = new JTextPane();
			txtLog.setText("Output Log:");
			txtLog.setForeground(SystemColor.textInactiveText);
			txtLog.setEditable(false);
			txtLog.setBackground(Color.WHITE);
		}
		return txtLog;
	}
}
