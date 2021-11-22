package com.mygdx.game.pieces;

import com.mygdx.game.Square;
import com.mygdx.game.pieces.moves.MoveGenerator;
import com.mygdx.game.pieces.moves.PointMoveGenerator;
import com.mygdx.game.pieces.moves.RayMoveGenerator;

public enum PieceType {
    Pawn
            {
                @Override
                public String toString() {
                    return "pawn"; //FIXME: i dont like these hardcoded filenames i should either load textures at compile time or make some sort of config
                }
                @Override
                public MoveGenerator move_generator() {
                    return new PointMoveGenerator(new Square[]{new Square(0, 1)});
                }
            },
    Rook {
                @Override
                public String toString() {
                    return "rook";
                }
                @Override
                public MoveGenerator move_generator() {
                        return new RayMoveGenerator( new Square[]{
                                new Square(0, 1),
                                new Square(1, 0),
                                new Square(0, -1),
                                new Square(-1, 0)
                        });
                }
            },
    Knight {
                @Override
                public String toString() {
                    return "knight";
                }
                @Override
                public MoveGenerator move_generator() {

                        return new PointMoveGenerator(new Square[]{
                                new Square(1, 2),
                                new Square(1, -2),
                                new Square(-1, 2),
                                new Square(-1, -2),
                                new Square(2, 1),
                                new Square(2, -1),
                                new Square(-2, 1),
                                new Square(-2, -1)
                        });
                }
            },
    Bishop {
                @Override
                public String toString() {
                    return "bishop";
                }
                @Override
                public MoveGenerator move_generator() {
                    return new RayMoveGenerator(new Square[]{
                                new Square(1, 1),
                                new Square(1, -1),
                                new Square(-1, 1),
                                new Square(-1, -1)
                        });
                }
            },
    King {
                @Override
                public String toString() {
                    return "king";
                }
                @Override
                public MoveGenerator move_generator() {
                    return new PointMoveGenerator(new Square[] {
                                new Square(1, 1),
                                new Square(1, -1),
                                new Square(-1, 1),
                                new Square(-1, -1),
                                new Square(0, 1),
                                new Square(1, 0),
                                new Square(0, -1),
                                new Square(-1, 0)
                        });
                }
            },
    Queen {
                @Override
                public String toString() {
                    return "queen";
                }
                @Override
                public MoveGenerator move_generator() {
                    return new RayMoveGenerator( new Square[] {
                            new Square(1, 1),
                            new Square(1, -1),
                            new Square(-1, 1),
                            new Square(-1, -1),
                            new Square(0, 1),
                            new Square(1, 0),
                            new Square(0, -1),
                            new Square(-1, 0)
                    });
                }
            },;

    @Override
    public abstract String toString() ;
    public abstract MoveGenerator move_generator();
    public static PieceType from_fen_char(char ch)
    {
        switch (ch)
        {
            case 'p':
                return Pawn;
            case 'r':
                return Rook;
            case 'b':
                return Bishop;
            case 'n':
                return Knight;
            case 'k':
                return King;
            case 'q':
                return Queen;
        }
        assert false;
        return null;
    }
}
