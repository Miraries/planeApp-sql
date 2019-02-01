package planeApp.views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import planeApp.common.Company;
import planeApp.common.Plane;
import planeApp.dao.CityDAO;
import planeApp.dao.CompanyDAO;
import planeApp.dao.ConnectionManager;
import planeApp.dao.PlaneDAO;
import planeApp.models.CompanyListModel;
import planeApp.models.PlaneListModel;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class EditCompaniesPlanes extends JFrame {

	private JPanel contentPane;
	private JLabel lblCompanies;
	private JScrollPane spPlaneList;
	private JLabel lblPlanes;
	private JScrollPane spCompany;
	private JList<Company> listCompany;
	private JList<Plane> listPlane;
	private JButton btnSearchCompany;
	private JTextField txtSearchCompany;
	private JTextField txtSearchPlane;
	private JButton btnSearchPlane;

	public EditCompaniesPlanes() {
		this.setTitle("Edit Companies and Planes");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 884, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(getTxtSearchCompany())
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(getBtnSearchCompany()))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(getLblCompanies())
							.addComponent(getSpCompany(), GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(getTxtSearchPlane(), GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(getBtnSearchPlane(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addComponent(getSpPlaneList(), GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
						.addComponent(getLblPlanes())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(getLblCompanies())
						.addComponent(getLblPlanes()))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(getSpCompany(), GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
						.addComponent(getSpPlaneList(), GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(getBtnSearchCompany())
						.addComponent(getTxtSearchCompany(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTxtSearchPlane(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getBtnSearchPlane()))
					.addGap(88))
		);
		contentPane.setLayout(gl_contentPane);
		//
		initEvents();
		setCompanyModel("");
		setPlaneModel("");
	}

	private void initEvents() {
		btnSearchCompany.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCompanyModel(txtSearchCompany.getText());
			}
		});
		btnSearchPlane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setPlaneModel(txtSearchPlane.getText());
			}
		});
	}
	
	private void setCompanyModel(String name) {
		try {
			DefaultListModel<Company> model = new DefaultListModel<Company>();
			List<Company> list = new CompanyDAO(ConnectionManager.getConnection()).search(name);
			for (Company c : list) {
				model.addElement(c);
			}
			listCompany.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPlaneModel(String name) {
		try {
			DefaultListModel<Plane> model = new DefaultListModel<Plane>();
			List<Plane> list = new PlaneDAO(ConnectionManager.getConnection()).search(name);
			for (Plane c : list) {
				model.addElement(c);
			}
			listPlane.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel getLblCompanies() {
		if (lblCompanies == null) {
			lblCompanies = new JLabel("Companies:");
		}
		return lblCompanies;
	}

	private JScrollPane getSpPlaneList() {
		if (spPlaneList == null) {
			spPlaneList = new JScrollPane();
			spPlaneList.setViewportView(getListPlane());
		}
		return spPlaneList;
	}

	private JLabel getLblPlanes() {
		if (lblPlanes == null) {
			lblPlanes = new JLabel("Planes:");
		}
		return lblPlanes;
	}

	private JScrollPane getSpCompany() {
		if (spCompany == null) {
			spCompany = new JScrollPane();
			spCompany.setViewportView(getListCompany());
		}
		return spCompany;
	}

	private JList<Company> getListCompany() {
		if (listCompany == null) {
			listCompany = new JList<Company>();
		}
		return listCompany;
	}

	private JList<Plane> getListPlane() {
		if (listPlane == null) {
			listPlane = new JList<Plane>();
		}
		return listPlane;
	}
	private JButton getBtnSearchCompany() {
		if (btnSearchCompany == null) {
			btnSearchCompany = new JButton("Search Company");
		}
		return btnSearchCompany;
	}
	private JTextField getTxtSearchCompany() {
		if (txtSearchCompany == null) {
			txtSearchCompany = new JTextField();
			txtSearchCompany.setColumns(10);
		}
		return txtSearchCompany;
	}
	private JTextField getTxtSearchPlane() {
		if (txtSearchPlane == null) {
			txtSearchPlane = new JTextField();
			txtSearchPlane.setColumns(10);
		}
		return txtSearchPlane;
	}
	private JButton getBtnSearchPlane() {
		if (btnSearchPlane == null) {
			btnSearchPlane = new JButton("Search Plane");
		}
		return btnSearchPlane;
	}
}
