// 498. Diagonal Traverse
// Solved
// Medium
// Topics
// Companies
// Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.

 

// Example 1:


// Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
// Output: [1,2,4,7,5,3,6,8,9]
// Example 2:

// Input: mat = [[1,2],[3,4]]
// Output: [1,2,3,4]
 

// Constraints:

// m == mat.length
// n == mat[i].length
// 1 <= m, n <= 104
// 1 <= m * n <= 104
// -105 <= mat[i][j] <= 105

class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        int r=mat.length;
        int c=mat[0].length;
        int cc=0;
        int[] ret=new int[r*c];
        int cur=0;
        while(cc<r+c -1){
            int nr=cc;
            int nc=0;
            if(cc>=r){
                nr=r-1;
                nc=cc-r+1;
            }
            while(nr>=0&&nc<c){
                // System.out.println(nr+" "+nc+" "+cur);
                ret[cur++]=mat[nr--][nc++];
            }
            cc++;
            if(cc==r+c-1)break;
            nc=cc;
            nr=0;
            if(cc>=c){
                nc=c-1;
                nr=cc-c+1;
            }
            while(nc>=0&&nr<r){
                ret[cur++]=mat[nr++][nc--];
            }
            cc++;
        }
        return ret;
    }
}