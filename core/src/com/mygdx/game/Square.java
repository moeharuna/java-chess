package com.mygdx.game;

import com.mygdx.game.pieces.PieceColor;

import java.util.Optional;

public class Square {
    public int x;
    public int y;
    public Square(char x, int y) //Init square using chess notation
    {
        x = Character.toLowerCase(x);
        x -= 'a';
        this.x = x;
        this.y = y;
    }
    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Optional<Square> relative2absolute(Square piece_position, Board board, PieceColor color)
    {
        return color==PieceColor.WHITE ? add(piece_position, board) : add_black(piece_position, board);
    }



    public Optional<Square> add(Square s2, Board board)
    {
        return board.get_square(x+s2.x, y+s2.y);
    }
    private  Optional<Square> add_black(Square s2, Board board)
    {
        return board.get_square(x+s2.x, s2.y-y);
    }
}
