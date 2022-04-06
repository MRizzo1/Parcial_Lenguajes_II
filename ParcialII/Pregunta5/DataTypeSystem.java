import java.io.*;
import java.util.*;

class DataTypeSystem {

    /**
     * Hashmap to store a name and the class such represent the atom with that name
     */
    private HashMap<String, Atom> atoms;

    /**
     * Hashmap to store a name and the class such represent the atom with that name
     */
    private HashMap<String, Struct> structs;

    /**
     * Hashmap to store a name and the class such represent the atom with that name
     */
    private HashMap<String, TypeArray> typeArrays;

    /**
     * Indicates that the simulation has started
     */
    private Boolean simulation;

    DataTypeSystem() {
        this.atoms = new HashMap<>();
        this.structs = new HashMap<>();
        this.typeArrays = new HashMap<>();
        this.simulation = true;
    }

    void ATOMICO(String name, int representation, int alignment) {

        if (atoms.containsKey(name) || structs.containsKey(name) || typeArrays.containsKey(name)) {
            System.out.println(
                    "Hubo un fallo al crear " + name + ", el nombre ya fue usado");
            return;
        }

        atoms.put(name, new Atom(name, representation, alignment));
    }

    void STRUCT(String name, LinkedList<String> fieldsAtoms) {

        if (atoms.containsKey(name) || structs.containsKey(name) || typeArrays.containsKey(name)) {
            System.out.println(
                    "Hubo un fallo al crear " + name + ", el nombre ya fue usado");
            return;
        }

        LinkedList<Atom> fields = new LinkedList<Atom>();
        String typeField = "";

        for (int i = 0; i < fieldsAtoms.size(); i++) {
            typeField = fieldsAtoms.get(i);

            if (!atoms.containsKey(typeField)) {
                System.out.println(
                        "Hubo un fallo al crear " + name + ", el tipo " + typeField + " no existe entre los atómicos");
                return;
            }

            fields.add(atoms.get(typeField));
        }

        structs.put(name, new Struct(name, fields));
    }

    void ARREGLO(String name, String arrayType, int size) {

        if (atoms.containsKey(name) || structs.containsKey(name) || typeArrays.containsKey(name)) {
            System.out.println(
                    "Hubo un fallo al crear " + name + ", el nombre ya fue usado");
            return;
        }

        if (size < 0) {
            System.out.println(
                    "El tamaño no puede ser negativo");
            return;
        }

        if (structs.containsKey(arrayType)) {
            typeArrays.put(name, new TypeArray(name, structs.get(arrayType), size));
            return;
        }

        if (atoms.containsKey(arrayType)) {
            typeArrays.put(name, new TypeArray(name, atoms.get(arrayType), size));
            return;
        }

        System.out.println(
                "No existe " + arrayType);
    }

    /**
     * Simulating sizeOf operator in C
     */
    int[] dataUnpacked(Struct s) {

        ListIterator<Atom> atomIterator = s.getFields().listIterator();

        Atom current;
        int size = atomIterator.next().getAlignment(), unused = 0, p;

        while (atomIterator.hasNext()) {
            p = (4 - size % 4) % 4;

            current = atomIterator.next();

            if (p - current.getAlignment() >= 0 && size % 4 != 0) {
                size += current.getAlignment();
            } else {
                size += p + current.getAlignment();
                unused += p;
            }

        }

        int[] ans = new int[2];
        ans[0] = size + (4 - size % 4) % 4;
        ans[1] = unused + (4 - size % 4) % 4;

        return ans;
    }

    /**
     * Simulating sizeOf with packed operator in C
     */
    int[] dataPacked(Struct s) {

        ListIterator<Atom> atomIterator = s.getFields().listIterator();

        int size = 0, unused = 0;

        while (atomIterator.hasNext()) {
            size += atomIterator.next().getAlignment();
        }

        int[] ans = new int[2];
        ans[0] = size + (4 - size % 4) % 4;
        ans[1] = unused + (4 - size % 4) % 4;

        return ans;
    }

    void DESCRIBIR(String name) {
        if (atoms.containsKey(name)) {
            Atom a = atoms.get(name);
            System.out.println(
                    a.getName() + " representación: " + a.getRepresentation() + " | alineación: " + a.getAlignment());
            return;
        }

        if (structs.containsKey(name)) {
            Struct s = structs.get(name);
            int[] dataUnpacked = dataUnpacked(s);
            int[] dataPacked = dataPacked(s);
            System.out.println(s.getName() + "\nSIN EMPAQUETAR \n   tamaño: " + dataUnpacked[0]
                    + " | bytes desperdiciados: " + dataUnpacked[1] + "\n");
            System.out.println("EMPAQUETADO \n   tamaño: " + dataPacked[0]
                    + "\n   bytes desperdiciados: " + dataPacked[1]);
            return;
        }

        if (typeArrays.containsKey(name)) {
            TypeArray ta = typeArrays.get(name);
            if (ta.getitemsStruct().getLast().getName() != null){
            System.out.println(
                    ta.getName() + " Tipo: " + ta.getitemsStruct().getLast().getName() + "  |   Num de elementos: " + ta.getitemsStruct().size());
            return;}
            System.out.println(
                    ta.getName() + " Tipo: " + ta.getitemsAtom().getLast().getName() + "  |   Num de elementos: " + ta.getitemsAtom().size());
            return;
        }

        System.out.println("No existe " + name);

    }

    void SALIR() {
        simulation = false;
    }

    public static void main(String args[]) throws IOException {
        String[] action;

        DataTypeSystem typeSystem = new DataTypeSystem();
        try (Scanner sc = new Scanner(System.in)) {
            while (typeSystem.simulation) {
                System.out.println("Inserte una acción a ejecutar (ATOMICO, STRUCT, ARREGLO, DESCRIBIR, SALIR)");
                action = sc.nextLine().split(" ");
                if (action[0].equals("ATOMICO")) {
                    if (action.length < 4) {
                        System.out.println("Numero de parametros insuficiente. ATOMICO <nombre> <representación> <alineación>");
                        continue;
                    }

                    typeSystem.ATOMICO(action[1], Integer.parseInt(action[2]), Integer.parseInt(action[3]));
                } else if (action[0].equals("STRUCT")) {
                    if (action.length < 3) {
                        System.out.println("Numero de parametros insuficiente. STRUCT <nombre> <[campos]>");
                        continue;
                    }

                    LinkedList<String> f = new LinkedList<String>();

                    for (int i = 2; i < action.length; i++) {
                        f.add(action[i]);
                    }

                    typeSystem.STRUCT(action[1], f);
                } else if (action[0].equals("ARREGLO")) {
                    if (action.length < 4) {
                        System.out.println("Numero de parametros insuficiente. ARREGLO <nombre> <tipo> <tamaño>");
                        continue;
                    }

                    typeSystem.ARREGLO(action[1], action[2], Integer.parseInt(action[3]));
                } else if (action[0].equals("DESCRIBIR")) {
                    if (action.length < 2) {
                        System.out.println("Numero de parametros insuficiente. DESCRIBIR <nombre>");
                        continue;
                    }

                    typeSystem.DESCRIBIR(action[1]);
                } else if (action[0].equals("SALIR")) {
                    System.out.println("Saliendo del programa.");
                    typeSystem.SALIR();
                } else {
                    System.out
                            .println("Acción no valida. Las acciones validas son:  (ATOMICO, STRUCT, ARREGLO, DESCRIBIR, SALIR)");
                }
            }
        }
    }
}
