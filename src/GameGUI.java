import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This GUI is a frame of 9 game fields (3X3) for the game 
 * "Tic Tac Toe" / "X , O".
 * 
 * @author Ahmad Kalaf
 */
public class GameGUI implements ActionListener {
	
	private JFrame _frame;
	private JLabel _infoLabel;
	private JPanel _fieldsPanel;
	private JButton[] _fields;
	private JButton _restartButton;
	private String _next;
	
	public GameGUI() {
		_next = "X";
		
		// initialize frame
		_frame = new JFrame();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setSize(350,420);
		_frame.setTitle("Tic Tac Toe");
		_frame.setResizable(false);
		_frame.setLayout(new FlowLayout());
		
		// label to display the information who's turn it is
		_infoLabel = new JLabel();
		_infoLabel.setBackground(Color.gray);
		_infoLabel.setOpaque(true);
		_infoLabel.setBorder(new LineBorder(Color.black, 2));
		_infoLabel.setPreferredSize(new Dimension(250, 50));
		_infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_infoLabel.setVerticalAlignment(SwingConstants.CENTER);
		_infoLabel.setFont(new Font("Lato", Font.PLAIN, 40));
		_infoLabel.setForeground(Color.white);
		
		// initialize a panel for the game field 
		_fieldsPanel = new JPanel(new GridLayout(3, 3));
		_fieldsPanel.setPreferredSize(new Dimension(300,300));
		_infoLabel.setText(_next + " ist dran");

		// initialize all game fields and add to the _fieldsPanel
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
		
		// single button to restart the game
		_restartButton = new JButton("\u27F3");
		_restartButton.addActionListener(this);
		_restartButton.setPreferredSize(new Dimension(50, 50));
		_restartButton.setFocusable(false);
		
		// add components to the frame
		_frame.add(_fieldsPanel);
		_frame.add(_infoLabel);
		_frame.add(_restartButton);

		_frame.setVisible(true);
		
	}


	/**
	 * If the user click one of the game fields or the restart button,
	 * this action will be performed.
	 * By click on a game filed / button, its text will be set to "X" or "O"
	 * depending to the next Player.
	 * By click on the restart button, the user get the option to confirm restarting
	 * the game or cancel the restarting.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _restartButton) {
			confirmClearFields();
		} else if(e.getSource() instanceof JButton) {
			JButton button = (JButton)e.getSource();
			button.setText(_next);
			button.setEnabled(false);
			_next = _next.equals("X") ? "O" : "X";
			_infoLabel.setText(_next + " ist dran");
		}
	}
	
	/**
	 * clear / delete the text of all game fields 
	 */
	private void clearFields() {
		for(int i = 0; i < _fields.length; ++i) {
			_fields[i].setText("");
			_fields[i].setEnabled(true);
			_next = "X";
			_infoLabel.setText("X ist dran");
		}
	}
	
	/**
	 * Ask the user to confirm restarting game / clear text of all game fields.
	 * User has the option to confirm or cancel by click on "Ja" or "Nein". 
	 */
	private void confirmClearFields() {
		JDialog confirmDialog = new JDialog(_frame, "Neustart bestätigen", true);
		
		JButton yes = new JButton("Ja");
		JButton no = new JButton("Nein");
		
		yes.setFocusable(false);
		no.setFocusable(false);
		
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
				confirmDialog.dispose();
			}
		});
		
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmDialog.dispose();
			}
		});
		
		JPanel yesNoPanel = new JPanel();
		yesNoPanel.add(yes);
		yesNoPanel.add(no);
		
		JLabel confirmQuestion = new JLabel("Spiel Neustarten?");
		confirmQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		
		confirmDialog.setLayout(new BorderLayout());
		confirmDialog.add(confirmQuestion, BorderLayout.CENTER);
		confirmDialog.add(yesNoPanel, BorderLayout.SOUTH);
		
		confirmDialog.setSize(200,100);
		confirmDialog.setLocationRelativeTo(_frame);
		confirmDialog.setResizable(false);
		
		confirmDialog.setVisible(true);
	}

}