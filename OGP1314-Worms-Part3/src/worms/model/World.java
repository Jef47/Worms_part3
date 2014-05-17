package worms.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.HashSet;

import worms.util.Util;

/**
 * A class of worlds, with a certain height and with.
 * A certain number of worms will be located in this world.
 * In this world there are passable and impassable maps for worms and their projectiles.
 * 
 * @authors Marnix Michiel Denys & Jef De Bie
 * @version 1.0
 */

public class World {
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param passableMap
	 * @post width and height must be in the range 0 to a certain MAX_VALUE.
	 *                   |isValid(width, height) == True
	 */

	public World (double width, double height, boolean[][] passableMap, Random random){
		assert isValid(width, height);
		this.random = random;
		this.setWidth(width);
		this.setHeight(height);
		this.setPassableMap(passableMap);
	}
	
	private Random random;
	
	public Random getRandom(){
		return this.random;
	}
	
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @return boolean
	 * @throws IllegalArgumentException
	 */
	
	public boolean isValid(double width, double height) throws IllegalArgumentException {
		if (Util.fuzzyGreaterThanOrEqualTo(width, 0) && Util.fuzzyGreaterThanOrEqualTo(height, 0) 
			&& Util.fuzzyLessThanOrEqualTo(width, Double.MAX_VALUE) && Util.fuzzyLessThanOrEqualTo(height, Double.MAX_VALUE)) {
			return true;
		}
		else{
			throw new IllegalArgumentException("The given dimension are not in the valid range");
		}
	}
	
	private double width;
	private double height;
	private boolean[][] passableMap;
	private LinkedList<Worm> WormsInWorld = new LinkedList<Worm>();
	private LinkedList<Team> TeamsInWorld = new LinkedList<Team>();
	private LinkedList<Food> FoodInWorld = new LinkedList<Food>();
	/**
	 * Add the worm to the list of worms from this world
	 * @param worm
	 */
	
	public void addWormToWorld(Worm worm){
		this.WormsInWorld.add(worm);
	}
	
	public void removeWorm(Worm worm){
		this.WormsInWorld.remove(worm);
	}
	
	/**
	 * add a team to this world
	 * @param team
	 */
	
	public void addTeamToWorld(Team team){
		this.TeamsInWorld.add(team);
	}
	
	public void removeTeam(Team team){
		this.TeamsInWorld.remove(team);
	}
	
	/**
	 * add food to this world
	 * @param food
	 */
	
	public void addFoodToWorld(Food food){
		this.FoodInWorld.add(food);
	}
	
	public void removeFood(Food food){
		this.FoodInWorld.remove(food);
	}
	/**
	 * return the food from this world
	 * @return food
	 */
	
	public LinkedList<Food> getFoodInWorld(){
		return this.FoodInWorld;
	}
	/**
	 * return the worms situated in this world
	 * @return worms
	 */
	
	public LinkedList<Worm> getWormsInWorld(){
		return this.WormsInWorld;
	}
	
	public LinkedList<Team> getTeamsInWorld(){
		return this.TeamsInWorld;
	}
	
	/**
	 * 
	 * @param width
	 */
	
	private void setWidth(double width){
		this.width = width;
	}
	
	/**
	 * 
	 * @return width
	 */
	
	public double getWidth(){
		return this.width;
	}
	
	/**
	 * 
	 * @param height
	 */
	
	private void setHeight(double height){
		this.height = height;
	}
	
	/**
	 * 
	 * @return height
	 */
	
	public double getHeight(){
		return this.height;
	}
	
	/**
	 * 
	 * @param passableMap
	 */
	private void setPassableMap(boolean[][] passableMap){
		this.passableMap = passableMap;
	}
	
	public boolean[][] getPassableMap(){
		return this.passableMap;
	}
	
	/**
	 * Check if a map is passable by checking the central position and then checking 20 points
	 *on a circle of the needed radius around this position.
	 * @param Xposition
	 * @param Yposition
	 * @return boolean
	 */
	
	public boolean isPassableMap(double Xposition,double Yposition, double radius){
		int pixelX = (int) Math.round(Xposition);
		int pixelY = (int) Math.round(Yposition);
		if(!this.passableMap[pixelY][pixelX]){
			return false;
		}
		else{
			for(int i = 0; i<21; i++){
				int newPixelX = pixelX + (int) Math.round(radius * Math.cos(i * 2 * Math.PI/20));
				int newPixelY = pixelY + (int) Math.round(radius * Math.sin(i * 2 * Math.PI/20));
				if(this.passableMap[newPixelY][newPixelX] == false){
					return false;
				}
			}
			return true;
		}
	}
	
	private double a = 0.0;
	private double b=0.5;
	private double c = 0.0;
	
	/**
	 * Method to find nearest impassable point from a certain point. It works iteratively.
	 * In each step it first checks if point is impassable, if so, it returns this point. 
	 * Else it will check points laying a certain number of pixels further alternatively horizontally and vertically,
	 * in order to move away from the initially point following a spiral.
	 * @param x
	 * @param y
	 * @return position
	 * @post position is the nearest impassableLocation to initial position
	 */
	
	// nog wat code bijschrijven zodat het gevonden punt zeker binnen de map ligt en het algoritme niet buiten de map gaat zoeken
	
//	public double[] nearestImpassableLocation(double x, double y,double radius){
//		assert positionOnMap(x, y);		
//		while(isPassableMap(x, y, radius)){
//			this.a+=Math.PI/2;
//			this.b+=0.5;
//			this.c+=0.5;
//			x = x + b*Math.round(Math.sin(a))*(width/passableMap[0].length);
//			y = y - c*Math.round(Math.cos(a))*(height/passableMap.length);
//			while (x <0 || y<0){
//				x +=0.5;
//				y +=0.5;
//			}
//		
//		}
//		double[] position = {x, y};
//		this.a = 0.0;
//		this.b = 0.5;
//		this.c = 0.0;
//		return position;
//		
//	}
//	
//	/**
//	 * Method with the same goal as nearestImpassableLocation but for a passable location this time.
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	
//	// nog wat code bijschrijven zodat het gevonden punt zeker binnen de map ligt en het algoritme niet buiten de map gaat zoeken
//	
//	public double[] nearestPassableLocation(double x, double y, double radius){
//		assert positionOnMap(x, y);
//		while (!isPassableMap(x, y, radius)){
//			this.a+=Math.PI/2;
//			this.b+=0.5;
//			this.c+=0.5;
//			x = x + b*Math.round(Math.sin(a))*(width/passableMap[0].length);
//			y = y - c*Math.round(Math.cos(a))*(height/passableMap.length);
//			while (x <0 || y<0){
//				x +=0.5;
//				y +=0.5;
//			}
//		}
//		double[] position = {x,y};
//		this.a = 0.0;
//		this.b = 0.5;
//		this.c = 0.0;
//		return position;
//	}

	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param xx
	 * @param yy
	 * @return the distance between (x, y) and (xx, yy)
	 */
	public double distanceBetweenPoints(double x,double y,double xx,double yy){
		double a = Math.pow((x-xx), 2);
		double b = Math.pow((y-yy), 2);
		return Math.sqrt(a+b);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return boolean
	 * @post if true: the position is a valid position for the game object with a certain radius
	 */
	
	public boolean isAdjacentPosition(double x, double y, double radius){
		if (isPassableMap(x, y, radius) && !isPassableMap(x, y, radius * 1.1)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public double minRadius = 0.25;
	public double maxRadius = 1.5; // niet gegeven maar ik heb een bovengrens nodig
		
	
	/**
	 * Method to create a new worm int he given world at a random but valid location, 
	 * with a random direction and radius, and a random name "wormXYZ" with X,Y,Z 3 random integers.
	 */
	public Worm addNewWorm(){
		double radius = Math.random()*1.25 + 0.25;
		double direction = Math.random()*Math.PI*2;
		Random r = new Random();
		char c = (char)(r.nextInt(26) + 'a');
		char b = (char)(r.nextInt(26) + 'a');
		String name = "Worm" + b + c;
		double[] position = findPosition(radius);
		while (position  == null){
			position =findPosition(radius);
		}
		return new Worm(this, position[0], position[1], direction, radius, name );
		
	}
	
	public double[] findPosition(double radius){
		double x = Math.random()*this.width;
		double y  =Math.random()*this.height;
		if (x>= y){
			y = 0;
		}
		else{
			x = 0;
		}
		double centerX = this.width/2;
		double centerY = this.height/2;
		double distance = distanceBetweenPoints(x, y, centerX, centerY);
		double xStep = (centerX - x)/distance;
		double yStep = (centerY - y)/distance;
		while(!isAdjacentPosition(x, y, radius) && !positionOnMap(x, y, radius)){
			x += xStep;
			y += yStep;
		}
		if (isAdjacentPosition(x, y, radius)){
			double[] position = {x, y};
			return position;
		}
		else{
			return null;
		}
	}
	
	public Food addNewFood(){
		double radius = 0.20;
		double[] position = findPosition(radius);
		while(position == null){
			position = findPosition(radius);
		}
		return new Food(this, position[0], position[1]);
	}
	
	/**
	 * method to check if a given location is situated on the game map 
	 * @param x
	 * @param y
	 * @return
	 */
	
	public boolean positionOnMap(double x, double y, double radius){
		if (Util.fuzzyGreaterThanOrEqualTo(x, 0) && Util.fuzzyGreaterThanOrEqualTo(y, 0) 
				&& Util.fuzzyLessThanOrEqualTo(x, width) && Util.fuzzyLessThanOrEqualTo(y, height)) {
			for(int i = 0; i<21; i++){
				double newPointX = x +  radius * Math.cos(i * 2 * Math.PI/20);
				double newPointY = y + radius * Math.sin(i * 2 * Math.PI/20);
				if (!Util.fuzzyGreaterThanOrEqualTo(newPointX, 0) ||  !Util.fuzzyGreaterThanOrEqualTo(newPointY, 0) 
				|| !Util.fuzzyLessThanOrEqualTo(newPointX, this.width) || !Util.fuzzyLessThanOrEqualTo(newPointY, this.height)){
					return false;
				}
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Return the active projectile of this world.
	 */
	public Projectile getActiveProjectile() {
		return this.activeProjectile;
	}

	public void setActiveProjectile(final Projectile projectile) {
		this.activeProjectile = projectile;
	}

	/**
	 * Variable registering the active projectile of this world
	 */
	private Projectile activeProjectile = null;
	
	public boolean gameFinished(){
		if (this.WormsInWorld.size() == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Worm getWinner(){
		if (this.gameFinished()){
			return this.WormsInWorld.get(0);
		}
		else{
			return null;
		}
	}
	
	public int wormNummer = 0;
	
	public void startGame(){
		this.WormsInWorld.get(wormNummer).setIsActive(true);
	}
	
	public void nextTurn(){
		if (this.wormNummer < this.WormsInWorld.size()){
			this.WormsInWorld.get(this.wormNummer).setIsActive(false);
			this.wormNummer += 1;
			this.WormsInWorld.get(this.wormNummer).setIsActive(true);
		}
		
		else{
			this.WormsInWorld.get(this.wormNummer).setIsActive(false);
			this.wormNummer = 0;
			this.WormsInWorld.get(this.wormNummer).setIsActive(true);
		}
	}
	
	 public Worm getCurrentWorm(){
		 for (Worm worm : this.WormsInWorld){
			 if (worm.getActive()){
				 return worm;
			 }
		 }
		 return null;
	 }

}
