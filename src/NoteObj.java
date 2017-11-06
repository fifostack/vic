

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
  
  public int distance(NoteObj a, NoteObj b) //get distance between two notes
  {
	int aInd = -1, bInd = -1; 
	int d = 0;
	for(int i = 0; i < Note.Notes.length; i++)
	{
		if(Note.Notes[i] == a.note) // if we find the correct note, store the index
		{
			aInd = i;
		}
		if(Note.Notes[i] == b.note)
		{
			bInd = i;
		}
		
		d = bInd - aInd;
	}
	
	if(aInd == -1 || bInd == -1) {return -999;} //if returns -999, the notes are invalid
	return d;
  }
}
