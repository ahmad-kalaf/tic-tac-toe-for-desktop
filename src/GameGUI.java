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

import game_logic.GameLogic;
import sounds.ClickSound;

/**
 * This GUI is a frame of 9 game fields (3X3) for the game "Tic Tac Toe" / "X ,
 * O".
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
	private GameLogic _logic;
	private final String _player1;
	private final String _player2;
	private ClickSound _clickSound;
	private boolean _isMute;
	private JToggleButton _setMute;

	public GameGUI() {
		_clickSound = new ClickSound();
		_player1 = "\u2717";
		_player2 = "\u2B55";
		_isMute = false;
		
		_logic = new GameLogic();

		_next = _player1;

		// initialize frame
		_frame = new JFrame();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setSize(350, 470);
		_frame.setTitle("Tic Tac Toe");
		_frame.setResizable(false);
		_frame.setLayout(new FlowLayout());
		_frame.setLocationRelativeTo(null);

		// label to display the information who's turn it is
		_infoLabel = new JLabel();
		_infoLabel.setBackground(Color.white);
		_infoLabel.setOpaque(true);
		_infoLabel.setBorder(new LineBorder(Color.black, 2));
		_infoLabel.setPreferredSize(new Dimension(300, 50));
		_infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_infoLabel.setVerticalAlignment(SwingConstants.CENTER);
		_infoLabel.setFont(new Font("Lato", Font.PLAIN, 40));
		_infoLabel.setForeground(Color.black);

		// initialize a panel for the game field
		_fieldsPanel = new JPanel(new GridLayout(3, 3));
		_fieldsPanel.setPreferredSize(new Dimension(300, 300));
		_infoLabel.setText(_next + " ist dran");

		// initialize all game fields and add to the _fieldsPanel
		Font buttonsFont = new Font("Palatino", Font.PLAIN, 40);
		_fields = new JButton[9];
		for (int i = 0; i < _fields.length; ++i) {
			_fields[i] = new JButton();
			_fields[i].setBackground(Color.white);
			_fields[i].setForeground(Color.black);
			_fields[i].setOpaque(true);
			_fields[i].addActionListener(this);
			_fields[i].setFocusable(false);
			_fields[i].setFont(buttonsFont);
			_fieldsPanel.add(_fields[i]);
		}

		// single button to restart the game
		_restartButton = new JButton("\u27F3");
		_restartButton.addActionListener(this);
		_restartButton.setPreferredSize(new Dimension(150, 50));
		_restartButton.setFocusable(false);
		
		// create button to turn on / off the sound
		createToggleButton();

		// add components to the frame
		_frame.add(_fieldsPanel);
		_frame.add(_infoLabel);
		_frame.add(_setMute);
		_frame.add(_restartButton);		

		_frame.setVisible(true);
	}

	/**
	 * If the user click one of the game fields or the restart button, this action
	 * will be performed. By click on a game filed / button, its text will be set to
	 * "X" or "O" depending to the next Player. By click on the restart button, the
	 * user get the option to confirm restarting the game or cancel the restarting.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int row = 0;
		int col = 0;

		// get the postition of clicked game field 
		for (int i = 0; i < 9; i++) {
			if (e.getSource() == _fields[i]) {
				if (i <= 2) {
					row = 0;
					col = i == 0 ? 0 : (i == 1 ? 1 : 2);
				} else if (i <= 5) {
					row = 1;
					col = i == 3 ? 0 : (i == 4 ? 1 : 2);
				} else if (i <= 8) {
					row = 2;
					col = i == 6 ? 0 : (i == 7 ? 1 : 2);
				}
			}
		}

		if (e.getSource() == _restartButton) { // source is restart button
			confirmClearFields();
		} else if (e.getSource() instanceof JButton) { // source is a game filed
			JButton button = (JButton) e.getSource();
			if (!_logic.spielIstZuende() && _logic.istFrei(row, col)) { // the game isn't over
				// play sound
				if(!_isMute) {
					try {
						_clickSound.startSound();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				_logic.besetzePosition(row, col);
				button.setText(_next);
				setNextPlayer();
				// game over ?
				if (_logic.spielIstZuende()) {
					showWinner();
				}
				
			} else if (_logic.spielIstZuende()) { // game is over
				showWinner();
			}
			
			_infoLabel.setText(_next + " ist dran");
		}
	}

	private void showWinner() {
		for (int index : _logic.get_winnIndicies()) {
			_fields[index].setBackground(Color.green);
		}
		gameOverDialog();
	}

	private void setNextPlayer() {
		_next = _logic.gibAktuellenSpieler() == 1 ? _player1 : _player2;
		if (_logic.gibAktuellenSpieler() == 0) {
			_next = "-";
		}
	}

	/**
	 * clear / delete the text of all game fields
	 */
	private void clearFields() {
		_logic = new GameLogic();
		for (int i = 0; i < _fields.length; ++i) {
			_fields[i].setText("");
			_fields[i].setBackground(Color.white);
			_fields[i].setEnabled(true);
			_next = _player1;
			_infoLabel.setText("X ist dran");
		}
	}

	/**
	 * Ask the user to confirm restarting game / clear text of all game fields. User
	 * has the option to confirm or cancel by click on "Ja" or "Nein".
	 */
	private void confirmClearFields() {
		JDialog confirmDialog = new JDialog(_frame, "Neustart", true);

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

		JLabel confirmQuestion = new JLabel("Spiel neustarten?");
		confirmQuestion.setHorizontalAlignment(SwingConstants.CENTER);

		confirmDialog.setLayout(new BorderLayout());
		confirmDialog.add(confirmQuestion, BorderLayout.CENTER);
		confirmDialog.add(yesNoPanel, BorderLayout.SOUTH);

		confirmDialog.setSize(200, 100);
		confirmDialog.setLocationRelativeTo(_frame);
		confirmDialog.setResizable(false);

		confirmDialog.setVisible(true);
	}

	/**
	 * This method is called when the game is over / all fields are occupied / a
	 * player has won. The player has the options to end the game or restart it.
	 */
	private void gameOverDialog() {
		JDialog confirmDialog = new JDialog(_frame, "Spiel zu Ende", true);

		JButton restart = new JButton("Neustart");
		JButton exit = new JButton("Spiel beenden");

		restart.setFocusable(false);
		exit.setFocusable(false);

		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
				confirmDialog.dispose();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_frame.dispose();
			}
		});

		JPanel yesNoPanel = new JPanel();
		yesNoPanel.add(restart);
		yesNoPanel.add(exit);

		JLabel confirmQuestion = new JLabel(
				_logic.gibGewinner() != 0 ? ("Gewinner: " + convNumToPlayer(_logic.gibGewinner()))
						: (convNumToPlayer(_logic.gibGewinner())));
		confirmQuestion.setHorizontalAlignment(SwingConstants.CENTER);

		confirmDialog.setLayout(new BorderLayout());
		confirmDialog.add(confirmQuestion, BorderLayout.CENTER);
		confirmDialog.add(yesNoPanel, BorderLayout.SOUTH);

		confirmDialog.pack();
		confirmDialog.setLocationRelativeTo(_frame);
		confirmDialog.setResizable(false);

		confirmDialog.setVisible(true);
	}

	/**
	 * Converts a number to player. 1 is X, 2 is O.
	 * @param num
	 * @return "X" if num is 1, "O" if num is 2, else "Unentschieden"
	 */
	private String convNumToPlayer(int num) {
		String result = "Unentschieden";
		switch (num) {
		case 1:
			result = _player1;
			break;
		case 2:
			result = _player2;
			break;
		}
		return result;
	}
	
	private void createToggleButton() {
		_setMute = new JToggleButton("Ton ist an");
		_setMute.setFocusable(false);
		_setMute.setPreferredSize(new Dimension(150, 50));
		_setMute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_setMute.isSelected()) {
					_setMute.setText("Ton is aus");
					_isMute = true;
				} else {
					_setMute.setText("Ton is an");
					_isMute = false;
				}
			}
		});
	}
}