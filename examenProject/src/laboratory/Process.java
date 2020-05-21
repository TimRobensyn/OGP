package laboratory;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration of recipe processes.
 *   In its current definition, the class only distinguishes between
 *   the processes add, heat, cool and mix.
 * 
 * @author  Tim Lauwers, Tim Robensyn, Robbe Van BierVliet
 * @version 1.0
 * 
 * @note    We make this enumeration class package available.
 *
 */
@Value
enum Process {
	add, heat, cool, mix;
}
