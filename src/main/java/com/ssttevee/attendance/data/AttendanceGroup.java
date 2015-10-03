package com.ssttevee.attendance.data;

import java.io.IOException;

public class AttendanceGroup extends DataBranchImpl
{
    public AttendanceGroup(DataBranchImpl dbi, String lastPart)
    {
        super(dbi, lastPart);
    }

    @Override
    public DataBranch traverse(String path) throws IOException
    {
        // All paths at this level must be sections
        return new AttendanceSection(this, path);
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
