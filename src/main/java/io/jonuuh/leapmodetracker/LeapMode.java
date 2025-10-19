package io.jonuuh.leapmodetracker;

public enum LeapMode
{
    ARROW,
    ARCED;

    @Override
    public String toString()
    {
        String str = super.toString();
        String firstChar = str.substring(0, 1).toUpperCase();
        String remainingChars = str.substring(1).toLowerCase();
        return firstChar + remainingChars;
    }
}
