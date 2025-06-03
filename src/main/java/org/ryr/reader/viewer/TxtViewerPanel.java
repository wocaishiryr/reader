package org.ryr.reader.viewer;

import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TxtViewerPanel {

    public static JPanel viewerPanel(String filePath) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // 设置为只读
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // 设置字体

        JBScrollPane scrollPane = new JBScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        File file = new File(filePath);
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (Exception e) {
            textArea.append("无法读取文件: " + e.getMessage());
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}

