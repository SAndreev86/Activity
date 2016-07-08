package ru.simbirsoft.intensiv.report;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;

import ru.simbirsoft.intensiv.TimeConverter;
import ru.simbirsoft.intensiv.TrackingWindow;
import ru.simbirsoft.intensiv.workWithDB.DataStatisticDB;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ReportHistoryButton implements ActionListener {

	String name;
	TrackingWindow trackingWindow;

	public ReportHistoryButton(String name) {
		this.name = name;
	}

	private JComboBox<String> comboBox;

	public void actionPerformed(ActionEvent e) {

		JDialog dialog = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setTitle("История отчетов");

		JPanel jPanelD = new JPanel();
		jPanelD.setLayout(null);

		String ThreeDays[] = new String[3];

		for (int n = 3; n > 0; n--) {

			Calendar ca = Calendar.getInstance();

			ca.add(Calendar.DAY_OF_MONTH, -n);

			Date strT = ca.getTime();

			int DayN = new Integer(new SimpleDateFormat("dd").format(strT));
			int MonthN = new Integer(new SimpleDateFormat("MM").format(strT));
			int YearN = new Integer(new SimpleDateFormat("yyyy").format(strT));

			ThreeDays[3 - n] = DayN + "." + MonthN + "." + YearN;

			System.out.println(ThreeDays[3 - n]);

		}

		comboBox = new JComboBox<String>(ThreeDays);
		comboBox.setBounds(0, 0, 170, 20);
		jPanelD.add(comboBox);

		JEditorPane incomingD = new JTextPane();
		incomingD.setContentType("text/html");
		incomingD.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
		// incomingD.setLineWrap(true);
		// incomingD.setWrapStyleWord(true);
		incomingD.setEditable(false);

		JScrollPane qScrollerD = new JScrollPane(incomingD);
		qScrollerD.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScrollerD.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		qScrollerD.setLocation(0, 20);
		qScrollerD.setSize(785, 465);

		jPanelD.add(qScrollerD);

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -3);
		Date strT = ca.getTime();

		List<DataStatisticDB> stat = WorkWithDB.getStatistic(name, strT);

		// for (DataStatisticDB actD : stat) {
		//
		// incomingD.append("Активность: " + actD.getActivity() + "; затраченное
		// время (чч:мм:сс): "
		// + TimeConverter.convert(actD.getTime()) + "\n");
		//
		// if (!"".equals(actD.getComment())) {
		// incomingD.append("Комментарий: " + actD.getComment() + "\n");
		// incomingD.append(" \n");
		// }
		// }

		String TempincomingD = "<h1><font face=\"Arial\">Oтчет за: " + new SimpleDateFormat("dd.MM.yyyy").format(strT) + "</font></h1>";

		for (DataStatisticDB actD : stat) {

			TempincomingD += "<h2><font face=\"Arial\">Время, затраченное на " + actD.getActivity() + ", составляет: "
					+ TimeConverter.convert(actD.getTime()) + "</font></h2>";

			if (!"".equals(actD.getComment())) {

				TempincomingD += "<p><font face=\"Arial\">Комментарий: " + actD.getComment() + "</font></p><br>";
			}

		}

		incomingD.setText(TempincomingD);

		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				incomingD.setText("");

				for (int n = 3; n > 0; n--) {

					Calendar ca = Calendar.getInstance();

					ca.add(Calendar.DAY_OF_MONTH, -n);

					Date strT = ca.getTime();

					System.out.println(strT);
					System.out.println(comboBox.getSelectedItem());
					System.out.println(ThreeDays[3 - n]);

					if (ThreeDays[3 - n].equals(comboBox.getSelectedItem())) {

						List<DataStatisticDB> stat = WorkWithDB.getStatistic(name, strT);

						String TempincomingD = "<h1><font face=\"Arial\">Oтчет за: " + new SimpleDateFormat("dd.MM.yyyy").format(strT)
								+ "</font></h1>";

						for (DataStatisticDB actD : stat) {

							TempincomingD += "<h2><font face=\"Arial\">Время, затраченное на " + actD.getActivity() + ", составляет: "
									+ TimeConverter.convert(actD.getTime()) + "</font></h2>";

							if (!"".equals(actD.getComment())) {

								TempincomingD += "<p><font face=\"Arial\">Комментарий: " + actD.getComment() + "</font></p><br>";
							}

						}

						incomingD.setText(TempincomingD);
					}
				}
			}
		});

		// реализация клавиши для истории/////////////////////////
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dialog.dispose();
				}
				return false;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

		});
		////////////////////////////////////////////////////

		dialog.setBounds(0, 0, 800, 520);
		dialog.setResizable(false);
		dialog.getContentPane().add(jPanelD);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}
}