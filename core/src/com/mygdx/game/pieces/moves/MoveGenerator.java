package com.mygdx.game.pieces.moves;

import com.mygdx.game.Board;
import com.mygdx.game.Square;

import java.util.ArrayList;


//Move generator contains all moves of concrete piece type as if piece had positioned 0,0(A1) without any checks.
// generate(...) returns these moves on real board with collision and bounds checks.
public interface MoveGenerator {
    ArrayList<Square> generate(Board board, Square piece_position);

}
