import java.util.LinkedList;
import java.util.List;

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
 int bars;
 int[] KEY;
 
 public midiGenetic() //default constructor
 {
  population = new LinkedList<LinkedList<NoteObj>>();
  ticks = 65;
  bars = (ticks-1)/16;
  POPSIZE = 100;
  fitness = new int[POPSIZE];
 }
 
 public midiGenetic(int n, int t)
 {
  population = new LinkedList<LinkedList<NoteObj>>();
  ticks = t+1;
  bars = (ticks-1)/16;
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
     mutation(.988);
     newGen();
     getBest();
     System.out.println("Best found at index: " + maxInd + " with fitness: " + fitness[maxInd] + " and length: " + getLength(getBest()));
   }
   
   getBest().get(getBest().size()-1).setLength(16);  
 }
 
 public int calcFit(LinkedList<NoteObj> no) //calculates the fitness of an individual
 {
  int keyFit = 0;
  int octFit = 0;
  int totalFit = 0;
  int count = 0;
  int refNote = no.get(1).getNote();//second note is used as a reference note
  int keyNote = no.get(0).getNote();//first note sets the key
  
  KEY = Note.getKey(keyNote); //get the lists of notes in this individual's key
  
  
  
  ///*---- Give fitness points based on notes in key *///
  for(NoteObj i : no) //for each note
  {
    for(int k : KEY) //check the array of acceptable in-key notes
    {
      if(i.getNote() == k)//if the note is in key, add fitness
       keyFit++;
    }
    count++;
  }
  
  keyFit = (int)((keyFit/(double)count)*100.0);
  ///*---- Give fitness points based on notes within one octave of reference note *///
  for(int i = 2; i < no.size(); i++)
  {
   int dis = NoteObj.distance(no.get(i),no.get(i-1));
   if(dis < octave)
   {
    octFit++;
   }
  }
  octFit = (int)((octFit/(double)count)*100.0);
  
  //FORMULA F1 & F2 CALCULATION---------------------------------------------------------------------------------------------
  int tickCount = no.get(1).getLength(), totalTicks = 0;
  LinkedList<Integer> indinterFits = new LinkedList<Integer>(); 
  int overflowCount = 0;
  int F1Fit = 0, F2Fit = 0;
  int FFit = 0;
  int a = 0;
  int barFit = 0;
  NoteObj currNote;
  NoteObj prevNote;
  int noteCount = 2;
  int barNoteCount = 0;
  int variance = 0;
  //Summation for formula f1 found in the paper
  for(int i = 0; i < bars; i++)//Sum of total interval fitnesses for each bar to get total of the melody
  {
    tickCount = 0 + overflowCount;//Initialize tickCount to 0 plus the overflow of the previous bar(if any)
    overflowCount = 0;
    barFit = 0;
    barNoteCount = 0;
    while(tickCount < 16 && totalTicks < (ticks-1))//Summation of interval fitness for each bar
    {
      try
      {
        currNote = no.get(noteCount);//Get current note
        prevNote = no.get(noteCount-1);//Get previous note
      }
      catch(IndexOutOfBoundsException e)
      {
        tickCount+= 16;
        continue;
      }
      int interval = Math.abs(NoteObj.distance(currNote, prevNote));//Calculate interval
      
      switch(interval)//Give fitness points based on interval type
      {
        //Perfect Consonant Category
        case 0: //Unison
        case 5: //Perfect Fourth
        case 7: //Perfect Fifth
        case 12: //Octave
          barFit += 5;
          indinterFits.add(5);
          break;
        //Imperfect Consonant Category
        case 4: //Major/Minor third
        case 9: //Major/Minor sixth
          barFit += 4;
          indinterFits.add(4);
          break;
        //Seconds
        case 2: //Major/Minor Second
          barFit += 3;
          indinterFits.add(3);
          break;
        //Sevenths
        case 11:
          barFit += 3;
          indinterFits.add(3);
          break;
        default:
          indinterFits.add(0);
          break;
      }
      
      tickCount += currNote.getLength();
      noteCount++;
      barNoteCount++;
      if(tickCount > 16)
      {
        overflowCount = tickCount - 16;
      }
      tickCount -= overflowCount;
    }
    if(barNoteCount == 0)
      barNoteCount++;
    if(overflowCount > 0)
      noteCount++;
    totalTicks += tickCount;
    a = (barFit/barNoteCount);
    for(int j = 0; j < barNoteCount-1; j++)
    {
      if(!(((barNoteCount-1)-i) < 0))
          variance += Math.pow(indinterFits.get((barNoteCount-1)-i) - a, 2);
    }
    variance /= barNoteCount;
    
    int F1Influence = 30;
    int F1Mean = 5;
    int F2Influence = 25;
    int F2Variance = 5;
    
    F1Fit += F1Influence*(F1Mean - a);
    F2Fit += F2Influence*(F2Variance - variance);
    
    //intervalF1Fit+= ;
    
  }
  
  //Weighted factors for the two halves of function F. These are set to one here but can be played with.
  int alpha = 1;
  int beta = 1;
  
  FFit = (alpha*F1Fit) + (beta*F2Fit);//Calculating the full function F.
  
  //int a = intervalF1Fit;
  //END F1 & F2 FORMULA CALCULATION-----------------------------------------------------------------------------------------
  totalFit = keyFit + octFit + FFit;
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
      if(rand <= (fitness[j]/(maxFitness*1.0))) //higher fitness means higher selection chance
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

     int ind = lengthMatch(p1,p2); //check if there is a point when the remaining notes are of equal length
     if(ind != -1)
  cPoint = ind; //if so, overwrite cPoint
     
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
//------------------normalize new melody lengths-------------------------//

//-------------handle melodies becoming too short-----------------//
  //System.out.println("C1\n");
  while(getLength(newP1) < ticks)
  {
   double y = (Math.random()*6)+1.0;
   newP1.get(newP1.size()-1).setLength(newP1.get(newP1.size()-1).getLength() + (int)y);
   double x = (Math.random()*(newP2.size()-1))+1;
   NoteObj n = newP2.get((int)x);
   newP1.addLast(n);
   
  }
  //System.out.println("C2\n");
  while(getLength(newP2) < ticks)
  {
   double y = (Math.random()*6)+1.0;
   newP2.get(newP2.size()-1).setLength((int)y);
   double x = (Math.random()*(newP1.size()-1))+1;
   NoteObj n = newP1.get((int)x);
   newP2.addLast(n);

  }

//-------------if they are too big--------------------------------//

//System.out.println("C3\n");
     while(getLength(newP1) > ticks)
     {
   if(getLength(newP1) - newP1.peekLast().getLength() >= ticks)
   {
    newP1.removeLast();
   }
   else if(getLength(newP1) - newP1.peekLast().getLength() < ticks)
   {
    int x = getLength(newP1) - newP1.peekLast().getLength();
    NoteObj b = newP1.get(newP1.size()-1);
    newP1.get(newP1.size()-1).setLength(b.getLength() - (getLength(newP1) - ticks));
   }
  
  }
  //System.out.println("C4\n");
  while(getLength(newP2) > ticks)
  {
   if((getLength(newP2) - newP2.peekLast().getLength()) >= ticks)
   {
    newP2.removeLast();
   }
   else if(getLength(newP2) - newP2.peekLast().getLength() < ticks)
   {
    int x = getLength(newP2) - newP2.peekLast().getLength();
    NoteObj b = newP2.get(newP2.size()-1);
    newP2.get(newP2.size()-1).setLength(b.getLength() - (getLength(newP2) - ticks));
   }
   
  }
  
     //System.out.println("C5\n");
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
 public void mutation(double stability)
 {
   LinkedList<NoteObj> melody, newMelody;
   
   for(int i = 0; i<newPop.size(); i++)
   {
  
     melody = newPop.get(i);
     newMelody = new LinkedList<NoteObj>(); //create a new melody
     newMelody.add(melody.get(0)); //copy key note
     newMelody.add(melody.get(1));
     
//------------------------Octave Squishing----------------------------//
//
     for(int j = 2; j < melody.size(); j++) //for every note but the first note
     {
       int dis = NoteObj.distance(melody.get(j), newMelody.get(j-1)); //distance between the notes
       
       if((Math.abs(dis)) > octave) //if the notes are more than an octave away
       { 
         NoteObj temp = melody.get(j);
         if(dis > 0) //if its too high, pull it down an octave
         {
           temp = new NoteObj((melody.get(j).getNote()) - 12,melody.get(j).getLength());
           newMelody.add(temp);
         }
         else if(dis < 0) //if its too low, push it up an octave
         {
           temp = new NoteObj((melody.get(j).getNote()) + 12,melody.get(j).getLength());
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
//     
//----------------------Random mutation-------------------------------//
//
     int noteDist = 0;
     for(int z = 1; z < newMelody.size(); z++)
     {
       //NoteObj y = newMelody.get(newMelody.indexOf(z) - 1);
       if(Math.random() > stability)
       {
         int randomNote = Note.Notes[(int)(Math.random()*96)];
         //noteDist = NoteObj.distance(newMelody.get(z),y);
         //if(!(noteDist > 12 || noteDist < -12))
         newMelody.set(z,new NoteObj(randomNote,newMelody.get(z).getLength()));
       }//randomize a note
       
       //System.out.println("MUTATED NOTE: " + z.getNote());
     }
     
//

//----------------------Random Transpose------------------------------//
//
/*
 if(Math.random() > 0.70)
 {
  boolean pos =(Math.random() > 0.53) ? true : false; // transpose up or down?
  
  for(NoteObj y : newMelody.subList(1,newMelody.size()))//for each real note
  {
   int n = y.getNote();
   int index = -1;
   int[] keynotes = Note.getKey(newMelody.get(0).getNote());
   for(int k = 0; k < keynotes.length; k++)
   {
    if(y.getNote() == keynotes[k])
    {
     index = k;
    }
   }
   
   if(pos && index != (keynotes.length-1)) //transpose up
   {
    if(index != -1)
     y.setNote(keynotes[index+1]);
    else
     y.setNote(y.getNote() + 0x01);
   }
   else if(!pos && index != 0)    //transpose down
   {
    if(index != -1)
     y.setNote(keynotes[index-1]);
    else
     y.setNote(y.getNote() - 0x01);
   }
  }
 }
 
 //----------------------Random Retrograde------------------------------//
 if(Math.random() > .5)
 {
  NoteObj temp;
  LinkedList<NoteObj> tempList = new LinkedList<NoteObj>();
   tempList.add(newMelody.get(0));
  for(int k = newMelody.size(); k >= 1; k--)
  {
    temp = newMelody.removeLast();
    tempList.add(temp);
  }
  newMelody = tempList;
 }
*/
//
     
     newPop.set(i,newMelody);//add the new melody to the new population
    }
   
    for(int k = 0; k < newPop.size(); k++)//recalculate fitness
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
 
 public int getLength(List<NoteObj> x)
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
 
 public int lengthMatch(LinkedList<NoteObj> a,LinkedList<NoteObj> b) //compare list lengths and fine a matching remainder
 {
  int l1 = a.size();
  int l2 = b.size();
  
  int temp = (l1 < l2) ? l1 : l2;    //length of the shortest
  
  
  for(int i = 2; i < temp; i++)      //for the length of the shortest list
  {
   int sum1 = 0, sum2 = 0;
   for(int j = i; j < l1; j++)    //for the remainder of this list
   {
    sum1 = a.get(j).getLength(); //sum remaining length
   }
   for(int j = i; j < l2; j++)
   {
    sum2 = b.get(j).getLength();
   }
   
   if(sum1 == sum2)   //if the remainders are the same
   { return i; }    //return the index
  }
  
  return -1;
 }
 
 
}







