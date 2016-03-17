package io.github.walbusindustries.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author WalbusIndustries
 * @version 0.1
 * @since 2016-03-10
 */
public class Engine {

	List<Integer> allEntities;
	HashMap<Class<?>, HashMap<Integer, ? extends Component>> componentMap;

	// Vector containing all of the entity systems in the project.
	// All entity systems that the program will run will need to be added to
	// this list.
	private ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();

	int eCount = 0;

	public Engine() {
		allEntities = new ArrayList<Integer>();
		// componentMap = new HashMap<Integer, ArrayList<Component>>();
	}

	public <T extends Component> void addComponent(int entity, T component) {

		// Creates a hashmap of
		// HashMap<Integer, ? extends Component> stored = componentMap
		// .get(component.getClass());

		if (componentMap.get(component.getClass()).get(entity) == null) {
			
			componentMap.get(component.getClass()).put(entity, );
			
		}

		/*
		 * if (componentMap.get(entity) == null) componentMap.put(entity, new
		 * ArrayList<Component>());
		 * 
		 * componentMap.get(entity).add(component);
		 */

	}

	public Component getComponent(int entity, Component component) {

		/*
		 * Component result = componentMap.get(entity).;
		 * 
		 * return result;
		 */

	}

	/**
	 * Method to access the entity systems of the engine.
	 * 
	 * @return The vector containing all of the entity systems.
	 */
	private ArrayList<EntitySystem> getEntitySystems() {

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

		allEntities.add(e);

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

		if (entitySystems.isEmpty() == false) {

			for (int i = 0; i < entitySystems.size(); i++) {

				entitySystems.get(i).process();

			}
		}

	}

}
