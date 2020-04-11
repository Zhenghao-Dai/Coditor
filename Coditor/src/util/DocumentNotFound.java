package util;

public class DocumentNotFound extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DocumentNotFound(int docID) {
        super("Document " + docID + " Not Found");
    }
}