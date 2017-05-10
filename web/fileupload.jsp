<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload a file</title>
    </head>
    <body>
        <h1>Upload a file</h1>
        <form   name="frm" action="fileuploadaction.jsp" method="post" enctype="multipart/form-data">
            <fieldset style="width:600px">
                <legend>
                    <b>Upload Photo</b>
                </legend>
                <table style="width:500px">
                    <tr>
                        <td style="width:30%;">Enter id/name :</td>
                        <td>
                            <input type="text" id="idname" name="idname" />
                        </td>
                    </tr>
                    <tr>
                        <td>Upload Photo :</td>
                        <td>
                            <input type="file" id="fileField" name="fileField" multiple  />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="align:center;">
                            <input type="submit" value="Upload"  />
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </body>
</html>