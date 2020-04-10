import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generator {
	private int rows = 3;
	private int columns = 4;

	public Generator(int number) {
		FileWriter file;
		Random rd = new Random();
		try {
//			file = new FileWriter("src/matrix-src.txt", true);
			file = new FileWriter("src/matrix-src.txt");
			
			for (int i = 0; i < number; i++) {
				file.write(String.valueOf(this.rows) + " ");
				file.write(String.valueOf(this.columns) + " ");
				file.write('\n');

				for (int j = 0; j < this.rows; j++) {
					for (int k = 0; k < this.columns; k++) {
						file.write(String.valueOf(rd.nextInt(10)));
						file.write(' ');
					}
					file.write('\n');
				}
				file.write('\n');
			}

			file.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
