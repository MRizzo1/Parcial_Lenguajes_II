// Operadores a usar y su funcionamiento
var operators = {
    "+": function(a, b) {
        return a + b
    },
    "-": function(a, b) {
        return a - b
    },
    "*": function(a, b) {
        return a * b
    },
    "/": function(a, b) {
        return a / b
    }
}

//Funcion que realiza la evaluacion de expresiones en orden prefijo
function evaluatePrefix(expression) {
    //Si el primer elemento actual de la expresion a evaluar es un numero,
    //se añade al valor actual y se quita de lo que resta de la expresion
    if (!isNaN(parseFloat(expression[0]))) {
        return {
            total: parseFloat(expression[0]),
            rest: expression.slice(1)
        }
    } else {
        var operation = operators[expression[0]] //Funcion que representa el operador actual a evaluar

        var first = evaluatePrefix(expression.slice(1))
        if (first.total == null) return { total: null, rest: [] } //Si no hay primer valor a evaluar

        var second = evaluatePrefix(first.rest)
        if (second.total == null) return { total: null, rest: [] } //Si no hay segundo valor a evaluar

        //Se realiza la evaluacion
        return {
            total: operation(first.total, second.total),
            rest: second.rest
        }
    }
}

function evaluatePostfix(expression) {
    //Si el primer elemento actual de la expresion a evaluar es un numero,
    //se añade al valor actual y se quita de lo que resta de la expresion
    if (!isNaN(parseFloat(expression[expression.length - 1]))) {
        return {
            total: parseFloat(expression.pop()),
            rest: expression
        }
    } else {
        var operation = operators[expression.pop()] //Funcion que representa el operador actual a evaluar

        var second = evaluatePostfix(expression)
        if (second.total == null) return { total: null, rest: [] } //Si no hay segundo valor a evaluar

        var first = evaluatePostfix(second.rest)
        if (first.total == null) return { total: null, rest: [] } //Si no hay primer valor a evaluar

        //Se realiza la evaluacion
        return {
            total: operation(first.total, second.total),
            rest: second.rest
        }
    }
}

function showPostfix(expression, result) {
    //Cuando un elemento es un numero
    if (!isNaN(parseFloat(expression[expression.length - 1]))) {
        result.push(expression.pop())
        return {
            result: result,
            rest: expression
        }
    } else {
        var operation = expression.pop() //Operador a usar

        var second = showPostfix(expression, result)
        if (second.result == null) return { result: null, rest: [] } //Si no hay segundo valor a evaluar

        var first = showPostfix(second.rest, result)
        if (first.result == null) return { result: null, rest: [] } //Si no hay primer valor a evaluar

        f = result.pop() //Se extrae el primer valor
        s = result.pop() //Se extrae el segundo valor
        result.push("(" + f + " " + operation + " " + s + ")") //Se inserta toda la opeeracion

        //Se almacena como resultado
        return {
            result: result,
            rest: second.rest
        }
    }
}

function showPrefix(expression, result) {
    //Cuando un elemento es un numero
    if (!isNaN(parseFloat(expression[0]))) {
        result.push(expression[0])
        return {
            result: result,
            rest: expression.slice(1)
        }
    } else {
        var operation = expression[0] //Operador a usar

        var first = showPrefix(expression.slice(1), result)
        if (first.result == null) return { result: null, rest: [] } //Si no hay primer valor a evaluar

        var second = showPrefix(first.rest, result)
        if (second.result == null) return { result: null, rest: [] } //Si no hay segundo valor a evaluar

        s = result.pop() //Se extrae el segundo valor
        f = result.pop() //Se extrae el primer valor
        result.push("(" + f + " " + operation + " " + s + ")") //Se inserta toda la opeeracion

        //Se almacena como resultado
        return {
            result: result,
            rest: second.rest
        }
    }
}

var rl = require("readline").createInterface({
    input: process.stdin,
    output: process.stdout
})

var waitForUserInput = function() {
    rl.question("Inserta un comando (EVAL, MOSTRAR, SALIR): \n", function(answer) {
        if (answer == "SALIR") {
            rl.close();
        } else if (answer.split(" ")[0] == "MOSTRAR" && answer.split(" ")[1] == "PRE") {
            console.log(showPrefix(answer.split(" ").slice(2), []).result[0]);
            waitForUserInput();
        } else if (answer.split(" ")[0] == "MOSTRAR" && answer.split(" ")[1] == "POST") {
            console.log(showPostfix(answer.split(" ").slice(2), []).result[0]);
            waitForUserInput();
        } else if (answer.split(" ")[0] == "EVAL" && answer.split(" ")[1] == "PRE") {
            console.log(evaluatePrefix(answer.split(" ").slice(2)).total);
            waitForUserInput();
        } else if (answer.split(" ")[0] == "EVAL" && answer.split(" ")[1] == "POST") {
            console.log(evaluatePostfix(answer.split(" ").slice(2)).total);
            waitForUserInput();
        } else {
            waitForUserInput();
        }
    })
}

waitForUserInput();

module.exports = evaluatePrefix;