public class Atom {
    /**
     * Name of the atom
     */
    private String name;

    /**
     * Bytes representation
     */
    private int representation;

    /**
     * Bytes representation
     */
    private int alignment;

    /**
     * Gets the name of the Atom
     * 
     * @return name of the Atom
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the bytes representation of the Atom
     * 
     * @return bytes representation of the Atom
     */
    public int getRepresentation() {
        return representation;
    }

    /**
     * Gets the alignment of the Atom
     * 
     * @return alignment of the Atom
     */
    public int getAlignment() {
        return alignment;
    }

    public Atom(String name, int representation, int alignment) {
        this.name = name;
        this.representation = representation;
        this.alignment = alignment;
    }
}