package com.cisco.cmad.api;

public enum Interest {
	TECHNOLOGY,MOVIES,SPORTS;
	public String toString(){
        switch (this) {
            case TECHNOLOGY: return "TECHNOLOGY";
            case MOVIES: return "MOVIES";
            case SPORTS: return "SPORTS";
        }
        return null;
    }
}
