package io.github.walbusindustries.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author WalbusIndustries
 * @version 0.1
 * @since 2016-03-10
 */
public abstract class EntitySystem {

	/**
	 * Array to hold all the the required components ("acting types") that each
	 * entity is required to have.
	 */
	protected ArrayList<Class<? extends Component>> actingTypes = new ArrayList<>();

	protected ArrayList<Integer> entities = new ArrayList<>();

	protected

	HashMap<Class<?>, HashMap<Integer, ? extends Component>> componentMap;

	public void addEntity(int e) {
		entities.add(e);
	}

	public ArrayList<Class<? extends Component>> getActingTypes() {
		return actingTypes;

	}

	public abstract void process();

}
