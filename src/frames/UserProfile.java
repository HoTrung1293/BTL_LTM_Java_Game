package frames;

import entity.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import repository.UserRepo;

public class UserProfile extends JFrame implements ActionListener{
    private User user;
    private UserRepo us;
    private JPanel topPanel, infoPanel, bottomPanel;
    private JButton infoButton, closeButton;
    private JLabel usernameLabel, usernameValue, genderLabel, genderValue, ratingLabel, ratingValue, avgCompetitorLabel, avgCompetitorValue,avgTimeLabel, avgTimeValue, totalMatchLabel, totalMatchValue, winLabel, winValue,
            loseLabel, loseValue,drawLabel,drawValue   ;
    public UserProfile(User user) {
        this.user=user;
        us =new UserRepo();
        // Thiết lập cửa sổ chính
        setTitle(user.getUsername()+ " Profile");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout chính
        setLayout(new BorderLayout(10, 10));

        // Tạo nút Info User phía trên
        topPanel = new JPanel();
        infoButton = createStyledButton("Info User");
        infoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        infoButton.setBackground(new Color(70, 130, 180));
        infoButton.setForeground(Color.WHITE);
        topPanel.add(infoButton);
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm cho thông tin người dùng
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(400, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Thông tin người dùng
        usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(usernameLabel, gbc);

        usernameValue = new JLabel(this.user.getUsername());
        usernameValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        infoPanel.add(usernameValue, gbc);

        genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(genderLabel, gbc);

        genderValue = new JLabel(this.user.getGender());
        genderValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        infoPanel.add(genderValue, gbc);

        ratingLabel = new JLabel("Rating: ");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(ratingLabel, gbc);

        ratingValue = new JLabel(""+this.user.getRating());
        ratingValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        infoPanel.add(ratingValue, gbc);

        avgCompetitorLabel = new JLabel("Avg Competitor: ");
        avgCompetitorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(avgCompetitorLabel, gbc);

        avgCompetitorValue = new JLabel(""+this.user.getAvgCompetitor());
        avgCompetitorValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        infoPanel.add(avgCompetitorValue, gbc);

        avgTimeLabel = new JLabel("Avg Time: ");
        avgTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(avgTimeLabel, gbc);

        avgTimeValue = new JLabel(""+this.user.getAvgTime()+" h");
        avgTimeValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        infoPanel.add(avgTimeValue, gbc);

        // Thông tin trận đấu bên phải
        totalMatchLabel = new JLabel("Total Match: ");
        totalMatchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 0;
        infoPanel.add(totalMatchLabel, gbc);

        totalMatchValue = new JLabel(""+this.user.getTotalMatch());
        totalMatchValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 3;
        gbc.gridy = 0;
        infoPanel.add(totalMatchValue, gbc);

        winLabel = new JLabel("Win: ");
        winLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 1;
        infoPanel.add(winLabel, gbc);

        winValue = new JLabel(""+this.user.getWin());
        winValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 3;
        gbc.gridy = 1;
        infoPanel.add(winValue, gbc);

        loseLabel = new JLabel("Lose: ");
        loseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 2;
        infoPanel.add(loseLabel, gbc);

        loseValue = new JLabel(""+this.user.getLose());
        loseValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 3;
        gbc.gridy = 2;
        infoPanel.add(loseValue, gbc);

        drawLabel = new JLabel("Draw: ");
        drawLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 3;
        infoPanel.add(drawLabel, gbc);

        drawValue = new JLabel(""+this.user.getDraw());
        drawValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 3;
        gbc.gridy = 3;
        infoPanel.add(drawValue, gbc);

        add(infoPanel, BorderLayout.CENTER);

        // Nút Close ở dưới
        bottomPanel = new JPanel();
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.setBackground(new Color(70, 130, 180));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

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
   @Override
    public void actionPerformed(ActionEvent ae)	{
        String command = ae.getActionCommand();
        if(command.equals(closeButton.getText())){
//            closeButton.addActionListener(e -> dispose());
        }
    }
}

