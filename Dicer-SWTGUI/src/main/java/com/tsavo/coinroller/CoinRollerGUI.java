package com.tsavo.coinroller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.tsavo.dicer.BetResult;
import org.tsavo.dicer.BettingStrategyListener;
import org.tsavo.dicer.coinroll.BalanceListener;
import org.tsavo.dicer.coinroll.HighscoreListener;
import org.tsavo.dicer.coinroll.StartingBetListener;
import org.tsavo.dicer.local.DicerLocal;
import org.tsavo.dicer.priority92.Priority92BettingStrategy;

public class CoinRollerGUI extends ApplicationWindow {
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());
	private Text txtBalance;
	private Text txtComputedRisk;
	private Text txtHighscore;
	private Text txtStartingBet;
	private Label lblHighestBalance;
	private Label lblStarting;
	private Label lblTotalRisk;
	private Text txtNormalRisk;
	private Text txtLossRisk;
	DicerLocal api = new DicerLocal();
	final Priority92BettingStrategy strategy = new Priority92BettingStrategy(api);
	//GuettingBettingStrategy strategy = new GuettingBettingStrategy(api);
	//ParoliBettingStrategy strategy = new ParoliBettingStrategy(api);
	//RapidRecoveryBettingStrategy strategy = new RapidRecoveryBettingStrategy(api);
	private Text txtMultiplier;
	private Text txtBadStreak;
	private Label lblNormalRisk;
	private Label lblLossRisk;
	private Label lblMultiplier;
	private Label lblBadStreak;
	private Button startButton;

	/**
	 * Create the application window.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public CoinRollerGUI() throws KeyManagementException,
			NoSuchAlgorithmException {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();

		BalanceListener listener = new BalanceListener() {
			@Override
			public void setBalance(final int aBalance) {
				updateBalance(aBalance);
			}
		};
		//api.setBalanceListener(listener);
		org.tsavo.dicer.rapid.ComputedRiskListener riskListener = new org.tsavo.dicer.rapid.ComputedRiskListener() {

			@Override
			public void setComputedRisk(final long anAmount) {
				updateRisk(anAmount);
			}
		};
		// strategy.setComputedRiskListener(riskListener);
		HighscoreListener highscoreListener = new HighscoreListener() {

			@Override
			public void setHighscore(int aHighscore) {
				updateHighscore(aHighscore);
			}
		};
		//api.setHighscoreListener(highscoreListener);
		StartingBetListener startingBetListener = new StartingBetListener() {

			@Override
			public void setStartingBet(int aStartingBet) {

			}
		};
		//api.setStartingBetListener(startingBetListener);
		BettingStrategyListener l = new BettingStrategyListener() {
			long highscore = 0;

			@Override
			public void stopped(String aReason) {
				System.out.println(aReason);
				updateStartButton("Start");
			}

			@Override
			public void started() {
				// TODO Auto-generated method stub

			}

			@Override
			public void oddsChanged(float theOdds) {
				// TODO Auto-generated method stub

			}

			@Override
			public void betSizeChanged(long anAmount) {
				// updateStartingBet(anAmount);
			}

			@Override
			public void betMade(BetResult aResult) {
				updateBalance(aResult.getBalance());
				highscore = Math.max(highscore, aResult.getBalance());
				updateHighscore(highscore);
			}

		};
		strategy.addBettingStrategyListener(l);
		// api.setBalance(5300000);
		// strategy.start();

	}

	DecimalFormat format = new DecimalFormat("#.########");

	public void updateStartingBet(final long myStartingBet) {
		updateGUI(new Runnable() {
			@Override
			public void run() {
				float highscore = ((float) myStartingBet) / 100000000;
				txtStartingBet.setText("" + format.format(highscore));
			}
		});
	}

	public void updateHighscore(final long myHighscore) {
		updateGUI(new Runnable() {
			@Override
			public void run() {
				float highscore = ((float) myHighscore) / 100000000;
				txtHighscore.setText("" + format.format(highscore));
			}
		});
	}

	public void updateRisk(final long anAmount) {
		updateGUI(new Runnable() {
			@Override
			public void run() {
				float risk = ((float) anAmount) / 100000000;
				txtComputedRisk.setText("" + format.format(risk));
			}
		});
	}

	public void updateBalance(final long l) {
		updateGUI(new Runnable() {
			@Override
			public void run() {
				float bal = ((float) l) / 100000000;
				txtBalance.setText("" + format.format(bal));
			}
		});
	}

	public void updateGUI(Runnable aRunnable) {
		Display.getDefault().syncExec(aRunnable);
	}

	public void updateStartButton(final String string) {
		updateGUI(new Runnable() {
			@Override
			public void run() {
				startButton.setText(string);
			}
		});
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(null);

		txtBalance = formToolkit.createText(container, "New Text", SWT.NONE);
		txtBalance.setBounds(89, 10, 76, 21);
		txtBalance.setToolTipText("Total Balance");
		txtBalance.setText("");

		Label lblBalance = new Label(container, SWT.NONE);
		lblBalance.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblBalance.setBounds(10, 13, 73, 15);
		formToolkit.adapt(lblBalance, true, true);
		lblBalance.setText("Balance");

		txtComputedRisk = new Text(container, SWT.BORDER);
		txtComputedRisk.setBounds(89, 91, 76, 21);
		formToolkit.adapt(txtComputedRisk, true, true);

		txtHighscore = new Text(container, SWT.BORDER);
		txtHighscore.setBounds(89, 37, 76, 21);
		formToolkit.adapt(txtHighscore, true, true);

		txtStartingBet = new Text(container, SWT.BORDER);
		txtStartingBet.setText("25");
		txtStartingBet.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				Integer i = new Integer(txtStartingBet.getText());
				if (i > 0) {
					strategy.setStartingBet(i);
				}
			}
		});
		txtStartingBet.setBounds(89, 64, 76, 21);
		formToolkit.adapt(txtStartingBet, true, true);

		lblHighestBalance = new Label(container, SWT.NONE);
		lblHighestBalance.setText("Highscore");
		lblHighestBalance.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblHighestBalance.setBounds(10, 40, 73, 15);
		formToolkit.adapt(lblHighestBalance, true, true);

		lblStarting = new Label(container, SWT.NONE);
		lblStarting.setText("Starting Bet");
		lblStarting.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblStarting.setBounds(10, 67, 73, 15);
		formToolkit.adapt(lblStarting, true, true);

		lblTotalRisk = new Label(container, SWT.NONE);
		lblTotalRisk.setText("Total Risk");
		lblTotalRisk.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblTotalRisk.setBounds(10, 94, 73, 15);
		formToolkit.adapt(lblTotalRisk, true, true);

		txtNormalRisk = new Text(container, SWT.BORDER);
		txtNormalRisk.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				int risk = new Integer(txtNormalRisk.getText());
				if (risk > 1) {
					// strategy.setNormalRisk(risk);
				}
			}
		});

		txtNormalRisk.setText("200");
		txtNormalRisk.setBounds(318, 10, 76, 21);
		formToolkit.adapt(txtNormalRisk, true, true);

		txtLossRisk = new Text(container, SWT.BORDER);
		txtLossRisk.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				int risk = new Integer(txtLossRisk.getText());
				if (risk > 1) {
					// strategy.setLossRisk(risk);
				}
			}
		});
		txtLossRisk.setText("10");
		txtLossRisk.setBounds(318, 37, 76, 21);
		formToolkit.adapt(txtLossRisk, true, true);

		txtMultiplier = new Text(container, SWT.BORDER);
		txtMultiplier.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				Float mul = new Float(txtMultiplier.getText());
				if (mul >= 2 && mul <= 4) {
					// strategy.setMultiplier(mul);
				}

			}
		});
		txtMultiplier.setText("2");
		txtMultiplier.setBounds(318, 64, 76, 21);
		formToolkit.adapt(txtMultiplier, true, true);

		txtBadStreak = new Text(container, SWT.BORDER);
		txtBadStreak.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

				Integer mul = new Integer(txtBadStreak.getText());
				if (mul > 6) {
					// strategy.setBadStreak(mul);
				}

			}
		});
		txtBadStreak.setText("13");
		txtBadStreak.setBounds(318, 91, 76, 21);
		formToolkit.adapt(txtBadStreak, true, true);

		lblNormalRisk = new Label(container, SWT.NONE);
		lblNormalRisk.setText("Normal Risk");
		lblNormalRisk.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblNormalRisk.setBounds(239, 16, 73, 15);
		formToolkit.adapt(lblNormalRisk, true, true);

		lblLossRisk = new Label(container, SWT.NONE);
		lblLossRisk.setText("Loss Risk");
		lblLossRisk.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblLossRisk.setBounds(239, 43, 73, 15);
		formToolkit.adapt(lblLossRisk, true, true);

		lblMultiplier = new Label(container, SWT.NONE);
		lblMultiplier.setText("Multiplier");
		lblMultiplier.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblMultiplier.setBounds(239, 70, 73, 15);
		formToolkit.adapt(lblMultiplier, true, true);

		lblBadStreak = new Label(container, SWT.NONE);
		lblBadStreak.setText("Bad Streak");
		lblBadStreak.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lblBadStreak.setBounds(239, 97, 73, 15);
		formToolkit.adapt(lblBadStreak, true, true);

		startButton = new Button(container, SWT.NONE);
		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (strategy.running) {
					strategy.running = false;
					updateStartButton("Start");
				} else {
					strategy.start();
					updateStartButton("Stop");
				}
			}

		});
		startButton.setBounds(92, 135, 220, 54);
		formToolkit.adapt(startButton, true, true);
		startButton.setText("Start");

		Button btnReset = new Button(container, SWT.NONE);
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//strategy.highscore = 0;
				txtHighscore.setText("0");
				updateHighscore(0);
			}
		});
		btnReset.setBounds(318, 150, 75, 25);
		formToolkit.adapt(btnReset, true, true);
		btnReset.setText("Reset");

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			CoinRollerGUI window = new CoinRollerGUI();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Roller");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(420, 310);
	}
}
