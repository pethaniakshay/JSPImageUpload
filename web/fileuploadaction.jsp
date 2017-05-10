<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.File"%>
<%@page import="javazoom.upload.MultipartFormDataRequest"%>
<%
    MultipartFormDataRequest nreq = new MultipartFormDataRequest(request);
    String idname = nreq.getParameter("idname").trim();
    File deleteFile1 = new File(getServletContext().getRealPath("/")+"/uploadedpics/" + idname);
    // check if the file is present or not
    if (deleteFile1.exists()) {
        deleteFile1.delete();
    }
    uploadutilities.FileMover fileMover = new uploadutilities.FileMover();
    fileMover.setNewfilename(idname);
    javazoom.upload.UploadBean upBean = new javazoom.upload.UploadBean();
    upBean.addUploadListener(fileMover);
    upBean.setFolderstore(getServletContext().getRealPath("/")+"/uploadedpics/");
    upBean.setOverwrite(false);
    upBean.store(nreq, "fileField");
    String userphotopath = "uploadedpics/" + idname;
    Thread.sleep(2000L);
    Class.forName("com.mysql.jdbc.Driver");
    Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/img_up","root","");
    PreparedStatement pinsert = connection.prepareStatement("insert into store values(?,?)");
    pinsert.setString(1, idname);
    pinsert.setString(2,userphotopath);
    pinsert.executeUpdate();
    pinsert = connection.prepareStatement("select * from store");
    ResultSet rs = pinsert.executeQuery();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Uploaded file</title>
    </head>
    <body>
        <h1>Uploaded file</h1>
        <form   name="frm" action="fileuploadaction.jsp" method="post" enctype="multipart/form-data">
            <fieldset style="width:600px">
                <legend>
                    <b>User Details</b>
                </legend>
                <table style="width:500px">
                    <tr>
                        <td style="width:30%;">User id/name :</td>
                        <td><%=idname%></td>
                    </tr>
                    <tr>
                        <td>Uploaded Photo :</td>
                        <td><img src="<%=userphotopath%>" style="width:100px; height: 100px"/></td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <fieldset style="width:600px">
            <legend>
                <b>All Users Details</b>
            </legend>
            <table>
                <%
                    while(rs.next()){
                %>
                <tr style="border: 1px solid gainsboro;">
                    <td><%=rs.getString("userid")%></td>
                    <td><img src="<%=rs.getString("photopath")%>" style="width:50px; height: 50px"/></td>
                </tr>
                <%
                    }
                %>
            </table>
        </fieldset>
    </body>
</html>