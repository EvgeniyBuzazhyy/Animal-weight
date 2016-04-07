package com.example.evgeniy_pc.weightinganimal;

import java.util.ArrayList;

public class Data
{
    public String Name;
    public ArrayList<String> gropAndChild = new ArrayList<String>();

    // Constructor
    public Data(String Name)
    {
        this.Name=Name;
    }

    // Method return name
    @Override
    public String toString()
    {
        return Name;
    }
}
