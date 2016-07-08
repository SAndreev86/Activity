package ru.simbirsoft.intensiv.report;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;

import ru.simbirsoft.intensiv.TimeConverter;
import ru.simbirsoft.intensiv.TrackingWindow;
import ru.simbirsoft.intensiv.workWithDB.DataStatisticDB;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class StatsButton implements ActionListener {

	String name;
	TrackingWindow trackingWindow;

	public StatsButton(String name, TrackingWindow trackingWindow) {
		this.name = name;
		this.trackingWindow = trackingWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JDialog dialog = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setTitle("Краткий отчет");

		JPanel jPanelL = new JPanel();
		jPanelL.setLayout(null);

		JButton detailedStatL = new JButton("подробнее..");
		detailedStatL.setLayout(null);
		detailedStatL.setBounds(460, 500, 190, 30);

		JTextArea incomingL = new JTextArea(20, 30);
		incomingL.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		incomingL.setLineWrap(true);
		incomingL.setWrapStyleWord(true);
		incomingL.setEditable(false);

		JScrollPane qScrollerL = new JScrollPane(incomingL);
		qScrollerL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScrollerL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		qScrollerL.setLocation(0, 0);
		qScrollerL.setSize(650, 500);

		jPanelL.add(detailedStatL);
		jPanelL.add(qScrollerL);

		List<DataStatisticDB> acty = WorkWithDB.getStatistic(name, new Date());

		// HashMap<String, DataStatisticDB> actySet = new HashMap<>();
		//
		// for(DataStatisticDB i : acty){
		// actySet.put(i.getActivity(), i);
		// }
		//
		// for(Entry<String, DataStatisticDB> i : actySet.entrySet()){
		// for(DataStatisticDB j: acty){
		// if(i.getValue().getActivity().equals(j.getActivity()) &&
		// !i.getValue().equals(j)){
		// i.getValue().setTime(i.getValue().getTime() + j.getTime());
		// }
		// }
		// }

		LinkedHashMap<String, DataStatisticDB> actySet = new LinkedHashMap<String, DataStatisticDB>();

		for (DataStatisticDB s : acty) {
			if (actySet.containsKey(s.getActivity())) {

				DataStatisticDB tempDB = actySet.get(s.getActivity());
				tempDB.setTime(tempDB.getTime() + s.getTime());
				actySet.put(s.getActivity(), tempDB);

			} else {

				actySet.put(s.getActivity(), s);

			}
		}

		incomingL.append("Краткий отчет за: " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + "\n");
		incomingL.append(" \n");

		for (Entry<String, DataStatisticDB> entry : actySet.entrySet()) {
			incomingL.append("Время, затраченное на " + entry.getValue().getActivity() + ", составляет: "
					+ TimeConverter.convert(entry.getValue().getTime()) + "\n");
		}

		detailedStatL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JDialog dialog1 = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
				dialog1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dialog1.setTitle("Развернутый отчет");

				JPanel jPanelD = new JPanel();
				jPanelD.setLayout(null);

				JEditorPane incomingD = new JTextPane();
				incomingD.setContentType("text/html");
				incomingD.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
				// incomingD.setLineWrap(true);
				// incomingD.setWrapStyleWord(true);
				incomingD.setEditable(false);

				JScrollPane qScrollerD = new JScrollPane(incomingD);
				qScrollerD.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				qScrollerD.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				qScrollerD.setLocation(0, 0);
				qScrollerD.setSize(785, 465);

				jPanelD.add(qScrollerD);

				List<DataStatisticDB> acty = WorkWithDB.getStatistic(name, new Date());

				String TempincomingD = "<h1><font face=\"Arial\">Подробный отчет за: "
						+ new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + "</font></h1>";

				for (DataStatisticDB actD : acty) {

					TempincomingD += "<h2><font face=\"Arial\">Время, затраченное на " + actD.getActivity() + ", составляет: "
							+ TimeConverter.convert(actD.getTime()) + "</font></h2>";

					if (!"".equals(actD.getComment())) {

						TempincomingD += "<p><font face=\"Arial\">Комментарий: " + actD.getComment() + "</font></p><br>";
					}

				}

				incomingD.setText(TempincomingD);

				// реализация клавиши для подробной
				// статистики///////////////////////////////////////////////////////////////////////////////////////////////////
				KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(final KeyEvent e) {
						if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							dialog1.dispose();
						}
						return false;
					}
				};
				KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

				dialog1.addWindowListener(new WindowListener() {

					@Override
					public void windowActivated(WindowEvent arg0) {
					}

					@Override
					public void windowClosed(WindowEvent arg0) {
						KeyboardFocusManager.getCurrentKeyboardFocusManager()
								.removeKeyEventDispatcher(keyEventDispatcher);
					}

					@Override
					public void windowClosing(WindowEvent arg0) {
						KeyboardFocusManager.getCurrentKeyboardFocusManager()
								.removeKeyEventDispatcher(keyEventDispatcher);
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
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				dialog.dispose();
				dialog1.setBounds(0, 0, 800, 500);
				dialog1.setResizable(false);
				dialog1.getContentPane().add(jPanelD);
				dialog1.setLocationRelativeTo(null);
				dialog1.setVisible(true);

				// закрываем фрейм краткой статистики

			}
		});

		// реализация клавиш для краткой
		// статистики///////////////////////////////////////////////////////////////////////////////////////////////////
		KeyEventDispatcher keyEvent = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dialog.dispose();
				}
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
					detailedStatL.doClick();
				}
				return false;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEvent);

		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
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
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		dialog.setBounds(0, 0, 667, 567);
		dialog.setResizable(false);
		dialog.getContentPane().add(jPanelL);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

	public void OnButton(JFrame frame) {

	}

}