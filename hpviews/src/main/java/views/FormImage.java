/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Peter A. Akinlolu
 */

public class FormImage extends Card implements ActionListener{

    private static Container conta;
    
    private int w  = 150;
    private int pW  = 60;
    private int dW = 500;
    private int dH = 500;
    private final Color fieldBgColor = new Color(244, 246, 248);
    private final Color fieldBorColor = new Color(234, 236, 238);
    private final Color chooserBgColor = new Color(104, 106, 108);
    private final int zoomSize = 5;
    private final int offset = 2;
    private final String imagePath = "/icons/FormImage_avatar.png";
    private final String pickerPath = "/icons/FormImage_picker.png";
    private final String [] formats = {".jpg", ".png", ".jpeg", ".bmp"};
    private String curDirectory = String.format("%s\\Pictures\\", System.getenv("USERPROFILE"));
    private int cropSize;
    private JLabel cropper;
    private BufferedImage uploadedImage;
    private JLabel imagerLabel;
    private JDialog dialog;
    private byte[] photoByte = null;
    private String photoString = null;
    private String ext = "jpg";


    public FormImage() {
        super(null);
        chooser(pickerPath);
        imager(imagePath);
        setPreferredSize(new Dimension(w, w));
        setMaximumSize(new Dimension(w, w));
        setMinimumSize(new Dimension(w, w));
        setOpacity(0.0f);
        
    }

    public FormImage(int size) {
        super(null);
        this.w = size;
        this.pW = (size * 35)/100;
        chooser(pickerPath);
        imager(imagePath);
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setOpacity(0.0f);
    }
  
    private void chooser(String imagePath){
        JLabel label = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D gd = (Graphics2D)g;
                BufferedImage image = new BufferedImage(pW, pW, BufferedImage.BITMASK);
                Graphics2D gImage = image.createGraphics();                
                gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int position = getXY(image, getImageSize(imagePath));
                gImage.setColor(chooserBgColor);                
                gImage.fillArc(0, 0, pW, pW, 0, 360);
                gImage.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                gImage.drawImage(getImage(imagePath), position, position, null);
                
                gImage.dispose();
                
                gd.drawImage(image, 0, 0, null);
                gd.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(pW, pW);
            }
            
             
            
        };
        label.setBounds(w-pW, w-pW, pW, pW);
        label.addMouseListener(new MouseInputAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(false);
                chooser.setDialogTitle("Select Photo");             
                chooser.setFileFilter(filterFile(formats));  
                if(curDirectory != null){
                    chooser.setCurrentDirectory(new File(curDirectory));
                }
                int option = chooser.showOpenDialog(null);  
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    int dot = file.getName().lastIndexOf(".");
                    ext = file.getName().substring(dot+1);
                    String path = file.getAbsolutePath();
                    curDirectory = path.substring(0, path.lastIndexOf("\\"));
                    try {
                        BufferedImage image = ImageIO.read(file);
                        imageDialogue(image);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            
            
            
        });
        add(label);
        
    }
     
    /*
    displayed avatar
    */
    private void imager(String imagePath){
        
        imagerLabel = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                BufferedImage image = new BufferedImage(w, w, BufferedImage.BITMASK);
                Graphics2D gImage = image.createGraphics();
                gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int position = getXY(image, getImageSize(imagePath));
                gImage.setColor(fieldBgColor);                
                gImage.fillArc(0, 0, w-offset, w-offset, 0, 360);
                
                gImage.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));                
                gImage.drawImage(getImage(imagePath), position, position, null);
                
                gImage.setColor(fieldBorColor);
                gImage.drawArc(0, 0, w-3, w-3, 0, 360);
                gImage.dispose();
                Graphics2D gd = (Graphics2D)g;
                gd.drawImage(image, 0, 0, null);
                gd.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(w, w);
            }

            
            
            
                                   
        };
        imagerLabel.setBounds(0, 0, w, w);
        add(imagerLabel);
    } 
     
    private Image getImage(String path){
        return new ImageIcon(getClass().getResource(path)).getImage();

    }
    
    private int getImageSize(String path){
        ImageIcon ImageIcon = new ImageIcon(getClass().getResource(path));
        return ImageIcon.getIconWidth();
    }
    
    private int getXY(BufferedImage parent, int cSize){
        int pSize = parent.getWidth();
        
        if(pSize == cSize ){
            return 0;
        }else if(pSize > cSize){
            return (pSize-cSize)/2;
        }
        return 0;
    }
    
     
    
    private void updateImager(BufferedImage cropImage){
       
        imagerLabel = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                BufferedImage image = new BufferedImage(w, w, BufferedImage.BITMASK);
                Graphics2D gImage = image.createGraphics();
                gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gImage.setColor(getBackground());
                
                gImage.fillArc(0, 0, w-offset, w-offset, 0, 360);
                gImage.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));                
                gImage.drawImage(cropImage.getScaledInstance(w, w, Image.SCALE_SMOOTH),
                        0, 0, null);
                gImage.setColor(fieldBgColor);
                gImage.drawArc(0, 0, w-3, w-3, 0, 360);
                gImage.dispose();
                Graphics2D gd = (Graphics2D)g;
                gd.drawImage(image, 0, 0, null);

                gd.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(w, w);
            }

                 
                                   
        };
         imagerLabel.setBounds(0, 0, w, w);
         add(imagerLabel); 
    }

    /*
    put imageView and cropper in a dialogue to block d screen.
     */
    private void imageDialogue(BufferedImage image){
        int ww = image.getWidth();
        int hh = image.getHeight();
        
        double size = (double)ww / (double)hh;
        if(dW/dH > size){
            dW = (int)((double)dH * (double)size);
        }else{
            dH = (int)((double)dW / (double)size);
        }
        
            dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(dW, dH);
            dialog.setLocationRelativeTo(null);
            Container container = dialog.getContentPane();
            container.setLayout(null);
            JPanel imageView = drawImageView(image);
                imageView.setBounds(0, 0, dW, dH);
            Card controls = imageControls();
                controls.setBounds(dW-50, 10, 35, 105);
             
            JLabel cropper = cropper();
            cropper.setBounds(5, 5, cropSize, cropSize);
            container.add(cropper);
            container.add(controls);
            container.add(imageView);
            dialog.setVisible(true);
            
    }

    /*
    create and render region to cut
     */
    private JLabel cropper(){
        cropSize = dW/3;
        cropper = new JLabel();
            cropper.setBorder(BorderFactory.createLineBorder(Color.RED));
            cropper.setPreferredSize(new Dimension(cropSize, cropSize));


            cropper.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                cropper.setLocation(e.getPoint());

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cropper.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }


            });


            return cropper;
    }

    /*
    resize and draw Image on panel to pick region to cut
     */
    private JPanel drawImageView(BufferedImage image){
        JPanel imageView = new JPanel(){
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(dW, dH);
            }

            @Override
            protected void paintComponent(Graphics g) {
                BufferedImage imageBufferedImage = new BufferedImage(dW, dH, BufferedImage.OPAQUE);
                Graphics2D gImage = imageBufferedImage.createGraphics();
                    gImage.drawImage(image.getScaledInstance(dW, dH, Image.SCALE_SMOOTH), 0, 0, null);
                    gImage.dispose();
                    g.drawImage(imageBufferedImage, 0, 0, null);
                    uploadedImage = imageBufferedImage;
                    g.dispose();
                    
            }
            
            
            };
        imageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() - (cropSize)/2;
                int y = e.getY() - (cropSize)/2;
                if(x < 1)x = 0;
                if(y < 1)y = 0;
                if((x+cropSize) > dW)x = dW-cropSize;
                if((y+cropSize) > dH)y = dH-cropSize;

                cropper.setLocation(x, y);
            }
        });
        return imageView;
    }

    /*
    group all controls together
     */
    private Card imageControls(){
        Card controlsHolder = new Card();
           controlsHolder.setBackground(Color.BLACK);
           controlsHolder.setOpacity(0.4f);
           controlsHolder.setPadding(0);
            controlsHolder.setLayout(new BoxLayout(controlsHolder, BoxLayout.PAGE_AXIS));
            controlsHolder.setPreferredSize(new Dimension(35,105));
            
            JButton zoomIn = new JButton("+");
                zoomIn.setAlignmentX(Component.RIGHT_ALIGNMENT);  
                zoomIn.addActionListener(this);
                zoomIn.setActionCommand("+");
                
            JButton zoomOut = new JButton("-");
                zoomOut.setPreferredSize(new Dimension(30,30));
                zoomOut.setAlignmentX(Component.RIGHT_ALIGNMENT);
                zoomOut.addActionListener(this);
                zoomOut.setActionCommand("-");
                
            JButton save = new JButton("[]");
                save.setPreferredSize(new Dimension(30,30));
                save.setAlignmentX(Component.RIGHT_ALIGNMENT);
                save.addActionListener(this);
                save.setActionCommand("[]");
                
                controlsHolder.add(Box.createRigidArea(new Dimension(0, 5)));
                controlsHolder.add(zoomIn);
                controlsHolder.add(Box.createRigidArea(new Dimension(0, 5)));
                controlsHolder.add(zoomOut);
                controlsHolder.add(Box.createRigidArea(new Dimension(0, 5)));
                controlsHolder.add(save);
                
                return controlsHolder;
    }
    
    private void save() {

            WindowEvent we = new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING);
            dialog.dispatchEvent(we);
            
            BufferedImage image = uploadedImage.getSubimage(cropper.getX(), cropper.getY(), cropSize, cropSize);
            
            validatePaintDP(image);
    }

    private void validatePaintDP(BufferedImage image){
        this.remove(imagerLabel);
        this.validate();
        this.repaint(imagerLabel.getBounds());
        updateImager(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, ext, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        photoByte = baos.toByteArray();
        photoString = Base64.getEncoder().encodeToString(photoByte);
    }

    private void zoomIn() {
        if(cropSize < dW/2){
            cropSize +=zoomSize;
            cropper.setBounds(cropper.getX(), cropper.getY(), cropSize, cropSize);
        }
       
    }

    private void zoomOut() {
        if(cropSize >= dW/3){
            cropSize -=zoomSize;
            cropper.setBounds(cropper.getX(), cropper.getY(), cropSize, cropSize);
        }
    }

    /*
    * get image byte*/
    public byte [] getPhotoByte(){
        return photoByte;
    }

    public void setPhotoByte(byte[] photoByte) {
        this.photoByte = photoByte;
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(photoByte));
            validatePaintDP(image);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getPhotoString(){
        return photoString;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         switch(e.getActionCommand()){
             case "[]":
                 save();
             break;
             case "+":
                 zoomIn();
             break;
             case "-":
                 zoomOut();
             break;
         }
    }


    private FileFilter filterFile(String[] formats){
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory())return true;
                String name = f.getName().toLowerCase();
                String format = name.substring(name.lastIndexOf("."));
                Arrays.sort(formats);
                int index = Arrays.binarySearch(formats, format);
                return index >= 0;
            }

            @Override
            public String getDescription() {
                return "JPG, PNG, BMP Images";
            }
        };

        return fileFilter;
    }
 
}