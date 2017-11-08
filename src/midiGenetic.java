

import java.util.LinkedList;

public class midiGenetic {
	
	LinkedList<NoteObj> melody;
	int octave = 18;
	
	public midiGenetic(LinkedList<NoteObj> m)
	{
		melody = m;
	}
	
	public void setMelody(LinkedList<NoteObj> m)
	{
		melody = m;
	}
	
	public LinkedList<NoteObj> refactor() //primitive melody altering method
	{
		LinkedList<NoteObj> n = new LinkedList<NoteObj>(); //create a new melody
		n.add(melody.get(0));
		
		for(int i = 1; i < melody.size(); i++) //for every note but the first note
		{
			int dis = NoteObj.distance(melody.get(i), n.get(i-1)); //distance between the notes
			
			if((Math.abs(dis)) > octave) //if the notes are more than an octave away
			{ 
				NoteObj temp = melody.get(i);
				if(dis > 0) //if its too high, pull it down an octave
				{
					temp = new NoteObj((melody.get(i).getNote()) - 18,melody.get(i).getLength());
					n.add(temp);
				}
				else if(dis < 0) //if its too low, push it up an octave
				{
					temp = new NoteObj((melody.get(i).getNote()) + 18,melody.get(i).getLength());
					n.add(temp);
				}
				else
				{
					System.out.println();
				}
			}
			else
			{
				n.add(melody.get(i));
			}
		}
		
		return n;//return the new melody
	}
	
	/*public int prevNote(int now)
	{
			for(int i = now; i >= 0; i--)
			{
				if(melody[i] != null)
				{
						return i;
				}
			}
			return -1;
	}
	*/
	
}







