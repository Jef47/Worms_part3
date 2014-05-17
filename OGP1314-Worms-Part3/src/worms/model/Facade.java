package worms.model;


import java.util.Collection;
import java.util.Random;

import worms.model.ModelException;

public class Facade implements IFacade {
	
	/**
	 * Create a new worm that is positioned at the given location,
	 * looks in the given direction, has the given radius and the given name.
	 * 
	 * @param x
	 * The x-coordinate of the position of the new worm (in meter)
	 * @param y
	 * The y-coordinate of the position of the new worm (in meter)
	 * @param direction
	 * The direction of the new worm (in radians)
	 * @param radius 
	 * The radius of the new worm (in meter)
	 * @param name
	 * The name of the new worm
	 */
	@Override
	public Worm createWorm(World world, double x, double y, double direction, double radius,
			String name) throws IllegalArgumentException {
		try { return new Worm (world, x, y, direction, radius, name); }
		catch(IllegalArgumentException illegalArgument) {
			throw new ModelException(illegalArgument);}
	}
	
	public void addEmptyTeam(World world, String newName){
		Team team = new Team(world, newName);
		}
	
	public void addNewFood(World world){
		world.addNewFood();
	}
	
	public void addNewWorm(World world){
		world.addNewWorm();
	}
	
	public boolean canFall(Worm worm){
		return worm.canFall();
	}
	
	public Food createFood(World world, double x, double y){
		return new Food(world, x, y);
	}
	
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random){
		return new World(width, height, passableMap, random);
	}
	
	public void fall(Worm worm){
		worm.fall();
	}
	
	public Collection<Food> getFood(World world){
		return world.getFoodInWorld();
	}
	
	public int getHitPoints(Worm worm){
		return worm.getCurrentHP();
	}
	
	public int getMaxHitPoints(Worm worm){
		return worm.getMaxHP();
	}
	
	public double getRadius(Food food){
		return food.getRadius();
	}
	
	public String getTeamName(Worm worm){
		return worm.getTeamName();
	}
	
	public double getX(Food food){
		return food.getPosition()[0];
	}
	
	public double getY(Food food){
		return food.getPosition()[1];
	}
	
	public boolean isActive(Food food){
		return !food.isTerminated();
	}
	
	public boolean isAdjacent(World world, double x, double y, double radius){
		return world.isAdjacentPosition(x, y, radius);
	}
	
	public boolean isAlive(Worm worm){
		return !worm.isTerminated();
	}
	
	public boolean isImpassable(World world, double x, double y, double radius){
		return world.isPassableMap(x, y, radius);
	}
	
	/**
	 * Returns the name of a single worm if that worm is the winner, or the name
	 * of a team if that team is the winner. This method should null if there is no winner.
	 * 
	 * (For single-student groups that do not implement teams, this method should always return the name of the winning worm, or null if there is no winner)
	 */
	public String getWinner(World world){
		return world.getWinner().getName();
	}
	
	/**
	 * Returns all the worms in the given world
	 */
	public Collection<Worm> getWorms(World world){
		return world.getWormsInWorld();
	}
	
	public boolean isGameFinished(World world){
		return world.gameFinished();
	}
	
	/**
	 * Starts a game in the given world.
	 */
	public void startGame(World world){
		world.startGame();
	}

	/**
	 * Starts the next turn in the given world
	 */
	public void startNextTurn(World world){
		world.nextTurn();
	}
	
	/**
	 * Returns the active projectile in the world, or null if no active projectile exists.
	 */
	public Projectile getActiveProjectile(World world) {
		return world.getActiveProjectile();
	}

	/**
	 * Returns the active worm in the given world (i.e., the worm whose turn it is).
	 */
	public Worm getCurrentWorm(World world){
		return world.getCurrentWorm();
	}
	
	/**
	 * Returns whether or not the given worm can move a given number of steps.
	 */
	@Override
	public boolean canMove(Worm worm) {
		return worm.canMove();
	}

	/**
	 * Moves the given worm by the given number of steps.
	 */
	@Override
	public void move(Worm worm) {
		worm.move();
	}

	/**
	 * Returns whether or not the given worm can turn by the given angle.
	 */
	public boolean canTurn(Worm worm, double angle) {
		return worm.isLegalTurn(angle);
	}

	/**
	 * Turns the given worm by the given angle.
	 */
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}
	/**
	 * Makes the given worm jump.
	 */
	public void jump(Worm worm, double timeStep) throws NullPointerException {
		try { worm.jump(timeStep); }
		catch (NullPointerException nullPointer) {
			throw new ModelException (nullPointer);
		}
		catch (IllegalArgumentException illegalArgument) {
			throw new ModelException (illegalArgument);
		}
	}

	/**
	 * Returns the total amount of time (in seconds) that a
	 * jump of the given worm would take.
	 */
	public double getJumpTime(Worm worm, double timeStep) {
		return worm.jumpTime(timeStep);
	}

	/**
	 * Returns the location on the jump trajectory of the given worm
	 * after a time t.
	 *  
	 * @return An array with two elements,
	 *  with the first element being the x-coordinate and
	 *  the second element the y-coordinate
	 */
	 public double[] getJumpStep(Worm worm, double t) throws IllegalArgumentException {
		try { return worm.jumpStep(t); }
		catch (IllegalArgumentException illegalArgument) {
			throw new ModelException (illegalArgument);
		}
	}

	/**
	 * Returns the x-coordinate of the current location of the given worm.
	 */
	public double getX(Worm worm) {
		return worm.getPositionX();
	}

	/**
	 * Returns the y-coordinate of the current location of the given worm.
	 */
	public double getY(Worm worm) {
		return worm.getPositionY();
	}

	/**
	 * Returns the current orientation of the given worm (in radians).
	 */
	public double getOrientation(Worm worm) {
		return worm.getOrientation();
	}

	/**
	 * Returns the radius of the given worm.
	 */
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}
	
	/**
	 * Sets the radius of the given worm to the given value.
	 */
	public void setRadius(Worm worm, double newRadius) {
		worm.setRadius(newRadius);
	}
	
	/**
	 * Returns the minimal radius of the given worm.
	 */
	public double getMinimalRadius(Worm worm) {
		return worm.getMinRadius();
	}

	/**
	 * Returns the current number of action points of the given worm.
	 */
	public int getActionPoints(Worm worm) {
		return worm.getCurrentActionPoints();
	}
	
	/**
	 * Returns the maximum number of action points of the given worm.
	 */
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}
	
	/**
	 * Returns the name the given worm.
	 */
	public String getName(Worm worm) {
		return worm.getName();
	}

	/**
	 * Renames the given worm.
	 */
	public void rename(Worm worm, String newName) {
		worm.setName(newName);
	}

	/**
	 * Returns the mass of the given worm.
	 */
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
	// projectielen, wapens en explosieven !!!
	
	/**
	 * Returns the location on the jump trajectory of the given projectile after a
	 * time t.
	 * 
	 * @return An array with two elements, with the first element being the
	 *         x-coordinate and the second element the y-coordinate
	 */
	public double[] getJumpStep(Projectile projectile, double t) {
		return projectile.jumpStep(t);
	}
	
	/**
	 * Determine the time that the given projectile can jump until it hits the terrain, hits a worm, or leaves the world.
	 * The time should be determined using the given elementary time interval.
	 * 
	 * @param projectile The projectile for which to calculate the jump time.
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the projectile will not completely move through a piece of impassable terrain.
	 *                 
	 * @return The time duration of the projectile's jump.
	 */
	public double getJumpTime(Projectile projectile, double timeStep) {
		return projectile.jumpTime(timeStep);
	}
	
	/**
	 * Returns the radius of the given projectile.
	 */
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}
	
	/**
	 * Returns the x-coordinate of the given projectile.
	 */
	public double getX(Projectile projectile) {
		return projectile.getX();
	}
	
	/**
	 * Returns the y-coordinate of the given projectile.
	 */
	public double getY(Projectile projectile) {
		return projectile.getY();
	}
	
	/**
	 * Returns whether the given projectile is still alive (active).
	 */
	public boolean isActive(Projectile projectile) {
		return !projectile.isTerminated();
	}
	
	/**
	 * Make the given projectile jump to its new location.
	 * The new location should be determined using the given elementary time interval. 
	 *  
	 * @param projectile The projectile that needs to jump
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the projectile will not completely move through a piece of impassable terrain.
	 */
	public void jump(Projectile projectile, double timeStep) {
		projectile.jump(timeStep);
	}
	
	/**
	 * Activates the next weapon for the given worm
	 */
	public void selectNextWeapon(Worm worm) {
		worm.selectNextWeapon();
	}
	
	/**
	 * Returns the name of the weapon that is currently active for the given worm,
	 * or null if no weapon is active.
	 */
	public String getSelectedWeapon(Worm worm) {
		Weapon weapon = worm.getSelectedWeapon();
		return weapon.getName();
	}

	
	/**
	 * Makes the given worm shoot its active weapon with the given propulsion yield.
	 */
	public void shoot(Worm worm, int yield) {
		worm.fireWeapon(yield);
	}
	
	

}