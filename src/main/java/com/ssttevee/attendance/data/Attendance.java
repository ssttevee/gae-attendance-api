package com.ssttevee.attendance.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssttevee.attendance.data.model.Participant;

import java.io.PrintWriter;

public class Attendance extends DataBranchImpl
{
    public static final Gson gson;
    static {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Participant.class, new Participant.Serializer());

        gson = gb.create();
    }

    public Attendance(PrintWriter writer, String... parts)
    {
        super(writer, parts);
    }

    @Override
    public DataBranch traverse(String path)
    {
        // All paths at this level must be groups
        return new AttendanceGroup(this, path);
    }

    @Override
    public String get(String data)
    {
        return null;
    }

    @Override
    public String post(String data)
    {
        return null;
    }

    @Override
    public String patch(String data)
    {
        return null;
    }

    @Override
    public String put(String data)
    {
        return null;
    }

    @Override
    public String delete(String data)
    {
        return null;
    }

}
