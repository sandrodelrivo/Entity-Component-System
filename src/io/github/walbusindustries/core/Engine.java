package io.github.walbusindustries.core;

import java.util.Vector;

public class Engine {

	private Vector<EntitySystem> entitySystems;

	int eCount = 0;

	public Engine() {

	}

	public int create() {

		int e = eCount;
		eCount++;

		return e;

	}

	public void addSystem(EntitySystem es) {

		entitySystems.add(es);

	}

}
