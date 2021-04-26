package juegos1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import aima.core.environment.tictactoe.TicTacToeGame;
import aima.core.environment.tictactoe.TicTacToeState;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;

import aima.core.search.framework.Metrics;
import aima.core.util.datastructure.XYLocation;

/**
 * Simple graphical Tic-tac-toe game application. It demonstrates the Minimax
 * algorithm for move selection as well as alpha-beta pruning.
 * 
 * @author Ruediger Lunde
 */
public class TicTacToeApp {

	static TicTacToePanelLog panelLog;
	
	
	/** Used for integration into the universal demo application. */
	public JFrame constructApplicationFrame() {
		JFrame frame = new JFrame();
		JPanel panel = new TicTacToePanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	/** Application starter. */
	public static void main(String[] args) {
		JFrame frame = new TicTacToeApp().constructApplicationFrame();
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		JFrame frameLog = new JFrame();
		panelLog = new TicTacToePanelLog();
		frameLog.add(panelLog, BorderLayout.CENTER);
		frameLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLog.setSize(500, 400);
		frameLog.setVisible(true);

		
	}

	private static class TicTacToePanelLog extends JPanel{
		JTextArea log;
		
		TicTacToePanelLog() {
					
			this.setLayout(new BorderLayout());
			
			JLabel statusBar = new JLabel("Log");
			add(statusBar, BorderLayout.NORTH);
			
			    log = new JTextArea();
		        log.setBounds(210, 30, 190, 100);
		        log.setEditable(true);
		        log.setLineWrap(true);
		        JScrollPane logScroll = new JScrollPane(log);
		        logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		        logScroll.setPreferredSize(new Dimension(190, 100));
		        add(logScroll, BorderLayout.CENTER);
		}
		
		public void setMessageLog (String logMessage){
			StringBuffer aux=new StringBuffer(log.getText());
			if (aux.length()==0)	log.setText(logMessage);
			else 					{
				log.append("\n");
				log.append(logMessage);
			}
			
		}
		
/*		 public void clearMessageLog ()
		{
			log.setText("");
		}
*/		
	}
	/** Simple panel to control the game. */
	private static class TicTacToePanel extends JPanel implements
			ActionListener {
		private static final long serialVersionUID = 1L;
		JComboBox strategyCombo;
		JButton clearButton;
		JButton proposeButton;
		JButton[] squares;
		JLabel statusBar;

		TicTacToeGame game;
		TicTacToeState currState;
		Metrics searchMetrics;

		/** Standard constructor. */
		TicTacToePanel() {
			this.setLayout(new BorderLayout());
			JToolBar tbar = new JToolBar();
			tbar.setFloatable(false);
			strategyCombo = new JComboBox(new String[] { "Minimax",
					"Alfa-Beta", "Iterative Deepening Alpha-Beta",
					"Iterative Deepening Alpha-Beta (log)" });
			strategyCombo.setSelectedIndex(1);
			tbar.add(strategyCombo);
			tbar.add(Box.createHorizontalGlue());
			clearButton = new JButton("Clear");
			clearButton.addActionListener(this);
			tbar.add(clearButton);
			proposeButton = new JButton("Propose Move");
			proposeButton.addActionListener(this);
			tbar.add(proposeButton);

			add(tbar, BorderLayout.NORTH);
			JPanel spanel = new JPanel();
			spanel.setLayout(new GridLayout(3, 3));
			add(spanel, BorderLayout.CENTER);
			squares = new JButton[9];
			Font f = new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 32);
			for (int i = 0; i < 9; i++) {
				JButton square = new JButton("");
				square.setFont(f);
				square.setBackground(Color.WHITE);
				square.addActionListener(this);
				squares[i] = square;
				spanel.add(square);
			}
			statusBar = new JLabel(" ");
			statusBar.setBorder(BorderFactory.createEtchedBorder());
			add(statusBar, BorderLayout.SOUTH);

			game = new TicTacToeGame();
			actionPerformed(null);
		}

		/** Handles all button events and updates the view. */
		@Override
		public void actionPerformed(ActionEvent ae) {
			searchMetrics = null;
			if (ae == null || ae.getSource() == clearButton)
				currState = game.getInitialState();
			else if (!game.isTerminal(currState)) {
				if (ae.getSource() == proposeButton)
					proposeMove();
				else {
					for (int i = 0; i < 9; i++)
						if (ae.getSource() == squares[i])
							currState = game.getResult(currState,
									new XYLocation(i % 3, i / 3));
				}
			}
			for (int i = 0; i < 9; i++) {
				String val = currState.getValue(i % 3, i / 3);
				if (val == TicTacToeState.EMPTY)
					val = "";
				squares[i].setText(val);
			}
			updateStatus();
		}

		/** Uses adversarial search for selecting the next action. */
		private void proposeMove() {
			AdversarialSearch<TicTacToeState, XYLocation> search;
			XYLocation action;
			switch (strategyCombo.getSelectedIndex()) {
			case 0:
				search = MinimaxSearch.createFor(game);
				break;
			case 1:
				search = AlphaBetaSearch.createFor(game);
				break;
			case 2:
				search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,
						1.0, 1000);
				break;
			default:
				search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,
						1.0, 1000);
				((IterativeDeepeningAlphaBetaSearch<?, ?, ?>) search)
						.setLogEnabled(true);
			}
			action = search.makeDecision(currState);
			searchMetrics = search.getMetrics();
			currState = game.getResult(currState, action);
			
			//panelLog.setMessageLog("aa");
			
			ImprimirCalidad(searchMetrics);
		}

		privat void ImprimirCalidad (Metrics m)
		{
			String NodosExpandidos = m.get("ExpandedNodes");
			String minNodes = m.get("ExpandedNodes");
			String mixNodes = m.get("ExpandedNodes");
			String tiempo = m.get("ExpandedNodes");
			
			panelLog.setMessageLog("Los nodos expandidos son:" + NodosExpandidos + "-"+ minNodes);
		}
		
		
		
		
		/** Updates the status bar. */
		private void updateStatus() {
			String statusText;
			if (game.isTerminal(currState))
				if (game.getUtility(currState, TicTacToeState.X) == 1)
					statusText = "X has won :-)";
				else if (game.getUtility(currState, TicTacToeState.O) == 1)
					statusText = "O has won :-)";
				else
					statusText = "No winner...";
			else
				statusText = "Next move: " + game.getPlayer(currState);
			if (searchMetrics != null)
				statusText += "    " + searchMetrics;
			statusBar.setText(statusText);
		}
	}
}