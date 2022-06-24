package br.com.zenix.lobby.inventories;

import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */

public class InventoryManager extends Management {

	private PreferencesInventory preferencesInventory;
	private CrateSelectInventory crateSelectInventory;

	public InventoryManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		this.preferencesInventory = new PreferencesInventory();
		this.crateSelectInventory = new CrateSelectInventory();

		return preferencesInventory != null;
	}

	public CrateSelectInventory getCrateSelectInventory() {
		return crateSelectInventory;
	}

	public PreferencesInventory getPreferencesInventory() {
		return preferencesInventory;
	}

}
