package com.minimajack.v8.parser;

import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import com.minimajack.v8.format.Container;
import com.minimajack.v8.model.RootReader;
import com.minimajack.v8.model.Context;
import com.minimajack.v8.utility.V8Reader;

public class Reader
{

    public Container container;

    public final void readVersion( String string )
    {
        V8Reader.init();
        try (RandomAccessFile aFile = new RandomAccessFile( string, "r" ); FileChannel inChannel = aFile.getChannel();)
        {
            MappedByteBuffer buffer = inChannel.map( FileChannel.MapMode.READ_ONLY, 0, inChannel.size() );
            buffer.order( ByteOrder.LITTLE_ENDIAN );

            Context root = new Context();
            root.setName( "root" );
            root.setReader( RootReader.class );
            root.setInflated( true );
            this.container = new Container( buffer );
            this.container.setContext( root );
            root.parseContainer( container );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
