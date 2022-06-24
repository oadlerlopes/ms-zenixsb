package br.com.zenix.lobby.command;

import org.bukkit.command.CommandSender;

import br.com.zenix.lobby.custom.BukkitCommand;

public class TestCommand extends BukkitCommand {

	public TestCommand() {
		super("test");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {

		return false;
	}

}
