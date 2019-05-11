package view;

import client.core.ClientThread;
import model.ConfigInfo;
import resourcetable.ResourceTable;
import util.ViewUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.Set;

/**
 *  by yidi on 5/6/19
 */

public class ClientMainJFrame {
    private JFrame clientJFrame = new JFrame();
    private Container container = clientJFrame.getContentPane();
    private Socket socket;
    private ConfigInfo configInfo;

    public ClientMainJFrame(Socket socket, ConfigInfo configInfo) {
        this.socket = socket;
        this.configInfo = configInfo;
        clientJFrame.setName(socket.getLocalAddress().getHostName() + " " + socket.getPort());
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
                boolean result = ResourceTable.updateResourceTableForOnline(socket.getLocalAddress().getHostAddress()
                        + "|" + socket.getPort(), configInfo);
                if (result) {
                    JOptionPane.showMessageDialog(null, "本地资源已同步到云端！", "更新成功"
                        , JOptionPane.OK_OPTION);
                } else {
                    JOptionPane.showMessageDialog(null, "资源更新失败", "更新失败"
                            , JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取输入的资源名，并进行检索
                Set<String> fileNames = ResourceTable.queryFileNameByKeyword(fileName.getText());
                new ClientGetFileJFrame(socket, fileNames);
            }
        });
        new Thread(new ClientThread(socket, configInfo)).start();
    }

    public static void main(String[] args) {
        new ClientMainJFrame(new Socket(), new ConfigInfo()).initFrame();
    }
}
