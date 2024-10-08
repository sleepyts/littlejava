public class Solution {
    class neighborSum {
        private int[][] grid;

        public neighborSum(int[][] grid) {
            this.grid=grid;
        }

        public int adjacentSum(int value) {
            int sum=0,pi=-1,pj=-1;
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0].length;j++){
                    if (grid[i][j]==value){
                        pi=i;
                        pj=j;
                        break;
                    }
                }
            }
            sum+=pi>0?grid[pi-1][pj]:0;
            sum+=pi<grid.length-1?grid[pi+1][pj]:0;
            sum+=pj>0?grid[pi][pj-1]:0;
            sum+=pj<grid[0].length-1?grid[pi][pj+1]:0;
            return sum;
        }

        public int diagonalSum(int value) {
            int sum=0,pi=-1,pj=-1;
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0].length;j++){
                    if (grid[i][j]==value){
                        pi=i;
                        pj=j;
                        break;
                    }
                }
            }
            sum+=pi>0&&pj>0?grid[pi-1][pj-1]:0;
            sum+=pi>0&&pj<grid[0].length-1?grid[pi-1][pj+1]:0;
            sum+=pi<grid.length-1&&pj>0?grid[pi+1][pj-1]:0;
            sum+=pi<grid.length-1&&pj<grid[0].length-1?grid[pi+1][pj+1]:0;
            return sum;
        }
    }
}
