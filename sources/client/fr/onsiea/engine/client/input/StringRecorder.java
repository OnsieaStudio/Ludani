package fr.onsiea.engine.client.input;

import org.joml.Vector2i;

import fr.onsiea.engine.utils.Direction;

public class StringRecorder
{
	private StringBuilder	content;
	private int				position;
	private boolean			insertMode;
	private Vector2i		selection;
	private boolean			isSelected;
	private Direction		selectionDirection;

	public StringRecorder()
	{
		this.content(new StringBuilder());
		this.selection(new Vector2i());
	}

	public void update()
	{

	}

	public String record(int codepointIn)
	{
		return this.record(Character.toString(codepointIn));
	}

	public String record(String strIn)
	{
		if (this.isSelected())
		{
			this.replaceSelection(strIn);
		}
		else if (this.position() >= this.content().length())
		{
			this.content().append(strIn).toString();

			this.increasePosition(strIn.length());
		}
		else if (this.isCorrect(this.position()))
		{
			if (this.insertMode())
			{
				this.content().replace(this.position(), this.position() + 1, strIn);
			}
			else
			{
				this.content().insert(this.position(), strIn);
			}
			this.increasePosition(strIn.length());
		}

		return this.content().toString();
	}

	public void reset()
	{
		this.content(new StringBuilder());
		this.position(0);
		this.insertMode(false);
		this.cancelSelection();
		this.selectionDirection(Direction.UP);
	}

	public String selectionStr()
	{
		if (this.isSelected() && this.isCorrect(this.selection().x) && this.isCorrect(this.selection().y()))
		{
			return this.content().substring(this.selection().x(), this.selection().y());
		}

		return null;
	}

	private void deleteSelection()
	{
		this.content().delete(this.selection().x(), this.selection().y());

		final var min = Math.min(this.selection().x(), this.content().length());
		this.position(min);

		this.cancelSelection();
	}

	private void replaceSelection(String strIn)
	{
		this.content().replace(this.selection().x(), this.selection().y(), strIn);

		final var min = Math.min(this.selection().x() + strIn.length(), this.content().length());
		this.position(min);

		this.cancelSelection();
	}

	private void cancelSelection()
	{
		this.selection().x	= 0;
		this.selection().y	= 0;
		this.selected(false);
	}

	private void startSelection()
	{
		this.selection().x	= this.position();
		this.selection().y	= this.position();
		this.selected(true);
	}

	public void extendSelectionInRight()
	{
		if (!this.isSelected())
		{
			return;
		}

		if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			if (this.selection().x() + 1 <= this.content().length())
			{
				this.selection().x += 1;
			}
		}
		else if (this.selection().y() + 1 <= this.content().length())
		{
			this.selection().y += 1;
		}
		if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			this.position(this.selection().y());
		}
		else
		{
			this.position(this.selection().x());
		}

		if (this.selection().x() == this.selection().y())
		{
			this.position(this.selection().x());
			this.selected(false);
		}
	}

	public void extendSelectionInLeft()
	{
		if (!this.isSelected())
		{
			return;
		}
		if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			if (this.selection().x() - 1 >= 0)
			{
				this.selection().x -= 1;
			}
		}
		else if (this.selection().y() - 1 >= 0)
		{
			this.selection().y -= 1;
		}
		if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			this.position(this.selection().y());
		}
		else
		{
			this.position(this.selection().x());
		}

		if (this.selection().x() == this.selection().y())
		{
			this.position(this.selection().x());
			this.selected(false);
		}
	}

	public void selectRight()
	{
		if (!this.isSelected())
		{
			this.startSelection();
			if (this.selection().y() + 1 <= this.content().length())
			{
				this.selection().y += 1;
			}
			this.selectionDirection(Direction.RIGHT);
		}
		else if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			if (this.selection().y() + 1 <= this.content().length())
			{
				this.selection().y += 1;
			}
		}
		else if (this.selection().x() + 1 <= this.content().length())
		{
			this.selection().x += 1;
		}
		if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			this.position(this.selection().y());
		}
		else
		{
			this.position(this.selection().x());
		}

		if (this.selection().x() == this.selection().y())
		{
			this.position(this.selection().x());
			this.selected(false);
		}
	}

	public void selectLeft()
	{
		if (!this.isSelected())
		{
			this.selectionDirection(Direction.LEFT);
			this.startSelection();
			if (this.selection().x() - 1 >= 0)
			{
				this.selection().x -= 1;
			}
		}
		else if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			if (this.selection().y() - 1 >= 0)
			{
				this.selection().y -= 1;
			}
		}
		else if (Direction.LEFT.equals(this.selectionDirection()) && this.selection().x() - 1 >= 0)
		{
			this.selection().x -= 1;
		}
		if (Direction.RIGHT.equals(this.selectionDirection()))
		{
			this.position(this.selection().y());
		}
		else
		{
			this.position(this.selection().x());
		}

		if (this.selection().x() == this.selection().y())
		{
			this.position(this.selection().x());
			this.selected(false);
		}
	}

	public String copy()
	{
		if (this.isSelected())
		{
			return this.selectionStr();
		}

		return null;
	}

	public String cut()
	{
		if (this.isSelected())
		{
			final var selection = this.selectionStr();

			this.deleteSelection();

			return selection;
		}

		return null;
	}

	public void selectAll()
	{
		this.selected(true);
		this.selection().x	= 0;
		this.selection().y	= this.content().length();
	}

	public int rightWord()
	{
		if (this.position() + 1 <= this.content().length()
				&& this.content().substring(this.position(), this.position() + 1).matches("[aA-zZ]"))
		{
			while (this.position() + 1 <= this.content().length()
					&& this.content().substring(this.position(), this.position() + 1).matches("[aA-zZ]"))
			{
				this.right();
			}
		}
		else
		{
			while (this.position() + 1 <= this.content().length()
					&& !this.content().substring(this.position(), this.position() + 1).matches("[aA-zZ]"))
			{
				this.right();
			}
		}

		return this.position();
	}

	public int leftWord()
	{
		if (this.position() - 1 >= 0
				&& this.content().substring(this.position() - 1, this.position()).matches("[aA-zZ]+"))
		{
			while (this.position() - 1 >= 0
					&& this.content().substring(this.position() - 1, this.position()).matches("[aA-zZ]"))
			{
				this.left();
			}
		}
		else
		{
			while (this.position() - 1 >= 0
					&& !this.content().substring(this.position() - 1, this.position()).matches("[aA-zZ]"))
			{
				this.left();
			}
		}

		return this.position();
	}

	public int right()
	{
		if (this.isSelected())
		{
			this.position(Math.max(this.selection().x(), this.selection().y()));
			this.cancelSelection();
		}
		else if (this.position() + 1 <= this.content().length())
		{
			return this.increasePosition();
		}

		return this.position();
	}

	public int left()
	{
		if (this.isSelected())
		{
			this.position(Math.min(this.selection().x(), this.selection().y()));
			this.cancelSelection();
		}
		else if (this.position() - 1 >= 0)
		{
			return this.decreasePosition();
		}

		return this.position();
	}

	public boolean insert()
	{
		this.insertMode(!this.insertMode());

		return this.insertMode();
	}

	public String delete()
	{
		if (this.isSelected())
		{
			this.deleteSelection();
		}
		else if (this.position() >= 0 && this.position() + 1 <= this.content().length())
		{
			this.content().delete(this.position(), this.position() + 1);
		}

		return this.content().toString();
	}

	public String backspace()
	{
		if (this.isSelected())
		{
			this.deleteSelection();
		}
		else if (this.position() - 1 >= 0 && this.position() <= this.content().length())
		{
			this.content().delete(this.position() - 1, this.position());

			this.decreasePosition();
		}

		return this.content().toString();
	}

	public String addTabulation()
	{
		return this.record("	");
	}

	public String contentStr()
	{
		return this.content().toString();
	}

	private int increasePosition()
	{
		return this.position++;
	}

	private int increasePosition(int valueIn)
	{
		return this.position += valueIn;
	}

	private int decreasePosition()
	{
		return this.position--;
	}

	@SuppressWarnings("unused")
	private int decreasePosition(int valueIn)
	{
		return this.position -= valueIn;
	}

	private boolean isCorrect(int positionIn)
	{
		return positionIn >= 0 && positionIn <= this.content().length();
	}

	public StringBuilder content()
	{
		return this.content;
	}

	public void content(StringBuilder contentIn)
	{
		this.content = contentIn;
	}

	public int position()
	{
		return this.position;
	}

	private void position(int positionIn)
	{
		this.position = positionIn;
	}

	public boolean insertMode()
	{
		return this.insertMode;
	}

	public void insertMode(boolean insertModeIn)
	{
		this.insertMode = insertModeIn;
	}

	public Vector2i selection()
	{
		return this.selection;
	}

	public void selection(Vector2i selectionIn)
	{
		this.selection = selectionIn;
	}

	public boolean isSelected()
	{
		return this.isSelected;
	}

	public void selected(boolean isSelectedIn)
	{
		this.isSelected = isSelectedIn;
	}

	public Direction selectionDirection()
	{
		return this.selectionDirection;
	}

	public void selectionDirection(Direction selectionDirectionIn)
	{
		this.selectionDirection = selectionDirectionIn;
	}
}