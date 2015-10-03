package com.ssttevee.attendance.web;

import com.ssttevee.attendance.data.DataBranch;
import com.ssttevee.attendance.data.Attendance;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AttendanceServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
    {
        PrintWriter writer = resp.getWriter();

        String[] parts = req.getPathInfo().split("/");

        DataBranch db = new Attendance(writer);

        for (String part : parts)
        {
            if (part.length() <= 0)
                continue;

            db = db.traverse(part);

            if(db == null)
            {
                resp.sendError(404, req.getPathInfo() + " was not found.");
                return;
            }
        }

        Method method;
        try
        {
            method = db.getClass().getMethod(req.getMethod().toLowerCase(), String.class);
        }
        catch (NoSuchMethodException e)
        {
            resp.sendError(405, e.getMessage());
            return;
        }

        try
        {
            String response = (String) method.invoke(db, getBody(req));
            resp.setStatus(db.getStatus());

            if(response == null)
            {
                resp.setStatus(204);
                return;
            }

            writer.write(response);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
            return;
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
            return;
        }
    }

    public static String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
