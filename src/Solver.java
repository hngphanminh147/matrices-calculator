import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {
	List<Matrix> matrices = new ArrayList<>();
	
	public Solver() {
		FileReader file;
		BufferedReader br;
		try {
			file = new FileReader("src/matrix-src.txt");
			br = new BufferedReader(file);

			String line;
			List<String> array;
			int rows;
			int columns;
			
			while ((line = br.readLine()) != null) {	
				array = Arrays.asList(line.split(" "));
				
				rows = Integer.parseInt(array.get(0));
				columns = Integer.parseInt(array.get(1));
				Double[][] matrix = new Double[rows][columns];
				
				for (int i = 0; i < rows; i++) {
					array = Arrays.asList(br.readLine().split(" "));
					for (int j = 0; j < array.size(); j++) {
						matrix[i][j] = Double.parseDouble(array.get(j));
					}
				}
				br.readLine();
				
				Matrix m = new Matrix(matrix);
				this.matrices.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showAll() {
		for (Matrix matrix: this.matrices) {
			matrix.show();
		}
	}
	
	public void show(int index) {
		this.matrices.get(index).show();
	}
	
	public void solveAll(){
		for (Matrix matrix: this.matrices) {
			List<String> result = matrix.solve();
			System.out.println(result);
		}
	}
	
	public void solve(int index) {
		List<String> result = this.matrices.get(index).solve();
		System.out.println(result);
	}
}
