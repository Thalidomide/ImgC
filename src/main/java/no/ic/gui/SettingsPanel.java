package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import engine.EngineMode;
import util.Manager;

/**
 * @author Olav Jensen
 * @since Apr 11, 2010
 */
public class SettingsPanel extends JPanel {

	private final SettingsListener listener;
	private EngineModeRadioButton[] engineModes;
	private JPanel engineModeSubPanel;

	/* Composite settings */
	private JCheckBox reversedImageOrder;

	public SettingsPanel(SettingsListener listener) {
		super(new BorderLayout());

		this.listener = listener;

		addSettingsComponents();
		addButtons();
	}

	private void addSettingsComponents() {
		JPanel settingsPanel = new JPanel(new BorderLayout());
		engineModeSubPanel = new JPanel(new CardLayout());

		addEngineModeRadioButtons(settingsPanel);

		settingsPanel.add(engineModeSubPanel, BorderLayout.CENTER);

		addCompositeEngineComponents();
		addManipulateEngineComponents();

		restoreGui();

		add(settingsPanel, BorderLayout.CENTER);
	}

	private void addEngineModeRadioButtons(JPanel settingsPanel) {
		EngineMode[] modes = EngineMode.values();
		JPanel engineModePanel = new JPanel();

		engineModes = new EngineModeRadioButton[modes.length];
		ButtonGroup group = new ButtonGroup();

		int index = 0;
		for (EngineMode engineMode : modes) {
			EngineModeRadioButton rb = new EngineModeRadioButton(engineMode);
			rb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateEngineWidgets();
				}
			});
			group.add(rb);
			engineModes[index++] = rb;
			engineModePanel.add(rb);
		}

		settingsPanel.add(engineModePanel, BorderLayout.NORTH);
	}

	private void addCompositeEngineComponents() {
		JPanel compositePanel = new JPanel();
		compositePanel.add(getReversedImageOrder());

		engineModeSubPanel.add(compositePanel, EngineMode.composite.name());
	}

	private void addManipulateEngineComponents() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Coming soon..."));

		engineModeSubPanel.add(panel, EngineMode.manipulate.name());
	}

	private void updateEngineWidgets() {
		EngineMode engineMode = getSelectedEngineRB().getEngineMode();
		CardLayout cardLayout = (CardLayout) engineModeSubPanel.getLayout();
		cardLayout.show(engineModeSubPanel, engineMode.name());
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");

		save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				save();
				listener.saveSettings();
			}
		});

		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				restoreGui();
				listener.cancelSettings();
			}
		});

		buttonPanel.add(save);
		buttonPanel.add(cancel);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void save() {
		Manager.get().setEngineMode(getSelectedEngineRB().getEngineMode());
	}

	private EngineModeRadioButton getSelectedEngineRB() {
		for (EngineModeRadioButton engineModeRadioButton : engineModes) {
			if (engineModeRadioButton.isSelected()) {
				return engineModeRadioButton;
			}
		}
		throw new RuntimeException("Operation failure; a engine mode should always be selected");
	}

	private void restoreGui() {
		Manager manager = Manager.get();

		EngineMode selectedEngineMode = manager.getEngineMode();

		for (EngineModeRadioButton engineModeRadioButton : engineModes) {
			if (selectedEngineMode == engineModeRadioButton.getEngineMode()) {
				engineModeRadioButton.setSelected(true);
				break;
			}
		}

		reversedImageOrder.setSelected(manager.isLeftRightReversed());

		updateEngineWidgets();
	}

	private JCheckBox getReversedImageOrder() {
		reversedImageOrder = new JCheckBox("Reverse left/right");
		reversedImageOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Manager.get().setLeftRightReversed(reversedImageOrder.isSelected());
			}
		});
		return reversedImageOrder;
	}

	private class EngineModeRadioButton extends JRadioButton {

		private final EngineMode engineMode;

		EngineModeRadioButton(EngineMode engineMode) {
			super(engineMode.getText());
			this.engineMode = engineMode;
		}

		public EngineMode getEngineMode() {
			return engineMode;
		}
	}
}
