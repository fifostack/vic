# VIC
A java program for writing MIDI tracks using a genetic algorithm

# Prerequisites
There are no significant requirements for this project except that
the user must have a JDK on their machine to compile and run the java files

# Files
vic.java - main class. Initializes the MIDI file, adds generated melodies to the track, and exports the .mid file

Note.java - contains definitions for each note's hex number so that they can be referenced more naturally. Notes are also listed by key.

NoteObj.java - custom note object definition, constructors, and basic methods

midiGenetic.java - holds the population of melodies and performs functions of the genetic algorithm to generate new generations of melodies based on fitness score
