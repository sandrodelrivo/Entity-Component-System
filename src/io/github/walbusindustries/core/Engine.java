package io.github.walbusindustries.core;

import java.util.Vector;

/**
 * @author WalbusIndustries
 * @version 0.1
 * @since 2016-03-10
 */
public class Engine {

	// Vector containing all of the entity systems in the project.
	// All entity systems that the program will run will need to be added to
	// this list.
	private Vector<EntitySystem> entitySystems;

	int eCount = 0;

	/**
	 * Method to access the entity systems of the engine.
	 * 
	 * @return The vector containing all of the entity systems.
	 */
	public Vector<EntitySystem> getEntitySystems() {

		return entitySystems;
	}

	/**
	 * Creates entity
	 * <p>
	 * A unique id is giving to the entity (an entity being an id represented by
	 * an integer). This allows the system to manage the entity. Entity
	 * assignments start at 0 and increment by one after every assignment.
	 * 
	 * @return A unique id.
	 */
	public int create() {

		int e = eCount;
		eCount++;

		return e;

	}

	/**
	 * Add an entity system to the engine
	 * <p>
	 * This will add the entity system to the engine.
	 * 
	 * @param es
	 *            - Entity System
	 */
	public void addSystem(EntitySystem es) {

		entitySystems.add(es);

	}

	/**
	 * Updates all of the entity systems.
	 * <p>
	 * This engine is designed to be used within any game engine so this update
	 * method must be called during whenever the game updates its logic.
	 */
	public void update() {

	}

}
