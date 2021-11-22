package com.mygdx.game.pieces.moves;

import com.mygdx.game.Board;
import com.mygdx.game.Square;
import com.mygdx.game.pieces.Piece;
import com.mygdx.game.pieces.PieceColor;

import java.util.ArrayList;
import java.util.Optional;


public class RayMoveGenerator implements  MoveGenerator{
    private  Square[] directions;
    private  ArrayList<Square> result_cache;
    private  int turn_counter;

    public RayMoveGenerator(Square[] move_directions)
    {
        this.directions = move_directions;
    }


    private boolean is_cache_valid(Board board)
    {
        return result_cache!=null && turn_counter== board.get_turn_count();
    }

    @Override
    public ArrayList<Square> generate(Board board, Square piece_position) {
        if(is_cache_valid(board)) return  result_cache;

        ArrayList<Square> moves = new ArrayList<>();

        Optional<Piece> my_piece = board.get_piece(piece_position);

        assert my_piece.isPresent();

        for(Square direction: directions)
        {
            Optional<Square> next_square = direction.relative2absolute(piece_position, board, PieceColor.WHITE);

            while(next_square.isPresent() )
            {
                Optional<Piece> piece_on_the_ray = board.get_piece(next_square.get());
                if(piece_on_the_ray.isPresent()) {
                    if (piece_on_the_ray.get().get_color() != my_piece.get().get_color())
                        moves.add(next_square.get());
                    break;
                }
                moves.add(next_square.get());
                next_square = next_square.get().add(direction, board);
            }
        }


        turn_counter = board.get_turn_count();
        result_cache = moves;
        return moves;
    }

    /*
    private ArrayList<Square> full_ray(Board board, Square piece_position)
    {
        ArrayList<Square> moves = new ArrayList<>();


        for(Square direction: directions)
        {
            Optional<Square> next_square = direction.relative2absolute(piece_position, board, PieceColor.WHITE);

            while(next_square.isPresent() )
            {
                moves.add(next_square.get());
                next_square = next_square.get().add(direction, board);
            }
        }
        return moves
    }

    private ArrayList<Square> first_piece_ray(Board b, Square start_position)
    {
        
    }
    private ArrayList<Piece> pins(Board b, Square start_position)
    {

    }
     */
}
