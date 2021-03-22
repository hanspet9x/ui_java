package views;

import containers.Card;
import services.HPGui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("rawtypes")
public class IconTag extends Card {

    private final ImageView imageView;
    private final Card titleCard;
    private final String tag;
    private Color tagBgColor = Color.LIGHT_GRAY, tagColor = Color.GRAY;
    private String description = "";
    private JLabel label;

    public IconTag(ImageView imageView, String tag) {
        setLayout(null);
        this.imageView = imageView;
        this.tag = tag;
        this.titleCard = new Card();
    }

    public IconTag(ImageView imageView, int tag) {
        setLayout(null);
        this.imageView = imageView;
        this.tag = String.valueOf(tag);
        this.titleCard = new Card();
    }

    public IconTag setTagBgColor(Color tagBgColor) {
        this.tagBgColor = tagBgColor;
        return this;
    }

    public IconTag setTagColor(Color tagColor) {
        this.tagColor = tagColor;
        return this;
    }

    public IconTag setDescription(String description) {
        this.description = description;
        return this;
    }


    public void updateTag(String tag){
        label.setText(tag);
    }


    public void updateTag(int tag){
        updateTag(String.valueOf(tag));
    }

    public void updateImageView(String imagePath){
        imageView.swap(imagePath);
    }

    public IconTag build(){
        this.titleCard.setBackground(tagBgColor);
        this.titleCard.setBorderRadius(8);
        this.titleCard.setPadding(1);
        label = new JLabel(tag);
        label.setForeground(tagColor);
        this.titleCard.add(label);

        setBackground(HPGui.getColTranslucent());
        setPadding(0);
        setBorder(BorderFactory.createLineBorder(Color.RED));

        int w = Math.max(imageView.getPreferredSize().width, imageView.getWidth());
        int h = Math.max(imageView.getPreferredSize().height, imageView.getHeight());

        HPGui.setAllSizes(this, w, h);


        HPGui.log();
        this.titleCard.setBounds(w - titleCard.getPreferredSize().width, 1, titleCard.getPreferredSize().width, titleCard.getPreferredSize().height);
        imageView.setBounds(0, 0, w, h);

        this.setToolTipText(description);
        add(this.titleCard);
        add(imageView);
        return this;
    }
}
