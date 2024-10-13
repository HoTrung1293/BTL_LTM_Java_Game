package frames;

import entity.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import repository.UserRepo;
import service.Client;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel title, userLabel, passLabel;
    private JTextField userTF;
    private JPasswordField passPF;
    private JButton loginBtn, backBtn, showPassBtn;
    private JPanel panel;

    public LoginFrame() {
        super("Login");

        this.setSize(800, 450);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Tạo một panel với layout GridBagLayout
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Màu nền tương tự như HomeFrame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.anchor = GridBagConstraints.CENTER;

        // Tiêu đề
        title = new JLabel("Login Form");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Tiêu đề chiếm 2 cột
        panel.add(title, gbc);

        // Nhãn và trường User ID
        userLabel = new JLabel("User ID: ");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridwidth = 1; // Trở lại chiếm 1 cột
        gbc.gridy = 1;
        panel.add(userLabel, gbc);

        userTF = new JTextField(15);
        gbc.gridx = 1;
        panel.add(userTF, gbc);

        // Nhãn và trường Password
        passLabel = new JLabel("Password: ");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        passPF = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passPF, gbc);

        // Nút Show/Hide Password
        showPassBtn = new JButton("Show");
        showPassBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        showPassBtn.setBackground(new Color(70, 130, 180));
        showPassBtn.setForeground(Color.WHITE);
        showPassBtn.addActionListener(e -> {
            if (passPF.getEchoChar() == '*') {
                passPF.setEchoChar((char) 0); // Hiện mật khẩu
                showPassBtn.setText("Hide");
            } else {
                passPF.setEchoChar('*'); // Ẩn mật khẩu
                showPassBtn.setText("Show");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(showPassBtn, gbc);

        // Nút Login
        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(this);
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(loginBtn, gbc);

        // Nút Back
        backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        backBtn.setBackground(new Color(70, 130, 180));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(this);
        gbc.gridx = 1;
        panel.add(backBtn, gbc);

        this.add(panel);
        this.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
        this.setVisible(true); // Hiển thị cửa sổ
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals(loginBtn.getText())) {
            // Gửi thông tin đăng nhập đến server
            String response = Client.login(userTF.getText(), String.valueOf(passPF.getPassword()));

            if ("Login successful".equals(response)) {
                // Nếu đăng nhập thành công, mở HomeFrame
                System.out.println("Login successful");
                UserRepo ur = new UserRepo();
                User user = ur.getUserLogin(userTF.getText(), String.valueOf(passPF.getPassword()));
                //System.out.println(user.getId()+" "+user.getUsername()+" "+user.getPassword());
                if (user != null) {
                user = ur.getUserInfo(user.getUsername());
                //System.out.println(user.getId()+" "+user.getUsername()+" "+user.getGender()+" "+user.getAvgCompetitor());
                HomeFrame hf = new HomeFrame(user);
                hf.setVisible(true);
                this.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Id or Password");
            }
        } else if (command.equals(backBtn.getText())) {
            // Logic for back button if needed
            this.setVisible(false); // Đóng cửa sổ LoginFrame khi nhấn Back
        }
    }
}
