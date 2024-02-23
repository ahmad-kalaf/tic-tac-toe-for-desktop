import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameGUI implements ActionListener {
	
	private JFrame _frame;
	private JLabel _infoLabel;
	private JPanel _fieldsPanel;
	private JButton[] _fields;
	private String _next;
	
	
	public GameGUI() {
		_next = "X";
		
		_frame = new JFrame();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setSize(350,420);
		_frame.setTitle("Tic Tac Toe");
		_frame.setResizable(true);
		_frame.setLayout(new FlowLayout());
		
		_infoLabel = new JLabel();
		_infoLabel.setBackground(Color.gray);
		_infoLabel.setOpaque(true);
		_infoLabel.setBorder(new LineBorder(Color.black, 2));
		_infoLabel.setPreferredSize(new Dimension(300, 50));
		_infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_infoLabel.setVerticalAlignment(SwingConstants.CENTER);
		_infoLabel.setFont(new Font("Lato", Font.PLAIN, 40));
		_infoLabel.setForeground(Color.white);
		_fieldsPanel = new JPanel(new GridLayout(3, 3));
		_fieldsPanel.setPreferredSize(new Dimension(300,300));
		_infoLabel.setText(_next + " ist dran");

		Font buttonsFont = new Font("Arial", Font.PLAIN, 40);
		_fields = new JButton[9];
		for(int i = 0; i < _fields.length; ++i) {
			_fields[i] = new JButton();
			_fields[i].setBackground(Color.cyan);
			_fields[i].setOpaque(true);
			_fields[i].addActionListener(this);
			_fields[i].setFocusable(false);
			_fields[i].setFont(buttonsFont);
			_fieldsPanel.add(_fields[i]);
		}
		
		_frame.add(_fieldsPanel);
		_frame.add(_infoLabel);

		_frame.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button = (JButton)e.getSource();
			button.setText(_next);
			button.setEnabled(false);
			_next = _next.equals("X") ? "O" : "X";
			_infoLabel.setText(_next + " ist dran");
		}
	}

}
