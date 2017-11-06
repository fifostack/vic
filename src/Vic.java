//package vic;

/**
 * Vic.java
 * @author Lucas Darnell
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
  static long cur = 0;
  static Sequence s;
  static Track t;
  static NoteObj[] melody;
  
  
  public static void main(String argv[]) {
    System.out.println("midifile begin ");
    
    //init();
    try
    {
//****  Create a new MIDI sequence with 4 ticks per beat  ****
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
      
//**** PLAYNOTE TESTING ****///
      playNote(Note.c5, 8);// C 5
      playNote(Note.d5, 4);// D 5
      playNote(Note.e5, 4);// E 5
      playNote(Note.f5, 4);// F 5
      playNote(Note.g5, 4);// G 5
      playNote(Note.a5, 4);// A 5
      playNote(Note.b5, 4);// B 5
      playNote(Note.c6, 8);// C 6
      playNote(Note.b5, 4);// B 5
      playNote(Note.a5, 4);// A 5
      playNote(Note.g5, 4);// G 5
      playNote(Note.f5, 4);// F 5
      playNote(Note.e5, 4);// E 5
      playNote(Note.d5, 4);// D 5
      playNote(Note.c5, 8);// C 5
      
      rest(1);
      
      firstMelody(64);
      
     
//****  set end of track (meta event) 4 ticks later  ****
      mt = new MetaMessage();
      byte[] bet = {}; // empty array
      mt.setMessage(0x2F,bet,0);
      me = new MidiEvent(mt, (long)cur + 4);//add a 4 tick break before ending
      cur += 4; //increment cur
      t.add(me);
      
//****  write the MIDI sequence to a MIDI file  ****
      File f = new File("gen1.mid");
      MidiSystem.write(s,1,f);
    } //try
    catch(Exception e)
    {
      System.out.println("Exception caught " + e.toString());
    } //catch
    System.out.println("midifile end ");
    
    
    printMelody(melody);
  } //main
  
  
  public static void init() //unused for now
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
      mm.setMessage(0xC0, 0x32, 0x00);
      me = new MidiEvent(mm,(long)0);
      t.add(me);
  }
  catch(Exception e)
  {
	  System.out.println("Exception caught in init: " + e.toString());//print any issue
  }
}

  public static void end() //unused for now
  {
	try{
	  //****  set end of track (meta event) 4 ticks later  ****
      MetaMessage mt = new MetaMessage();
      byte[] bet = {}; // empty array
      mt.setMessage(0x2F,bet,0);
      MidiEvent me = new MidiEvent(mt, (long)cur + 4);//add a 4 tick break before ending
      cur += 4; //increment cur
      t.add(me);
      
//****  write the MIDI sequence to a MIDI file  ****
      File f = new File("gen1.mid");
      MidiSystem.write(s,1,f);
    } //try
    catch(Exception e)
    {
      System.out.println("Exception caught " + e.toString());
    } //catch
    System.out.println("midifile end ");
    
    printMelody(melody);
  }

  
 /*---------------------- firstMelody ----------------------------------
  */
  public static void firstMelody(int ticks)
  {
    int rNote = 0, rDur = 0, curTotal = 0;
    melody = new NoteObj[ticks];
    while(curTotal < ticks)
    {
      rNote = (int)(Math.random()*96);
      //rDur = (int)((Math.random()*15)+1);
      rDur = 1;   //set the length of the note in ticks (16 - whole note)
      
      NoteObj n = new NoteObj(Note.Notes[rNote], rDur); //make a note from generated values
      melody[curTotal] = n;                             //add it to the melody
      playNote(n);                                      //play it
      curTotal+= rDur;                                  //add to total ticks
      
    }
  }
  
 /*-------------------------- printMelody ------------------------------
  */
  static void printMelody(NoteObj[] n) //display each note in the object array 
  {
    for(int i = 0; i < n.length; i++)
    {
      if(n[i] != null) //if there is a note, print it
      {
        System.out.print(n[i].toString() + " ");
      }
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
      //****  note on - E3  ****
      ShortMessage mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,n.getNote(),0x60);
      MidiEvent me = new MidiEvent(mm,(long)cur);//start the note at this tick
      t.add(me);
      
      //****  note off - E3  ****
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
