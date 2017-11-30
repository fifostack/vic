import java.util.LinkedList;

public class midiGenetic {
 
 public LinkedList<LinkedList<NoteObj>> population;
 LinkedList<LinkedList<NoteObj>> newPop;
 private int[] fitness; //stores fitness of each individual
 private int POPSIZE; //size of each generation
 private static int maxFitness = 31; // maximum possible fitness (at this point)
          // 16 notes in key, each within octave of the first
 
 int maxInd;
          
 int gens; //number of generations
 int ticks; //length of each individual in ticks
 int octave = 12; //size of an octave
 
 public midiGenetic() //default constructor
 {
  population = new LinkedList<LinkedList<NoteObj>>();
  ticks = 65;
  POPSIZE = 100;
  fitness = new int[POPSIZE];
 }
 
 public midiGenetic(int n, int t)
 {
  population = new LinkedList<LinkedList<NoteObj>>();
  ticks = t;
  POPSIZE = n;
  fitness = new int[POPSIZE];
 }
 
 public void init() //initializes population with popSize Linked Lists of melodies
 {
  for(int i = 0; i < POPSIZE; i++)
  {
   population.add(new LinkedList<NoteObj>());//create each individual
  }
  
  for(int i = 0; i < POPSIZE; i++)
  {
   population.set(i, createMelody(ticks, Note.eF3));
   fitness[i] = calcFit(population.get(i));
   //System.out.println(calcFit(population.get(i)));
  }
 }
 
 public void runGenetics(int generations)
 {
   for(int i = 0; i < generations; i++)
   {
     System.out.println("Selecting...");
     selection();
     System.out.println("Performing crossover...");
     crossover();
     System.out.println("Mutating...");
     mutation();
     newGen();
     getBest();
     System.out.println("Best found at index: " + maxInd + " with fitness: " + fitness[maxInd] + " and length: " + getLength(getBest()));
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
  
  //System.out.println("Octave Fitness: " + octFit + "Key FItness: " + keyFit);
  
  totalFit = keyFit + octFit;
  return totalFit;
 }
 
 ///SELECTION-----------------------------------------------------------------------
 public void selection()
 {
  //select individuals based on fitness
  newPop = new LinkedList<LinkedList<NoteObj>>(); //initialize the new population list
  
  int size = POPSIZE;
  double rand = 0;
  boolean isSelect = false;
  for(int i = 0; i < (int)(Math.ceil(POPSIZE/2)); i++)
  {
    newPop.add(getBest());
    population.remove(maxInd);
    size--;
    isSelect = false;
    for(int j = 0; j < size; j++)
    {
      rand = Math.random();
      if(rand <= (fitness[j]/(maxFitness*1.0))) //higher firness means higher selection chance
      {
        newPop.add(population.get(j));
        population.remove(j);
        isSelect = true;
        break;
      }
    }
    if(!isSelect)
    {
      newPop.add(population.get(size-1));
      population.remove(size-1);
      isSelect = true;
    }
    size--;
    
    
  }
  
  for(int k = 0; k < newPop.size(); k++)
    {
      fitness[k] = calcFit(newPop.get(k));
    }
  
 }
 ///SELECTION END-------------------------------------------------------------------
 
 ///CROSSOVER-----------------------------------------------------------------------
 public void crossover()
 {
   LinkedList<NoteObj> p1, p2, newP1, newP2;
   double cPoint; //crossover point
   //swap the notes of the parent melodies
   
   for(int i = 1; i<newPop.size(); i+=2) //for every two melodies
   {
     p1 = newPop.get(i-1); //grab the two parents for crossover
     p2 = newPop.get(i);
     int temp;
     
     newP1 = new LinkedList<NoteObj>();
     newP2 = new LinkedList<NoteObj>();
     
     if(p1.size() < p2.size())
     {
       temp = p2.size();
       cPoint = Math.random() * p1.size() + 1;
     }
     else
     {
       temp = p1.size();
       cPoint = Math.random() * p2.size() + 1;
     }

     
     for(int j = 0; j < temp; j++)
     {
       if(j < (int)cPoint)
       {
        if(p1.peekFirst() != null)
          newP1.add(p1.removeFirst());
        if(p2.peekFirst() != null)
          newP2.add(p2.removeFirst());
       }
       else //swap insertion after the crossover point
       {
         if(p2.peekFirst() != null)
           newP1.add(p2.removeFirst());
         if(p1.peekFirst() != null)
           newP2.add(p1.removeFirst());
       }
     }
     
     newPop.set(i-1, newP1); //insert the new parents
     newPop.set(i, newP2); 
     
   }
   
   for(int k = 0; k < newPop.size(); k++)
    {
      fitness[k] = calcFit(newPop.get(k));
    }
   
   
   
 }
 ///CROSSOVER END-------------------------------------------------------------------
 
 ///MUTATION------------------------------------------------------------------------
 public void mutation()
 {
   LinkedList<NoteObj> melody, newMelody;
   
   for(int i = 0; i<newPop.size(); i++)
   {
     melody = newPop.get(i);
     newMelody = new LinkedList<NoteObj>(); //create a new melody
     newMelody.add(melody.get(0)); //copy key note
     newMelody.add(melody.get(1));
     
     for(int j = 2; j < melody.size(); j++) //for every note but the first note
     {
       int dis = NoteObj.distance(melody.get(j), newMelody.get(j-1)); //distance between the notes
       
       if((Math.abs(dis)) > octave) //if the notes are more than an octave away
       { 
         NoteObj temp = melody.get(j);
         if(dis > 0) //if its too high, pull it down an octave
         {
           temp = new NoteObj((melody.get(j).getNote()) - 0x0C,melody.get(j).getLength());
           newMelody.add(temp);
         }
         else if(dis < 0) //if its too low, push it up an octave
         {
           temp = new NoteObj((melody.get(j).getNote()) + 0x0C,melody.get(j).getLength());
           newMelody.add(temp);
         }
         else
         {
           System.out.println("uhhh");
         }
       }
       else
       {
         newMelody.add(melody.get(j));
       }
     }
     
     newPop.set(i,newMelody);
   }
   
   for(int k = 0; k < newPop.size(); k++)
    {
      fitness[k] = calcFit(newPop.get(k));
    }
   
   //population = newPop;
   //mutate the notes, tending slightly to mutating "bad" notes
 }
 ///MUTATION END--------------------------------------------------------------------
 
 public void newGen()
 {
   population = newPop;
 }
 
 ///GET BEST------------------------------------------------------------------------
 public LinkedList<NoteObj> getBest() //returns the melody with the highest fitness
 {
  int max = -999;
  maxInd = 0;
  
  for(int i = 0; i < population.size(); i++)
  {
   if(fitness[i] > max)
   {
    //System.out.println("current max: " + max);
    max = fitness[i];
    maxInd = i;
   }
  }
  //System.out.println("Best found at index: " + maxInd + " with fitness: " + max);
  LinkedList<NoteObj> best = population.get(maxInd);
  return best;
 }
 ///GET BEST END--------------------------------------------------------------------
 
 ///GET FIT---------------
 public int getFit(int n)
 {                       
  return fitness[n];     
 }                       
 ///GET FIT END-----------
 
 public int getLength(LinkedList<NoteObj> x)
 {
  int length = 0;
  for(NoteObj i : x)
  {
   length += i.getLength();
  }
  return length;
 }
 
 ///CREATE MELODY-----------------------------------------------------------------------------
 public LinkedList<NoteObj> createMelody(int ticks, int r) //generates a single random melody
 {
  LinkedList<NoteObj> temp = new LinkedList<NoteObj>();
  int rNote = 0, rDur = 0, curTotal = 0;
  
  if(r == 0)//first note acts as identifier for the Key. 
    rNote = (int)(Math.random()*96);
  else//Either a specific Key is entered or 0 for a random one
    rNote = r;
  
  NoteObj keyN = new NoteObj(rNote, 1); //first note determines key, and is not played
  
  temp.add(keyN);
  
  while(curTotal < ticks) //fill the list with random notes until we reach the desired length
  {
   rNote = (int)(Math.random()*96);
   rDur = (int)((Math.random()*15)+1);
   //rDur = 4;   //set the length of the note in ticks (16 - whole note)
      
   NoteObj n = new NoteObj(Note.Notes[rNote], rDur); //make a note from generated values
   temp.add(n);                                    //add it to the melody
   curTotal+= rDur;                                  //add to total ticks
      
  }
  
  if(curTotal > ticks)
  {
    int tickDiff = curTotal - ticks;
    int bigsLength = temp.get(temp.size()-1).getLength();
    temp.get(temp.size()-1).setLength(bigsLength - tickDiff);
  }
  
  return temp;
 }
 ///CREATE MELODY END-------------------------------------------------------------------------
 
}







