package olj.ic.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

import olj.ic.engine.EngineMode;
import olj.ic.engine.EngineSettings;
import olj.ic.gui.components.Button;
import olj.ic.gui.components.CheckBox;
import olj.ic.gui.components.Label;
import olj.ic.gui.components.Panel;
import olj.ic.gui.components.RadioButton;
import olj.ic.util.Constants;
import olj.ic.util.Manager;

/**
 * @author Olav Jensen
 * @since Apr 11, 2010
 */
public class SettingsPanel extends Panel {

	private final SettingsListener listener;
	private Panel engineModeSubPanel;
	private EngineSettings engineSettings;

	private EngineModeRadioButton[] engineModes;
	private JComboBox imageParts;

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
		Panel settingsPanel = new Panel(new BorderLayout());
		engineModeSubPanel = new Panel(new CardLayout());
		engineModeSubPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		addEngineModePrimaryComponents(settingsPanel);

		settingsPanel.add(engineModeSubPanel, BorderLayout.CENTER);

		addCompositeEngineComponents();
		addManipulateEngineComponents();

		restoreGui();

		add(settingsPanel, BorderLayout.CENTER);
	}

	private void addEngineModePrimaryComponents(Panel settingsPanel) {
		EngineMode[] modes = EngineMode.values();
		Panel engineModePanel = new Panel();

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

		Label partsLabel = new Label("Image parts:");

		Vector<Integer> partNumbers = new Vector<Integer>();

		for (int i = Constants.MIN_IMAGE_PARTS; i <= Constants.MAX_IMAGE_PARTS; i++) {
			partNumbers.add(i);
		}

		imageParts = new JComboBox(partNumbers);

		engineModePanel.add(partsLabel);
		engineModePanel.add(imageParts);

		settingsPanel.add(engineModePanel, BorderLayout.NORTH);
	}

	private void addCompositeEngineComponents() {
		Panel compositePanel = getSubEnginePanel();
		compositePanel.add(getReversedImageOrder());

		engineModeSubPanel.add(compositePanel, EngineMode.composite.name());
	}

	private void addManipulateEngineComponents() {
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
		engineSettings.setEngineMode(getSelectedEngineRB().getEngineMode());
		engineSettings.setImageParts(getImageParts());
	}

	private int getImageParts() {
		return (Integer) imageParts.getSelectedItem();
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
