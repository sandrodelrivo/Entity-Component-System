package io.github.walbusindustries.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author WalbusIndustries
 * @version 0.1
 * @since 2016-03-10
 */
public class Engine {

	List<Integer> allEntities;

	/**
	 * 
	 */
	HashMap<Class<?>, HashMap<Integer, ? extends Component>> componentMap;

	// Vector containing all of the entity systems in the project.
	// All entity systems that the program will run will need to be added to
	// this list.
	private ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();

	int eCount = 0;

	public Engine() {
		allEntities = new ArrayList<>();
		componentMap = new HashMap<>();
	}

	/**
	 * Method that returns all entities with the specified component
	 * 
	 * @param componentType (ex. "Position.class")
	 * @return Set containing all entities(integers) with the component
	 */
	public <T extends Component> Set<Integer> getEntitiesWithType(Class<T> componentType) {
		
		// Creates a hashmap of the entities under that component type.
		HashMap<Integer, ? extends Component> stored = componentMap.get(componentType);
		
		// If there are no entities the function returns a blank hashset
		if (stored == null) {
			return new HashSet<Integer>();
		}
	
		return stored.keySet();
		
	}
	
	/**
	 * Method to add a component to an entity
	 * <p>
	 * The method takes in an entity and a component and adds the component to
	 * the entity in the engine's componentMap
	 * 
	 * @param entity
	 * @param component - Any subclass of component
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> void addComponent(int entity, T component) {

		// Creates a hash map of what is in the that component's storage
		// This hash map is an integer and its component
		// This hash map will be put into the main component store under
		// the component type (generic class)
		HashMap<Integer, ? extends Component> stored = componentMap.get(component.getClass());

		// if the stored is null - ie. no entities already use this component.
		if (stored == null) {

			// Create a hashmap of an integer and a type (the component)
			stored = new HashMap<Integer, T>();

			// Add this new hashmap to the component map so that the entity can
			// be placed
			componentMap.put(component.getClass(), stored);

		}

		// Cast stored to correct hashmap and put the entity and component
		((HashMap<Integer, T>)stored).put(entity, component);

		System.out.println(component.getClass() + " Component added to entity - " + entity);

	}

	/**
	 * Method to get the component of the specified entity
	 * 
	 * @param entity
	 * @param componentType - The type of component you want to access ("S.class")
	 * @return T - A type that extends component - ie. the component of the entity
	 */
	public <T extends Component> T getComponent(int entity, Class<T> componentType) {

		// A hashmap that contains the the hashmap under the componentType
		HashMap<Integer, ? extends Component> stored = componentMap.get(componentType);

		// If there is no entity-component map stored under that component
		if (stored == null) {
			throw new IllegalArgumentException("There are no entities with a Component of class: " + componentType);
		}

		// Creates a type that extends component that has the component stored
		// under the entity
		T comp = componentType.cast(stored.get(entity));

		// If comp is null - ie. there is no entity with that type
		if (comp == null)
			throw new IllegalArgumentException(
					"Entity - " + entity + " does not have a component of this type \n   missing: " + componentType);

		return comp;

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
