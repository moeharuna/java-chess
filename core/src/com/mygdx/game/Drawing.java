package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.pieces.Piece;

import java.util.Optional;

public class Drawing {
    final private Board board;
    final private SpriteBatch pieces_batch;
    final private OrthographicCamera camera;
    final private ShapeRenderer squares_renderer;

    public  Drawing(Board b)
    {
        this.board =b;
        camera = new OrthographicCamera();
        pieces_batch = new SpriteBatch();
        squares_renderer  = new ShapeRenderer();
        on_resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public int square_size_in_pixels()
    {
        final int number_of_squares = board.get_size();
        int cell_width = Gdx.graphics.getWidth() /   number_of_squares;
        int cell_height = Gdx.graphics.getHeight() / number_of_squares;


        return Math.min(cell_height, cell_width);
    }

    private  Color square_color_by_position(int x, int y)
    {
        return (y+x)%2==0? Color.RED : Color.WHITE;
    }

    public void on_resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    private  void draw_board()
    {
        int square_size = square_size_in_pixels();
        squares_renderer.begin(ShapeRenderer.ShapeType.Filled);
        squares_renderer.setProjectionMatrix(camera.combined);
        Optional<Piece> selected = board.get_selected();

        for(int y = 0; y<board.get_size(); ++y)
        {
            for(int x=0; x< board.get_size(); ++x)
            {
                Optional<Square> square = board.get_square(x, y);
                if(selected.isPresent() && square.isPresent() && selected.get().get_position()==square.get())
                {
                    squares_renderer.setColor(Color.YELLOW);
                }
                else if(selected.isPresent() && square.isPresent() && selected.get().moves(board).contains(square.get())) {
                    squares_renderer.setColor(Color.GREEN);
                }
                else squares_renderer.setColor( square_color_by_position(x, y));
                squares_renderer.rect(square_size*x, square_size*y, square_size, square_size);
            }
        }

        squares_renderer.end();
    }
    private  void draw_pieces()
    {
        int square_size = square_size_in_pixels();
        pieces_batch.begin();
        pieces_batch.setProjectionMatrix(camera.combined);

        for(int y = 0; y<board.get_size(); ++y)
        {
            for(int x=0; x < board.get_size(); ++x)
            {
                Optional<Piece> piece = board.get_piece(x, y);
                if(piece.isPresent())
                    pieces_batch.draw(piece.get().get_texture(), square_size*x, square_size*y, square_size, square_size);
            }
        }
        pieces_batch.end();
    }
    public void draw()
    {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        draw_board();
        draw_pieces();
    }

}
