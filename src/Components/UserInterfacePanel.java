package Components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import Enums.BuildType;
import Enums.ClickType;
import Levels.Level;

public class UserInterfacePanel extends JPanel {
	private GamePanel gamePanel;
	private JLabel hpIconLabel, hpLabel, moneyIconLabel, moneyLabel, waveLabel, shopLabel;
	private JButton turretButton, wallButton, removeButton, bankButton, nextWaveButton;
	private JSeparator jSeparator1, jSeparator2;

	public UserInterfacePanel(GamePanel gamePanel) {
		super();
		this.gamePanel = gamePanel;
		initComponents();
		initLayout();
	}
	
	public void tick(long delta) {
		hpLabel.setText(String.valueOf(gamePanel.getHealth()));
		moneyLabel.setText(String.valueOf(gamePanel.getMoney()));
		waveLabel.setText(String.format("%d / %d", Level.waveNumber, Level.numberOfWaves));
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
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel3Layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(bankButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(wallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(turretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(41, 41, 41))
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup()
								.addComponent(nextWaveButton, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
						.addGroup(
								jPanel3Layout.createSequentialGroup()
										.addGroup(jPanel3Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(moneyIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(hpIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(0, 0, 0)
										.addGroup(jPanel3Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(moneyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(hpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(waveLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(14, 14, 14)))));
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
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
						.addComponent(shopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(turretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(wallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(bankButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(nextWaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(6, 6, 6)));
	}

	private void initComponents() {
		hpIconLabel = new javax.swing.JLabel();
		hpLabel = new javax.swing.JLabel();
		moneyIconLabel = new javax.swing.JLabel();
		moneyLabel = new javax.swing.JLabel();
		waveLabel = new javax.swing.JLabel();
		shopLabel = new javax.swing.JLabel();
		turretButton = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jSeparator2 = new javax.swing.JSeparator();
		wallButton = new javax.swing.JButton();
		removeButton = new javax.swing.JButton();
		bankButton = new javax.swing.JButton();
		nextWaveButton = new javax.swing.JButton();

		setPreferredSize(new java.awt.Dimension(176, 512));
		setRequestFocusEnabled(false);

		hpLabel.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24));
		hpLabel.setText("100");

		moneyLabel.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24));
		moneyLabel.setText("5252");

		waveLabel.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 18));
		waveLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		waveLabel.setText("15 / 15");
		waveLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		shopLabel.setFont(new java.awt.Font("Tahoma", 0, 10));
		shopLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		shopLabel.setText("Shop");

		turretButton.setText("Turret");
		turretButton.setToolTipText("");
		turretButton.setBorderPainted(false);
		turretButton.setContentAreaFilled(false);
		turretButton.setFocusPainted(false);
		turretButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		turretButton.setFocusable(false);
		turretButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(BuildType.Turret);
				gamePanel.setClickSelection(ClickType.Build);
			}
		});

		wallButton.setText("Wall");
		wallButton.setToolTipText("");
		wallButton.setBorderPainted(false);
		wallButton.setContentAreaFilled(false);
		wallButton.setFocusPainted(false);
		wallButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		wallButton.setFocusable(false);
		wallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(BuildType.Wall);
				gamePanel.setClickSelection(ClickType.Build);
			}
		});

		removeButton.setText("Remove");
		removeButton.setToolTipText("");
		removeButton.setBorderPainted(false);
		removeButton.setContentAreaFilled(false);
		removeButton.setFocusPainted(false);
		removeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		removeButton.setFocusable(false);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(null);
				gamePanel.setClickSelection(ClickType.Remove);
			}
		});

		bankButton.setText("Bank");
		bankButton.setToolTipText("");
		bankButton.setBorderPainted(false);
		bankButton.setContentAreaFilled(false);
		bankButton.setFocusPainted(false);
		bankButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		bankButton.setFocusable(false);
		bankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setBuildSelection(BuildType.Bank);
				gamePanel.setClickSelection(ClickType.Build);
			}
		});

		nextWaveButton.setText("Next Wave");
		nextWaveButton.setToolTipText("");
		nextWaveButton.setBorderPainted(false);
		nextWaveButton.setFocusPainted(false);
		nextWaveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		nextWaveButton.setFocusable(false);
	}
}
