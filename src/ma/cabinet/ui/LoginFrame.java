/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.ui;

import ma.cabinet.dao.UtilisateurDAO;
import ma.cabinet.model.Utilisateur;
import ma.cabinet.util.Session;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtLogin = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();
    private UtilisateurDAO userDAO = new UtilisateurDAO();

    public LoginFrame() {
        setTitle("Connexion - Cabinet médical");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Login :"));
        form.add(txtLogin);
        form.add(new JLabel("Mot de passe :"));
        form.add(txtPassword);

        JButton btnLogin = new JButton("Se connecter");
        JButton btnQuitter = new JButton("Quitter");

        btnLogin.addActionListener(e -> doLogin());
        btnQuitter.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel();
        buttons.add(btnLogin);
        buttons.add(btnQuitter);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void doLogin() {
        try {
            String login = txtLogin.getText().trim();
            String pwd = new String(txtPassword.getPassword());

            if (login.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez saisir le login et le mot de passe.");
                return;
            }

            // 🔐 Authentification (assistant OU médecin)
            Utilisateur user = userDAO.authenticate(login, pwd);

            if (user == null) {
                JOptionPane.showMessageDialog(this,
                        "Login ou mot de passe incorrect.");
                return;
            }

            // ✅ On mémorise l'utilisateur connecté (Assistant ou Medecin)
            Session.setCurrentUser(user);

            // ✅ On ouvre la fenêtre principale adaptée au rôle
            MainFrame main = new MainFrame();
            main.setVisible(true);

            // On ferme la fenêtre de login
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
