/*
	Alex Hahn
	CS 3A
	Simple Computer Emulator
	
	Description: emulates a simple computer machine. has 10 basic instructions to execute commands and 
				 perform calculations. Entire emulation is run in the console in a text based interface. 
				 User is prompted to select options through a menu system in order to set memory, input
				 cards, program counter, and also clear data and execute the program. All simple computer
				 data (memory, input, output, accumulator, program counter, instruction register) are all
				 initialized in the main method and then passed into the mainMenu method which recursively
				 runs the program updating data as the user specifies and exiting when the user specifies.
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;

public class simpleComputer 
{
	public static void main(String[] args)
	{	
		//initializes all SC data elements to recursively run through emulator program
		String[] memory = new String[100];		
		String[] inputCards = new String[11];
		String[] outputCards = new String[10];
		
		String informationRegister = null;
		
		int programCounter = 0;
		int accumulator = 0;
		int carry = 0;
		
		//begins program recursion by calling the mainMenu method and passing all data elements
		mainMenu(memory, inputCards, outputCards, programCounter, accumulator, carry, informationRegister);
	}
	
	public static void mainMenu(String[] memory, String[] input, String[] output, int PC, int AC, int carry, 
								String IR)
	{
		Scanner keyInput = new Scanner(System.in);
		
		//prompts user to select a menu option
		System.out.println("\n" + "\n" + "MAIN MENU" + "\n");
		System.out.println("Choose one of the following options: " + "\n");
		System.out.println("1: Enter instructions into memory");
		System.out.println("2: Enter values into input cards");
		System.out.println("3: Set program counter");
		System.out.println("4: Execute program and display output");
		System.out.println("5: Clear data (memory, input cards, output cards or all)");
		System.out.println("6: Exit Simple Computer emulator");
		
		System.out.print("\n" + "Enter corresponding number to select a menu option: ");
		int choice = keyInput.nextInt();
		
		//processes for each menu option
		switch (choice)
		{
		case 1: memory = setMemory(memory);
				break;
				
		case 2: input = setIC(input);
				break;
				
		case 3: PC = setPC(memory);
				break;
		
		//processes all simple computer instructions and displays final output
		case 4: process(memory, input, output, PC, AC, carry, IR);
				break;
		
		//calls the clearMenu method. Maintains SC data by passing the data to the method
		case 5: clearMenu(memory, input, output, PC, AC, carry, IR);
				break;
				
		case 6: System.exit(0);
			
		default: System.out.println("\n" + "Invalid selection, please try again");
				 break;
		}
		
		mainMenu(memory, input, output, PC, AC, carry, IR);
	}
	
	//clearMenu method, allows user to selectively, or totally, clear SC data
	public static void clearMenu(String[] memory, String[] input, String[] output, int PC, int AC, int carry, 
								 String IR)
	{
		Scanner keyInput = new Scanner(System.in);
		
		//prompts user to selects what they wish to clear (individual or all)
		System.out.println("\n" + "\n" + "CLEAR DATA MENU" + "\n");
		System.out.println("Choose one of the following options: " + "\n");
		System.out.println("1: Clear memory");
		System.out.println("2: Clear input cards: ");
		System.out.println("3: Clear output cards: ");
		System.out.println("4: Clear all");
		System.out.println("5: Return to main menu");
		
		System.out.print("\n" + "Enter corresponding number to select a menu option: ");
		int choice = keyInput.nextInt();
		
		//switch statements to correspond to menu options
		switch (choice)
		{
		case 1: clear(choice, memory, input, output, PC, AC, carry ,IR);
				break;
				
		case 2: clear(choice, memory, input, output, PC, AC, carry, IR);
				break;
				
		case 3: clear(choice, memory, input, output, PC, AC, carry, IR);
				break;
				
		case 4: clear(choice, memory, input, output, PC, AC, carry, IR);
				break;
				
		case 5: mainMenu(memory, input, output, PC, AC, carry, IR);
		}
		
		//returns user to the main menu after clearing. maintains SC data by passing to the method
		mainMenu(memory, input, output, PC, AC, carry, IR);
	}
	
	//clear method will pass user choice and clear either memory, input, output or all three
	public static void clear(int choice, String[] memory, String[] input, String[] output, int PC, int AC, 
							 int carry, String IR)
	{
		switch (choice)
		{
		
		//clears memory
		case 1: for (int i = 1; i < memory.length; i++)
				{
					memory[i] = null;
				}
				JOptionPane.showMessageDialog(null, "Memory has been cleared");
				break;
		
		//clears input cards	
		case 2: for (int i = 0; i < input.length; i++)
				{
					input[i] = null;
				}
				JOptionPane.showMessageDialog(null, "Input cards have been cleared");
				break;
		
		//clears output cards
		case 3: for (int i = 0; i < output.length; i++)
				{
					output[i] = null;
				}
				JOptionPane.showMessageDialog(null, "Output cards have been cleared");
				break;
		
		//clears all three
		case 4: for (int i = 1; i < memory.length; i++)
				{
					memory[i] = null;
				}
				for (int i = 0; i < input.length; i++)
				{
					input[i] = null;
				}
				for (int i = 0; i < output.length; i++)
				{
					output[i] = null;
				}
				JOptionPane.showMessageDialog(null, "Memory, input and output cards have been cleared");
				break;
				
		default: JOptionPane.showMessageDialog(null, "Invalid selection, returning to main menu. Please " +
													 "try again");
				 break;
		}
		
		//maintains recursion by calling clearMenu method and passing all SC data elements
		clearMenu(memory, input, output, PC, AC, carry, IR);
	}
	
	//setMemory method to first specify starting cell of memory input, then instructions into memory
	public static String[] setMemory(String[] cells)
	{
		Scanner input = new Scanner(System.in);
		
		//prompts user to select a start cell for memory input
		System.out.print("\n" + "Enter a cell address (1 - 99) to begin memory input: ");
		int startCell = input.nextInt();
		
		//protects cell 00 as well as prevents user from entering data into non-existent cell
		while (startCell <= 0 || startCell >= 100)
		{
			System.out.println("\n" + "Invalid entry. Cell address MUST be between 1 and 99");
			System.out.print("\n" + "Enter a cell address (1 - 99) to begin memory input: ");
			startCell = input.nextInt();
		}
				
		System.out.println("\n" + "Enter memory contents consecutively starting with cell " + startCell +
						   "\n" + "Memory contents must be numbers between 001 and 999" + "\n" +
						   "\n" + "Type 'done' and press enter when all cells have been entered" + "\n" +
						   "\n" + "At any time type 'change' and press enter to switch to a different " +
						   "memory cell" + "\n");
		
		//prompts user to enter data into each cell consecutively, starting with specified start cell
		for (int i = startCell; i < 100; i++)
		{
			cells[0] = "001";
			
			System.out.print(i + ": ");
			String inputString = input.next();
			
			inputString = format(inputString);
			
			//allows user to enter "change" instead of SC instruction in order to switch cells
			if (inputString.equals("change"))
			{
				cells = setMemory(cells);
			}
			
			//checks that all SC instructions are only numbers, will deny input if letters are entered
			for (int k = 0; k < 3; k++)
			{
				if (inputString.equals("done") || inputString.equals("change"))
				{
					k = 3;
				}
				else
				{
					try
					{
						@SuppressWarnings("unused")
						int digitTest = Integer.parseInt(inputString.substring(k));
					}
					catch (NumberFormatException ex)
					{
						System.out.println("\n" + "Memory input must ONLY contain digits (0 - 9)");
						inputString = "invalid";
						k = 3;
					}
				}
			}
			
			//checks instruction length to verify all entered instructions are only 3 numerical digits
			switch (inputString.length())
			{				
			case 3: cells[i] = inputString;
					break;
			
			//allows user to type "done" when memory input is complete
			case 4: if (inputString.equals("done"))
					{
						i = 100;
						break;
					}
			
			case 6: if (inputString.equals("change"))
					{
						i = 100;
						break;
					}
					
			default: System.out.println("\n" + "Invalid entry. Memory content MUST be a number " +
										"between 001 and 999");
					 System.out.println("Re-enter memory content at cell " + i + "\n");
					 i--;
					 break;
			}
		}
		
		//replaces all 'null' cells with blank spaces to maintain look of final output
		for (int i = 0; i < cells.length; i++)
		{
			if (cells[i] == null)
			{
				cells[i] = "   ";
			}
		}
		
		return cells;
	}
	
	//prompts user to enter input cards starting at input card 1 (10 input cards possible)
	public static String[] setIC(String[] inputCards)
	{		
		Scanner input = new Scanner(System.in);

		System.out.println("\n" + "Enter each input card followed by the return key" + "\n" +
						   "Type 'done' followed by the return key when all cards have been entered" + "\n");
		
		for (int i = 0; i < (inputCards.length - 1); i++)
		{
			System.out.print((i + 1) + ": ");
			
			try
			{
			int intIC = input.nextInt();
				
				//prevents user from entering invalid input card values
				while (intIC < -999 || intIC > 999)
				{
					System.out.println("\n" + "Invalid entry. Input cards must be between -999 and 999");
					System.out.println("Re-enter content at input card " + (i + 1) + "\n");
					System.out.print((i + 1) + ": ");
					intIC = input.nextInt();
				}
				
				inputCards[i] = format(Integer.toString(intIC));
			}
			
			//allows user to enter "done" to finish input card input
			catch (InputMismatchException ex)
			{
				i = inputCards.length;
			}
		}
		
		inputCards[10] = null;

		return inputCards;
	}
	
	//prompts user to set the initial value of the program counter
	public static int setPC(String[] memory)
	{
		Scanner input = new Scanner(System.in);
		
		System.out.print("\n" + "Enter the start value of the program counter: ");
		int pc = input.nextInt();
		
		while (pc < 0 || pc >= 100 || memory[pc] == null)
		{
			System.out.print("\n" + "Invalid entry. There is no instruction stored at memory cell " +
							 pc + "\n" + "Enter a new start value of the program counter: ");
			pc = input.nextInt();
		}
		
		return pc;
	}
	
	//process method which passes in all SC data and processes op-codes
	public static void process(String[] memory, String[] input, String[] output, int PC, int AC, int carry, 
							   String IR)
	{		
		//initializes current input and output cards at card 1 (array index 0)
		int currentIC = 0;
		int currentOC = 0;
		
		for (int i = PC; i < memory.length; i++)
		{
				IR = memory[i];
				
				//separates each memory instruction into separate op-code and address int values
				int opCode = Integer.parseInt(IR.substring(0, 1));
				int address = Integer.parseInt(IR.substring(1, 3));
				
				//switch block that processes all op-codes and performs specified instructions
				switch (opCode)
				{
				case 0: if (input[currentIC] == null)
						{
							JOptionPane.showMessageDialog(null, "Program halted. No more input cards.");
							PC = 0;
							currentIC++;
							display(memory, input, output, PC, AC, carry, IR);
							System.exit(0);
						}
						else
						{
							memory[address] = input[currentIC];
							currentIC++;
							PC++;
							break;
						}
				
				case 1: output[currentOC] = memory[address];
						currentOC++;
						PC++;
						break;

				case 2: AC += Integer.parseInt(memory[address]);
						
						if (AC > 999)
						{
							carry = 1;
							AC = Integer.parseInt(Integer.toString(AC).substring(1, 4));
						}
						PC++;
						break;

				case 3: AC -= Integer.parseInt(memory[address]);
						PC++;
						break;
				
				case 4: AC = Integer.parseInt(memory[address]);
						PC++;
						break;
				
				case 5: memory[address] = format(Integer.toString(AC));
						PC++;
						break;
				
				case 6: memory[99] = format(Integer.toString(PC + 1));
						PC = address;
						i = address - 1;
						break;
						
				case 7: if (AC < 0)
						{
							PC = address;
							i = address - 1;
							break;
						}
						else
						{
							PC++;
							break;
						}
				
				case 8: boolean negative = false;
						String currentCell = memory[i];
						String currentAC = Integer.toString(AC);

						if (AC < 0)
						{
							negative = true;
							currentAC = currentAC.substring(1, currentAC.length());
							currentAC = format(currentAC);
						}
						else
						{
						currentAC = format(currentAC);
						}
						
						int left = Integer.parseInt(currentCell.substring(1, 2));
						int right = Integer.parseInt(currentCell.substring(2, 3));
						
						switch (left)
						{
							case 0: break;
							
							case 1: carry = Integer.parseInt(currentAC.substring(0, 1));
									currentAC = currentAC.substring(1, 3) + "0";
									break;
									
							case 2: carry = Integer.parseInt(currentAC.substring(1, 2));
									currentAC = currentAC.substring(2, 3) + "00";
									break;
									
							case 3: carry = Integer.parseInt(currentAC.substring(2, 3));
									currentAC = "000";
									break;
									
							case 4: carry = 0;
									currentAC = "000";
									break;
						}
						
						switch (right)
						{
							case 0: break;
						
							case 1: currentAC = Integer.toString(carry) + currentAC.substring(0, 2);
									carry = 0;
									break;
							
							case 2: currentAC = "0" + Integer.toString(carry) + currentAC.substring(0, 1);
									carry = 0;
									break;
									
							case 3: currentAC = "00" + Integer.toString(carry);
									carry = 0;
									break;
									
							case 4: currentAC = "000";
									carry = 0;
									break;
						}
						
						if (negative == true)
						{
							currentAC = "-" + currentAC;
						}
						
						AC = Integer.parseInt(currentAC);
						
						break;
					
				case 9: PC = Integer.parseInt(memory[address]);
						i = 101;
						break;
				
				default: display(memory, input, output, PC, AC, carry, IR);
				}
		}
		
		//displays processed data
		display(memory, input, output, PC, AC, carry, IR);
	}
	
	//display method to output simple computer data after processing op-code instructions
	public static void display(String[] memory, String[] input, String[] output, int PC, int AC, int carry, 
							   String IR)
	{
		Scanner keyInput = new Scanner(System.in);
		
		//prints post-process accumulator, instruction register and program counter values
		//uses the format method to maintain simple computer visual protocol
		System.out.println("\n" + "  AC: " + carry + " " + format(Integer.toString(AC)) + 
						   "  IR: " + IR + "  PC: " + format(Integer.toString(PC)) + "\n");
		System.out.println("  Input" + "    Output");
		
		/* prints input and output cards. if a value is positive it concatenates a space to the
		   front of the value to maintain proper spacing with negative values */
		for (int i = 0; i < output.length; i++)
		{
			if (input[i] == null)
			{
				input[i] = "   ";
			}
			
			if (output[i] == null)
			{
				output[i] = "   ";
			}
			
			System.out.print("  " + formatLabels(Integer.toString((i + 1))) + ":");
			if (input[i].length() == 3)
			{
				System.out.print(" " + input[i]);
			}
			else
			{
				System.out.print(input[i]);
			}
			
			System.out.print("  " + formatLabels(Integer.toString((i + 1))) + ":");
			if (output[i].length() == 3)
			{
				System.out.print(" " + output[i] + "\n");
			}
			else
			{
				System.out.print(output[i] + "\n");
			}
		}
		
		System.out.println("\n" + "  Memory");
		
		//prints memory contents after SC processing
		for (int i = 0; i < 20; i++)
		{
			System.out.print("  " + formatLabels(Integer.toString(i)) + ":");
			
			/* if a value is positive it concatenates a space to the front of the value to 
			   maintain proper spacing with negative values */
			if (memory[i].length() == 3)
			{
				System.out.print(" " + memory[i]);
			}
			else
			{
				System.out.print(memory[i]);
			}
			
			//separates SC's 100 memory cells into 5 columns of 20 cells
			System.out.print("  " + formatLabels(Integer.toString((i + 20))) + ":");
			if (memory[i + 20].length() == 3)
			{
				System.out.print(" " + memory[i + 20]);
			}
			else
			{
				System.out.print(memory[i]);
			}
			
			System.out.print("  " + formatLabels(Integer.toString((i + 40))) + ":");
			if (memory[i + 40].length() == 3)
			{
				System.out.print(" " + memory[i + 40]);
			}
			else
			{
				System.out.print(memory[i + 40]);
			}
			
			System.out.print("  " + formatLabels(Integer.toString((i + 60))) + ":");
			if (memory[i + 60].length() == 3)
			{
				System.out.print(" " + memory[i + 60]);
			}
			else
			{
				System.out.print(memory[i + 60]);
			}
			
			System.out.print("  " + formatLabels(Integer.toString((i + 80))) + ":");
			if (memory[i + 80].length() == 3)
			{
				System.out.print(" " + memory[i + 80] + "\n");
			}
			else
			{
				System.out.print(memory[i + 80] + "\n");
			}
		}
		
		//prompts user to return to main menu or exit the SC emulator
		System.out.println("\n" + "Return to main menu or exit emulator?" + "\n");
		System.out.println("1: Main Menu");
		System.out.println("2: Exit");
		System.out.print("\n" + "Enter corresponding number to select a menu option: ");
		int choice = keyInput.nextInt();
		
		switch (choice)
		{
		case 1: mainMenu(memory, input, output, PC, AC, carry, IR);
				break;
				
		case 2: System.exit(0);
		
		default: JOptionPane.showMessageDialog(null, "Invalid selection. Returning to main menu");
				 mainMenu(memory, input, output, PC, AC, carry, IR);
				 break;
		}
	}
	
	//format method to maintain SC spacing and formatting when displaying the output
	public static String format(String formatMe)
	{	
		if (formatMe.equals("done"))
		{
			formatMe = "0000";
		}
		
		if (formatMe.equals("change"))
		{
			formatMe = "00000";
		}
		
		int negativeTest = Integer.parseInt(formatMe);
		
		if (negativeTest < 0)
		{
			formatMe = formatMe.substring(1);
		}
		
		switch (formatMe.length())
		{
		case 1: formatMe = "00" + formatMe;
				break;
				
		case 2: formatMe = "0" + formatMe;
				break;
				
		case 3: break;
		
		case 4: formatMe = "done";
				break;
				
		case 5: formatMe = "change";
				break;
				
		default: break;
		}
		
		if (negativeTest < 0)
		{
			formatMe = "-" + formatMe;
		}

		return formatMe;
	}
	
	//formatLabels method to format input and output card labels
	public static String formatLabels(String formatMe)
	{
		switch (formatMe.length())
		{
		case 1: formatMe = "0" + formatMe;
				break;
		
		default: break;
		}
		
		return formatMe;
	}
	
}
