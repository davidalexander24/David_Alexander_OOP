package com.david.frontend.observers;

import java.util.*;

public class ScoreManager implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private int score = 0;

    public ScoreManager(){
        observers = new ArrayList<>();
        score = 0;
    }

    public void addObserver(Observer observer){
        observers.add(observer);

    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int score) {
        observers.listIterator(observer.update(score))
    }

    public void setScore(int newScore) {
        if (newScore != score) {
            score = newScore;
            notifyObservers(score);
        }
    }

    public int getScore() {
        return score;
    }

}
