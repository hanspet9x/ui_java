package views;

import containers.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SelectView extends Card {

    /**
     * The selector array values;
     */
    private final String [] values;

    /**
     * the selector current value;
     */
    private String value;

    /**
     * The selector's default background color
     */
    private Color bgc0 = Color.WHITE;

    /**
     * The selector's active background color
     */
    private Color bgc1 = Color.decode("#93a7f2");

    /**
     * The selector's default foreground color
     */
    private Color fgc0 = Color.GRAY;

    /**
     * The selector's active foreground color
     */
    private Color fgc1 = Color.WHITE;

    /**
     * The selector's components repo
     */
    private Map<String, ViewHolder> holders = new HashMap<>();

    /**
     * It is a view that defines a list of option and allows selection of its options.
     * @param values String[] - An array of options to show.
     */
    public SelectView(String [] values) {
        this.values = values;
        this.value = null;
        build();
    }

    /**
     * It is a view that defines a list of option and allows selection of its options.
     * @param values String[] - An array of options to show.
     * @param value  String - A default option.
     */
    public SelectView(String [] values, String value) {
        this.values = values;
        this.value = value;
        build();
    }

    /**
     * It is a view that defines a list of option and allows selection of its options.
     * @param values String [] - An array of options to show.
     * @param value String - A default option.
     * @param bgc0 Color - Default background color.
     * @param bgc1 Color - Active background color.
     * @param fgc0 Color - Default foreground color.
     * @param fgc1 Color - Active foreground color.
     */
    public SelectView(String[] values, String value, Color bgc0, Color bgc1, Color fgc0, Color fgc1) {
        this.values = values;
        this.value = value;
        this.bgc0 = bgc0;
        this.bgc1 = bgc1;
        this.fgc0 = fgc0;
        this.fgc1 = fgc1;
        build();
    }

    /**
     * assembles the comp
     */
    private void build(){

        for (String value: values) {

            add(getLabel(value));

        }
        setBorderRadius(4);
//        setBoxShadow(Color.DARK_GRAY);
        setPadding(0);

    }

    /**
     * create a card that holds a label.
     * @param value String
     * @return Card
     */
    private Card getLabel(String value){
        JLabel label = new JLabel(value);
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        Color [] colors = getColors(value.equals(Objects.toString(this.value, values[0])));
        label.setForeground(colors[1]);
        label.addMouseListener(new OnMouseMove());

        Card holder = new Card();
        holder.add(label);
        holder.setPadding(1);
        holder.setBorder(null);
        holder.setBorderRadius(3);
        holder.setBackground(colors[0]);


        holders.put(value, new ViewHolder(holder, label));
        return holder;
    }

    /**
     * It returns the either active or default colors based on argument supplied.
     * @param isActive boolean
     * @return Color[]
     */
    private Color[] getColors(boolean isActive){
        return isActive ? new Color[]{bgc1, fgc1} : new Color[]{bgc0, fgc0};
    }

    /**
     * It sets active a selected option using a value and makes default others.
     * @param value String
     */
    public void setValue(String value){
        this.value = value;
        holders.forEach( (label, holder) -> {
            Color [] colors = getColors(label.equals(value));
            holder.getHolder().setBackground(colors[0]);
            holder.getLabel().setForeground(colors[1]);
            holder.getHolder().repaint();
        });
    }

    public void trigger(String value){
        setValue(value);
        onOptionSelected.option(value);
    }

    /**
     * Itb returns the active value;
     * @return
     */
    public String getValue(){
        return value;
    }


    /**
     * Superclass for the options event.
     */
    class OnMouseMove extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel label = (JLabel)e.getSource();
            setValue(label.getText());
            if(onOptionSelected != null)onOptionSelected.option(label.getText());
            //onOptionSelected = Objects.requireNonNull(onOptionSelected, "OnOptionSelected is not instantiated.");

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel)e.getSource();
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * Triggered to obtain value outside this structure.
     */
    public interface OnOptionSelected{
        void option(String value);
    }

    public OnOptionSelected onOptionSelected = null;

    /**
     * It instantiates the OnOptionSelected Callback;
     * @param onOptionSelected OnOptionSelected
     */
    public void setOnOptionSelected(OnOptionSelected onOptionSelected) {
        this.onOptionSelected = onOptionSelected;
    }

    /**
     * It stores all comps.
     */
    class ViewHolder{
        private final Card holder;
        private final JLabel label;

        public ViewHolder(Card holder, JLabel label) {
            this.holder = holder;
            this.label = label;
        }

        public Card getHolder() {
            return holder;
        }

        public JLabel getLabel() {
            return label;
        }
    }
}
