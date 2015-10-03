package com.ssttevee.attendance.data;

import com.ssttevee.attendance.data.model.Participant;

import java.io.IOException;

public class AttendanceParticipant extends DataBranchImpl
{
    private long id;

    public AttendanceParticipant(DataBranchImpl dbi, long lastPart)
    {
        super(dbi, lastPart + "");
        this.id = lastPart;
    }

    @Override
    public DataBranch traverse(String path) throws IOException
    {
        return null;
    }

    @Override
    public String get(String data)
    {
        Participant p = new Participant.Builder().setId(id).get();
        return Attendance.gson.toJson(p);
    }

    @Override
    public String post(String data)
    {
        setStatus(405);
        return "";
    }

    @Override
    public String patch(String data)
    {
        Participant p = new Participant.Builder(Attendance.gson.fromJson(data, Participant.class)).setId(id).build();

        if(p.patch())
            setStatus(201);
        else
            setStatus(400);

        return "";
    }

    @Override
    public String put(String data)
    {
        Participant p = new Participant.Builder(Attendance.gson.fromJson(data, Participant.class)).setId(id).build();

        if(p.put())
            setStatus(201);
        else
            setStatus(400);

        return "";
    }

    @Override
    public String delete(String data)
    {
        Participant p = new Participant.Builder()
                .setId(id)
                .build();

        if(p.delete())
            setStatus(204);
        else
            setStatus(400);

        return "";
    }
}
