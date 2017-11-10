

import java.util.LinkedList;

public class midiGenetic {
	
	LinkedList<LinkedList<NoteObj>> population;
	int generations;
	private int popSize;
	int ticks; //length of each individual in ticks
	int octave = 18;
	int fitness;
	
	public midiGenetic()
	{
		population = new LinkedList<LinkedList<NoteObj>>();
		ticks = 64;
		popSize = 100;
	}
	
	public midiGenetic(int n, int t)
	{
		population = new LinkedList<LinkedList<NoteObj>>();
		ticks = t;
		popSize = n;
	}
	
	public void init() //initializes population with popSize Linked Lists of melodies
	{
		for(int i = 0; i < popSize; i++)
		{
			population.add(new LinkedList<NoteObj>());
		}
		
		for(LinkedList<NoteObj> i : population)
		{
			i = createMelody(ticks);
		}
	}
	
	public void selection()
	{
		
	}
	
	public void crossover()
	{
		
	}
	
	public void mutation()
	{
		
	}

	public LinkedList<NoteObj> createMelody(int ticks)
	{
		LinkedList<NoteObj> temp = new LinkedList<NoteObj>();
		int rNote = 0, rDur = 0, curTotal = 0;
		while(curTotal < ticks)
		{
			rNote = (int)(Math.random()*96);
			//rDur = (int)((Math.random()*15)+1);
			rDur = 4;   //set the length of the note in ticks (16 - whole note)
      
			NoteObj n = new NoteObj(Note.Notes[rNote], rDur); //make a note from generated values
			temp.add(n);                             	  //add it to the melody
			curTotal+= rDur;                                  //add to total ticks
      
		}
		return temp;
	}
	
	
}







