package ru.simbirsoft.intensiv;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

@SuppressWarnings("serial")
public class AddCommentWindow extends JFrame {

    JTextArea comment;
    JButton addCommentButton;
    JButton skipCommentButton;

    private long timerTime;
    private String tabName;
    private String selectedItem;

    TrackingWindow trackingWindow;

    public AddCommentWindow(String tabName, String selectedItem, long timerTime) {

        this.timerTime = timerTime;
        this.tabName = tabName;
        this.selectedItem = selectedItem;
    }

    public void showDialog(TrackingWindow trackingWindow){

        JDialog dialog = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setTitle("Добавление комментария");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(300, 200);

        JLabel commentLabel = new JLabel("Ваш комментарий:");
        commentLabel.setBounds(50, 5, 150, 20);
        panel.add(commentLabel);

        comment = new JTextArea();
        comment.setFont((new Font("Arial", Font.CENTER_BASELINE, 11)));
        comment.setBounds(10, 10, 280, 50);
        comment.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(comment);
        scroll.setBounds(10, 30, 280, 50);
        panel.add(scroll);

        JLabel simpleString = new JLabel("Максимальное кол-во символов = 140");
        simpleString.setFont((new Font("Arial", Font.CENTER_BASELINE, 11)));
        simpleString.setVisible(false);
        simpleString.setLayout(null);
        simpleString.setBounds(20, 80, 300, 15);
        panel.add(simpleString);

        JLabel longString = new JLabel("Не может быть пустой, состоящий из пробелов");
        longString.setFont((new Font("Arial", Font.CENTER_BASELINE, 11)));
        longString.setVisible(false);
        longString.setLayout(null);
        longString.setBounds(20, 80, 250, 15);
        panel.add(longString);

        addCommentButton = new JButton("Oтправить");
        addCommentButton.setBounds(30, 120, 100, 35);
        panel.add(addCommentButton);
        addCommentButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String tempName = comment.getText();
                Pattern p1 = Pattern.compile("^[ ]{0,26}$");
                Matcher m1 = p1.matcher(tempName);
                if (m1.matches()) {
                    //что не может быть пустой или состоящей из пробелов
                    simpleString.setVisible(false);
                    longString.setVisible(true);
                    comment.setText(null);

                } else {
                    Pattern p = Pattern.compile("^[А-Яа-яA-Za-z0-9!@#%=ёЁ^\\-/&*()_+}:?>\"\\[\\]\\{\\}<№;\\.\\,]{1}" + "[А-Яа-яA-Za-z0-9!@#%=ёЁ^\\-/&*()_+}:?>\"\\[\\]\\{\\}<№;\\.\\, ]{0,138}"
                            + "[А-Яа-яA-Za-z0-9!@#=%ёЁ^\\-/&*()_+}:?>\"\\[\\]\\{\\}<№;\\.\\,]{0,1}$");
                    Matcher m = p.matcher(tempName);
                    if (m.matches()) {
                        WorkWithDB.wtiteStatistic(selectedItem, timerTime, comment.getText(), tabName, new Date());
                        System.out.println(tabName + " " + selectedItem + " " + comment.getText() + " " + timerTime);
                        dialog.dispose();
                    } else {
                        //выходит надпись что нарушены правила
                        simpleString.setVisible(true);
                        longString.setVisible(false);
                        comment.setText(null);
                    }
                }

            }
        });

        // реализация клавиш//////////////////////////////////////////////
        addCommentButton.setFocusable(false);

        KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dialog.dispose();
                }
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addCommentButton.doClick();
                }
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

        addWindowListener(new WindowListener() {

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
        ///////////////////////////////////////////////////

        skipCommentButton = new JButton("Пропустить");
        skipCommentButton.setBounds(160, 120, 100, 35);
        panel.add(skipCommentButton);
        skipCommentButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comment.setText("");
                // метод добавления записи в БД

                WorkWithDB.wtiteStatistic(selectedItem, timerTime, comment.getText(), tabName, new Date());

                System.out.println(tabName + " " + selectedItem + " " + comment.getText() + " " + timerTime);

               dialog.dispose();

            }
        });


        dialog.setBounds(0, 0, 300, 200);
        dialog.setResizable(false);
        dialog.getContentPane().add(panel);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

}