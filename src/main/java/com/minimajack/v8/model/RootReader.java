package com.minimajack.v8.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.common.io.ByteStreams;
import com.minimajack.v8.format.Container;
import com.minimajack.v8.format.V8File;
import com.minimajack.v8.io.reader.AbstractReader;
import com.minimajack.v8.metadata.root.V8Root;
import com.minimajack.v8.utility.V8Reader;

public class RootReader
    implements AbstractReader, Runnable
{

    /**
     * 
     */
    private Context context;

    private Container container;

    @Override
    public void run()
    {
        try
        {
            container.read();
            container.getFileSystem().read();
            container.getFileSystem().readFiles();
            container.getFileSystem().iterate( new Visitor<V8File>()
            {

                @Override
                public void visit( V8File member )
                {
                    try
                    {
                        member.readHeader( container.getBuffer() );
                        member.readBody( container.getBuffer() );
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }

                }
            } );
            V8File v8File = container.getFileSystem().findFile( "root" );
            InputStream dataStream = v8File.getBody().getDataStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteStreams.copy( dataStream, baos );
            ByteBuffer bb = ByteBuffer.wrap( baos.toByteArray() );
            bb.get( new byte[3] ); //skip BOM
            V8Root rootFile = V8Reader.read( V8Root.class, bb );
            System.out.println( rootFile.guid );
            container.cleanUp();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext()
    {
        return context;
    }

    @Override
    public void setContext( Context context )
    {
        this.context = context;
    }

    @Override
    public void read()
    {
        this.run();
    }

    @Override
    public void setContainer( Container container )
    {
        this.container = container;
    }

}