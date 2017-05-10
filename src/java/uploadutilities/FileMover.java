package uploadutilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Vector;
import javazoom.upload.UploadFile;
import javazoom.upload.UploadListener;
import javazoom.upload.UploadParameters;


public class FileMover implements UploadListener, Serializable
{
  private String prefix = "";
  private String postfix = "";
  private String newfilename = null;
  private Vector filenames = null;
  private boolean copyfile = false;
  private String altfolder = null;
  private char[] inputchar = null;
  private char[] outputchar = null;

  public FileMover()
  {
    filenames = new Vector();
  }

  /**
   * Copy file and rename the copy if enabled. Rename file if disabled.
   * @param b
   */
  public void enableCopy(boolean b)
  {
    copyfile = b;
  }
  /**
   * Set filename prefix.
   * @param prefix
   */
  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
  }

  /**
   * Return filename prefix.
   * @return
   */
  public String getPrefix()
  {
    return prefix;
  }

  /**
   * Set filename postfix.
   * @param postfix
   */
  public void setPostfix(String postfix)
  {
    this.postfix = postfix;
  }

  /**
   * Return filename postfix.
   * @return
   */
  public String getPostfix()
  {
    return postfix;
  }
  /**
   * Set new filename. The uploaded filename will be renamed by given filename.
   * @param newfilename
   */
  public void setNewfilename(String newfilename)
  {
    this.newfilename = newfilename;
  }
  /**
   * Return new filename.
   * @return
   */
  public String getNewfilename()
  {
    return this.newfilename;
  }
  /**
   * Set characters to replace in filename. For instance replace space (" ") by underscore ("_");
   * @param input array of char to be replaced 
   * @param output array of char replacing input
   */
  public void setReplaceChar(char[] input, char[] output)
  {
   this.inputchar = input;
   this.outputchar = output;
  }
  /**
   * Set alternate folder to copy/move the uploaded file.
   * @param postfix
   */
  public void setAltFolder(String folder)
  {
    this.altfolder = folder;
  }
  /**
   * Get alternate folder to copy/move the uploaded file.
   * @return
   */
  public String getAltFolder()
  {
    return altfolder;
  }
  /**
   * UploadListener callback.
   * @param params uploaded parameters.
   * @param file uploaded file.
   */
  public void fileUploaded(UploadParameters params, UploadFile file)
  {
    long filesize = file.getFileSize();
    String contentType = file.getContentType();
    String oldfilename = file.getFileName();
    String altfilename = oldfilename;
    String newfilename = oldfilename;
    if ((inputchar != null) && (outputchar != null) && (inputchar.length==outputchar.length))
    {
     for (int c=0;c<inputchar.length;c++)
     {
   oldfilename = oldfilename.replace(inputchar[c],outputchar[c]);
     }
    }
    if (this.newfilename != null) oldfilename = this.newfilename;
    String currentfolder = params.getStoreinfo();
    // File Location is saved in the upload bean params.getStoreInfo()
    if ((prefix != null) && (prefix.length() > 0))
    {
      oldfilename = prefix + oldfilename;
    }
    if ((postfix != null) && (postfix.length() > 0))
    {
      oldfilename = oldfilename + postfix;
    }
    newfilename = oldfilename;
    try
    {
      if (copyfile == true)
      {
        if (altfolder != null) currentfolder = altfolder;
        byte[] data = file.getData();
        FileOutputStream out = new FileOutputStream(currentfolder + File.separator + newfilename);
        out.write(data);
        out.close();
      }
      else
      {
        File fold = new File(currentfolder + File.separator + file.getFileName());
        if (altfolder != null) currentfolder = altfolder;
        File fnew = new File(currentfolder + File.separator + newfilename);
        fold.renameTo(fnew);
      }
      UploadParameters newparams = new UploadParameters(newfilename, filesize, contentType, params.getStoremodel(),currentfolder,altfilename);

      filenames.add(newparams);
    }
    catch (Exception e)
    {
      System.err.println(e.toString());
    }
    // Remove uploaded file from memory.
    file.reset();
  }
  /**
   * Return renamed filename.
   * @return
   */
  public String getFileName()
  {
    if (filenames.size() > 0)
    {
        UploadParameters up =  (UploadParameters) filenames.elementAt(filenames.size()-1);
        return up.getFilename();
    }
    else return null;
  }
  /**
   * Return renamed filenames (for multiple uploads) as UploadParameters.
   * @return Vector of UploadParameters.
   */
  public Vector getFileNames()
  {
    return filenames;
  }
  public void fileUploadStarted(File file, int contentlength, String contenttype)
  {
  }
  public void dataRead(int read)
  {
  }
}


