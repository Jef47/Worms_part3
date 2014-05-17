package worms.model;


import java.util.regex.Matcher;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import worms.util.Util;


/**
 * A class of worms, all located in a certain position (x,y).
 * Each worm also has a certain name, radius, orientation, mass and amount of AP's (action points).
 * 
 * minimum radius = 0.25
 * shape = circle
 * density = 1062
 * 
 * 
 * 
 * @authors Marnix Michiel Denys & Jef De Bie
 * @version 1.0
 */


public class Worm {
	
	/**
	 * @param positionX
	 * @param positionY
	 * @param radius
	 * @param name
	 * @param minRadius (= 0.25)
	 */
	
	public Worm(){} // No-argument Constructor, nodig zodat projectile can inheritten van worm
	
	public Worm (World world, double positionX, double positionY, double orientation, double radius, String name) {
		this.setWorld(world);
		this.setPosition(positionX,positionY);
		this.setMinRadius(0.25);
		this.setRadius(radius);
		this.setOrientation(orientation);
		this.setName(name);
	    this.setCurrentAPToMaxAP(); 
	    this.setCurrentHPtoMaxHP();
	    this.setIsActive(false);
	    this.setTeam(null);
	    this.selectWeapon(Rifle);
	}
	
	private String Teamname;
	private boolean isActive;
	
	public void setTeam(String teamname){
		this.Teamname = teamname;
	}
	
	public String getTeamName(){
		return this.Teamname;
	}
	
	public void setIsActive(boolean argument){
		this.isActive = argument;
	}
	
	public boolean getActive(){
		return this.isActive;
	}
	
	/**
	 * private variables that define the minimum radius and the density of the worms.
	 */
	
	private static  double density = 1062;
	
	protected World world;
	
	/**
	 * 
	 * @param world
	 */
	
	protected void setWorld(World world){
		this.world = world;
		world.addWormToWorld(this);
	}
	
	/**
	 * 
	 * @return world
	 */
	
	public World getWorld(){
		return this.world;
	}
	
	
	/*
	 * POSITION, ORIENTATION, RADIUS, MASS AND ACTION POINTS
	 */
	
	
	/**
	 * Sets the position of the Worm.
	 * 
	 * @param positionX
	 * 			the X-coordinate of the Worm.
	 * 
	 * @param positionY
	 * 			the Y-coordinate of the Worm.
	 * 
	 * @post the new position is set to the given coordinates
	 * 			| getPositionX() = positionX
	 * 			| getPositionY() = positionY
	 * 
	 * Defensive
	 */
	protected void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	/**
	 * Gets the X-coordinate of the Worm.
	 * 
	 * @return the X-coordinate
	 */
	@Basic
	public double getPositionX() {
		return this.positionX;
	}
	
	/**
	 * Gets the Y-coordinate of the Worm.
	 * 
	 * @return the Y-coordinate
	 */
	@Basic
	public double getPositionY() {
		return this.positionY;
	}
	
	protected double positionX;
	protected double positionY;
	
	/**
	 * Sets the radius of the Worm.
	 * 
	 * @param radius
	 * 			the radius of the worm (in meters)
	 * 
	 * 
	 * @throws IllegalArgumentException
	 * 			The radius must be greater than or equal to the minimum radius.
	 * 			| radius >= minRadius
	 * 
	 * @post the radius of the Worm must be set to the given radius.
	 * 			| this.radius = radius
	 * 
	 * Defensive
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (Util.fuzzyGreaterThanOrEqualTo(radius, this.getMinRadius())) {
			this.radius = radius;
			this.setMass(radius);
		}
		else{
			throw new IllegalArgumentException("The given radius is not greater than or equal to the predefined minimum radius.");
		}
	}
	
	/**
	 * Gets the radius of the worm.
	 * 
	 * @return the radius
	 */
	@Basic
	public double getRadius() {
		return this.radius;
	}
	
	
	/**
	 * Sets the minimum radius of the worm
	 * 
	 * @param minRadius
	 * 
	 * Defensive
	 */
	private void setMinRadius(double minRadius) throws IllegalArgumentException {
		if (Math.signum(minRadius) == -1.0) {
			throw new IllegalArgumentException("Negative minimum radius.");
		}
		else {
			this.minRadius = minRadius;
		}
	}
	
	/**
	 * Returns the minimum radius;
	 * 
	 * @return the minimum radius
	 */
	public final double getMinRadius() {
		return this.minRadius;
	}
	
	
	private double minRadius;
	private double radius;
	
	/**
	 * Sets the mass of the worm (in kg), according to it's radius.
	 * 
	 * @param radius
	 * 
	 * @post the mass must be : m = p * (4/3) * Pi * (r^3)
	 * 
	 * Defensive
	 */
	private void setMass(double radius) throws IllegalArgumentException {
		if (Util.fuzzyGreaterThanOrEqualTo(radius, this.getMinRadius())) {
			this.mass = density * (4 * Math.PI * Math.pow(radius, 3.0) / 3);
			this.setMaxActionPoints();
			this.setMaxHP();
		}
		else {
			throw new IllegalArgumentException ("The given radius is not greater than or equal to the predefined minimum radius.");
		}
	}
	
	
	/**
	 * returns the mass of the worm.
	 * 
	 * @return the mass
	 */
	@Basic
	public double getMass() {
		return this.mass;
	}

	
	public double mass;   //  ! ! nodig? (en indien nodig, niet eerder private?) ! ! 

	
	/**
	 * set the orientation to the value between 0 and 2*PI that matches the value given as orientation.
	 * 
	 * @param orientation
	 * 
	 * 
	 * @post The orientation is a value between 0 and 2Pi.
	 * 			| orientation >=0
	 * 			| orientation <=2*Math.PI
	 * @post The orientation matches the given value for orientation
	 * 			| new.orientation = orientation
	 * 
	 * Nominal
	 */
	protected void setOrientation(double orientation) {
		double sign = Math.signum(orientation);
		orientation = Math.abs(orientation);
		while (Util.fuzzyGreaterThanOrEqualTo(orientation, 2*Math.PI)) {
			orientation -= 2*Math.PI;
		}
		if (sign == -1.0) {
			orientation = (2*Math.PI) - orientation;
		}
		this.orientation = orientation;
	}
	
	/**
	 * Gets the orientation.
	 * 
	 * @return the orientation
	 */
	public double getOrientation() {
		return this.orientation;
	}
	
	protected double orientation;
	
	/**
	 * Sets the Name of the worm to the given string.
	 * 
	 * @param name
	 * 
	 * 
	 * @post  the name of the worm must be set to the given string (if it applies to the given rules).
	 * 			| new.name == name
	 * 
	 * Defensive
	 */
	public void setName(String name) throws IllegalArgumentException{
		if (this.isValidName(name)) {
			this.name = name;
		}
		else {
			throw new IllegalArgumentException("Name is invalid.");
		}
	}
	
	/**
	 * Gets the name of the worm.
	 * 
	 * @return the name
	 * 
	 * Defensive
	 */
	public String getName() {
		return this.name;
	}
	
	public String name;
	
	/**
	 * Checks if given name is a valid name.
	 * 
	 * (http://www.tutorialspoint.com/java/java_string_matches.htm : uitleg over de functie "matches")
	 * 
	 * @param name
	 * 
	 * @return boolean (true if name is valid)
	 * 
	 * @post if returns true : 
	 * 		name must be valid (longer than 2, first character must be uppercase and all characters must be of the given set)
	 * 			|name.length() > 2
	 * 			|Character.isUpperCase(name.CharAt(0))
	 * 			|name.matches("[a-zA-Z'\" ]*")
	 */
	private boolean isValidName(String name) {
		return (name.length() > 2) && (Character.isUpperCase(name.charAt(0))) && name.matches("[a-zA-Z'\" ]*");
	}
	
	/**
	 * Sets the maximum AP's, according to the mass of the worm.
	 * 
	 * @param mass
	 * 
	 * @post the maxActionPoints must be equal to the mass rounded to the closest integer.
	 * 			| new.maxActionPoints == (int) Math.round(this.getMass()) 
	 * 
	 * Total
	 */
	private void setMaxActionPoints() {
		double mass = this.getMass();
		this.maxActionPoints = (int) Math.round(mass);
	}
	
	/**
	 * Gets the maximum AP's.
	 * 
	 * @return max AP's
	 */
	public int getMaxActionPoints() {
		return this.maxActionPoints;
	}
	
	/**
	 * Sets max HP of the worm equal to his mass
	 */
	
	private void setMaxHP() {
		double mass = this.getMass();
		this.maxHP = (int) Math.round(mass);
	}
	
	/**
	 * Returns the max amount of hitpoints of a worm
	 * @return maxHP
	 */
	
	public int getMaxHP(){
		return this.maxHP;
	}
	
	/**
	 * Sets the current number of hitpoints of a worm to the max amount 
	 */
	
	private void setCurrentHPtoMaxHP(){
		this.currentHP = this.getMaxHP();
	}
	
	public int maxHP;
	public int currentHP;
	public int maxActionPoints;
	public int currentActionPoints;
	
	/**
	 * Sets the Current APs to the maxAPs.
	 * 
	 * @post the current APs must be equal to the maxAPs.
	 * 			| new.currentActionPoints == this.getMaxActionPoints()
	 * 
	 * Total
	 */
	private void setCurrentAPToMaxAP() {
		this.currentActionPoints = this.getMaxActionPoints();
	}
	
	/**
	 * Sets the currentActionPoints to the given integer.
	 * 
	 * @param ActionPoints 
	 * 
	 * @post if ActionPoints < 0 set current ActionPoints to 0
	 * 			| this.currentActionPoints == 0
	 * @post if ActionPoints > maxActionPoints set currentActionPoints to maxActionPoints
	 * 			| this.currentActionPoints == this.getMaxActionPoints
	 * @post if ActionPoints < maxActionPoints set currentActionPoints to ActionPoints
	 * 			| this.currentActionPoints == ActionPoints
	 * 
	 * Total
	 */
	private void setCurrentActionPoints(int ActionPoints) {
		if (ActionPoints < 0) {
			this.currentActionPoints = 0;
		}
		if (ActionPoints > this.getMaxActionPoints()) {
			this.setCurrentAPToMaxAP();
		}
		else {
			this.currentActionPoints = ActionPoints;
		}
	}
	
	/**
	 * sets the amount of hitpoints of a worm to a certain integer
	 * @param Hitpoints
	 */
	
	private void setCurrentHP(int Hitpoints) {
		if (Hitpoints < 0) {
			this.currentHP = 0;
		}
		if (Hitpoints > this.getMaxHP()) {
			this.setCurrentHPtoMaxHP();
		}
		else {
			this.currentHP = Hitpoints;
		}
	}
	
	/**
	 * Return if the tun of a worm is over, this is the case if his action points or hit points are all used
	 * or if he gets outside the game map.
	 * @return boolean turnEnded
	 */
	public boolean turnEnded(){
		if (this.getCurrentActionPoints() == 0){
			if (this.getCurrentHP()  == 0){
				this.killWorm();
				return true;
			}
			if (this.stillOnMap() == false){
				this.killWorm();
				return true;
			}
			else{
				return true;
			}
		}
		if (this.getCurrentHP()  == 0){
			this.killWorm();
			return true;
		}
		if (this.stillOnMap() == false){
			this.killWorm();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Method to give a worm 10 extra HP at the start of his turn
	 */
	public void startOfTurn(){
		if (this.getCurrentHP() > 0 && this.getCurrentHP()<= this.getMaxHP() && !this.isTerminated() ){
			int newHP = this.getCurrentHP() + 10;
			this.setCurrentHP(newHP);
		}
	}
	
	/**
	 * Method to kill a worm if needed.
	 */
	
	private void killWorm(){
		assert this.getCurrentHP() == 0 || this.stillOnMap() == false;
		this.getWorld();
		this.terminate();
	}
	
	/**
	 * return wethever the worm is still on the game map or not
	 * @return boolean
	 */
	
	private boolean stillOnMap(){
		World world = this.getWorld();
		double X = this.getPositionX();
		double Y = this.getPositionY();
		double radius = this.getRadius();
		if (world.positionOnMap(X, Y, radius)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Gets the current action points.
	 * 
	 * @return current AP's
	 */
	public int getCurrentActionPoints() {
		return this.currentActionPoints;
	}
	
	/**
	 * returns the current amount of hitpoints of a worm
	 * @return integer
	 */
	
	public int getCurrentHP(){
		return this.currentHP;
	}
	
	
	
		
	/*
	 * TURNING AND MOVING
	 */
	
	
	/**
	 * private variables that define the cost to turn 2Pi radians, the cost to move horizontally and vertically.
	 */
	private static final double turnCost = 60;
	private static final double horizontalCost = 1;
	private static final double verticalCost = 4;
	
	
	/**
	 *  Method to move worm
	 *  
	 *  @param numberOfSteps
	 *  
	 *  @throws IllegalArgumentException
	 *  	if it is not a valid move.
	 *  			| ! this.isLegalMove(numberOfSteps)
	 *  
	 *  @post the position of the worm must be changed by adding the given numberOfSteps in the current orientation to the current position.
	 *  			| new.getPositionX() = this.getPositionX() + (Math.cos(this.getOrientation()) * this.getRadius() * numberOfSteps)
	 *  			| new.getPositionY() = this.getPositionY() + (Math.sin(this.getOrientation()) * this.getRadius() * numberOfSteps)
	 *  
	 *  Defensive
	 */
	public void move() throws IllegalArgumentException {
		double idealNewPositionX = this.getPositionX() + Math.cos(this.getOrientation())*this.getRadius();
		double idealNewPositionY = this.getPositionY() + Math.sin(this.getOrientation())*this.getRadius();
		if (this.getWorld().isAdjacentPosition(idealNewPositionX, idealNewPositionY, this.getRadius())){
			if(isLegalMove(idealNewPositionX, idealNewPositionY, this.getPositionX(), this.getPositionY())){
				this.costMove(idealNewPositionX, idealNewPositionY, this.getPositionX(),this.getPositionY());
				this.setPosition(idealNewPositionX, idealNewPositionY);
			}
			else{
				throw new IllegalArgumentException("Not enough action points");
			}
		}
		else{
			while (!this.getWorld().isAdjacentPosition(idealNewPositionX, idealNewPositionY, this.getRadius()) &&
					this.getWorld().distanceBetweenPoints(this.getPositionX(), this.getPositionY(), idealNewPositionX, idealNewPositionY)>= 0.1){
				idealNewPositionX -= Math.cos(this.getOrientation())*0.01;
				idealNewPositionY -= Math.cos(this.getOrientation())*0.01;
			}
			if (this.getWorld().distanceBetweenPoints(this.getPositionX(), this.getPositionY(), idealNewPositionX, idealNewPositionY)< 0.1){
				int i = 1;
				int a = 1;
				while (this.getWorld().distanceBetweenPoints(this.getPositionX(), this.getPositionY(), idealNewPositionX, idealNewPositionY)< 0.1){
					double newOrientation = this.getOrientation() + Math.pow(-1, i) * Math.round(a/2) * 0.0175;
					if (a > 90){
						throw new IllegalArgumentException ("Can't move in that direction");
					}
					else{
						idealNewPositionX = this.getPositionX() + Math.cos(newOrientation)*this.getRadius();
						idealNewPositionY = this.getPositionY() + Math.sin(newOrientation)*this.getRadius();
						while (!this.getWorld().isAdjacentPosition(idealNewPositionX, idealNewPositionY, this.getRadius()) &&
								this.getWorld().distanceBetweenPoints(this.getPositionX(), this.getPositionY(), idealNewPositionX, idealNewPositionY)>= 0.1){
							idealNewPositionX -= Math.cos(this.getOrientation())*0.01;
							idealNewPositionY -= Math.cos(this.getOrientation())*0.01;
						}
					}
				}
				if (this.getWorld().isAdjacentPosition(idealNewPositionX, idealNewPositionY, this.getRadius())){
					if(this.isLegalMove(idealNewPositionX, idealNewPositionY, this.getPositionX(), this.getPositionY())){
						this.costMove(idealNewPositionX, idealNewPositionY, this.getPositionX(), this.getPositionY());
						this.setPosition(idealNewPositionX, idealNewPositionY);
					}
					else{
						throw new IllegalArgumentException("Not enough action points");
					}
			    }
			}
			else{
				if(this.isLegalMove(idealNewPositionX, idealNewPositionY, this.getPositionX(), this.getPositionY())){
					this.costMove(idealNewPositionX, idealNewPositionY, this.getPositionX(), this.getPositionY());
					this.setPosition(idealNewPositionX, idealNewPositionY);
				}
				else{
					throw new IllegalArgumentException("Not enough action points");
				}
			}
		}
	}
	
	public boolean canMove() {
		if (this.getCurrentActionPoints() >  0 && this.getWorld().isAdjacentPosition(this.getPositionX(), this.getPositionY(), this.getRadius())) {
			return true;
		}
		else{
			return false;
		}
	}


	/**
	 * Checks if a movement is legal.
	 * 
	 * @return (boolean) if the nbSteps is a pos int && if the move doesn't cost more than the current amounts of APs
	 */
	public boolean isLegalMove(double newX,double newY,double x,double y) {
		return ( !(this.calculateCostMove(newX, newY, x, y) > this.getCurrentActionPoints()));
	}
	
	/**
	 * Method to calculate the cost of a move.
	 * 
	 * @return (int) the total cost of a move (vertical + horizontal)
	 */
	private int calculateCostMove(double newX,double newY,double x,double y) {
		double slope = Math.atan((newY - y)/(newX - x));
		double moveCostStep = horizontalCost * Math.abs(Math.cos(slope)) + verticalCost * Math.abs(Math.sin(slope));
		int totalMoveCost = (int) Math.round(moveCostStep);
		return totalMoveCost;
	}
	
	/**
	 * Subtracts the cost of this move from the current amount of APs.
	 * 
	 * @post the current amount of APs is equal to the previous amount minus the cost of the move.
	 * 			| new.getCurrentActionPoints() == this.getCurrentActionPoints() - this.calculateCostMove(numberOfSteps)
	 * 
	 * Defensive
	 */
	private void costMove(double newX, double newY, double x, double y) {
		this.setCurrentActionPoints(this.getCurrentActionPoints() - this.calculateCostMove(newX, newY, x, y));
	}
		
	
	
	/**
	 *  Method to turn.
	 * 
	 * @param angle
	 * 
	 * @post the new orientation must be equal to the old orientation plus the given angle.
	 * 			| 
	 * 
	 * @ throws NullPointerException
	 * 			The worm needs sufficient ActionPoints to turn over the given angle.
	 * 
	 * Nominal
	 */
	public void turn(double angle) throws NullPointerException {
		double newOrientation = this.getOrientation() + angle;
		if ( Util.fuzzyGreaterThanOrEqualTo(newOrientation, this.getOrientation()) ) {
			double newAngle = newOrientation - this.getOrientation();
			if (this.isLegalTurn(newAngle)) {
				this.setOrientation(newOrientation);
				this.costTurn(newAngle);
			}
			else {
				throw new NullPointerException ("Illegal turn (Insufficient ActionPoints)");
			}
		} 
		else {
			double newAngle = this.getOrientation() - newOrientation;
			if (this.isLegalTurn(newAngle)) {
				this.setOrientation(newOrientation);
				this.costTurn(newAngle);
			}
			else {
				throw new NullPointerException ("Illegal turn (Insufficient ActionPoints)");
			}
		}
	}
	
	/**
	 * Checks if the worm has sufficient ActionPoints to turn over the given angle.
	 * 
	 * @param angle
	 * 
	 * @return boolean (true if turn is legal)
	 * 
	 * @post if returns true :
	 * 		Current ActionPoints must be greater than the cost of the turn.
	 * 			| this.getCurrentActionPoints() > this.calculateCostTurn(angle)
	 */
	public boolean isLegalTurn(double angle) {
		return ( this.getCurrentActionPoints() > this.calculateCostTurn(angle) );
	}
	
	/**
	 * Calculates the cost to turn over the given angle.
	 * 
	 * @param angle
	 * 
	 * @return int cost
	 * 
	 * @post the cost is equal to the integer closest to turnCost * (angle / 2Pi)
	 * 			| cost = turnCost * (angle / (2 * Math.PI))
	 */
	private int calculateCostTurn(double angle) {
		double fraction = angle / (2 * Math.PI);
		int cost = (int) Math.round(fraction * turnCost);
		return cost;
	}
	
	/**
	 * Subtracts the cost of a given angle from the current ActionPoints.
	 * 
	 * @param angle
	 * 
	 * @post the current APs must be equal to the APs before minus the cost of this angle
	 * 			|new.getCurrentActionPoints() = this.getCurrentActionPoints - this.calculateCostTurn(angle)
	 * 
	 * Nominal
	 */
	private void costTurn(double angle) {
		this.setCurrentActionPoints(this.getCurrentActionPoints() - this.calculateCostTurn(angle));
	}
	
	
	
	
	/*
	 * JUMPING
	 */
	
	
	
	
	/**
	 * Method jump.
	 * 
	 * @post the new X position must be equal to the sum of the previous and the jump distanc
	 * 			| new.getPositionX() == this.getPositionX() + this.getJumpDistance()
	 * @post the new Y position must be the same as the previous
	 * 			| new.getPositionY() == this.getPositionY()
	 * @post the current AP must be set to 0
	 * 			| new.getCurrentActionPoints() == 0
	 * 
	 * Defensive
	 */
	public void jump(double timeStep) throws NullPointerException, IllegalArgumentException {
		if (! this.isFacedDown()) {
			if (this.getCurrentActionPoints() > 0) {
				this.setPosition(this.jumpStep(timeStep)[0], this.jumpStep(timeStep)[1]);
				int counter = 1;
				while (this.getWorld().isPassableMap(this.getPositionX(), this.getPositionY(), this.getRadius())) {
					counter = counter + 1;
					this.setPosition( this.jumpStep(timeStep * counter)[0], this.jumpStep(timeStep * counter)[1] );
				}
				this.setCurrentActionPoints(0);
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
	 * Method to calculate the force of the jump
	 * 
	 * @return force =  (5*APs) + (mass*gravity)
	 */
	public double getJumpForce() {
		double APs = this.getCurrentActionPoints();
		double mass = this.getMass();
		double force = (5 * APs) + (mass * gravity);
		return force;
	}
	
	
	/**
	 * method to calculate the initial velocity o the worm that jumps
	 * 
	 * @return initial velocity
	 */
	public double getInitialVelocity() {
		double velocity = (this.getJumpForce() / this.getMass()) * 0.5;
		return velocity;
	}
	
	/**
	 * method to calculate the distance of the jump
	 * 
	 * @return distance
	 */
	public double getJumpDistance() {
		double v0 = this.getInitialVelocity();
		double theta = this.getOrientation();
		double distance = (Math.pow(v0, 2) * Math.sin(2*theta)) / gravity;
		return distance;
	}
	
	/**
	 * Calculates the time of the jump
	 * 
	 * @return time
	 */
	public double jumpTime(double timeStep) {
		int counter = 0;
		while (this.getWorld().isPassableMap(this.jumpStep(timeStep*counter)[0], this.jumpStep(timeStep*counter)[1], this.getRadius())) {
			counter = counter + 1;
			
		}
		return counter * timeStep;
	}
	
	
	/**
	 * Returns if a worm can fall, that means he is situated on passable terrain that is not adjacent.
	 * @return
	 */
	
	public boolean canFall(){
		double X = this.getPositionX();
		double Y = this.getPositionY();
		double radius = this.getRadius();
		World world = this.getWorld();
		if (!this.isTerminated() && !world.isAdjacentPosition(X, Y, this.getRadius()) && world.isPassableMap(X, Y, radius)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * makes a worm fall, that means, decrease his Y-coördinate until he reaches an adjacent location or
	 * he falls outside the map. 
	 */
	
	public void fall(){
		assert this.canFall();
		double X = this.getPositionX();
		double Y = this.getPositionY();
		World world = this.getWorld();
		boolean[][] passableMap = world.getPassableMap();
		double height = world.getHeight();
		while (!world.isAdjacentPosition(X, Y, this.getRadius()) && this.stillOnMap()){ 
			double newY = this.getPositionY() + height/passableMap.length;
			this.setPosition(X, newY);
		}
		if (!this.stillOnMap()){
			this.killWorm();
		}
	}
	
	/**
	 * Checks if the worm is facing downwards.
	 * 
	 * @return (boolean) if the worm is faced downwards
	 */
	public boolean isFacedDown() {
		return ( (this.getOrientation() > Math.PI) && (this.getOrientation() < 2*Math.PI) );
	}
	
	/**
	 * Calculates the position of the jumping worm at a given time.
	 * 
	 * @return the location of the worm at the given time
	 */
	public double[] jumpStep(double deltaT) throws IllegalArgumentException {
		if ( (! this.isFacedDown()) && this.getCurrentActionPoints() > 0) {
			double v0 = this.getInitialVelocity();
			double theta = this.getOrientation();
			double v0x = v0 * Math.cos(theta);
			double v0y = v0 * Math.sin(theta);
			double xT = this.getPositionX() + (v0x * deltaT);
			double yT = this.getPositionY() + (v0y * deltaT) - (gravity * Math.pow(deltaT, 2.0) / 2);
			double[] jumpstep = {xT, yT};
			return jumpstep;
		}
		else{
			throw new IllegalArgumentException ("Not able to jump (Faced down / Insufficient ActionPoints)");
		}
	}
	
	/**
	 * private variable gravity that equals the Earth's standard acceleration (9.80665).
	 */
	private static final double gravity = 9.80665;
	
	private boolean isTerminated = false;
	
	/**
	 * Method to "destroy" a worm if it has no hit points anymore. 
	 */
	
	public void terminate(){
		this.isTerminated = true;
	}
	
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	public Weapon Bazooka = new Weapon(world, this, 300, 50, 80, 2.5, 9.5, "Bazooka");
	public Weapon Rifle = new Weapon(world, this, 10, 10, 20, 1.5, 1.5, "Rifle");
	
	public void selectWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getSelectedWeapon() {
		return this.weapon;
	}
	
	private Weapon weapon; 
	
	public void selectNextWeapon() {
		if (this.weapon == Bazooka) {
			this.selectWeapon(Rifle);
		}
		else {
			this.selectWeapon(Bazooka);
		}
	}
	
	
	public void fireWeapon(int yield) {   // ! ! nog bekijken ! ! 
		if (this.getCurrentActionPoints() > this.getSelectedWeapon().getCost()) {
			this.getSelectedWeapon().shoot(yield);
			int newActionPoints = (int)  Math.round(this.getCurrentActionPoints() - this.getSelectedWeapon().getCost());
			this.setCurrentActionPoints(newActionPoints);
		}
	}
	
	
	
}