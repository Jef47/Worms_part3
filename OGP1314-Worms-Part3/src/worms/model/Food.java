package worms.model;

/**
 * A class of worm food.
 * Each food ration is located in a certain world, at a certain position.
 * The shape of the food rations is a cirkel wit radius 0.2.
 * 
 * @author MArnix Michiel Denys and Jef De Bie
 * 
 * @version 1.0
 */

public class Food {
	
	/**
	 * Set up food
	 * @param world
	 * @param x
	 * @param y
	 */
	
	public Food(World world, double x, double y){
		this.setWorld(world);
		this.setPosition(positionX,positionY);
		this.setRadius(radius);
	}
	
	private World world;
	private double positionX;
	private double positionY;
	private double radius = 0.20;
	
	/**
	 * Set food into a certain world
	 * @param world
	 */
	private void setWorld(World world){
		this.world = world;
		world.addFoodToWorld(this);
	}
	
	/**
	 * Set the position of the food ration
	 * @param positionX
	 * @param positionY
	 */
	
	private void setPosition(double positionX, double positionY){
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	/**
	 * set the radius of the food ration to a certain raidius
	 * @param radius
	 */
	private void setRadius(double radius){
		this.radius = radius;
	}
	
	/**
	 * return in wich world the food is placed
	 * @return world
	 */
	
	public World getWordl(){
		return this.world;
	}
	
	/**
	 * return the position of the food
	 * @return position
	 */
	
	public double[] getPosition(){
		double[] position = {this.positionX , this.positionY};
		return position;
	}
	
	/**
	 * 
	 * @return radius
	 */
	public double getRadius(){
		return this.radius;
	}
	
	private boolean isTerminated = false;
	
	/**
	 * Method to "destroy" a food ration if it gets eaten. 
	 */
	
	public void terminate(){
		this.isTerminated = true;
	}
	
	public boolean isTerminated(){
		return this.isTerminated;
	}

}
