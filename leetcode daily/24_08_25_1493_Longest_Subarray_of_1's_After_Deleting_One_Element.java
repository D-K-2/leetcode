// 1493. Longest Subarray of 1's After Deleting One Element
// Solved
// Medium
// Topics
// premium lock icon
// Companies
// Hint
// Given a binary array nums, you should delete one element from it.

// Return the size of the longest non-empty subarray containing only 1's in the resulting array. Return 0 if there is no such subarray.

 

// Example 1:

// Input: nums = [1,1,0,1]
// Output: 3
// Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
// Example 2:

// Input: nums = [0,1,1,1,0,1,1,0,1]
// Output: 5
// Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is [1,1,1,1,1].
// Example 3:

// Input: nums = [1,1,1]
// Output: 2
// Explanation: You must delete one element.
 

// Constraints:

// 1 <= nums.length <= 105
// nums[i] is either 0 or 1.


class Solution {
    public int longestSubarray(int[] nums) {
        int len=nums.length;
        int ret=0;
        int prev=-1;
        int s=-1;
        int i=0;
        int o=0;
        for(;i<len;i++){
            if(nums[i]==0){
                ret=Math.max(ret,i-s-2);
                s=prev;
                prev=i;
            }
            else o++;
        }
        if(nums[len-1]==1)ret=Math.max(ret,len-s-2);
        return ret;
    }
}