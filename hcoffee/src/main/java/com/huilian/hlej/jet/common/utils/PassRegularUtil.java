package com.huilian.hlej.jet.common.utils;

import java.util.Random;


public  class PassRegularUtil {
	
	static String arrayLetter[]  = new String[52];
	
	static String arrayNumber[]  = new String[10];
	
	static String arraySymbol[]  = new String[30];
	
	
	static{
		initArrayNumber();
		initArrayLetter();
		initArraySymbol();
	}
	
	
	public static void initArrayNumber(){
		
		arrayNumber[0] = "0";
	    arrayNumber[1] = "1";
	    arrayNumber[2] = "2";
	    arrayNumber[3] = "3";
	    arrayNumber[4] = "4";
	    arrayNumber[5] = "5";
	    arrayNumber[6] = "6";
	    arrayNumber[7] = "7";
	    arrayNumber[8] = "8";
	    arrayNumber[9] = "9";
	}
	
	
	public static  void initArrayLetter(){
		    arrayLetter[0] = "a";
		    arrayLetter[1] = "b";
		    arrayLetter[2] = "c";
		    arrayLetter[3] = "d";
		    arrayLetter[4] = "e";
		    arrayLetter[5] = "f";
		    arrayLetter[6] = "g";
		    arrayLetter[7] = "h";
		    arrayLetter[8] = "i";
		    arrayLetter[9] = "j";
		    arrayLetter[10] = "k";
		    arrayLetter[11] = "l";
		    arrayLetter[12] = "m";
		    arrayLetter[13] = "n";
		    arrayLetter[14] = "o";
		    arrayLetter[15] = "p";
		    arrayLetter[16] = "q";
		    arrayLetter[17] = "r";
		    arrayLetter[18] = "s";
		    arrayLetter[19] = "t";
		    arrayLetter[20] = "u";
		    arrayLetter[21] = "v";
		    arrayLetter[22] = "w";
	        arrayLetter[23] = "x";
	        arrayLetter[24] = "y";
	        arrayLetter[25] = "z";
	        //大写字母
	        arrayLetter[26] = "A";
	        arrayLetter[27] = "B";
	        arrayLetter[28] = "C";
	        arrayLetter[29] = "D";
	        arrayLetter[30] = "E";
	        arrayLetter[31] = "F";
	        arrayLetter[32] = "G";
	        arrayLetter[33] = "H";
	        arrayLetter[34] = "I";
	        arrayLetter[35] = "J";
	        arrayLetter[36] = "K";
	        arrayLetter[37] = "L";
	        arrayLetter[38] = "M";
	        arrayLetter[39] = "N";
	        arrayLetter[40] = "O";
	        arrayLetter[41] = "P";
	        arrayLetter[42] = "Q";
	        arrayLetter[43] = "R";
	        arrayLetter[44] = "S";
	        arrayLetter[45] = "T";
	        arrayLetter[46] = "U";
	        arrayLetter[47] = "V";
	        arrayLetter[48] = "W";
	        arrayLetter[49] = "X";
	        arrayLetter[50] = "Y";
	        arrayLetter[51] = "Z";
	}
	
	
	public static void initArraySymbol(){
		
		arraySymbol[0] = "`";
		arraySymbol[1] = "~";
		arraySymbol[2] = "!";
		arraySymbol[3] = "@";
		arraySymbol[4] = "#";
		arraySymbol[5] = "$";
		arraySymbol[6] = "%";
		arraySymbol[7] = "^";
		arraySymbol[8] = "&";
		arraySymbol[9] = "*";
		arraySymbol[10] = "(";
		arraySymbol[11] = ")";
		arraySymbol[12] = "_";
		arraySymbol[13] = "+";
		arraySymbol[14] = "[";
		arraySymbol[15] = "]";
		arraySymbol[16] = "\\";
		arraySymbol[17] = "{";
		arraySymbol[18] = "}";
		arraySymbol[19] = "|";
		
		arraySymbol[20] = ";";
		arraySymbol[21] = "'";
		arraySymbol[22] = ":";
		arraySymbol[23] = "\"";
		
		arraySymbol[24] = ",";
		arraySymbol[25] = ".";
		arraySymbol[26] = "/";
		arraySymbol[27] = "<";
		arraySymbol[28] = ">";
		arraySymbol[29] = "?";
		
	}
	
	
	public static  String doGenerateBack(final int intPassLength){
		
		Random  classRandom = new Random();
		String strPassword = "";
		for(int i=0;i<intPassLength;i++){
			int classInt = classRandom.nextInt(2);
			//0代表数字类型
			if(classInt==0){
				int numberInt = classRandom.nextInt(10);
				strPassword =strPassword+arrayNumber[numberInt];
			}
			//1代表字母类型
			else if(classInt==1){
				int numberInt = classRandom.nextInt(52);
				strPassword =strPassword+arrayLetter[numberInt];
			}
			//2代表符号类型
			/*else if(classInt==2){
				int numberInt = classRandom.nextInt(30);
				strPassword =strPassword+arraySymbol[numberInt];
			}*/
		
		}
		return strPassword;
	}
	
	
	public static  String doGenerateReception(final int intPassLength){
		Random  classRandom = new Random();
		String strPassword = "";
		for(int i=0;i<intPassLength;i++){
			int classInt = classRandom.nextInt(3);
			//0代表数字类型
			if(classInt==0){
				int numberInt = classRandom.nextInt(10);
				strPassword =strPassword+arrayNumber[numberInt];
			}
			//1代表字母类型
			else if(classInt==1){
				int numberInt = classRandom.nextInt(52);
				strPassword =strPassword+arrayLetter[numberInt];
			}
			//2代表符号类型
			else if(classInt==2){
				int numberInt = classRandom.nextInt(30);
				strPassword =strPassword+arraySymbol[numberInt];
			}
		
		}
		return strPassword;
	}
	
	
	public static void main(String[] args){
		//生成前台密码
		System.out.println(PassRegularUtil.doGenerateReception(9));
		//生成后台密码
		System.out.println(PassRegularUtil.doGenerateBack(6));
	}
	
}
