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
import javax.sound.midi.*;// package for all midi classes



public class Vic
{
  static long cur = 0;
  static Sequence s;
  static Track t;
  
  public static void main(String argv[]) {
    System.out.println("midifile begin ");
    try
    {
//****  Create a new MIDI sequence with 24 ticks per beat  ****
      s = new Sequence(javax.sound.midi.Sequence.PPQ,24);
      
      
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
      byte[] bt = {0x02, (byte)0x00, 0x00};
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
      
/*//****  note on - middle C  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,0x3C,0x60);
      me = new MidiEvent(mm,(long)1);
      t.add(me);
      
//****  note off - middle C - 120 ticks later  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_OFF,0x3C,0x40);
      me = new MidiEvent(mm,(long)121);
      t.add(me);
      
//****  note on - E3  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,0x3E,0x60);
      me = new MidiEvent(mm,(long)141);
      t.add(me);
      
//****  note off - E3 - 120 ticks later  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_OFF,0x3E,0x40);
      me = new MidiEvent(mm,(long)261);
      t.add(me);
      
      
//****  note on - middle C - 20 ticks later ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,0x3C,0x60);
      me = new MidiEvent(mm,(long)281);
      t.add(me);
      
//****  note off - middle C - 120 ticks later  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_OFF,0x3C,0x40);
      me = new MidiEvent(mm,(long)401);
      t.add(me);
      
//****  set end of track (meta event) 19 ticks later  ****
      mt = new MetaMessage();
      byte[] bet = {}; // empty array
      mt.setMessage(0x2F,bet,0);
      me = new MidiEvent(mt, (long)420);
      t.add(me);*/
      
      
//**** PLAYNOTE TESTING ****///
      playNote(0x30, 96);// C 3
      playNote(0x32, 48);// D 3
      playNote(0x34, 48);// E 3
      playNote(0x35, 48);// F 3
      playNote(0x37, 48);// G 3
      playNote(0x39, 48);// A 4
      playNote(0x3B, 48);// B 4
      playNote(0x3C, 96);// C 4
      playNote(0x3B, 48);// B 4
      playNote(0x39, 48);// A 4
      playNote(0x37, 48);// G 3
      playNote(0x35, 48);// F 3
      playNote(0x34, 48);// E 3
      playNote(0x32, 48);// D 3
      playNote(0x30, 96);// C 3
      
//****  write the MIDI sequence to a MIDI file  ****
      File f = new File("midifile.mid");
      MidiSystem.write(s,1,f);
    } //try
    catch(Exception e)
    {
      System.out.println("Exception caught " + e.toString());
    } //catch
    System.out.println("midifile end ");
  } //main
  
  
  
  public static void playNote(int note, int duration)
  {
    
    try{
      //****  note on - E3  ****
      ShortMessage mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_ON,note,0x60);
      MidiEvent me = new MidiEvent(mm,(long)cur);//start the note at this tick
      t.add(me);
      
      //****  note off - E3 - 120 ticks later  ****
      mm = new ShortMessage();
      mm.setMessage(ShortMessage.NOTE_OFF,note,0x40);
      me = new MidiEvent(mm,(long)(cur+duration));//end note on current+length
      t.add(me);
      cur += duration;
    }//try
    catch(Exception e)
    {
      System.out.println("Exception caught: " + e.toString());//print any issue
    }//catch
  } //playnote
  
  
} //class
