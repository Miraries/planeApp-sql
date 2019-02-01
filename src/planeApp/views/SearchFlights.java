package planeApp.views;

import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.util.stream.Stream;
import javax.swing.border.TitledBorder;
import planeApp.common.City;
import planeApp.dao.CityDAO;
import planeApp.dao.ConnectionManager;
import planeApp.dao.FlightDAO;
import planeApp.models.CityCBoxModel;
import planeApp.models.FlightTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SearchFlights extends JFrame {

	private static BufferedImage mapBackground;
	private static BufferedImage mapMarker;
	private static BufferedImage mapMarkerBlue;

	private JPanel contentPane;
	private JPanel mapPane;
	private JPanel panelFlight;
	private JButton btnSearchFlights;
	private JLabel lblOrigin;
	private JLabel lblDestination;
	private JLabel lblDate;
	private JTextField txtDay;
	private JTextField txtMonth;
	private JScrollPane spFlightList;
	private JTextField txtYear;
	private JTable tableFlights;

	private int[] lineXY;
	private JComboBox<City> cbOrigin;
	private JComboBox<City> cbDestination;
	public static final String[] columnNames = { "Origin", "Destination", "Airline", "Plane Model",
			"Date & Time", "Duration", "Price"};

	public SearchFlights() {
		this.setTitle("Search Flights");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1397, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(getPanelFlight(), GroupLayout.PREFERRED_SIZE, 354,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE).addComponent(
										getMapPane(), GroupLayout.PREFERRED_SIZE, 979, GroupLayout.PREFERRED_SIZE))
						.addComponent(getSpFlightList(), GroupLayout.DEFAULT_SIZE, 1351, Short.MAX_VALUE))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(getPanelFlight(), GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
						.addComponent(getMapPane(), GroupLayout.PREFERRED_SIZE, 489, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(getSpFlightList(), GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		//
		try {
			mapBackground = ImageIO.read(EditCities.class.getResource("/planeApp/res/equiMap.jpg"));
			mapMarker = ImageIO.read(EditCities.class.getResource("/planeApp/res/marker_32.png"));
			mapMarkerBlue = ImageIO.read(EditCities.class.getResource("/planeApp/res/markerBlue_32.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		initEvents();
		lineXY = new int[4];
	}

	private void showErrorDialog(String error) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	private void setLineCoords(boolean isOrigin) {
		City dest = (City) cbDestination.getSelectedItem();
		City origin = (City) cbOrigin.getSelectedItem();
		if (dest != null && origin != null && dest.equals(origin)) {
			if (isOrigin)
				cbOrigin.setSelectedIndex(-1);
			else
				cbDestination.setSelectedIndex(-1);
			showErrorDialog("Origin and destination cannot be the same");
			return;
		}
		if (isOrigin) {
			lineXY[0] = (int) ((1010 / 360.0) * (180 + origin.getLon()));
			lineXY[1] = (int) ((486 / 180.0) * (90 - origin.getLat()));
		} else {
			lineXY[2] = (int) ((1010 / 360.0) * (180 + dest.getLon()));
			lineXY[3] = (int) ((486 / 180.0) * (90 - dest.getLat()));
		}
		repaint();
	}

	private int[] getInputDate() {
		/*try {
			int[] arr = Stream.of(new JTextField[] { txtYear, txtMonth, txtDay })
					.mapToInt(x -> Integer.parseInt(x.getText())).toArray();
			System.out.println(arr[0] + " " + arr[1] + " " + arr[2]);
			if(arr[0] < 1900)
				throw new DateTimeException("Invalid year");
			return LocalDate.of(arr[0], arr[1], arr[2]);
		} catch (Exception e) {
			System.out.println(e);
			showErrorDialog("Invalid date");
			return null;
		}*/
		return Stream.of(new JTextField[] { txtYear, txtMonth, txtDay })
				.mapToInt(x -> Integer.parseInt(x.getText().isEmpty() ? "0" : x.getText())).toArray();
	}

	private void addFlightsToTable(int[] date) {
		City origin = (City) cbOrigin.getSelectedItem();
		City dest = (City) cbDestination.getSelectedItem();
		
		
		try {
			Connection conn = ConnectionManager.getConnection();
			FlightDAO flightDAO = new FlightDAO(conn);
			System.out.println(flightDAO.getAll());
			tableFlights.setModel(new FlightTableModel(flightDAO.filter(origin.getName(), dest.getName(), date)));/*
			PreparedStatement stmt = conn.prepareStatement("SELECT CONCAT(c1.name,\", \",c1.country) as origin, CONCAT(c2.name,\", \",c2.country) as dest,\r\n" + 
					"co.name as company, p.name as plane, f.takeoff, f.duration, f.price\r\n" + 
					"FROM `flight` f, `city` c1, `city` c2, `company` co, `plane` p\r\n" + 
					"WHERE f.origin = c1.id AND f.destination = c2.id AND f.company = co.id AND f.plane = p.id\r\n" + 
					"AND c1.name LIKE ? AND c2.name LIKE ?;");
			stmt.setString(1, ((City)cbOrigin.getSelectedItem()).getName());
			stmt.setString(2, ((City)cbDestination.getSelectedItem()).getName());
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT count(*) FROM flight");
			rs2.next();
			int len = rs2.getInt(1);
			System.out.println(len);
			
			String[][] matrix = new String[len][7];
			int i = 0;
			
			while(rs.next()) {
				String[] temp= {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)};
				matrix[i++] = temp;
			}
			for (String[] strings : matrix) {
				for (String s : strings) {
					System.out.println(s);
				}
			}
			
			tableFlights = new JTable(matrix, columnNames);
			tableFlights.repaint();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEvents() {
		cbDestination.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLineCoords(false);
			}
		});
		cbOrigin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setLineCoords(true);
			}
		});
		btnSearchFlights.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFlightsToTable(getInputDate());
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
					if (lineXY[0] > 1) {
						g.drawImage(mapMarker, lineXY[0] - mapMarker.getWidth() / 2, lineXY[1] - mapMarker.getHeight(),
								null);
					}
					if (lineXY[2] > 1) {
						g.drawImage(mapMarkerBlue, lineXY[2] - mapMarker.getWidth() / 2,
								lineXY[3] - mapMarker.getHeight(), null);
					}
					if (lineXY[0] < 1 || lineXY[2] < 1)
						return;
					System.out.println("Drawing line");
					Graphics2D g2d = (Graphics2D) g;
					g2d.setColor(Color.WHITE);
					g2d.setStroke(new BasicStroke(3));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawLine(lineXY[0], lineXY[1], lineXY[2], lineXY[3]);
				}
			};
		}
		return mapPane;
	}

	private JPanel getPanelFlight() {
		if (panelFlight == null) {
			panelFlight = new JPanel();
			panelFlight.setBorder(new TitledBorder(null, "Flight", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GroupLayout gl_panelFlight = new GroupLayout(panelFlight);
			gl_panelFlight.setHorizontalGroup(gl_panelFlight.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panelFlight.createSequentialGroup().addContainerGap().addGroup(gl_panelFlight
							.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, gl_panelFlight.createSequentialGroup()
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
											.addComponent(getLblOrigin(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblDate(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE))
									.addGap(36)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panelFlight.createSequentialGroup()
													.addComponent(getTxtDay(), GroupLayout.PREFERRED_SIZE, 45,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(getTxtMonth(), GroupLayout.PREFERRED_SIZE, 45,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(getTxtYear(), GroupLayout.PREFERRED_SIZE, 45,
															GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_panelFlight.createSequentialGroup()
													.addComponent(getCbOrigin(), 0, 207, Short.MAX_VALUE)
													.addContainerGap())))
							.addGroup(Alignment.LEADING,
									gl_panelFlight.createSequentialGroup()
											.addComponent(getLblDestination(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
											.addComponent(getCbDestination(), GroupLayout.PREFERRED_SIZE, 207,
													GroupLayout.PREFERRED_SIZE)
											.addContainerGap())
							.addGroup(gl_panelFlight.createSequentialGroup().addComponent(getBtnSearchFlights())
									.addContainerGap()))));
			gl_panelFlight
					.setVerticalGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelFlight.createSequentialGroup().addContainerGap()
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblOrigin()).addComponent(getCbOrigin(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblDestination()).addComponent(getCbDestination(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getTxtDay(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getTxtMonth(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getTxtYear(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblDate()))
									.addPreferredGap(ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
									.addComponent(getBtnSearchFlights()).addContainerGap()));
			panelFlight.setLayout(gl_panelFlight);
		}
		return panelFlight;
	}

	private JButton getBtnSearchFlights() {
		if (btnSearchFlights == null) {
			btnSearchFlights = new JButton("Search Flights");
		}
		return btnSearchFlights;
	}

	private JLabel getLblOrigin() {
		if (lblOrigin == null) {
			lblOrigin = new JLabel("Origin:");
		}
		return lblOrigin;
	}

	private JLabel getLblDestination() {
		if (lblDestination == null) {
			lblDestination = new JLabel("Destination:");
		}
		return lblDestination;
	}

	private JLabel getLblDate() {
		if (lblDate == null) {
			lblDate = new JLabel("Date:");
		}
		return lblDate;
	}

	private JTextField getTxtDay() {
		if (txtDay == null) {
			txtDay = new JTextField();
			txtDay.setColumns(10);
		}
		return txtDay;
	}

	private JTextField getTxtMonth() {
		if (txtMonth == null) {
			txtMonth = new JTextField();
			txtMonth.setColumns(10);
		}
		return txtMonth;
	}

	private JScrollPane getSpFlightList() {
		if (spFlightList == null) {
			spFlightList = new JScrollPane();
			spFlightList.setViewportView(getTableFlights());
		}
		return spFlightList;
	}

	private JTextField getTxtYear() {
		if (txtYear == null) {
			txtYear = new JTextField();
			txtYear.setColumns(10);
		}
		return txtYear;
	}

	private JTable getTableFlights() {
		if (tableFlights == null) {
			tableFlights = new JTable();
			tableFlights.setFillsViewportHeight(true);
		}
		return tableFlights;
	}

	private JComboBox<City> getCbOrigin() {
		if (cbOrigin == null) {
			cbOrigin = new JComboBox<City>();
			cbOrigin.setMaximumRowCount(20);
			try {
				CityDAO cDAO = new CityDAO(ConnectionManager.getConnection());
				cbOrigin.setModel(new CityCBoxModel(cDAO.search("")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cbOrigin;
	}

	private JComboBox<City> getCbDestination() {
		if (cbDestination == null) {
			cbDestination = new JComboBox<City>();
			cbDestination.setMaximumRowCount(20);
			try {
				CityDAO cDAO = new CityDAO(ConnectionManager.getConnection());
				cbDestination.setModel(new CityCBoxModel(cDAO.search("")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cbDestination;
	}
}
