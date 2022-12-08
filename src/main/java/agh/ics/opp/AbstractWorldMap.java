package agh.ics.opp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Plant> plants = new HashMap<>();
    public final static Vector2d lowerLeft = new Vector2d(0 ,0);
    private final Vector2d upperRight;
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight;
    }

    abstract public boolean canMoveTo(Vector2d position);

    @Override
    public void placeMapElement(IMapElement element) {
        if(element instanceof Animal animal)
            if(animals.get(animal.getPosition()) == null) {
                animals.put(animal.getPosition(), new ArrayList<Animal>(){{add(animal);}});
            }
            else{
                animals.get(animal.getPosition()).add(animal);
            }
        else if (element instanceof Plant plant)
            plants.put(plant.getPosition(), plant);
    }

    public void removeMapElement(Vector2d position, IMapElement element){
        if(element instanceof Animal animal){
            animals.get(position).remove(animal);
            if(animals.get(position).isEmpty()){
                animals.remove(position);
            }
        }
        else if (element instanceof Plant plant){
            plants.remove(position);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animals.get(position) != null){
            return animals.get(position).get(0);
        }
        return plants.get(position);
    }

    @Override
    public List<Animal> getAnimals() {
        List<Animal> animalList = new ArrayList<>();
        for (List<Animal> list: this.animals.values())
            animalList.addAll(list);
        return animalList;
    }

    @Override
    public List<Plant> getPlants(){
        return new ArrayList<>(plants.values());
    }

    @Override
    public final Vector2d getLowerLeft() { return lowerLeft; }

    @Override
    public Vector2d getUpperRight(){
        return upperRight;
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }

    abstract public void adjustAnimalPosition(Vector2d oldPosition, Animal animal);
    @Override
    public void positionChanged(Vector2d oldPosition, Animal animal){
        this.removeMapElement(oldPosition, animal);
        this.placeMapElement(animal);
    }
}
