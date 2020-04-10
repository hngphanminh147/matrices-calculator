import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
	private final char parameter = 'a';
	private Double[][] matrix;

	private int columns;
	private int rows;

	// Default cons
	public Matrix() {
		this.matrix = null;
		this.columns = 0;
		this.rows = 0;
	}

	// Copy cons
	public Matrix(Double[][] matrix) {
		this.rows = matrix.length;
		this.columns = matrix[0].length;
		this.matrix = matrix;
	}
	// Setter
	public void setMatrix(Double[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		this.columns = matrix[0].length;
	}

	// Getter
	public Double[][] getMatrix() {
		return matrix;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public int getPivot(int row) {
		for (int i = 0; i < this.getColumns(); i++) {
			if (this.getMatrix()[row][i] != 0) {
				return i;
			}
		}
		return -1;
	}

	public double getPivotValue(int row) {
		return this.matrix[row][this.getPivot(row)];
	}

	// Gaussian-Jordan elimination
	public Double[][] getReducedRowEchelonForm() {
		Matrix matrix = new Matrix(this.getMatrix());
		// get reduced row echelon form
		// rows traverse
		for (int i = 0; i < matrix.getRows(); i++) {
			boolean isEmtyRow = true;
			for (int j = 0; j < matrix.getColumns(); j++) {
				if (matrix.getMatrix()[i][j] != 0) {
					isEmtyRow = false;
					break;
				}
			}
			if (isEmtyRow) {
				continue;
			}

			// get pivot
			int pos = matrix.getPivot(i);
			for (int j = i + 1; j < matrix.getRows(); j++) {
				// swap rows
				if (pos > matrix.getPivot(j) && matrix.getPivot(j) != -1) {
					matrix.setMatrix(matrix.swapRow(i, j));
					break;
				}
			}
			pos = matrix.getPivot(i);

			// multiply each cell of pivots row by 1/pivot
			matrix.setMatrix(matrix.scalarMultication(i, 1.0 / matrix.getMatrix()[i][pos]));
//			matrix.show();

			// subtract multiples of pivot to other row
			for (int j = 0; j < matrix.getRows(); j++) {
				if (j == i) {
					continue;
				}
				double scale = -matrix.getMatrix()[j][pos];
				matrix.setMatrix(matrix.rowAddition(j, i, scale));
//				matrix.show();
			}
		}
		return matrix.getMatrix();
	}

	public int getRank() {
		Matrix matrix = new Matrix(this.getReducedRowEchelonForm());
		int rank = 0;
		for (int i = 0; i < matrix.getRows(); i++) {
			for (int j = 0; j < matrix.getColumns(); j++) {
				if (matrix.getMatrix()[i][j] != 0) {
					rank++;
					break;
				}
			}
		}
		return rank;
	}

	// Operation
	public Double[][] swapRow(int row1, int row2) {
		Double[][] matrix = this.getMatrix().clone();
		for (int i = 0; i < this.getColumns(); i++) {
			double temp = matrix[row1][i];
			matrix[row1][i] = matrix[row2][i];
			matrix[row2][i] = temp;
		}
		return matrix;
	}

	public Double[][] scalarMultication(int row, double number) {
		Double[][] matrix = this.getMatrix().clone();
		for (int i = 0; i < this.getColumns(); i++) {
			matrix[row][i] *= number;
		}
		return matrix;
	}

	public Double[][] scalarAddition(int row, double number) {
		Double[][] matrix = this.getMatrix().clone();
		for (int i = 0; i < this.getColumns(); i++) {
			matrix[row][i] += number;
		}
		return matrix;
	}

	public Double[][] rowAddition(int row1, int row2, double number) {
		Double[][] matrix = this.getMatrix().clone();
		for (int i = 0; i < this.getColumns(); i++) {
			matrix[row1][i] += number * matrix[row2][i];
		}
		return matrix;
	}

	//Solve
	public List<String> solve() {
		List<String> result = new ArrayList<String>();
		Matrix matrix = new Matrix(this.getReducedRowEchelonForm());

		// argue;
		// flag check if every coefficient matrixs cell except the last one equal to zero
		boolean isEqualZero = true;
		int rank = 0;
		for (int i = 0; i < matrix.getRows(); i++) {
			for (int j = 0; j < matrix.getColumns() - 1; j++) {
				isEqualZero = true;
				if (matrix.getMatrix()[i][j] != 0) {
					isEqualZero = false;
					rank++;
					break;
				}
			}
			if (isEqualZero && (matrix.getMatrix()[i][matrix.getColumns() - 1]) != 0) {
				// no solution
				// return empty result
				return result;
			}
		}

		if (rank == matrix.getRows()) {
			// unique solution
			for (int i = 0; i < matrix.getRows(); i++) {
				BigDecimal number = new BigDecimal(matrix.getMatrix()[i][matrix.getRows()]).setScale(2,
						RoundingMode.HALF_UP);
				result.add(String.valueOf(number));
			}
		} else {
			// infinitely many solutions
			for (int i = 0; i < matrix.getColumns() - 1; i++) {
				result.add("");
			}

			// mark parameter
			for (int i = 0; i < matrix.getRows(); i++) {
				for (int j = matrix.getPivot(i) + 1; j < matrix.getColumns() - 1; j++) {
//					result.set(j, (matrix.getMatrix()[i][j] != 0) ? String.valueOf((char) (matrix.parameter + i)): result.get(j));
					if (matrix.getMatrix()[i][j] != 0) {
						result.set(j, String.valueOf((char) (matrix.parameter + j)));
					}
				}
			}

			for (int i = 0; i < matrix.getRows(); i++) {
				// ignore empty row
				if (matrix.getPivot(i) == -1) {
					continue;
				}
				String string = "";					
				// add argumented value
				BigDecimal number = new BigDecimal(matrix.getMatrix()[i][matrix.getRows()]).setScale(2,
						RoundingMode.HALF_UP);
				string = String.valueOf(number);
				for (int j = matrix.getPivot(i) + 1; j < matrix.getColumns() - 1; j++) {
					if (matrix.getMatrix()[i][j] == 0) {
						continue;
					}
					// add inverse sign
					string += (matrix.getMatrix()[i][j] < 0) ? " + " : " - ";
					BigDecimal pos = new BigDecimal(Math.abs(matrix.getMatrix()[i][j])).setScale(2, RoundingMode.HALF_UP);
					string += String.valueOf(pos);
					string += (char) (matrix.parameter + j);
				}
				result.set(matrix.getPivot(i), string);
			}
		}
		return result;
	}

	public void show() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if (matrix[i][j] < 0) {
					System.out.printf("%.1f ", matrix[i][j]);
				} else {
					System.out.printf(" %.1f ", matrix[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
