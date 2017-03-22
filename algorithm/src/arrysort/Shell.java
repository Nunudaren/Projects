package arrysort;

public class Shell {
	public static void main(String[] args) {
		Shell s = new Shell();
		int[] a = { 45, 20, 80, 40, 26, 58, 66, 70 };
		s.shell_sort(a);
	}

	public void shell_sort(int[] arrays) {
		for (int d = 5; d > 0; d = d - 2) {
			for (int c = 0; c < arrays.length - d; c++) {
				for (int i = c; i < arrays.length; i = i + d) {
					for (int j = i; j > 0; j = j - d) {
						if (j < d)
							break;
						if (arrays[j] < arrays[j - d]) {
							int tmp;
							tmp = arrays[j];
							arrays[j] = arrays[j - d];
							arrays[j - d] = tmp;

						}
					}
				}

			}
			snp(arrays);
		}

	}

	public void snp(int[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			System.out.print(arrays[i] + " ");

		}
		System.out.println();
	}

}