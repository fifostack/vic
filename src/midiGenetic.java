

import java.util.LinkedList;

public class midiGenetic {
	
	LinkedList<NoteObj> melody;
	int octave = 12;
	
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
		LinkedList<NoteObj> n = melody; //create a new melody
		n.add(melody.get(0));
		
		for(int i = 1; i < n.size(); i++) //for every note but the first note
		{
			n[i] = melody[i];
			
			if((Math.abs(NoteObj.distance(melody.get(i), melody.get(i-1))) > octave)) //if the notes are more than an octave away
			{ 
				//n[i] = melody[i];
				
				if(NoteObj.distance(melody.get(i), melody.get(i-1)) > 0) //if its too high
					n[i].setNote(n[i].getNote() - 18);
				else if(NoteObj.distance(melody.get(i), melody.get(i-1)) < 0) //if its too low
					n[i].setNote(n[i].getNote() + 18);
			}
		}
		
		//n[3] = melody[3];
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







