

import java.util.LinkedList;

public class midiGenetic {
	
	LinkedList<LinkedList<NoteObj>> population;
	private int[] fitness; //stores fitness of each individual
	private int popSize; //size of each generation
	private static int maxFitness = 31; // maximum possible fitness (at this point)
										// 16 notes in key, each within octave of the first
										
	int gens; //number of generations
	int ticks; //length of each individual in ticks
	int octave = 12; //size of an octave
	
	public midiGenetic() //default constructor
	{
		population = new LinkedList<LinkedList<NoteObj>>();
		ticks = 65;
		popSize = 100;
		fitness = new int[popSize];
	}
	
	public midiGenetic(int n, int t)
	{
		population = new LinkedList<LinkedList<NoteObj>>();
		ticks = t;
		popSize = n;
		fitness = new int[popSize];
	}
	
	public void init() //initializes population with popSize Linked Lists of melodies
	{
		for(int i = 0; i < popSize; i++)
		{
			population.add(new LinkedList<NoteObj>());//create each individual
		}
		
		for(int i = 0; i < popSize; i++)
		{
			population.set(i, createMelody(ticks));
			fitness[i] = calcFit(population.get(i));
			//System.out.println(calcFit(population.get(i)));
		}
	}
	
	public int calcFit(LinkedList<NoteObj> no) //calculates the fitness of an individual
	{
		int keyFit = 0;
		int octFit = 0;
		int totalFit = 0;
		int refNote = no.get(1).getNote();//second note is used as a reference note
		int keyNote = no.get(0).getNote();//first note sets the key
		
		int[] KEY = Note.getKey(keyNote); //get the lists of notes in this individual's key
		
	 ///*---- Give fitness points based on notes in key *///
		for(NoteObj i : no) //for each note
		{
				for(int k : KEY) //check the array of acceptable in-key notes
				{
						if(i.getNote() == k)//if the note is in key, add fitness
							keyFit++;
				}
		}
	 ///*---- Give fitness points based on notes within one octave of reference note *///
		for(int i = 2; i < no.size(); i++)
		{
			int dis = NoteObj.distance(no.get(i),no.get(i-1));
			if(dis < octave)
			{
				octFit++;
			}
		}
		
		totalFit = keyFit + octFit;
		return totalFit;
	}
	
	
	public void selection()
	{
		//select individuals based on fitness
	}
	
	public void crossover()
	{
		//swap the notes of the parent melodies
	}
	
	public void mutation()
	{
		//mutate the notes, tending slightly to mutating "bad" notes
	}
	
	public LinkedList<NoteObj> getBest() //returns the melody with the highest fitness
	{
		int max = -999;
		int maxInd = 0;
		
		for(int i = 0; i < fitness.length; i++)
		{
			if(fitness[i] > max)
			{
				//System.out.println("current max: " + max);
				max = fitness[i];
				maxInd = i;
			}
		}
		System.out.println("Best found at index: " + maxInd + " with fitness: " + max);
		return population.get(maxInd);
	}
	
	public int getFit(int n)
	{
		return fitness[n];
	}
	
	public LinkedList<NoteObj> createMelody(int ticks) //generates a single random melody
	{
		LinkedList<NoteObj> temp = new LinkedList<NoteObj>();
		int rNote = 0, rDur = 0, curTotal = 0;
		
		rNote = (int)(Math.random()*96);
		NoteObj keyN = new NoteObj(Note.Notes[rNote], 1); //first note determines key, and is not played
		
		temp.add(keyN);
		
		while(curTotal < ticks) //fill the list with random notes until we reach the desired length
		{
			rNote = (int)(Math.random()*96);
			//rDur = (int)((Math.random()*15)+1);
			rDur = 4;   //set the length of the note in ticks (16 - whole note)
      
			NoteObj n = new NoteObj(Note.Notes[rNote], rDur); //make a note from generated values
			temp.add(n);                             	      //add it to the melody
			curTotal+= rDur;                                  //add to total ticks
      
		}
		return temp;
	}
	
	
}







