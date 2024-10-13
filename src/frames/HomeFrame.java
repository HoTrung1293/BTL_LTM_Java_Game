package frames;

import entity.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import repository.UserRepo;
import service.Client;

public class HomeFrame extends JFrame implements ActionListener {
    private User user;
    private UserRepo us;
    private JButton profileButton, playButton, infoButton, logoutButton, exitButton;
    private JScrollPane scrollPane;
    private JPanel topPanel;
    private JLabel usernameLabel;
    private JTable table;
    private JPanel rightPanel;
    private DefaultTableModel model;
    private String selectedUsername;
    
    public HomeFrame(User user) {
        this.user = user;
        us = new UserRepo();

        // Thiết lập tiêu đề của cửa sổ
        setTitle("Home");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo layout chính
        setLayout(new BorderLayout(10, 10));

        // Panel phía trên cùng cho tên người dùng và nút Profile
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(new Color(220, 220, 220));

        usernameLabel = new JLabel("Username: " + this.user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profileButton = createStyledButton("My Profile");
        profileButton.setFont(new Font("Arial", Font.PLAIN, 14));
        profileButton.setBackground(new Color(70, 130, 180));
        profileButton.setForeground(Color.WHITE);
        profileButton.addActionListener(this);

        topPanel.add(usernameLabel);
        topPanel.add(profileButton);
        add(topPanel, BorderLayout.NORTH);

        // Bảng người dùng
        String data[][] = us.getAllUser();
        String head[] = {"Username", "Status"};
        
        model = new DefaultTableModel(data, head) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa các ô
            }
        };

        table = new JTable(model);

        // Đặt font cho bảng và tùy chỉnh màu nền
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(192, 192, 192));

        // Tùy chỉnh màu trạng thái
        table.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel cell = new JLabel(value.toString());
                cell.setOpaque(true);
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 14));

                switch (value.toString()) {
                    case "Online":
                        cell.setForeground(Color.GREEN);
                        break;
                    case "In Match":
                        cell.setForeground(Color.BLUE);
                        break;
                    default:
                        cell.setForeground(Color.RED);
                        break;
                }
                 if (isSelected) {
                    cell.setBackground(new Color(173, 216, 230)); // Màu nền nổi bật
                } else {
                    cell.setBackground(Color.WHITE); // Màu nền mặc định
                }
                return cell;
            }
        });
        // Lắng nghe sự kiện click chuột trên bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) { // Kiểm tra có hàng nào được chọn không
                    selectedUsername = table.getValueAt(row, 0).toString(); // Lấy tên người dùng
                }
            }
        });

        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bên phải chứa các nút điều khiển
        rightPanel = new JPanel(new GridLayout(5, 1, 5, 15));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playButton = createStyledButton("Play");
        infoButton = createStyledButton("Info");
        logoutButton = createStyledButton("Log out");
        exitButton = createStyledButton("Exit");
        
        playButton.addActionListener(this);
        infoButton.addActionListener(this);
        logoutButton.addActionListener(this);
        exitButton.addActionListener(this);

        rightPanel.add(playButton);
        rightPanel.add(infoButton);
        rightPanel.add(logoutButton);
        rightPanel.add(exitButton);

        add(rightPanel, BorderLayout.EAST);

        // Cập nhật bảng tự động khi có thay đổi
        Timer timer = new Timer(1000, e -> refreshUserTable());
        timer.start();

        // Thêm Window Listener để ngắt kết nối khi cửa sổ bị đóng
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnectFromServer();
            }
        });

        // Hiển thị cửa sổ
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void refreshUserTable() {
        // Lấy dữ liệu mới và cập nhật bảng
        String[][] data = us.getAllUser();
        model.setDataVector(data, new String[] {"Username", "Status"});
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(192, 192, 192));

        // Tùy chỉnh màu trạng thái
        table.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel cell = new JLabel(value.toString());
                cell.setOpaque(true);
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 14));

                switch (value.toString()) {
                    case "Online":
                        cell.setForeground(Color.GREEN);
                        break;
                    case "In Match":
                        cell.setForeground(Color.BLUE);
                        break;
                    default:
                        cell.setForeground(Color.RED);
                        break;
                }
                return cell;
            }
        });
    }

    private void disconnectFromServer() {
        Client.disconnect();
        System.exit(0); // Thoát ứng dụng
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        if (command.equals(profileButton.getText())) {
            UserProfile mp = new UserProfile(this.user);
            mp.setVisible(true);
        } else if (command.equals(playButton.getText())) {
            // Thêm logic cho Play button
        } else if (command.equals(infoButton.getText())) {
            if (selectedUsername != null) {
                // Hiển thị MyProfileFrame với username đã chọn
                UserRepo ur = new UserRepo();
                User selectedUser = ur.getUserInfo(selectedUsername);
                UserProfile mp = new UserProfile(selectedUser);
                mp.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a username from the table.");
            }
        } else if (command.equals(logoutButton.getText())) {
             // Gọi phương thức disconnect() của lớp Client
            Client.disconnect();
            
            // Mở lại LoginFrame và đóng HomeFrame
            new LoginFrame().setVisible(true);
            this.dispose();
        } else if (command.equals(exitButton.getText())) {
            disconnectFromServer();
        }
    }
}
