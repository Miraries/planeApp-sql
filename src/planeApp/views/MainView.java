package planeApp.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import planeApp.common.App;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import java.awt.Color;

@SuppressWarnings("serial")
public class MainView extends JFrame {

	private JPanel contentPane;
	private JButton btnEditCities;
	private JButton btnEditCompanyPlane;
	private JButton btnEditFlights;
	private JButton btnSearchFlights;
	private JButton btnImportData;
	private JButton btnExportJsonXML;
	private JButton btnExportPdf;

	public MainView() {
		this.setTitle("planeApp v0.2");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 228, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(getBtnSearchFlights(), Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(getBtnEditFlights(), Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(getBtnEditCompanyPlane(), GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(getBtnEditCities(), GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
						.addComponent(getBtnImportData(), GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(getBtnExportJsonXML(), GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(getBtnExportPdf(), GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addComponent(getBtnEditCities(), GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(getBtnEditCompanyPlane(), GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(getBtnEditFlights(), GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(getBtnSearchFlights(), GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(getBtnImportData(), GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(getBtnExportJsonXML(), GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(getBtnExportPdf(), GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		initEvents();
	}

	private void initEvents() {
		btnEditCities.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditCities ecFrame = new EditCities();
				ecFrame.setVisible(true);
			}
		});
		btnEditCompanyPlane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditCompaniesPlanes ecpFrame = new EditCompaniesPlanes();
				ecpFrame.setVisible(true);
			}
		});
		/*btnEditFlights.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditFlights efFrame = new EditFlights();
				efFrame.setVisible(true);
			}
		});*/
		btnSearchFlights.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchFlights sfFrame = new SearchFlights();
				sfFrame.setVisible(true);
			}
		});
		btnImportData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImportData sfFrame = new ImportData();
				sfFrame.setVisible(true);
			}
		});
		btnExportJsonXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportJSONXML sfFrame = new ExportJSONXML();
				sfFrame.setVisible(true);
			}
		});
		btnExportPdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportPDFView sfFrame = new ExportPDFView();
				sfFrame.setVisible(true);
			}
		});
		this.addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent e){
	            int i=JOptionPane.showConfirmDialog(null, "Confirm exit");
	            //App.AppInstance.serialize("app.ser");
	            if(i==0)
	                System.exit(0);
	        }
	    });
	}
	
	

	private JButton getBtnEditCities() {
		if (btnEditCities == null) {
			btnEditCities = new JButton("Edit Cities");
		}

		return btnEditCities;
	}
	private JButton getBtnEditCompanyPlane() {
		if (btnEditCompanyPlane == null) {
			btnEditCompanyPlane = new JButton("Edit Companies and Planes");
		}
		return btnEditCompanyPlane;
	}
	private JButton getBtnEditFlights() {
		if (btnEditFlights == null) {
			btnEditFlights = new JButton("Edit Flights");
			btnEditFlights.setEnabled(false);
			btnEditFlights.setBackground(Color.LIGHT_GRAY);
			btnEditFlights.setForeground(UIManager.getColor("Button.disabledForeground"));
		}
		return btnEditFlights;
	}
	private JButton getBtnSearchFlights() {
		if (btnSearchFlights == null) {
			btnSearchFlights = new JButton("Search Flights");
		}
		return btnSearchFlights;
	}
	private JButton getBtnImportData() {
		if (btnImportData == null) {
			btnImportData = new JButton("Import Data");
		}
		return btnImportData;
	}
	private JButton getBtnExportJsonXML() {
		if (btnExportJsonXML == null) {
			btnExportJsonXML = new JButton("Export JSON & XML");
		}
		return btnExportJsonXML;
	}
	private JButton getBtnExportPdf() {
		if (btnExportPdf == null) {
			btnExportPdf = new JButton("Export PDF");
		}
		return btnExportPdf;
	}
}
