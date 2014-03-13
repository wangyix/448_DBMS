/* Generated By:JavaCC: Do not edit this line. SQLParserTokenManager.java */
package parser;
import java.util.*;
import ast.*;

/** Token Manager. */
public class SQLParserTokenManager implements SQLParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 9:
         jjmatchedKind = 2;
         return jjMoveNfa_0(3, 0);
      case 10:
         jjmatchedKind = 3;
         return jjMoveNfa_0(3, 0);
      case 12:
         jjmatchedKind = 4;
         return jjMoveNfa_0(3, 0);
      case 13:
         jjmatchedKind = 5;
         return jjMoveNfa_0(3, 0);
      case 32:
         jjmatchedKind = 1;
         return jjMoveNfa_0(3, 0);
      case 33:
         return jjMoveStringLiteralDfa1_0(0x8000000000L);
      case 40:
         jjmatchedKind = 48;
         return jjMoveNfa_0(3, 0);
      case 41:
         jjmatchedKind = 50;
         return jjMoveNfa_0(3, 0);
      case 42:
         jjmatchedKind = 36;
         return jjMoveNfa_0(3, 0);
      case 43:
         jjmatchedKind = 34;
         return jjMoveNfa_0(3, 0);
      case 44:
         jjmatchedKind = 49;
         return jjMoveNfa_0(3, 0);
      case 45:
         jjmatchedKind = 35;
         return jjMoveNfa_0(3, 0);
      case 47:
         jjmatchedKind = 37;
         return jjMoveNfa_0(3, 0);
      case 59:
         jjmatchedKind = 47;
         return jjMoveNfa_0(3, 0);
      case 60:
         jjmatchedKind = 40;
         return jjMoveStringLiteralDfa1_0(0x40000000000L);
      case 61:
         jjmatchedKind = 38;
         return jjMoveNfa_0(3, 0);
      case 62:
         jjmatchedKind = 41;
         return jjMoveStringLiteralDfa1_0(0x80000000000L);
      case 65:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 67:
         return jjMoveStringLiteralDfa1_0(0x100000c00L);
      case 68:
         return jjMoveStringLiteralDfa1_0(0x221010000L);
      case 70:
         return jjMoveStringLiteralDfa1_0(0x82000L);
      case 72:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 73:
         return jjMoveStringLiteralDfa1_0(0x80600000L);
      case 75:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 79:
         return jjMoveStringLiteralDfa1_0(0x200L);
      case 80:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 81:
         return jjMoveStringLiteralDfa1_0(0x40000000L);
      case 82:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x4040000L);
      case 84:
         return jjMoveStringLiteralDfa1_0(0x10020000L);
      case 85:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 86:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 87:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 97:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x100000c00L);
      case 100:
         return jjMoveStringLiteralDfa1_0(0x221010000L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x82000L);
      case 104:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x80600000L);
      case 107:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x200L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 113:
         return jjMoveStringLiteralDfa1_0(0x40000000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x4040000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x10020000L);
      case 117:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      default :
         return jjMoveNfa_0(3, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 0);
   }
   switch(curChar)
   {
      case 61:
         if ((active0 & 0x8000000000L) != 0L)
         {
            jjmatchedKind = 39;
            jjmatchedPos = 1;
         }
         else if ((active0 & 0x40000000000L) != 0L)
         {
            jjmatchedKind = 42;
            jjmatchedPos = 1;
         }
         else if ((active0 & 0x80000000000L) != 0L)
         {
            jjmatchedKind = 43;
            jjmatchedPos = 1;
         }
         break;
      case 65:
         return jjMoveStringLiteralDfa2_0(active0, 0x10820000L);
      case 69:
         return jjMoveStringLiteralDfa2_0(active0, 0x22d04c000L);
      case 72:
         return jjMoveStringLiteralDfa2_0(active0, 0x100100800L);
      case 78:
         return jjMoveStringLiteralDfa2_0(active0, 0x80600100L);
      case 79:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 80:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000L);
      case 82:
         if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x91400L);
      case 85:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000000L);
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x10820000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x22d04c000L);
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x100100800L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x80600100L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 112:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000L);
      case 114:
         if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x91400L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000000L);
      default :
         break;
   }
   return jjMoveNfa_0(3, 1);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 1);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 1);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 66:
         return jjMoveStringLiteralDfa3_0(active0, 0x10020000L);
      case 67:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000000L);
      case 68:
         if ((active0 & 0x100L) != 0L)
         {
            jjmatchedKind = 8;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000L);
      case 69:
         return jjMoveStringLiteralDfa3_0(active0, 0x100c00L);
      case 70:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 73:
         return jjMoveStringLiteralDfa3_0(active0, 0x40001000L);
      case 76:
         return jjMoveStringLiteralDfa3_0(active0, 0x9840000L);
      case 79:
         return jjMoveStringLiteralDfa3_0(active0, 0x90000L);
      case 82:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 83:
         return jjMoveStringLiteralDfa3_0(active0, 0x20200000L);
      case 84:
         if ((active0 & 0x4000000L) != 0L)
         {
            jjmatchedKind = 26;
            jjmatchedPos = 2;
         }
         else if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 31;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 89:
         if ((active0 & 0x4000L) != 0L)
         {
            jjmatchedKind = 14;
            jjmatchedPos = 2;
         }
         break;
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x10020000L);
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000000L);
      case 100:
         if ((active0 & 0x100L) != 0L)
         {
            jjmatchedKind = 8;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x100c00L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x40001000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x9840000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x90000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x20200000L);
      case 116:
         if ((active0 & 0x4000000L) != 0L)
         {
            jjmatchedKind = 26;
            jjmatchedPos = 2;
         }
         else if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 31;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 121:
         if ((active0 & 0x4000L) != 0L)
         {
            jjmatchedKind = 14;
            jjmatchedPos = 2;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(3, 2);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 2);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 2);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000400L);
      case 67:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000800L);
      case 69:
         return jjMoveStringLiteralDfa4_0(active0, 0x124a000L);
      case 73:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000000L);
      case 76:
         return jjMoveStringLiteralDfa4_0(active0, 0x10020000L);
      case 77:
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 19;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
      case 79:
         if ((active0 & 0x400000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 3;
         }
         break;
      case 80:
         if ((active0 & 0x10000L) != 0L)
         {
            jjmatchedKind = 16;
            jjmatchedPos = 3;
         }
         else if ((active0 & 0x8000000L) != 0L)
         {
            jjmatchedKind = 27;
            jjmatchedPos = 3;
         }
         break;
      case 82:
         if ((active0 & 0x100000000L) != 0L)
         {
            jjmatchedKind = 32;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x100000L);
      case 84:
         if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 3;
         }
         break;
      case 85:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000L);
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000400L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000800L);
      case 101:
         return jjMoveStringLiteralDfa4_0(active0, 0x124a000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x10020000L);
      case 109:
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 19;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
      case 111:
         if ((active0 & 0x400000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 3;
         }
         break;
      case 112:
         if ((active0 & 0x10000L) != 0L)
         {
            jjmatchedKind = 16;
            jjmatchedPos = 3;
         }
         else if ((active0 & 0x8000000L) != 0L)
         {
            jjmatchedKind = 27;
            jjmatchedPos = 3;
         }
         break;
      case 114:
         if ((active0 & 0x100000000L) != 0L)
         {
            jjmatchedKind = 32;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x100000L);
      case 116:
         if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 3;
         }
         break;
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000L);
      default :
         break;
   }
   return jjMoveNfa_0(3, 3);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 3);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 3);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000L);
      case 67:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
      case 69:
         if ((active0 & 0x20000L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 4;
         }
         else if ((active0 & 0x100000L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 4;
         }
         return jjMoveStringLiteralDfa5_0(active0, 0x10800000L);
      case 73:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
      case 75:
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 11;
            jjmatchedPos = 4;
         }
         break;
      case 77:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000000L);
      case 82:
         return jjMoveStringLiteralDfa5_0(active0, 0x20208000L);
      case 84:
         return jjMoveStringLiteralDfa5_0(active0, 0x3000400L);
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000L);
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
      case 101:
         if ((active0 & 0x20000L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 4;
         }
         else if ((active0 & 0x100000L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 4;
         }
         return jjMoveStringLiteralDfa5_0(active0, 0x10800000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
      case 107:
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 11;
            jjmatchedPos = 4;
         }
         break;
      case 109:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x20208000L);
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x3000400L);
      default :
         break;
   }
   return jjMoveNfa_0(3, 4);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 4);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 4);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa6_0(active0, 0x200000000L);
      case 69:
         if ((active0 & 0x400L) != 0L)
         {
            jjmatchedKind = 10;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x1000000L) != 0L)
         {
            jjmatchedKind = 24;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 25;
            jjmatchedPos = 5;
         }
         return jjMoveStringLiteralDfa6_0(active0, 0x8000L);
      case 71:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000L);
      case 73:
         return jjMoveStringLiteralDfa6_0(active0, 0x20000000L);
      case 82:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000L);
      case 83:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x10000000L) != 0L)
         {
            jjmatchedKind = 28;
            jjmatchedPos = 5;
         }
         break;
      case 84:
         if ((active0 & 0x40000L) != 0L)
         {
            jjmatchedKind = 18;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x200000L) != 0L)
         {
            jjmatchedKind = 21;
            jjmatchedPos = 5;
         }
         break;
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x200000000L);
      case 101:
         if ((active0 & 0x400L) != 0L)
         {
            jjmatchedKind = 10;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x1000000L) != 0L)
         {
            jjmatchedKind = 24;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 25;
            jjmatchedPos = 5;
         }
         return jjMoveStringLiteralDfa6_0(active0, 0x8000L);
      case 103:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000L);
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x20000000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000L);
      case 115:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x10000000L) != 0L)
         {
            jjmatchedKind = 28;
            jjmatchedPos = 5;
         }
         break;
      case 116:
         if ((active0 & 0x40000L) != 0L)
         {
            jjmatchedKind = 18;
            jjmatchedPos = 5;
         }
         else if ((active0 & 0x200000L) != 0L)
         {
            jjmatchedKind = 21;
            jjmatchedPos = 5;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(3, 5);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 5);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 5);
   }
   switch(curChar)
   {
      case 66:
         return jjMoveStringLiteralDfa7_0(active0, 0x20000000L);
      case 76:
         if ((active0 & 0x200000000L) != 0L)
         {
            jjmatchedKind = 33;
            jjmatchedPos = 6;
         }
         break;
      case 78:
         if ((active0 & 0x2000L) != 0L)
         {
            jjmatchedKind = 13;
            jjmatchedPos = 6;
         }
         return jjMoveStringLiteralDfa7_0(active0, 0x8000L);
      case 89:
         if ((active0 & 0x1000L) != 0L)
         {
            jjmatchedKind = 12;
            jjmatchedPos = 6;
         }
         break;
      case 98:
         return jjMoveStringLiteralDfa7_0(active0, 0x20000000L);
      case 108:
         if ((active0 & 0x200000000L) != 0L)
         {
            jjmatchedKind = 33;
            jjmatchedPos = 6;
         }
         break;
      case 110:
         if ((active0 & 0x2000L) != 0L)
         {
            jjmatchedKind = 13;
            jjmatchedPos = 6;
         }
         return jjMoveStringLiteralDfa7_0(active0, 0x8000L);
      case 121:
         if ((active0 & 0x1000L) != 0L)
         {
            jjmatchedKind = 12;
            jjmatchedPos = 6;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(3, 6);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 6);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 6);
   }
   switch(curChar)
   {
      case 67:
         return jjMoveStringLiteralDfa8_0(active0, 0x8000L);
      case 69:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 29;
            jjmatchedPos = 7;
         }
         break;
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x8000L);
      case 101:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 29;
            jjmatchedPos = 7;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(3, 7);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 7);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 7);
   }
   switch(curChar)
   {
      case 69:
         return jjMoveStringLiteralDfa9_0(active0, 0x8000L);
      case 101:
         return jjMoveStringLiteralDfa9_0(active0, 0x8000L);
      default :
         break;
   }
   return jjMoveNfa_0(3, 8);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(3, 8);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(3, 8);
   }
   switch(curChar)
   {
      case 83:
         if ((active0 & 0x8000L) != 0L)
         {
            jjmatchedKind = 15;
            jjmatchedPos = 9;
         }
         break;
      case 115:
         if ((active0 & 0x8000L) != 0L)
         {
            jjmatchedKind = 15;
            jjmatchedPos = 9;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(3, 9);
}
static final long[] jjbitVec0 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int strKind = jjmatchedKind;
   int strPos = jjmatchedPos;
   int seenUpto;
   input_stream.backup(seenUpto = curPos + 1);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { throw new Error("Internal Error"); }
   curPos = 0;
   int startsAt = 0;
   jjnewStateCnt = 16;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 45)
                        kind = 45;
                     jjCheckNAdd(9);
                  }
                  else if (curChar == 39)
                     jjCheckNAddStates(0, 3);
                  else if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 0:
                  if (curChar == 45)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 1:
                  if ((0xfffffffffffffbffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if (curChar == 10 && kind > 6)
                     kind = 6;
                  break;
               case 5:
                  jjAddStates(4, 5);
                  break;
               case 8:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 9:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 45)
                     kind = 45;
                  jjCheckNAdd(9);
                  break;
               case 10:
               case 12:
                  if (curChar == 39)
                     jjCheckNAddStates(0, 3);
                  break;
               case 11:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     jjCheckNAddStates(0, 3);
                  break;
               case 13:
                  if (curChar == 39)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 15:
                  if (curChar == 39 && kind > 46)
                     kind = 46;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 44)
                        kind = 44;
                     jjCheckNAdd(8);
                  }
                  else if (curChar == 123)
                     jjCheckNAddTwoStates(5, 6);
                  break;
               case 1:
                  jjAddStates(6, 7);
                  break;
               case 4:
                  if (curChar == 123)
                     jjCheckNAddTwoStates(5, 6);
                  break;
               case 5:
                  if ((0xdfffffffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(5, 6);
                  break;
               case 6:
                  if (curChar == 125 && kind > 7)
                     kind = 7;
                  break;
               case 7:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(8);
                  break;
               case 8:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(8);
                  break;
               case 11:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjAddStates(0, 3);
                  break;
               case 14:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjAddStates(6, 7);
                  break;
               case 5:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjAddStates(4, 5);
                  break;
               case 11:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjAddStates(0, 3);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 16 - (jjnewStateCnt = startsAt)))
         break;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { break; }
   }
   if (jjmatchedPos > strPos)
      return curPos;

   int toRet = Math.max(curPos, seenUpto);

   if (curPos < toRet)
      for (i = toRet - Math.min(curPos, seenUpto); i-- > 0; )
         try { curChar = input_stream.readChar(); }
         catch(java.io.IOException e) { throw new Error("Internal Error : Please send a bug report."); }

   if (jjmatchedPos < strPos)
   {
      jjmatchedKind = strKind;
      jjmatchedPos = strPos;
   }
   else if (jjmatchedPos == strPos && jjmatchedKind > strKind)
      jjmatchedKind = strKind;

   return toRet;
}
static final int[] jjnextStates = {
   11, 13, 14, 15, 5, 6, 1, 2, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, "\53", "\55", "\52", "\57", "\75", 
"\41\75", "\74", "\76", "\74\75", "\76\75", null, null, null, "\73", "\50", "\54", 
"\51", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x7ffffffffff01L, 
};
static final long[] jjtoSkip = {
   0xfeL, 
};
protected JavaCharStream input_stream;
private final int[] jjrounds = new int[16];
private final int[] jjstateSet = new int[32];
protected char curChar;
/** Constructor. */
public SQLParserTokenManager(JavaCharStream stream){
   if (JavaCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public SQLParserTokenManager(JavaCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(JavaCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 16; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(JavaCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
