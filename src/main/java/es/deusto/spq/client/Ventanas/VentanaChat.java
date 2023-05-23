package es.deusto.spq.client.Ventanas;

import javax.swing.*;

import es.deusto.spq.client.PictochatntClient;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaChat extends JFrame{
	
	private static final long serialVersionUID = 6788025475843454961L;
	private static final int size = 512;
	int screenWidth;
    int screenHeigth;
    
    JTextField texto;
    JButton palante;
    
    private int x1, y1;
    
    JPanel pDibujo;
    
    JLabel title;
    JTextArea taTexto;
    Graphics g;
    Graphics2D g2d;
    BufferedImage bImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    
    public static VentanaChat ventanaChat;

    public VentanaChat() {
		ventanaChat = this;
        setTitle("Chat In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit tk = Toolkit.getDefaultToolkit();
        screenWidth = tk.getScreenSize().width;
        screenHeigth = tk.getScreenSize().height;
        setUndecorated(true);
        

        JPanel panel = new JPanel() {
			private static final long serialVersionUID = -4560585749615976907L;
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/main/java/es/deusto/spq/client/Imagenes/Wallpaper.jpeg").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JPanel panel2 = new JPanel() {
			private static final long serialVersionUID = -506574610894438989L;
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("src/main/java/es/deusto/spq/client/Imagenes/closeImage.png").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel2.setOpaque(false);
        panel2.setBounds(20,20,50,50);
        // Font y = new Font("Serif", Font.BOLD, 100);
        Font x = new Font("Serif", Font.PLAIN, 35);
        Font z = new Font("Serif", Font.PLAIN, 25);
        
        
        pDibujo = new JPanel();
        pDibujo.setBounds(50,100,(int)(screenWidth*0.6),(int)(screenHeigth*0.85));
        pDibujo.setBackground(Color.WHITE);
        
        
        
        taTexto = new JTextArea(8,40);
        taTexto.setBounds(1000,100,100,100);
        taTexto.setLocation(1000,100);
        taTexto.setFont(z);
        taTexto.setEditable(false);
        
        
        JScrollPane pTexto = new JScrollPane(taTexto);
        pTexto.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pTexto.setBounds((int)(screenWidth*0.65),100,(int)(screenWidth*0.3),(int)(screenHeigth*0.75));
        pTexto.setBackground(Color.WHITE);
        
        title = new JLabel("Sala XXXX");
        title.setBounds(200, 200, 500, 75);
        title.setForeground(Color.black);
        title.setFont(x);
        title.setLocation(screenWidth/2 - 50, 25);
        
        texto = new JTextField();
        texto.setFont(x);
        texto.setBounds((int)(screenWidth*0.65),(int)(screenHeigth*0.87),(int)(screenWidth*0.25),(int)(screenHeigth*0.05));
        palante = new JButton("Ok");
        palante.setFont(x);
        palante.setBounds((int)(screenWidth*0.90),(int)(screenHeigth*0.87),(int)(screenWidth*0.05),(int)(screenHeigth*0.05));
        
        
        
        panel.add(title);
        panel.add(panel2);
        panel.add(pDibujo);
        panel.add(pTexto);
        panel.add(texto);
        panel.add(palante);
        setContentPane(panel);
        setLayout(null);
        setVisible(true);
        
        g = getGraphics();
        g2d = (Graphics2D) g;
        panel2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				PictochatntClient.leaveRoom();
			}
		});
        
        pDibujo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	/*
            	x1 = e.getXOnScreen();
                y1 = e.getYOnScreen(); 
                if (e.getButton() == 1) {
                    // Botón izquierdo del mouse (pintar en blanco)
                	g.setColor(Color.black);
                	g2d.setStroke(new BasicStroke(3));
                } else if (e.getButton() == 3) {
                    // Botón derecho del mouse (pintar en negro)
                    g.setColor(Color.white);
                    g2d.setStroke(new BasicStroke(5));
                }*/
            }
        });
        
        pDibujo.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            	/*
                int x2 = e.getXOnScreen();
                int y2 = e.getYOnScreen();
                if (pDibujo.contains(e.getPoint())) {
                    
                    g2d.drawLine(x1, y1, x2, y2);

                    x1 = x2;
                    y1 = y2;
                }*/
            }
        });;
        
        palante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//taTexto.append("(Time)(User) " + texto.getText() + "\n");
				
				PictochatntClient.sendMessage(texto.getText());
				texto.setText("");
			}
		});
        
        
    }
    
    public void addMessage(String user, String text, long timeStamp) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	Date date = new Date(timeStamp);
    	taTexto.append("" + sdf.format(date) + " (" + user + ") " + text + "\n");
    }
	
	public void setRoomName(String name) {
		title.setText(name);
	}
	
	public void getImageHistory(byte[] image) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (image[y*size+x] != 0) {
					bImage.setRGB(x, y, 0xFFFFFF);
				} else {
					bImage.setRGB(x, y, 0x000000);
				}
			}
		}
		Rectangle r = g.getClipBounds();
		g2d.drawImage(bImage, 0, 0, (int) r.getWidth(), (int) r.getHeight(), Color.WHITE, null);
		pDibujo.repaint();
	}
	
	private void setPixel(int x, int y, boolean erase) {
		if (!erase) {
			bImage.setRGB(x, y, 0x000000);
		} else {
			bImage.setRGB(x, y, 0xFFFFFF);
		}
	}
	
	public void paint(int x, int y, boolean erase) {
		int startX = x - 1;
		int startY = y - 1;
		int endX = x + 1;
		int endY = y + 1;
		
		if (startX < 0) {
			startX = 0;
		}
		
		if (startY < 0) {
			startY = 0;
		}
		
		if (endX >= size) {
			endX = size - 1;
		}
		
		if (endY >= size) {
			endY = size - 1;
		}
		
		for (int pX = startX; pX <= endX; pX++) {
			for (int pY = startY; pY <= endY; pY++) {
				this.setPixel(pX, pY, erase);
			}
		}
		
		Rectangle r = g.getClipBounds();
		g2d.drawImage(bImage, 0, 0, (int) r.getWidth(), (int) r.getHeight(), Color.WHITE, null);
		pDibujo.repaint();
	}

    public static void main(String[] args) {
        new VentanaChat();
    }
}