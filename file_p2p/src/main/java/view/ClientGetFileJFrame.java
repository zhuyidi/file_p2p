package view;

import client.message.ClientMessageEnum;
import client.receive.ReceiveCenter;
import client.send.SendMessageForClient;
import model.ConfigInfo;
import model.MessageInfo;
import org.apache.log4j.Logger;
import util.NodeTypeEnum;
import util.ViewUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.Set;

/**
 *  by yidi on 5/7/19
 */

public class ClientGetFileJFrame {
    private static final Logger LOGGER = Logger.getLogger(ClientGetFileJFrame.class);
    private Socket socket;
    private JFrame jFrame = new JFrame();
    private JList<String> fileList;
    private DefaultListModel<String> fileModel = new DefaultListModel<>();
    private JButton choose = new JButton("选择");
    private JButton cancel = new JButton("取消");
    private Set<String> fileNameList;
    private ConfigInfo configInfo;


    public ClientGetFileJFrame(Socket socket, ConfigInfo configInfo, Set<String> fileNameList) {
        this.socket = socket;
        this.configInfo = configInfo;
        this.fileNameList = fileNameList;
        if (fileNameList == null || fileNameList.size() == 0) {
            JOptionPane.showMessageDialog(null, "抱歉！你要找的资源不存在", "资源不存在", JOptionPane.WARNING_MESSAGE);
        } else {
            init();
        }
    }

    public void init() {
        int[] result = ViewUtil.getWidAndHei();
        jFrame.setBounds(result[0], result[1], ViewUtil.FRAME_WIDTH, ViewUtil.FRAME_HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileNameList.stream().forEach(e -> fileModel.addElement(e));
        fileList = new JList<>(fileModel);
        fileList.setVisibleRowCount(5);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        choose();
        cancel();

        JPanel jPanel = new JPanel();
        jPanel.add(choose);
        jPanel.add(cancel);
        jFrame.add(new JScrollPane(fileList));
        jFrame.add(jPanel, BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }

    private void choose() {
        choose.addActionListener(evn -> {
            MessageInfo messageInfo = new MessageInfo(NodeTypeEnum.CLIENT.getCode(),
                    socket.getLocalAddress().getHostAddress() + "|" + ReceiveCenter.PORT, NodeTypeEnum.SERVER.getCode(), "",
                    ClientMessageEnum.REQUEST.getCode(), fileList.getSelectedValue());
            System.out.println(fileList.getSelectedValue());
            SendMessageForClient.sendMessage(messageInfo, socket);
            jFrame.dispose();
//            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
        });
    }

    private void cancel() {
        cancel.addActionListener(evn -> jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING)));
    }
}
