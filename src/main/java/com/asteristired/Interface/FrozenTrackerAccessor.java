package com.asteristired.Interface;

public interface FrozenTrackerAccessor {
    void setFreeze(boolean frozen); // fun little fact, this has to be setFreeze beacuse setFrozen is used in the game I think, otherwise I have no reason as to why the mobs started taking frost dmg
    boolean isFreeze();
}