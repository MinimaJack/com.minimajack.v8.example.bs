package com.minimajack.v8.parser;

import java.io.File;


public class MainApp
{

    public static void main( String[] args )
    {
        if ( args.length != 1 )
        {
            System.out.println( "Usage %file% " );
            return;
        }
        long times = System.currentTimeMillis();
        File fileInput = new File( args[0] );
        if ( !fileInput.exists() )
        {
            throw new RuntimeException( "File not exsist" );
        }
        if ( fileInput.isFile() )
        {
            Reader reader = new Reader();
            reader.readVersion( args[0] );
        }
        else
        {
            System.out.println( "Usage %file%" );
        }

        System.out.println( "Time: " + (int) ( System.currentTimeMillis() - times ) / 1000 );
    }
}
