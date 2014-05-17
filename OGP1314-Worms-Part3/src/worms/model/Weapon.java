package worms.model;

public class Weapon{
	public Weapon(World world, Worm worm, double mass, double cost, double damage, double minForce, double maxForce, String name) {
		this.setWorld(world);
		this.setWorm(worm);
		this.setMass(mass);
		this.setCost(cost);
		this.setDamage(damage);
		this.setForce(minForce, maxForce);
		this.setName(name);
	}
	
	private World world;
	private Worm worm;
	
	
	private void setWorld(World world) {
		this.world = world;
	}
	
	public World getworld() {
		return this.world;
	}
	
	private void setWorm(Worm worm) {
		this.worm = worm;
	}
	
	public Worm getWorm() {
		return this.worm;
	}
	
	private String name;
	
	private void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	private void setMass(double mass) {
		this.mass= mass;
	}
	
	public double getMass(){
		return this.mass;
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
	
	private void setForce(double minForce, double maxForce) {
		this.minForce = minForce;
		this.maxForce = maxForce;
	}
	
	public double getMinForce() {
		return this.minForce;
	}
	
	public double getMaxForce() {
		return this.maxForce;
	}
	
	private double mass;
	private double cost;
	private double damage;
	private double minForce;
	private double maxForce;
	
	public void shoot(int yield) {
		Projectile shot = new Projectile(this.getworld(), this.getWorm(), this.getMass(), this.getCost(), this.getDamage(), this.getMinForce(), this.getMaxForce(), yield);
		shot.jump(0.01);
	}
	
	
}
