// 3459. Length of Longest V-Shaped Diagonal Segment
// Hard
// Topics
// premium lock icon
// Companies
// Hint
// You are given a 2D integer matrix grid of size n x m, where each element is either 0, 1, or 2.

// A V-shaped diagonal segment is defined as:

// The segment starts with 1.
// The subsequent elements follow this infinite sequence: 2, 0, 2, 0, ....
// The segment:
// Starts along a diagonal direction (top-left to bottom-right, bottom-right to top-left, top-right to bottom-left, or bottom-left to top-right).
// Continues the sequence in the same diagonal direction.
// Makes at most one clockwise 90-degree turn to another diagonal direction while maintaining the sequence.


// Return the length of the longest V-shaped diagonal segment. If no valid segment exists, return 0.

 

// Example 1:

// Input: grid = [[2,2,1,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]

// Output: 5

// Explanation:



// The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: (0,2) → (1,3) → (2,4), takes a 90-degree clockwise turn at (2,4), and continues as (3,3) → (4,2).

// Example 2:

// Input: grid = [[2,2,2,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]

// Output: 4

// Explanation:



// The longest V-shaped diagonal segment has a length of 4 and follows these coordinates: (2,3) → (3,2), takes a 90-degree clockwise turn at (3,2), and continues as (2,1) → (1,0).

// Example 3:

// Input: grid = [[1,2,2,2,2],[2,2,2,2,0],[2,0,0,0,0],[0,0,2,2,2],[2,0,0,2,0]]

// Output: 5

// Explanation:



// The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: (0,0) → (1,1) → (2,2) → (3,3) → (4,4).

// Example 4:

// Input: grid = [[1]]

// Output: 1

// Explanation:

// The longest V-shaped diagonal segment has a length of 1 and follows these coordinates: (0,0).

 

// Constraints:

// n == grid.length
// m == grid[i].length
// 1 <= n, m <= 500
// grid[i][j] is either 0, 1 or 2.
 
class Solution {
    int[][] g;
    public int lenOfVDiagonal(int[][] grid) {
        // for(int[] i:grid){
        //     System.out.println(Arrays.toString(i));
        // }
        g=grid;
        int r=grid.length,c=grid[0].length;
        int[][][] memo=new int[r][c][5];
        for(int[][] i:memo){
            for(int[] j:i)Arrays.fill(j,-1);
        }
        int ret=0;
        int on=0;
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(grid[i][j]==1){
                    on++;
                    if(i-1>=0&&j-1>=0&&g[i-1][j-1]==2){
                        fun(memo,i-1,j-1,2,0,1);
                        ret=Math.max(ret,memo[i-1][j-1][4]+1);
                    }
                    if(i-1>=0&&j+1<c&&g[i-1][j+1]==2){
                        fun(memo,i-1,j+1,3,0,1);
                        ret=Math.max(ret,memo[i-1][j+1][4]+1);
                    }
                    if(i+1<r&&j+1<c&&g[i+1][j+1]==2){
                        fun(memo,i+1,j+1,0,0,1);
                        ret=Math.max(ret,1+memo[i+1][j+1][4]);
                        // System.out.println(" in hr "+ ret+" i "+i+" j "+j+" fin "+memo[i+1][j+1][4]);
                    }
                    if(i+1<r&&j-1>=0&&g[i+1][j-1]==2){
                        fun(memo,i+1,j-1,1,0,1);
                        ret=Math.max(ret,1+memo[i+1][j-1][4]);
                    }
                }
            }
        }
        // for(int[][] i:memo){
        //     System.out.println(Arrays.deepToString(i));
        // }
        if(on>0)return Math.max(1,ret);
        return ret;

    }
    int[][] mov={{-1,-1},{-1,1},{1,1},{1,-1}};


    public void fun(int[][][] m,int r,int c,int d,int p,int rem){
        // System.out.println(r+" r "+ c+" d "+d+" rem "+rem);
        // int pr=r+mov[d][0],pc=mov[d][1]+c;
        int ind=(d+2)%4;
        if((p^2)!=g[r][c]){
            // System.out.println(" num failed");
            m[r][c][d]=1;
            m[r][c][4]=1;
            return;
        }
        if(rem==0){
            // System.out.println(" in 0 rem ");
            if(m[r][c][ind]!=-1){
                // System.out.println(m[r][c][ind]+" returning ");
                return;
            }
            else {
                int nr=r+mov[ind][0];
                int nc=c+mov[ind][1];
                if(nr>=0&&nr<m.length&&nc>=0&&nc<m[0].length){
                    // System.out.println("moving to 0 rem edge"+ nr+" c "+nc);
                    if((g[nr][nc]^2)==g[r][c]){
                        fun(m,nr,nc,d,p^2,rem);
                        // System.out.println("new add "+m[nr][nc][ind]+" r "+ r +" c "+c);
                        m[r][c][ind]=1+m[nr][nc][ind];
                    }
                    else {
                        m[r][c][ind]=1;
                        m[nr][nc][d]=1;
                    }
                }
                else {
                    m[r][c][ind]=1;
                }
            }
        }

        else {
            // System.out.println(" moving to all edge "+ind);
            int max=1;
            int nr=r+mov[ind][0];
            int nc=c+mov[ind][1];
            if(nr>=0&&nr<m.length&&nc>=0&&nc<m[0].length){
                if((g[r][c]^2)!=g[nr][nc]){
                    max=1;
                    m[nr][nc][d]=1;
                    m[r][c][ind]=1;
                }
                else {
                    fun(m,nr,nc,d,g[r][c],rem);
                    // System.out.println(" entering val "+r+" c "+ c +" ind "+ind +" val "+(1+m[nr][nc][ind]));
                    m[r][c][ind]=1+m[nr][nc][ind];
                    max=Math.max(max,m[nr][nc][4]+1);
                }
            }
            else {
                m[r][c][ind]=1;
            }
            int i=(ind+1)%4;
            nr=mov[i][0]+r;
            nc=mov[i][1]+c;
            if(nr>=0&&nr<m.length&&nc>=0&&nc<m[0].length){
                if((g[r][c]^2)!=g[nr][nc]){
                    max=Math.max(1,max);
                    m[nr][nc][(i+2)%4]=1;
                    m[r][c][i]=1;
                }
                else {
                    fun(m,nr,nc,(i+2)%4,g[r][c],0);
                    m[r][c][i]=1+m[nr][nc][i];
                    max=Math.max(max,m[nr][nc][i]+1);
                }
            }
            else{
                m[r][c][i]=1;
            }
            // System.out.println(max+" max at"+r+" c "+c);
            m[r][c][4]=max;
        }
    }
}



/**
Time Complexity: O(mn)
Space Complexity: O(mn)
 */
class Solution {
    private static final int[][] DIRS = { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };

    public int lenOfVDiagonal(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // Too many dimensions affect efficiency, so compress k and canTurn into one int
        int[][][] memo = new int[m][n][1 << 3];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 1) {
                    continue;
                }
                int[] maxs = { m - i, j + 1, i + 1, n - j }; //Theoretical maximum (go to the boundary)
                for (int k = 0; k < 4; k++) { // Enumerate starting direction
                    // Optimization 1: If the theoretical maximum does not exceed ans, skip recursion
                    if (maxs[k] > ans) { 
                        ans = Math.max(ans, dfs(i, j, k, 1, 2, grid, memo) + 1);
                    }
                }
            }
        }
        return ans;
    }

    private int dfs(int i, int j, int k, int canTurn, int target, int[][] grid, int[][][] memo) {
        i += DIRS[k][0];
        j += DIRS[k][1];
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != target) {
            return 0;
        }
        int mask = k << 1 | canTurn;
        if (memo[i][j][mask] > 0) {
            return memo[i][j][mask];
        }
        int res = dfs(i, j, k, canTurn, 2 - target, grid, memo);
        if (canTurn == 1) {
            int[] maxs = { grid.length - i - 1, j, i, grid[i].length - j - 1 }; // Theoretical maximum (go to the boundary)
            k = (k + 1) % 4;
            // Optimization 2: If the theoretical maximum does not exceed res, skip recursion
            if (maxs[k] > res) {
                res = Math.max(res, dfs(i, j, k, 0, 2 - target, grid, memo));
            }
        }
        return memo[i][j][mask] = res + 1;
    }
}




class Solution {
    int dirs[][] = {{-1,1},{1,1},{1,-1},{-1,-1}};
    int [][] grid;
    int row, col;   

    public int lenOfVDiagonal(int[][] grid) {
        row = grid.length;
        col = grid[0].length;
        this.grid = grid;
        int res = 0;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(grid[i][j] == 1) {
                    res = Math.max(res, 1);
                    for(int d = 0; d < 4; d++) {
                        //row , col , direction , target , isTurned
                        res = Math.max(res, dfs(i, j, d, 2,false));
                    }
                }
            }
        }
        return res;
    }

    private int dfs(int i, int j , int dir ,int target, boolean isTurned){
        int x = i + dirs[dir][0];//next position
        int y = j + dirs[dir][1];//next position

        //  x and y in boundarys and that posion is target
        if(x < 0 || x >= row || y < 0 || y >= col || grid[x][y] != target) return 1;

        int straight = 1 + dfs(x, y, dir, target == 2 ? 0: 2, isTurned);//continue in the same direction

        int turn = 0; //length after turn
        if(!isTurned) { //can turn only once
            // dir + 1 --> 90deg turn
            turn = 1 + dfs(x, y, (dir + 1) % 4, target == 2 ? 0: 2, true);
        }
        return Math.max(straight, turn);
    }
}