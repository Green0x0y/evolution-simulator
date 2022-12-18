package agh.ics.opp.elements;

import agh.ics.opp.MapDirection;
import agh.ics.opp.Vector2d;
import agh.ics.opp.behaviours.IGeneSelector;
import agh.ics.opp.maps.IAnimalPositionCorrector;

import java.util.concurrent.ThreadLocalRandom;

public class Animal extends AbstractMapElement{
    private final int[] genome;
    private final int fullEnergy;
    private final int energyConsumption;

    private Vector2d position;
    private Integer energy;
    private MapDirection direction;
    private int nextGeneIndex;

    //stats
    private int numOfDescendants = 0;
    private int numOfEatenPlants = 0;
    private int age = 0;
    //stats end

    private final IAnimalPositionCorrector corrector;
    private final IGeneSelector selector;

    public Animal(Vector2d position, Integer initEnergy, int fullEnergy, int energyConsumption, int[] genome,
                  IAnimalPositionCorrector corrector, IGeneSelector selector){
        this.position = position;
        this.energy = initEnergy;
        this.fullEnergy = fullEnergy;
        this.energyConsumption = energyConsumption;
        this.genome = genome;
        this.corrector = corrector;
        this.selector = selector;
        this.nextGeneIndex = ThreadLocalRandom.current().nextInt(0, genome.length);
        this.direction = MapDirection.getRandom();
    }
    
    public void makeMove(){
        this.turnBy(genome[nextGeneIndex]);
        this.position = this.position.add(this.direction.toUnitVector());
        corrector.correctAnimalPosition(this);
        this.updateNextGeneIndex();
    }

    // direction
    private void turnBy(int rotation){
        this.direction = direction.rotate(rotation);
    }
    public void turnBack(){
        this.turnBy(4);
    }
    // direction end

    // position
    public void setPosition(Vector2d position){
        this.position = position;
    }
    // position end

    // nextGeneIndex
    private void updateNextGeneIndex() {
        this.nextGeneIndex = selector.getNextGeneIndex(this.nextGeneIndex);
    }
    // nextGeneIndex end

    // energy
    public void reduceEnergy(Integer energy){
        this.energy = Math.max(this.energy-energy, 0);
    }
    public void increaseEnergy(Integer energy){
        this.energy += energy;
    }
    public boolean isBreedable(){
        return this.energy >= this.fullEnergy;
    }
    // energy end
    public void hasEaten(int gainedEnergy){
        increaseEnergy(gainedEnergy);
        numOfEatenPlants++;
    }
    public void hasBred(){
        reduceEnergy(energyConsumption);
        numOfDescendants++;
    }
    public void hasLivedDay(){
        reduceEnergy(1);
        age++;
    }

    public int getNumOfDescendants() {
        return numOfDescendants;
    }
    public int getAge() {
        return age;
    }
    public int getEnergyConsumption(){
        return this.energyConsumption;
    }
    public MapDirection getDirection(){
        return this.direction;
    }
    public int[] getGenome(){
        return this.genome;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    public int getEnergy(){ return this.energy; }
    public int getNumOfEatenPlants() {
        return numOfEatenPlants;
    }
    public int getNextGeneIndex(){
        return nextGeneIndex;
    }

    public String toString(){
        return energy.toString();
//        return "{"+energy+","+age+","+numOfDescendants+":"+position+"}";
    }
    // gui
    public String getImagePath() {
        return null;
    }
    public String getLabelName() {
        return null;
    }
    // gui end
}
