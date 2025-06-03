package org.ryr.reader;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.ryr.reader.viewer.PdfViewerPanel;
import org.ryr.reader.viewer.TxtViewerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class ReaderToolWindow implements ToolWindowFactory, DumbAware {

    private static JPanel jPanel;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CustomViewerPanel toolWindowContent = new CustomViewerPanel();
        Content content = ContentFactory.getInstance().createContent(toolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public static class CustomViewerPanel extends JPanel {

        public CustomViewerPanel() {
            this.setLayout(new BorderLayout());
            this.setVisible(true);

            CustomViewerPanel that = this;

            JButton openButton = new JButton("选择文件");
            JButton closeButton = new JButton("清空");
            JButton helpButton = new JButton("支持一下");

            JPanel toolPanel = new JPanel();
            toolPanel.add(openButton);
            toolPanel.add(closeButton);
            toolPanel.add(helpButton);
            toolPanel.setVisible(true);

            this.add(toolPanel, BorderLayout.NORTH);

            openButton.addActionListener(e -> {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(that);

                File selectedFile = jFileChooser.getSelectedFile();

                if (selectedFile != null && selectedFile.getName().endsWith(".pdf")) {
                    String filePath = selectedFile.getAbsolutePath();
                    if (jPanel == null) {
                        jPanel = PdfViewerPanel.viewerPanel(filePath);
                        that.remove(jPanel);
                        that.add(jPanel,BorderLayout.CENTER);
                    } else {
                        that.remove(jPanel);
                        jPanel = PdfViewerPanel.viewerPanel(filePath);
                        that.add(jPanel,BorderLayout.CENTER);
                    }

                } else if (selectedFile != null ) {
                    String filePath = selectedFile.getAbsolutePath();
                    if (jPanel == null) {
                        jPanel = TxtViewerPanel.viewerPanel(filePath);
                        that.remove(jPanel);
                        that.add(jPanel,BorderLayout.CENTER);
                    } else {
                        that.remove(jPanel);
                        jPanel = TxtViewerPanel.viewerPanel(filePath);
                        that.add(jPanel,BorderLayout.CENTER);
                    }
                }

                that.revalidate();
                that.repaint();
            });

            closeButton.addActionListener(e -> {
                if (jPanel != null) {
                    that.remove(jPanel);
                    that.revalidate();
                    that.repaint();
                }
            });

            helpButton.addActionListener(e -> {
                JFrame frame = new JFrame("打个赏支持一下吧!");
                frame.setLocationRelativeTo(null);
                frame.setSize(400, 460);
                frame.setVisible(true);

                JTabbedPane tab = new JTabbedPane();

                ImageIcon aliImageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/payImg/ali.jpg")));
                ImageIcon wxImageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/payImg/wx.jpg")));

                tab.addTab("支付宝", new JLabel(aliImageIcon));
                tab.addTab("微信", new JLabel(wxImageIcon));

                frame.setContentPane(tab);
            });
        }
    }
}
