package org.ryr.reader.viewer;

import com.intellij.ui.jcef.JBCefBrowser;

import javax.swing.*;
import java.awt.*;

public class PdfViewerPanel {

    public static JPanel viewerPanel(String filePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JBCefBrowser browser = new JBCefBrowser();
        browser.loadURL("file:///" + filePath);
        panel.add(browser.getComponent());
        return panel;
    }

}

