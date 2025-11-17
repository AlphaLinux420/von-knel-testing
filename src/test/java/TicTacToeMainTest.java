import ch.bbw.m450.tictactoe.TicTacToeMain;
import ch.bbw.m450.tictactoe.TicTacToePlayer;
import ch.bbw.m450.tictactoe.players.GreedyPlayer;
import org.junit.Test;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TicTacToeMainTest {


    static Stream<Object[]> winBoards() {
        return Stream.of(
                new Object[] {
                        new TicTacToePlayer.Stone[] {TicTacToePlayer.Stone.CROSS, TicTacToePlayer.Stone.CROSS, TicTacToePlayer.Stone.CROSS,
                                null, null, null, null, null, null}, TicTacToePlayer.Stone.CROSS, true},
                new Object[] {
                        new TicTacToePlayer.Stone[] {null, null, null,
                                TicTacToePlayer.Stone.CIRCLE, TicTacToePlayer.Stone.CIRCLE, TicTacToePlayer.Stone.CIRCLE,
                                null, null, null}, TicTacToePlayer.Stone.CIRCLE, true},
                new Object[] {
                        new TicTacToePlayer.Stone[] {TicTacToePlayer.Stone.CROSS, null, null,
                                null, TicTacToePlayer.Stone.CROSS, null,
                                null, null, TicTacToePlayer.Stone.CROSS}, TicTacToePlayer.Stone.CROSS, true},
                new Object[] {
                        new TicTacToePlayer.Stone[] {TicTacToePlayer.Stone.CROSS, TicTacToePlayer.Stone.CIRCLE, TicTacToePlayer.Stone.CROSS,
                                TicTacToePlayer.Stone.CIRCLE, TicTacToePlayer.Stone.CIRCLE, TicTacToePlayer.Stone.CROSS,
                                TicTacToePlayer.Stone.CROSS, TicTacToePlayer.Stone.CROSS, TicTacToePlayer.Stone.CIRCLE}, TicTacToePlayer.Stone.CROSS, false}
        );
    }


    @ParameterizedTest(name = "isWin scenario {index}")
    @MethodSource("winBoards")
    void testIsWin(TicTacToePlayer.Stone[] board, TicTacToePlayer.Stone color, boolean expected) {
        assertThat(TicTacToeMain.isWin(board, color)).isEqualTo(expected);
    }


    @Test
    public void toString_showsIndexesForEmptyBoard() {
        var empty = new TicTacToePlayer.Stone[9];
        var out = TicTacToeMain.toString(empty);
        assertThat(out).contains("0").contains("4").contains("8");
    }


    @Test
    public void play_throws_when_same_player_given() {
        var p = new GreedyPlayer();
        assertThatThrownBy(() -> TicTacToeMain.play(p, p)).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void greedy_vs_greedy_runs_without_error() {
        var x = new GreedyPlayer();
        var o = new GreedyPlayer();
        var winner = TicTacToeMain.play(x, o);
        assertThat(winner == null || winner == TicTacToePlayer.Stone.CROSS || winner == TicTacToePlayer.Stone.CIRCLE).isTrue();
    }


    @Test
    public void invalid_move_from_player_throws() {
        var bad = new TicTacToePlayer() {
            @Override
            public int play(Stone[] board, Stone colorToPlay) {
                return 99;
            }
        };
        var p = new GreedyPlayer();
        assertThatThrownBy(() -> TicTacToeMain.play(bad, p)).isInstanceOf(IllegalStateException.class);
    }
}