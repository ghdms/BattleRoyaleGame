package breaktime1.Login;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
   private Statement stmt;
   JTextField nicknameField;
   JPasswordField passwordField;

   JButton bt, bt2;
   private String nickname;
   private char[] pass;

   public BufferedImage img;
   
   public JTextField getNicknameField() {
      return nicknameField;
   }

   public void setNicknameField(JTextField nicknameField) {
      this.nicknameField = nicknameField;
   }

   public JPasswordField getPasswordField() {
      return passwordField;
   }

   public void setPasswordField(JPasswordField passwordField) {
      this.passwordField = passwordField;
   }

   public Signup(Statement st) {
     
      stmt = st;
      
      int x, y;
      x = 800;
      y = 600;
      setTitle("ȸ������");
      setSize(x, y);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLayout(null);

      JLayeredPane layeredPane = new JLayeredPane();
      layeredPane.setBounds(0, 0, x, y);
      layeredPane.setLayout(null);

      MyPanel panel = new MyPanel();
       panel.setBounds(0, 0, x, y);
       
      try {
         img = ImageIO.read(getClass().getResource("/breakt/sign.png"));
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("�̹��� �ҷ����� ����");
      }

      bt = new JButton("�����ϱ�");
      bt.setBounds(470, 420, 140, 70);
      bt.addActionListener(this); // ������ �߰�
      layeredPane.add(bt);

      // �г��� �ʵ�
      nicknameField = new JTextField(15);
      nicknameField.setBounds(170, 420, 280, 30);
      layeredPane.add(nicknameField);

      // ��й�ȣ �ʵ�
      passwordField = new JPasswordField(15);
      passwordField.setBounds(170, 460, 280, 30);
      layeredPane.add(passwordField);
      
      // ������ �߰� ��
      add(layeredPane);
      add(panel);

      setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub

      nickname = nicknameField.getText();
      pass = passwordField.getPassword();

      String password = new String(pass);

      if (nickname.equals("") || password.equals("")) {
         // ���� �޽���
         JOptionPane.showMessageDialog(null, "ȸ�� ������ ��� �Է����ּ���.");
      }
      else {
         String qr = "select nickname from user where nickname = '" + nickname + "'";
         String qr2 = "insert into user values('" + nickname + "', '" + password + "')"; 
         try {
            ResultSet rs = stmt.executeQuery(qr);
            if(rs.next())
            {
               JOptionPane.showMessageDialog(null, "�г��� �ߺ�", "ȸ������ ����", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
               stmt.execute(qr2);
               JOptionPane.showMessageDialog(null, "ȸ������ ����\n" + "nickname : " + nickname);
            }
         } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      }
   }

   class MyPanel extends JPanel {
       public void paint(Graphics g) {
          g.drawImage(img, 0,0,790,570, this);
         setOpaque(false);
       super.paintComponent(g);
       }
    }
}

