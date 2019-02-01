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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.JTextComponent;

import planeApp.common.App;
import planeApp.common.City;
import planeApp.common.Company;
import planeApp.common.Flight;
import planeApp.common.Plane;
import planeApp.models.FlightTableModel;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class EditFlights extends JFrame {

	private static BufferedImage mapBackground;
	private static BufferedImage mapMarker;
	private static BufferedImage mapMarkerBlue;

	private JPanel contentPane;
	private JPanel mapPane;

	private JPanel panelFlight;
	private JScrollPane spFlightList;
	private JTable tableFlights;
	private JLabel lblOrigin;
	private JLabel lblDestination;
	private JLabel lblAirline;
	private JLabel lblPlaneModel;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblDuration;
	private JLabel lblPrice;
	private JTextField txtOrigin;
	private JTextField txtDestination;
	private JTextField txtHour;
	private JTextField txtDurationHour;
	private JTextField txtDay;
	private JTextField txtMonth;
	private JTextField txtMinute;
	private JTextField txtDurationMinute;
	private JButton btnRemoveFlight;
	private JButton btnUpdateFlight;
	private JButton btnAddFlight;
	private AbstractTableModel flightTableModel = new FlightTableModel(null);
	private JLabel labelOriginCountry;
	private JTextField txtOriginCountry;
	private JLabel lblDestinationCountry;
	private JTextField txtDestinationCountry;
	private JTextField txtPrice;
	private JTextField txtCompany;
	private JTextField txtPlaneModel;
	private JTextField txtYear;

	private int[] lineXY;
	String[] errorMsgs = new String[] { "Origin City", "Origin Country", "Destination City", "Destination Country",
			"Company", "Plane Model", "Price" };

	public EditFlights() {
		this.setTitle("Edit Flights");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1397, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(getSpFlightList(), Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1351,
										Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(getPanelFlight(), GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
										.addGap(18).addComponent(getMapPane(), GroupLayout.PREFERRED_SIZE, 979,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(getMapPane(), GroupLayout.PREFERRED_SIZE, 489, GroupLayout.PREFERRED_SIZE)
						.addComponent(getPanelFlight(), GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(getSpFlightList(), GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
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

	private void updateValues(Flight f) {
		txtOrigin.setText(f.getOrigin().getName());
		txtOriginCountry.setText(f.getOrigin().getCountry());
		txtDestination.setText(f.getDestination().getName());
		txtDestinationCountry.setText(f.getDestination().getCountry());
		txtCompany.setText(f.getAirline().getName());
		txtPlaneModel.setText(f.getPlane().getName());
		txtPrice.setText(f.getPrice() + "");
		LocalDateTime ldt = f.getTakeoffTime();
		txtDay.setText(ldt.getDayOfMonth() + "");
		txtMonth.setText(ldt.getMonthValue() + "");
		txtYear.setText(ldt.getYear() + "");
		txtHour.setText(ldt.getHour() + "");
		txtMinute.setText(ldt.getMinute() + "");
		txtDurationHour.setText(f.getDurationArray()[0] + "");
		txtDurationMinute.setText(f.getDurationArray()[1] + "");
	}

	private JTextComponent[] txtString() {
		return new JTextComponent[] { txtOrigin, txtOriginCountry, txtDestination, txtDestinationCountry, txtCompany,
				txtPlaneModel, txtPrice };
	}

	private JTextComponent[] txtInt() {
		return new JTextComponent[] { txtYear, txtMonth, txtDay, txtHour, txtMinute, txtDurationHour,
				txtDurationMinute };
	}

	private void showErrorDialog(String error) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	private boolean addFlight() {
		String[] arrStr = Stream.of(txtString()).map(x -> x.getText()).toArray(String[]::new);
		int[] arrInt = Stream.of(txtInt()).mapToInt(x -> Integer.parseInt(x.getText().length() < 1 ? "-1" : x.getText()))
				.toArray();
		
		City origin = App.AppInstance.cityList.get(new City(arrStr[0], arrStr[1], 0, 0));
		City dest = App.AppInstance.cityList.get(new City(arrStr[2], arrStr[3], 0, 0));
		Company company = App.AppInstance.companyList.get(new Company(arrStr[4], ""));
		Plane plane = App.AppInstance.planeList.get(new Plane(arrStr[5], 0));

		LocalDateTime ldt = getInputDateTime(arrInt);
		int duration = getInputDuration(arrInt).toSecondOfDay()/60;
		if(ldt == null || duration < 1)
			return false;

		Flight f = new Flight(origin, dest, company, plane, ldt, duration, Double.parseDouble(arrStr[6]));
		if (!App.AppInstance.flightList.add(f)) {
			showErrorDialog("Flight has already been added");
			return false;
		}
		
		flightTableModel.fireTableDataChanged();
		resetInputs();
		return true;
	}
	
	public void removeLine() {
		lineXY = new int[]{-1,-1,-1,-1};
		repaint();
	}
	
	private void resetInputs() {
		Stream.of(txtString()).forEach(x -> x.setText(""));
		Stream.of(txtInt()).forEach(x -> x.setText(""));
		removeLine();
	}
	
	private LocalDateTime getInputDateTime(int[] arrInt) {
		try {
			// So apparently you can't pass an int array to String.format, only Integer?
			return LocalDateTime.of(arrInt[0], arrInt[1], arrInt[2], arrInt[3], arrInt[4]);
		} catch (Exception e) {
			for (int i : arrInt) {
				System.out.println(i+" ");
			}
			System.out.println(e);
			showErrorDialog("Invalid date & time");
			return null;
		}
	}
	
	private LocalTime getInputDuration(int[] arrInt) {
		try {
			return LocalTime.of(arrInt[5], arrInt[6]);
		} catch (Exception e) {
			showErrorDialog("Invalid duration time");
			return LocalTime.of(0,0);
		}
	}

	private boolean validateInputs() {
		String[] arrStr = Stream.of(txtString()).map(x -> x.getText()).toArray(String[]::new);
		for (int i = 0; i < arrStr.length; i++) {
			if (arrStr[i].length() < 1) {
				showErrorDialog("You must input the " + errorMsgs[i]);
				return false;
			} else if ((i == 0 || i == 2)
					&& !App.AppInstance.cityList.contains(new City(arrStr[i], arrStr[i + 1], 0, 0))) {
				showErrorDialog("Invalid " + errorMsgs[i] + " or " + errorMsgs[i + 1]);
				return false;
			} else if (i == 4 && !App.AppInstance.companyList.contains(new Company(arrStr[i], ""))) {
				showErrorDialog("Invalid " + errorMsgs[i]);
				return false;
			} else if (i == 5 && !App.AppInstance.planeList.contains(new Plane(arrStr[i], 0))) {
				showErrorDialog("Invalid " + errorMsgs[i]);
				return false;
			}
		}
		if (Double.parseDouble(txtPrice.getText()) < 1) {
			showErrorDialog("Invalid Price");
			return false;
		}
		return true;
	}
	
	private void removeFlight() {
		if (tableFlights.getSelectedRow() < 0)
			return;
		App.AppInstance.flightList.remove(tableFlights.getSelectedRow());
		flightTableModel.fireTableDataChanged();
	}

	public void initEvents() {
		btnAddFlight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateInputs())
					addFlight();
			}
		});
		tableFlights.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tableFlights.getSelectedRow() < 0)
					return;
				System.out.println(tableFlights.getValueAt(tableFlights.getSelectedRow(), 0).toString());
				Flight f = App.AppInstance.flightList.get(tableFlights.getSelectedRow());
				lineXY[0] = (int) ((1010 / 360.0) * (180 + f.getOrigin().getLon()));
				lineXY[1] = (int) ((486 / 180.0) * (90 - f.getOrigin().getLat()));
				lineXY[2] = (int) ((1010 / 360.0) * (180 + f.getDestination().getLon()));
				lineXY[3] = (int) ((486 / 180.0) * (90 - f.getDestination().getLat()));
				repaint();
				updateValues(f);
			}
		});
		btnRemoveFlight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeFlight();
			}
		});
		btnUpdateFlight.addActionListener(new ActionListener() {
			//Fix this sometime yeh?
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validateInputs()) {
					removeFlight();
					addFlight();
					resetInputs();
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
					for (int i : lineXY) {
						if (i < 1)
							return;
					}
					System.out.println("Drawing line");
					Graphics2D g2d = (Graphics2D) g;
					g2d.setColor(Color.WHITE);
					g2d.setStroke(new BasicStroke(3));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawLine(lineXY[0], lineXY[1], lineXY[2], lineXY[3]);
					g.drawImage(mapMarker, lineXY[0] - mapMarker.getWidth() / 2, lineXY[1] - mapMarker.getHeight(),
							null);
					g.drawImage(mapMarkerBlue, lineXY[2] - mapMarker.getWidth() / 2, lineXY[3] - mapMarker.getHeight(),
							null);
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
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelFlight.createSequentialGroup().addComponent(getBtnAddFlight())
									.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(getBtnUpdateFlight())
									.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(
											getBtnRemoveFlight())
									.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE))
							.addGroup(gl_panelFlight.createSequentialGroup()
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
											.addComponent(getLblOrigin(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLabelOriginCountry(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblDestination(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblDestinationCountry(), GroupLayout.PREFERRED_SIZE, 111,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblAirline(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblPlaneModel(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblDate(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblTime(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblDuration(), GroupLayout.PREFERRED_SIZE, 79,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(getLblPrice(), GroupLayout.PREFERRED_SIZE, 93,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
											.addComponent(getTxtPrice(), GroupLayout.PREFERRED_SIZE, 45,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panelFlight.createSequentialGroup()
													.addGroup(gl_panelFlight
															.createParallelGroup(Alignment.LEADING, false)
															.addComponent(getTxtDay(), GroupLayout.PREFERRED_SIZE, 45,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(getTxtHour(), GroupLayout.PREFERRED_SIZE, 45,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(getTxtDurationHour(),
																	GroupLayout.PREFERRED_SIZE, 45,
																	GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_panelFlight
															.createParallelGroup(Alignment.LEADING, false)
															.addComponent(getTxtDurationMinute(),
																	GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
															.addComponent(getTxtMinute(), GroupLayout.DEFAULT_SIZE, 45,
																	Short.MAX_VALUE)
															.addComponent(getTxtMonth(), 0, 0, Short.MAX_VALUE))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(getTxtYear(), GroupLayout.PREFERRED_SIZE, 45,
															GroupLayout.PREFERRED_SIZE)
													.addGap(70))
											.addGroup(gl_panelFlight.createSequentialGroup()
													.addComponent(getTxtPlaneModel(), GroupLayout.DEFAULT_SIZE, 201,
															Short.MAX_VALUE)
													.addGap(16))
											.addGroup(gl_panelFlight.createSequentialGroup()
													.addComponent(getTxtCompany(), GroupLayout.DEFAULT_SIZE, 201,
															Short.MAX_VALUE)
													.addGap(16))
											.addGroup(gl_panelFlight.createSequentialGroup()
													.addGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
															.addComponent(getTxtDestinationCountry())
															.addComponent(getTxtDestination())
															.addComponent(getTxtOriginCountry())
															.addComponent(getTxtOrigin(), GroupLayout.DEFAULT_SIZE, 201,
																	Short.MAX_VALUE))
													.addGap(16)))))
							.addGap(0)));
			gl_panelFlight
					.setVerticalGroup(gl_panelFlight.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelFlight.createSequentialGroup().addContainerGap()
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblOrigin()).addComponent(getTxtOrigin(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLabelOriginCountry()).addComponent(getTxtOriginCountry(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblDestination()).addComponent(getTxtDestination(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblDestinationCountry())
											.addComponent(getTxtDestinationCountry(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblAirline()).addComponent(getTxtCompany(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblPlaneModel()).addComponent(getTxtPlaneModel(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblDate())
											.addComponent(getTxtDay(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getTxtMonth(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getTxtYear(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblTime())
											.addComponent(getTxtHour(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getTxtMinute(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblDuration())
											.addComponent(getTxtDurationHour(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(getTxtDurationMinute(), GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getLblPrice()).addComponent(getTxtPrice(),
													GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE))
									.addGap(126)
									.addGroup(gl_panelFlight.createParallelGroup(Alignment.BASELINE)
											.addComponent(getBtnRemoveFlight()).addComponent(getBtnUpdateFlight())
											.addComponent(getBtnAddFlight()))
									.addContainerGap()));
			panelFlight.setLayout(gl_panelFlight);
		}
		return panelFlight;
	}

	private JScrollPane getSpFlightList() {
		if (spFlightList == null) {
			spFlightList = new JScrollPane();
			spFlightList.setViewportView(getTableFlights());
		}
		return spFlightList;
	}

	private JTable getTableFlights() {
		if (tableFlights == null) {
			tableFlights = new JTable(flightTableModel);
			tableFlights.setFillsViewportHeight(true);
		}
		return tableFlights;
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

	private JLabel getLblAirline() {
		if (lblAirline == null) {
			lblAirline = new JLabel("Airline:");
		}
		return lblAirline;
	}

	private JLabel getLblPlaneModel() {
		if (lblPlaneModel == null) {
			lblPlaneModel = new JLabel("Plane Model:");
		}
		return lblPlaneModel;
	}

	private JLabel getLblDate() {
		if (lblDate == null) {
			lblDate = new JLabel("Date:");
		}
		return lblDate;
	}

	private JLabel getLblTime() {
		if (lblTime == null) {
			lblTime = new JLabel("Time:");
		}
		return lblTime;
	}

	private JLabel getLblDuration() {
		if (lblDuration == null) {
			lblDuration = new JLabel("Duration:");
		}
		return lblDuration;
	}

	private JLabel getLblPrice() {
		if (lblPrice == null) {
			lblPrice = new JLabel("Price($):");
		}
		return lblPrice;
	}

	private JTextField getTxtOrigin() {
		if (txtOrigin == null) {
			txtOrigin = new JTextField();
			txtOrigin.setColumns(10);
		}
		return txtOrigin;
	}

	private JTextField getTxtDestination() {
		if (txtDestination == null) {
			txtDestination = new JTextField();
			txtDestination.setColumns(10);
		}
		return txtDestination;
	}

	private JTextField getTxtHour() {
		if (txtHour == null) {
			txtHour = new JTextField();
			txtHour.setColumns(10);
		}
		return txtHour;
	}

	private JTextField getTxtDurationHour() {
		if (txtDurationHour == null) {
			txtDurationHour = new JTextField();
			txtDurationHour.setColumns(10);
		}
		return txtDurationHour;
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

	private JTextField getTxtMinute() {
		if (txtMinute == null) {
			txtMinute = new JTextField();
			txtMinute.setColumns(10);
		}
		return txtMinute;
	}

	private JTextField getTxtDurationMinute() {
		if (txtDurationMinute == null) {
			txtDurationMinute = new JTextField();
			txtDurationMinute.setColumns(10);
		}
		return txtDurationMinute;
	}

	private JButton getBtnRemoveFlight() {
		if (btnRemoveFlight == null) {
			btnRemoveFlight = new JButton("Remove Flight");
		}
		return btnRemoveFlight;
	}

	private JButton getBtnUpdateFlight() {
		if (btnUpdateFlight == null) {
			btnUpdateFlight = new JButton("Update Flight");
		}
		return btnUpdateFlight;
	}

	private JButton getBtnAddFlight() {
		if (btnAddFlight == null) {
			btnAddFlight = new JButton("Add Flight");
		}
		return btnAddFlight;
	}

	private JLabel getLabelOriginCountry() {
		if (labelOriginCountry == null) {
			labelOriginCountry = new JLabel("Origin Country:");
		}
		return labelOriginCountry;
	}

	private JTextField getTxtOriginCountry() {
		if (txtOriginCountry == null) {
			txtOriginCountry = new JTextField();
			txtOriginCountry.setColumns(10);
		}
		return txtOriginCountry;
	}

	private JLabel getLblDestinationCountry() {
		if (lblDestinationCountry == null) {
			lblDestinationCountry = new JLabel("Destination Country:");
		}
		return lblDestinationCountry;
	}

	private JTextField getTxtDestinationCountry() {
		if (txtDestinationCountry == null) {
			txtDestinationCountry = new JTextField();
			txtDestinationCountry.setColumns(10);
		}
		return txtDestinationCountry;
	}

	private JTextField getTxtPrice() {
		if (txtPrice == null) {
			txtPrice = new JTextField();
			txtPrice.setColumns(10);
		}
		return txtPrice;
	}

	private JTextField getTxtCompany() {
		if (txtCompany == null) {
			txtCompany = new JTextField();
			txtCompany.setColumns(10);
		}
		return txtCompany;
	}

	private JTextField getTxtPlaneModel() {
		if (txtPlaneModel == null) {
			txtPlaneModel = new JTextField();
			txtPlaneModel.setColumns(10);
		}
		return txtPlaneModel;
	}

	private JTextField getTxtYear() {
		if (txtYear == null) {
			txtYear = new JTextField();
			txtYear.setColumns(10);
		}
		return txtYear;
	}
}
