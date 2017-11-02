

public class NoteObj
{
  private int note;
  private int length;
  
  public NoteObj()
  {
    note = Note.c3;
    length = 4;
  }
  
  public NoteObj(int n, int l)
  {
    note = n;
    length = l;
  }
  
  public int getNote()
  {
    return note;
  }
  
  public int getLength()
  {
    return length;
  }
  
  public void setNote(int n)
  {
    note = n;
  }
  
  public void setLength(int l)
  {
    length = l;
  }
  
  public String toString()
  {
    return "(" + note + ", " + length + ")"; 
  }
}
