/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import containers.Card;

import javax.swing.*;
import javax.swing.SpringLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Peter A. Akinlolu
 */

@SuppressWarnings("rawtypes")
public  class HPGui {

    private static final int FRAME_EXTRA_WIDTH = 16;
    private static final int FRAME_ICON_SIZE = 60;
    private static final int TASK_BAR_HEIGHT = 40;
    private static SystemTray systemTray;
    private static TrayIcon tray;
    public static String FontStandard = "Mongolian Baiti";
    public static String FontHandwriting = "Lucida Handwriting";
    public static String FontHead = "Patua One";

    public static String FontHead2 = "Bree Serif";

    public static String FontText = "Cormorant Garamond";

    public static String FontComplex = "Harrington";

    public static final List<String> imgExtensions = List.of(".jpg", ".jpeg", ".png", ".gif", ".tiff");

    public static final List<String> audioExtensions = List.of(".ogg", ".mp3", ".m4a", ".wav", ".mpeg-4", ".midi", ".wma", ".aac");

    public static final List<String> videoExtensions = List.of(".mp4", ".avi", ".mov", ".mkv", ".flv", ".swf");

    public static final List<String> documentExtensions = List.of(".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx");

    public HPGui() {
       if(systemTray.isSupported()){
           systemTray = SystemTray.getSystemTray();
       }
    }

    public static void init(){
        registerFonts();
    }

    public static void registerFonts(){
        List<String> fonts = Arrays.asList("/fonts/BreeSerif-Regular.ttf",
                "/fonts/CormorantGaramond-Regular.ttf", "/fonts/PatuaOne-Regular.ttf");

        fonts.forEach( fontName -> {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, new File(HPGui.class.getResource(fontName).toURI()));
                if(!GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font)){
                    throw new Exception("Font "+fontName+" registration failed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

//     DUMMYTEXT

    public static String lorem(){
        return "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    }

/*    public static Dimension getScreenSize(){
        Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(toolkit.width, toolkit.height - 40);

    }*/
    public static void setPrefSize(Component comp, Dimension dim){
        comp.setPreferredSize(dim);
    }

    public static void setMinSize(Component comp, Dimension dim){
        comp.setMinimumSize(dim);
    }

    public static void setMaxSize(Component comp, Dimension dim){
        comp.setMaximumSize(dim);
    }

    public static Component setAllSizes(Component comp, Dimension dim){
        comp.setMinimumSize(dim);
        comp.setMaximumSize(dim);
        comp.setPreferredSize(dim);
        comp.setSize(dim);
        return comp;
    }

    public static Component setAllSizes(Component comp, int width, int height){
        Dimension dim = new Dimension(width, height);
        return setAllSizes(comp, dim);
    }

    public static Component setAllSizes(Component comp, Dimension dim, int difference){
        return setAllSizes(comp, dim.width - difference, dim.height - difference);
    }

    public static Component setAllWidths(Component comp, int width){
        Dimension dim = new Dimension(width, comp.getHeight());
        comp.setMinimumSize(dim);
        comp.setMaximumSize(dim);
        comp.setPreferredSize(dim);
        comp.setSize(dim);
        return comp;
    }

    public static Component setAllHeights(Component comp, int height){
        Dimension dim = new Dimension(comp.getWidth(), height);
        comp.setMinimumSize(dim);
        comp.setMaximumSize(dim);
        comp.setPreferredSize(dim);
        comp.setSize(dim);
        return comp;
    }

    public static Dimension getDimensionReduced(Dimension dim, int difference){
        return new Dimension(dim.width - difference, dim.height - difference);
    }

    public static boolean compareDimensions(Dimension dim1, Dimension dim2){
        return dim1.width == dim2.width && dim1.height == dim2.height;
    }

    public static Dimension getScreenSize(){
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        return new Dimension(displayMode.getWidth(), displayMode.getHeight() - TASK_BAR_HEIGHT);
    }

    public static int getA4Height(int width){
        return (int)(width * 1.4142);
    }

    public static int getA4Width(int height){
        return (int)(height / 1.4142);
    }

    /*
    SWING & Component
    */

    public static JTextArea getTextArea(String text, int width, int height){
        JTextArea textArea = new JTextArea(text);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setBorder(BorderFactory.createEmptyBorder());
            textArea.setWrapStyleWord(true);
            setAllSizes(textArea, width, height);
            textArea.setOpaque(false);
            textArea.setBackground(getColTranslucent());
            return textArea;
    }


    public static JTextArea getTextArea(String text, int width){
            return getTextArea(text, width, 20);
    }

    public static Card getTextRule(String text, Color color){
        int padding = 10;
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int width = g.getFontMetrics().stringWidth(text)+padding;

        JLabel label = new JLabel(text);
        setAllSizes(label, width, 70);

        JLabel rule = new JLabel();
        rule.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, color));
        setTopPadding(rule, 4);

        Card holder = new Card(new SpringLayout());
        holder.setPadding(0);
        holder.setOpacity(0.0f);
        holder.add(label);
        holder.add(rule);

        HPGui.Springer.makeCompactGrid(holder, 1, 2, 0, 0, 1, 0);

        return holder;
    }
    /*
    CONTAINERS & COLUMNS
    */

    public static Card twoColumns(Component one, Component two, int x, int y){
        Card card = new Card(new SpringLayout());
        card.setOpacity(.0f);
        card.add(one);
        card.add(two);
        Springer.makeCompactGrid(card, 1, 2, 0, 0, x, y);
        return card;
    }

    public static Card twoColumns(Component one, Component two, int x){

        return twoColumns(one, two, x, 0);
    }

    public static Card twoColumns(Component one, Component two){
        return twoColumns(one, two, 0, 0);
    }

    public static Card threeColumns(Component one, Component two, Component three,int x, int y){
        Card card = new Card(new SpringLayout());
        card.setOpacity(.0f);
        card.add(one);
        card.add(two);
        card.add(three);

        Springer.makeCompactGrid(card, 1, 3, 0, 0, x, y);
        return card;
    }

    public static Card threeColumns(Component one, Component two, Component three,int x){
        return threeColumns(one, two, three, x, 0);
    }

    public static Card threeColumns(Component one, Component two, Component three){
        return threeColumns(one, two, three, 0, 0);
    }

    public static Card twoRows(Component one, Component two, int x, int y){
        Card card = new Card(new SpringLayout());
        card.setOpacity(.0f);
        card.add(one);
        card.add(two);
        Springer.makeCompactGrid(card, 2, 1, 0, 0, x, y);
        return card;
    }

    public static Card twoRows(Component one, Component two, int x){

        return twoRows(one, two, x, 0);
    }

    public static Card twoRows(Component one, Component two){
        return twoRows(one, two, 0, 0);
    }

    public static Component getBox(int w, int h){
        return Box.createRigidArea(new Dimension(w, h));
    }

    public static JScrollPane getScrollPane(JComponent view, int width, int height){
        JScrollPane pane = new JScrollPane(view);
        pane.setOpaque(false);
        setAllSizes(pane, width, height);
        pane.setBackground(getColTranslucent());
        pane.setViewportBorder(BorderFactory.createEmptyBorder());
        pane.setBorder(BorderFactory.createEmptyBorder());

        JViewport vport = pane.getViewport();
        vport.setBackground(getColTranslucent());
        vport.setOpaque(false);
        return pane;
    }

    public static JScrollPane getScrollPane(Card card, int width, int height){
        JScrollPane pane = new JScrollPane(card);
        pane.setOpaque(false);
        setAllSizes(pane, width, height);
        pane.setBackground(getColTranslucent());
        pane.setViewportBorder(BorderFactory.createEmptyBorder());
        pane.setBorder(BorderFactory.createEmptyBorder());

        JViewport vport = pane.getViewport();
        vport.setBackground(getColTranslucent());
        vport.setOpaque(false);
        return pane;
    }

    public static JScrollPane getScrollPane(Card card){
        JScrollPane pane = new JScrollPane(card);
        pane.setOpaque(false);
        pane.setBackground(getColTranslucent());
        pane.setViewportBorder(BorderFactory.createEmptyBorder());
        pane.setBorder(BorderFactory.createEmptyBorder());

        JViewport vport = pane.getViewport();
        vport.setBackground(getColTranslucent());
        vport.setOpaque(false);
        return pane;
    }

    public static JScrollPane getScrollPane(){
        JScrollPane pane = new JScrollPane();
        pane.setOpaque(false);
        pane.setBackground(getColTranslucent());
        pane.setViewportBorder(BorderFactory.createEmptyBorder());
        pane.setBorder(BorderFactory.createEmptyBorder());

        JViewport vport = pane.getViewport();
        vport.setBackground(getColTranslucent());
        vport.setOpaque(false);
        return pane;
    }

    public static JScrollPane getScrollPane(JComponent view){
        JScrollPane pane = new JScrollPane(view);
        pane.setOpaque(false);
        pane.setBackground(getColTranslucent());
        pane.setViewportBorder(BorderFactory.createEmptyBorder());
        pane.setBorder(BorderFactory.createEmptyBorder());

        JViewport vport = pane.getViewport();
        vport.setBackground(getColTranslucent());
        vport.setOpaque(false);
        return pane;
    }


    public static Card getCard(){
        Card card = new Card();
        card.setOpacity(.0f);
        card.setPadding(0);
        return card;
    }








    /*
    TIME and DATE
    */

    public static String getFormalDay(int day){
        String sDay = String.valueOf(day);
        String c = String.valueOf(sDay.charAt(sDay.length()-1));

        if(sDay.length() > 1 && c.equals("2")){
            return sDay+"nd";
        }

        switch(day){
            case 1:
               sDay = 1+"st";
               break;
            case 2:
               sDay = 2+"nd";
               break;
            case 3:
               sDay = 3+"rd";
               break;
            default:
                sDay = day+"th";
        }

        return sDay;
    }

    public static String getFormalDay(String day){
        String sDay;
        String c = String.valueOf(day.charAt(day.length()-1));

        if(day.length() > 1 && c.equals("2")){
            return day+"nd";
        }

        switch(day){
            case "1":
               sDay = "1st";
               break;
            case "2":
               sDay = "2nd";
               break;
            case "3":
               sDay = "3rd";
               break;
            default:
                sDay = day+"th";
        }

        return sDay;
    }

    public static String getDateTime() {
        return Date.from(Instant.now()).toString();
    }

    public static String getDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return prefixZero(day)+"-"+prefixZero(month)+"-"+year;
    }

    public static long getDateDiff(String date1, String date2) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final LocalDate firstDate = LocalDate.parse(date1, formatter);
        final LocalDate secondDate = LocalDate.parse(date2, formatter);
        return ChronoUnit.DAYS.between(firstDate, secondDate);
    }

    public static int getMonth(String month) {
        Date date = null;
        try {
            date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH)+1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;        }

    }

    public static String prefixZero(String num){
        if(num.length() < 2){
            return "0"+num;
        }
        return num;
    }

    public static String prefixZero(int num){
        String sNum = String.valueOf(num);
        if(sNum.length() < 2){
            return "0"+num;
        }
        return sNum;
    }

//    ALIGNMENT
    public static Card setAlignCenter(JComponent comp){

        comp.setAlignmentX(Component.CENTER_ALIGNMENT);

        Card center = new Card();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));

        center.add(Box.createVerticalGlue());
        center.add(comp);
        center.add(Box.createVerticalGlue());

        return center;
    }
    public static Card setAlignCenterH(JComponent comp){

        Card panel = new Card(new FlowLayout(FlowLayout.CENTER, 0,0));
        panel.add(comp);
        panel.setOpacity(.0f);
        return panel;
    }


    public static void setAlignCenterV(Component comp){
        Container cont = comp.getParent();
        int height = cont.getHeight();

        int cHeight = comp.getHeight();

        int y = height - cHeight;

        y = (int)((double)y/(double)2);
        comp.setLocation(comp.getLocation().x, y);
        comp.revalidate();
    }
    public static Card setAlignLeft(JComponent comp){
        Card panel = new Card();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(comp);
        panel.setPadding(0);
        panel.setOpacity(.0f);
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
    public static Container setAlignRight(JComponent comp){
        Card panel = new Card();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(comp);
        panel.setPadding(0);
        panel.setOpacity(.0f);
        panel.setBackground(getColTranslucent());
        comp.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return panel;
    }

    public static void setPadding(JComponent comp, int value){

        comp.setBorder(BorderFactory.createEmptyBorder(value, value, value, value));

    }

    public static void setPadding(JComponent comp, Border inside, int value){
        Border outside = BorderFactory.createEmptyBorder(value, value, value, value);
        Border b = BorderFactory.createCompoundBorder(outside, inside);
        comp.setBorder(b);
    }
    public static void setPadding(JComponent comp, int x, int y){
        comp.setBorder(BorderFactory.createEmptyBorder(y, x, y, x));
    }
    public static void setPadding(JComponent comp, int top, int left, int bottom, int right){
        comp.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }
    public static void setLeftPadding(JComponent comp, int n){
        comp.setBorder(BorderFactory.createEmptyBorder(0, n, 0, 0));
    }
    public static void setRightPadding(JComponent comp, int n){
        comp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, n));
    }
    public static void setTopPadding(JComponent comp, int n){
        comp.setBorder(BorderFactory.createEmptyBorder(n, 0, 0, 0));
    }
    public static void setBottomPadding(JComponent comp, int n){
        comp.setBorder(BorderFactory.createEmptyBorder(0, 0, n, 0));
    }


    public static Card getMarginCard(JComponent comp, int size){
        Card c = new Card();
        c.add(comp);
        c.setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return c;
    }
/*    public static void getBorder(JComponent comp, Color color, int thickness, boolean rounded){
        Card c = new Card();
        c.setBorder(BorderFactory.createLineBorder(color, thickness, rounded));
    }
    public static void getBorder(JComponent comp, String hexColor, int thickness, boolean rounded){
        comp.setBorder(BorderFactory.createLineBorder(Color.decode(hexColor), thickness, rounded));
    }*/

    public static void setBorder(JComponent comp, Color color, int thickness, boolean rounded){
        comp.setBorder(BorderFactory.createLineBorder(color, thickness, rounded));
    }
    public static void setBorder(JComponent comp, String hexColor, int thickness, boolean rounded){
        comp.setBorder(BorderFactory.createLineBorder(Color.decode(hexColor), thickness, rounded));
    }


    /*Font*/

    public static Font getFontStandard(int fontSize){
        return new Font(HPGui.FontStandard, Font.PLAIN, fontSize);
    }

    public static Font getFontStandardBold(int fontSize){
        return new Font(HPGui.FontStandard, Font.BOLD, fontSize);
    }

    public static Font getFontHead(int fontSize){
        return new Font(HPGui.FontHead, Font.PLAIN, fontSize);
    }

    public static Font getFontHead2(int fontSize){
        return new Font(HPGui.FontHead2, Font.PLAIN, fontSize);
    }

    public static Font getFontText(int fontSize){
        return new Font(HPGui.FontText, Font.PLAIN, fontSize);
    }

    public static Font getFontWriting(int fontSize){
        return new Font(HPGui.FontHandwriting, Font.PLAIN, fontSize);
    }

    /*
    COLORS
     */
    public static Color getColor(String hex){
        return Color.decode(hex);
    }

    public static Color getColor(String hex, int alpha){
        Color color = Color.decode(hex);
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();

        return new Color(red, green, blue, alpha);
    }

    public static Color getColor(Color color, int alpha){
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();

        return new Color(red, green, blue, alpha);
    }

    public static Color getColor(String hex, float alpha){
        Color color = Color.decode(hex);
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();

        int alp = Math.round(alpha * 255);

        return new Color(red, green, blue, alp);
    }

    public static Color getColor(Color color, float alpha){
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();

        int alp = Math.round(alpha * 255);

        return new Color(red, green, blue, alp);
    }

    public static Color getColorByPercent(Color color, int percent){
        int red = getPercent(percent, color.getRed());
        int blue = getPercent(percent, color.getBlue());
        int green = getPercent(percent, color.getGreen());
        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }

    public static Color getDarkerColor(Color color, int darkness){
        int red = validateColor(color.getRed() - darkness);
        int blue = validateColor(color.getBlue() - darkness);
        int green = validateColor(color.getGreen() - darkness);
        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }

    public static Color getBrighterColor(Color color, int lightness){
        int red = validateColor(color.getRed() + lightness);
        int blue = validateColor(color.getBlue() + lightness);
        int green = validateColor(color.getGreen() + lightness);
        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }

    private static int validateColor(int colorResult){
        if(colorResult > 255){
            return 255;
        }else return Math.max(colorResult, 0);
    }

    public static boolean hasAlpha(Color color){
        return color.getAlpha() > 0;
    }

    public static Color getColor(int r, int g, int b, float a){
        return new Color(r, g, b, a);
    }

    public static Color getColor(int r, int g, int b){
        return new Color(r, g, b);
    }

    public static Color getColTranslucent(){
        return new Color(.0f, .0f, .0f, 0.0f);
    }

    public static boolean isSameColor(Color color1, Color color2){
        if(color1.getRed() != color2.getRed()){
            return false;
        }else if(color1.getBlue() != color2.getBlue()){
            return false;
        }else if(color1.getGreen() != color2.getGreen()){
            return false;
        }else return color1.getAlpha() == color2.getAlpha();
    }

    public static void hasAlpha2(Color color){
        HPGui.log(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }

    public static int getIntFromColor(Color color){

        return (color.getAlpha() << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
    }

    public static int getIntFromColor(Color color, int alpha){

        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB();
    }

    public static Color getColorFromInt(int color){

        return new Color(
                (color >> 16) & 0xff,
                (color >> 8) & 0xff,
                color & 0xff,
                (color >> 24) & 0xff);

    }

    public static Color getRandomColor(){
        return new Color(getRandomNo(255), getRandomNo(255), getRandomNo(255));
    }




    public static void  log(Class className, Object ...o){
        System.out.print(className.getName());
        for (Object ob:
             o) {
            System.out.print(", ");
            System.out.print(ob);

        }
        System.out.println();
    }

    public static void log(Object ...o){

        for (Object ob:
                o) {

            System.out.print(ob);
            System.out.print(", ");

        }
        System.out.println();
    }




    /*
    BACKGROUND COLOUR
    */

    public static LinearGradientPaint getLinearGradientPaint(int width,
        float [] points, Color [] colors){

        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(width, 0);
     return new LinearGradientPaint(start, end, points, colors);

    }

    public static float [] floats(float ...floats){
        return floats;
    }

    public static Color [] colors(Color ...colors){
        return colors;
    }

    /*
    IMAGES and ICONS
     */
    public static Image getResizeImage(Image image, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, width, height, null);
            graphics2D.dispose();
            return bufferedImage;
    }

    public static Image getResizeImage(String path, String desc, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
            ImageIcon imageIcon = (ImageIcon)getIcon(path, desc);
            graphics2D.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
            graphics2D.dispose();
            return bufferedImage;
    }

    public static Image getResizeImage(String path, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
            ImageIcon imageIcon = (ImageIcon)getIcon(path, null);
            graphics2D.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
            graphics2D.dispose();
            return bufferedImage;
    }

    public static ImageIcon getImageIconFromImage(Image image){
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return new ImageIcon(bufferedImage);
    }

    public static Icon getIcon(String path, String desc){
        URL url = new HPGui().getClass().getResource(path);
        log(HPGui.class, new HPGui().getClass());
        return new ImageIcon(url, desc);
    }

    public static Image getImage(String path){
        return Toolkit.getDefaultToolkit().getImage(path);
    }

    public static Image getImage2(String path){
        return new ImageIcon(new HPGui().getClass().getResource(path)).getImage();
    }

    public static ImageIcon getImageIcon(String path){
        try {
            return new ImageIcon(new HPGui().getClass().getResource(path));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static ImageIcon getImageIconFromSystem(String path){
        try {
            return new ImageIcon(path);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static byte [] getBytesImage(String path){
        try {
            BufferedImage image = ImageIO.read(new HPGui().getClass().getResource(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, getExt(path), baos);
            baos.flush();
            return baos.toByteArray();

        } catch (IOException ex) {
            Logger.getLogger(HPGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ImageIcon getImageIcon(String path, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(getImageIcon(path).getImage(), 0, 0, width, height,null);

            graphics2D.dispose();
            return new ImageIcon(bufferedImage);
    }

    public static ImageIcon getImageIconFromSystem(String path, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            BufferedImage img = ImageIO.read(new File(path));
            graphics2D.drawImage(img, 0, 0, width, height,null);

            graphics2D.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ImageIcon(bufferedImage);
    }

    public static String addSlashToImagePath(String path){
        if(!Character.toString(path.charAt(0)).equals("/") && !Character.toString(path.charAt(0)).equals("\\")){
            return File.separator+path;
        }
        return path;
    }

    public static String removeSlashFromImage(String path){
        if(Character.toString(path.charAt(0)).equals("/") || Character.toString(path.charAt(0)).equals("\\")){
            return path.substring(1);
        }
        return path;
    }

    public static void setFrameIcon(JFrame frame, String path){

        frame.setIconImage(getResizeImage(path, FRAME_ICON_SIZE, FRAME_ICON_SIZE));
    }


    /*
    String
    */

    public static String textLimit(String data, int limit){

        int len = data.length();
        if(limit >= len){
            return data;
        }

        return data.substring(0, limit)+"...";
    }

    /*
    Calculations
    */

    public static Rectangle getCenterBound(Container container, Component component) throws Exception{
        Dimension size = container.getPreferredSize();
        Dimension cSize = component.getPreferredSize();

        if(size.height > cSize.height && size.width > cSize.width){
                int x = (size.width - cSize.width)/2;
                int y = (size.height - cSize.height)/2;
                return new Rectangle(x, y, cSize.width, cSize.height);
        }else{
            throw new Exception("Component cannot be greater than container.");
        }
    }

    public static Dimension getDimenstions(Component [] components){
        int w = 0, h = 0;
        for (Component component : components) {
            w +=component.getWidth();
            h +=component.getHeight();
        }

        return new Dimension(w, h);
    }

    public static int getRandomNo(int max){
        Random ran = new Random();
        return ran.nextInt(max);
    }

    public static String getRandomAlphabets(int max){
        String [] alp = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f"
        , "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < max; i++) {
            res.append(alp[getRandomNo(51)]);
        }
        return res.toString();
    }


    public static int getPercent(int value, int total){
       return  (int)(((double)value/100) * (double)total);
    }
    /*
    Dialogue
    */

    public static void alert(Container parent, String message){
       new Dialogue(parent, message, true);
    }

    static class Dialogue extends JDialog implements ActionListener{

        private String title = "Alert";
        private JDialog dialog = this;

        public Dialogue(Container frame, String message) {

           setSize(frame.getWidth()-16, frame.getHeight()-8);
           setLocation(frame.getX()+8, frame.getY());
           setUndecorated(true);
           setBackground(getColor(0, 0, 0, 0.3f));
        }

        public Dialogue(Container frame, String message, boolean decoratedParent) {

           setSize(frame.getWidth()-16, frame.getHeight()-39);
           setLocation(frame.getX()+8, frame.getY()+31);
           setLayout(new FlowLayout());
           setUndecorated(true);
           setBackground(getColor(0, 0, 0, 0.3f));
           JPanel panel = simpleLayout(title, message);
            add(panel);

            setVisible(true);
        }

        public Dialogue(Container frame, Component component) {

           setSize(frame.getWidth()-16, frame.getHeight()-8);
           setLocation(frame.getX()+8, frame.getY());
           setUndecorated(true);
           setBackground(getColor(0, 0, 0, 0.3f));
        }

        public Dialogue(Container frame, Component component, boolean decoratedParent) {

           setSize(frame.getWidth()-16, frame.getHeight()-39);
           setLocation(frame.getX()+8, frame.getY()+31);
           setUndecorated(true);
           setBackground(getColor(0, 0, 0, 0.3f));
        }

       private JPanel simpleLayout(String title, String message){
           JPanel panel = new JPanel(){
               @Override
               protected void paintComponent(Graphics g) {
                   boxShadow(g, this, Color.WHITE, 0.3f, 2, 10);
               }


               @Override
               public Border getBorder() {
                   return new EmptyBorder(10, 10, 10, 10);
               }

               @Override
               public Dimension getPreferredSize() {
                   return new Dimension(200, 200);
               }

           };

           JLabel titleLabel = new JLabel(title);
           titleLabel.setFont(new Font(FontStandard, Font.BOLD, 14));

           JLabel mesLabel = new JLabel(message);
           mesLabel.setFont(new Font(FontHandwriting, Font.BOLD, 12));

           JButton close = new JButton("close");
            close.setOpaque(false);
            close.setActionCommand("close");
            close.addActionListener(this);


           panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
           panel.add(titleLabel);
           panel.add(Box.createRigidArea(new Dimension(0, 10)));
           panel.add(mesLabel);
           panel.add(Box.createRigidArea(new Dimension(0, 20)));
           panel.add(Box.createRigidArea(new Dimension(10, 0)));
           panel.add(Box.createHorizontalGlue());
           panel.add(close);

           return panel;
       }

        @Override
        public void actionPerformed(ActionEvent e) {
            if("close".equals(e.getActionCommand())){
                WindowEvent evt = new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING);
                dialog.dispatchEvent(evt);
            }
        }


    }

    public static void boxShadow(Graphics g, JComponent box, Color surfaceColor, float opacity, int size){
        Graphics2D g2d = (Graphics2D)g;
        int offset = size;
        BufferedImage image = new BufferedImage(box.getWidth(), box.getHeight(), BufferedImage.BITMASK);
        Graphics2D gd = image.createGraphics();
        RoundRectangle2D rect = new RoundRectangle2D.Float(1, offset, box.getWidth()-2,
                box.getHeight(), 0, 0);
        gd.setColor(new Color(0,0,0, opacity));
        gd.fill(rect);
//        gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 0.1f));
        gd.setColor(surfaceColor);
        gd.fillRoundRect(0, 0, box.getWidth(), box.getHeight()-offset, 0, 0);

        gd.dispose();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    public static void boxShadow(Graphics g, JComponent box, Color surfaceColor, float opacity, int size, int radius){
        Graphics2D g2d = (Graphics2D)g;
        int offset = size;
        BufferedImage image = new BufferedImage(box.getWidth(), box.getHeight(), BufferedImage.BITMASK);
        Graphics2D gd = image.createGraphics();
        RoundRectangle2D rect = new RoundRectangle2D.Float(0, offset, box.getWidth(),
                box.getHeight(), radius, radius);
        gd.setColor(getColor(0, 0, 0, opacity));
        gd.fill(rect);

        gd.setColor(surfaceColor);
        gd.fillRoundRect(0, 0, box.getWidth(), box.getHeight()-offset, radius, radius);

        gd.dispose();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    public static Cursor getHandCursor(){
        return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    }
    /*
    FILE
    */

    public static String getExt(String path){
        String ext = path.substring(path.lastIndexOf("."));
        return ext.toLowerCase();
    }

    public static boolean isFile(String path){
        if(path.lastIndexOf(".") == -1){
            return false;
        }else{
            return getExt(path).length() >= 3;
        }
    }


    /*
    JFrame
    */
       public static void setLookAndFeel(String name){
        for (UIManager.LookAndFeelInfo installedLookAndFeel : UIManager.getInstalledLookAndFeels()) {
                if(name.equals(installedLookAndFeel.getName()))
                  try {
                      UIManager.setLookAndFeel(installedLookAndFeel.getClassName());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                }
            }
       public static void setTempFrame(Component component){
       JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(component);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       }


     public static void setTempFrame(Component component, int w, int h){
       JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEADING));
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(component);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       }
    /*
    TRAYICON
    */

    public static TrayIcon addTray(String path){
        tray = new TrayIcon(getImage(path));
        tray.setImageAutoSize(true);
        try {
            systemTray.add(tray);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return tray;
    }

    public static TrayIcon addTray(String path, ActionListener listener){
        tray = new TrayIcon(getImage(path));
        tray.setImageAutoSize(true);
        tray.addActionListener(listener);
        try {
            systemTray.add(tray);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return tray;
    }
    /*
    SPring Layout
    */
    public static class Springer{


    public static void printSizes(Component c) {
//        System.out.println("minimumSize = " + c.getMinimumSize());
//        System.out.println("preferredSize = " + c.getPreferredSize());
//        System.out.println("maximumSize = " + c.getMaximumSize());
    }

    /**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component is as big as the maximum
     * preferred width and height of the components.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad x padding between cells
     * @param yPad y padding between cells
     */
    public static void makeGrid(Container parent,
                                int rows, int cols,
                                int initialX, int initialY,
                                int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeGrid must use SpringLayout.");
            return;
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        //Calculate Springs that are the max of the width/height so that all
        //cells have the same size.
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).
                                    getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).
                                    getHeight();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(i));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        //Apply the new width/height Spring. This forces all the
        //components to have the same size.
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        //Then adjust the x/y constraints of all the cells so that they
        //are aligned in a grid.
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                                 parent.getComponent(i));
            if (i % cols == 0) { //start of new row
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else { //x position depends on previous component
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
                                     xPadSpring));
            }

            if (i / cols == 0) { //first row
                cons.setY(initialYSpring);
            } else { //y position depends on previous row
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH),
                                     yPadSpring));
            }
            lastCons = cons;
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH,
                            Spring.sum(
                                Spring.constant(yPad),
                                lastCons.getConstraint(SpringLayout.SOUTH)));
        pCons.setConstraint(SpringLayout.EAST,
                            Spring.sum(
                                Spring.constant(xPad),
                                lastCons.getConstraint(SpringLayout.EAST)));
    }

    /* Used by makeCompactGrid. */
    private static SpringLayout.Constraints getConstraintsForCell(
                                                int row, int col,
                                                Container parent,
                                                int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    /**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component in a column is as wide as the maximum
     * preferred width of the components in that column;
     * height is similarly determined for each row.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad x padding between cells
     * @param yPad y padding between cells
     */
    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width,
                                   getConstraintsForCell(r, c, parent, cols).
                                       getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height,
                                    getConstraintsForCell(r, c, parent, cols).
                                        getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
}


    public static class filter implements DirectoryStream.Filter<Path> {

        private final FilterType filterType;

        public filter(FilterType filterType) {
            this.filterType = filterType;
        }

        @Override
        public boolean accept(Path entry) throws IOException {

            if(!Files.isDirectory(entry)){
                switch (filterType){

                    case Images -> {

                        return imgExtensions.contains(HPGui.getExt(entry.toString()));
                    }
                    case Videos -> {
                        return videoExtensions.contains(HPGui.getExt(entry.toString()));
                    }
                    case Pdfs -> {
                        return HPGui.getExt(entry.toString()).equals(".pdf");
                    }
                    case Docs -> {
                        return documentExtensions.contains(HPGui.getExt(entry.toString()));
                    }

                }
            }
            return false;
        }
    }

    public static boolean isImage(String path){
        return imgExtensions.contains(HPGui.getExt(path));
    }

    public static boolean isAudio(String path){
        return audioExtensions.contains(HPGui.getExt(path));
    }

    public static boolean isVideo(String path){
        return videoExtensions.contains(HPGui.getExt(path));
    }

    public static boolean isPdf(String path){
        return HPGui.getExt(path).equals(".pdf");
    }

    public enum FilterType{
        Images, Videos, Pdfs, Docs
    }
    
}

