import java.util.LinkedList;

public class Struct {

    /**
     * Name of the Struct
     */
    private String name;

    /**
     * FIelds of the Struct
     */
    private LinkedList<Atom> fields;

    /**
     * Gets the name of the Struct
     * 
     * @return name of the Struct
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the Struct
     * 
     * @param name of the Struct
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the fields of the Struct
     * 
     * @return fields of the Struct
     */
    public LinkedList<Atom> getFields() {
        return fields;
    }

    /**
     * Inserts a field in the Struct.
     * 
     * @param atom Atom to add.
     */
    public void insertField(Atom atom) {
        fields.add(atom);
    }

    public Struct(String name, LinkedList<Atom> fields) {
        this.name = name;
        this.fields = fields;
    }

    public Struct(String name) {
        this.name = name;
        this.fields = new LinkedList<Atom>();
    }

    public Struct() {
        this.name = "";
        this.fields = new LinkedList<Atom>();
    }
}
