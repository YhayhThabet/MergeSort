/*
@author Yhayh Thabet
@assignment project 2
@semester Fall 2019
@class COSC 311
@professor Dr. Haynes
@description Comparing speed of external merge sort and internal merge sort
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


// Used code from this link. Sorry, external sort wasn't going to work
// so, I just used this instead.
//https://codereview.stackexchange.com/questions/60155/external-file-based-mergesort
public class ExternalMergeSort {
	public static void merge(String inputFile, String outputFile, long blockSize) throws IOException {
		// readers for block1/2
		FileInputStream fis1 = new FileInputStream(inputFile);
		DataInputStream dis1 = new DataInputStream(fis1);
		FileInputStream fis2 = new FileInputStream(inputFile);
		DataInputStream dis2 = new DataInputStream(fis2);

		// writer to output file
		FileOutputStream fos = new FileOutputStream(outputFile);
		DataOutputStream dos = new DataOutputStream(fos);

		// merging 2 sub lists
		// go along pairs of blocks in inputFile
		// continue until end of input

		// initialize block2 at right position
		dis2.skipBytes((int) blockSize);

		// while we haven't reached the end of the file
		while (dis1.available() > 0) {
			// if block1 is last block, copy block1 to output
			if (dis2.available() <= 0) {
				while (dis1.available() > 0)
					dos.writeInt(dis1.readInt());
				break;
			}
			// if block1 not last block, merge block1 and block2
			else {
				long block1Pos = 0;
				long block2Pos = 0;
				boolean block1Over = false;
				boolean block2Over = false;

				// data read from each block
				int e1 = dis1.readInt();
				int e2 = dis2.readInt();

				// keep going until fully examined both blocks
				while (!block1Over | !block2Over) {
					// copy from block 1 if:
					// block1 hasn't been fully examined AND
					// block1 element less than block2s OR block2 has been fully examined
					while (!block1Over & ((e1 <= e2) | block2Over)) {
						dos.writeInt(e1);
						block1Pos += 4;
						if (block1Pos < blockSize & dis1.available() > 0)
							e1 = dis1.readInt();
						else
							block1Over = true;
					}
					// same for block2
					while (!block2Over & ((e2 < e1) | block1Over)) {
						dos.writeInt(e2);
						block2Pos += 4;
						if (block2Pos < blockSize & dis2.available() > 0)
							e2 = dis2.readInt();
						else
							block2Over = true;
					}
				}
			}
			// skip to next blocks
			dis1.skipBytes((int) blockSize);
			dis2.skipBytes((int) blockSize);
		}
		dis1.close();
		dis2.close();
		dos.close();
		fos.close();
	}

	public static void fillFile(int size) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.bin"));

		Random rand = new Random(97);

		for (int i = 0; i < size; i++) {
			dos.writeInt(rand.nextInt(10000));
		}

		dos.close();
	}

	// display runtime and dataset size
	public static void display(long time,  int size) {

		System.out.println("Runtime is " + time + " milliseconds.");
		System.out.println("Dataset Size: " + size);
	}

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("1 = 100,000");
		System.out.println("Enter a dataset size:");
		
		int size = 100000 * input.nextInt(); // setting size

		long start = 0, end = 0; // Get time

		fillFile(size);

		start = System.nanoTime();
		merge("data.bin", "buffer.bin", size);
		end = System.nanoTime();

		long time = (end - start) / 1000000; // Convert to milliseconds

		display(time, size);
	}
}
