package Components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

import Buildings.Bank;
import Buildings.Turret;
import Enums.BuildType;
import Enums.ClickType;
import Levels.Level;
import Utility.Assets;

public class UserInterfacePanel extends JPanel {
	private static final String turretTooltip = String.format(
			"<html><font color='black'> Turret shoots <b>%d</b> damage projectiles at nearby enemies </font></html>",
			Turret.DAMAGE);
	private static final String wallTooltip = "<html><font color='black'> Wall blocks enemies from passing through </font></html>";
	private static final String bankTooltip = String.format(
			"<html><font color='black'> Bank gives you <b>%d</b> extra money for every enemy that dies within its range </font></html>",
			Bank.BOUNTY_BONUS);
	private static final String removeTooltip = "<html><font color='black'> Removing a building <b>does not</b> give you any money back </font></html>";
	private GamePanel gamePanel;
	private JLabel hpIconLabel, hpLabel, moneyIconLabel, moneyLabel, waveLabel, shopLabel;
	private CustomButton turretButton, wallButton, removeButton, bankButton, nextWaveButton;
	private JSeparator jSeparator1, jSeparator2;
	public static JProgressBar waveProgressBar;

	public UserInterfacePanel(GamePanel gamePanel) {
		super();
		this.gamePanel = gamePanel;
		initComponents();
		initLayout();
	}

	public JProgressBar getProgressBar() {
		return waveProgressBar;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Assets.background_UIpanel, 0, 0, getWidth(), getHeight(), null);
	}

	public void tick(long delta) {
		hpLabel.setText(String.valueOf(gamePanel.getHealth()));
		moneyLabel.setText(String.valueOf(gamePanel.getMoney()));
		waveLabel.setText(
				String.format("<html><font color:black>%d / %d</font></html>", Level.waveNumber, Level.numberOfWaves));
	}

	private void initLayout() {
		setPreferredSize(new Dimension(176, 512));
		setRequestFocusEnabled(false);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(this);
		setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(shopLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
				.addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup()
								.addGroup(jPanel3Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(moneyIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(hpIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(0, 0, 0)
								.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(moneyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(hpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(waveLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(14, 14, 14))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(wallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(bankButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(turretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(42, 42, 42))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
								.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(waveProgressBar, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(nextWaveButton, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap()))));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
										.addGroup(jPanel3Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(hpIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(hpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanel3Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(moneyLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(moneyIconLabel)))
								.addGroup(jPanel3Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(
										waveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(5, 5, 5)
						.addComponent(shopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(turretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addComponent(wallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addComponent(bankButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(waveProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
						.addComponent(nextWaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
	}

	class CustomButton extends JButton implements MouseListener {

		public CustomButton(String tooltipText, int id) {
			try {
				setIcon(new ImageIcon(Assets.tileset_button.get(id)));
				setSelectedIcon(new ImageIcon(Assets.tileset_button.get(id + 1)));
				setPressedIcon(new ImageIcon(Assets.tileset_button.get(id + 2)));

			} catch (Exception e) {
			}
			setToolTipText(tooltipText);
			addMouseListener(this);
			setBorderPainted(false);
			setContentAreaFilled(false);
			setFocusPainted(false);
			setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			setFocusable(false);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setSelected(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setSelected(false);
		}

	}

	private void initComponents() {
		setPreferredSize(new java.awt.Dimension(176, 512));
		setRequestFocusEnabled(false);

		hpIconLabel = new javax.swing.JLabel();
		hpLabel = new javax.swing.JLabel();
		moneyIconLabel = new javax.swing.JLabel();
		moneyLabel = new javax.swing.JLabel();
		waveLabel = new javax.swing.JLabel();
		shopLabel = new javax.swing.JLabel();

		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();

		turretButton = new CustomButton(turretTooltip, 0);
		wallButton = new CustomButton(wallTooltip, 3);
		bankButton = new CustomButton(bankTooltip, 6);
		removeButton = new CustomButton(removeTooltip, 9);
		nextWaveButton = new CustomButton("", 20);

		waveProgressBar = new JProgressBar();
		waveProgressBar.setMinimum(0);
		waveProgressBar.setValue(0);

		hpLabel.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24));
		hpIconLabel.setIcon(new ImageIcon(Assets.icon_heart));

		moneyLabel.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24));
		moneyIconLabel.setIcon(new ImageIcon(Assets.icon_money));

		waveLabel.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 18));
		waveLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		waveLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		shopLabel.setFont(new java.awt.Font("Tahoma", 0, 10));
		shopLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		shopLabel.setText("Shop");
		
		hpLabel.setText(String.valueOf(gamePanel.getHealth()));
		moneyLabel.setText(String.valueOf(gamePanel.getMoney()));
		waveLabel.setText(
				String.format("<html><font color:black>%d / %d</font></html>", Level.waveNumber, Level.numberOfWaves));

		turretButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(BuildType.Turret);
				gamePanel.setClickSelection(ClickType.Build);
			}
		});

		wallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(BuildType.Wall);
				gamePanel.setClickSelection(ClickType.Build);
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(null);
				gamePanel.setClickSelection(ClickType.Remove);
			}
		});

		bankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(BuildType.Bank);
				gamePanel.setClickSelection(ClickType.Build);
			}
		});

		nextWaveButton.setText("Next Wave");
		nextWaveButton.setContentAreaFilled(true);
		nextWaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePanel.getCurrentLevel().nextWave();
			}
		});
	}
}
