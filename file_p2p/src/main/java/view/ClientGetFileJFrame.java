package view;

import client.message.ClientMessageEnum;
import client.send.SendMessage;
import model.MessageInfo;
import org.apache.log4j.Logger;
import resourcetable.ResourceTable;
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
    private String keyWords;
    private JFrame jFrame = new JFrame();
    private JList<String> fileList;
    private DefaultListModel<String> fileModel = new DefaultListModel<>();
    private JButton choose = new JButton("选择");
    private JButton cancel = new JButton("取消");

    public ClientGetFileJFrame(String keyWords, Socket socket) {
        this.keyWords = keyWords;
        this.socket = socket;
    }

    public void init() {
        int[] result = ViewUtil.getWidAndHei();
        jFrame.setBounds(result[0], result[1], ViewUtil.FRAME_WIDTH, ViewUtil.FRAME_HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Set<String> fileNames = ResourceTable.queryFileNameByKeyword(keyWords);
        fileNames.stream().forEach(e -> fileModel.addElement(e));
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
            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
            MessageInfo messageInfo = new MessageInfo(NodeTypeEnum.CLIENT.getCode(),
                    socket.getLocalAddress() + "|" + socket.getPort(), NodeTypeEnum.SERVER.getCode(), "",
                    ClientMessageEnum.REQUEST.getCode(), keyWords);
            SendMessage.sendMessage(messageInfo, socket);
        });
    }

    private void cancel() {
        cancel.addActionListener(evn -> jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING)));
    }

    public static void main(String[] args) {
        new ClientGetFileJFrame("test", null).init();
    }
}
