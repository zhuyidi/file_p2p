package view;

import client.core.ClientThread;
import util.ViewUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

/**
 *  by yidi on 5/6/19
 */

public class ClientMainJFrame {
    private JFrame clientJFrame = new JFrame();
    private Container container = clientJFrame.getContentPane();
    private Socket socket;

    public ClientMainJFrame(Socket socket) {
        this.socket = socket;
        clientJFrame.setName(socket.getLocalAddress() + " " + socket.getPort());
    }

    public void initFrame() {
        JLabel welcome = new JLabel("欢迎使用本系统");
        JButton updateResource = new JButton("更新资源");
        JTextField fileName = new JTextField(10);
        JLabel text1 = new JLabel();
        text1.setText("点此更新资源：");
        JLabel text2 = new JLabel();
        text2.setText("请输入资源名：");
        JButton search = new JButton("查找");

        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        jPanel1.add(welcome);
        jPanel2.add(text1);
        jPanel2.add(updateResource);
        jPanel3.add(text2);
        jPanel3.add(fileName);
        jPanel3.add(search);

        int[] result = ViewUtil.getWidAndHei();
        clientJFrame.setBounds(result[0], result[1], ViewUtil.FRAME_WIDTH, ViewUtil.FRAME_HEIGHT);
        clientJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientJFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
//        container.setLayout(new GridLayout(3,2));  //设置容器布局
//        container.add(jPanel1);
        container.add(jPanel3);
        container.add(jPanel2);
        clientJFrame.setVisible(true);

        updateResource.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // todo 更新客户端资源
                System.out.println("hello");
            }
        });
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取输入的资源名，并进行检索
                new ClientGetFileJFrame(fileName.getText()).init();
            }
        });
        new Thread(new ClientThread(socket)).start();
    }

    private void getFileList(int fileCount) {
        while (fileCount > 0) {
            JPanel jPanel = new JPanel();
            JButton jButton = new JButton(String.valueOf(fileCount));
            jButton.setSize(10, 4);
            jPanel.add(jButton);
            container.add(jPanel);
            System.out.println(fileCount);
            fileCount--;
        }
        clientJFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ClientMainJFrame(new Socket()).initFrame();
    }
}
