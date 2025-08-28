// 3446. Sort Matrix by Diagonals
// Solved
// Medium
// Topics
// premium lock icon
// Companies
// Hint
// You are given an n x n square matrix of integers grid. Return the matrix such that:

// The diagonals in the bottom-left triangle (including the middle diagonal) are sorted in non-increasing order.
// The diagonals in the top-right triangle are sorted in non-decreasing order.
 

// Example 1:

// Input: grid = [[1,7,3],[9,8,2],[4,5,6]]

// Output: [[8,2,3],[9,6,7],[4,5,1]]

// Explanation:



// The diagonals with a black arrow (bottom-left triangle) should be sorted in non-increasing order:

// [1, 8, 6] becomes [8, 6, 1].
// [9, 5] and [4] remain unchanged.
// The diagonals with a blue arrow (top-right triangle) should be sorted in non-decreasing order:

// [7, 2] becomes [2, 7].
// [3] remains unchanged.
// Example 2:

// Input: grid = [[0,1],[1,2]]

// Output: [[2,1],[1,0]]

// Explanation:



// The diagonals with a black arrow must be non-increasing, so [0, 2] is changed to [2, 0]. The other diagonals are already in the correct order.

// Example 3:

// Input: grid = [[1]]

// Output: [[1]]

// Explanation:

// Diagonals with exactly one element are already in order, so no changes are needed.

 

// Constraints:

// grid.length == grid[i].length == n
// 1 <= n <= 10
// -105 <= grid[i][j] <= 105


class Solution {
    public int[][] sortMatrix(int[][] grid) {
        int len=grid.length;
        for(int i=0;i<len;i++){
            PriorityQueue<Integer> cur=new PriorityQueue<>();
            for(int j=0;j<len-i;j++){
                cur.add(grid[j][j+i]);
            }
            for(int j=0;j<len-i;j++){
                grid[j][j+i]=cur.poll();
            }
            cur=new PriorityQueue<>((a,b)->Integer.compare(b,a));
            for(int j=0;j<len-i;j++){
                cur.add(grid[j+i][j]);                
            }
            for(int j=0;j<len-i;j++){
                grid[j+i][j]=cur.poll();
            }
        }
        return grid;
        
    }
}


class Solution {
    public int[][] sortMatrix(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        
        for (int row = 0; row < rows; row++) {
            sortDiagonal(mat, row, 0, false); // false for non-increasing
        }

        for (int col = 1; col < cols; col++) {
            sortDiagonal(mat, 0, col, true); // true for non-decreasing
        }

        return mat;
    }

    private void sortDiagonal(int[][] mat, int row, int col, boolean increasing) {
        int rows = mat.length;
        int cols = mat[0].length;
        
        int len = Math.min(rows - row, cols - col);
        int[] diagonal = new int[len];

        for (int i = 0; i < len; i++) {
            diagonal[i] = mat[row + i][col + i];
        }

        Arrays.sort(diagonal);

        if (!increasing) {
            reverse(diagonal);
        }

        for (int i = 0; i < len; i++) {
            mat[row + i][col + i] = diagonal[i];
        }
    }
    private void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}