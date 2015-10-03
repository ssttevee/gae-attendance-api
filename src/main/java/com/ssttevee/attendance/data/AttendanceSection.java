package com.ssttevee.attendance.data;

public class AttendanceSection extends DataBranchImpl
{

    public AttendanceSection(DataBranchImpl dbi, String lastPart)
    {
        super(dbi, lastPart);
    }

    @Override
    public DataBranch traverse(String path)
    {
        switch (path)
        {
            case "participants":
                return new AttendanceParticipants(this, path);
            case "entries":
            case "dates":
            default:
                return null;
        }
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
