package com.ssttevee.attendance.data.model;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Participant extends Model
{
    protected String name;
    protected Integer section;
    protected Integer year;

    public Participant()
    {
    }

    public Participant(Entity entity)
    {
        fromEntity(entity);
    }

    public String getName()
    {
        return name;
    }

    public Integer getSection()
    {
        return section;
    }

    public Integer getYear()
    {
        return year;
    }

    static public class Builder
    {
        private Participant model;
        public Builder()
        {
            this.model = new Participant();
        }

        public Builder(Participant model)
        {
            this.model = model;
        }

        public Builder setId(long id)
        {
            this.model.id = id;
            return this;
        }

        public Builder setName(String name)
        {
            this.model.name = name;
            return this;
        }

        public Builder setSection(int section)
        {
            this.model.section = section;
            return this;
        }

        public Builder setYear(int year)
        {
            this.model.year = year;
            return this;
        }

        public Participant build()
        {
            return this.model;
        }

        public Participant get()
        {
            this.model.get();
            return this.model;
        }
    }

    public static class Serializer implements JsonSerializer<Participant>
    {
        @Override
        public JsonElement serialize(Participant src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject retObj = new JsonObject();

            retObj.addProperty("id", src.id);
            retObj.addProperty("name", src.name);
            retObj.addProperty("section", src.section);
            retObj.addProperty("year", src.year);

            return retObj;
        }
    }
}
