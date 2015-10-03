package com.ssttevee.attendance.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DataBranchImpl implements DataBranch
{
    public static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    protected final PrintWriter writer;
    protected final String[] parts;
    protected int status = 200;

    public DataBranchImpl(DataBranchImpl dbi, String lastPart)
    {
        this(dbi.writer, dbi.parts, lastPart);
    }

    public DataBranchImpl(PrintWriter writer, String path)
    {
        this.writer = writer;
        List<String> partList = new ArrayList<>();
        for(String part : path.split("/")) {
            if(part.length() <= 0)
                continue;
            partList.add(part);
        }
        this.parts = partList.toArray(new String[partList.size()]);
    }

    public DataBranchImpl(PrintWriter writer, String... parts)
    {
        this.writer = writer;
        this.parts = parts;
    }

    public DataBranchImpl(PrintWriter writer, String[] parts, String lastPart)
    {
        this.writer = writer;
        List<String> partList = new ArrayList<>(Arrays.asList(parts));
        partList.add(lastPart);
        this.parts = partList.toArray(new String[partList.size()]);
    }

    public int getStatus()
    {
        return this.status;
    }

    protected void setStatus(int status)
    {
        this.status = status;
    }
}
