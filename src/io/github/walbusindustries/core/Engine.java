package io.github.walbusindustries.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author WalbusIndustries
 * @version 0.2
 * @since 2016-03-10
 */
public class Engine {

	static List<Integer> allEntities;

	private static HashMap<Class<?>, HashMap<Integer, ? extends Component>> componentMap;

	// Vector containing all of the entity systems in the project.
	// All entity systems that the program will run will need to be added to
	// this list.
	private static ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();

	static int eCount = 0;

	public Engine() {
		System.out.println("--Initializing Engine Class--");
		allEntities = new ArrayList<>();
		componentMap = new HashMap<>();
	}

	/**
	 * Method that tells you whether or not an entity possesses a certain
	 * component.
	 * <p>
	 * Pass in a component and an entity and it will access the component map
	 * and return a boolean. True if it is possessed. False if it is does not.
	 * 
	 * @param entity
	 * @param componentType
	 * @return Boolean. True if the entity has the component. False if not
	 */
	public static <T extends Component> Boolean hasComponent(Integer entity, Class<T> componentType) {

		// Creates a hashmap of all entity-component pairs within the component
		HashMap<Integer, ? extends Component> stored = componentMap.get(componentType);
		if (stored.get(entity) != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Method that returns all entities with the specified component
	 * 
	 * @param componentType
	 *            (ex. "Position.class")
	 * @return Set containing all entities(integers) with the component
	 */
	public static <T extends Component> Set<Integer> getEntitiesWithType(Class<T> componentType) {

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
	 * @param component
	 *            - Any subclass of component
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Component> void addComponent(int entity, T component) {

		// Creates a hash map of what is in the that component's storage
		// This hash map is an integer and its component
		// This hash map will be put into the main component store under
		// the component type (generic class)
		HashMap<Integer, ? extends Component> stored = componentMap.get(component.getClass());

		// if the stored is null - ie. no entities already use this component.
		if (stored == null) {

			// Initialize the Hashmap
			stored = new HashMap<>();

			// Add this new hashmap to the component map so that the entity can
			// be placed
			componentMap.put(component.getClass(), stored);

		}

		// Cast stored to correct hashmap and put the entity and component
		((HashMap<Integer, T>) stored).put(entity, component);

		addEntityToSystems(entity);

		System.out.println(component.getClass() + " Component added to entity - " + entity);

	}

	/**
	 * Method to get the component of the specified entity
	 * 
	 * @param entity
	 * @param componentType
	 *            - The type of component you want to access ("S.class")
	 * @return T - The instance of the component class that is paired with the
	 *         entity. ex. engine.getComponent(entity,
	 *         ExampleComponent.class).exampleVar = 5;
	 */
	public static <T extends Component> T getComponent(int entity, Class<T> componentType) {

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

	public static ArrayList<Component> getAllComponents(int e) {

		// Get All Entity-Component pairs from value list
		Collection<HashMap<Integer, ? extends Component>> stored = componentMap.values();

		// Create an array list to hold all the entities with their components
		ArrayList<HashMap<Integer, ? extends Component>> allPairs = new ArrayList<>();
		allPairs.addAll(stored);

		System.out.println("Amount of Entity-Component Pairs: " + allPairs.size());
		System.out.println("Pairs As Follows: ");
		for (int i = 0; i < allPairs.size(); i++) {
			System.out.println(allPairs.get(i));
		}

		ArrayList<Component> components = new ArrayList<>();

		for (int i = 0; i < allPairs.size(); i++) {

			if (allPairs.get(i).containsKey(e)) {
				components.add(allPairs.get(i).get(e));
			}

			System.out.println("Components of Entity: " + e + " as follows " + components);
			System.out.println(" ");

		}

		return components;

	}

	/**
	 * Method to access the entity systems of the engine.
	 * 
	 * @return The vector containing all of the entity systems.
	 */
	private static ArrayList<EntitySystem> getEntitySystems() {

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
	public static int create() {

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
	public static void addSystem(EntitySystem es) {

		entitySystems.add(es);
	}

	/**
	 * Runs every time a new component is added to an entity. The function
	 * determines what systems the component belongs to and adds the entity to
	 * the system's internal component list.
	 * 
	 * Also runs for every entity every time a new entity system is created.
	 * 
	 * @param e
	 *            - Entity
	 */
	private static void addEntityToSystems(int e) {

		ArrayList<Component> components = getAllComponents(e);
		ArrayList<Class<? extends Component>> componentTypes = new ArrayList<>();

		for (Component comp : components) {

			componentTypes.add(comp.getClass());

		}

		for (int i = 0; i < entitySystems.size(); i++) {

			System.out.println("Entity Component Types: " + componentTypes);
			System.out.println("System acting types: " + entitySystems.get(i).getActingTypes());

			if (componentTypes.equals(entitySystems.get(i).getActingTypes())) {

				System.out.println("Added Entity: " + e + " to Entity System: " + entitySystems.get(i));
				entitySystems.get(i).addEntity(e);

			}

		}

	}

	/**
	 * Updates all of the entity systems.
	 * <p>
	 * This engine is designed to be used within any game engine so this update
	 * method must be called during whenever the game updates its logic.
	 */
	public static void update() {

		for (int i = 0; i < entitySystems.size(); i++) {
			entitySystems.get(i).process();
		}

	}

}
