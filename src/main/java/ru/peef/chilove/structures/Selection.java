package ru.peef.chilove.structures;

public class Selection {
    private int firstPosLocationX;
    private int firstPosLocationY;
    private int firstPosLocationZ;
    private int secondPosLocationX;
    private int secondPosLocationY;
    private int secondPosLocationZ;
    public Selection() {}

    public void setPosition(boolean first, String cordinate, int position) {
        if (first) {
            switch (cordinate) {
                case "x": firstPosLocationX = position; break;
                case "y": firstPosLocationY = position; break;
                case "z": firstPosLocationZ = position; break;
            }
        } else {
            switch (cordinate) {
                case "x": secondPosLocationX = position; break;
                case "y": secondPosLocationY = position; break;
                case "z": secondPosLocationZ = position; break;
            }
        }
    }

    public int getFirstPositionX() { return firstPosLocationX; }
    public int getFirstPositionY() { return firstPosLocationY; }
    public int getFirstPositionZ() { return firstPosLocationZ; }
    public int getSecondPositionX() { return secondPosLocationX; }
    public int getSecondPositionY() { return secondPosLocationY; }
    public int getSecondPositionZ() { return secondPosLocationZ; }
}
