package com.Turkey.TurkeyBot.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.Turkey.TurkeyBot.TurkeyBot;
import com.Turkey.TurkeyBot.chat.ModerateChat;
import com.Turkey.TurkeyBot.gui.ConsoleTab.Level;

public class ChatSettingsTab extends Tab implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private List<JComponent> components = new ArrayList<JComponent>();

	private JButton save;
	private JButton moderate;
	private String[] groups = { "Caps", "Emotes", "Symbols", "other" };
	private JPanel panel;

	public ChatSettingsTab()
	{

	}

	public void load()
	{
		if(TurkeyBot.bot.getProfile() == null)
			return;
		JLabel label;
		JTextArea text;

		save = new JButton("Save");
		save.setName("Save");
		save.setLocation((super.getWidth() / 2) - 50, super.getHeight() - 100);
		save.setSize(100, 25);
		save.addActionListener(this);
		components.add(save);
		super.add(save);

		moderate = new JButton(ModerateChat.Moderate ? "Stop Chat Moderation" : "Start Chat Moderation");
		moderate.setName("Moderate");
		moderate.setSize(500, 25);
		moderate.setLocation((super.getWidth() - moderate.getWidth()) / 2, super.getHeight() - 600);
		moderate.addActionListener(this);
		components.add(moderate);
		super.add(moderate);

		for(int i = 0; i < groups.length; i++)
		{
			String s = groups[i];

			panel = new JPanel();
			panel.setLayout(null);
			panel.setLocation(10, (i * 100) + 10);
			panel.setSize(400, 100);

			if(s.equalsIgnoreCase("other"))
			{
				label = new JLabel("Block Links");
				label.setLocation(0, 25);
				label.setSize(150, 25);
				components.add(label);
				panel.add(label);

				text = new JTextArea();
				text.setName("Block Links");
				text.setLocation(150, 25);
				text.setSize(60, 15);
				text.setText(TurkeyBot.bot.getProfile().chatSettings.getSetting("BlockLinks"));
				components.add(text);
				panel.add(text);

				label = new JLabel("Max Message Length");
				label.setLocation(0, 50);
				label.setSize(150, 25);
				components.add(label);
				panel.add(label);

				text = new JTextArea();
				text.setName("Max Message Length");
				text.setLocation(150, 50);
				text.setSize(60, 15);
				text.setText(TurkeyBot.bot.getProfile().chatSettings.getSetting("MaxMessageLength"));
				components.add(text);
				panel.add(text);

				label = new JLabel("Word Black List");
				label.setLocation(0, 75);
				label.setSize(150, 25);
				components.add(label);
				panel.add(label);

				text = new JTextArea();
				text.setName("Word Black List");
				text.setLocation(150, 75);
				text.setSize(300, 200);
				text.setText(TurkeyBot.bot.getProfile().chatSettings.getSetting("WordBlackList"));
				components.add(text);
				panel.add(text);
			}
			else
			{
				label = new JLabel("Minimum " + s);
				label.setLocation(0, 25);
				label.setSize(150, 25);
				components.add(label);
				panel.add(label);

				text = new JTextArea();
				text.setName("Minimum " + s);
				text.setLocation(150, 25);
				text.setSize(60, 15);
				text.setText(TurkeyBot.bot.getProfile().chatSettings.getSetting("Minimum" + s));
				components.add(text);
				panel.add(text);

				label = new JLabel("Max " + s);
				label.setLocation(0, 50);
				label.setSize(150, 25);
				components.add(label);
				panel.add(label);

				text = new JTextArea();
				text.setName("Max " + s);
				text.setLocation(150, 50);
				text.setSize(60, 15);
				text.setText(TurkeyBot.bot.getProfile().chatSettings.getSetting("Max" + s));
				components.add(text);
				panel.add(text);

				label = new JLabel("Max percent of " + s);
				label.setLocation(0, 75);
				label.setSize(150, 25);
				components.add(label);
				panel.add(label);

				text = new JTextArea();
				text.setName("Max percent of " + s);
				text.setLocation(150, 75);
				text.setSize(60, 15);
				text.setText(TurkeyBot.bot.getProfile().chatSettings.getSetting("Maxpercentof" + s));
				components.add(text);
				panel.add(text);
			}
			super.add(panel);
			components.add(panel);
		}

		super.setVisible(true);
	}

	public void unLoad()
	{
		if(TurkeyBot.bot.getProfile() == null)
			return;
		for(JComponent comp : components)
		{
			comp.setVisible(false);
			super.remove(comp);
		}
		
		components.clear();
		super.setVisible(false);
	}

	/**
	 * Saves settings of the bot from this tab
	 */
	public void saveSettings()
	{
		for(JComponent comp : components)
		{
			if(comp instanceof JTextArea)
			{
				TurkeyBot.bot.getProfile().chatSettings.setSetting(comp.getName().replace(" ", ""), ((JTextArea) comp).getText());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof JButton)
		{
			JButton button = (JButton) e.getSource();
			if(button.getName().equalsIgnoreCase("Save"))
				saveSettings();
			else if(button.getName().equalsIgnoreCase("Moderate"))
			{
				ModerateChat.Moderate = !ModerateChat.Moderate;
				ConsoleTab.output(Level.Info, ModerateChat.Moderate ? "TurkeyBot is now moderating the chat!" : "TurkeyBot is no longer moderating the chat!");
				Gui.reloadTab();
			}
		}
	}
}