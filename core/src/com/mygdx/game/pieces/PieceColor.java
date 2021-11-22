package com.mygdx.game.pieces;


public enum PieceColor {
    WHITE {
        public String toString() {return  "white";}

        @Override
        public PieceColor not() {
            return BLACK;
        }
    },
    BLACK {
        public String toString() {return  "black";}

        @Override
        public PieceColor not() {
            return WHITE;
        }
    };
    public abstract String toString();
    public abstract PieceColor not();
}
