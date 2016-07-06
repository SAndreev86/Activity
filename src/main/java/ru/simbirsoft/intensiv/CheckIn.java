package ru.simbirsoft.intensiv;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckIn  implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        class Temp extends JFrame{
            public Temp(){
                setVisible(true);
                setResizable(false);
                setTitle("Регистрация");
                setSize(350, 250);
                setLocationRelativeTo(null);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(null);

                JLabel name = new JLabel("Имя:");
                name.setLayout(null);
                name.setBounds(80, 40, 50, 20);

                jPanel.add(name);

                JTextField name1 = new JTextField();
                name1.setLayout(null);
                name1.setBounds(140, 40, 120, 20);

                jPanel.add(name1);

                JLabel string1 = new JLabel("20 символов. Допустимы: A-z,А-я,0-9 и пробелы в середине");
                string1.setLayout(null);
                string1.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
                string1.setBounds(20, 60, 300, 20);

                jPanel.add(string1);


                JLabel password = new JLabel("Пароль:");
                password.setLayout(null);
                password.setBounds(80, 90, 50, 20);

                jPanel.add(password);

                JTextField password1 = new JTextField();
                password1.setLayout(null);
                password1.setBounds(140, 90, 120, 20);

                jPanel.add(password1);


                JLabel string2 = new JLabel("16 символов. Допустимы: A-z,0-9");
                string2.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
                string2.setLayout(null);
                string2.setBounds(90, 110, 300, 20);

                jPanel.add(string2);

                JLabel mistake = new JLabel("Пароль/Логин не соответствует требованиям!");
                mistake.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
                mistake.setLayout(null);
                mistake.setVisible(false);
                mistake.setBounds(60, 20, 300, 20);

                jPanel.add(mistake);

                JLabel mistake1 = new JLabel("Логин занят!");
                mistake1.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
                mistake1.setLayout(null);
                mistake1.setVisible(false);
                mistake1.setBounds(160, 20, 300, 20);

                jPanel.add(mistake1);

                JLabel test2 = new JLabel("Максимальное количество пользователей = 4");
                test2.setVisible(false);
                test2.setLayout(null);
                test2.setBounds(60, 20, 300, 20);

                jPanel.add(test2);

                JButton cancel = new JButton("Отмена");
                cancel.setLayout(null);
                cancel.setBounds(30, 160, 80, 25);

                jPanel.add(cancel);

                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                    }
                });

                JButton enter = new JButton("Зарегистрироваться");
                enter.setLayout(null);
                enter.setBounds(150, 160, 170, 25);

                jPanel.add(enter);


                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //если имя уже занято
                        String tempName = name1.getText();
                        String tempPassword = password1.getText();
                        if(TrackingWindow.tabbedPane.getTabCount() < 5) {
                            if (WorkWithDB.isExistUser(tempName)) {
                                //сделать надпись видимой а остальные скрыть
                                mistake.setVisible(false);
                                mistake1.setVisible(true);
                                name1.setText(null);
                                password1.setText(null);
                            } else {
                                //проверки на длину и тд
                                Pattern p = Pattern.compile("^[А-Яа-яA-Za-z0-9]{1}[А-Яа-яA-Za-z0-9 ]{0,18}[А-Яа-яA-Za-z0-9]{0,1}$");
                                Matcher m = p.matcher(tempName);

                                Pattern p1 = Pattern.compile("^[A-Za-z0-9]{0,16}$");
                                Matcher m1 = p1.matcher(tempPassword);

                                if (m.matches() && m1.matches() && tempName.charAt(tempName.length() - 1) != ' ') {
                                    //добавить панель  и записать юзера в базу
                                    Password.doYouNeedCheckPassword = false;
                                    RunWindow.tabIsStarted.put(tempName, false);
                                    WorkWithDB.writeNewUser(tempName, tempPassword);
                                    TrackingWindow.addPanel(tempName);

                                    setVisible(false);
                                } else {
                                    //вывести сообщенине о неверном вводе остальные скрыть
                                    mistake1.setVisible(false);
                                    mistake.setVisible(true);
                                    name1.setText(null);
                                    password1.setText(null);
                                }
                            }
                        }else{
                            mistake.setVisible(false);
                            mistake1.setVisible(false);
                            test2.setVisible(true);
                            name1.setText(null);
                            password1.setText(null);
                        }
                    }
                });
                getContentPane().add(jPanel);
            }
        }
        new Temp();
    }
}
