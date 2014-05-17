package worms.model;

import java.util.*;

/**
 * A class of teams, worms can be part of a team, if only one team remains on the map, 
 * this is the winning team.
 * @author Marnix Michiel Denys and Jef De Bie
 *
 */

public class Team {
	
	/**
	 * Initializes a new team with a certain name
	 * @param Name
	 */
	
	public Team(World world, String Name){
		this.setName(Name);
		this.setWorld(world);
	}
	
	
	private World world;
	private String Name;
	
	/**
	 * sets the name of a team, if the name is valid.
	 * @param name
	 * @throws IllegalArgumentException
	 */
	
	public void setName(String name) throws IllegalArgumentException{
		if (this.isValidName(name)) {
			this.Name = name;
		}
		else {
			throw new IllegalArgumentException("Name is invalid.");
		}
	}
	/**
	 * 
	 * @param world
	 */
	
	private void setWorld(World world){
		this.world = world;
		world.addTeamToWorld(this);
	}
	
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * checks if a name is valid
	 * @param name
	 * @return boolean isValid
	 */
	
	private boolean isValidName(String name) {
		return (name.length() > 2) && (Character.isUpperCase(name.charAt(0))) && name.matches("[a-zA-Z'\" ]*");
	}
		
	public String getName(){
		return this.Name;
	}
	
	/**
	 * ad a worm to a certain team
	 * @param worm
	 */
	
	
	public void addWormToTeam(Worm worm){
		this.WormsInTeam.add(worm);
		worm.setTeam(this.getName());
	}
	
	private Collection<Worm> WormsInTeam;

}
