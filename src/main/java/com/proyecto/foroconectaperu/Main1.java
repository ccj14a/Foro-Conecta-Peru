package com.proyecto.foroconectaperu;

import javax.swing.JOptionPane;

import com.proyecto.Clases.Administrador;
import com.proyecto.Clases.Foro;
import com.proyecto.Clases.Mensaje;
import com.proyecto.Clases.Tema;
import com.proyecto.Clases.Usuario;
import com.proyecto.Menus.MenuAdministracion;
import com.proyecto.Menus.MenuForo;
import com.proyecto.Menus.MenuRegistro;
import com.proyecto.Menus.MenuUusario;
import com.proyecto.Otros.ColoresPanel;
import com.proyecto.Otros.Formatos;
import com.proyecto.Otros.InputLogin;
import com.proyecto.Otros.MensajeInicio;

public class Main1 {

    public static void main(String[] args) {
        clearConsole();
        ColoresPanel.colorPanel();
        int opRegistro = 0;
        Usuario gestionU = new Usuario();
        gestionU.loadUser(); // Cargar usuarios al inicio
        Administrador gestionA = new Administrador();
        gestionA.cargaAdmin();// PARA CARGAR LOS ADMINISTARDORES ACTUALES REGISTRADOS
        Foro foro = new Foro(); // Crear instancia de foro y cargar temas
        Usuario usuarioActual = null;
        Administrador adminActualNormal = null;
        StringBuilder m = MensajeInicio.mensaje();
        JOptionPane.showMessageDialog(null, m);

        do {
            opRegistro = MenuRegistro.menuRegistro();
            switch (opRegistro) {
                case 1:
                    String user = InputLogin.pedirUsuario();
                    String password = InputLogin.pedirContra();
                    usuarioActual = gestionU.login(user, password);
                    if (usuarioActual != null) {
                        JOptionPane.showMessageDialog(null,
                                "Sesión Iniciada");
                        int opUsuario = 0;
                        do {

                            opUsuario = MenuUusario.menuUsuario(usuarioActual);

                            switch (opUsuario) {
                                case 1:
                                    int opF;
                                    do {
                                        foro.mostrarForo();

                                        opF = MenuForo.menuForo(usuarioActual);
                                        // clearConsole();

                                        switch (opF) {

                                            case 1:
                                                String titulo = JOptionPane
                                                        .showInputDialog("Ingrese el título del nuevo tema:");
                                                if (foro.crearTema(titulo, usuarioActual.getUsuario())) {
                                                    foro.mostrarForo();
                                                    clearConsole();
                                                }
                                                clearConsole();
                                                break;

                                            case 2:
                                                // Agregar un mensaje a un tema
                                                int idTema = Integer.parseInt(JOptionPane.showInputDialog(
                                                        "Ingrese el número del tema al que desea agregar un mensaje:"));
                                                String autor = usuarioActual.getUsuario();
                                                String contenido = JOptionPane.showInputDialog("Ingrese su mensaje:");
                                                foro.aMensaje(idTema, autor, contenido); // Ahora se usa el ID en lugar
                                                // del título
                                                foro.mostrarForo();
                                                clearConsole();
                                                break;
                                            case 3:
                                                // Responder a un mensaje
                                                idTema = Integer.parseInt(JOptionPane.showInputDialog(
                                                        "Ingrese el número del tema al que desea dejar un comentario:"));
                                                int numMensaje = Integer.parseInt(JOptionPane
                                                        .showInputDialog("Ingresa el número del mensaje a comentar:"));
                                                autor = usuarioActual.getUsuario();
                                                contenido = JOptionPane.showInputDialog("Ingrese el comentario:");
                                                foro.responderMensaje(idTema, numMensaje, autor, contenido); // Se usa
                                                // el ID
                                                // para
                                                // responder
                                                foro.mostrarForo();
                                                clearConsole();
                                                break;

                                            /*
                                             * case 4:
                                             * foro.mostrarForo();
                                             * break;
                                             */
                                            case 4:
                                                String palabraClave = JOptionPane.showInputDialog(
                                                        "Ingrese la palabra clave para buscar en el foro:");
                                                if (palabraClave != null && !palabraClave.trim().isEmpty()) {
                                                    foro.buscarPorPalabraClave(palabraClave);
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Debe ingresar una palabra clave válida.", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                                foro.mostrarForo();
                                                clearConsole();

                                                break;
                                            case 5:
                                                String autorX = JOptionPane
                                                        .showInputDialog("Ingrese el nombre del autor a filtrar:");
                                                boolean encontrado = false;
                                                StringBuilder mensajesAutor = new StringBuilder();

                                                for (Tema tema : foro.getTemas()) {
                                                    for (Mensaje mensaje : tema.getMensajes()) {
                                                        // Verificar el mensaje principal
                                                        if (mensaje.getAutor().equalsIgnoreCase(autorX)) {
                                                            mensajesAutor.append(mensaje.toString()).append("\n");
                                                            encontrado = true;
                                                        }
                                                        // Verificar las respuestas del mensaje principal
                                                        for (Mensaje respuesta : mensaje.getRespuestas()) {
                                                            if (respuesta.getAutor().equalsIgnoreCase(autorX)) {
                                                                mensajesAutor.append(respuesta.toString()).append("\n");
                                                                encontrado = true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if (encontrado) {
                                                    String mensajesFormateados = Formatos
                                                            .limpiarFormatoConsola(mensajesAutor.toString());
                                                    JOptionPane.showMessageDialog(null, mensajesFormateados,
                                                            "Mensajes del Autor: " + autorX,
                                                            JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "No se encontraron mensajes del autor: " + autorX, "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                                foro.mostrarForo();
                                                clearConsole();
                                                break;
                                            case 6:
                                                int idB = Integer
                                                        .parseInt(JOptionPane.showInputDialog(null, "ID del tema:"));
                                                foro.deleteTema(idB, usuarioActual.getUsuario());
                                                clearConsole();
                                                break;
                                            case 7:
                                                int idBu = Integer
                                                        .parseInt(JOptionPane.showInputDialog(null, "ID del tema:"));
                                                int numE = Integer
                                                        .parseInt(JOptionPane.showInputDialog(null, "ID del mensaje:"));
                                                foro.eliminarMensaje(idBu, numE, usuarioActual.getUsuario());
                                                clearConsole();
                                                break;

                                            case 8:
                                                int idBus = Integer
                                                        .parseInt(JOptionPane.showInputDialog(null, "ID del tema:"));
                                                int numMen = Integer
                                                        .parseInt(JOptionPane.showInputDialog(null, "ID del mensaje:"));
                                                int numR = Integer
                                                        .parseInt(JOptionPane.showInputDialog(null,
                                                                "ID del comentario:"));
                                                foro.eliminarRespuesta(idBus, numMen, numR, usuarioActual.getUsuario());
                                                clearConsole();
                                                break;
                                            case 9:
                                                clearConsole();
                                                break;
                                        }
                                    } while (opF != 9);
                                    break;

                                case 2:
                                    JOptionPane.showMessageDialog(null,
                                            "Nombres: "
                                                    + usuarioActual.getNombres() + "\n"
                                                    + "Usuario: " + usuarioActual.getUsuario() + "\n" + "Contraseña: "
                                                    + usuarioActual.getContra() + "\nEdad: " + usuarioActual.getEdad()
                                                    + "\nEstado: " + usuarioActual.getEstadoComoTexto(),
                                            "👤:  @" + usuarioActual.getUsuario(),
                                            JOptionPane.INFORMATION_MESSAGE);

                                    break;
                                case 3:
                                    gestionU.modificarAtributosUsuario(usuarioActual);
                                    break;
                                case 4:
                                    JOptionPane.showMessageDialog(null, "Sesion cerrada con éxito");
                                    break;

                            }
                        } while (opUsuario != 4 && usuarioActual != null);
                    } else {

                        JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Credenciales incorrectas",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    int opAdmin = 0;
                    Administrador admin = new Administrador();
                    String adminUserG = InputLogin.pedirUsuario();
                    String adminPasswordG = InputLogin.pedirContra();
                    if (admin.loginAG(adminUserG, adminPasswordG)) {

                        JOptionPane.showMessageDialog(null, "Credenciales correctas. Sesion iniciada", "Mensaje",
                                JOptionPane.INFORMATION_MESSAGE);

                    } else {

                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    do {

                        opAdmin = MenuAdministracion.menuAdmin();

                        switch (opAdmin) {
                            case 1:
                                if (gestionU.isListaVacia()) {
                                    JOptionPane.showMessageDialog(null, "No hay usuarios registrados, hasta el momento",
                                            "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                                    break;
                                }
                                int opOpcion;
                                do {
                                    opOpcion = gestionU.mostrarUsuariosEnLista();
                                    Usuario usuario = null;
                                    switch (opOpcion) {

                                        case 0: // Desactivar Usuario
                                            String userx = JOptionPane
                                                    .showInputDialog("Ingrese el nombre del usuario:");
                                            usuario = admin.desactivarUser(userx, gestionU.getCab()); // Supone que
                                            // devuelve un
                                            // objeto Usuario
                                            // o null
                                            if (usuario != null) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Usuario: " + userx + " Desactivado");
                                                gestionU.saveUser();
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Usuario no encontrado",
                                                        "INFORMACION", JOptionPane.INFORMATION_MESSAGE, null);
                                            }
                                            break;
                                        case 1: // Reactivar Usuario
                                            String userToReactivate = JOptionPane
                                                    .showInputDialog("Ingrese el nombre del usuario:");
                                            usuario = admin.activarUser(userToReactivate, gestionU.getCab());
                                            if (usuario != null) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Usuario: " + userToReactivate + " Reactivado");
                                                gestionU.saveUser();

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Usuario no encontrado",
                                                        "INFORMACION", JOptionPane.INFORMATION_MESSAGE, null);
                                            }
                                            break;
                                        case 2: // Eliminar Usuario
                                            String userE = JOptionPane
                                                    .showInputDialog("Ingrese el nombre del usuario:");
                                            usuario = admin.EliminarUser(userE, gestionU.getCab());
                                            if (usuario != null) {
                                                JOptionPane.showMessageDialog(null, "Usuario: " + userE + " Eliminado");
                                                gestionU.saveUser();

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Usuario no encontrado",
                                                        "INFORMACION", JOptionPane.INFORMATION_MESSAGE, null);
                                            }
                                    }
                                    break;

                                } while (opOpcion != 3);
                                break;
                            case 2:
                                if (gestionA.adminVacio()) {
                                    JOptionPane.showMessageDialog(null, "No hay administardores agregados");
                                    break;
                                }
                                int opcion = gestionA.mostrarAdministradoresEnLista();

                                break;
                            case 3:
                                String userA = JOptionPane.showInputDialog("Usuario: ");
                                gestionA.agregarAdministradorEspecifico(userA);

                                break;

                            case 4:
                                StringBuilder temasMensaje = new StringBuilder("Temas creados:\n");
                                for (Tema tema : foro.getTemas()) {
                                    temasMensaje.append(tema.getTitulo()).append("\n");
                                }
                                JOptionPane.showMessageDialog(null, temasMensaje.toString(), "Temas",
                                        JOptionPane.INFORMATION_MESSAGE);

                                break;
                            case 5:
                                StringBuilder mensajesMensaje = new StringBuilder("Mensajes:\n");
                                for (Tema tema : foro.getTemas()) {
                                    mensajesMensaje.append("Tema: ").append(tema.getTitulo()).append("\n");
                                    for (Mensaje mensaje : tema.getMensajes()) {
                                        mensajesMensaje.append(mensaje.toString()).append("\n");
                                    }
                                }
                                Formatos.mostrarMensajesEnPaginas(mensajesMensaje.toString(), "Mensajes");
                                break;
                            /*
                             * case 6:
                             * // Solicitar fechas al usuario
                             * String fechaInicioStr = JOptionPane
                             * .showInputDialog("Ingrese la fecha de inicio (YYYY-MM-DD):");
                             * String fechaFinStr = JOptionPane
                             * .showInputDialog("Ingrese la fecha de fin (YYYY-MM-DD):");
                             * 
                             * // Convertir las fechas ingresadas por el usuario a objetos LocalDate
                             * LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
                             * LocalDate fechaFin = LocalDate.parse(fechaFinStr);
                             * 
                             * // Validar que la fecha de fin sea posterior a la fecha de inicio
                             * if (fechaFin.isBefore(fechaInicio)) {
                             * JOptionPane.showMessageDialog(null,
                             * "La fecha de fin debe ser posterior a la fecha de inicio.", "Error",
                             * JOptionPane.ERROR_MESSAGE);
                             * break;
                             * }
                             * 
                             * // Filtrar mensajes por fecha
                             * StringBuilder mensajesFiltrados = new StringBuilder();
                             * boolean encontradoFecha = false;
                             * for (Tema tema : foro.getTemas()) {
                             * for (Mensaje mensaje : tema.getMensajes()) {
                             * LocalDateTime fechaMensaje = LocalDateTime.parse(mensaje.getFechaHora(),
                             * Mensaje.formatter); // Accede a formatter como AtrMensaje.formatter
                             * LocalDate fechaMensajeLocalDate = fechaMensaje.toLocalDate();
                             * if (!fechaMensajeLocalDate.isBefore(fechaInicio)
                             * && !fechaMensajeLocalDate.isAfter(fechaFin)) {
                             * mensajesFiltrados.append(mensaje.toString()).append("\n");
                             * encontradoFecha = true;
                             * }
                             * }
                             * }
                             * // Mostrar resultados en JOptionPane
                             * if (encontradoFecha) {
                             * String mensajesFormateados = Formatos
                             * .limpiarFormatoConsola(mensajesFiltrados.toString());
                             * JOptionPane.showMessageDialog(null,
                             * "<html><pre>" + mensajesFormateados + "</pre></html>",
                             * "Mensajes entre " + fechaInicioStr + " y " + fechaFinStr,
                             * JOptionPane.INFORMATION_MESSAGE);
                             * } else {
                             * JOptionPane.showMessageDialog(null,
                             * "No se encontraron mensajes entre " + fechaInicioStr + " y " + fechaFinStr,
                             * "Error", JOptionPane.ERROR_MESSAGE);
                             * }
                             * break;
                             */

                            case 6:
                                JOptionPane.showMessageDialog(null, "Sesion cerrada con éxito");
                                break;
                        }

                    } while (opAdmin != 6);
                    break;
                case 3:
                    String nombre = JOptionPane.showInputDialog(null, "Nombres*", "Registro de Usuario",
                            JOptionPane.QUESTION_MESSAGE);
                    String usuario = JOptionPane.showInputDialog(null, "Usuario*", "Registro de Usuario",
                            JOptionPane.QUESTION_MESSAGE);
                    if (gestionU.findUser(usuario) != null) {
                        JOptionPane.showMessageDialog(null, "Usuario no disponible");
                        continue;
                    }
                    String contraseña = JOptionPane.showInputDialog(null, "Contraseña*", "Registro de Usuario",
                            JOptionPane.QUESTION_MESSAGE);
                    int edad = Integer.parseInt(JOptionPane.showInputDialog(null, "Edad*", "Registro de Usuario",
                            JOptionPane.QUESTION_MESSAGE));
                    Usuario nuevo = new Usuario(nombre, usuario, contraseña, edad);
                    gestionU.addUser(nuevo);
                    JOptionPane.showMessageDialog(null, "Se ha creado con éxito su cuenta");
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Se ha salido con éxito de la aplicación");
                    // System.exit(0);
            }

        } while (opRegistro != 4);

    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }

}
