# Parser

#### Program Description:
1.	The given grammar does not need to eliminate left recursion.
2.	The modification of the grammar:
	- a. device_close<filetoken>; 
	- b. device_open<filetoken>;
	- c. read_from_device<filetoken>;
	- d. write_to_device<filetoken>;
3.	The modifications of the sample test case are as follow:
	- a.	All comment lines are removed.
	- b.	Using:namespace:std; is revomed.
	- c.	for a := 125 to 0 do read (string); end do;

### Program Instruction:
1.	The program is able to read tokens from an external text file.
2.	Three files, Main.java, FinalParser_v10.java, and sample_test_case have to put under the same folder.
3.	Main class is where the program starts.
4.	Scanner is used to pass tokens to the Parser.
5.	The file name of the text file feeding to Scanner or Parser can only be changed in Main.
6.	GUI is not available, the result can only be seen in console.


### State Diagram
![state diagram](https://github.com/urbancitysky/Parser/blob/master/State%20Diagram.jpg)