package com.ssttevee.attendance.data;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gson.JsonSyntaxException;
import com.ssttevee.attendance.data.model.Participant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceParticipants extends DataBranchImpl
{

    public AttendanceParticipants(DataBranchImpl dbi, String lastPart)
    {
        super(dbi, lastPart);
    }

    @Override
    public DataBranch traverse(String path) throws IOException
    {
        try
        {
            return new AttendanceParticipant(this, Long.parseLong(path));
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    @Override
    public String get(String data)
    {
        List<Participant> participants = new ArrayList<>();

        Query q = new Query("Participant");
        PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);

        for(Entity entity : pq.asIterable())
        {
            participants.add(new Participant(entity));
        }

        return Attendance.gson.toJson(participants);
    }

    @Override
    public String post(String data)
    {
        try
        {
            Participant participant = Attendance.gson.fromJson(data, Participant.class);
            if(participant.insert())
                return Attendance.gson.toJson(participant);
        }
        catch (JsonSyntaxException e)
        {
            setStatus(400);
            return "";
        }

        return "";
    }

    @Override
    public String patch(String data)
    {
        setStatus(405);
        return "";
    }

    @Override
    public String put(String data)
    {
        setStatus(405);
        return "";
    }

    @Override
    public String delete(String data)
    {
        setStatus(405);
        return "";
    }
}
