package planeApp.views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import planeApp.common.App;
import planeApp.common.City;
import planeApp.dao.CityDAO;
import planeApp.dao.ConnectionManager;
import planeApp.models.CityTableModel;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class EditCities extends JFrame {

	private static BufferedImage mapBackground;
	private static BufferedImage mapMarker;

	private JPanel leftPane;
	private JPanel mapPane;
	private JScrollPane scrollPaneCities;
	private JLabel lblCities;
	private JTable tableCities;
	private int xMarker;
	private int yMarker;
	private JButton btnSearch;
	private JTextField txtSearchCity;
	
	private JFrame frame;

	public EditCities() {
		this.setTitle("Edit Cities");
		frame = this;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1500, 560);
		leftPane = new JPanel();
		leftPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(leftPane);
		GroupLayout gl_leftPane = new GroupLayout(leftPane);
		gl_leftPane.setHorizontalGroup(
			gl_leftPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_leftPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_leftPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_leftPane.createSequentialGroup()
							.addGroup(gl_leftPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_leftPane.createSequentialGroup()
									.addComponent(getTxtSearchCity(), GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(getBtnSearch()))
								.addComponent(getScrollPaneCities(), GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
							.addGap(18))
						.addGroup(gl_leftPane.createSequentialGroup()
							.addComponent(getLblCities())
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(getMapPane(), GroupLayout.PREFERRED_SIZE, 979, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_leftPane.setVerticalGroup(
			gl_leftPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_leftPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_leftPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_leftPane.createSequentialGroup()
							.addComponent(getLblCities())
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getScrollPaneCities(), GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_leftPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(getBtnSearch())
								.addComponent(getTxtSearchCity(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(7))
						.addComponent(getMapPane(), GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
					.addContainerGap())
		);
		leftPane.setLayout(gl_leftPane);
		try {
			mapBackground = ImageIO.read(EditCities.class.getResource("/planeApp/res/equiMap.jpg"));
			mapMarker = ImageIO.read(EditCities.class.getResource("/planeApp/res/marker_32.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.getRootPane().setDefaultButton(btnSearch);
	}

	public void initEvents() {
		tableCities.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tableCities.getSelectedRow() < 0)
					return;			
				try {
					CityDAO cDAO = new CityDAO(ConnectionManager.getConnection());
					City c = cDAO.get(tableCities.getModel().getValueAt(tableCities.getSelectedRow(), 0).toString());
					xMarker = (int) ((1010 / 360.0) * (180 + c.getLon()));
					yMarker = (int) ((486 / 180.0) * (90 - c.getLat()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				repaint();
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CityDAO cDAO = new CityDAO(ConnectionManager.getConnection());
					tableCities.setModel(new CityTableModel(cDAO.search(txtSearchCity.getText())));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private JPanel getMapPane() {
		if (mapPane == null) {
			mapPane = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(mapBackground, 0, 0, null);
					if (xMarker > 1 && yMarker > 1) {
						g.drawImage(mapMarker, xMarker - mapMarker.getWidth() / 2, yMarker - mapMarker.getHeight(), null);
					}
				}
			};
		}
		return mapPane;
	}

	private JScrollPane getScrollPaneCities() {
		if (scrollPaneCities == null) {
			scrollPaneCities = new JScrollPane();
			scrollPaneCities.setViewportView(getTableCities());
		}
		return scrollPaneCities;
	}

	private JLabel getLblCities() {
		if (lblCities == null) {
			lblCities = new JLabel("Cities:");
		}
		return lblCities;
	}

	private JTable getTableCities() {
		if (tableCities == null) {
			try {
				tableCities = new JTable(new CityTableModel(new CityDAO(ConnectionManager.getConnection()).getAll()));
				tableCities.setFillsViewportHeight(true);
				tableCities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				initEvents();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Cannot connect to database", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		return tableCities;
	}
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("Search City");
		}
		return btnSearch;
	}
	private JTextField getTxtSearchCity() {
		if (txtSearchCity == null) {
			txtSearchCity = new JTextField();
			txtSearchCity.setColumns(10);
		}
		return txtSearchCity;
	}
}
