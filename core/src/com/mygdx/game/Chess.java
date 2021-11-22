package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.pieces.Piece;

import java.util.Optional;


public class Chess extends ApplicationAdapter {

	Board board;
	Drawing drawing;


	private Optional<Square> clicked_square(int clickX, int clickY)
	{
		int size = drawing.square_size_in_pixels();


		int height = Gdx.graphics.getHeight();

		int boardX = (int) Math.floor((float) clickX / size);
		int boardY =  (int) Math.floor((float)(height - clickY) / size);
		System.out.println(boardY);
		return board.get_square(boardX, boardY);

	}

	private void on_click(int clickX, int clickY)
	{

		Optional<Square> clicked_square = clicked_square(clickX, clickY);
		if(!clicked_square.isPresent()) return;
		board.square_is_clicked(clicked_square.get());
	}

	@Override
	public void create () {
		board = new Board();
		drawing = new Drawing(board);

	}

	@Override
	public void resize(int width, int height) {
		drawing.on_resize(width, height);
	}


	@Override
	public void render () {
		drawing.draw();
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			on_click(Gdx.input.getX(), Gdx.input.getY());

		}
	}
	
	@Override
	public void dispose () {

	}
}
