/**
 * This file contains the hex numbers for each note so they can be referenced in a more natural way
 */
public class Note
{
  
  static final int 
    c3= (0x18),cS3= (0x19),d3= (0x1A),eF3= (0x1B),e3= (0x1C),f3= (0x1D),fS3= (0x1E),g3= (0x1F),gS3= (0x20),a3= (0x21),bF3= (0x22),b3= (0x23),
    c4= (0x24),cS4= (0x25),d4= (0x26),eF4= (0x27),e4= (0x28),f4= (0x29),fS4= (0x2A),g4= (0x2B),gS4= (0x2C),a4= (0x2D),bF4= (0x2E),b4= (0x2F),
    c5= (0x30),cS5= (0x31),d5= (0x32),eF5= (0x33),e5= (0x34),f5= (0x35),fS5= (0x36),g5= (0x37),gS5= (0x38),a5= (0x39),bF5= (0x3A),b5= (0x3B),
    c6= (0x3C),cS6= (0x3D),d6= (0x3E),eF6= (0x3F),e6= (0x40),f6= (0x41),fS6= (0x42),g6= (0x43),gS6= (0x44),a6= (0x45),bF6= (0x46),b6= (0x47),
    c7= (0x48),cS7= (0x49),d7= (0x4A),eF7= (0x4B),e7= (0x4C),f7= (0x4D),fS7= (0x4E),g7= (0x4F),gS7= (0x50),a7= (0x51),bF7= (0x52),b7= (0x53),
    c8= (0x54),cS8= (0x55),d8= (0x56),eF8= (0x57),e8= (0x58),f8= (0x59),fS8= (0x5A),g8= (0x5B),gS8= (0x5C),a8= (0x5D),bF8= (0x5E),b8= (0x5F),
    c9= (0x60),cS9= (0x61),d9= (0x62),eF9= (0x63),e9= (0x64),f9= (0x65),fS9= (0x66),g9= (0x67),gS9= (0x68),a9= (0x69),bF9= (0x6A),b9= (0x6B),
    c10= (0x6C),cS10= (0x6D),d10= (0x6E),eF10= (0x6F),e10= (0x70),f10= (0x71),fS10= (0x72),g10= (0x73),gS10= (0x74),a10= (0x75),bF10= (0x76),
    b10= (0x77);
  
  static int[] Notes = {c3,cS3,d3,eF3,e3,f3,fS3,g3,gS3,a3,bF3,b3,
                 c4,cS4,d4,eF4,e4,f4,fS4,g4,gS4,a4,bF4,b4,
                 c5,cS5,d5,eF5,e5,f5,fS5,g5,gS5,a5,bF5,b5,
                 c6,cS6,d6,eF6,e6,f6,fS6,g6,gS6,a6,bF6,b6,
                 c7,cS7,d7,eF7,e7,f7,fS7,g7,gS7,a7,bF7,b7,
                 c8,cS8,d8,eF8,e8,f8,fS8,g8,gS8,a8,bF8,b8,
                 c9,cS9,d9,eF9,e9,f9,fS9,g9,gS9,a9,bF9,b9,
                 c10,cS10,d10,eF10,e10,f10,fS10,g10,gS10,a10,bF10,b10};
  
  static int[][] KEYS = new int[12][12];
  /*C MAJOR*/KEYS[0] = {c3,d3,e3,f3,g3,a3,b3,c4,d4,e4,f4,g4,a4,b4,c5,d5,e5,f5,g5,a5,b5,c6,d6,e6,f6,g6,a6,b6,c7,d7,e7,f7,g7,a7,b7,c8,d8,e8,f8,g8,a8,b8,c9,d9,e9,f9,g9,a9,b9,c10,d10,e10,f10,g10,a10,b10};
  /*G MAJOR*/KEYS[1] = {c3,d3,e3,fS3,g3,a3,b3,c4,d4,e4,fS4,g4,a4,b4,c5,d5,e5,fS5,g5,a5,b5,c6,d6,e6,fS6,g6,a6,b6,c7,d7,e7,fS7,g7,a7,b7,c8,d8,e8,fS8,g8,a8,b8,c9,d9,e9,fS9,g9,a9,b9,c10,d10,e10,fS10,g10,a10,b10};
  /*D MAJOR*/KEYS[2] = {cS3,d3,e3,fS3,g3,a3,b3,cS4,d4,e34,fS4,g4,a4,b4,cS5,d5,e5,fS5,g5,a5,b5,cS6,d6,e6,fS6,g6,a6,b6,cS7,d7,e7,fS7,g7,a7,b7,cS8,d8,e8,fS8,g8,a8,b8,cS9,d9,e9,fS9,g9,a9,b9,cS10,d10,e10,fS10,g10,a10,b10};
  /*A MAJOR*/KEYS[3] = {cS3,d,e3,fS3,gS3,a3,b3,cS4,d4,e4,fS4,gS4,a34,b4,cS5,d5,e5,fS5,gS5,a5,b5,cS6,d6,e6,fS6,gS6,a6,b6,cS7,d7,e7,fS7,gS7,a7,b7,cS8,d8,e8,fS8,gS8,a8,b8,cS9,d9,e9,fS9,gS9,a9,b9,cS10,d10,e10,fS10,gS10,a10,b10};
  /*E MAJOR*/KEYS[4] = {cS3,dS3,e3,fS3,gS3,a3,b3,cS4,dS4,e4,fS4,gS4,a4,b4,cS5,dS5,e5,fS5,gS5,a5,b5,cS6,dS6,e6,fS6,gS6,a6,b6,cS7,dS7,e7,fS7,gS7,a7,b7,cS8,dS8,e8,fS8,gS8,a8,b8,cS9,dS9,e9,fS9,gS9,a9,b9,cS10,dS10,e10,fS10,gS10,a10,b10};
  /*B MAJOR*/KEYS[5] = {cS3,dS3,e3,fS3,gS3,aS3,b3,cS4,dS4,e4,fS4,gS4,aS4,b4,cS5,dS5,e5,fS5,gS5,aS5,b5,cS6,dS6,e6,fS6,gS6,aS6,b6,cS7,dS7,e7,fS7,gS7,aS7,b7,cS8,dS8,e8,fS8,gS8,aS8,b8,cS9,dS9,e9,fS9,gS9,aS9,b9,cS9,dS9,e9,fS9,gS9,aS9,b9,cS10,dS10,e10,fS10,gS10,aS10,b10};
  /*Fsharp MAJOR*/KEYS[6] = {fS3,gS3,c3,cS3,eF3,f3,fS4};
  /*Csharp MAJOR*/KEYS[7] = {c30};
  /*Aflat MAJOR*/KEYS[8] = {c30};
  /*Eflat MAJOR*/KEYS[9] = {c30};
  /*Bflat MAJOR*/KEYS[10] = {c30};
  /*F MAJOR*/KEYS[11] = {c30};
  
 
}