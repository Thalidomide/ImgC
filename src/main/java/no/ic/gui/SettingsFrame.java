package gui;

import java.awt.HeadlessException;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import engine.EngineMode;
import util.Manager;

/**
 * @author Olav Jensen
 * @since 11.apr.2010
 */
public class SettingsFrame extends JFrame {

	private EngineModeRadioButton[] engineModes;

	public SettingsFrame() throws HeadlessException {
		setSize(640, 480);
		setLocation(0, 0);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		getContentPane().add(getPanel());
	}

	private JPanel getPanel() {
		JPanel panel = new JPanel();

		EngineMode[] modes = EngineMode.values();
		EngineMode selected = Manager.get().getEngineMode();

		engineModes = new EngineModeRadioButton[modes.length];
		ButtonGroup group = new ButtonGroup();

		int index = 0;
		for (EngineMode engineMode : modes) {
			EngineModeRadioButton rb = new EngineModeRadioButton(engineMode);
			if (selected == engineMode) {
				rb.setSelected(true);
			}
			group.add(rb);
			engineModes[index++] = rb;
			panel.add(rb);
		}

		return panel;
	}
}

class EngineModeRadioButton extends JRadioButton {

	private final EngineMode engineMode;

	EngineModeRadioButton(EngineMode engineMode) {
		super(engineMode.getText());
		this.engineMode = engineMode;
	}

	public EngineMode getEngineMode() {
		return engineMode;
	}
}
