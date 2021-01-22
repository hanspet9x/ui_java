package views;

import containers.Card;

import java.awt.*;

public class UIProgress extends Card {

    private final Card progressView;
    private final int height;

    public UIProgress(int width, int height) {
        super(new FlowLayout(FlowLayout.LEADING, 0, 0));
        progressView = new Card(new FlowLayout(FlowLayout.LEADING, 0, 0));

        setPadding(0);
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        this.height = height;
        add(progressView);

    }

    public Card getProgressView() {
        return progressView;
    }

    public void setValue(int value) {
        progressView.setMinimumSize(new Dimension(value, height));
        progressView.setPreferredSize(new Dimension(value, height));
        progressView.setMaximumSize(new Dimension(value, height));
    }
}
