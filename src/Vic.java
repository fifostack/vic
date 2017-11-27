/**
 * Vic.java
 * @author Lucas Darnell, Jared Miller
 * 
 * A program to build midi files, using a genetic algorithm
 * to refine music bars
 * 
 * Built from Karl Brown's midifile.java
 * 
 */

import java.io.*;
import java.util.*;
import javax.sound.midi.*; // package for all midi classes


public class Vic
{
  static long cur = 0; //tracks current tick count
  static Sequence s;
  static Track t;
  static midiGenetic GA;
  
  
  public static void main(String argv[]) {  
   
 GA = new midiGenetic(100,64); //creates a genetic algorithm of 100 members of 64 ticks each (4 bars in 4/4 time)
 GA.init();
 GA.runGenetics(1);
 LinkedList<NoteObj> THEBEST = GA.getBest();
 printAll(GA.population);
 printMelody(THEBEST);
    System.out.println("midifile begin ");
    initTrack(); //create midi file and set initial values
      
//------------- PLAYNOTE TESTING ------------------------//

      playNote(Note.c5, 4);// C 5
      playNote(Note.d5, 2);// D 5
      playNote(Note.e5, 2);// E 5
      playNote(Note.f5, 2);// F 5
      playNote(Note.g5, 2);// G 5
      playNote(Note.a5, 2);// A 5
      playNote(Note.b5, 2);// B 5
      playNote(Note.c6, 4);// C 6
      playNote(Note.b5, 2);// B 5
      playNote(Note.a5, 2);// A 5
      playNote(Note.g5, 2);// G 5
      playNote(Note.f5, 2);// F 5
      playNote(Note.e5, 2);// E 5
      playNote(Note.d5, 2);// D 5
      playNote(Note.c5, 4);// C 5
      
      rest(4); //4th note rest
      
      playMelody(THEBEST); //play individual with highest fitness
      
//-------------------------------------------------------//
      
   endTrack();
   writeFile("gen1.mid"); //writes the midi file

  } //main
  
 /*------------------------ Init ----------------------------------
  * sends initial MIDI settings
  */
  public static void initTrack() 
  {
   try
    {
//****  Create a new MIDI sequence with 24 ticks per beat  ****
      s = new Sequence(javax.sound.midi.Sequence.PPQ,4);
      
      
//****  Obtain a MIDI track from the sequence  ****
      t = s.createTrack();
      
//****  General MIDI sysex -- turn on General MIDI sound set  ****
      byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
      SysexMessage sm = new SysexMessage();
      sm.setMessage(b, 6);
      MidiEvent me = new MidiEvent(sm,(long)0);
      t.add(me);
      
//****  set tempo (meta event)  ****
      MetaMessage mt = new MetaMessage();
      byte[] bt = {0x07, (byte)0xA1, 0x20};
      mt.setMessage(0x51 ,bt, 3);
      me = new MidiEvent(mt,(long)0);
      t.add(me);
      
//****  set track name (meta event)  ****
      mt = new MetaMessage();
      String TrackName = "midifile track";
      mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
      me = new MidiEvent(mt,(long)0);
      t.add(me);
      
//****  set omni on  ****
      ShortMessage mm = new ShortMessage();
      mm.setMessage(0xB0, 0x7D,0x00);
      me = new MidiEvent(mm,(long)0);
      t.add(me);
      
//****  set poly on  ****
      mm = new ShortMessage();
      mm.setMessage(0xB0, 0x7F,0x00);
      me = new MidiEvent(mm,(long)0);
      t.add(me);
      
//****  set instrument to Piano  ****
      mm = new ShortMessage();
      mm.setMessage(0xC0, 0x00, 0x00);
      me = new MidiEvent(mm,(long)0);
      t.add(me);
  }
  catch(Exception e)
  {
   System.out.println("Exception caught in init: " + e.toString());//print any issue
  }
}

 /*------------------------ endTrack ----------------------------------
  * sends the track end message
  */
  public static void endTrack() 
  {
 try{
   //****  set end of track (meta event) 4 ticks later  ****
      MetaMessage mt = new MetaMessage();
      byte[] bet = {}; // empty array
      mt.setMessage(0x2F,bet,0);
      MidiEvent me = new MidiEvent(mt, (long)cur + 4);//add a 4 tick break before ending
      cur += 4; //increment cur
      t.add(me);
      
    } //try
    catch(Exception e)
    {
      System.out.println("Exception caught " + e.toString());
    } //catch
    System.out.println("midifile end ");
  }
  
 /*------------------------ writeFile ----------------------------------
  * outputs the finished MIDI file to the disk
  */
  public static void writeFile(String f)
  {
   try{
   File outfile = new File("" + f);
   MidiSystem.write(s,1,outfile);
      }
      catch(Exception e)
      { System.out.println("Exception caught in writeFile: " + e.toString()); }
  }
  
  /*---------------------- playMelody ----------------------------------
  */
  public static void playMelody(LinkedList<NoteObj> notes)
  {
    while(notes.size() > 1)
    {
  playNote(notes.remove(1));
 }
    
  }
  
  /*-------------------------- printMelody ------------------------------
  */
  static void printMelody(LinkedList<NoteObj> notes) //display each note in the object array 
  {
	System.out.print(" Start: ");
    
    for(int i = 1; i < notes.size(); i++)
    {
      System.out.print(notes.get(i).toString());
    }
    
    System.out.println(" End");
  }
  
  static void printAll(LinkedList<LinkedList<NoteObj>> pop)
  {
    int num = 0;
    for(LinkedList<NoteObj> individual : pop)
    {
      System.out.print(num + ": ");
      printMelody(individual);
      num++;
    }
  }
  
 /*-------------------------- playNote ---------------------------------
  */
  public static void playNote(int note, int duration) //simplified method of playing a note
  {
    
    try{
      //****  note on - E3  ****
      ShortMessage mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,note,0x60);
      MidiEvent me = new MidiEvent(mm,(long)cur);//start the note at this tick
      t.add(me);
      
      //****  note off - E3  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_OFF,note,0x40);
      me = new MidiEvent(mm,(long)(cur+duration));//end note on current+length
      t.add(me);
      cur += duration;
    }//try
    catch(Exception e)
    {
      System.out.println("Exception caught in playNote: " + e.toString());//print any issue
    }//catch
  } //playnote
  
  public static void playNote(NoteObj n) //alternative object method
  {
   try{
      //****  note on  ****
      ShortMessage mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,n.getNote(),0x60);
      MidiEvent me = new MidiEvent(mm,(long)cur);//start the note at this tick
      t.add(me);
      
      //****  note off  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_OFF,n.getNote(),0x40);
      me = new MidiEvent(mm,(long)(cur+ (n.getLength())));//end note on current+length
      t.add(me);
      cur += n.getLength();
    }//try
    catch(Exception e)
    {
      System.out.println("Exception caught in playNote: " + e.toString());//print any issue
    }//catch
  }
  
/*--------------------------- rest -------------------------------------
 */
  public static void rest(int duration)
  {
    cur += duration;
  }
  
  
} //class
