package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

import olj.ic.engine.EngineMode;
import olj.ic.engine.EngineSettings;
import olj.ic.gui.components.Button;
import olj.ic.gui.components.CheckBox;
import olj.ic.gui.components.DoubleTextField;
import olj.ic.gui.components.Label;
import olj.ic.gui.components.Panel;
import olj.ic.gui.components.RadioButton;
import olj.ic.util.Constants;
import olj.ic.util.Manager;
import olj.ic.util.Util;

/**
 * @author Olav Jensen
 * @since Apr 11, 2010
 */
public class SettingsPanel extends Panel {

	private final SettingsListener listener;
	private Panel engineModeSubPanel;
	private EngineSettings engineSettings;

	/* Common settings */
	private EngineModeRadioButton[] engineModes;
	private JComboBox imageParts;
	private JComboBox threads;
	private DoubleTextField scaleX;
	private DoubleTextField scaleY;

	/* Composite settings */
	private CheckBox reversedImageOrder;
	private final Color subEngineBackground = Constants.BACKGROUND_INPUT;

	public SettingsPanel(SettingsListener listener) {
		super(new BorderLayout());

		this.listener = listener;
		engineSettings = Manager.get().getEngineSettings();

		addSettingsComponents();
		addButtons();
	}

	private void addSettingsComponents() {
		Panel settingsContent = new Panel(new BorderLayout());

		Panel engineCommonPanel = new Panel();
		engineModeSubPanel = new Panel(new CardLayout());
		engineModeSubPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		addEngineModePrimaryComponents(engineCommonPanel);

		settingsContent.add(engineCommonPanel, BorderLayout.NORTH);
		settingsContent.add(engineModeSubPanel, BorderLayout.CENTER);

		addCompositeEngineComponents();
		addRestructureEngineComponents();
		addManipulatorEngineComponents();

		restoreGui();

		add(settingsContent, BorderLayout.CENTER);
	}

	private void addEngineModePrimaryComponents(Panel settingsPanel) {
		Panel engineModePanel = new Panel();
		engineModePanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		engineModePanel.add(addEngineModes(), constraints);
		constraints.gridx = 1;
		engineModePanel.add(addImageParts(), constraints);
		constraints.gridx = 2;
		engineModePanel.add(addThreads(), constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		engineModePanel.add(getScalingContent(), constraints);

		settingsPanel.add(engineModePanel, BorderLayout.NORTH);
	}

	private Panel addEngineModes() {
		Panel panel = new Panel();
		EngineMode[] modes = EngineMode.values();
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
			panel.add(rb);
		}

		return panel;
	}

	private Panel addImageParts() {
		Panel panel = new Panel();
		imageParts = new JComboBox(Util.getInterval(Constants.MIN_IMAGE_PARTS, Constants.MAX_IMAGE_PARTS));
		panel.add(new Label("Image parts:"));
		panel.add(imageParts);
		return panel;
	}

	private Panel addThreads() {
		Panel panel = new Panel();
		threads = new JComboBox(Util.getInterval(Constants.MIN_WORKING_THREADS, Constants.MAX_WORKING_THREADS));
		panel.add(new Label("Number of threads:"));
		panel.add(threads);
		return panel;
	}

	private Panel getScalingContent() {
		scaleX = getDoubleField();
		scaleY = getDoubleField();

		Panel panel = new Panel();
		panel.add(new Label("Image scaling"));
		panel.add(new Label("Width scale:"));
		panel.add(scaleX);
		panel.add(new Label("Height scale:"));
		panel.add(scaleY);

		return panel;
	}

	private DoubleTextField getDoubleField() {
		DoubleTextField field = new DoubleTextField();
		field.setOptional(false);
		field.setPreferredSize(new Dimension(50, (int) field.getPreferredSize().getHeight()));

		return field;
	}

	private void addCompositeEngineComponents() {
		Panel compositePanel = getSubEnginePanel();
		compositePanel.add(getReversedImageOrder());

		engineModeSubPanel.add(compositePanel, EngineMode.composite.name());
	}

	private void addRestructureEngineComponents() {
		Panel panel = getSubEnginePanel();
		panel.add(new Label("Currently there are no specific settings for this mode."));

		engineModeSubPanel.add(panel, EngineMode.restructure.name());
	}

    private void addManipulatorEngineComponents() {
		Panel panel = getSubEnginePanel();
		panel.add(new Label("Currently there are no specific settings for this mode."));

		engineModeSubPanel.add(panel, EngineMode.manipulate.name());
	}

	private Panel getSubEnginePanel() {
		Panel panel = new Panel();
		panel.setBackground(subEngineBackground);
		return panel;
	}

	private void updateEngineWidgets() {
		EngineMode engineMode = getSelectedEngineRB().getEngineMode();
		CardLayout cardLayout = (CardLayout) engineModeSubPanel.getLayout();
		cardLayout.show(engineModeSubPanel, engineMode.name());
	}

	private void addButtons() {
		Panel buttonPanel = new Panel();
		Button save = new Button("Save");
		Button cancel = new Button("Cancel");

		save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (isValidInput()) {
					save();
					listener.saveSettings();
				}
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

	private boolean isValidInput() {
		return scaleX.isValidDouble() && scaleY.isValidDouble();
	}

	private void save() {
		engineSettings.setEngineMode(getSelectedEngineRB().getEngineMode());
		engineSettings.setImageParts(getImageParts());
		engineSettings.setThreads(getThreads());
		engineSettings.setScaleX(scaleX.getDouble());
		engineSettings.setScaleY(scaleY.getDouble());
	}

	private int getImageParts() {
		return (Integer) imageParts.getSelectedItem();
	}

	private int getThreads() {
		return (Integer) threads.getSelectedItem();
	}

	private EngineModeRadioButton getSelectedEngineRB() {
		for (EngineModeRadioButton engineModeRadioButton : engineModes) {
			if (engineModeRadioButton.isSelected()) {
				return engineModeRadioButton;
			}
		}
		throw new RuntimeException("Operation failure; a ic.engine mode should always be selected");
	}

	private void restoreGui() {
		Manager manager = Manager.get();

		EngineMode selectedEngineMode = manager.getEngineSettings().getEngineMode();

		for (EngineModeRadioButton engineModeRadioButton : engineModes) {
			if (selectedEngineMode == engineModeRadioButton.getEngineMode()) {
				engineModeRadioButton.setSelected(true);
				break;
			}
		}

		reversedImageOrder.setSelected(manager.getEngineSettings().isLeftRightReversed());

		imageParts.setSelectedItem(engineSettings.getImageParts());
		threads.setSelectedItem(engineSettings.getThreads());

		scaleX.setDouble(Manager.get().getEngineSettings().getScaleX());
		scaleY.setDouble(Manager.get().getEngineSettings().getScaleY());

		updateEngineWidgets();
	}

	private CheckBox getReversedImageOrder() {
		reversedImageOrder = new CheckBox("Reverse image orders");
		reversedImageOrder.setBackground(subEngineBackground);
		reversedImageOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Manager.get().getEngineSettings().setLeftRightReversed(reversedImageOrder.isSelected());
			}
		});
		return reversedImageOrder;
	}

	private class EngineModeRadioButton extends RadioButton {

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
