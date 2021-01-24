import object.Direction;

public class Test1 {
    public static void main(String[] args) {
        //object.Direction direction;

        for(Direction direction:Direction.values()) {
            System.out.println(direction.ordinal());
        }
    }
}
