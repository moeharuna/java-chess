package com.mygdx.game;

import com.mygdx.game.pieces.Piece;
import com.mygdx.game.pieces.PieceColor;

import java.util.NoSuchElementException;
import java.util.Optional;


import static java.lang.System.exit;

public class Board {
    private Piece[][] pieces;
    private Square[][] squares; //I use this field only to reference existing squares instead of constructing new ones, it is possible to not use it at all
    private Piece selected; //Pointer to selected piece
    private PieceColor currentPlayer;
    static final String default_fen_string = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    static final int number_of_squares = 8;

    private int turn_counter=0;

    public Board()
    {
        this(default_fen_string);
    }




    private Piece[][] parse_fen_positions(String string) {


        Piece[][] result = new Piece[number_of_squares][number_of_squares];

        try {

            String[] lines = string.split("/");

            if (lines.length != number_of_squares) throw new Exception("Number of rows in fen_string to low!");

            for (int y = 0; y < number_of_squares; ++y) {

                char[] chars = lines[y].toCharArray();
                //if (chars.length != number_of_squares) throw  new Exception("Number of columns in fen_string to low!");

                for (int x=0; x<number_of_squares; ++x) {
                    if (Character.isDigit(chars[x]))            x+= chars[x]-'0';
                    else if (Character.isAlphabetic(chars[x]))  {


                        Optional<Square> square = get_square(x, y);
                        assert square.isPresent();
                        result[y][x] = Piece.piece_from_fen_char(chars[x], square.get());
                    }
                    else                                        throw new Exception(String.format("This fen character: %c does not exist!", chars[x]));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exit(1);
        }
        return  result;

    }

    private PieceColor parse_first_to_move(String s)
    {
        if(s.equals("w")) return  PieceColor.WHITE;
        else return PieceColor.BLACK;
    }

    public Board(String fen_string)
    {
        this.squares = new Square[number_of_squares][number_of_squares];
        for(int y=0; y< squares.length; ++y)
        {
            for(int x=0; x<squares[0].length; ++x)
            {
                squares[y][x] = new Square(x, y);
            }
        }
        String[] splited_fen = fen_string.split(" ");
        this.pieces =  parse_fen_positions(splited_fen[0]);
        this.currentPlayer  = parse_first_to_move(splited_fen[1]);

        //TODO: finish fen parsing
        //String castling = splited_fen[2];
        //String en_passunt = splited_fen[3];
        //String half_move_clock = splited_fen[4];
        //String full_move_clock = splited_fen[5];
    }

    /**
     * @return number of  squares in the row
     */
    public int get_size()
    {
        return number_of_squares;
    }


    public Optional<Square> get_square(int x, int y)
    {
        if(x<0 || y<0 || x>number_of_squares-1 || y>number_of_squares-1) return  Optional.empty();
        return  Optional.of(squares[y][x]);
    }
    public Optional<Piece> get_piece(Square s)
    {
        return get_piece(s.x, s.y);
    }
    public  Optional<Piece> get_piece(int x, int y)
    {
        if(x<0 || y<0 || x>number_of_squares-1 || y>number_of_squares-1 || pieces[y][x]==null) return  Optional.empty();
        return  Optional.of(pieces[y][x]);
    }
    public Optional<Piece> get_selected() {
        if(selected==null)
            return Optional.empty();
        return Optional.of(selected);
    }
    public int get_turn_count() { return  turn_counter;}

    public void set_selected(Piece selected) {
        if(selected==null) return;
        if(! get_piece(selected.get_position()).isPresent()) throw new NoSuchElementException(); //if trying to select piece that not on the board
        this.selected = selected;

    }

    public void square_is_clicked(Square clicked_square)
    {
        if(selected!=null) {
            make_move(selected, clicked_square);
        } else {
            Optional<Piece> piece_on_clicked = get_piece(clicked_square);
            if(piece_on_clicked.isPresent() && piece_on_clicked.get().get_color()==currentPlayer)
                set_selected(piece_on_clicked.get());
        }
    }

    public void make_move(Piece p, Square where)
    {
        Square old = p.get_position();
        if(!p.moves(this).contains(where))
        {
            selected = null;
            return;
        }
        this.pieces[where.y][where.x] = p;
        this.pieces[old.y][old.x] = null;
        p.set_position(where);
        turn_counter++;
        selected = null;
        this.currentPlayer = currentPlayer.not();
    }

}
