import org.junit.Test;
import static org.junit.Assert.*;

public class TestJumpGame {
    @Test
    public void testexample() {
        int[] nums = new int[] {1, 5, 2, 1, 0, 2, 0};
        jumpgame game = new jumpgame();
        game.canJump(nums);
    }
}
