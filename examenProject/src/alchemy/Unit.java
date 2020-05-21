package alchemy;

import be.kuleuven.cs.som.annotate.*;

public enum Unit {
	DROP_LIQUID(1,1,State.LIQUID,false),
	SPOON_LIQUID(8,2,State.LIQUID,true),
	VIAL_LIQUID(5,3,State.LIQUID,true),
	BOTTLE_LIQUID(3,4,State.LIQUID,true),
	JUG_LIQUID(7,5,State.LIQUID,true),
	BARREL_LIQUID(12,6,State.LIQUID,true),
	STOREROOM_LIQUID(5,7,State.LIQUID,false),
	
	PINCH_POWDER(1,1,State.POWDER,false),
	SPOON_POWDER(6,2,State.POWDER,true),
	SACHET_POWDER(7,3,State.POWDER,true),
	BOX_POWDER(6,4,State.POWDER,true),
	SACK_POWDER(3,5,State.POWDER,true),
	CHEST_POWDER(10,6,State.POWDER,true),
	STOREROOM_POWDER(5,7,State.POWDER,false);
	
	/**
	 * Initialize this unit with the given capacity, index and state.
	 * 
	 * @param	capacity
	 * 			The capacity to be given to the new unit.
	 * @param	index
	 * 			The index to be given to the new unit.
	 * @param	state
	 * 			The state to be given to the new unit.
	 */
	@Raw
	private Unit(int capacity, int index, State state, boolean isContainer) {
		this.capacity = capacity;
		this.index = index;
		this.state = state;
		this.isContainer = isContainer;
	}
	
	/**
	 * Return the capacity of this unit.
	 */
	@Basic @Immutable
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * A variable containing this unit's capacity.
	 */
	private int capacity;
	
	/**
	 * Return the index of this unit.
	 */
	@Basic @Immutable
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Check whether the given index is valid or not.
	 * 
	 * @param  index
	 * 		   The index to check.
	 * @return True if and only if the greater than zero and not above the number of values
	 *         in this enumeration class.
	 */
	public static boolean isValidIndex(int index) {
		return (index>0); //TODO deze checker fixen
	}
	
	/**
	 * A variable containing this unit's index relative to the other units with the same state.
	 */
	private int index;
	
	/**
	 * Return the state of this unit.
	 */
	@Basic @Immutable
	public State getState() {
		return this.state;
	}
	
	/**
	 * A variable containing this unit's state.
	 */
	private State state;
	
	/**
	 * Check if this unit can be a container.
	 */
	@Basic @Immutable
	public boolean isContainer() {
		return this.isContainer;
	}
	
	/**
	 * A variable containing the boolean which decides whether this unit can be a container.
	 */
	private boolean isContainer;
	
	/********************************************************************************
	 * METHODS
	 ********************************************************************************/
	
	/**
	 * Return the capacity of this unit measured in the smallest unit of the same state.
	 */
	@Immutable
	public int getAbsoluteCapacity() {
		int result = 1;
		for (Unit unit: Unit.values()) {
			if ((unit.getState()==this.getState())
					&&(unit.getIndex()<=this.getIndex())){
				result *= this.getCapacity();
			}
		}
		return result;
	}
	/**
	 * Return the smallest container of the given state that can hold the given quantity.
	 */
	public static Unit getContainer(State state, int quantity) {
		Unit result = getBiggestContainer(state);		
		for (Unit unit: Unit.values()) {
			if ((((unit.getState()==state)
					&&(unit.isContainer())
					&&(unit.getIndex()<result.getIndex()))
					&&(unit.getAbsoluteCapacity()>=quantity))){
				result = unit;
			}
		}
		return result;
	}
	
	/**
	 * Return the biggest container of the given state.
	 */
	private static Unit getBiggestContainer(State state) {
		Unit result = null; //TODO Deze initialisatie bespreken.
		for (Unit unit: Unit.values()) {
			if (((unit.getState()==state)
					&&(unit.isContainer()))
					&&(unit.getAbsoluteCapacity()<result.getAbsoluteCapacity())){
				result = unit;
			}
		}
		return result;
	}
	
	/**
	 * Return the ratio between two given states, assuming their second unit as base.
	 * 
	 * @return	spoonCapacityFirstState/spoonCapacitySecondState
	 */
	public static double getRatio(State firstState, State secondState) {
		int firstCapacity = 1;
		int secondCapacity = 1;
		for (Unit unit: Unit.values()) {
			if (unit.getIndex()==2) {
				if (unit.getState()==firstState) firstCapacity = unit.getCapacity();
				if (unit.getState()==secondState) secondCapacity = unit.getCapacity();
			}
		}
		return firstCapacity/secondCapacity;
	}

}
