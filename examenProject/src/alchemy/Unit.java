package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

/**
 * Enumeration class of units as to be used in alchemy.
 * 
 * @invar   Each unit has a valid capacity.
 *          | isValidCapacity(getCapacity())
 * @invar   Each unit has a valid index according to its state.
 * 			| isValidIndex(getIndex(),getState())
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van Biervliet
 * @version 1.0
 */

@Value
public enum Unit {
	DROP_LIQUID(1,1,State.LIQUID,false),
	SPOON_LIQUID(8,2,State.LIQUID,true),
	VIAL_LIQUID(5,2,State.LIQUID,true),
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
	 * Initialize this unit with the given capacity, index, state and flag indicating whether this unit
	 * can be used as a container.
	 * 
	 * @param	capacity
	 * 			The capacity to be given to the new unit.
	 * @param	index
	 * 			The index to be given to the new unit.
	 * @param	state
	 * 			The state to be given to the new unit.
	 * @param   isContainer
	 * 			The flag to be given to the new unit.
	 * @post    The capacity of this unit is equal to the given capacity.
	 * 			| new.getCapacity() == capacity
	 * @post 	The index of this unit is equal to the given index.
	 * 			| new.getIndex() == index
	 * @post    The state of this unit is equal to the given state.
	 * 			| new.getState() == state
	 * @post    The flag indicating whether this unit can be used as a container is equal to the given flag.
	 * 			| new.isContainer() == isContainer
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
	 * Check whether the given capacity is valid.
	 * 
	 * @return True if and only if the given value is greater than or equal to zero and
	 * 		   not above the max value of a number.
	 * 		   | result == (capacity>=0 && capacity<=Long.MAX_VALUE)
	 */
	public boolean isValidCapacity(int capacity) {
		return (capacity>=0 && capacity<=Long.MAX_VALUE);
	}
	
	/**
	 * A variable containing this unit's capacity.
	 */
	private final int capacity;
	
	/**
	 * Return the index of this unit.
	 */
	@Basic @Immutable
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Check whether the given index is valid or not depending on the state of the unit.
	 * 
	 * @param  index
	 * 		   The index to check.
	 * @param  state
	 * 		   The state to check the index for.
	 * @return False if there are two units with the same state and the same given index.
	 * 		   | for some unit1, unit2 in Unit.values()
	 * 		   | if ( unit1.getState() == unit2.getState()
	 * 		   |   && unit1.getIndex() == unit2.getIndex()
	 *         |   && unit1.getIndex() == index )
	 * 		   |   then result == false
	 * 		   Otherwise, true if and only if the index is greater than zero and not above the number of values
	 *         in this enumeration class with the given state.
	 *         | count=0
	 *         | for each unit in Unit.values()
	 *         |   if (unit.getState()==state)
	 *         |      then count++
	 *         | result == (index>0 && index<=count)
	 */
	public static boolean isValidIndex(int index, State state) {
		int count = 0;
		ArrayList<Integer> usedIndexes = new ArrayList<Integer>();
		for (Unit unit: Unit.values()) {
			if (unit.getState()==state) {
				count++;
				// If we want to add the given index a second time, return false.
				if (usedIndexes.contains(unit.getIndex())
				   && unit.getIndex()==index)
					return false;
				else usedIndexes.add(unit.getIndex());
			}
		}
		return (index>0 && index<=count);
	}
	
	/**
	 * A variable containing this unit's index relative to the other units with the same state.
	 */
	private final int index;
	
	/**
	 * Return the state of this unit.
	 */
	@Basic @Immutable
	public State getState() {
		return this.state;
	}
	
	/**
	 * A variable containing this unit's state.
	 * 
	 * @note No checker is needed, values that are not mentioned in the state enumeration class are 
	 *       rejected anyway.
	 */
	private final State state;
	
	/**
	 * Check if this unit can be a container.
	 */
	@Basic @Immutable
	public boolean isContainer() {
		return this.isContainer;
	}
	
	/**
	 * A variable containing the boolean which decides whether this unit can be a container.
	 * 
	 * @note No checker is needed, because the only two values (true and false) are correct.
	 */
	private final boolean isContainer;
	
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
			if ( unit.getState()==this.getState()
		      && unit.getIndex()<=this.getIndex() ){
				result *= this.getCapacity();
			}
		}
		return result;
	}
	
	/**
	 * Return the smallest container of the given state that can hold the given quantity.
	 * 
	 * @param  state
	 * 		   The state of which the container should be.
	 * @param  quantity
	 * 	       The quantity we want to store as efficiently as possible, that is in the smallest container needed.
	 * @return From the collection of units that has the given state as its state, can be a container and
	 * 		   has an absolute capacity not below the given quantity, the unit is returned which has the smallest
	 * 		   index.
	 * 		   | result == such that 
	 *         | for each unit in Unit.values()
	 *         |   if (unit.getState()==state
	 *         |      && unit.isContainer()
	 *         |      && unit.getAbsoluteCapacity()>=quantity)
	 *         |     then result.getIndex() <= unit.getIndex()
	 */
	public static Unit getContainer(State state, int quantity) {
		Unit result = getBiggestContainer(state);		
		for (Unit unit: Unit.values()) {
			if ( unit.getState()==state
			  && unit.isContainer()
			  && unit.getIndex()<result.getIndex()
		      && unit.getAbsoluteCapacity()>=quantity ){
				result = unit;
			}
		}
		return result;
	}
	
	/**
	 * Return the biggest possible container of the given state.
	 * 
	 * @param  state
	 * 		   The state we want the biggest container of.
	 * @return From the collection of units that have the given state as its state and can be a container, the 
	 * 		   unit with the biggest index (and thus the biggest absolute capacity) is returned. If this collection
	 * 		   is empty, a non-effective unit is returned.
	 * 		   | result == such that 
	 *         | for each unit in Unit.values()
	 *         |   if (unit.getState()==state
	 *         |      && unit.isContainer())
	 *         |     then result.getIndex() >= unit.getIndex()
	 * @note   We use the invariant on the index that says that an index cannot be negative.
	 */
	public static Unit getBiggestContainer(State state) {
		Unit result = null;
		int highestIndex = -1;
		for (Unit unit: Unit.values()) {
			if ( unit.getState()==state
			  && unit.isContainer()
			  && unit.getIndex()>highestIndex ){
				result = unit;
				highestIndex = unit.getIndex();
			}
		}
		return result;
	}
	
	/**
	 * Return the ratio between two given states, assuming their second unit as base.
	 * 
	 * @param   firstState
	 * 			The first given state to calculate the ratio of.
	 * @param   secondState
	 * 			The second given state to calculate the ratio to.
	 * @return	Assuming the unit with index 2 of both states represents the same quantity in real-life 
	 * 			alchemy, the ratio between their capacities relative to their own smallest unit is returned.
	 * 			| unitFirstState is such that
	 *          |   ( unitFirstState.getState() == firstState
	 *          |   && unitFirstState.getIndex() == 2)
	 *          | unitSecondState is such that
	 *          |   ( unitSecondState.getState() == secondState
	 *          |   && unitSecondState.getIndex() == 2)
	 *          | result == unitFirstState.getAbsoluteCapacity()/unitSecondState.getAbsoluteCapacity()
	 */
	public static double getRatio(State firstState, State secondState) {
		final int base = 2;
		int firstCapacity = 1;
		int secondCapacity = 1;
		for (Unit unit: Unit.values()) {
			if (unit.getIndex()==base) {
				if (unit.getState()==firstState) firstCapacity = unit.getAbsoluteCapacity();
				if (unit.getState()==secondState) secondCapacity = unit.getAbsoluteCapacity();
			}
		}
		return firstCapacity/secondCapacity;
	}

	
	public static void main(String [ ] args) {
		boolean flag = isValidIndex(2,State.LIQUID);
		System.out.println(flag);
	}
}
