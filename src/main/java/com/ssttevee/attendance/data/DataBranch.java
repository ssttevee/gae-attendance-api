package com.ssttevee.attendance.data;

import java.io.IOException;

public interface DataBranch
{
    DataBranch traverse(String path) throws IOException;

    String get(String data) throws IOException;
    String post(String data) throws IOException;
    String patch(String data) throws IOException;
    String put(String data) throws IOException;
    String delete(String data) throws IOException;

    int getStatus();
}
