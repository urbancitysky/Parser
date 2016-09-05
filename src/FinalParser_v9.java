import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * CECS444 Assignment 6 Final Parser
 * Date: 05-13-2013
 * @author Shih Kai Chen
 */
public class FinalParser_v9{
	private int state, token_number,stacktop,table_entry;
	private int current_read, currentTokenNumber;
	private char current_char;
	private boolean buffered;
	private String token_under_construction, current_token;
	
	int nTokenIndex = 0;
	ArrayList<String> tokens = new ArrayList<String>();	
	ArrayList<Integer> tokensNumberArray = new ArrayList<Integer>();	
	
	public FinalParser_v9(String _token_under_construction, int _current_read, int _state){
		token_under_construction = _token_under_construction;
		current_read = _current_read;
		state = _state;
	}
	void read_characters(String filename){
		try{
			current_char = ' ';
			char ReadChar[] = new char[1];
			boolean boolEOF = false;
			
			buffered = false;
			
			
			
			BufferedReader file_in = new BufferedReader(new FileReader(filename));
			
			
			while(file_in.ready()){
				if((!buffered)||(current_char == ' ')||(current_char == '\n')||current_char == '\r'){
					file_in.read(ReadChar);
					current_char = ReadChar[0];	// reset to S0
				}
				if(Character.isLetter(current_char))
					current_read = 0;
				else if(Character.isDigit(current_char))
					current_read = 1;
				else if (Character.isSpaceChar(current_char))
					current_read = 20;
				else{
					switch(current_char){
					case '$':
						current_read = 2;break;
					case '.':
						current_read = 3;break;
					case ',':
						current_read = 4;break;
					case '[':
						current_read = 5;break;
					case ']':
						current_read = 6;break;
					case '"':
						current_read = 7;break;
					case '/':
						current_read = 8;break;
					case '_':
						current_read = 9;break;
					case '+':
						current_read = 10;break;
					case '-':
						current_read = 11;break;
					case '<':
						current_read = 12;break;
					case '>':
						current_read = 13;break;
					case '!':
						current_read = 14;break;
					case '=':
						current_read = 15;break;
					case ':':
						current_read = 16;break;
					case '^':
						current_read = 17;break;
					case '\n':
						current_read = 18;break;
					case '*':
						current_read = 19;break;
					case '@':
						current_read = 22;break;
					case '&':
						current_read = 22;break;
					case '#':
						current_read = 21;break;
					case '~':
						current_read = 22;break;
					case '\t':
						current_read = 20;break;
					case '\'':
						current_read = 22;break;
					case ';':
						current_read = 23;break;
					case '(':
						current_read = 24;break;
					case ')':
						current_read = 25;break;
					case '{':
						current_read = 26;break;
					case '}':
						current_read = 27;break;
					default:
						current_read = 28;break;
					}// switch case
				}// end if

				if((next_state(state,current_read)!=-1) && (action(state,current_read)==1)){
					buffered = false;
					token_under_construction=token_under_construction+current_char;
					state= next_state(state,current_read);
				}else if (( next_state(state,current_read)==-1)&&(action(state,current_read)==2)){
					
					
					
					
//					for(int i=0; i<tokens.size();i++ )
//						System.out.println(tokens.get(i)+" ");
					
					buffered = true;
					// 1 = Identfier, 2 = Integer, 3 = Real, 4 = Currency, 
					// 5 = Library Token, 6 = Scientific Notaion, 7 = Device or File, 
					// 8 = String Literal, 9 = Comment Token, 10 = Simple Operator, 
					// 11 = Compound Operator
					switch(look_up(state,current_read)){
					case 1:
						switch(token_under_construction){
						case"program":
							token_number=-1;break;
						case"var":
							token_number=-3;break;
						case "begin":
							token_number=-5;break;
						case"abs":
							token_number=-29;break;
						case"end":
							token_number=-30;break;
						case"return":
							token_number=-31;break;
						case"until":
							token_number=-32;break;
						case"for":
							token_number=-33;break;
						case"repeat":
							token_number=-34;break;
						case"while":
							token_number=-35;break;
						case"if":
							token_number=-36;break;
						case"do":
							token_number=-37;break;
						case"then":
							token_number=-38;break;
						case"println":
							token_number=-39;break;
						case"read":
							token_number=-40;break;
						case"else":
							token_number=-41;break;
						case"to":
							token_number=-47;break;
						case"write_to_device":
							token_number=-42;break;
						case"device_open":
							token_number=-43;break;
						case"device_close":
							token_number=-44;break;
						case"read_from_device":
							token_number=-45;break;
						default: 
							token_number=-4;break;
						}
//						System.out.println(token_under_construction+"\t\t--> ID" );
						break;
					case 2:
//						System.out.println(token_under_construction+"\t\t--> Valid integer");
						token_number=-23;
						
						break;
					case 3:
//						System.out.println(token_under_construction+"\t\t--> Valid real number" );
						token_number=-24;
						break;
					case 4:
//						System.out.println(token_under_construction+"\t\t--> Valid currency" );
						token_number=-26;
						break;
					case 5:
//						System.out.println(token_under_construction +"\t--> Library Token ");
						token_number=-2;
						break;
					case 6:
//						System.out.println(token_under_construction +"\t\t--> Valid scientific number");
						token_number=-25;
						break;
					case 7:
//						System.out.println(token_under_construction + "\t\t--> File token" );
						token_number=-28;break;
					case 8:
//						System.out.println(token_under_construction + "\t\t--> Valid string literal" );
						token_number=-27;
						break;
					case 9:
//						System.out.println(token_under_construction + "\t\t--> --> Comment Token");
						token_number=-4;
						break;
					case 10:
//						System.out.println(token_under_construction + "\t\t--> Simple Operator " );
						switch(token_under_construction){
						case ".":
							token_number=-6;break;
						case ";":
							token_number=-8;break;
						case ",":
							token_number=-9;break;
						case ":":
							token_number=-10;break;
						case "(":
							token_number=-11;break;
						case ")":
							token_number=-12;break;
						case "<":
							token_number=-13;break;
						case ">":
							token_number=-14;break;
						case "+":
							token_number=-19;break;
						case "-":
							token_number=-20;break;
						case "*":
							token_number=-21;break;
						case "/":
							token_number=-22;break;
						default: 
							break;
						}
						break;
					case 11:
//						System.out.println(token_under_construction + "\t\t--> Compound Operator" );
						switch(token_under_construction){
						case ":=":
							token_number=-7;break;
						case "<=":
							token_number=-15;break;
						case ">=":
							token_number=-16;break;
						case "==":
							token_number=-17;break;
						case "!=":
							token_number=-18;break;
						default: 
							break;
						}
						break;
					case 12:
						break;
					default:
						System.out.println("error");
						break;
					}
					nTokenIndex++;
					tokens.add(token_under_construction);
					tokensNumberArray.add(token_number);
					state =0;
					token_under_construction="";
				}//if
			}// end scanner while loop
			
//			for(int i = 0 ; i<tokens.size();i++){
//				if (tokens.get(i).equals(" "))
//					tokens.remove(i);
//			}
//			for(int i = 0 ; i<tokens.size();i++)
//				System.out.println(tokens.get(i));
			
//			System.out.println(tokensNumberArray);
//			System.out.println(tokens);
//			System.out.println("tokens: "+nTokenIndex);
			System.out.println("-----------------------Parser-----------------\n");
			Stack<Integer> S = new Stack<Integer>();
			int ArrayIndex=0;
			S.push(1);
			while(!boolEOF){
//				System.out.println("ArrayIndex: "+ ArrayIndex + " "+ tokens.get(ArrayIndex) );
				stacktop = S.peek();
				if(stacktop > 0){
					currentTokenNumber = tokensNumberArray.get(ArrayIndex);
//					System.out.println(stacktop +" , "+ currentTokenNumber);
					table_entry = next_table_entry(stacktop,Math.abs(currentTokenNumber));
					switch(table_entry){
					case 1:
						System.out.println("Fire 1");
						stacktop = S.pop();
						//1. <Goal> --> <Libtoken> <Libtoken__Tail><Start> 
						// 3 2 4
						// 4 2 3
						S.push(4); S.push(2); S.push(3);
						break;
					case 2:
						System.out.println("Fire 2");
						stacktop = S.pop();
						//2. <Goal> --> <Start>
						// 4							
						S.push(4);
						break;
					case 3:
						System.out.println("Fire 3");
						stacktop = S.pop();
						//<Libtoken__Tail>  --> <Libtoken><Libtoken_Tail>
						// 3 2		
						// 2 3
						S.push(2); S.push(3);
						break;
					case 4:
						System.out.println("Fire 4");
						stacktop = S.pop();
						//4. <Libtoken__Tail>  --> �f
						break;
					case 5:
						System.out.println("Fire 5");
						stacktop = S.pop();
						//<Libtoken> -> librarytoken ;
						// -2
						S.push(-2);
						break;
					case 6:
						System.out.println("Fire 6");
						stacktop = S.pop();
						//6. <Start> --> <Code>
						// 5
						S.push(5); 
						break;
					case 7:
						System.out.println("Fire 7");
						stacktop = S.pop();
						//7. <Code> --> program id; <variables> < Main > <end>
						// -1 -4 -8 8 15 22
						//22 15 8 -8 -4 -1
						S.push(22); S.push(15); S.push(6); S.push(-8); S.push(-4);S.push(-1);
						break;
					case 8:
						System.out.println("Fire 8");
						stacktop = S.pop();
						//8. <variables> --> var <variable_type> : id ; <variable_type_tail>
						//-3 8 -10 -4 -8 7
						//7 -8 -4 -10 8 -3
						S.push(7);S.push(-8);S.push(-4);S.push(-10);S.push(8);S.push(-3);
						break;
					case 9:
						System.out.println("Fire 9");
						stacktop = S.pop();
						//9. <variable__type_tail> --> <variable_type> : id ; <variable_type_tail> 
						//8 -10 -4 -8 7
						//7 -8 -4 -10 8
						S.push(7);S.push(-8);S.push(-4);S.push(-10);S.push(8);
						break;
					case 10:
						System.out.println("Fire 10");
						stacktop = S.pop();
						//10. <variable__type_tail> --> �f
						S.push(11);
						break;
					case 11:
						System.out.println("Fire 11");
						stacktop = S.pop();
						//11. <variable_type> --> <variable> <variable tail>
						//10 9 
						//9 10
						S.push(9);S.push(10);
						break;
					case 12:
						System.out.println("Fire 12");
						stacktop = S.pop();
						//12. <variable_tail>  --> , <variable> <variable_tail>
						// -9 10 9
						// 9 10 -9
						S.push(9);S.push(10);S.push(-9);
						break;
					case 13:
						System.out.println("Fire 13");
						stacktop = S.pop();
						//13. <variable_tail>  --> �f							
						break;
					case 14:
						System.out.println("Fire 14");
						stacktop = S.pop();
						//14. <variable> --> id
						//-4
						S.push(-4);
						break;
					case 15:
						System.out.println("Fire 15");
						stacktop = S.pop();
						//15. <Main> --> begin <statement_list> return id;
						// -5 12 -31 -4 -8
						//-8 -4 -31 12 -5
						S.push(-8); S.push(-4);S.push(-31);S.push(12);S.push(-5);
						break;
					case 16:
						System.out.println("Fire 16");
						stacktop = S.pop();
						//16. <statement_list> --> <statement> <statement tail>
						// 14 13
						// 13 14
						S.push(13); S.push(14);
						break;
					case 17:
						System.out.println("Fire 17");
						stacktop = S.pop();
						//17. <statemen_ tail�r --> <statement> <statement_tail> 
						// 14 13
						// 13 14
						S.push(13);S.push(14);
						break;
					case 18:
						System.out.println("Fire 18");
						stacktop = S.pop();
						//18. <statemen_ tail�r -->  �f							
						break;
					case 19:
						System.out.println("Fire 19");
						stacktop = S.pop();
						//19. <statement> --> id := <expressionGversion2>;
						// -4 -7 17 -8
						//-8 17 -7 -4
						S.push(-8);S.push(17); S.push(-7); S.push(-4); 
						break;
					case 20:
						System.out.println("Fire 20");
						stacktop = S.pop();
						//20. <statement> --> device_open<filetoken>; 
						// -43 -28 -8
						// -8 -28 -43
						S.push(-8);S.push(-28);S.push(-43);
						break;
					case 21: 
						System.out.println("Fire 21");
						stacktop = S.pop();
						//21. <statement> -->for id := int to int do <statement__list> end do; 
						// -33 -4 -7 -23 -47 -23 -37 12 -30 -37 -8
						// -8 -37 -30 12 -37 -23 -47 -23 -7 -4 -33
						S.push(-8); S.push(-37);S.push(-30);S.push(12);S.push(-37);S.push(-23);S.push(-47);S.push(-23);S.push(-7);
						S.push(-4);S.push(-33);
						break;
					case 22:
						System.out.println("Fire 22");
						stacktop = S.pop();
						//22. <statement> -->repeat do <statement_list> until (<Booleancondition>) end do; 
						//-34 -37 12 -32 -11 15 -12 -30 -37 -8
						//-8 -37 -30 -12 15 -11 -32 12 -37 -34							
						S.push(-8);S.push(-37);S.push(-30);S.push(-12);S.push(15);S.push(-11);S.push(-32);S.push(12);S.push(-37);S.push(-34);
						break;
					case 23:
						System.out.println("Fire 23");
						stacktop = S.pop();
						//23. <statement> --> while (<Booleancondition>) do <statement_list> end do;
						// -35 -11 15 -12 -37 12 30 -37 -8
						// -8 -37 30 12 -37 -12 15 -11 -35
						S.push(-8);S.push(-37);S.push(30);S.push(12);S.push(-37);S.push(-12);S.push(15);S.push(-11);S.push(-35);
						break;
					case 24:
						System.out.println("Fire 24");
						stacktop = S.pop();
						//24. <statement> -->if (<Booleancondition>) then begin <statement_list> end ; else begin <staten:ent_list> end; 
						//-36 15 -38 -5 -12 -30 -8 -41 -5 12 -30 -8 -44 -8
						// -8 -44 -8 -30 12 -5 -41 -8 -30 12 -5 -38 15 -36
						S.push(-8);S.push(-44);S.push(-8);S.push(-30);S.push(12);S.push(-5);S.push(-41);S.push(-8);S.push(-30);S.push(12);S.push(-5);
						S.push(-38);S.push(15);S.push(36);
						break;
					case 25:
						System.out.println("Fire 25");
						//25.  device_close<filetoken>;
						S.push(-8);S.push(-28);S.push(-44);
						break;
					case 26:
						System.out.println("Fire 26");
						stacktop = S.pop();
						//25. <statement> --> read_from_device <filetoken> ;
						// -45 -28 -8	
						//-8 -28 -45
						S.push(-8);S.push(-28);S.push(-45);
						break;
					case 27:
						System.out.println("Fire 27");
						stacktop = S.pop();
						//26. <statement> -->write_to_device<filetoken>; 
						// -42 -28 -8			
						//-8 -28 -42
						S.push(-8);S.push(-28);S.push(-42);
						break;
					case 28:
						System.out.println("Fire 28");
						stacktop = S.pop();
						//27. <statement> -->println(<expressicnGversion2>) ; 
						// -39 -11 17 -12 -8
						// -8 -12 17 -11 -39
						S.push(-8);S.push(-12);S.push(17);S.push(-11);S.push(-39);
						break;
					case 29:
						System.out.println("Fire 29");
						stacktop = S.pop();
						//28. <statement> --> read(<variable type>);
						// -40 -11 8 -12 -8
						//-8 -12 8 -11 -40
						S.push(-8);S.push(-12);S.push(8);S.push(-11);S.push(-40);
						break;
					case 30:
						System.out.println("Fire 30");
						stacktop = S.pop();
						//29. <Booleancondition> --> <expressionGversion2> <relational_op> <expressionGversion2>
						//17 16 17
						S.push(17);S.push(16);S.push(17);
						break;
					case 31:
						System.out.println("Fire 31");
						stacktop = S.pop();
						//30. <relational_op> --> <
						//-13
						S.push(-13);
						break;
					case 32:
						System.out.println("Fire 32");
						stacktop = S.pop();
						//31. <relational_op> --> >
						//-14
						S.push(-14);
						break;
					case 33:
						System.out.println("Fire 33");
						stacktop = S.pop();
						//32. <relational_op> --> <= 
						//-15
						S.push(-15);
						break;
					case 34:
						System.out.println("Fire 34");
						stacktop = S.pop();
						//<relational_op> --> >= 
						//-16
						S.push(-16);
						break;
					case 35:
						System.out.println("Fire 35");
						stacktop = S.pop();
						//<relational_op> --> == 
						// -17
						S.push(-17);
						break;
					case 36:
						System.out.println("Fire 36");
						stacktop = S.pop();
						//<relational_op> --> !=
						//-18
						S.push(-18);
						break;
					case 37:
						System.out.println("Fire 37");
						stacktop = S.pop();
						//<expressionGversion2> --> <primary> <primary_tail> 
						//19 18
						//18 19
						S.push(18);S.push(19);
						break;
					case 38:
						System.out.println("Fire 38");
						stacktop = S.pop();
						//37. <expressionGversion2> --> stringliteral
						//-27
						S.push(-27);
						break;
					case 39:
						System.out.println("Fire 39");
						stacktop = S.pop();
						//38. <primary_tail> --> + <primary><pnmary_tail> 
						//-19 19 18 
						//18 19 -19
						S.push(18);S.push(19);S.push(-19);
						break;
					case 40:
						System.out.println("Fire 40");
						stacktop = S.pop();
						//39. <primary_tail> --> - <primary> <primary__tail> 
						//-20 19 18
						// 18 19 -20
						S.push(18);S.push(19);S.push(-20);
						break;
					case 41:
						System.out.println("Fire 41");
						stacktop = S.pop();
						//<primary_tail> -->  �f
						break;
					case 42:
						System.out.println("Fire 42");
						stacktop = S.pop();
						//<primary>  --> <secondary> <secondary_tail>
						// 21 20
						//20 21
						S.push(20);S.push(21);
						break;
					case 43:
						System.out.println("Fire 43");
						stacktop = S.pop();
						//42. <secondary_tail> --> * <secondary><secondary_tail> 
						// -21 21 20
						// 20 21 -21
						S.push(20);S.push(21);S.push(-21);
						break;
					case 44:
						System.out.println("Fire 44");
						stacktop = S.pop();
						//43. <secondary_tail> --> / <secondary> <secondary_tail>
						// -22 21 20 
						// 20 21 -22
						S.push(20);S.push(21);S.push(-22);
						break;
					case 45:
						System.out.println("Fire 45");
						stacktop = S.pop();
						//44. <secondary_tail> -->  �f
						break;
					case 46:
						System.out.println("Fire 46");
						stacktop = S.pop();
						//<secondary> --> (<expressionGversion2>) 
						//-11 17 -12 
						// -12 17 -11
						S.push(-12);S.push(17);S.push(-11);
						break;
					case 47:
						System.out.println("Fire 47");
						stacktop = S.pop();
						//<secondary> --> id 
						// -4
						S.push(-4);
						break;
					case 48:
						System.out.println("Fire 48");
						stacktop = S.pop();
						//<secondary> --> int 
						// -23
						S.push(-23);
						break;
					case 49:
						System.out.println("Fire 49");
						stacktop = S.pop();
						//<secondary> --> real 
						// -24
						S.push(-24);
						break;
					case 50:
						System.out.println("Fire 50");
						stacktop = S.pop();
						//<secondary> --> scientific 
						// -25
						S.push(-25);
						break;
					case 51:
						System.out.println("Fire 51");
						stacktop = S.pop();
						//<secondary> --> currencylit
						//-26
						S.push(-26);
						break;
					case 52:
						System.out.println("Fire 52");
						stacktop = S.pop();
						//51. <secondary> --> abs (<expressionGversion2>)
						//-29 -11 17 -12
						// -12 17 -11 -29
						S.push(-12);S.push(17);S.push(-11);S.push(-29);
						break;
					case 53:

						break;
					case 54:
						System.out.println("Fire 54");
						stacktop = S.pop();
						//<end> -> end .
						// -30 -6 
						// -6 -30
						S.push(-6);S.push(-30);
						break;
					case 98:
						System.out.print("Scan error -->\t\t" + tokens.get(ArrayIndex) );
						//scan error occurs, goto next token directly
						ArrayIndex++;
						break;
					case 99:
						System.out.println("Pop error");
						//pop error occurs, take out one # from stack
						S.pop();
						break;
					default:
						System.out.println("parse error");
						break;
					}//end switch
				}else if(stacktop == currentTokenNumber){
//					System.out.println(stacktop +" , "+ currentTokenNumber);
					System.out.println("Match and pop \t\t" + tokens.get(ArrayIndex));
					stacktop= S.pop();
//					System.out.println("-------------------------------------");
					ArrayIndex++;
					currentTokenNumber = tokensNumberArray.get(ArrayIndex);
//					System.out.println(currentTokenNumber);
				}else{
					System.out.println("parse error2");
//					System.out.println(stacktop +" , "+ currentTokenNumber);
					currentTokenNumber = tokensNumberArray.get(ArrayIndex);
//					System.out.println(currentTokenNumber);
//					System.out.println("-------------------------------------");
					stacktop= S.pop();
					System.out.println(stacktop);
					ArrayIndex++;
				}// if else
//				System.out.println(ArrayIndex);
//				System.out.println(nTokenIndex);
				if(ArrayIndex==nTokenIndex-1)
					boolEOF = true;
			}// end parser
			System.out.println("Match and pop \t\t" + tokens.get(ArrayIndex)+"\nAccept!!");
			file_in.close();

		}catch(Exception e){
		}
	}

	int next_table_entry(int _non_terminal, int _token){
		int parse_table[][]={
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,2,1,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,4,3,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,99,5,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,6,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,7,98},
				{0,7,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,8,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,99,9,10,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,11,99,98,98,98,99,99,98,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,13,13,98,98,99,12,13,13,13,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,14,99,98,98,98,99,99,98,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,99,15,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,99,99,99,98,98,99,99,99,99,99,99,99,98,98},
				{0,98,98,98,16,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,16,16,16,16,98,98,16,16,98,16,16,16,16,98,98},
				{0,98,98,98,17,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,18,18,18,17,17,17,17,98,98,17,17,98,17,17,17,17,98,98},
				{0,98,98,98,19,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,32,32,98,98,98,21,22,23,24,98,98,28,29,98,27,20,25,26,98,98},
				{0,98,98,98,30,98,98,98,98,98,98,98,98,99,99,99,99,99,99,98,98,30,30,98,98,98,98,98,98,98,18,98,98,98,98,98,98,98,98,99,99,99,99,99,99,99,98,98},
				{0,98,98,98,99,98,98,98,98,98,98,98,98,31,32,33,34,35,36,98,98,98,98,98,98,98,98,99,39,39,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,37,98,98,98,99,98,98,35,98,99,99,99,99,99,99,98,98,37,37,37,37,98,37,38,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,99,98,98,98,41,98,98,98,41,41,41,41,41,41,41,39,40,98,98,98,98,98,98,99,44,44,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,42,98,98,98,99,98,98,40,98,99,99,99,99,99,99,99,99,99,99,42,42,42,42,98,98,42,99,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,99,98,98,98,45,98,98,98,45,45,45,45,45,45,45,45,45,43,44,99,99,99,99,98,98,98,98,98,98,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,47,98,98,98,99,98,98,44,99,99,99,99,99,99,99,99,99,99,99,48,49,50,51,98,98,52,99,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},
				{0,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,99,99,99,98,98,98,54,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98},


		};
		return parse_table[_non_terminal][_token];
	}
	private int next_state(int new_state, int new_char){
		int state_table[][] = { 
				{1,60,46,58,7,31,7,15,25,7,8,21,10,11,12,13,14,7,-1,7,98,38,7,7,85,7,17,7,-1},
				{1,2,-1,4,-1,-1,-1,-1,-1,3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{2,2,-1,-1,-1,-1,-1,-1,-1,3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{2,2,-1,-1,-1,-1,-1,-1,-1,3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{5,-1,-1,4,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,5,-1,-1,-1,6,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,9,-1,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,-1,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{15,15,-1,-1,-1,-1,-1,16,-1,-1,-1,-1,-1,-1,15,-1,-1,-1,-1,-1,15,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{18,18,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,18,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,20,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,9,22,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{23,23,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,24,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{23,23,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,24,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,27,-1,-1,-1,-1,-1,-1,9,-1,-1,-1,26,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,28,26,26,26,26,26,26,26,26,26},
				{27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,27,29,27,27,27,27,27,27,27,27,27,27},
				{-1,-1,-1,-1,-1,-1,-1,-1,30,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{32,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,81,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{34,34,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{34,34,-1,35,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{36,36,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{36,36,-1,-1,-1,-1,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{39,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{39,39,-1,-1,-1,-1,-1,40,-1,-1,-1,-1,40,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{41,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{42,42,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{42,42,-1,43,-1,-1,-1,-1,-1,-1,-1,-1,-1,45,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{44,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{44,44,-1,-1,-1,-1,-1,45,-1,-1,-1,-1,-1,45,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,50,-1,47,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,48,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,49,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,55,-1,47,51,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,52,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,53,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,54,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,47,51,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,56,-1,47,51,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,57,-1,51,47,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,57,-1,47,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,59,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,59,-1,-1,74,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,76,-1,61,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,62,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,62,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,63,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,64,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,65,65,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,66,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,67,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,68,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,70,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,71,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,72,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,74,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,73,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,95,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,75,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,78,-1,77,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,59,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,79,-1,77,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,79,-1,-1,80,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,81,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,82,-1,-1,74,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,83,-1,-1,74,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,96,83,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,73,-1,-1,-1},
				{-1,81,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,86,-1,-1,-1,-1,-1,-1,-1,-1,86,86,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,87,-1,87,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,88,-1,-1,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,89,-1,-1,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,90,-1,-1,69,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,90,-1,-1,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,92,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,93,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,94,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,73,-1,-1,-1},
				{-1,96,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,97,-1,-1,-1,-1,75,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,75,-1,-1,-1},
				{-1,97,-1,-1,74,-1,75,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,75,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
		};
		return state_table[new_state][new_char];
	}
	/*
	 * 0 error, 1 MA, 2 HR
	 */
	int action(int new_state, int new_char){
		int action_table[][] = {    
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0},
				{1,1,2,1,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,1,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,1,2,2,2,2,2,1,2,2,2,2,2,2,1,2,2,2,2,2,1,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,1,2,2,2,1,2,2,2,2,2,2,2,2,2},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,2,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{1,1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{1,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2},
				{2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2},
				{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,1,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2},
				{2,1,2,2,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		};
		return action_table[new_state][new_char];
	}
	/*	
	 * 1 ID, 2 int, 3 real, 4 $, 5 LT, 6 SN, 7 DF, 8 SL, 9 CT, 10 SO, 11 CO
	 */
	int look_up(int new_state, int new_char){
		int look_up_table[][] = { 
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{0,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
				{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,10,0,0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11},
				{10,10,10,10,10,10,10,10,10,10,10,10,0,0,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,10,10,10,10,0,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,10,10,10,10,10,0,0,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{0,0,10,10,10,10,10,0,10,10,10,10,10,10,0,10,10,10,10,10,0,10,10,10,10,10,10,10,10},
				{8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
				{10,10,10,10,10,10,10,10,10,10,0,0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{0,0,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,11,11,11,11,11,11,11,11,11,11},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
				{10,10,10,10,10,10,10,10,0,10,10,10,10,10,10,0,10,10,10,0,10,10,10,10,10,10,10,10,10},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
				{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
				{0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
				{0,0,10,10,10,10,10,0,10,10,10,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
				{10,0,10,0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{4,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{4,0,4,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{4,4,4,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{4,0,4,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{4,0,4,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{4,0,4,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				{10,0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{3,0,3,3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{2,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,3,3,3,3,3,3,3,3,3,3},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{6,0,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
				{6,0,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
				{6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,2,2,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,3,3},
				{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{2,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,0,2,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{3,0,3,3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{3,0,3,3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{3,3,3,3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,3,3},
				{3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{10,10,10,10,10,10,10,10,10,10,1,1,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
				{2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{2,2,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,2,2,2},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,3,3},
				{3,0,3,3,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,3,3},
				{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12},
		};
		return look_up_table[new_state][new_char];
	}
	
	
}
