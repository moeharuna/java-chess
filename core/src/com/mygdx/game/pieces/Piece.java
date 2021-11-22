package com.mygdx.game.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Board;
import com.mygdx.game.Square;
import com.mygdx.game.pieces.moves.MoveGenerator;
import com.mygdx.game.pieces.moves.RayMoveGenerator;

import java.util.ArrayList;


public class Piece { //
    private PieceColor color;
    private MoveGenerator moves;
    private Square position;
    private Square start_positions;
    private  Texture texture;
    private PieceType type;


    public  Piece(Texture t)
    {
        this.texture = t;
    }
    public  Piece(Texture t, PieceColor c)
    {
        this.texture = t;
        this.color = c;
    }
    private  static Texture load_texture(PieceType type, PieceColor color)
    {
        final String file_extension = ".png";
        String filename =   color.toString()+"_"+type.toString()+file_extension;
        return new Texture(Gdx.files.internal(filename));
    }
    public  Piece(PieceType type, PieceColor color, Square position)
    {
        this.texture = load_texture(type, color);
        this.color = color;
        this.moves = type.move_generator();
        this.position = position;
        this.start_positions = position;
        this.type = type;
    }


    public static Piece piece_from_fen_char(char ch, Square position) {
        PieceColor color = Character.isUpperCase(ch)? PieceColor.BLACK : PieceColor.WHITE;
        ch = Character.toLowerCase(ch);
        return new Piece(PieceType.from_fen_char(ch), color, position);
    }

    public Texture get_texture() {
        return  texture;
    }


    public Square get_position() {
        return position;
    }

    public PieceColor get_color() {return  color;}

    public ArrayList<Square> moves(Board board)
    {
        return moves.generate(board, position);
    }

    public void set_position(Square s) {
        position = s;
    }

    public PieceType getType() {
        return type;
    }

    public Square get_start_positions() {
        return start_positions;
    }
}