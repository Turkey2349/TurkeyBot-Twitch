package com.Turkey.TurkeyBot.chat;

import com.Turkey.TurkeyBot.TurkeyBot;
import com.Turkey.TurkeyBot.gui.ConsoleTab;
import com.Turkey.TurkeyBot.gui.ConsoleTab.Level;

public class AutoAnnouncement implements Runnable
{

	public static boolean run = false;
	private int delay = 0;
	private Thread thread;

	public AutoAnnouncement()
	{
		try
		{
			delay = Integer.parseInt(TurkeyBot.bot.getProfile().settings.getSetting("AnnounceDelay"));
		} catch(NumberFormatException e)
		{
			ConsoleTab.output(Level.Error, "The Announcement time is not set as a integer!");
			return;
		}
		initAutoAnnouncemer();
	}

	public void initAutoAnnouncemer()
	{
		run = true;
		if(thread == null || !thread.isAlive())
		{
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Starts the thread the controls the Auto Announcement
	 */
	@Override
	public void run()
	{
		while(run)
		{
			try
			{
				synchronized(this)
				{
					this.wait(delay * 1000);
				}
			} catch(InterruptedException e)
			{
				e.printStackTrace();
				this.stop();
			}
			makeAnnouncement();
			try
			{
				delay = Integer.parseInt(TurkeyBot.bot.getProfile().settings.getSetting("AnnounceDelay"));
			} catch(NumberFormatException e)
			{
				ConsoleTab.output(Level.Error, "The Announcement time is not set as a integer!");
				return;
			}
		}
		try
		{
			thread.interrupt();
			thread.join();
		} catch(InterruptedException e)
		{
		}
	}

	private void makeAnnouncement()
	{
		String msg = TurkeyBot.bot.getProfile().announceFile.getRandomAnnouncement();
		if(msg != "") TurkeyBot.bot.sendMessage(msg);
	}

	public void stop()
	{
		run = false;
	}

	public boolean isRunning()
	{
		return run;
	}
}
