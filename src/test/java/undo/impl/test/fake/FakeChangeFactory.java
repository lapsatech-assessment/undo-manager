package undo.impl.test.fake;

import undo.Change;
import undo.Document;

public abstract class FakeChangeFactory {

    private FakeChangeFactory() {
    }

    private static class DeletionChange implements Change {

	private final int pos;
	private final String s;

	private DeletionChange(final int pos, final String s) {
	    this.pos = pos;
	    this.s = s;
	}

	@Override
	public void apply(final Document doc) {
	    doc.delete(pos, s);
	}

	@Override
	public void revert(final Document doc) {
	    doc.insert(pos, s);
	}
    }

    private static class InsertionChange implements Change {

	private final int pos;
	private final String s;

	private InsertionChange(final int pos, final String s) {
	    this.pos = pos;
	    this.s = s;
	}

	@Override
	public void apply(final Document doc) {
	    doc.insert(pos, s);
	}

	@Override
	public void revert(final Document doc) {
	    doc.delete(pos, s);
	}
    }

    public static Change deletion(final int pos, final String s) {
	return new DeletionChange(pos, s);
    }

    public static Change insertion(final int pos, final String s) {
	return new InsertionChange(pos, s);
    }

}
