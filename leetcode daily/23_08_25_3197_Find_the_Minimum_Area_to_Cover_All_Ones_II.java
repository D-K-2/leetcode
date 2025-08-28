// 3197. Find the Minimum Area to Cover All Ones II
// Solved
// Hard
// Topics
// premium lock icon
// Companies
// Hint
// You are given a 2D binary array grid. You need to find 3 non-overlapping rectangles having non-zero areas with horizontal and vertical sides such that all the 1's in grid lie inside these rectangles.

// Return the minimum possible sum of the area of these rectangles.

// Note that the rectangles are allowed to touch.

 

// Example 1:

// Input: grid = [[1,0,1],[1,1,1]]

// Output: 5

// Explanation:



// The 1's at (0, 0) and (1, 0) are covered by a rectangle of area 2.
// The 1's at (0, 2) and (1, 2) are covered by a rectangle of area 2.
// The 1 at (1, 1) is covered by a rectangle of area 1.
// Example 2:

// Input: grid = [[1,0,1,0],[0,1,0,1]]

// Output: 5

// Explanation:



// The 1's at (0, 0) and (0, 2) are covered by a rectangle of area 3.
// The 1 at (1, 1) is covered by a rectangle of area 1.
// The 1 at (1, 3) is covered by a rectangle of area 1.
 

// Constraints:

// 1 <= grid.length, grid[i].length <= 30
// grid[i][j] is either 0 or 1.
// The input is generated such that there are at least three 1's in grid.


// Solution number 1

class Solution {
    public int minimumSum(int[][] grid) {
        int r=grid.length;
        int c=grid[0].length;
        int min=fun(grid,0,c-1,0,r-1,3);
        // min=Math.min(min,vfun())
        return min;
    }
    public int fun(int[][] g,int l,int r,int t,int b,int rem){
        int min=Integer.MAX_VALUE;
        // if(rem==0)
        for(int i=l;i<r;i++){
            int cur=0;
            if(rem==3){
                cur=Math.min(fun(g,l,i,t,b,2)+area(g,t,b,i+1,r),area(g,t,b,l,i)+fun(g,i+1,r,t,b,2));
                min=Math.min(min,cur);
            }
            else{
                cur=area(g,t,b,l,i)+area(g,t,b,i+1,r);
                min=Math.min(min,cur);
            }
        }
        for(int i=t;i<b;i++){
            int cur=0;
            if(rem==3){
                cur=Math.min(fun(g,l,r,t,i,2)+area(g,i+1,b,l,r),area(g,t,i,l,r)+fun(g,l,r,i+1,b,2));
                min=Math.min(min,cur);
            }
            else {
                cur=area(g,t,i,l,r)+area(g,i+1,b,l,r);
                min=Math.min(min,cur);
            }
            
        }
        return min;
    }
    public int area(int[][] mat,int t,int b,int l,int r){
        int top=Integer.MAX_VALUE;
        int bot=Integer.MIN_VALUE;
        int ri=Integer.MIN_VALUE;
        int le=Integer.MAX_VALUE;
        for(int i=t;i<=b;i++){
            for(int j=l;j<=r;j++){
                if(mat[i][j]==1){
                    top=Math.min(top,i);
                    bot=i;
                    ri=Math.max(ri,j);
                    le=Math.min(le,j);
                }
            }
        }
        return (bot-top+1)*(ri-le+1);
    }
}


// solution 2 uses prefix sum and binary search 

public class Solution {
    private int[][] pref;

    private void computePrefixSum(int[][] grid){
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                pref[i+1][j+1] = grid[i][j] + pref[i+1][j] + pref[i][j+1] - pref[i][j];
            }
        }
    }

    private int getBoxSum(int x1, int x2, int y1, int y2){
        if (x1 > x2 || y1 > y2) return 0;
        return pref[x2+1][y2+1] - pref[x1][y2+1] - pref[x2+1][y1] + pref[x1][y1];
    }

    private void findBoundingCoordinates(int[][] grid, int[] bounds){
        int m = grid.length, n = grid[0].length;
        int low_x = Integer.MAX_VALUE, high_x = -1;
        int low_y = Integer.MAX_VALUE, high_y = -1;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    low_x = Math.min(low_x, i);
                    high_x = Math.max(high_x, i);
                    low_y = Math.min(low_y, j);
                    high_y = Math.max(high_y, j);
                }
            }
        }
        bounds[0] = low_x; bounds[1] = high_x; bounds[2] = low_y; bounds[3] = high_y;
    }

    private int findMinArea(int x1, int x2, int y1, int y2, int[][] grid){
        if (x1 > x2 || y1 > y2) return 0;
        int total = getBoxSum(x1, x2, y1, y2);
        if (total == 0) return 0;

        // min_x: first row in [x1..x2] that contains a 1 in cols [y1..y2]
        int min_x = x2;
        int lo = x1, hi = x2;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (getBoxSum(x1, mid, y1, y2) > 0) {
                min_x = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }

        // max_x: last row in [x1..x2] that contains a 1 in cols [y1..y2]
        int max_x = x1;
        lo = x1; hi = x2;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (getBoxSum(mid, x2, y1, y2) > 0) {
                max_x = mid;
                lo = mid + 1;
            } else hi = mid - 1;
        }

        // min_y: first col in [y1..y2] that contains a 1 in rows [x1..x2]
        int min_y = y2;
        lo = y1; hi = y2;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (getBoxSum(x1, x2, y1, mid) > 0) {
                min_y = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }

        // max_y: last col in [y1..y2] that contains a 1 in rows [x1..x2]
        int max_y = y1;
        lo = y1; hi = y2;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (getBoxSum(x1, x2, mid, y2) > 0) {
                max_y = mid;
                lo = mid + 1;
            } else hi = mid - 1;
        }

        return (max_x - min_x + 1) * (max_y - min_y + 1);
    }

    public int minimumSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        pref = new int[m + 1][n + 1];
        computePrefixSum(grid);

        int[] bounds = new int[4];
        findBoundingCoordinates(grid, bounds);
        int low_x = bounds[0], high_x = bounds[1], low_y = bounds[2], high_y = bounds[3];

        // if no 1s
        if (high_x == -1) return 0;

        int lowest_area = Integer.MAX_VALUE;

        // Case 1..4
        for (int i = low_x; i < high_x; ++i) {
            for (int j = low_y; j < high_y; ++j) {
                for (int count = 1; count <= 4; ++count) {
                    int area = 0;
                    switch (count) {
                        case 1: // Up
                            area = findMinArea(low_x, i, low_y, j, grid)
                                 + findMinArea(low_x, i, j+1, high_y, grid)
                                 + findMinArea(i+1, high_x, low_y, high_y, grid);
                            break;
                        case 2: // Right
                            area = findMinArea(low_x, high_x, low_y, j, grid)
                                 + findMinArea(low_x, i, j+1, high_y, grid)
                                 + findMinArea(i+1, high_x, j+1, high_y, grid);
                            break;
                        case 3: // Down
                            area = findMinArea(low_x, i, low_y, high_y, grid)
                                 + findMinArea(i+1, high_x, low_y, j, grid)
                                 + findMinArea(i+1, high_x, j+1, high_y, grid);
                            break;
                        case 4: // Left
                            area = findMinArea(low_x, i, low_y, j, grid)
                                 + findMinArea(i+1, high_x, low_y, j, grid)
                                 + findMinArea(low_x, high_x, j+1, high_y, grid);
                            break;
                    }
                    lowest_area = Math.min(lowest_area, area);
                }
            }
        }

        // Case-5: Horizontal slicing
        for (int i = low_x; i < high_x - 1; ++i) {
            for (int j = i + 1; j < high_x; ++j) {
                int area = findMinArea(low_x, i, low_y, high_y, grid)
                         + findMinArea(i+1, j, low_y, high_y, grid)
                         + findMinArea(j+1, high_x, low_y, high_y, grid);
                lowest_area = Math.min(lowest_area, area);
            }
        }

        // Case-6: Vertical slicing
        for (int i = low_y; i < high_y - 1; ++i) {
            for (int j = i + 1; j < high_y; ++j) {
                int area = findMinArea(low_x, high_x, low_y, i, grid)
                         + findMinArea(low_x, high_x, i+1, j, grid)
                         + findMinArea(low_x, high_x, j+1, high_y, grid);
                lowest_area = Math.min(lowest_area, area);
            }
        }

        return (lowest_area == Integer.MAX_VALUE) ? 0 : lowest_area;
    }
}