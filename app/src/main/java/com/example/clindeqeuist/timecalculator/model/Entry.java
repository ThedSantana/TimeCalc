package com.example.clindeqeuist.timecalculator.model;

public class Entry
{

    private String description;
    private Integer value;


    public Entry(String description, Integer value)
    {
        this.description = description;
        this.value = value;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public Integer getValue()
    {
        return value;
    }


    public void setValue(Integer value)
    {
        this.value = value;
    }

}
