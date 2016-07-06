package ru.simbirsoft.intensiv.report;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import ru.simbirsoft.intensiv.TimeConverter;
import ru.simbirsoft.intensiv.workWithDB.DataStatisticDB;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

public class ReportHistoryButton implements ActionListener {

	String name;

	public ReportHistoryButton(String name) {
		this.name = name;
		System.out.println(name);
	}

	private JComboBox<String> comboBox;

	public void actionPerformed(ActionEvent e) {

		JFrame frameD = new JFrame("История отчетов");
		JPanel jPanelD = new JPanel();
		jPanelD.setLayout(null);

		frameD.add(jPanelD);

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

		JTextArea incomingD = new JTextArea(20, 30);
		incomingD.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		incomingD.setLineWrap(true);
		incomingD.setWrapStyleWord(true);
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

		for (DataStatisticDB actD : stat) {

			incomingD.setText("Активность: " + actD.getActivity() + "; затраченное время (чч:мм:сс): "
					+ TimeConverter.convert(actD.getTime()) + "\n");

			if (!"".equals(actD.getComment())) {
				incomingD.append("Комментарий: " + actD.getComment() + "\n");
				incomingD.append(" \n");
			}
		}

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

						for (DataStatisticDB actD : stat) {

							incomingD.setText("Активность: " + actD.getActivity() + "; затраченное время (чч:мм:сс): "
									+ TimeConverter.convert(actD.getTime()) + "\n");

							if (!"".equals(actD.getComment())) {
								incomingD.append("Комментарий: " + actD.getComment() + "\n");
								incomingD.append(" \n");
							}
						}
					}
				}
			}
		});

		// реализация клавиши для истории/////////////////////////
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frameD.dispose();
				}
				return false;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

		frameD.addWindowListener(new WindowListener() {

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

		frameD.setVisible(true);
		frameD.setSize(800, 520);
		frameD.setLocationRelativeTo(null);
		frameD.setResizable(false);

	}
}