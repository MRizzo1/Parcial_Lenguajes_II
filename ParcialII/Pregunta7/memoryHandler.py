import ctypes


class ptr:
    def __init__(self, obj):
        self._ptr = {
            "obj": obj
        }

    @property
    def obj(self):
        return self._ptr["obj"]

    def set_obj(self, obj):
        self._ptr["obj"] = obj


def allocate(name, value, table):
    if (name in table.keys() and not table[name].obj is None):
        print("No se pudo reservar" + name + ", el nombre ya fue asignado")
        return 0

    if (name in table.keys() and table[name].obj is None):
        table[name].set_obj(value)
    else:
        table[name] = ptr(value)
    print("Se reservó el puntero '" + name + "' con valor '" + table[name].obj + "'")
    return 1


def assign(name1, name2, table):
    if (not name2 in table.keys() or table[name2].obj is None):
        print("ERROR, el nombre '" + name2 + "' no apunta a un valor válido")
        return 0

    table[name1] = table[name2]
    print("Ahora el puntero '" + name1 + "' apunta al mismo objeto que '" + name2 + "'")
    return 1


def free(name, table):
    if (not name in table.keys() or table[name].obj is None):
        print("ERROR, el nombre '" + name + "' no apunta a un valor válido")
        return 0

    table[name].set_obj(None)
    print("Se liberó '" + name + "'")
    return 1


def printPtr(name, table):
    if (not name in table.keys() or table[name].obj is None):
        print("ERROR, el nombre '" + name + "' no apunta a un valor válido")
        return 0

    print(table[name].obj)
    return 1


def main():
    table = {}
    exit = False
    print("Se usó algo similar a tombstone. Quasi-tombstone-pythonico")

    while (not exit):
        ipt = input(
            "Seleccione una acción con sus parámetros adecuados (RESERVAR, ASIGNAR, LIBERAR, IMPRIMIR, SALIR) \n").split()

        if (len(ipt) > 0):
            if (ipt[0] == "SALIR"):
                exit = True
            elif (ipt[0] == "IMPRIMIR" and len(ipt) == 2):
                printPtr(ipt[1], table)
            elif (ipt[0] == "LIBERAR" and len(ipt) == 2):
                free(ipt[1], table)
            elif (ipt[0] == "ASIGNAR" and len(ipt) == 3):
                assign(ipt[1], ipt[2], table)
            elif (ipt[0] == "RESERVAR" and len(ipt) == 3):
                allocate(ipt[1], ipt[2], table)
            elif (ipt[0] == "HELP"):
                print(
                    " RESERVAR <nombre> <valor> \n ASIGNAR <nombre1> <nombre2> \n LIBERAR <nombre> \n IMPRIMIR <nombre> \n SALIR")
            else:
                print(
                    "Accion invalida o con insuficientes parametros. Escriba HELP para revisar el listado de acciones")
        else:
            print(
                "Por favor, elija una acción de todas las posibles. Escriba HELP para poder ver un listado")


if __name__ == "__main__":
    main()
