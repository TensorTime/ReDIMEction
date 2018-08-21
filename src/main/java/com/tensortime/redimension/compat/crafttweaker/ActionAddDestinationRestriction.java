package com.tensortime.redimension.compat.crafttweaker;

import com.tensortime.redimension.Redimension;

import crafttweaker.IAction;

public class ActionAddDestinationRestriction implements IAction {

	private int source;
	private int destination;

	public ActionAddDestinationRestriction(int source, int destination) {

		this.source = source;
		this.destination = destination;
	}

	@Override
	public void apply() {

		Redimension.SOURCE_MAP.put(this.source, this.destination);
	}

	@Override
	public String describe() {

		return String.format("Dimension %d has mandatory destination %d", this.source, this.destination);
	}
}
