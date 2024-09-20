import java.util.ArrayList;
import java.util.Scanner;

class HabitacionNoDisponibleException extends Exception {
    public HabitacionNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}

class HabitacionNoEncontradaException extends Exception {
    public HabitacionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

abstract class Habitacion {
    private int numero;
    private boolean ocupada;

    public Habitacion(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public void reservar() throws HabitacionNoDisponibleException {
        if (ocupada) {
            throw new HabitacionNoDisponibleException("La habitación " + numero + " ya está ocupada.");
        }
        ocupada = true;
        System.out.println("Habitación " + numero + " reservada.");
    }

    public void liberar() {
        ocupada = false;
        System.out.println("Habitación " + numero + " liberada.");
    }

    public abstract String getTipo(); 

    public String estado() {
        return "Habitación " + numero + " (" + getTipo() + ") - " + (ocupada ? "Ocupada" : "Disponible");
    }
}

class HabitacionSimple extends Habitacion {
    public HabitacionSimple(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() {
        return "Simple";
    }
}

class HabitacionDoble extends Habitacion {
    public HabitacionDoble(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() {
        return "Doble";
    }
}

class Suite extends Habitacion {
    public Suite(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() {
        return "Suite";
    }
}

// Clase Hotel
class Hotel {
    private ArrayList<Habitacion> habitaciones;

    public Hotel() {
        habitaciones = new ArrayList<>();
    }

    public void registrarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
        System.out.println("Habitación " + habitacion.getNumero() + " (" + habitacion.getTipo() + ") registrada.");
    }

    public void consultarHabitaciones() {
        System.out.println("\nEstado de todas las habitaciones:");
        for (Habitacion habitacion : habitaciones) {
            System.out.println(habitacion.estado());
        }
    }

    public Habitacion buscarHabitacion(int numero) throws HabitacionNoEncontradaException {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        throw new HabitacionNoEncontradaException("La habitación " + numero + " no existe.");
    }

    public void reservarHabitacion(int numero) {
        try {
            Habitacion habitacion = buscarHabitacion(numero);
            habitacion.reservar();
        } catch (HabitacionNoDisponibleException | HabitacionNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void liberarHabitacion(int numero) {
        try {
            Habitacion habitacion = buscarHabitacion(numero);
            habitacion.liberar();
        } catch (HabitacionNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarDisponibilidad(int numero) {
        try {
            Habitacion habitacion = buscarHabitacion(numero);
            System.out.println(habitacion.estado());
        } catch (HabitacionNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public int contarHabitacionesDisponibles() {
        int disponibles = 0;
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.estaOcupada()) {
                disponibles++;
            }
        }
        return disponibles;
    }

    public int contarHabitacionesOcupadas() {
        int ocupadas = 0;
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.estaOcupada()) {
                ocupadas++;
            }
        }
        return ocupadas;
    }
}

public class Main {
    public static void main(String[] args) {
        Hotel miHotel = new Hotel();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\nOpciones:");
            System.out.println("1. Agregar habitación");
            System.out.println("2. Consultar todas las habitaciones");
            System.out.println("3. Reservar habitación");
            System.out.println("4. Liberar habitación");
            System.out.println("5. Buscar disponibilidad de una habitación");
            System.out.println("6. Ver cuántas habitaciones están ocupadas");
            System.out.println("7. Ver cuántas habitaciones están disponibles");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el número de habitación: ");
                    int numero = scanner.nextInt();
                    System.out.print("Introduce el tipo de habitación (1=Simple, 2=Doble, 3=Suite): ");
                    int tipo = scanner.nextInt();
                    Habitacion habitacion = null;
                    switch (tipo) {
                        case 1:
                            habitacion = new HabitacionSimple(numero);
                            break;
                        case 2:
                            habitacion = new HabitacionDoble(numero);
                            break;
                        case 3:
                            habitacion = new Suite(numero);
                            break;
                        default:
                            System.out.println("Tipo de habitación no válido.");
                            break;
                    }
                    if (habitacion != null) {
                        miHotel.registrarHabitacion(habitacion);
                    }
                    break;
                case 2:
                    miHotel.consultarHabitaciones();
                    break;
                case 3:
                    System.out.print("Introduce el número de habitación a reservar: ");
                    int numeroReservar = scanner.nextInt();
                    miHotel.reservarHabitacion(numeroReservar);
                    break;
                case 4:
                    System.out.print("Introduce el número de habitación a liberar: ");
                    int numeroLiberar = scanner.nextInt();
                    miHotel.liberarHabitacion(numeroLiberar);
                    break;
                case 5:
                    System.out.print("Introduce el número de habitación para consultar: ");
                    int numeroBuscar = scanner.nextInt();
                    miHotel.buscarDisponibilidad(numeroBuscar);
                    break;
                case 6:
                    System.out.println("Habitaciones ocupadas: " + miHotel.contarHabitacionesOcupadas());
                    break;
                case 7:
                    System.out.println("Habitaciones disponibles: " + miHotel.contarHabitacionesDisponibles());
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}