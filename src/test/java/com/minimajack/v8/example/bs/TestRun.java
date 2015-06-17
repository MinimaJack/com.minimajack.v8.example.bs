package com.minimajack.v8.example.bs;

import org.junit.Test;

import com.minimajack.v8.parser.Reader;

public class TestRun
{
    @Test
    public void readFromFile()
    {
        Reader reader = new Reader();
        reader.readVersion( "./ВнешняяОбработкаРеквизитСтрока.epf" );
    }

}
