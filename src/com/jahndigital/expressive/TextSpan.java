package com.jahndigital.expressive;

/**
 * Basic class that represents the starting and ending point of a specific error in the syntax.
 */
public final class TextSpan {
    private final int _start;
    private final int _length;

    public int getStart()
    {
        return _start;
    }

    public int getLength()
    {
        return _length;
    }

    public TextSpan(int start, int length)
    {
        this._start = start;
        this._length = length;
    }
}
