import containers.Card;
import containers.Center;
import containers.Flex;
import containers.TransparentContainer;
import forms.PasswordField;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;
import views.ButtonLoader;
import views.ImageView;
import views.UIFilePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.Paths;


public class TestUI extends JFrame{

    public TestUI() throws HeadlessException {
//        setLayout(new FlowLayout(FlowLayout.LEADING));
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Action getAction(){
        return new Action() {
            @Override
            public Object getValue(String key) {

                return "1";
            }

            @Override
            public void putValue(String key, Object value) {
                HPGui.log("put");
            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };


    }

    public ActionListener getActionListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    public JMenuItem getItem(String s){
        return new JMenuItem(s);
    }
    public static void main(String[] args) throws InterruptedException, IOException {

        HPGui hp = new HPGui();
        HPGui.registerFonts();


        TestUI testUI = new TestUI();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("menu");

        menuBar.add(menu);

        JMenuItem item1 = new JMenuItem("menuItem");
        menu.add(testUI.getItem("item 2"));
        menu.add(testUI.getItem("item 3"));
        menu.add(testUI.getItem("item 4"));
        menu.add(item1);


        testUI.setJMenuBar(menuBar);

        ImageView imageView = new ImageView("views/forms/eye.png");
        testUI.add(imageView);
        testUI.setVisible(true);

        Thread.sleep(2000);
        imageView.changeColor(HPGui.getColor("#3b3b3b"), Color.red);

    }



}
