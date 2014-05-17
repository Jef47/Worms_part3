package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class for the projectiles worms can shoot. It uses some methods of the class worm.
 * @author Marnix Michiel Denys and Jef De Bie
 * @version 1.0
 */

public class Projectile {
	public Projectile (World world, Worm worm, double mass, double cost, double damage, double minForce, double maxForce, int yield){
		this.setWorld(world);
		this.setWorm(worm);
		this.setInitialPosition(worm);
		this.setInitialOrientation(worm);
		this.setMass(mass);
		this.setCost(cost);
		this.setDamage(damage);
		this.setForce(minForce, maxForce, yield);
	}
	
	private World world;
	private Worm worm;
	
	
	private void setWorld(World world) {
		this.world = world;
		world.setActiveProjectile(this);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	private void setWorm(Worm worm) {
		this.worm = worm;
	}
	
	/**
	 * to set and get the position of the projectile.
	 * 
	 * @param positionX
	 * @param positionY
	 */
	protected void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	private void setInitialPosition(Worm worm) {
		double orientation = worm.getOrientation();
		double radius = worm.getRadius();
		double projectileX = worm.getPositionX() + (Math.cos(orientation) * radius);
		double projectileY = worm.getPositionY() + (Math.sin(orientation) * radius);
		this.setPosition(projectileX, projectileY);
	}
	
	public double getX() {
		return this.positionX;
	}
	
	public double getY() {
		return this.positionY;
	}
	
	private double positionX;
	private double positionY;
	
	/**
	 * 
	 * @param worm
	 */
	private void setInitialOrientation (Worm worm) {
		this.orientation = worm.getOrientation();
	}
	
	public double getOrientation() {
		return this.orientation;
	}
	
	private double orientation;
	
	private void setMass(double mass) {
		this.mass= mass;
	}
	
	public double getMass(){
		return this.mass;
	}
	
	private void setForce(double minForce, double maxForce, int yield) {
		double diff = maxForce - minForce;
		this.force = minForce + (diff * (yield/100));
	}
	
	
	public double getForce() {
		return this.force;
	}
	
	private void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	private void setDamage(double damage) {
		this.damage = damage;
	}
	
	public double getDamage() {
		return this.damage;
	}
	
	private void setRadius() {
		this.radius = Math.pow((this.getMass() * 3) / (density * 4 * Math.PI), 1/(3.0));
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	private double density = 7800;
	private double mass;
	private double force;
	private double cost;
	private double damage;
	private double radius;

	
	/**
	 * Method jump.
	 * 
	 * @post the new X position must be equal to the sum of the previous and the jump distance
	 * 			| new.getPositionX() == this.getPositionX() + this.getJumpDistance()
	 * @post the new Y position must be the same as the previous
	 * 			| new.getPositionY() == this.getPositionY()
	 * @post the current AP must be set to 0
	 * 			| new.getCurrentActionPoints() == 0
	 * 
	 * Defensive
	 */
	public void jump(double timeStep) throws NullPointerException, IllegalArgumentException {
		if (! this.worm.isFacedDown()) {
			if (this.worm.getCurrentActionPoints() > 0) {
				this.setPosition(this.jumpStep(timeStep)[0], this.jumpStep(timeStep)[1]);
				int counter = 1;
				while (this.getWorld().isPassableMap(this.getX(), this.getY(), this.getRadius())) {
					counter = counter + 1;
					this.setPosition( this.jumpStep(timeStep * counter)[0], this.jumpStep(timeStep * counter)[1] );
				}
			}
			else {
				throw new NullPointerException ("You don't have any ActionPoints left.");
			}
		}
		else {
			throw new IllegalArgumentException ("This worm is faced downwards");
		}
	}
	
	/**
	 * Calculates the time of the jump
	 * 
	 * @return time
	 */
	public double jumpTime(double timeStep) {
		int counter = 0;
		while (this.getWorld().isPassableMap(this.jumpStep(timeStep*counter)[0], this.jumpStep(timeStep*counter)[0], this.getRadius())) {
			counter = counter + 1;
			this.setPosition( this.jumpStep(timeStep * counter)[0], this.jumpStep(timeStep * counter)[1] );
		}
		return counter * timeStep;
	}
	
	/**
	 * Calculates the position of the jumping worm at a given time.
	 * 
	 * @return the location of the worm at the given time
	 */
	public double[] jumpStep(double deltaT) throws IllegalArgumentException {
		if ( (! this.worm.isFacedDown()) && this.worm.getCurrentActionPoints() > 0) {
			double v0 = this.getInitialVelocity(this.getForce(), this.getMass());
			double theta = this.getOrientation();
			double v0x = v0 * Math.cos(theta);
			double v0y = v0 * Math.sin(theta);
			double xT = this.getX() + (v0x * deltaT);
			double yT = this.getY() + (v0y * deltaT) - (gravity * Math.pow(deltaT, 2.0) / 2);
			double[] jumpstep = {xT, yT};
			return jumpstep;
		}
		else{
			throw new IllegalArgumentException ("Not able to jump (Faced down / Insufficient ActionPoints)");
		}
	}
	
	/**
	 * method to calculate the initial velocity o the worm that jumps
	 * 
	 * @return initial velocity
	 */
	public double getInitialVelocity(double force, double mass) {
		double velocity = (force / mass) * 0.5;
		return velocity;
	}
	
	/**
	 * method to calculate the distance of the jump
	 * 
	 * @return distance
	 */
	public double getJumpDistance(double force, double mass) {
		double v0 = this.getInitialVelocity(force, mass);
		double theta = this.getOrientation();
		double distance = (Math.pow(v0, 2) * Math.sin(2*theta)) / gravity;
		return distance;
	}
	
	private static final double gravity = 9.80665;
	
	private boolean isTerminated = false;
	
	/**
	 * Method to "destroy" a projectile. 
	 */
	
	public void terminate(){
		this.isTerminated = true;
	}
	
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
}