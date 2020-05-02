package com.example.takecamera.Utils;

public class Encryps {

    public static String encrypt(String password)
    {
        int panjang = 0;
        String txt = "";
        String txt2 = "";
        panjang = password.length();
        for(int j=0;j<panjang;j++){
            if(j!=panjang-1){
                txt2 = password.substring(j,j+1);
            }else{
                txt2 = password.substring(j,panjang);
            }

            switch (txt2){
                case "A":
                    txt += "Y";break;
                case "B":
                    txt += "V";break;
                case "C":
                    txt += "T";
                    break;
                case "D":
                    txt += "X";
                    break;
                case "E":
                    txt += "Q";
                    break;
                case "F":
                    txt += "U";
                    break;
                case "G":
                    txt += "R";
                    break;
                case "H":
                    txt += "W";
                    break;
                case "I":
                    txt += "S";
                    break;
                case "J":
                    txt += "N";
                    break;
                case "K":
                    txt += "F";
                    break;
                case "L":
                    txt += "P";
                    break;
                case "M":
                    txt += "D";
                    break;
                case "N":
                    txt += "Z";
                    break;
                case "O":
                    txt += "G";
                    break;
                case "P":
                    txt += "I";
                    break;
                case "Q":
                    txt += "M";
                    break;
                case "R":
                    txt += "J";
                    break;
                case "S":
                    txt += "O";
                    break;
                case "T":
                    txt += "A";
                    break;
                case "U":
                    txt += "K";
                    break;
                case "V":
                    txt += "C";
                    break;
                case "W":
                    txt += "L";
                    break;
                case "X":
                    txt += "H";
                    break;
                case "Y":
                    txt += "E";
                    break;
                case "Z":
                    txt += "B";
                    break;
                case "a":
                    txt += "k";
                    break;
                case "b":
                    txt += "h";
                    break;
                case "c":
                    txt += "z";
                    break;
                case "d":
                    txt += "j";
                    break;
                case "e":
                    txt += "v";
                    break;
                case "f":
                    txt += "o";
                    break;
                case "g":
                    txt += "p";
                    break;
                case "h":
                    txt += "s";
                    break;
                case "i":
                    txt += "g";
                    break;
                case "j":
                    txt += "q";
                    break;
                case "k":
                    txt += "n";
                    break;
                case "l":
                    txt += "e";
                    break;
                case "m":
                    txt += "r";
                    break;
                case "n":
                    txt += "t";
                    break;
                case "o":
                    txt += "y";
                    break;
                case "p":
                    txt += "i";
                    break;
                case "q":
                    txt += "m";
                    break;
                case "r":
                    txt += "f";
                    break;
                case "s":
                    txt += "d";
                    break;
                case "t":
                    txt += "u";
                    break;
                case "u":
                    txt += "b";
                    break;
                case "v":
                    txt += "w";
                    break;
                case "w":
                    txt += "x";
                    break;
                case "x":
                    txt += "l";
                    break;
                case "y":
                    txt += "a";
                    break;
                case "z":
                    txt += "c";
                    break;
                case "1":
                    txt += "6";
                    break;
                case "2":
                    txt += "0";
                    break;
                case "3":
                    txt += "7";
                    break;
                case "4":
                    txt += "9";
                    break;
                case "5":
                    txt += "3";
                    break;
                case "6":
                    txt += "2";
                    break;
                case "7":
                    txt += "5";
                    break;
                case "8":
                    txt += "4";
                    break;
                case "9":
                    txt += "8";
                    break;
                case "0":
                    txt += "1";
                    break;
                case "&":
                    txt += "|";
                    break;
                case "|":
                    txt += "/";
                    break;
                case ".":
                    txt += ".";
                    break;
                case "_":
                    txt += "_";
                    break;
                case "-":
                    txt += "-";
                    break;
                case " ":
                    txt += " ";
                    break;
                default:
                    txt+=txt2;
                    break;
            }
        }
        return txt;
    }

    public static String decrypt(String password)
    {
        //dekrip password:
        int panjang = 0;
        String txt = "";
        String txt2 = "";
        panjang = password.length();
        for(int j=0;j<panjang;j++){
            if(j!=panjang-1){
                txt2 = password.substring(j,j+1);
            }else{
                txt2 = password.substring(j,panjang);
            }
            switch (txt2)
            {
                case "Y":
                    txt += "A";
                    break;
                case "V":
                    txt += "B";
                    break;
                case "T":
                    txt += "C";
                    break;
                case "X":
                    txt += "D";
                    break;
                case "Q":
                    txt += "E";
                    break;
                case "U":
                    txt += "F";
                    break;
                case "R":
                    txt += "G";
                    break;
                case "W":
                    txt += "H";
                    break;
                case "S":
                    txt += "I";
                    break;
                case "N":
                    txt += "J";
                    break;
                case "F":
                    txt += "K";
                    break;
                case "P":
                    txt += "L";
                    break;
                case "D":
                    txt += "M";
                    break;
                case "Z":
                    txt += "N";
                    break;
                case "G":
                    txt += "O";
                    break;
                case "I":
                    txt += "P";
                    break;
                case "M":
                    txt += "Q";
                    break;
                case "J":
                    txt += "R";
                    break;
                case "O":
                    txt += "S";
                    break;
                case "A":
                    txt += "T";
                    break;
                case "K":
                    txt += "U";
                    break;
                case "C":
                    txt += "V";
                    break;
                case "L":
                    txt += "W";
                    break;
                case "H":
                    txt += "X";
                    break;
                case "E":
                    txt += "Y";
                    break;
                case "B":
                    txt += "Z";
                    break;
                case "k":
                    txt += "a";
                    break;
                case "h":
                    txt += "b";
                    break;
                case "z":
                    txt += "c";
                    break;
                case "j":
                    txt += "d";
                    break;
                case "v":
                    txt += "e";
                    break;
                case "o":
                    txt += "f";
                    break;
                case "p":
                    txt += "g";
                    break;
                case "s":
                    txt += "h";
                    break;
                case "g":
                    txt += "i";
                    break;
                case "q":
                    txt += "j";
                    break;
                case "n":
                    txt += "k";
                    break;
                case "e":
                    txt += "l";
                    break;
                case "r":
                    txt += "m";
                    break;
                case "t":
                    txt += "n";
                    break;
                case "y":
                    txt += "o";
                    break;
                case "i":
                    txt += "p";
                    break;
                case "m":
                    txt += "q";
                    break;
                case "f":
                    txt += "r";
                    break;
                case "d":
                    txt += "s";
                    break;
                case "u":
                    txt += "t";
                    break;
                case "b":
                    txt += "u";
                    break;
                case "w":
                    txt += "v";
                    break;
                case "x":
                    txt += "w";
                    break;
                case "l":
                    txt += "x";
                    break;
                case "a":
                    txt += "y";
                    break;
                case "c":
                    txt += "z";
                    break;
                case "6":
                    txt += "1";
                    break;
                case "0":
                    txt += "2";
                    break;
                case "7":
                    txt += "3";
                    break;
                case "9":
                    txt += "4";
                    break;
                case "3":
                    txt += "5";
                    break;
                case "2":
                    txt += "6";
                    break;
                case "5":
                    txt += "7";
                    break;
                case "4":
                    txt += "8";
                    break;
                case "8":
                    txt += "9";
                    break;
                case "1":
                    txt += "0";
                    break;
                case "|":
                    txt += "&";
                    break;
                case "/":
                    txt += "|";
                    break;
                case ".":
                    txt += ".";
                    break;
                case " ":
                    txt += " ";
                    break;
                case "_":
                    txt += "_";
                    break;
                case "-":
                    txt += "-";
                    break;
                default:
                    txt+=txt2;
                    break;
            }
        }

        return txt;
    }
}
