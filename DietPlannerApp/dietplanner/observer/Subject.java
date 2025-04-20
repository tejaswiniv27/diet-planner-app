package dietplanner.observer;

import java.util.ArrayList;
import java.util.List;

// Subject interface for Observer Pattern
public class Subject {
  private List<Observer> observers = new ArrayList<>();

  public void attach(Observer observer) {
    observers.add(observer);
  }

  public void detach(Observer observer) {
    observers.remove(observer);
  }

  public void notifyObservers(Object data) {
    for (Observer observer : observers) {
      observer.update(data);
    }
  }
}