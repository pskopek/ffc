package org.fc.entity.report;

public class Boats {
    public int round;
    public String sector;
    public int location;
    public String front;
    public String rear;
    public String referee;

    public String toString() {
        return round + ", " + sector + ", " + location + ", " + front + ", " + rear + ", " + referee;
    }
}
