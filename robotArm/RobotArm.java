import java.util.Arrays;
import java.util.ArrayList;

public class RobotArm {
    public static void main(String[] args) {
        
        RobotArm robotArm = new RobotArm();

        BoardLocation[] board = { 
            new BoardLocation(1,1),
            new BoardLocation(1,3),
            new BoardLocation(2,2),
            new BoardLocation(3,1),
            new BoardLocation(3,2)
        };

        BoardLocation armStartLocation = new BoardLocation(2,1);
        
        System.out.println(robotArm.solderOptimally(board, armStartLocation, new ArrayList()));
    }

    public ArrayList solderOptimally(BoardLocation[] board, BoardLocation startLocation, ArrayList order) {

        int closestIndex = 0;
        int notSolderedCount = 0;
        BoardLocation boardLocation;
        BoardLocation closestBoardLocation = null;

        for (int i=0; i<board.length; i++) {

            boardLocation = board[i];
            closestBoardLocation = board[closestIndex];
            
            if (closestBoardLocation.isSoldered()) {
                closestIndex = i;
                closestBoardLocation = boardLocation;
            }

            if(!boardLocation.isSoldered()) {
                notSolderedCount++;
                if(boardLocation.distanceFrom(startLocation) <= closestBoardLocation.distanceFrom(startLocation)) {
                    closestIndex = i;
                    closestBoardLocation = boardLocation;
                }
            }
        }

        if(notSolderedCount == 0) {
            return order;
        }

        closestBoardLocation.solder();
        order.add(closestIndex);

        return solderOptimally(board, board[closestIndex], order);
    }

}

class BoardLocation {

    BoardLocation(double x, double y) {
        this.soldered = false;
        this.location = new double[2];
        this.location[0] = x;
        this.location[1] = y;
    }

    public void solder() {
        this.soldered = true;
    }

    public boolean isSoldered() {
        return this.soldered;
    }

    public double distanceFrom(BoardLocation otherLocation) {
        return this.distance(this.location, otherLocation.getLocation());
    }

    public double[] getLocation() {
        return this.location;
    }

    private double distance(double[] target, double[] currentLocation) {
        double distance = absoluteDistance(currentLocation[0] - target[0]) +
            absoluteDistance(currentLocation[1] - target[1]);
        return distance;
    }

    private double squared(double value) {
        return value * value;
    }

    private double absoluteDistance(double value) {
        return Math.sqrt(squared(value));
    }

    private double[] location;

    private boolean soldered;
}


