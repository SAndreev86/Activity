package ru.simbirsoft.intensiv.report;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import ru.simbirsoft.intensiv.TimeConverter;
import ru.simbirsoft.intensiv.workWithDB.DataStatisticDB;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

public class StatsButton implements ActionListener {
	
	String name;

	public StatsButton(String name) {
		this.name = name;
		System.out.println(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
				
		JFrame frameL = new JFrame("Краткий отчет");
		JPanel jPanelL = new JPanel();
		jPanelL.setLayout(null);
		
		frameL.add(jPanelL);
		
		
		
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
		qScrollerL.setLocation(0,0);
		qScrollerL.setSize(650, 500);

		jPanelL.add(detailedStatL);
		jPanelL.add(qScrollerL);
		
		
		List<DataStatisticDB> stat = WorkWithDB.getStatistic(name, new Date());
		
		
		
						
		List<DataStatisticDB> acty = new ArrayList<DataStatisticDB>();
		
		for(DataStatisticDB a : stat) {
			
			DataStatisticDB temp = new DataStatisticDB();
			temp.setActivity(a.getActivity());
			temp.setTime(a.getTime());
			
			acty.add(temp);
		}
		
		for(int n = 0; n < acty.size(); n++) {
			for(int t = 0; t < acty.size(); t++) {
				
				if(acty.get(n).getActivity().equals((acty.get(t).getActivity())) && n != t) {
					acty.get(n).setTime(acty.get(n).getTime() + acty.get(t).getTime());
					acty.remove(t);
					
				}
				
			}
		}
		
		incomingL.append("Краткий отчет за: "+new SimpleDateFormat("dd.MM.yyyy").format(new Date())+"\n");
		incomingL.append(" \n");
		
		for(DataStatisticDB s : acty) {
			incomingL.append("Время, затраченное на "+s.getActivity()+", составляет: "+TimeConverter.convert(s.getTime())+"\n");
		}
		detailedStatL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame  frameD = new JFrame("Развернутый отчет");
				JPanel jPanelD = new JPanel();
				jPanelD.setLayout(null);
				
				frameD.add(jPanelD);
	
				JTextArea incomingD = new JTextArea(20, 30);
				incomingD.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
				incomingD.setLineWrap(true);
				incomingD.setWrapStyleWord(true);
				incomingD.setEditable(false);
				
				JScrollPane qScrollerD = new JScrollPane(incomingD);
				qScrollerD.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				qScrollerD.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				qScrollerD.setLocation(0,0);
				qScrollerD.setSize(785, 465);


				jPanelD.add(qScrollerD);
				
				incomingD.append("Подробный отчет за: "+new SimpleDateFormat("dd.MM.yyyy").format(new Date())+"\n");
				incomingD.append(" \n");
				
				for(DataStatisticDB actD : stat) {
					
					incomingD.append("Время, затраченное на "+actD.getActivity()+", составляет: "+TimeConverter.convert(actD.getTime())+"\n");
					
					if(!"".equals(actD.getComment())) {
						incomingD.append("Комментарий: "+actD.getComment()+"\n");
						incomingD.append(" \n");
					}
				}
				
				//реализация клавиши для подробной статистики///////////////////////////////////////////////////////////////////////////////////////////////////				
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
					public void windowActivated(WindowEvent arg0) { }

					@Override
					public void windowClosed(WindowEvent arg0) {
				        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);	
					}

					@Override
					public void windowClosing(WindowEvent arg0) {
				        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);	
					}

					@Override
					public void windowDeactivated(WindowEvent arg0) { }

					@Override
					public void windowDeiconified(WindowEvent arg0) { }

					@Override
					public void windowIconified(WindowEvent arg0) { }

					@Override
					public void windowOpened(WindowEvent arg0) { }
		        	
		        });
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		        
				frameD.setVisible(true);
				frameD.setSize(800, 500);
				frameD.setLocationRelativeTo(null);
				frameD.setResizable(false);
				
				//закрываем фрейм краткой статистики
				frameL.dispose();
				
			}	
		});
		
		//реализация клавиш для краткой статистики///////////////////////////////////////////////////////////////////////////////////////////////////
		KeyEventDispatcher keyEvent = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    				frameL.dispose();
                }
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                	detailedStatL.doClick();
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEvent);
        
        frameL.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) { }

			@Override
			public void windowClosed(WindowEvent arg0) {
		        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
		        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) { }

			@Override
			public void windowDeiconified(WindowEvent arg0) { }

			@Override
			public void windowIconified(WindowEvent arg0) { }

			@Override
			public void windowOpened(WindowEvent arg0) { }
        	
        });
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
		frameL.setVisible(true);
		frameL.setSize(667, 567);
		frameL.setLocationRelativeTo(null);
		frameL.setResizable(false);
		
	}
	
	public void OnButton( JFrame frame) {
		
	}
	
}