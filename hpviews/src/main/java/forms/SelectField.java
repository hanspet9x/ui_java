package forms;

import animations.Rotate;
import animations.Scale;
import containers.Card;
import containers.Flex;
import controllers.AncestorAdapter;
import controllers.PopupMenuAdapter;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;
import views.ImageView;
import views.OverflowView;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class SelectField<T> extends Form{

    private T [] values = null;
    private T value = null;
    private Rotate rotatedImageView;
    private boolean hasSize = false;
    private OverflowView valuesWrapper;
    private Scale scale;
    private int valueHeight = 40;
    private JComboBox<T> comboBox;
    private Color foreground = HPGui.getColor("#333344");
    private boolean caretClicked = false;

    public SelectField(T ...values) {
        this.values = values;
        common();
    }

    public SelectField(int width, int height, T[] values) {
        super(width, height);
        this.values = values;
        hasSize = true;
    }

    public SelectField() {
        common();
    }

    public void onItemClick(Consumer<ItemEvent> item){
        comboBox.addItemListener(item::accept);
    }

    public void addItem(T item){
        comboBox.addItem(item);
    }

    @SafeVarargs
    public final void addItems(T... items){
        for (T item : items) {
            comboBox.addItem(item);
        }
    }

    public void setSelectedItem(T item){
        comboBox.setSelectedItem(item);
    }

    private void common(){

        rotatedImageView = new Rotate(new ImageView("views/forms/expand.png"), 10);
        setRightComp(rotatedImageView);
        rotatedImageView.onClick( e -> onClickCaret());

        getField().setEnabled(false);
        addMainField();
    }


    private void addMainField(){
        Card card = new Card();
        card.setLayout(null);

        comboBox = new JComboBox<>(values);
        comboBox.setRenderer(new Renderer());
        comboBox.setBorder(null);

        this.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                HPGui.log("select", getFieldWrapperHeight(), getWidth(), getFormWidth());
                if(!hasSize){
                    comboBox.setBounds(-2, -2, getWidth() - getPadding() - 8, getHeight() - getPadding() - 3);
                }else{
                    comboBox.setBounds(-2, -2, getFieldWrapperWidth(), getFieldWrapperHeight());
                }
            }
        });

        comboBox.addPopupMenuListener(new PopupMenuAdapter() {
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }
        });
        card.add(comboBox);

        getFieldWrapper().add(card);
    }

    private void onClickCaret() {
        caretClicked = true;
        if(rotatedImageView.getFromDegree() == -180){
            comboBox.hidePopup();
            rotatedImageView.animate(-180, 0);
        }else {

            comboBox.showPopup();
            rotatedImageView.animate(0, -180);
        }
    }

    public JComboBox<T> getSelectField() {
        return comboBox;
    }

    private class Renderer extends JLabel implements ListCellRenderer<Object>{


        public Renderer() {
            setFont(new Font(HPGui.FontText, Font.PLAIN, 20));
            setOpaque(true);
            HPGui.setAllSizes(this, super.getPreferredSize().width, valueHeight);
            HPGui.setLeftPadding(this, 2);
            setVerticalAlignment(JLabel.CENTER);

//            HPGui.setBottomPadding(this, 5);
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            setText(value.toString());
            JList.DropLocation dropLocation = list.getDropLocation();
            Color background;

            if(dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index){
                background = Color.LIGHT_GRAY;
                foreground = Color.black;
            }else if (isSelected) {
                background = Color.LIGHT_GRAY;
                // unselected, and not the DnD drop location
            } else {
                background = Color.WHITE;
            };

            setBackground(background);
            setForeground(foreground);

            return this;
        }
    }
}
