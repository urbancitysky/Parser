import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;
/*
 * H
 */
public class EnParser {
	private String current_token;
	private int table_entry, token_number, stacktop, wait;

	public EnParser(String _current_token, int _table_entry, int _token_number) {
		current_token = _current_token;
		table_entry = _table_entry;
		token_number = _token_number;
	}

	void parser_from_file(String filename){
		try{
			boolean boolEOF = false;
			BufferedReader file_in = new BufferedReader(new FileReader(filename));
			if(!file_in.ready()){
				System.out.println("can't find file");
			}
			Stack<Integer> S = new Stack();
			S.push(1);
			//if doesn't reach the end of file, carry on
			while(!boolEOF){
				String strTrimedLine = file_in.readLine().trim();
				if(!strTrimedLine.equals("")){
					//seperate a line of tokens putting into a string[] by white spaces
					String[] ArrayTokens = strTrimedLine.split("\\s");
					if(ArrayTokens.length > 0){
						int nTokenIndex = 0;
						current_token = ArrayTokens[nTokenIndex];
						//if tokens exist, do while
						while(ArrayTokens.length > nTokenIndex){
							//translate tokens into corresponding #
							token_number = getNumberOfCurrentToken(current_token);
							stacktop = S.peek();
							if(stacktop > 0){
								//check Predict Table for next state
								table_entry = next_table_entry(stacktop,Math.abs(token_number));
								//Productions
								switch(table_entry){
								case 1:
									System.out.println("Fire 1");
									stacktop = S.pop();
									//<Start> -> import <Libtoken> <Libtoken_Tail> <Code>
									S.push(4); S.push(2); S.push(3); S.push(-2);
									break;
								case 2:
									System.out.println("Fire 2");
									stacktop = S.pop();
									//<Start> -> <Code>
									S.push(4);
									break;
								case 3:
									System.out.println("Fire 3");
									stacktop = S.pop();
									//<Libtoken_Tail> -> import <Libtoken> <Libtoken_Tail>
									S.push(2); S.push(3); S.push(-2);
									break;
								case 4:
									System.out.println("Fire 4");
									stacktop = S.pop();
									//<Libtoken_Tail> -> lambda
									break;
								case 5:
									System.out.println("Fire 5");
									stacktop = S.pop();
									//<Libtoken> -> librarytoken ;
									S.push(-20);S.push(-15);
									break;
								case 6:
									System.out.println("Fire 6");
									stacktop = S.pop();
									//<Code> -> program id ; <varibles> <body> <end>
									S.push(21); S.push(10); S.push(5); S.push(-20); S.push(-7); S.push(-1);
									break;
								case 7:
									System.out.println("Fire 7");
									stacktop = S.pop();
									//<variables> -> var <variable_type> : id ; <variable_type_tail>
									S.push(6); S.push(-20); S.push(-7); S.push(-21); S.push(7); S.push(-3);
									break;
								case 8:
									System.out.println("Fire 8");
									stacktop = S.pop();
									//<variable_type_tail> -> <variable_type> : id ; <variable_type_tail>
									S.push(6);S.push(-20);S.push(-7);S.push(-21);S.push(7);
									break;
								case 9:
									System.out.println("Fire 9");
									stacktop = S.pop();
									//<variable_type_tail> -> lambda
									break;
								case 10:
									System.out.println("Fire 10");
									stacktop = S.pop();
									//<variable_type> -> <variable> <variable_tail>
									S.push(8); S.push(9);
									break;
								case 11:
									System.out.println("Fire 11");
									stacktop = S.pop();
									//<variable_tail> -> , <variable> <variable_tail>
									S.push(8);S.push(9);S.push(-19);
									break;
								case 12:
									System.out.println("Fire 12");
									stacktop = S.pop();
									//<variable_tail> -> lambda
									break;
								case 13:
									System.out.println("Fire 13");
									stacktop = S.pop();
									//<variable> -> id
									S.push(-7);
									break;
								case 14:
									System.out.println("Fire 14");
									stacktop = S.pop();
									//<body> -> begin <statement_list> return int ;
									S.push(-20);S.push(-9); S.push(-6);S.push(11);S.push(-4);
									break;
								case 15:
									System.out.println("Fire 15");
									stacktop = S.pop();
									//<statement_list> -> <statement> <statement_tail>
									S.push(12); S.push(13);
									break;
								case 16:
									System.out.println("Fire 16");
									stacktop = S.pop();
									//<statement_tail> -> <statement> <statement_tail>
									S.push(12); S.push(13);
									break;
								case 17:
									System.out.println("Fire 17");
									stacktop = S.pop();
									//<statement> -> lambda
									break;
								case 18:
									System.out.println("Fire 18");
									stacktop = S.pop();
									//<statement> -> while ( <condition> ) do <statement_list> end do ;
									S.push(-20); S.push(-36); S.push(-5); S.push(11); S.push(-36); S.push(-23); S.push(14); S.push(-22); S.push(-35);
									break;
								case 19:
									System.out.println("Fire 19");
									stacktop = S.pop();
									//<statement> -> id := <expression> ;
									S.push(-20); S.push(16); S.push(-34); S.push(-7);
									break;
								case 20:
									System.out.println("Fire 20");
									stacktop = S.pop();
									//<statement> -> device_openfiletoken ;
									S.push(-20); S.push(-17);
									break;
								case 21:
									System.out.println("Fire 21");
									stacktop = S.pop();
									//<statement> => for id := id to id do <statement_list> end do ;
									S.push(-20); S.push(-36);S.push(-5);S.push(11);S.push(-36);S.push(-7);S.push(-47);S.push(-7);S.push(-34);S.push(-7);S.push(-37);
									break;
								case 22:
									System.out.println("Fire 22");
									stacktop = S.pop();
									//<statement> -> repeat do <statement_list> until ( <condition> ) end do ;
									S.push(-20);S.push(-36);S.push(-5);S.push(-23);S.push(14);S.push(-22);S.push(-42);S.push(11);S.push(-36);S.push(-41);
									break;
								case 23:
									System.out.println("Fire 23");
									stacktop = S.pop();
									//<statement> -> if ( <condition> ) then begin <statement_list> end ; else begin <statement_list> end ;
									S.push(-20);S.push(-5);S.push(11);S.push(-4);S.push(-39);S.push(-20);S.push(-5);S.push(11);S.push(-4);S.push(-40);S.push(-23);S.push(14);S.push(-22);S.push(-38);
									break;
								case 24:
									System.out.println("Fire 24");
									stacktop = S.pop();
									//<statement> -> open filetoken ;
									S.push(-20);S.push(-16);S.push(-43);
									break;
								case 25:
									System.out.println("Fire 25");
									stacktop = S.pop();
									//<statement> -> close filetoken ;
									S.push(-20);S.push(-16);S.push(-44);
									break;
								case 26:
									System.out.println("Fire 26");
									stacktop = S.pop();
									//<statement> -> write ( <expression> ) ;
									S.push(-20);S.push(-23);S.push(16);S.push(-22);S.push(-46);
									break;
								case 27:
									System.out.println("Fire 27");
									stacktop = S.pop();
									//<statement> -> read ( <variable_type> ) ;
									S.push(-20);S.push(-23);S.push(7);S.push(-22);S.push(-45);
									break;
								case 28:
									System.out.println("Fire 28");
									stacktop = S.pop();
									//<condition> -> <expression> <relational_op> <expression>
									S.push(16);S.push(15);S.push(16);
									break;
								case 29:
									System.out.println("Fire 29");
									stacktop = S.pop();
									//<relational_op> -> <
									S.push(-28);
									break;
								case 30:
									System.out.println("Fire 30");
									stacktop = S.pop();
									//<relational_op> -> >
									S.push(-29);
									break;
								case 31:
									System.out.println("Fire 31");
									stacktop = S.pop();
									//<relational_op> -> <=
									S.push(-30);
									break;
								case 32:
									System.out.println("Fire 32");
									stacktop = S.pop();
									//<relational_op> -> >=
									S.push(-31);
									break;
								case 33:
									System.out.println("Fire 33");
									stacktop = S.pop();
									//<relational_op> -> ==
									S.push(-32);
									break;
								case 34:
									System.out.println("Fire 34");
									stacktop = S.pop();
									//<relational_op> -> !=
									S.push(-33);
									break;
								case 35:
									System.out.println("Fire 35");
									stacktop = S.pop();
									//<expression> -> <primary> <primary_tail>
									S.push(17);S.push(18);
									break;
								case 36:
									System.out.println("Fire 36");
									stacktop = S.pop();
									//<expression> -> string
									S.push(-8);
									break;
								case 37:
									System.out.println("Fire 37");
									stacktop = S.pop();
									//<primary_tail> -> + <prmary> <primary_tail>
									S.push(17);S.push(18);S.push(-24);
									break;
								case 38:
									System.out.println("Fire 38");
									stacktop = S.pop();
									//<primary_tail> -> - <primary> <primary_tail>
									S.push(17);S.push(18);S.push(-25);
									break;
								case 39:
									System.out.println("Fire 39");
									stacktop = S.pop();
									//<primary_tail> -> lambda
									break;
								case 40:
									System.out.println("Fire 40");
									stacktop = S.pop();
									//<primary> -> <secondary> <secondary_tail>
									S.push(19);S.push(20);
									break;
								case 41:
									System.out.println("Fire 41");
									stacktop = S.pop();
									//<secondary_tail> -> * <secondary> <secondary_tail>
									S.push(19);S.push(20);S.push(-26);
									break;
								case 42:
									System.out.println("Fire 42");
									stacktop = S.pop();
									//<secondary_tail> -> / <secondary> <secondary_tail>
									S.push(19);S.push(20);S.push(-27);
									break;
								case 43:
									System.out.println("Fire 43");
									stacktop = S.pop();
									//<secondary_tial> -> lambda
									break;
								case 44:
									System.out.println("Fire 44");
									stacktop = S.pop();
									//<secondary> -> ( <expression> )
									S.push(-23);S.push(16);S.push(-22);
									break;
								case 45:
									System.out.println("Fire 45");
									stacktop = S.pop();
									//<secondary> -> id
									S.push(-7);
									break;
								case 46:
									System.out.println("Fire 46");
									stacktop = S.pop();
									//<secondary> -> int
									S.push(-9);
									break;
								case 47:
									System.out.println("Fire 47");
									stacktop = S.pop();
									//<secondary> -> real
									S.push(-10);
									break;
								case 48:
									System.out.println("Fire 48");
									stacktop = S.pop();
									//<secondary> -> scientific
									S.push(-11);
									break;
								case 49:
									System.out.println("Fire 49");
									stacktop = S.pop();
									//<secondary> -> currencylit
									S.push(-12);
									break;
								case 50:
									System.out.println("Fire 50");
									stacktop = S.pop();
									//<secondary> -> abs ( <expression> )
									S.push(-23);S.push(16);S.push(-22);S.push(-13);
									break;
								case 51:
									System.out.println("Fire 51");
									stacktop = S.pop();
									//<secondary -> fabs ( <expression> )
									S.push(-23);S.push(16);S.push(-22);S.push(-14);
									break;
								case 52:
									System.out.println("Fire 52");
									stacktop = S.pop();
									//<end> -> end .
									S.push(-18);S.push(-5);
									break;


								case 98:
									System.out.println("Scan error --> " + current_token);
									//scan error occurs, goto next token directly
									nTokenIndex++;
									break;
								case 99:
									System.out.println("Pop error");
									//pop error occurs, take out one # from stack
									stacktop = S.pop();
									break;
								default:
									System.out.println("error");
								}//end switch
							}
							//if stacktop == input token, pop out and move to next input
							else if(stacktop == token_number){
								System.out.println("Match and pop " + current_token);
								stacktop = S.pop();
								nTokenIndex++;
								if(nTokenIndex < ArrayTokens.length)
									current_token = ArrayTokens[nTokenIndex];
							}
							else{
								System.out.println("error");
								//error occurs, skip current token
								nTokenIndex++;
							}
						}//end while      
					}
				}
				// if current loop is the last line of file, out while loop
				if(!file_in.ready())
					boolEOF = true;
			}
			file_in.close();
			if(stacktop == token_number){
				System.out.println("Parsing finished, ACCEPT!!!");
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	int next_table_entry(int _non_terminal, int _token){
		int parse_table[][]={
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,2,1,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,99},
				{0,4,3,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,99,99,98,98,98,98,98,98,98,98,98,98,98,98,5,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,6,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,99},
				{0,98,98,7,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,9,98,98,8,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,10,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,11,98,12,98,12,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,13,98,98,98,98,98,98,98,98,98,98,98,99,98,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,14,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,99,99,15,98,98,98,98,98,98,98,98,98,15,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,15,98,15,15,98,98,15,99,15,15,15,15,99,98},
				{0,98,98,98,98,17,17,16,98,98,98,98,98,98,98,98,98,16,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,16,98,16,16,98,98,16,17,16,16,16,16,99,98},
				{0,98,98,98,98,99,99,19,98,98,98,98,98,98,98,98,98,20,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,18,98,21,23,98,98,22,99,24,25,27,26,99,98},
				{0,98,98,98,98,98,98,28,98,28,28,28,28,28,28,98,98,98,98,98,98,98,28,99,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,99,98,99,99,99,99,99,99,98,98,98,98,98,98,98,99,98,98,98,98,98,29,30,31,32,33,34,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,35,36,35,35,35,35,35,35,98,98,98,98,98,99,98,35,99,98,98,98,98,99,99,99,99,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,39,98,98,39,37,38,98,98,39,39,39,39,39,39,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,40,98,40,40,40,40,40,40,98,98,98,98,98,99,98,40,99,99,99,98,98,99,99,99,99,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,43,98,98,43,43,43,41,42,43,43,43,43,43,43,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,98,98,45,98,46,47,48,49,50,51,98,98,98,98,98,99,98,44,99,99,99,99,99,99,99,99,99,99,99,98,98,98,98,98,98,98,98,98,98,98,98,98,99,98},
				{0,98,98,98,98,52,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,98,99,99}
		};
		return parse_table[_non_terminal][_token];
	}

	int getNumberOfCurrentToken(String _CurrentToken){
		//return a proper # for a terminal
		int nCurrentTokenNumber = -1;
		String[] strTerminals = {"program", "import", "var", "begin", "end", "return", "id", "string", "int", "real", "scientific",
				"currencylit", "abs", "fabs", "librarytoken", "filetoken", "device_openfiletoken", ".", ",", ";", ":", "(", ")", "+",
				"-", "*", "/", "<", ">", "<=", ">=", "==", "!=", ":=", "while", "do", "for", "if", "else", "then", "repeat", "until",
				"open", "close", "read", "write", "to"};
		for(int i = 0;i < strTerminals.length;i++){
			if(strTerminals[i].equals(_CurrentToken))
				nCurrentTokenNumber = (i + 1) * -1;
		}
		return nCurrentTokenNumber;
	}
}//end class
