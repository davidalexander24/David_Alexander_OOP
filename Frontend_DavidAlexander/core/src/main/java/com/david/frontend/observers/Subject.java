package com.david.frontend.observers;

public interface Subject {
    public void addObserver(Observer observer);
    public void removeObserver (Observer observer);
    public void notifyObservers (int score);
}
