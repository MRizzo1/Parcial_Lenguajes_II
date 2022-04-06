import java.util.LinkedList;

public class TypeArray {

    /**
     * Name of the TypeArray
     */
    private String name;

    /**
     * Atom items of the TypeArray
     */
    private LinkedList<Atom> itemsAtom;

    /**
     * Struct items of the TypeArray
     */
    private LinkedList<Struct> itemsStruct;

    /**
     * Gets the name of the TypeArray
     * 
     * @return name of the TypeArray
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the itemsAtom of the TypeArray
     * 
     * @return itemsAtom of the TypeArray
     */
    public LinkedList<Atom> getitemsAtom() {
        return itemsAtom;
    }

    /**
     * Gets the itemsStruct of the TypeArray
     * 
     * @return itemsStruct of the TypeArray
     */
    public LinkedList<Struct> getitemsStruct() {
        return itemsStruct;
    }

    /**
     * Inserts a field in the TypeArray.
     * 
     * @param atom Atom to add.
     */
    public void InsertNode(Atom atom) {
        itemsAtom.add(atom);
    }

    public TypeArray(String name, Atom type, int size) {
        this.name = name;
        this.itemsAtom = new LinkedList<Atom>();
        
        for (int i = 0; i < size; i++) {
            itemsAtom.add(type);
        }
    }

    public TypeArray(String name, Struct type, int size) {
        this.name = name;
        this.itemsStruct = new LinkedList<Struct>();
        
        for (int i = 0; i < size; i++) {
            itemsStruct.add(type);
        }
    }
}
