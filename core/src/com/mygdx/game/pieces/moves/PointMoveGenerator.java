package com.mygdx.game.pieces.moves;

import com.mygdx.game.Board;
import com.mygdx.game.Square;
import com.mygdx.game.pieces.Piece;
import com.mygdx.game.pieces.PieceType;

import java.util.ArrayList;
import java.util.Optional;


public class PointMoveGenerator implements MoveGenerator{
    private final  Square[] default_moves;
    private  ArrayList<Square> result_cache;
    private  int turn_counter;
    public PointMoveGenerator(Square[] default_moves)
    {
        this.default_moves = default_moves;
    }

    private void add_move_if_allowed(Board board, Piece piece, Square move, ArrayList<Square> list)
    {
        Optional<Square> final_square = move.relative2absolute(piece.get_position(), board, piece.get_color());
        if(!final_square.isPresent() || !is_move_here_allowed(board, final_square.get(), piece))
            return; //square does not exist on board. Fixme: Add exception
        list.add(final_square.get());
    }

    private boolean is_move_here_allowed(Board board, Square square, Piece piece)
    {
        Optional<Piece> piece_on_square = board.get_piece(square);
        return !piece_on_square.isPresent() || piece_on_square.get().get_color()!=piece.get_color();
    }
    private boolean is_cache_valid(Board board)
    {
        return result_cache!=null && turn_counter== board.get_turn_count();
    }
    @Override
    public ArrayList<Square> generate(Board board, Square piece_position) {
        if(is_cache_valid(board)) return  result_cache;
        Optional<Piece> my_piece = board.get_piece(piece_position);
        assert(my_piece.isPresent());
        ArrayList<Square> result = generate_generic(board, my_piece.get());
        result.addAll(special_cases(board, my_piece.get()));
        return  result;
    }

    private ArrayList<Square> generate_generic(Board board, Piece piece)
    {
        ArrayList<Square> moves = new ArrayList<>();
        for (Square default_move: default_moves) {
            add_move_if_allowed(board, piece, default_move, moves);
        }
        result_cache = moves;
        turn_counter = board.get_turn_count();
        return moves;
    }



    private ArrayList<Square> special_cases(Board board, Piece pawn)
    {
        return pawn_double_square_move(board, pawn);
    }

    private  ArrayList<Square> pawn_double_square_move(Board board, Piece pawn)
    {
        ArrayList<Square> result = new ArrayList<>();
        if(pawn.getType()!= PieceType.Pawn || pawn.get_position()!= pawn.get_start_positions()) return result;
        add_move_if_allowed(board, pawn, new Square(0, 2), result);
        return result;
    }


}
